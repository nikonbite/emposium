package me.nikonbite.emposium;

import me.nikonbite.emposium.app.EmposiumApplication;

public class EmposiumTestApplication extends EmposiumApplication {
    protected EmposiumTestApplication() {
        super(3001);
    }

    public static void main(String[] args) {
        new EmposiumTestApplication();
    }

    @Override
    public void init() {
        new TestRestController().register(httpServer);
    }

    @Override
    public void stop() {

    }
}