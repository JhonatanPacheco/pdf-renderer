package se.billes.pdf.renderer;

import java.util.ArrayList;
import java.util.List;

import se.billes.pdf.json.ConfigNotValidException;
import se.billes.pdf.json.ConfigReader;
import se.billes.pdf.module.ConfigModule;
import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.exception.PluginSocketException;
import se.billes.pdf.renderer.process.FileRendered;
import se.billes.pdf.renderer.process.Renderer;
import se.billes.pdf.renderer.process.Standalone;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.response.Response;
import se.billes.pdf.renderer.socket.BMSSocketRequest;
import se.billes.pdf.renderer.socket.PluginServerSocket;
import se.billes.pdf.renderer.validator.PdfRequestValidator;
import se.billes.pdf.schedule.BMSSocketScheduler;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
	public static void main( String[] args ){
		
		if( args.length == 0 ){
			System.err.println( "First argument must be the path to the config file" );
			System.exit(0);
		}
		Config config = null;
		try {
			config = new ConfigReader(args[0]).readConfig();
		} catch (ConfigNotValidException e) {
			System.err.println( e.getMessage() );
			System.exit(0);
		}
		
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
		final Injector injector = Guice.createInjector(modules);
		if( config.getRun().isStandAlone() ){
			try {
				injector.getInstance(Standalone.class).run();
			} catch (PdfRenderException e) {
				e.printStackTrace();
			}
		}else{
			PluginServerSocket server = new PluginServerSocket(){	
				@Override
				protected void onRequestProcessed( PdfRequest request ) throws PdfRequestNotValidException{
					BMSSocketRequest socketRequest = injector.getInstance(BMSSocketRequest.class);
					
					try{
					    injector.getInstance(PdfRequestValidator.class).validateAll(request);
						new Renderer(request) {
							@Override
							public void onRendered(FileRendered fileRendered) {
								System.out.println(fileRendered);
								
							}
							
						}.onRender();
					}catch( PdfRequestNotValidException e ){
						Response response = request.getResponse();
						
					}catch (PdfRenderException e) {
						e.printStackTrace();
						
					} 
				}
			};

			try {
				server.onStart();
			} catch (PluginSocketException e) {
				e.printStackTrace();
			}
		}
	}
}
