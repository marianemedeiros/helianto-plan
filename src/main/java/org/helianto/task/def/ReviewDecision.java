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

import org.helianto.core.def.ResolutionExtended;

/**
 * Define <code>Review</code> decisions.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ReviewDecision {
	
    /**
     * Justify.
     */
	JUSTIFY('J', ResolutionExtended.UNFOUNDED),
    /**
     * Start.
     */
	START('S', ResolutionExtended.TODO),
    /**
     * Stage (before actually start).
     */
	STAGE('A', ResolutionExtended.PRELIMINARY),
    /**
     * Suspend if running.
     */
	SUSPEND('W', ResolutionExtended.WAIT),
    /**
     * Continue.
     */
	CONTINUE('G', ResolutionExtended.TODO),
    /**
     * RESCHEDULE if waiting.
     */
	RESCHEDULE('E', ResolutionExtended.TODO),
    /**
     * CLOSE and keep the progress as it is, if active.
     */
	CLOSE('C', ResolutionExtended.DONE),
    /**
     * FINISH to force to 100% and close if active.
     */
	FINISH('F', ResolutionExtended.DONE),
    /**
     * CANCEL .
     */
	CANCEL('X', ResolutionExtended.CANCELLED),
    /**
     * REOPEN if not active.
     */
	REOPEN('R', ResolutionExtended.TODO),
    /**
     * Reassess if unfounded.
     */
	REASSESS('B', ResolutionExtended.ISSUED),
    /**
     * Revert if cancelled.
     */
	REVERT('V', ResolutionExtended.TODO);
    
    private char value;
    private ResolutionExtended nextResolution;
    
    private ReviewDecision(char value, ResolutionExtended nextResolution) {
        this.value = value;
        this.nextResolution = nextResolution;
    }
    
    public char getValue() {
        return this.value;
    }
    
    public ResolutionExtended getNextResolution() {
		return nextResolution;
	}

}
