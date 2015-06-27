package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.task.domain.TestGroup;
import org.helianto.task.domain.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Test report repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface TestReportRepository 
	extends JpaRepository<TestReport, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param testGroup
	 * @param testCode
	 */
	TestReport findByTestGroupAndReportCode(TestGroup testGroup, String testCode);
	
}
