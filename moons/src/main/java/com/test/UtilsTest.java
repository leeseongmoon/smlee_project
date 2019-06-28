package com.test;

public class UtilsTest {

    public static void main(String[] args) {

        System.out.println("["+Utils.lPadSpace("12345", 10)+"]");

        System.out.println("["+Utils.lPadZero("12345", 10)+"]");

        System.out.println("["+Utils.lPad("12345",'A', 10)+"]");

        System.out.println("["+Utils.rPadSpace("12345", 10)+"]");

        System.out.println("["+Utils.rPadZero("12345", 10)+"]");

        System.out.println("["+Utils.rPad("12345",'A', 10)+"]");

        System.out.println(Utils.getCompApiKey());
    }
}
