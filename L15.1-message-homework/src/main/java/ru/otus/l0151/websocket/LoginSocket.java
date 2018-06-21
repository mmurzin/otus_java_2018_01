package ru.otus.l0151.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.l0151.FrontendUtils;
import ru.otus.l0151.UserCredentials;
import ru.otus.l0151.frontend.FrontendService;

@WebSocket
public class LoginSocket {
    private Session session;

    @OnWebSocketMessage
    public void onMessage(String data) {
        FrontendService frontendService = FrontendUtils.getFrontendService();
        if (frontendService != null) {
            frontendService.removeSocket(this);
            frontendService.doLogin(UserCredentials.fromJson(data), this);
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        FrontendService frontendService = FrontendUtils.getFrontendService();
        if (frontendService != null) {
            frontendService.removeSocket(this);
        }
    }
}
