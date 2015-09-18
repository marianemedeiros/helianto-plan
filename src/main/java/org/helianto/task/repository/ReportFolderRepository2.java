package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.task.domain.ReportFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ReportFolder repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ReportFolderRepository2 
	extends JpaRepository<ReportFolder, Serializable> 
{
	
	public static final String QUERY = "select new "
			+ "org.helianto.task.domain.ReportFolder"
			+ "( reportFolder_.id"
			+ ", reportFolder_.entity.id, "
			+ "  reportFolder_.folderCode, "
			+ "  reportFolder_.content, "
			+ "  reportFolder_.encoding, "
			+ "  reportFolder_.owner.id, "
			+ "  reportFolder_.reportNumberPattern, "
			+ "  reportFolder_.patternSuffix, "
			+ "  reportFolder_.parsedContent, "
			+ "  reportFolder_.category.id, "
			+ "  reportFolder_.privacyLevel, "
			+ "  reportFolder_.zIndex, "
			+ "  reportFolder_.partner.id, "
			+ "  reportFolder_.userGroup.id, "
			+ "  reportFolder_.folderCaption, "
			+ "  reportFolder_.parentPath, "
			+ "  reportFolder_.nature, "
			+ "  reportFolder_.traceabilityItems, "
			+ "  reportFolder_.startDate, "
			+ "  reportFolder_.endDate, "
			+ "  reportFolder_.volumeTags, "
			+ "  reportFolder_.categoryOverrideAllowed"
			+ ") "
			+ "from ReportFolder reportFolder_ ";
	
	/**
	 * Find report folder.
	 * 
	 * @param id
	 */
	@Query(QUERY
			+ "where reportFolder_.id = ?1 ")
	ReportFolder findReportFolder(int id);
	
	/**
	 * Page report folders by entity id.
	 * 
	 * @param entityId
	 * @param page
	 */
	@Query(QUERY
			+ "where reportFolder_.entity.id = ?1 ")
	Page<ReportFolder> findReportFolderByEntity(int entityId, Pageable page);
	
	/**
	 * Page report folders by entity and category id.
	 * 
	 * @param entityId
	 * @param categoryId
	 * @param page
	 */
	@Query(QUERY
			+ "where reportFolder_.entity.id = ?1 "
			+ "and reportFolder_.category.id = ?2 ")
	Page<ReportFolder> findReportFolderByCategory(int entityId, int categoryId, Pageable page);
	
	/**
	 * Page report folders by entity and user group id.
	 * 
	 * @param entityId
	 * @param userGroupId
	 * @param page
	 */
	@Query(QUERY
			+ "where reportFolder_.entity.id = ?1 "
			+ "and reportFolder_.userGroup.id = ?2 ")
	Page<ReportFolder> findReportFolderByUser(int entityId, int userGroupId, Pageable page);
	
}

