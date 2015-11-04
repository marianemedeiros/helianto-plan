package org.helianto.task.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.helianto.core.domain.Category;
import org.helianto.core.internal.InterpretableCategory;
import org.helianto.document.internal.AbstractContent;

/**
 * Context base class that can be interpreted.
 * 
 * @author mauriciofernandesdecastro
 */
@MappedSuperclass
public abstract class AbstractInterpretableContent 
	extends AbstractContent 
	implements InterpretableCategory {

	private static final long serialVersionUID = 1L;
	
    @Column(length=2048)
    private String parsedContent;
    
    @Column(length=2048)
    private String securedContent;

	/**
	 * <<Transient>> True if have a folder with category associate with it. 
	 */
	public boolean isFolderCategoryEnabled() {
		return getFolder()!=null && getFolder().isCategoryEnabled();
	}
	
	/**
	 * <<Transient>> True if can substitute a folder category by a category directly in context.
	 */
	public boolean isCategoryOverrideAllowed() {
		return false;
	}
	
	/**
	 * <<Transient>> Allow that subclass update category in function of report state.
	 * 
	 * <p>
	 * Default implementation verify if it's possible substitute a category, if true, inherits
	 * folder category.
	 * </p>
	 */
	protected Category getInternalCategory(Category category) {
		if (isCategoryOverrideAllowed() && category!=null) {
			return category;
		}
		if (isFolderCategoryEnabled()) {
			return getFolder().getCategory();
		}
		return category;
	}
	
	/**
	  * Merger.
	  * 
	  * @param command
  	  **/
			public AbstractInterpretableContent merge(AbstractInterpretableContent command) {
				super.merge(command);
				setParsedContent(command.getParsedContent());
				setSecuredContent(command.getSecuredContent());
				return this;
			}
	
    /**
     * Transformed context.
     */
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
    /**
     * Security context.
     */
    public String getSecuredContent() {
		return securedContent;
	}
    public void setSecuredContent(String securedContent) {
		this.securedContent = securedContent;
	}
    
	/**
	 * <<Transient>> True when have available category.
	 */
	protected boolean isCategoryEnabled() {
		return getCategory()!=null && getCategory().getScriptItemsAsArray().length>0;
	}
	
    /**
     * List of scripts.
     * 
     * <p>
     * Scripts are extracted in most cases from category. Just when haven't result the folder
     * is used to extracted scripts.
     * 
     * </p>
     */
    public String getScriptItems() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItems();
		}
		return "";
    }
    
    public List<String> getScriptList() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptList();
		}
		return null;
    }
    
    public String[] getScriptItemsAsArray() {
		if (isCategoryEnabled()) {
			return getCategory().getScriptItemsAsArray();
		}
		return null;
    }
    
}
