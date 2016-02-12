package se.billes.pdf.renderer.request.factory;

import java.util.Locale;

import com.itextpdf.text.pdf.HyphenationAuto;

import se.billes.pdf.renderer.model.Hyphenation;

/**
 * This program is built on top of iText.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License version 3 as published by the Free Software Foundation with 
 * the addition of the following permission added to Section 15 as permitted in Section 7(a): 
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY ITEXT GROUP NV, ITEXT GROUP 
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of 
 * the GNU Affero General Public License along with this program; if not, 
 * see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc., 51 Franklin Street, 
 * Fifth Floor, Boston, MA, 02110-1301 USA, or download the license from the following
 * URL: http://itextpdf.com/terms-of-use/ 
 * The interactive user interfaces in modified source and object code versions of this program must 
 * display Appropriate Legal Notices, as required under Section 5 of the GNU Affero General Public License.
 * In accordance with Section 7(b) of the GNU Affero General Public License, you must retain the producer line 
 * in every PDF that is created or manipulated using iText. You can be released from the requirements of the 
 * license by purchasing a commercial license. Buying such a license is mandatory as soon as you develop 
 * commercial activities involving the iText software without disclosing the source code of your own 
 * applications. These activities include: offering paid services to customers as an ASP, 
 * serving PDFs on the fly in a web application, shipping iText with a closed source product.
 */
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
