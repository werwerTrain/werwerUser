package com.buaa.werweruser.service;

import com.buaa.werweruser.dto.OrderDTO;
import com.buaa.werweruser.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO(order.getOid(), order.getUid(), order.getBillTime(), order.getTotal(), order.getOrderStatus().name(), order.getOrderType().name());
        dto.setOid(order.getOid());
        dto.setUid(order.getUid());
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setOrderType(order.getOrderType().name());
        dto.setBillTime(order.getBillTime());
        dto.setCancelTime(order.getCancelTime());
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order(dto.getOid(), dto.getUid(), dto.getBillTime(), dto.getTotal(), Order.OrderStatus.valueOf(dto.getOrderStatus()), Order.OrderType.valueOf(dto.getOrderType()));
        order.setOid(dto.getOid());
        order.setUid(dto.getUid());
        order.setOrderStatus(Order.OrderStatus.valueOf(dto.getOrderStatus()));
        order.setOrderType(Order.OrderType.valueOf(dto.getOrderType()));
        order.setBillTime(dto.getBillTime());
        order.setCancelTime(dto.getCancelTime());
        return order;
    }
}

