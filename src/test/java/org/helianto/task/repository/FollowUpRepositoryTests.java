package org.helianto.task.repository;

import java.io.Serializable;

import javax.inject.Inject;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.FollowUp;
import org.helianto.task.domain.Report;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FollowUpRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<FollowUp,FollowUpRepository> {
	
	@Inject
	private FollowUpRepository repository;
	
	@Inject
	private ReportRepository reportRepository;

	private Report report;

	protected FollowUpRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(FollowUp target) {
		return target.getId();
	}

	protected FollowUp getNewTarget() {
		report = reportRepository.save(new Report(entity, "0"));
		return new FollowUp(report, 1000L);
	}

	protected FollowUp findByKey() {
		return getRepository().findByReportAndInternalNumber(report, 1000L);
	}

}
