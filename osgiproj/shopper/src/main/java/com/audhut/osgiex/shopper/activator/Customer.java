package com.audhut.osgiex.shopper.activator;

import com.audhut.osgiex.oms.OrderMngSystem;
import com.audhut.osgiex.order.services.OrderService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Created by avdhut on 18/6/17.
 */
/* This class is just a test class to demonstrate how one bundle can talk to
another which has a reference to a service
 */
public class Customer implements BundleActivator{

    public void start(BundleContext bundleContext) throws Exception {
//        OrderMngSystem oms = new OrderMngSystem();
//        oms.createOrder();

        /*ServiceReference<OrderService> svcRef = bundleContext.getServiceReference(OrderService.class);
        OrderService ordSvc = bundleContext.getService(svcRef);
        OrderMngSystem oms = new OrderMngSystem(ordSvc);
        if(null!=ordSvc){
            System.out.println("Got the registered service from blueprint");
        }*/
    }

    public void stop(BundleContext bundleContext) throws Exception {

    }
}
