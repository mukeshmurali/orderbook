package com.exchange;

import com.exchange.model.Order;
import com.exchange.model.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestMain {
    public static void main(String[] args) {
        Order order1 = new Order(Side.ASK, "IGG", 5, 30);
        Order order2 = new Order(Side.ASK, "IGG", 10, 28);
        Order order3 = new Order(Side.ASK, "IGG", 13, 28);
        Order order4 = new Order(Side.ASK, "IGG", 7, 25);

        List<Order> askList = new ArrayList<>();
        askList.add(order1);
        askList.add(order2);
        askList.add(order3);
        askList.add(order4);



        List<Order> iggOrdersLevel1 = askList
                .stream()
                .filter(o -> o.getPrice() >=30)
                .collect(Collectors.toList());


        //filtering orders that match a criteria from orderslist
        for (Order o:iggOrdersLevel1) {
            System.out.println(o.getQuantity() + " " + o.getPrice() + " " + o.getSymbol());
        }

        Map<Integer,Order> orderMap1=new HashMap<>();
        orderMap1.put(1,order1);
        orderMap1.put(2,order2);
        orderMap1.put(3,order3);
        orderMap1.put(4,order4);

        Map<Integer,Order> orderMap2=new HashMap<>(orderMap1);
        System.out.println(orderMap2.get(1).getPrice());
        //Merging the maps
        orderMap2.merge(1,order1,(v1,v2) -> new Order(Side.ASK,"IGG",v2.getQuantity(),66));
        System.out.println(orderMap2.get(1).getPrice());







    }
}
