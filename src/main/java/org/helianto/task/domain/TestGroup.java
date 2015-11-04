package org.helianto.task.domain;

import java.io.Serializable;
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
import org.helianto.core.domain.Operator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Tests report.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="test_group",
	uniqueConstraints = {@UniqueConstraint(columnNames={"operatorId", "testCode"})}
)
public class TestGroup
	implements Serializable 
{

	private static final long serialVersionUID = 1L;
    
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Version
    private Integer version;
	
	@ManyToOne
	@JoinColumn(name="operatorId", nullable=true)
	private Operator operator;
	
	@Column(length=20)
	private String testCode = "";
	
	@ManyToOne
	@JoinColumn(name="ownerId", nullable=true)
    private Identity owner;
	
	@DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
	
    private char resolution = 'P';
    
    @Column(length=4096)
    private String reportComment = "";
    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
    public TestGroup merge(TestGroup command) {
   			setId(command.getId());
   			setTestCode(command.getTestCode());
   			setIssueDate(command.getIssueDate());
   			setResolution(command.getResolution());
   			setReportComment(command.getReportComment());
   			return this;
   		}
    
    /**
     * Constructor.
     */
    public TestGroup() {
		super();
		setIssueDate(new Date());
	}

    /**
     * Constructor key.
     * 
     * @param operator
     * @param caseCode
     */
    public TestGroup(Operator operator, String caseCode) {
		this();
		setOperator(operator);
		setTestCode(caseCode);
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
     * Operator.
     * 
     * @see {@link Operator}
     */
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * Code.
	 */
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String caseCode) {
		this.testCode = caseCode;
	}

	/**
	 * Oowner.
	 */
	public Identity getOwner() {
		return owner;
	}
	public void setOwner(Identity owner) {
		this.owner = owner;
	}

    /**
     * Issue Date.
     */
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Resolution.
	 */
	public char getResolution() {
		return resolution;
	}
	public void setResolution(char resolution) {
		this.resolution = resolution;
	}

	/**
	 * Comment.
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
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result
				+ ((testCode == null) ? 0 : testCode.hashCode());
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
		if (!(obj instanceof TestGroup)) {
			return false;
		}
		TestGroup other = (TestGroup) obj;
		if (operator == null) {
			if (other.operator != null) {
				return false;
			}
		} else if (!operator.equals(other.operator)) {
			return false;
		}
		if (testCode == null) {
			if (other.testCode != null) {
				return false;
			}
		} else if (!testCode.equals(other.testCode)) {
			return false;
		}
		return true;
	}
    
    

}
