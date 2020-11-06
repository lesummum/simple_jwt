package com.simplejwt.demo.bll;

import com.simplejwt.demo.dtos.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getRoles();
    RoleDto getRole(String roleId);
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(String roleId, RoleDto roleDtoUpdate);
    void deleteRole(String roleId);
}
