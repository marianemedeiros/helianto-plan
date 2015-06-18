package org.helianto.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.helianto.core.def.Resolution;
import org.helianto.core.domain.Identity;
import org.helianto.core.internal.Extensible;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Confirmação de recebimento de mensagem.
 * 
 * @author mauriciofernandesdecastro
 */
@javax.persistence.Entity
@javax.persistence.Table(name="doc_confirmation",
	uniqueConstraints = {@UniqueConstraint(columnNames={"articleId", "sequence"})}
)
public class Confirmation 
	implements Extensible {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Version
    private Integer version;
    
    @ManyToOne
    @JoinColumn(name="articleId", nullable=true)
    private Article article;
    
    private int sequence;
    
    @JsonBackReference 
    @ManyToOne
    @JoinColumn(name="identityId", nullable=true)
    private Identity identity;
    
    @DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate = new Date();
    
    private char resolution = Resolution.PRELIMINARY.getValue();
    
    @Column(length=512)
    private String parsedContent = "";
    
    /**
     * Constructor.
     */
    public Confirmation() {
		super();
	}

    /**
     * Key constructor.
     * 
     * @param article
     * @param sequence
     */
    public Confirmation(Article article, int sequence) {
		this();
		setArticle(article);
		setSequence(sequence);
	}

    /**
     * Identity constructor.
     * 
     * @param article
     * @param identity
     */
    public Confirmation(Article article, Identity identity) {
		this(article, 0);
		setIdentity(identity);
	}

    /**
     * Resolution constructor.
     * 
     * @param article
     * @param identity
     * @param resolution
     */
    public Confirmation(Article article, Identity identity, char resolution) {
		this(article, identity);
		setResolution(resolution);
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
     * <<NaturalKey>> Artigo.
     * @see {@link Article}
     */
    public Article getArticle() {
		return article;
	}
    public void setArticle(Article article) {
		this.article = article;
	}
    
    /**
     * Sequence.
     */
    public int getSequence() {
		return sequence;
	}
    public void setSequence(int sequence) {
		this.sequence = sequence;
	}
    
    /**
     * Identity.
     * @see {@link Identity}
     */
    public Identity getIdentity() {
		return identity;
	}
    public void setIdentity(Identity identity) {
		this.identity = identity;
	}
    
    /**
     * Issue date.
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
     * Conteúdo transformado.
     */
   
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + sequence;
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
		if (!(obj instanceof Confirmation)) {
			return false;
		}
		Confirmation other = (Confirmation) obj;
		if (article == null) {
			if (other.article != null) {
				return false;
			}
		} else if (!article.equals(other.article)) {
			return false;
		}
		if (sequence != other.sequence) {
			return false;
		}
		return true;
	}
    
}
