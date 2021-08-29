package hr.vbestak.authclient.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ApiValidationError  {
    private String object;
    private String field;
    private String message;

    @JsonIgnore
    private Object rejectedValue;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ApiValidationError(FieldError fieldError){
        this.object = fieldError.getObjectName();
        this.field = fieldError.getField();
        this.rejectedValue = fieldError.getRejectedValue();
        this.message = fieldError.getCode();
    }

    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}