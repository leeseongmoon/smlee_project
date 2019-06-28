package com.designpattern;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class DP2Adapter {

    public static void goodMethod(Enumeration<String> enu){
        while (enu.hasMoreElements()){
            System.out.println(enu.nextElement());
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("leeseongmoon");
        list.add("kimjaeeun");
        list.add("leeyunseul");

        Enumeration<String> ite = new IteratorToEnumeration(list.iterator());
        DP2Adapter.goodMethod(ite);
    }

}

/*
 IteratorToEnumeration 클래스는 Iterator를 받아서 Enumeration 으로 변경시켜줍니다.
 */
class IteratorToEnumeration implements Enumeration<String>{

    private Iterator<String> iter;

    public IteratorToEnumeration(Iterator<String> iter){
        this.iter = iter;
    }

    public boolean hasMoreElements(){
        return iter.hasNext();
    }

    public String nextElement(){
        return iter.next();
    }
}
