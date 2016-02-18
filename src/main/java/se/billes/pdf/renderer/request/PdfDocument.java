package se.billes.pdf.renderer.request;

import se.billes.pdf.renderer.model.Color;
import se.billes.pdf.renderer.model.Cutmarks;
import se.billes.pdf.renderer.model.Font;
import se.billes.pdf.renderer.model.Hyphenation;
import se.billes.pdf.renderer.model.Page;

import com.itextpdf.text.pdf.HyphenationAuto;

public class PdfDocument {
	
	private Integer[] size;
	private Cutmarks cutmarks;
	private String name;
	private Page[] pages;
	private Color[] colors;
	private Font[] fonts;
	private Hyphenation hyphenation;
	private HyphenationAuto hyphenationAuto;


	public Integer[] getSize() {
		return size;
	}

	public void setSize(Integer[] size) {
		this.size = size;
	}
	
	public Cutmarks getCutmarks() {
		return cutmarks;
	}


	public void setCutmarks(Cutmarks cutmarks) {
		this.cutmarks = cutmarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Page[] getPages() {
		return pages;
	}

	public void setPages(Page[] pages) {
		this.pages = pages;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public Font[] getFonts() {
		return fonts;
	}

	public void setFonts(Font[] fonts) {
		this.fonts = fonts;
	}

	public Hyphenation getHyphenation() {
		return hyphenation;
	}

	public void setHyphenation(Hyphenation hyphenation) {
		this.hyphenation = hyphenation;
	}

	public HyphenationAuto getHyphenationAuto() {
		return hyphenationAuto;
	}

	public void setHyphenationAuto(HyphenationAuto hyphenationAuto) {
		this.hyphenationAuto = hyphenationAuto;
	}

}
