package se.billes.pdf.renderer.validator;

import com.itextpdf.text.BaseColor;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.alignment.VerticalAlign;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;

public class BlockValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public BlockValidator withPageIndex( int index ){
		this.pageIndex = index;
		return this;
	}
	
	public BlockValidator withBlockIndex( int index ){
		this.blockIndex = index;
		return this;
	}
	
	public void validate(PdfRequest request,Block block) throws PdfRequestNotValidException {
		
	
		DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageIndex).withBlockIndex(blockIndex);
		
		if( block.getBackgroundRef()  != null ){
			BaseColor color = new ColorFactory().getBaseColorByRef(request, block.getBackgroundRef());
			if( color == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find backgroundRef color for block" ) );
			}
			block.setBaseColor(color);
		}
		
		if( block.getBorder() != null ){
			if( block.getBorder().getThickness() > 0 ){
				if( block.getBorder().getColorRef() == null ){
					block.getBorder().setBaseColor( new ColorFactory().getBlack());
				}else{
					BaseColor color = new ColorFactory().getBaseColorByRef(request, block.getBorder().getColorRef());
					if( color == null ){
						throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find colorRef for border" ) );
					}
					block.getBorder().setBaseColor(color);
				}
			}
		}
		
		if( block.getMargins() != null ){
			if( block.getMargins().length != 4 ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Incorrect margins. Must be [float,float,float,float]" ));
			}
		}
		
		if( block.getVerticalAlign() == null ){
			throw new PdfRequestNotValidException(  errorFactory.appendErrorString("Incorrect vertical align. Must be any of[top,middle,bottom]" ));
		}else{
			VerticalAlign align = VerticalAlign.getByName(block.getVerticalAlign() );
			if( align == null ){
				throw new PdfRequestNotValidException(  errorFactory.appendErrorString("Incorrect vertical align. Must be any of[top,middle,bottom]"));
			}
		}

		new ParagraphValidator()
		.withBlockIndex(blockIndex)
		.withPageIndex(pageIndex)
		.validate(request, block);
	}
}
