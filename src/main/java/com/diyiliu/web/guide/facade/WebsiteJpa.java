package com.diyiliu.web.guide.facade;

import com.diyiliu.web.guide.dto.Website;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: WebsiteJpa
 * Author: DIYILIU
 * Update: 2018-05-15 21:18
 */
public interface WebsiteJpa extends JpaRepository<Website, Long> {

    Page<Website> findByNameLikeOrUrlLike(String like1, String like2, Pageable pageable);

    Website findById(long id);

    void deleteByIdIn(List<Long> ids);
}
