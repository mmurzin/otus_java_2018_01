package ru.otus.l0151.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * This class represents a servlet starting a webSocket application
 */
public class WebSocketLoginServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new LoginWebSocketCreator());


//        factory.getExtensionFactory().unregister("permessage-deflate");
    }
}
