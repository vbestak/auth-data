package hr.vbestak.authclient.controller.api;

import hr.vbestak.authclient.dto.UserUpdateCommand;
import hr.vbestak.authclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user")
public class UserApiController {
    //TODO KORISNIK UPRAVLJA SOBOM
    private UserService userService;
    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable String id, @RequestBody UserUpdateCommand user){
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }
}
