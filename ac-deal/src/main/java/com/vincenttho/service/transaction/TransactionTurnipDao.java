package com.vincenttho.service.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public interface TransactionTurnipDao extends JpaRepository<TransactionTurnipDO, Integer> {

    Page<TransactionTurnipDO> findByAmountBetween(int beginAmount, int endAmount, Pageable pageable);

    @Query(value = "select new com.vincenttho.service.transaction.TransactionTurnipView(t,u) " +
            "from TransactionTurnipDO t, com.vincenttho.service.user.UserInfoDO u " +
            "where t.createUserId = u.userId " +
            "and (:beginAmount is null or t.amount >= :beginAmount) " +
            "and (:endAmount is null or t.amount <= :endAmount) " +
            "and (:tradingItems is null or t.tradingItems like %:tradingItems%) " +
            "and (:transactionType is null or t.transactionType = :transactionType) " +
            "and t.isAvailable = 1 " +
            "and t.inTransaction = 0")
    Page<TransactionTurnipView> findPage(@Param("beginAmount") Integer beginAmount,
                                         @Param("endAmount") Integer endAmount,
                                         @Param("tradingItems") String tradingItems,
                                         @Param("transactionType") String transactionType,
                                         Pageable pageable);

    @Query("select t from TransactionTurnipDO t " +
            "where t.isAvailable = true " +
            "and (:#{#condition.tradingItems} is null or t.tradingItems = :#{#condition.tradingItems})" +
            "and (:#{#condition.transactionType} is null or t.transactionType = :#{#condition.transactionType})" +
            "and (:#{#condition.inTransaction} is null or t.inTransaction = :#{#condition.inTransaction})" +
            "and (:#{#condition.transactionUserId} is null or t.transactionUserId = :#{#condition.transactionUserId})" +
            "and (:#{#condition.createUserId} is null or t.createUserId = :#{#condition.createUserId})" +
            "")
    List<TransactionTurnipDO> findFilter(
            @Param("condition") TransactionTurnipCondition condition);

    Page<TransactionTurnipDO> findByCreateUserId(int createUserId, Pageable pageable);

    TransactionTurnipDO findByIdAndIsAvailable(Integer id, boolean isAvailable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_transaction_turnip " +
            "set is_available = 0 " +
            "where transaction_type = 'SALE' and " +
            "create_date < STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'), ' 12:00:00'), '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
    int clearSaleAm();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_transaction_turnip " +
            "set is_available = 0 " +
            "where transaction_type = 'SALE' and " +
            "create_date < STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'), ' 22:00:00'), '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
    int clearSalePm();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_transaction_turnip " +
            "set is_available = 0 " +
            "where transaction_type = 'BUY' and " +
            "create_date < STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'), ' 12:00:00'), '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
    int clearBuyAm();

}