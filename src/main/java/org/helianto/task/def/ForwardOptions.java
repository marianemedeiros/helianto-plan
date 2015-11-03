package org.helianto.task.def;

/**
 * Routing options.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ForwardOptions {
	
	/**
	 * Routing not required (default).
	 */
	NOT_REQUIRED('N'),
	/**
	 * Routing like a attach document in a report.
	 */
	REPORT_DOCUMENT('R'),
	/**
	 * Routing like evaluation to an user attached to a report.
	 */
	REPORT_ASSESSMENT('A'),
	/**
	 * Routing like maintenance order.
	 */
	MAINTENANCE('M');
	
	private ForwardOptions(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Enumeration value.
	 */
	public char getValue() {
		return value;
	}

}
