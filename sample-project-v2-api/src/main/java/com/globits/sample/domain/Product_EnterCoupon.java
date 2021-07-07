package com.globits.sample.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Table(name = "tbl_product_entercoupon")
@Entity
public class Product_EnterCoupon extends BaseObject {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "entercoupon_id")
    private EnterCoupon enterCoupon;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public EnterCoupon getEnterCoupon() {
        return enterCoupon;
    }

    public void setEnterCoupon(EnterCoupon enterCoupon) {
        this.enterCoupon = enterCoupon;
    }
}
