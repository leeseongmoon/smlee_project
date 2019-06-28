package com.designpattern;

public class DP5Singleton {

    public static void main(String[] args) {
        DP5Singleton ds = new DP5Singleton();

        ds.Amethod();
        ds.Bmethod();

    }

    public void Amethod(){
        SingletonCounter sc = SingletonCounter.getInstance();
        System.out.println("Amethod에서 카운터 호출 " + sc.getNextInt() );

    }

    public void Bmethod(){
        SingletonCounter sc = SingletonCounter.getInstance();
        System.out.println("Bmethod에서 카운터 호출 " + sc.getNextInt() );

    }
}


class SingletonCounter{
    private static SingletonCounter singleton = new SingletonCounter();

    private int cnt = 0;

    private SingletonCounter(){

    }

    public static SingletonCounter getInstance(){
        return singleton;
    }

    public int getNextInt(){
        return ++cnt;
    }
}

/**
 * 클래스 로드 시 new 가 실행 됨.
 * 항상 1개의 인스턴스를 가지게 되며 코드가 가장 짧고 쉬움
 * 성능도 다른 방법에 비해 좋음
 */
class Singleton1{
    private static final Singleton1 single = new Singleton1();
    public static Singleton1 getInstance(){
        return single;
    }
    private Singleton1(){

    }
}

/**
 * 클래스 로드시에는 인스턴스를 생성하지 않는다.
 * getInstance()가 처음 호출 될때 생성 됨
 * 그러나 synchronized 가 걸려 있어서 성능이 좋지 않다.
 * 인스턴스를 사용할 필요가 없을 때는 인스턴스가 생성되지 않는 다는 점이 첫번째 방법에 비해 장점
 */
class Singleton2{
    private static Singleton2 single;
    public static synchronized Singleton2 getInstance(){
        if(single == null){
            single = new Singleton2();
        }

        return single;
    }

    private Singleton2(){

    }
}

/**
 * 첫번째의 장점인 성능이 좋다(synchronized 가 안 걸려서)와 두번째의 장점인 안쓸때는 아예 만들지 않느다는 장점만 뽑아온 방법
 * 여기서 중요한 점은 if(single == null) 을 두 번이나 체크
 * A, B 2개의 thread가 접근을 한다고 가정
 * A와 B가 거의 동시에 들어와서 바깥쪽 single== null 인 부분을 통과했다고 칩시다.
 * 그리고 A가 조금 먼저 synchronized 블럭에 진입했습니다. B는 그 앞에서 대기 중이지요.
 * A가 다시 single== null을 체크합니다. 여전히 null이지요. 그러면 인스턴스를 만들고 synchronized 블럭을 탈출합니다.
 * 그러면 B가 synchronized 안으로 진입합니다. single은 더 이상 null이 아닙니다.
 * A가 만들었으니까요. B는 그냥 synchronized 블럭을 빠져나옵니다.
 * 바깥쪽 if(single == null) 가 없다면, 성능 저하가 발생합니다.
 * 매번 synchronized 블럭 안으로 들어가니까요. 두번째 방법과 같다고 보시면 됩니다.
 * 안쪽의 if(single == null) 가 없다면, singleton이 보장되지 않습니다.
 * volatile 키워드도 꼭 써줘야 합니다. volatile 키워드는 변수의 원자성을 보장합니다.
 * single = new Singleton3(); 이란 구문의 실행은 원자성이 아닙니다.
 * (원자성이란 JVM이 실행하는 최소단위의 일을 말합니다. 즉 객체 생성은 JVM이 실행하는 최소단위가 몇 번 실행되어야 완료되는 작업이란 뜻입니다.)
 * JVM에 따라서 single이라는 변수의 공간만을 먼저 생성하고 초기화가 나중에 실행되는 경우도 있습니다.
 * 변수의 공간만 차지해도 null은 아니기 때문에 singleton이 보장된기 어렵습니다.
 * JVM 버전이 1.4(어쩌면 1.5 잘 기억이..--;; ) 이전에서는 volatile 키워드가 정상적으로 작동하지 않을 수도 있다고 합니다.
 */
class Singleton3{
    private volatile static Singleton3 single;
    public static Singleton3 getInstance(){
        if(single == null){

            synchronized (Singleton3.class){
                if(single == null){
                    single = new Singleton3();
                }
            }
        }
        return single;
    }

    private Singleton3(){

    }
}

/**
 * 내부 클래스를 사용하는 방법
 * 기존의 3가지 방법에서는 Singleton 클래스가 자기 자신의 타입을 가지는 멤버변수를 가지고 있는데
 * 여기에서는 내부 클래스가 가지고 있다.
 * 내부 클래스가 호출되는 시점에 최총 생성이 되기 때문에 속도도 빠르고 필요치 않다면 생성하지도 않는다.
 */
class Singleton4{
    private Singleton4(){

    }
    private static class SingletonHolder{
        static final Singleton4 single = new Singleton4();
    }
    public static Singleton4 getInstance(){
        return SingletonHolder.single;
    }
}