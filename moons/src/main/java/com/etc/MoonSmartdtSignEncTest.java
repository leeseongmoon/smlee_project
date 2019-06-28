package com.etc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MoonSmartdtSignEncTest {

    public static void main(String[] args) {

        //SignEnc nicesign = new SignEnc();

        String path = MoonSmartdtSignEncTest.class.getResource("").getPath();
        System.out.println(path);

        byte[] in = new byte[1086];
        byte[] out = new byte[1048];


        final String BmpPath = path+"SignDataBmp.bmp";
        final String DataPath = path+"SignData.txt";

        File f = new File(BmpPath);

        if(f.exists()){

        }
        try {
            FileInputStream input = new FileInputStream(f);
            input.read(in);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //int ret = nicesign.GetEncData(in, out);

        StringBuffer sb = new StringBuffer(out.length * 2);
        for (int x = 0; x < out.length; x++) {
            String HexNumber = "0" + Integer.toHexString(0xff & out[x]);
            sb.append(HexNumber.substring(HexNumber.length() - 2));
        }
        System.out.println("[Result]\n" + sb.toString());

        //if (ret == 1) {
            try {
                FileOutputStream output = new FileOutputStream(DataPath);
                output.write(String.valueOf(sb).getBytes());
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
         //   System.out.println("Success!");
        //} else {
        //    System.out.println("Fail!");
        //}
    }

}

class SignEnc {
    static {
        NativeLoader nl = new NativeLoader();
        boolean ret = nl.load("libSignEnc.so");

        if(ret){
            System.out.println("Module Load Success!");
        }else{
            System.out.println("Module Load Fail!");
        }
    }

    public native int GetEncData(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

    public native int MakePinBlock(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);

    public static int SignEncJar(byte[] input, byte[] output){
        SignEnc enc = new SignEnc();
        int ret = enc.GetEncData(input, output);
        return ret;
    }

    public static int MakePinBlockJar(byte[] card, byte[] password, byte[] pin16){
        SignEnc enc = new SignEnc();
        int ret = enc.MakePinBlock(card, password, pin16);
        return ret;
    }
}

class NativeLoader {

    private static Boolean loaded = null;

    boolean load(String libname) {

        if (loaded != null) {
            return loaded == Boolean.TRUE;
        }

        try {

            ClassLoader cl = NativeLoader.class.getClassLoader();

            InputStream in = cl.getResourceAsStream(libname);

            if (in == null) {
                throw new Exception("libname: " + libname + " not found");
            }

            int libnameIndex = libname.lastIndexOf('.');

            File tmplib = File.createTempFile(libname.substring(0, libnameIndex),libname.substring(libnameIndex));
            tmplib.deleteOnExit();
            OutputStream out = new FileOutputStream(tmplib);

            byte[] buf = new byte['?'];
            int len;

            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

            System.load(tmplib.getAbsolutePath());

            loaded = Boolean.TRUE;
            return true;
        } catch (Exception localException) {
            loaded = Boolean.FALSE;
        }
        return false;
    }

}