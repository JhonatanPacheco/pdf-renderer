package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.exception.CreateColorException;
import se.billes.pdf.renderer.model.Color;
import se.billes.pdf.renderer.request.PdfRequest;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfSpotColor;
import com.itextpdf.text.pdf.SpotColor;

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
	
	public BaseColor getBaseColorByRef( PdfRequest request, String ref ){
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
