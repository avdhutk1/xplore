package com.audhut.osgiex.order.activator;

import com.audhut.osgiex.order.services.OrderService;
import com.audhut.osgiex.order.services.impl.OrderServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Created by osswrk01 on 2/7/17.
 */
public class OrderServiceActivator implements BundleActivator {

   // Logger logger = LoggerFactory.getLogger(OrderServiceActivator.class);

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("order service is started");
        OrderService ordSvc = new OrderServiceImpl();
        ServiceRegistration<OrderService> svcReg = bundleContext.registerService(OrderService.class, ordSvc, null);
        System.out.println("order service is registered");
      //  logger.debug("order service is registered");

    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("order service is stopped");
      //  logger.debug("order service is stopped");

    }

    public void register() throws Exception {
        System.out.println("order service is registered using blueprint");
    }

    public void unregister() throws Exception {
        System.out.println("order service is un-registered using blueprint");

    }



}
