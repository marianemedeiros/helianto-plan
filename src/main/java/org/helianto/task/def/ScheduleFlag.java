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
 * Define how the <code>Task</code> has been re-scheduled.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ScheduleFlag {
    
    /**
     * Start and finish schedule have not changed.
     */
    ORIGINAL('O'),
    /**
     * Start schedule has changed.
     */
    START_CHANGED('S'),
    /**
     * Finish schedule has changed.
     */
    FINISH_CHANGED('F'),
    /**
     * Start and finish schedule have changed.
     */
    BOTH_CHANGED('B');
    
    private char value;
    
    private ScheduleFlag(char value) {
        this.value = value;
    }
    public char getValue() {
        return this.value;
    }

}
