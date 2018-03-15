package ru.otus.l041;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception{
        installGCMonitoring();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);
        mbean.run();
    }

    private static void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            System.out.println(gcbean.getName());

            NotificationListener listener = (notification, handback) -> {

                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();
                    String gctype = info.getGcAction();

                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                    System.out.println(dateFormat.format(new Date()) +" "+gctype + ": - "
                            + info.getGcInfo().getId() + ", "
                            + info.getGcName()
                            + " (from " + info.getGcCause() + ") " + duration + " milliseconds");

                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }
}
