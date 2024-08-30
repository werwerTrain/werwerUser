package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.dto.OrderDTO;
import com.buaa.werweruser.client.OrderClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderClientFallback implements OrderClient {
    @Override
    public List<OrderDTO> getOrdersByUidAndStatus(String uid, String status, String type) {
        System.out.println("get orders by uid and status request failed, fallback method executed");
        return new ArrayList<>();
    }

    @Override
    public List<OrderDTO> getOrderByUid(String uid, String type) {
        System.out.println("get order by uid request failed, fallback method executed");
        return new ArrayList<>();
    }

    @Override
    public OrderDTO getOrderByOidAndUid(String oid, String uid) {
        System.out.println("get train order by oid and uid request failed, fallback method executed");
        return new OrderDTO("f" + OrderDTO.generateOrderId(),
                "defaultUid",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                0.0,
                "Done",
                "Train");
    }

    @Override
    public OrderDTO getOrder(String oid) {
        System.out.println("get order request failed, fallback method executed");
        return new OrderDTO("f" + OrderDTO.generateOrderId(),
                "defaultUid",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                0.0,
                "Done",
                "Train");
    }

    @Override
    public void addOrder(OrderDTO orderDTO) {
        // 记录降级日志
        System.out.println("Add order request failed, fallback method executed.");
    }

    @Override
    public void cancelOrder(OrderDTO orderDTO) {
        System.out.println("Cancel order request failed, fallback method executed.");
    }

    @Override
    public void deleteOrder(OrderDTO orderDTO) {
        System.out.println("Delete order request failed, fallback method executed.");
    }

    @Override
    public void setCancelTime(String oid, String cancelTime) {
        System.out.println("Set cancel time request failed, fallback method executed.");
    }

    @Override
    public List<OrderDTO> getIdByUidAndStatus(String userID, String status) {
        System.out.println("get id by uid and status request failed, fallback method executed");
        return new ArrayList<>();
    }

    @Override
    public List<OrderDTO> getIdByUid(String userID) {
        System.out.println("get id by uid request failed, fallback method executed");
        return new ArrayList<>();
    }

    @Override
    public List<OrderDTO> getAllTrain() {
        System.out.println("get all train request failed, fallback method executed");
        return new ArrayList<>();
    }

    @Override
    public Boolean getMessageSend(String orderId) {
        System.out.println("get message send request failed, fallback method executed");
        return Boolean.FALSE;
    }

    @Override
    public void setMessageHaveSend(String orderId) {
        System.out.println("Set message have sent request failed, fallback method executed.");
    }

    @Override
    public void finishOrder(String oid) {
        System.out.println("Finish order request failed, fallback method executed.");
    }
}
