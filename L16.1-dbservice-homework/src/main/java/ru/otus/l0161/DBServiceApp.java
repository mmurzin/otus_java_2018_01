package ru.otus.l0161;

import com.google.gson.Gson;
import ru.otus.l0161.cache.CacheEngine;
import ru.otus.l0161.cache.CacheEngineImpl;
import ru.otus.l0161.cache.CacheInformation;
import ru.otus.l0161.channel.ClientMessageWorker;
import ru.otus.l0161.channel.SocketMessageWorker;
import ru.otus.l0161.db.DBService;
import ru.otus.l0161.db.DBServiceImpl;
import ru.otus.l0161.db.ImitationUtils;
import ru.otus.l0161.messages.*;
import ru.otus.l0161.server.DBServiceAppMBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.otus.l0161.App.DEFAULT_LOGIN;
import static ru.otus.l0161.App.DEFAULT_PASSWORD;

public class DBServiceApp implements DBServiceAppMBean {
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private final Logger logger = Logger.getLogger(DBService.class.getSimpleName());
    private volatile boolean isRunning;

    public static void main(String[] args) throws Exception {
        new DBServiceApp().start();
    }

    private void start() throws Exception {
        logger.log(Level.INFO, "DBServiceApp start called");
        DBService dbService = new DBServiceImpl(new CacheEngineImpl());
        SocketMessageWorker client = new ClientMessageWorker(HOST, PORT);
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Message message = client.take();
                    logger.log(Level.INFO, "Message received "+message.getClass().getSimpleName());
                    handleMessage(dbService, client, message);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE,e.getMessage());
            }
        });

        setRunning(true);
    }

    private void handleMessage(DBService dbService,
                               SocketMessageWorker client,
                               Message message) {
        if(message instanceof LoginUserMessage){
            LoginUserMessage loginMessage = (LoginUserMessage)message;
            boolean isValidUserCredentials = dbService.isSuccessfulLogin(loginMessage.getUserCredentials());
            client.send(new LoginResultMessage(isValidUserCredentials));
        }
        if(message instanceof CacheMessage){
            CacheEngine cacheEngine = dbService.getCacheEngine();
            if(cacheEngine == null){
                client.send(null);
            }else {
                client.send(new CacheResultMessage(cacheEngine.getCacheInformation()));
            }
        }
    }

    private boolean isValidUserCredentials(String login, String password) {
        return DEFAULT_LOGIN.equals(login) && DEFAULT_PASSWORD.equals(password);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void setRunning(boolean running) {
        isRunning = running;
    }
}
