package org.helianto.task.domain;

import java.io.Serializable;

import org.helianto.core.domain.Identity;

/**
 * Classe auxiliar para contar visualizações.
 * 
 * @author mauriciofernandesdecastro
 */
public class ArticleViewCount 
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private Article article;
	
    private Identity owner;
    
    private long articleViewCount;
    
    /**
     * Construtor base.
     * 
     * @param article
     * @param owner
     */
    public ArticleViewCount(Article article, Identity owner) {
		super();
		setArticle(article);
		setOwner(owner);
	}
    
    /**
     * Construtor completo.
     * 
     * @param article
     * @param owner
     * @param articleViewCount
     */
    public ArticleViewCount(Article article, Identity owner, long articleViewCount) {
		this(article, owner);
		setArticleViewCount(articleViewCount);
	}
    
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public Identity getOwner() {
		return owner;
	}
	public void setOwner(Identity owner) {
		this.owner = owner;
	}
	
	public long getArticleViewCount() {
		return articleViewCount;
	}
	public void setArticleViewCount(long articleViewCount) {
		this.articleViewCount = articleViewCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result
				+ (int) (articleViewCount ^ (articleViewCount >>> 32));
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		if (!(obj instanceof ArticleViewCount)) {
			return false;
		}
		ArticleViewCount other = (ArticleViewCount) obj;
		if (article == null) {
			if (other.article != null) {
				return false;
			}
		} else if (!article.equals(other.article)) {
			return false;
		}
		if (articleViewCount != other.articleViewCount) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		return true;
	}

	
}
