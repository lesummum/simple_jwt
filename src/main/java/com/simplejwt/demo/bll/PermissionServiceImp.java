package com.simplejwt.demo.bll;

import com.simplejwt.demo.bll.tools.Utils;
import com.simplejwt.demo.dal.PermissionRepository;
import com.simplejwt.demo.dal.RoleRepository;
import com.simplejwt.demo.dtos.PermissionDto;
import com.simplejwt.demo.models.PermissionEntity;
import com.simplejwt.demo.models.RoleEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionServiceImp implements PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Override
    public List<PermissionDto> getPermissions() {
        List<PermissionDto> returnValue = new ArrayList<>();
        List<PermissionEntity> listPermissions = new ArrayList<>();
        Iterable<PermissionEntity> iterablePermission = permissionRepository.findAll();
        iterablePermission.forEach(listPermissions::add);

        for (PermissionEntity permissionEntity: listPermissions){
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(permissionEntity, permissionDto);
            returnValue.add(permissionDto);
        }


        return returnValue;
    }

    @Override
    public PermissionDto getPermission(String permissionId) {
        PermissionEntity permissionEntity = permissionRepository.findByPermissionPubId(permissionId);
        // check if Permission exist
        if(permissionEntity == null) throw new RuntimeException(permissionId);

        PermissionDto returnValue = new PermissionDto();
        BeanUtils.copyProperties(permissionEntity, returnValue);

        return returnValue;
    }


    @Override
    public PermissionDto createPermission(@NotNull PermissionDto permissionDto) {

        PermissionDto returnValue = new PermissionDto();

        if(permissionRepository.findByName(permissionDto.getName()) != null) throw new RuntimeException("Name already exists");

        PermissionEntity newPermissionEntity = new PermissionEntity();
        BeanUtils.copyProperties(permissionDto, newPermissionEntity);

        String publicPermissionId = utils.generatePubId(30);
        newPermissionEntity.setPermissionPubId(publicPermissionId);

        PermissionEntity storedPermissionEntity = permissionRepository.save(newPermissionEntity);
        BeanUtils.copyProperties(storedPermissionEntity, returnValue);

        return returnValue;
    }

    @Override
    public PermissionDto updatePermission(String permissionId, PermissionDto permissionDtoUpdate) {
        PermissionDto returnValue = new PermissionDto();

        PermissionEntity permissionEntity = permissionRepository.findByPermissionPubId(permissionId);
        if(permissionEntity == null) throw new RuntimeException("this permission not found");

        // permission unicite
        if(permissionRepository.findByName(permissionDtoUpdate.getName()) != null) throw new RuntimeException("this permission name is already exist");

        permissionEntity.setName(permissionDtoUpdate.getName());

        PermissionEntity permissionUpdated = permissionRepository.save(permissionEntity);
        BeanUtils.copyProperties(permissionUpdated, returnValue);

        return returnValue;
    }

    @Override
    public void deletePermission(String permissionId) {
        // delete a permission from any role have a link with this permission
        // delete a permission

        PermissionEntity permissionEntity = permissionRepository.findByPermissionPubId(permissionId);
        if(permissionEntity == null) throw new RuntimeException("this permission not found");
         for (RoleEntity roleEntity : permissionEntity.getRoles()) {
            if (roleEntity.getPermissions().size() == 1) {
                roleRepository.delete(roleEntity);
            } else {
                roleEntity.getPermissions().remove(permissionEntity);
            }
        }
        permissionRepository.delete(permissionEntity);
    }
}
