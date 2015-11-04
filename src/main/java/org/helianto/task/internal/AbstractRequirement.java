package org.helianto.task.internal;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.helianto.core.domain.Category;
import org.helianto.core.domain.Entity;
import org.helianto.core.internal.AbstractInternalEntity;
import org.helianto.core.internal.InterpretableCategory;
import org.helianto.core.number.Sequenceable;
import org.helianto.task.domain.AbstractFollowUp;
import org.helianto.task.domain.Report;

/**
 * Superclasse para documentação associado a um relatório.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.MappedSuperclass
public abstract class AbstractRequirement 
	extends AbstractInternalEntity
	implements Sequenceable
	, InterpretableCategory {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name="reportId")
    private Report report;
    
    private int sequence;
    
    @Column(length=512)
    private String remark;
    
    @Column(length=255)
    private String parsedContent;
    
    /**
   	+	 * Merger.
   	+	 * 
   	+	 * @param command
   	+	 */
   		public AbstractRequirement merge(AbstractRequirement command) {
   			setSequence(command.getSequence());
   			setRemark(command.getRemark());
   			setParsedContent(command.getParsedContent());
   			return this;
   		}       
    
    
    /* Métodos obtidos do relatório */
    
//    @Transient
    public Category getCategory() {
    	if (getReport()!=null) {
    		return getReport().getCategory();
    	}
    	return null;
    }
    
//    @Transient
    public String getScriptItems() {
    	if (getReport()!=null) {
    		return getReport().getScriptItems();
    	}
    	return "";
    }
    
//    @Transient
    public String[] getScriptItemsAsArray() {
    	if (getReport()!=null) {
    		return getReport().getScriptItemsAsArray();
    	}
    	return new String[0];
    }
    
//    @Transient
    public List<String> getScriptList() {
    	if (getReport()!=null) {
    		return getReport().getScriptList();
    	}
    	return null;
    }
    
    /**
     * Default constructor.
     */
    public AbstractRequirement() {
		this((Entity) null, 0);
	}
    
    /**
     * Key constructor.
     * 
     * @param entity
     * @param internalNumber
     */
    public AbstractRequirement(Entity entity, long internalNumber) {
    	super(entity, internalNumber);
		
	}
    
    /**
     * Report constructor.
     * 
     * @param report
     * @param sequence
     */
    public AbstractRequirement(Report report, int sequence) {
		this(report.getEntity(), 0);
		setReport(report);
		setSequence(sequence);
	}
    
    /**
     * Report that monitors the development.
     */
    public Report getReport() {
		return report;
	}
    public void setReport(Report report) {
		this.report = report;
	}
    
    /**
     * Sequence.
     */
    public int getSequence() {
		return sequence;
	}
    public void setSequence(int sequence) {
		this.sequence = sequence;
	}
    
    /**
     * Remark.
     */
    public String getRemark() {
		return remark;
	}
    public void setRemark(String remark) {
		this.remark = remark;
	}
    
    /**
     * Parsed content.
     */
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("report").append("='").append(getReport()).append("' ");
        buffer.append("sequence").append("='").append(getSequence()).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }

    /**
     * equals
     */
    public boolean equals(Object other) {
          if ( (this == other ) ) return true;
          if ( (other == null ) ) return false;
          if ( !(other instanceof AbstractRequirement) ) return false;
          AbstractRequirement castOther = (AbstractRequirement) other; 
          
          return ((this.getReport()==castOther.getReport()) || ( this.getReport()!=null && castOther.getReport()!=null && this.getReport().equals(castOther.getReport()) ))
        		  && (this.getSequence()==castOther.getSequence());
    }
    
    /**
     * hashCode
     */
    public int hashCode() {
          int result = 17;
          result = 37 * result + ( getReport() == null ? 0 : this.getReport().hashCode() );
          result = 37 * result + getSequence();
          return result;
    }

    
}
