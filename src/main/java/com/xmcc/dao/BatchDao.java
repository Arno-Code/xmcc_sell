package com.xmcc.dao;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 执行批量操作
 */

public interface BatchDao<T> {


    @Transactional
    void batchInsert(List<T> list);
}
