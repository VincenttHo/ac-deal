package com.vincenttho.common.model;

import lombok.Data;

/**
 * @className:com.vincenttho.common.model.ResultBean
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/7     VincentHo       v1.0.0        create
 */
@Data
public class ResultBean<T> {

    protected boolean result;

    protected String msg;

    protected String errorMsg;

    protected T data;

    public ResultBean(){}

    public ResultBean(T data) {
        this.result = true;
        this.msg = "接口调用成功！";
        this.data = data;
    }

    public ResultBean(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public ResultBean(boolean result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public ResultBean(String msg, String errorMsg) {
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

}