package com.mes.factory.mapper;

import com.mes.factory.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDto findByLoginId(String loginId);

    void insertUser(UserDto userDto);

}
