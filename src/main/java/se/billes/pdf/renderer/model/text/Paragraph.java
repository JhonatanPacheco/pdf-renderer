package se.billes.pdf.renderer.model.text;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.Indent;
import se.billes.pdf.renderer.model.alignment.HorizontalAlign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * This program is built on top of iText.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License version 3 as published by the Free Software Foundation with 
 * the addition of the following permission added to Section 15 as permitted in Section 7(a): 
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY ITEXT GROUP NV, ITEXT GROUP 
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of 
 * the GNU Affero General Public License along with this program; if not, 
 * see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc., 51 Franklin Street, 
 * Fifth Floor, Boston, MA, 02110-1301 USA, or download the license from the following
 * URL: http://itextpdf.com/terms-of-use/ 
 * The interactive user interfaces in modified source and object code versions of this program must 
 * display Appropriate Legal Notices, as required under Section 5 of the GNU Affero General Public License.
 * In accordance with Section 7(b) of the GNU Affero General Public License, you must retain the producer line 
 * in every PDF that is created or manipulated using iText. You can be released from the requirements of the 
 * license by purchasing a commercial license. Buying such a license is mandatory as soon as you develop 
 * commercial activities involving the iText software without disclosing the source code of your own 
 * applications. These activities include: offering paid services to customers as an ASP, 
 * serving PDFs on the fly in a web application, shipping iText with a closed source product.
 */
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
