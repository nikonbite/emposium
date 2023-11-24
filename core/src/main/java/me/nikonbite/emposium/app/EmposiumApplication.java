package me.nikonbite.emposium.app;

import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;
import me.nikonbite.emposium.util.Logger;
import me.nikonbite.emposium.web.template.manager.EmposiumTemplateManager;

import java.net.InetSocketAddress;

public abstract class EmposiumApplication {
    private static EmposiumApplication INSTANCE;
    protected EmposiumTemplateManager manager;
    protected HttpServer httpServer;
    protected final int port;

    @SneakyThrows
    protected EmposiumApplication(int port) {
        this.port = port;
        start();
    }

    @SneakyThrows
    public void start() {
        INSTANCE = this;

        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        Logger.log(Logger.PREFIX, "HTTP Server initialized!");

        manager = new EmposiumTemplateManager();
        Logger.log(Logger.PREFIX, "Template Manager initialized (*.emposium)");

        init();
        Logger.log(Logger.PREFIX, "Initialization completed!");

        httpServer.start();
        Logger.logf("%sHTTP Server has been started at port %d!", Logger.PREFIX, port);
    }

    public abstract void stop();

    public abstract void init();

    public static EmposiumApplication instance() {
        return INSTANCE;
    }

    public EmposiumTemplateManager manager() {
        return manager;
    }

    public HttpServer httpServer() {
        return httpServer;
    }
}