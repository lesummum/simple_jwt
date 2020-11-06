package com.simplejwt.demo.dal;


import com.simplejwt.demo.models.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String usedId);
}
