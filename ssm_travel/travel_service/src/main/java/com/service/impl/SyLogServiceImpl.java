package com.service.impl;

import com.dao.ISyLogDao;
import com.domain.SyLog;
import com.github.pagehelper.PageHelper;
import com.service.ISyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SyLogServiceImpl implements ISyLogService {

    @Autowired
    private ISyLogDao syLogDao;

    public void save(SyLog syLog) throws Exception {
        syLogDao.save(syLog);
    }

    public List<SyLog> findAll(Integer page,Integer size) throws Exception {
        System.out.println("业务层：findAll");
        PageHelper.startPage(page,size);
        return syLogDao.findAll();
    }
}
