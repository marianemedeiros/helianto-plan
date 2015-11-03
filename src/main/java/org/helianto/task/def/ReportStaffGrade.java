package org.helianto.task.def;

/**
 * Staff level of knowledge.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ReportStaffGrade {
	
	STARTER('1'),
	BASIC('2'),
	MEDIUM('3'),
	FULL('4'),
	ADVANCED('5'),
	COMPLEX('6');
	
	private ReportStaffGrade(char value) {
		this.value = value;
	}
	
	private char value;
	
	public char getValue() {
		return value;
	}

}
