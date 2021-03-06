package com.ozguryazilim.tekir.entities;

import com.ozguryazilim.telve.annotations.BizKey;
import com.ozguryazilim.telve.entities.AuditBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Farklı fiş türlerini bir araya gruplamak için bir model.
 * 
 * Alış ve Satış işleri, farklı fiş türleri bazen bir araya gruplanmak istenebiliyor.
 * Bu model bunu sağlayacak. Fişler üzerinde seçimlik bir alan olarak bulunacak.
 * 
 * @author Hakan Uygun
 */
@Entity
@Table(name = "TVO_GROUP")
public class VoucherGroup extends AuditBase{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;
    
    /**
     * Uygulama tarafında takip için kullanılan temel numara
     * Fiş Numarası
     */
    @Column(name="GROUP_NO", length=30, nullable=false, unique=true)
    @NotNull @Size(max = 30)
    @BizKey
    private String groupNo;
    
    /**
     * Grup başlığı
     */
    
    @Column(name = "TOPIC")
    private String topic;
    
    /**
     * Ek kod alanı. Raporlar v.s. için
     */
    @Column(name="CODE", length=30)
    @Size(max=30)
    private String code;
    
    /**
     * Grubun durumu(aktif-pasif)
     */
    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private VoucherGroupStatus status;
    
    /**
     * Fiş açıklama alanı
     */
    @Column(name="INFO")
    private String info;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

	public VoucherGroupStatus getStatus() {
		return status;
	}

	public void setStatus(VoucherGroupStatus status) {
		this.status = status;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

    
    
}
