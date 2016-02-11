package se.billes.pdf.renderer.validator;

import com.itextpdf.text.BaseColor;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Line;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;

public class LineValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public LineValidator withPageIndex( int index ){
		this.pageIndex = index;
		return this;
	}
	
	public LineValidator withBlockIndex( int index ){
		this.blockIndex = index;
		return this;
	}
	
	public void validate(PdfRequest request, Line line) throws PdfRequestNotValidException {
		
		DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageIndex).withBlockIndex(blockIndex);
		
		
		if( line.getColorRef() == null ){
			line.setBaseColor( new ColorFactory().getBlack() );
		}
		
		if( line.getThickness() <= 0 ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString("No point of drawing empty line. No thickness for line") );
		}
		
		if( line.getPosition() == null || line.getPosition().length != 2 ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString("Line must have position:[x,y]" ));
		}
		
		if( line.getMoveTo() == null || line.getMoveTo().length != 2 ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString("Line must have moveTo:[x1,y1]" ));
		}
		if( line.getColorRef() != null ){
			BaseColor color = new ColorFactory().getBaseColorByRef(request, line.getColorRef());
			if( color == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find colorRef color for line"));
			}
			line.setBaseColor(color);
		}
	}

}
