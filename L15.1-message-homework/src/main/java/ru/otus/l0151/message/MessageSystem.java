package ru.otus.l0151.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
    private static final int DEFAULT_STEP_TIME = 10;

    private final List<Thread> workers;
    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap;
    private final Map<Address, Addressee> addresseeMap;
    private static MessageSystem instance;

    private MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
        startWorkerByAddress(addressee);
    }

    private void startWorkerByAddress(Addressee addressee) {
        Address key =  addressee.getAddress();
        String name = "MS-worker-" + key.getId();
        Thread thread = new Thread(() -> {
            while (true) {
                ConcurrentLinkedQueue<Message> queue = messagesMap.get(key);
                while (!queue.isEmpty()) {
                    Message message = queue.poll();
                    message.exec(addressee);
                }
                try {
                    Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                } catch (InterruptedException e) {
                    logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                    return;
                }
                if (Thread.currentThread().isInterrupted()) {
                    logger.log(Level.INFO, "Finishing: " + name);
                    return;
                }
            }
        });
        thread.setName(name);
        thread.start();
        workers.add(thread);
    }

    public void sendMessage(Message message) {
        ConcurrentLinkedQueue<Message> queue = messagesMap.get(message.getTo());
        if(queue != null){
            queue.add(message);
        }else {
            logger.log(Level.INFO, "queue is null by address "+message.getClass().getSimpleName());
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }

    public static MessageSystem getMessageSystem() {
        if(instance == null){
            instance = new MessageSystem();
        }
        return instance;
    }
}
