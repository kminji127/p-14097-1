package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WiseSayingController {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    @GetMapping("/wiseSayings/write")
    @ResponseBody
    public String write(@RequestParam(defaultValue = "내용") String content,
                        @RequestParam(defaultValue = "작가") String author) { // Spring MVC에서 기본적으로 같은 변수명이면 Query Parameter를 자동으로 메서드 파라미터에 매핑
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

    @GetMapping("/wiseSayings/{id}")
    @ResponseBody
    public String detail(@PathVariable int id) {
        WiseSaying wiseSaying = findById(id).get();

        return """
                <h1>명언 : %s</h1>
                <div>번호 : %d</div>
                <div>작가 : %s</div>
                """.formatted(wiseSaying.getContent(), wiseSaying.getId(), wiseSaying.getAuthor());
    }

    @GetMapping("/wiseSayings/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        WiseSaying wiseSaying = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언은 존재하지 않습니다.".formatted(id)));
        wiseSayings.remove(wiseSaying);
        return "%d번 명언이 삭제되었습니다.".formatted(id);
    }

    private Optional<WiseSaying> findById(int id) {
        return wiseSayings.stream()
                .filter(ws -> ws.getId() == id)
                .findFirst();
    }

    @GetMapping("/wiseSayings/modify/{id}")
    @ResponseBody
    public String modify(@PathVariable int id,
                         @RequestParam(defaultValue = "내용") String content,
                         @RequestParam(defaultValue = "작가") String author) {
        if (content.isBlank()) {
            throw new IllegalArgumentException("내용은 null이나 빈 칸일 수 없습니다.");
        }

        if (author.isBlank()) {
            throw new IllegalArgumentException("작가는 null이나 빈 칸일 수 없습니다.");
        }

        WiseSaying wiseSaying = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언은 존재하지 않습니다.".formatted(id)));
        wiseSaying.modify(content, author);
        return "%d번 명언이 수정되었습니다.".formatted(id);
    }
}
