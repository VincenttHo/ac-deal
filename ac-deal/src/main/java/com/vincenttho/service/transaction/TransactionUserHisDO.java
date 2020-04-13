package com.vincenttho.service.transaction;

import com.vincenttho.common.model.BaseDO;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @className:com.vincenttho.service.transaction.TransactionUserHisDO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/11     VincentHo       v1.0.0        create
 */
@Entity
@Table(name="t_transaction_user_his")
@Data
@DynamicUpdate
@DynamicInsert
public class TransactionUserHisDO extends BaseDO {

    /**
     * 主键id
    */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 交易类型（TURNIP-大头菜，ITEM-换物）
     */
    @Column(length = 30)
    private String transactionType;

    /**
     * 交易id
     */
    @Column
    private Integer transactionId;

    /**
     * 用户id
     */
    @Column
    private Integer userId;

}