package com.designpattern;


public class DP4TemplateMethod {
    public static void main(String[] args) {
        Worker designer = new Designer();
        designer.work();

        Worker gamer = new Gamer();
        gamer.work();
    }
}

abstract class Worker{
    protected abstract void doit();

    public final void work(){
        System.out.println("START");
        doit();
        System.out.println("END");
    }
}

class Designer extends Worker{
    @Override
    protected void doit() {
        System.out.println("work design~!!!");
    }
}

class Gamer extends Worker{
    @Override
    protected void doit() {
        System.out.println("play Game~!!");
    }
}
