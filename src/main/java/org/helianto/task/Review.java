package org.helianto.task;

import java.util.Date;

import org.helianto.core.Controllable;

/**
 * Review interface.
 * 
 * @param <S> sujeito da avaliação.
 * 
 * @author mauriciofernandesdecastro
 */
public interface Review<S extends ControlSource> extends Controllable {
	
	Date getIssueDate();
	
	/**
	 * Sujeito da avaliação.
	 */
	S getSubject();
	
	/**
	 * Verdadeiro se a avaliação pode ser realizada.
	 */
	boolean isValid();

	/**
	 * Deciséo.
	 */
	char getDecision();
	
	/**
	 * Atualiza a resolução no sujeito.
	 */
	void update();

	/**
	 * Atualiza a próxima verificação no sujeito.
	 * 
	 * @param nextCheckDate
	 */
	void update(Date nextCheckDate);

}
