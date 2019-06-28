package com.FCM;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class MoonFcmTestSmartData {

    public final static String AUTH_KEY_FCM = "AAAAfyq7cAo:APA91bHbB5R15a8uYPvn5DHUzXHwowDsI8m4LoZMilX8MsC78ZXZk8YF5P4sR7NDloj3W9NR4Zky_OWoySounMmXjXuiH70oJ-nIipS3w6xoww8mzQjyOM0nOJPa8sNZu9ZiDexD4th_";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static void main(String[] args) throws Exception{

        //String token = tokenList.get(count).getDEVICE_ID();

        String _title = "스마트데이터 앱 알림";
        String _body = "가맹점 승인정보가 도착했습니다.";
        String _actionType = "new";
        String _storeCode = "SDABB239330000100010";
        //String _token = "/topics/ALL"; // 전체
        String _token = "dl60nc4Zy8s:APA91bEAgHQWgwdDKCyuw6YWNyIsbsWGVdV_x2qYevkLHJz6eTM4OaTjgtlu7O8B4MlXR23__i74_tWwzoRmpm96KOWOmJBVEEcmrF8vg3TnqGTnV67lzN-gmXWQsOD5tZ0gQcy7dwUp"; // 개인

        final String apiKey = AUTH_KEY_FCM;
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + apiKey);

        conn.setDoOutput(true);


        // 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
        String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";

        // 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
        //String input = "{\"notification\" : {\"title\" : \" 여기다 제목넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\" 여기가 받을 사람 토큰  \"}";

        JSONObject json = new JSONObject();
        JSONObject notification = new JSONObject();
        JSONObject data = new JSONObject();

        notification.put("title", _title);
        notification.put("body", _body);

        data.put("actionType", _actionType);
        data.put("storeCode", _storeCode);

        json.put("notification", notification);
        json.put("to", _token);
        json.put("data", data);

        String sendMsg = json.toString();

        OutputStream os = conn.getOutputStream();

        // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
        //os.write(input.getBytes("UTF-8"));
        os.write(sendMsg.getBytes("UTF-8"));
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + input);
        System.out.println("Post parameters2 : " + sendMsg);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print result
        System.out.println(response.toString());
    }

}
