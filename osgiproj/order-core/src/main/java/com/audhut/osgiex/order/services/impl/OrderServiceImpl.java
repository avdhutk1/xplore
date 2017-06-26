package com.audhut.osgiex.order.services.impl;

import com.audhut.osgiex.order.domain.Order;
import com.audhut.osgiex.order.services.OrderService;

/**
 * Created by osswrk01 on 1/31/17.
 */

/* This bundle/package only contains the implementation of the service
 * This class is the implementation of the osgi service
 * The interface/api, OrderService, and its implementation,OrderServiceImpl are in different bundles
 *  */
public class OrderServiceImpl implements OrderService {
    public String createOrder(Order order) {
        System.out.println("order created");
        return "123";
    }
}
