package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.request.PdfDocument;

import com.itextpdf.text.Rectangle;

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
public class SizeFactory {
	
	private static final float POSTSCRIPT_POINT_PER_MILLIMETER = 2.83464567f;
	private static final float MILLIMETER_PER_POSTSCRIPT_POINT = 0.352777778f;
	public static final float CUT_MARK = 10f;
	public static final float CUT_MARK_LINE = 7f;
	
	public static float millimetersToPostscriptPoints( float millimeter ){
		return millimeter * POSTSCRIPT_POINT_PER_MILLIMETER;
	}
	
	public  static float PostscriptPointsToMillimeters( float postscriptPoint ){
		return postscriptPoint * MILLIMETER_PER_POSTSCRIPT_POINT;
	}
	
	/**
	 * @param request
	 * @return the trim box in post script points as array of four elements
	 */
	public float[] getTrimBoxInPS(PdfDocument request){
		
		if( request.getCutmarks() != null ){
			return getTrimBoxInPSWithCutmarks(request);
		}
		
		float[] returnValue = {
								0f,
								0f,
								millimetersToPostscriptPoints(request.getSize()[0]),
								millimetersToPostscriptPoints(request.getSize()[1])
							};
		return returnValue;
	}
	
	private float[] getTrimBoxInPSWithCutmarks(PdfDocument request){
		float[] returnValue = {
					millimetersToPostscriptPoints(CUT_MARK),
					millimetersToPostscriptPoints(CUT_MARK),
					millimetersToPostscriptPoints(request.getSize()[0] + CUT_MARK),
					millimetersToPostscriptPoints(request.getSize()[1] + CUT_MARK)
				};
		return returnValue;
	}
	
	/**
	 * 
	 * @param request
	 * @return {@link Rectangle} with cutmark if that is used transformed to postscript points
	 */
	public Rectangle getSizeAsRectangle(PdfDocument request){
		float bothSidesOfCutmark = 0;
		if( request.getCutmarks() != null ){
			bothSidesOfCutmark = CUT_MARK * 2;
		}
		return new Rectangle( millimetersToPostscriptPoints(request.getSize()[0] + bothSidesOfCutmark ), millimetersToPostscriptPoints(request.getSize()[1] + bothSidesOfCutmark ) );
	}
	

	
	public Rectangle getTrimBoxAsRectangle(PdfDocument request){
		float[] trimBox = getTrimBoxInPS(request);
		return new Rectangle( trimBox[0],trimBox[1],trimBox[2],trimBox[3] );
	}
	
	
	
}
