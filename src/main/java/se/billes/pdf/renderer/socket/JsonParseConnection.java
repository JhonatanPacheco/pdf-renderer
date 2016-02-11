package se.billes.pdf.renderer.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;

import se.billes.pdf.json.BlockTypeSelector;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.request.PdfRequest;

import com.google.gson.Gson;


/**
* Connection class that handles all incoming requests to the server.
* When parsed the connection will forward the parsed data to implementing class
* 
* @author Mathias Nilsson
*
*/
public abstract class JsonParseConnection implements Runnable{

	private Socket socket;
	private long startTime;


	public JsonParseConnection(  Socket socket ){
		this.startTime = new Date().getTime();
		this.socket = socket;
		Thread t = new Thread( this );
		t.start();
	}

	public void run() {
		InputStream is = null;
		ObjectInputStream in = null;
		Throwable  error = null;
		try{
			is = socket.getInputStream();
			if ( is == null) return;

			String jsonRequest = inputStreamAsString(is);

			try{
				Gson gson = new BlockTypeSelector().createGson();
				PdfRequest request = gson.fromJson(jsonRequest, PdfRequest.class);
				request.setStartExecutionTime(startTime);
				onRequestProcessed( request );
				
			}catch( Exception e ){
				error = e;
			}
			

		}catch ( IOException ex ){
			error = ex;
		}finally{
			if( is != null ){
				try{
					is.close();
				}catch( Exception e ){}
			}
			if( in != null ){
				try{
					in.close();
				}catch( Exception e ){}
			}
			
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		
		if( error != null ){
			onError(error);
		}
	}

	public  String inputStreamAsString(InputStream stream) throws IOException {
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(stream));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = br.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        return sb.toString();
		}catch( Exception e ){
			
		}finally{
			try{
				br.close();
			}catch(Exception e ){}
		}
		
		return null;
    }

	protected abstract void onRequestProcessed( PdfRequest request ) throws PdfRequestNotValidException;
	protected abstract void onError( Throwable e );

}