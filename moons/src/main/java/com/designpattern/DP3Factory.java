package com.designpattern;

public class DP3Factory {
    public static void main(String[] args) {
        Animal a1 = AnimalFactory.create("cow");
        a1.printDescription();

        Animal a2 = AnimalFactory.create("cat");
        a2.printDescription();

        Animal a3 = AnimalFactory.create("dog");
        a3.printDescription();
    }
}


interface Animal{
    public void printDescription();
}

class AnimalFactory{
    public static Animal create(String animalName){
        if(animalName == null){
            throw new IllegalArgumentException("Null is NO NO NO");
        }

        if(animalName.equals("cow")){
            return new Cow();
        }else if(animalName.equals("cat")){
            return new Cat();
        }else if(animalName.equals("dog")){
            return new Dog();
        }else{
            return null;
        }
    }
}

class Cat implements Animal{
    public void printDescription() {
        System.out.println("I'm Cat");
    }
}

class Cow implements Animal{
    public void printDescription() {
        System.out.println("I'm Cow");
    }
}

class Dog implements Animal{
    public void printDescription() {
        System.out.println("I'm Dog");
    }
}