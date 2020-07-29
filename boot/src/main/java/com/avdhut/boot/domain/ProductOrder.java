package com.avdhut.boot.domain;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Component
public class ProductOrder {

    @Min(value=1L, message="Id must be greater than 0")
    private int Id;

    @NotEmpty(message = "name should not be empty")
    @NotNull(message = "name should not be null")
    private String name;

    private String description;
    private List<ProductOrderItem> productOrderItemList;
    private Map<String,String> skumap;
    private String status;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductOrderItem> getProductOrderItemList() {
        return productOrderItemList;
    }

    public void setProductOrderItemList(List<ProductOrderItem> productOrderItemList) {
        this.productOrderItemList = productOrderItemList;
    }

    public Map<String, String> getSkumap() {
        return skumap;
    }

    public void setSkumap(Map<String, String> skumap) {
        this.skumap = skumap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
