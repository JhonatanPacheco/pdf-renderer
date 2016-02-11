package se.billes.pdf.renderer.exception;

/**
 * Exception to mark incorrect creation of BaseFont
 * 
 * @author      Mathias Nilsson
 * @author      mathias@edgesoft.se
 */
public class CreateFontException
	extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CreateFontException() {
	}
	
	/**
	 * Constructs a new CreateFontException exception with the specified detail message
	 * @param message the detailed message for the Exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 */
	public CreateFontException(String message) {
		super(message);
	}

	public CreateFontException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreateFontException(Throwable cause) {
		super(cause);
	}
}