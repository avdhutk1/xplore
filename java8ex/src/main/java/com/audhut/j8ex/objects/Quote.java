package com.audhut.j8ex.objects;

/**
 * Created by avdhut on 2/10/18.
 */
public class Quote {

    private final String shopName;
    private final double price;
    private final Discount.Code code;

    public Quote(String shopName, double price, Discount.Code code) {
        this.shopName = shopName;
        this.price = price;
        this.code = code;
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getCode() {
        return code;
    }

    public static Quote parse(String s){
        String[] splt = s.split(":");
        String shopName = splt[0];
        double price = Double.parseDouble(splt[1]);
        Discount.Code disCode = Discount.Code.valueOf(splt[2]);
        return new Quote(shopName,price,disCode);
    }
}
