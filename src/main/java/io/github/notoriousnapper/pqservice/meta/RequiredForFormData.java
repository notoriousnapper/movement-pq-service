package io.github.notoriousnapper.pqservice.meta;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// If tagged with this, form reader will pickup and place it in requiredlist
@Target( { FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredForFormData {
}
