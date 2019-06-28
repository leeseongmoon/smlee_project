package com.crypto;

import java.security.MessageDigest;

public class Crypto {
    public static void main(String[] args) {

        String fullStr = null;
        String store_code = "";
        String trans_at = "";
        String compApikey = "";

        fullStr = store_code+trans_at+compApikey;

                System.out.println();

    }


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
}
