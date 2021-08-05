/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.finstudio.encdec.service.impl;

import com.finlogic.util.PasswordUtil;
import com.finlogic.finstudio.encdec.bean.EncDecDTO;
import com.finlogic.finstudio.encdec.service.EncDecService;
import com.finlogic.util.Logger;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jigna Patel
 */

@Service(value = "encDecService")
@Lazy(true)
public class EncDecServiceImpl implements EncDecService {
    
    @Override
    public String encrypt(EncDecDTO dto) {
        String encText = "";
        try {
            if(!dto.getAllowedServer().contains(dto.getServerName()))
                throw new IOException("This utility is not allowed for \'"+ dto.getServerName() + "\' server");
            
	    if(dto.getServerName().equals("prod"))
		return PasswordUtil.getEncodedPassword(dto.getPlainText());

            StringBuilder urlString = new StringBuilder(getDomain(dto.getServerName()));
            urlString.append("/finstudio/getenctext.do?plainText=");
            urlString.append(URLEncoder.encode(dto.getPlainText(), "UTF-8"));
            
            HttpClient httpClient = new HttpClient();
            PostMethod ps = new PostMethod(urlString.toString());
            httpClient.executeMethod(ps);
            if (ps.getStatusCode() == HttpURLConnection.HTTP_OK) {
                String responseBody = ps.getResponseBodyAsString();
                if(ps.getResponseBodyAsString().contains("Invalid Request")) {
                    throw new IOException(responseBody);
                }
                else if (ps.getResponseBodyAsString().equals("Null")) {
                    throw new IOException(responseBody);
                }
                else {
                    encText = responseBody;
                }
            }
            else {
                throw new IOException("HttpURL not connected.");
            }
        }
        catch(Exception e) {
            encText = "-1";
            Logger.DataLogger("EncDecService | Exception => "+e);
        }
        return encText;
    }
    
    private static String getDomain(String serverName) {
        
        String domainUrl;
        switch(serverName) {
            case "dev":
                domainUrl = "https://dev.njtechdesk.com";
                break;
            case "test":
                domainUrl = "https://test.njtechdesk.com";
                break;
            case "prod":
                domainUrl = "https://apps.njtechdesk.com";
                break;
            default:
                domainUrl = "";
                break;
        }
        return domainUrl;
    }
}
