package com.kagzz.jmix.rys.test_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class Validations {

    @Autowired
    Validator validator;

    public <T> List<ValidationResults> validate(T entity) {
        return validator.validate(entity, Default.class)
                .stream()
                .map(ValidationResults::new)
                .collect(Collectors.toList());
    }

    public String validationMessage(String errorType) {
        return "{javax.validation.constraints."+errorType+".message}";
    }

    public <T> ValidationResults validateFirst(T entity) {
        return validate(entity).get(0);
    }

    public <T> void assertExactlyOneViolationWith(T entity, String attribute, String errorType) {
        List<Validations.ValidationResults> violations = validate(entity);

//        Then
        assertThat(violations).hasSize(1);

        Validations.ValidationResults productViolations = violations.get(0);

        assertThat(productViolations.getAttribute()).isEqualTo(attribute);

        assertThat(productViolations.getErrorType())
                .isEqualTo(validationMessage(errorType));
    }

    public <T> void assertOneViolationWith(T entity, String attribute, String errorType) {
        List<Validations.ValidationResults> violations = validate(entity);

//        Then
        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(validationResults -> validationResults.matches(attribute, validationMessage(errorType)));
    }

    public <T> void assertOneViolationWith(T entity,  String errorType) {
        List<Validations.ValidationResults> violations = validate(entity);

//        Then
        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(validationResults -> validationResults.matches(validationMessage(errorType)));
    }

    public <T>  void assertNoViolations(T entity) {
        List<Validations.ValidationResults>  violations = validate(entity);
        assertThat(violations).isEmpty();
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

        public boolean matches(String attribute, String error) {
            return getAttribute().equalsIgnoreCase(attribute) && getErrorType().equals(error);
        }

        public boolean matches(String error) {
            return getErrorType().equals(error);
        }
    }
}
