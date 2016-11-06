/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.tekir.account.credit;

import com.ozguryazilim.tekir.account.config.AccountNotePages;
import com.ozguryazilim.tekir.entities.AccountCreditNote;
import com.ozguryazilim.tekir.entities.VoucherState;
import com.ozguryazilim.tekir.voucher.VoucherFormBase;
import com.ozguryazilim.tekir.voucher.VoucherStateAction;
import com.ozguryazilim.tekir.voucher.VoucherStateConfig;
import com.ozguryazilim.telve.data.RepositoryBase;
import com.ozguryazilim.telve.feature.FeatureHandler;
import com.ozguryazilim.telve.forms.FormEdit;
import javax.inject.Inject;

/**
 *
 * @author oyas
 */
@FormEdit(browsePage = AccountNotePages.AccountCreditNoteBrowse.class, editPage = AccountNotePages.AccountCreditNote.class, viewContainerPage = AccountNotePages.AccountCreditNoteView.class, masterViewPage = AccountNotePages.AccountCreditNoteMasterView.class)
public class AccountCreditNoteHome extends VoucherFormBase<AccountCreditNote>{

    @Inject
    private AccountCreditNoteRepository repository;
    
    @Override
    protected VoucherStateConfig buildStateConfig() {
        VoucherStateConfig config = new VoucherStateConfig();
        config.addTranstion(VoucherState.DRAFT, new VoucherStateAction("Publish", "fa fa-check", false, ""), VoucherState.OPEN);
        config.addTranstion(VoucherState.OPEN, new VoucherStateAction("Won", "fa fa-check", false, ""), VoucherState.CLOSE);
        config.addTranstion(VoucherState.OPEN, new VoucherStateAction("Loss", "fa fa-close", true, ""), VoucherState.CLOSE);
        config.addTranstion(VoucherState.OPEN, new VoucherStateAction("Cancel", "fa fa-ban", true, ""), VoucherState.CLOSE);
        return config;
    }

    @Override
    public Class<? extends FeatureHandler> getFeatureClass() {
        return AccountCreditNoteFeature.class;
    }

    @Override
    protected RepositoryBase<AccountCreditNote, ?> getRepository() {
        return repository;
    }
    
}
