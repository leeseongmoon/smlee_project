package com.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;

import com.ftp.FtpClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
 
public class FtpClientUse {
 
    public static void main(String[] args) throws Exception {
        //파일 선언   
        File target =null;
         
        //저장할 파일을 target에 넣은 부분은 생략(각자 환경에 맞게 파일을 읽어서 넣어주시면 됩니다.)
         
        FtpClient ftp_ivr = new FtpClient("FTP 전송할 서버 주소", "FTP아이디", "FTP비밀번호", "");
        boolean result = ftp_ivr.upload(target, "파일 저장 경로");
        System.out.println("FTP result : " + result);

    }
}