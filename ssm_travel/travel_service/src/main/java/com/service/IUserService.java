package com.service;

import com.domain.Role;
import com.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 当前接口必须扩展UserDetailsService
 *      因为UserDetailsService中有一个方法可以让我们完成论证的
 *      底层运行逻辑：
 *          使用security框架时，会加载很多配置文件，其中就将UserDetailsService放进去了容器中
 */
public interface IUserService extends UserDetailsService {


    List<UserInfo> findAll(int page,int size) throws Exception;

    void saveUsers(UserInfo userInfo)throws Exception;

    UserInfo findById(String userId)throws Exception;

    List<Role> findOtherRoles(String userId) throws Exception;

    void addRoleToUser(String userId, String[] rolesId);

    void deleteById(String id);
}
