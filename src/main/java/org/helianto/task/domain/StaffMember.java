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

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.helianto.core.domain.Identity;
import org.helianto.task.def.ReportStaffAssignment;
import org.helianto.task.def.ReportStaffGrade;
import org.helianto.task.internal.AbstractParticipant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Membros de uma equipe.
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
    
    private char memberGrade = ReportStaffGrade.STARTER.getValue();
    
    private char memberGroup = ReportStaffAssignment.VIEWER.getValue();

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
     * Pasta à qual os membros pertencem.
     */
    public ReportFolder getReportFolder() {
        return this.reportFolder;
    }
    public void setReportFolder(ReportFolder reportFolder) {
        this.reportFolder = reportFolder;
    }
    
    /**
     * Grau de conhecimento com o qual participante contribui para o projeto.
     */
    public char getMemberGrade() {
		return memberGrade;
	}
    public void setMemberGrade(char memberGrade) {
		this.memberGrade = memberGrade;
	}
    public void setMemberGradeAsEnum(ReportStaffGrade reportStaffGrade) {
		this.memberGrade = reportStaffGrade.getValue();
	}
    
    /**
     * Literal usado na atribuição da equipe.
     */
    public char getMemberGroup() {
		return memberGroup;
	}
    public void setMemberGroup(char memberGroup) {
		this.memberGroup = memberGroup;
	}
    public void setMemberGroupAsEnum(ReportStaffAssignment reportStaffAssignment) {
		this.memberGrade = reportStaffAssignment.getValue();
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

}
