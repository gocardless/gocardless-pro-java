package com.gocardless.pro.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field as a parameter used to construct the path for a request.
 *
 * Fields marked with this annotation will not be serialized as JSON when
 * making HTTP requests.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PathParam {
}
