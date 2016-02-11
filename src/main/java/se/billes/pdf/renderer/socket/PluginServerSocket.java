package se.billes.pdf.renderer.socket;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.exception.PluginSocketException;
import se.billes.pdf.renderer.request.PdfRequest;



public class PluginServerSocket{
	
	private int port = 25677;
	SocketChannel sc;
	private volatile boolean run = true;
	ServerSocketChannel ssc;
	private int millisecondsToPerformCommand = 100;
	private long lastCommandTime = System.currentTimeMillis();

	
	public void setPort( int port ){
		this.port = port;
	}

	public void onStart() throws PluginSocketException{

			try {			
				ssc = ServerSocketChannel.open();
			    ssc.socket().bind(new InetSocketAddress(port));
			    ssc.configureBlocking(false);
			  
			    while (true) {
			    	
			    	sc = ssc.accept();
			    	
			    	if( sc != null ){
				    	try{
							new JsonParseConnection( sc.socket()){
								@Override
								protected void onError(Throwable e) {	
									e.printStackTrace();
								}

								@Override
								protected void onRequestProcessed(PdfRequest request) throws PdfRequestNotValidException{
									PluginServerSocket.this.onRequestProcessed(request);
								}
							};
						}catch( Exception e ){
							e.printStackTrace();
						}
			    	}else{
			    		try{
			    			Thread.sleep(100);
			    		}catch( InterruptedException e ){
			    			e.printStackTrace();
			    		}
			    	}
			    	
			    	long current = System.currentTimeMillis();
					if( lastCommandTime + millisecondsToPerformCommand < current ){
						lastCommandTime = System.currentTimeMillis();
						//onProcess();
					}
			    	if( ! run ) break;
			    	
			     }


			} catch (IOException e) {
				onShutdown();
			}
			
	
		
	}


	
	public void onShutdown() {
		run = false;
		try{
			if( sc != null ){
				sc.close();
			}

		}catch ( IOException ioe ) {}
		
		if( ssc != null ){
			try {
				ssc.close();
			} catch (IOException e) {

			}
		}
		
		
	}

	protected  void onRequestProcessed( PdfRequest request ) throws PdfRequestNotValidException{
		
	};
	
	
}