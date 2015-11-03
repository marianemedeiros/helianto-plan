package org.helianto.task.def;

import java.util.Date;

import javax.persistence.Transient;

import org.helianto.core.def.Resolution;

/**
 * Em estudo.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ControlTarget {

    /**
     * <<Transient>> Método recomendado para encerrar um controle.
     * 
     * <p>
     * Atualiza a resolução para Resolution.DONE, coloca o progresso em 100% e 
     * o fluxo de aprovação na éltima fase.
     * </p>
     * 
     * @param actualEndDate
     */
    @Transient
    void close(Date actualEndDate);
        
    /**
     * <<Transient>> Método recomendado para avaéar o relatório.
     * 
     * <p>
     * Atualiza o fluxo de aprovação para a próxima fase, atualiza o 
     * progresso de acordo com o avanão.
     * </p>
     */
    @Transient
    boolean forward();
    
    /**
     * <<Transient>> Método recomendado para avançar o controle.
     * 
     * <p>
     * Atualiza o fluxo de aprovação para a fase indicada, atualiza o 
     * progresso de acordo com o avanão.
     * </p>
     * <p>
     * No caso especial em que o relatório ainda não havia sido iniciado, inicia.
     * </p>
     * 
     * @param nextWorkflowPhase
     */
    @Transient
    public boolean forward(int nextWorkflowPhase);
    
//    {
//    	if (nextWorkflowPhase<=getWorkflowSize()) {
//			if (getResolution()==Resolution.PRELIMINARY.getValue()) {
//				setResolution(Resolution.TODO.getValue());
//			}
//    		setWorkflowPhase(nextWorkflowPhase);
//    		setComplete((getWorkflowPhase() * 100) / (getWorkflowSize() + 1));
//    		return true;
//    	}
//    	return false;
//    }
    
    /**
     * <<Transient>> Método recomendado para cancelar o controle.
     * 
     * <p>
     * Atualiza a resolução para Resolution.DONE, coloca o progresso em 0%, 
     * o fluxo de aprovação na éltima fase e indica cancelamento através do 
     * estado de execução.
     * </p>
     * 
     * @param actualCancelDate
     */
    @Transient
    public void cancel(Date actualCancelDate);
        
	void setResolutionAsEnum(Resolution resolution);
	
	void setComplete(int progress);
	
	void setWorkflowPhase(int workflowPhase);
	
	void setRunState(int runState);
	
	void setNextCheckDate(Date nextCheckDate);

	void setActualEndDate(Date actualEndDate);
}
