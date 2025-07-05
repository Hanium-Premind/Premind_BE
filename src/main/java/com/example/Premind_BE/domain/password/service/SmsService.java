package com.example.Premind_BE.domain.password.service;

import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final RedisUtil redisUtil;

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    // 인증번호 생성 (6자리 추천)
    private String createRandomNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(rand.nextInt(10)); // 0~9
        }
        return sb.toString();
    }

    // 문자 전송 파라미터 구성
    private HashMap<String, String> makeParams(String to, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", "01094841183"); // 사전 등록된 발신번호
        params.put("type", "SMS");
        params.put("app_version", "Premind 1.0");
        params.put("to", to);
        params.put("text", "[Premind] 인증번호 [" + code + "]를 입력해 주세요.");
        return params;
    }

    // 인증번호 전송 (비밀번호 찾기 등에서 사용)
    @Transactional
    public void certificateSMS(String phoneNumber) {
        Message coolsms = new Message(apiKey, apiSecret);
        String code = createRandomNumber();

        // Redis에 5분 유효시간으로 저장
        redisUtil.set(phoneNumber, code, 3);

        // 문자 발송
        try {
            JSONObject obj = (JSONObject) coolsms.send(makeParams(phoneNumber, code));
            System.out.println("인증번호 발송 성공: " + obj.toJSONString());
        } catch (CoolsmsException e) {
            throw new CustomException(ErrorCode.VERIFICATION_CODE_SEND_FAILED);
        }
    }

    // 인증번호 검증용 메서드
    public boolean verifyCode(String phoneNumber, String inputCode) {
        String savedCode = redisUtil.get(phoneNumber);
        if (savedCode == null || !savedCode.equals(inputCode)) {
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);  // 인증번호 불일치 오류 발생
        }
        return true;  // 인증번호 일치하면 true 반환
    }

}
