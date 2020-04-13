package com.vincenttho.service.transaction;

import com.vincenttho.common.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @className:com.vincenttho.service.topic.TopicDO
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/7     VincentHo       v1.0.0        create
 */
@Entity
@Table(name="t_transaction_turnip")
@Data
@DynamicUpdate
@DynamicInsert
@ApiModel("大头菜交易帖子对象")
public class TransactionTurnipDO extends BaseDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty("主键id")
    private Integer id;

    /** 交易类型（BUY-买入，SALE-卖出） */
    @Column(length = 30)
    @ApiModelProperty("交易类型（BUY-买入，SALE-卖出）")
    private String transactionType;

    /** 金额（如果是买入则表示买入金额，卖出则表示卖出金额） */
    @Column
    @ApiModelProperty("金额（如果是买入则表示买入金额，卖出则表示卖出金额）")
    private Integer amount;

    /** 交易物品 */
    @Column(length = 255)
    @ApiModelProperty("交易提供物品")
    private String tradingItems;

    /** 是否交易中 */
    @Column(columnDefinition = "tinyint(1) default 0")
    @ApiModelProperty("是否交易中")
    private boolean inTransaction;

    @Column
    @ApiModelProperty("当前交易人id")
    private Integer transactionUserId;

    @Column(length = 255)
    @ApiModelProperty("房间密码")
    private String password;

}