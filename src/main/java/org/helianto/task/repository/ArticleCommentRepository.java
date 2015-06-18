package org.helianto.task.repository;

import java.io.Serializable;

import org.helianto.core.data.FilterRepository;

import org.helianto.task.domain.Article;
import org.helianto.task.domain.ArticleComment;

/**
 * Article comment repository.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ArticleCommentRepository 
	extends FilterRepository<ArticleComment, Serializable> {
	
	/**
	 * Find by natural key.
	 * 
	 * @param article
	 * @param commentPath
	 */
	ArticleComment findByArticleAndCommentPath(Article article, String commentPath);
	
}
