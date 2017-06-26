package com.audhut.osgiex.oms.activator;

import com.audhut.osgiex.oms.OrderMngSystem;
import com.audhut.osgiex.order.services.OrderService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Created by avdhut on 18/6/17.
 */
public class OrderMngActivator implements BundleActivator{

    public void start(BundleContext bundleContext) throws Exception {

        /*ServiceReference<OrderService> svcRef = bundleContext.getServiceReference(OrderService.class);
        OrderService ordSvc = bundleContext.getService(svcRef);
        OrderMngSystem oms = new OrderMngSystem(ordSvc);
        if(null!=ordSvc){
            System.out.println("Got the registered service");
        }*/
        System.out.println("order mng activator called");
    }

    public void stop(BundleContext bundleContext) throws Exception {

    }

    public void bind(final OrderService ordSvc) throws Exception {
            System.out.println("service ref bound via blueprint");
    }

    public void unbind(final OrderService ordSvc) throws Exception {
        System.out.println("service ref unbound via blueprint");

    }
}
