package se.billes.pdf.renderer.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.BMSSocketException;
import se.billes.pdf.renderer.response.Response;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class BMSSocketRequest {
	
	@Inject Config config;
	
	public void onJsonRequest( Response response )  throws BMSSocketException{
		OutputStreamWriter out = null;
		ObjectInputStream in = null;
		Socket requestSocket = null;
		try{
			requestSocket = new Socket( config.getBms().getHost(), config.getBms().getPort());
			out = new OutputStreamWriter(requestSocket.getOutputStream());
			if( response == null ){
				out.write(new Gson().toJson(config.getRegistry()));
			}else{
				out.write(new Gson().toJson(response));
			}
			
			out.flush();
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace(); 
		}
		finally{
			try{
				if( in != null )
				in.close();
				if( out != null )
				out.close();
				
				if( requestSocket != null )
				requestSocket.close();
			}
			catch(IOException e){
			}
		}
	}
	
}
