package com.vincenttho.service.transaction;

import com.vincenttho.common.model.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className:com.vincenttho.service.transaction.TransactionTurnipCondition
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/8     VincentHo       v1.0.0        create
 */
@Data
@ApiModel("列表查询入参")
public class TransactionTurnipCondition extends PageCondition {

    @ApiModelProperty("价格区间最小价格")
    private Integer beginAmount;

    @ApiModelProperty("价格区间最大价格")
    private Integer endAmount;

    @ApiModelProperty("交易提供物品")
    private String tradingItems;

    @ApiModelProperty("【必填】交易类型，BUY-买入大头菜，SALE-卖出大头菜")
    private String transactionType;

    @ApiModelProperty("是否在交易中")
    private Boolean inTransaction;

    @ApiModelProperty("交易人id")
    private Integer transactionUserId;

    @ApiModelProperty("创建人id")
    private Integer createUserId;

}