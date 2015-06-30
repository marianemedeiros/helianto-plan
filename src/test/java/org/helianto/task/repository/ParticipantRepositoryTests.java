package org.helianto.task.repository;

import java.io.Serializable;

import javax.inject.Inject;

import org.helianto.core.domain.Identity;
import org.helianto.core.repository.IdentityRepository;
import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.Participant;
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

public class ParticipantRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<Participant, ParticipantRepository> {
	
	@Inject
	private ParticipantRepository repository;
	
	@Inject
	private ReportRepository reportRepository;
	
	@Inject
	private IdentityRepository identityRepository;

	private Report report;
	private Identity identity;

	protected ParticipantRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(Participant target) {
		return target.getId();
	}

	protected Participant getNewTarget() {
		report = reportRepository.save(new Report(entity, "0"));
		identity = identityRepository.save(new Identity());
		return new Participant(report, identity);
	}

	protected Participant findByKey() {
		return getRepository().findByReportAndIdentity(report, identity);
	}

}
