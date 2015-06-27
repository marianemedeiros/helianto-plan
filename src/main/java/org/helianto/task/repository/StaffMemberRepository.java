package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.domain.Identity;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * StaffMember repository.
 * 
 * @author Eldevan Nery Junior
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

}
