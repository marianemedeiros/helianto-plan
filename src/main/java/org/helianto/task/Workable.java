package org.helianto.task;

import java.math.BigDecimal;

/**
 * Interface to classes representing a work done.
 * 
 * @author mauriciofernandesdecastro
 */
public interface Workable {
	
	/**
	 * Work actually done.
	 */
	BigDecimal getActualWork();
	
	/**
	 * Working unit.
	 */
	char getWorkingUnit();

}
