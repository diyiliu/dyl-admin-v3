package com.diyiliu.web.operate.facade;

import com.diyiliu.web.operate.dto.Deploy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: DeployJpa
 * Author: DIYILIU
 * Update: 2018-05-26 11:31
 */
public interface DeployJpa extends JpaRepository<Deploy, Long> {

    Page<Deploy> findByAddressLikeOrPlatformLikeOrTagLike(String like1, String like2, String like13, Pageable pageable);

    Deploy findById(long id);

    void deleteByIdIn(List<Long> ids);
}
