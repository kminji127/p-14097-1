package com.back;

import org.springframework.stereotype.Service;

// 패키지 내 @Component, @Service, @Repository, @Controller 스캔 -> 자동으로 Bean으로 등록
@Service
public class PersonService {
    public long count() {
        return 3;
    }
}
