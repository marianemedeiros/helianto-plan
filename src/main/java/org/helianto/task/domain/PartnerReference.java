/* Copyright 2005 I Serv Consultoria Empresarial Ltda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.helianto.task.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.helianto.partner.domain.Partner;

/**
 * Data partner or process not yet can be associated directly.
 * 
 * @author Mauricio Fernandes de Castro
 */
@Embeddable
public class PartnerReference implements Serializable {

	private static final long serialVersionUID = 1L;
    private Partner partner;
    
    @Column(length=32)
    private String partnerAlias = "";
    
    @Column(length=64)
    private String contactName = "";
    
    @Column(length=20)
    private String contactPhone = "";
    
    /**
	  * Merger.
	  * 
	  * @param command
  	  **/
   		public PartnerReference merge(PartnerReference command) {
   			setPartner(command.getPartner());
   			setPartnerAlias(command.getPartnerAlias());
   			setContactName(command.getContactName());
   			setContactPhone(command.getContactPhone());
   			return this;
   		}

    /**
     * Default constructor.
     */
    public PartnerReference() {
    }
    
    /**
     * Constructor of partner.
     */
    public PartnerReference(Partner partner) {
    	setPartner(partner);
    }
    
    /**
     * <<Transient>> Partner referenced.
     */
//    @Transient
	public Partner getPartner() {
		return partner;
	}
	/**
	 * <<Transient>> True if partner referenced is null.
	 */
	@Transient
	public boolean isPartnerNotNull() {
		return (getPartner()!=null);
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
    /**
     * Name of partner referenced, or included in partner referenced.
     * 
     */
	public String getPartnerAlias() {
    	if (isPartnerNotNull()) {
    		return getPartner().getEntityAlias();
    	}
		return partnerAlias;
	}
	/**
	 * <<Transient>> True if partner referenced name exists. 
	 */
	@Transient
	public boolean isPartnerAliasEmpty() {
		return (getPartnerAlias().length()==0);
	}
	public void setPartnerAlias(String partnerAlias) {
		this.partnerAlias = partnerAlias;
	}

    /**
	 * Contact Name.
	 */
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Contact Phone.
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}
