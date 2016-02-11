package se.billes.pdf.renderer.model.text;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

public class DottedFillPhrase extends AbstractPhrase{
	
	private float gap = 5f;
	
	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}

	@Override
	public void onRender(com.itextpdf.text.Paragraph paragraph) {
		DottedLineSeparator sep = new DottedLineSeparator();
		sep.setAlignment( Element.ALIGN_LEFT );
		sep.setGap( gap );
		sep.setLineColor( getBaseColor());
		com.itextpdf.text.Font f = new com.itextpdf.text.Font( getBaseFont(), getFontSize()  );
		f.setColor( getBaseColor() );
		Chunk separator = new Chunk( sep );
		separator.setFont(  f );
		paragraph.add( separator );
	}
}
