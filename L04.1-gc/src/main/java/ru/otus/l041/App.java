package ru.otus.l041;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class App {

    private static HashSet<String> gcNamesSet = new HashSet<>();
    private static ArrayList<GcReportItem> gcReportItems = new ArrayList<>();
    private static final int PRINT_REPORT_INTERVAL = 60_000;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws Exception {
        System.out.println("Benchmark started " + dateFormat.format(new Date()) + "\n");
        installGCMonitoring();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                printReport();
                resetGcData();
            }
        }, 0, PRINT_REPORT_INTERVAL);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);
        mbean.run();
    }

    private static void resetGcData() {
        gcNamesSet = new HashSet<>();
        gcReportItems = new ArrayList<>();
    }

    private static void printReport() {
        for (String name : gcNamesSet) {
            int count = 0;
            long duration = 0;
            ListIterator<GcReportItem> iterator = gcReportItems.listIterator();
            while (iterator.hasNext()) {
                GcReportItem item = iterator.next();
                if (item.getName().equals(name)) {
                    count++;
                    duration += item.getDuration();
                    iterator.remove();
                }
            }
            System.out.println(dateFormat.format(new Date()) + "\nGC Name: " + name +
                    "\nCount: " + String.valueOf(count)
                    + ".\nDuration: " + msToSeconds(duration) + " seconds\n");
        }
    }

    private static String msToSeconds(long milliseconds) {
        return String.valueOf(milliseconds / 1_000.0);
    }

    private static void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            NotificationListener listener = (notification, handback) -> {

                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo
                            .from((CompositeData) notification.getUserData());

                    String name = info.getGcName();
                    gcNamesSet.add(name);
                    gcReportItems.add(new GcReportItem(name, info.getGcInfo().getDuration()));
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }
}
