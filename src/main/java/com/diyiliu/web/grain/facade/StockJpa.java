package com.diyiliu.web.grain.facade;

import com.diyiliu.web.grain.dto.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: StockJpa
 * Author: DIYILIU
 * Update: 2018-05-21 21:33
 */
public interface StockJpa extends JpaRepository<Stock, Long> {


}
