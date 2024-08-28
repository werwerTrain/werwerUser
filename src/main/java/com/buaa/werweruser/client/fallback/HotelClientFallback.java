package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.client.HotelClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HotelClientFallback implements HotelClient {
    @Override
    public List<Map<String, Object>> getHotelOrderDetail(String oid) {
        System.out.println("get hotelOrder detail request failed, fallback method executed");
        return new ArrayList<>();
    }
}
