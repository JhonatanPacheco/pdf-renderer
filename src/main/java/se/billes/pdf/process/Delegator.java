package se.billes.pdf.process;

import se.billes.pdf.firebase.FirebaseSchedule;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.process.Renderer;
import se.billes.pdf.renderer.response.PdfAction;
import se.billes.pdf.renderer.response.PdfResponse;
import se.billes.pdf.renderer.validator.PdfRequestValidator;
import se.billes.pdf.request.incoming.IncomingRequest;
import se.billes.pdf.request.incoming.InputRequest;

import com.google.inject.Inject;

public class Delegator {
	@Inject PdfRequestValidator pdfRequestValidator;
	@Inject FirebaseSchedule firebaseSchedule;
	
	public void execute(IncomingRequest request) {
		InputRequest input = request.getInput();
		try {
			pdfRequestValidator.validateAll(input);
			new Renderer(input) {
				@Override
				public void onRendered(PdfResponse response) {
					
				}
				
			}.onRender();
		} catch (PdfRequestNotValidException e) {
			e.printStackTrace();
		} catch (PdfRenderException e) {
			e.printStackTrace();
		}
	}
	
	private static PdfResponse generateFailResponse(Exception e, InputRequest request ){
		PdfResponse response = new PdfResponse();
		PdfAction action = new PdfAction();
		action.setMessage(e.getMessage());
		action.setSuccess(false);
		response.setAction(action);
		return response;
	}
}
