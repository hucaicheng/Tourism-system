package com.service;

import com.domain.Orders;

import java.util.List;


public interface IOrdersService {

    //查询orders表中的所有数据
    public List<Orders> findAll(int page,int size) throws Exception;


    public Orders findById(String ordersId) throws Exception;
}
