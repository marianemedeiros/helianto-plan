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
 * Um participante.
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
    
    private char assignmentType;
    
    @DateTimeFormat(style="S-")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;
    
    private int workflowLevel;

    /** 
     * Construtor vazio. 
     */
    public AbstractParticipant() {
    	setAssignmentTypeAsEnum(Assignment.PARTICIPANT);
    	setJoinDate(new Date());
    }

    /** 
     * Chave primária. 
     */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
   
    public int getVersion() {
		return version;
	}
    public void setVersion(int version) {
		this.version = version;
	}

    /**
     * Identidade do participante.
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
     * <<Transient>> Permite que as subclasses modifiquem a forma pela qual a identidade é obtida.
     */
    protected Identity getInternalIdentity() {
    	return this.identity;
    }
    
    /**
     * Conveniente para recuperar o apelido do participante.
     */
    public String getParticipantAlias() {
    	if (getIdentity()!=null) {
    		return getIdentity().getDisplayName();
    	}
    	return "?";
    }

    /**
     * Conveniente para recuperar o nome do participante.
     */
    public String getParticipantName() {
    	if (getIdentity()!=null) {
    		return getIdentity().getIdentityName();
    	}
    	return "?";
    }

    /**
     * Conveniente para recuperar a foto do participante.
     */
    public byte[] getParticipantPhoto() {
    	if (getIdentity()!=null) {
    		return getIdentity().getPhoto();
    	}
    	return new byte[0];
    }

    /**
     * Tipo da atribuição.
     * @see Assignment
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
     * <<Transient>> Permite que as subclasses modifiquem a forma pela qual o tipo de atribuição é obtido.
     */
    protected char getInternalAssignmentType() {
    	return this.assignmentType;
    }
    
    /**
     * Data em que a atribuição foi reconhecida pelo participante.
     */
    public Date getJoinDate() {
        return getInternalJoinDate();
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    
    /**
     * <<Transient>> Permite que as subclasses modifiquem a forma pela qual a data de atribuição é obtida.
     */
    protected Date getInternalJoinDate() {
    	return this.joinDate;
    }
    
    /**
     * Nível de workflow do participante.
     */
    public int getWorkflowLevel() {
		return getInternalWorkflowLevel();
	}
    public void setWorkflowLevel(int workflowLevel) {
		this.workflowLevel = workflowLevel;
	}
    
    /**
     * <<Transient>> Permite que as subclasses modifiquem a forma pela qual o nível de workflow é obtido.
     */
    protected int getInternalWorkflowLevel() {
    	return this.workflowLevel;
    }
    
    /**
     * <<Transient>> Determina se um indivéduo participa do workflow.
     */
    public boolean isWithinWorkflow() {
    	return getWorkflowLevel() >0;
    }

    /**
     * Comparador de participantes.
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

}
