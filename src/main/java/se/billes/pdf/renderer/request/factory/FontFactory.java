package se.billes.pdf.renderer.request.factory;

import java.io.File;
import java.io.IOException;

import se.billes.pdf.renderer.exception.CreateFontException;
import se.billes.pdf.renderer.model.Font;
import se.billes.pdf.renderer.request.PdfRequest;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

/**
 * TODO: We need to add the path in to the pdf-renderer.
 * @author Mathias
 *
 */
public class FontFactory {
	
	public BaseFont createBaseFont(Font font) throws CreateFontException{
		
		if( font == null ){
			throw new CreateFontException( "Font is null" );
		}
		
		if( font.getPath() == null ){
			throw new CreateFontException( "Font path is null" );
		}
		
		if( ! new File( font.getPath()).exists() ){
			throw new CreateFontException( "Font path " + font.getPath() + " does not exists" );
		}
		
		if( font.getEncoding() == null){
			font.setEncoding("");
		}
		if( font.getEncoding() != null && font.getEncoding().equals( "utf-8")){
			font.setEncoding( BaseFont.IDENTITY_H );
		}
		
		
		try {
			return BaseFont.createFont( font.getPath(),font.getEncoding(), BaseFont.EMBEDDED  );
		} catch (DocumentException e) {
			throw new CreateFontException(e);
		} catch (IOException e) {
			throw new CreateFontException(e);
		}
	}
	
	public BaseFont getBaseFontByRef( PdfRequest request, String ref ){
		if( ref == null ){
			return null;
		}
		for( Font font : request.getFonts()){
			if( font.getRef().equals(ref)){
				return font.getBaseFont();
			}
		}
		
		return null;
	}
}
