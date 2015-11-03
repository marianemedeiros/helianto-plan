package org.helianto.task.def;

import java.util.Date;

import javax.persistence.Transient;

import org.helianto.core.def.Resolution;

/**
 * In study.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ControlTarget {

    /**
     * <<Transient>> Method recommended to finish a control.
     * <p>
     * Update a resolution to Resolution.DONE, put progress in 100% and
     * the approval flow in the last phase.
     * </p>
     * 
     * @param actualEndDate
     */
    @Transient
    void close(Date actualEndDate);
        
    /**
     * <<Transient>> Method to evaluate the report.
     * 
     * <p>
     * Update the approval flow to next phase, update progress according to the evaluation.
     * </p>
     */
    @Transient
    boolean forward();
    
    /**
     * <<Transient>> Method recommended to progress the control.
     * 
     * <p>
     * Update approval flow to the indicated phase, update progress according to evaluation.
     * </p>
     * <p>
     * In special case when report does not yet started, start it.
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
     * <<Transient>> Method to cancel control.
     * 
     * <p>
     * Update the resolution to Resolution.DONE, put progress in 0%, 
     * approval flow in the last phase and indicate cancellation through the 
     * execution state.
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
