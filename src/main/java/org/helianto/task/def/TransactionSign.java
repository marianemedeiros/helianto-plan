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

import java.math.BigDecimal;

/**
 * Signals to transaction.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum TransactionSign {
	
	LOAD('+', 1),
	
	UNLOAD('-', -1);
	
	private TransactionSign(char value, int multiplier) {
		this.value = value;
		this.multiplier = new BigDecimal(multiplier);
	}
	
	private char value;
	private BigDecimal multiplier;
	
	public final char getValue() {
		return this.value;
	}

	public final BigDecimal getMultiplier() {
		return this.multiplier;
	}

	public static BigDecimal getMultiplier(char value) {
		if (value==LOAD.getValue()) {
			return LOAD.getMultiplier();
		}
		if (value==UNLOAD.getValue()) {
			return UNLOAD.getMultiplier();
		}
		throw new IllegalArgumentException("Invalid value "+value+" passed.");
	}

}
