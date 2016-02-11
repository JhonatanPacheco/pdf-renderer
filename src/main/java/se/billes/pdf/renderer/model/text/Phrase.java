package se.billes.pdf.renderer.model.text;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;

public class Phrase extends AbstractPhrase{
	
	private String text = "";
	private String textTransform;
	private float textRise = .0f;
	private float skewAlpha = .0f;
	private float skewBeta = .0f;
	private String style; // normal, bold, italic, bolditalic
	

	public String getStyle() {
		if( style == null ) return null;
		return style.toLowerCase();
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public Float getSkewAlpha() {
		return skewAlpha;
	}
	
	public void setSkewAlpha(Float skewAlpha) {
		this.skewAlpha = skewAlpha;
	}
	
	public Float getSkewBeta() {
		return skewBeta;
	}
	
	public void setSkewBeta(Float skewBeta) {
		this.skewBeta = skewBeta;
	}
	
	public Float getTextRise() {
		return textRise;
	}
	
	public void setTextRise(Float textRise) {
		this.textRise = textRise;
	}
	
	public String getText() {
		if( textTransform == null ){
			return text;
		}
		
		if( textTransform.equals( "uppercase") ){
			return text.toUpperCase();
		}
		
		if( textTransform.equals( "lowercase") ){
			return text.toLowerCase();
		}
		
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	


	public void setTextRise(float textRise) {
		this.textRise = textRise;
	}

	public void setSkewAlpha(float skewAlpha) {
		this.skewAlpha = skewAlpha;
	}

	public void setSkewBeta(float skewBeta) {
		this.skewBeta = skewBeta;
	}

	

	public String getTextTransform() {
		return textTransform;
	}

	public void setTextTransform(String textTransform) {
		this.textTransform = textTransform;
	}

	public void onRender( com.itextpdf.text.Paragraph paragraph ){		
		Font font = new Font( getBaseFont(), getFontSize() );
		font.setColor( getBaseColor()  );
		if( getStyle() != null )
		font.setStyle( getStyle() );
		String text = getText();
		if( text.length() == 0 ) text = " ";
		text = text.replace( "&gt;", ">" );
        text = text.replace( "&lt;", "<" );

		Chunk chunk = new Chunk(  text, font );
		if( getParagraph().getHyphenationAuto() != null ){
			chunk.setHyphenation( getParagraph().getHyphenationAuto() );	
		}
		chunk.setTextRise( getTextRise() );
		chunk.setSkew( getSkewAlpha(), getSkewBeta() );
		paragraph.add( chunk );
	}
}
