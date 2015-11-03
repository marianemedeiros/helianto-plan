package org.helianto.task.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.helianto.core.config.HeliantoServiceConfig;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.domain.Operator;
import org.helianto.core.repository.EntityRepository;
import org.helianto.core.repository.IdentityRepository;
import org.helianto.core.repository.OperatorRepository;
import org.helianto.core.test.EntityTestSupport;
import org.helianto.core.test.OperatorTestSupport;
import org.helianto.core.test.TestDataSourceConfig;
import org.helianto.task.domain.ReportFolder;
import org.helianto.task.domain.StaffMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestDataSourceConfig.class, HeliantoServiceConfig.class})
@Transactional
public class StaffMemberJoinFolderRepositoryTests  {

	@Inject
	private EntityRepository entityRepository;
    
	@Inject
	private OperatorRepository operatorRepository;

	@Inject
	private StaffMemberRepository staffMemberRepository;
	
	@Inject
	private ReportFolderRepository reportFolderRepository;
	
	@Inject
	private IdentityRepository identityRepository;
	
	@Inject
	private StaffMemberJoinFolderRepository staffMemberJoinFolderRepository;
	
	private Identity identity;
	
	private ReportFolder reportFolder;
	
	private Operator context;
	
	private Entity entity;

	protected Serializable getTargetId(StaffMember target) {
		return target.getId();
	}

	@Test
	public void join() {
		context = operatorRepository.save(OperatorTestSupport.createOperator());
		entity = entityRepository.save(EntityTestSupport.createEntity(context));
		reportFolder = reportFolderRepository.save(new ReportFolder(entity, "t"));
		identity = identityRepository.save(new Identity("p"));
		StaffMember staffMember = staffMemberRepository.saveAndFlush(new StaffMember(reportFolder,identity));
		assertNotNull(staffMember);
		Pageable page = new PageRequest(0, 1, Sort.DEFAULT_DIRECTION, "id");
		List<StaffMember> projectAndMembers = staffMemberJoinFolderRepository.findByIdentity(entity.getId(), identity.getId(), page).getContent();
		assertFalse(projectAndMembers.isEmpty());
		for (StaffMember s: projectAndMembers) {
			assertTrue(s.getIdentityId().equals(identity.getId()));
			assertTrue(s.getReportFolderId().equals(reportFolder.getId()));
		}
	}

}
