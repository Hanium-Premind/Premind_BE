package com.example.Premind_BE.domain.resume.service;

import com.example.Premind_BE.domain.resume.dao.ResumeQuestionRedisRepository;
import com.example.Premind_BE.domain.resume.domain.ResumeQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RedisDataInitializer implements CommandLineRunner {

    private final ResumeQuestionRedisRepository redisRepository;

    @Override
    public void run(String... args) {
        List<String> questions = List.of(
                "본인의 성장과정에 대해 설명하세요",
                "지원 동기를 작성하세요",
                "본인 성격의 장단점을 설명하세요",
                "도전 및 문제해결 경험을 설명하세요",
                "협업 또는 팀워크 경험에 대해 설명하세요",
                "가장 성취감을 느꼈던 경험을 설명하세요",
                "본인의 단기 및 장기 목표를 설명하세요",
                "지원 직무를 선택한 이유와 준비 과정을 설명하세요",
                "실패 경험과 그 극복 과정을 설명하세요",
                "입사 후 포부에 대해 설명하세요",
                "스트레스를 어떻게 관리하는지 설명하세요",
                "리더십을 발휘한 경험에 대해 설명하세요",
                "자신의 가치관 또는 신념에 대해 설명하세요",
                "타인과의 갈등을 해결한 경험을 설명하세요",
                "해당 기업에 지원한 이유와 기업에 대해 조사한 내용을 포함해 설명하세요"
        );

        long id = 1;
        for (String q : questions) {
            ResumeQuestion rq = ResumeQuestion.builder()
                    .id(id++)
                    .question(q)
                    .build();
            redisRepository.save(rq);
        }
    }
}

