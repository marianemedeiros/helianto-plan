package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Entity;

import org.helianto.task.domain.DocumentRequirement;
/**
 *  DocumentRequirement repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface DocumentRequirementRepository extends
		FilterRepository<DocumentRequirement, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param entity
	 * @param internalNumber
	 */
	DocumentRequirement findByEntityAndInternalNumber(Entity entity, long internalNumber);
	
}
