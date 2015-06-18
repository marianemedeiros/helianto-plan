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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private DateFormat dateFormat;
	boolean dirty;
	private Calendar calendar;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	
    /**
     * This is shorthand for
     * <code>new DateParser(new Date())</code>.
     */
	public DateParser() {
		this(new Date());
	}
	
	public DateParser(Date date) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dirty = false;
		calendar = dateFormat.getCalendar();
		calendar.setTime(date);
		year = String.valueOf(calendar.get(Calendar.YEAR));
		month = String.valueOf(calendar.get(Calendar.MONTH));
		day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(calendar.get(Calendar.MINUTE));
		second = String.valueOf(calendar.get(Calendar.SECOND));
	}
	
	protected void parse() {
		if (!dirty) {
			return;
		}
		try {
			dateFormat.parse(getSourceString());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unable to parse ["+getSourceString()+"]", e);
		}
		dirty = false;
	}
	
	protected String getSourceString() {
		return new StringBuilder()
	    .append(year)
	    .append("-")
	    .append(month)
	    .append("-")
	    .append(day)
	    .append(" ")
	    .append(hour)
	    .append(":")
	    .append(minute)
	    .append(":")
	    .append(second)
	    .toString(); 
	}
	
	public Date getDate() {
		return calendar.getTime();
	}
	
	public String getFormatedDate() {
		return dateFormat.format(calendar.getTime());
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		parse();
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		if (!year.equals(this.year)) {
			dirty = true;
			this.year = year;
		}
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		parse();
		return String.valueOf(calendar.get(Calendar.MONTH));
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		if (!month.equals(this.month)) {
			dirty = true;
			this.month = month;
		}
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		parse();
		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}
	/**
	 * @param date the day to set
	 */
	public void setDay(String day) {
		if (!day.equals(this.day)) {
			dirty = true;
			this.day = day;
		}
	}
	/**
	 * @return the hour
	 */
	public String getHour() {
		parse();
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		if (!hour.equals(this.hour)) {
			dirty = true;
			this.hour = hour;
		}
	}
	/**
	 * @return the minute
	 */
	public String getMinute() {
		parse();
		return String.valueOf(calendar.get(Calendar.MINUTE));
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(String minute) {
		if (!minute.equals(this.minute)) {
			dirty = true;
			this.minute = minute;
		}
	}
	/**
	 * @return the second
	 */
	public String getSecond() {
		parse();
		return String.valueOf(calendar.get(Calendar.SECOND));
	}
	/**
	 * @param second the second to set
	 */
	public void setSecond(String second) {
		if (!second.equals(this.second)) {
			dirty = true;
			this.second = second;
		}
	}
	

}
