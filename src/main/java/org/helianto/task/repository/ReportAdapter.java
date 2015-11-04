package org.helianto.task.repository;

import java.io.Serializable;
import java.util.Date;

import org.helianto.core.internal.AbstractItemAdapter;
import org.helianto.task.domain.Report;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Adaptor to reports.
 * 
 * @author Eldevan Nery Jr
 */
public class ReportAdapter extends AbstractItemAdapter
	implements Serializable
{	
	
	private static final long serialVersionUID = 1L;
	
	private Report adaptee;

	private Long internalNumber; 
	
	private String reportCode = "";
	
    private String summary = "";
    
	private String taskDesc = "";
	
	private Integer categoryId;
	
    private String categoryCode = "";
    
    private String categoryName = "";
	
	private String contentAsString = "";
	
	private Integer relativeSize;
	
	private Integer identityId;
	
	private Character priority;
	
	private Integer frequency;
	
	private Integer reportFolderId;
	
	private Character phase;
	
	public ReportAdapter(Integer id, Long internalNumber, String reportCode,
			String summary, Character resolution, Date nextCheckDate,
			String taskDesc, Integer categoryId, String categoryCode,
			String categoryName, byte[] content, Integer relativeSize,
			Character priority, Integer frequency, Integer folderId, Character phase) {
		super(id, "", reportCode, taskDesc, nextCheckDate, resolution);
		this.internalNumber = internalNumber;
		this.reportCode = reportCode;
		this.summary = summary;
		this.taskDesc = taskDesc;
		this.categoryId = categoryId;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.contentAsString = new String(content);
		this.relativeSize = relativeSize;
		this.priority = priority;
		this.frequency = frequency;
		this.reportFolderId = folderId;
	}
	
	public ReportAdapter(Integer id, Long internalNumber, String reportCode,
			String summary, Character resolution, Date nextCheckDate,
			String taskDesc, Integer categoryId, byte[] content, Integer relativeSize,
			Character priority, Integer frequency, Integer folderId , Character phase) {
		super(id, "", reportCode, taskDesc, nextCheckDate, resolution);
		this.internalNumber = internalNumber;
		this.reportCode = reportCode;
		this.summary = summary;
		this.taskDesc = taskDesc;
		this.categoryId = categoryId;
		this.contentAsString = new String(content);
		this.relativeSize = relativeSize;
		this.priority = priority;
		this.frequency = frequency;
		this.reportFolderId = folderId;
		this.phase = phase;
	}

	/**
	 * Adaptee constructor.
	 *
	 * @param adaptee
	 */
	public ReportAdapter(Report report) {
		super();
		this.adaptee = report;
	}
	
	public ReportAdapter() {
		super();
	}

	public ReportAdapter build(){
		if (adaptee==null) {
			throw new RuntimeException("Null report cannot be persisted.");
		}
		this.id = adaptee.getId();
		if(adaptee.getCategory()!=null){
			this.categoryId = adaptee.getCategory().getId();
		}
		if(adaptee.getSeries()!=null){
			setReportFolderId(adaptee.getSeries().getId());
		}
		setContentAsString(adaptee.getContentAsString());
		setInternalNumber(adaptee.getInternalNumber());
		setReportCode(adaptee.getReportCode());
		setSummary(adaptee.getSummary());
		setTaskDesc(adaptee.getTaskDesc()); 
		setRelativeSize(adaptee.getRelativeSize());
		setPriority(adaptee.getPriority());
		setFrequency(adaptee.getFrequency());
		setPhase(adaptee.getPhase());
		return this;
	}
	

	public Report merge(){
		adaptee.setId(getId());
		adaptee.setInternalNumber(getInternalNumber());
		adaptee.setReportCode(getReportCode());
		adaptee.setSummary(getSummary());
		adaptee.setTaskDesc(getTaskDesc());
		adaptee.setRelativeSize(getRelativeSize());
		adaptee.setPriority(getPriority());
		adaptee.setFrequency(getFrequency());
		adaptee.setContentAsString(getContentAsString());
		adaptee.setPhase(getPhase());
		return adaptee;
	}

	@JsonIgnore
	public Report getAdaptee() {
		return adaptee;
	}
	public ReportAdapter setAdaptee(Report adaptee) {
		this.adaptee = adaptee;
		return this;
	}
	
	public long getInternalNumber() {
		return internalNumber;
	}

	public void setInternalNumber(long internalNumber) {
		this.internalNumber = internalNumber;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Integer getRelativeSize() {
		return relativeSize;
	}

	public void setRelativeSize(Integer relativeSize) {
		this.relativeSize = relativeSize;
	}

	public Character getPriority() {
		return priority;
	}

	public void setPriority(Character priority) {
		this.priority = priority;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getContentAsString() {
		return contentAsString;
	}
	public void setContentAsString(String contentAsString) {
		this.contentAsString = contentAsString;
	}
	
	public Integer getReportFolderId() {
		return reportFolderId;
	}
	public void setReportFolderId(Integer reportFolderId) {
		this.reportFolderId = reportFolderId;
	}
	
	public void setIdentityId(Integer identityId) {
		this.identityId = identityId;
	}
	
	public Character getPhase() {
		return phase;
	}
	
	public void setPhase(Character phase) {
		this.phase = phase;
	}
	
	/**
	 * Used by reporter
	 * 
	 */
	public Integer getIdentityId() {
		return identityId;
	}
	
	public String getOpeningIcon() {
		switch(super.getResolution()){
		case 'I': return "fa fa-square-o";
		}
		return "fa fa-check-square-o";
	}
	
	public String getClosingIcon() {
		switch(super.getResolution()){
		case 'T': return "fa fa-clock-o";
		case 'D': return "fa fa-check-circle-o";
		case 'X': return "fa fa-times-circle-o";
		}
		return "fa fa-circle-o";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportAdapter other = (ReportAdapter) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportAdapter [id=" + getId() + ", internalNumber=" + internalNumber
				+ ", reportCode=" + reportCode + ", summary=" + summary
				+ ", resolution=" + getResolution() + ", nextCheckDate="
				+ getNextCheckDate() + ", taskDesc=" + taskDesc + ", categoryId="
				+ categoryId + ", categoryCode=" + categoryCode
				+ ", categoryName=" + categoryName + ", contentAsString="
				+ contentAsString + ", relativeSize=" + relativeSize
				+ ", priority=" + priority + ", frequency=" + frequency + "]";
	}

}
