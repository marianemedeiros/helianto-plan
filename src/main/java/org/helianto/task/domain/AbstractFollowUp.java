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

package org.helianto.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.helianto.core.internal.AbstractEventControl;
import org.helianto.task.def.NotificationOption;
import org.helianto.task.def.ReviewDecision;

/**
 * Base class to follow up.
 * 
 * @author Mauricio Fernandes de Castro
 */
@MappedSuperclass
public abstract class AbstractFollowUp 
	extends AbstractEventControl 
	implements Comparable<AbstractFollowUp> 
{

    private static final long serialVersionUID = 1L;
    
    @Column(length=512)
    private String followUpDesc;
    
    private char decision;
    
    private char notificationOption;

    /** 
     * Default constructor.
     */
    protected AbstractFollowUp() {
    	super();
    	setNotificationOptionAsEnum(NotificationOption.REPORTER);
    }

    /**
     * Description.
     */
    public String getFollowUpDesc() {
        return this.followUpDesc;
    }
    public void setFollowUpDesc(String followUpDesc) {
        this.followUpDesc = followUpDesc;
    }

    /**
     * Notification option.
     */
    public char getNotificationOption() {
    	return this.notificationOption;
    }
    public void setNotificationOption(char notificationOption) {
    	this.notificationOption = notificationOption;
    }
    public void setNotificationOptionAsEnum(NotificationOption notificationOption) {
    	this.notificationOption = notificationOption.getValue();
    }
    
    /**
     * Decison getter.
     */
    public char getDecision() {
        return this.decision;
    }
    public void setDecision(char decision) {
        this.decision = decision;
    }
    
    /**
     * Decision as enum.
     */
//    @Transient
    public ReviewDecision getDecisionAsEnum() {
    	for (ReviewDecision d: ReviewDecision.values()) {
    		if (d.getValue()==getDecision()) {
    			return d;
    		}
    	}
    	throw new IllegalArgumentException("Invalid decision.");
    }
    public void setDecisionAsEnum(ReviewDecision decision) {
        this.decision = decision.getValue();
    }
    //TODO
//    /**
//     * The control target.
//     */
////    @Transient
//    public abstract ControlSource getSubject(); 
//    
////    @Transient
//    public void update() {
//    	setResolutionAsEnum(getDecisionAsEnum().getNextResolution());
//    	getSubject().setResolution(getResolution());
//    }
//    
////    @Transient
//    public void update(Date nextCheckDate) {
//    	getSubject().setComplete(getComplete());
//    	getSubject().setNextCheckDate(nextCheckDate);
//    }
    
    /**
     * @see Comparable interface, descending order
     */
    public int compareTo(AbstractFollowUp nextFollowUp) {
        return (int) (-getIssueDate().getTime() + nextFollowUp.getIssueDate().getTime());
    }

}
