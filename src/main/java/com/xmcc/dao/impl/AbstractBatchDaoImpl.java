package com.xmcc.dao.impl;

import com.xmcc.dao.BatchDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AbstractBatchDaoImpl<T> implements BatchDao<T> {

    @PersistenceContext
    protected EntityManager en;


    @Override
    @Transactional
    public void batchInsert(List<T> list) {
        long length = list.size();
        for (int i = 0; i < length; i++) {
            en.persist(list.get(i));
            if (i % 100 == 0 || i == length-1){//每100条执行一次写入数据库操作
                en.flush();
                en.clear();
            }
        }
    }
}
