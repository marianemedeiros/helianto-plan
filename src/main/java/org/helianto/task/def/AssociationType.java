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
 * Define como as tarefas associam-se entre si.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum AssociationType {
    
    /**
     * Start to start.
     */
    SS('0', false, false, false),
    /**
     * Start to finish.
     */
    SF('1', false, false, false),
    /**
     * Finish to start.
     */
    FS('2', false, false, false),
    /**
     * Finish to finish.
     */
    FF('3', false, false, false),
    /**
     * Ação de contenção.
     */
    CONTAINMENT('C', true, false, false),
    /**
     * Ação de disposição.
     */
    DISPOSITION('D', true, false, false),
    /**
     * Ação permanente.
     */
    PERMANENT('P', false, true, false),
    /**
     * Ação permanente.
     */
    RECURRENCE('R', false, false, true),
    /**
     * Parent-child.
     */
    PARENT_CHILD('9', false, false, false);
    
    private char value;
    private boolean immediate;
    private boolean permanent;
    private boolean recurrencePrevention;
    
    private AssociationType(char value, boolean immediate, boolean permanent, boolean recurrencePrevention) {
        this.value = value;
        this.immediate = immediate;
        this.permanent = permanent;
        this.recurrencePrevention = recurrencePrevention;
    }
    public char getValue() {
        return this.value;
    }
    public boolean isImmediate() {
        return this.immediate;
    }
    public boolean isPermanent() {
        return this.permanent;
    }
    public boolean isRecurrencePrevention() {
        return this.recurrencePrevention;
    }
    public static AssociationType getValues(char value) {
    	for(AssociationType associationType: values()) {
    		if (associationType.getValue()==value) {
    			return associationType;
    		}
    	}
    	return null;
    }
    

}
