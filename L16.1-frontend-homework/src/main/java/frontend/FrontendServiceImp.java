package frontend;

import ru.otus.l0161.channel.ClientMessageWorker;
import ru.otus.l0161.channel.SocketMessageWorker;
import ru.otus.l0161.messages.Message;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendServiceImp implements FrontendService {
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 2;

    private SocketMessageWorker client;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Logger logger = Logger.getLogger(FrontendServiceImp.class.getSimpleName());



    public FrontendServiceImp() throws Exception{
        client = new ClientMessageWorker(HOST, PORT);
        client.init();

        executorService.submit(() -> {
            try {
                while (true) {
                    Message message = client.take();
                    handleMessage(message);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE,e.getMessage());
            }
        });
    }

    private void handleMessage(Message message) {

    }


    @Override
    public void doLogin(UserCredentials credentials, LoginSocket loginSocket) {
        sockets.put(credentials.getLogin(), loginSocket);
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

        logger.log(Level.INFO, "publishLoginResult " + credentials.toString() +" result "+isSuccessfulLogin);

        LoginSocket loginSocket = sockets.get(credentials.getLogin());
        if(loginSocket != null){
            AuthResult authResult = new AuthResult(isSuccessfulLogin);
            String result = GsonUtil.getGson().toJson(authResult);
            AuthUtils.authorized(loginSocket.getHttpSession(),authResult);
            try {
                loginSocket.getSession().getRemote().sendString(result);
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
