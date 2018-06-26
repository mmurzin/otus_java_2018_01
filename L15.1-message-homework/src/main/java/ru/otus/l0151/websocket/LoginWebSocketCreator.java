package ru.otus.l0151.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
public class LoginWebSocketCreator implements WebSocketCreator {
    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return new LoginSocket();
    }
}
