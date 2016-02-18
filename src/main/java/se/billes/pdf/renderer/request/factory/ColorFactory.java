package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.exception.CreateColorException;
import se.billes.pdf.renderer.model.Color;
import se.billes.pdf.renderer.request.PdfDocument;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfSpotColor;
import com.itextpdf.text.pdf.SpotColor;

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
public class ColorFactory {
	
	public BaseColor createBaseColor( Color color  ) throws CreateColorException{
		if( color.getColor().length < 4 && color.getColor().length > 5 ){
			throw new CreateColorException( "Incorrect length of color. Should be [cyan,magenta,yellow,black] or [cyan,magenta,yellow,black,tint]");
		}
		
		if( color.getRef() == null ){
			throw new CreateColorException( "You must specify a reference" );
		}
		
		for( float c : color.getColor() ){
			if( c > 1 || c < 0 ){
				throw new CreateColorException( "[cyan,magenta,yellow,black] or [cyan,magenta,yellow,black,tint] must be between 0 and 1" );
			}
		}
		float[] colors = color.getColor();
		CMYKColor baseColor =  new CMYKColor( colors[0],colors[1],colors[2],colors[3] );
		if( color.getColor().length == 4 ){
			return baseColor;
		}

		return new SpotColor(new PdfSpotColor(color.getRef(), baseColor),colors[4]);	
	}
	
	public BaseColor getBaseColorByRef( PdfDocument request, String ref ){
		if( ref == null ){
			return null;
		}
		for( Color color : request.getColors()){
			if( color.getRef().equals(ref)){
				return color.getBaseColor();
			}
		}
		
		return null;
	}
	
	public CMYKColor getBlack(){
		return new CMYKColor(0.0f, 0.0f, 0.0f, 1.0f );
	}
}
