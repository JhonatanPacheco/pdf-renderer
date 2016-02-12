package se.billes.pdf.json;


/**
 * This program is built on top of iText.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License version 3 as published by the Free Software Foundation with 
 * the addition of the following permission added to Section 15 as permitted in Section 7(a): 
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY ITEXT GROUP NV, ITEXT GROUP 
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of 
 * the GNU Affero General Public License along with this program; if not, 
 * see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc., 51 Franklin Street, 
 * Fifth Floor, Boston, MA, 02110-1301 USA, or download the license from the following
 * URL: http://itextpdf.com/terms-of-use/ 
 * The interactive user interfaces in modified source and object code versions of this program must 
 * display Appropriate Legal Notices, as required under Section 5 of the GNU Affero General Public License.
 * In accordance with Section 7(b) of the GNU Affero General Public License, you must retain the producer line 
 * in every PDF that is created or manipulated using iText. You can be released from the requirements of the 
 * license by purchasing a commercial license. Buying such a license is mandatory as soon as you develop 
 * commercial activities involving the iText software without disclosing the source code of your own 
 * applications. These activities include: offering paid services to customers as an ASP, 
 * serving PDFs on the fly in a web application, shipping iText with a closed source product.
 */
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
