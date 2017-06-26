package com.audhut.osgiex.order.services;

import com.audhut.osgiex.order.domain.Order;

/**
 * Created by osswrk01 on 1/20/17.
 */
/* This bundle/package only contains the interface of the service*/
public interface OrderService {

    public String createOrder(Order order);

}
