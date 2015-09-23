package org.helianto.task.def;

/**
 * Allows for pattern generation.
 * 
 * @author mauriciofernandesdecastro
 */
public interface Sequencer {
	
	/**
	 * Pattern prefix.
	 */
	String getPatternPrefix();
	
	/**
	 * Pattern suffix.
	 */
	String getPatternSuffix();
	
	/**
	 * Number of digits.
	 */
	int getNumberOfDigits();

}
