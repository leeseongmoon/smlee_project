package com.test;


import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    

    /**
     * 날짜 구하기
     * @param pattern
     * @param addMonth
     * @return
     *
     * ex) getToday("yyyy/MM/dd", -3)
     */
    public static String getToday(String pattern, int addMonth){

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, addMonth);

        return sdf.format(calendar.getTime());
    }

    /**
     *   ENC_SHA256() => SHA256 방식의 암호화
     */
    public static String ENC_SHA256(String txt) throws Exception {

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
            return hexString.toString();

        } catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }

    public static String ENC_SHA256s(String txt) throws Exception {
        StringBuffer sbuf = new StringBuffer();

        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        mDigest.update(txt.getBytes());

        byte[] msgStr = mDigest.digest() ;

        for(int i=0; i < msgStr.length; i++){
            byte tmpStrByte = msgStr[i];
            String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);

            sbuf.append(tmpEncTxt) ;
        }

        return sbuf.toString();
    }


    /**
     * ENC_MD5() => MD5 암호화
     * <pre>
     * MD5(Message-Digest algorithm 5)는 128비트 암호화 해시 함수로 주로 프로그램이나 파일이 원본 그대로인지를 확인하는 무결성 검사 등에 사용되는 암호화의 한 종류 입니다.
     * 암호화에 있어서는 치명적인 결함이 지속적으로 발견 되어 보안용도로 사용은 권장되지 않는 암호화 방식입니다.
     * 만약 보안용도로 사용되는 경우 시스템에 심각한 보안 문제를 발생 시킬수 있으므로  보안상 이슈가 발생 되지 않는 범위에서 사용 해야 합니다.
     *
     * 해쉬 함수는 기본적으로 복호화를 목적으로 하지 않습니다.
     * </pre>
     */
    public static String ENC_MD5(){

        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
        System.out.println(sdf.format(now));
        String str = "ABCDEFGHIJKLMN" + sdf.format(now);

        try {

            // create MD5 Hash
            MessageDigest  digest = java.security.MessageDigest.getInstance("MD5");

            digest.update(str.getBytes());

            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();

            for(int i=0; i < messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);

                while(h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            System.out.println("[MD5] ========> hexString.toString() ::: "+hexString.toString());


            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 오브젝트 데이터 Null 체크
     * Null 일 경우 true
     * Null 이 아닐경우 false
     */
    public static boolean isEmpty(Object object) throws Exception {

        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            String str = (String) object;
            return str.length() == 0;
        }

        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            return collection.size() == 0;
        }

        if (object.getClass().isArray()) {
            try {
                if (Array.getLength(object) == 0) {
                    return true;
                }
            } catch (Exception e) {
                //do nothing
            }
        }

        return false;
    }

    /**
     * getMaxByteString() => 파라미터 String str 을 byte로 계산하여 maxLen의 길이만큼만 리턴해준다.
     * [ 길이 이후 문자는 버림 ]
     */
    public static String getMaxByteString(String str, int maxLen) throws Exception {
        StringBuilder sb = new StringBuilder();
        int curLen = 0;
        String curChar;

        str = str.trim();
        str = str.replace(" ", "");

        for (int i = 0; i < str.length(); i++){
            curChar = str.substring(i, i + 1);
            curLen += curChar.getBytes().length;

            if (curLen > maxLen)
                break;
            else
                sb.append(curChar);
        }
        return sb.toString();
    }

    /**
     * 문자에 공백을 채워준다.
     */
    public static String execformatString(String arg, int num) throws Exception {
        arg = getMaxByteString(arg, num); // 전문텍스트 길이 이후 문자는 버림
        if( num !=  byteCheckLength(arg) ){
            int deffscore = num - byteCheckLength(arg) ;
            for(int i=0; i < deffscore ; i++){
                arg = arg+ " ";
            }
        }
        return arg;
    }

    /**
     *  execFormatStringIncludeNull(String arg0, int num)
     */
    public static String execFormatStringIncludeNull(String arg0, int num) throws Exception{

        StringBuilder returnSb = new StringBuilder();
        int curLen = 0;
        String curChar;
        String returnStr = null;

        for (int i = 0; i < arg0.length(); i++){
            curChar = arg0.substring(i, i + 1);
            curLen += curChar.getBytes().length;

            if (curLen > num)
                break;
            else
                returnSb.append(curChar);
        }

        returnStr = returnSb.toString();

        if( num !=  byteCheckLength(returnStr) ){
            int deffscore = num - byteCheckLength(returnStr) ;
            for(int i=0; i < deffscore ; i++){
                returnStr = returnStr+ " ";
            }
        }
        return returnStr;

    }

    /**
     * byteCheckLength() => 문자의 byte 를 계산해서 리턴
     */
    public static int byteCheckLength(String txt){
        int curLen = 0;
        String curChar;
        for (int i = 0; i < txt.length(); i++){
            curChar = txt.substring(i, i + 1);
            curLen += curChar.getBytes().length;
        }
        return curLen;
    }

    /**
     *  checkValueTypeMap() => 필수 데이터의 경우 맵으로 받아서 Null 체크후 Exception 처리
     */
    public static void checkValueTypeMap(Map<String,Object> checkData) throws Exception{

        String key = null;
        Object value = null;
        boolean chekflag = false;

        for(Map.Entry<String, Object> entry : checkData.entrySet()){
            key = entry.getKey();
            value = entry.getValue();

            if(isEmpty(value)){
                chekflag = true;
                break;
            }
        }

        if(chekflag){
            throw new Exception(key+"의 값은 필수 값이므로 null 일 수 없습니다.");
        }

    }

    /**
     * 문자가 null이면 빈문자열을 리턴한다.
     * @param input 원본 문자열
     * @return String output 변환된 문자열
     */
    public static String nvl(Object input){
        String output = input!=null?String.valueOf(input):"";
        return output;
    }

    public static String nullData(Object obj){

        String returnStr = "";

        if (obj != null)
            returnStr = obj.toString();


        return returnStr;
    }

    /**
     * Map<String, Object> 에 담긴 내용을 출력한다.
     * @param fileName 파일명
     * @param method 메소드명
     * @param map 맵
     */
    public static void printParameterLogByMap(String fileName, String method, Map<String, Object> map){
        System.out.println("====> FILE NAME : ["+fileName+"] / Method : ["+method+"] ");
        for(Map.Entry<String, Object> entry : map.entrySet()){
            System.out.println("==> [" + entry.getKey() + "] : " + entry.getValue());
        }
        System.out.println("");

    }

    /**
     * getKsc5601() => KSC5601 한글 변환 함수
     */
    public static String getKsc5601(byte[] byteData) throws UnsupportedEncodingException {
        String outValue = "";
        outValue = new String(byteData, "KSC5601");
        return outValue;
    }


    /**
     * hex string to byte[]
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    /**
     * byte[] to hex sting
     */
    public static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer(ba.length * 2);
        String hexNumber;
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

    /**
     * ENC_RSA() => RSA 암호화
     */
    public static final String PUBLIC_KEY = "30819f300d06092a864886f70d010101050003818d0030818902818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001";
    public static final String PRIVATE_KEY = "30820278020100300d06092a864886f70d0101010500048202623082025e02010002818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001028181009b64f9234e5fbc7f505fd35de4d4d25646c77865b6b8399f990be5121678802e870247429e4bf4529d210b25e5e18a94834edf12cdd147c9b268e13c5af2ba5486e206e591d5de96e9e750044a896195bf5ca4bb17096c5adac349c7dd8ab8a6be82221406677130210c3184cbe4c7c4226f9af919ec5e179553cf7dae1f4e31024100e5ddb2aedcf9a0b1ebf215bb049c73fc4ded6257e82c25874a1cb07019ead2d310f0c2e8a88dc0f96c592cad9ec6f78973a9f224177aa6db39eeefe40a1ad5fd024100e5cd9dcdb19335c9b98de22f8a9fd65e79a986c41dc56e6db4fa8a006e68c2273d813e6863f312dd4c09daae9bc71eeafa6a5abcd34e8316584ed27e27c2f4dd02404255179b1e696ad5ed208ee4c90fdce892144eaccf72ede17ca18ac8ceb1d4e4d39ea6a03d03ab0c4f17ecacad84fd29cd16dda94c9d38494b0e886b65ff1881024100dcfc0399ffff5e2424698a6ec951b765967d2d797e5f9337b0679539a0f2e071b7b5877bff518a7c8058a1907380e1fc78deb96f078c6286a458f81614ca6789024100bfb09d592efad4e00b17ae303afddaea6d5377300320615c965ef956a8714ebc6e9b1815a72757502ae9054db34ce5fc648d258fc50a0f46b123ccd8e54b2c88";

    public static String ENC_RSA(String encStr) throws Exception{

        //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE"); // 알고리즘 명 / Cipher 알고리즘 mode / padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING"); // 알고리즘 명 / Cipher 알고리즘 mode / padding

        X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(hexToByteArray(PUBLIC_KEY));

        KeyFactory ukeyFactory = KeyFactory.getInstance("RSA");

        PublicKey publickey = null;

        try {
            // PublicKey에 공용키 값 설정
            publickey = ukeyFactory.generatePublic(ukeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] input = encStr.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, publickey);

        byte[] cipherText = cipher.doFinal(input);

        return byteArrayToHex(cipherText);
    }

    /**
     * DEC_RSA() => RSA 복호화
     */
    public static String DEC_RSA(String decStr) throws Exception{

        //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");

        PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(hexToByteArray(PRIVATE_KEY));
        KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;

        try {
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 복호
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = cipher.doFinal(hexToByteArray(decStr));

        String returnStr = new String(plainText);

        //날짜영역 제거
        returnStr = returnStr.substring(0,returnStr.length()-6);

        return returnStr;
    }

    /**
     * ENC_RSA2() => RSA 암호화 - base64
     */
//    public static String ENC_RSA_base64(String encStr) throws Exception{
//
//        String nicePublicKey = Constants.NICE_PUBLIC_KEY.replaceAll("\\n", "").replaceAll("\\r", "");
//
//        byte[] bPublickey = Base64.decodeBase64(nicePublicKey.getBytes());
//
//        Cipher cipher = Cipher.getInstance("RSA");
//
//        X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(bPublickey);
//
//        KeyFactory ukeyFactory = KeyFactory.getInstance("RSA");
//
//        PublicKey publickey = null;
//
//        try {
//            // PublicKey에 공용키 값 설정
//            publickey = ukeyFactory.generatePublic(ukeySpec);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        byte[] input = encStr.getBytes();
//        cipher.init(Cipher.ENCRYPT_MODE, publickey);
//
//        byte[] cipherText = cipher.doFinal(input);
//
//        return Base64.encodeBase64String(cipherText);
//    }

    /**
     * DEC_RSA2() => RSA 복호화 - base64
     */
    public static String DEC_RSA_base64(String decStr) throws Exception{

        byte[] bprivKeyStr = Base64.decodeBase64(PRIVATE_KEY.getBytes());

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE");

        PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(bprivKeyStr);
        KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;

        try {
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 복호
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bdecStr = Base64.decodeBase64(decStr.getBytes());

        byte[] plainText = cipher.doFinal(bdecStr);

        String returnStr = new String(plainText);

        //날짜영역 제거
        returnStr = returnStr.substring(0,returnStr.length()-6);

        return returnStr;
    }

    /**
     * KISA_SEED로 암호화, base를 key로 암호화
     */
    public static byte[] ENC_SEED(byte[] key, byte[] base){

        int[] pdwRoundKey = new int[32];

        byte[] buff = new byte[128];
        byte[] encData = new byte[128];

        //System.arraycopy(buff, 0, base, 0, base.length);
        System.arraycopy(base, 0, buff, 0, base.length);

        SeedKisa.SeedRoundKey(pdwRoundKey, key);
        SeedKisa.SeedEncrypt(buff, pdwRoundKey, encData);

        return encData;
    }


    /**
     * FCM 푸쉬메시지 보내기
     */
    //public String SEND_FCM(EgovMap map) throws Exception{
    public static String SEND_FCM(Map<String, Object> map) throws Exception{

        // map 정보 ( storeCode, token )
        System.out.println("##### FCM 푸쉬메시지 보내기 map : " + map);
        
        final String AUTH_KEY_FCM = "AAAAfyq7cAo:APA91bHbB5R15a8uYPvn5DHUzXHwowDsI8m4LoZMilX8MsC78ZXZk8YF5P4sR7NDloj3W9NR4Zky_OWoySounMmXjXuiH70oJ-nIipS3w6xoww8mzQjyOM0nOJPa8sNZu9ZiDexD4th_";

        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        final String TITLE = "스마트데이터 앱 알림";
        final String BODY = "가맹점 승인정보가 도착했습니다.";

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);

        JSONObject json = new JSONObject();
        JSONObject notification = new JSONObject();
        JSONObject data = new JSONObject();

        StringBuffer response = new StringBuffer();
        String result = null;

        String mobileType = (String) map.get("mobileType");
        String storeCode = (String) map.get("storeCode");
        String pushToken = (String) map.get("pushToken");

        if(mobileType.equals("Android") || mobileType.equals("iOS")){

            if(mobileType.equals("Android")){
                data.put("title", TITLE);
                data.put("body", BODY);
            }else if(mobileType.equals("iOS")){
                notification.put("title", TITLE);
                notification.put("body", BODY);
                notification.put("sound" , "default");
                json.put("notification", notification);
            }

            data.put("actionType", "new");
            data.put("storeCode", storeCode);

            json.put("to", pushToken);
            json.put("data", data);

            String sendMsg = json.toString();

            OutputStream os = conn.getOutputStream();

            os.write(sendMsg.getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            /*System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters2 : " + sendMsg);
            System.out.println("Response Code : " + responseCode);*/

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            //System.out.println("==> FCM push result :: "+ response.toString());

            result = response.toString();

        }else{

            json.put("resultCode", "9999");
            json.put("resultMessage", "Android / iOS 이외의 모바일 타입에 대한 정의가 없음 ");
            result = json.toString();
        }

        return result;
    }


    /**
     * 지정된 길이만큼 왼쪽에 0을 채운다.
     * @param origin
     * @param length
     * @return
     */
    public static  String lPadZero(String origin, int length){
        return lPad(origin, '0', length);
    }

    /**
     * 지정된 길이만큼 왼쪽에 공백 을 채운다.
     * @param origin
     * @param length
     * @return
     */
    public static String lPadSpace(String origin, int length){
        return lPad(origin, ' ', length);
    }

    /**
     * 지정된 길이만큼 왼쪽에 특정 문자열을 채운다.
     * @param origin
     * @param letter
     * @param length
     * @return
     */
    public static String lPad(String origin, char letter, int length){
        if(origin == null){
            origin = "";
        }

        String result = "";
        int fillLength = length - origin.length();
        if (fillLength < 0){
            return origin;
        } else {
            for (int i = 0 ; i < fillLength ; i++){
                result += letter;
            }
            result += origin;
        }

        return result;
    }

    /**
     * 지정된 길이만큼 오른쪽에 0을 채운다.
     * @param origin
     * @param length
     * @return
     */
    public static String rPadZero(String origin, int length){
        return rPad(origin, '0', length);
    }

    /**
     * 지정된 길이만큼 오른쪽에 공백을 채운다.
     * @param origin
     * @param length
     * @return
     */
    public static String rPadSpace(String origin, int length){
        return rPad(origin, ' ', length);
    }

    /**
     * 지정된 길이만큼 오른쪽에 특정 문자열을 채운다.
     * @param origin
     * @param letter
     * @param length
     * @return
     */
    public static String rPad(String origin, char letter, int length){
        if(origin == null){
            origin = "";
        }

        String result = "";
        int fillLength = length - origin.length();
        if (fillLength < 0){
            return origin;
        } else {
            result += origin;
            for (int i = 0 ; i < fillLength ; i++){
                result += letter;
            }
        }

        return result;
    }

    public static String msgConcat(Map<String, Object> resultMap, String argStr){

        String resMsg = "";
        for(int i=1; i < 5 ; i++){
            String str = nvl(resultMap.get(argStr+i)).trim();
            if(!"".equals(str)){
                resMsg += str;
            }
        }

        return resMsg;

    }

    /**
     * 가맹점 별 API KEY 생성
     * @return
     */
    public static String getCompApiKey(){
        String compApikey = "";

        for (int i = 1; i <= 40; i++) {
            int num = (int) (Math.random() * (2)) + 1;
            if(num == 1){
                compApikey += (char) ((Math.random() * 26) + 65); // 대문자
            }else{
                compApikey += (char) ((Math.random() * 26) + 97); // 소문자
            }

        }
        System.out.println("====> compApikey : "+compApikey);

        return compApikey;
    }

    /**
     * vo 출력
     * @param title
     * @param obj
     */
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

    public static JSONArray jsonStringToArray(String name, String jsonStr) throws Exception{

        System.out.println("=====> jsonStr " + jsonStr);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(jsonStr);
        org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
        jsonObject = (org.json.simple.JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObject.get(name);

        return jsonArray;
    }


    /**
     * Vo를 Map으로 변환
     * @param vo
     * @return
     */
    public static Map convertObjectToMap(Object vo){
        Map map = new HashMap();
        Field[] fields = vo.getClass().getDeclaredFields();
        for(int i=0; i <fields.length; i++){
            fields[i].setAccessible(true);
            try{
                map.put(fields[i].getName(), fields[i].get(vo));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Map을 Vo로 변환
     * @param map
     * @param vo
     * @return
     */
    public static Object convertMapToObject(Map<String,Object> map, Object vo){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();

        while(itr.hasNext()){
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            Method[] methods = vo.getClass().getDeclaredMethods();
            for(int i=0;i<methods.length;i++){
                if(methodString.equals(methods[i].getName())){
                    try{
                        methods[i].invoke(vo, map.get(keyAttribute));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return vo;
    }

    public static Map<String,Object> SPLIT(Map<String, Object> map, String name, String pattern){

        if(map.get(name) != null && !"".equals(map.get(name))){
            String targetStr = map.get(name).toString();
            String[] targetStrArr = targetStr.split(pattern);

            int index = 1;
            for(String num : targetStrArr){
                map.put(name+index, num);
                index++;
            }
        }

        return map;
    }
}
