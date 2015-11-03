package org.helianto.task.service;

import javax.inject.Inject;

import org.helianto.core.domain.Category;
import org.helianto.core.repository.CategoryRepository;
import org.helianto.partner.domain.Partner;
import org.helianto.partner.repository.PartnerRepository;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.repository.ReportFolderRepository2;
import org.helianto.user.domain.User;
import org.helianto.user.domain.UserGroup;
import org.helianto.user.repository.UserGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Report command service.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class ReportFolderCommandService {

	public static final int DAYS_TO_WARN = 30;
	
	private static final Logger logger = LoggerFactory.getLogger(ReportFolderCommandService.class);
	
	@Inject
	protected UserGroupRepository userRepository;

	@Inject
	protected CategoryRepository categoryRepository;

	@Inject
	protected PartnerRepository partnerRepository;

	@Inject
	protected ReportFolderRepository2 reportFolderRepository;
	
	/**
	 * Update reportFolder.
	 * 
	 * @param userId
	 * @param command
	 */
	public ReportFolder reportFolder(int userId, ReportFolder command) {
		User user = (User) userRepository.findOne(userId);
		if (user==null) {
			throw new IllegalArgumentException("Unable to persist, owner not found.");
		}
		ReportFolder reportFolder = null;
		if (command==null) {
			throw new IllegalArgumentException("Unable to persist null report folder.");
		}
		if (command.getId()==0) {
			logger.debug("New report folder.");
			reportFolder = command;
			reportFolder.setEntity(user.getEntity());
			reportFolder.setOwner(user.getIdentity());
			Category category = categoryRepository.findOne(command.getCategoryId());
			reportFolder.setCategory(category);
			Partner partner = partnerRepository.findOne(command.getPartnerId());
			reportFolder.setPartner(partner);
			UserGroup userGroup = userRepository.findOne(command.getUserGroupId());
			reportFolder.setUserGroup(userGroup);
		}
		else {
			reportFolder = reportFolderRepository.findReportFolder(command.getId());
		}
		reportFolder.setFolderCode(command.getFolderCode());
		reportFolder.setContent(command.getContent());
		reportFolder.setEncoding(command.getEncoding());
		reportFolder.setReportNumberPattern(command.getReportNumberPattern());
		reportFolder.setPatternSuffix(command.getPatternSuffix());
		reportFolder.setParsedContent(command.getParsedContent());
		reportFolder.setPrivacyLevel(command.getPrivacyLevel());
		reportFolder.setZIndex(command.getZIndex());
		reportFolder.setFolderCaption(command.getFolderCaption());
		reportFolder.setParentPath(command.getParentPath());
		reportFolder.setNature(command.getNature());
		reportFolder.setTraceabilityItems(command.getTraceabilityItems());
		reportFolder.setStartDate(command.getStartDate());
		reportFolder.setEndDate(command.getEndDate());
		reportFolder.setVolumeTags(command.getVolumeTags());
		reportFolder.setCategoryOverrideAllowed(command.isCategoryOverrideAllowed());
		return reportFolder ;
	}

}