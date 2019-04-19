package com.xmcc.repository;

import com.xmcc.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {



    @Query(value = "SELECT * FROM order_master WHERE buyer_openid = ?1",
            countQuery = "SELECT count(*) FROM order_master WHERE buyer_openid = ?1",
            nativeQuery = true)
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

    OrderMaster findByOrderIdAndBuyerOpenid(String orderId,String buyerOpenid);
}
