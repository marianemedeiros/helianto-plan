package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.Report;
import org.helianto.task.domain.ReportReview;
import org.helianto.task.repository.ReportRepository;
import org.helianto.task.repository.ReportReviewRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportReviewRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<ReportReview,ReportReviewRepository> {
	
	@Autowired
	private ReportReviewRepository repository;
	
	@Autowired
	private ReportRepository reportRepository;

	private Report report;

	protected ReportReviewRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(ReportReview target) {
		return target.getId();
	}

	protected ReportReview getNewTarget() {
		report = reportRepository.save(new Report(entity, "0"));
		return new ReportReview(report, 1000L);
	}

	protected ReportReview findByKey() {
		return getRepository().findByReportAndTimeKey(report, 1000L);
	}

}
