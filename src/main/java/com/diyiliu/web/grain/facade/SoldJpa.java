package com.diyiliu.web.grain.facade;

import com.diyiliu.web.grain.dto.Sold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Description: SoldJpa
 * Author: DIYILIU
 * Update: 2018-05-25 22:27
 */
public interface SoldJpa extends JpaRepository<Sold, Long>, JpaSpecificationExecutor<Sold> {

    Sold findById(long id);

    void deleteByIdIn(List<Long> ids);
}
