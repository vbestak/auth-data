package hr.vbestak.authclient.validator;

import hr.vbestak.authclient.dto.UserUpdateCommand;
import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.util.UserUtil;
import hr.vbestak.authclient.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {
    private ValidatorUtil validatorUtil;

    @Autowired
    public UserUpdateValidator(ValidatorUtil validatorUtil) {
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserUpdateCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User currentUser = UserUtil.getCurrentUser().get();

        UserUpdateCommand target = (UserUpdateCommand) o;
        String email = target.getEmail();

        if(email != currentUser.getEmail() && !ValidatorUtil.isEmpty(email)) {
            if(!ValidatorUtil.validEmail(email)) {
                errors.rejectValue("email", "Email is invalid!");
            } else if (validatorUtil.emailInUse(email)) {
                errors.rejectValue("email", "Email already in use!");
            }
        }

    }
}
