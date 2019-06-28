package com.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.apache.commons.codec.binary.Base64;

/*
 Sample Result

 pubKeyHex:
 30819f300d06092a864886f70d010101050003818d0030818902818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001

 privKeyHex:
 30820278020100300d06092a864886f70d0101010500048202623082025e02010002818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001028181009b64f9234e5fbc7f505fd35de4d4d25646c77865b6b8399f990be5121678802e870247429e4bf4529d210b25e5e18a94834edf12cdd147c9b268e13c5af2ba5486e206e591d5de96e9e750044a896195bf5ca4bb17096c5adac349c7dd8ab8a6be82221406677130210c3184cbe4c7c4226f9af919ec5e179553cf7dae1f4e31024100e5ddb2aedcf9a0b1ebf215bb049c73fc4ded6257e82c25874a1cb07019ead2d310f0c2e8a88dc0f96c592cad9ec6f78973a9f224177aa6db39eeefe40a1ad5fd024100e5cd9dcdb19335c9b98de22f8a9fd65e79a986c41dc56e6db4fa8a006e68c2273d813e6863f312dd4c09daae9bc71eeafa6a5abcd34e8316584ed27e27c2f4dd02404255179b1e696ad5ed208ee4c90fdce892144eaccf72ede17ca18ac8ceb1d4e4d39ea6a03d03ab0c4f17ecacad84fd29cd16dda94c9d38494b0e886b65ff1881024100dcfc0399ffff5e2424698a6ec951b765967d2d797e5f9337b0679539a0f2e071b7b5877bff518a7c8058a1907380e1fc78deb96f078c6286a458f81614ca6789024100bfb09d592efad4e00b17ae303afddaea6d5377300320615c965ef956a8714ebc6e9b1815a72757502ae9054db34ce5fc648d258fc50a0f46b123ccd8e54b2c88

 */
public class MoonRSAGenKey {
    /**
     * @param args
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {

        String pubkey = "smartdata";

        /*
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024, random); // 여기에서는 2048 bit 키를 생성하였음
        */

        SecureRandom random = new SecureRandom(pubkey.getBytes());
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","SunJSSE"); // OK
        generator.initialize(2048, random); // 여기에서는 2048 bit 키를 생성하였음

        KeyPair pair = generator.generateKeyPair();
        Key pubKey = pair.getPublic(); // Kb(pub) 공개키
        Key privKey = pair.getPrivate();// Kb(pri) 개인키

        KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec = keyFactory1.getKeySpec(pubKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory1.getKeySpec(privKey, RSAPrivateKeySpec.class);

        System.out.println("Public  key modulus : " + rsaPublicKeySpec.getModulus());
        System.out.println("Public  key exponent: " + rsaPublicKeySpec.getPublicExponent());
        System.out.println("Private key modulus : " + rsaPrivateKeySpec.getModulus());
        System.out.println("Private key exponent: " + rsaPrivateKeySpec.getPrivateExponent());

        System.out.println("pubKeyHex:" + pubKey.getEncoded());
        System.out.println("privKeyHex:" + privKey.getEncoded());

        System.out.println("pubKeyHex:" + byteArrayToHex(pubKey.getEncoded()));
        System.out.println("privKeyHex:" + byteArrayToHex(privKey.getEncoded()));

        String sPublicKey = Base64.encodeBase64String(pubKey.getEncoded());
        String sPrivateKey = Base64.encodeBase64String(privKey.getEncoded());

        System.out.println("sPublicKey:" + sPublicKey);
        System.out.println("sPrivateKey:" + sPrivateKey);
    }

    /*
     * hexToByteArray, byteArrayToHex라는 함수가 있는데 뒤쪽에도 공통적으로 나오는 함수입니다. 해당 함수는
     * byte[]의 값을 hex값으로 변환하여 출력을 용이 하게 하여 다른쪽으로 붙여넣어 전달하기 편하게 하기 위해서 사용되는 함수
     * 입니다. 즉 " " 라는 문자열을 byteArrayToHex를 호출하면 "20" 이 되며 반대로 "20"을
     * hexToByteArray를 호출하면 " " 이 됩니다.
     */

    // hex string to byte[]
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

    // byte[] to hex sting
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
}

/*
 * hexToByteArray, byteArrayToHex라는 함수가 있는데 뒤쪽에도 공통적으로 나오는 함수입니다. 해당 함수는 byte[]의
 * 값을 hex값으로 변환하여 출력을 용이 하게 하여 다른쪽으로 붙여넣어 전달하기 편하게 하기 위해서 사용되는 함수 입니다. 즉 " " 라는
 * 문자열을 byteArrayToHex를 호출하면 "20" 이 되며 반대로 "20"을 hexToByteArray를 호출하면 " " 이 됩니다.
 *
 * KeyPairGenerator.getInstance("RSA", "SunJSSE"); 함수의 인자는 두부분으로 구성되어있는데
 * Provider 관련 내용을 확인 하면 됩니다. "SunJSSE"가 Provider로 RSA를 지원하는 Provider를 선택해서 기록하면
 * 됩니다. Sun에서 지원하는 provider 종류는 아래 링크를 참고 하기 바랍니다. provider 관련
 * https://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html 코드 설명은 두개의 키를
 * 만들어 화면에 출력하게 됩니다. random에 의해서 키는 실행될때마다 바뀝니다.
 */
