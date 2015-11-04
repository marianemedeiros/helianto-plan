package org.helianto.task.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.core.internal.SimpleCounter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Abstract read adapter.
 * 
 * @author mauriciofernandesdecastro
 */
public class AbstractReadAdapter 
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private int id;

	@JsonSerialize
	private int countItems;
	
	@JsonSerialize
	private int countAlerts;
	
	@JsonSerialize
	private int countWarnings;
	
	@JsonSerialize
	private int countOthers;
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public AbstractReadAdapter(int id) {
		super();
		this.id = id;
	}
	
	public AbstractReadAdapter() {
		super();
	}
	
	public int getId() {
		return id;
	}
	protected void setId(int id) {
		this.id = id;
	}

	public int getCountItems() {
		return countItems;
	}
	public void setCountItems(int countItems) {
		this.countItems = countItems;
	}

	public int getCountAlerts() {
		return countAlerts;
	}
	public void setCountAlerts(int countAlerts) {
		this.countAlerts = countAlerts;
	}

	public int getCountWarnings() {
		return countWarnings;
	}
	public void setCountWarnings(int countWarnings) {
		this.countWarnings = countWarnings;
	}

	public int getCountOthers() {
		return countOthers;
	}
	public void setCountOthers(int countOthers) {
		this.countOthers = countOthers;
	}
	
	/**
	 * Update count of items.
	 * 
	 * @param counterList
	 */
	@JsonIgnore
	public AbstractReadAdapter setCountItems(List<? extends SimpleCounter> counterList) {
		for (SimpleCounter simpleCounter: counterList) {
			if ((Integer) simpleCounter.getBaseClass()==getId()) {
				setCountItems((int) simpleCounter.getItemCount());
				break;
			}
		}
		return this;
	}
	
	/**
	 * Update count of alerts.
	 * 
	 * @param counterList
	 */
	@JsonIgnore
	public AbstractReadAdapter setCountAlerts(List<? extends SimpleCounter> counterList) {
		for (SimpleCounter simpleCounter: counterList) {
			if ((Integer) simpleCounter.getBaseClass()==getId()) {
				setCountAlerts((int) simpleCounter.getItemCount());
				break;
			}
		}
		return this;
	}
	
	/**
	 * Update count of warnings.
	 * 
	 * @param counterList
	 */
	@JsonIgnore
	public AbstractReadAdapter setCountWarnings(List<? extends SimpleCounter> counterList) {
		for (SimpleCounter simpleCounter: counterList) {
			if ((Integer) simpleCounter.getBaseClass()==getId()) {
				setCountWarnings((int) simpleCounter.getItemCount());
				break;
			}
		}
		return this;
	}
	
	/**
	 * Update count of others.
	 * 
	 * @param counterList
	 */
	@JsonIgnore
	public AbstractReadAdapter setCountOthers(List<? extends SimpleCounter> counterList) {
		for (SimpleCounter simpleCounter: counterList) {
			if ((Integer) simpleCounter.getBaseClass()==getId()) {
				setCountOthers((int) simpleCounter.getItemCount());
				break;
			}
		}
		return this;
	}

	
}
