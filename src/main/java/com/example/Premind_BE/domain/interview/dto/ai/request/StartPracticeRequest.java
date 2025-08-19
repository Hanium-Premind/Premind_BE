package com.example.Premind_BE.domain.interview.dto.ai.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

// 연습모드에서 첫번째 질문을 생성하기 위한 요청
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartPracticeRequest {
    private List<StartPracticeItem> items;

    // "상냥함", "엄격함" 등 명세서의 문자열 그대로 보낼 수 있게 String 유지
    private String style;

    private Integer num_questions;

    @Builder.Default
    private Boolean even_split = true;

    private String file_name;

    private String role_name;

    private String company_name;

    @Builder.Default
    private String model = "gpt-4o";

    @Builder.Default
    private Double temperature = 0.5;

    @Builder.Default
    private Double top_p = 0.9;
}
