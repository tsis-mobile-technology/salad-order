package com.tsis.hello;

import com.tsis.hello.repository.AdminRepository;
import com.tsis.hello.repository.JdbcMemberRepository;
import com.tsis.hello.repository.JdbcTemplateMemberRepository;
import com.tsis.hello.repository.JpaMemberRepository;
import com.tsis.hello.repository.MemberRepository;
import com.tsis.hello.repository.MemoryMemberRepository;
import com.tsis.hello.repository.TbFoodOrderInfoRepository;
import com.tsis.hello.repository.TbFoodOrderHistRepository;
import com.tsis.hello.service.AdminService;
import com.tsis.hello.service.MemberService;
import com.tsis.hello.service.TbFoodOrderInfoService;
import com.tsis.hello.service.TbFoodOrderHistService;

import jakarta.persistence.EntityManager;

import com.tsis.hello.aop.TimeTraceAop;
import com.tsis.hello.domain.Admin;
import com.tsis.hello.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Configuration
public class SpringConfig {

    // SpringDataJPA case
    private final MemberRepository memberRepository;
    private final TbFoodOrderInfoRepository tbFoodOrderInfoRepository;
    private final TbFoodOrderHistRepository tbFoodOrderHistRepository;
    private final AdminRepository adminRepository;

    // @Autowired

    @Autowired
    public SpringConfig(MemberRepository memberRepository, TbFoodOrderInfoRepository tbFoodOrderInfoRepository, TbFoodOrderHistRepository tbFoodOrderHistRepository, AdminRepository adminRepository) {
        this.memberRepository = memberRepository;
        this.tbFoodOrderInfoRepository = tbFoodOrderInfoRepository;
        this.tbFoodOrderHistRepository = tbFoodOrderHistRepository;
        this.adminRepository = adminRepository;
    }

    // JPA case
    // private final EntityManager entityManager;

    // @Autowired
    // public SpringConfig(EntityManager entityManager) {
    //     this.entityManager = entityManager;
    // }

    // Memory, Jdbc, JdbcTemplate case
    // private DataSource dataSource;

    // @Autowired
    // public SpringConfig(DataSource dataSource) {
    //     this.dataSource = dataSource;
    // }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    @Bean
    public TbFoodOrderInfoService tbFoodOrderInfoService() {
        return new TbFoodOrderInfoService(tbFoodOrderInfoRepository);
    }

    @Bean
    public TbFoodOrderHistService tbFoodOrderHistService() {
        return new TbFoodOrderHistService(tbFoodOrderHistRepository);
    }

    @Bean
    public AdminService adminService() {
        return new AdminService(adminRepository); 
    }

    // @Bean
    // public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        // return new JpaMemberRepository(entityManager);
    // }

    // below code spring.main.allow-circular-references 
    // @Bean
    // public TimeTraceAop timeTraceAop() {
    //     return new TimeTraceAop();
    // }
}
