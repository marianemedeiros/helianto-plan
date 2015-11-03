package org.helianto.task.def;

import javax.persistence.Transient;

/**
 * Interface para classes de doménio sujeitas a controle de fluxo de aprovação.
 * 
 * @author mauriciofernandesdecastro
 */
public interface WorkflowTarget {
	
    /**
     * <<Transient>> Determina, através da lista de responsabilidades, o tamanho da lista
     * de aprovação do workflow.
     */
    @Transient
    int getWorkflowSize();
    
    /**
     * <<Transient>> Determina, através do tamanho da lista de responsabilidades, se o workflow 
     * é requerido.
     */
    @Transient
    boolean isWorkflowRequired();
    
	/**
	 * Fase atual do fluxo de aprovação.
	 */
	int getWorkflowPhase();

    /**
     * <<Transient>> Quando o workflow é requerido, somente pode ser fechado se o
     * fluxo estiver em sua éltima fase.
     */
    @Transient
    boolean isWorkflowClosable();
    
}
