package com.gokart.gokartservice.common;

import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
  private String enumValues;

  @Override
  public void initialize(ValueOfEnum constraintAnnotation) {
    // Get the enum class from the annotation
    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

    // Retrieve the enum constants from the enum class
    Enum<?>[] enumConstants = enumClass.getEnumConstants();

    // Build a comma-separated string of enum names
    enumValues = Arrays.stream(enumConstants)
            .map(Enum::name)
            .collect(Collectors.joining(", "));
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Consider null as valid or handle accordingly
    }

    // Convert the CharSequence value to a String for comparison
    String valueAsString = value.toString();

    // Check if the value matches any of the enum names
    boolean isValid = Arrays.stream(enumValues.split(", "))
            .anyMatch(valueAsString::equals);

    if (!isValid) {
      // Customize the default ConstraintValidatorContext message template
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("must be any of enum values: " + enumValues)
              .addConstraintViolation();
    }

    return isValid;
  }
}
