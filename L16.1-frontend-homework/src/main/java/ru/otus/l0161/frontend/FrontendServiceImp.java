package ru.otus.l0161.frontend;


import com.google.gson.Gson;
import ru.otus.l0161.UserCredentials;
import ru.otus.l0161.cache.CacheInformation;
import ru.otus.l0161.channel.ClientMessageWorker;
import ru.otus.l0161.channel.SocketMessageWorker;
import ru.otus.l0161.messages.*;

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
    private LoginResultDelegate loginResultDelegate;
    private CacheResultDelegate cacheResultDelegate;

    private SocketMessageWorker client;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Logger logger = Logger.getLogger(FrontendServiceImp.class.getSimpleName());


    public FrontendServiceImp() throws Exception {
        client = new ClientMessageWorker(HOST, PORT);
        client.init();

        executorService.submit(() -> {
            try {
                while (true) {
                    Message message = client.take();
                    handleMessage(message);
                }
            } catch (InterruptedException | IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private void handleMessage(Message message) throws IOException {
        logger.log(Level.INFO, "handleMessage " + message.getClass().getSimpleName());
        if (message instanceof LoginResultMessage) {
            if (loginResultDelegate != null) {
                loginResultDelegate.publishResult(((LoginResultMessage) message).isLoginSuccessfully());
            }
        }

        if (message instanceof CacheResultMessage){
            if(cacheResultDelegate != null){
                cacheResultDelegate.publishCacheInformation(((CacheResultMessage)message).getCacheInformation());
            }
        }
    }


    @Override
    public void doLogin(UserCredentials credentials, LoginResultDelegate loginResultDelegate) {
        this.loginResultDelegate = loginResultDelegate;
        sendMessage(new LoginUserMessage(credentials));
    }

    @Override
    public void getCacheInformation(CacheResultDelegate cacheResultDelegate) {
        this.cacheResultDelegate = cacheResultDelegate;
        sendMessage(new CacheMessage());
    }

    private synchronized void sendMessage(Message message) {
        if (client != null) {
            client.send(message);
        } else {
            logger.log(Level.SEVERE, "Client is null");
        }
    }


}
