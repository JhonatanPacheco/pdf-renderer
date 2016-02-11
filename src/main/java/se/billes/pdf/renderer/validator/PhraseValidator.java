package se.billes.pdf.renderer.validator;

import java.util.ArrayList;
import java.util.List;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.text.AbstractPhrase;
import se.billes.pdf.renderer.model.text.Paragraph;
import se.billes.pdf.renderer.model.text.Phrase;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;
import se.billes.pdf.renderer.request.factory.FontFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

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
	
	public void validate( PdfRequest request, Paragraph paragraph ) throws PdfRequestNotValidException{
		
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
				BaseColor color = new ColorFactory().getBaseColorByRef(request, abstractPhrase.getColorRef() );
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
				BaseFont font = new FontFactory().getBaseFontByRef(request,abstractPhrase.getFontRef() );
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
