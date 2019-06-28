package com.FCM;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class MoonFcmTest {

    public final static String AUTH_KEY_FCM = "AAAAfyq7cAo:APA91bHbB5R15a8uYPvn5DHUzXHwowDsI8m4LoZMilX8MsC78ZXZk8YF5P4sR7NDloj3W9NR4Zky_OWoySounMmXjXuiH70oJ-nIipS3w6xoww8mzQjyOM0nOJPa8sNZu9ZiDexD4th_";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static void main(String[] args) throws Exception{

        String _title = "타이틀넣기";
        String _body = "푸시메시지 넣기";
        String _token = "/topics/ALL";

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();

        info.put("title", _title);
        info.put("body", _body);

        json.put("notification", info);
        json.put("to", _token);

        String sendMsg = json.toString();

        OutputStream os = conn.getOutputStream();

        // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
        //os.write(input.getBytes("UTF-8"));
        os.write(sendMsg.getBytes("UTF-8"));
        os.flush();
        os.close();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output = "";
        StringBuffer resultMsg = new StringBuffer();
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            resultMsg.append(output);
        }
        br.close();
        conn.disconnect();


        System.out.println("===> resultMsg.toString() :: "+resultMsg.toString());
    }

}
