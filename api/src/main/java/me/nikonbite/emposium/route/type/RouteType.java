package me.nikonbite.emposium.route.type;

public enum RouteType {

    /**
     * <h2 style="color: #F8F8F8;">Emposium Web Framework</h2>
     * ———<br>
     * The <strong style="color: #ACFF75;">GET</strong> method requests a representation of a resource. <br>
     * Queries using this method can only retrieve data.
     **/
    GET,

    /**
     * <h2 style="color: #F8F8F8;">Emposium Web Framework</h2>
     * ———<br>
     * <strong style="color: #FFD875;">POST</strong> is designed to send a request where the web server <br>
     * accepts data enclosed in the message body for storage.
     **/
    POST,

    /**
     * <h2 style="color: #F8F8F8;">Emposium Web Framework</h2>
     * ———<br>
     * <strong style="color: #D875FF;">PUT</strong> is obsolete and has no need to be used. <br>
     * <strong style="color: #FFD875;">POST</strong> can be used instead.
     **/
    @Deprecated
    PUT,

    /**
     * <h2 style="color: #F8F8F8;">Emposium Web Framework</h2>
     * ———<br>
     * <strong style="color: #FF7575;">DELETE</strong> is obsolete and has no need to be used. <br>
     * <strong style="color: #FFD875;">POST</strong> can be used instead.
     **/
    @Deprecated
    DELETE
}