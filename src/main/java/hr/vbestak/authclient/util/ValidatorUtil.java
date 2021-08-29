package hr.vbestak.authclient.util;

import hr.vbestak.authclient.model.common.Pattern;
import hr.vbestak.authclient.model.error.ApiValidationError;
import hr.vbestak.authclient.service.UserService;
import liquibase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidatorUtil {
    private UserService userService;

    @Autowired
    public ValidatorUtil(UserService userService) {
        this.userService = userService;
    }

    public static boolean isEmpty(String value) {
        return StringUtil.isEmpty(value);
    }

    public static boolean validEmail(String value) {
        return value.matches(Pattern.EMAIL);
    }

    public boolean emailInUse(String value) {
        try {
            userService.findByEmail(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean validPassword(String value) {
        return value.matches(Pattern.PASSWORD);
    }

    public static List<ApiValidationError> mapFieldError(List<FieldError> errors) {
        return errors.stream().map(error -> new ApiValidationError(error)).collect(Collectors.toList());
    }
}
