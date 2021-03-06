package com.xmcc.service.impl;

import com.xmcc.dao.impl.AbstractBatchDaoImpl;
import com.xmcc.entity.OrderDetail;
import com.xmcc.repository.OrderDetailRepository;
import com.xmcc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private AbstractBatchDaoImpl batchDao;

    @Autowired
    private OrderDetailRepository detailRepository;

    /**
     * 批量添加OrderDetail
     * @param orderDetailList
     */
    @Override
    @Transactional
    public void batchInsert(List<OrderDetail> orderDetailList) {
        batchDao.batchInsert(orderDetailList);
    }

    @Override
    public List<OrderDetail> findDetailByOrderId (String orderId){
        List<OrderDetail> details = detailRepository.findByOrderId(orderId);
        return details;
    }
}
