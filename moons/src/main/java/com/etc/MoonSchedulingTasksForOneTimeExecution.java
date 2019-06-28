package com.etc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MoonSchedulingTasksForOneTimeExecution {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("Main thread starts here...");

        Timer timer = new Timer("Timer-thread", false); //false: user thread, true: daemon
        Date currentTime = new Date();
        System.out.println("["+currentThreadName+"] Current time" + dateFormatter.format(currentTime));


        Date scheduledTime = TimeUtils.getFutureTime(currentTime, 5000);
        
        ScheduledTask task0 = new ScheduledTask(100);
        timer.schedule(task0, scheduledTime); //schedule(TimerTask task, Date time)
        System.out.println("1["+currentThreadName+"] Task-0 is scheduled for running at " + dateFormatter.format(currentTime));

        long delayMillis = 10000;
        
        ScheduledTask task1 = new ScheduledTask(100);
        timer.schedule(task1, delayMillis); //schedule(TimerTask task, long delay)
        System.out.println("2["+currentThreadName+"] Task-1 is scheduled for running "+ delayMillis/1000 + " at " + dateFormatter.format(currentTime));

        ScheduledTask task2 = new ScheduledTask(100);
        timer.schedule(task2, delayMillis); //schedule(TimerTask task, long delay)
        System.out.println("3["+currentThreadName+"] Task-2 is scheduled for running "+ delayMillis/1000 + " at " + dateFormatter.format(currentTime));

        task1.cancel(); //task1 canceled

        TimeUnit.MILLISECONDS.sleep(12000);
        timer.cancel();
        System.out.println("Main thread ends here...");
    }
}


class ScheduledTask extends TimerTask {
    private static int count = 0;
    private long sleepTime;
    private String taskId;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    @Override
    public void run(){
        Date startTime = new Date();
        Date SchdulingForRunningTime = new Date(super.scheduledExecutionTime());
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId
                + " > Scheduled to run at "
                + dateFormatter.format(SchdulingForRunningTime)
                + ", Actually started at "
                + dateFormatter.format(startTime)
                + "####");

        for(int i = 0; i<5; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("#### <" + currentThreadName + "," + taskId
                + " >Finished at "
                + dateFormatter.format(new Date())
                + "####");
    }


    public ScheduledTask(long sleepTime) {
        this.sleepTime = sleepTime;
        this.taskId = "ScheduledTask-" + count++;
    }
}

/* Utility class to get the future time*/
class TimeUtils {
    private TimeUtils() {

    }
    public static Date getFutureTime(Date initialTime, long millisToAdd) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(initialTime.getTime() + millisToAdd);
        return cal.getTime();
    }
}
