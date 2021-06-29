package com.dao;

import com.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionDao {

    @Select("select * from permission where id in (select permissionId from role_permission where roleId = #{roleId}) ")
    public List<Permission> findPermissionsById(String roleId) throws Exception;


    @Select("select * from permission")
    List<Permission> findAll()throws Exception;

    @Insert("insert into permission (id,permissionName,url) values(#{id},#{permissionName},#{url})")
    void save(Permission permission) throws Exception;

    @Select("select * from permission where id = #{permissionId}")
    Permission findById(String permissionId);
}
