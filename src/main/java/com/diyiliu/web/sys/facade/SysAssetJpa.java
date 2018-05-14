package com.diyiliu.web.sys.facade;

import com.diyiliu.web.sys.dto.SysAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Description: SysAssetJpa
 * Author: DIYILIU
 * Update: 2018-04-28 14:23
 */

public interface SysAssetJpa extends JpaRepository<SysAsset, Long> {

    // List<SysAsset> findByIsMenuOrderByPidAscSortAsc(int isMenu);

    List<SysAsset> findByIsMenuAndIdInOrderByPidAscSortAsc(int isMenu, Set ids);

    List<SysAsset> findByIdIn(Set ids);

    SysAsset findById(long id);

    SysAsset findByCode(String code);

    SysAsset findByController(String controller);

    void deleteByIdIn(List<Long> ids);
}
