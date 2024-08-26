package com.buaa.werweruser.controller;

import com.buaa.werweruser.dto.OrderDTO;
import com.buaa.werweruser.entity.Order;
import com.buaa.werweruser.service.IOrderService;
import com.buaa.werweruser.service.OrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @GetMapping("/orders/{uid}")
    public List<OrderDTO> getOrderByUid(@PathVariable String uid, @RequestParam String type) {
        Order.OrderType orderType = Order.OrderType.valueOf(type);
        List<Order> orders = orderService.getOrderByUid(uid, orderType);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/{oid}/{uid}")
    public OrderDTO getOrderByOidAndUid(@PathVariable String oid, @PathVariable String uid) {
        Order order = orderService.getOrderByOidAndUid(oid, uid);
        return orderConverter.toDTO(order);
    }

    @GetMapping("/orders/{oid}")
    public OrderDTO getOrder(@PathVariable String oid) {
        Order order = orderService.getOrder(oid);
        return orderConverter.toDTO(order);
    }

    @PostMapping("/orders/addOrder")
    public void addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderConverter.toEntity(orderDTO);
        orderService.addOrder(order);
    }

    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderConverter.toEntity(orderDTO);
        orderService.cancelOrder(order);
    }

    @DeleteMapping("/orders/deleteOrder")
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderConverter.toEntity(orderDTO);
        orderService.deleteOrder(order);
    }

    @PostMapping("/orders/{oid}/cancel-time")
    public void setCancelTime(@PathVariable String oid, @RequestParam String cancelTime) {
        orderService.setCancelTime(oid, cancelTime);
    }

    @GetMapping("/orders/{userID}/status-id")
    public List<OrderDTO> getIdByUidAndStatus(@PathVariable String userID, @RequestParam String status) {
        List<Order> orders = orderService.getIdByUidAndStatus(userID, status);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/{userID}/id")
    public List<OrderDTO> getIdByUid(@PathVariable String userID) {
        List<Order> orders = orderService.getIdByUid(userID);
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/trains")
    public List<OrderDTO> getAllTrain() {
        List<Order> orders = orderService.getAllTrain();
        return orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/{orderId}/message-sent")
    public Boolean getMessageSend(@PathVariable String orderId) {
        return orderService.getMessageSend(orderId);
    }

    @PostMapping("/orders/{orderId}/message-sent")
    public void setMessageHaveSend(@PathVariable String orderId) {
        orderService.setMessageHaveSend(orderId);
    }

    @PostMapping("/orders/{oid}/finish")
    public void finishOrder(@PathVariable String oid) {
        orderService.finishOrder(oid);
    }
}

