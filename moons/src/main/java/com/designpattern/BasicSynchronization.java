package com.designpattern;

public class BasicSynchronization {
    private String mMessage;

    public static void main(String[] agrs) {
        BasicSynchronization temp1 = new BasicSynchronization();
        BasicSynchronization temp2 = new BasicSynchronization(); // 추가

        System.out.println("Test start!");

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                temp1.callMe("Thread1");
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                //temp1.callMe("Thread2");
                temp1.callMe("Thread2"); // temp2 로 변경
           }
        }).start();

        System.out.println("Test end!");
    }

    public synchronized void callMe(String whoCallMe) {
    //public void callMe(String whoCallMe) { // synchronized 삭
        // 1. parameter 값을 멤버변수에 저장하고
        mMessage = whoCallMe;

        try {
            // 2. 랜덤하게 sleep한 후
            long sleep = (long) (Math.random() * 100);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. 멤버변수와 parameter와 값이 같지 않으면 로그를 찍습니다.
        if (!mMessage.equals(whoCallMe)) {
            System.out.println(whoCallMe + " | " + mMessage);
        }
    }


}
