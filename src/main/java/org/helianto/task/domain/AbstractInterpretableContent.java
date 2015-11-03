package org.helianto.task.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.helianto.core.domain.Category;
import org.helianto.core.internal.InterpretableCategory;
import org.helianto.document.internal.AbstractContent;

/**
 * Classe base para conteúdo que pode ser interpretado.
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
	 * <<Transient>> Verdadeiro se há uma pasta com uma categoria associada a ela.
	 */
	public boolean isFolderCategoryEnabled() {
		return getFolder()!=null && getFolder().isCategoryEnabled();
	}
	
	/**
	 * <<Transient>> Verdadeiro quando é permitido substituir a categoria da pasta
	 * por uma categoria diretamente no conteúdo.
	 */
	public boolean isCategoryOverrideAllowed() {
		return false;
	}
	
	/**
	 * <<Transient>> Permite que as subclasses atualizem a categoria em função do estado do relatório.
	 * 
	 * <p>
	 * Implementação padréo verifica se é possível substituir a categoria, caso não seja, herda a 
	 * categoria da pasta, quando houver.
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
     * Conteúdo transformado.
     */
    public String getParsedContent() {
		return parsedContent;
	}
    public void setParsedContent(String parsedContent) {
		this.parsedContent = parsedContent;
	}
    
    /**
     * Conteúdo seguro.
     */
    public String getSecuredContent() {
		return securedContent;
	}
    public void setSecuredContent(String securedContent) {
		this.securedContent = securedContent;
	}
    
	/**
	 * <<Transient>> Verdadeiro quando há uma categoria disponível.
	 */
	protected boolean isCategoryEnabled() {
		return getCategory()!=null && getCategory().getScriptItemsAsArray().length>0;
	}
	
    /**
     * Lista de scripts, como lista CSV dos códigos dos scripts.
     * 
     * <p>
     * Os scripts são extraídos preferencialmente da categoria. Somente se não houver resultado
     * a pasta é então usada para extrair scripts.
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
