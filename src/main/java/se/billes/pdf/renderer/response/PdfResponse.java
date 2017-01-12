package se.billes.pdf.renderer.response;

import se.billes.pdf.firebase.model.FirebaseRequestInput;


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
public class PdfResponse{
	
	private String type;
	private String chainId;
	private Integer triggerIndex;
	private IOutput output;
	private Long executionOfPdfRendering;
	private Long totalTimeOfExecution;
	private FirebaseRequestInput payload;
	
	
	public String getType() {
		return type;
	}

	public IOutput getOutput() {
		return output;
	}
	public void setOutput(IOutput output) {
		this.output = output;
	}

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	public Integer getTriggerIndex() {
		return triggerIndex;
	}

	public void setTriggerIndex(Integer triggerIndex) {
		this.triggerIndex = triggerIndex;
	}

	public Long getExecutionOfPdfRendering() {
		return executionOfPdfRendering;
	}

	public void setExecutionOfPdfRendering(Long executionOfPdfRendering) {
		this.executionOfPdfRendering = executionOfPdfRendering;
	}

	public Long getTotalTimeOfExecution() {
		return totalTimeOfExecution;
	}

	public void setTotalTimeOfExecution(Long totalTimeOfExecution) {
		this.totalTimeOfExecution = totalTimeOfExecution;
	}

	public FirebaseRequestInput getPayload() {
		return payload;
	}

	public void setPayload(FirebaseRequestInput payload) {
		this.payload = payload;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
