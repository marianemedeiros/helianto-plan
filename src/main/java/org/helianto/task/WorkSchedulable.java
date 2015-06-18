package org.helianto.task;

import java.math.BigDecimal;

/**
 * Interface to classes representing a work done or to be done.
 * 
 * @author mauriciofernandesdecastro
 */
public interface WorkSchedulable extends Workable {
	
	/**
	 * Work scheduled.
	 */
	BigDecimal getScheduledWork();
	
	/**
	 * Work converter.
	 * 
	 * @param work
	 */
	long convertToMilliseconds(BigDecimal work);

}
