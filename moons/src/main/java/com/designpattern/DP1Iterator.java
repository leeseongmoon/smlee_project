package com.designpattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DP1Iterator implements Iterable<String> {

    public static void main(String[] args) {
        DP1Iterator dp1Iterator = new DP1Iterator();

        dp1Iterator.add("이터");
        dp1Iterator.add("Jun");
        dp1Iterator.add("John");

        Iterator<String> iterator = dp1Iterator.iterator();

        while(iterator.hasNext()){
            String element = iterator.next();
            System.out.println(element);
        }
    }

    private List<String> list = new ArrayList<String>();

    public void add(String name){
        list.add(name);
    }

    public Iterator<String> iterator(){
        return new Iterator<String>() {

            int seq = 0;

            public boolean hasNext() {
                return seq < list.size();
            }

            public String next() {
                return list.get(seq++);
            }

            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }



}
