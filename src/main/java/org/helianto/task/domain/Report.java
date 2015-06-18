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
import javax.persistence.Embedded;
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
 * Relatórios.
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
    
    @ManyToOne
    @JoinColumn(name="reporterId", nullable=true)
    private Identity reporter;
    
    @ManyToOne
    @JoinColumn(name="assesseeId", nullable=true)
    private Identity assessee;
    
    @Column(length=32)
	private String prefix = "";
    
    @Column(length=64)
    private String summary = "";
    
    @Column(length=512)
    private String taskDesc = "";
    
    @ManyToOne
    @JoinColumn(name="reportFolderId")
    private ReportFolder series;
    
    @Column(length=20)
    private String nature = "";
    
    @Embedded
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
    
    @ManyToOne
    @JoinColumn(name="partnerId", nullable=true)
    private Partner partner;
    
    @ManyToOne
    @JoinColumn(name="categoryId", nullable=true)
    private Category category;
    
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
     * Anexa padrão de numeração à chave para geração de números.
     */
//    @Transient
    public String getInternalNumberKey() {
        return new StringBuilder("REPORT").append(getNumberPatternPrefix()).toString();
    }

//    @Transient
    public int getStartNumber() {
    	return 1;
    }
    
    /**
     * Anexa padrão de numeração à chave para geração de números.
     */
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
    public String getNumberPattern() {
    	if (getReportFolder()!=null && getReportFolder().getReportNumberPattern()!=null) {
    		return getReportFolder().getReportNumberPattern();
    	}
		return "#";
	}
    
    /**
     * Extrai o prefixo do padrão para formatação de códigos de relatório.
     */
//    @Transient
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
//    @Transient
	public boolean isReporterNull() {
		return getReporter()==null ? true : false;
	}

    /**
     * ID do relator, ou do solcitante, na falta do primeiro.
     */
//    @Transient
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
//    @Transient
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
//    @Transient
	public String getReporterOptionalAlias() {
        return isReporterNull() ? getOwnerDisplayName() : reporter.getDisplayName();
	}
    
    /**
     * Nome do relator, ou do solcitante, na falta do primeiro.
     */
//    @Transient
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
//    @Transient
	public boolean isAssesseeNull() {
		return getAssessee()==null ? true : false;
	}

    /**
     * <<Transient>> Apelido do avaliado.
     */
//    @Transient
	public String getAssesseeDisplayName() {
        return isAssesseeNull() ? "" : getAssessee().getDisplayName();
	}
    
    /**
     * <<Transient>> Apelido do avaliado.
     * @deprecated
     */
//    @Transient
	public String getAssesseeOptionalAlias() {
        return isAssesseeNull() ? "" : getAssessee().getDisplayName();
	}
    
    /**
     * <<Transient>> Nome do avaliado.
     */
//    @Transient
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
//    @Transient
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
//    @Transient
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
//    @Transient
    protected String getInternalSummary() {
    	if (getAssessee()!=null) {
    		this.summary=getAssesseeName();
    	}
    	return this.summary;
    }
    
    /**
     * <<Transient>> Verifica se pode alterar o resumo.
     */
//    @Transient
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
//    @Transient
    public String getTaskShortSummary() {
    	if (summary==null) {
    		return "";
    	}
		if (summary.length() < 20) {
			return summary;
		}
		return new StringBuilder(summary.substring(0, 17)).append(" ...").toString();
	}

//    @Transient
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
    
//    @Transient
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
    
    /**
     * <<Transient>> Conveniente para exibir a pasta que guarda este documento.
     */
//    @Transient
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
//    @Transient
    public char getContentType() {
    	if (getReportFolder()!=null) {
    		return getReportFolder().getContentType();
    	}
    	return '_';
    }
    
    /**
     * <<Transient>> Conveniente recuperar os membros da equipe.
     */
//    @Transient
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
//    @Transient
    public String getReportFolderCode() {
		if(getReportFolder()!=null) {
			return getReportFolder().getFolderCode();
		}
    	return "JOURNAL";
	}
    
    /**
     * Conveniente para exibir o nome da pasta que guarda este documento.
     */
