package com.diyiliu.web.account.facade;

import com.diyiliu.web.account.dto.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: SysUserJpa
 * Author: DIYILIU
 * Update: 2018-05-02 22:15
 */
public interface SysUserJpa extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);
}
