package com.example.Premind_BE.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReceiveCodeReqDto {
    private String phoneNumber;
}
