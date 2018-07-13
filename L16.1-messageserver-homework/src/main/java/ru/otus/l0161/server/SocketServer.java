package ru.otus.l0161.server;

import ru.otus.l0161.channel.SocketMessageWorker;
import ru.otus.l0161.messages.*;
import sun.rmi.runtime.Log;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer implements SocketServerMBean {
    private static final Logger logger = Logger.getLogger(SocketServer.class.getSimpleName());

    private static final int PORT = 5050;
    private static final int NUM_OF_THREADS = 1;
    private static final int MSG_PROCESSING_DELAY_MS = 100;

    private final ExecutorService executor;
    private final List<MessageWorker> workers;

    private boolean isRunning = true;

    public SocketServer() {
        executor = Executors.newFixedThreadPool(NUM_OF_THREADS);
        workers = new CopyOnWriteArrayList<>();
    }

    public void start() throws Exception {
        executor.submit(this::work);
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()){
                Socket socket = serverSocket.accept();
                logger.log(Level.INFO, "Connection from "+socket.getLocalPort());
                SocketMessageWorker client = new SocketMessageWorker(socket);
                client.init();
                workers.add(client);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void work(){
        while (true){
            for (MessageWorker worker:workers){
                Message message = worker.pool();
                while(message != null){
                    logger.log(Level.INFO, "Message "+message.getClass().getSimpleName());
                    if(message instanceof LoginUserMessage ||
                            message instanceof LoginResultMessage ||
                            message instanceof CacheMessage ||
                            message instanceof CacheResultMessage){
                        for (MessageWorker messageWorker:workers){
                            messageWorker.send(message);
                        }
                    }
                    message = worker.pool();
                }
            }
            try {
                Thread.sleep(MSG_PROCESSING_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
        if (!isRunning) {
            for (MessageWorker worker : workers) {
                worker.close();
            }
            executor.shutdown();
            logger.info("Shutdown");
        }
    }
}
