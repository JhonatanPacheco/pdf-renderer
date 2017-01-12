package se.billes.pdf.renderer.validator;

import java.util.ArrayList;
import java.util.List;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.text.AbstractPhrase;
import se.billes.pdf.renderer.model.text.Paragraph;
import se.billes.pdf.renderer.model.text.Phrase;
import se.billes.pdf.renderer.request.PdfDocument;
import se.billes.pdf.renderer.request.factory.ColorFactory;
import se.billes.pdf.renderer.request.factory.FontFactory;
import se.billes.pdf.request.incoming.InputRequest;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

/**
 * This program is built on top of iText.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License version 3 as published by the Free Software Foundation with 
 * the addition of the following permission added to Section 15 as permitted in Section 7(a): 
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY ITEXT GROUP NV, ITEXT GROUP 
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of 
 * the GNU Affero General Public License along with this program; if not, 
 * see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc., 51 Franklin Street, 
 * Fifth Floor, Boston, MA, 02110-1301 USA, or download the license from the following
 * URL: http://itextpdf.com/terms-of-use/ 
 * The interactive user interfaces in modified source and object code versions of this program must 
 * display Appropriate Legal Notices, as required under Section 5 of the GNU Affero General Public License.
 * In accordance with Section 7(b) of the GNU Affero General Public License, you must retain the producer line 
 * in every PDF that is created or manipulated using iText. You can be released from the requirements of the 
 * license by purchasing a commercial license. Buying such a license is mandatory as soon as you develop 
 * commercial activities involving the iText software without disclosing the source code of your own 
 * applications. These activities include: offering paid services to customers as an ASP, 
 * serving PDFs on the fly in a web application, shipping iText with a closed source product.
 */
public class PhraseValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	private Integer paragraphIndex;
	private Integer cellIndex;

	public PhraseValidator withPageIndex( int pageIndex ){
		this.pageIndex = pageIndex;
		return this;
	}
	
	public PhraseValidator withBlockIndex( int blockIndex ){
		this.blockIndex = blockIndex;
		return this;
	}
	
	public PhraseValidator withCellIndex( int blockIndex ){
		this.cellIndex = blockIndex;
		return this;
	}
	
	

	public PhraseValidator withParagraphIndex( int paragraphIndex ){
		this.paragraphIndex = paragraphIndex;
		return this;
	}
	
	public void validate( InputRequest request, Paragraph paragraph ) throws PdfRequestNotValidException{
		
		PdfDocument document = request.getDocument();
		if( paragraph.getPhrases() == null || paragraph.getPhrases().length == 0 ){
			return;
		}
		
		int phraseCount = 0;
		for( AbstractPhrase abstractPhrase : paragraph.getPhrases() ){
			
			DocumentErrorFactory errorFactory = new DocumentErrorFactory()
			.withPageIndex(pageIndex)
			.withBlockIndex(blockIndex)
			.withParagraphIndex(paragraphIndex)
			.withCellIndex( cellIndex )
			.withPhraseIndex(phraseCount);
			
			
			if( abstractPhrase.getFontSize() == null ){
				abstractPhrase.setFontSize(paragraph.getFontSize());
			}
			
			if( abstractPhrase.getColorRef() == null ){
				if( paragraph.getColorRef() == null ){
					abstractPhrase.setBaseColor( new ColorFactory().getBlack() );
				}else{
					abstractPhrase.setBaseColor(paragraph.getBaseColor() );
				}
				
			}else{
				BaseColor color = new ColorFactory().getBaseColorByRef(document, abstractPhrase.getColorRef() );
				if( color == null ){
					throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find color for ref" ) );
				}
				abstractPhrase.setBaseColor(color);
			}
			
			
			if( abstractPhrase.getFontRef() == null ){
				if( paragraph.getFontRef() == null ){
					throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find font ref for phrase") );
				}
				abstractPhrase.setBaseFont(paragraph.getBaseFont());
			}else{
				BaseFont font = new FontFactory().getBaseFontByRef(document,abstractPhrase.getFontRef() );
				if( font == null ){
					throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find font for phrase") );
				}
				abstractPhrase.setBaseFont(font);
			}
			
			if( abstractPhrase instanceof Phrase ){
				Phrase phrase = (Phrase) abstractPhrase;
				
				if( phrase.getText() == null ){
					throw new PdfRequestNotValidException( errorFactory.appendErrorString("No text for phrase") );
				}
				if( phrase.getStyle() != null ){
					if( ! anyOf(phrase.getStyle() )){
						throw new PdfRequestNotValidException( errorFactory.appendErrorString("Style must be any of [normal,bold,italic,bolditalic]"));
					}
				}
				
				if( phrase.getTextTransform() != null ){
					String transform = phrase.getTextTransform();
					if( ! transform.equals( "uppercase") && ! transform.equals( "lowercase") ){
						throw new PdfRequestNotValidException( errorFactory.appendErrorString("Text transform must be any of [uppercase, lowercase]"));
					}
				}
				
			}
			
			abstractPhrase.setParagraph(paragraph);
			phraseCount ++;
		}
		
	}
	
	public boolean anyOf( String style ){
		List<String> styles = new ArrayList<String>();
		styles.add( "normal" );
		styles.add( "bold" );
		styles.add( "italic" );
		styles.add( "bolditalic" );
		
		return styles.contains( style );
	}
}	
