package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.request.PdfRequest;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;

public class BlockFactory {
	
	private PdfRequest getRequest(BaseElement block){
		return block.getPage().getPdfRequest();
	}
	
	public float[] getBoundsInMMIncludeCutmark( BaseElement block ){
		PdfRequest request =  getRequest(block);
		float[] position = block.getPosition();
		float leftInMM = position[0];
		float topInMM = position[1];
		if(  request.getCutmarks() != null ){
			leftInMM += SizeFactory.CUT_MARK;
			topInMM -= SizeFactory.CUT_MARK;
		}
		
		float[] dest = new float[position.length];
		
		System.arraycopy(position, 0, dest, 0, position.length);
		dest[0] = leftInMM;
		dest[1] = topInMM;
		
		
		return dest;
	}
		
	public float[] getBoundsInPs( BaseElement block ){
		float[] position = getBoundsInMMIncludeCutmark(block);
		float[] dest = new float[position.length];
		System.arraycopy(position, 0, dest, 0, position.length);
		for(int i = 0; i < position.length; i++){
			dest[i] = SizeFactory.millimetersToPostscriptPoints(dest[i]);
		}
	
		return dest;
	}
	
	public void createRoundRectangle(PdfContentByte cb, Block block ){
		PdfRequest request =  getRequest(block);
		float[] positions = getBoundsInPs(block);
		float[] mmPositions = getBoundsInMMIncludeCutmark(block);
		PdfTemplate tp = cb.createTemplate(positions[2],positions[3]);
	
		tp.roundRectangle(
				0, 
				0, 
				positions[2], 
				positions[3], 
				SizeFactory.millimetersToPostscriptPoints( block.getRadius() )
		);
		
		
		
		
		// because pdf i calculated from bottom up
		cb.addTemplate(
				tp, 
				positions[0], 
				SizeFactory.millimetersToPostscriptPoints( request.getSize()[1] - ( mmPositions[1] + mmPositions[3]))
		);

	}
	
	public ColumnText createColumn(PdfContentByte cb, Block block){
		
		ColumnText ct = new ColumnText( cb );
		PdfRequest request =  getRequest(block);
		float[] mmPositions = getBoundsInMMIncludeCutmark(block);
		ct.setSimpleColumn( 
				SizeFactory.millimetersToPostscriptPoints( mmPositions[0] ), 
				SizeFactory.millimetersToPostscriptPoints( request.getSize()[1] - ( mmPositions[1] + mmPositions[3]) ), 
				SizeFactory.millimetersToPostscriptPoints(  mmPositions[0] +  mmPositions[2] ),
				SizeFactory.millimetersToPostscriptPoints( request.getSize()[1] -  mmPositions[1] ), 0, Element.ALIGN_LEFT );
		
		return ct;
	}
	
}
