package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;

import org.helianto.task.domain.FollowUp;
import org.helianto.task.domain.Report;
/**
 *  FollowUp repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface FollowUpRepository extends	FilterRepository<FollowUp, Serializable> {

	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param internalNumber
	 */

	FollowUp findByReportAndInternalNumber(Report report, long internalNumber);
	
}
