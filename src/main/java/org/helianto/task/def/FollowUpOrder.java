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
 * Enumerate possible forms of ordination of the accompaniments.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum FollowUpOrder {
    
    /**
     * Last data.
     */
    LAST_DATE_ON_TOP('L', -1),
    /**
     * First data.
     */
    FIRST_DATE_ON_TOP('F', 1);
    
    private char value;
    private int signal;
    
    private FollowUpOrder(char value, int signal) {
        this.value = value;
        this.signal = signal;
    }
    
    public char getValue() {
        return this.value;
    }

    public int getSignal() {
        return this.signal;
    }

}
