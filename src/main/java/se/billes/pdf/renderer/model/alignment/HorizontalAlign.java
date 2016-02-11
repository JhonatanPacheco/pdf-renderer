package se.billes.pdf.renderer.model.alignment;


import com.itextpdf.text.Element;

public enum HorizontalAlign{
	
	
	LEFT( "left" , Element.ALIGN_LEFT),
	CENTER( "center" , Element.ALIGN_CENTER ),
	RIGHT( "right" , Element.ALIGN_RIGHT);
	
	private String name;
	private int alignment;
	

	HorizontalAlign( String name, int alignment ){
		this.name = name;
		this.alignment = alignment;
	}

	public String getName() {
		return name;
	}

	public int getAlignment() {
		return alignment;
	}
	
	public static HorizontalAlign getByName( String name ){
		for(HorizontalAlign align : values()){
			if( align.getName().equals( name )) return align;
		}
		
		return null;
	}
}