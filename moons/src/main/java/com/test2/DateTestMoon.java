package com.test2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTestMoon {

    public static void main(String[] args) {


        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

        sdf.format(now);

        String sdCatid = null;

        sdCatid = sdf.format(now) + "1001";

        System.out.println(sdCatid);
    }
}
