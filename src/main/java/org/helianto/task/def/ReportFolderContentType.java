package org.helianto.task.def;

/**
 * Define report folders type.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ReportFolderContentType {
	
	/**
	 * Folders show in menu 'Portfolio' 
	 */
	PORTFOLIO('P'),
	/**
	 * Folders show in menu 'Campaign'
	 */
	CAMPAIGN('C'),
	/**
	 * Folders show in menu 'Production process'
	 */
	PROCESS('R');
	
	private ReportFolderContentType(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * 
	 * @return
	 */
	public char getValue() {
		return value;
	}

}
