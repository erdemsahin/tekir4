/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.tekir.contact.information;

import com.ozguryazilim.tekir.entities.Contact;
import com.ozguryazilim.tekir.entities.ContactInformation;
import com.ozguryazilim.telve.data.RepositoryBase;
import java.util.List;
import javax.enterprise.context.Dependent;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

/**
 * ContactInformation sınıfları için Repository
 * 
 * @author Hakan Uygun
 */
@Dependent
@Repository
public abstract class ContactInformationRepository extends
            RepositoryBase<ContactInformation, ContactInformation>
                implements
                    CriteriaSupport<ContactInformation> {
    
    
    /**
     * Verilen contact'a ait tüm iletişim bilgilerini döndürür.
     * 
     * @param contact
     * @return 
     */
    public abstract List<ContactInformation> findByContact( Contact contact );
    
    
}