package se.billes.pdf.firebase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;

import se.billes.pdf.registry.Config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;
import com.google.inject.Inject;

public class FirebaseInitializer {
	
	@Inject Config config;
	
	public void init() throws FileNotFoundException{
		FirebaseOptions options = new FirebaseOptions.Builder()
		.setServiceAccount(new FileInputStream(config.getFirebase().getServiceAccountFile()))
		.setDatabaseUrl(config.getFirebase().getDatabaseURL())
		.build();
		FirebaseApp.initializeApp(options);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference(config.getFirebase().getPluginPath());
		final CountDownLatch sync = new CountDownLatch(1);
		ref.push().setValue("connected")
		   .addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        sync.countDown();
		      }
		    });
		try {
			sync.await();
		} catch (InterruptedException e) {
		}
		
	}
}
