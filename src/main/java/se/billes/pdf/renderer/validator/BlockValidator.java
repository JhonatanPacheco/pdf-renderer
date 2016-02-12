package se.billes.pdf.renderer.validator;

import com.itextpdf.text.BaseColor;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.alignment.VerticalAlign;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;

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
public class BlockValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public BlockValidator withPageIndex( int index ){
		this.pageIndex = index;
		return this;
	}
	
	public BlockValidator withBlockIndex( int index ){
		this.blockIndex = index;
		return this;
	}
	
	public void validate(PdfRequest request,Block block) throws PdfRequestNotValidException {
		
	
		DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageIndex).withBlockIndex(blockIndex);
		
		if( block.getBackgroundRef()  != null ){
			BaseColor color = new ColorFactory().getBaseColorByRef(request, block.getBackgroundRef());
			if( color == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find backgroundRef color for block" ) );
			}
			block.setBaseColor(color);
		}
		
		if( block.getBorder() != null ){
			if( block.getBorder().getThickness() > 0 ){
				if( block.getBorder().getColorRef() == null ){
					block.getBorder().setBaseColor( new ColorFactory().getBlack());
				}else{
					BaseColor color = new ColorFactory().getBaseColorByRef(request, block.getBorder().getColorRef());
					if( color == null ){
						throw new PdfRequestNotValidException( errorFactory.appendErrorString("Could not find colorRef for border" ) );
					}
					block.getBorder().setBaseColor(color);
				}
			}
		}
		
		if( block.getMargins() != null ){
			if( block.getMargins().length != 4 ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString("Incorrect margins. Must be [float,float,float,float]" ));
			}
		}
		
		if( block.getVerticalAlign() == null ){
			throw new PdfRequestNotValidException(  errorFactory.appendErrorString("Incorrect vertical align. Must be any of[top,middle,bottom]" ));
		}else{
			VerticalAlign align = VerticalAlign.getByName(block.getVerticalAlign() );
			if( align == null ){
				throw new PdfRequestNotValidException(  errorFactory.appendErrorString("Incorrect vertical align. Must be any of[top,middle,bottom]"));
			}
		}

		new ParagraphValidator()
		.withBlockIndex(blockIndex)
		.withPageIndex(pageIndex)
		.validate(request, block);
	}
}
