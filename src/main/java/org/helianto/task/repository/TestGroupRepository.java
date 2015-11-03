package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.domain.Operator;
import org.helianto.task.domain.TestGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Test case repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface TestGroupRepository 
	extends JpaRepository<TestGroup, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param operator
	 * @param testCode
	 */
	TestGroup findByOperatorAndTestCode(Operator operator, String testCode);
	
}
