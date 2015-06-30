package org.helianto.task.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.helianto.document.domain.Document;
import org.helianto.user.domain.User;

/**
 * Report knowledge level.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="plan_knowledge",
    uniqueConstraints = {@UniqueConstraint(columnNames={"documentRequirementId", "userRequirementId"})}
)
public class ReportKnowledge 
	implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Version
	private Integer version;
	
	@ManyToOne
    @JoinColumn(name="documentRequirementId", nullable=true)
	private DocumentRequirement documentRequirement;
	
	@ManyToOne
	@JoinColumn(name="userRequirementId", nullable=true)
	private UserRequirement userRequirement;
	
    private char knowledgeLevel;
    
    /**
     * Default constructor.
     */
    public ReportKnowledge() {
    	super();
		setKnowledgeLevel('0');
	}
	
    /**
     * Key constructor.
     * 
     * @param documentRequirement
     * @param userRequirement
     */
    public ReportKnowledge(DocumentRequirement documentRequirement, UserRequirement userRequirement) {
		this();
		setDocumentRequirement(documentRequirement);
		setUserRequirement(userRequirement);
	}
	
	
	/**
     * Solicitação de documento.
     */
	public DocumentRequirement getDocumentRequirement() {
		return documentRequirement;
	}
	public void setDocumentRequirement(DocumentRequirement documentRequirement) {
		this.documentRequirement = documentRequirement;
	}
	
	/**
	 * <<Transient>> Conveniente para recuperar o documento.
	 */
	@Transient
	public Document getDocument() {
		if (getDocumentRequirement()!=null) {
			return getDocumentRequirement().getDocument();
		}
		return null;
	}
	
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getVersion() {
        return this.version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    
	/**
     * User requirement.
     */
	public UserRequirement getUserRequirement() {
		return userRequirement;
	}
	public void setUserRequirement(UserRequirement userRequirement) {
		this.userRequirement = userRequirement;
	}
	
	/**
	 * <<Transient>> helper method to retrieve user.
	 */
	@Transient
	public User getUser() {
		if (getUserRequirement()!=null) {
			return getUserRequirement().getUser();
		}
		return null;
	}
	
	/**
	 * <<Transient>> helper method to retrieve report.
	 */
	@Transient
	public Report getReport() {
		if (getUserRequirement()!=null) {
			return getUserRequirement().getReport();
		}
		return null;
	}
	
	/**
	 * Knowledge level.
	 */
	public char getKnowledgeLevel() {
		return knowledgeLevel;
	}
	public void setKnowledgeLevel(char knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((documentRequirement == null) ? 0 : documentRequirement
						.hashCode());
		result = prime * result
				+ ((userRequirement == null) ? 0 : userRequirement.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ReportKnowledge)) {
			return false;
		}
		ReportKnowledge other = (ReportKnowledge) obj;
		if (documentRequirement == null) {
			if (other.documentRequirement != null) {
				return false;
			}
		} else if (!documentRequirement.equals(other.documentRequirement)) {
			return false;
		}
		if (userRequirement == null) {
			if (other.userRequirement != null) {
				return false;
			}
		} else if (!userRequirement.equals(other.userRequirement)) {
			return false;
		}
		return true;
	}
}
