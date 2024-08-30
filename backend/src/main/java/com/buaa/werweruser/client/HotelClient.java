package com.buaa.werweruser.client;

import com.buaa.werweruser.client.fallback.HotelClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "hotel-service", path = "/api/hotels", fallback = HotelClientFallback.class)
public interface HotelClient {

    @GetMapping("/getOrderDetail")
    public List<Map<String, Object>> getHotelOrderDetail(String oid);
}
