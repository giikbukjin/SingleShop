package com.elice.team4.singleShop.order.repository;

import com.elice.team4.singleShop.order.entity.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
}