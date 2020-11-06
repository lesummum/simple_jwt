package com.simplejwt.demo.bll;

import com.simplejwt.demo.bll.tools.Utils;
import com.simplejwt.demo.dal.RoleRepository;
import com.simplejwt.demo.dal.UserRepository;
import com.simplejwt.demo.dtos.UserDetailsUpdate;
import com.simplejwt.demo.dtos.UserDto;
import com.simplejwt.demo.models.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService{


    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        // adaptable page in link for we can start by 1
        if (page>0) page-=1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
        // prepared list from page of UserEntity
        List<UserEntity> users =userPage.getContent();
        // fill list(return type) UserDto
        for (UserEntity userEntity: users){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    public UserDto createUser(@NotNull UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail()) != null) throw new RuntimeException("Email alredy exists");
        UserEntity newUserEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, newUserEntity);

        String publicUserId = utils.generatePubId(30);
        newUserEntity.setUserId(publicUserId);
        newUserEntity.setEncryptedPassword(bCryptPasswordEncoder.encode((userDto.getPassword())));

        // affect role to user for testing
        newUserEntity.setRole(roleRepository.findByName("Admin"));

        UserEntity storedUserEntity =userRepository.save(newUserEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserEntity, returnValue);
        return returnValue;


    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);
        // check if User exist
        if(userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;

    }

    @Override
        public UserDto updateUser(String userId, UserDetailsUpdate userDetailsUpdate) {

        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);

        userEntity.setFirstName(userDetailsUpdate.getFirstName());
        userEntity.setLastName(userDetailsUpdate.getLastName());

        UserEntity userUpdated = userRepository.save(userEntity);
        BeanUtils.copyProperties(userUpdated, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        userRepository.delete(userEntity);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
