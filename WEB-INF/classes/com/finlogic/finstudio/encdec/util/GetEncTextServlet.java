/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.finstudio.encdec.util;

import com.finlogic.util.PasswordUtil;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jigna Patel
 */
public class GetEncTextServlet extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String plainText;
        
        try {
            plainText = request.getParameter("plainText");
            
            if(plainText.trim().isBlank()) {
                throw new IOException("Empty Plain Text not allowed.");
            }
            response.getOutputStream().print(PasswordUtil.getEncodedPassword(plainText));
        }
        catch(Exception e) {
            response.getOutputStream().print(e.getMessage());
        }
    }
}