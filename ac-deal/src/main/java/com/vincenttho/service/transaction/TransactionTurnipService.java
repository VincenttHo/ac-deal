package com.vincenttho.service.transaction;

import com.vincenttho.common.model.PageCondition;
import com.vincenttho.common.model.PageResultBean;
import com.vincenttho.common.model.SysUser;
import com.vincenttho.utils.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @className:com.vincenttho.service.transaction.TransactionTurnipService
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/10     VincentHo       v1.0.0        create
 */
@Service
public class TransactionTurnipService {

    @Autowired
    private TransactionTurnipDao transactionTurnipDao;

    @Autowired
    private TransactionUserHisDao transactionUserHisDao;

    /**
    * 分页获取帖子列表
    * @param condition
    * @return com.vincenttho.common.model.PageResultBean<java.util.List<com.vincenttho.service.transaction.TransactionTurnipView>>
    * @author: VincentHo
    * @date 2020/4/10
    */
    public PageResultBean<List<TransactionTurnipView>> listPages(TransactionTurnipCondition condition) {

        Page<TransactionTurnipView> page =
                transactionTurnipDao.findPage(
                        condition.getBeginAmount(), condition.getEndAmount(),
                        condition.getTradingItems(),
                        condition.getTransactionType(),
                        condition.getPageable());
        PageResultBean<List<TransactionTurnipView>> result = new PageResultBean<>();
        List<TransactionTurnipView> contents = page.getContent();
        for(TransactionTurnipView content : contents) {
            content.getTransactionInfo().setPassword(null);
            content.getCreateUserInfo().setPassword(null);
        }
        result.setResult(true);
        result.setData(contents);
        result.setCurrentPage(condition.getCurrentPage());
        result.setPageSize(condition.getPageSize());
        result.setTotalPages(page.getTotalPages());
        result.setTotalElements(page.getTotalElements());

        return result;
    }

    /**
    * 进入帖子
    * @param id
    * @return com.vincenttho.service.transaction.TransactionTurnipDO
    * @author: VincentHo
    * @date 2020/4/10
    */
    public TransactionTurnipDO getTransactionTurnip(Integer id) {

        synchronized (id) {
            SysUser currentUser = UserInfoUtil.getCurrentUser();
            TransactionTurnipDO transactionTurnipDO =
                    transactionTurnipDao.findByIdAndIsAvailable(id, true);
            if(currentUser.getUserId() != transactionTurnipDO.getCreateUserId()) {
                // 判断是否有正在交易的帖子
                TransactionTurnipCondition condition = new TransactionTurnipCondition();
                condition.setInTransaction(true);
                condition.setTransactionUserId(currentUser.getUserId());
                List<TransactionTurnipDO> list = this.transactionTurnipDao.findFilter(condition);
                if(!CollectionUtils.isEmpty(list)) {
                    TransactionTurnipDO checkTransaction = list.get(0);
                    if(!checkTransaction.getId().equals(id)) {
                        throw new RuntimeException("您当前有正在交易的帖子，不能进入其他帖子！");
                    }
                }
                // 判断这个帖子是否正在与其他人交易
                if(transactionTurnipDO.isInTransaction() &&
                        currentUser.getUserId() != transactionTurnipDO.getTransactionUserId()) {
                    throw new RuntimeException("你慢了一步，该房间已经正在交易，请稍后或寻找其他帖子哦");
                }
                // 修改交易状态
                transactionTurnipDO.setInTransaction(true);
                transactionTurnipDO.setTransactionUserId(currentUser.getUserId());
                transactionTurnipDO.setUpdateDate(new Date());
                transactionTurnipDO.setUpdateUserId(currentUser.getUserId());
                transactionTurnipDO.setUpdateUserName(currentUser.getUserName());
                transactionTurnipDao.save(transactionTurnipDO);
                // 增加交易历史人信息
                TransactionUserHisDO transactionUserHisDO = new TransactionUserHisDO();
                transactionUserHisDO.setTransactionId(id);
                transactionUserHisDO.setUserId(UserInfoUtil.getCurrentUserId());
                transactionUserHisDO.setCreateUserId(UserInfoUtil.getCurrentUserId());
                transactionUserHisDO.setCreateUserName(UserInfoUtil.getCurrentUserName());
                transactionUserHisDO.setUpdateUserId(UserInfoUtil.getCurrentUserId());
                transactionUserHisDO.setUpdateUserName(UserInfoUtil.getCurrentUserName());
                transactionUserHisDao.save(transactionUserHisDO);
            }
            return transactionTurnipDO;
        }
    }

