package org.helianto.task;

/**
 * Interface to classes representing a report.
 * 
 * @author mauriciofernandesdecastro
 */
public interface Reportable {
	
	/**
	 * Report summary.
	 */
	String getSummary();
	
	/**
	 * Report description.
	 */
	String getReportDesc();

}
