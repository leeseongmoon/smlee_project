package com.etc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

public class MoonHttpsClientWithoutValidation {

    public static void main(String[] args) throws Exception {
        MoonHttpsClientWithoutValidation test = new MoonHttpsClientWithoutValidation();
        test.getHttps("https://www.google.com");

    }

    /**
     *
     * @param urlString
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public void getHttps(String urlString) throws IOException, NoSuchAlgorithmException, KeyManagementException {

     // Get HTTPS URL connection
     URL url = new URL(urlString);
     HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

     // Set Hostname verification
     conn.setHostnameVerifier(new HostnameVerifier() {
      @Override
      public boolean verify(String hostname, SSLSession session) {
       // Ignore host name verification. It always returns true.
       return true;
      }

     });

     // SSL setting
     SSLContext context = SSLContext.getInstance("TLS");
     context.init(null, null, null);  // No validation for now
     conn.setSSLSocketFactory(context.getSocketFactory());

     // Connect to host
     conn.connect();
     conn.setInstanceFollowRedirects(true);

     // Print response from host
     InputStream in = conn.getInputStream();
     BufferedReader reader = new BufferedReader(new InputStreamReader(in));

     String line = null;
     StringBuffer resultSb = new StringBuffer();

     while((line = reader.readLine()) != null){
         System.out.printf("%s\n", line);
         resultSb.append(line);
     }

     reader.close();

     System.out.println("##### resultSb : "+resultSb);

    }

}
