package com.audhut.osgiex.order.domain;

/**
 * Created by osswrk01 on 2/7/17.
 */
public class OrderItem {

    private String orderItemId;
    private Product product;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
