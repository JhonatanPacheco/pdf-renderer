package se.billes.pdf.renderer.request.factory;

import java.io.File;
import java.io.IOException;

import se.billes.pdf.renderer.exception.CreateFontException;
import se.billes.pdf.renderer.model.Font;
import se.billes.pdf.renderer.request.PdfRequest;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

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
