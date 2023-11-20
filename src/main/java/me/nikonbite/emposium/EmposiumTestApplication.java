package me.nikonbite.emposium;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class EmposiumTestApplication {
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8080), 0);

        new TestController().register(server);

        server.start();

        System.out.println("Server is running on port 8080");
    }
}