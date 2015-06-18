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


package org.helianto.task;

import java.io.Serializable;
import java.util.Date;

/**
 * Convenience to filter dates.
 * 
 * @author Mauricio Fernandes de Castro
 */
public class DateFilterSegment implements Serializable {

	private static final long serialVersionUID = 1L;
	private String segmentName;
	private Date fromDate;
	private Date toDate;
	
	public DateFilterSegment() {
	}

	public DateFilterSegment(String segmentName, Date fromDate, Date toDate) {
		setSegmentName(segmentName);
		setFromDate(fromDate);
		setToDate(toDate);
	}

	/**
	 * Segment name.
	 */
	public String getSegmentName() {
		return segmentName;
	}
	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	/**
	 * Date start.
	 */
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Date end.
	 */
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	/**
	 * Date start and end.
	 */
	public void changeDates(Date fromDate, Date toDate) {
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return new StringBuilder()
		.append("Field ")
		.append(getSegmentName())
		.append(", date from ")
		.append(getFromDate())
		.append(" to ")
		.append(getToDate())
		.toString();
	}

}
