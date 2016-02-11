package se.billes.pdf.registry;


public class Config {
	private Registry registry;
    private Endpoint bms;
    private Environment run;
    
	
    public Registry getRegistry() {
		return registry;
	}
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public Endpoint getBms() {
		return bms;
	}
	public void setBms(Endpoint bms) {
		this.bms = bms;
	}
	
	public Environment getRun() {
		return run;
	}
	public void setRun(Environment run) {
		this.run = run;
	}

}
