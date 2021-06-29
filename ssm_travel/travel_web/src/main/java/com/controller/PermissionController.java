package com.controller;

import com.domain.Permission;
import com.github.pagehelper.PageInfo;
import com.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     *  查询所有权限信息
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page, @RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception{
        ModelAndView mv = new ModelAndView();
        System.err.println("查询权限信息");
        List<Permission> list = permissionService.findAll(page,size);
        PageInfo permissionList = new PageInfo(list);
        mv.addObject("permissionList",permissionList);
        mv.setViewName("permission-list");
        return mv;
    }

    /**
     * 添加权限
     */
    @RequestMapping("/save")
    public void save(Permission permission, HttpServletResponse response, HttpServletRequest request) throws Exception {
        System.err.println("添加权限");
        permissionService.save(permission);
        System.err.println("添加完成");
        response.sendRedirect(request.getContextPath()+"/permission/findAll");
    }

    /**
     * 通过id查询权限
     */
    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam(name = "id") String permissionId){
        System.err.println("根据id查询权限");
        ModelAndView mv = new ModelAndView();
        Permission permission = permissionService.findById(permissionId);
        mv.addObject("permission",permission);
        mv.setViewName("permission-show");
        return mv;
    }

}

