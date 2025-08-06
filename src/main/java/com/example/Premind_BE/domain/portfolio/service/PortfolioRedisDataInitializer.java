package com.example.Premind_BE.domain.portfolio.service;

import com.example.Premind_BE.domain.portfolio.dao.PortfolioQuestionRedisRepository;
import com.example.Premind_BE.domain.portfolio.domain.PortfolioQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PortfolioRedisDataInitializer implements CommandLineRunner {

    private final PortfolioQuestionRedisRepository redisRepository;

    @Override
    public void run(String... args) {
        List<String> questions = List.of(
                "이번 프로젝트의 주제와 핵심 목표는 무엇인가요?",
                "해당 프로젝트를 하게 된 동기나 계기는 무엇인가요?",
                "본인의 포지션과 주요 역할은 무엇이었나요?",
                "팀 구성과 협업 방식에 대해 설명해주세요.",
                "프로젝트 기간과 일정 관리는 어떻게 이루어졌나요?",
                "프로젝트 중 예상치 못한 문제는 무엇이었고, 어떻게 해결하셨나요?",
                "이 프로젝트에서 사용한 주요 기술 또는 도구는 무엇인가요?",
                "구현 중 가장 어려웠던 기능은 무엇이고, 어떻게 해결했나요?",
                "이 프로젝트에서 본인이 가장 자랑스럽게 생각하는 부분은 무엇인가요?",
                "프로젝트의 결과물은 어떤 방식으로 평가되었나요?",
                "사용자나 고객의 피드백을 받은 경험이 있다면 소개해주세요.",
                "이 프로젝트를 통해 성장한 부분이나 배운 점은 무엇인가요?",
                "프로젝트를 다시 진행한다면 어떤 점을 개선하고 싶나요?",
                "해당 프로젝트를 통해 자신이 추구하는 직무와 어떻게 연결된다고 생각하나요?",
                "이 경험을 통해 본인의 장점이 어떻게 드러났다고 생각하시나요?"
        );

        long id = 1;
        for (String q : questions) {
            PortfolioQuestion rq = PortfolioQuestion.builder()
                    .id(id++)
                    .question(q)
                    .build();
            redisRepository.save(rq);
        }
    }
}
