package se.billes.pdf.renderer.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.request.PdfDocument;
import se.billes.pdf.renderer.request.factory.ImageFactory;
import se.billes.pdf.renderer.request.factory.SizeFactory;
import se.billes.pdf.renderer.response.PdfResponse;
import se.billes.pdf.renderer.response.SucceededOutput;
import se.billes.pdf.request.incoming.InputRequest;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
public abstract class Renderer{

	private InputRequest pdfRequest;
	
	public Renderer( InputRequest pdfRequest ){
		this.pdfRequest = pdfRequest;
	}
	
	public void onRender() throws PdfRenderException{
		
		long startTime = new Date().getTime();
		System.err.println( startTime + ": Renderer called");
		PdfDocument pdfDocument = pdfRequest.getDocument();
		File destinationPdf = new File(pdfRequest.getPath(),pdfDocument.getName());
		Document document = new Document();
		boolean finishedRender = false;
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance( document, new FileOutputStream( destinationPdf ));
			document.addAuthor( "iText" );
			writer.setBoxSize( "trim" , new SizeFactory().getTrimBoxAsRectangle(pdfDocument) );
			document.setPageSize( new SizeFactory().getSizeAsRectangle(pdfDocument) );
	
			document.open();
			for( Page page : pdfDocument.getPages() ){
				page.onNewPage(writer,document);
			}
			System.err.println( new Date().getTime() + ": Renderer finished");
			finishedRender = true;
			
			
		} catch (FileNotFoundException e) {
			generatePdfRenderException(e);
		} catch (DocumentException e) {
			generatePdfRenderException(e);
		}catch( Exception e ){
			generatePdfRenderException(e);
		}finally{
			try{
				writer.close();
			}catch( Exception e ){}
			try{
				document.close();
			}catch( Exception e ){}
			
			ImageFactory.clear();
			
			if( finishedRender ){
				long endTime = new Date().getTime();
				System.err.println( endTime + ": document closed");
				PdfResponse response = new PdfResponse();
				response.setType("success");
				SucceededOutput output = new SucceededOutput();
				output.setFile(destinationPdf.getAbsolutePath());
				response.setExecutionOfPdfRendering(endTime - startTime);
				response.setTotalTimeOfExecution((endTime - pdfRequest.getStartExecutionTime()));
				response.setOutput(output);
				onRendered( response );
			}
		}

	}
	
	public void generatePdfRenderException(Exception e) throws PdfRenderException{
		throw new PdfRenderException( e );
	}

	
	/**
	 * Called when and if the renderer process is done
	 */
	public abstract void onRendered( PdfResponse pdfResponse );
	
}
