package org.helianto.task.def;

/**
 * Definição de prioridades.
 * 
 * @author mauriciofernandesdecastro
 */
public enum Priority {
	
	EXTREMELY_URGENT('X'),
	URGENT('U'),
	VERY_IMPORTANT('0'),
	IMPORTANT('1'),
	ORDINARY('2'),
	OPTIONAL('3');
	
	private Priority(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor literal.
	 */
	public char getValue() {
		return value;
	}

}
