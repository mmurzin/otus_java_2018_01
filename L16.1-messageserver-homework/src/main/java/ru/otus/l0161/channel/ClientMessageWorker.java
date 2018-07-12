package ru.otus.l0161.channel;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientMessageWorker extends SocketMessageWorker {
    private final Socket socket;
    private Logger logger;

    public ClientMessageWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    public ClientMessageWorker(Socket socket) {
        super(socket);
        this.logger = Logger.getLogger(ClientMessageWorker.class.getSimpleName());
        this.socket = socket;
    }

    public void close() {
        super.close();
        try {
            this.socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Closing socket error " + e.getMessage());
        }
    }
}
