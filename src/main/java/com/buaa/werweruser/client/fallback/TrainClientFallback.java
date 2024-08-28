package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.client.TrainClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainClientFallback implements TrainClient {

    @Override
    public Integer insertPassenger(String id, Map<String, String> requestMap) {
        System.out.println("insert passenger request failed, fallback method executed");
        return Integer.valueOf("0");
    }

    @Override
    public List<Map<String, Object>> getTrainIdAndDate(String orderId) {
        System.out.println("get trainId and date request failed, fallback method executed");
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getStartTime(String trainId, String trainDate) {
        System.out.println("get start time request failed, fallback method executed");
        return new HashMap<>();
    }

    @Override
    public Boolean getTrainState(String trainId, String trainDate) {
        System.out.println("get train state request failed, fallback method executed");
        return Boolean.FALSE;
    }
}
