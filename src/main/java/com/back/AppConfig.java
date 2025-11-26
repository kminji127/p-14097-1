package com.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
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
    public ApplicationRunner myApplicationRunner3() {
        // 익명 함수(람다)
        return args -> {
            self.work1();
            self.work2();
        };
    }

    private void work1() {
        System.out.println("work1");
    }

    private void work2() {
        System.out.println("work2");
    }
}
