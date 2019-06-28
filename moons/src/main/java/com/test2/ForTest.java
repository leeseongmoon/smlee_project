package com.test2;

import com.oracle.javafx.jmx.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;

public class ForTest {

    /**
     *  list 내에 동일한 ID를 가진 놈들끼리 merge
     */

    public static void main(String[] args) throws Exception {

        String jsonArrStr =
                "{\"arlist\":" +
                        "[ " +
                            "{ \"id\": \"1\" ,  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"4\" ,  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"2\" ,  \"Name1\": \"승인\" }" +
                            "{ \"id\": \"3\" ,  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"14\",  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"4\" ,  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"1\" ,  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"5\" ,  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"9\" ,  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"17\",  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"5\" ,  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"3\" ,  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"5\" ,  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"20\",  \"Name1\": \"승인\" }" +
                           ",{ \"id\": \"5\" ,  \"Name1\": \"취소\" }" +
                           ",{ \"id\": \"11\",  \"Name1\": \"승인\" }" +
                        "]}";

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(jsonArrStr);

        JSONObject jsonObject = new JSONObject();
        jsonObject = (JSONObject) obj;

        JSONArray jsonArr = (JSONArray) jsonObject.get("arlist");

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.add((JSONObject) jsonArr.get(i ));
            System.out.println("list get i" + jsonValues.get(i));
        }

        Collections.sort(jsonValues, new Comparator<JSONObject>() {

            private static final String KEY_NAME = "id";

            public int compare(JSONObject a, JSONObject b) {
                /*String valA = new String();
                String valB = new String();*/

                Integer valA1 = 0;
                Integer valB1 = 0;

                try {
                    /*valA = (String) a.get(KEY_NAME);
                    valB = (String) b.get(KEY_NAME);*/

                    valA1 = Integer.parseInt(a.get(KEY_NAME).toString());
                    valB1 = Integer.parseInt(b.get(KEY_NAME).toString());
                }
                catch (JSONException e) {
                    //do something
                }

                return valA1.compareTo(valB1);
            }
        });


        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listmap2 = new LinkedList<Map<String, Object>>();

        for (int i = 0; i < jsonArr.size(); i++) {
            listmap2.add(jsonValues.get(i));
        }


        Map<String, Object> mergeMap = new HashMap<String, Object>();

        for (int i = 0; i < listmap2.size(); i++) {

            if(i==0){

                mergeMap.put("id",listmap2.get(i).get("id"));

                if("승인".equals(listmap2.get(i).get("Name1"))){
                    mergeMap.put("aa",listmap2.get(i).get("Name1"));
                }else{
                    mergeMap.put("bb",listmap2.get(i).get("Name1"));
                }

            }else{

                    if(jsonArr.size() ==  i+1){

                        mergeMap = new HashMap<String, Object>();; // 초기화

                        if(listmap2.get(i).get("id").equals(listmap2.get(i-1).get("id"))){

                            mergeMap.put("id",listmap2.get(i).get("id"));

                            if("승인".equals(listmap2.get(i).get("Name1"))){
                                mergeMap.put("aa",listmap2.get(i).get("Name1"));
                            }else{
                                mergeMap.put("bb",listmap2.get(i).get("Name1"));
                            }

                            listmap.add(mergeMap);

                        }else{

                            mergeMap.put("id",listmap2.get(i).get("id"));

                            if("승인".equals(listmap2.get(i).get("Name1"))){
                                mergeMap.put("aa",listmap2.get(i).get("Name1"));
                            }else{
                                mergeMap.put("bb",listmap2.get(i).get("Name1"));
                            }

                            listmap.add(mergeMap);

                        }

                    }else{

                        if(listmap2.get(i).get("id").equals(listmap2.get(i-1).get("id"))){

                            mergeMap.put("id",listmap2.get(i).get("id"));

                            if("승인".equals(listmap2.get(i).get("Name1"))){
                                mergeMap.put("aa",listmap2.get(i).get("Name1"));
                            }else{
                                mergeMap.put("bb",listmap2.get(i).get("Name1"));
                            }

                        }else{

                            mergeMap = new HashMap<String, Object>();; // 초기화

                            mergeMap.put("id",listmap2.get(i).get("id"));

                            if("승인".equals(listmap2.get(i).get("Name1"))){
                                mergeMap.put("aa",listmap2.get(i).get("Name1"));
                            }else{
                                mergeMap.put("bb",listmap2.get(i).get("Name1"));
                            }
                        }

                        if(!listmap2.get(i).get("id").equals(listmap2.get(i+1).get("id"))){
                            listmap.add(mergeMap);
                        }

                    }
            }
        }


        // 출력 확인
        for(int j =0; j < listmap.size(); j++ ){
            System.out.println("==> "+listmap.get(j));
        }

    }
}
