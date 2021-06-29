package com.dao;


import com.domain.Role;
import com.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {

    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id" ,javaType = java.util.List.class,many=@Many(select = "com.dao.IRoleDao.findUsersById")),
    })
    public UserInfo findByUserName(String name) throws Exception;

    @Select("select * from users")
    public List<UserInfo> findAll()throws Exception;

    @Insert("insert into users(id,email,username,password,phoneNum,status) values(#{id},#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    @Select("select * from users where id = #{userId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id" ,javaType = java.util.List.class,many=@Many(select = "com.dao.IRoleDao.findUsersById")),
    })
    UserInfo findByUserId(String userId);


    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> findOtherRoles(String userId);

    @Insert("insert INTO users_role(userId,roleId)VALUES(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);

    @Delete("DELETE FROM users where id = #{id}")
    void deleteUsersById(String id);

    @Delete("DELETE FROM users_role where userId = #{id}")
    void deleteUser_RoleById(String id);
}