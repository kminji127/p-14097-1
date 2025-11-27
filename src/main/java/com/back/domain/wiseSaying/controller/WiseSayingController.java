package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WiseSayingController {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    @GetMapping("/wiseSayings/write")
    @ResponseBody
    public String write(String content, String author) { // Spring MVC에서 기본적으로 같은 변수명이면 Query Parameter를 자동으로 메서드 파라미터에 매핑
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용은 null이나 빈 칸일 수 없습니다.");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("작가는 null이나 빈 칸일 수 없습니다.");
        }

        int id = ++lastId;
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        wiseSayings.add(wiseSaying);

        return "%d번 명언 생성 완료".formatted(id);
    }

    @GetMapping("/wiseSayings")
    @ResponseBody
    public String getList() {
        return "<ul>" +
                wiseSayings.stream()
                        .map(wiseSaying -> "<li>%d / %s / %s</li>".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()))
                        .collect(Collectors.joining(""))
                + "</ul>";
    }
}
