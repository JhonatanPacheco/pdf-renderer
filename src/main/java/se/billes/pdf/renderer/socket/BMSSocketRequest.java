package se.billes.pdf.renderer.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.BMSSocketException;
import se.billes.pdf.renderer.response.AbstractResponse;

import com.google.gson.Gson;
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
public class BMSSocketRequest {
	
	@Inject Config config;
	
	
	public void onJsonRequest( AbstractResponse response )  throws BMSSocketException{
		OutputStreamWriter out = null;
		ObjectInputStream in = null;
		Socket requestSocket = null;
		try{
			System.out.println("Before sending socket to bmsl");
			System.out.println(config.getBms().getHost());
			System.out.println(config.getBms().getPort());
			System.out.println(new Gson().toJson(response));
			requestSocket = new Socket( config.getBms().getHost(), config.getBms().getPort());
			out = new OutputStreamWriter(requestSocket.getOutputStream());
			out.write(new Gson().toJson(response));
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
			}catch( Exception e ){}
			try{
				if( out != null )
				out.close();
			}catch( Exception e){}
			try{	
				if( requestSocket != null )
				requestSocket.close();
			}catch(IOException e){}
		}
	}
	
}
