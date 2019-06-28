package com.designpattern;

import com.test.Company;

import java.util.HashMap;
import java.util.Map;
import com.designpattern.PersonFactory.Person;

public class DP13Flyweight {

    public static void main(String[] args) {
        Person p1 = PersonFactory.getPerson("AAA");
        Person p2 = PersonFactory.getPerson("BBB");
        Person p3 = PersonFactory.getPerson("AAA");

        System.out.println(p1 == p2);
        System.out.println(p1 == p3);

    }
}

/**
 * Person class 및 Person을 Flyweight로 관리하는 Factory
 */
class PersonFactory{

    private static Map<String, Person> map = new HashMap<String, Person>();

    public synchronized static Person getPerson(String name){
        if(!map.containsKey(name)){
            Person tmp = new Person(name);
            map.put(name, tmp);
        }
        return map.get(name);
    }

    public static class Person{
        private final String name;
        private Person(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }
}
