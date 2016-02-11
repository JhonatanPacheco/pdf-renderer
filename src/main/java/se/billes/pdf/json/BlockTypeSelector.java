package se.billes.pdf.json;

import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;
import se.billes.pdf.renderer.model.Barcode;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.Image;
import se.billes.pdf.renderer.model.Line;
import se.billes.pdf.renderer.model.QRCode;
import se.billes.pdf.renderer.model.text.AbstractParagraph;
import se.billes.pdf.renderer.model.text.AbstractPhrase;
import se.billes.pdf.renderer.model.text.DottedFillPhrase;
import se.billes.pdf.renderer.model.text.FillParagraph;
import se.billes.pdf.renderer.model.text.Paragraph;
import se.billes.pdf.renderer.model.text.Phrase;
import se.billes.pdf.renderer.model.text.TableCell;
import se.billes.pdf.renderer.model.text.TableParagraph;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;




public class BlockTypeSelector {
	
	public Gson createGson(){
		
		GsonFireBuilder builder = new GsonFireBuilder()
		.registerTypeSelector(AbstractPhrase.class, new TypeSelector<AbstractPhrase>() {
			@Override
			public Class<? extends AbstractPhrase> getClassForElement( JsonElement readElement) {
				 JsonElement element = readElement.getAsJsonObject().get("dotted");
				 if( element != null ){
					  return DottedFillPhrase.class;
				 }
				
				return Phrase.class;
			}
			
		})
		.registerTypeSelector(AbstractParagraph.class, new TypeSelector<AbstractParagraph>() {
			@Override
			public Class<? extends AbstractParagraph> getClassForElement( JsonElement readElement) {
				 JsonElement element = readElement.getAsJsonObject().get("cells");
				 if( element != null ){
					  return TableParagraph.class;
				 }
				 
				 element = readElement.getAsJsonObject().get("padding");
				 if( element != null ){
					return TableCell.class;
				 }
				 
				 element = readElement.getAsJsonObject().get("fill");
				 if( element != null ){
					return FillParagraph.class;
				 }

				return Paragraph.class;
			}
			
		})
	    .registerTypeSelector(BaseElement.class, new TypeSelector<BaseElement>() {
	        @Override
	        public Class<? extends BaseElement> getClassForElement(JsonElement readElement) {
	            JsonElement element = readElement.getAsJsonObject().get("type");
	            if( element == null ){
	            	throw new JsonSyntaxException( "You must have a type in block" );
	            }
	            String kind = element.getAsString();
	            if(kind.equals("LINE")){
	                return Line.class;
	            }else if(kind.equals("BLOCK")) {
	                return Block.class; 
	            }else if(kind.equals("IMAGE")) {
	                return Image.class; 
		        }else if(kind.equals("QR")) {
	                return QRCode.class; 
	            }else if(kind.equals("BARCODE")) {
	                return Barcode.class; 
	            } else {
	                return null; //returning null will trigger Gson's default behavior
	            }
	        }
	    });
		
		return builder.createGson();
		
		
	}
	
}
