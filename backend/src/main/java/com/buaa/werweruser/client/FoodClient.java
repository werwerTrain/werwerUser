package com.buaa.werweruser.client;

import com.buaa.werweruser.client.fallback.FoodClientFallback;
import com.buaa.werweruser.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "food-service", path = "/api/foods", fallback = FoodClientFallback.class)
public interface FoodClient {

    @GetMapping("/getRelate/{tid}/{date}/{uid}")
    public List<OrderDTO> getTrainRelatedFoodOrders(@PathVariable String tid, @PathVariable String date, @PathVariable String uid);
}
