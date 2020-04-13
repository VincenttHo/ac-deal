package com.vincenttho.service.user;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @className:com.vincenttho.service.user.CaptchaDO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/13     VincentHo       v1.0.0        create
 */
@Entity
@Table(name="t_captcha")
@Data
@DynamicUpdate
@DynamicInsert
public class CaptchaDO implements Serializable {

    @Id
    private String email;

    @Column(length = 6)
    private String captcha;

    @Column
    private Date expiry;

    @Column
    private Date createDate;

}