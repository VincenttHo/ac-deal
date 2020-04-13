package com.vincenttho.service.transaction;

import com.vincenttho.service.user.UserInfoDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className:com.vincenttho.service.transaction.TransactionTurnipView
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/9     VincentHo       v1.0.0        create
 */
@Data
@ApiModel("交易列表信息")
public class TransactionTurnipView {

    @ApiModelProperty("交易信息")
    private TransactionTurnipDO transactionInfo;

    @ApiModelProperty("创建人信息")
    private UserInfoDO createUserInfo;

    public TransactionTurnipView(TransactionTurnipDO transactionInfo, UserInfoDO createUserInfo) {
        this.transactionInfo = transactionInfo;
        this.createUserInfo = createUserInfo;
    }

}