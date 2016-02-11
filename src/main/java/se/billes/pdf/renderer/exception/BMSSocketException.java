package se.billes.pdf.renderer.exception;

/**
 * Exception to mark wrong when connecting to bms.
 * 
 * @author      Mathias Nilsson
 * @author      mathias@edgesoft.se
 */
public class BMSSocketException
	extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BMSSocketException() {
	}
	
	/**
	 * Constructs a new BMSSocketException exception with the specified detail message
	 * @param message the detailed message for the Exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 */
	public BMSSocketException(String message) {
		super(message);
	}

	public BMSSocketException(String message, Throwable cause) {
		super(message, cause);
	}

	public BMSSocketException(Throwable cause) {
		super(cause);
	}
}