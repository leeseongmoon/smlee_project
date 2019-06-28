package com.rsa;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/*
 Sample Result

 pubKeyStr = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100a028575f94e0b27fe133a7a7dfe72dc68e3e16d084be26bd9a5eb85cc947eb5785a15c99d65140cec88c61a590b55cf3790f18b53a31976ed68378a47d2cda3c575fa612050adecfe6107608dc2ddcbb16e6177b2ad3b30bf42252efdb81bea87ff339b33990f2345d7e552f4ff1184706a4dfec6c9abc86ee020a8bffd3714dd0979d24a6e2c223fd6a190524e95b9c5513309624f25d2e229aa5208feb2b956e26ba5c947b9644247935d2bcc529017e7b1dfdaacd92c1140f3346d4f6d092341ca294d7986a859e5af8e9e879ca29e4f668121ae0d626298c1d94d894cf30ce324bc428e9acfe0540ef8116bbfbf9bfb2da48335758abef03f7ae769024e30203010001";
 privKeyStr = "308204bd020100300d06092a864886f70d0101010500048204a7308204a30201000282010100a028575f94e0b27fe133a7a7dfe72dc68e3e16d084be26bd9a5eb85cc947eb5785a15c99d65140cec88c61a590b55cf3790f18b53a31976ed68378a47d2cda3c575fa612050adecfe6107608dc2ddcbb16e6177b2ad3b30bf42252efdb81bea87ff339b33990f2345d7e552f4ff1184706a4dfec6c9abc86ee020a8bffd3714dd0979d24a6e2c223fd6a190524e95b9c5513309624f25d2e229aa5208feb2b956e26ba5c947b9644247935d2bcc529017e7b1dfdaacd92c1140f3346d4f6d092341ca294d7986a859e5af8e9e879ca29e4f668121ae0d626298c1d94d894cf30ce324bc428e9acfe0540ef8116bbfbf9bfb2da48335758abef03f7ae769024e30203010001028201003cbaef5ec4226c2d47a501b70b952aeb76b69e8a153bd18ee60172e16445cc3cd4e43a2bef73c2226733ac374ee726c70029b451cc2e4fe0eca0cfd777cbd1393e5c7ba9ab95d2a57cf4d2fd4b186cf1d89e095b6003048accd9531fb6d9bd0541f765f2cb2a665d6af263b4dc186cff49caad4a86682a82f3af7c7657dcc2c2886d69b03d21e61d797e564af6aada7b13a4a0de8a61d383c9e1376848f78fd890ecc11c00206103c8371cee243965e07b0324e2e1ca1f22e2c906da9b08f399351544334b6df1baf24b84e3bd8e399a9047cd13ae2211a3e85c1da8eb566f9c76da847a9b162a24b2b822630dad10caf51a8dd6ec7be3a230e11065de287bf102818100d04cd7ade00661f287ff3975db1699a57267531708fd9d95fa98be2a757d915b76afa973346880522c56720ff6a06fc3ac0a53a7d05af8d62d55026d0bf042532a70702bc342cbd7b3ea73b7eca6fad53d812f7846d000895712cec692761bcd84e966c1527439f1314e9af6d824c7fc4acf86f8188e4bfbaa2e7e80be60b70902818100c4d53dd43e434e04872b31373e24b4294751abd4ed73a70246f68c0e80dde65f7f3bf4cfdb36aa5085ff56314bcc6b42dc4d8a40072a7e02d269f2587bb7db5477cccc442f033a45d488b21df160d2c362919663e0b7ece77f3a605be5c46e1caa583d7fb1f3f802fb2fb471af70f2fceeaa73b757863923645a80b3062c6b8b02818056e7183c74707540c83854f2b49af333314da63ec0037f5169217c851e4b6aef6bced1b53cb2348713f464d60020e3401170a58227cd2c6fbeadeebb2bf5ead5e3a8d14390cd375a20b7b9db8c2206181cf6dea52175fa23526e8852141cae70c9f8ce63ed508c33f24c471ea1028764dc99444811869c70bc897541a52b987102818100b1505fd879166c8bcbb06f2f92bdc1c685c6df027bbdf9af257a2885503595b86587ff6bd7d090f0e52535e246a429e41b4d86c35331463f088a04950d2e7d58c46b2b8028d1c186f28509dfcb782ba573802f785e11924aa0e457b5b17098b91e034bb362fda2681d1bc673bb5606af2fd94150e279b9b3564de92e5cdb5b2d02818058018bbf5d04bb6c8548f7fc56a274988523aa7a5d0d4d3c5ba77fdf16b3dffc047c97dc3aa3cde2668e2aa2e81ebfe8548f31139194d577a5a934223b9f30ceb60be79c668bea9af3c59c8a6f9e1aabd81891b14a848a87f7bfa4c135888cb6d5253deaf6796c6ff38d75746432525f36260329a7735b25c6b844233ad8e300";
 plainText:암호화된 문자열 abcdefg hijklmn
 */
