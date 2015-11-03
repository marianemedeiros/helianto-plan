package org.helianto.task.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.ReportPhase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *  ReportPhase repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ReportPhaseRepository 
extends JpaRepository<ReportPhase, Serializable> {
	/**
	 * Find by natural key.
	 * 
	 * @param reportFolder
	 * @param literal
	 */

	ReportPhase findByReportFolderAndLiteral(ReportFolder reportFolder, char literal);


	@Query(
			"select new "
			+ "org.helianto.task.repository.ReportPhaseAdapter"
			+ "( reportPhase.id" 
			+ ", reportPhase.reportFolder.id" 
			+ ", reportPhase.literal" 
			+ ", reportPhase.phaseName" 
			+ ", reportPhase.content" 
			+ ", reportPhase.encoding" 
			+ ", reportPhase.estimate" 
			+ ", reportPhase.scheduledStartDate" 
			+ ", reportPhase.scheduledEndDate" 
			+ ") "
			+ "from ReportPhase reportPhase "
			+ "where reportPhase.reportFolder.id = ?1 "
			)
	Page<ReportPhaseAdapter> findByReportFolderId(Integer reportId, Pageable page);
	
	@Query(
			"select new "
			+ "org.helianto.task.repository.ReportPhaseAdapter"
			+ "( reportPhase.id" 
			+ ", reportPhase.reportFolder.id" 
			+ ", reportPhase.literal" 
			+ ", reportPhase.phaseName" 
			+ ", reportPhase.content" 
			+ ", reportPhase.encoding" 
			+ ", reportPhase.estimate" 
			+ ", reportPhase.scheduledStartDate" 
			+ ", reportPhase.scheduledEndDate" 
			+ ") "
			+ "from ReportPhase reportPhase "
			+ "where reportPhase.reportFolder.id = ?1 "
			)
	List<ReportPhaseAdapter> findByReportFolderId(Integer reportId);
	
	@Query(
			"select new "
			+ "org.helianto.task.repository.ReportPhaseAdapter"
			+ "( reportPhase.id" 
			+ ", reportPhase.reportFolder.id" 
			+ ", reportPhase.literal" 
			+ ", reportPhase.phaseName" 
			+ ", reportPhase.content" 
			+ ", reportPhase.encoding" 
			+ ", reportPhase.estimate" 
			+ ", reportPhase.scheduledStartDate" 
			+ ", reportPhase.scheduledEndDate" 
			+ ") "
			+ "from ReportPhase reportPhase "
			+ "where reportPhase.id = ?1 "
			)
	ReportPhaseAdapter findById(Integer id);

	
}
