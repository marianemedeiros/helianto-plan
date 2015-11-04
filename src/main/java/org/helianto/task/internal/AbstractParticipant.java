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

package org.helianto.task.internal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.helianto.core.domain.Identity;
import org.helianto.task.def.Assignment;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Team base class.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.MappedSuperclass
public class AbstractParticipant 
	implements Serializable, Comparable<AbstractParticipant> 
{
	
    private static final long serialVersionUID = 1L;
    
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Version
    private int version;
    
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="identityId", nullable=true)
    private Identity identity;
    
    @Transient
    private Integer identityId;
    
    @Transient
    private String identityFirstName;
    
    @Transient
    private String identityLastName;
    
    @Transient
    private String identityDisplayName;
    
    @Transient
    private String identityImageUrl;
    
    @Transient
    private char identityGender;
    
    /**
     * @deprecated
     */
    private char assignmentType = Assignment.PARTICIPANT.getValue();
    
    @DateTimeFormat(style="S-")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;
    
    private int workflowLevel;

    /** 
     * Empty constructor. 
     */
    public AbstractParticipant() {
    	setJoinDate(new Date());
    }

    /** 
     * Primary key. 
     */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Consistency check.
     */
    public int getVersion() {
		return version;
	}
    public void setVersion(int version) {
		this.version = version;
	}

    /**
     * Identity.
     */
    public Identity getIdentity() {
        return getInternalIdentity();
    }
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
    
    /**
     * <<Transient>> identity id.
     */
    public Integer getIdentityId() {
		return identityId;
	}
    public void setIdentityId(Integer identityId) {
		this.identityId = identityId;
	}
    
    /**
     * <<Transient>> Allow subclass modify the form of the identity is obtained. 
     */
    protected Identity getInternalIdentity() {
    	return this.identity;
    }
    
    /**
     * Recovery the nickname of participant.
     */
    public String getParticipantAlias() {
    	if (getIdentity()!=null) {
    		return getIdentity().getDisplayName();
    	}
    	return "?";
    }

    /**
     * Recovery the name of participant.
     */
    public String getParticipantName() {
    	if (getIdentity()!=null) {
    		return getIdentity().getIdentityName();
    	}
    	return "?";
    }
    
    public String getIdentityFirstName() {
		return identityFirstName;
	}
    public void setIdentityFirstName(String identityFirstName) {
		this.identityFirstName = identityFirstName;
	}
    
    public String getIdentityLastName() {
		return identityLastName;
	}
    public void setIdentityLastName(String identityLastName) {
		this.identityLastName = identityLastName;
	}
    
    public String getIdentityName() {
    	return identityFirstName + " " + identityLastName;
    }
    
    public String getIdentityDisplayName() {
		return identityDisplayName;
	}
    public void setIdentityDisplayName(String identityDisplayName) {
		this.identityDisplayName = identityDisplayName;
	}

    public String getIdentityImageUrl() {
		return identityImageUrl;
	}
    public void setIdentityImageUrl(String identityImageUrl) {
		this.identityImageUrl = identityImageUrl;
	}
    
    public char getIdentityGender() {
		return identityGender;
	}
    public void setIdentityGender(char identityGender) {
		this.identityGender = identityGender;
	}
    
    /**
     * @deprecated
     */
    public char getAssignmentType() {
        return getInternalAssignmentType();
    }
    public void setAssignmentType(char assignmentType) {
        this.assignmentType = assignmentType;
    }
    public void setAssignmentTypeAsEnum(Assignment assignment) {
        this.assignmentType = assignment.getValue();
    }

    /**
     * <<Transient>> 
     * Allow sublcass change the way of type of attribution is obtained.
     */
    protected char getInternalAssignmentType() {
    	return this.assignmentType;
    }
    
    /**
     * Join date.
     */
    public Date getJoinDate() {
        return getInternalJoinDate();
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    
    /**
     * <<Transient>> Allow subclass change de way of attribution data is obtained. 
     */
    protected Date getInternalJoinDate() {
    	return this.joinDate;
    }
    
    /**
     * Workflow level of participant.
     */
    public int getWorkflowLevel() {
		return getInternalWorkflowLevel();
	}
    public void setWorkflowLevel(int workflowLevel) {
		this.workflowLevel = workflowLevel;
	}
    
    /**
     * <<Transient>> Allow subclass change the way of workflow is obtained. 
     */
    protected int getInternalWorkflowLevel() {
    	return this.workflowLevel;
    }
    
    /**
     * <<Transient>> Determine if an individual participates of workflow.
     */
    public boolean isWithinWorkflow() {
    	return getWorkflowLevel() >0;
    }

    /**
     * Compare of participants.
     */
    public int compareTo(AbstractParticipant next) {
    	if (getIdentity()!=null && next.getIdentity()!=null) {
    		return getIdentity().getDisplayName().compareTo(next.getIdentity().getDisplayName());
    	}
		return this.getId() - next.getId();
	}   

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("identity").append("='").append(getIdentity()).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }
    
    /**
     * Merger.
     * 
     * @param command
     */
	protected void merge(AbstractParticipant command) {
		setAssignmentType(command.getAssignmentType());
		setJoinDate(command.getJoinDate());
		setWorkflowLevel(command.getWorkflowLevel());
	}

}
