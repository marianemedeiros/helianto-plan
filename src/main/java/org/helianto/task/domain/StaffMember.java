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

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.helianto.core.domain.Identity;
import org.helianto.task.def.ReportStaffAssignment;
import org.helianto.task.def.ReportStaffGrade;
import org.helianto.task.def.ReportStaffRole;
import org.helianto.task.internal.AbstractParticipant;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Team (or staff) assigned to a project.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_staff",
    uniqueConstraints = {@UniqueConstraint(columnNames={"reportFolderId", "identityId"})}
)
public class StaffMember 
	extends AbstractParticipant 
{
	
    private static final long serialVersionUID = 1L;
  
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="reportFolderId", nullable=true)
    private ReportFolder reportFolder;
    
	@Transient
    private Integer reportFolderId;
    
	@Transient
    private String reportFolderCode;
    
	@Transient
    private String reportFolderName;
    
	@Transient
    private Integer partnerId;
    
	@Transient
    private Date startDate;
    
	@Transient
    private Date endDate;
    
	/**
	 * @deprecated
	 */
    private char memberGrade = ReportStaffGrade.STARTER.getValue();
    
    @Column(length=12)
    @Enumerated(EnumType.STRING)
    private ReportStaffGrade staffGrade = ReportStaffGrade.STARTER;
    
	/**
	 * @deprecated
	 */
    private char memberGroup = ReportStaffAssignment.VIEWER.getValue();
    
    @Column(length=12)
    @Enumerated(EnumType.STRING)
    private ReportStaffRole staffRole = ReportStaffRole.TEAM;    

    /** 
     * Default constructor.
     */
    public StaffMember() {
    	super();
    	setMemberGrade('1');
    }

    /** 
     * Form constructor.
     * 
     * @param reportFolder
     */
    public StaffMember(ReportFolder reportFolder) {
    	this();
    	setReportFolder(reportFolder);
    }

    /** 
     * Key constructor.
     * 
     * @param report
     * @param identity
     */
    public StaffMember(ReportFolder report, Identity identity) {
    	this(report);
    	setIdentity(identity);
    }
    
    /**
     * Form constructor.
     * 
     * @param id
     * @param reportFolderId
     * @param folderCode
     * @param folderName
     * @param identityId
     * @param partnerId
     * @param startDate
     * @param endDate
     */
    public StaffMember(int id
    		, int reportFolderId
    		, String folderCode
    		, String folderName
    		, int identityId
    		, Integer partnerId
    		, Date startDate
    		, Date endDate
    		) {
		super();
		setId(id);
		setReportFolderId(reportFolderId);
		setReportFolderCode(folderCode);
		setReportFolderName(folderName);
		setIdentityId(identityId);
		setPartnerId(partnerId!=null ? partnerId: 0);
		setStartDate(startDate);
		setEndDate(endDate);
	}

    /**
     * Form constructor.
     * 
     * @param id
     * @param reportFolderId
     * @param identityId
     * @param identityDisplayName
     * @param identityFirstName
     * @param identityLastName
     * @param identityImageUrl
     * @param identityGender
     * @param partnerId
     * @param startDate
     * @param endDate
     * @param joinDate
     * @param staffGrade
     * @param staffRole
     */
    public StaffMember(int id
    		, int reportFolderId
    		, int identityId
    		, String identityDisplayName
    		, String identityFirstName
    		, String identityLastName
    		, String identityImageUrl
    		, Character identityGender
    		, Integer partnerId
    		, Date startDate
    		, Date endDate
    		, Date joinDate
    		, ReportStaffGrade staffGrade
    		, ReportStaffRole staffRole
    		) {
		super();
		setId(id);
		setReportFolderId(reportFolderId);
		setIdentityId(identityId);
		setIdentityDisplayName(identityDisplayName);
		setIdentityFirstName(identityFirstName);
		setIdentityLastName(identityLastName);
		setIdentityImageUrl(identityImageUrl);
		setIdentityGender(identityGender!=null ? identityGender:'N');
		setPartnerId(partnerId!=null ? partnerId: 0);
		setStartDate(startDate);
		setEndDate(endDate);
		setJoinDate(joinDate);
		setStaffGrade(staffGrade);
		setStaffRole(staffRole);
	}

	/**
     * Report folder (or project).
     */
    public ReportFolder getReportFolder() {
        return this.reportFolder;
    }
    public void setReportFolder(ReportFolder reportFolder) {
        this.reportFolder = reportFolder;
    }
    
    /**
     * <<Transient>> report folder id.
     */
    public Integer getReportFolderId() {
		return reportFolderId;
	}
    public void setReportFolderId(Integer reportFolderId) {
		this.reportFolderId = reportFolderId;
	}
    
    /**
     * <<Transient>> report folder code.
     */
    public String getReportFolderCode() {
		return reportFolderCode;
	}
    public void setReportFolderCode(String reportFolderCode) {
		this.reportFolderCode = reportFolderCode;
	}
    
    /**
     * <<Transient>> report folder name.
     */
    public String getReportFolderName() {
		return reportFolderName;
	}
    public void setReportFolderName(String reportFolderName) {
		this.reportFolderName = reportFolderName;
	}
    
    /**
     * <<Transient>> report folder partner id.
     */
    public Integer getPartnerId() {
		return partnerId;
	}
    public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
    
    /**
     * <<Transient>> report folder start date.
     */
    public Date getStartDate() {
		return startDate;
	}
    public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    
    /**
     * <<Transient>> report folder end date.
     */
    public Date getEndDate() {
		return endDate;
	}
    public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
    /**
     * @deprecated
     */
    public char getMemberGrade() {
		return memberGrade;
	}
    public void setMemberGrade(char memberGrade) {
		this.memberGrade = memberGrade;
	}
    
    /**
     * Assignee level of knowledge at the time of assignment.
     */
    public ReportStaffGrade getStaffGrade() {
		return staffGrade;
	}
    public void setStaffGrade(ReportStaffGrade staffGrade) {
		this.staffGrade = staffGrade;
	}
    
    /**
     * @deprecated
     */
    public char getMemberGroup() {
		return memberGroup;
	}
    public void setMemberGroup(char memberGroup) {
		this.memberGroup = memberGroup;
	}
    
    /**
     * Assignee role.
     */
    public ReportStaffRole getStaffRole() {
		return staffRole;
	}
    public void setStaffRole(ReportStaffRole staffRole) {
		this.staffRole = staffRole;
	}

   /**
    * equals
    */
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
         if ( (other == null ) ) return false;
         if ( !(other instanceof StaffMember) ) return false;
         StaffMember castOther = (StaffMember) other; 
         
         return ((this.getReportFolder()==castOther.getReportFolder()) || ( this.getReportFolder()!=null && castOther.getReportFolder()!=null && this.getReportFolder().equals(castOther.getReportFolder()) ))
             && ((this.getIdentity()==castOther.getIdentity()) || ( this.getIdentity()!=null && castOther.getIdentity()!=null && this.getIdentity().equals(castOther.getIdentity()) ));
   }
   
   /**
    * hashCode
    */
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getReportFolder() == null ? 0 : this.getReportFolder().hashCode() );
         result = 37 * result + ( getIdentity() == null ? 0 : this.getIdentity().hashCode() );
         return result;
   }

	@Override
	public String toString() {
		return "StaffMember [reportFolderId=" + reportFolderId + ", partnerId=" + partnerId + ", memberGrade=" + memberGrade
				+ ", memberGroup=" + memberGroup + ", getIdentityId()=" + getIdentityId() + "]";
	}
	
	/**
	 * Merger.
	 * 
	 * @param command
	 */
	public StaffMember merge(StaffMember command) {
		super.merge(command);
		setStartDate(command.getStartDate());
		setEndDate(command.getEndDate());
		setStaffGrade(command.getStaffGrade());
		setStaffRole(command.getStaffRole());
		return this;
	}

}
