package com.vincenttho.service.transaction;

import com.vincenttho.common.model.PageCondition;
import com.vincenttho.common.model.PageResultBean;
import com.vincenttho.common.model.RestfulApiConstants;
import com.vincenttho.common.model.ResultBean;
import com.vincenttho.service.user.UserInfoDO;
import com.vincenttho.utils.CheckUtil;
import com.vincenttho.utils.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className:com.vincenttho.service.transaction.TransactionTurnipController
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/7     VincentHo       v1.0.0        create
 */
@RestController
@RequestMapping(RestfulApiConstants.URL_PREFFIX + "/transaction/turnip")
@Api(tags = "大头菜交易接口")
public class TransactionTurnipController {

    @Autowired
    private TransactionTurnipDao transactionTurnipDao;

    @Autowired
    private TransactionUserHisDao transactionUserHisDao;

    @Autowired
    private TransactionTurnipService transactionTurnipService;

    @GetMapping("")
    @ApiOperation("帖子列表查询")
    public PageResultBean<List<TransactionTurnipView>> listAll(
            @ModelAttribute TransactionTurnipCondition condition) {

        CheckUtil.notEmpty(condition.getTransactionType(), "交易类型不能为空");

        PageResultBean<List<TransactionTurnipView>> result =
                transactionTurnipService.listPages(condition);

        return result;

    }

    @GetMapping("/{id}")
    @ApiOperation("进入帖子")
    public ResultBean<TransactionTurnipDO> getTransactionTurnip(
            @PathVariable("id") Integer id) {

        TransactionTurnipDO transactionTurnip =
                this.transactionTurnipService.getTransactionTurnip(id);

        return new ResultBean<>(transactionTurnip);

    }

    @GetMapping("/myList")
    @ApiOperation("获取我发布的帖子")
    public PageResultBean<List<TransactionTurnipDO>> listMyTransactionTurnip(
            @ModelAttribute PageCondition condition) {

        PageResultBean<List<TransactionTurnipDO>> result =
                this.transactionTurnipService.listMyTransactionTurnip(condition);

        return result;

    }

    @PostMapping("")
    @ApiOperation("创建帖子")
    public ResultBean<TransactionTurnipDO> createTransaction(@RequestBody TransactionTurnipDO transactionTurnipDO) {

        CheckUtil.notNull(transactionTurnipDO.getAmount(), "金额不能为空");
        CheckUtil.notEmpty(transactionTurnipDO.getTransactionType(), "交易类型不能为空");
        CheckUtil.notEmpty(transactionTurnipDO.getPassword(), "密码不能为空");

        this.transactionTurnipService.createTransaction(transactionTurnipDO);

        return new ResultBean<>(transactionTurnipDO);

    }

    @PatchMapping("/nextTransaction/{id}")
    @ApiOperation("下一笔交易")
    ResultBean<TransactionTurnipDO> nextTransaction(@PathVariable("id") Integer id) {

        TransactionTurnipDO transactionTurnipDO = this.transactionTurnipService.nextTransaction(id);
        return new ResultBean<>(transactionTurnipDO);

    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除帖子")
    ResultBean delete(@PathVariable("id") Integer id) {
        this.transactionTurnipService.delete(id);
        return new ResultBean(true, "删除成功");
    }

    @GetMapping("/myTransaction")
    @ApiOperation("获取我正在交易的帖子列表")
    ResultBean<TransactionTurnipDO> listMyTransaction() {
        TransactionTurnipCondition condition = new TransactionTurnipCondition();
        condition.setTransactionUserId(UserInfoUtil.getCurrentUserId());
        condition.setInTransaction(true);
        List<TransactionTurnipDO> resultList =
                transactionTurnipDao.findFilter(condition);
        if(!CollectionUtils.isEmpty(resultList)) {
            return new ResultBean<>(resultList.get(0));
        } else {
            return new ResultBean<>(null);
        }

    }

    @GetMapping("/historyUser/{transactionId}")
    ResultBean<List<UserInfoDO>> listHistoryUser(
            @PathVariable("transactionId") Integer transactionId) {

        List<UserInfoDO> userList =
                transactionUserHisDao.findTransactionUserHis("TURNIP", transactionId);

        return new ResultBean<>(userList);

    }

}