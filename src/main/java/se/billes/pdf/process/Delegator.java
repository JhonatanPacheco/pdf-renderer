package se.billes.pdf.process;

import java.util.Arrays;

import se.billes.pdf.firebase.FirebaseSchedule;
import se.billes.pdf.firebase.model.FirebaseRequest;
import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.process.Renderer;
import se.billes.pdf.renderer.response.FailedOutput;
import se.billes.pdf.renderer.response.PdfResponse;
import se.billes.pdf.renderer.validator.PdfRequestValidator;
import se.billes.pdf.request.incoming.IncomingRequest;
import se.billes.pdf.request.incoming.InputRequest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class Delegator {
	@Inject PdfRequestValidator pdfRequestValidator;
	@Inject FirebaseSchedule firebaseSchedule;
	@Inject Config config;
	
	public void execute(final IncomingRequest request, final String payload) {
		InputRequest input = request.getInput();
		try {
			pdfRequestValidator.validateAll(input);
			new Renderer(input) {
				@Override
				public void onRendered(PdfResponse response) {
					response = getCommonResponse(request, response, payload);
					setCompleted(request, response);
				}
				
			}.onRender();
		} catch (PdfRequestNotValidException e) {
			e.printStackTrace();
			generateFailResponse(e, request, payload);
		} catch (PdfRenderException e) {
			e.printStackTrace();
			generateFailResponse(e, request, payload);
		}finally {
			FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference ref = database.getReference(config.getFirebase().getRunningPath());
		    ref.child(request.getKey()).removeValue();
		    
		    firebaseSchedule.onSchedule();
		}
	}
	
	private void setCompleted (IncomingRequest request, PdfResponse response) {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference(config.getFirebase().getCompletedPath()).child(request.getKey());
		ref.setValue(response);
	}
	
	private PdfResponse getCommonResponse (IncomingRequest request, PdfResponse response, String payload) {
		if (response == null) {
			response = new PdfResponse();
		}
		
		response.setChainId(request.getChainId());
		response.setTriggerIndex(request.getTriggerIndex());
		
		FirebaseRequest firebaseRequestInput = new Gson().fromJson(payload, FirebaseRequest.class);
		firebaseRequestInput.input.startExecutionTime = null;
		response.setPayload(firebaseRequestInput.input);
		return response;
	}
	
	private  void generateFailResponse(Exception e, IncomingRequest request, String payload ){
		PdfResponse response = getCommonResponse(request, null, payload);
		FailedOutput output = new FailedOutput();
		output.setErrors(Arrays.asList(new String[]{e.getMessage()}));
		response.setType("fail");
		response.setOutput(output);
		setCompleted(request, response);
	}
}
