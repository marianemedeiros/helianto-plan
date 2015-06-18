package org.helianto.task.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Entity;
import org.helianto.core.repository.EntityReadAdapter;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.ReportFolderExported;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * {@link ReportFolderExported} repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportFolderExportedRepository extends
		FilterRepository<ReportFolderExported, Serializable> {
	

	
	ReportFolderExported findByExportedEntityAndReportFolder(Entity entity, ReportFolder reportFolder);
	
	ReportFolderExported findByExportedEntityIdAndReportFolderId(Integer entityId, Integer reportFolderId);
	
	/**
	 * Find by exportedEntity
	 * 
	 * @param entityId
	 * @param page
	 * @return
	 */	 
	@Query("select new "
			+ "org.helianto.home.internal.FolderReadAdapter"
			+ "(exported.reportFolder.id"
			+ ", exported.reportFolder.category.id"
			+ ", exported.reportFolder.folderCode"
			+ ", exported.reportFolder.folderName"
			+ ", exported.reportFolder.folderDecorationUrl"
			+ ", exported.reportFolder.patternPrefix"
			+ ", exported.reportFolder.patternSuffix"
			+ ", exported.reportFolder.numberOfDigits"
			+ ", exported.reportFolder.entity.id) "
			+ "from ReportFolderExported exported "
			+ "where exported.exportedEntity.id = ?1 ")
	Page<FolderReadAdapter> findByEntity_Id(int entityId, Pageable page);

	/**
	 * Find by id
	 * @return
	 */
	@Query("select new "
			+ "org.helianto.home.internal.FolderReadAdapter"
			+ "(exported.reportFolder.id, exported.reportFolder.category.id, exported.reportFolder.folderCode, exported.reportFolder.folderName, "
			+ "exported.reportFolder.folderDecorationUrl, exported.reportFolder.patternPrefix, exported.reportFolder.patternSuffix, exported.reportFolder.numberOfDigits, exported.reportFolder.entity.id) "
			+ "from ReportFolderExported exported "
			+ "where exported.reportFolder.id = ?1 ")
	FolderReadAdapter findById(int id);
	

	
	@Query(value="select new "
			+ "org.helianto.core.repository.EntityReadAdapter"
			+ "(exported.exportedEntity.id"
			+ ", exported.exportedEntity.operator.id"
			+ ", 0"
			+ ", exported.exportedEntity.alias"
			+ ", exported.exportedEntity.installDate"
			+ ", exported.exportedEntity.summary"
			+ ", exported.exportedEntity.entityDomain"
			+ ", exported.exportedEntity.externalLogoUrl"
			+ ", exported.exportedEntity.customProperties"
			+ ", exported.exportedEntity.activityState"
			+ ", exported.exportedEntity.entityType" 
			+ ", exported.exportedEntity.city.id"
			+ ", exported.exportedEntity.city.cityName"
			+ ", exported.exportedEntity.city.state.id"
			+ ", exported.exportedEntity.city.state.stateCode"
			+ ", exported.exportedEntity.city.state.stateName"
			+ ", exported.exportedEntity.city.state.country.id"
			+ ") "
			+ " from ReportFolderExported exported "
			+ "where exported.reportFolder.id = ?1 ")
	Page<EntityReadAdapter> findByReportFolderId(int reportFolderId, Pageable pageable);
	
	@Query("select exported.exportedEntity.id from ReportFolderExported exported "
			+ "where exported.reportFolder.id = ?1 ")
	List<Integer> exclusions(Integer reportFolderId);
	
	@Query("select exported.id from ReportFolderExported exported "
			+ "where exported.exportedEntity.id = ?1 AND exported.reportFolder.id = ?2 ")
	Integer findExportedIdByExportedEntityIdAndReportFolderId(Integer entityId, Integer reportFolderId);
	
}

