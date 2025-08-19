package com.example.Premind_BE.domain.interview.dto.ai.request;


import lombok.*;

// 연습모드에서 첫번째 질문을 생성하기 위한 요청중 자소서 질문-답변을 담는 요청
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartPracticeItem {
    private String title;
    private String content;
}
