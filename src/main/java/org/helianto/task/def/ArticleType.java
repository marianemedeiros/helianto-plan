package org.helianto.task.def;

/**
 * Tipos de artigo.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ArticleType {
	
	MESSAGE('M'),
	REQUEST('R'),
	INVITATION('I'),
	FORUM('F'),
	BLOG('B');
	
	private ArticleType(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor do tipo de artigo.
	 */
	public char getValue() {
		return value;
	}

}
