package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;

import org.helianto.task.domain.TestGroup;
import org.helianto.task.domain.TestReport;

/**
 * Test report repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface TestReportRepository 
	extends FilterRepository<TestReport, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param testGroup
	 * @param testCode
	 */
	TestReport findByTestGroupAndReportCode(TestGroup testGroup, String testCode);
	
}
