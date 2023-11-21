package me.nikonbite.emposium;

import me.nikonbite.emposium.rest.controller.RestController;
import me.nikonbite.emposium.rest.header.Header;
import me.nikonbite.emposium.rest.header.key.Key;
import me.nikonbite.emposium.rest.header.value.ContentType;
import me.nikonbite.emposium.rest.request.info.RequestInfo;
import me.nikonbite.emposium.rest.request.param.Param;
import me.nikonbite.emposium.rest.route.Route;
import me.nikonbite.emposium.rest.route.type.RouteType;

public class TestRestController implements RestController {
    @Route(context = "/hello", type = RouteType.GET)
    @Header(key = Key.CONTENT_TYPE, value = ContentType.HTML)
    public String hello() {
        return "<h1 style=\"color: #7581FF;\">Hello from Emposium</h1>";
    }

    @Route(context = "/bye", type = RouteType.GET)
    @Header(key = Key.CONTENT_TYPE, value = ContentType.PLAIN_TEXT)
    public String bye() {
        return "Bye from Emposium!";
    }

    /**
     *  Route: <strong>/param?name=Example</strong>
     */
    @Route(context = "/param", type = RouteType.POST)
    public String popa(@Param("name") String name) {
        return "Hello, " + name + "!";
    }

    @Route(context = "/paramRequest", type = RouteType.POST)
    @Header(key = Key.CONTENT_TYPE, value = ContentType.JSON)
    public String popaWithRequestInfo(RequestInfo ri) {
        var requestBody = ri.requestBody();

        return "Request Body: " + requestBody;
    }
}