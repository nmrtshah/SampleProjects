/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finfilter;

import com.finlogic.util.persistence.SQLUtility;
import com.finlogic.util.tags.FileBoxHandler;
import com.finlogic.util.ws.WSValidator;
import finpack.FinPack;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FinWebFilter implements Filter {

    //private static String logPath = "/opt/apache-tomcat1/webapps/finstudio/log/datalog.txt";
    private static List blockList;
    private static Set wsSet;
    private static long lastMod = 0;
    private FileBoxHandler fileBoxHandler = new FileBoxHandler();
    private String serverType = "";
    private String serverName = "";
    private final int CACHE_DUR_IN_SEC = 60 * 60 * 36; // 36 Hours
    private final long CACHE_DUR_IN_MS = CACHE_DUR_IN_SEC * 1000;
    private final String allowedUploadExt = ",.bmp,.csv,.dbf,.doc,.docx,.eml,.fla,.flt,.flv,.gif,.jpeg,.jpg,.mp3,.odg,.odp,.ods,.odt,.pdf,.png,.pps,.ppsx,.ppt,.pptx,.psv,.rar,.swf,.tif,.tiff,.txt,.wri,.xls,.xlsx,.xml,.zip,.iso-fmr,.mp4,.ogg,.ai,.cdr,.eps,.mpeg,.avi,.mkv,.html,";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

//        try
//        {
//            ACLAgent aclAgent = new ACLAgent();
//            aclAgent.hasAccess(httpRequest, httpResponse);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        if (isMultiPartSpringRequest(httpRequest)) {
            CommonsMultipartResolver cmr = new CommonsMultipartResolver(httpRequest.getServletContext());
            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) cmr.resolveMultipart(httpRequest);

            if (!checkMultiPart(mhsr, httpResponse)
                    && !isFileBox(mhsr, httpResponse)
                    && !checkRequestParams(mhsr, httpResponse)
                    && !checkRefererBlocking(mhsr, httpResponse)
                    && !checkSiteBlocking(mhsr, httpResponse)
                    && !hasMapped(mhsr, httpResponse)) {
                compMap(httpRequest);
                checkCacheRefresh(mhsr, httpResponse);
                chain.doFilter(mhsr, res);
            }
        } else if (!isFileBox(httpRequest, httpResponse)
                && isValidWSClient(httpRequest, httpResponse)
                && !isWSRegistered(httpRequest, httpResponse)
                && !checkRequestParams(httpRequest, httpResponse)
                && !checkRefererBlocking(httpRequest, httpResponse)
                && !checkTomcatManager(httpRequest, httpResponse)
                && !checkSiteBlocking(httpRequest, httpResponse)
                && !hasMapped(httpRequest, httpResponse)) {
            compMap(httpRequest);
            checkCacheRefresh(httpRequest, httpResponse);
            chain.doFilter(req, res);
        }

    }

    private boolean isMultiPartSpringRequest(HttpServletRequest httpRequest) throws IOException, ServletException {
        boolean isMultiPart = false;
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.endsWith(".fin")) {
            String contentType = httpRequest.getContentType();
            if (contentType != null
                    && contentType.contains("multipart/form-data")) {
                writeLog("FinWebFilter.isMultiPartSpringRequest Log requestURI=" + requestURI);
                isMultiPart = true;
            }
        }

        // to skip project as per property file
        String skipMultiPart = (FinPack.getProperty("skipMultiPart") == null) ? "" : FinPack.getProperty("skipMultiPart");
        if (skipMultiPart.contains("," + httpRequest.getContextPath() + ",")) {
            isMultiPart = false;
        }

        return isMultiPart;
    }

    private boolean checkMultiPart(MultipartHttpServletRequest mhsr, HttpServletResponse httpResponse) throws IOException, ServletException {
        boolean accessDeniedFlag = false;

        Map fileMap = mhsr.getFileMap();
        if (fileMap != null) {
            for (Iterator it = fileMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                MultipartFile value = (MultipartFile) entry.getValue();
                String originalFilename = value.getOriginalFilename();

                if (originalFilename != null && originalFilename.trim().length() != 0) {
                    int dotIndex = originalFilename.lastIndexOf(".");
                    if (dotIndex != -1) {
                        String ext = originalFilename.substring(dotIndex);
                        if (!(allowedUploadExt.contains("," + ext.toLowerCase() + ",")
                                || ext.matches("\\.[0-9]{3}"))) {
                            writeLog("FinWebFilter.checkMultiPart Log requestURI=" + mhsr.getRequestURI() + " key=" + key + " getContentType=" + value.getContentType() + " getName=" + value.getName() + " getOriginalFilename=" + originalFilename + " accessDeniedFlag=" + accessDeniedFlag);
                            accessDeniedFlag = true;
                            break;
                        }
                    } else {
                        writeLog("FinWebFilter.checkMultiPart Log requestURI=" + mhsr.getRequestURI() + " key=" + key + " getContentType=" + value.getContentType() + " getName=" + value.getName() + " getOriginalFilename=" + originalFilename + " accessDeniedFlag=" + accessDeniedFlag);
                        accessDeniedFlag = true;
                        break;
                    }
                }
            }
        }

        if (accessDeniedFlag) {
            blockSite(httpResponse, "Access Denied", "Invalid file type found while uploading. Please contact R & D team for more detail.", "NA");
        }
        return accessDeniedFlag;
    }

    private boolean checkTomcatManager(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        String requestURI = httpRequest.getRequestURI();
        boolean accessDeniedFlag = true;

        if (requestURI.contains("/manager/")) {
            String remoteAddr = httpRequest.getRemoteAddr();
            int userType = 0; // 1=admin,2=subadmin,3=user

            String accessString = FinPack.getProperty("tomcatManager");

            if (accessString.contains("," + remoteAddr + ":A,")) {
                userType = 1;
            } else if (accessString.contains("," + remoteAddr + ":B,")) {
                userType = 2;
            } else if (accessString.contains("," + remoteAddr + ":C,")) {
                userType = 3;
            }

            if (userType == 1) {
                accessDeniedFlag = false;
            } else if (userType == 2) {
                if (requestURI.contains("/manager/images/")
                        || requestURI.equals("/manager/html")
                        || requestURI.equals("/manager/html/")
                        || requestURI.equals("/manager/html/start")
                        || requestURI.equals("/manager/html/list")
                        || requestURI.equals("/manager/html/reload")
                        || requestURI.equals("/manager/status")
                        || requestURI.equals("/manager/status/")) {
                    accessDeniedFlag = false;
                }
            } else if (userType == 3) {
                if (requestURI.contains("/manager/images/")
                        || requestURI.equals("/manager/html")
                        || requestURI.equals("/manager/html/")
                        || requestURI.equals("/manager/html/list")
                        || requestURI.equals("/manager/status")
                        || requestURI.equals("/manager/status/")
                        || requestURI.startsWith("/manager/status/;jsessionid=")) {
                    accessDeniedFlag = false;
                }
            }
            if (accessDeniedFlag) {
                blockSite(httpResponse, "Access Denied", "This machine may not be allowed to use this functionality.<!--" + remoteAddr + " " + requestURI + "-->", "NA");
                writeLog("FinWebFilter.checkTomcatManager This machine may not be allowed to use this functionality (" + remoteAddr + " " + requestURI + ").");
            }
        } else {
            accessDeniedFlag = false;
        }
        return accessDeniedFlag;
    }

    private void compMap(HttpServletRequest httpRequest) throws IOException, ServletException {
        try {

            String uri = httpRequest.getRequestURI();

            if (uri.contains("/wfm/ModuleComponentMaping.fin")) {
                String resolver = httpRequest.getParameter("resolver");
                if ("addModuleComponent".equals(resolver)) {
                    String rIP = FinPack.getRemoteIP(httpRequest);
                    if (rIP == null) {
                        rIP = "NA";
                    }

                    HttpSession session = httpRequest.getSession();
                    String empCode = "NA";
                    if (session != null && session.getAttribute("ACLEmpCode") != null) {
                        empCode = String.valueOf(session.getAttribute("ACLEmpCode"));
                    }
                    String newComponent = httpRequest.getParameter("component");
                    String mainComponent = httpRequest.getParameter("component_hid");
                    String subComponentIDs[] = httpRequest.getParameterValues("subcomponent");
                    String moduleId = httpRequest.getParameter("module");
                    String subComponentIDAll = "0";

                    for (int i = 0; i < subComponentIDs.length; i++) {
                        subComponentIDAll += "," + subComponentIDs[i];
                    }
                    writeLog("FinWebFilter.compMap IP:" + rIP + " uri=" + uri + " moduleId=" + moduleId + " newComponent=" + newComponent + " mainComponent=" + mainComponent + " subComponentIDAll=" + subComponentIDAll + " empCode=" + empCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCacheRefresh(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        try {
            String rIP = FinPack.getRemoteIP(httpRequest);
            if (rIP == null) {
                rIP = "";
            }

            if (serverType.equals("dev") || serverType.equals("test")
                    || rIP.contains("192.168.71.") || rIP.contains("192.168.61.")) {
                httpResponse.addHeader("x-custom-header-ip", httpRequest.getLocalAddr());
            }
//
//            String contextPath = httpRequest.getContextPath();
//            contextPath = contextPath.replace("/", "");
//            String requestURI = httpRequest.getRequestURI();
//            String requestURIStatic = requestURI.trim().toUpperCase();
//
//            if (requestURIStatic.endsWith(".JS")
//                    || requestURIStatic.endsWith(".CSS"))
//            {
//                String cacheRefresh = FinPack.getProperty("cache_refresh");
////                System.out.println("CacheRefresh cacheRefresh=" + cacheRefresh);
//                if (cacheRefresh != null && cacheRefresh.contains("," + contextPath + ";"))
//                {
//                    Calendar c = Calendar.getInstance();
//                    String strDate = c.get(Calendar.DAY_OF_MONTH) + "-"
//                            + (c.get(Calendar.MONTH) + 1) + "-"
//                            + c.get(Calendar.YEAR);
//                    String[] array = cacheRefresh.split(",");
//
//                    for (int i = 0; i < array.length; i++)
//                    {
//                        String[] string = array[i].split(";");
////                        System.out.println("CacheRefresh string[0]=" + string[0]);
////                        System.out.println("CacheRefresh string[1]=" + string[1]);
////                        System.out.println("CacheRefresh contextPath=" + contextPath);
////                        System.out.println("CacheRefresh strDate=" + strDate);
//
//                        if (contextPath.equals(string[0]) && string[1].contains(strDate))
//                        {
//                            long lMod = System.currentTimeMillis();
//                            httpResponse.addHeader("Cache-Control", "max-age=" + CACHE_DUR_IN_SEC);
//                            httpResponse.addHeader("Cache-Control", "must-revalidate");
//                            httpResponse.setDateHeader("Last-Modified", lMod);
//                            httpResponse.setDateHeader("Expires", lMod + CACHE_DUR_IN_MS);
//                            httpResponse.setCharacterEncoding("UTF-8");
//                            writeLog("FinWebFilter.checkCacheRefresh CacheRefreshed for " + requestURI);
//                        }
//                    }
//                }
//            }
            {

            }
        } catch (Exception e) {
            writeLog("FinWebFilter.checkCacheRefresh CacheRefresh Error " + e.getMessage());
        }
    }

    private boolean hasMapped(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        try {
            if (!(serverType.equals("dev") || serverType.equals("test"))) {
                return false;
            }
            if (!(serverName.startsWith("devweb1") || serverName.startsWith("devweb2")
                    || serverName.startsWith("testweb1") || serverName.startsWith("testweb2"))) {
                return false;
            }

            String requestURI = httpRequest.getRequestURI();
            if (requestURI.endsWith("/")) {
                return false;
            }

            // skip as per mail from Kinjal Sir
            if (serverType.equals("test")
                    && (requestURI.contains("/pmsdesk/")
                    || requestURI.contains("/NA/"))) {
                return false;
            }
            // skip log access url
            if (requestURI.equals("/log/index.jsp")) {
                return false;
            }

            String contextPath = httpRequest.getContextPath();
            String requestURIStatic = requestURI.trim().toUpperCase();

            // manager accessed with IP, so skipped
            // ws accessed with IP, so skipped
            // finstudio accessed with IP for File Deployment, so skipped
            // ecommon shipped as per request from Kinjal Sir
            // mobapp accessed with IP (due to ssl issue in mobile), so skipped
            if (requestURI.contains("/manager/")
                    || requestURI.contains("/ws/")
                    || requestURI.contains("/mobapp")
                    || requestURI.contains("/ecommon")
                    || requestURI.contains("/finstudio/")
                    || requestURIStatic.lastIndexOf(".GIF") > 0
                    || requestURIStatic.lastIndexOf(".ICO") > 0
                    || requestURIStatic.lastIndexOf(".SWF") > 0
                    || requestURIStatic.lastIndexOf(".FLV") > 0
                    || requestURIStatic.lastIndexOf(".BMP") > 0
                    || requestURIStatic.lastIndexOf(".ZIP") > 0
                    || requestURIStatic.lastIndexOf(".RAR") > 0
                    || requestURIStatic.lastIndexOf(".DBF") > 0
                    || requestURIStatic.lastIndexOf(".DAT") > 0
                    || requestURIStatic.lastIndexOf(".JPEG") > 0
                    || requestURIStatic.lastIndexOf(".TIFF") > 0
                    || requestURIStatic.lastIndexOf(".JPG") > 0
                    || requestURIStatic.lastIndexOf(".XML") > 0
                    || requestURIStatic.lastIndexOf(".PDF") > 0
                    || requestURIStatic.lastIndexOf(".CSV") > 0
                    || requestURIStatic.lastIndexOf(".CLASS") > 0
                    || requestURIStatic.lastIndexOf(".JAD") > 0
                    || requestURIStatic.lastIndexOf(".COD") > 0
                    || requestURIStatic.lastIndexOf(".JAR") > 0
                    || requestURIStatic.lastIndexOf(".XLS") > 0
                    || requestURIStatic.lastIndexOf(".DOC") > 0
                    || requestURIStatic.lastIndexOf(".PPT") > 0
                    || requestURIStatic.lastIndexOf(".ODS") > 0
                    || requestURIStatic.lastIndexOf(".ODG") > 0
                    || requestURIStatic.lastIndexOf(".SQL") > 0
                    || requestURIStatic.lastIndexOf(".TXT") > 0
                    || requestURIStatic.lastIndexOf(".ODT") > 0
                    || requestURIStatic.lastIndexOf(".PNG") > 0
                    || requestURIStatic.lastIndexOf(".EOT") > 0
                    || requestURIStatic.lastIndexOf(".SVG") > 0
                    || requestURIStatic.lastIndexOf(".TTF") > 0
                    || requestURIStatic.lastIndexOf(".WOFF") > 0
                    || (requestURIStatic.lastIndexOf(".JS") > 0
                    && requestURIStatic.lastIndexOf(".JSP") == -1)
                    || requestURIStatic.lastIndexOf(".CSS") > 0) {
                return false;
            }

            String hostName = httpRequest.getHeader("Host");

            SQLUtility sqlu = new SQLUtility();
            String sql;
            int count;

            // check host
//            SQLUtility sqlu = new SQLUtility();
//            String sql = "SELECT COUNT(*) FROM WFM.PROJECT_MST WHERE HOST='" + hostName + "'";
//            int count = sqlu.getInt("acl_offline", sql);
//
//            if (count == 0)
//            {
//                String helpSql = "";
//                if (hostName.contains("test.") || hostName.contains("dev."))
//                {
//                    helpSql = "UPDATE WFM.PROJECT_MST SET HOST='" + hostName.replace("dev.", "www.").replaceAll("test.", "www.") + "' WHERE PRJ_ID=<id>"
//                            + "go"
//                            + "UPDATE WFM.PROJECT_MST_TEST SET HOST='" + hostName.replace("dev.", "test.") + "' WHERE PRJ_ID=<id>"
//                            + "go"
//                            + "UPDATE WFM.PROJECT_MST_DEV SET HOST='" + hostName.replace("test.", "dev.") + "' WHERE PRJ_ID=<id>"
//                            + "go";
//                }
//
////                blockSite(httpResponse, "Invalid Request", "Requested Domain Name "
////                        + hostName + " not mapped. Please contact R & D team for more detail.", "NA");
//                System.out.println("HAS-ACCESS Requested Domain Name " + hostName + " not mapped.");
//                System.out.println("HAS-ACCESS DEBUG Requested Domain Name " + hostName + " not mapped " + requestURI);
//                return true;
//            }
            // check domain
            contextPath = contextPath.replace("/", "");

            sql = "SELECT PM.PRJ_ID FROM WFM.PROJECT_MST PM "
                    + "LEFT JOIN WFM.PROJECT_DOMAIN_MST PDM ON PDM.PRJ_ID=PM.PRJ_ID "
                    + "WHERE HOST='" + hostName + "' "
                    + "AND (PM.DOMAIN_NAME='" + contextPath + "' "
                    + "OR PDM.DOMAIN_NAME='" + contextPath + "')";

            List prjList = sqlu.getList("acl_offline", sql);
            StringBuilder prjId = new StringBuilder();

            if (prjList == null || prjList.isEmpty()) {
//                String helpSql = "UPDATE WFM.PROJECT_MST SET DOMAIN_NAME='" + contextPath + "' WHERE PRJ_ID=<id>"
//                        + "go"
//                        + "UPDATE WFM.PROJECT_MST_TEST SET DOMAIN_NAME='" + contextPath + "' WHERE PRJ_ID=<id>"
//                        + "go"
//                        + "UPDATE WFM.PROJECT_MST_DEV SET DOMAIN_NAME='" + contextPath + "' WHERE PRJ_ID=<id>"
//                        + "go"
//                        + "---OR---"
//                        + "INSERT INTO WFM.PROJECT_DOMAIN_MST VALUES(<id>,'" + contextPath + "')"
//                        + "GO";

                blockSite(httpResponse, "Invalid Request", "Requested Project Name " + contextPath + " not mapped or not mapped with Domain Name " + hostName + ". Please contact R & D team for more detail.", "NA");
                writeLog("FinWebFilter.hasMapped Requested Project Name " + contextPath + " not mapped or not mapped with Domain Name " + hostName + ".");
                return true;
            } else {
                for (int i = 0; i < prjList.size(); i++) {
                    Map m = (Map) prjList.get(i);
                    prjId.append(String.valueOf(m.get("PRJ_ID"))).append(",");
                }
                prjId.deleteCharAt(prjId.length() - 1);
            }

            // check uri
            String compName = requestURI.split("/")[requestURI.split("/").length - 1];

            sql = "SELECT COMPONENT_ID FROM WFM.COMPONENT_MST WHERE PRJ_ID IN (" + prjId.toString() + ") AND NAME='" + compName + "'";

            List compList = sqlu.getList("acl_offline", sql);
            StringBuilder compIds = new StringBuilder();

            if (compList == null || compList.isEmpty()) {
                blockSite(httpResponse, "Invalid Request", "Requested Component Name " + compName + " not mapped with Project Id " + prjId + ". Please contact R & D team for more detail.", "NA");
                writeLog("FinWebFilter.hasMapped Requested Component Name " + compName + " not mapped with Project Id(s) " + prjId.toString() + " (" + contextPath + ").");
                return true;
            } else {
                for (int i = 0; i < compList.size(); i++) {
                    Map m = (Map) compList.get(i);
                    compIds.append(String.valueOf(m.get("COMPONENT_ID"))).append(",");
                }
                compIds.deleteCharAt(compIds.length() - 1);
            }

            // CHECK MODULE
            sql = "SELECT COUNT(*) FROM ( "
                    + "SELECT MODULE_ID FROM WFM.MODULE_MST WHERE COMPONENT_ID IN (" + compIds.toString() + ") "
                    + "UNION ALL "
                    + "SELECT MODULE_ID FROM WFM.MODULE_COMPONENT_MAP WHERE COMPONENT_ID IN (" + compIds.toString() + ") ) X1";

            count = sqlu.getInt("acl_offline", sql);

            if (count == 0) {
                blockSite(httpResponse, "Invalid Request", "Requested Component Name " + compName + " not mapped with Module. Please contact R & D team for more detail.", "NA");
                writeLog("FinWebFilter.hasMapped Requested Component Name " + compName + " not mapped with Module" + " (" + contextPath + ").");
                return true;
            }

        } catch (Exception e) {
            writeLog("FinWebFilter.hasMapped Error " + e.getMessage());
            //e.printStackTrace();
        }

        return false;
    }

    private boolean checkRequestParams1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        Enumeration<String> e = httpRequest.getParameterNames();
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();

        if (serverType.equals("prod") || serverType.equals("mytest")) {
            if (!(contextPath.contains("exchangetrax")
                    || contextPath.contains("tradingaccount")
                    || contextPath.contains("capitalmarket")
                    || contextPath.contains("mfund")
                    || (contextPath.contains("fileaccess")
                    && (requestURI.contains("ViewBrokerageBillPdf.fin")
                    || requestURI.contains("mfundViewDelAttachment.fin")
                    || requestURI.contains("/mfund/servlet/adv_update_brok_table")
                    || requestURI.contains("ViewDelAttachmentNew.fin"))))) {
                while (e.hasMoreElements()) {

                    String paramName = e.nextElement();
                    String paramValue = httpRequest.getParameter(paramName);

                    if (paramValue != null && paramValue.startsWith("/")) {
                        File f = new File(paramValue);
                        String absolutePath = f.getAbsolutePath();
                        if (f.exists()
                                && !absolutePath.equals("/")
                                && !paramValue.contains("webapps" + contextPath)) {
                            writeLog("FinWebFilter.checkRequestParams Abs Path requestURI=" + requestURI + " " + paramName + "=" + paramValue);
                            //blockSite(httpResponse, "Invalid Request", "Request parameter contains invalid value (absolute path).", "NA");
                            blockSite(httpResponse, "Invalid Request", "There is some technical problem, please try after some time.", "NA");
                            return true;
                        }
                    } else {
                        if (checkForSqlInj(paramValue)) {
                            writeLog("FinWebFilter.checkRequestParams Sql Inj requestURI=" + requestURI + " " + paramName + "=" + paramValue.replaceAll("\n", " "));
                            if (!serverName.equals("apps.njtechdesk.com")) {
                                //blockSite(httpResponse, "Invalid Request", "Request parameter contains invalid value (sql string).", "NA");
                                blockSite(httpResponse, "Invalid Request.", "There is some technical problem, please try after some time.", "NA");
                                return true;
                            }
                        }

                    }
                }
            }
        } else {
            while (e.hasMoreElements()) {
                String paramName = e.nextElement();
                String paramValue = httpRequest.getParameter(paramName);

                if (paramValue != null && paramValue.startsWith("/")) {
                    File f = new File(paramValue);
                    if (f.exists()
                            && !f.getAbsolutePath().equals("/")) {
                        writeLog("FinWebFilter.checkRequestParams Abs Path requestURI=" + requestURI + " " + paramName + "=" + paramValue);
                        blockSite(httpResponse, "Invalid Request", "Request parameter contains invalid value (absolute path)."
                                + " Please contact R & D team for more detail.", "NA");
                        return true;
                    }
                } else {
                    if (paramValue != null && paramValue.contains("=") && paramValue.toUpperCase().contains(" OR ")) {
                        writeLog("FinWebFilter.checkRequestParams Sql Inj Log requestURI=" + requestURI + " " + paramName + "=" + paramValue.replaceAll("\n", " "));
                    }

                    // njwealth skipped as per mail from sweta upto date 10-06-2014
                    //if (!(requestURI.contains("/mfund/servlet/adv_update_brok_table") || requestURI.contains("/njwealth/")))
                    if (!(requestURI.contains("/mfund/servlet/adv_update_brok_table"))) {
                        // do not check on howeb3
                        if (checkForSqlInj(paramValue)) {
                            writeLog("FinWebFilter.checkRequestParams Sql Inj requestURI=" + requestURI + " " + paramName + "=" + paramValue.replaceAll("\n", " "));
                            blockSite(httpResponse, "Invalid Request.", "Request parameter contains invalid value (sql string)."
                                    + " Please contact R & D team for more detail.", "NA");
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean checkRequestParams(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        Enumeration<String> e = httpRequest.getParameterNames();
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();
        boolean absFlag = false;
        boolean sqlFlag = false;

        while (e.hasMoreElements()) {
            String paramName = e.nextElement();
            String[] paramValues = httpRequest.getParameterValues(paramName);

            int paramLen = paramValues.length;

            for (int i = 0; i < paramLen; i++) {
                String paramValue = paramValues[i];

                if (paramValue != null) {
                    // absolute path
                    if (paramValue.startsWith("/")) {
                        File f = new File(paramValue);
                        String absolutePath = f.getAbsolutePath();
                        if (f.exists()
                                && !absolutePath.equals("/")) {
                            writeLog("FinWebFilter.checkRequestParams Abs Path requestURI=" + requestURI + " " + paramName + "=" + paramValue);
                            absFlag = true;

                          //Updated patch1 - for finstudio filedirauto req module - finstudio will be able to serve other application files
                            if (contextPath.equals("/finstudio"))
                            {
                                absFlag = false;
                            }
                            else if (serverType.equals("prod") && paramValue.contains("webapps" + contextPath))
                            {
                                absFlag = false;
                            }
			// patch 2 - skip some projects if access within tomcat or apache on prod only
                            else if (serverType.equals("prod")
                                    && (contextPath.contains("exchangetrax")
                                    || contextPath.contains("tradingaccount")
                                    || contextPath.contains("capitalmarket")
                                    // removed as per talked with Krunal
                                    //|| contextPath.contains("mfund")
                                    || contextPath.contains("fileaccess"))) {
                                if ((paramValue.startsWith("/opt/") && paramValue.contains("/webapps/"))
                                        || (paramValue.startsWith("/var/") && paramValue.contains("/html/"))) {
                                    absFlag = false;
                                }
                            }
                        }

                    }
                }
                // sql inj
                // check for length 8 as 1' OR 1=1
                if (paramValue.trim().length() > 8) {
                    if (paramValue.contains("=") && paramValue.toUpperCase().contains(" OR ")) {
                        writeLog("FinWebFilter.checkRequestParams Sql Inj Log requestURI=" + requestURI + " " + paramName + "=" + paramValue.replaceAll("\n", " "));
                    }
                    if (checkForSqlInj(paramValue)) {
                        writeLog("FinWebFilter.checkRequestParams Sql Inj requestURI=" + requestURI + " " + paramName + "=" + paramValue.replaceAll("\n", " "));
                        sqlFlag = true;
                        // patch 1 - skip some project or url for genuine case (for all servers)
                        if (requestURI.contains("/mfund/servlet/adv_update_brok_table")
                                || requestURI.contains("/reportwriter/querydisplay.fin")
                                || requestURI.contains("/reportwriter/storequery.fin")
                                || requestURI.contains("/finstudio/findatareqexec.fin")) {
                            sqlFlag = false;
                        } // patch 2 - skip checking on howeb3
                        else if (serverName.equals("apps.njtechdesk.com")) {
                            sqlFlag = false;
                        }

                    }
                }

//                if (checkForHTMLInj(paramValue)){
//                    writeLog(new StringBuilder().append("FinWebFilter.checkRequestParams HTMLInjection requestURI=").append(requestURI).append(" ").append(paramName).append("=").append(paramValue.replaceAll("\n", " ")).toString());
//                }
            }

            if (sqlFlag || absFlag) {
                break;
            }

        }

        if (serverType.equals("prod") || serverType.equals("mytest")) {
            if (absFlag) {
                blockSite(httpResponse, "Invalid Request", "There is some technical problem, please try after some time.", "NA");
                return true;
            }
            if (sqlFlag) {
                blockSite(httpResponse, "Invalid Request.", "There is some technical problem, please try after some time.", "NA");
                return true;
            }
        } else if (serverType.equals("dev") || serverType.equals("test")) {
            if (absFlag) {
                blockSite(httpResponse, "Invalid Request", "Request parameter contains invalid value (absolute path)."
                        + " Please contact R & D team for more detail.", "NA");
                return true;
            }
            if (sqlFlag) {
                blockSite(httpResponse, "Invalid Request.", "Request parameter contains invalid value (sql string)."
                        + " Please contact R & D team for more detail.", "NA");
                return true;
            }
        }
        return false;
    }

    private boolean isValidWSClient(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        boolean flag = true;
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.contains("/ws/")
                && (requestURI.contains("service")
                || requestURI.contains("HRWS"))) {
            flag = WSValidator.isValidClient(httpRequest, "NA");
            if (!flag) {
                writeLog("FinWebFilter.isValidWSClient " + requestURI);
                blockSite(httpResponse, "UnAuthorized Access", "Client not allowed to call this service.", "NA");
            }
        }
        return flag;
    }

    private boolean isWSRegistered(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        boolean flag = false;
        if (serverType.equals("dev") || serverType.equals("test")) {
            if (wsSet == null) {
                fillWSEntries();
            }
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.endsWith("/ws/") || requestURI.endsWith("/ws//")) {
                flag = false;
            } else if (requestURI.contains("/ws/")
                    && !wsSet.contains(requestURI)) {
                flag = true;
                writeLog("FinWebFilter.isWSRegistered " + requestURI);
                blockSite(httpResponse, "Access Denied", "This Web Service is not registered."
                        + "<br>Contact to R & D team and register it first.", "NA");
            }
        }
        return flag;
    }

    private static void fillWSEntries() {
        wsSet = new HashSet<String>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("/tomcat/WSConsumer.xml"));

            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//bean[@class=\"org.apache.cxf.jaxws.JaxWsProxyFactoryBean\"]");
            Object exprResult = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList listOfNodes = (NodeList) exprResult;

            for (int s = 0; s < listOfNodes.getLength(); s++) {
                NodeList childProNodeList = ((Element) listOfNodes.item(s)).getElementsByTagName("property");
                String url[] = ((Element) childProNodeList.item(1)).getAttribute("value").split("/");
                wsSet.add("/" + url[3] + "/" + url[4] + "/" + url[5].toLowerCase().replace("?wsdl", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isFileBox(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        boolean flag = false;

        if (httpRequest.getRequestURI().endsWith("/finfilebox.do")) {
            flag = true;
            fileBoxHandler.service(httpRequest, httpResponse);
        }
        return flag;
    }

    private boolean checkRefererBlocking(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        String referer = httpRequest.getHeader("referer");
        boolean flag = false;

        if (referer != null & !"".equals(referer)) {
            String requestURL = httpRequest.getRequestURL().toString();
            // if request is for finlibrary then allow
            // as devlopers using from finlibrary from server only
            if (!requestURL.contains("/finlibrary/")) {
                String requestHost = new URL(requestURL).getHost();
                //String refererHost = new URI(referer).getHost();;

                String refererHost = null;
                try {
                    refererHost = new URI(referer).getHost();
                } catch (Exception e) {
                    writeLog("Exception in Project level - Filter - URI change::" + e.getMessage());
                }

                // if fromrequest and torequest servers are same then allow
                if (!requestHost.equals(refererHost)) {
                    String[] deniedHostArr = FinPack.getProperty("denied_referer_host").split(",");

                    for (int i = 0; i < deniedHostArr.length; i++) {
                        if (refererHost.matches(deniedHostArr[i])) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        writeLog("FinWebFilter.checkRefererBlocking " + referer + " " + requestURL);
                        blockSite(httpResponse, "Invalid Request", "Access Denied. Invalid Referer.", "NA");
                    }
                }
            }
        }
        return flag;
    }

    private boolean checkSiteBlocking(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        fillBlockList();
        HttpSession httpSession = httpRequest.getSession(false);

        int lstSize = blockList.size();
        for (int b = 0; b < lstSize; b++) {
            Map row = (Map) blockList.get(b);

            String vHost = (row.get("vHost") == null) ? "NA" : (String) row.get("vHost");
            String vURI = (row.get("vURI") == null) ? "NA" : (String) row.get("vURI");
            String vParamNames = (row.get("vParamNames") == null) ? "NA" : (String) row.get("vParamNames");
            String vParamValues = (row.get("vParamValues") == null) ? "NA" : (String) row.get("vParamValues");
            String vSessionAttributeNames = (row.get("vSessionAttributeNames") == null) ? "NA" : (String) row.get("vSessionAttributeNames");
            String vSessionAttributeValues = (row.get("vSessionAttributeValues") == null) ? "NA" : (String) row.get("vSessionAttributeValues");
            String vAccessFlag = (row.get("vAccessFlag") == null) ? "NA" : (String) row.get("vAccessFlag");
            String vAllowedIP = (row.get("vAllowedIP") == null) ? "NA" : (String) row.get("vAllowedIP");
            String vTitle = (row.get("vTitle") == null) ? "NA" : (String) row.get("vTitle");
            String vMessage = (row.get("vMessage") == null) ? "NA" : (String) row.get("vMessage");
            String vBlockPeriod = (row.get("vBlockPeriod") == null) ? "NA" : (String) row.get("vBlockPeriod");
            String vRedirectURI = (row.get("vRedirectURI") == null) ? "NA" : (String) row.get("vRedirectURI");

            int fSession = 0;

            // check host, uri and param
            if (checkHost(httpRequest, vHost)
                    && checkURI(httpRequest, vURI)
                    && checkParam(httpRequest, vParamNames, vParamValues)) {
                // check session grant or denied

                if (!(vSessionAttributeNames.equalsIgnoreCase("NA") || vSessionAttributeNames.equalsIgnoreCase("N/A"))) {
                    String[] vSessionAttributeNamesArray = vSessionAttributeNames.split(",");
                    String[] vSessionAttributeValuesArray = vSessionAttributeValues.split(",");

                    if (vSessionAttributeNamesArray.length == vSessionAttributeValuesArray.length) {
                        int counter = 0;
                        int i = 0;
                        for (int j = 0; j < vSessionAttributeNamesArray.length; j++) {
                            if (httpSession.getAttribute(vSessionAttributeNamesArray[j]) != null
                                    && Arrays.asList(vSessionAttributeValuesArray[j].split(";")).contains(httpSession.getAttribute(vSessionAttributeNamesArray[j]).toString())) {
                                counter++;
                            }
                            i++;
                        }
                        if (counter != 0 && counter == i) {
                            fSession = 1;
                        }
                    }
                } else {
                    fSession = 1;
                }

                if ((vAccessFlag.equalsIgnoreCase("DENIED") && fSession == 1)
                        || (vAccessFlag.equalsIgnoreCase("GRANT") && fSession == 0)) {
                    // check allowed IP

                    if (!Arrays.asList(vAllowedIP.split(",")).contains(getRemoteHost(httpRequest))) {
                        // check period
                        if (isBlockPeriod(vBlockPeriod)) {
                            blockSite(httpResponse, vTitle, vMessage, vRedirectURI);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String getRemoteHost(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String x;
        if ((x = request.getHeader("X-FORWARDED-FOR")) != null) {
            remoteAddr = x;
            int idx = remoteAddr.indexOf(',');
            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }
        return remoteAddr;
    }

    private void fillBlockList() throws IOException, ServletException {
        try {
            File file = new File("/tomcat/BlockURL.xml");

            if (file.lastModified() > lastMod || blockList == null) {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();
                NodeList listOfblocks = doc.getElementsByTagName("block");

                List l = new ArrayList();

                for (int blockno = 0; blockno < listOfblocks.getLength(); blockno++) {
                    Node BlockNode = listOfblocks.item(blockno);
                    if (BlockNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element blockElement = (Element) BlockNode;
                        Map block = new HashMap();
                        block.put("vHost", getElement(blockElement, "host"));
                        block.put("vURI", getElement(blockElement, "uri"));
                        block.put("vParamNames", getElement(blockElement, "paramNames"));
                        block.put("vParamValues", getElement(blockElement, "paramValues"));
                        block.put("vSessionAttributeNames", getElement(blockElement, "sessionAttributeNames"));
                        block.put("vSessionAttributeValues", getElement(blockElement, "sessionAttributeValues"));
                        block.put("vAccessFlag", getElement(blockElement, "accessFlag"));
                        block.put("vAllowedIP", getElement(blockElement, "allowedIP"));
                        block.put("vTitle", getElement(blockElement, "title"));
                        block.put("vMessage", getElement(blockElement, "message"));
                        block.put("vBlockPeriod", getElement(blockElement, "blockPeriod"));
                        block.put("vRedirectURI", getElement(blockElement, "redirectURI"));
                        l.add(block);
                    }
                }
                blockList = l;
                lastMod = file.lastModified();
            }
        } catch (ParserConfigurationException e) {
            throw new ServletException(e);
        } catch (SAXException e) {
            throw new ServletException(e);
        }

    }

    private String getElement(Element blockElement, String elementName) {
        String out;
        try {
            out = ((Node) ((Element) (blockElement.getElementsByTagName(elementName)).item(0)).getChildNodes().item(0)).getNodeValue().trim();
        } catch (Exception e) {
            System.out.println("Error in FinWebFilter.getElement : " + e.getMessage());
            out = "NA";
        }
        return out;

    }

    private boolean checkHost(HttpServletRequest httpRequest, String vHost) throws IOException, ServletException {
        boolean flag = false;
        if (!(vHost.equalsIgnoreCase("NA") || vHost.equalsIgnoreCase("N/A"))) {
            URL url = new URL(httpRequest.getRequestURL().toString());
            if (url.getHost().equalsIgnoreCase(vHost)) {
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean checkURI(HttpServletRequest httpRequest, String vURI) throws IOException, ServletException {
        boolean flag = false;
        if (!(vURI.equalsIgnoreCase("NA") || vURI.equalsIgnoreCase("N/A"))) {
            if (httpRequest.getRequestURI().toUpperCase().startsWith(vURI.toUpperCase())) {
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean checkParam(HttpServletRequest httpRequest, String vParamNames, String vParamValues) throws IOException, ServletException {
        boolean flag = false;

        if (!(vParamNames.equalsIgnoreCase("NA") || vParamNames.equalsIgnoreCase("N/A"))) {

            String[] vParamNamesArray = vParamNames.split(",");
            String[] vParamValuesArray = vParamValues.split(",");

            if (vParamNamesArray.length == vParamValuesArray.length) {
                int counter = 0;
                int i = 0;

                for (int j = 0; j < vParamNamesArray.length; j++) {

                    if (httpRequest.getParameter(vParamNamesArray[j]) != null
                            && httpRequest.getParameter(vParamNamesArray[j]).equalsIgnoreCase(vParamValuesArray[j])) {
                        counter++;
                    }
                    i++;
                }
                if (counter != 0 && counter == i) {
                    flag = true;
                }
            }
        } else {
            flag = true;
        }

        return flag;
    }

    private boolean isBlockPeriod(String period) throws IOException {
        boolean flag = false;

        if (period == null || period.toUpperCase().equals("NA") || period.toUpperCase().equals("N/A")) {
            flag = true;
        } else {
            //System.out.println("period " + period);
            String periodType = "";
            if (period.contains("individual")) {
                periodType = "I";
                period = period.replaceAll("individual", "");
            } else if (period.contains("combined")) {
                periodType = "C";
                period = period.replaceAll("combined", "");
            }

            Calendar cal = Calendar.getInstance();
            String lastMonthDay = String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));

            String currMin = String.valueOf(cal.get(Calendar.MINUTE));
            currMin = (currMin.length() == 1) ? "0" + currMin : currMin;
            String currHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
            currHour = (currHour.length() == 1) ? "0" + currHour : currHour;
            String currDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            currDay = (currDay.length() == 1) ? "0" + currDay : currDay;
            String currMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
            currMonth = (currMonth.length() == 1) ? "0" + currMonth : currMonth;
            String currWeek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
            currWeek = (currWeek.length() == 1) ? "0" + currWeek : currWeek;

            String fromPeriod = period.trim().split(":")[0];
            String toPeriod = period.trim().split(":")[1];

            String fromPeriodRow[] = fromPeriod.trim().split(" ");
            String toPeriodRow[] = toPeriod.trim().split(" ");

            String fromMin = (fromPeriodRow[0].equals("*")) ? "00" : fromPeriodRow[0];
            fromMin = (fromMin.length() == 1) ? "0" + fromMin : fromMin;
            String fromHour = (fromPeriodRow[1].equals("*")) ? "00" : fromPeriodRow[1];
            fromHour = (fromHour.length() == 1) ? "0" + fromHour : fromHour;
            String fromDay = (fromPeriodRow[2].equals("*")) ? "01" : fromPeriodRow[2];
            fromDay = (fromDay.length() == 1) ? "0" + fromDay : fromDay;
            String fromMonth = (fromPeriodRow[3].equals("*")) ? "01" : fromPeriodRow[3];
            fromMonth = (fromMonth.length() == 1) ? "0" + fromMonth : fromMonth;
            String fromWeek = (fromPeriodRow[4].equals("*")) ? "01" : fromPeriodRow[4];
            fromWeek = (fromWeek.length() == 1) ? "0" + fromWeek : fromWeek;

            String toMin = (toPeriodRow[0].equals("*")) ? "59" : toPeriodRow[0];
            toMin = (toMin.length() == 1) ? "0" + toMin : toMin;
            String toHour = (toPeriodRow[1].equals("*")) ? "23" : toPeriodRow[1];
            toHour = (toHour.length() == 1) ? "0" + toHour : toHour;
            String toDay = (toPeriodRow[2].equals("*")) ? lastMonthDay : toPeriodRow[2];
            toDay = (toDay.length() == 1) ? "0" + toDay : toDay;
            String toMonth = (toPeriodRow[3].equals("*")) ? currMonth : toPeriodRow[3];
            toMonth = (toMonth.length() == 1) ? "0" + toMonth : toMonth;
            String toWeek = (toPeriodRow[4].equals("*")) ? "07" : toPeriodRow[4];
            toWeek = (toWeek.length() == 1) ? "0" + toWeek : toWeek;

            String fromTime = "1" + fromHour + fromMin;
            String fromDate = "1" + fromMonth + fromDay;
            String fromDateTime = "1" + fromMonth + fromDay + fromHour + fromMin;

            String toTime = "1" + toHour + toMin;
            String toDate = "1" + toMonth + toDay;
            String toDateTime = "1" + toMonth + toDay + toHour + toMin;

            String currTime = "1" + currHour + currMin;
            String currDate = "1" + currMonth + currDay;
            String currDateTime = "1" + currMonth + currDay + currHour + currMin;

            //System.out.println("FT  " + fromTime);
            //System.out.println("TT  " + toTime);
            //System.out.println("CT  " + currTime);
            //System.out.println("FD  " + fromDate);
            //System.out.println("TD  " + toDate);
            //System.out.println("CD  " + currDate);
            //System.out.println("FDT " + fromDateTime);
            //System.out.println("TDT " + toDateTime);
            //System.out.println("CDT " + currDateTime);
            //writeLog("FinWebFilter.isBlockPeriod fromDateTime=" + fromDateTime + " toDateTime=" + toDateTime + " currDateTime=" + currDateTime);
            //writeLog("FinWebFilter.isBlockPeriod fromWeek=" + fromWeek + " toWeek=" + toWeek + " currWeek=" + currWeek);
            int iFromWeek = Integer.parseInt(fromWeek);
            int iToWeek = Integer.parseInt(toWeek);
            int iCurrWeek = Integer.parseInt(currWeek);

            if (iCurrWeek >= iFromWeek && iCurrWeek <= iToWeek) {
                if (periodType.equals("I")) {

                    int iFromTime = Integer.parseInt(fromTime);
                    int iToTime = Integer.parseInt(toTime);
                    int iCurrTime = Integer.parseInt(currTime);
                    if (iCurrTime >= iFromTime && iCurrTime <= iToTime) {
                        int iFromDate = Integer.parseInt(fromDate);
                        int iToDate = Integer.parseInt(toDate);
                        int iCurrDate = Integer.parseInt(currDate);
                        if (iCurrDate >= iFromDate && iCurrDate <= iToDate) {
                            //System.out.println("Matched 1");
                            flag = true;
                        }
                    }
                }
                if (periodType.equals("C")) {
                    long iFromDateTime = Long.parseLong(fromDateTime);
                    long iToDateTime = Long.parseLong(toDateTime);
                    long iCurrDateTime = Long.parseLong(currDateTime);
                    if (iCurrDateTime >= iFromDateTime && iCurrDateTime <= iToDateTime) {
                        //System.out.println("Matched 2");
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    private void blockSite(HttpServletResponse httpResponse, String vTitle, String vMessage,
            String vRedirectURI) throws IOException, ServletException {
        if (vRedirectURI.toUpperCase().equals("NA")) {
            httpResponse.setContentType("text/html;charset=UTF-8");

            String content1 = FinPack.getBlockContent("content1");
            String content2 = FinPack.getBlockContent("content2");
            String content3 = FinPack.getBlockContent("content3");
            StringBuilder pageContent = new StringBuilder();
            pageContent.append(content1).append(vTitle).append(content2).append(vMessage).append(content3);
            httpResponse.getWriter().write(pageContent.toString());
        } else {
            httpResponse.sendRedirect(vRedirectURI);
        }
    }

    public boolean checkForSqlInj(String paramValue) {
        if (paramValue.length() > 10 && paramValue.contains("'")) {
//            if (!(serverType.equals("dev") || serverType.equals("test")))
//            {
//                return false;
//            }
//            else if (!(serverName.startsWith("devweb1") || serverName.startsWith("devweb2")
//                    || serverName.startsWith("testweb1") || serverName.startsWith("testweb2")))
//            {
//                return false;
//            }
            String sql_pairs_prop = finpack.FinPack.getProperty("sql_pair");

            int ind;
            String[] sqlPairs = sql_pairs_prop.split(",");
            int sLen = sqlPairs.length;
            String uParamValue = paramValue.toUpperCase();

            for (int i = 0; i < sLen; i++) {
                String[] pairTerms = sqlPairs[i].split(";");
                if ((ind = uParamValue.indexOf(pairTerms[0] + " ")) >= 0 && uParamValue.indexOf(pairTerms[1] + " ") > ind) {
                    return true;
                }
            }
        }
        return false;
    }

//    public boolean checkForHTMLInj(String paramValue)
//    {
//      String validString = StringEscapeUtils.escapeHtml(paramValue);
//      if (paramValue.contains("&"))
//      {
//        paramValue = paramValue.replaceAll("&", "&amp;");
//      }
//      return validString.equals(paramValue);
//    }
    private void writeLog(String line) throws IOException {
        FileWriter fw = new FileWriter("/opt/apache-tomcat1/webapps/log/log/finstudio-filter-" + getDate() + ".txt", true);
        PrintWriter pw = new PrintWriter(fw);
        //pw.println("---------- Date : " + Calendar.getInstance().getTime());
        pw.println(line);
        pw.close();
        fw.close();
    }

    private static String getDate() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        serverType = (FinPack.getProperty("server_type") == null) ? "" : FinPack.getProperty("server_type");
        serverName = (FinPack.getProperty("java_server_name") == null) ? "" : FinPack.getProperty("java_server_name");
    }

    @Override
    public void destroy() {
    }
}
