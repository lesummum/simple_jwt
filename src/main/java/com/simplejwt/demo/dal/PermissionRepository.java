package com.simplejwt.demo.dal;


import com.simplejwt.demo.models.PermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionEntity, Integer> {
    @Query("SELECT p " +
            "FROM PermissionEntity p " +
            "inner join p.roles pr " +
            "where pr.id = :role_id")
    List<PermissionEntity> getPermissionIdByRoleId(@Param("role_id") int role_id);

    PermissionEntity findByPermissionPubId(String permissionPubId);
    PermissionEntity findByName(String name);
           // "SELECT p " +
           // "FROM PermissionEntity p " +
           // "join p.roles pr on p.id = pr.id " +
           // "where pr.id = :role_id"
}
