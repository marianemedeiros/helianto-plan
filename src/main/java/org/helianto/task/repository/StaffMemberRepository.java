package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Identity;

import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.StaffMember;

/**
 * StaffMember repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface StaffMemberRepository extends
		FilterRepository<StaffMember, Serializable> {

	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param identity
	 */
	StaffMember findByReportFolderAndIdentity(ReportFolder report,
			Identity identity);

}
