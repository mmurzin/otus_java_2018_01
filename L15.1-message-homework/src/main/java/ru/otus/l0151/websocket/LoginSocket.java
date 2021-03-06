package ru.otus.l0151.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.l0151.UserCredentials;
import ru.otus.l0151.frontend.FrontendService;

import javax.servlet.http.HttpSession;

@WebSocket
public class LoginSocket {
    private Session session;
    private final HttpSession httpSession;
    private final FrontendService frontendService;

    public LoginSocket(HttpSession httpSession){
        this.httpSession = httpSession;
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        frontendService = (FrontendService) context.getBean("frontendService");
    }
    @OnWebSocketMessage
    public void onMessage(String data) {
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
        if (frontendService != null) {
            frontendService.removeSocket(this);
        }
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }
}
