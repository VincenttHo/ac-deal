package com.vincenttho.service.login;

import com.vincenttho.common.model.RestfulApiConstants;
import com.vincenttho.common.model.ResultBean;
import com.vincenttho.config.model.Audience;
import com.vincenttho.service.user.CaptchaDO;
import com.vincenttho.service.user.CaptchaDao;
import com.vincenttho.service.user.UserInfoDO;
import com.vincenttho.service.user.UserInfoDao;
import com.vincenttho.utils.CheckUtil;
import com.vincenttho.utils.JwtTokenUtil;
import com.vincenttho.utils.SendEamilUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @className:com.vincent.service.login.LoginController
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2019/10/18     VincentHo       v1.0.0        create
 */
@RestController
@RequestMapping(RestfulApiConstants.URL_PREFFIX + "/auth")
@Api(tags = "权限相关接口")
public class AuthController {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private Audience audience;

    @Autowired
    private CaptchaDao captchaDao;

    private Pattern emailPattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");

    @PostMapping("/login")
    @ApiOperation("登录接口")
    ResultBean<TokenDTO> login(@RequestBody UserInfoDO userInfoDO) {

        String email = userInfoDO.getEmail();
        String password = userInfoDO.getPassword();
        if(email == null) {
            throw new RuntimeException("登录邮箱不能为空！");
        }
        if(password == null) {
            throw new RuntimeException("登录密码不能为空！");
        }

        UserInfoDO userInfo = userInfoDao.findByEmail(email);
        if(userInfo == null) {
            throw new RuntimeException("无该用户！");
        }
        if(!password.equals(userInfo.getPassword())){
            throw new RuntimeException("用户名或密码错误！");
        }

        String token = JwtTokenUtil.createJWT(userInfo.getUserId().toString(), userInfo.getUserName(), audience);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setUserInfo(userInfo);
        tokenDTO.setExpirationDate(JwtTokenUtil.getExpiration(token, audience.getBase64Secret()));

        return new ResultBean<>(tokenDTO);

    }

    @PostMapping("/sendCaptcha")
    @ApiOperation("发送验证码")
    ResultBean sendCaptcha(@RequestBody SendCaptchaDTO sendCaptchaDTO) throws GeneralSecurityException, MessagingException {

        String email = sendCaptchaDTO.getEmail();
        CheckUtil.notEmpty(email, "邮箱地址不能为空！");
        if(!emailPattern.matcher(email).matches()) {
            throw new RuntimeException("该邮箱格式错误");
        }

        int captcha = (int)((Math.random()*9+1)*100000);
        CaptchaDO captchaDO = new CaptchaDO();
        captchaDO.setEmail(email);
        captchaDO.setCaptcha(String.valueOf(captcha));
        Date expiry = new Date(System.currentTimeMillis() + 305000);
        captchaDO.setExpiry(expiry);
        captchaDO.setCreateDate(new Date());
        captchaDao.save(captchaDO);
        SendEamilUtil.sendEmail(email, "【验证码】动物之森交易系统注册",
                "您本次注册的验证码为：" + captcha + "，有效时间为5分钟，请在有效时间内注册");
        return new ResultBean(true, "调用成功");
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    @Transactional(rollbackFor = Exception.class)
    ResultBean<UserInfoDO> register(@RequestBody UserInfoDO userInfoDO) {

        Date now = new Date();

        String email = userInfoDO.getEmail();
        String password = userInfoDO.getPassword();
        String rePassword = userInfoDO.getRePassword();
        String captcha = userInfoDO.getCaptcha();
        if(email == null) {
            throw new RuntimeException("登录邮箱不能为空！");
        }
        if(!emailPattern.matcher(email).matches()) {
            throw new RuntimeException("该邮箱格式错误");
        }
        if(password == null) {
            throw new RuntimeException("登录密码不能为空！");
        }
        if(rePassword == null) {
            throw new RuntimeException("重复密码不能为空！");
        }
        if(!password.equals(rePassword)) {
            throw new RuntimeException("密码不一致");
        }

        UserInfoDO validUser = userInfoDao.findByEmail(email);
        if(validUser != null) {
            throw new RuntimeException("该邮箱已注册");
        }

        CheckUtil.notEmpty(captcha, "验证码不能为空！");

        CaptchaDO captchaDO = captchaDao.findByEmailAndCaptcha(email, captcha);
        if(captchaDO == null || captchaDO.getExpiry().compareTo(now) < 0) {
            throw new RuntimeException("验证码无效或已过期");
        }

        captchaDao.delete(captchaDO);

        return new ResultBean<>(userInfoDao.save(userInfoDO));


    }

}