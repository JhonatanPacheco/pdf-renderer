package se.billes.pdf.renderer.model;

public class Cutmarks {
	private boolean ignoreCutStroke = false;

	public boolean isIgnoreCutStroke() {
		return ignoreCutStroke;
	}

	public void setIgnoreCutStroke(boolean ignoreCutStroke) {
		this.ignoreCutStroke = ignoreCutStroke;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append( "ignoreCutStroke: " + ignoreCutStroke );
		
		return builder.toString();
	}
}
