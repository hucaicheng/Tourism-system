package com.service.impl;

import com.dao.IOrdersDao;
import com.domain.Orders;
import com.github.pagehelper.PageHelper;
import com.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao orders;



    public List<Orders> findAll(int page,int size) throws Exception {

        //参数pageNum是控制页码值，参数pageSize是控制每页显示的数量
        PageHelper.startPage(page,size);
        return orders.findAll();
    }

    public Orders findById(String ordersId) throws Exception {
        System.out.println("业务层执行findById:查看产品的详情");
        System.out.println(ordersId);
        return orders.findById(ordersId);
    }
}
