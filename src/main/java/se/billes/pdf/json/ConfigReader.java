package se.billes.pdf.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import se.billes.pdf.registry.Config;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

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
public class ConfigReader {
	
	private String path;
	public ConfigReader( String path ){
		this.path  = path;
	}
	
	private void validateConfig(Config config ) throws ConfigNotValidException{
		if( config.getFirebase() == null ){
			throw new ConfigNotValidException( "Could not find firebase in config" );
		}
		
		if( config.getBms() == null ){
			throw new ConfigNotValidException( "Could not find bms in config" );
		}
		
		if( config.getBms().getMountPath() == null ){
			throw new ConfigNotValidException( "mountPath can not be null" );
		}
		
		if( config.getFirebase().getCompletedPath() == null ){
			throw new ConfigNotValidException( "completedPath can not be null" );
		}
		
		if( config.getFirebase().getDatabaseURL() == null ){
			throw new ConfigNotValidException( "databaseUrl can not be null" );
		}
		
		if( config.getFirebase().getIncomingPath() == null ){
			throw new ConfigNotValidException( "incomingPath can not be null" );
		}
		
		if( config.getFirebase().getPluginPath() == null ){
			throw new ConfigNotValidException( "pluginPath can not be null" );
		}
		
		if( config.getFirebase().getRunningPath() == null ){
			throw new ConfigNotValidException( "runningPath can not be null" );
		}
		
		if( config.getFirebase().getServiceAccountFile() == null ){
			throw new ConfigNotValidException( "serviceAccountFile can not be null" );
		}

		File file = new File(config.getFirebase().getServiceAccountFile());
		if( ! file.exists() ){
			throw new ConfigNotValidException( "Could not find seriveAccountFile path." );
		}
		
		file = new File(config.getBms().getMountPath());
		if( ! file.exists() && !file.isDirectory() ){
			throw new ConfigNotValidException( "Could not find mountPath from bms. Must be a directory." );
		}
		
	}
	
	public Config readConfig() throws ConfigNotValidException{
		File file = new File( path);
		try {
			Config config = null;
			try{
				JsonReader reader = new JsonReader( new FileReader(file));
				config = new Gson().fromJson(reader, Config.class);
				validateConfig(config);
			}catch( JsonSyntaxException e ){
				throw new ConfigNotValidException(e);
			}catch( JsonIOException e ){
				throw new ConfigNotValidException(e);
			}
			
			
			return config;
		} catch (FileNotFoundException e) {
			throw new ConfigNotValidException(e);
		}
	}
}
