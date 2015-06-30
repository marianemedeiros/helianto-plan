package org.helianto.task.repository;

import java.io.Serializable;

import javax.inject.Inject;

import org.helianto.core.domain.Identity;
import org.helianto.core.repository.IdentityRepository;
import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.StaffMember;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class StaffMemberRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<StaffMember,StaffMemberRepository> {
	
	@Inject
	private StaffMemberRepository repository;
	
	@Inject
	private ReportFolderRepository reportFolderRepository;
	
	@Inject
	private IdentityRepository identityRepository;
	
	private Identity identity;
	private ReportFolder reportFolder;

	protected StaffMemberRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(StaffMember target) {
		return target.getId();
	}

	protected StaffMember getNewTarget() {
		reportFolder = reportFolderRepository.save(new ReportFolder(entity, "t"));
		identity = identityRepository.save(new Identity("p"));
		return new StaffMember(reportFolder,identity);
	}

	protected StaffMember findByKey() {
		return getRepository().findByReportFolderAndIdentity(reportFolder, identity);
	}

}

