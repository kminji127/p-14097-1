package com.back;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class AppConfig {
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
            work1();
            work2();
        };
    }

    private void work1() {
        System.out.println("work1");
    }

    private void work2() {
        System.out.println("work2");
    }
}
