package org.helianto.task.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.helianto.core.def.Resolution;
import org.helianto.core.domain.Entity;
import org.helianto.core.domain.PublicEntity;
import org.helianto.document.domain.Document;
import org.helianto.task.def.ArticleType;
import org.helianto.task.def.ForwardOptions;
import org.helianto.user.domain.User;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Artigo, também chamado de mensagem.
 * 
 * <p>
 * Uma sublcasse de documentos não sujeita a versionamento e que pode reporesentar 
 * desde uma simples mensagem até um blog.
 * </p>
 * <p>
 * Pertencendo à hierarquia de "AbstractCustomizableContent", um artigo recebe nomes de acordo com
 * padrões estabelecidos pela pasta (numberPattern) ou através de prefixo personalizado (customPrefix).
 * </p>
 * <p>
 * Pertencendo à hierarquia de "AbstractInterpretableContent", um artigo pode aceitar personalização
 * através de categorias.
 * </p>
 * 
 * <p>
 * Artigos podem ser encaminhados para associarem-se a relatórios.
 * </p>
 * 
 * @author mauriciofernandesdecastro
 */
@javax.persistence.Entity
@javax.persistence.Table(name="doc_article")
public class Article 
	extends AbstractCustomizableContent 
{
	
	private static final long serialVersionUID = 1L;
	
	private char articleType = 'M';
	
	@Column(length=20)
	private String optionalEntityAlias = "";
	
	@ManyToOne
	@JoinColumn(name="publicEntityId", nullable=true)
	private PublicEntity publicEntity;
	
	@DateTimeFormat(style="SS")
    @Temporal(TemporalType.TIMESTAMP)
	private Date nextCheckDate;
	
	private char forwardOptions = ForwardOptions.NOT_REQUIRED.getValue();
	
	@ManyToOne
	@JoinColumn(name="documentRequirementId")
	private DocumentRequirement documentRequirement;
	
	@ManyToOne
	@JoinColumn(name="documentId")
	private Document document;
	
	@Lob
	private byte[] optionalContent;
	
	@ManyToOne
	@JoinColumn(name="lastCommentId", nullable=true)
	private ArticleComment lastComment;
	
	@JsonManagedReference
	@OneToMany(mappedBy="article")
	private Set<ArticleComment> articleComments = new HashSet<>();
	
	/**
	 * Constructor.
	 */
	public Article() {
		super();
		setNextCheckDate(getIssueDate());
		setResolution(Resolution.DONE.getValue());
	}
	
	/**
	 * Key constructor.
	 * 
	 * @param entity
	 * @param docCode
	 */
	public Article(Entity entity, String docCode) {
		this();
		setEntity(entity);
		setDocCode(docCode);
	}
	
	/**
	 * Convenience user setter.
	 * 
	 * @param user
	 */
//	@Transient
	public void setUser(User user) {
		setEntity(user.getEntity());
		setOwner(user.getIdentity());
	}
	
	/**
	 * Tipo de artigo.
	 */
	public char getArticleType() {
		return articleType;
	}
	public void setArticleType(char articleType) {
		this.articleType = articleType;
	}
	public void setArticleTypeAsEnum(ArticleType articleType) {
		this.articleType = articleType.getValue();
	}
	
	/**
	 * Prefixo para numeração automática. 
	 * 
	 * <p>
	 * Caso não haja prefixo personalizado, utiliza ${articleType}${issueDate?string('yy')}, 
	 * ou seja, composto pelo tipo e ano da emissão. Exemplo: M13, R13, etc.
	 * </p>
	 */
//	@Transient
	public StringBuilder getPrefix() {
		if (isCustomPrefixValid()) {
			return new StringBuilder(getCustomPrefix());
		}
		return new StringBuilder()
			.append(getArticleType())
			.append(new SimpleDateFormat("yy").format(getIssueDate()));
	}
	
	@Override
//	@Transient
	public String getNumberPattern() {
		return new StringBuilder("'#").append(getPrefix()).append("-'00000").toString();
	}
	
	/**
	 * Apelido dado a uma entidade parceira.
	 */
	public String getOptionalEntityAlias() {
		return internalOptionalEntityAlias();
	}
	public void setOptionalEntityAlias(String optionalEntityAlias) {
		this.optionalEntityAlias = optionalEntityAlias;
	}
	
    /**
     * <<Transient>> Permite que as subclasses substituam o apelido opcional 
     * de acordo com as condições deste artigo.
     * 
     * <p>
     * Implementação padrão usa o código da entidade pública, quando ela existir.
     * </p>
     */
//    @Transient
    protected String internalOptionalEntityAlias() {
    	if (isPublicEntityEnabled()) {
    		return getPublicEntity().getEntityAlias();
    	}
		return optionalEntityAlias;
	}
    
    /**
     * <<Transient>> onveniente para determinar se há uma entidade pública associada.
     */
//    @Transient
    public boolean isPublicEntityEnabled() {
		if (getPublicEntity()!=null) {
			return true;
		}
    	return false;
	}
    
    /**
     * Vinculação (opcional) a uma entidade pública.
     */
	public PublicEntity getPublicEntity() {
		return publicEntity;
	}
	public void setPublicEntity(PublicEntity publicEntity) {
		this.publicEntity = publicEntity;
	}

	/**
	 * Data de verificação.
	 */
	public Date getNextCheckDate() {
		return nextCheckDate;
	}
	public void setNextCheckDate(Date nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}
	
	/**
	 * Opções de encaminhamento.
	 */
	public char getForwardOptions() {
		return forwardOptions;
	}
	public void setForwardOptions(char forwardOptions) {
		this.forwardOptions = forwardOptions;
	}
	
	/**
	 * Verdadeiro se requer encaminhamento.
	 */
//	@Transient
	public boolean isForwardEnabled() {
		if (getForwardOptions()!=ForwardOptions.NOT_REQUIRED.getValue()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Referência cruzada para associação entre documentos e relatórios.
	 */
	public DocumentRequirement getDocumentRequirement() {
		return documentRequirement;
	}
	public void setDocumentRequirement(DocumentRequirement documentRequirement) {
		this.documentRequirement = documentRequirement;
	}
	
	/**
	 * Conveniente para fornecer um relatório.
	 */
//	@Transient TODO
//	public Report getReport() {
//		if (isForwardEnabled()) {
//			if (getDocumentRequirement()!=null && getDocumentRequirement().getReport()!=null) {
//				return getDocumentRequirement().getReport();
//			}
//		}
//		return null;
//	}
//	
	/**
	 * Referência cruzada para documentos.
	 */
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	
	/**
	 * Conteúdo opcional, normalmente tratado por scripts.
	 */
	public byte[] getOptionalContent() {
		return optionalContent;
	}
	public void setOptionalContent(byte[] optionalContent) {
		this.optionalContent = optionalContent;
	}
	
	/**
	 * Transfere a verificação da resolução para o relatório, quando houver, ou de 
	 * acordo com a data de próxima verificação.
	 */
//	@Transient TODO
//	protected char validateResolution(char resolution) {
//		if (getReport()!=null) {
//			return getReport().getResolution();
//		}
//		if (resolution==Resolution.TODO.getValue()) {
//			if (getNextCheckDate().after(new Date())) {
//				// Fecha automaticamente mensagens com resolução T após expirar a data de verificação
//				return Resolution.DONE.getValue();
//			}
//		}
//		return super.validateResolution(resolution);
//	}
	
    /**
     * Último comentário.
     */
    public ArticleComment getLastComment() {
		return lastComment;
	}
    public void setLastComment(ArticleComment lastComment) {
		this.lastComment = lastComment;
	}
	
	/**
	 * Comentários.
	 */
	public Set<ArticleComment> getArticleComments() {
		return articleComments;
	}
	public void setArticleComments(Set<ArticleComment> articleComments) {
		this.articleComments = articleComments;
	}

}
