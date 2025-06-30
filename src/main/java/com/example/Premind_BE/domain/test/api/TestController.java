package com.example.Premind_BE.domain.test.api;

import com.example.Premind_BE.domain.test.dto.TestResponseDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/health_check")
    public TestResponseDto healthCheck() {
        return new TestResponseDto("테스트에 성공하였습니다");
    }

    @GetMapping("/error_check")
    public void errorCheck() {
        throw new CustomException(ErrorCode.TEST_ERROR_CODE);
    }
}
