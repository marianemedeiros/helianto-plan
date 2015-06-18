package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Entity;

import org.helianto.task.domain.Volume;

/**
 * Volume repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface VolumeRepository extends
		FilterRepository<Volume, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param entity
	 * @param volumeCode
	 */
	Volume findByEntityAndVolumeCode(Entity entity, String volumeCode);
}
