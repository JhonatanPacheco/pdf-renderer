package se.billes.pdf.renderer.process;

import java.io.File;

public class FileRendered {
	private File file;
	private long totalTimeOfExecution;
	private long timeOfPdfRendering;
	
	public FileRendered withFile( File file ){
		this.file = file;
		return this;
	}
	
	public FileRendered withTotalTimeOfExecution( long time ){
		this.totalTimeOfExecution = time;
		return this;
	}
	
	public FileRendered withtimeOfPdfRendering( long time ){
		this.timeOfPdfRendering = time;
		return this;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public long getTotalTimeOfExecution() {
		return totalTimeOfExecution;
	}
	public void setTotalTimeOfExecution(long totalTimeOfExecution) {
		this.totalTimeOfExecution = totalTimeOfExecution;
	}
	public long getTimeOfPdfRendering() {
		return timeOfPdfRendering;
	}
	public void setTimeOfPdfRendering(long timeOfPdfRendering) {
		this.timeOfPdfRendering = timeOfPdfRendering;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append( "[Total time of execution: " + totalTimeOfExecution + " milleseconds]\n");
		builder.append( "[Time of pdf execution: " + timeOfPdfRendering + "] milleseconds\n");
		builder.append( "[File rendered: " + file.getAbsolutePath() + "]\n");
		
		return builder.toString();
	}

}
