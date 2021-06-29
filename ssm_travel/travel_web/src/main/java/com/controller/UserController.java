package com.controller;

import com.domain.Role;
import com.domain.UserInfo;
import com.github.pagehelper.PageInfo;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询所有用户
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page, @RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> list = userService.findAll(page,size);
        PageInfo userList = new PageInfo(list);
        mv.addObject("userList",userList);
        mv.setViewName("user-list");
        return  mv;
    }

    /**
     * 添加用户user
     */

    @RequestMapping("/save")
    @PreAuthorize("authentication.principal.username =='hcc2'") //authentication:当前用户的信息,principal  当前正在操作的对象
    public void save(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("添加新用户,users表");
        userService.saveUsers(userInfo);
        response.sendRedirect(request.getContextPath()+"/user/findAll/");
    }

    //删除用户
    @RequestMapping("/deleteById")
    public void deleteById(@RequestParam(name = "id") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.err.println("删除用户");
        System.err.println(id);
        userService.deleteById(id);
        response.sendRedirect(request.getContextPath()+"/user/findAll/");
    }

    //查询id指定用户
    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam(name = "id")String userId) throws Exception {
        System.err.println("通过id查询详情");
        ModelAndView mv = new ModelAndView();
        UserInfo list = userService.findById(userId);
        mv.addObject("user",list);
        mv.setViewName("user-show1");
        return mv;
    }

    //添加角色权限
    @RequestMapping("/findUserByIdAndAllRole")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id") String userId) throws Exception {
        ModelAndView mv = new ModelAndView();
        //1.通过id查看用户
        UserInfo userInfo = userService.findById(userId);
        //2.根据用户id查询能够添加权限
        List<Role> otherRoles= userService.findOtherRoles(userId);
        mv.addObject("user",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    //添加角色权限
    @RequestMapping("/addRoleToUser")
    @RolesAllowed("ADMIN")
    public void addRoleToUser(@RequestParam(name = "userId")String userId,@RequestParam(name = "ids") String[] rolesId,HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.addRoleToUser(userId,rolesId);
        response.sendRedirect(request.getContextPath()+"/user/findAll");
    }
}