//    @Transient
    public String getReportFolderName() {
		if(getReportFolder()!=null) {
			return getReportFolder().getFolderName();
		}
    	return "";
	}
    
    /**
     * Conveniente para determinar se pode ser planejado pela fase.
     */
//    @Transient
    public boolean isScheduleByPhaseEnabled() {
    	if (getSeries()!=null && getSeries().getPhases()!=null && getSeries().getPhases().size()>0) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Conveniente para listar as fases em ordem.
     */
//    @Transient
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
    
//	@Transient
	public String[] getNatureAsArray() {
		return StringListUtils.stringToArray(getNature());
	}
	public void setNatureAsArray(String[] natureArray) {
		setNature(StringListUtils.arrayToString(natureArray));
	}


    
//    @Transient
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
    	if (getReportContent()!=null) {
    		return getReportContent().getContentSize();
    	}
    	return 0;
    }
    
//	@Transient
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

//	@Transient
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
//    @Transient
    public boolean isText() {
    	if (getMultipartFileContentType().startsWith("text")) {
    		return true;
    	}
    	return false;
    }

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "text/html".
     */
//    @Transient
    public boolean isHtml() {
    	if (getMultipartFileContentType().startsWith("text/html")) {
    		return true;
    	}
    	return false;
    }

    /**
     * True if {@link #afterInternalNumberSet(long)} starts with "image".
     */
//    @Transient
    public boolean isImage() {
    	if (getMultipartFileContentType().startsWith("image")) {
    		return true;
    	}
    	return false;
    }

    /**
     * By default, a document can be changed.
     */
//    @Transient
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
     * Nome do arquivo externo.
     */
    public String getReportFile() {
		return reportFile;
	}
    public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
    
    /**
     * Tipo do arquivo externo.
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
//	@Transient
	public void processFile() throws IOException {
		setContent(file.getBytes());
		setMultipartFileContentType(file.getContentType());
	}
	
	/*
	 * Campos destinados ao monitoramento de relatórios.
	 */
	
	/**
	 * Tamanho ou esforço planejado.
	 */
	public BigDecimal getForecastWork() {
		return forecastWork;
	}
	public void setForecastWork(BigDecimal forecastWork) {
		this.forecastWork = forecastWork;
	}
	
	/**
	 * Tamanhou ou esforço realizado.
	 */
	public BigDecimal getActualWork() {
		return actualWork;
	}
	public void setActualWork(BigDecimal actualWork) {
		this.actualWork = actualWork;
	}
	
//	@Transient
	public char getWorkingUnit() {
		return 'H';
	}
	
	/**
	 * Tamanho relativo.
	 */
	public int getRelativeSize() {
		return relativeSize;
	}
	public void setRelativeSize(int relativeSize) {
		this.relativeSize = relativeSize;
	}
	
	/**
	 * Progresso restante, complementar ao progresso já ocorrido.
	 * 
	 * <p>
	 * Pela fórmula: (1 - (${complete}/100)).
	 * </p>
	 */
//	@Transient
	public BigDecimal getRemainingProgress() {
		return BigDecimal.ONE.add(new BigDecimal(getComplete()).divide(new BigDecimal(-100)));
	}
	
	/**
	 * Tamanho restante, calculado em função do progresso restante.
	 * <p>
	 * Pela fórmula: (${relativeSize} * ${remainingProgress}).
	 * </p>
	 */
//	@Transient
	public BigDecimal getRemainingSize() {
		return new BigDecimal(getRelativeSize()).multiply(getRemainingProgress());
	}
	
	/*
	 * Implementação da interface programmable, para uso com scripts.
	 */
	
//	@Transient
	public String[] getScriptItemsAsArray() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItemsAsArray();
		}
		return null;
	}
	
//	@Transient
	public List<String> getScriptList() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptList();
		}
		return null;
	}
	
//    @Transient
    public String getScriptItems() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItems();
		}
		return "";
	}
	
    /**
     * Conjunto de participantes.
     */
    public Set<Participant> getParticipants() {
        return this.participants;
    }
    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }
    
	/**
	 * Ordem dos acompanhamentos.
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
     * Lista de itens separados por virgula para controlar a visualização.
     */
    public String getPresentationOrder() {
		return presentationOrder;
	}
    public void setPresentationOrder(String presentationOrder) {
		this.presentationOrder = presentationOrder;
	}
    
    /**
     * <<Transient>> Matriz de ordem de apresentação.
     */
