package com.vincenttho.service.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vincenttho.service.user.UserInfoDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @className:com.vincent.service.login.TokenDTO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2019/10/18     VincentHo       v1.0.0        create
 */
@Data
@ApiModel("登录返回参数")
public class TokenDTO {

    @ApiModelProperty(value ="用户信息")
    private UserInfoDO userInfo;

    @ApiModelProperty(value ="token")
    private String token;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @ApiModelProperty(value ="失效时间")
    private Date expirationDate;

}