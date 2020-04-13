package com.vincenttho.service.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className:com.vincenttho.service.login.SendCaptchaDTO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/13     VincentHo       v1.0.0        create
 */
@Data
@ApiModel("发送验证码请求对象")
public class SendCaptchaDTO {

    @ApiModelProperty("邮箱地址")
    private String email;

}