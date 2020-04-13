package com.vincenttho.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @className:com.vincenttho.common.model.BaseDO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/7     VincentHo       v1.0.0        create
 */
@MappedSuperclass
@Data
@DynamicInsert
@DynamicUpdate
public class BaseDO {

    /** 是否有效 */
    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean isAvailable = true;

    /** 创建人id */
    @Column
    private Integer createUserId;

    /** 创建人id */
    @Column(length = 100)
    private String createUserName;

    /** 创建日期 */
    @Column(columnDefinition = "datetime not null default now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();

    /** 创建人id */
    @Column
    private Integer updateUserId;

    /** 创建人id */
    @Column(length = 100)
    private String updateUserName;

    /** 更新日期 */
    @Column(columnDefinition = "datetime not null default now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate = new Date();

    /** 描述 */
    @Column(length = 255)
    private String description;

}