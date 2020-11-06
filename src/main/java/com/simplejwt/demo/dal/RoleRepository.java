package com.simplejwt.demo.dal;

import com.simplejwt.demo.models.RoleEntity;
import com.simplejwt.demo.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    RoleEntity findByRolePubId(String rolePubId);
    RoleEntity findByName(String name);

}
