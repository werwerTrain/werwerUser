package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.client.TrainClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainClientFallback implements TrainClient {

    @Override
    public Integer insertPassenger(@PathVariable("id") String id, @RequestParam(value="name")String name, @RequestParam(value="identification")String identification, @RequestParam(value="phone")String phone)
    {
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
