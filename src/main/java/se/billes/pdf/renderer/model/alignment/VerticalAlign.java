package se.billes.pdf.renderer.model.alignment;

import com.itextpdf.text.Element;

public enum VerticalAlign{
	TOP( "top" , Element.ALIGN_TOP),
	MIDDLE( "middle" , Element.ALIGN_MIDDLE ),
	BOTTOM( "bottom" , Element.ALIGN_BOTTOM );
	
	private String name;
	private int alignment;
	
	VerticalAlign( String name, int alignment ){
		this.name = name;
		this.alignment = alignment;
	}

	public String getName() {
		return name;
	}

	public int getAlignment() {
		return alignment;
	}
	
	public static VerticalAlign getByName( String name ){
		for(VerticalAlign align : values()){
			if( align.getName().equals( name.toLowerCase() )) return align;
		}
		
		return null;
	}
}