package org.helianto.task.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.core.domain.Identity;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.StaffMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * StaffMember repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface StaffMemberRepository 
	extends JpaRepository<StaffMember, Serializable> {

	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param identity
	 */
	StaffMember findByReportFolderAndIdentity(ReportFolder report,
			Identity identity);

	public static final String QUERY = "select new "
			+ "org.helianto.task.domain.StaffMember"
			+ "( staff_.id"
			+ ", staff_.reportFolder.id"
			+ ", staff_.identity.id"
			+ ", staff_.identity.displayName"
			+ ", staff_.identity.personalData.firstName"
			+ ", staff_.identity.personalData.lastName"
			+ ", staff_.identity.personalData.imageUrl"
			+ ", staff_.identity.personalData.gender"
			+ ", staff_.reportFolder.partner.id"
			+ ", staff_.reportFolder.startDate"
			+ ", staff_.reportFolder.endDate"
			+ ", staff_.joinDate"
			+ ", staff_.staffGrade"
			+ ", staff_.staffRole"
			+ ") "
			+ "from StaffMember staff_ ";
	
	/**
	 * Find by folder id.
	 * 
	 * @param qualifierValue
	 * @param page
	 */
	@Query(QUERY
			+ "where staff_.reportFolder.id = ?1 ")
	Page<StaffMember> findByFolderId(Integer qualifierValue, Pageable page);

	/**
	 * Find by id.
	 * 
	 * @param id
	 */
	@Query(QUERY
			+ "where staff_.id = ?1 ")
	StaffMember findById(int id);

	/**
	 * Find id by key.
	 * 
	 * @param reportFolderId
	 * @param identityId
	 */
	@Query("select staff_.id " 
			+ "from StaffMember staff_ "
			+ "where staff_.reportFolder.id = ?1 "
			+ "and staff_.identity.id = ?2 ")
	Integer findByReportFolderIdAndIdentityId(Integer reportFolderId, Integer identityId);

	/**
	 * Find id's by folder.
	 * 
	 * @param reportFolderId
	 */
	@Query("select staff_.identity.id " 
			+ "from StaffMember staff_ "
			+ "where staff_.reportFolder.id = ?1")
	List<Integer> findIdentityIdsByReportFolderOnStaffMember(Integer reportFolderId);

}
