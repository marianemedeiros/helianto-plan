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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.helianto.core.def.Resolution;
import org.helianto.core.domain.Category;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.internal.AbstractEvent;
import org.helianto.core.internal.InterpretableCategory;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Evaluate to a report.
 * 
 * <p>
 * This class expands behavior of a coupling to give consistency to the close of reports.
 * Through that the field "resolution" of class "report"  should be updated.
 * </p>
 * 
 * <p>
 * This class is sensitive to the necessary of approval flow (workflow) and  retains a level approval
 * of owner during creation. With this datas, a participant that try close a report in a phase
 * before the last, makes he advance to the next phase.
 * </p>
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_review",
    uniqueConstraints = {@UniqueConstraint(columnNames={"reportId", "timeKey"})}
)
public class ReportReview 
	extends AbstractEvent
	implements InterpretableCategory {
	
    private static final long serialVersionUID = 1L;
    
    @JsonBackReference 
    @ManyToOne
    @JoinColumn(name="reportId", nullable=true)
    private Report report;
    
    private long timeKey;
    
    @Column(length=512)
    private String reviewText = "";
    
    private int workflowLevel;
    
    @Column(length=512)
    private String parsedContent = "";
    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
    public ReportReview merge(ReportReview command) {
   			setTimeKey(command.getTimeKey());
   			setReviewText(command.getReviewText());
   			setWorkflowLevel(command.getWorkflowLevel());
   			setParsedContent(command.getParsedContent());
   			return this;
   		}
       

	/** 
	 * Default constructor.
	 */
    public ReportReview() {
    	super();
        setIssueDate(new Date());
        setTimeKey(getIssueDate().getTime());
    }
    
    /** 
     * Key constructor.
     * 
     * @param report
     */
    public ReportReview(Report report) {
    	this();
        setReport(report);
        setResolution(report.getResolution());
    }

    /** 
     * Key constructor.
     * 
     * @param report
     * @param timeKey
     */
    public ReportReview(Report report, long timeKey) {
    	this(report);
        setTimeKey(timeKey);
    }

    /** 
     * Owner constructor.
     * 
     * @param report
     * @param owner
     */
    public ReportReview(Report report, Identity owner) {
    	this(report);
    	setOwner(owner);
    }

//    @Transient
    public Entity getEntity() {
    	if (getReport()!=null) {
    		return getReport().getEntity();
    	}
    	return null;
    }

    /**
     * Report origin.
     */
    public Report getReport() {
    	return this.report;
    }
    public void setReport(Report report) {
    	this.report = report;
    }
    
    /**
     * Automated key.
     */
    public long getTimeKey() {
		return timeKey;
	}
    public void setTimeKey(long timeKey) {
		this.timeKey = timeKey;
	}
    
    /**
     * Evaluation text.
     */
    public String getReviewText() {
		return reviewText;
	}
    public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
    
    /**
     * Workflow level (approval) extracted from participant in the momento of revision.
     */
    public int getWorkflowLevel() {
		return workflowLevel;
	}
    public void setWorkflowLevel(int workflowLevel) {
		this.workflowLevel = workflowLevel;
	}
    
    /**
     * Update resolution along with origin report.
     * 
     * <p>
     * Should consult if this evaluation is the last one, before realize the update. 
     * This can be done with method {@link #isReportResolutionProtected()}
     * </p>
     */
//    @Transient
    public void setReportResolution(char resolution) {
    	if (getReport()!=null) {
    		getReport().setResolution(resolution);
    	}
    	super.setResolution(resolution);
    }
    
    /**
     * Protect against changes the resolution of origin report, if this evaluation
     * don't be the last one.
     * 
     * <p>
     * To knows if it is the last one, this methods test if origin values from 
     * that evaluation are equals. If have many evaluation with the same resolution
     * in different moments of life cycle of origin report, this method does not prevent
     * an evaluation with the same resolution change the origin report.
     * </p>
     */
//    @Transient
    public boolean isReportResolutionProtected() {
    	if (getReport()!=null) {
    		return getReport().getResolution()!=getResolution();
    	}
    	return true;
    }
    
    /**
     * To use safeReportResolution as property in presentation  layer.
     */
//    @Transient
    public char getSafeReportResolution() {
    	if (getReport()!=null) {
    		return getReport().getResolution();
    	}
    	throw new IllegalArgumentException("Must have a report to read this property!");
    }
    
    /**
     * Update resolution together with origin report.
     * @param resolution
     */
//    @Transient
    public void setSafeReportResolution(char resolution) {
    	// Teste 1: o relatório está protegido por outra revisão?
    	if (!isReportResolutionProtected()) {
    		// Teste 2: tem fluxo de aprovação e quer encerrar uma fase?
    		if (getReport().isWorkflowRequired() && resolution==Resolution.DONE.getValue()) {
    			// Teste 3: o usuário pode modificar a resolução do relatório?
    			if (isOwnerAuthorizedToChangeReport()) {
    				// Teste 4: o relatório deve ser fechado?
    				if (getReport().isWorkflowClosable()) {
    					// ok, fechar relatório
    					getReport().close(null);
    				}
    				else {
        				// ao invés de fechar, podemos avançar o relatório para a próxima fase
        				getReport().forward();
    				}
    			}
    		}
    		else {
    			// No caso em que não tem fluxo de aprovação ou não quer encerrar
        		getReport().setResolution(resolution);
    		}
    	}
    	super.setResolution(resolution);
    }
    
    /**
     * True if report is in approval flow phase equal to the owner approval level, or true
     * if report does not request approval flow.
     */
//    @Transient
    protected boolean isOwnerAuthorizedToChangeReport() {
		if (getReport().isWorkflowRequired()) {
			if (getReport().getWorkflowPhase()==getWorkflowLevel()) {
				return true;
			}
			return false;
		}
		return true;
    }

    
    /**
     * Update approval phase of origin report.
     * 
     * @param workflowPhase
     */
//    @Transient
    public void setReportWorkflowPhase(int workflowPhase) {
    	if (getReport()!=null && getReport().isWorkflowRequired()) {
    		getReport().setWorkflowPhase(workflowPhase);
    		// atualiza também o progresso
    		getReport().setComplete((workflowPhase * 100) / (getReport().getWorkflowSize() + 1));
    	}
    }
    
    /**
     * 
     * To use SafeReportProgress as property in presentation  layer.
     */
//    @Transient
    public int getSafeReportProgress() {
    	if (getReport()!=null) {
    		return getReport().getComplete();
    	}
    	throw new IllegalArgumentException("Must have a report to read this property!");
    }
    
    /**
     * Update origin report progress
     * @param complete
     */
//    @Transient
    public void setSafeReportProgress(int complete) {
    	if (!isReportResolutionProtected()) {
    		getReport().setComplete(complete);
    	}
    }
    
//	@Transient
	public Category getCategory() {
		if (getReport()!=null) {
			return getReport().getCategory();
		}
		return null;
	}
	
    /**
     * Parsed Content.
     */
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
	/**
	 * <<Transient>> true when have available category.
	 */
//	@Transient
	protected boolean isCategoryEnabled() {
		return getCategory()!=null && getCategory().getScriptItemsAsArray().length>0;
	}
	
    /**
     * Scripst list, as list CSV of scripts codes.
     * 
     * <p>
     * Scripsts are extracted from category. Just when does not have result, so folder are user
     * to extract scripts.
     * </p>
     */
//    @Transient
    public String getScriptItems() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItems();
		}
		return "";
    }
    
//    @Transient
    public List<String> getScriptList() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptList();
		}
		return null;
    }
    
//    @Transient
    public String[] getScriptItemsAsArray() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItemsAsArray();
		}
		return null;
    }
    
    /**
     * toString
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("report#").append("='").append(getReport().getInternalNumber()).append("' ");
        buffer.append("issueDate").append("='").append(getIssueDate()).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }

    /**
     * equals
     */
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof ReportReview)) return false;
		ReportReview castOther = (ReportReview) other;

		return ((this.getReport() == castOther.getReport()) || (this
				.getReport() != null
				&& castOther.getReport() != null && this.getReport().equals(
				castOther.getReport())))
				&& (this.getTimeKey() == castOther.getTimeKey());
	}

	/**
	 * hashCode
	 */
	public int hashCode() {
		int result = 17;
		result = 37 * result + (int) this.getTimeKey();
		return result;
	}

}
