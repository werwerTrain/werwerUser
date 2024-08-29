package com.buaa.werweruser.client;

import com.buaa.werweruser.client.fallback.TrainClientFallback;
import com.buaa.werweruser.dto.PassengerDTO;
import com.buaa.werweruser.dto.TrainDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "train-service", path = "/api/trains", fallback = TrainClientFallback.class)
public interface TrainClient {

    @PostMapping("/insertPassengers/{id}")
    public Integer insertPassenger(@PathVariable("id") String id, @RequestParam(value="name")String name, @RequestParam(value="identification")String identification,@RequestParam(value="phone")String phone);

    @GetMapping("/getTrainIdAndDate/{orderId}")
    public List<Map<String, Object>> getTrainIdAndDate(@PathVariable String orderId);

    @GetMapping("/getStartTime/{trainId}/{trainDate}")
    public Map<String, Object> getStartTime(@PathVariable String trainId, @PathVariable String trainDate);

    @GetMapping("/getTrainState/{trainId}/{trainDate}")
    public Boolean getTrainState(@PathVariable String trainId, @PathVariable String trainDate);
}
