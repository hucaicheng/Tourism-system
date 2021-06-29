package com.controller;

import com.domain.SyLog;
import com.service.ISyLogService;
import com.utils.GenerateUuid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISyLogService syLogService;

    @Autowired
    private GenerateUuid uuid;

    private Date visitTime;//开始的时间
    private Class clazz;//访问的类
    private Method method;//执行的方法

    //前置通知  主要功能：获取访问时间、访问的哪个类，访问的哪个方法
    @Before("execution(* com.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        System.out.println("前置通知");
        visitTime = new Date();//获取当前时间
        clazz = jp.getTarget().getClass();//获取具体要访问的类
        String methodName = jp.getSignature().getName();//获取当前执行的方法名
        Object[] args = jp.getArgs();//获取访问的方法参数
        //获取具体的执行的方法的Method对象
        if (args == null ||  args.length==0) {
            System.out.println("无参");
            method = clazz.getMethod(methodName);//只能获取无参的方法
        }else {

            Class[] classArgs = new Class[args.length];
            for (int i = 0;i<args.length;i++){
                classArgs[i] = args[i].getClass();//通过当前参数获取类
            }
            method = clazz.getMethod(methodName,classArgs);
        }

    }

    //后置通知
    @After("execution(* com.controller.*.*(..))")
    public  void doAfter(JoinPoint jp) throws Exception {
        System.out.println("后置通知");
        //获取访问时长
        Long time = new Date().getTime()-visitTime.getTime();

        //获取请求的url  通过反射来获取
        String url = "";
        int a = 0;
        if (clazz!=null && method !=null &&clazz!=LogAop.class){
            //System.out.println("url不为空");
            a++;
            //1.获取类上注解@RequestMapping
            RequestMapping clazzAnnotation= (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (clazzAnnotation!=null){

                String[] classValues = clazzAnnotation.value();//获取具体的value值

                //2.获取方法上的@RequestMapping注解
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValues = methodAnnotation.value();

                    //将请求拼接
                    url = classValues[0]+methodValues[0];


                    //获取访问的ip   通过request对象，在web.xml文件配置一个listener，名为RequestContextListener
                    String ip = request.getRemoteAddr();

                    //获取访问者
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前的登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //将日志信息封装到SyLog对象中
                    SyLog syLog = new SyLog();
                    syLog.setId(uuid.Uuid());
                    syLog.setExecutionTime(time);
                    syLog.setIp(ip);
                    syLog.setUrl(url);
                    syLog.setUsername(username);
                    syLog.setVisitTime(visitTime);
                    syLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());

                    //调用service完成储存
                    System.out.println("切面：启动日志储存");
                    syLogService.save(syLog);
                }

            }

        }

    }
}
