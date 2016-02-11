package se.billes.pdf.renderer.exception;

/**
 * 
 * @author      Mathias Nilsson
 * @author      mathias@edgesoft.se
 */
public class PdfRequestNotValidException
	extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PdfRequestNotValidException() {
	}
	
	/**
	 * Constructs a new PdfRequestNotValidException exception with the specified detail message
	 * @param message the detailed message for the Exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 */
	public PdfRequestNotValidException(String message) {
		super(message);
	}

	public PdfRequestNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfRequestNotValidException(Throwable cause) {
		super(cause);
	}
}