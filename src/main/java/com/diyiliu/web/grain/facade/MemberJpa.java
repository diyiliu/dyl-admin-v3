package com.diyiliu.web.grain.facade;

import com.diyiliu.web.grain.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: MemberJpa
 * Author: DIYILIU
 * Update: 2018-05-21 21:32
 */
public interface MemberJpa extends JpaRepository<Member, Long> {

}
