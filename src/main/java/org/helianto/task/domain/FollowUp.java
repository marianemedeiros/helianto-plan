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

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.number.Sequenceable;
import org.helianto.task.def.FollowUpAction;
import org.helianto.task.def.NotificationOption;
import org.helianto.task.def.ReviewDecision;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 	Accompaniment to a report.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_followup",
    uniqueConstraints = {@UniqueConstraint(columnNames={"taskId", "internalNumber"})}
)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.CHAR
)
@DiscriminatorValue("F")
public class FollowUp 
	extends AbstractFollowUp 
	implements 
	  Sequenceable {
	
	/**
	 * Expose discriminator.
	 */
//	@Transient
	public char getDiscriminatorValue() {
		return 'F';
	}
	
    private static final long serialVersionUID = 1L;
   
    @JsonBackReference 
    @ManyToOne
    @JoinColumn(name="taskId", nullable=true)
    private Report report;
    
    private int duration = 30;
    
    private char followUpAction = FollowUpAction.ACTION_PLAN.getValue();
    
    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
   		public FollowUp merge(FollowUp command) {
   			super.merge(command);
   			setReport(command.getReport());
   			setDuration(command.getDuration());
   			setFollowUpAction(command.getFollowUpAction());
   			return this;
   		}

	/** 
	 * Default constructor.
	 */
    public FollowUp() {
    	super();
    	setDecisionAsEnum(ReviewDecision.START);
        setNotificationOption(NotificationOption.ALL.getValue());
        setIssueDate(new Date());
        setInternalNumber(getIssueDate().getTime());
    }
    
    /** 
     * Key constructor.
     * 
     * @param report
     * @param internalNumber
     */
    public FollowUp(Report report, long internalNumber) {
    	this();
        setReport(report);
        setInternalNumber(internalNumber);
        setResolution(report.getResolution());
        setComplete(report.getComplete());
    }

    /** 
     * Owner constructor.
     * 
     * @param report
     * @param owner
     */
    public FollowUp(Report report, Identity owner) {
    	this(report, 0);
    	setOwner(owner);
    }

    /** 
     * Child constructor.
     * 
     * @param parent
     */
    public FollowUp(FollowUp parent) {
    	this(parent.getReport(), 0);
        setComplete(parent.getTask().getComplete());
    }

    /** 
     * Child owner constructor.
     * 
     * @param parent
     * @param owner
     */
    public FollowUp(FollowUp parent, Identity owner) {
    	this(parent);
    	setOwner(owner);
    }

//    @Transient
    public Entity getEntity() {
    	if (getTask()!=null) {
    		return getTask().getEntity();
    	}
    	return null;
    }

//    @Transient
    @Override
    public String getInternalNumberKey() {
    	return "FOLLOWUP";
    }
    
//    @Transient
    public int getStartNumber() {
    	return 1;
    }

	/**
	 * True if evaluation can be accept.
	 */
//	@Transient
	public boolean isValid() {
//		if (getOwner().equals(getReport().getOwner()) || getOwner().equals(getReport().getReporter())) {
//			return true;
//		}
		return true;
	}
	
//    @Transient
    public String getSummary() {
    	if (getFollowUpDesc()==null) {
    		if (getTask()!=null && getTask().getSummary()!=null) {
    			return getTask().getSummary();
    		}
    		return "";
    	}
    	String followUpDesc = getFollowUpDesc();
    	int len = followUpDesc.length();
    	if (len>100) {
    		followUpDesc = followUpDesc.substring(0, 100);
    	}
		return followUpDesc.replaceAll("\\<.*?\\>", "").replaceAll("\n", " ").replaceAll("\t", " ");
	}

    /**
     * Origin plan.
     */
//    @Transient
    public Report getTask() {
        return this.report;
    }
    public void setTask(Report report) {
        this.report = report;
    }

    /**
     * Origin plan.
     */
    
    public Report getReport() {
        return getTask();
    }
    public void setReport(Report report) {
        setTask(report);
    }

//    @Transient
    public Report getSubject() {
        return getReport();
    }
    
    /**
     * Duration (minutes).
     */
    public int getDuration() {
    	return duration;
    }
    public void setDuration(int duration) {
    	this.duration = duration;
    }   

    /**
     * Action follow up action.
     */
    public char getFollowUpAction() {
		return followUpAction;
	}
	public void setFollowUpAction(char followUpAction) {
		this.followUpAction = followUpAction;
	}
	public void setFollowUpActionAsEnum(FollowUpAction followUpAction) {
		this.followUpAction = followUpAction.getValue();
	}

    /**
     * Last <code>FollowUp</code> query segment.
     */
//    @Transient
    public static String getFollowUpLastQuerySegmentString() {
    	StringBuilder querySegment = new StringBuilder();
        return querySegment
            .append("followUp.followUpDate = (")
                .append("select max(f.followUpDate) as lastFollowUpDate ")
                .append("from FollowUp f ")
                .append("where f.plan = followUp.plan ")
                .append("group by f.plan ")
            .append(") ").toString();
    }

    /**
     * toString
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("task#").append("='").append(getTask().getInternalNumber()).append("' ");
        buffer.append("issueDate").append("='").append(getIssueDate()).append("' ");
        buffer.append("followUpAction").append("='").append(getFollowUpAction()).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }

    /**
     * equals
     */
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof FollowUp)) return false;
		FollowUp castOther = (FollowUp) other;

		return ((this.getTask() == castOther.getTask()) || (this
				.getTask() != null
				&& castOther.getTask() != null && this.getTask().equals(
				castOther.getTask())))
				&& (this.getInternalNumber() == castOther.getInternalNumber());
	}

	/**
	 * hashCode
	 */
	public int hashCode() {
		int result = 17;
		result = 37 * result + (int) this.getInternalNumber();
		return result;
	}

}
