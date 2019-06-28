package com.etc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class MoonTimerTest {

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private String value;
    
    public void insertValue(String value){
        this.value = value; 
    }
    
    public void sys(){
        System.out.println("==========> value :: "+value);
    }

    public static void main(String[] args) throws InterruptedException{

        Date now = new Date();

        System.out.println("============== START =============[ " + dateFormatter.format(now)+ " ]");
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("Main thread starts here..." + currentThreadName);


        Timer smTimer = new Timer("payment", false);
        scjTask check1task = new scjTask("check1task");

        smTimer.schedule(check1task, 0, 1000);
        //smTimer.cancel();

        TimeUnit.MILLISECONDS.sleep(15000);
        System.out.println("============== END =============[ " + dateFormatter.format(new Date())+ " ]");
        smTimer.cancel();

    }

}


class scjTask extends TimerTask{

    private static int count = 0;
    private long sleepTime;
    private String taskId;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    MoonTimerTest tt = new MoonTimerTest();

    @Override
    public void run() {

        for(int i = 0; i < 15; i++){
            if(i == 3){
                
            }
        }

    }

    public scjTask(String taskId) {
        this.taskId = taskId;
        this.count++;
    }
}
