package com.etc;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

public class MoonJsonTest {

    @Test
    public void jsonTest(){

        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "name", "Mars" );
        data.put( "age", 32 );
        data.put( "city", "NY" );

        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put( "name", "Lee" );
        data2.put( "age", 39 );
        data2.put( "city", "IC" );

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

        list.add(data);
        list.add(data2);


        JSONObject json = new JSONObject();
        json.put( "data", data );
        json.put("list", list);

        System.out.printf( "JSON: %s", json.toString(2) );
    }

    @Test
    public void sha256Test(){

        String txt = "1234";

        try{

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(txt.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            //출력
            System.out.println(hexString.toString());

        } catch(Exception ex){
            throw new RuntimeException(ex);
        }


    }

}
