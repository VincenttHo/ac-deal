package com.vincenttho.config;

import com.vincenttho.common.model.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @className:com.vincent.config.ExceptionHandler
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2019/10/11     VincentHo       v1.0.0        create
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResultBean ExceptionHandler(Exception e) {
        log.error("拦截异常：{}", e);
        return new ResultBean(e.getMessage(), e.toString());
    }

}