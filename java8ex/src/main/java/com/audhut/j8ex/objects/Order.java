package com.audhut.j8ex.objects;

import java.util.function.Consumer;

/**
 * Created by avdhut on 17/9/17.
 */
public class Order {

    Apple a;

    public Apple getApple() {
        return a;
    }

    public void setApple(Apple a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "Order{" +
                "a=" + a.toString() +
                '}';
    }

    public String placeOrder(int custId, Consumer<Customer> makeHappy){
        Customer cus = getCustomerfromDB(custId);
        makeHappy.accept(cus);
        return "success";

    }

    public Customer getCustomerfromDB(int custid){
        return new Customer();
    }
}
