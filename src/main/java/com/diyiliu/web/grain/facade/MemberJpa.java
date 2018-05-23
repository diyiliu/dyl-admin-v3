package com.diyiliu.web.grain.facade;

import com.diyiliu.web.grain.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: MemberJpa
 * Author: DIYILIU
 * Update: 2018-05-21 21:32
 */
public interface MemberJpa extends JpaRepository<Member, Long> {

    List<Member> findByNameLike(String name);

    Member findByNameAndTel(String name, String tel);
}
