package me.nikonbite.emposium.rest.route;

import me.nikonbite.emposium.rest.route.type.RouteType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String context();

    RouteType type();
}