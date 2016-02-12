package se.billes.pdf.renderer.validator;

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
public class DocumentErrorFactory {
	
	private Integer pageIndex;
	private Integer blockIndex;
	private Integer paragraphIndex;
	private Integer phraseIndex;
	private Integer cellIndex;
	
	public DocumentErrorFactory withPageIndex(int index){
		this.pageIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withBlockIndex(int index){
		this.blockIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withParagraphIndex(int index){
		this.paragraphIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withPhraseIndex(int index){
		this.phraseIndex = index;
		return this;
	}
	
	public DocumentErrorFactory withCellIndex(Integer index){
		this.cellIndex = index;
		return this;
	}
	
	public String appendErrorString(  String error ){
		StringBuilder builder = new StringBuilder();
		builder.append( error );
		if( pageIndex != null ){
			builder.append( " at(pageIndex: " + pageIndex );
		}
		if( blockIndex != null ){
			builder.append( ", blockIndex: " + blockIndex );
		}
		
		if( paragraphIndex != null ){
			builder.append( ", paragraphIndex: " + paragraphIndex );
		}
		
		if( cellIndex != null ){
			builder.append( ", cellIndex: " + cellIndex );
		}
		
		
		if( phraseIndex != null ){
			builder.append( ", phraseIndex: " + phraseIndex );
		}
		builder.append(")");
		
		return builder.toString();
	}
}
