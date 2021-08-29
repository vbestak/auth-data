package hr.vbestak.authclient.validator;

import hr.vbestak.authclient.dto.RegistrationForm;
import hr.vbestak.authclient.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {
    private ValidatorUtil validatorUtil;

    @Autowired
    public RegistrationFormValidator(ValidatorUtil validatorUtil) {
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) o;
        String email = registrationForm.getEmail();
        String password = registrationForm.getPassword();
        String passwordRepeat = registrationForm.getPasswordRepeat();


        if(ValidatorUtil.isEmpty(email)) {
            errors.rejectValue("email", "Email is empty!");
        } else if(!ValidatorUtil.validEmail(email)) {
            errors.rejectValue("email", "Email is invalid!");
        } else if (validatorUtil.emailInUse(email)) {
            errors.rejectValue("email", "Email already in use!");
        }

        if(ValidatorUtil.isEmpty(password)){
            errors.rejectValue("password", "Password is empty!");
        } else if(!ValidatorUtil.validPassword(password)){
            errors.rejectValue("password", "Password invalid pattern!");
        }

        if(ValidatorUtil.isEmpty(passwordRepeat)){
            errors.rejectValue("passwordRepeat", "Password repeat is empty!");
        } else if(!ValidatorUtil.isEmpty(password) && !password.equals(passwordRepeat)){
            errors.rejectValue("passwordRepeat", "Passwords do not match!");
        }
    }
}
