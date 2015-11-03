package org.helianto.task.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Report folder exported.
 * 
 * @author Eldevan Nery Junior
 */
@javax.persistence.Entity
@javax.persistence.Table(name="task_exported",
	uniqueConstraints = {@UniqueConstraint(columnNames={"reportFolderId", "exportedEntityId"})}
)
public class ReportFolderExported 
	implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
	@Version
    private Integer version;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="reportFolderId")
    private ReportFolder reportFolder;
	
	@ManyToOne
	@JoinColumn(name="exportedEntityId")
    private Entity exportedEntity;
    
	@ManyToOne
	@JoinColumn(name="exporterId", nullable=true)
	private Identity exporter; 
	
	/**
	  * Merger.
	  * 
	  * @param command
  	  **/
	public ReportFolderExported merge(ReportFolderExported command) {
			setId(command.getId());
			
			return this;
		}
    
    /**
     * Constructor
     */
	public ReportFolderExported() {
		super();
	}
	/**
	 * key constructor
	 * @param reportFolder 
	 * @param exportedEntity
	 */
	public ReportFolderExported(ReportFolder reportFolder ,
			Entity exportedEntity) {
		setExportedEntity(exportedEntity);
		setReportFolder(reportFolder );
	}
    
	 /**
     * Primary key.
     */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Version.
     */
    public Integer getVersion() {
        return this.version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * <<NaturalKey>> ReportFolder
     * @see {@link ReportFolder}
     */
    public ReportFolder getReportFolder() {
		return reportFolder;
	}
    public void setReportFolder(ReportFolder reportFolder) {
		this.reportFolder = reportFolder;
	}
    
    /**
     * <<NaturalKey>> exportedEntity.
     * 
     * @see {@link Entity}
     */
    public Entity getExportedEntity() {
		return exportedEntity;
	}
    public void setExportedEntity(Entity identity) {
		this.exportedEntity = identity;
	}
    
    /**
     * Exporter identity.
     * 
     * @see {@link Identity}
     */
	public Identity getExporter() {
		return exporter;
	}
	public void setExporter(Identity exporter) {
		this.exporter = exporter;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reportFolder == null) ? 0 : reportFolder.hashCode());
		result = prime * result
				+ ((exportedEntity == null) ? 0 : exportedEntity.hashCode());
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
		ReportFolderExported other = (ReportFolderExported) obj;
		if (reportFolder == null) {
			if (other.reportFolder != null)
				return false;
		} else if (!reportFolder.equals(other.reportFolder))
			return false;
		if (exportedEntity == null) {
			if (other.exportedEntity != null)
				return false;
		} else if (!exportedEntity.equals(other.exportedEntity))
			return false;
		return true;
	}
	
    
}
