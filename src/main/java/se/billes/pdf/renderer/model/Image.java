package se.billes.pdf.renderer.model;

import java.io.File;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.alignment.HorizontalAlign;
import se.billes.pdf.renderer.model.alignment.VerticalAlign;
import se.billes.pdf.renderer.request.factory.BlockFactory;
import se.billes.pdf.renderer.request.factory.ImageFactory;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;

public class Image extends BaseBlock {
	
	private String path;
	private File file;
	private boolean fillFrameProportionally;
	private boolean fitContentProportionally;
	private boolean scaleToFit;
	private String horizontalAlign;
	private String verticalAlign;
	private int dotsPerInch;
	private boolean centerImageToPageWidth; // pdf
	
	public Integer getDotsPerInch() {
		return dotsPerInch;
	}

	public void setDotsPerInch(Integer dotsPerInch) {
		this.dotsPerInch = dotsPerInch;
	}

	public boolean isFillFrameProportionally() {
		return fillFrameProportionally;
	}

	public void setFillFrameProportionally(boolean fillFrameProportionally) {
		this.fillFrameProportionally = fillFrameProportionally;
	}

	public boolean isFitContentProportionally() {
		return fitContentProportionally;
	}

	public void setFitContentProportionally(boolean fitContentProportionally) {
		this.fitContentProportionally = fitContentProportionally;
	}

	public boolean isScaleToFit() {
		return scaleToFit;
	}

	public void setScaleToFit(boolean scaleToFit) {
		this.scaleToFit = scaleToFit;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String getHorizontalAlign() {
		return horizontalAlign;
	}

	public void setHorizontalAlign(String horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	public String getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public boolean isCenterImageToPageWidth() {
		return centerImageToPageWidth;
	}

	public void setCenterImageToPageWidth(boolean centerImageToPageWidth) {
		this.centerImageToPageWidth = centerImageToPageWidth;
	}

	@Override
	public void onRender(PdfContentByte cb) throws PdfRenderException {
		
		com.itextpdf.text.Image image = null;
		try {
			image = new ImageFactory().getImageByFile(cb, file);
		} catch (Exception e) {
			throw new PdfRenderException(e);
		}

		
		float pageHeight = getPage().getPdfRequest().getSize()[1];
		float pageWidth = getPage().getPdfRequest().getSize()[0];
		
		float x = 0;
		float y = 0;
		float fitHeight = 0f;
		float imageHeight = 0;
		float imageWidth = 0;
		boolean alignImage = false;
		
		float[] positions = new BlockFactory().getBoundsInPs(this);
		
		float width =  positions[2];
		float height =  positions[3];
		
		int dpiX = image.getDpiX();
		if( dpiX == 0 ){
			dpiX = 300;
			
		}
		if( ! new ImageFactory().isPdf(file) ){
			dpiX = getDotsPerInch();
			alignImage = true; // always align jpg
		}
		
		float realPercent = 72f / dpiX * 100; // only jpg
		if( isScaleToFit() ){
			image.scaleToFit( width, height );
			imageHeight = image.getScaledHeight();
			imageWidth = image.getScaledWidth();
		}else if( isFitContentProportionally() ){
			image.scaleToFit( width, height );
			imageHeight = image.getScaledHeight();
			imageWidth = image.getScaledWidth();
			fitHeight =  height - imageHeight;
			alignImage = true;
		}else{
			if( isFillFrameProportionally() ){
				float percentWidth = width / image.getWidth();
				float percentHeight = height / image.getHeight();
				realPercent = Math.max( percentHeight , percentWidth ) * 100;
				alignImage = true;
				if( new ImageFactory().isPdf(file) ){
					image.scalePercent( realPercent );
					imageHeight = image.getScaledHeight();
					imageWidth = image.getScaledWidth();
					fitHeight =  height - imageHeight;
				}
			}
			
			if( isCenterImageToPageWidth() && new ImageFactory().isPdf(file) ){
				
				
				imageWidth = image.getScaledWidth();
				float middle = ( pageWidth / 2 ) - (imageWidth / 2);
				positions[0] = SizeFactory.PostscriptPointsToMillimeters( middle );
			}
			
			
			if( ! new ImageFactory().isPdf(file) ){
				image.scalePercent( realPercent );
				imageHeight = image.getScaledHeight();
				imageWidth = image.getScaledWidth();
				fitHeight =  height - imageHeight;
			}
		}
		
		if( alignImage ){
			float[] result = handleAlignment(width, imageWidth, height, imageHeight, fitHeight);
			x = result[0];
			y = result[1];
		}
		try{
			PdfTemplate tp = cb.createTemplate(width, height);
			image.setAbsolutePosition( x , y );
			tp.roundRectangle(0, 0, width, height, SizeFactory.millimetersToPostscriptPoints( getRadius() ));
			tp.clip();
			tp.newPath();
			tp.addImage(image);
			
			float left = getPosition()[0];
			float top = getPosition()[1];
			if( getPage().getPdfRequest().getCutmarks() != null ){
				left += SizeFactory.CUT_MARK;
				top -= SizeFactory.CUT_MARK;
			}

			cb.addTemplate(tp, SizeFactory.millimetersToPostscriptPoints( left ), SizeFactory.millimetersToPostscriptPoints( pageHeight - ( top + getPosition()[3])));
			if( getBorder() != null ){
				cb.setLineWidth( SizeFactory.millimetersToPostscriptPoints( getBorder().getThickness()) );
				cb.setColorStroke( getBorder().getBaseColor() );
				cb.roundRectangle(	SizeFactory.millimetersToPostscriptPoints( left ), 
									SizeFactory.millimetersToPostscriptPoints( pageHeight - ( top + getPosition()[3])), 
									width, 
									height, 
									SizeFactory.millimetersToPostscriptPoints( getRadius() )); 
				cb.stroke(); 
			}
			

			
			
		}catch( Exception e ){
			throw new PdfRenderException(e);
		}
		
		
	}
	
	public float[] handleAlignment( float width, float imageWidth, float height, float imageHeight, float fitHeight){
			float bottom = 0;
			float top = height - imageHeight;
			float middle = top / 2;
			
			float left = 0;
			float right = width - imageWidth;
			float center = right  / 2;
			float x = top;
			float y = left;

			if( getVerticalAlign() != null ){
				VerticalAlign align = VerticalAlign.getByName(getVerticalAlign());
				switch( align ){
					case TOP:
						y = 0; //top;
						break;
					case BOTTOM:
						y = bottom;
						fitHeight = 0f;
						break;
					case MIDDLE:
						y = middle;
						fitHeight = 0f;
						break;
				}
			}
			
			if( getHorizontalAlign() != null ){
				HorizontalAlign align = HorizontalAlign.getByName(getHorizontalAlign());
				switch( align ){
					case LEFT:
						x = 0;
						break;
					case CENTER:
						x = center;
						break;
					case RIGHT:
						x = right;
						break;
				}	
			}
			float[] result = new float[]{x,y + fitHeight};
			return result;
		
	}
}