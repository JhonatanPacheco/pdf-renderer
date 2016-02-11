package se.billes.pdf.renderer.exception;

/**
 * Exception to mark wrong iText implementation
 * 
 * @author      Mathias Nilsson
 * @author      mathias@edgesoft.se
 */
public class PdfRenderException
	extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PdfRenderException() {
	}
	
	/**
	 * Constructs a new PdfRenderException exception with the specified detail message
	 * @param message the detailed message for the Exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 */
	public PdfRenderException(String message) {
		super(message);
	}

	public PdfRenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfRenderException(Throwable cause) {
		super(cause);
	}
}