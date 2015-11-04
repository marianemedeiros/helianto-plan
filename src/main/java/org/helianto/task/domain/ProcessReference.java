/* Copyright 2005 I Serv Consultoria Empresarial Ltda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.helianto.task.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.helianto.document.domain.ProcessDocument;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Data partner or process not yet can be associated directly.
 *
 * @author Mauricio Fernandes de Castro
 */
@Embeddable
public class ProcessReference 
	implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	@JsonBackReference 
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="referenceProcessDocumentId", nullable=true)
    private ProcessDocument processDocument;
   
	@Column(length=32)
	private String processCode = "";
	
	/**
	  * Merger.
	  * 
	  * @param command
  	  **/
   		public ProcessReference merge(ProcessReference command) {
   			setProcessCode(command.getProcessCode());
   			return this;
   		}

    /**
     * Default constructor.
     */
    public ProcessReference() {
    }
    
    /**
     * Constructor of process.
     */
    public ProcessReference(ProcessDocument processDocument) {
    	setProcessDocument(processDocument);
    }
    
    /**
     * Process referenced.
     */
	public ProcessDocument getProcessDocument() {
		return processDocument;
	}
	/**
	 * <<Transient>> True if process referenced is null.
	 */
	@Transient
	public boolean isProcessDocumentNotNull() {
		return (getProcessDocument()!=null);
	}
	public void setProcessDocument(ProcessDocument referenceProcess) {
		this.processDocument = referenceProcess;
	}
	
    /**
     * Code process referenced, or included in referenced process.
     */
	public String getProcessCode() {
    	if (isProcessDocumentNotNull()) {
    		return getProcessDocument().getDocCode();
    	}
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	
}
