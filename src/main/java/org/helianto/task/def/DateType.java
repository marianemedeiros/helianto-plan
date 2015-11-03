package org.helianto.task.def;

/**
 * @author Mauricio Fernandes de Castro
 */
public enum DateType {

    ISSUE('I'),
    SCHEDULED_START('S'),
    SCHEDULED_END('E');
    
    private char value;

    private DateType(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

}
