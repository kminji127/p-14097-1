package com.back;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 패키지 내 @Component, @Service, @Repository, @Controller 스캔 -> 자동으로 Bean으로 등록
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional // 트랜잭션 관리를 위해 프록시 생성
    public long count() {
        return personRepository.count();
    }
}
