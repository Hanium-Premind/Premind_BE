package com.example.Premind_BE.domain.portfolio.api;

import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUploadReqDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioQuestionResDto;
import com.example.Premind_BE.domain.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
@Tag(name = "Portfolio API", description = "포트폴리오 관련 API입니다.")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Operation(
            summary = "포트폴리오 업로드",
            description = "PDF 포트폴리오 파일과 제목/직무/기업/질문답변 정보 등을 함께 업로드합니다.",
            requestBody = @RequestBody(
                    content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = PortfolioUploadReqDto.class))
                    }
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "업로드 성공"),
                    @ApiResponse(responseCode = "400", description = "요청 오류")
            }
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPortfolio(
            @Parameter(description = "포트폴리오 정보 JSON", required = true)
            @RequestPart("portfolioRequest") PortfolioUploadReqDto portfolioUploadReqDto,

            @Parameter(description = "업로드할 PDF 파일", required = true)
            @RequestPart("file") MultipartFile file
    ) {
        portfolioService.uploadPortfolio(portfolioUploadReqDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "포트폴리오 질문 항목 조회 API")
    @GetMapping("/question/list")
    public List<PortfolioQuestionResDto> portfolioQuestionList() {
        return portfolioService.portfolioQuestionList();
    }
}
