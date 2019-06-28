package com.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MoonFileCopy {
    public static void main(String[] args) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try{
            // 복사할 대상 파일을 지정해준다.
            File file = new File("c:\\example\\File\\umejintan_new.txt");

            // FileInputStream 는 File object를 생성자 인수로 받을 수 있다.
            input = new FileInputStream(file);
            // 복사된 파일의 위치를 지정해준다.
            output = new FileOutputStream(new File("c:\\example\\File\\umejintan_copy.txt"));

            int readBuffer = 0;
            byte [] buffer = new byte[512];
            while((readBuffer = input.read(buffer)) != -1) {
                output.write(buffer, 0, readBuffer);
            }
            System.out.println("파일이 복사되었습니다.");
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try{
                // 생성된 InputStream Object를 닫아준다.
                input.close();
                // 생성된 OutputStream Object를 닫아준다.
                output.close();
            } catch(IOException io) {}
        }
    }

}
