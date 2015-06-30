package org.helianto.task.repository;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.Report;
import org.helianto.task.repository.ReportFolderCounter;
import org.helianto.task.repository.ReportRepository;
import org.junit.Test;
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
public class ReportRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<Report, ReportRepository> {

	@Autowired
	private ReportRepository repository;
	
	protected ReportRepository getRepository() {
		return repository;
	}
	
	protected Report getNewTarget() {
		return new Report(entity, "0");		
	}
	
	protected Serializable getTargetId(Report target) {
		return target.getId();
	}
	
	protected Report findByKey() {
		return getRepository().findByEntityAndReportCode(entity, "0");
	}
	
	@Test
	public void count() {
		List<ReportFolderCounter> folderList = repository.countByReportFolder(entity);
		assertNotNull(folderList);
		folderList = repository.countLateByReportFolder(entity, new Date());
		assertNotNull(folderList);
		repository.count(entity);
		repository.countLate(entity, new Date());
	}
	
}
