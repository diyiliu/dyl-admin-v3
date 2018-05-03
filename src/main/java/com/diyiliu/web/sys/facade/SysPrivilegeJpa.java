package com.diyiliu.web.sys.facade;

import com.diyiliu.web.sys.dto.SysPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Description: SysPrivilegeJpa
 * Author: DIYILIU
 * Update: 2018-05-03 13:20
 */
public interface SysPrivilegeJpa extends JpaRepository<SysPrivilege, Long> {

    List<SysPrivilege> findByMasterAndMasterValueIn(String master, Set set);
}
