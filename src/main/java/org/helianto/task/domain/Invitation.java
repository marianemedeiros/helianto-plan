package org.helianto.task.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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

import org.helianto.user.domain.User;
import org.joda.time.DateMidnight;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Convite.
 * 
 * @author mauriciofernandesdecastro
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "doc_invitation"
, uniqueConstraints = { @UniqueConstraint(columnNames = {"articleId", "userId" }) }
)
public class Invitation 
	implements Serializable, Comparable<Invitation> 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Version
	private Integer version;
	
	@ManyToOne
	@JoinColumn(name = "articleId", nullable = true)
	private Article article;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = true)
	private User user;
	
	@DateTimeFormat(style = "SS")
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueDate = new Date();
	
	@DateTimeFormat(style = "SS")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	@Column(length = 60)
	private String accessToken = "";
	
	private int quantityOfInvitees = 1;
	
	@ManyToOne
	@JoinColumn(name = "computedConfirmationId", nullable = true)
	private Confirmation computedConfirmation;

	/**
	 * Constructor.
	 */
	public Invitation() {
		super();
	}

	/**
	 * Key constructor.
	 * 
	 * @param article
	 * @param user
	 */
	public Invitation(Article article, User user) {
		this();
		setArticle(article);
		setUser(user);
	}
	
	/**
	 * 
	 * @author Eldevan Nery Junior  
	 * 
	 *	Enum para criar métodos para Ordenar o convite
	 * Falta implementar outros métodos de sort
	 */
	public static enum Sort implements Comparator<Invitation> {
		ByIssueDate() {

			@Override
			public int compare(Invitation o1, Invitation o2) {
				return o1.getIssueDate().compareTo(o2.getIssueDate());
			}

		},
		ByExpirationDate() {

			@Override
			public int compare(Invitation o1, Invitation o2) {
				return o1.getExpirationDate().compareTo(o2.getExpirationDate());
			}

		},
		ByVersion() {

			@Override
			public int compare(Invitation o1, Invitation o2) {
				return o1.getVersion().compareTo(o2.getVersion());
			}

		},
		ById(){

			@Override
			public int compare(Invitation o1, Invitation o2) {
				if (o1.getId() < o2.getId()) {
					return -1;
				}
				if (o1.getId() > o2.getId()) {
					return 1;
				}
				return 0;
			}
			
		}
		;
		public Comparator<Invitation> asc() {
			return this;
		}

		public Comparator<Invitation> desc() {
			return Collections.reverseOrder(this);
		}
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
	 * 
	 * @see {@link Article}
	 */
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * <<NaturalKey>> User.
	 * 
	 * @see {@link User}
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	 * Expiration date.
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Verdadeiro se o convite ainda não expirou.
	 */
//	@Transient
	public boolean isNotExpired() {
		Date date = new DateMidnight().plusDays(1).toDate();
		return getExpirationDate() != null && getExpirationDate().before(date);
	}

	/**
	 * Código de acesso.
	 */
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Quantidade de convidados.
	 */
	public int getQuantityOfInvitees() {
		return quantityOfInvitees;
	}

	public void setQuantityOfInvitees(int quantityOfInvitees) {
		this.quantityOfInvitees = quantityOfInvitees;
	}

	/**
	 * Resultado das confirmações.
	 */
	public Confirmation getComputedConfirmation() {
		return computedConfirmation;
	}

	public void setComputedConfirmation(Confirmation computedConfirmation) {
		this.computedConfirmation = computedConfirmation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (!(obj instanceof Invitation)) {
			return false;
		}
		Invitation other = (Invitation) obj;
		if (article == null) {
			if (other.article != null) {
				return false;
			}
		} else if (!article.equals(other.article)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Invitation other) {
		if (other!=null) {
			return user.compareTo(other.getUser());
		}
		return 1;
	}

}
