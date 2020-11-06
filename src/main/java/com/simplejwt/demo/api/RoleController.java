package com.simplejwt.demo.api;


import com.simplejwt.demo.bll.RoleService;
import com.simplejwt.demo.dtos.RoleDto;
import com.simplejwt.demo.dtos.UserDetailsUpdate;
import com.simplejwt.demo.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE}
                )
    public List<RoleDto> getRoles()
    {
        List<RoleDto> returnValue = roleService.getRoles();
        return returnValue;
    }

    @GetMapping(path = "{id}",
                produces = { MediaType.APPLICATION_JSON_VALUE }
                )
    public RoleDto getRole(@PathVariable String id)
    {
        RoleDto returnValue = roleService.getRole(id);

        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE}
            )
    public RoleDto createRole(@RequestBody RoleDto roleDto)
    {
        RoleDto returnValue = roleService.createRole(roleDto);
        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public RoleDto updateRole(@PathVariable String id, @RequestBody RoleDto roleDto )
    {

        RoleDto returnValue = roleService.updateRole(id, roleDto);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public String deleteRole(@PathVariable String id)
    {
        roleService.deleteRole(id);
        return "delete role";
    }
}
