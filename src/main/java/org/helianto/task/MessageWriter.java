package org.helianto.task;

/**
 * Interface para colaboradores que escrevem mensagens.
 * 
 * @author mauriciofernandesdecastro
 */
public interface MessageWriter {
	
	/**
	 * Escreve a mensagem como html.
	 * 
	 * @param params
	 */
	String writeMessageAsHtml(Object... params);

	/**
	 * Escreve a mensagem como texto.
	 * 
	 * @param params
	 */
	String writeMessageAsText(Object... params);

}
