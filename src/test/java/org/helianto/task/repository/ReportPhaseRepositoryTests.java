package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.ReportPhase;
import org.helianto.task.repository.ReportFolderRepository;
import org.helianto.task.repository.ReportPhaseRepository;
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
public class ReportPhaseRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<ReportPhase,ReportPhaseRepository>  {
	@Autowired
	private ReportPhaseRepository repository;
	
	@Autowired
	private ReportFolderRepository reportFolderRepository;

	private ReportFolder reportFolder;

	protected ReportPhaseRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(ReportPhase target) {
		return target.getId();
	}

	protected ReportPhase getNewTarget() {
		reportFolder = reportFolderRepository.save(new ReportFolder(entity,"123"));
		return new ReportPhase(reportFolder, 'l');
	}

	protected ReportPhase findByKey() {
		return getRepository().findByReportFolderAndLiteral(reportFolder, 'l');
	}
	

}
