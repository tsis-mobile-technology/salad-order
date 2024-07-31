package com.tsis.hello;

import com.tsis.hello.repository.JdbcMemberRepository;
import com.tsis.hello.repository.MemberRepository;
import com.tsis.hello.repository.MemoryMemberRepository;
import com.tsis.hello.service.MemberService;
import com.tsis.hello.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
