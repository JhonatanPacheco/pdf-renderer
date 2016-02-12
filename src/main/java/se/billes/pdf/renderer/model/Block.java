package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.text.AbstractParagraph;
import se.billes.pdf.renderer.process.RenderParagraphs;
import se.billes.pdf.renderer.request.factory.BlockFactory;
import se.billes.pdf.renderer.request.factory.CellFactory;
import se.billes.pdf.renderer.request.factory.TableFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

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
public class Block extends BaseBlock{
	
	
	private boolean useAscender;
	private float[] margins = new float[]{0,0,0,0}; // left,right,top,bottom
	private int rotation;
	private String backgroundRef;
	private BaseColor baseColor;
	private AbstractParagraph[] paragraphs;
	private String verticalAlign = "top"; // do not move to BaseBlock because image does not have a default value
	
	public HyphenationAuto getHyphenationAuto(){
		return getPage().getHyphenationAuto();
	}

	public String getVerticalAlign() {
		return verticalAlign;
	}
	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
	}
	

	public boolean isUseAscender() {
		return useAscender;
	}

	public void setUseAscender(boolean useAscender) {
		this.useAscender = useAscender;
	}

	public float[] getMargins() {
		return margins;
	}

	public void setMargins(float[] margins) {
		this.margins = margins;
	}


	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public String getBackgroundRef() {
		return backgroundRef;
	}

	public void setBackgroundRef(String backgroundRef) {
		this.backgroundRef = backgroundRef;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}


	public AbstractParagraph[] getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(AbstractParagraph[] paragraphs) {
		this.paragraphs = paragraphs;
	}

	public void onRender( PdfContentByte cb ) throws PdfRenderException{
		float[] positions = new BlockFactory().getBoundsInPs(this);
		new BlockFactory().createRoundRectangle(cb, this);
		ColumnText ct = new BlockFactory().createColumn(cb,this);
		PdfPTable table = new TableFactory().createTable(positions[2]);
		PdfPCell cell = new CellFactory().createCell(this);
		new RenderParagraphs().render(cell, this);
		new TableFactory().apply(ct, table, cell);	
	}
}
