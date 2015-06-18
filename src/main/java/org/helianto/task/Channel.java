package org.helianto.task;

import java.util.Set;

/**
 * Channel interface.
 * 
 * <p>
 * Defines keys to be accessible across different entities.
 * </p>
 * 
 * @author mauriciofernandesdecastro
 *
 */
public interface Channel {
	
	/**
	 * The channel alias.
	 */
	String getChannelAlias();
	
	/**
	 * Channel set of keys accessible through the channel.
	 */
	Set<String> getChannelSet();

	/**
	 * Convenience to add a key to the channel set.
	 * 
	 * @param key
	 * @return true if key was actually added.
	 */
	boolean add(String key);
	
}
