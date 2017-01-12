package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.alignment.HorizontalAlign;
import se.billes.pdf.renderer.model.text.AbstractParagraph;
import se.billes.pdf.renderer.model.text.Paragraph;
import se.billes.pdf.renderer.model.text.TableParagraph;
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
public class ParagraphValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public ParagraphValidator withPageIndex( int pageIndex ){
		this.pageIndex = pageIndex;
		
		return this;
	}
	
	public ParagraphValidator withBlockIndex( int blockIndex ){
		this.blockIndex = blockIndex;
		
		return this;
	}
	
	public void validateParagraph(InputRequest request, Paragraph paragraph,DocumentErrorFactory errorFactory ) throws PdfRequestNotValidException{
		PdfDocument document = request.getDocument();
		if( paragraph.getLeading() <= 0 ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString("Font leading is 0 or less") );
		}
		
		if( paragraph.getColorRef() == null ){
			paragraph.setBaseColor( new ColorFactory().getBlack() );
		}else{
			BaseColor color = new ColorFactory().getBaseColorByRef(document, paragraph.getColorRef() );
			if( color == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find color ref for paragraph"));
			}
			paragraph.setBaseColor(color);
		}
		
		if( paragraph.getFontRef() != null ){
			BaseFont font = new FontFactory().getBaseFontByRef(document,paragraph.getFontRef() );
			if( font == null ){
				throw new PdfRequestNotValidException(errorFactory.appendErrorString("Could not find font ref for paragraph"));
			}
			paragraph.setBaseFont(font);
		}
		
		if( paragraph.getHorizontalAlign() != null ){
			HorizontalAlign align = HorizontalAlign.getByName(paragraph.getHorizontalAlign());
			if( align == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Horizontal align must be any of [left,center,right]" ));
			}
		}
		
	
	}
	
	public void validate( InputRequest request, Block block ) throws PdfRequestNotValidException{
		if( block.getParagraphs() != null ){
			int count = 0;
			for( AbstractParagraph abstractParagraph : block.getParagraphs() ){
				
				DocumentErrorFactory errorFactory = new DocumentErrorFactory()
													.withPageIndex(pageIndex)
													.withBlockIndex(blockIndex)
													.withParagraphIndex(count);
				
				abstractParagraph.setBlock(block);
				
				if( abstractParagraph instanceof Paragraph  ){
					errorFactory.withCellIndex(null);
					validateParagraph(request, (Paragraph)abstractParagraph, errorFactory);
					new PhraseValidator()
					.withBlockIndex(blockIndex)
					.withPageIndex(pageIndex)
					.withParagraphIndex(count)
					.validate(request, (Paragraph)abstractParagraph);
				}

				
				if( abstractParagraph instanceof TableParagraph ){
					errorFactory.withCellIndex(null);
					TableParagraph paragraph = (TableParagraph)abstractParagraph;
					if( paragraph.getCells() == null || paragraph.getCells().length == 0 ){
						throw new PdfRequestNotValidException(  errorFactory.appendErrorString("You table must contain cells" ) );
					}
					if( paragraph.getWidths() == null || paragraph.getWidths().length == 0 ){
						throw new PdfRequestNotValidException(  errorFactory.appendErrorString("Your table must contain widths" ) );
					}
					float widths = 0f;
					for( float width : paragraph.getWidths() ){
						widths += width;
					}
					
					if( widths != block.getPosition()[2]){
						throw new PdfRequestNotValidException(  errorFactory.appendErrorString("Your table must be the size of the  block" ) );
					}
					
					int cellCount = 0;
					for( AbstractParagraph cell : paragraph.getCells()){
						if( cell instanceof Paragraph ){
							cell.setBlock(block);
							errorFactory.withCellIndex(cellCount);
							validateParagraph(request, (Paragraph)cell, errorFactory);
							
							
							new PhraseValidator()
							.withBlockIndex(blockIndex)
							.withPageIndex(pageIndex)
							.withParagraphIndex(count)
							.withCellIndex(cellCount)
							.validate(request, (Paragraph)cell);
							cellCount++;
						}
						
					}
				}
			}
		}
	}
	
}
