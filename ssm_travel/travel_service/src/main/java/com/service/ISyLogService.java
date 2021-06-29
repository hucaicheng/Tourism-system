package com.service;

import com.domain.SyLog;

import java.util.List;

public interface ISyLogService {

    void save(SyLog syLog) throws Exception;

     List<SyLog> findAll(Integer page,Integer size) throws Exception;
}
