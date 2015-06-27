package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.domain.Entity;
import org.helianto.task.domain.UserRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRequirement repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface UserRequirementRepository 
	extends JpaRepository<UserRequirement, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param entity
	 * @param internalNumber
	 */
	UserRequirement findByEntityAndInternalNumber(Entity entity,
			long internalNumber);
}
