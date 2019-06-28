package com.rsa;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

//https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
//Cipher.getInstance 첫번째 인자 의미
/*
 Parameters:
 transformation - the name of the transformation, e.g., DES/CBC/PKCS5Padding. See the Cipher section in the Java Cryptography Architecture Standard Algorithm Name Documentation for information about standard transformation names.
 Cipher Algorithm Names/Cipher Algorithm Modes/Cipher Algorithm Padding
 provider - the name of the provider.

 provider 관련
 https://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html
 */

//https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
//http://stackoverflow.com/questions/7348224/generating-constant-rsa-keys-java

/*
 Sample Result

 pubKeyStr = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100a028575f94e0b27fe133a7a7dfe72dc68e3e16d084be26bd9a5eb85cc947eb5785a15c99d65140cec88c61a590b55cf3790f18b53a31976ed68378a47d2cda3c575fa612050adecfe6107608dc2ddcbb16e6177b2ad3b30bf42252efdb81bea87ff339b33990f2345d7e552f4ff1184706a4dfec6c9abc86ee020a8bffd3714dd0979d24a6e2c223fd6a190524e95b9c5513309624f25d2e229aa5208feb2b956e26ba5c947b9644247935d2bcc529017e7b1dfdaacd92c1140f3346d4f6d092341ca294d7986a859e5af8e9e879ca29e4f668121ae0d626298c1d94d894cf30ce324bc428e9acfe0540ef8116bbfbf9bfb2da48335758abef03f7ae769024e30203010001";
 privKeyStr = "308204bd020100300d06092a864886f70d0101010500048204a7308204a30201000282010100a028575f94e0b27fe133a7a7dfe72dc68e3e16d084be26bd9a5eb85cc947eb5785a15c99d65140cec88c61a590b55cf3790f18b53a31976ed68378a47d2cda3c575fa612050adecfe6107608dc2ddcbb16e6177b2ad3b30bf42252efdb81bea87ff339b33990f2345d7e552f4ff1184706a4dfec6c9abc86ee020a8bffd3714dd0979d24a6e2c223fd6a190524e95b9c5513309624f25d2e229aa5208feb2b956e26ba5c947b9644247935d2bcc529017e7b1dfdaacd92c1140f3346d4f6d092341ca294d7986a859e5af8e9e879ca29e4f668121ae0d626298c1d94d894cf30ce324bc428e9acfe0540ef8116bbfbf9bfb2da48335758abef03f7ae769024e30203010001028201003cbaef5ec4226c2d47a501b70b952aeb76b69e8a153bd18ee60172e16445cc3cd4e43a2bef73c2226733ac374ee726c70029b451cc2e4fe0eca0cfd777cbd1393e5c7ba9ab95d2a57cf4d2fd4b186cf1d89e095b6003048accd9531fb6d9bd0541f765f2cb2a665d6af263b4dc186cff49caad4a86682a82f3af7c7657dcc2c2886d69b03d21e61d797e564af6aada7b13a4a0de8a61d383c9e1376848f78fd890ecc11c00206103c8371cee243965e07b0324e2e1ca1f22e2c906da9b08f399351544334b6df1baf24b84e3bd8e399a9047cd13ae2211a3e85c1da8eb566f9c76da847a9b162a24b2b822630dad10caf51a8dd6ec7be3a230e11065de287bf102818100d04cd7ade00661f287ff3975db1699a57267531708fd9d95fa98be2a757d915b76afa973346880522c56720ff6a06fc3ac0a53a7d05af8d62d55026d0bf042532a70702bc342cbd7b3ea73b7eca6fad53d812f7846d000895712cec692761bcd84e966c1527439f1314e9af6d824c7fc4acf86f8188e4bfbaa2e7e80be60b70902818100c4d53dd43e434e04872b31373e24b4294751abd4ed73a70246f68c0e80dde65f7f3bf4cfdb36aa5085ff56314bcc6b42dc4d8a40072a7e02d269f2587bb7db5477cccc442f033a45d488b21df160d2c362919663e0b7ece77f3a605be5c46e1caa583d7fb1f3f802fb2fb471af70f2fceeaa73b757863923645a80b3062c6b8b02818056e7183c74707540c83854f2b49af333314da63ec0037f5169217c851e4b6aef6bced1b53cb2348713f464d60020e3401170a58227cd2c6fbeadeebb2bf5ead5e3a8d14390cd375a20b7b9db8c2206181cf6dea52175fa23526e8852141cae70c9f8ce63ed508c33f24c471ea1028764dc99444811869c70bc897541a52b987102818100b1505fd879166c8bcbb06f2f92bdc1c685c6df027bbdf9af257a2885503595b86587ff6bd7d090f0e52535e246a429e41b4d86c35331463f088a04950d2e7d58c46b2b8028d1c186f28509dfcb782ba573802f785e11924aa0e457b5b17098b91e034bb362fda2681d1bc673bb5606af2fd94150e279b9b3564de92e5cdb5b2d02818058018bbf5d04bb6c8548f7fc56a274988523aa7a5d0d4d3c5ba77fdf16b3dffc047c97dc3aa3cde2668e2aa2e81ebfe8548f31139194d577a5a934223b9f30ceb60be79c668bea9af3c59c8a6f9e1aabd81891b14a848a87f7bfa4c135888cb6d5253deaf6796c6ff38d75746432525f36260329a7735b25c6b844233ad8e300";

 inputText: 낌재은dldbstmf이썽문0039221234dlrp뭐다냐

 inputHex:(31):becfc8a3c8adb5c820b9aec0dabfad20616263646566672068696a6b6c6d6e
 cipherHex:(256):74e199ec8b1d1845a7569622a43500598bab5b194b44915da91929aa2007564cbe8ac4e996ead6b3cdc337603c4a031e18471e35efd3d8e49590e3269ba2254c095bc6c0c38ca113b7760b7ea792f3fa1bb3b5560d918f81b48e189da29c369fffac7e7b4e722de70e87b719d827c380cbec1cd446c1f81084a7429f627443580943937f46af78d1e76b83fc26e2b1010a70cfcf396dfa76f0edfbee9c3515efcc1c803798411a54c83e35f60b1089626a4c6106a355b27e86d56d06447186b33b2884f8e73986cc5c44bf2a00ddab7c3fdb1138c3e86208ec7ed5210d18bd97bed15653f76ca7ddae2acf4d338cfd8bcd91fe18d94ca4fed5759dc7f1f0ec79
 */
