package com.service.impl;

import com.dao.IUserDao;
import com.domain.Role;
import com.domain.UserInfo;
import com.github.pagehelper.PageHelper;
import com.service.IUserService;
import com.utils.GenerateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {
    /**
     * 必须实现接口，才能让security 知道如何调用service
     * @param
     * @return
     * @throws UsernameNotFoundException
     */

    @Autowired
    private IUserDao userDao;

    @Autowired
    private GenerateUuid generateUuid;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder ;//加密密码


    //登录时进行密码验证
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println("登录验证");
        UserInfo userInfo = null;
        try {
            userInfo= userDao.findByUserName(name);
        }catch (Exception e){
            e.printStackTrace();
        }
        //这个user对象事security框架自带的，实现了UserDetails接口，所以可以返回，
        // 三个参数：1：用户名， 2：密码，3：哪种角色(为空就登录失败）
        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));
        //四个参数（true忽略)：1：用户名， 2：密码，3：账户是否可以（0为不可用，1为可用），4：哪种角色(为空就登录失败）
        //未开启加密方法需要加{noop}
        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),userInfo.getStatus()== 0 ? false:true,true,true,true,getAuthority(userInfo.getRoles()));
        //开启加密
        User user = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()== 0 ? false:true,true,true,true,getAuthority(userInfo.getRoles()));

        return user;
    }

    /**
     * 该方法作用就是返回一个list集合，集合中装入的是角色的描述
     *      由于不能直接返回GrantedAuthority类型，所以用 SimpleGrantedAuthority
     *      在SimpleGrantedAuthority中，有个方法可以生成角色（权限）
     */
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        System.out.println("将查询出来的角色表信息返回:");
        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        //将在IRoleDao中查询到的 role表中角色角色字段传进去
        for(Role role:roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
            System.err.println("name="+role.getRoleName());
        }
        return list;
    }



    //查询所有数据（user)
    public List<UserInfo> findAll(int page,int size) throws Exception {
        System.out.println("业务层：执行findAll-user");
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

    //保存user表（个人信息表）
    public void saveUsers(UserInfo userInfo) {
        System.out.println("业务层：执行save-user");
        System.out.println("设置id值");
        userInfo.setId(generateUuid.Uuid());
        System.out.println(userInfo.getId());
        //加密密码
        String encode = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encode);
        System.out.println("密码加密成功："+encode+"长度："+encode.length());
        System.out.println("开始保存");
        userDao.save(userInfo);
        System.out.println("保存成功");
    }

    //查询用户详细信息：包括个人信息，权限信息，角色信息
    public UserInfo findById(String userId) {
        System.out.println("通过id查询个人所有信息"+userId);
        UserInfo list = userDao.findByUserId(userId);
        System.err.println(list);
        return list;
    }

    public List<Role> findOtherRoles(String userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    public void addRoleToUser(String userId, String[] rolesId) {
        for (String roleId:rolesId){
            userDao.addRoleToUser(userId,roleId);
        }
    }

    public void deleteById(String id) {
        System.err.println("业务层：删除用户"+id);
        userDao.deleteUser_RoleById(id);
        userDao.deleteUsersById(id);

    }
}
