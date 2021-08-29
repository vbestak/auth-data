package hr.vbestak.authclient.controller.api;

import hr.vbestak.authclient.dto.UserPrincipal;
import hr.vbestak.authclient.dto.UserUpdateCommand;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.model.error.ApiValidationError;
import hr.vbestak.authclient.service.UserService;
import hr.vbestak.authclient.util.UserUtil;
import hr.vbestak.authclient.util.ValidatorUtil;
import hr.vbestak.authclient.validator.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/user")
public class UserApiController {
    private UserService userService;

    private UserUpdateValidator userUpdateValidator;

    @Autowired
    public UserApiController(UserService userService, UserUpdateValidator userUpdateValidator) {
        this.userService = userService;
        this.userUpdateValidator = userUpdateValidator;
    }

    @GetMapping("/whoAmI")
    public ResponseEntity<UserPrincipal> whoAmI(){
        Optional<User> user = UserUtil.getCurrentUser();

        return ResponseEntity.ok().body(new UserPrincipal(user.get()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateCommand userCommand){
        Optional<User> user = UserUtil.getCurrentUser();

        if(user.get().getId() == id) {
            Errors errors = new BeanPropertyBindingResult(userCommand, "user");
            userUpdateValidator.validate(userCommand, errors);

            if (errors.hasErrors()) {
                List<ApiValidationError> error = ValidatorUtil.mapFieldError(errors.getFieldErrors());
                return ResponseEntity.badRequest().body(error);
            }

            userService.update(id, userCommand);
            return ResponseEntity.accepted().build();
        } else {
            throw new IllegalCallerException("User can only update itself!");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        Optional<User> user = UserUtil.getCurrentUser();

        if(user.get().getId() == id) {
            userService.delete(id);
        } else {
            throw new IllegalCallerException("User can only delete itself!");
        }
    }
}
