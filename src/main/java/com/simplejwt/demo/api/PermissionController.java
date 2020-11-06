package com.simplejwt.demo.api;


import com.simplejwt.demo.bll.PermissionService;
import com.simplejwt.demo.dtos.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE}
                )
    public List<PermissionDto> getPermission()
    {
        List<PermissionDto> returnValue = permissionService.getPermissions();
        return returnValue;
    }

    @GetMapping(path = "{id}",
                produces = { MediaType.APPLICATION_JSON_VALUE }
                )
    public PermissionDto getPermission(@PathVariable String id)
    {
        PermissionDto returnValue = permissionService.getPermission(id);
        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE}
            )
    public PermissionDto createPermission(@RequestBody PermissionDto permissionDto)
    {
        PermissionDto returnValue = permissionService.createPermission(permissionDto);
        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public PermissionDto updatePermission(@PathVariable String id, @RequestBody PermissionDto permissionDto )
    {

        PermissionDto returnValue = permissionService.updatePermission(id, permissionDto);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public String deletePermission(@PathVariable String id)
    {
        permissionService.deletePermission(id);
        return "delete permission";
    }
}
