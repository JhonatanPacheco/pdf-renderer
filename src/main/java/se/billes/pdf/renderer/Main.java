package se.billes.pdf.renderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.billes.pdf.json.ConfigNotValidException;
import se.billes.pdf.json.ConfigReader;
import se.billes.pdf.module.ConfigModule;
import se.billes.pdf.registry.Config;
import se.billes.pdf.registry.Endpoint;
import se.billes.pdf.renderer.exception.BMSSocketException;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.exception.PluginSocketException;
import se.billes.pdf.renderer.process.Renderer;
import se.billes.pdf.renderer.process.Standalone;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.response.PdfResponse;
import se.billes.pdf.renderer.socket.BMSSocketRequest;
import se.billes.pdf.renderer.socket.PluginServerSocket;
import se.billes.pdf.renderer.validator.PdfRequestValidator;
import se.billes.pdf.schedule.BMSSocketScheduler;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

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
public class Main {
	
	public static void main( String[] args ){
		
		if( args.length == 0 ){
			System.err.println( "First argument must be the path to the config file" );
			System.exit(0);
		}
		Config config = null;
		System.err.println( new Date().getTime() + ": before read config");
		try {
			config = new ConfigReader(args[0]).readConfig();
		} catch (ConfigNotValidException e) {
			System.err.println( e.getMessage() );
			System.exit(0);
		}
		
		System.err.println( new Date().getTime() + ": after read config");
		List<AbstractModule> modules = new ArrayList<AbstractModule>();
		modules.add( new ConfigModule(config));
		if( ! config.getRun().isStandAlone() ){
			modules.add( new org.nnsoft.guice.guartz.QuartzModule(){
				@Override
				protected void schedule() {
					scheduleJob( BMSSocketScheduler.class );
				}
			});
		}
		System.err.println( new Date().getTime() + ": before creating injector");
		final Injector injector = Guice.createInjector(modules);
		System.err.println( new Date().getTime() + ": after creating injector");
		if( config.getRun().isStandAlone() ){
			try {
				System.err.println( new Date().getTime() + ": before running standalone");
				injector.getInstance(Standalone.class).run();
			} catch (PdfRenderException e) {
				e.printStackTrace();
			}
		}else{
			PluginServerSocket server = new PluginServerSocket(){	
				@Override
				protected void onRequestProcessed( PdfRequest request ) throws PdfRequestNotValidException{
					final BMSSocketRequest socketRequest = injector.getInstance(BMSSocketRequest.class);
					
					try{
					    injector.getInstance(PdfRequestValidator.class).validateAll(request);
						new Renderer(request) {
							@Override
							public void onRendered(PdfResponse response) {
								System.out.println(new Gson().toJson(response));
								try {
									socketRequest.onJsonRequest(response);
								} catch (BMSSocketException e) {
									e.printStackTrace();
								}
								
							}
							
						}.onRender();
					}catch( PdfRequestNotValidException e ){
						e.printStackTrace();
						
					}catch (PdfRenderException e) {
						e.printStackTrace();
						
					}
				}
			};

			try {
				Endpoint endpoint = config.getRegistry().getEndpoint();
				server.setPort( endpoint.getPort());
				server.onStart();
			} catch (PluginSocketException e) {
				e.printStackTrace();
			}
		}
	}
}
