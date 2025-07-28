package com.example.Premind_BE.domain.portfolio.api;

import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUploadReqDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioInquiryResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioQuestionResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioUploadResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PresignedUrlResDto;
import com.example.Premind_BE.domain.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
@Tag(name = "Portfolio API", description = "포트폴리오 관련 API입니다.")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Operation(summary = "presigned url 발급",description = "포트폴리오 업로드 페이지에서 파일을 pdf업로드 항목에 드래그하여 놓는 순간 presigned url을 발급 받고 해당 url로 파일 업로드 진행")
    @PostMapping(value = "/file-upload")
    public PresignedUrlResDto generatePresignedUrl() {
        return portfolioService.generatePresignedUrl();
    }

    @Operation(summary = "포트폴리오 업로드",description = "PDF 포트폴리오 파일과 제목/직무/기업/질문답변 정보 등을 함께 업로드합니다.")
    @PostMapping(value = "/upload")
    public PortfolioUploadResDto uploadPortfolio(@RequestBody PortfolioUploadReqDto reqDto) {
        return portfolioService.uploadPortfolio(reqDto);
    }

    @Operation(summary = "포트폴리오 질문 항목 조회 API")
    @GetMapping("/question/list")
    public List<PortfolioQuestionResDto> portfolioQuestionList() {
        return portfolioService.portfolioQuestionList();
    }

    @Operation(summary = "포트폴리오 상세 조회 API")
    @Parameter(name = "portfolioId", in = ParameterIn.PATH, description = "조회하고자 하는 포트폴리오 id값", required = true)
    @GetMapping("/{portfolioId}")
    public PortfolioInquiryResDto portfolioInquiry(@PathVariable Long portfolioId) {
        return portfolioService.portfolioInquiry(portfolioId);
    }
}
