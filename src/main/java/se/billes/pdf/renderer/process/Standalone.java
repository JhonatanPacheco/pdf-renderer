package se.billes.pdf.renderer.process;

import java.io.FileReader;
import java.util.Date;

import se.billes.pdf.json.BlockTypeSelector;
import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.response.PdfResponse;
import se.billes.pdf.renderer.validator.PdfRequestValidator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;

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
public class Standalone {
	@Inject BlockTypeSelector blockTypeSelector;
	@Inject Config config;
	@Inject PdfRequestValidator pdfRequestValidator;
	
	
	public void run() throws PdfRenderException{
		
		try{
			System.err.println( new Date().getTime() + ": before parse json");
			Gson gson = blockTypeSelector.createGson();
			JsonReader reader = new JsonReader(new FileReader(config.getRun().getPathToJsonDocument()));
			System.err.println( new Date().getTime() + ": after read json file");
			final PdfRequest request = gson.fromJson(reader,PdfRequest.class);
			System.err.println( new Date().getTime() + ": after parse json");
			request.setStartExecutionTime(new Date().getTime());
			System.err.println( new Date().getTime() + ": before validate all");
			pdfRequestValidator.validateAll(request);
			System.err.println( new Date().getTime() + ": after validate");
			new Renderer(request) {
				@Override
				public void onRendered( PdfResponse response ) {
					System.out.println(new Gson().toJson(response));
				}
			}.onRender();
		}catch( Exception e ){
			throw new PdfRenderException(e);
		}
	}
}
