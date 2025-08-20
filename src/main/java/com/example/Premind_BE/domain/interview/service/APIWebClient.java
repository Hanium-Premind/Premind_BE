package com.example.Premind_BE.domain.interview.service;

import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeRequest;
import com.example.Premind_BE.domain.interview.dto.ai.response.GenerateQAResDto;
import com.example.Premind_BE.domain.interview.dto.ai.response.PracticeQAFeedbackResDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
@RequiredArgsConstructor
public class APIWebClient {
    private final WebClient interviewApiClient;

    // 연습모드 - 첫번째 질문 생성
    public GenerateQAResDto startPractice(StartPracticeRequest req) {
        return interviewApiClient.post()
                .uri("/api/v1/practice/start")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        resp -> resp.bodyToMono(String.class)
                                .defaultIfEmpty("Unknown error")
                                .map(msg -> new RuntimeException("Interview API error: " + msg)))
                .bodyToMono(GenerateQAResDto.class)
                .block(); // 동기 방식
    }

    // 연습모드 - 질문애 대한 답변 제출 + 피드백
    public PracticeQAFeedbackResDto uploadVideo(String jobId, MultipartFile videoFile) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("job_id", jobId);
        builder.part("file", videoFile.getResource())
                .filename(videoFile.getOriginalFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM);

        return interviewApiClient.post()
                .uri("/api/v1/practice/submit")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        resp -> resp.bodyToMono(String.class)
                                .defaultIfEmpty("Unknown error")
                                .map(msg -> new RuntimeException("Interview API error: " + msg)))
                .bodyToMono(PracticeQAFeedbackResDto.class)
                .block();
    }
}
