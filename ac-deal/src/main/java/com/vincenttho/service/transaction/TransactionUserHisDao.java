package com.vincenttho.service.transaction;

import com.vincenttho.service.user.UserInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
public interface TransactionUserHisDao extends JpaRepository<TransactionUserHisDO, Integer> {

    @Query("select u " +
            "from TransactionUserHisDO t, com.vincenttho.service.user.UserInfoDO u " +
            "where u.userId = t.userId " +
            "and t.transactionId = :transactionId " +
            "and t.transactionType = :transactionType " +
            "and t.isAvailable = true")
    List<UserInfoDO> findTransactionUserHis(
            @Param("transactionType") String transactionType,
            @Param("transactionId") Integer transactionId);

}