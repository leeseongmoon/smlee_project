package com.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class MoonTestRSA {
    public static void main(String[] args) {
        try {
            //공개키 및 개인키 생성
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.genKeyPair();
            Key publicKey = keyPair.getPublic(); // 공개키
            Key privateKey = keyPair.getPrivate(); // 개인키

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            System.out.println("public key modulus(" + publicKeySpec.getModulus() + ") exponent(" + publicKeySpec.getPublicExponent() + ")");
            System.out.println("private key modulus(" + privateKeySpec.getModulus() + ") exponent(" + privateKeySpec.getPrivateExponent() + ")");


            // 암호화(Encryption) 예제
            String inputStr = "가나다라 이성문 이윤슬 김재은 123"; // "세이프123"을 암호화한다!

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] arrCipherData = cipher.doFinal(inputStr.getBytes()); // 암호화된 데이터(byte 배열)
            String strCipher = new String(arrCipherData);

            System.out.println("encdata : ["+arrCipherData+"]"); // 암호화 결과물 출력(눈으로 보이기에는 깨질 수 있음)
            System.out.println("encdata : ["+strCipher+"]"); // 암호화 결과물 출력(눈으로 보이기에는 깨질 수 있음)


            // 복호화(Decryption) 예제
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] arrData = cipher.doFinal(arrCipherData);
            String strResult = new String(arrData);

            System.out.println("desdata : ["+strResult+"]"); // 복호화 결과물 출력(다시 "세이프123"이 출력됨)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
