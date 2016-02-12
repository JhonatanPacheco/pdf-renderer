package se.billes.pdf.renderer.request;

import se.billes.pdf.renderer.model.Color;
import se.billes.pdf.renderer.model.Cutmarks;
import se.billes.pdf.renderer.model.Font;
import se.billes.pdf.renderer.model.Hyphenation;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.response.Response;

import com.itextpdf.text.pdf.HyphenationAuto;

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
public class PdfRequest {
	
	private Integer[] size;
	private Cutmarks cutmarks;
	private String path;
	private String name;
	private Page[] pages;
	private Color[] colors;
	private Font[] fonts;
	private Hyphenation hyphenation;
	private HyphenationAuto hyphenationAuto;
	private long startExecutionTime;
	private Response response;

	public Integer[] getSize() {
		return size;
	}

	public void setSize(Integer[] size) {
		this.size = size;
	}
	
	public Cutmarks getCutmarks() {
		return cutmarks;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setCutmarks(Cutmarks cutmarks) {
		this.cutmarks = cutmarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Page[] getPages() {
		return pages;
	}

	public void setPages(Page[] pages) {
		this.pages = pages;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public Font[] getFonts() {
		return fonts;
	}

	public void setFonts(Font[] fonts) {
		this.fonts = fonts;
	}

	public Hyphenation getHyphenation() {
		return hyphenation;
	}

	public void setHyphenation(Hyphenation hyphenation) {
		this.hyphenation = hyphenation;
	}

	public HyphenationAuto getHyphenationAuto() {
		return hyphenationAuto;
	}

	public void setHyphenationAuto(HyphenationAuto hyphenationAuto) {
		this.hyphenationAuto = hyphenationAuto;
	}

	public long getStartExecutionTime() {
		return startExecutionTime;
	}

	public void setStartExecutionTime(long startExecutionTime) {
		this.startExecutionTime = startExecutionTime;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
}
