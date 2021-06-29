package com.service.impl;

import com.dao.IRoleDao;
import com.domain.Permission;
import com.domain.Role;
import com.github.pagehelper.PageHelper;
import com.service.IRoleService;
import com.utils.GenerateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
public class RoleServiceImpl  implements IRoleService {

    @Autowired
    private GenerateUuid generateUuid;

    @Autowired
    private IRoleDao roleDao;

    public List<Role> findAll(int page, int size) throws Exception {
        System.err.println("进入业务层：查询所有角色信息");
        PageHelper.startPage(page,size);
        return roleDao.findAll();
    }

    public void save(Role role) throws Exception {
        System.err.println("业务层：储存角色表");
        role.setId(generateUuid.Uuid());
        System.err.println("设置roleId:"+role.getId());
        roleDao.save(role);
    }

    public Role findById(String roleId) throws Exception {
        System.err.println("业务层：通过roleId查询角色详情:"+roleId);
        Role list = roleDao.findById(roleId);
        return list;
    }

    public void deleteById(String roleId) throws Exception {
        System.err.println("业务层：执行删除");
        roleDao.deleteFromUser_RoleById(roleId);
        roleDao.deleteFromRole_PermissionById(roleId);
        roleDao.deleteById(roleId);
    }

    public List<Permission> findRoleByIdAndAllPermission(String roleId) {
        System.err.println("业务层查询需要添加的权限");
        return roleDao.findRoleByIdAndAllPermission(roleId);
    }

    public void addPermissionToRole(String userId, String[] permissionIds) {
        for (String permissionId:permissionIds){
            roleDao.addPermissionToRole(userId,permissionId);
        }
    }
}
