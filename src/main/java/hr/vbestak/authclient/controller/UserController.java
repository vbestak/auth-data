package hr.vbestak.authclient.controller;

import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.dto.UserPrincipal;
import hr.vbestak.authclient.dto.UserUpdateCommand;
import hr.vbestak.authclient.service.UserService;
import hr.vbestak.authclient.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/whoAmI")
    public ResponseEntity<UserPrincipal> whoAmI(){
        Optional<String> username = UserUtil.getCurrentUser();
        User user = (User) userService.loadUserByUsername(username.get());

        return ResponseEntity.ok().body(new UserPrincipal(user));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable String id, @RequestBody UserUpdateCommand user){
        userService.update(user);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        //TODO hijerarhija brisanja > superadmin one ispod, admin one nize od sebe itd....
        userService.delete(id);
    }
}
