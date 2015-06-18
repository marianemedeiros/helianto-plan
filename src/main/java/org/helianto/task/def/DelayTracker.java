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
 * Define valores assumidos pelo rastreador de atraso.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum DelayTracker {
    
    /**
     * Em dia.
     */
    ON_SCHEDULE('O', false),
    /**
     * Atrasado.
     */
    LATE('L', true),
    /**
     * Muito atrasado.
     */
    TOO_LATE('T', true),
    /**
     * Néo iniciado
     */
    NOT_STARTED('N', false),
    /**
     * Vencido
     */
    PAST_DUE_DATE('P', true),
    /**
     * Pronto para começar
     */
    READY_TO_START('R', false),
    /**
     * Atrasado para o inécio
     */
    LATE_TO_START('S', true),
    /**
     * Invélido
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