    /**
    * 获取登陆人发布的帖子
    * @param condition
    * @return com.vincenttho.common.model.PageResultBean<java.util.List<com.vincenttho.service.transaction.TransactionTurnipDO>>
    * @author: VincentHo
    * @date 2020/4/10
    */
    public PageResultBean<List<TransactionTurnipDO>> listMyTransactionTurnip(PageCondition condition) {

        SysUser currentUser = UserInfoUtil.getCurrentUser();

        Page<TransactionTurnipDO> page =
                transactionTurnipDao.findByCreateUserId(currentUser.getUserId(), condition.getPageable());
        PageResultBean<List<TransactionTurnipDO>> result = new PageResultBean<>();
        result.setResult(true);
        result.setData(page.getContent());
        result.setCurrentPage(condition.getCurrentPage());
        result.setPageSize(condition.getPageSize());
        result.setTotalPages(page.getTotalPages());
        result.setTotalElements(page.getTotalElements());

        return result;

    }

    /**
     * 发布帖子
     * @param transactionTurnipDO
     * @return com.vincenttho.common.model.PageResultBean<java.util.List<com.vincenttho.service.transaction.TransactionTurnipDO>>
     * @author: VincentHo
     * @date 2020/4/10
     */
    public void createTransaction(TransactionTurnipDO transactionTurnipDO) {
        transactionTurnipDO.setInTransaction(false);
        transactionTurnipDO.setCreateUserId(UserInfoUtil.getCurrentUserId());
        transactionTurnipDO.setCreateUserName(UserInfoUtil.getCurrentUserName());
        transactionTurnipDao.save(transactionTurnipDO);
    }

    /**
    * 下一笔交易
    * @param id
    * @return com.vincenttho.service.transaction.TransactionTurnipDO
    * @author: VincentHo
    * @date 2020/4/10
    */
    public TransactionTurnipDO nextTransaction(@PathVariable("id") Integer id) {

        TransactionTurnipDO transactionTurnipDO =
                transactionTurnipDao.findByIdAndIsAvailable(id, true);

        Integer createUserId = transactionTurnipDO.getCreateUserId();
        SysUser currentUser = UserInfoUtil.getCurrentUser();
        if(currentUser.getUserId() != createUserId
                && !transactionTurnipDO.getTransactionUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("您非创建人，无权修改帖子");
        }

        transactionTurnipDO.setUpdateUserId(currentUser.getUserId());
        transactionTurnipDO.setUpdateUserName(currentUser.getUserName());
        transactionTurnipDO.setUpdateDate(new Date());
        transactionTurnipDO.setInTransaction(false);
        transactionTurnipDO.setTransactionUserId(null);

        transactionTurnipDao.save(transactionTurnipDO);
        return transactionTurnipDO;

    }

    /**
    * 删除帖子
    * @param id
    * @return void
    * @author: VincentHo
    * @date 2020/4/10
    */
    public void delete(Integer id) {
        TransactionTurnipDO transactionTurnipDO = transactionTurnipDao.findByIdAndIsAvailable(id, true);
        transactionTurnipDO.setIsAvailable(false);
        SysUser currentUser = UserInfoUtil.getCurrentUser();
        transactionTurnipDO.setUpdateUserId(currentUser.getUserId());
        transactionTurnipDO.setUpdateUserName(currentUser.getUserName());
        transactionTurnipDO.setUpdateDate(new Date());
        transactionTurnipDao.save(transactionTurnipDO);
    }

}