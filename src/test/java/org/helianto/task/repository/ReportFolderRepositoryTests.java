package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.repository.ReportFolderRepository;
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
public class ReportFolderRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<ReportFolder,ReportFolderRepository> {
	@Autowired
	private ReportFolderRepository repository;
	
	protected ReportFolderRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(ReportFolder target) {
		return target.getId();
	}

	protected ReportFolder getNewTarget() {
		return new ReportFolder(entity, "folder");
	}

	protected ReportFolder findByKey() {
		return getRepository().findByEntityAndFolderCode(entity, "folder");
	}
}
