package me.nikonbite.emposium.rest.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;
import me.nikonbite.emposium.rest.header.Header;
import me.nikonbite.emposium.rest.request.info.RequestInfo;
import me.nikonbite.emposium.rest.request.param.Param;
import me.nikonbite.emposium.rest.route.Route;

public interface RestController extends HttpHandler {

    @Override
    @SneakyThrows
    default void handle(HttpExchange exchange) {
        var requestMethod = exchange.getRequestMethod();
        var requestPath = exchange.getRequestURI().getPath();
        var method = findHandlerMethod(requestPath, requestMethod);

        if (method != null) {
            processHeaders(method, exchange);
            invokeHandlerMethod(method, exchange);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    default Method findHandlerMethod(String path, String method) {
        var currentClass = getClass();
        while (currentClass != null) {
            for (var m : currentClass.getDeclaredMethods()) {
                var routeAnnotation = m.getAnnotation(Route.class);
                if (routeAnnotation != null && routeAnnotation.context().equals(path)
                        && routeAnnotation.type().name().equals(method)) {
                    return m;
                }
            }
            currentClass = (Class<? extends RestController>) currentClass.getSuperclass();
        }
        return null;
    }

    @SneakyThrows
    default void invokeHandlerMethod(Method method, HttpExchange exchange) {
        try {
            var result = invokeMethodWithParams(method, exchange);
            exchange.sendResponseHeaders(200, 0);
            var os = exchange.getResponseBody();
            os.write(result.toString().getBytes());
            os.close();
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        }
    }

    @SneakyThrows
    default Object invokeMethodWithParams(Method method, HttpExchange exchange) {
        var params = method.getParameters();
        var args = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            var param = params[i];
            if (param.isAnnotationPresent(Param.class)) {
                var paramAnnotation = param.getAnnotation(Param.class);
                args[i] = extractParamValue(exchange.getRequestURI().getQuery(), paramAnnotation.value());
            } else if (param.getType() == RequestInfo.class) {
                args[i] = new RequestInfo(exchange);
            }
        }
        return method.invoke(this, args);
    }

    default String extractParamValue(String queryString, String paramName) {
        var params = queryString.split("&");

        for (var param : params) {
            var keyValue = param.split("=");

            if (keyValue.length == 2) {
                var decodedParamName = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                if (decodedParamName.equals(paramName)) {
                    return URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }

    default void processHeaders(Method method, HttpExchange exchange) {
        var headerAnnotations = method.getAnnotationsByType(Header.class);

        for (var headerAnnotation : headerAnnotations)
            exchange.getResponseHeaders().set(headerAnnotation.key(), headerAnnotation.value());
    }

    default void register(HttpServer server) {
        for (var method : getClass().getMethods()) {
            var routeAnnotation = method.getAnnotation(Route.class);
            if (routeAnnotation != null) {
                server.createContext(routeAnnotation.context(), this);
            }
        }
    }
}
