package com.mes.factory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private int userId;
    private String loginId;     // 로그인 아이디
    private String password;    // 비밀번호
    private String userName;    // 이름
    private String role;        // 권한 (ROLE_USER / ROLE_ADMIN)
    private LocalDateTime createdAt;
}
