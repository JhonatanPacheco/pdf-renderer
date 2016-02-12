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
 *
 * Connection class that handles all incoming requests to the server.
 * When parsed the connection will forward the parsed data to implementing class
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