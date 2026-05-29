package com.mes.factory.service;

import com.mes.factory.dto.UserDto;
import com.mes.factory.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // 아이디 중복 체크
    public boolean isLoginIdDuplicated(String loginId) {
        return userMapper.countByLoginId(loginId) > 0;
    }

    public boolean register(UserDto userDto) {
        if (isLoginIdDuplicated(userDto.getLoginId())) {
            return false;
        }
        // 비밀번호 암호화
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // 기본 권한 설정
        userDto.setRole("ROLE_USER");
        userMapper.insertUser(userDto);
        return true;
    }


}
