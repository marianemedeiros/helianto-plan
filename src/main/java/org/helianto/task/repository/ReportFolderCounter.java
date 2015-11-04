package org.helianto.task.repository;

import org.helianto.core.repository.ItemCounter;

import org.helianto.task.domain.ReportFolder;

/**
 * Count latest by folder.
 * 
 * @author mauriciofernandesdecastro
 */
public class ReportFolderCounter 
	extends ItemCounter<ReportFolder>
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public ReportFolderCounter() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param reportFolder
	 * @param instrumentCount
	 */
	public ReportFolderCounter(ReportFolder reportFolder, long instrumentCount) {
		this();
		setBaseClass(reportFolder);
		setItemCount(instrumentCount);
	}
	
}
