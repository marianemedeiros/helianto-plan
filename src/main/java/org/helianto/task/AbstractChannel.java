package org.helianto.task;

import java.io.Serializable;
import java.util.Set;

/**
 * Base channel.
 * 
 * @author mauriciofernandesdecastro
 * 
 */
public abstract class AbstractChannel implements Serializable, Channel {
	
	private static final long serialVersionUID = 1L;
	private String channelAlias;
	private Set<String> channelSet;
	
	/**
	 * Channel constructor.
	 * 
	 * @param channelAlias
	 */
	public AbstractChannel(String channelAlias) {
		setChannelAlias(channelAlias);
	}
	
	public String getChannelAlias() {
		return channelAlias;
	}
	public void setChannelAlias(String channelAlias) {
		this.channelAlias = normalize(channelAlias);
	}
	
	public Set<String> getChannelSet() {
		return channelSet;
	}
	public void setChannelSet(Set<String> channelSet) {
		this.channelSet = channelSet;
	}
	
	public boolean add(String key) {
		if (getChannelSet()!=null) {
			return getChannelSet().add(key);
		}
		return false;
	}
	
	/**
	 * Normalize token to assure a slash only as first character and no whitespace.
	 * 
	 * @param token
	 */
	public static String normalize(String token) {
		return token.replaceAll("\\s", "").replaceAll("^/*", "").replaceAll("/*$", "");
	}

	/**
	 * String representation.
	 */
	@Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("channelAlias").append("='").append(getChannelAlias()).append("' ");
        if (getChannelSet()!=null) {
            buffer.append("channelSet").append("='").append(getChannelSet().toArray()).append("' ");
        }
        buffer.append("]");
      
        return buffer.toString();
	}
	
	/**
	 * Hash code.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getChannelAlias() == null ? 0 : this.getChannelAlias().hashCode() );
		return result;
	}   

	/**
	 * Equals.
	 */
	@Override
	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( (other == null ) ) return false;
        if ( !(other instanceof AbstractChannel) ) return false;
        AbstractChannel castOther = (AbstractChannel) other; 
        
        return this.getChannelAlias()==castOther.getChannelAlias()
        		|| ( this.getChannelAlias()!=null 
        		&& castOther.getChannelAlias()!=null 
        		&& this.getChannelAlias().equals(castOther.getChannelAlias()) );
	}
	
}
