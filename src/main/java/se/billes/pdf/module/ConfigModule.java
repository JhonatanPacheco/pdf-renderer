package se.billes.pdf.module;

import se.billes.pdf.registry.Config;

import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule{
	
	private Config config;
	
	public ConfigModule(Config config ){
		this.config = config;
	}

	@Override
	protected void configure() {
		bind(Config.class).toInstance(config);
	}

}
