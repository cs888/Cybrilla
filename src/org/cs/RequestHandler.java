package org.cs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {
    static String url;

    public RequestHandler() {
        this.url = "any valid URL";
    }

    Request request = new Request("ICIC0000021", "12345454", 100000.34, "txn00124", "date", "merc001");

    public static void main(String[] args) {
        // 1.prepare request
        Request request = new Request("ICIC0000021", "12345454", 100000.34, "txn00124", "date", "merc001");
        // set hascode
        request.setHash(new Request().hashAPI(request));
        // 2.encrpt request to AES128CBC
        String ms = new AES128CBC().encrypt(request);
        //convert request to Base64EncoderDecoder
        String msg = new Base64EncoderDecoder().enCode(ms);
        // 3.send to server & get responce
        StringBuffer response = null;
        try {
            response = sendPost(msg);
        } catch (Exception e) {
            System.out.println("Exception in sendPost method ");
            e.printStackTrace();
        }
        String response1 = response.substring(0, response.lastIndexOf("|"));
        String decrypt = new Base64EncoderDecoder().deCode(response1);
        String res = new AES128CBC().decrypt(decrypt, "any valid key");

        // 5.Verify response
        if (request.getHash().compareTo(response.substring(response.lastIndexOf("=")).trim()) != 0) {
            System.out.println("Hascode do not matched ");
            return;
        }

        // 6.Print all the parameter in response
        System.out.println(res);

    }

    private static StringBuffer sendPost(String msg) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Cybrilla");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(msg);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;

    }
}
