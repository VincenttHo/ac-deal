package com.vincenttho.service.user;

import com.vincenttho.common.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @className:com.vincent.service.base.UserInfoDO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2019/10/10     VincentHo       v1.0.0        create
 */
@Entity
@Table(name = "t_user_info")
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户信息")
public class UserInfoDO extends BaseDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(value ="用户id")
    private Integer userId;

    @Column(length = 100)
    @ApiModelProperty(value ="邮箱地址")
    private String email;

    @Column(length = 100)
    @ApiModelProperty(value ="用户名")
    private String userName;

    @Column(length = 100)
    @ApiModelProperty(value ="岛名")
    private String islandName;

    @Column(length = 100)
    @ApiModelProperty(value ="sw码")
    private String nintendoAccount;

    @Column(length = 100)
    @ApiModelProperty(value ="头像")
    private String avatar;

    @Column(length = 100)
    @ApiModelProperty(value ="密码")
    private String password;

    @Transient
    @ApiModelProperty(value ="重复密码")
    private String rePassword;

    @ApiModelProperty(value ="岛上的水果")
    @Column(length = 100)
    private String fruit;

    @ApiModelProperty(value ="北半球-N，南半球-S")
    @Column(length = 1)
    private String hemisphere;

    @Transient
    @ApiModelProperty(value ="验证码")
    private String captcha;

}