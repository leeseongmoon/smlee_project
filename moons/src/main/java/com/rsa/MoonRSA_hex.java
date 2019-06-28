package com.rsa;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;

public class MoonRSA_hex {

    public static final String PUBLIC_KEY = "30819f300d06092a864886f70d010101050003818d0030818902818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001";
    public static final String PRIVATE_KEY = "30820278020100300d06092a864886f70d0101010500048202623082025e02010002818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001028181009b64f9234e5fbc7f505fd35de4d4d25646c77865b6b8399f990be5121678802e870247429e4bf4529d210b25e5e18a94834edf12cdd147c9b268e13c5af2ba5486e206e591d5de96e9e750044a896195bf5ca4bb17096c5adac349c7dd8ab8a6be82221406677130210c3184cbe4c7c4226f9af919ec5e179553cf7dae1f4e31024100e5ddb2aedcf9a0b1ebf215bb049c73fc4ded6257e82c25874a1cb07019ead2d310f0c2e8a88dc0f96c592cad9ec6f78973a9f224177aa6db39eeefe40a1ad5fd024100e5cd9dcdb19335c9b98de22f8a9fd65e79a986c41dc56e6db4fa8a006e68c2273d813e6863f312dd4c09daae9bc71eeafa6a5abcd34e8316584ed27e27c2f4dd02404255179b1e696ad5ed208ee4c90fdce892144eaccf72ede17ca18ac8ceb1d4e4d39ea6a03d03ab0c4f17ecacad84fd29cd16dda94c9d38494b0e886b65ff1881024100dcfc0399ffff5e2424698a6ec951b765967d2d797e5f9337b0679539a0f2e071b7b5877bff518a7c8058a1907380e1fc78deb96f078c6286a458f81614ca6789024100bfb09d592efad4e00b17ae303afddaea6d5377300320615c965ef956a8714ebc6e9b1815a72757502ae9054db34ce5fc648d258fc50a0f46b123ccd8e54b2c88";

    public static void main(String[] args) throws Exception {


        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");


      String text = "143534";

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

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE"); // 알고리즘 명 / Cipher 알고리즘 mode / padding

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

    // DEC_RSA() => RSA 복호화
    public static String DEC_RSA(String decStr) throws Exception{

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE");

        PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(hexToByteArray(PRIVATE_KEY));
        KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;

        try {
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 복호화
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = cipher.doFinal(hexToByteArray(decStr));

        String returnStr = new String(plainText);

        //날짜영역 제거
        returnStr = returnStr.substring(0,returnStr.length()-6);

        return returnStr;
    }

    /*
     * 복호화 시 String을 byte[] 로 변경 시 사용
     * ( hex string to byte[] )
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

    /*
     * 암호화 후 String 으로 변환 시 사용
     * ( byte[] to hex sting )
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

}
