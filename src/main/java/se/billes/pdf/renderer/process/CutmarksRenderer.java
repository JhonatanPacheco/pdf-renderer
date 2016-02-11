package se.billes.pdf.renderer.process;

import se.billes.pdf.renderer.model.Cutmarks;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class CutmarksRenderer {
	
	private PdfRequest request;
	public CutmarksRenderer(PdfRequest request){
		this.request = request;
	}
	
	public void render(PdfWriter writer, Document document){
		
		Cutmarks cutmarks = request.getCutmarks();
		if(  cutmarks != null && ! cutmarks.isIgnoreCutStroke()  ){
			
			float width = SizeFactory.CUT_MARK;
			float height = SizeFactory.CUT_MARK;
			float lineWidth = SizeFactory.CUT_MARK_LINE;
			float lineHeight = SizeFactory.CUT_MARK_LINE;
			Rectangle rect = document.getPageSize();
			PdfContentByte cb = writer.getDirectContent();
			cb.setLineWidth( 0.1f );


			cb.moveTo( SizeFactory.millimetersToPostscriptPoints(width), 0);
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(width),SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			
			cb.moveTo( 0, SizeFactory.millimetersToPostscriptPoints(height));
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(lineWidth),SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
			
			// Upper left cut mark
			cb.moveTo( SizeFactory.millimetersToPostscriptPoints(width), rect.getHeight() );
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(width), rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			cb.moveTo( 0, rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(lineWidth),rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
			
			/**
			 * Upper right cut mark
			 */
			cb.moveTo( rect.getWidth(), rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(lineWidth),rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
			cb.moveTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width), rect.getHeight() );
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width),rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			/**
			 * Lower right cut mark
			 */
			cb.moveTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width), 0 );
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width),SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			cb.moveTo( rect.getWidth() , SizeFactory.millimetersToPostscriptPoints(height) );
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(lineWidth),SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
		}
	}
}	
