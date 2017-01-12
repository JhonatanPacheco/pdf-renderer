package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseBlock {

	public String type;
	public List<Double> position; // LINE 2 pos, rest 4 pos
	public Double radius;
	public Integer rotation;
	public String verticalAlign;
	public String backgroundRef;
	public List<Double> margins;
	public Boolean useAscender;
	public Border border;
	public Double thickness; // LINE
	public List<Double> moveTo; // LINE
	public String barColorRef; // BARCODE
	public String codeColorRef; // BARCODE
	public String align; // BARCODE
	public Double fontSize; // BARCODE
	public String barCodeType; // BARCODE
	public String fontRef; // BARCODE
	public String code; // BARCODE
	public Boolean showGuardBars; // BARCODE
	public String colorRef; // QR
	public String text; // QR
	public String path; // IMAGE
	public Boolean scaleToFit; // IMAGE
	public Boolean fillFrameProportionally; // IMAGE
	public Boolean fitContentProportionally; // IMAGE
	public Boolean centerImageToPageWidth; // IMAGE
	public String horizontalAlign; // IMAGE
	public Integer dotsPerInch; // IMAGE
	public List<FirebaseParagraph> paragraphs;
	
	class Border {
		public Double thickness;
		public String colorRef;
	}

}
