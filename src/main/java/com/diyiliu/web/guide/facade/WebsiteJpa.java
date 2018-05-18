package com.diyiliu.web.guide.facade;

import com.diyiliu.web.guide.dto.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Description: WebsiteJpa
 * Author: DIYILIU
 * Update: 2018-05-15 21:18
 */
public interface WebsiteJpa extends JpaRepository<Website, Long>, JpaSpecificationExecutor<Website> {

    Website findById(long id);

    List<Website> findByIdIn(List<Long> ids);
}
