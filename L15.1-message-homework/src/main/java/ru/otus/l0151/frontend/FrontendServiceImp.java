package ru.otus.l0151.frontend;

import ru.otus.l0151.*;
import ru.otus.l0151.message.Address;
import ru.otus.l0151.message.DoLoginMessage;
import ru.otus.l0151.message.Message;
import ru.otus.l0151.message.MessageSystem;
import ru.otus.l0151.websocket.LoginSocket;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendServiceImp implements FrontendService {
    private final MessageContext context;
    private final Address address;
    private final Map<UserCredentials, LoginSocket> sockets =
            new ConcurrentHashMap<>();
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());


    public FrontendServiceImp(MessageContext context) {
        this.context = context;
        this.address = context.getFrontAddress();

        MessageSystem messageSystem = context.getMessageSystem();
        if (messageSystem != null) {
            messageSystem.addAddressee(this);
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return context.getMessageSystem();
    }

    @Override
    public void doLogin(UserCredentials credentials, LoginSocket loginSocket) {
        sockets.put(credentials, loginSocket);
        Message message = new DoLoginMessage(getAddress(),
                context.getDbAddress(), credentials);
        MessageSystem messageSystem = context.getMessageSystem();
        if (messageSystem != null) {
            messageSystem.sendMessage(message);
        }
    }

    @Override
    public void publishLoginResult(UserCredentials credentials,
                                   boolean isSuccessfulLogin) {


        LoginSocket loginSocket = sockets.get(credentials);
        if(loginSocket != null){
            AuthResult authResult = new AuthResult(isSuccessfulLogin);
            String result = GsonUtil.getGson().toJson(authResult);
            try {
                loginSocket.getSession().getRemote().sendString(result);
                AuthUtils.authorized(loginSocket.getHttpSession(), true);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public void removeSocket(LoginSocket loginSocket) {
        sockets.values().remove(loginSocket);
    }


}
