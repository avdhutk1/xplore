package com.avdhut.boot.service;

import com.avdhut.boot.domain.ProductOrder;
import com.avdhut.boot.domain.ProductOrderItem;
import com.avdhut.boot.exception.InvalidProductNameException;
import com.avdhut.boot.exception.InvalidProductStatusException;
import com.avdhut.boot.exception.InvalidValueException;
import com.avdhut.boot.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Override
    public int createOrder(ProductOrder productOrder) throws Exception {

        logger.info("Product order name received is {}", productOrder.getName());
        logger.info("Product order id received is {}", productOrder.getId());
        logger.info("Product order description received is {}", productOrder.getDescription());
        logger.info("Product order item list received is {}", productOrder.getProductOrderItemList());
        logger.info("Product order skumap received is {}", productOrder.getSkumap());
        logger.info("Product order name received is {}", productOrder.getStatus());

        if (productOrder.getName().equals("exception")){
            throw new InvalidProductNameException(String.format("name of product %s is not acceptable", productOrder.getName()).toString());
        }

        if (productOrder.getId() == 444){
            throw new InvalidValueException(String.format("Id of the product cannot be %s", productOrder.getId()).toString());
        }

        if (productOrder.getStatus().equals("unknown")){
            throw new InvalidProductStatusException(String.format("status of product is %s", productOrder.getStatus()));
        }

        return productOrder.getId();
    }

    @Override
    public ProductOrder getOrder(int id, String orderStatus) throws Exception {

        logger.info("path param of order is {} and request param is {}", id, orderStatus);

        if (id==333){
            throw new ResourceNotFoundException(String.format("product with id %s not found", id).toString());
        }

        ProductOrder po = new ProductOrder();
        po.setId(id);
        po.setStatus(orderStatus);
        po.setProductOrderItemList(getProductOrderItemList());
        po.setSkumap(getSkuMap());
        po.setName("ProductOrder " + id);
        po.setDescription("Product Order description " + id);
        return po;
    }
    
    private List<ProductOrderItem> getProductOrderItemList(){
        
        List<ProductOrderItem> poiList = new ArrayList<>();


       for(int i=0;i<2;i++) {

           ProductOrderItem poi = new ProductOrderItem();
           poi.setId(ThreadLocalRandom.current().nextInt());
           poi.setName("tee " + i);
           poi.setDescription("some tee " + i);
           poi.setStatus("completed " + i);
           poiList.add(poi);

       }
       return poiList;
        
    }

    private Map<String, String> getSkuMap() {

        Map<String, String> skumap = new HashMap <>();

        for (int i=0;i<2;i++){
            skumap.put("3333 " + i, "tee " + i);
            skumap.put("4444 " + i, "tee " + i);
        }

        return skumap;
    }

}
