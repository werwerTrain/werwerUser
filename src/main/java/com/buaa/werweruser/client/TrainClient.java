package com.buaa.werweruser.client;

import com.buaa.werweruser.dto.PassengerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "train-service")
public interface TrainClient {

    @PostMapping("/insertPassengers/{id}")
    PassengerDTO insertPassenger(@PathVariable("id") String id);
}
