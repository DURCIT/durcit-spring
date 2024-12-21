package org.durcit.be.security.service;

import lombok.RequiredArgsConstructor;
import org.durcit.be.security.domian.Member;
import org.durcit.be.security.domian.MemberDetails;
import org.durcit.be.security.repository.MemberRepository;
import org.durcit.be.system.exception.ExceptionMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.MEMBER_NOT_FOUND_ERROR));

        return MemberDetails.from(member);
    }
}