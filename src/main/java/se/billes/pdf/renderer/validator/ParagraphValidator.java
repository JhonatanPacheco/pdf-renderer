package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.alignment.HorizontalAlign;
import se.billes.pdf.renderer.model.text.AbstractParagraph;
import se.billes.pdf.renderer.model.text.Paragraph;
import se.billes.pdf.renderer.model.text.TableParagraph;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;
import se.billes.pdf.renderer.request.factory.FontFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

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
	
	public void validateParagraph(PdfRequest request, Paragraph paragraph,DocumentErrorFactory errorFactory ) throws PdfRequestNotValidException{

		if( paragraph.getLeading() <= 0 ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString("Font leading is 0 or less") );
		}
		
		if( paragraph.getColorRef() == null ){
			paragraph.setBaseColor( new ColorFactory().getBlack() );
		}else{
			BaseColor color = new ColorFactory().getBaseColorByRef(request, paragraph.getColorRef() );
			if( color == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find color ref for paragraph"));
			}
			paragraph.setBaseColor(color);
		}
		
		if( paragraph.getFontRef() != null ){
			BaseFont font = new FontFactory().getBaseFontByRef(request,paragraph.getFontRef() );
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
	
	public void validate( PdfRequest request, Block block ) throws PdfRequestNotValidException{
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
						/**
						if( cell.getFontRef() == null ){
							cell.setFontRef(paragraph.getFontRef());
						}
						if( cell.getColorRef() == null ){
							cell.setColorRef( paragraph.getColorRef() );
						}
						if( cell.getFontSize() == 0){
							cell.setFontSize( paragraph.getFontSize());
						}
						**/
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
