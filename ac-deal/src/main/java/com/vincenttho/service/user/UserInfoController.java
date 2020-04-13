package com.vincenttho.service.user;

import com.vincenttho.common.model.RestfulApiConstants;
import com.vincenttho.common.model.ResultBean;
import com.vincenttho.utils.AvatarUtil;
import com.vincenttho.utils.UpdateUtil;
import com.vincenttho.utils.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @className:com.vincenttho.service.user.UserInfoController
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/9     VincentHo       v1.0.0        create
 */
@RestController
@RequestMapping(RestfulApiConstants.URL_PREFFIX + "/user")
@Api(tags = "用户接口")
public class UserInfoController {

    @Autowired
    private UserInfoDao userInfoDao;

    @GetMapping("/myInfo")
    @ApiOperation("查询当前登录用户的用户信息")
    ResultBean<UserInfoDO> getCurrentUser() {
        Integer currentUserId = UserInfoUtil.getCurrentUserId();
        UserInfoDO currentUser = userInfoDao.findByUserId(currentUserId);
        currentUser.setPassword(null);
        return new ResultBean<>(currentUser);
    }

    @GetMapping("/{userId}")
    @ApiOperation("根据用户id获取用户信息")
    ResultBean<UserInfoDO> getUserInfo(@PathVariable Integer userId) {
        UserInfoDO userInfo = userInfoDao.findByUserId(userId);
        userInfo.setPassword(null);
        return new ResultBean<>(userInfo);
    }

    @GetMapping("/avatar")
    @ApiOperation("获取头像列表")
    ResultBean<List<String>> getAvatarList() {
        return new ResultBean<>(AvatarUtil.listAvatars());
    }

    @PatchMapping("/myInfo")
    @ApiOperation("修改本人的信息")
    ResultBean updateUserInfo(@RequestBody UserInfoDO updateInfo) {

        updateInfo.setPassword(null);
        updateInfo.setEmail(null);
        updateInfo.setCreateUserId(null);
        updateInfo.setCreateUserName(null);
        updateInfo.setCreateDate(null);

        UserInfoDO userInfoDO = userInfoDao.findByUserId(UserInfoUtil.getCurrentUserId());
        BeanUtils.copyProperties(updateInfo, userInfoDO, UpdateUtil.getNullPropertyNames(updateInfo));
        userInfoDO.setUpdateDate(new Date());
        userInfoDO.setUpdateUserId(UserInfoUtil.getCurrentUserId());
        userInfoDO.setUpdateUserName(UserInfoUtil.getCurrentUserName());
        userInfoDao.save(userInfoDO);
        return new ResultBean(true, "修改成功");

    }


    @PatchMapping("/password")
    @ApiOperation("修改密码")
    ResultBean updatePassword(@RequestBody UserInfoDO updateInfo) {
        String password = updateInfo.getPassword();
        String rePassword = updateInfo.getRePassword();
        if(password == null) {
            throw new RuntimeException("登录密码不能为空！");
        }
        if(rePassword == null) {
            throw new RuntimeException("重复密码不能为空！");
        }
        if(!password.equals(rePassword)) {
            throw new RuntimeException("密码不一致");
        }

        UserInfoDO userInfoDO = userInfoDao.findByUserId(UserInfoUtil.getCurrentUserId());
        userInfoDO.setUpdateUserId(UserInfoUtil.getCurrentUserId());
        userInfoDO.setUpdateUserName(UserInfoUtil.getCurrentUserName());
        userInfoDO.setUpdateDate(new Date());
        userInfoDO.setPassword(password);
        userInfoDao.save(userInfoDO);
        return new ResultBean(true, "修改密码成功");
    }

}