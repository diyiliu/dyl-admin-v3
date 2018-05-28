package com.diyiliu.web.grain.facade;

import com.diyiliu.web.grain.dto.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Description: StockJpa
 * Author: DIYILIU
 * Update: 2018-05-21 21:33
 */
public interface StockJpa extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {

    Stock findById(long id);

    void deleteByIdIn(List<Long> ids);

    @Query(value = "select sum(suttle), sum(money) from grain_stock", nativeQuery = true)
    List selectSum();
}
