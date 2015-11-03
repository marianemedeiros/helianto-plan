package org.helianto.task.def;

/**
 * Staff role.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ReportStaffRole {
	
    /**
     * Business leader (or product owner).
     */
    OWNER('0'),
    /**
     * Technical leader (or Scrum Master).
     */
    LEADER('1'),
    /**
     * Staff member (Pig).
     */
    TEAM('2'),
    /**
     * Observer (Chicken).
     */
    OBSERVER('3'),
    /**
     * Other.
     */
    OTHER('4');
    
    private char value;
    
    private ReportStaffRole(char value) {
        this.value = value;
    }
    public char getValue() {
        return this.value;
    }

}
