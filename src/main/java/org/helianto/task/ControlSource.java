package org.helianto.task;

import java.util.Date;

import org.helianto.core.Controllable;
import org.helianto.core.StateResolver;

/**
 * Control source.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ControlSource extends Controllable {
	
	/**
	 * Resolution setter.
	 * 
	 * @param resolution
	 */
	public void setResolution(char resolution);
	
	/**
	 * Progress (complete) setter.
	 * 
	 * @param complete
	 */
	public void setComplete(int complete);
	
	/**
	 * Next check date setter.
	 * 
	 * @param nextCheckDate
	 */
	public void setNextCheckDate(Date nextCheckDate);
	
	/**
	 * State resolver.
	 */
	public StateResolver getState();

}
