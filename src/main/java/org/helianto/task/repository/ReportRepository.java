package org.helianto.task.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.helianto.core.domain.Entity;
import org.helianto.task.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Report repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ReportRepository 
	extends	JpaRepository<Report, Serializable> {

	/**
	 * Find by natural key.
	 * 
	 * @param entity
	 * @param reportCode
	 */
	Report findByEntityAndReportCode(Entity entity, String reportCode);
	
	/**
	 * Find open reports.
	 */
	@Query("select distinct report from Report report " + 
    			"where report.resolution = :resolution " +
    			"and report.frequency > 0 " +
    			"and report.nextCheckDate < current_date() " +
    			"order by report.reporter.id, report.owner.id ")
	List<Report> findToDate(@Param("resolution") char resolution);

	public static final String QUERY = "SELECT new "
			+ "org.helianto.task.domain.Report"
			+ "(report.id"
			+ ", report.internalNumber"
			+ ", report.reportCode"
			+ ", report.summary"
			+ ", report.resolution"
			+ ", report.nextCheckDate"
			+ ", report.taskDesc"
			+ ", report.category.id"
			+ ", report.reportContent.content"
			+ ", report.relativeSize"
			+ ", report.priority"
			+ ", report.frequency"
			+ ", report.series.id"
			+ ", report.phase"
			+ ") "
			+ " FROM Report report ";
	
	/**
	 * Find by reportFolderId on phase.
	 * 
	 * @param reportFolderId
	 * @param phases
	 * @param pageable
	 */
	@Query(QUERY
			+ "WHERE report.series.id = ?1 "
			+ "AND report.phase in ?2")
	Page<Report> findBySeries_IdOnPhase(int reportFolderId, char[] phases, Pageable pageable);
	
	/**
	 * 
	 * Find by reportFolderId.
	 * 
	 * @param reportFolderId
	 * @param pageable
	 */
	@Query(QUERY
			+ "WHERE report.series.id = ?1 ")
	Page<Report> findBySeries_Id(int reportFolderId, Pageable pageable);

	/**
	 * Relatórios em aberto, ou seja, não encerrados (D), não suspensos (W) 
	 * ou não cancelados (X).
	 * Ta
	 * @param reportFolderId
	 * @param checkDate
	 */
	@Query("select distinct report.id from Report report " + 
    			"where report.series.id = ?1 " +
    			"and report.nextCheckDate between report.issueDate and ?2 " +
    			"and report.resolution not in ('D', 'W', 'X') " +
    			"order by report.id ")
	List<Integer> findLateBySeriesId(int reportFolderId, Date checkDate);

	
	/**
	 * Find by report code or summary like.
	 * 
	 * @param reportCode
	 * @param summary
	 */
	List<Report> findByReportCodeLikeOrSummaryLike(String reportCode, String summary);
	
	/**
	 * Find by report code or summary like.
	 * 
	 * @param reportCode
	 * @param summary
	 */
	List<Report> findByReportCodeOrSummary(String reportCode, String summary);
	
	/**
	 * Count by folder.
	 * 
	 * @param entity
	 */
	@Query("select new org.helianto.task.repository.ReportFolderCounter(report.series, count(report)) " +
			"from Report report " +
			"where report.entity = ?1 " +
			"group by report.series ")
	List<ReportFolderCounter> countByReportFolder(Entity entity);

	/**
	 * Count.
	 * 
	 * @param entity
	 */
	@Query("select count(report) " +
			"from Report report " +
			"where report.entity = ?1 ")
	long count(Entity entity);

	// TODO confirm and remove countByCategory
	//
	// Use reportStatsRepository
	//
	
	/**
	 * Count by category.
	 * 
	 * @param entity
	 * @deprecated
	 */
	@Query("select new org.helianto.task.repository.ReportCategoryCounter(report.series.category, count(report)) " +
			"from Report report " +
			"where report.entity = ?1 " +
			"group by report.series.category")
	List<ReportCategoryCounter> countByCategory(Entity entity);

	// TODO confirm and remove countLateByCategory
	//
	// Use reportStatsRepository
	//
	
	/**
	 * Count late by category.
	 * 
	 * @param entity
	 * @param checkDate
	 * @deprecated
	 */
	@Query("select new org.helianto.task.repository.ReportCategoryCounter(report.series.category, count(report)) " +
			"from Report report " +
			"where report.entity = ?1 " +
			"and report.nextCheckDate between report.issueDate and ?2 " +
			"and report.resolution <> 'D' " +
			"group by report.series.category ")
	List<ReportCategoryCounter> countLateByCategory(Entity entity, Date checkDate);

	/**
	 * Count late by folder.
	 * 
	 * @param entity
	 * @param checkDate
	 */
	@Query("select new org.helianto.task.repository.ReportFolderCounter(report.series, count(report)) " +
			"from Report report " +
			"where report.entity = ?1 " +
			"and report.nextCheckDate between report.issueDate and ?2 " +
			"and report.resolution <> 'D' " +
			"group by report.series ")
	List<ReportFolderCounter> countLateByReportFolder(Entity entity, Date checkDate);

	/**
	 * Count late.
	 * 
	 * @param entity
	 * @param checkDate
	 */
	@Query("select count(report) " +
			"from Report report " +
			"where report.entity = ?1 " +
			"and report.nextCheckDate between report.issueDate and ?2 " +
			"and report.resolution <> 'D' ")
	long countLate(Entity entity, Date checkDate);
	
	/**
	 * Count report by reportCode like.
	 * 
	 * @param entity
	 * @param reportCode
	 */
	long countByEntityAndReportCodeLike(Entity entity, String reportCode);
	
	/**
	 * Find by reportCode like.
	 * 
	 * @param entity
	 * @param reportCode
	 */
	List<Report> findByEntityAndReportCodeLike(Entity entity, String reportCode);
	
	/**
	 * Find by reportCode like.
	 * 
	 * @param entity
	 * @param reportCode
	 */
	List<Report> findByEntityAndReportCodeLike(Entity entity, String reportCode, Pageable pageable);
	
	/**
	 * Find by category, folder and report code like.
	 * 
	 * @param categoryId
	 * @param folderId
	 * @param reportCode
	 */
	List<Report> findByCategory_IdAndSeriesIdAndReportCodeLike(int categoryId, int folderId, String reportCode);
	
	/**
	 * Find by folder and report code like.
	 * 
	 * @param folderId
	 * @param reportCode
	 */
	List<Report> findBySeriesIdAndReportCodeLike(int folderId, String reportCode);
	
	/**
	 * 
	 * Find by reportFolderId.
	 * 
	 * @param reportFolderId
	 * @deprecated
	 */
	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode,"
			+ " report.summary, report.resolution, report.nextCheckDate,"
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.series.id = ?1 ")
	Page<ReportAdapter> findBySeriesId(int reportFolderId, Pageable pageable);
	
	
	/**
	 * 
	 * Find by reportFolderId on phase.
	 * 
	 * @param reportFolderId
	 * @deprecated
	 */
	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode,"
			+ " report.summary, report.resolution, report.nextCheckDate,"
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.series.id = ?1 AND report.phase in ?2")
	Page<ReportAdapter> findBySeriesIdOnPhase(int reportFolderId, Character[] phases, Pageable pageable);
	
	/**
	 * Find by id
	 */
	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode,"
			+ " report.summary, report.resolution, report.nextCheckDate,"
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.id = ?1 ")
	ReportAdapter findById(int reportId);

	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode, "
			+ " report.summary, report.resolution, report.nextCheckDate, "
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.entity.id = ?1 AND report.reportCode like ?2 OR report.summary like ?3 ")	
	Page<ReportAdapter> findByEntity_idAndReportCodeOrSummary(int entityId, String reportCode, String summary, Pageable paged);

	
	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode,"
			+ " report.summary, report.resolution, report.nextCheckDate,"
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.entity.id = ?1 AND report.reportCode like ?2 ")	
	Page<ReportAdapter> findByEntity_idAndReportCode(int entityId, String reportCode, Pageable paged);

	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode,"
			+ " report.summary, report.resolution, report.nextCheckDate,"
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.entity.id = ?1 AND report.summary like ?2 ")	
	Page<ReportAdapter> findByEntity_IdAndSummary(int entityId, String string, Pageable paged);
	
	@Query("SELECT new "
			+ "org.helianto.task.repository.ReportAdapter"
			+ "(report.id, report.internalNumber, report.reportCode,"
			+ " report.summary, report.resolution, report.nextCheckDate,"
			+ " report.taskDesc, report.category.id, " 
			+ " report.reportContent.content, report.relativeSize, "
			+ " report.priority, report.frequency, report.series.id, report.phase) "
			+ " FROM Report report "
			+ "WHERE report.entity.id = ?1 AND report.internalNumber = ?2 ")
	Page<ReportAdapter> findByEntity_IdAndInternalNumber(int entityId, long internalNumber, Pageable paged);
	

	@Query("select max(report.internalNumber) "
			+ "from Report report "
			+ "where report.series.id = ?1 ")
	Long findLastInternalNumberByReportFolderId(int reportFolderId);
	
	Report findByEntity_idAndReportCode(int entityId, String reportCode);
	
	@Query("SELECT  report.reporter.id "
			+ " FROM Report report "
			+ "WHERE report.id = ?1 ")
	Integer findReporterIdByReport(int reportId);
}
