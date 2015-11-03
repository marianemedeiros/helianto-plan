package org.helianto.task.repository;

import org.helianto.core.domain.Category;
import org.helianto.core.repository.ItemCounter;

/**
 * Contador para atrasos por categoria.
 * 
 * @author mauriciofernandesdecastro
 */
public class ReportCategoryCounter 
	extends ItemCounter<Category>
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor.
	 */
	public ReportCategoryCounter() {
		super();
	}
	
	/**
	 * Construtor.
	 * 
	 * @param category
	 * @param reportCount
	 */
	public ReportCategoryCounter(Category category, long reportCount) {
		this();
		setBaseClass(category);
		setItemCount(reportCount);
	}
	
}
