package org.helianto.task.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * Context base class that can have custom code.
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
     * Allow secondary key control with a different prefix of the used as folder name.
     * 
     */
    public String getCustomPrefix() {
		return customPrefix;
	}
    public void setCustomPrefix(String customPrefix) {
		this.customPrefix = customPrefix;
	}
    
    /** 
     * True if key can be generated with a personalized prefix.     
     */
    public boolean isCustomPrefixValid() {
		if (getCustomPrefix()!=null && getCustomPrefix().length()>0) {
			return true;
		}
		return false;
	}
    
	/**
	 * Suffix (optional) used to create docCode.
	 * 
	 */
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

    /**
     * True if key can be generated with a personalized prefix. 
     * 
     */
    public boolean isSuffixValid() {
		if (getSuffix()!=null && getSuffix().length()>0) {
			return true;
		}
		return false;
	}
    
	/**
	 * Use personalized prefix, if have to build docCode.
	 * 
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
