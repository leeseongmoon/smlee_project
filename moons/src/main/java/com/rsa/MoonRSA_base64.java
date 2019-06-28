package com.rsa;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class MoonRSA_base64 {

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOV/EQKkILVWrZ4UrCjeuBzEEKItyhUJE/UwmxuiVOwhmzPDA5wQi7hlWDSnFUwv88qGEmL3s+gI2to/BQ0/w/cXDIvjTIVXprdE7OKf7kw7HtAN/f5ShHNvxSR4gbQ3J2SQ1l0dog4LInYdiLqm6zBC1BtFcclxdwGQz0AdnfaQIDAQAB";
    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM5X8RAqQgtVatnhSsKN64HMQQoi3KFQkT9TCbG6JU7CGbM8MDnBCLuGVYNKcVTC/zyoYSYvez6Aja2j8FDT/D9xcMi+NMhVemt0Ts4p/uTDse0A39/lKEc2/FJHiBtDcnZJDWXR2iDgsidh2IuqbrMELUG0VxyXF3AZDPQB2d9pAgMBAAECgYEAm2T5I05fvH9QX9Nd5NTSVkbHeGW2uDmfmQvlEhZ4gC6HAkdCnkv0Up0hCyXl4YqUg07fEs3RR8myaOE8WvK6VIbiBuWR1d6W6edQBEqJYZW/XKS7FwlsWtrDScfdirimvoIiFAZncTAhDDGEy+THxCJvmvkZ7F4XlVPPfa4fTjECQQDl3bKu3PmgsevyFbsEnHP8Te1iV+gsJYdKHLBwGerS0xDwwuiojcD5bFksrZ7G94lzqfIkF3qm2znu7+QKGtX9AkEA5c2dzbGTNcm5jeIvip/WXnmphsQdxW5ttPqKAG5owic9gT5oY/MS3UwJ2q6bxx7q+mpavNNOgxZYTtJ+J8L03QJAQlUXmx5patXtII7kyQ/c6JIUTqzPcu3hfKGKyM6x1OTTnqagPQOrDE8X7KythP0pzRbdqUydOElLDohrZf8YgQJBANz8A5n//14kJGmKbslRt2WWfS15fl+TN7BnlTmg8uBxt7WHe/9RinyAWKGQc4Dh/HjeuW8HjGKGpFj4FhTKZ4kCQQC/sJ1ZLvrU4AsXrjA6/drqbVN3MAMgYVyWXvlWqHFOvG6bGBWnJ1dQKukFTbNM5fxkjSWPxQoPRrEjzNjlSyyI";

    public static void main(String[] args) throws Exception {

        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");


        //String text = "가나다123ablkjefs굿";
        String text = "가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿가나다123ablkjefs굿";
        System.out.println("평문\n==> " + text);

        text = text += sdf.format(now);
        System.out.println("평문\n==> " + text);


        /*암호화*/
        String enc = ENC_RSA(text);
        System.out.println("암호화\n==> " + enc);

        /*복호화*/
        String dec = DEC_RSA(enc);
        System.out.println("복호화\n==> " + dec);

    }

 // ENC_RSA() => RSA 암호화
    public static String ENC_RSA(String encStr) throws Exception{

        Cipher cipher = Cipher.getInstance("RSA");

        byte[] bpubKeyStr = Base64.decodeBase64(PUBLIC_KEY.getBytes());

        X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(bpubKeyStr);

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

        return Base64.encodeBase64String(cipherText);
    }

    // DEC_RSA() => RSA 복호화
    public static String DEC_RSA(String decStr) throws Exception{

        Cipher cipher = Cipher.getInstance("RSA");

        byte[] bprivKeyStr = Base64.decodeBase64(PRIVATE_KEY.getBytes());
        byte[] bdecStr = Base64.decodeBase64(decStr.getBytes());

        PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(bprivKeyStr);
        KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;

        try {
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 복호화
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = cipher.doFinal(bdecStr);

        String returnStr = new String(plainText);

        //날짜영역 제거
        returnStr = returnStr.substring(0,returnStr.length()-6);

        return returnStr;
    }

}
