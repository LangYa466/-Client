package cn.liora.event.api;

import cn.liora.event.type.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EventTarget {
    EventPriority priority() default EventPriority.NORMAL;
}