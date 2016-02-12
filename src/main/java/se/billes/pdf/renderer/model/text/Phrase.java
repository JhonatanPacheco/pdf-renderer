package se.billes.pdf.renderer.model.text;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;

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
