package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.application.users.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {

    Role[] roles() default{};
}
