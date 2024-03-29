package se.billes.pdf.renderer.validator;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.request.PdfDocument;
import se.billes.pdf.renderer.request.PdfRequest;

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
 *
 * Validates the Json request. The order of the validators are
 * important. The ColorValidator needs to be in place before the block validator
 *
 */
public class PdfRequestValidator {
	 
	List<IPdfRequestValidatable> validatables = new ArrayList<IPdfRequestValidatable>();
	@Inject SizeValidator sizeValidator;
	@Inject PathValidator pathValidator;
	@Inject PageValidator pageValidator;
	@Inject FontValidator fontValidator;
	@Inject BaseElementValidator baseElementValidator;
	
	public void validateAll(PdfRequest request) throws PdfRequestNotValidException{
		
		if( request == null || request.getDocument() == null ){
			throw new PdfRequestNotValidException( "Request or document can not be null" );
		}
		
		validatables.add( pathValidator );
		validatables.add( sizeValidator);
		validatables.add( new NameValidator() );
		validatables.add( new HyphenationValidator() );
		validatables.add( pageValidator );
		validatables.add( new ColorValidator() );
		validatables.add( fontValidator );
		validatables.add( baseElementValidator );
		
		for( IPdfRequestValidatable validatable : validatables ){
			validatable.validate(request);
		}
		
		PdfDocument document = request.getDocument();
		for( Page page : document.getPages() ){
			page.setPdfDocument(document);
			if( page.getBlocks() != null ){
				for( BaseElement block : page.getBlocks() ){
					block.setPage(page);
				}
			}
		}
		
	}
}
