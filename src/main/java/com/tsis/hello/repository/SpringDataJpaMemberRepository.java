package com.tsis.hello.repository;

import com.tsis.hello.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);

    // 10.6.5. 참고 : 
    // 실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적쿼리는 'Querydsl'이라는 라이브러리를 사용하면 된다.'Querydsl'을 사용하면 쿼리도 자바 코드로 안전하게 작성 가능하며, 동적 쿼리도 편리하게 작성할 수 있다.이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 JdbcTemplate을 사용하면 된다. ( 스프링 데이터 JPA와 섞어서 사용이 가능하다. )
    // 출처: https://montoo.tistory.com/entry/스프링부트-InteliJ로-SpringBoot-빌드하기-Gradle [Keon.:티스토리]
}
