package org.helianto.task.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Um adaptador para exibir itens.
 * 
 * @author mauriciofernandesdecastro
 */
public class AbstractItemAdapter 
	implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	protected int id;
    
	private String folderName;
    
	private String code;
    
	private String name;
    
	private Date nextCheckDate;
    
	private char resolution;
    
	private boolean late = false;
    
	private boolean warn = false;
    
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param folderName
	 * @param code
	 * @param name
	 * @param nextCheckDate
	 * @param resolution
	 */
	public AbstractItemAdapter(int id, String folderName, String code, String name, Date nextCheckDate, char resolution) {
		super();
    	this.id = id;
        this.folderName = folderName;
        this.code = code;
        this.name = name;
        this.nextCheckDate = nextCheckDate;
        this.resolution = resolution;
	}
	public AbstractItemAdapter() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public String getFolderName() {
		return folderName;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	public Date getNextCheckDate() {
		return nextCheckDate;
	}
	
	public char getResolution() {
		return resolution;
	}
	
	public boolean isLate() {
		return late;
	}
	public void setLate(boolean late) {
		this.late = late;
	}
	@JsonIgnore
	public boolean setLate(List<?> lateList) {
		for (Object late: lateList) {
			try {
				Integer id = (Integer) (late.getClass().getMethod("id")).invoke(late);
				if(id!=null && id.equals(getId())) {
					setLate(true);
					return true;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public boolean isWarn() {
		return warn;
	}
	public void setWarn(boolean warn) {
		this.warn = warn;
	}
	@JsonIgnore
	public boolean setWarn(List<?> warnList) {
		for (Object late: warnList) {
			try {
				Integer id = (Integer) (late.getClass().getMethod("id")).invoke(late);
				if(id!=null && id.equals(getId())) {
					setWarn(true);
					return true;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public String getRunLabel() {
		if (isLate()) return "danger";
		if (isWarn()) return "warning";
		return "default";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getCode() == null) ? 0 : getCode().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractItemAdapter)) {
			return false;
		}
		AbstractItemAdapter other = (AbstractItemAdapter) obj;
		if (getCode() == null) {
			if (other.getCode() != null) {
				return false;
			}
		} else if (!getCode().equals(other.getCode())) {
			return false;
		}
		return true;
	}

}
