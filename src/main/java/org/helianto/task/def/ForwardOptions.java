package org.helianto.task.def;

/**
 * Opções de encaminhamento.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ForwardOptions {
	
	/**
	 * Encaminhamento não requerido (default).
	 */
	NOT_REQUIRED('N'),
	/**
	 * Encaminhar como documento anexo a um relatório.
	 */
	REPORT_DOCUMENT('R'),
	/**
	 * Encaminhar como avaliação para um usuário anexo a um relatório.
	 */
	REPORT_ASSESSMENT('A'),
	/**
	 * Encaminhar como ordem de manutenção.
	 */
	MAINTENANCE('M');
	
	private ForwardOptions(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor da enumeração.
	 */
	public char getValue() {
		return value;
	}

}