public class MoonRSADecrypt {

    /**
     * @param args
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException {
        String privKeyStr = "30820278020100300d06092a864886f70d0101010500048202623082025e02010002818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001028181009b64f9234e5fbc7f505fd35de4d4d25646c77865b6b8399f990be5121678802e870247429e4bf4529d210b25e5e18a94834edf12cdd147c9b268e13c5af2ba5486e206e591d5de96e9e750044a896195bf5ca4bb17096c5adac349c7dd8ab8a6be82221406677130210c3184cbe4c7c4226f9af919ec5e179553cf7dae1f4e31024100e5ddb2aedcf9a0b1ebf215bb049c73fc4ded6257e82c25874a1cb07019ead2d310f0c2e8a88dc0f96c592cad9ec6f78973a9f224177aa6db39eeefe40a1ad5fd024100e5cd9dcdb19335c9b98de22f8a9fd65e79a986c41dc56e6db4fa8a006e68c2273d813e6863f312dd4c09daae9bc71eeafa6a5abcd34e8316584ed27e27c2f4dd02404255179b1e696ad5ed208ee4c90fdce892144eaccf72ede17ca18ac8ceb1d4e4d39ea6a03d03ab0c4f17ecacad84fd29cd16dda94c9d38494b0e886b65ff1881024100dcfc0399ffff5e2424698a6ec951b765967d2d797e5f9337b0679539a0f2e071b7b5877bff518a7c8058a1907380e1fc78deb96f078c6286a458f81614ca6789024100bfb09d592efad4e00b17ae303afddaea6d5377300320615c965ef956a8714ebc6e9b1815a72757502ae9054db34ce5fc648d258fc50a0f46b123ccd8e54b2c88";
        //String cipherText = "717b394934cdaaff7e24efb296d132c754dcca4e80c638db4bfc3347543d7f58236b4138ad9021049e20cb140c60edf5155ff6ab95910e7d1cfef9f00ba51ee5dd9b721b0d60a983708e0c9bf3c7ca1ae247cde3c0e600e5b17c6b3f05410dfe50c8ca575aa335ed7ab405c1d84a0c11cbb9501d476134aae33dd53fcd833f13";



        String cipherText = "398ed3eda6952ab35fbb3f3c2287cc001c31456904393bf14b1d87afdffa81378328fb8881dc8c1e92ccdacabc94feb08b30b376b918da97e804ecbb4ee3fd6dbf4f2f3aff0d1aebf4cfed397dbc8f6295e0e5f17e27b07853ff0db5cc4de763d3333956f232c7f24c7b0913d0a6ab6c9a4a5b1d3b0392586caba00c55a13370";

        //String privKeyStr2 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM5X8RAqQgtVatnhSsKN64HMQQoi3KFQkT9TCbG6JU7CGbM8MDnBCLuGVYNKcVTC/zyoYSYvez6Aja2j8FDT/D9xcMi+NMhVemt0Ts4p/uTDse0A39/lKEc2/FJHiBtDcnZJDWXR2iDgsidh2IuqbrMELUG0VxyXF3AZDPQB2d9pAgMBAAECgYEAm2T5I05fvH9QX9Nd5NTSVkbHeGW2uDmfmQvlEhZ4gC6HAkdCnkv0Up0hCyXl4YqUg07fEs3RR8myaOE8WvK6VIbiBuWR1d6W6edQBEqJYZW/XKS7FwlsWtrDScfdirimvoIiFAZncTAhDDGEy+THxCJvmvkZ7F4XlVPPfa4fTjECQQDl3bKu3PmgsevyFbsEnHP8Te1iV+gsJYdKHLBwGerS0xDwwuiojcD5bFksrZ7G94lzqfIkF3qm2znu7+QKGtX9AkEA5c2dzbGTNcm5jeIvip/WXnmphsQdxW5ttPqKAG5owic9gT5oY/MS3UwJ2q6bxx7q+mpavNNOgxZYTtJ+J8L03QJAQlUXmx5patXtII7kyQ/c6JIUTqzPcu3hfKGKyM6x1OTTnqagPQOrDE8X7KythP0pzRbdqUydOElLDohrZf8YgQJBANz8A5n//14kJGmKbslRt2WWfS15fl+TN7BnlTmg8uBxt7WHe/9RinyAWKGQc4Dh/HjeuW8HjGKGpFj4FhTKZ4kCQQC/sJ1ZLvrU4AsXrjA6/drqbVN3MAMgYVyWXvlWqHFOvG6bGBWnJ1dQKukFTbNM5fxkjSWPxQoPRrEjzNjlSyyI";
        //String cipherText2 = "hfCv5bR+OZJYYke0ELFKrx9kQpGSsOzARt4hst9jpNw7wDsFJ23Jpkc5Q18CoasvtRTmPFD0Gvs3rCWLe33UAtoZoQ2Ot5PLYwl2cZTht0fRylIeD8DDldrOEOe7NTWqe0kRvGcWq2mDvGNH/q/SK3zkJU6pk0afNoxCzhJa18E=";
        //byte[] bcipherText2 = Base64.decodeBase64(cipherText2.getBytes());
        //byte[] bprivKeyStr2 = Base64.decodeBase64(privKeyStr2.getBytes());



        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");

        // Turn the encoded key into a real RSA private key.
        // Private keys are encoded in PKCS#8.
        PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(hexToByteArray(privKeyStr));

        //PKCS8EncodedKeySpec rkeySpec2 = new PKCS8EncodedKeySpec(bprivKeyStr2);

        KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;
        PrivateKey privateKey2 = null;

        try {
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
            //privateKey2 = rkeyFactory.generatePrivate(rkeySpec2);

        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        // 개인키를 가지고있는쪽에서 복호화
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //cipher.init(Cipher.DECRYPT_MODE, privateKey2);

        byte[] plainText = cipher.doFinal(hexToByteArray(cipherText));
        //byte[] plainText = cipher.doFinal(bcipherText2);

        String returnStr = new String(plainText);

        returnStr = returnStr.substring(0,returnStr.length()-6);

        System.out.println("plainText:" + returnStr);
    }

    // hex string to byte[]
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer
                    .parseInt(hex.substring(2 * i, 2 * i + 2), 16);
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
 * 소스 설명 privKeyStr , cipherText 에 각각 개인키, 암호화된 문장을 입력하여 byte타입으로 변경 후 복호화를 하게
 * 되는데 개인키의 경우 PKCS#8 타입으로 인코딩 되어 있습니다. PKCS8EncodedKeySpec 클래스를 이용하여 로딩해줘야 합니다.
 * 그리고 아래 함수들에 의해서 복호화가 이루어지게 되며 plainText에 처음 입력했던 문장이 나오게 됩니다.
 * cipher.init(Cipher.DECRYPT_MODE, privateKey); byte[] plainText =
 * cipher.doFinal(hexToByteArray(cipherText));
 */
