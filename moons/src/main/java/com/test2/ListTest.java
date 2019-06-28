package com.test2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {


        List<String> sheetlist1 = new ArrayList<String>();

        Collections.addAll(sheetlist1, "row","A","B","C","D");

        for(String str : sheetlist1){
            System.out.println(str);
        }
    }

}
