package com.controller;

import com.domain.Orders;
import com.github.pagehelper.PageInfo;
import com.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    //无分页参数
//    @RequestMapping("/findAll")
//    public ModelAndView findAll() throws Exception {
//        ModelAndView mc = new ModelAndView();
//        System.out.println("查询orders订单表信息");
//        List<Orders> orders = ordersService.findAll();
//        mc.addObject("ordersList",orders);
//        mc.setViewName("orders-list");
//
//        return mc;
//    }

    @RequestMapping("/findAll")
    @Secured("ROLE_ADMIN")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page, @RequestParam(value = "size",required = true,defaultValue = "5") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        System.out.println("查询orders订单表信息");
        List<Orders> orders = ordersService.findAll(page,size);
        //pageInfo就是一个封装的分页bean，传入list以后，会自己将list返回。
        PageInfo pageInfo = new PageInfo(orders);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;
    }

    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam(name = "id",required = true)String ordersId) throws Exception {
        System.out.println("查看订单详情");
        ModelAndView mv = new ModelAndView();
        Orders orders = ordersService.findById(ordersId);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }

}
