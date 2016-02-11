package se.billes.pdf.renderer.validator;

import java.io.File;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Image;
import se.billes.pdf.renderer.request.PdfRequest;

public class ImageValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public ImageValidator withPageIndex( int index ){
		this.pageIndex = index;
		return this;
	}
	
	public ImageValidator withBlockIndex( int index ){
		this.blockIndex = index;
		return this;
	}
	
	public void validate(PdfRequest request, Image image) throws PdfRequestNotValidException {
		
		DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageIndex).withBlockIndex(blockIndex);
		
		if( image.getPath() == null ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString("Image must have a path"));
		}else{
			if( ! image.getPath().toLowerCase().endsWith( ".pdf" ) 
					&&  ! image.getPath().toLowerCase().endsWith( ".jpg" ) 
					&&  ! image.getPath().toLowerCase().endsWith( ".jpeg" )
			){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Image must be jpg or pdf" ));
			}
			File file = new File( image.getPath() );
			if( ! file.exists() ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find path: (" + image.getPath() + ") for image"));
			}
			
			image.setFile(file);
		}
		
		
		
		
	}

}
