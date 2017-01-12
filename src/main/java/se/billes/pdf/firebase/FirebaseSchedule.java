package se.billes.pdf.firebase;

import java.util.Date;

import se.billes.pdf.firebase.model.FirebaseRequest;
import se.billes.pdf.firebase.model.Kalle;
import se.billes.pdf.json.BlockTypeSelector;
import se.billes.pdf.process.Delegator;
import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.validator.PdfRequestValidator;
import se.billes.pdf.request.incoming.IncomingRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class FirebaseSchedule {
	
	@Inject Config config;
	@Inject Delegator delegator;
	@Inject PdfRequestValidator pdfRequestValidator;
	
	private DatabaseReference getIncomingReference() {
	 FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference ref = database.getReference(config.getFirebase().getIncomingPath());
     return ref;
	}
	
	public void onSchedule() {

		final DatabaseReference ref = getIncomingReference();
		
		ValueEventListener listener = new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				System.err.println("you");
				ref.removeEventListener(this);
				if (snapshot.getChildrenCount() > 0 ){
					System.out.println("On data change");
					onTransaction(ref.child(snapshot.getChildren().iterator().next().getKey()));
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
			}
		};
		
		ref.limitToFirst(1).addValueEventListener(listener);
	}
	
	private void onTransaction (final DatabaseReference childRef) {
		childRef.runTransaction(new Transaction.Handler(){
			@Override
			public Result doTransaction(MutableData data) {
				if (data.getValue() != null) {
					try {
						
						Gson gson = new BlockTypeSelector().createGson();
						String json = new Gson().toJson(data.getValue());
						//IncomingRequest req = gson.fromJson(json, IncomingRequest.class);
						//req.getInput().setStartExecutionTime(new Date().getTime());
						FirebaseRequest val = new Gson().fromJson(json, FirebaseRequest.class);
						val.getInput().setStartExecutionTime(new Date().getTime());
						System.err.println(val);
						childRef.removeValue();
						data.setValue(val);
						return Transaction.success(data);
					} catch (Exception e ){
						e.printStackTrace();
						childRef.removeValue();
						return Transaction.abort();
					}
					
				} else {
					return Transaction.success(data);
				}
				
			}

			@Override
			public void onComplete(DatabaseError error, boolean committed, DataSnapshot snapshot) {
				

				if (committed) {
					System.err.println(snapshot);
					System.err.println("After snapshot");
					System.out.println(new Gson().toJson(snapshot.getValue()));
					FirebaseDatabase database = FirebaseDatabase.getInstance();
				    DatabaseReference ref = database.getReference(config.getFirebase().getRunningPath()).child(snapshot.getKey());
					ref.setValue(snapshot.getValue());
					IncomingRequest request = fromDataSnapShot(snapshot);
					request.setKey(snapshot.getKey());
					delegator.execute(request);
				}else {
					if (error != null) {
						System.err.println(error);
					}
					//onSchedule();
				}
			}
		});
	}
	
	private IncomingRequest fromDataSnapShot (DataSnapshot snapshot) {
		if (snapshot.getChildrenCount() > 0) {
			snapshot.getChildren().iterator().next();
			String val = snapshot.getValue().toString();
			Gson gson = new BlockTypeSelector().createGson();
			return gson.fromJson(val.replace("=",  ":"), IncomingRequest.class);
		}
		return null;
	}
	
}
