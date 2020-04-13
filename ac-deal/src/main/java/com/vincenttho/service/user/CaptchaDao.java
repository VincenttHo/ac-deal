package com.vincenttho.service.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @className:com.vincenttho.service.user.UserDao
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/7     VincentHo       v1.0.0        create
 */
@Repository
public interface CaptchaDao extends JpaRepository<CaptchaDO, String> {

    CaptchaDO findByEmailAndCaptcha(String email, String captcha);

}