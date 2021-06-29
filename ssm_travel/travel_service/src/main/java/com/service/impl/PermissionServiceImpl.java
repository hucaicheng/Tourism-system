package com.service.impl;

import com.dao.IPermissionDao;
import com.domain.Permission;
import com.github.pagehelper.PageHelper;
import com.service.IPermissionService;
import com.utils.GenerateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("permission")
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private GenerateUuid uuid;

    @Autowired
    private IPermissionDao permissionDao;

    public List<Permission> findAll(int page,int size) throws Exception {
        System.err.println("业务层：查询所有权限");
        PageHelper.startPage(page,size);
        return permissionDao.findAll();
    }

    public void save(Permission permission) throws Exception {
        System.err.println("开始储存权限");
        permission.setId(uuid.Uuid());
        System.err.println(permission);
        permissionDao.save(permission);
    }

    public Permission findById(String permissionId) {
        System.err.println("业务层：查询权限");
        Permission permission = permissionDao.findById(permissionId);
        System.err.println(permission);
        return permission;
    }
}
