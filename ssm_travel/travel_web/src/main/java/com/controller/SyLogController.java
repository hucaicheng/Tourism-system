package com.controller;


import com.domain.SyLog;
import com.github.pagehelper.PageInfo;
import com.service.ISyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SyLogController {

    @Autowired
    private ISyLogService syLogService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page, @RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception {
        System.out.println("日志查询");
        ModelAndView mv = new ModelAndView();
        List<SyLog> sysLogList = syLogService.findAll(page,size);
        PageInfo sysLogs = new PageInfo(sysLogList);
        mv.addObject("sysLogs",sysLogs);
        mv.setViewName("syslog-list");
        return mv;
    }
}
