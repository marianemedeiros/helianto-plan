package org.helianto.task.def;

/**
 * Define tipos de pastas de relatórios.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ReportFolderContentType {
	
	/**
	 * Pastas mostradas no menu 'Portfolio'
	 */
	PORTFOLIO('P'),
	/**
	 * Pastas mostradas no menu 'Campanha'
	 */
	CAMPAIGN('C'),
	/**
	 * Pastas mostradas no menu 'Processo de produção'
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
