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

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.helianto.core.def.Resolution;
import org.helianto.core.def.Uploadable;
import org.helianto.core.domain.Category;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.internal.InterpretableCategory;
import org.helianto.core.number.Sequenceable;
import org.helianto.core.utils.StringListUtils;
import org.helianto.document.internal.AbstractPlan;
import org.helianto.partner.domain.Partner;
import org.helianto.task.def.ActionType;
import org.helianto.task.def.FollowUpOrder;
import org.helianto.task.def.RequestType;
import org.helianto.task.def.WorkflowTarget;
import org.helianto.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * Report.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_report",
	uniqueConstraints = {@UniqueConstraint(columnNames={"entityId", "reportCode"})}
)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.CHAR
)
@DiscriminatorValue("R")
public class Report 
	extends AbstractPlan 
	implements 
	  Sequenceable
	, Uploadable
	, InterpretableCategory
	, Comparator<FollowUp>
	, WorkflowTarget 
{

	/**
	 * <<Transient>> Usado para expor o valor dado ao discriminador da classe.
	 */
    public char getDiscriminator() {
        return 'R';
    }

    private static final long serialVersionUID = 1L;
	
    @Column(length=12)
    private String reportCode = "";
    
    private int runState;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="reporterId", nullable=true)
    private Identity reporter;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="assesseeId", nullable=true)
    private Identity assessee;
    
    @Column(length=32)
	private String prefix = "";
    
    @Column(length=64)
    private String summary = "";
    
    @Column(length=512)
    private String taskDesc = "";
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="reportFolderId")
    private ReportFolder series;
    
    @Transient
    private int reportFolderId;

	@Column(length=20)
    private String nature = "";
    
    private UploadableContent reportContent;
    
    @Column(length=128)
    private String reportFile = "";
    
    @Column(length=32)
    private String reportFileContentType = "";
    
    @Transient
    private transient MultipartFile file;
    
    private BigDecimal forecastWork;
    
    private BigDecimal actualWork;
    
    private int relativeSize;
    
    private char followUpOrder = FollowUpOrder.FIRST_DATE_ON_TOP.getValue();
   
    @Column(length=16)
    private String presentationOrder = "";
  
    @Column(length=64)
    private String traceability = "";
    
    private char requestType = RequestType.INTERNAL.getValue();
    
    private char actionType = ActionType.EXECUTION.getValue();
    
    private char phase = '0';
    
    @Column(length=512)
    private String parsedContent = "";
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="partnerId", nullable=true)
    private Partner partner;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="categoryId", nullable=true)
    private Category category;
    
    @Transient
    private Integer categoryId;
    
    private int workflowPhase = 0;
    
    private int mainRequirementSequence = -1;
    
    @JsonManagedReference 
    @OneToMany(mappedBy="report")
    private Set<Participant> participants = new HashSet<Participant>(0);
    
    @JsonManagedReference 
    @OneToMany(mappedBy="report")
    private Set<FollowUp> followUps = new HashSet<FollowUp>(0);
    
    @Transient
    private List<FollowUp> followUpList;
    
    @Transient
    private List<Report> children;

    @Transient
	private int countItems;

    @Transient
	private int countAlerts;

    @Transient
	private int countWarnings;

    @Transient
	private int countLikes;

    @Transient
	private char favourite = 'U';

    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
   		public Report merge(Report command) {
   			setReportCode(command.getReportCode());
   			setPrefix(command.getPrefix());
   			setSummary(command.getSummary());
   			setNature(command.getNature());
   			setReportContent(command.getReportContent());
   			setReportFileContentType(command.getReportFileContentType());
   			setTaskDesc(command.getTaskDesc());
   			setForecastWork(command.getForecastWork());
   			setActualWork(command.getActualWork());
   			setRelativeSize(command.getRelativeSize());
   			setFollowUpOrder(command.getFollowUpOrder());
   			setPresentationOrder(command.getPresentationOrder());
   			setTraceability(command.getTraceability());
   			setRequestType(command.getRequestType());
   			setActionType(command.getActionType());
   			setPhase(command.getPhase());
   			setParsedContent(command.getParsedContent());
   			setWorkflowPhase(command.getWorkflowPhase());
   			setMainRequirementSequence(command.getMainRequirementSequence());
   			return this;
   		}
    
    /** 
     * Default constructor.
     */
    public Report() {
    	super();
    	setMultipartFileContentType("text/html");
    	setReportContent(new UploadableContent());
    }
    
    /** 
     * Entity constructor.
     * 
     * @param entity
     */
    public Report(Entity entity) {
    	this();
    	setEntity(entity);
    }
    
    /** 
     * Key constructor.
     * 
     * @param entity
     * @param reportCode
     */
    public Report(Entity entity, String reportCode) {
    	this(entity);
    	setReportCode(reportCode);
    }
    
    /** 
     * Folder constructor.
     * 
     * @param reportFolder
     * @param reportCode
     */
    public Report(ReportFolder reportFolder, String reportCode) {
    	this(reportFolder.getEntity(), reportCode);
    	setReportFolder(reportFolder);
    }
    
    /** 
     * Form constructor.
     * 
     * @param owner
     */
    public Report(User owner) {
    	this(owner.getEntity());
    	setOwner(owner.getIdentity());
    }
    
    /**
     * Form constructor.
     * 
     * @param id
     * @param internalNumber
     * @param reportCode
     * @param summary
     * @param resolution
     * @param nextCheckDate
     * @param taskDesc
     * @param categoryId
     * @param content
     * @param relativeSize
     * @param priority
     * @param frequency
     * @param reportFolderId
     * @param phase
     */
	public Report(Integer id
			, Long internalNumber
			, String reportCode
			, String summary
			, Character resolution
			, Date nextCheckDate
			, String taskDesc
			, Integer categoryId
			, byte[] content
			, Integer relativeSize
			, Character priority
			, Integer frequency
			, Integer reportFolderId 
			, Character phase
			) {
		setId(id);
		setInternalNumber(internalNumber);
		setReportCode(reportCode);
		setSummary(summary);
		setResolution(resolution);
		setNextCheckDate(nextCheckDate);
		setTaskDesc(taskDesc);
		setCategoryId(categoryId);
		setContent(content);
		setRelativeSize(relativeSize);
		setPriority(priority);
		setFrequency(frequency);
		setReportFolderId(reportFolderId);
		setPhase(phase);
	}
	
    /**
     * Anexa padrão de numeração à chave para geração de números.
     */
    public String getInternalNumberKey() {
        return new StringBuilder("REPORT").append(getNumberPatternPrefix()).toString();
    }

    public int getStartNumber() {
    	return 1;
    }
    
    /**
     * Anexa padrão de numeração à chave para geração de números.
     */
    public String getSequenceGenerator() {
//    	if (getReportFolder()!=null) {
//    		return new StringBuilder(getReportFolder().get)
//    			.append(getNumberPatternPrefix()).toString();
//    	}
    	return "";
    }
    
    /*
     * Métodos recomendados para alterar o andamento do relatório.
     */
    
    /**
     * <<Transient>> Método recomendado para encerrar o relatório.
     * 
     * <p>
     * Atualiza a resolução para Resolution.DONE, coloca o progresso em 100% e 
     * o fluxo de aprovação na éltima fase.
     * </p>
     * 
     * @param actualEndDate
     */
    public void close(Date actualEndDate) {
    	if (actualEndDate==null) {
    		actualEndDate = new Date();
    	}
		setResolution(Resolution.DONE.getValue());
		setComplete(100);
		setWorkflowPhase(getWorkflowSize() + 1);
		setRunState(0);
		setActualEndDate(actualEndDate);
		setNextCheckDate(getActualEndDate());
    }
    
    /**
     * <<Transient>> Método recomendado para avaéar o relatório.
     * 
     * <p>
     * Atualiza o fluxo de aprovação para a próxima fase, atualiza o 
     * progresso de acordo com o avanão.
     * </p>
     */
    public boolean forward() {
		return forward(getWorkflowPhase() + 1);
    }
    
    /**
     * <<Transient>> Método recomendado para avançar o relatório.
     * 
     * <p>
     * Atualiza o fluxo de aprovação para a fase indicada, atualiza o 
     * progresso de acordo com o avanço.
     * </p>
     * <p>
     * No caso especial em que o relatório ainda não havia sido iniciado, inicia.
     * </p>
     * 
     * @param nextWorkflowPhase
     */
    public boolean forward(int nextWorkflowPhase) {
    	if (nextWorkflowPhase<=getWorkflowSize()) {
			if (getResolution()==Resolution.PRELIMINARY.getValue()) {
				setResolution(Resolution.TODO.getValue());
			}
    		setWorkflowPhase(nextWorkflowPhase);
    		setComplete((getWorkflowPhase() * 100) / (getWorkflowSize() + 1));
    		return true;
    	}
    	return false;
    }
    
    /**
     * <<Transient>> Método recomendado para cancelar o relatório.
     * 
     * <p>
     * Atualiza a resolução para Resolution.DONE, coloca o progresso em 0%, 
     * o fluxo de aprovação na éltima fase e indica cancelamento através do 
     * estado de execução.
     * </p>
     * 
     * @param actualCancelDate
     */
    public void cancel(Date actualCancelDate) {
    	if (actualCancelDate==null) {
    		actualCancelDate = new Date();
    	}
		setResolution(Resolution.DONE.getValue());
		setComplete(0);
		setWorkflowPhase(getWorkflowSize() + 1);
		setRunState(-1);
		setActualEndDate(actualCancelDate);
		setNextCheckDate(getActualEndDate());
    }
    
    /*
     * Outros métodos.
     */
    
    /**
     * Caso ainda não iniciado, data de inécio é a data programada.
     */
    @Override
    protected Date internalActualStartDate() {
    	if (getResolution()==Resolution.PRELIMINARY.getValue()) {
    		return getScheduledStartDate();
    	}
    	if (super.internalActualStartDate()==null) {
    		return new Date();
    	}
    	return super.internalActualStartDate();
    }
    
    /**
     * A data mais cedo entre inécio programado e verdadeiro.
     */
    public Date getEarliestDate() {
    	if (getScheduledStartDate()!=null && getScheduledStartDate().before(getActualStartDate())) {
    		return getScheduledStartDate();
    	}
    	return getActualStartDate();
    }
    
    /**
     * Caso ainda não terminado, data de fim é a data programada.
     */
    @Override
    protected Date internalActualEndDate() {
    	if (getResolution()!=Resolution.DONE.getValue()) {
    		return getScheduledEndDate();
    	}
    	if (super.internalActualEndDate()==null) {
    		return new Date();
    	}
    	return super.internalActualEndDate();
    }

    /**
     * A data mais tarde entre fim programado e verdadeiro.
     */
    public Date getLatestDate() {
    	if (getScheduledEndDate()!=null && !getScheduledEndDate().before(getActualEndDate())) {
    		return getScheduledEndDate();
    	}
    	return getActualEndDate();
    }
    
    /**
     * Código do relatório.
     */
    public String getReportCode() {
		return internalReportCode(reportCode);
	}
    public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
    
    /**
     * Permite que sublcasses alterem o código do relatório.
     * 
     * @param reportCode
     */
    protected String internalReportCode(String reportCode) {
//    	if (reportCode!=null && reportCode.length()>0) {
//    		return reportCode;
//    	}
    	if (getNumberPattern()!=null && getNumberPattern().length()>0) {
    		return new DecimalFormat(getNumberPattern()).format(getInternalNumber());
    	}
		return reportCode;
	}
    
    /**
     * Padrão para formatação de códigos de relatório.
     */
    public String getNumberPattern() {
    	if (getReportFolder()!=null && getReportFolder().getReportNumberPattern()!=null) {
    		return getReportFolder().getReportNumberPattern();
    	}
		return "#";
	}
    
    /**
     * Extrai o prefixo do padrão para formatação de códigos de relatório.
     */
    public String getNumberPatternPrefix() {
    	String[] prefixSegments = getNumberPattern().split("'");
    	if (prefixSegments.length>1) {
    		return prefixSegments[1];
    	}
		return String.valueOf(getContentType());
	}
    
    /**
     * Relator.
     * @see {@link Identity}
     */
	public Identity getReporter() {
		return reporter;
	}
	public void setReporter(Identity reporter) {
		this.reporter = reporter;
	}
	
    /**
     * Verdadeiro se não há relator.
     */
	public boolean isReporterNull() {
		return getReporter()==null ? true : false;
	}

    /**
     * ID do relator, ou do solcitante, na falta do primeiro.
     */
	public long getReporterId() {
    	if (getReporter()!=null) {
    		return getReporter().getId();
    	}
		if (getOwner()!=null) {
			return getOwner().getId();
		}
		return 0L;
	}
    
    /**
     * Nome a exibir do relator, ou do solcitante, na falta do primeiro.
     */
	public String getReporterDisplayName() {
    	if (getReporter()!=null) {
    		return getReporter().getDisplayName();
    	}
		if (getOwner()!=null) {
			return getOwnerDisplayName();
		}
		return "";
	}
    
    /**
     * Apelido do relator, ou do solcitante, na falta do primeiro.
     * @deprecated
     */
	public String getReporterOptionalAlias() {
        return isReporterNull() ? getOwnerDisplayName() : reporter.getDisplayName();
	}
    
    /**
     * Nome do relator, ou do solcitante, na falta do primeiro.
     */
	public String getReporterName() {
    	if (getReporter()!=null) {
    		return getReporter().getIdentityName();
    	}
		if (getOwner()!=null) {
			return getOwnerName();
		}
		return "";
	}
    
    /**
     * Avaliado.
     * @see {@link Identity}
     */
	public Identity getAssessee() {
		return assessee;
	}
	public void setAssessee(Identity assessee) {
		this.assessee = assessee;
	}
	
    /**
     * <<Transient>> Verdadeiro se não há avaliado.
     */
	public boolean isAssesseeNull() {
		return getAssessee()==null ? true : false;
	}

    /**
     * <<Transient>> Apelido do avaliado.
     */
	public String getAssesseeDisplayName() {
        return isAssesseeNull() ? "" : getAssessee().getDisplayName();
	}
    
    /**
     * <<Transient>> Apelido do avaliado.
     * @deprecated
     */
	public String getAssesseeOptionalAlias() {
        return isAssesseeNull() ? "" : getAssessee().getDisplayName();
	}
    
    /**
     * <<Transient>> Nome do avaliado.
     */
	public String getAssesseeName() {
        return isAssesseeNull() ? "" : getAssessee().getIdentityName();
	}
    
    /**
     * O estado de espera completa a informação em {@link #getResolution()}.
     * 
     * <p>
     * Caso seja 0, não há alteração na resolução, podendo significar planejado,
     * em andamento ou encerrado de acordo com ela. Valor negativo (-1) significam negação
     * da resolução: nãoo iniciar (improcedente), não continuar (suspenso) e não concluir
     * (cancelado). Valor positivo significa execução contínua (repetição).
     * </p>
     */
    public int getRunState() {
		return runState;
	}
    public void setRunState(int runState) {
		this.runState = runState;
	}
    
	/**
	 * Um prefixo para o sumário da tarefa.
	 */
    public String getPrefix() {
		return prefix;
	}
    protected StringBuilder getPrefixAsBuilder() {
    	if (getPrefix()!= null && !getPrefix().equals("")) {
    		return new StringBuilder(getPrefix()).append(" ");
    	}
    	return new StringBuilder();
    }
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
   
    /**
     * <<Transient>> Permite subordinar o prefixo do relatório a uma condição externa.
     */
    protected String getInternalPrefix() {
    	if (getSeries()!=null) {
    		this.prefix=getSeries().getFolderCode();
    	}
    	return this.prefix;
    }
    
    /**
     * Descrição sumária.
     */
    public String getSummary() {
        return getInternalSummary();
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    /**
     * <<Transient>> Permite subordinar o resumo do relatório a uma condição externa.
     */
    protected String getInternalSummary() {
    	if (getAssessee()!=null) {
    		this.summary=getAssesseeName();
    	}
    	return this.summary;
    }
    
    /**
     * <<Transient>> Verifica se pode alterar o resumo.
     */
    public boolean isSummaryEditable() {
    	if (getAssessee()==null) {
    		// se não existe uma pessoa em avaliação, permite edição
    		return true;
    	}
    	return false;
    }
    
    /**
     * <<Transient>> Descrição suméria com no méximo 20 caracteres.
     */
    public String getTaskShortSummary() {
    	if (summary==null) {
    		return "";
    	}
		if (summary.length() < 20) {
			return summary;
		}
		return new StringBuilder(summary.substring(0, 17)).append(" ...").toString();
	}

	public String getLabel() {
		return getSummary();
	}
    
    /**
     * Descrição completa.
     */
    public String getTaskDesc() {
        return this.taskDesc;
    }
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
    
    public String getReportDesc() {
        return this.taskDesc;
    }
    public void setReportDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
    
    /**
     * Pasta que guarda este documento.
     */
    public ReportFolder getSeries() {
    	return this.series;
    }
    public void setSeries(ReportFolder series) {
    	if (getEntity()==null && series.getEntity()!=null) {
    		setEntity(series.getEntity());
    	}
		this.series = series;
	}

    public int getReportFolderId() {
		return reportFolderId;
	}
	public void setReportFolderId(int reportFolderId) {
		this.reportFolderId = reportFolderId;
	}
    
    /**
     * <<Transient>> Conveniente para exibir a pasta que guarda este documento.
     */
    @JsonIgnore
    public ReportFolder getReportFolder() {
    	return this.series;
    }
    public void setReportFolder(ReportFolder series) {
    	if (getEntity()==null && series.getEntity()!=null) {
    		setEntity(series.getEntity());
    	}
		this.series = series;
	}
    
    /**
     * <<Transient>> Conveniente para recuperar tipo de conteúdo.
     */
    public char getContentType() {
    	if (getReportFolder()!=null) {
    		return getReportFolder().getContentType();
    	}
    	return '_';
    }
    
    /**
     * <<Transient>> Conveniente recuperar os membros da equipe.
     */
    @JsonIgnore
    public Set<StaffMember> getStaffMembers() {
    	if (getReportFolder()!=null) {
    		return getReportFolder().getStaff();
    	}
    	return new HashSet<StaffMember>(0);
    }
    
    /**
     * Conveniente para exibir o código da pasta que guarda este documento.
     * 
     * <p>
     * Caso não haja pasta, retorna o nome especial "JOURNAL".
     * </p>
     */
    public String getReportFolderCode() {
		if(getReportFolder()!=null) {
			return getReportFolder().getFolderCode();
		}
    	return "JOURNAL";
	}
    
    /**
     * Conveniente para exibir o nome da pasta que guarda este documento.
     */
    public String getReportFolderName() {
		if(getReportFolder()!=null) {
			return getReportFolder().getFolderName();
		}
    	return "";
	}
    
    /**
     * Conveniente para determinar se pode ser planejado pela fase.
     */
    @JsonIgnore
    public boolean isScheduleByPhaseEnabled() {
    	if (getSeries()!=null && getSeries().getPhases()!=null && getSeries().getPhases().size()>0) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Conveniente para listar as fases em ordem.
     */
    @JsonIgnore
    public List<ReportPhase> getPhases() {
    	List<ReportPhase> phases = new ArrayList<ReportPhase>();
    	if (isScheduleByPhaseEnabled()) {
    		phases.addAll(getSeries().getPhases());
    	}
    	return phases;
    }
    
    /**
     * Natureza do relatório, como lista csv de palavras-chave.
     */
    public String getNature() {
		return nature;
	}
    public void setNature(String nature) {
		this.nature = nature;
	}
    
	public String[] getNatureAsArray() {
		return StringListUtils.stringToArray(getNature());
	}
	public void setNatureAsArray(String[] natureArray) {
		setNature(StringListUtils.arrayToString(natureArray));
	}


    
    public byte[] getContent() {
    	if (getReportContent()!=null) {
    		return getReportContent().getContent();
    	}
        return new byte[0];
    }
    public void setContent(byte[] content) {
    	if (getReportContent()!=null) {
    		getReportContent().setContent(content);
    	}
    }
    @JsonIgnore
    public void setContent(String content) {
    	if (getReportContent()!=null) {
    		getReportContent().setContent(content);
    	}
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
		setContent(contentAsString);
	}
    
    public int getContentSize() {
    	if (getReportContent()!=null) {
    		return getReportContent().getContentSize();
    	}
    	return 0;
    }
    
	public String getEncoding() {
    	if (getReportContent()!=null) {
    		return getReportContent().getEncoding();
    	}
    	return "iso_88591";
	}
	public void setEncoding(String encoding) {
    	if (getReportContent()!=null) {
    		getReportContent().setEncoding(encoding);
    	}
	}

	public String getMultipartFileContentType() {
    	if (getReportContent()!=null) {
    		return getReportContent().getMultipartFileContentType();
    	}
    	return "text/plain";
	}
	public void setMultipartFileContentType(String multipartFileContentType) {
    	if (getReportContent()!=null) {
    		getReportContent().setMultipartFileContentType(multipartFileContentType);
    	}
	}
	
    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "text".
     */
    public boolean isText() {
    	if (getMultipartFileContentType().startsWith("text")) {
    		return true;
    	}
    	return false;
    }

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "text/html".
     */
    public boolean isHtml() {
    	if (getMultipartFileContentType().startsWith("text/html")) {
    		return true;
    	}
    	return false;
    }

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "image".
     */
    public boolean isImage() {
    	if (getMultipartFileContentType().startsWith("image")) {
    		return true;
    	}
    	return false;
    }

    /**
     * By default, a document can be changed.
     */
    public boolean isLocked() {
    	return false;
    }

    /**
     * Report result getter.
     */
    public UploadableContent getReportContent() {
		return reportContent;
	}
    public void setReportContent(UploadableContent reportContent) {
		this.reportContent = reportContent;
	}
    
    /**
     * External folder name.
     */
    public String getReportFile() {
		return reportFile;
	}
    public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
    
    /**
     * Type of external folder.
     */
    public String getReportFileContentType() {
		return reportFileContentType;
	}
    public void setReportFileContentType(String reportFileContentType) {
		this.reportFileContentType = reportFileContentType;
	}

	/**
	 * <<Transient>> Convenience property to hold uploaded data.
	 */
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	/**
	 * <<Transient>> Convenience method to read uploaded data.
	 */
	public void processFile() throws IOException {
		setContent(file.getBytes());
		setMultipartFileContentType(file.getContentType());
	}
	
	/*
	 * Monitoring reports.
	 */
	
	/**
	 * Size or effort planned.
	 */
	public BigDecimal getForecastWork() {
		return forecastWork;
	}
	public void setForecastWork(BigDecimal forecastWork) {
		this.forecastWork = forecastWork;
	}
	
	/**
	 * Size or effort realized.
	 */
	public BigDecimal getActualWork() {
		return actualWork;
	}
	public void setActualWork(BigDecimal actualWork) {
		this.actualWork = actualWork;
	}
	
	public char getWorkingUnit() {
		return 'H';
	}
	
	/**
	 * Relative Size.
	 */
	public int getRelativeSize() {
		return relativeSize;
	}
	public void setRelativeSize(int relativeSize) {
		this.relativeSize = relativeSize;
	}
	
	/**
	 * Remaining progress, additional to progress already occured.
	 * 
	 * <p>
	 * Formula: (1 - (${complete}/100)).
	 * </p>
	 */
	public BigDecimal getRemainingProgress() {
		return BigDecimal.ONE.add(new BigDecimal(getComplete()).divide(new BigDecimal(-100)));
	}
	
	/**
	 * Remaining size,  calculated in function of remaining progress.
	 * <p>
	 * Formula: (${relativeSize} * ${remainingProgress}).
	 * </p>
	 */
	public BigDecimal getRemainingSize() {
		return new BigDecimal(getRelativeSize()).multiply(getRemainingProgress());
	}
	
	/*
	 * Implementation of interface programmable, to use scripts.
	 */
	
	public String[] getScriptItemsAsArray() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItemsAsArray();
		}
		return null;
	}
	
	public List<String> getScriptList() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptList();
		}
		return null;
	}
	
    public String getScriptItems() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItems();
		}
		return "";
	}
	
    /**
     * Participants
     */
    public Set<Participant> getParticipants() {
        return this.participants;
    }
    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }
    
	/**
	 * Follow Up Order.
	 */
    public char getFollowUpOrder() {
        return this.followUpOrder;
    }
    public void setFollowUpOrder(char followUpOrder) {
        this.followUpOrder = followUpOrder;
    }
    public void setFollowUpOrderAsEnum(FollowUpOrder followUpOrder) {
        this.followUpOrder = followUpOrder.getValue();
    }
    
    /**
     * List of items separate by comma to controlling visualization.
     */
    public String getPresentationOrder() {
		return presentationOrder;
	}
    public void setPresentationOrder(String presentationOrder) {
		this.presentationOrder = presentationOrder;
	}
    
    /**
     * <<Transient>> Matrix of order of presentation. 
     * 
     */
    public String[] getPresentationOrderAsArray() {
		return StringListUtils.stringToArray(getPresentationOrder());
	}
	public void setPresentationOrderAsArray(String[] presentationOrderArray) {
		setPresentationOrder(StringListUtils.arrayToString(presentationOrderArray));
	}

	/**
	 * List of items separate by comma to keep traceability.
     */
    public String getTraceability() {
		return traceability;
	}
    public void setTraceability(String traceability) {
		this.traceability = traceability;
	}
    
    /**
     * <<Transient>> List of traceability.
     */
    public String[] getTraceabilityAsArray() {
		return StringListUtils.stringToArray(getTraceability());
	}
	public void setTraceabilityAsArray(String[] traceabilityArray) {
		setTraceability(StringListUtils.arrayToString(traceabilityArray));
	}
    
    /**
     * Type Request.
     */
    public char getRequestType() {
        return this.requestType;
    }
    public void setRequestType(char requestType) {
        this.requestType = requestType;
    }
    public void setRequestTypeAsEnum(RequestType requestType) {
        this.requestType = requestType.getValue();
    }

    /**
     * Action Type 
     */
    public char getActionType() {
        return this.actionType;
    }
    public void setActionType(char actionType) {
        this.actionType = actionType;
    }
    public void setActionTypeAsEnum(ActionType actionType) {
        this.actionType = actionType.getValue();
    }
    
    /**
     * Phase.
     */
    public char getPhase() {
		return phase;
	}
    public void setPhase(char phase) {
		this.phase = phase;
	}
    
    /**
     * Phase like instance of class Phase.
     */
    @JsonIgnore
    public ReportPhase getPhaseAsObject() {
    	if (isScheduleByPhaseEnabled()) {
    		for (ReportPhase phase: getSeries().getPhases()) {
    			if (getPhase()==phase.getLiteral()) {
    				return phase;
    			}
    		}
    	}
		return null;
	}
    
    /**
     * Category.
     * @see {@link Partner}
     */
    @JsonIgnore
	public Category getCategory() {
		return getInternalCategory();
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
	 * <<Transient>> True if category it's the folder.
	 */
    @JsonIgnore
	public boolean isFolderCategoryEnabled() {
		return getReportFolder()!=null && getReportFolder().isCategoryEnabled();
	}
	
	/**
	 * <<Transient>> Allow subclass update category in function of state of report. 
	 * 
	 * <p>
	 * Default implementation inherits category of folder.
	 * </p>
	 */
    @JsonIgnore
	protected Category getInternalCategory() {
		if (isCategoryOverrideAllowed()) {
			return category;
		}
		if (isFolderCategoryEnabled()) {
			return getReportFolder().getCategory();
		}
		return category;
	}
	
	/**
	 * <<Transient>> True if category exists.
	 */
    @JsonIgnore
	public boolean isCategoryEnabled() {
		return getCategory()!=null;
	}
	
	/**
	 * <<Transient>> True if is allowed override folder. 
	 */
    @JsonIgnore
	public boolean isCategoryOverrideAllowed() {
		return isFolderCategoryEnabled() && getReportFolder().isCategoryOverrideAllowed();
	}
	
    /**
     * Parsed Content.
     */
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
    /**
     * Partner.
     * @see {@link Partner}
     */
    @JsonIgnore
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	/**
	 * <<Transient>> Properties from category.
	 */
    @JsonIgnore
	public Map<String, Object> getCustomPropertiesAsMap() {
		if (isCategoryEnabled()) {
			return getCategory().getCustomPropertiesAsMap();
		}
		return new HashMap<String, Object>();
	}
	
    /**
     * <<Transient>> List of responsibilities converted on matrix.  
     */
    @JsonIgnore
    public String[] getCustomWorkflowRolesAsArray() {
    	if (isCategoryEnabled() && getCategory().isWorkflowEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsArray();
    	}
		return new String[0];
	}
    
    /**
     * <<Transient>> List of responsibilities converted on map.  
     * 
     */
    @JsonIgnore
    public Map<String, String> getCustomWorkflowRolesAsMap() {
    	if (isCategoryEnabled() && getCategory().isWorkflowEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsMap();
    	}
		return new HashMap<String, String>();
	}
    
    /**
     * <<Transient>> Conveniente para recuperar a próxima responsabilidade 
     * na lista de aprovação, ou nulo, se não houver.
     */
    @JsonIgnore
    public String getNextWorkflowRole() {
    	if (isCategoryEnabled() && getCategory().isWorkflowEnabled()) {
    		if (!isWorkflowClosable()) {
    			return getCustomWorkflowRolesAsArray()[getWorkflowPhase()];
    		}
    	}
		return null;
	}
    
    /**
     * Phase of workflow.
     */
    public int getWorkflowPhase() {
		return workflowPhase;
	}
    public void setWorkflowPhase(int workflowPhase) {
		this.workflowPhase = workflowPhase;
	}
    
    /**
     * <<Transient>> Determine through list of responsibilities, size list of approval workflow.
     */
    public int getWorkflowSize() {
    	return getCustomWorkflowRolesAsArray().length;
    }
    
    /**
     * <<Transient>> Determine, through list of responsibilities  if workflow
     * is required to this report.
     */
    public boolean isWorkflowRequired() {
    	return getWorkflowSize() > 0;
    }
    
    /**
     * <<Transient>> When workflow is require, just can be closed if the report are in your last phase.
     */
    public boolean isWorkflowClosable() {
    	if (isWorkflowRequired() && getWorkflowPhase()!=getWorkflowSize()) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * <<Transient>> Determines obtained advance in every stage of workflow.
     */
    public int getWorkflowProgressIncrement() {
    	return 0;
    }
    
    /**
     * Refer to a sequence chosen to principal document.
     */
    public int getMainRequirementSequence() {
		return mainRequirementSequence;
	}
    public void setMainRequirementSequence(int mainRequirementSequence) {
		this.mainRequirementSequence = mainRequirementSequence;
	}
    
    /**
     * Set of accompaniment.
     */
    @JsonIgnore
    public Set<FollowUp> getFollowUps() {
        return this.followUps;
    }
    public void setFollowUps(Set<FollowUp> followUps) {
        this.followUps = followUps;
    }
    
    /**
	 * <<Transient>> List order by accompaniment.
	 */
    @JsonIgnore
    public List<FollowUp> getFollowUpOrderedList() {
        return this.followUpList;
    }
    public void setFollowUpOrderedList(List<FollowUp> followUpList) {
        this.followUpList = followUpList;
    }
    
    /**
     * <<Transient>> List children.
     * 
     */
    @JsonIgnore
    public List<Report> getChildren() {
    	return this.children;
    }
    public void setChildren(List<Report> children) {
		this.children = children;
	}
    
    /**
     * <<Transient>> Count items.
     */
    public int getCountItems() {
		return countItems;
	}
    public void setCountItems(int countItems) {
		this.countItems = countItems;
	}
    
    /**
     * <<Transient>> Count Alerts.
     */
    public int getCountAlerts() {
		return countAlerts;
	}
    public void setCountAlerts(int countAlerts) {
		this.countAlerts = countAlerts;
	}
    
    /**
     * <<Transient>> Count Warnings.
     */
    public int getCountWarnings() {
		return countWarnings;
	}
    public void setCountWarnings(int countWarnings) {
		this.countWarnings = countWarnings;
	}
    
    /**
     * <<Transient>> Count likes.
     */
    public int getCountLikes() {
		return countLikes;
	}
    public void setCountLikes(int countLikes) {
		this.countLikes = countLikes;
	}
    
    /**
     * <<Transient>> Indicates favourite.
     */
    public char getFavourite() {
		return favourite;
	}
    public void setFavourite(char favourite) {
		this.favourite = favourite;
	}
    
	/**
	 * Comparator to accompaniment.
	 */
    public int compare(FollowUp followUp, FollowUp other) {
    	if (getFollowUpOrder()==FollowUpOrder.FIRST_DATE_ON_TOP.getValue()) {
    		return (int) (followUp.getIssueDate().getTime() - other.getIssueDate().getTime());
    	}
		return (int) (other.getIssueDate().getTime() - followUp.getIssueDate().getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getEntity() == null) ? 0 : getEntity().hashCode());
		result = prime * result
				+ ((getReportCode() == null) ? 0 : getReportCode().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( (other == null ) ) return false;
        if ( !(other instanceof Report) ) return false;
		Report castOther = (Report) other;
		
		if (getEntity() == null) {
			if (castOther.getEntity() != null) {
				return false;
			}
		} else if (!getEntity().equals(castOther.getEntity())) {
			return false;
		}
		
		if (getReportCode() == null) {
			if (castOther.getReportCode() != null) {
				return false;
			}
		} else if (!getReportCode().equals(castOther.getReportCode())) {
			return false;
		}
		
		return true;
	}

}


