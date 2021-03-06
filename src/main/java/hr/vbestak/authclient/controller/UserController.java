package hr.vbestak.authclient.controller;

import hr.vbestak.authclient.dto.UserUpdateCommand;
import hr.vbestak.authclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable Long id, @RequestBody UserUpdateCommand user){
        userService.update(id, user);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        //TODO hijerarhija brisanja > superadmin one ispod, admin one nize od sebe itd....
        userService.delete(id);
    }
}
