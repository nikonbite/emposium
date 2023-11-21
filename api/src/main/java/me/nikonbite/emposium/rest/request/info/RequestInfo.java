package me.nikonbite.emposium.rest.request.info;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public record RequestInfo(HttpExchange exchange) {
    @SneakyThrows
    public String requestBody() {
        var requestBody = exchange.getRequestBody();
        var reader = new BufferedReader(new InputStreamReader(requestBody));
        var stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    public Headers requestHeaders() {
        return exchange.getRequestHeaders();
    }

    public String requestMethod() {
        return exchange.getRequestMethod();
    }

    public String requestPath() {
        return exchange.getRequestURI().getPath();
    }

    public String queryString() {
        return exchange.getRequestURI().getQuery();
    }

    public InetSocketAddress remoteAddress() {
        return exchange.getRemoteAddress();
    }
}