package org.helianto.task.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * Classe base para conteúdo que pode ter o código personalizado.
 * 
 * @author mauriciofernandesdecastro
 */
@MappedSuperclass
public class AbstractCustomizableContent 
	extends AbstractInterpretableContent {

	private static final long serialVersionUID = 1L;
	
    @Column(length=20)
	private String customPrefix = "";
	
	@Column(length=12)
    private String suffix = "";

	
	/**
	  * Merger.
	  * 
	  * @param command
  	  **/
		public AbstractCustomizableContent merge(AbstractCustomizableContent command) {
			super.merge(command);
			setCustomPrefix(command.getCustomPrefix());
			setSuffix(command.getSuffix());
			return this;
		}
	
    /**
     * Permite o controle de chave secundária com um prefixo diferente do
     * usado como nome da pasta.
     */
    public String getCustomPrefix() {
		return customPrefix;
	}
    public void setCustomPrefix(String customPrefix) {
		this.customPrefix = customPrefix;
	}
    
    /** 
     * Verdadeiro se a chave deve ser gerada com um prefixo personalizado.
     */
    public boolean isCustomPrefixValid() {
		if (getCustomPrefix()!=null && getCustomPrefix().length()>0) {
			return true;
		}
		return false;
	}
    
	/**
	 * Sufixo (opcional) usado para cria docCode.
	 */
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

    /** 
     * Verdadeiro se a chave deve ser gerada com um sufixo personalizado.
     */
    public boolean isSuffixValid() {
		if (getSuffix()!=null && getSuffix().length()>0) {
			return true;
		}
		return false;
	}
    
	/**
	 * Usa o prefixo personalizado, caso haja, para construir docCode.
	 */
	public String getNumberPattern() {
		if (isCustomPrefixValid()) {
			StringBuilder customPattern = new StringBuilder("'").append(getCustomPrefix().replace("'", "")).append("'");
			if (super.getNumberPattern()!=null) {
				customPattern.append(super.getNumberPattern());
			}
			if (isSuffixValid()) {
				customPattern.append("'").append(getSuffix().replace("'", "")).append("'");
			}
			return customPattern.toString();
		}
		return super.getNumberPattern();
	}
	
}
