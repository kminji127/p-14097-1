package com.back;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // 스프링 부트 실행 시 @Bean 메서드들을 1번씩 실행 => 객체 => 보관 => 공유
    @Bean
    PersonService personService() {
        System.out.println("postService 빈 생성됨");
        return new PersonService();
    }
}
