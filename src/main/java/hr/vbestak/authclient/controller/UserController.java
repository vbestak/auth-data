package hr.vbestak.authclient.controller;

import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.UserPrincipal;
import hr.vbestak.authclient.service.UserService;
import hr.vbestak.authclient.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Optional<String> username = UserUtil.getCurrentUserUsername();
        User user = (User) userService.loadUserByUsername(username.get());

        return ResponseEntity.ok().body(new UserPrincipal(user));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public ResponseEntity<UserPrincipal> admin(){
        Optional<String> username = UserUtil.getCurrentUserUsername();
        User user = (User) userService.loadUserByUsername(username.get());

        return ResponseEntity.ok().body(new UserPrincipal(user));
    }
}
