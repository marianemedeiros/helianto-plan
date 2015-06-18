package org.helianto.task.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.helianto.core.def.HumanReadable;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.core.number.Sequenceable;
import org.helianto.user.domain.User;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * Comentário para um artigo.
 * 
 * @author mauriciofernandesdecastro
 */
@javax.persistence.Entity
@javax.persistence.Table(name="doc_comment", 
	uniqueConstraints = {@UniqueConstraint(columnNames={"articleId", "commentPath"})}
)
public class ArticleComment 
	implements Serializable
	, HumanReadable
	, Sequenceable {
	
    private static final long serialVersionUID = 1L;
  
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Version
    private Integer version;
    
    @ManyToOne
    @JoinColumn(name="ownerId", nullable=true)
    private Identity owner;
    
    @DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
    
    @JsonBackReference 
    @ManyToOne
    @JoinColumn(name="articleId", nullable=true)
	private Article article;
    
	private long internalNumber;
	
	@Column(length=64)
	private String parentPath = "";
	
	@Column(length=16)
	private String commentPath = "";
	
	@Lob
	private byte[] content = "".getBytes();
	
	@Column(length=24)
	private String encoding = "iso8859_1";
	
	/**
	 * Constructor.
	 */
	public ArticleComment() {
		super();
		setIssueDate(new Date());
	}
	
	/**
	 * Key constructor.
	 * 
	 * @param article
	 * @param commentPath
	 */
	public ArticleComment(Article article, String commentPath) {
		this();
		setArticle(article);
		setCommentPath(commentPath);
	}
	
	/**
	 * Form constructor.
	 * 
	 * @param article
	 * @param user
	 */
	public ArticleComment(Article article, User user) {
		this(article, "");
		setOwner(user.getIdentity());
	}
	
	/**
	 * Paath constructor.
	 * 
	 * @param article
	 * @param user
	 * @param commentPath
	 */
	public ArticleComment(Article article, User user, String commentPath) {
		this(article, commentPath);
		setOwner(user.getIdentity());
	}
	
//	@Transient
	public String getInternalNumberKey() {
		return "ARTICLE";
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
     * <<Transient>> Conveniente para apresentar o apelido.
     * @deprecated
     */
//    @Transient
	public String getOwnerOptionalAlias() {
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
     * Issue date.
     */
    public Date getIssueDate() {
        return this.issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Artigo a que pertence o comentário.
     */
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public long getInternalNumber() {
		return internalNumber;
	}
	public void setInternalNumber(long internalNumber) {
		this.internalNumber = internalNumber;
	}
	
	/**
	 * Caminho do comentário superior.
	 */
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	
	/**
	 * Caminho para o comentário.
	 */
	public String getCommentPath() {
		return commentPath;
	}
	public void setCommentPath(String commentPath) {
		this.commentPath = commentPath;
	}
	
	/**
	 * Gera sgmentos com a capacidade de 40 bits no formato hexadecimal.
	 */
//	@Transient
	public String generatePathSegment() {
//		long base = 1325383200000l; // 2012/01/01
//		long timePart = ((((getIssueDate().getTime()-base)/1000) << 12) & 0xfffffff000l); 
//		int ownerPart = (int) (getOwner().getId() & 0xfff); 
//		return String.format("%10s", Long.toHexString(timePart + ownerPart)).replace(' ', '0');
		return String.format("%4s", Long.toHexString(getInternalNumber() & 0xffffl)).replace(' ', '0');
	}
	
	/**
	 * Conteúdo do comentário.
	 */
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	/**
	 * Conteúdo na forma de string.
	 */
//	@Transient
	public String getContentAsString() {
		if (getContent()!=null) {
			return new String(getContent());
		}
		return "";
	}
	public void setContentAsString(String contentAsString) {
		setContent(contentAsString.getBytes());
	}
	
	/**
	 * Codificação.
	 */

	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * Tipo 'text/html' por definição.
	 */
//	@Transient
	public String getMultipartFileContentType() {
		return "text/html";
	}

//	@Transient
	public boolean isText() {
		return true;
	}

//	@Transient
	public boolean isHtml() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result
				+ ((commentPath == null) ? 0 : commentPath.hashCode());
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
		if (!(obj instanceof ArticleComment)) {
			return false;
		}
		ArticleComment other = (ArticleComment) obj;
		if (article == null) {
			if (other.article != null) {
				return false;
			}
		} else if (!article.equals(other.article)) {
			return false;
		}
		if (commentPath == null) {
			if (other.commentPath != null) {
				return false;
			}
		} else if (!commentPath.equals(other.commentPath)) {
			return false;
		}
		return true;
	}

}
