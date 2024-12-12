package org.durcit.be.security.dao;

import org.durcit.be.security.domian.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
