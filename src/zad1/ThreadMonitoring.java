package zad1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

// specjalna klasa monitorująca wątki

public class ThreadMonitoring {
    public static void printThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        System.out.println("Active thread count: " + threadMXBean.getThreadCount());
        for (long threadId : threadMXBean.getAllThreadIds()) {
            System.out.println(threadMXBean.getThreadInfo(threadId));
        }
    }
}

