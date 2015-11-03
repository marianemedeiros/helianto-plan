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
 * Define plans orders.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum PlanOrder {

    /**
     * Order by next check date.
     */
	NEXT_CHECK_DATE('C', "nextCheckDate DESC"),
    /**
     * Order by issue date.
     */
	ISSUE_DATE('I', "issueDate DESC"),
    /**
     * Order by category code.
     */
	CATEGORY('T', "category.categoryCode ASC");
    
    private char value;
    private String orderString;
    
    private PlanOrder(char value, String orderString) {
        this.value = value;
        this.orderString = orderString;
    }
    public char getValue() {
        return this.value;
    }
    public String getOrderString() {
        return this.orderString;
    }

}
