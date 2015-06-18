package org.helianto.task.domain;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.helianto.core.def.Uploadable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Report result content, if any.
 * 
 * @author Mauricio Fernandes de Castro
 */
@Embeddable
public class UploadableContent 
	implements 
	  Serializable
	, Uploadable 
{

	private static final long serialVersionUID = 1L;
	
	@Lob
	private byte[] content;
	
	@Column(length=32)
    private String encoding;
	
	@Column(length=32)
    private String multipartFileContentType;
	
    @Transient
    private transient MultipartFile file;
   
    /**
     * Default constructor.
     */
    public UploadableContent() {
		setContent("");
		setEncoding("iso_88591");
    	setMultipartFileContentType("text/html");
	}
    
    public byte[] getContent() {
        return this.content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
    @JsonIgnore
    public void setContent(String content) {
    	this.content = content.getBytes();
    }
    
    /**
     * Helper method to get text content as String.
     */
//    @Transient
    public String getContentAsString() {
    	if (getContent()!=null && isText()) {
    		return new String(getContent());
    	}
    	return "";
    }
    public void setContentAsString(String contentAsString) {
		setContent(contentAsString);
	}
    
//    @Transient
    public int getContentSize() {
    	if (getContent()!=null) {
    		return getContent().length;
    	}
    	return 0;
    }
    
	public String getEncoding() {
		return this.encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getMultipartFileContentType() {
		return multipartFileContentType;
	}
	public void setMultipartFileContentType(String multipartFileContentType) {
		this.multipartFileContentType = multipartFileContentType;
	}
	
    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "text".
     */
//    @Transient
    public boolean isText() {
    	if (getMultipartFileContentType().startsWith("text")) {
    		return true;
    	}
    	return false;
    }

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "text/html".
     */
//    @Transient
    public boolean isHtml() {
    	if (getMultipartFileContentType().startsWith("text/html")) {
    		return true;
    	}
    	return false;
    }

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "image".
     */
//    @Transient
    public boolean isImage() {
    	if (getMultipartFileContentType().startsWith("image")) {
    		return true;
    	}
    	return false;
    }

	/**
	 * <<Transient>> Convenience property to hold uploaded data.
	 */
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	/**
	 * <<Transient>> Convenience method to read uploaded data.
	 */
//	@Transient
	public void processFile() throws IOException {
		setContent(file.getBytes());
		setMultipartFileContentType(file.getContentType());
	}
	
}
