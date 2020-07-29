package com.avdhut.boot.service;

import com.avdhut.boot.domain.ProductOrder;

public interface OrderService {

    public int createOrder(ProductOrder productOrder) throws Exception;
    public ProductOrder getOrder(int id, String orderStatus) throws Exception;

}
