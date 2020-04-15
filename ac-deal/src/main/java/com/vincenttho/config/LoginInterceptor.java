package com.vincenttho.config;

import com.vincenttho.utils.RedisUtil;
import com.vincenttho.utils.UserInfoUtil;
import com.vincenttho.common.model.SysUser;
import com.vincenttho.config.model.Audience;
import com.vincenttho.config.model.PassUrlConfig;
import com.vincenttho.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PassUrlConfig passUrlConfig;

    @Autowired
    private Audience audience;

    /**
     * 处理前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {


        String path = request.getServletPath();
        log.info("请求地址：{}", path);
        if(path.startsWith("/api")) {
            path = path.substring(4, path.length());

            List<String> passUrlList = passUrlConfig.getPassUrlList();

            // 可忽略连接
            if (!CollectionUtils.isEmpty(passUrlList)) {
                for (String passUrl : passUrlList) {
                    boolean startStar = passUrl.startsWith("**/");
                    boolean endStar = passUrl.endsWith("/**");
                    if (startStar && endStar && path.contains(passUrl)) {
                        log.info("请求地址是忽略校验地址");
                        return true;
                    } else if (startStar) {
                        passUrl = passUrl.replaceAll("/\\*\\*", "");
                        if (path.endsWith(passUrl)) {
                            log.info("请求地址是忽略校验地址");
                            return true;
                        }
                    } else if (endStar) {
                        passUrl = passUrl.replaceAll("/\\*\\*", "");
                        if (path.startsWith(passUrl)) {
                            log.info("请求地址是忽略校验地址");
                            return true;
                        }
                    } else if (path.equals(passUrl)) {
                        log.info("请求地址是忽略校验地址");
                        return true;
                    }
                }
            }

            try {
                String token = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
                if (token == null) {
                    log.error("登录无效，无token信息" );
                    this.unAuth(response, "登录无效，无token信息");
                    return false;
                }

                log.info("当前token：{}", token);

                String jwtToken = RedisUtil.get("auth::token::" + token);

                String userId = JwtTokenUtil.getUserId(jwtToken, audience.getBase64Secret());
                String username = JwtTokenUtil.getUsername(jwtToken, audience.getBase64Secret());
                SysUser sysUser = new SysUser();
                sysUser.setUserId(Integer.parseInt(userId));
                sysUser.setUserName(username);
                UserInfoUtil.add(sysUser);
                UserInfoUtil.add(request);

                log.info("当前用户信息：{}", sysUser);

                boolean expiration = JwtTokenUtil.isExpiration(jwtToken, audience.getBase64Secret());
                if (expiration) {
                    log.info("校验通过" );
                    return true;
                } else {
                    log.error("token已过期");
                    this.unAuth(response, "token已过期");
                    return false;
                }
            } catch (Exception e) {
                log.error("登录失效：{}", e.getMessage());
                this.unAuth(response, "登录失效");
                return false;
            }
        }

        return true;
    }
 
    /**
     * 处理后调用（正常）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
 
    /**
     * 处理后调用(任何情况)
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoUtil.remove();
    }

    private void unAuth(HttpServletResponse response, String errorMsg) throws Exception {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(errorMsg);
    }
 
}