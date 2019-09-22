package com.audhut.j8ex.intf;

import com.audhut.j8ex.objects.Apple;
import com.audhut.j8ex.objects.Order;

/**
 * Created by avdhut on 17/9/17.
 */
@FunctionalInterface
public interface OrderingSystem {

    Order createOrder(Apple a);

}
