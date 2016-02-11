package se.billes.pdf.renderer.exception;

/**
 * Exception to mark incorrect creation of SpotColor
 * 
 * @author      Mathias Nilsson
 * @author      mathias@edgesoft.se
 */
public class CreateColorException
	extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CreateColorException() {
	}
	
	/**
	 * Constructs a new CreateColorException exception with the specified detail message
	 * @param message the detailed message for the Exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 */
	public CreateColorException(String message) {
		super(message);
	}

	public CreateColorException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreateColorException(Throwable cause) {
		super(cause);
	}
}