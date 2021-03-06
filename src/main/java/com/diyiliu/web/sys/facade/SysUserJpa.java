package com.diyiliu.web.sys.facade;

import com.diyiliu.web.sys.dto.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: SysUserJpa
 * Author: DIYILIU
 * Update: 2018-05-02 22:15
 */
public interface SysUserJpa extends JpaRepository<SysUser, Long> {

    Page<SysUser> findByUsernameLikeOrNameLike(String like1, String like2, Pageable pageable);

    SysUser findByUsername(String username);

    SysUser findById(long id);

    void deleteByIdIn(List<Long> ids);
}
