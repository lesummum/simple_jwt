package com.simplejwt.demo.bll;

import com.simplejwt.demo.dtos.UserDetailsUpdate;
import com.simplejwt.demo.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getUsers(int page, int limit);
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserByUserId(String userId);
    UserDto updateUser(String userId, UserDetailsUpdate userDetailsUpdate);
    void deleteUser(String userId);

}
