package com.buaa.werweruser.client;

import com.buaa.werweruser.dto.PassengerDTO;
import com.buaa.werweruser.dto.TrainDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "train-service",path="/api/trains")
public interface TrainClient {

    @PostMapping("/insertPassengers/{id}")
    PassengerDTO insertPassenger(@PathVariable("id") String id);

    @GetMapping("/getTrainIdAndDate/{orderId}")
    public List<Map<String, Object>> getTrainIdAndDate(@PathVariable String orderId);

    @GetMapping("/getStartTime/{trainId}/{trainDate}")
    public Map<String,Object> getStartTime(@PathVariable String trainId, @PathVariable String trainDate);

    @GetMapping("/getTrainState/{trainId}/{trainDate}")
    public Boolean getTrainState(String trainId,String trainDate);
}
