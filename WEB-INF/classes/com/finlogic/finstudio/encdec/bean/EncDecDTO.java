/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.finstudio.encdec.bean;

import java.util.ArrayList;

/**
 *
 * @author Jigna Patel
 */
public class EncDecDTO
{
    private String serverName;
    private String plainText;
    private String encText;
    
    private ArrayList<String> allowedServer;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getEncText() {
        return encText;
    }

    public void setEncText(String encText) {
        this.encText = encText;
    }

    public ArrayList<String> getAllowedServer() {
        return allowedServer;
    }

    public void setAllowedServer(ArrayList<String> allowedServer) {
        this.allowedServer = allowedServer;
    }
}