package com.ozguryazilim.tekir.entities;

import com.ozguryazilim.tekir.entites.converters.TagListConverter;
import com.ozguryazilim.telve.entities.EntityBase;
import com.ozguryazilim.telve.entities.FeaturePointer;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author oyas
 */
@Entity
@Table(name = "TFN_FINANCE_TXN")
public class FinanceAccountTxn extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", foreignKey = @ForeignKey(name = "FK_BCACCTXN_ACC"))
    private FinanceAccount account;
    
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID", foreignKey = @ForeignKey(name = "FK_ACCTXN_ACC"))
    private Contact contact;
    
    /**
     * Belgenin Düzenlenme Tarih Saati
     */
    @Column(name = "TXNDATE")
    @Temporal(value = TemporalType.DATE)
    private Date date;
    
    /**
     * Ek kod alanı. Raporlar v.s. için
     */
    @Column(name = "TAGS", length = 2000)
    @Convert(converter = TagListConverter.class)
    private List<String> tags;
    
    
    /**
     * Fiş açıklama alanı
     */
    @Column(name="INFO")
    private String info;
    
    
    /**
     * Resmi/Matbuu belge üzerinde bulunan numara.
     * Belge Numarası
     */
    @Column(name="REFERENCE_NO", length=30)
    @Size(max=30)
    private String referenceNo;
    
    /**
     * Süreç numarası.
     * Belgeler eğer bir süreç içerisinde yer alıyor ise bu numara farklı belgeler içerisinde aynı olacaktır.
     * 
     * Örneğin satış sürecinde Fırsat için alınan numara Teklif, Sipariş, Fatura ve Ödeme belgelerinde de aynı olacaktır.
     * 
     */
    @Column(name="PROCESS_ID")
    private String processId;
    
    /**
     * Bu belge'nin erişim yetkilisi, sorumlusunun kim olduğu
     */
    @Column(name="OWNER")
    private String owner;
    
    /**
     * Bu hareketin kaynağı olan fiş/özellik
     * 
     */
    @Embedded
    private FeaturePointer feature;
 
    /**
     * Fiş durum bilgisi : açık kapalı v.b.
     */
    @Column(name="STATUS")
    private String status = "DRAFT";
    
    /**
     * Özellikle başarısız kapanışlarda başarısızlık nedeni
     */
    @Column(name="STATUS_REASON")
    private String statusReason;
    
    @Column(name="CCY")
    private Currency currency;
    
    @Column(name="AMOUNT")
    private BigDecimal amount = BigDecimal.ZERO;
    
    @Column(name="LOCAL_AMOUNT")
    private BigDecimal localAmount = BigDecimal.ZERO;
    
    @Column(name="DEBIT")
    private Boolean debit = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinanceAccount getAccount() {
        return account;
    }

    public void setAccount(FinanceAccount account) {
        this.account = account;
    }
    
    public Contact getContact() {
        return contact;
    }
    
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public FeaturePointer getFeature() {
        return feature;
    }

    public void setFeature(FeaturePointer feature) {
        this.feature = feature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getDebit() {
        return debit;
    }

    public void setDebit(Boolean debit) {
        this.debit = debit;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }
    
    

}
