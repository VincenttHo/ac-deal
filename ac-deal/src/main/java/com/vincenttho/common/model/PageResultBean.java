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
public class PageResultBean<T> extends ResultBean<T> {

    private int currentPage;

    private int pageSize;

    private int totalPages;

    private long totalElements;

    public PageResultBean(){}

    public PageResultBean(T data) {
        this.result = true;
        this.msg = "接口调用成功！";
        this.data = data;
    }

    public PageResultBean(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public PageResultBean(boolean result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public PageResultBean(String msg, String errorMsg) {
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

}