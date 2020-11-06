package com.simplejwt.demo.bll;


import com.simplejwt.demo.dtos.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> getPermissions();
    PermissionDto getPermission(String permissionId);
    PermissionDto createPermission(PermissionDto permissionDto);
    PermissionDto updatePermission(String permissionId, PermissionDto permissionDtoUpdate);
    void deletePermission(String permissionId);
}
