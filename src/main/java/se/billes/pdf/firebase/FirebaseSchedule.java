package se.billes.pdf.firebase;

import java.util.Date;

import se.billes.pdf.json.BlockTypeSelector;
import se.billes.pdf.process.Delegator;
import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.request.PdfRequest;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Result;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class FirebaseSchedule {
	
	@Inject Config config;
	@Inject Delegator delegator;
	
	private DatabaseReference getIncomingReference() {
	 FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference ref = database.getReference(config.getFirebase().getIncomingPath());
     return ref;
	}
	
	public void onSchedule() {

		final DatabaseReference ref = getIncomingReference();	
		ChildEventListener listener = new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot snapshot) {}
			
			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}
			
			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {}
			
			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				ref.removeEventListener(this);
				onTransaction(ref.child(snapshot.getKey()));
			}
			
			@Override
			public void onCancelled(DatabaseError error) {}
		};
		
		ref.limitToFirst(1).addChildEventListener(listener);
	}
	
	// TODO: handle data that is wrong
	private void onTransaction (final DatabaseReference childRef) {
		childRef.runTransaction(new Transaction.Handler(){
			@Override
			public Result doTransaction(MutableData data) {
				if (data.getValue() != null) {
					try {
						if (data.getChildrenCount() > 0) {
							data.getChildren().iterator().next();
							String val = data.getValue().toString();
							Gson gson = new BlockTypeSelector().createGson();
							PdfRequest req = gson.fromJson(val.replace("=",  ":"), PdfRequest.class);
							req.setStartExecutionTime(new Date().getTime());
						}
					} catch (DatabaseException e ){
						
					}
				    
					childRef.removeValue();
					return Transaction.success(data);
				} else {
					return Transaction.success(data);
				}
				
			}

			@Override
			public void onComplete(DatabaseError error, boolean committed, DataSnapshot snapshot) {
				if (committed) {
					// TODO: check for null
					FirebaseDatabase database = FirebaseDatabase.getInstance();
				    DatabaseReference ref = database.getReference(config.getFirebase().getRunningPath()).child(snapshot.getKey());
					ref.setValue(snapshot.getValue());
					PdfRequest request = fromDataSnapShot(snapshot);
					// TODO: refactor pdfRequest to hold triggerIndex, chainIndex and input, output
					request.setPath("/mathias");
					request.setKey(snapshot.getKey());
					delegator.execute(request);
				}else {
					System.err.println(error);
					onSchedule();
				}
			}
		});
	}
	
	private PdfRequest fromDataSnapShot (DataSnapshot snapshot) {
		if (snapshot.getChildrenCount() > 0) {
			snapshot.getChildren().iterator().next();
			String val = snapshot.getValue().toString();
			Gson gson = new BlockTypeSelector().createGson();
			return gson.fromJson(val.replace("=",  ":"), PdfRequest.class);
		}
		return null;
	}
	
}
