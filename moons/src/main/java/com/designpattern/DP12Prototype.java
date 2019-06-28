package com.designpattern;

import java.util.Date;

public class DP12Prototype {

    public static void main(String[] args) {
        Complex com = new Complex("very ver Complete Infomation");

        try{
            Complex cloned1 = (Complex) com.clone();
            cloned1.setDate(new Date(2019,0,1));

            Complex cloned2 = (Complex) com.clone();
            cloned2.setDate(new Date(2019,2,1));

            System.out.println(com.getDate());
            System.out.println(cloned1.getDate());
            System.out.println(cloned2.getDate());
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
}

class Complex implements Cloneable{

    private String complexInfo;

    private Date date;

    public Complex(String complexInfo){
        this.complexInfo = complexInfo;
    }

    public String getComplexInfo(){
        return complexInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        Complex tmp = (Complex) super.clone();
        return tmp;
    }
}

