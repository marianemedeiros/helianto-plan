package org.helianto.task.repository;

import org.helianto.core.domain.Category;
import org.helianto.core.repository.ItemCounter;

/**
 * Count latest by category.
 * 
 * @author mauriciofernandesdecastro
 */
public class ReportCategoryCounter 
	extends ItemCounter<Category>
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public ReportCategoryCounter() {
		super();
	}
	
	/**
	 * Constructor.
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
