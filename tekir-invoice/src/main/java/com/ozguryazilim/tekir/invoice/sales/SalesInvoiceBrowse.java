package com.ozguryazilim.tekir.invoice.sales;

import com.ozguryazilim.tekir.entities.SalesInvoice;
import com.ozguryazilim.tekir.invoice.InvoiceBrowse;
import com.ozguryazilim.tekir.voucher.VoucherFormBase;
import com.ozguryazilim.tekir.voucher.VoucherRepositoryBase;
import com.ozguryazilim.telve.forms.Browse;
import javax.inject.Inject;

/**
 *
 * @author oyas
 */
@Browse( feature=SalesInvoiceFeature.class )
public class SalesInvoiceBrowse extends InvoiceBrowse<SalesInvoice, SalesInvoiceViewModel> {

    @Inject
    private SalesInvoiceRepository repository;
    
    @Inject
    private SalesInvoiceHome home;
    
    @Override
    public VoucherRepositoryBase<SalesInvoice, SalesInvoiceViewModel> getVoucherRepository() {
        return repository;
    }

	@Override
	public VoucherFormBase<SalesInvoice> getHome() {
		// TODO Auto-generated method stub
		return home;
	}

    @Override
    public String getTagKey() {
        return home.getTagKey();
    }
    
}
