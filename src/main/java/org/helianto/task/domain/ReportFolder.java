/* Copyright 2005 I Serv Consultoria Empresarial Ltda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.helianto.task.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.helianto.core.Navigable;
import org.helianto.core.Programmable;
import org.helianto.core.def.HumanReadable;
import org.helianto.core.def.PrivacyLevel;
import org.helianto.core.domain.Category;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.utils.StringListUtils;
import org.helianto.document.internal.AbstractSerializer;
import org.helianto.partner.domain.Partner;
import org.helianto.task.def.ReportFolderContentType;
import org.helianto.task.def.Resolution2;
import org.helianto.user.domain.User;
import org.helianto.user.domain.UserGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Report folder, a.k.a. Project.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_serializer",
	    uniqueConstraints = {@UniqueConstraint(columnNames={"entityId", "builderCode"})}
	)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.CHAR
)
@DiscriminatorValue("F")
public class ReportFolder 
	extends AbstractSerializer<Report> 
	implements HumanReadable
	, Programmable
	, Navigable
{

	private static final long serialVersionUID = 1L;
	
	@Lob
	private byte[] content ="".getBytes();
	
	@Column(length=32)
    private String encoding = "iso8859_1";
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ownerId")
	private Identity owner;
	
	@Transient
	private Integer ownerId;
	
	@Column(length=20)
	private String reportNumberPattern = "";
	
	private String patternSuffix = "";
	
	@Column(length=1024)
	private String parsedContent = "";
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="categoryId", nullable=true)
	private Category category;
	
	@Transient
	private Integer categoryId;
	
	private Character privacyLevel = new Character(PrivacyLevel.PUBLIC.getValue());
	
	@Column(length=20)
	private String zIndex = "";
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="partnerId")
	private Partner partner;
	
	@Transient
	private Integer partnerId;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="userGroupId", nullable=true)
    private UserGroup userGroup;
	
	@Transient
	private Integer userGroupId;
	
	@Column(length=32)
	private String folderCaption = "";
	
	@Column(length=64)
	private String parentPath = "";
	
	@Column(length=12)
    private String nature = "";
	
	@Column(length=6)
	@Enumerated(EnumType.STRING)
	private Resolution2 resolution;
	
	@Column(length=255)
    private String traceabilityItems = "";
	
    @Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(length=128)
	private String volumeTags = "";
	
	private Boolean categoryOverrideAllowed = false;
	
	@JsonIgnore
	@OneToMany(mappedBy="reportFolder")
	private Set<StaffMember> staff = new HashSet<StaffMember>(0);
    
	@JsonIgnore
	@OneToMany(mappedBy="reportFolder")
	private Set<ReportPhase> phases = new HashSet<ReportPhase>(0);
	
	@Transient
    private List<String> transientScriptContents = new ArrayList<String>();
    
	/**
	  * Merger.
	  * 
	  * @param command
  	  **/
	public ReportFolder merge(ReportFolder command) {
			setEncoding(command.getEncoding());
			setReportNumberPattern(command.getReportNumberPattern());
			setPatternSuffix(command.getPatternSuffix());
			setParsedContent(command.getParsedContent());
			setPrivacyLevel(command.getPrivacyLevel());
			setZIndex(command.getZIndex());
			setFolderCaption(command.getFolderCaption());
			setParentPath(command.getParentPath());
			setNature(command.getNature());
			setResolution(command.getResolution());
			setTraceabilityItems(command.getTraceabilityItems());
			setStartDate(command.getStartDate());
			setEndDate(command.getEndDate());
			setVolumeTags(command.getVolumeTags());
			return this;
		}
	
	
    /** 
     * Required constructor.
     */
    public ReportFolder() {
    	this(null, "");
    }

    /** 
     * Key constructor.
     * 
     * @param entity
     * @param folderCode
     */
    public ReportFolder(Entity entity, String folderCode) {
    	super(entity, folderCode);
    	setResolution(Resolution2.TODO);
    	setContentTypeAsEnum(ReportFolderContentType.PORTFOLIO);
    }
    
    /** 
     * Form constructor.
     * 
     * @param user
     */
    public ReportFolder(User user) {
    	this(user.getEntity(), "");
    	setOwner(user.getIdentity());
    }
    
    /** 
     * Form constructor.
     * 
     * @param user
     * @param contentType
     */
    public ReportFolder(User user, char contentType) {
    	this(user);
    	setContentType(contentType);
    }
    
    /** 
     * Category constructor.
     * 
     * @param entity
     * @param category
     */
    public ReportFolder(Entity entity, Category category) {
    	this(entity,"");
    	setCategory(category);
    }
    
    /**
     * Form constructor.
     * 
     * @param id
     * @param entityId
     * @param folderCode
     * @param content
     * @param encoding
     * @param ownerId
     * @param reportNumberPattern
     * @param patternSuffix
     * @param parsedContent
     * @param categoryId
     * @param privacyLevel
     * @param zIndex
     * @param partnerId
     * @param userGroupId
     * @param folderCaption
     * @param parentPath
     * @param nature
     * @param traceabilityItems
     * @param startDate
     * @param endDate
     * @param volumeTags
     * @param categoryOverrideAllowed
     */
    public ReportFolder(Integer id
    		, Integer entityId
    		, String folderCode
    		, byte[] content
    		, String encoding
    		, Integer ownerId
    		, String reportNumberPattern
    		, String patternSuffix
    		, String parsedContent
    		, Integer categoryId
    		, Character privacyLevel
    		, String zIndex
    		, Integer partnerId
    		, Integer userGroupId
    		, String folderCaption
    		, String parentPath
    		, String nature
    		, String traceabilityItems
    		, Date startDate
    		, Date endDate
    		, String volumeTags
    		, Boolean categoryOverrideAllowed
    		) {
		this();
		setId(id);
		setEntityId(entityId!=null ? entityId:0);
		setFolderCode(folderCode);
		setContent(content);
		setEncoding(encoding);
		setOwnerId(ownerId!=null ? ownerId:0);
		setReportNumberPattern(reportNumberPattern);
		setPatternSuffix(patternSuffix);
		setParsedContent(parsedContent);
		setCategoryId(categoryId!=null ? categoryId:0);
		setPrivacyLevel(privacyLevel!=null ? privacyLevel:'0');
		setZIndex(zIndex);
		setPartnerId(partnerId!=null ? partnerId:0);
		setUserGroupId(userGroupId!=null ? userGroupId:0);
		setFolderCaption(folderCaption);
		setParentPath(parentPath);
		setNature(nature);
		setTraceabilityItems(traceabilityItems);
		setStartDate(startDate);
		setEndDate(endDate);
		setVolumeTags(volumeTags);
		setCategoryOverrideAllowed(categoryOverrideAllowed!=null ? categoryOverrideAllowed:false);
	}
    
	/**
     * Content type as enum.
     */
    public void setContentTypeAsEnum(ReportFolderContentType contentType) {
    	super.setContentType(contentType.getValue());
    }
    
    /**
     * Internal number
     */
    public long getInternalNumber() {
    	if (getFolderCode()!=null && getFolderCode().length()>0) {
    		return 1;
    	}
    	return 0;
    }
    
    public String getInternalNumberKey() {
    	return "REPORTFOLDER";
    }
    
    public int getStartNumber() {
    	return 1;
    }

    /**
     * Generation codes of reports folders.
     */
    public String getReportNumberPattern() {
    	return internalReportNumberPattern(reportNumberPattern);
    }
    public void setReportNumberPattern(String reportNumberPattern) {
    	this.reportNumberPattern = reportNumberPattern;
    }
    
    public String getPatternSuffix() {
		return patternSuffix;
	}
    public void setPatternSuffix(String patternSuffix) {
		this.patternSuffix = patternSuffix;
	}
    
    /**
     * Allow subclass change the default generation codes of reports.
     * 
     * @param reportNumberPattern
     */
    protected String internalReportNumberPattern(String reportNumberPattern) {
    	if (isCategoryEnabled() 
    			&& getCategory().getCustomNumberPattern()!=null 
    			&& getCategory().getCustomNumberPattern().length()>0) {
    		return getCategory().getCustomNumberPattern();
    	}
    	if (reportNumberPattern!=null 
    			&& reportNumberPattern.length()>0) {
    		return reportNumberPattern;
    	}
    	return new StringBuilder("'").append(getContentType()).append("'000000").toString();
    }
    
    public byte[] getContent() {
        return this.content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
    
    /**
     * Helper method to get text content as String.
     */
    public String getContentAsString() {
    	if (getContent()!=null && isText()) {
    		return new String(getContent());
    	}
    	return "";
    }
    public void setContentAsString(String contentAsString) {
    	if (contentAsString!=null) {
        	this.content = contentAsString.getBytes();
    	}
	}
    
    /**
     * Content size.
     */
    public int getContentSize() {
    	if (content!=null) {
        	return this.content.length;
    	}
    	return 0;
    }
    
	public String getEncoding() {
		return this.encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Context folder always be html.
	 */
	public String getMultipartFileContentType() {
		return "text/html";
	}
	

    public boolean isText() {
    	return true;
    }

    public boolean isHtml() {
    	return true;
    }

    /**
     * Owner.
     */
    public Identity getOwner() {
		return owner;
	}
    public void setOwner(Identity owner) {
		this.owner = owner;
	}
    
    /**
     * <<Transient>> owner id.
     */
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	
    /**
     * Owner principal.
     */
    public String getOwnerPrincipal() {
		if (getOwner()!=null) {
			return getOwner().getPrincipal();
		}
		return "?";
	}
    
    /**
     * Owner display name.
     */
    public String getOwnerDisplayName() {
		if (getOwner()!=null) {
			return getOwner().getDisplayName();
		}
		return "?";
	}
    
    /**
     * Owner optional alias.
     * @deprecated
     */
    public String getOwnerOptionalAlias() {
		if (getOwner()!=null) {
			return getOwner().getDisplayName();
		}
		return "?";
	}
    
    /**
     * Owner name.
     */
    public String getOwnerName() {
		if (getOwner()!=null) {
			return getOwner().getIdentityName();
		}
		return "?";
	}
    
    /**
     * Category.
     */
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * <<Transient>> category id.
	 */
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * <<Transient>> True if have category.
	 */
	public boolean isCategoryEnabled() {
		return getCategory()!=null;
	}
	
	/**
	 * True if folders reports can substitute a category.
	 */
	public boolean isCategoryOverrideAllowed() {
		return categoryOverrideAllowed;
	}
	public void setCategoryOverrideAllowed(boolean categoryOverrideAllowed) {
		this.categoryOverrideAllowed = categoryOverrideAllowed;
	}
	
    public Character getPrivacyLevel() {
		return privacyLevel;
	}
    public void setPrivacyLevel(Character privacyLevel) {
		this.privacyLevel = privacyLevel;
	}
    public void setPrivacyLevelAsEnum(PrivacyLevel privacyLevel) {
		this.privacyLevel = privacyLevel.getValue();
	}
    
    /**
     * Utilized to folder instructions.
     */
    public String getZIndex() {
		return internalZIndex(this.zIndex);
	}
    public void setZIndex(String zIndex) {
		this.zIndex = zIndex;
	}
    
    /**
     * Call by {@link #getZIndex()}.
     * 
     * @param zIndex
     */
    protected String internalZIndex(String zIndex) {
    	if (zIndex!=null && zIndex.equals(null)) {
    		return getFolderCode();
    	}
    	return zIndex;
    }
    
    public Partner getPartner() {
		return partner;
	}
    public void setPartner(Partner partner) {
		this.partner = partner;
	}
    
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	
    /**
     * User group.
     */
    public UserGroup getUserGroup() {
		return userGroup;
	}
    public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
    
    /**
     * <<Transient>> partner id.
     */
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	
    /**
     * Folder caption.
     */
    public String getFolderCaption() {
		return folderCaption;
	}
    public void setFolderCaption(String folderCaption) {
		this.folderCaption = folderCaption;
	}
    
    /**
     * Nature of folders, like list csv of key words.
     */
    public String getNature() {
		return nature;
	}
    public void setNature(String nature) {
		this.nature = nature;
	}
    
    /**
     * Resolution.
     */
    public Resolution2 getResolution() {
		return resolution;
	}
    public void setResolution(Resolution2 resolution) {
		this.resolution = resolution;
	}
    
    /**
     * <<Transient>> List of nature as String[].
     */
	public String[] getNatureAsArray() {
		return StringListUtils.stringToArray(getNature());
	}
	public void setNatureAsArray(String[] natureArray) {
		setNature(StringListUtils.arrayToString(natureArray));
	}
	
    /**
     * Path
     */
    public String getParentPath() {
		return parentPath;
	}
    public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
    

    public String getCurrentPath() {
    	if (getParentPath()!=null) {
    		return new StringBuilder(getParentPath()).append(getFolderCode()).append("/").toString();
    	}
    	return "/";
    }

    /**
     * List of items required to traceability, like list CVS of pair KV.
     * 
     * <p>
     * Examples: UC1=Use case 1, UC2=Use case 2, etc.
     * </p>
     */
    public String getTraceabilityItems() {
		return traceabilityItems;
	}
    public void setTraceabilityItems(String traceabilityItems) {
		this.traceabilityItems = traceabilityItems;
	}
    
    /**
     * <<Transient>> List of Scripts as String[].
     */
    @JsonIgnore
    public Map<String, String> getTraceabilityItemsAsMap() {
		return parse(traceabilityItems);
	}
    
    /**
     * List of Scripts as list CSV of codes scripts.
     */
    public String getScriptItems() {
    	if (isCategoryEnabled()) {
    		return getCategory().getScriptItems();
    	}
		return "";
	}
    
    /**
     * <<Transient>> Lists of items required to traceability, like list CVS of pair KV.
     */
    public String[] getScriptItemsAsArray() {
		return StringListUtils.stringToArray(getScriptItems());
	}
    
    /**
     * <<Transient>> List of scripts contexts. 
     */
    public List<String> getScriptList() {
    	return transientScriptContents;
    }
    
    /** 
     * Add context of a script to a list.
     * 
     * @param scriptContent
     */
    public void addScriptContent(String scriptContent) {
    	getScriptList().add(scriptContent);
	}
    
    /**
     * Start date.
     */
    public Date getStartDate() {
		return startDate;
	}
    public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    
    /**
     * End date.
     */
    public Date getEndDate() {
		return endDate;
	}
    public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
    /**
     * <<Transient>> 
     * Reference default to partner selection as a list CSV with pairs KV converted in matrix.
     */
    public static String[] getStandardPartnerTypeFilterPatternAsArray() {
    	// CLiente, fornecedor,filial,representante,laboratério,fabricante,produtor,transportadora,ensino
		return "C,S,D,A,L,M,R,T,E".split(",");
	}

    /**
     * <<Transient>> True if have a partner selection default, determined by category. 
     */
    public boolean isPartnerRequired() {
    	return isCategoryEnabled() && getCategory().getPartnerFilterPatternAsArray().length >0;
    }
    
    /**
     * <<Transient>> Default list to selection of category converted to a matrix. 
     */
    public String[] getPartnerFilterPatternAsArray() {
    	if (isPartnerRequired()) {
    		return getCategory().getPartnerFilterPatternAsArray();
    	}
    	return new String[0];
	}
    
    /**
     * Set of participants.
     */
    public Set<StaffMember> getStaff() {
		return staff;
	}
    public void setStaff(Set<StaffMember> staff) {
		this.staff = staff;
	}
    
    /**
     * Set of phases.
     */
    public Set<ReportPhase> getPhases() {
		return phases;
	}
    public void setPhases(Set<ReportPhase> phases) {
		this.phases = phases;
	}
    
    /**
     * <<Transient>> List of responsibilities converted in matrix.
     */
    public String[] getCustomWorkflowRolesAsArray() {
    	if (isCategoryEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsArray();
    	}
    	return new String[0];
	}

    /**
     * <<Transient>> List of responsibilities converted in a map where the key
     * is the order of presentation.  
     *   
     * <p>
     * Keys to map have base 1, i.e., the first responsibility is mark as 1.
     * </p>
     */
    public Map<String, String> getCustomWorkflowRolesAsMap() {
		Map<String, String> workflowRolesMap = new HashMap<String, String>();
    	if (isCategoryEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsMap();
    	}
    	return workflowRolesMap;
	}

	/**
	 * Accepts until 6 tags of volume, int the form CSV.
	 */
    public String getVolumeTags() {
		return volumeTags;
	}
	public void setVolumeTags(String volumeTags) {
		this.volumeTags = volumeTags;
	}
	
	/**
	 * <<Transient>> Convenient to convert tags of volume to a matrix. 
	 */
	public String[] getVolumeTagsAsArray() {
		return StringListUtils.stringToArray(getVolumeTags());
	}
	public void setVolumeTagsAsArray(String[] volumeTagsAsArray) {
		setVolumeTags(StringListUtils.arrayToString(volumeTagsAsArray));
	}
	
	/**
	 * <<Transient>> True if have tags of volume.
	 */
	public boolean isVolumeTagEnabled() {
		return getVolumeTags()!=null && getVolumeTags().length()>0;
	}
	
	/**
	 * Parsed content.
	 */
	public String getParsedContent() {
		return parsedContent;
	}
	public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}

    /**
     * equals
     */
   @Override
   public boolean equals(Object other) {
         if ( !(other instanceof ReportFolder) ) return false;
         return super.equals(other);
   }
   
   /**
    * <<Transient>> Map parser.
    */
	protected LinkedHashMap<String, String> parse(String parsedContent) {
		LinkedHashMap<String, String> parsedMap = new LinkedHashMap<String, String>();
		if (parsedContent == null) {
			return parsedMap;
		}
		String[] keyValuePairs = parsedContent.split(",");
		for (String keyValue : keyValuePairs) {
			String[] keyValuePair = keyValue.split("=");
			String key = "", value = "";
			if (keyValuePair.length > 1) {
				value = keyValuePair[1].trim();
			}
			if (keyValuePair.length > 0) {
				key = keyValuePair[0].trim();
				parsedMap.put(key, value);
			}
		}
		return parsedMap;
	}
   
}
