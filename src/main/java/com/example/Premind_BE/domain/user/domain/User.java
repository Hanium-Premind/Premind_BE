package com.example.Premind_BE.domain.user.domain;

import com.example.Premind_BE.domain.user.dto.request.UpdatePersonalInfoReqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Column(length = 50)
    private String name;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;



    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateInfo(UpdatePersonalInfoReqDto dto) {
        this.name = dto.getName();
        this.birth = dto.getBirthAsLocalDate();
        this.gender = dto.getGender();
    }

}
