package se.billes.pdf.renderer.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class Renderer{

	private PdfRequest pdfRequest;
	
	public Renderer( PdfRequest pdfRequest ){
		this.pdfRequest = pdfRequest;
	}
	
	public void onRender() throws PdfRenderException{
		long startTime = new Date().getTime();
		File destinationPdf = new File(pdfRequest.getPath(),pdfRequest.getName());
		Document document = new Document();
		boolean finishedRender = false;
		try {
			PdfWriter writer = PdfWriter.getInstance( document, new FileOutputStream( destinationPdf ));
			document.addAuthor( "Billes tryckeri AB" );
			writer.setBoxSize( "trim" , new SizeFactory().getTrimBoxAsRectangle(pdfRequest) );
			document.setPageSize( new SizeFactory().getSizeAsRectangle(pdfRequest) );
	
			document.open();
			for( Page page : pdfRequest.getPages() ){
				page.onNewPage(writer,document);
			}
			
			finishedRender = true;
			
			
		} catch (FileNotFoundException e) {
			throw new PdfRenderException( e );
		} catch (DocumentException e) {
			throw new PdfRenderException( e );
		}catch( Exception e ){
			throw new PdfRenderException( e );
		}finally{
			try{
				document.close();
			}catch( Exception e ){	
			}
			if( finishedRender ){
				long endTime = new Date().getTime();
				onRendered( new FileRendered()
							.withFile(destinationPdf)
							.withtimeOfPdfRendering(endTime - startTime)
							.withTotalTimeOfExecution(endTime - pdfRequest.getStartExecutionTime())
						  );
			}
		}

	}

	
	/**
	 * Called when and if the renderer process is done
	 */
	public abstract void onRendered( FileRendered fileRendered );
	
}
