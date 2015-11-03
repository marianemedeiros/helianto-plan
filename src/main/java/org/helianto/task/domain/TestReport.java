package org.helianto.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.helianto.core.domain.Identity;
import org.helianto.core.internal.Extensible;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Relatórios de teste.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="test_report",
	uniqueConstraints = {@UniqueConstraint(columnNames={"testGroupId", "reportCode"})}
)
public class TestReport 
	implements Extensible {

	private static final long serialVersionUID = 1L;
   
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Version
    private Integer version;
	
	@ManyToOne
    @JoinColumn(name="testGroupId", nullable=true)
	private TestGroup testGroup;
	
	@Column(length=20)
	private String reportCode = "";
	
	@Column(length=32)
	private String rootFolder = "";
	
	@ManyToOne
    @JoinColumn(name="reporterId", nullable=true)
    private Identity reporter;
	
	@DateTimeFormat(style="SS")
	@Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
	
    private char resolution = 'P';
    
    @Column(length=4096)
    private String parsedContent = "";
    
    @Column(length=4096)
    private String reportComment = "";
    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
    	public TestReport merge(TestReport command) {
   			setId(command.getId());
   			setReportCode(command.getReportCode());
   			setRootFolder(command.getRootFolder());
   			setIssueDate(command.getIssueDate());
   			setResolution(command.getResolution());
   			setParsedContent(command.getParsedContent());
   			setReportComment(command.getReportComment());
   			return this;
   		}
    
    
    /**
     * Construtor.
     */
    public TestReport() {
		super();
		setIssueDate(new Date());
	}

    /**
     * Construtor chave.
     * 
     * @param testCase
     * @param reportCode
     */
    public TestReport(TestGroup testCase, String reportCode) {
		this();
		setTestGroup(testCase);
		setReportCode(reportCode);
	}

    /**
     * Primary key.
     */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Version.
     */
    public Integer getVersion() {
        return this.version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Grupo de teste.
     * 
     * @see {@link TestGroup}
     */
	public TestGroup getTestGroup() {
		return testGroup;
	}
    public void setTestGroup(TestGroup testGroup) {
		this.testGroup = testGroup;
	}

	/**
	 * Código.
	 */
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	
	/**
	 * Pasta raéz.
	 */
	public String getRootFolder() {
		return rootFolder;
	}
	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}

	/**
	 * Relator.
	 */
	public Identity getReporter() {
		return reporter;
	}
    public void setReporter(Identity reporter) {
		this.reporter = reporter;
	}

    /**
     * Data de emisséo.
     */
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Resolução.
	 */
	public char getResolution() {
		return resolution;
	}
	public void setResolution(char resolution) {
		this.resolution = resolution;
	}

	/**
	 * Conteúdo analisado.
	 */
	public String getParsedContent() {
		return parsedContent;
	}
	public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}

	/**
	 * Comentério.
	 */
	public String getReportComment() {
		return reportComment;
	}
	public void setReportComment(String reportComment) {
		this.reportComment = reportComment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reportCode == null) ? 0 : reportCode.hashCode());
		result = prime * result
				+ ((testGroup == null) ? 0 : testGroup.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TestReport)) {
			return false;
		}
		TestReport other = (TestReport) obj;
		if (reportCode == null) {
			if (other.reportCode != null) {
				return false;
			}
		} else if (!reportCode.equals(other.reportCode)) {
			return false;
		}
		if (testGroup == null) {
			if (other.testGroup != null) {
				return false;
			}
		} else if (!testGroup.equals(other.testGroup)) {
			return false;
		}
		return true;
	}

}
