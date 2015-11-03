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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.helianto.core.SimpleStateResolver;
import org.helianto.core.StateResolver;
import org.helianto.core.Verifiable;
import org.helianto.document.Plan;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Associa fases de projeto a pastas.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_phase",
    uniqueConstraints = {@UniqueConstraint(columnNames={"reportFolderId", "literal"})}
)
public class ReportPhase 
	implements 
	  Plan
	, Verifiable
	, Comparable<ReportPhase> 
{

    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="reportFolderId", nullable=true)
    private ReportFolder reportFolder;
    
    @Column
    private char literal;
    
    @Column(length=64)
    private String phaseName;
    
    @Lob
    private byte[] content;
    
    @Column(length=32)
    private String encoding;
    
    private int estimate;
    
    @DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledStartDate;
    
    @DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledEndDate;

    /**
   	 * Merger.
   	 * 
   	 * @param command
   	 **/
    public ReportPhase merge(ReportPhase command) {
   			setLiteral(command.getLiteral());
   			setPhaseName(command.getPhaseName());
   			setContent(command.getContent());
   			setEncoding(command.getEncoding());
   			setEstimate(command.getEstimate());
   			setScheduledStartDate(command.getScheduledStartDate());
   			setScheduledEndDate(command.getScheduledEndDate());
   			return this;
   		}
       
    
    /** 
     * Default constructor.
     */
    public ReportPhase() {
    	setLiteral('0');
    	setScheduledStartDate(new Date());
    	setScheduledEndDate(getScheduledStartDate());
     }
    
    /**
     * Form constructor.
     * 
     * @param reportFolder
     */
    public ReportPhase(ReportFolder reportFolder) {
    	this();
    	setReportFolder(reportFolder);
    }
    
    /**
     * Key constructor.
     * 
     * @param reportFolder
     * @param literal
     */
    public ReportPhase(ReportFolder reportFolder, char literal) {
    	this(reportFolder);
    	setLiteral(literal);
    }
    
    /**
     * Primary key
     */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Report folder.
     */
    public ReportFolder getReportFolder() {
		return reportFolder;
	}
    public void setReportFolder(ReportFolder reportFolder) {
		this.reportFolder = reportFolder;
	}
    
    /**
     * Conveniente para recuperar o código da pasta.
     */
//    @Transient
    public String getBuilderCode() {
    	if (getReportFolder()!=null) {
    		return getReportFolder().getFolderCode();
    	}
    	return "?";
    }

    /**
     * Conveniente para recuperar o nome da pasta.
     */
//    @Transient
    public String getBuilderName() {
    	if (getReportFolder()!=null) {
    		return getReportFolder().getFolderName();
    	}
    	return "?";
    }

    /**
     * Letra que representa o requisito.
     */
    public char getLiteral() {
		return literal;
	}
    public void setLiteral(char literal) {
		this.literal = literal;
	}
    
    /**
     * Nome da fase.
     */
    public String getPhaseName() {
		return phaseName;
	}
    public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
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
    	if (getContent()!=null) {
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

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "text".
     */
    /**
     * Estimativa (HH).
     */
    public int getEstimate() {
		return estimate;
	}
    public void setEstimate(int estimate) {
		this.estimate = estimate;
	}
    
    /**
     * Data programada para inécio.
     */
    public Date getScheduledStartDate() {
        return this.scheduledStartDate;
    }
    public void setScheduledStartDate(Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }
    
    /**
     * Data programada para fim.
     */
    public Date getScheduledEndDate() {
        return this.scheduledEndDate;
    }
    public void setScheduledEndDate(Date scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    /**
     * Resolve plan status.
     */
//    @Transient
    public StateResolver getState() {
    	return new SimpleStateResolver(this);
    }
    
    @Override
    public int compareTo(ReportPhase other) {
    	if (other!=null) {
    		return getLiteral() - other.getLiteral();
    	}
    	return 0;
    }

    /**
     * toString
     * @return String
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("reportFolder").append("='").append(getReportFolder()).append("' ");
        buffer.append("literal").append("='").append(getLiteral()).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }

   /**
    * equals
    */
    @Override
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
         if ( (other == null ) ) return false;
         if ( !(other instanceof ReportPhase) ) return false;
         ReportPhase castOther = (ReportPhase) other; 
         
         return  ((this.getReportFolder()==castOther.getReportFolder()) || ( this.getReportFolder()!=null && castOther.getReportFolder()!=null && this.getReportFolder().equals(castOther.getReportFolder()) ))
        		 && (this.getLiteral()==castOther.getLiteral());
   }
   
   /**
    * hashCode
    */
   @Override
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getReportFolder() == null ? 0 : this.getReportFolder().hashCode() );
         result = 37 * result + getLiteral();
         return result;
   }

//	@Transient
	public char getResolution() {
		// TODO Remover a interface Verifiable
		return 'D';
	}
	
//	@Transient
	public Date getNextCheckDate() {
		// TODO Remover a interface Verifiable
		return null;
	}

}
