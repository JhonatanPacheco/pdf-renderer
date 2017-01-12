package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseDocument {
	private List<Integer> size;
	private List<FirebaseCutmarks> cutmarks;
	private String name;
	private List<FirebasePage> pages;
	
	public FirebaseDocument(){
		
	}
	
	public List<Integer> getSize() {
		return size;
	}
	public void setSize(List<Integer> size) {
		this.size = size;
	}
	public List<FirebaseCutmarks> getCutmarks() {
		return cutmarks;
	}
	public void setCutmarks(List<FirebaseCutmarks> cutmarks) {
		this.cutmarks = cutmarks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FirebasePage> getPages() {
		return pages;
	}
	public void setPages(List<FirebasePage> pages) {
		this.pages = pages;
	}

}
