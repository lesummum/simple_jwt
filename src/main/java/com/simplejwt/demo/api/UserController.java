package com.simplejwt.demo.api;


import com.simplejwt.demo.bll.UserService;
import com.simplejwt.demo.dtos.UserDetailsUpdate;
import com.simplejwt.demo.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(
                produces = { MediaType.APPLICATION_JSON_VALUE}
                )
    public List<UserDto> getUsers(@RequestParam(value = "page", defaultValue = "0")int page,
                                  @RequestParam(value = "limit", defaultValue = "25") int limit)
    {
        List<UserDto> returnValue = userService.getUsers(page, limit) ;

        return returnValue;
    }

    @GetMapping(path = "/{id}",
                produces = { MediaType.APPLICATION_JSON_VALUE}
                )
    public UserDto getUser(@PathVariable String id)
    {
        UserDto returnValue = userService.getUserByUserId(id);
        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
            )
    public UserDto  createUser(@RequestBody UserDto userDto)
    {
        UserDto returnValue = userService.createUser(userDto);
        return returnValue;
    }

    @PutMapping(path = "/{id}",
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE }
                )
    public UserDto updateUser(@PathVariable String id,@RequestBody UserDetailsUpdate userDetailsUpdate)
    {

        UserDto returnValue = userService.updateUser(id, userDetailsUpdate);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public String deleteUser(@PathVariable String id)
    {
        userService.deleteUser(id);
        return "delete user";
    }

}
