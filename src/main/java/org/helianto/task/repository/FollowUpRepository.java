package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.task.domain.FollowUp;
import org.helianto.task.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *  FollowUp repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface FollowUpRepository 
	extends	JpaRepository<FollowUp, Serializable> 
{

	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param internalNumber
	 */

	FollowUp findByReportAndInternalNumber(Report report, long internalNumber);
	
}
