package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Operator;

import org.helianto.task.domain.TestGroup;

/**
 * Test case repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface TestGroupRepository 
	extends FilterRepository<TestGroup, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param operator
	 * @param testCode
	 */
	TestGroup findByOperatorAndTestCode(Operator operator, String testCode);
	
}