//    @Transient
    public String[] getPresentationOrderAsArray() {
		return StringListUtils.stringToArray(getPresentationOrder());
	}
	public void setPresentationOrderAsArray(String[] presentationOrderArray) {
		setPresentationOrder(StringListUtils.arrayToString(presentationOrderArray));
	}

	/**
     * Lista de itens separados por virgula para manter a rastreabilidade.
     */
    public String getTraceability() {
		return traceability;
	}
    public void setTraceability(String traceability) {
		this.traceability = traceability;
	}
    
    /**
     * <<Transient>> Lista de rastreabilidade.
     */
//    @Transient
    public String[] getTraceabilityAsArray() {
		return StringListUtils.stringToArray(getTraceability());
	}
	public void setTraceabilityAsArray(String[] traceabilityArray) {
		setTraceability(StringListUtils.arrayToString(traceabilityArray));
	}
    
    /**
     * Tipo de solicitação (interna, externa).
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
     * Tipo de ação (cntrole, execução).
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
     * Fase.
     */
    public char getPhase() {
		return phase;
	}
    public void setPhase(char phase) {
		this.phase = phase;
	}
    
    /**
     * Fase como uma instância da classe Phase.
     */
//    @Transient
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
     * Categoria.
     * @see {@link Partner}
     */
	public Category getCategory() {
		return getInternalCategory();
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * <<Transient>> Verdadeiro se a categoria é da pasta.
	 */
//	@Transient
	public boolean isFolderCategoryEnabled() {
		return getReportFolder()!=null && getReportFolder().isCategoryEnabled();
	}
	
	/**
	 * <<Transient>> Permite que as subclasses atualizem a categoria em função do estado do relatório.
	 * 
	 * <p>
	 * Implementação padrão herda a categoria da pasta, quando houver.
	 * </p>
	 */
//	@Transient
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
	 * <<Transient>> Verdadeiro se a categoria existe.
	 */
//	@Transient
	public boolean isCategoryEnabled() {
		return getCategory()!=null;
	}
	
	/**
	 * <<Transient>> Verdadeiro se pode substituir a categoria da pasta.
	 */
//	@Transient
	public boolean isCategoryOverrideAllowed() {
		return isFolderCategoryEnabled() && getReportFolder().isCategoryOverrideAllowed();
	}
	
    /**
     * Conteúdo transformado.
     */
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
    /**
     * Parceiro.
     * @see {@link Partner}
     */
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	/**
	 * <<Transient>> Propriedades derivadas da categoria.
	 */
//	@Transient
	public Map<String, Object> getCustomPropertiesAsMap() {
		if (isCategoryEnabled()) {
			return getCategory().getCustomPropertiesAsMap();
		}
		return new HashMap<String, Object>();
	}
	
    /**
     * <<Transient>> Lista de responsabilidades convertida em matriz.
     */
//    @Transient
    public String[] getCustomWorkflowRolesAsArray() {
    	if (isCategoryEnabled() && getCategory().isWorkflowEnabled()) {
    		return getCategory().getCustomWorkflowRolesAsArray();
    	}
		return new String[0];
	}
    
    /**
     * <<Transient>> Lista de responsabilidades convertida em mapa.
     */
//    @Transient
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
//    @Transient
    public String getNextWorkflowRole() {
    	if (isCategoryEnabled() && getCategory().isWorkflowEnabled()) {
    		if (!isWorkflowClosable()) {
    			return getCustomWorkflowRolesAsArray()[getWorkflowPhase()];
    		}
    	}
		return null;
	}
    
    /*
     * Métodos da interface <tt>WorkflowTarget</tt>.
     */
    
    /**
     * Fase do workflow.
     */
    public int getWorkflowPhase() {
		return workflowPhase;
	}
    public void setWorkflowPhase(int workflowPhase) {
		this.workflowPhase = workflowPhase;
	}
    
    /**
     * <<Transient>> Determina, através da lista de responsabilidades, o tamanho da lista
     * de aprovação do workflow.
     */
//    @Transient
    public int getWorkflowSize() {
    	return getCustomWorkflowRolesAsArray().length;
    }
    
    /**
     * <<Transient>> Determina, através da lista de responsabilidades, se o workflow 
     * é requerido para este relatório.
     */
//    @Transient
    public boolean isWorkflowRequired() {
    	return getWorkflowSize() > 0;
    }
    
    /**
     * <<Transient>> Quando o workflow é requerido, somente pode ser fechado se o
     * relatório estiver em sua última fase.
     */
//    @Transient
    public boolean isWorkflowClosable() {
    	if (isWorkflowRequired() && getWorkflowPhase()!=getWorkflowSize()) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * <<Transient>> Determina o avanço obtido em cada etapa do workflow.
     */
//    @Transient
    public int getWorkflowProgressIncrement() {
    	return 0;
    }
    
    /**
     * Conveniente para associar o principal documento a partir da lista.
     * 
     * @param mainRequirementList
     * @param mainSequence
     */
    @JsonIgnore
// TODO  
//    public void setMainRequirement(List<DocumentRequirement> mainRequirementList, int mainSequence) {
//    	for (DocumentRequirement mainRequirement: mainRequirementList) {
//    		if (mainRequirement.getSequence()==mainSequence) {
//    			setMainRequirement(mainRequirement);
//    			break;
//    		}
//    	}
//	}
//    
    /**
     * Reere-se à sequência escolhida para o pricipal documento.
     */
    public int getMainRequirementSequence() {
		return mainRequirementSequence;
	}
    public void setMainRequirementSequence(int mainRequirementSequence) {
		this.mainRequirementSequence = mainRequirementSequence;
	}
    
    /**
     * Conveniente para associar o principal documento a partir da lista.
     * 
     * @param mainRequirementList
     */
    @JsonIgnore
    //TODO
//    public void setMainRequirement(List<DocumentRequirement> mainRequirementList) {
//    	if (getMainRequirementSequence()>=0 && getMainRequirementSequence() < mainRequirementList.size()) {
//    		setMainRequirement(mainRequirementList, getMainRequirementSequence());
//    	}
//	}
    
    /**
     * Conjunto de acompanhamentos.
     */
    public Set<FollowUp> getFollowUps() {
        return this.followUps;
    }
    public void setFollowUps(Set<FollowUp> followUps) {
        this.followUps = followUps;
    }
    
    /**
	 * <<Transient>> Lista ordenada de acompanhamentos.
	 */
//    @Transient
    public List<FollowUp> getFollowUpOrderedList() {
        return this.followUpList;
    }
    public void setFollowUpOrderedList(List<FollowUp> followUpList) {
        this.followUpList = followUpList;
    }
    
    /**
     * <<Transient>> Lista descendentes.
     * 
     */
    public List<Report> getChildren() {
    	return this.children;
    }
    public void setChildren(List<Report> children) {
		this.children = children;
	}
    
    /**
     * <<Transient>> Conta itens.
     */
    public int getCountItems() {
		return countItems;
	}
    public void setCountItems(int countItems) {
		this.countItems = countItems;
	}
    
    /**
     * <<Transient>> Conta alertas.
     */
    public int getCountAlerts() {
		return countAlerts;
	}
    public void setCountAlerts(int countAlerts) {
		this.countAlerts = countAlerts;
	}
    
    /**
     * <<Transient>> Conta avisos.
     */
    public int getCountWarnings() {
		return countWarnings;
	}
    public void setCountWarnings(int countWarnings) {
		this.countWarnings = countWarnings;
	}
    
    /**
     * <<Transient>> Conta votos.
     */
    public int getCountLikes() {
		return countLikes;
	}
    public void setCountLikes(int countLikes) {
		this.countLikes = countLikes;
	}
    
    /**
     * <<Transient>> Indica favorito.
     */
    public char getFavourite() {
		return favourite;
	}
    public void setFavourite(char favourite) {
		this.favourite = favourite;
	}
    
	/**
	 * Comparador para acompanhamentos.
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


