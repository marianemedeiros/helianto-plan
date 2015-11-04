package org.helianto.task.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.helianto.core.domain.Entity;
import org.helianto.core.internal.AbstractEvent;

/**
 * Volumes.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_volume",
    uniqueConstraints = {@UniqueConstraint(columnNames={"entityId", "volumeCode"})}
)
public class Volume 
	extends AbstractEvent {

	private static final long serialVersionUID = 1L;
	
	@Column(length=12)
	private String volumeCode;
	
	@Column(length=64)
	private String volumeName;
	
	 /**
	  * Merger.
	  * 
	  * @param command
   	  **/
    public Volume merge(Volume command) {
    		setVolumeCode(command.getVolumeCode());
   			setVolumeName(command.getVolumeName());
   		   	return this;
   		}
    
	
	/**
	 * Default constructor.
	 */
	public Volume() {
		super();
		setResolution('A');
	}
	
	/**
	 * Key constructor.
	 * 
	 * @param entity
	 * @param volumeCode
	 */
	public Volume(Entity entity, String volumeCode) {
		this();
		setEntity(entity);
		setVolumeCode(volumeCode);
	}
	
	/**
	 * Code.
	 */
	public String getVolumeCode() {
		return volumeCode;
	}
	public void setVolumeCode(String volumeCode) {
		this.volumeCode = volumeCode;
	}
	
	/**
	 * Volume name.
	 */
	public String getVolumeName() {
		return volumeName;
	}
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEntity() == null) ? 0 : getEntity().hashCode());
		result = prime * result
				+ ((getVolumeCode() == null) ? 0 : getVolumeCode().hashCode());
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
		if (!(obj instanceof Volume)) {
			return false;
		}
		Volume other = (Volume) obj;
		if (getEntity() == null) {
			if (other.getEntity() != null) {
				return false;
			}
		} else if (!getEntity().equals(other.getEntity())) {
			return false;
		}
		if (getVolumeCode() == null) {
			if (other.getVolumeCode() != null) {
				return false;
			}
		} else if (!getVolumeCode().equals(other.getVolumeCode())) {
			return false;
		}
		return true;
	}

}
