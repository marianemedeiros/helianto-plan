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
 * Sets actions arising from an accompaniment.
 * 
 * <p>Actions follow 8D method.</p>
 * <ul>
 * <li>Identification: given in the report</li>
 * <li>Team: reporter, attendees.</li>
 * <li>Immediate action: when add inclination, given in occurrence.</li>
 * <li>REVIEW: Criticism analysis. </li>
 * <li>CAUSE: cause</li>
 * <li>ACTION_PLAN: action</li>
 * <li>ACTION_RESULT: action result</li>
 * <li>EFFECTIVENESS: effectiveness</li>
 * <li>IMPACT: impact and coverage</li>
 * <li>DEPLOYMENT: closing and communication.</li>
 * </ul>
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum FollowUpAction {

    /**
     * Critical Analysis.
     */
	REVIEW('R'),
    /**
     * Investigation of cause.
     */
	CAUSE('C'),
    /**
     * Action planning.
     */
	ACTION_PLAN('P'),
    /**
     * Action result.
     */
	ACTION_RESULT('A'),
    /**
     * Effectiveness verification.
     */
	EFFECTIVENESS('E'),
    /**
     * Impact.
     */
	IMPACT('I'),
    /**
     * Closing and communication.
     */
	DEPLOYMENT('D');
    
    private char value;
    
    private FollowUpAction(char value) {
        this.value = value;
    }
    
    public char getValue() {
        return this.value;
    }

}
