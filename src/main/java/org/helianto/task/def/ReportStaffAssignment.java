package org.helianto.task.def;

/**
 * Assignment.
 * 
 * @author mauriciofernandesdecastro
 * @deprecated
 */
public enum ReportStaffAssignment {
	
    /**
     * Proprietário (ou Product Owner).
     */
    OWNER('0'),
    /**
     * Líder (ou Scrum Master).
     */
    LEADER('1'),
    /**
     * Membro da equip (Pig).
     */
    TEAM('2'),
    /**
     * Participante (Chicken).
     */
    PARTICIPANT('3'),
    /**
     * Outros, cientes.
     */
    VIEWER('4');
    
    private char value;
    
    private ReportStaffAssignment(char value) {
        this.value = value;
    }
    public char getValue() {
        return this.value;
    }

}
