package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.client.FoodClient;
import com.buaa.werweruser.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class FoodClientFallback implements FoodClient {
    @Override
    public List<OrderDTO> getTrainRelatedFoodOrders(String tid, String date, String uid) {
        System.out.println("get train related foodOrders request failed, fallback method executed");
        return new ArrayList<>();
    }
}
