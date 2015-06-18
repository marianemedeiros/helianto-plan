package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;

import org.helianto.task.domain.DocumentRequirement;
import org.helianto.task.domain.ReportKnowledge;
import org.helianto.task.domain.UserRequirement;
/**
 *  ReportKnowledge repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportKnowledgeRepository extends
		FilterRepository<ReportKnowledge, Serializable> {
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
