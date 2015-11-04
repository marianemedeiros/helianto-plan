package org.helianto.task.repository;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.helianto.task.domain.ReportPhase;

/**
 * Adapter to phases.
 * 
 * @author Eldevan Nery Junior
 */
public class ReportPhaseAdapter {

    private Integer id = 0;
    
    private Integer reportFolderId;
    
    private ReportPhase adaptee;
    
    private Character literal = '1';
    
    private String phaseName;
    
    private byte[] content;
    
    private String encoding;
    
    private Integer estimate = 0;
    
    private Date scheduledStartDate;
    
    private Date scheduledEndDate;
    
    public ReportPhaseAdapter( Integer id
    		, Integer reportFolderId
    		, Character literal
    		, String phaseName
			, byte[] content
			, String encoding
			, Integer estimate
			, Date scheduledStartDate
			, Date scheduledEndDate) {
		this.id = id;
		this.reportFolderId = reportFolderId;
		this.literal = literal;
		this.phaseName = phaseName;
		this.content = content;
		this.encoding = encoding;
		this.estimate = estimate;
		this.scheduledStartDate = scheduledStartDate;
		this.scheduledEndDate = scheduledEndDate;
	}

	public ReportPhaseAdapter(ReportPhase adaptee) {
		this.adaptee = adaptee;
		this.setReportFolderId(adaptee.getId());
    }
	
	public ReportPhaseAdapter() {
		super();
	}
    
	public ReportPhaseAdapter build(){
		if (adaptee==null) {
			throw new RuntimeException("Null reportPhase cannot be persisted.");
		}
		setId(adaptee.getId());
		if(adaptee.getReportFolder()!=null){
			setReportFolderId(adaptee.getReportFolder().getId());
		}
		setContentAsString(adaptee.getContentAsString());
		setEncoding(adaptee.getEncoding());
		setEstimate(adaptee.getEstimate());
		setLiteral(adaptee.getLiteral());
		setPhaseName(adaptee.getPhaseName());
		setScheduledEndDate(adaptee.getScheduledEndDate());
		setScheduledStartDate(adaptee.getScheduledStartDate());
		return this;
	}
	
	public ReportPhase merge(){
		adaptee.setId(getId());
		adaptee.setContent(getContentAsString().getBytes());
		adaptee.setEncoding(getEncoding());
		adaptee.setEstimate(getEstimate());
		adaptee.setLiteral(getLiteral());
		adaptee.setPhaseName(getPhaseName());
		adaptee.setScheduledEndDate(getScheduledEndDate());
		adaptee.setScheduledStartDate(getScheduledStartDate());
		return adaptee;
	}

	@JsonIgnore
	public ReportPhase getAdaptee() {
		return adaptee;
	}
	public ReportPhaseAdapter setAdaptee(ReportPhase adaptee) {
		this.adaptee = adaptee;
		return this;
	}
	


    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportFolderId() {
		return reportFolderId;
	}
    
    public void setReportFolderId(Integer reportFolderId) {
		this.reportFolderId = reportFolderId;
	}
    
    public Character getLiteral() {
		return literal;
	}
    public void setLiteral(Character literal) {
		this.literal = literal;
	}
    
    public String getPhaseName() {
		return phaseName;
	}
    public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
    
    @JsonIgnore
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
    
    public String getContentAsString() {
    	if (getContent()!=null) {
    		return new String(getContent());
    	}
    	return "";
    }
    public void setContentAsString(String contentAsString) {
		setContent(contentAsString);
	}
    
    public Integer getContentSize() {
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

    public Integer getEstimate() {
		return estimate;
	}
    public void setEstimate(Integer estimate) {
		this.estimate = estimate;
	}
    
    public Date getScheduledStartDate() {
        return this.scheduledStartDate;
    }
    public void setScheduledStartDate(Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }
    
    public Date getScheduledEndDate() {
        return this.scheduledEndDate;
    }
    public void setScheduledEndDate(Date scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

}
