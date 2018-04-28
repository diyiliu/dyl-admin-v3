package com.diyiliu.web.home.facade;

import com.diyiliu.web.home.dto.SysAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: SysAssetJpa
 * Author: DIYILIU
 * Update: 2018-04-28 14:23
 */

public interface SysAssetJpa extends JpaRepository<SysAsset, Long> {

    List<SysAsset> findByIsMenuOrderByPidAscSortAsc(int isMenu);
}
