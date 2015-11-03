package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.task.domain.Report;
import org.helianto.task.domain.ReportReview;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  ReportReview repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportReviewRepository 
	extends JpaRepository<ReportReview, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param timeKey
	 */
	ReportReview findByReportAndTimeKey(Report report, long timeKey);

}
