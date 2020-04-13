package com.vincenttho.utils;

import com.vincenttho.common.model.SysUser;

import javax.servlet.http.HttpServletRequest;

public class UserInfoUtil {
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();
 
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
 
    public static void add(SysUser sysUser){
        userHolder.set(sysUser);
    }
 
    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }
 
    public static SysUser getCurrentUser(){
        return userHolder.get();
    }
 
    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    public static Integer getCurrentUserId(){
        SysUser sysUser = userHolder.get();
        return sysUser.getUserId();
    }

    public static String getCurrentUserName(){
        SysUser sysUser = userHolder.get();
        return sysUser.getUserName();
    }

    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }
 
}