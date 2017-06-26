package com.audhut.osgiex.oms;

import com.audhut.osgiex.order.domain.Order;
import com.audhut.osgiex.order.services.OrderService;

/**
 * Created by avdhut on 18/6/17.
 */
/* This bundle demonstrates how a service can be retrieved from the osgi registry
and used. This class is a client of the osgi service orderService
 */
public class OrderMngSystem {

    OrderService ordSvc;

    public OrderMngSystem(OrderService orderSvc) {
        this.ordSvc = orderSvc;
    }

    public OrderMngSystem(){

    }

    public void createOrder(){
        Order ord = new Order();
        ordSvc.createOrder(ord);
        System.out.println("order mngsystem called");

    }
}
