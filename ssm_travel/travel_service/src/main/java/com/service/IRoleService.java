package com.service;

import com.domain.Permission;
import com.domain.Role;

import java.util.List;

public interface IRoleService {


    List<Role> findAll(int page, int size) throws Exception;

    void save(Role role) throws Exception;

    Role findById(String roleId) throws Exception;

    void deleteById(String roleId) throws Exception;

    List<Permission> findRoleByIdAndAllPermission(String roleId);

    void addPermissionToRole(String userId, String[] permissionId);
}
