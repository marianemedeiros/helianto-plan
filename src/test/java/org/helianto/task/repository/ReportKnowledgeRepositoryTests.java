package org.helianto.task.repository;

import java.io.Serializable;

import javax.inject.Inject;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.DocumentRequirement;
import org.helianto.task.domain.ReportKnowledge;
import org.helianto.task.domain.UserRequirement;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportKnowledgeRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<ReportKnowledge,ReportKnowledgeRepository>{
	
	@Inject
	private ReportKnowledgeRepository repository;
	
	@Inject
	private DocumentRequirementRepository  documentRequirementRepository ;
	
	@Inject
	private UserRequirementRepository  userRequirementRepository ;
	
	private DocumentRequirement documentRequirement;
	
	private UserRequirement userRequirement;

	protected ReportKnowledgeRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(ReportKnowledge target) {
		return target.getId();
	}

	protected ReportKnowledge getNewTarget() {
		documentRequirement = documentRequirementRepository.save(new DocumentRequirement());
		userRequirement = userRequirementRepository.save(new UserRequirement());
		return new ReportKnowledge(documentRequirement, userRequirement);
	}

	protected ReportKnowledge findByKey() {
		return getRepository().findByDocumentRequirementAndUserRequirement(documentRequirement, userRequirement);
	}
}
