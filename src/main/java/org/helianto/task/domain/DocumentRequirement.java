package org.helianto.task.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.helianto.core.domain.Entity;
import org.helianto.document.domain.Document;
import org.helianto.task.internal.AbstractRequirement;

/**
 * Documentation requirements associated to a report.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name = "plan_requirement"
	, uniqueConstraints = { @UniqueConstraint(columnNames = {"entityId", "internalNumber" }) }
)
public class DocumentRequirement 
	extends AbstractRequirement
	implements Comparable<DocumentRequirement> {

    private static final long serialVersionUID = 1L;
  
    @ManyToOne
    @JoinColumn(name="documentId")
    private Document document;
    
    @Column(length=20)
    private String optionalCode;
    
    @Column(length=64)
    private String optionalName;
    
    @Column(length=128)
    private String actualName;
    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
   		public DocumentRequirement merge(DocumentRequirement command) {
   			super.merge(command);
   			setOptionalCode(command.getOptionalCode());
   			setOptionalName(command.getOptionalName());
   			setActualName(command.getActualName());
   			return this;
   		}
    
    
//    @Transient
    public String getInternalNumberKey() {
    	return "DOCRQMT";
    }
    
//    @Transient
    public int getStartNumber() {
    	return 1;
    }
    
    /* Métodos da própria classe */
    
    /**
     * Default constructor.
     */
    public DocumentRequirement() {
		this((Entity) null, 0);
	}
    
    /**
     * Key constructor.
     * 
     * @param entity
     * @param internalNumber
     */
    public DocumentRequirement(Entity entity, long internalNumber) {
    	super(entity, internalNumber);
		
	}
    
    /**
     * Report constructor.
     * 
     * @param report
     * @param sequence
     */
    public DocumentRequirement(Report report, int sequence) {
		this(report.getEntity(), 0);
		setReport(report);
		setSequence(sequence);
	}
    
    /**
     * Document constructor.
     * 
     * @param document
     */
    public DocumentRequirement(Document document) {
		this(document.getEntity(), 0);
		setDocument(document);
	}
    
    /**
     * Document and report constructor.
     * 
     * @param document
     * @param report
     */
    public DocumentRequirement(Document document, Report report) {
		this(document);
		setReport(report);
	}
        
    /**
     * Associated Document. (can be null).
     */
    public Document getDocument() {
		return document;
	}
    public void setDocument(Document document) {
		this.document = document;
	}
    
    /**
     * <<Transient>> True if have attached document to the request.
     * 
     */
//    @Transient
    public boolean isDocumentEnabled() {
    	return getDocument()!=null;
    }
    
    /**
     * Optional code.
     */
    public String getOptionalCode() {
		return internalOptionalCode();
	}
    public void setOptionalCode(String optionalCode) {
		this.optionalCode = optionalCode;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace an optional code according
     * to conditions of request. 
     * 
     * <p>
     * Default implementation use document code.
     * </p>
     */
//    @Transient
    protected String internalOptionalCode() {
    	if (isDocumentEnabled()) {
    		return getDocument().getDocCode();
    	}
		return optionalCode;
	}
    
    /**
     * Optional name.
     */
    public String getOptionalName() {
		return optionalName;
	}
    public void setOptionalName(String optionalName) {
		this.optionalName = optionalName;
	}
    
    /**
     * True name.
     */
    public String getActualName() {
		return internalActualName();
	}
    public void setActualName(String actualName) {
		this.actualName = actualName;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace the true name according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use document name.
     * </p>
     */
//    @Transient
    protected String internalActualName() {
    	if (isDocumentEnabled()) {
    		return getDocument().getDocName();
    	}
		return actualName;
	}
    
    
    /**
     * Comparator of requests.
     */
    public int compareTo(DocumentRequirement next) {
    	if (getReport()!=null && next.getReport()!=null) {
    		if (getReport().equals(next)) {
        		return (int) (getSequence() - next.getSequence());
    		}
    		return (int) (getReport().getInternalNumber() - next.getReport().getInternalNumber());
    	}
		return this.getId() - next.getId();
	}   

}
