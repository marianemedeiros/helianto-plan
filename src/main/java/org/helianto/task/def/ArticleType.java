package org.helianto.task.def;

/**
 * Articles types.
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
	 * Value of type of article.
	 */
	public char getValue() {
		return value;
	}

}
