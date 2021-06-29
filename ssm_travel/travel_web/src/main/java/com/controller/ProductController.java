package com.controller;

import com.domain.Permission;
import com.domain.Product;
import com.github.pagehelper.PageInfo;
import com.service.IPermissionService;
import com.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    /**
     * 查询所有
     * @param
     * @return
     */
    @RequestMapping("/findAll")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "5")Integer size){
        ModelAndView mv =new ModelAndView();
        System.out.println("主页查询所有产品");
        List<Product> list = productService.findAll(page,size);
        PageInfo productpage = new PageInfo(list);
        mv.addObject("productList",productpage);
        mv.setViewName("product-list1");
        return mv;
    }


    /**
     * 添加数据
     * @param product
     */
    @RequestMapping("/save")
    public void saveProduct(Product product,HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("开始添加产品数据");
        productService.saveProduct(product);
        System.out.println("添加成功");
        System.out.println("跳转查询方法");
        response.sendRedirect(request.getContextPath()+"/product/findAll/");
    }



}
