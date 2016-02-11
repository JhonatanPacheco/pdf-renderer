package se.billes.pdf.renderer.request.factory;

import com.itextpdf.text.Rectangle;

import se.billes.pdf.renderer.request.PdfRequest;

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
	public float[] getTrimBoxInPS(PdfRequest request){
		
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
	
	private float[] getTrimBoxInPSWithCutmarks(PdfRequest request){
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
	public Rectangle getSizeAsRectangle(PdfRequest request){
		float bothSidesOfCutmark = 0;
		if( request.getCutmarks() != null ){
			bothSidesOfCutmark = CUT_MARK * 2;
		}
		return new Rectangle( millimetersToPostscriptPoints(request.getSize()[0] + bothSidesOfCutmark ), millimetersToPostscriptPoints(request.getSize()[1] + bothSidesOfCutmark ) );
	}
	

	
	public Rectangle getTrimBoxAsRectangle(PdfRequest request){
		float[] trimBox = getTrimBoxInPS(request);
		return new Rectangle( trimBox[0],trimBox[1],trimBox[2],trimBox[3] );
	}
	
	
	
}
