/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finfiletransfer;

import com.finlogic.util.Logger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author njuser
 */
public class CheckStatus {

    public static String getSource(String location, String param) throws IOException {
        URL url = new URL(location);
        HttpURLConnection connection;
        DataOutputStream wr = null;
        InputStream is = null;
        BufferedReader rd = null;
        String res;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("Content-Length", Integer.toString(param.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(param);
            wr.flush();

            is = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            res = response.toString();
            
            Logger.DataLogger("CheckStatus.getSource\nlocation : "+location+"\nres : "+res);

            connection.disconnect();
        } catch (Exception e) {

            Logger.DataLogger("error");
            Logger.ErrorLogger(e);
            throw e;
        } finally {
            rd.close();
            is.close();
            wr.close();
        }
        return res;
    }

    public String checkStatus_Src(String id, String server, String lang) throws IOException {
        String address;
        switch (server) {
            case "devhoweb1.njtechdesk.com":
            case "devhoweb2.njtechdesk.com":
            case "devhoweb3.njtechdesk.com":
            case "devhoweb4.njtechdesk.com":
            case "testhoweb1.njtechdesk.com":
            case "testhoweb2.njtechdesk.com":
            case "testhoweb3.njtechdesk.com":
            case "testhoweb4.njtechdesk.com":
                address = "https://";
                break;
            default:
                address = "http://";
                break;
        }
        address += server + "/finstudio/FinFileTransferServlet.do?action=srcChecking&id=" + id + "&lang=" + lang;

        Logger.DataLogger(address);
        String res = getSource(address, id);
        return res;
    }

    public String checkStatus_SrcCnfrm(String id, String server, String lang) throws IOException {
        String serverLocal[] = server.split("-");
        String address;
        switch (serverLocal[0]) {
            case "devhoweb1.njtechdesk.com":
            case "devhoweb2.njtechdesk.com":
            case "devhoweb3.njtechdesk.com":
            case "devhoweb4.njtechdesk.com":
            case "testhoweb1.njtechdesk.com":
            case "testhoweb2.njtechdesk.com":
            case "testhoweb3.njtechdesk.com":
            case "testhoweb4.njtechdesk.com":
                address = "https://";
                break;
            default:
                address = "http://";
                break;
        }
        address += serverLocal[0] + "/finstudio/FinFileTransferServlet.do?action=srcCheckingConfirm&id=" + id + "&lang=" + lang;

        String res = getSource(address, id);
        return res;
    }

    public String checkStatus_Dest(String id, String server, String lang) throws UnsupportedEncodingException, MalformedURLException, IOException {
        String address;
        
        switch(server)  {
            case "testhoweb1.njtechdesk.com":
            case "testhoweb2.njtechdesk.com":
            case "testhoweb3.njtechdesk.com":
            case "testhoweb4.njtechdesk.com":
            case "prodhoweb1.njtechdesk.com":
            case "prodhoweb2.njtechdesk.com":
            case "prodhoweb3.njtechdesk.com":
            case "prodhoweb4.njtechdesk.com":
                address = "https://";
                break;
            default:
                address = "http://";
                break;
        }
        
        address += server + "/finstudio/FinFileTransferServlet.do?action=destChecking&id=" + id + "&lang=" + lang;
        Logger.DataLogger(address);
        String res = getSource(address, id);
        return res;
    }
}
