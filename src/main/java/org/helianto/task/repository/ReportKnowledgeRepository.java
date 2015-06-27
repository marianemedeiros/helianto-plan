package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.task.domain.DocumentRequirement;
import org.helianto.task.domain.ReportKnowledge;
import org.helianto.task.domain.UserRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *  ReportKnowledge repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportKnowledgeRepository extends
		JpaRepository<ReportKnowledge, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param documentRequirement
	 * @param userRequirement
	 */
	ReportKnowledge findByDocumentRequirementAndUserRequirement(
			DocumentRequirement documentRequirement,
			UserRequirement userRequirement);
}
