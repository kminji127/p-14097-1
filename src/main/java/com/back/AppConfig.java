package com.back;

import com.back.domain.member.entity.Member;
import com.back.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    // 프록시 예제 1
//    private final AppConfig self;
//
//    public AppConfig(@Lazy AppConfig appConfig) {
//        this.self = appConfig;
//    }

    // 프록시 개선 코드
    @Autowired // 필드 주입
    @Lazy // 가짜 객체(프록시 객체)
    private AppConfig self;

    private final MemberService memberService;

    @Bean
    int version() {
        return 55;
    }

    @Bean
    @Order(2)
    public ApplicationRunner myApplicationRunner() {
        // 함수
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                System.out.println("myApplicationRunner 실행 중");
            }
        };
    }

    @Bean
    @Order(1)
    public ApplicationRunner myApplicationRunner2() {
        // 익명 함수(람다)
        return args -> System.out.println("myApplicationRunner2 실행 중");
    }

    @Bean
    @Order(3)
    public ApplicationRunner baseInitDataApplicationRunner() {
        // 익명 함수(람다)
        return args -> {
            self.work1(); // self(프록시)를 통한 간접 호출 : 트랜잭션 작동 O
//            this.work2(); // this를 통한 직접 호출 : 트랜잭션 작동 X - 같은 객체 내의 메서드를 내부호출 할 때는 @Transactional 이 작동하지 않는다.
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        System.out.println("work1");
        if (memberService.count() > 0) return;
        Member memberSystem = memberService.join("system", "1234", "시스템");
        Member memberAdmin = memberService.join("admin", "1234", "관리자");
        Member memberUser1 = memberService.join("user1", "1234", "유저1");
        Member memberUser2 = memberService.join("user2", "1234", "유저2");
        Member memberUser3 = memberService.join("user3", "1234", "유저3");
    }

    @Transactional
    public void work2() {
        System.out.println("work2");
        Member memberUser2 = memberService.findByUsername("user1").get();
        memberUser2.setNickname("유저1 new");
    }
}
