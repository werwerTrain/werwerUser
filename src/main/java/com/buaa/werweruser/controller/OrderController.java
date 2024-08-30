package com.buaa.werweruser.controller;

import com.buaa.werweruser.dto.OrderDTO;
import com.buaa.werweruser.entity.Order;
import com.buaa.werweruser.service.IOrderService;
import com.buaa.werweruser.service.OrderConverter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderConverter orderConverter;

    @GetMapping("/orders/{uid}/status")
    @CircuitBreaker(name = "getOrderByUidAndStatus", fallbackMethod = "getOrdersByUidAndStatusFallback")
    public List<OrderDTO> getOrdersByUidAndStatus(@PathVariable String uid,
                                                  @RequestParam String status,
                                                  @RequestParam String type) {
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
        Order.OrderType orderType = Order.OrderType.valueOf(type);
        List<Order> orders = orderService.getOrdersByUidAndStatus(uid, orderStatus, orderType);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUidAndStatusFallback(String uid, String status, String type, Throwable t) {
        System.out.println("get orders by uid and status request failed, fallback method executed");
        return new ArrayList<>();
    }

    @GetMapping("/orders/byUid/{uid}")
    @CircuitBreaker(name = "getOrderByUid", fallbackMethod = "getOrderByUidFallback")
    public List<OrderDTO> getOrderByUid(@PathVariable String uid, @RequestParam String type) {
        Order.OrderType orderType = Order.OrderType.valueOf(type);
        List<Order> orders = orderService.getOrderByUid(uid, orderType);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrderByUidFallback(String uid, String type, Throwable t) {
        System.out.println("get order by uid request failed, fallback method executed");
        return new ArrayList<>();
    }

    @GetMapping("/orders/byOidAndUid/{oid}/{uid}")
    @CircuitBreaker(name = "getOrderByOidAndUid", fallbackMethod = "getOrderByOidAndUidFallback")
    public OrderDTO getOrderByOidAndUid(@PathVariable String oid, @PathVariable String uid) {
        Order order = orderService.getOrderByOidAndUid(oid, uid);
        return orderConverter.toDTO(order);
    }

    public OrderDTO getOrderByOidAndUidFallback(String oid, String uid, Throwable t) {
        System.out.println("get train order by oid and uid request failed, fallback method executed");
        return new OrderDTO("f" + OrderDTO.generateOrderId(),
                "defaultUid",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                0.0,
                "Done",
                "Train");
    }

    @GetMapping("/orders/byOid/{oid}")
    @CircuitBreaker(name = "getOrder", fallbackMethod = "getOrderFallback")
    public OrderDTO getOrder(@PathVariable String oid) {
        Order order = orderService.getOrder(oid);
        return orderConverter.toDTO(order);
    }

    public OrderDTO getOrderFallback(String oid, Throwable t) {
        System.out.println("get order request failed, fallback method executed");
        return new OrderDTO("f" + OrderDTO.generateOrderId(),
                "defaultUid",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                0.0,
                "Done",
                "Train");
    }

    @PostMapping("/orders/addOrder")
    @CircuitBreaker(name = "addOrder", fallbackMethod = "addOrderFallback")
    public void addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderConverter.toEntity(orderDTO);
        orderService.addOrder(order);
    }

    public void addOrder(OrderDTO orderDTO, Throwable t) {
        // 记录降级日志
        System.out.println("Add order request failed, fallback method executed.");
    }

    @PostMapping("/orders/cancel")
    @CircuitBreaker(name = "cancelOrder", fallbackMethod = "cancelOrderFallback")
    public void cancelOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderConverter.toEntity(orderDTO);
        orderService.cancelOrder(order);
    }

    public void cancelOrderFallback(OrderDTO orderDTO, Throwable t) {
        System.out.println("Cancel order request failed, fallback method executed.");
    }

    @DeleteMapping("/orders/deleteOrder")
    @CircuitBreaker(name = "deleteOrder", fallbackMethod = "deleteOrderFallback")
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderConverter.toEntity(orderDTO);
        orderService.deleteOrder(order);
    }

    public void deleteOrderFallback(OrderDTO orderDTO, Throwable t) {
        System.out.println("Delete order request failed, fallback method executed.");
    }

    @PostMapping("/orders/{oid}/cancel-time")
    @CircuitBreaker(name = "setCancelTime", fallbackMethod = "setCancelTimeFallback")
    public void setCancelTime(@PathVariable String oid, @RequestParam String cancelTime) {
        orderService.setCancelTime(oid, cancelTime);
    }

    public void setCancelTimeFallback(String oid, String cancelTime, Throwable t) {
        System.out.println("Set cancel time request failed, fallback method executed.");
    }


    @GetMapping("/orders/{userID}/status-id")
    @CircuitBreaker(name = "getIdByUidAndStatus", fallbackMethod = "getIdByUidAndStatusFallback")
    public List<OrderDTO> getIdByUidAndStatus(@PathVariable String userID, @RequestParam String status) {
        List<Order> orders = orderService.getIdByUidAndStatus(userID, status);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getIdByUidAndStatusFallback(String userID, String status, Throwable t) {
        System.out.println("get id by uid and status request failed, fallback method executed");
        return new ArrayList<>();
    }


    @GetMapping("/orders/{userID}/id")
    @CircuitBreaker(name = "getIdByUid", fallbackMethod = "getIdByUidFallback")
    public List<OrderDTO> getIdByUid(@PathVariable String userID) {
        List<Order> orders = orderService.getIdByUid(userID);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getIdByUidFallback(String userID, Throwable t) {
        System.out.println("get id by uid request failed, fallback method executed");
        return new ArrayList<>();
    }

    @GetMapping("/orders/trains")
    @CircuitBreaker(name = "getAllTrain", fallbackMethod = "getAllTrainFallback")
    public List<OrderDTO> getAllTrain() {
        List<Order> orders = orderService.getAllTrain();
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllTrainFallback(Throwable t) {
        System.out.println("get all train request failed, fallback method executed");
        return new ArrayList<>();
    }

    @GetMapping("/orders/{orderId}/get-message-sent")
    @CircuitBreaker(name = "getMessageSend", fallbackMethod = "getMessageSendFallback")
    public Boolean getMessageSend(@PathVariable String orderId) {
        return orderService.getMessageSend(orderId);
    }


    public Boolean getMessageSendFallback(String orderId, Throwable t) {
        System.out.println("get message send request failed, fallback method executed");
        return Boolean.FALSE;
    }

    @PostMapping("/orders/{orderId}/set-message-sent")
    @CircuitBreaker(name = "setMessageHaveSend", fallbackMethod = "setMessageHaveSendFallback")
    public void setMessageHaveSend(@PathVariable String orderId) {
        orderService.setMessageHaveSend(orderId);
    }

    public void setMessageHaveSendFallback(String orderId, Throwable t) {
        System.out.println("Set message have sent request failed, fallback method executed.");
    }

    @PostMapping("/orders/{oid}/finish")
    @CircuitBreaker(name = "finishOrder", fallbackMethod = "finishOrderFallback")
    public void finishOrder(@PathVariable String oid) {
        orderService.finishOrder(oid);
    }

    public void finishOrderFallback(String oid, Throwable t) {
        System.out.println("Finish order request failed, fallback method executed.");
    }


}

