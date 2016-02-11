package se.billes.pdf.renderer.model.text;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.Indent;
import se.billes.pdf.renderer.model.alignment.HorizontalAlign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfPCell;

public class Paragraph extends AbstractParagraph{
	
	private Indent indent = new Indent();
	private String horizontalAlign = "left"; // left,center,right
	private float fontSize;
	private String fontRef;
	private float leading;
	private String colorRef;
	private BaseFont baseFont;
	private BaseColor baseColor;
	private boolean useHyphenation = true;
	private AbstractPhrase[] phrases;
	
	public Indent getIndent() {
		return indent;
	}

	public void setIndent(Indent indent) {
		this.indent = indent;
	}

	public HyphenationAuto getHyphenationAuto(){
		if( ! useHyphenation) return null;
		return getBlock().getHyphenationAuto();
	}
	
	public String getHorizontalAlign() {
		return horizontalAlign;
	}

	public void setHorizontalAlign(String horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontRef() {
		return fontRef;
	}

	public void setFontRef(String fontRef) {
		this.fontRef = fontRef;
	}

	public float getLeading() {
		return leading;
	}

	public void setLeading(float leading) {
		this.leading = leading;
	}

	public String getColorRef() {
		return colorRef;
	}

	public void setColorRef(String colorRef) {
		this.colorRef = colorRef;
	}

	public BaseFont getBaseFont() {
		return baseFont;
	}

	public void setBaseFont(BaseFont baseFont) {
		this.baseFont = baseFont;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}


	public boolean isUseHyphenation() {
		return useHyphenation;
	}

	public void setUseHyphenation(boolean useHyphenation) {
		this.useHyphenation = useHyphenation;
	}

	public AbstractPhrase[] getPhrases() {
		return phrases;
	}

	public void setPhrases(AbstractPhrase[] phrases) {
		this.phrases = phrases;
	}

	public void onRender(PdfPCell cell ) throws PdfRenderException{
		com.itextpdf.text.Paragraph pr = new com.itextpdf.text.Paragraph();
		pr.setLeading( leading );
		pr.setExtraParagraphSpace(0);
		pr.setAlignment( HorizontalAlign.getByName(horizontalAlign).getAlignment() );
		pr.setIndentationLeft( indent.getLeft() );
		pr.setFirstLineIndent( indent.getFirst() );
		pr.setIndentationRight( indent.getRight() );
		pr.setSpacingBefore(0);
		pr.setSpacingAfter(0);
		if( phrases != null ){
			for( AbstractPhrase phrase : phrases ){
				phrase.onRender( pr );
			}
		}
		cell.addElement( pr );
	}
}
