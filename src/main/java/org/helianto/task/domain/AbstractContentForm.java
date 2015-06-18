package org.helianto.task.domain;

import org.helianto.core.form.internal.AbstractEventForm;



/**
 * Classe abstrata para 
 * atributos comuns de serviços de conteúdo.
 * 
 * @author Eldevan Nery Jr
 */
@SuppressWarnings("serial")
public abstract class AbstractContentForm  
	extends AbstractEventForm
{

	private String folderCode;
	private int folderId;
	private String docCode;
	private String docName;
	private int contentId;
	private int documentId;
	
	public String getFolderCode() {
		return folderCode;
	}
	public void setFolderCode(String folderCode) {
		this.folderCode = folderCode;
	}
	
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	
	
	
	
	

}
