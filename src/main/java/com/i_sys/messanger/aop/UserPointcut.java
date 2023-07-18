package com.i_sys.messanger.aop;

import org.aspectj.lang.annotation.Pointcut;

public class UserPointcut {

    @Pointcut("execution(* com.i_sys.messanger.controller.*.*(..))")
    public void checkUserExistence(){}
}
