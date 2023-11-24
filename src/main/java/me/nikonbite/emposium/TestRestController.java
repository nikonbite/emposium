package me.nikonbite.emposium;

import me.nikonbite.emposium.rest.authentication.Authentication;
import me.nikonbite.emposium.rest.controller.RestController;
import me.nikonbite.emposium.rest.controller.route.ControllerRoute;
import me.nikonbite.emposium.rest.header.Header;
import me.nikonbite.emposium.rest.header.key.Key;
import me.nikonbite.emposium.rest.header.value.ContentType;
import me.nikonbite.emposium.rest.request.info.RequestInfo;
import me.nikonbite.emposium.rest.request.param.Param;
import me.nikonbite.emposium.rest.route.Route;
import me.nikonbite.emposium.rest.route.type.RouteType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerRoute(context = "/api")
public class TestRestController implements RestController {
    @Route(context = "/", type = RouteType.GET)
    @Header(key = Key.CONTENT_TYPE, value = ContentType.HTML)
    public String main() {
        var context = new HashMap<String, Object>();
        context.put("name", "George");

        var users = List.of(
                Map.of("name", "Alice"),
                Map.of("name", "Bob")
        );
        context.put("users", users);

        return EmposiumTestApplication.instance().manager().template("index")
                .process(Map.of("hello", "Hello", "title", "Emposium", "test_array", "{ \"0\": { \"name\": \"Popa\" }, \"1\": { \"name\": \"Pipi\" } }"));
    }

    @Route(context = "/bye", type = RouteType.GET)
    @Header(key = Key.CONTENT_TYPE, value = ContentType.PLAIN_TEXT)
    @Authentication(keys = {"123", "321"})
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