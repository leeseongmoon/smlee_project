package com.designpattern;

import java.util.Observable;
import java.util.Observer;

public class DP11Observer {

    public static void main(String[] args) {
        Watcher watcher = new Watcher();

        Employee pc1 = new Employee("만화책 보는 놈");
        Employee pc2 = new Employee("퍼질러 자는 놈");
        Employee pc3 = new Employee("포카치는 놈");

        // spy는 pc3을 보고 있음
        Spy spy = new Spy(pc3);

        // watcher한테 observer로 등록
        watcher.addObserver(pc1);
        watcher.addObserver(pc2);
        watcher.addObserver(pc3);
        watcher.addObserver(spy);
        watcher.action("사장 뜸~~", "employer");
        watcher.action("잡상인 옴~!!", "job");


        watcher.deleteObserver(pc3);
        watcher.deleteObserver(spy);
        watcher.action("사장 뜸~~~", "employer");
        watcher.action("밥 옴~!!", "bob");
    }

}

/**
 * 변화를 통보하는 Observable
 */
class Watcher extends Observable{
    public void action(String string, String who){
        System.out.println("======="+string+"========");

        if(who.equals("employer")){

            setChanged(); // 변화가 일어났다는 것을 알

        }else{
            System.out.println("아무도 신경 안씀~!! ㅜㅜ");
        }

        notifyObservers(string); // <-- 얘가 Employee의 upate() 호
    }
}

/**
 * 변화를 통보받는 직원
 */
class Employee implements Observer{

    private String desc;

    public Employee(String desc){
        this.desc = desc;
    }

    public void update(Observable ob, Object arg){
        // arg는 notifyObservers()의 인자값

        if(arg.toString().contains("사장")){
            System.out.print("사장이 떴네~!!?????");
        }

        if(ob instanceof Watcher){
            System.out.println(desc + "이 일하는 척하네 ㅋ");
        }
    }

    public String getDesc(){
        return desc;
    }
}

/**
 * 변화를 통보받는 사장 끄나플
 */
class Spy implements Observer{

    private Employee employee;

    public Spy(Employee employee){
        this.employee = employee;
    }

    public void update(Observable ob, Object arg){

        if(arg.toString().contains("사장")){
            System.out.print("사장이 떴네~!!?????");
        }

        if(ob instanceof Watcher){
            System.out.println("고자질쟁이가 "+employee.getDesc() +"이 놀고 있었다고 고자질하네 ㅋ");
        }
    }
}
