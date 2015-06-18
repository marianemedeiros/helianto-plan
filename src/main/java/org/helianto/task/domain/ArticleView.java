package org.helianto.task.domain;

import java.io.Serializable;
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

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.user.domain.User;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Visualização para um artigo.
 * 
 * @author mauriciofernandesdecastro
 */
@javax.persistence.Entity
@javax.persistence.Table(name="doc_view", 
	uniqueConstraints = {@UniqueConstraint(columnNames={"ownerId", "articleId"})}
)
public class ArticleView 
	implements Serializable
{
	
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Version
    private Integer version;
    
    @ManyToOne
    @JoinColumn(name="ownerId", nullable=true)
    private Identity owner;
    
    @ManyToOne
    @JoinColumn(name="articleId", nullable=true)
	private Article article;
    
    @DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
    
    private int relevance;
    
    @Column(length=128)
    private String articleVote = "";
	
	/**
	 * Constructor.
	 */
	public ArticleView() {
		super();
		setIssueDate(new Date());
	}
	
	/**
	 * Article constructor.
	 * 
	 * @param article
	 */
	public ArticleView(Article article) {
		this();
		setArticle(article);
	}
	
	/**
	 * Key constructor.
	 * 
	 * @param owner
	 * @param article
	 */
	public ArticleView(Identity owner, Article article) {
		this(article);
		setOwner(owner);
	}
	
	/**
	 * Form constructor.
	 * 
	 * @param article
	 * @param user
	 */
	public ArticleView(Article article, User user) {
		this(article);
		setOwner(user.getIdentity());
	}
	
//	@Transient
	public Entity getEntity() {
		if (getArticle()!=null) {
			return getArticle().getEntity();
		}
		return null;
	}
	
//	@Transient
	public int getStartNumber() {
		return 1;
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
     * Proprietário.
     * @see {@link Identity}
     */
	public Identity getOwner() {
		return owner;
	}
	public void setOwner(Identity owner) {
		this.owner = owner;
	}   

	/**
     * <<Transient>> Conveniente para apresentar o nome a exibir.
     */
//    @Transient
	public String getOwnerDisplayName() {
        return getOwner()==null ? "" : owner.getDisplayName();
	}
    
    /**
     * <<Transient>> Conveniente para apresentar o nome.
     */
//    @Transient
	public String getOwnerName() {
        return getOwner()==null ? "" : owner.getIdentityName();
	}

    /**
     * Artigo a que pertence o comentério.
     */
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
    /**
     * Data de emisséo.
     */
    public Date getIssueDate() {
        return this.issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    
	/**
	 * Relevéncia do artigo.
	 */
	public int getRelevance() {
		return relevance;
	}
	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}

    /**
     * Voto (méx 128c.).
     */
    public String getArticleVote() {
		return articleVote;
	}
    public void setArticleVote(String articleVote) {
		this.articleVote = articleVote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result
				+ ((issueDate == null) ? 0 : issueDate.hashCode());
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
		if (!(obj instanceof ArticleView)) {
			return false;
		}
		ArticleView other = (ArticleView) obj;
		if (article == null) {
			if (other.article != null) {
				return false;
			}
		} else if (!article.equals(other.article)) {
			return false;
		}
		if (issueDate == null) {
			if (other.issueDate != null) {
				return false;
			}
		} else if (!issueDate.equals(other.issueDate)) {
			return false;
		}
		return true;
	}
	
	
	
}
