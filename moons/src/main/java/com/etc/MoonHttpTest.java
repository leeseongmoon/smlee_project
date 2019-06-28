package com.etc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MoonHttpTest {

    public static void main(String[] args) throws Exception {

        String url1 = "PAY_SELECT_LOGIN"; // 로그인
        String url2 = "PAY_SELECT_APPROVE"; // 승인정보 요청
        String url3 = "PAY_CANCEL_APPROVE"; // 승인취소 요청
        String url4 = "PAY_PUSH_ALARM"; // 푸시

        URL url = new URL("http://192.168.0.35/appapi/comps/"+url2+"/appRequest.do"); // 호출할 url
        Map<String,Object> params = new LinkedHashMap<String, Object>();

        // url1
        /*params.put("storeCode", "SDABB239330000100010");
        params.put("userId", "smartdata");
        params.put("userPassword", "1234");
        params.put("mobileType", "Android");*/

        // url2
        params.put("storeCode", "SDABB239330000100010");
        params.put("searchType", "ALL");
        params.put("searchStartData", "2018-09-13");
        params.put("searchEndData", "2018-09-14");

        // url3
        /*params.put("storeCode", "SDABB239330000100010");
        params.put("userId", "smartdata");
        params.put("txId", "SDABB239330000100010_REQ120180919184029");
        params.put("APPROVAL_NUMBER", "18410228");*/

        StringBuilder postData = new StringBuilder();

        for(Map.Entry<String,Object> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);

        conn.getOutputStream().write(postDataBytes); // POST 호출

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String inputLine;
        StringBuffer resultSb = new StringBuffer();

        while((inputLine = in.readLine()) != null) { // response 출력
            resultSb.append(inputLine);
            System.out.println("통신결과\n"+inputLine);
        }

        in.close();

        JSONObject moonJson = new JSONObject(resultSb.toString());
        //String resultMap = moonJson.get("resultMap").toString();
        //String resultList = moonJson.get("resultList").toString();

        System.out.println("==> moonJson : "+moonJson.toString());
        //System.out.println("==> resultMap : "+moonJson.get("resultMap").toString());
        //System.out.println("==> resultList : "+moonJson.get("resultList").toString());

    }

}
