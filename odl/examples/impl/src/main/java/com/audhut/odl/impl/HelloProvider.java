/*
 * Copyright © 2017 Copyright (c) 2015 Yoyodyne, Inc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.audhut.odl.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.rev150105.HelloService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.rev150105.HelloWorldInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.rev150105.HelloWorldInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.rev150105.HelloWorldOutput;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HelloProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ExamplesProvider.class);

    private final DataBroker dataBroker;
    private final RpcProviderRegistry rpcreg;
    private RpcRegistration<HelloService> helsvc;

    public HelloProvider(final DataBroker dataBroker, final RpcProviderRegistry helsvc)
    {
        this.dataBroker = dataBroker;
        this.rpcreg = helsvc;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {

        LOG.info("HelloProvider Session Initiated");
        helsvc = rpcreg.addRpcImplementation(HelloService.class, new HelloWorldImpl());
        /*HelloService hs = helsvc.getRpcService(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.rev150105.HelloService.class);
        HelloWorldInput hin = new HelloWorldInputBuilder().setName("avdhut").build();
        Future<RpcResult<HelloWorldOutput>> ft = hs.helloWorld(hin);
        try {
            RpcResult<HelloWorldOutput> rpcres = ft.get();
            String gret = rpcres.getResult().getGreeting();
            LOG.info("greeting received is " + gret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("HelloProvider Closed");
        if (helsvc != null){
            helsvc.close();
        }
    }
}
