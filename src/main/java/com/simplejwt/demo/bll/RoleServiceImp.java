package com.simplejwt.demo.bll;

import com.simplejwt.demo.bll.tools.Utils;
import com.simplejwt.demo.dal.PermissionRepository;
import com.simplejwt.demo.dal.RoleRepository;
import com.simplejwt.demo.dal.UserRepository;
import com.simplejwt.demo.dtos.RoleDto;
import com.simplejwt.demo.models.PermissionEntity;
import com.simplejwt.demo.models.RoleEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
     @Autowired
     RoleRepository roleRepository;
     @Autowired
     PermissionRepository permissionRepository;
     @Autowired
     UserRepository userRepository;
     @Autowired
     Utils utils;

     @Override
     public List<RoleDto> getRoles() {
          List<RoleDto> returnValue = new ArrayList<>();
          List<RoleEntity> listRoles = new ArrayList<>();
          Iterable<RoleEntity> iterableRoles = roleRepository.findAll();
          iterableRoles.forEach(listRoles::add);

          for (RoleEntity roleEntity: listRoles){
               RoleDto roleDto = new RoleDto();
               BeanUtils.copyProperties(roleEntity, roleDto);
               returnValue.add(roleDto);
          }



          return returnValue;
     }

     @Override
     public RoleDto getRole(String roleId) {
          RoleEntity roleEntity = roleRepository.findByRolePubId(roleId);
          // check if Role exist
          if(roleEntity == null) throw new RuntimeException(roleId);

        RoleDto returnValue = new RoleDto();
        BeanUtils.copyProperties(roleEntity, returnValue);

          return returnValue;
     }

     @Override
     public RoleDto createRole(@NotNull RoleDto roleDto) {

          RoleDto returnValue = new RoleDto();

          if(roleRepository.findByName(roleDto.getName()) != null) throw new RuntimeException("Name already exists");

          RoleEntity newRoleEntity = new RoleEntity();
          BeanUtils.copyProperties(roleDto, newRoleEntity);

          String publicRoleId = utils.generatePubId(30);
          newRoleEntity.setRolePubId(publicRoleId);


          RoleEntity storedRoleEntity = roleRepository.save(newRoleEntity);
          BeanUtils.copyProperties(storedRoleEntity, returnValue);

          return returnValue;
     }

     @Override
     public RoleDto updateRole(String roleId, RoleDto roleDtoUpdate) {

          RoleDto returnValue = new RoleDto();

          RoleEntity roleEntity = roleRepository.findByRolePubId(roleId);
          if(roleEntity == null) throw new  RuntimeException("this role not found");

          roleEntity.setName(roleDtoUpdate.getName());

          RoleEntity roleUpdated = roleRepository.save(roleEntity);
          BeanUtils.copyProperties(roleUpdated, returnValue);
          return returnValue;
     }

     @Override
     public void deleteRole(String roleId) {

          // delete all relations linked with any permission
          // and delete all user user have this role Id

          RoleEntity roleEntity = roleRepository.findByRolePubId(roleId);
          if(roleEntity == null) throw new RuntimeException("this role not found");


          for (PermissionEntity permissionEntity : roleEntity.getPermissions()) {
              permissionEntity.getRoles().remove(roleEntity);
          }

        roleRepository.delete(roleEntity);
     }
}
