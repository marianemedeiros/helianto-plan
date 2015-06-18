package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;
import org.helianto.core.domain.Identity;
import org.springframework.data.jpa.repository.Query;

import org.helianto.task.domain.Article;
import org.helianto.task.domain.ArticleView;

/**
 * Article comment repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ArticleViewRepository 
	extends FilterRepository<ArticleView, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param owner
	 * @param article
	 */
	ArticleView findByOwnerAndArticle(Identity owner, Article article);
	
	/**
	 * Count by article.
	 * 
	 * @param articleId
	 */
	@Query("select count(a) from ArticleView a where a.article.id = ?1")
	long countByArticleId(int articleId);
	
	/**
	 * Count by article and owner.
	 * 
	 * @param ownerId
	 */
	@Query("select count(a) from ArticleView a where a.owner.id = ?2")
	long countByOwnerId(long ownerId);
	
}
