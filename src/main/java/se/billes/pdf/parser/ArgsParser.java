package se.billes.pdf.parser;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import se.billes.pdf.json.ConfigNotValidException;
import se.billes.pdf.json.ConfigReader;
import se.billes.pdf.registry.Config;

public class ArgsParser {
	
	private String[] args;
	
	public ArgsParser(String args[]) {
		this.args = args;
	}
	
	public Config parse() throws ConfigNotValidException{
		Config config = null;
		ArgumentParser parser = ArgumentParsers.newArgumentParser("pdf-renderer");
   	 	parser.addArgument("-prop", "--properties")
   	 	.type( String.class )
        .help("Specify properties to use for pdf-renderer");
	   	 Namespace ns = null;
	     try {
	        ns = parser.parseArgs(args); 
	 		config = new ConfigReader(ns.getString( "properties" )).readConfig();
	 		
	     } catch (ArgumentParserException e) {
	    	 parser.handleError(e);
	    	 throw new ConfigNotValidException(e.getMessage());
	     }
	     
	     return config;
	}
}
