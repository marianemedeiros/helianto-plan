package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;

import org.helianto.task.domain.Report;
import org.helianto.task.domain.ReportReview;

/**
 *  ReportReview repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportReviewRepository 
	extends FilterRepository<ReportReview, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param timeKey
	 */
	ReportReview findByReportAndTimeKey(Report report, long timeKey);

}
