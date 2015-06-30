package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.test.AbstractJpaRepositoryIntegrationTest;
import org.helianto.task.domain.Volume;
import org.helianto.task.repository.VolumeRepository;
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
public class VolumeRepositoryTests
	extends AbstractJpaRepositoryIntegrationTest<Volume,VolumeRepository> {
	
	@Autowired
	private VolumeRepository repository;

	protected VolumeRepository getRepository() {
		return repository;
	}

	protected Serializable getTargetId(Volume target) {
		return target.getId();
	}

	protected Volume getNewTarget() {
		return new Volume(entity,"c");
	}

	protected Volume findByKey() {
		return getRepository().findByEntityAndVolumeCode(entity, "c");
	}

}
