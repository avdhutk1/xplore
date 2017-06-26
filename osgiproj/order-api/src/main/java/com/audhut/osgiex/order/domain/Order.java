package com.audhut.osgiex.order.domain;

import java.util.List;

/**
 * Created by osswrk01 on 1/31/17.
 */
public class Order {

    private String orderId;

    private List<OrderItem> orderItems;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }



}
