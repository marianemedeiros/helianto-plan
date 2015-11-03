package org.helianto.task.def;

import javax.persistence.Transient;

/**
 * Interface to class of domain subject to a flow approval control.
 * 
 * @author mauriciofernandesdecastro
 */
public interface WorkflowTarget {
	
    /**
     * <<Transient>> Define, through a list of responsibilities, the list size approval workflow.
     */
    @Transient
    int getWorkflowSize();
    
    /**
     * <<Transient>> Define, through a list of responsibilities, if workflow is required. 
     * 
     */
    @Transient
    boolean isWorkflowRequired();
    
	/**
	 * Current phase of approval flow.
	 */
	int getWorkflowPhase();

    /**
     * <<Transient>> When workflow is required, just can be closed if flow are in your last phase. 
     */
    @Transient
    boolean isWorkflowClosable();
    
}
