package org.helianto.task;

import java.util.HashSet;
import java.util.Set;

/**
 * Open channel.
 * 
 * @author mauriciofernandesdecastro
 */
public class OpenChannel extends AbstractChannel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Alias constructor.
	 * 
	 * @param channelAlias
	 */
	public OpenChannel(String channelAlias) {
		this(channelAlias, new HashSet<String>());
	}

	/**
	 * Set constructor.
	 * 
	 * @param channelAlias
	 * @param channelSet
	 */
	public OpenChannel(String channelAlias, Set<String> channelSet) {
		super(channelAlias);
		setChannelSet(channelSet);
	}

	/**
	 * Key constructor.
	 * 
	 * @param channelAlias
	 * @param channelKeys
	 */
	public OpenChannel(String channelAlias, String... channelKeys) {
		super(channelAlias);
		for (String channelKey: channelKeys) {
			add(channelKey);
		}
	}

}
