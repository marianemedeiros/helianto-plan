package org.helianto.task.repository;

import java.io.Serializable;

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
public interface StaffMemberJoinFolderRepository 
	extends JpaRepository<StaffMember, Serializable> {

	public static final String QUERY = "select new "
			+ "org.helianto.task.domain.StaffMember"
			+ "( staff_.id, "
			+ "  reportFolder_.id, "
			+ "  reportFolder_.folderCode, "
			+ "  reportFolder_.folderName, "
			+ "  staff_.identity.id, "
			+ "  reportFolder_.partner.id, "
			+ "  reportFolder_.startDate, "
			+ "  reportFolder_.endDate"
			+ ") "
			+ "from ReportFolder reportFolder_ "
			+ "join reportFolder_.staff staff_ ";
	
	/**
	 * Find by identity.
	 * 
	 * @param entityId
	 * @param identity
	 */
	@Query(QUERY
			+ "where reportFolder_.entity.id = ?1 "
			+ "and staff_.identity.id = ?2 ")
	Page<StaffMember> findByIdentity(int entityId, int identityId, Pageable page);

}
