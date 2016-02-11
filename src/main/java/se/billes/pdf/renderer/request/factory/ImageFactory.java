package se.billes.pdf.renderer.request.factory;

import java.io.File;
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class ImageFactory {
	
	public Image getImageByFile( PdfContentByte cb , File file ) throws IOException, BadElementException{
		Image image = null;
		if( file.getName().toLowerCase().endsWith( ".pdf")){	
			PdfReader reader = new PdfReader( file.getAbsolutePath() );
			PdfImportedPage p = cb.getPdfWriter().getImportedPage(reader, 1);
			image = Image.getInstance(p); 
		}else{
			image = Image.getInstance( file.getAbsolutePath() );
		}
		
		return image;
	}
	
	public boolean isPdf( File file ){
		if( file.getName().toLowerCase().endsWith( ".pdf")){	
			return true;
		}
		
		return false;
	}
	
}
