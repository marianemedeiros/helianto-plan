package org.helianto.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.helianto.core.def.Appellation;
import org.helianto.core.def.Gender;
import org.helianto.core.domain.Entity;
import org.helianto.task.internal.AbstractRequirement;
import org.helianto.user.domain.User;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Requisito de documentação associado a um relatório.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name = "plan_user"
, uniqueConstraints = { @UniqueConstraint(columnNames = {"entityId", "internalNumber" }) }
)
public class UserRequirement 
	extends AbstractRequirement 
	implements Comparable<UserRequirement> 
{

    private static final long serialVersionUID = 1L;
   
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    
    @Column(length=40)
    private String optionalKey = "";
    
    @Column(length=40)
    private String displayName = "";
    
    @Column(length=32)
    private String optionalFirstName = "";
    
    @Column(length=32)
    private String optionalLastName = "";
   
    private char optionalGender = Gender.NOT_SUPPLIED.getValue();
    
    private char optionalAppellation = Appellation.NOT_SUPPLIED.getValue();
   
    @DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date optionalBirthDate;
    
    private int frequency;
    
    private int complete;
    
    /**
   	 * Merger.
   	 * 
   	 * @param command
   	 **/
    public UserRequirement merge(UserRequirement command) {
   			super.merge(command);
    		setOptionalKey(command.getOptionalKey());
   			setDisplayName(command.getDisplayName());
   			setOptionalFirstName(command.getOptionalFirstName());
   			setOptionalLastName(command.getOptionalLastName());
   			setOptionalGender(command.getOptionalGender());
   			setOptionalAppellation(command.getOptionalAppellation());
   			setOptionalBirthDate(command.getOptionalBirthDate());
   			setFrequency(command.getFrequency());
   			setComplete(command.getComplete());
   			return this;
   		}
    
    
//    @Transient
    public String getInternalNumberKey() {
    	return "USRRQMT";
    }
    
//    @Transient
    public int getStartNumber() {
    	return 1;
    }
    
   /* Métodos da própria classe */
    
    /**
     * Default constructor.
     */
    public UserRequirement() {
		this((Entity) null, 0);
	}
    
    /**
     * Key constructor.
     * 
     * @param entity
     * @param internalNumber
     */
    public UserRequirement(Entity entity, long internalNumber) {
    	super(entity, internalNumber);
		
	}
    
    /**
     * Report constructor.
     * 
     * @param report
     * @param sequence
     */
    public UserRequirement(Report report, int sequence) {
		this(report.getEntity(), 0);
		setReport(report);
		setSequence(sequence);
	}
    
    /**
     * User constructor.
     * 
     * @param user
     */
    public UserRequirement(User user) {
		this(user.getEntity(), 0);
		setUser(user);
	}
    
    /**
     * Associated user (can be null).
     */
    public User getUser() {
		return user;
	}
    public void setUser(User user) {
		this.user = user;
	}
    
    /**
     * <<Transient>> Verdadeiro se hé um usuário anexo é requisição.
     */
//    @Transient
    public boolean isUserEnabled() {
    	return getUser()!=null;
    }
    
    /**
     * Key (email) optional.
     */
    public String getOptionalKey() {
		return internalOptionalKey(optionalKey);
	}
    public void setOptionalKey(String optionalKey) {
		this.optionalKey = optionalKey;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace an optional code according
     * to conditions of request. 
     * 
     * <p>
     * Default implementation use user code.
     * </p>
     */
//    @Transient
    protected String internalOptionalKey(String optionalKey) {
    	if (isUserEnabled()) {
    		return getUser().getUserKey();
    	}
		return optionalKey;
	}
    
    /**
     * Common name optional.
     * @deprecated
     */
//    @Transient
    public String getOptionalAlias() {
		return getDisplayName();
	}
    public void setOptionalAlias(String optionalAlias) {
		setDisplayName(optionalAlias);
	}
    
    /**
     * Name to display.
     */
    public String getDisplayName() {
    	return internalDisplayName(displayName);
	}
    public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace the optional key according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use user key.
     * </p>
     */
//    @Transient
    protected String internalDisplayName(String displayName) {
    	if (isUserEnabled()) {
    		return getUser().getUserDisplayName();
    	}
		return displayName;
	}
    
    /**
     * First Name Optional.
     */
    public String getOptionalFirstName() {
		return internalOptionalFirstName(optionalFirstName);
	}
    public void setOptionalFirstName(String optionalFirstName) {
		this.optionalFirstName = optionalFirstName;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace the first name according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use first name.
     * </p>
     */
//    @Transient
    protected String internalOptionalFirstName(String optionalFirstName) {
    	if (isUserEnabled()) {
    		return getUser().getUserFirstName();
    	}
		return optionalFirstName;
	}
    
    /**
     * Last name optional.
     */
    public String getOptionalLastName() {
		return internalOptionalLastName(optionalLastName);
	}
    public void setOptionalLastName(String optionalLastName) {
		this.optionalLastName = optionalLastName;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace the last name according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use last name.
     * </p>
     */
//    @Transient
    protected String internalOptionalLastName(String optionalLastName) {
    	if (isUserEnabled()) {
    		return getUser().getUserLastName();
    	}
		return optionalLastName;
	}
    
    /**
     * Gender Optional.
     */
    public char getOptionalGender() {
		return internalOptionalGender(optionalGender);
	}
    public void setOptionalGender(char optionalGender) {
		this.optionalGender = optionalGender;
	}   
    
    /**
     * <<Transient>> Allow that the subclass replace the true gender according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use user gender.
     * </p>
     */
//    @Transient
    protected char internalOptionalGender(char optionalGender) {
    	if (isUserEnabled()) {
    		return getUser().getUserGender();
    	}
		return optionalGender;
	}
    
    /**
     * Treatment optional.
     */
    public char getOptionalAppellation() {
		return internalOptionalAppellation(optionalAppellation);
	}
    public void setOptionalAppellation(char optionalAppellation) {
		this.optionalAppellation = optionalAppellation;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace the true treatment according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use user treatment.
     * </p>
     */
//    @Transient
    protected char internalOptionalAppellation(char optionalAppellation) {
    	if (isUserEnabled()) {
    		return getUser().getUserAppelation();
    	}
		return optionalAppellation;
	}
    
    /**
     * Birth data optional.
     */
    public Date getOptionalBirthDate() {
		return internalOptionalBirthDate(optionalBirthDate);
	}
    public void setOptionalBirthDate(Date optionalBirthDate) {
		this.optionalBirthDate = optionalBirthDate;
	}
    
    /**
     * <<Transient>> Allow that the subclass replace the birth date according the conditions 
     * of request.  
     * 
     * <p>
     * Default implementation use user birth date.
     * </p>
     */
//    @Transient
    public Date internalOptionalBirthDate(Date optionalBirthDate) {
    	if (isUserEnabled()) {
    		return getUser().getUserBirthDate();
    	}
		return optionalBirthDate;
	}
    
//    /**
//     * Date to be controlled.
//     */
//    @DateTimeFormat(style="SS")
//    @Temporal(TemporalType.TIMESTAMP)
//    public Date getNextCheckDate() {
//        return validateNextCheckDate(this.nextCheckDate);
//    }
//    public void setNextCheckDate(Date nextCheckDate) {
//        this.nextCheckDate = nextCheckDate;
//    }
    
    /**
     * Give subclasses a chance to validate next check date.
     */
    protected Date validateNextCheckDate(Date nextCheckDate) {
    	return nextCheckDate;
    }
    
    /**
     * Frequency.
     */
    public int getFrequency() {
    	return getInternalFrequency();
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
//    @Transient
    protected int getInternalFrequency() {
    	return this.frequency;
    }

    public int getComplete() {
    	return validateCompleteness(this.complete);
    }
    public void setComplete(int complete) {
        this.complete = complete;
    }
    
    /**
     * Give subclasses a chance to validate completeness.
     */
    protected int validateCompleteness(int complete) {
    	return complete;
    }
    
    /**
     * Requests comparator.
     */
    public int compareTo(UserRequirement next) {
    	if (getReport()!=null && next.getReport()!=null) {
    		if (getReport().equals(next)) {
        		return (int) (getSequence() - next.getSequence());
    		}
    		return (int) (getReport().getInternalNumber() - next.getReport().getInternalNumber());
    	}
		return this.getId() - next.getId();
	}   

    /**
     * equals
     */
    public boolean equals(Object other) {
          if ( (this == other ) ) return true;
          if ( (other == null ) ) return false;
          if ( !(other instanceof UserRequirement) ) return false;
          UserRequirement castOther = (UserRequirement) other; 
          
          return ((this.getReport()==castOther.getReport()) || ( this.getReport()!=null && castOther.getReport()!=null && this.getReport().equals(castOther.getReport()) ))
        		  && (this.getSequence()==castOther.getSequence());
    }
    
}
