package se.billes.pdf.renderer.process;

import java.io.IOException;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.model.Template;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

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
public class TemplatePageRenderer {
	
	private Page page;
	
	public TemplatePageRenderer(Page page){
		this.page = page;
	}
	

	public void render( PdfWriter writer,Document document ) throws PdfRenderException{
		writer.setPageEvent( page );
		Template template = page.getTemplate();
		
		try {
			PdfReader reader = new PdfReader( template.getTemplatePath() );
			PdfImportedPage pageImportedPage = writer.getImportedPage(reader, template.getPage());
			Image pdfMirror;
			
			float width = SizeFactory.CUT_MARK;
			float height = SizeFactory.CUT_MARK;
			
			pdfMirror = Image.getInstance(pageImportedPage);
			pdfMirror.setAbsolutePosition( SizeFactory.millimetersToPostscriptPoints( width ),SizeFactory.millimetersToPostscriptPoints( height ));
			document.newPage();
			
			PdfContentByte cb = writer.getDirectContent();
			try {
				cb.addImage( pdfMirror );
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			if( page.getBlocks() != null ){
				for( BaseElement block : page.getBlocks() ){
					block.onRender( cb );
				}
			}
			
		} catch (BadElementException e) {
			e.printStackTrace();
			throw new PdfRenderException( e );
		} catch (IOException e) {
			throw new PdfRenderException( e );
		}
		
		
	}
}
