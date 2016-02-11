package se.billes.pdf.json;

/**
 * 
 * @author      Mathias Nilsson
 * @author      mathias@edgesoft.se
 */
public class ConfigNotValidException
	extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ConfigNotValidException() {
	}
	

	public ConfigNotValidException(String message) {
		super(message);
	}

	public ConfigNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigNotValidException(Throwable cause) {
		super(cause);
	}
}