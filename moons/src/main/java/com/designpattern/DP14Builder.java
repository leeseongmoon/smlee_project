package com.designpattern;

public class DP14Builder {

    public static void main(String[] args) {
        Builder builder = new BatmanBuilder();
        Director director = new Director(builder);

        director.build();
        Hero hero = director.getHero();
        hero.showResult();
    }
}

/**
 * 복잡한 과정을 거쳐서 만들어 지는 객체가 될 Hero 클래스
 */
class Hero{

    private String armSource;
    private String legSource;
    private String name;

    public Hero(String name){
        super();
        this.name = name;
    }

    public void setArmSource(String armSource) {
        this.armSource = armSource;
    }

    public void setLegSource(String legSource) {
        this.legSource = legSource;
    }

    public void showResult(){
        System.out.println(armSource +"로 만든 팔과 " + legSource +"로 만든 다리를 가진 " + name);
    }
}


/**
 * 복작한 Hero 객체를 만들어내기 위한 객체 생성과정을 관리하는 Builder 인터페이스
 */
interface Builder{
    void makeArm();
    void makeLeg();
    Hero getResult();
}

/**
 * 복잡한 Hero 객체를 실제로 만들어내는 Builder의 구현체인 배트맨 찍어내는 클래스
 */
class BatmanBuilder implements Builder{

    private Hero batman;
    BatmanBuilder(){
        batman = new Hero("배트맨");
    }

    public void makeArm(){
        batman.setArmSource("돈지랄");
    }

    public void makeLeg(){
        batman.setLegSource("돈지랄");
    }

    public Hero getResult(){
        return batman;
    }
}


/**
 * Builder를 관리해 주는 Director
 */
class Director {
    private Builder builder;
    public Director(Builder builder) {
        this.builder = builder;
    }
    public void build(){
        builder.makeArm();
        builder.makeLeg();
    }
    public Hero getHero(){
        return builder.getResult();
    }
}