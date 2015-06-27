package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.domain.Identity;
import org.helianto.task.domain.Participant;
import org.helianto.task.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Participant repository.
 * 
 * @author Eldevan Nery Junior
 */
public interface ParticipantRepository 
	extends JpaRepository<Participant , Serializable> 
{
	/**
	 * Find by natural key.
	 * 
	 * @param report
	 * @param identity
	 */
	 Participant findByReportAndIdentity(Report report, Identity identity) ;
	
}
