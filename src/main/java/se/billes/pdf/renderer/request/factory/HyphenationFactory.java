package se.billes.pdf.renderer.request.factory;

import java.util.Locale;

import com.itextpdf.text.pdf.HyphenationAuto;

import se.billes.pdf.renderer.model.Hyphenation;

public class HyphenationFactory {
	
	private HyphenationAuto getHyphenationAuto( Locale locale ){
		if( locale == null ){
			return null;
		}
		HyphenationAuto hyphenation = new HyphenationAuto( locale.getLanguage(), locale.getCountry(), 2 , 2 );
		return hyphenation;
	}
	
	public HyphenationAuto getHypenationAutoByLocale( Hyphenation hypen ){
		
		if( hypen == null ) return null;
		String language = hypen.getLanguage();
		String country = hypen.getCountry();
		if( language == null ){
			return null;
		}
		if( country == null ){
			country = "";
		}
		
		Locale locale = null;
		
		if( language.equals( "" )) return null;
		// Danish
		if( language.toLowerCase().equals( "da" )){
			locale = new Locale( "da", "" );
		}
		// German
		if( language.toLowerCase().equals( "de" )){
			if( country.toUpperCase().equals( "DR") ){
				locale = new Locale( "de", "DR" );
			}
			if( locale == null )
			locale = new Locale( "de", "" );
		}
		
		// Greek
		if( language.toLowerCase().equals( "el" )){
			locale = new Locale( "el", "" );
		}
		
		// English
		if( language.toLowerCase().equals( "en" )){
			if( country.toUpperCase().equals( "GB") ){
				locale = new Locale( "en", "GB" );
			}
			if( country.toUpperCase().equals( "US") ){
				locale = new Locale( "en", "US" );
			}
			if( locale == null )
			locale = new Locale( "en", "" );
		}
		
		// Spanish
		if( language.toLowerCase().equals( "es" )){
			locale = new Locale( "es", "" );
		}
		
		// Finnish
		if( language.toLowerCase().equals( "fi" )){
			locale = new Locale( "fi", "" );
		}
		
		// French
		if( language.toLowerCase().equals( "fr" )){
			locale = new Locale( "fr", "" );
		}
		
		// Hungarian
		if( language.toLowerCase().equals( "hu" )){
			locale = new Locale( "hu", "" );
		}
		
		// Italian
		if( language.toLowerCase().equals( "it" )){
			locale = new Locale( "it", "" );
		}
		
		// Deutch
		if( language.toLowerCase().equals( "nl" )){
			locale = new Locale( "nl", "" );
		}
		
		// Norway
		if( language.toLowerCase().equals( "no" )){
			locale = new Locale( "no", "" );
		}
		// Swedish gets norwegian hyphenation
		if( language.toLowerCase().equals( "sv" )){
			locale = new Locale( "no", "" );
		}
		
		// Polish
		if( language.toLowerCase().equals( "pl" )){
			locale = new Locale( "pl", "" );
		}
		
		// Portugese
		if( language.toLowerCase().equals( "pt" )){
			locale = new Locale( "pt", "" );
		}
		
		// Russian
		if( language.toLowerCase().equals( "ru" )){
			locale = new Locale( "ru", "" );
		}
		
		// Slovak
		if( language.toLowerCase().equals( "sk" )){
			locale = new Locale( "sk", "" );
		}
		
		return getHyphenationAuto(locale);
	
	}
}