public class MoonRSAEncrypt {

    /**
     * @param args
     * @throws NoSuchPaddingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE");
        // 알고리즘 명 / Cipher 알고리즘 mode / padding

        String pubKeyStr = "30819f300d06092a864886f70d010101050003818d0030818902818100ce57f1102a420b556ad9e14ac28deb81cc410a22dca150913f5309b1ba254ec219b33c3039c108bb8655834a7154c2ff3ca861262f7b3e808dada3f050d3fc3f7170c8be34c8557a6b744ece29fee4c3b1ed00dfdfe5284736fc5247881b437276490d65d1da20e0b22761d88baa6eb3042d41b4571c971770190cf401d9df690203010001";

        String pubKeyStr2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOV/EQKkILVWrZ4UrCjeuBzEEKItyhUJE/UwmxuiVOwhmzPDA5wQi7hlWDSnFUwv88qGEmL3s+gI2to/BQ0/w/cXDIvjTIVXprdE7OKf7kw7HtAN/f5ShHNvxSR4gbQ3J2SQ1l0dog4LInYdiLqm6zBC1BtFcclxdwGQz0AdnfaQIDAQAB";
        byte[] bpubKeyStr2 = Base64.decodeBase64(pubKeyStr2.getBytes());

        // public 키는 X509규격으로 인코딩 되어있습니다. 따라서 로딩할때 X509규격으로 로딩해줘야 합니다.
        // hexToByteArray(pubKeyStr) 함수를 호출함으로서 공용키의 byte type으로 로딩되고,
        X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(hexToByteArray(pubKeyStr));

        KeyFactory ukeyFactory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = null;

        try {
            // generatePublic 를 이용해서 PublicKey 에 공용키 값이 들어가게 됨.
            publicKey = ukeyFactory.generatePublic(ukeySpec);
            System.out.println("pubKeyHex:"+ byteArrayToHex(publicKey.getEncoded()));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        // 공개키를 전달하여 암호화
        byte[] input = "낌재은dldbstmf이썽문0039221234dlrp뭐다냐".getBytes(); // 암호화하고자 하는 평문
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = cipher.doFinal(input);

        System.out.println("inputText:[" + new String(input) +"]");
        System.out.println("inputHex:(" + input.length + "):["+ byteArrayToHex(input) +"]");
        System.out.println("cipherHex:(" + cipherText.length + "):["+ byteArrayToHex(cipherText) +"]");
        /*System.out.println("inputHex:(" + input.length + "):["+ Base64.encodeBase64String(input) +"]");
        System.out.println("cipherHex:(" + cipherText.length + "):["+ Base64.encodeBase64String(cipherText) +"]");*/
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
 * 소스 설명 Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE"); 이부분 아래 provider
 * 부분을 보면 됩니다. 첫번째 인자는 transformation 으로서 Algorithm Names/Cipher Algorithm
 * Modes/Cipher Algorithm Padding 를 선택하도록 합니다. provider 문서를 보면 제공하는 종류가 있습니다.
 * https
 * ://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html
 *
 * 그 다음은 X509EncodedKeySpec 관련 내용인데 public 키는 X509규격으로 인코딩 되어있습니다. 따라서 로딩할때
 * X509규격으로 로딩해줘야 합니다. hexToByteArray(pubKeyStr) 함수를 호출함으로서 공용키의 byte type으로
 * 로딩되고 generatePublic 를 이용해서 PublicKey 에 공용키 값이 들어가게 됩니다. input 은 암호화 하고자 하는
 * 평문이고 byte[] cipherText = cipher.doFinal(input); 에 의해 cipherText가 암호화 된 문장입니다.
 * cipherHex 는 Hex 포맷으로 화면에 나타내거나 다른쪽에서 읽어들이기 위해 변형된 문장입니다.
 */
