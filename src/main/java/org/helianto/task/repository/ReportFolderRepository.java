package org.helianto.task.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Entity;
import org.helianto.task.domain.ReportFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * ReportFolder repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportFolderRepository extends
		FilterRepository<ReportFolder, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param entity
	 * @param folderCode
	 */
	ReportFolder findByEntityAndFolderCode(Entity entity, String folderCode);
	
	/**
	 * Find by folder code or folder name like.
	 * 
	 * @param folderCode
	 * @param folderName
	 */
	List<ReportFolder> findByFolderCodeLikeOrFolderNameLike(String folderCode, String folderName);
	
	/**
	 *  Find by folder code or folder name.
	 *  
	 * @param folderCode
	 * @param folderName
	 */
	List<ReportFolder> findByFolderCodeOrFolderName(String folderCode, String folderName);
	
	/**
	 * Find by categoryId
	 * 
	 * @param categoryId
	 */
	List<ReportFolder> findByCategoryId(int categoryId);
	
	/**
	 * Find by natural key.
	 * 
	 * @param entityId
	 */
	List<ReportFolder> findByEntity_IdAndCategoryIsNull(int entityId);
	
	/**
	 * Find by null category.
	 * 
	 * @param entityId
	 */
	@Query("select new "
			+ "org.helianto.home.internal.FolderReadAdapter"
			+ "(folder.id, folder.category.id, folder.folderCode, folder.folderName, "
			+ "folder.folderDecorationUrl, folder.patternPrefix, folder.patternSuffix, folder.numberOfDigits, folder.entity.id) "
			+ "from ReportFolder folder "
			+ "where folder.entity.id = ?1 and folder.category.id = null ")
	Page<FolderReadAdapter> findByEntity_IdAndCategoryIsNull(int entityId, Pageable page);

	/**
	 * Find by Category
	 * @param entityId
	 * @param categoryId
	 * @param page
	 * @return
	 */	 
	@Query("select new "
			+ "org.helianto.home.internal.FolderReadAdapter"
			+ "(folder.id, folder.category.id, folder.folderCode, folder.folderName, "
			+ "folder.folderDecorationUrl, folder.patternPrefix, folder.patternSuffix, folder.numberOfDigits, folder.entity.id) "
			+ "from ReportFolder folder "
			+ "where (folder.entity.id = ?1 OR folder.id in (select e.reportFolder.id from ReportFolderExported  e where e.exportedEntity.id = ?1)) and folder.category.id = ?2 ")
	Page<FolderReadAdapter> findByEntity_IdAndCategoryId(int entityId, int categoryId, Pageable page);

	/**
	 * Find By folderCode
	 * @param entityId
	 * @param folderCode
	 * @return
	 */
	@Query("select new "
			+ "org.helianto.home.internal.FolderReadAdapter"
			+ "(folder.id, folder.category.id, folder.folderCode, folder.folderName, "
			+ "folder.folderDecorationUrl, folder.patternPrefix, folder.patternSuffix, folder.numberOfDigits, folder.entity.id) "
			+ "from ReportFolder folder "
			+ "where folder.entity.id = ?1 and folder.folderCode = ?2 ")
	FolderReadAdapter findByEntity_IdAndFolderCode(int entityId, String folderCode);
	
	/**
	 * Find by id
	 * @return
	 */
	@Query("select new "
			+ "org.helianto.home.internal.FolderReadAdapter"
			+ "(folder.id, folder.category.id, folder.folderCode, folder.folderName, "
			+ "folder.folderDecorationUrl, folder.patternPrefix, folder.patternSuffix, folder.numberOfDigits, folder.entity.id) "
			+ "from ReportFolder folder "
			+ "where folder.id = ?1 ")
	FolderReadAdapter findById(int id);
	
	@Query("select folder.id "
			+ "from ReportFolder folder "
			+ "where folder.category.id = ?1 AND lower(folder.folderCode) like ?2")
	List<Integer> findIdsByCategoryIdAndFolderCodeLike(int categoryId, String folderCode);
	
}

