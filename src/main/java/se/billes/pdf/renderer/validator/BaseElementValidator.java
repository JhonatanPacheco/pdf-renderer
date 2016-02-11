package se.billes.pdf.renderer.validator;

import java.util.ArrayList;
import java.util.List;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Barcode;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.Image;
import se.billes.pdf.renderer.model.Line;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.model.QRCode;
import se.billes.pdf.renderer.request.PdfRequest;

public class BaseElementValidator implements IPdfRequestValidatable {
	List<String> types = new ArrayList<String>();
	List<String> absolutePositionedBlocks = new ArrayList<String>();
	public BaseElementValidator() {
		types.add( "IMAGE" );
		types.add( "BLOCK" );
		types.add( "BARCODE" );
		types.add( "LINE" );
		types.add( "QR" );
		
		absolutePositionedBlocks.add( "IMAGE" );
		absolutePositionedBlocks.add( "BLOCK" );
		absolutePositionedBlocks.add( "QR" );
		absolutePositionedBlocks.add( "BARCODE" );
	}
	
	
	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
		int pageCount = 0;
		for( Page page : request.getPages() ){
			int blockCount = 0;
			if( page.getBlocks() != null ){
				for( BaseElement element : page.getBlocks() ){
					DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageCount).withBlockIndex(blockCount);
					if( element.getType() == null ){
						throw new PdfRequestNotValidException(errorFactory.appendErrorString("A block must have a type"));
					}
					
					if(! acceptedTypes(element.getType())){
						throw new PdfRequestNotValidException( errorFactory.appendErrorString("Type: " + element.getType() + " is not accepted") );
					}
					
					if( isAbsolutePositionedBlock(element.getType() )){
						if( element.getPosition() == null || element.getPosition().length != 4 ){
							throw new PdfRequestNotValidException( errorFactory.appendErrorString("Absolute positioned blocks must have position:[x,y,w,h]") );
						}
					}
					
					if( element instanceof Block ){
						Block block = (Block) element;
						new BlockValidator()
						.withBlockIndex(blockCount)
						.withPageIndex(pageCount)
						.validate(request, block);						
					}
					if( element instanceof Line ){
						Line line = (Line) element;
						new LineValidator()
						.withPageIndex(pageCount)
						.withBlockIndex(blockCount)
						.validate(request, line);
					}	
					if( element instanceof Image ){
						Image image = (Image) element;
						new ImageValidator()
						.withPageIndex(pageCount)
						.withBlockIndex(blockCount)
						.validate(request, image);
					}
					if( element instanceof QRCode ){
						QRCode qr = (QRCode) element;
						new QRCodeValidator()
						.withPageIndex(pageCount)
						.withBlockIndex(blockCount)
						.validate(request, qr);
					}	
					if( element instanceof Barcode ){
						Barcode barcode = (Barcode) element;
						new BarcodeValidator()
						.withPageIndex(pageCount)
						.withBlockIndex(blockCount)
						.validate(request, barcode);
					}	
					
				}
				blockCount++;
			}
			pageCount ++;
		}
	}
	
	private boolean isAbsolutePositionedBlock( String type ){
		return absolutePositionedBlocks.contains(type.toUpperCase());
	}
	
	private boolean acceptedTypes( String type ){
		return types.contains(type.toUpperCase());
	}

}
