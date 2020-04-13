package com.vincenttho.task;

import com.vincenttho.service.transaction.TransactionTurnipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearTransactionTask {

    @Autowired
    private TransactionTurnipDao transactionTurnipDao;

    @Scheduled(cron="0 2 12 * * ?")
    private void clearSaleAm(){
        transactionTurnipDao.clearSaleAm();
    }

    @Scheduled(cron="0 2 22 * * ?")
    private void clearSalePm(){
        transactionTurnipDao.clearSalePm();
    }

    @Scheduled(cron="0 2 12 * * ?")
    private void clearBuyAm(){
        transactionTurnipDao.clearBuyAm();
    }

}