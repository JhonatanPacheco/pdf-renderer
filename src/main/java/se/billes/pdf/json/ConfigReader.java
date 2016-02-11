package se.billes.pdf.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import se.billes.pdf.registry.Config;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class ConfigReader {
	
	private String path;
	public ConfigReader( String path ){
		this.path  = path;
	}
	
	private void validateConfig(Config config ) throws ConfigNotValidException{
		if( config.getRun() == null ){
			throw new ConfigNotValidException( "Could not find run in config" );
		}
		if( config.getRun().getEnvironment() == null ){
			throw new ConfigNotValidException( "Enviroment can not be null" );
		}
		
		if( !( config.getRun().getEnvironment().equals( "standalone" ) || config.getRun().getEnvironment().equals( "server" ) ) ){
			throw new ConfigNotValidException( "Enviroment must be standalone or server" );
		}
		
		if( config.getRun().getMountPath() == null ){
			throw new ConfigNotValidException( "Mount path can not be null" );
		}
		File file = new File(config.getRun().getMountPath());
		if( ! file.exists() || ! file.isDirectory() ){
			throw new ConfigNotValidException( "Could not find mount path. Must be a directory" );
		}
		
		if( config.getRun().isStandAlone() ){
			if( config.getRun().getPathToJsonDocument() == null ){
				throw new ConfigNotValidException( "You must specify path to json document when running in stand alone" );
			}
			if( ! new File(config.getRun().getPathToJsonDocument()).exists() ){
				throw new ConfigNotValidException( "Could not find json file" );
			}
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
