package com.kagzz.jmix.rys.test_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationVerification {

    @Autowired
    Validator validator;

    public <T> List<ValidationResults> validate(T customer) {
        return validator.validate(customer, Default.class)
                .stream()
                .map(ValidationResults::new)
                .collect(Collectors.toList());
    }

    public String validationMessage(String errorType) {
        return "{javax.validation.constraints."+errorType+".message}";
    }

    public <T> ValidationResults validateFirst(T customer) {
        return validate(customer).get(0);
    }

    public static class ValidationResults<T>{

        private final ConstraintViolation<T> violation;

        public ValidationResults(ConstraintViolation<T> violation) {
            this.violation = violation;
        }

        public String getAttribute() {
            return violation.getPropertyPath().toString();
        }

        public String getErrorType() {
            return violation.getMessageTemplate();
        }
    }
}
