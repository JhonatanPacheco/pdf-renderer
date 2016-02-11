package se.billes.pdf.renderer.model;

import java.util.Hashtable;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.BlockFactory;
import se.billes.pdf.renderer.request.factory.ColorFactory;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;

public class QRCode extends BaseElement{
	
	private String text;
	private String colorRef;
	private BaseColor baseColor;
	
	public String getColorRef() {
		return colorRef;
	}

	public void setColorRef(String colorRef) {
		this.colorRef = colorRef;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@SuppressWarnings("unchecked")
	public void onRender( PdfContentByte cb ) throws PdfRenderException{
		
		float[] positions = new BlockFactory().getBoundsInPs(this);
		
		try{
			@SuppressWarnings("rawtypes")
			Hashtable hintMap = new Hashtable();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			PdfRequest req = getPage().getPdfRequest();
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(getText(), BarcodeFormat.QR_CODE, (int) (positions[2]),(int) (positions[3]), hintMap);
			int matrixWidth = byteMatrix.getWidth();
			int matrixHeight = byteMatrix.getHeight();
			float pageHeight = req.getSize()[1];
			float top = getPosition()[1];

			if (getPage().getPdfRequest().getCutmarks() != null) {
				pageHeight += SizeFactory.CUT_MARK * 2;
				top += SizeFactory.CUT_MARK;
			}

			cb.setColorFill(getBaseColor());
			float pageHeightInPs = SizeFactory.millimetersToPostscriptPoints(pageHeight);
			float topInPs = SizeFactory.millimetersToPostscriptPoints(top);
			for (int i = byteMatrix.getTopLeftOnBit()[0]; i < matrixWidth; i++) {
				for (int j = byteMatrix.getTopLeftOnBit()[0]; j < matrixHeight; j++) {
					if (byteMatrix.get(i, j)) {
						
						cb.rectangle(positions[0] + (i - byteMatrix.getTopLeftOnBit()[0]),pageHeightInPs - ((topInPs + 1) + (j - byteMatrix .getTopLeftOnBit()[0])), 1, 1);
					}
				}
			}

			cb.fill();
			cb.setColorFill(new ColorFactory().getBlack()); 
  
		}catch( Exception e ){
			
		}
	}
	
}
