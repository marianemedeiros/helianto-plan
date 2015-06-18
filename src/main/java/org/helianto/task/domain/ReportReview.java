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

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.helianto.core.def.Resolution;
import org.helianto.core.domain.Category;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.internal.AbstractEvent;
import org.helianto.core.internal.InterpretableCategory;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Avaliação para um relatório.
 * 
 * <p>
 * Esta classe amplia o comportamento de um simples acompanhamento para dar consitência
 * ao fechamento dos relatórios. É através dela que o campo "resolução" da classe "relatório"
 * deve ser atualizado, sendo desencorajada sua atualização por outros meios.
 * </p>
 * 
 * <p>
 * Esta classe é sensível à necessidade de fluxo de aprovação (workflow) e retém o nível de aprovação 
 * do proprietário no momento de sua criação. Com base nestes dados, um participante que tente
 * fechar o relatório em uma fase anterior à última, faz com que ele avance para a próxima fase.
 * </p>
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="task_review",
    uniqueConstraints = {@UniqueConstraint(columnNames={"reportId", "timeKey"})}
)
public class ReportReview 
	extends AbstractEvent
	implements InterpretableCategory {
	
    private static final long serialVersionUID = 1L;
    
    @JsonBackReference 
    @ManyToOne
    @JoinColumn(name="reportId", nullable=true)
    private Report report;
    
    private long timeKey;
    
    @Column(length=512)
    private String reviewText = "";
    
    private int workflowLevel;
    
    @Column(length=512)
    private String parsedContent = "";

	/** 
	 * Default constructor.
	 */
    public ReportReview() {
    	super();
        setIssueDate(new Date());
        setTimeKey(getIssueDate().getTime());
    }
    
    /** 
     * Key constructor.
     * 
     * @param report
     */
    public ReportReview(Report report) {
    	this();
        setReport(report);
        setResolution(report.getResolution());
    }

    /** 
     * Key constructor.
     * 
     * @param report
     * @param timeKey
     */
    public ReportReview(Report report, long timeKey) {
    	this(report);
        setTimeKey(timeKey);
    }

    /** 
     * Owner constructor.
     * 
     * @param report
     * @param owner
     */
    public ReportReview(Report report, Identity owner) {
    	this(report);
    	setOwner(owner);
    }

//    @Transient
    public Entity getEntity() {
    	if (getReport()!=null) {
    		return getReport().getEntity();
    	}
    	return null;
    }

    /**
     * Relatório de origem.
     */
    public Report getReport() {
    	return this.report;
    }
    public void setReport(Report report) {
    	this.report = report;
    }
    
    /**
     * Chave automética
     */
    public long getTimeKey() {
		return timeKey;
	}
    public void setTimeKey(long timeKey) {
		this.timeKey = timeKey;
	}
    
    /**
     * Texto da avaliação.
     */
    public String getReviewText() {
		return reviewText;
	}
    public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
    
    /**
     * Nível de workflow (aprovação) extraédo do participante no momento da revisão.
     */
    public int getWorkflowLevel() {
		return workflowLevel;
	}
    public void setWorkflowLevel(int workflowLevel) {
		this.workflowLevel = workflowLevel;
	}
    
    /**
     * Atualiza a resolução junto com a do relatório de origem.
     * 
     * <p>
     * Convém consultar se esta avaliação é a éltima antes de realizar
     * a atualização. Isto pode ser feito com o método {@link #isReportResolutionProtected()}
     * abaixo.
     * </p>
     */
//    @Transient
    public void setReportResolution(char resolution) {
    	if (getReport()!=null) {
    		getReport().setResolution(resolution);
    	}
    	super.setResolution(resolution);
    }
    
    /**
     * Protege contra alterações a resolução do relatório de origem caso esta avaliação não
     * tenha sido a éltima.
     * 
     * <p>
     * Para saber se é a éltima, o método apenas testa se os valores de origem e desta 
     * avaliação séo iguais. Se houver vérias avaliações com a mesma resolução em momentos 
     * diferentes do ciclo de vida do relatório de origem, o método não impede que uma avaliação
     * com a mesma resolução da éltima modifique o relatório de origem.
     * </p>
     */
//    @Transient
    public boolean isReportResolutionProtected() {
    	if (getReport()!=null) {
    		return getReport().getResolution()!=getResolution();
    	}
    	return true;
    }
    
    /**
     * Conveninte para utilizar safeReportResolution como propriedade na camada de apresentação.
     */
//    @Transient
    public char getSafeReportResolution() {
    	if (getReport()!=null) {
    		return getReport().getResolution();
    	}
    	throw new IllegalArgumentException("Must have a report to read this property!");
    }
    
    /**
     * Atualiza a resolução junto com a do relatório de origem caso 
     * não esteja protegida.
     * 
     * @param resolution
     */
//    @Transient
    public void setSafeReportResolution(char resolution) {
    	// Teste 1: o relatório está protegido por outra revisão?
    	if (!isReportResolutionProtected()) {
    		// Teste 2: tem fluxo de aprovação e quer encerrar uma fase?
    		if (getReport().isWorkflowRequired() && resolution==Resolution.DONE.getValue()) {
    			// Teste 3: o usuário pode modificar a resolução do relatório?
    			if (isOwnerAuthorizedToChangeReport()) {
    				// Teste 4: o relatório deve ser fechado?
    				if (getReport().isWorkflowClosable()) {
    					// ok, fechar relatório
    					getReport().close(null);
    				}
    				else {
        				// ao invés de fechar, podemos avançar o relatório para a próxima fase
        				getReport().forward();
    				}
    			}
    		}
    		else {
    			// No caso em que não tem fluxo de aprovação ou não quer encerrar
        		getReport().setResolution(resolution);
    		}
    	}
    	super.setResolution(resolution);
    }
    
    /**
     * Verdadeiro se o relatório se encontra numa fase do fluxo de aprovação igual ao nível 
     * de aprovação do proprietário desta revisão, ou também verdadeiro se o relatório não 
     * requer fluxo de aprovação.
     */
//    @Transient
    protected boolean isOwnerAuthorizedToChangeReport() {
		if (getReport().isWorkflowRequired()) {
			if (getReport().getWorkflowPhase()==getWorkflowLevel()) {
				return true;
			}
			return false;
		}
		return true;
    }

    
    /**
     * Atualiza a fase de aprovação do relatório de origem.
     * 
     * @param workflowPhase
     */
//    @Transient
    public void setReportWorkflowPhase(int workflowPhase) {
    	if (getReport()!=null && getReport().isWorkflowRequired()) {
    		getReport().setWorkflowPhase(workflowPhase);
    		// atualiza também o progresso
    		getReport().setComplete((workflowPhase * 100) / (getReport().getWorkflowSize() + 1));
    	}
    }
    
    /**
     * Conveninte para utilizar safeReportProgress como propriedade na camada de apresentação.
     */
//    @Transient
    public int getSafeReportProgress() {
    	if (getReport()!=null) {
    		return getReport().getComplete();
    	}
    	throw new IllegalArgumentException("Must have a report to read this property!");
    }
    
    /**
     * Atualiza o progresso do relatório de origem caso 
     * não esteja protegido.
     * 
     * @param complete
     */
//    @Transient
    public void setSafeReportProgress(int complete) {
    	if (!isReportResolutionProtected()) {
    		getReport().setComplete(complete);
    	}
    }
    
//	@Transient
	public Category getCategory() {
		if (getReport()!=null) {
			return getReport().getCategory();
		}
		return null;
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
	 * <<Transient>> Verdadeiro quando hé uma categoria disponível.
	 */
//	@Transient
	protected boolean isCategoryEnabled() {
		return getCategory()!=null && getCategory().getScriptItemsAsArray().length>0;
	}
	
    /**
     * Lista de scripts, como lista CSV dos códigos dos scripts.
     * 
     * <p>
     * Os scripts séo extraédos preferencialmente da categoria. Somente se não houver resultado
     * a pasta é entéo usada para extrair scripts.
     * </p>
     */
//    @Transient
    public String getScriptItems() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItems();
		}
		return "";
    }
    
//    @Transient
    public List<String> getScriptList() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptList();
		}
		return null;
    }
    
//    @Transient
    public String[] getScriptItemsAsArray() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItemsAsArray();
		}
		return null;
    }
    
    /**
     * toString
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("report#").append("='").append(getReport().getInternalNumber()).append("' ");
        buffer.append("issueDate").append("='").append(getIssueDate()).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }

    /**
     * equals
     */
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof ReportReview)) return false;
		ReportReview castOther = (ReportReview) other;

		return ((this.getReport() == castOther.getReport()) || (this
				.getReport() != null
				&& castOther.getReport() != null && this.getReport().equals(
				castOther.getReport())))
				&& (this.getTimeKey() == castOther.getTimeKey());
	}

	/**
	 * hashCode
	 */
	public int hashCode() {
		int result = 17;
		result = 37 * result + (int) this.getTimeKey();
		return result;
	}

}
