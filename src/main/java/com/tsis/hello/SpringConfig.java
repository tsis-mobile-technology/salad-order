package com.tsis.hello;

import com.tsis.hello.repository.MemberRepository;
import com.tsis.hello.repository.MemoryMemberRepository;
import com.tsis.hello.service.MemberService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
