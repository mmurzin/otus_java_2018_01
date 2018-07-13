package ru.otus.l0161;

import ru.otus.l0161.process.ProcessRunnerImpl;
import ru.otus.l0161.server.SocketServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static final String DEFAULT_LOGIN = "admin";
    public static final String DEFAULT_PASSWORD = "123456";

    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final String START_DB_SERVICE_COMMAND = "java -jar ../../L16.1-dbserve-homework/target/dbservice.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new App().startServer();
    }

    private void startServer() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //startDbService(executorService);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        SocketServer server = new SocketServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startDbService(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(START_DB_SERVICE_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Starting DB Service error", e);
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }


}
