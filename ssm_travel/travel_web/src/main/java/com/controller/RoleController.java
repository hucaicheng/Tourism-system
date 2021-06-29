package com.controller;

import com.domain.Permission;
import com.domain.Role;
import com.github.pagehelper.PageInfo;
import com.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 查询所有角色
     * @param page 网页数
     * @param size  多少条数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page, @RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception {
        System.err.println("请求：role/findAll。查询所有角色");
        ModelAndView mv = new ModelAndView();
        List<Role> list = roleService.findAll(page,size);
        PageInfo roleList = new PageInfo(list);
        mv.addObject("roleList",roleList);
        mv.setViewName("role-list");
        return mv;
    }

    /**
     * 添加角色
     */
    @RequestMapping("/save")
    public void save(Role role, HttpServletRequest request, HttpServletResponse response)throws Exception{
        System.err.println("请求：role/save:储存角色表数据");
        roleService.save(role);
        response.sendRedirect(request.getContextPath()+"/role/findAll");
    }

    /**
     * 查询角色详情
     * @param roleId
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam(name = "id") String roleId) throws Exception{
        System.err.println("查询角色详情");
        System.err.println(roleId);
        ModelAndView mv = new ModelAndView();
        Role role=roleService.findById(roleId);
        System.err.println(role);
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    /**
     * 通过i删除角色
     */
    @RequestMapping("/deleteById")
    public void deleteById(@RequestParam(name = "id")String roleId,HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("通过id删除角色");
        roleService.deleteById(roleId);
        response.sendRedirect(request.getContextPath()+"/role/findAll");
    }

    /**
     * 查询需要添加的权限
     */
    @RequestMapping("/findRoleByIdAndAllPermission")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id") String roleId) throws Exception {
//        System.err.println("角色添加权限");
//        roleService.findRoleByIdAndAllPermission(roleId);
//        response.sendRedirect(request.getContextPath()+"/role/findAll");
        System.err.println("查询需要添加的权限");
        ModelAndView mv = new ModelAndView();
        //查询当前角色
        Role role = roleService.findById(roleId);
        //查询需要添加的权限
        List<Permission> permissionList= roleService.findRoleByIdAndAllPermission(roleId);
        mv.addObject("permissionList",permissionList);
        mv.addObject("role",role);
        mv.setViewName("role-permission-add");
        return mv;
    }

    @RequestMapping("/addPermissionToRole")
    public void addPermissionToRole(@RequestParam(name = "userId")String userId,@RequestParam(name = "ids") String[] permissionId,HttpServletRequest request,HttpServletResponse response ) throws IOException {
        System.err.println("添加权限");
        roleService.addPermissionToRole(userId,permissionId);
        response.sendRedirect(request.getContextPath()+"/role/findAll");
    }

}
