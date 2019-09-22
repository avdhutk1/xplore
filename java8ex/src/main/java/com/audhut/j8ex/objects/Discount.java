package com.audhut.j8ex.objects;


import java.util.concurrent.CompletableFuture;

/**
 * Created by avdhut on 2/10/18.
 */
public class Discount {

    public enum Code{
            GOLD(20), SILVER(10), BRONZE(5);

            private final int percent;

            Code(int percent){
            this.percent = percent;
        }
    }

    public static String applyDiscount(Quote quote){
        return quote.getShopName() + "price is " +
                Discount.apply(quote.getPrice(),quote.getCode());

    }

    public static double apply(double price, Code code){
        //introduce a delay of 1 sec to simulate asyn operation
        delay();
        return (price * (100 - code.percent)/100);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

   String cs;

    public Discount(){

    }

    public Discount(String cs){
        this.cs = cs;
    }

    public String getCs(){
        return cs;
    }
}
