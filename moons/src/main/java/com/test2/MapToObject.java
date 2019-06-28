package com.test2;

import sun.applet.resources.MsgAppletViewer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapToObject {

    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name","이성문");
        map.put("id","bloodmun");
        map.put("passwd","dltjdans");
        map.put("sex","male");

        TestVo vo = new TestVo();


        convertMaptoObject(map, vo);

        //printVo("",vo);

    }

    public static Object convertMaptoObject(Map<String, Object> map, Object obj){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;

        Iterator iter = map.keySet().iterator();

        while(iter.hasNext()){

            keyAttribute = (String) iter.next();
            methodString = setMethodString + keyAttribute.substring(0,1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();

            for(int i=0; i<methods.length; i++){
                if(methodString.equals(methods[i].getName())){
                    try{
                        methods[i].invoke(obj, map.get(keyAttribute));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return obj;
    }

    public static void printVo(String title, Object obj){
        try {

            System.out.println("====================>title ["+title+"]");

            for (Field field : obj.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value=field.get(obj);
                System.out.println("=====> name ["+field.getName()+"] / value ["+value+"]");
            }
        } catch (Exception e) {

        }
    }
}

class TestVo{

    private String name;
    private String id;
    private String passwd;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
