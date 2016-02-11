package se.billes.pdf.renderer.validator;

public class DocumentErrorFactory {
	
	private Integer pageIndex;
	private Integer blockIndex;
	private Integer paragraphIndex;
	private Integer phraseIndex;
	private Integer cellIndex;
	
	public DocumentErrorFactory withPageIndex(int index){
		this.pageIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withBlockIndex(int index){
		this.blockIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withParagraphIndex(int index){
		this.paragraphIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withPhraseIndex(int index){
		this.phraseIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withCellIndex(Integer index){
		this.cellIndex = index;
		return this;
	}
	
	public String appendErrorString(  String error ){
		StringBuilder builder = new StringBuilder();
		builder.append( error );
		if( pageIndex != null ){
			builder.append( " at(pageIndex: " + pageIndex );
		}
		if( blockIndex != null ){
			builder.append( ", blockIndex: " + blockIndex );
		}
		
		if( paragraphIndex != null ){
			builder.append( ", paragraphIndex: " + paragraphIndex );
		}
		
		if( cellIndex != null ){
			builder.append( ", cellIndex: " + cellIndex );
		}
		
		
		if( phraseIndex != null ){
			builder.append( ", phraseIndex: " + phraseIndex );
		}
		builder.append(")");
		
		return builder.toString();
	}
}
