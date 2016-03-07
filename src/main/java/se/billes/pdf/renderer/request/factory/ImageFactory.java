package se.billes.pdf.renderer.request.factory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.billes.pdf.renderer.model.ImageInstance;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

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
public class ImageFactory {
	
	private static List<ImageInstance> instances = new ArrayList<ImageInstance>();
	
	public ImageInstance getImageByFile( PdfContentByte cb , File file ) throws IOException, BadElementException{
		Image image = null;
		ImageInstance instance = null;
		if( file.getName().toLowerCase().endsWith( ".pdf")){	
			PdfReader reader = new PdfReader( file.getAbsolutePath() );
			PdfImportedPage p = cb.getPdfWriter().getImportedPage(reader, 1);
			image = Image.getInstance(p);
			instance = new ImageInstance(image, reader);
		}else{
			image = Image.getInstance( file.getAbsolutePath() );
			instance = new ImageInstance(image, null);
		}
		
		instances.add(instance);
		

		return instance;
	}
	
	public static List<ImageInstance> getInstances() {
		return instances;
	}

	public boolean isPdf( File file ){
		if( file.getName().toLowerCase().endsWith( ".pdf")){	
			return true;
		}
		
		return false;
	}
	
	public static void clear(){
		Iterator<ImageInstance> iter = instances.iterator();
		while( iter.hasNext() ){
			ImageInstance instance = iter.next();
			try{
				if( instance.getPdfReader() != null ){
					instance.getPdfReader().close();
					
				}
			}catch( Exception e ){}
			iter.remove();
		}

	}
	
}
