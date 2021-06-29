package com.dao;

import com.domain.Permission;
import com.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao {

    //根据用户id 去users_role表中查询出是哪种角色
    @Select("SELECT * FROM role WHERE id in (select roleId from users_role WHERE userId = #{UsersId} )")
    @Results({
            @Result(id = true,property = "id", column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.dao.IPermissionDao.findPermissionsById"))
    })
    public List<Role> findUsersById(String UsersId) throws Exception;

    //查询role所有数据
    @Select("select * from role")
    List<Role> findAll() throws Exception;

    @Insert("insert into role(id,roleName,roleDesc) values (#{id},#{roleName},#{roleDesc})")
    void save(Role role) throws Exception;

    @Select("select * from role where id = #{roleId}")
    @Results({
            @Result(id = true,property = "id", column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.dao.IPermissionDao.findPermissionsById"))

    })
    Role findById(String roleId) throws Exception;

    @Delete("DELETE FROM users_role where roleId = #{roleId}")
    void deleteFromUser_RoleById(String roleId) throws Exception;

    @Delete("DELETE FROM role_permission where roleId = #{roleId}")
    void deleteFromRole_PermissionById(String roleId) throws Exception;

    @Delete("delete from role where id = #{roleId}")
    void deleteById(String roleId) throws Exception;

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId = #{roleId} )")
    List<Permission> findRoleByIdAndAllPermission(String roleId);

    @Insert("insert into role_permission(roleId,permissionId)values(#{userId},#{permissionId})")
    void addPermissionToRole(@Param(value = "userId") String userId, @Param(value = "permissionId") String permissionId);
}
