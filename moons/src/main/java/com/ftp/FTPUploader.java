package com.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
 
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
 
/*

ftp로 통신을 하려면 먼저 ftp서버가 돌고 있어야 파일 전송을 받아준다.

virtual machine 을 사용해서 리눅스(우분투)를 설치한 후에 ftp서버를 사용할 수 있게 바꿔줘야 한다.

 */
public class FTPUploader {
    
    FTPClient ftp = null;
    
    //param( host server ip, username, password )
    public FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);//호스트 연결
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);//로그인
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }
    //param( 보낼파일경로+파일명, 호스트에서 받을 파일 이름, 호스트 디렉토리 )
    public void uploadFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        try(InputStream input = new FileInputStream(new File(localFileFullName))){
        this.ftp.storeFile(hostDir + fileName, input);
        //storeFile() 메소드가 전송하는 메소드
        }
    }
 
    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Start");
        FTPUploader ftpUploader = new FTPUploader("192.168.0.153", "jdk", "1234");
        ftpUploader.uploadFile("C:\\Users\\jdg\\Desktop\\jeongpro\\hello.txt", "hello.txt", "/home/jdk/");
        ftpUploader.disconnect();
        System.out.println("Done");
    }

    /*
    간단한 소스코드를 보면서 설명을 하면, 먼저 FTPClient 객체를 만들고 connect() 메소드로 해당 ftp서버 즉, 보낼 곳에 연결을 한다.

그 뒤 login() 메소드로 ftp서버에 로그인을 한다. (물론 FileType이나 Mode등 설정 필요)

끝으로 storeFile() 메소드로 전송하면 끝!
     */
 
}