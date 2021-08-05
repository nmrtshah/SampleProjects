/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.filter;

import com.finlogic.util.properties.HardCodeProperty;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author njuser
 */
public class FinstudioFilter implements Filter
{

    private StringBuffer access_denied_message;
    private StringBuffer file_not_found_message;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        access_denied_message = new StringBuffer();
        access_denied_message.append("<html>");
        access_denied_message.append("<head>");
        access_denied_message.append("<title>Access Denied</title>");
        access_denied_message.append("</head>");
        access_denied_message.append("<body>");
        access_denied_message.append("<div align='center' style='color:red;'>");
        access_denied_message.append("Access Denied");
        access_denied_message.append("</div>");
        access_denied_message.append("</body>");
        access_denied_message.append("</html>");

        file_not_found_message = new StringBuffer();
        file_not_found_message.append("<html>");
        file_not_found_message.append("<head>");
        file_not_found_message.append("<title>File not found.</title>");
        file_not_found_message.append("</head>");
        file_not_found_message.append("<body>");
        file_not_found_message.append("<div align='center' style='color:red;'>");
        file_not_found_message.append("Requested File not found.");
        file_not_found_message.append("</div>");
        file_not_found_message.append("</body>");
        file_not_found_message.append("</html>");
    }

    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        //finutils.errorhandler.ErrorHandler.PrintInLog("/opt/apache-tomcat1/webapps/finstudio/log/script_log.txt", "Filter is in action");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        boolean isFiltered = false;
        boolean isValid = false;
        boolean isFileExist = false;

        HttpSession session;
        session = req.getSession(false);
        
        try
        {
            String reqURL = req.getRequestURL().toString();

            /*VALIDATION FOR DATA REQUEST EXECUTOR*/

            String dre_regex = "/generated/DRE_(\\d)+\\.xls";
            Matcher matcher = Pattern.compile(dre_regex).matcher(reqURL);
            if (matcher.find())
            {

                isFiltered = true;

                /*Allow only if Valid Login*/

                HardCodeProperty hcp = new HardCodeProperty();
                String validCodes[];
                String hcpcodes = hcp.getProperty("data_request_valid_executor");

                if (hcpcodes != null && session != null && session.getAttribute("ACLEmpCode") != null)
                {
                    String empCode;
                    empCode = session.getAttribute("ACLEmpCode").toString();
                    validCodes = hcpcodes.split(",");
                    for (String validCode : validCodes)
                    {
                        if (empCode.equals(validCode))
                        {
                            isValid = true;
                            break;
                        }
                    }
                }
                if (isValid)
                {
                    String reqFile = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio" + matcher.group();
                    //finutils.errorhandler.ErrorHandler.PrintInLog("/opt/apache-tomcat1/webapps/finstudio/log/script_log.txt", "file requested = "+reqFile);
                    isFileExist = new File(reqFile).exists();
                }
            }

        }
        catch (Exception e)
        {
            isValid = false;
            finutils.errorhandler.ErrorHandler.PrintInFile(e, "/opt/apache-tomcat1/webapps/finstudio/log/script_log.txt", "Error in FinstudioFilter.");
        }

        if (isFiltered && !isValid)
        {
            res.getOutputStream().print(access_denied_message.toString());
        }
        else if (isFiltered && !isFileExist)
        {
            res.getOutputStream().print(file_not_found_message.toString());
        }
        else
        {
            chain.doFilter(request, response);
        }
    }
}
