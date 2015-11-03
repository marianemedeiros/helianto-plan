package org.helianto.task.def;

/**
 * Archive types.
 * 
 * @author mauriciofernandesdecastro
 */
public enum Archive {
	
	CURRENT('C', true),
	ARCHIVED('A', false);
	
	private Archive(char value, boolean current) {
		this.value = value;
		this.current = current;
	}
	
	private char value;
	private boolean current;
	
	/**
	 * The archive char value.
	 */
	public char getValue() {
		return value;
	}
	
	/**
	 * True if is current.
	 */
	public boolean isCurrent() {
		return current;
	}
	
	/**
	 * Returns the enum constant corresponding to the specified value.
	 * 
	 * @param value
	 */
	public static Archive valueOf(char value) {
		for (Archive a: values()) {
			if (a.getValue()==value) {
				return a;
			}
		}
		return null;
	}

}
