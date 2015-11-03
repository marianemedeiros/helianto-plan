/* Copyright 2005 I Serv Consultoria Empresarial Ltda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.helianto.task.def;

/**
 * Sets defaults values by the delay tracker.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum DelayTracker {
    
    /**
     * Updated.
     */
    ON_SCHEDULE('O', false),
    /**
     * Late.
     */
    LATE('L', true),
    /**
     * Very late.
     */
    TOO_LATE('T', true),
    /**
     * Not started.
     */
    NOT_STARTED('N', false),
    /**
     * Overdue.
     */
    PAST_DUE_DATE('P', true),
    /**
     * ready to start.
     */
    READY_TO_START('R', false),
    /**
     * Late to the beginning.
     */
    LATE_TO_START('S', true),
    /**
     * Invalid.
     */
    INVALID('I', false);
    
    private char value;
    private boolean late;
    
    private DelayTracker(char value, boolean late) {
        this.value = value;
        this.late = late;
    }
    
    public char getValue() {
        return this.value;
    }
    public boolean isLate() {
        return this.late;
    }
    public static DelayTracker getValue(char value) {
    	for (DelayTracker delayTracker: values()) {
    		if (delayTracker.getValue()==value) {
    			return delayTracker;
    		}
    	}
    	return null;
    }
    public static boolean isLate(char value) {
    	return getValue(value).late;
    }

}
