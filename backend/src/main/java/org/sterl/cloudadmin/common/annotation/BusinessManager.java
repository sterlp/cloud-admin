package org.sterl.cloudadmin.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Business Manager represents the main control class of a domain / component.
 * Any change to the domain classes has to be passed to it. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Service
@Transactional(propagation = Propagation.REQUIRED, timeout = 30)
public @interface BusinessManager {

}
