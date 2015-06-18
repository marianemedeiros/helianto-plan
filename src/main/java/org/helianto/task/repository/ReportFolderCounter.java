package org.helianto.task.repository;

import org.helianto.core.repository.ItemCounter;

import org.helianto.task.domain.ReportFolder;

/**
 * Contador para atrasos por pasta.
 * 
 * @author mauriciofernandesdecastro
 */
public class ReportFolderCounter 
	extends ItemCounter<ReportFolder>
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtotr.
	 */
	public ReportFolderCounter() {
		super();
	}
	
	/**
	 * Construtor.
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
