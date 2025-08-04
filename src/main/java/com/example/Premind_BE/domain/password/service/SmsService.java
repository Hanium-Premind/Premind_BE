package com.example.Premind_BE.domain.password.service;

import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.apache.juli.logging.Log;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {
    private final RedisUtil redisUtil;

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    // 인증번호 생성
    private String createRandomNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
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

        redisUtil.set(phoneNumber, code, 3, TimeUnit.MINUTES);;

        // 문자 발송
        try {
            JSONObject obj = (JSONObject) coolsms.send(makeParams(phoneNumber, code));
            System.out.println("인증번호 발송 성공: " + obj.toJSONString());
        } catch (CoolsmsException e) {
            log.error("Coolsms 전송 실패: {}", e.getMessage());  // 로그에 메시지 남기기
            throw new CustomException(ErrorCode.VERIFICATION_CODE_SEND_FAILED);
        }
    }

    // 인증번호 검증용 메서드
    public boolean verifyCode(String phoneNumber, String inputCode) {
        String savedCode = redisUtil.get(phoneNumber);

        if (savedCode == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_VERIFICATION_RECORD);
        } else if (!savedCode.equals(inputCode)) {
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        redisUtil.set("verify:" + phoneNumber, "true", Duration.ofMinutes(15));

        return true;
    }


}
