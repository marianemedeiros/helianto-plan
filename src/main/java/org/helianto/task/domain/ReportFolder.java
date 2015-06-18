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
import javax.persistence.FetchType;
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
import org.helianto.core.Privacy;
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
import org.helianto.user.domain.User;
import org.helianto.user.domain.UserGroup;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;



/**
 * Pastas para relatórios.
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
	implements 
	  Privacy
	, HumanReadable
	, Programmable
	, Navigable
{

	private static final long serialVersionUID = 1L;
	
	@Lob
	private byte[] content ="".getBytes();
	
	@Column(length=32)
    private String encoding = "iso8859_1";
	
	@ManyToOne
	@JoinColumn(name="ownerId")
	private Identity owner;
	
	@Column(length=20)
	private String reportNumberPattern = "";
	
	private String patternSuffix = "";
	
	@ManyToOne
    @JoinColumn(name="categoryId", nullable=true)
	private Category category;
	
	private char privacyLevel = PrivacyLevel.PUBLIC.getValue();
	
	@Column(length=20)
	private String zIndex = "";
	
	@ManyToOne
	@JoinColumn(name="partnerId")
	private Partner partner;
	
	@ManyToOne
    @JoinColumn(name="userGroupId", nullable=true)
    private UserGroup userGroup;
	
	@Column(length=32)
	private String folderCaption = "";
	
	@Column(length=64)
	private String parentPath = "";
	
	@Column(length=12)
    private String nature = "";
	
	@Column(length=255)
    private String traceabilityItems = "";
	
	private char archive = ' ';
	
	@DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@DateTimeFormat(style="SS")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(length=128)
	private String volumeTags = "";
	
	private boolean categoryOverrideAllowed = false;
	
	@JsonManagedReference 
	@OneToMany(mappedBy="reportFolder")
	private Set<StaffMember> staff = new HashSet<StaffMember>(0);
    
	@JsonManagedReference 
	@OneToMany(mappedBy="reportFolder", fetch=FetchType.EAGER)
	private Set<ReportPhase> phases = new HashSet<ReportPhase>(0);
	
	@Transient
    private List<String> transientScriptContents = new ArrayList<String>();
    
    /** 
     * Required constructor.
     */
    ReportFolder() {
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
     * Restringe aos tipos vélidos para pastas de relatórios.
     */
    public void setContentTypeAsEnum(ReportFolderContentType contentType) {
    	super.setContentType(contentType.getValue());
    }
    
    /*
     * O código da pasta seré gerado automaticamente a partir de um prefixo e uma 
     * squencia numérica gerada a partir da interface Sequenceable
     */
    
    /**
     * Somente responde com um numero não nulo caso o código da pasta ainda não tenha sido
     * estabelecido.
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
     * Padréo para geração dos códigos dos relatórios da pasta.
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
     * Permite que sublcasses alterem o padréo para geração dos códigos do relatório.
     * 
     * @param reportNumberPattern
     */
//    @Transient
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
    @JsonIgnore
    public void setContent(String content) {
    	this.content = content.getBytes();
    }
    
    /**
     * Helper method to get text content as String.
     */
//    @Transient
    public String getContentAsString() {
    	if (getContent()!=null && isText()) {
    		return new String(getContent());
    	}
    	return "";
    }
    public void setContentAsString(String contentAsString) {
		setContent(contentAsString);
	}
    
//    @Transient
    public int getContentSize() {
    	return this.content.length;
    }
    
	public String getEncoding() {
		return this.encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Conteúdo da pasta sempre seré html.
	 */
//	@Transient
	public String getMultipartFileContentType() {
		return "text/html";
	}
	
//    @Transient
    public boolean isText() {
    	return true;
    }

//    @Transient
    public boolean isHtml() {
    	return true;
    }

    public Identity getOwner() {
		return owner;
	}
    public void setOwner(Identity owner) {
		this.owner = owner;
	}
    
    /**
     * Owner principal.
     */
//    @Transient
    public String getOwnerPrincipal() {
		if (getOwner()!=null) {
			return getOwner().getPrincipal();
		}
		return "?";
	}
    
    /**
     * Owner display name.
     */
//    @Transient
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
//    @Transient
    public String getOwnerOptionalAlias() {
		if (getOwner()!=null) {
			return getOwner().getDisplayName();
		}
		return "?";
	}
    
    /**
     * Owner name.
     */
//    @Transient
    public String getOwnerName() {
		if (getOwner()!=null) {
			return getOwner().getIdentityName();
		}
		return "?";
	}
    
    /**
     * Categoria.
     */
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * <<Transient>> Verdadeiro se hé uma categoria.
	 */
//	@Transient
	public boolean isCategoryEnabled() {
		return getCategory()!=null;
	}
	
	/**
	 * Verdadeiro se os relatórios desta pasta podem subsituir a categoria.
	 */
	public boolean isCategoryOverrideAllowed() {
		return categoryOverrideAllowed;
	}
	public void setCategoryOverrideAllowed(boolean categoryOverrideAllowed) {
		this.categoryOverrideAllowed = categoryOverrideAllowed;
	}
	
    public char getPrivacyLevel() {
		return privacyLevel;
	}
    public void setPrivacyLevel(char privacyLevel) {
		this.privacyLevel = privacyLevel;
	}
    public void setPrivacyLevelAsEnum(PrivacyLevel privacyLevel) {
		this.privacyLevel = privacyLevel.getValue();
	}
    
    /**
     * Utilizado para ordenar pastas.
     */
    public String getZIndex() {
		return internalZIndex(this.zIndex);
	}
    public void setZIndex(String zIndex) {
		this.zIndex = zIndex;
	}
    
    /**
     * Chamado internamente por {@link #getZIndex()}, facilita és subclasses 
     * alterar a atribuição de zIndex.
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
     * Nome dado é pasta.
     */
    public String getFolderCaption() {
		return folderCaption;
	}
    public void setFolderCaption(String folderCaption) {
		this.folderCaption = folderCaption;
	}
    
    /**
     * Natureza da pasta, como lista csv de palavras-chave.
     */
    public String getNature() {
		return nature;
	}
    public void setNature(String nature) {
		this.nature = nature;
	}
    
    /**
     * <<Transient>> Lista de naturezas como String[].
     */
//	@Transient
	public String[] getNatureAsArray() {
		return StringListUtils.stringToArray(getNature());
	}
	public void setNatureAsArray(String[] natureArray) {
		setNature(StringListUtils.arrayToString(natureArray));
	}
	
    /**
     * Caminho
     */
    public String getParentPath() {
		return parentPath;
	}
    public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
    
//    @Transient
    public String getCurrentPath() {
    	if (getParentPath()!=null) {
    		return new StringBuilder(getParentPath()).append(getFolderCode()).append("/").toString();
    	}
    	return "/";
    }



    /**
     * Lista de itens requeridos para rastreabilidade, como lista CSV de pares KV.
     * 
     * <p>
     * Exemplo: UC1=Use case 1, UC2=Use case 2, etc.
     * </p>
     */
    public String getTraceabilityItems() {
		return traceabilityItems;
	}
    public void setTraceabilityItems(String traceabilityItems) {
		this.traceabilityItems = traceabilityItems;
	}
    
    /**
     * <<Transient>> Lista de Scripts como String[].
     */
    @JsonIgnore
//    @Transient
    public Map<String, String> getTraceabilityItemsAsMap() {
		return parse(traceabilityItems);
	}
    
    /**
     * Lista de scripts, como lista CSV de códigos dos scripts.
     */
//    @Transient
    public String getScriptItems() {
    	if (isCategoryEnabled()) {
    		return getCategory().getScriptItems();
    	}
		return "";
	}
    
    /**
     * <<Transient>> Lista de itens requeridos para rastreabilidade, como lista CSV de pares KV.
     */
//    @Transient
    public String[] getScriptItemsAsArray() {
		return StringListUtils.stringToArray(getScriptItems());
	}
    
    /**
     * <<Transient>> Lista de com o conteúdo dos scripts (carregados durante a execução).
     */
//    @Transient
    public List<String> getScriptList() {
    	return transientScriptContents;
    }
    
    /**
     * Adiciona conteúdo de um script é lista.
     * 
     * @param scriptContent
     */
    public void addScriptContent(String scriptContent) {
    	getScriptList().add(scriptContent);
	}
    
    /**
     * Archive type.
     * @deprecated
     */
    public char getArchive() {
		return archive;
	}
    public void setArchive(char archive) {
		this.archive = archive;
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
     * <<Transient>> Padréo de referéncia para seleção de parceiros como lista uma CSV de pares KV convertido em matriz.
     */
//    @Transient
    public static String[] getStandardPartnerTypeFilterPatternAsArray() {
    	// CLiente, fornecedor,filial,representante,laboratério,fabricante,produtor,transportadora,ensino
		return "C,S,D,A,L,M,R,T,E".split(",");
	}

    /**
     * <<Transient>> Verdadeiro se hé um padréo para seleção de parceiros determinado pela categoria.
     */
//    @Transient
    public boolean isPartnerRequired() {
    	return isCategoryEnabled() && getCategory().getPartnerFilterPatternAsArray().length >0;
    }
    
    /**
     * <<Transient>> Lista de padrées para seleção de categorias convertida para uma matriz.
     */
//    @Transient
    public String[] getPartnerFilterPatternAsArray() {
    	if (isPartnerRequired()) {
    		return getCategory().getPartnerFilterPatternAsArray();
    	}
    	return new String[0];
	}
    
    /**
     * Conjunto de participantes.
     */
    public Set<StaffMember> getStaff() {
		return staff;
	}
    public void setStaff(Set<StaffMember> staff) {
		this.staff = staff;
	}
    
    /**
     * Conjunto de fases.
     */
    public Set<ReportPhase> getPhases() {
		return phases;
	}
    public void setPhases(Set<ReportPhase> phases) {
		this.phases = phases;
	}
    
    /**
     * Total da estimativa das fases.
     */
//    @Transient
    public int getEstimate() {
    	int estimate = 0;
    	for (ReportPhase phase: getPhases()) {
    		estimate = phase.getEstimate();
    	}
    	return estimate;
    }
    
    /**
     * <<Transient>> Lista de responsabilidades convertida em matriz.
     */
//    @Transient
    public String[] getCustomWorkflowRolesAsArray() {
    	if (isCategoryEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsArray();
    	}
    	return new String[0];
	}

    /**
     * <<Transient>> Lista de responsabilidades convertida em mapa onde a chave é 
     * a ordem de apresentação.
     * 
     * <p>
     * As chaves do mapa tem base 1, i.e., a primeira responsabilidade é marcada como 1.
     * </p>
     */
//    @Transient
    public Map<String, String> getCustomWorkflowRolesAsMap() {
		Map<String, String> workflowRolesMap = new HashMap<String, String>();
    	if (isCategoryEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsMap();
    	}
    	return workflowRolesMap;
	}

	/**
	 * Aceita até 6 tags de volume, no formato CSV.
	 */
    public String getVolumeTags() {
		return volumeTags;
	}
	public void setVolumeTags(String volumeTags) {
		this.volumeTags = volumeTags;
	}
	
	/**
	 * <<Transient>> Conveniente para converter tags de volume em matriz.
	 */
//	@Transient
	public String[] getVolumeTagsAsArray() {
		return StringListUtils.stringToArray(getVolumeTags());
	}
	public void setVolumeTagsAsArray(String[] volumeTagsAsArray) {
		setVolumeTags(StringListUtils.arrayToString(volumeTagsAsArray));
	}
	
	/**
	 * <<Transient>> Verdadeiro caso haja tags de volume.
	 */
//	@Transient
	public boolean isVolumeTagEnabled() {
		return getVolumeTags()!=null && getVolumeTags().length()>0;
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
//   @Transient
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
