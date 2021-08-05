/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.scriptgen;

import com.finlogic.apps.wfms2.scriptgen.ScriptGenService;
import com.finlogic.business.wfms2.scriptgen.ScriptGenDataManager;
import com.finlogic.business.wfms2.scriptgen.ScriptGenEntityBean;
import com.finlogic.util.csvreader.CsvWriter;
import finpack.FinPack;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Bhumika Dodiya
 */
public class SeleniumJavaClassGenerator
{

    private static final String TOMCAT_PATH = FinPack.getProperty("tomcat1_path");

//    public String generateScript1(final int srno, final int[] eventArray, final ScriptGenEntityBean entityBean) throws SQLException, IOException, Exception
//    {
//        FileWriter writer;
//        PrintWriter pwriter = null;
//        final ScriptGenDataManager dataMan = new ScriptGenDataManager();
//        final ScriptGenService genService = new ScriptGenService();
//        try
//        {
//            int totalControls = eventArray.length;
//            String htmlControlName;
//            String controlName;
//            String controlEvent;
//
//            String testCaseNature;
//            String nameid;
//            String xpath;
//            int confirm_msg_count = 0;
//            int control_count = 0;
//
//            String projectName = encode(dataMan.getProjectName(entityBean.getPrjID()));
//            //loop for parent modules                         
//            SqlRowSet srs = genService.getParentModuleList(entityBean.getModuleID());
//            List parentModuleList = new ArrayList();
//            while (srs.next())
//            {
//                parentModuleList.add(srs.getString(1));
//            }
//
//            String fileName = projectName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + parentModuleList.get(0).toString().replaceAll("[^a-zA-Z0-9]", "_") + "_" + srno;
//            fileName = fileName.replaceAll("__", "_");
//            /*------- Edited by Divyang Kankotiya -------- */
//            File filepath = new File(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + "/" + fileName + ".java");
//            filepath.getParentFile().mkdirs();
//            writer = new FileWriter(filepath);
//            //  writer = new FileWriter(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + ".java");
//           /* ----- End Edited by Divyang Kankotiya --- */
//
//            //for generating csv file
//            /*------- Edited by Divyang Kankotiya -------- */
//            writeCSV(totalControls, entityBean, fileName);
//            /*------- End Edited by Divyang Kankotiya -------- */
//
//            pwriter = new PrintWriter(writer);
//
//            //pwriter.println("package " + projectName.replaceAll("[^a-zA-Z0-9]", "_") + "." + parentModuleList.get(0).toString().replaceAll("[^a-zA-Z0-9]", "_") + ";");
//
//            pwriter.println("import com.thoughtworks.selenium.*;");
//            pwriter.println("import org.testng.annotations.*;");
//            pwriter.println("import java.io.IOException;");
//            pwriter.println("import java.io.FileNotFoundException;");
//            pwriter.println("import com.finlogic.util.csvreader.CsvReader;");
//            pwriter.println("import java.util.HashMap;");
//            pwriter.println("import java.util.Map;");
//            pwriter.println("import java.io.File;");
//
//            boolean flag = false;
//            for (int counter = 0, eventCounter = 0; counter < totalControls; counter++)
//            {
//                int count = 0;
//                if (entityBean.getHtmlControlID()[counter] == 8)
//                {
//                    flag = true;
//                    break;
//                }
//                while (count < eventArray[counter])
//                {
//                    String message = entityBean.getMessage()[eventCounter];
//                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1))
//                    {
//                        flag = true;
//                        break;
//                    }
//                    count++;
//                    eventCounter++;
//                }
//            }
//
//            if (flag)
//            {
//                pwriter.println("import org.testng.Assert;");
//            }
//
//            pwriter.println("");
//            pwriter.println("public class " + fileName);
//            pwriter.println("{");
//            pwriter.println("    Selenium selenium;");
//            pwriter.println("    public " + fileName + "()");
//            pwriter.println("    {");
//            pwriter.println("    }");
//
//            //for reading csv file
//            pwriter.println("    @DataProvider(name = \"getData\")");
//            pwriter.println("    public Object[][] createData() throws IOException");
//            pwriter.println("    {");
//            pwriter.println("        CsvReader reader = new CsvReader(\".\" + File.separatorChar + \"src\" + File.separatorChar +\"" + fileName + ".csv\");");
//            pwriter.println("        reader.readHeaders();");
//            pwriter.println("        int counter = 0;");
//            pwriter.println("        while (reader.readRecord())");
//            pwriter.println("        {");
//            pwriter.println("            counter++;");
//            pwriter.println("        }");
//            pwriter.println("        reader = new CsvReader(\".\" + File.separatorChar + \"src\" + File.separatorChar+\"" + fileName + ".csv\");");
//            pwriter.println("        Object[][] objArr = new Object[counter][];");
//            pwriter.println("        HashMap data;");
//            pwriter.println("        int i = 0;");
//            pwriter.println("        reader.readHeaders();");
//            pwriter.println("        while (reader.readRecord())");
//            pwriter.println("        {");
//            pwriter.println("            data = new HashMap();");
//            pwriter.println("            data.put(\"username\", reader.get(\"username\"));");
//            pwriter.println("            data.put(\"password\", reader.get(\"password\"));");
//            for (int counter = 0; counter < totalControls; counter++)
//            {
//                if (entityBean.getCsvHeaderString()[counter] != null)
//                {
//                    pwriter.println("            data.put(\"" + entityBean.getCsvHeaderString()[counter] + "\", reader.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
//                }
//
//                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
//                controlName = entityBean.getControlNameID()[counter];
//                StringTokenizer multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
//                int tokens = 0;
//                if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
//                {
//                    while (tokens < multiValues.countTokens())
//                    {
//                        pwriter.println("            data.put(\"" + checkControlName(controlName + (tokens + 1), control_count) + "\", reader.get(\"" + checkControlName(controlName + (tokens + 1), control_count) + "\"));");
//                        tokens++;
//                    }
//                }
//                else
//                {
//                    pwriter.println("            data.put(\"" + controlName + "\", reader.get(\"" + controlName + "\"));");
//                }
//            }
//            pwriter.println("            objArr[i] = new Object[]");
//            pwriter.println("            {");
//            pwriter.println("                data");
//            pwriter.println("            };");
//            pwriter.println("            i++;");
//            pwriter.println("        }");
//            pwriter.println("        return objArr;");
//            pwriter.println("    }");
//            pwriter.println("    @BeforeTest (alwaysRun=true)");
//            pwriter.println("    @Parameters(");
//            pwriter.println("    {");
//            pwriter.println("       \"ip\",\"port\",\"browser\"");
//            pwriter.println("    })");
//            pwriter.println("    public void setUp(@Optional String ip,@Optional String port,@Optional String browser) throws Exception ");
//            pwriter.println("    {");
//            if (entityBean.getCmbServerName().equals("dev.njtechdesk.com") || entityBean.getCmbServerName().equals("test.njtechdesk.com"))
//            {
//                pwriter.println("        selenium = new DefaultSelenium(ip, Integer.parseInt(port), browser, \"http://" + entityBean.getCmbServerName() + "/ACL/index.fin\");");
//            }
//            else
//            {
//                pwriter.println("        selenium = new DefaultSelenium(ip, Integer.parseInt(port), browser, \"http://" + entityBean.getCmbServerName() + ":8081/ACL/index.fin\");");
//            }
//            pwriter.println("        selenium.start();");
//            pwriter.println("        selenium.windowMaximize();");
//            pwriter.println("        selenium.setSpeed(\"1000\");");
//            pwriter.println("    }");
//            pwriter.println("    @Test(dataProvider = \"getData\")");
//            pwriter.println("    public void runTest(Map controlValue) throws InterruptedException, FileNotFoundException, IOException");
//            pwriter.println("    {");
//            if (entityBean.getCmbServerName().equals("dev.njtechdesk.com") || entityBean.getCmbServerName().equals("test.njtechdesk.com"))
//            {
//                pwriter.println("        selenium.open(\"http://" + entityBean.getCmbServerName() + "/ACL/index.fin\");");
//            }
//            else
//            {
//                pwriter.println("        selenium.open(\"http://" + entityBean.getCmbServerName() + ":8081/ACL/index.fin\");");
//            }
//
//            pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
//            pwriter.println("        selenium.type(\"name=a_user\",controlValue.get(\"username\").toString());");
//            pwriter.println("        selenium.type(\"name=a_pwd\",controlValue.get(\"password\").toString());");
//            pwriter.println("        selenium.click(\"css=input[name=\\\"login\\\"]\");");
//            pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
//            pwriter.println("        selenium.click(\"link=" + projectName + "\");");
//            pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
//
//            for (int i = parentModuleList.size() - 2; i >= 0; i--)
//            {
//                pwriter.println("        selenium.click(\"link=" + encode(parentModuleList.get(i).toString()) + "\");");
//                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
//            }
//
//            String message;
//            for (int counter = 0, eventCounter = 0; counter < totalControls; counter++)
//            {
//                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
//
//                controlName = encode(entityBean.getControlNameID()[counter]);
//
//                nameid = entityBean.getAccess()[counter];
//                xpath = entityBean.getXpath()[counter];
//
//                if (htmlControlName.equalsIgnoreCase("text box") || htmlControlName.equalsIgnoreCase("text area")
//                        || htmlControlName.equalsIgnoreCase("date & time picker") || htmlControlName.equalsIgnoreCase("file"))
//                {
//                    if ("xpath".equals(nameid))
//                    {
//                        pwriter.println("        selenium.type(\"xpath=" + xpath + "\",controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
//                    }
//                    else
//                    {
//                        pwriter.println("        selenium.type(\"name=" + controlName + "\",controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
//                    }
//                }
//                else if (htmlControlName.equalsIgnoreCase("link"))
//                {
//                    pwriter.println("        selenium.click(\"link=" + controlName.trim() + "\");");
//                }
//                else if (htmlControlName.equalsIgnoreCase("image"))
//                {
//                    pwriter.println("        Assert.assertTrue(selenium.isElementPresent(\"xpath=//img[@src='\"+controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString() + \"']\"));");
//                }
//                else if (htmlControlName.equalsIgnoreCase("check box"))
//                {
//                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
//
//                    if ("xpath".equals(nameid) && accessMethod.equalsIgnoreCase("check"))
//                    {
//                        pwriter.println("        selenium.check(\"" + xpath + "\");");
//                    }
//                    else if ("xpath".equals(nameid) && !accessMethod.equalsIgnoreCase("check"))
//                    {
//                        pwriter.println("        selenium.uncheck(\"" + xpath + "\");");
//                    }
//                    else if (!"xpath".equals(nameid) && accessMethod.equalsIgnoreCase("check"))
//                    {
//                        pwriter.println("        selenium.check(\"" + nameid + "=\" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
//                    }
//                    else
//                    {
//                        pwriter.println("        selenium.uncheck(\"" + nameid + "= \" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
//                    }
//                }
//                else if (htmlControlName.equalsIgnoreCase("radio button"))
//                {
//                    String accessMethod;
//                    accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
//                    if (accessMethod.equalsIgnoreCase("check"))
//                    {
//                        pwriter.println("        selenium.check(\"xpath=//input[@" + nameid + "='" + controlName + "' and @value='\" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString() + \"']\");");
//                    }
//                    else
//                    {
//                        pwriter.println("        selenium.uncheck(\"xpath=//input[@" + nameid + "='" + controlName + "' and @value='\" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString() + \"']\");");
//                    }
//                }
//                else if (htmlControlName.equalsIgnoreCase("button"))
//                {
//                    message = entityBean.getMessage()[eventCounter];
//                    testCaseNature = dataMan.getTestCaseNature(entityBean.getTestCaseNatureID()[eventCounter]);
//
//                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1) && testCaseNature.equalsIgnoreCase("verify confirmation"))
//                    {
//                        /*------- Edited by Divyang Kankotiya -------- */
//                        if ("ok".equals(message))
//                        {
//                            pwriter.println("        selenium.chooseOkOnNextConfirmation();");
//                        }
//                        /*-------End Edited by Divyang Kankotiya -------- */
//                        if ("cancel".equals(message))
//                        {
//                            pwriter.println("        selenium.chooseCancelOnNextConfirmation();");
//                        }
//                    }
//                    if ("xpath".equals(nameid))
//                    {
//                        pwriter.println("        selenium.click(\"xpath=" + xpath + "\");");
//                    }
//                    else
//                    {
//                        pwriter.println("        selenium.click(\"" + nameid + "=" + controlName + "\");");
//                    }
//                }
//                else if (htmlControlName.equalsIgnoreCase("combo box (single select)"))
//                {
//                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
//
//                    if ("xpath".equals(nameid))
//                    {
//                        pwriter.println("        selenium.select(\"" + xpath + "\", ");
//                    }
//                    else
//                    {
//                        pwriter.print("        selenium.select(\"" + nameid + "=" + controlName + "\", ");
//                    }
//                    pwriter.print("\"" + accessMethod.toLowerCase() + "=\"+ controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
//                    pwriter.println("");
//                }
//                else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
//                {
//                    pwriter.println("        selenium.removeAllSelections(\"" + controlName + "\");");
//                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
//                    StringTokenizer multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
//                    int tokens = 0;
//                    while (tokens < multiValues.countTokens())
//                    {
//                        if ("xpath".equals(nameid))
//                        {
//                            pwriter.println("        selenium.addSelection(\"" + xpath + "\",");
//                        }
//                        else
//                        {
//                            pwriter.print("        selenium.addSelection(\"" + nameid + "=" + controlName + "\",");
//                        }
//                        pwriter.print("\"" + accessMethod.toLowerCase() + "=\"+ controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
//                        //multiValues.nextToken();
//                        tokens++;
//                        pwriter.println("");
//                    }
//                }
//                /**
//                 * Selenium tag for the events and test case nature.
//                 */
//                int count = 0;
//                while (count < eventArray[counter])
//                {
//                    message = entityBean.getMessage()[eventCounter];
//                    testCaseNature = dataMan.getTestCaseNature(entityBean.getTestCaseNatureID()[eventCounter]);
//
//                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1) && (!htmlControlName.equalsIgnoreCase("button")) && testCaseNature.equalsIgnoreCase("verify confirmation"))
//                    {
//                        /*------- Edited by Divyang Kankotiya -------- */
//                        if ("ok".equals(message) && (!htmlControlName.equalsIgnoreCase("button")))
//                        {
//                            pwriter.println("        selenium.chooseOkOnNextConfirmation();");
//                        }
//                        /*-------End Edited by Divyang Kankotiya -------- */
//                        if ("cancel".equals(message) && (!htmlControlName.equalsIgnoreCase("button")))
//                        {
//                            pwriter.println("        selenium.chooseCancelOnNextConfirmation();");
//                        }
//                    }
//
//                    if ((entityBean.getEventID()[eventCounter] != -1) && (!htmlControlName.equalsIgnoreCase("button")))
//                    {
//                        controlEvent = dataMan.getControlEventName(entityBean.getEventID()[eventCounter]).toLowerCase();
//                        if ("".equals(nameid))
//                        {
//                            pwriter.println("        selenium.fireEvent(\"" + htmlControlName.toLowerCase() + "=" + controlName + "\", \"" + controlEvent.substring(2) + "\");");
//                            pwriter.println("        //selenium.waitForPageToLoad(\"3000\");");
//                        }
//                        else
//                        {
//                            pwriter.println("        selenium.fireEvent(\"" + nameid + "=" + controlName + "\", \"" + controlEvent.substring(2) + "\");");
//                            pwriter.println("        //selenium.waitForPageToLoad(\"3000\");");
//                        }
//                    }
//                    /**
//                     * Switch to newly open window 8 == Switch to New Window
//                     */
//                    if (entityBean.getTestCaseNatureID()[eventCounter] == 8)
//                    {
//                        pwriter.println("        selenium.selectWindow(\"" + message + "\");");
//                        pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
//                    }
//                    else if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1))
//                    {
//                        if (testCaseNature.equalsIgnoreCase("verify element present"))
//                        {
//                            pwriter.println("        Assert.assertTrue(selenium.isElementPresent(\"" + nameid + "=" + message + "\"),\"Expected element '" + message + "' not present on page\");");
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("verify text present"))
//                        {
//                            pwriter.println("        Assert.assertTrue(selenium.isTextPresent(\"" + message + "\"),\"Expected text '" + message + "' not present on page\");");
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("verify text"))
//                        {
//                            if (entityBean.getConfirmMsg() != null && confirm_msg_count < entityBean.getConfirmMsg().length)
//                            {
//                                if (!"".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
//                                {
//                                    pwriter.println("        Assert.assertEquals(\"" + entityBean.getConfirmMsg()[confirm_msg_count] + "\",selenium.getValue(\"" + message + "\"));");
//                                }
//                                confirm_msg_count++;
//                            }
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("verify title"))
//                        {
//                            pwriter.println("        Assert.assertTrue(selenium.getTitle().equals(\"" + message + "\"),\"Title '" + message + "' not macthed\");");
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("verify link"))
//                        {
//                            pwriter.println("        Assert.assertTrue(selenium.isElementPresent(\"link=" + message + "\"),\"Expected link '" + message + "' not present on page\");");
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("verify alert"))
//                        {
//                            pwriter.println("        Assert.assertTrue(selenium.getAlert().contains(\"" + message + "\"),\"Alert message '" + message + "' not matched\");");
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("verify confirmation"))
//                        {
//                            if (entityBean.getConfirmMsg() != null && confirm_msg_count < entityBean.getConfirmMsg().length)
//                            {
//                                if (!"".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
//                                {
//                                    pwriter.println("        Assert.assertTrue(selenium.getConfirmation().contains(\"" + encode(entityBean.getConfirmMsg()[confirm_msg_count]) + "\"),\"Confirmation meassage not matched\");");
//                                }
//                                /*------- Edited by Divyang Kankotiya -------- */
//                                else
//                                {
//                                    pwriter.println("        selenium.getConfirmation();");
//                                }
//                                /*------- Edited by Divyang Kankotiya -------- */
//                            }
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("wait for page to load"))
//                        {
//                            pwriter.println("        selenium.waitForPageToLoad(\"" + Integer.parseInt(message) + "\" );");
//                        }
//                        else if (testCaseNature.equalsIgnoreCase("Select Frame"))
//                        {
//                            pwriter.println("        selenium.selectFrame(\"relative=up\");");
//                            pwriter.println("        selenium.selectFrame(\"" + message + "\");");
//                        }
//                    }
//                    eventCounter++;
//                    count++;
//                }
//                control_count++;
//                confirm_msg_count++;
//            }
//            pwriter.println("    }");
//
//            pwriter.println("    @AfterTest");
//            pwriter.println("    public  void tearDownClass() throws Exception");
//            pwriter.println("    {");
//            pwriter.println("        selenium.close();");
//            pwriter.println("        selenium.stop();");
//            pwriter.println("    }");
//            pwriter.println("}");
//
//            return fileName;
//        }
//        finally
//        {
//            //pwriter.close();
//        }
//    }
    public String generateScript(final int srno, final int[] eventArray, final ScriptGenEntityBean entityBean) throws SQLException, IOException
    {
        FileWriter writer;
        PrintWriter pwriter = null;
        final ScriptGenDataManager dataMan = new ScriptGenDataManager();
        final ScriptGenService genService = new ScriptGenService();
        try
        {
            int totalControls = eventArray.length;
            String htmlControlName;
            String controlName;
            String controlEvent;

            String testCaseNature;
            String nameid;
            String xpath;
            int confirm_msg_count = 0;
            int control_count = 0;

            String projectName = encode(dataMan.getProjectName(entityBean.getPrjID()));
            //loop for parent modules                         
            SqlRowSet srs = genService.getParentModuleList(entityBean.getModuleID());
            List parentModuleList = new ArrayList();
            while (srs.next())
            {
                parentModuleList.add(srs.getString(1));
            }

            String fileName = projectName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + parentModuleList.get(0).toString().replaceAll("[^a-zA-Z0-9]", "_") + "_" + srno;
            fileName = fileName.replaceAll("__", "_");
            /*------- Edited by Divyang Kankotiya -------- */
            File filepath = new File(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + "/" + fileName + ".java");
            filepath.getParentFile().mkdirs();
            writer = new FileWriter(filepath);
            //  writer = new FileWriter(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + ".java");
           /* ----- End Edited by Divyang Kankotiya --- */

            //for generating csv file
            /*------- Edited by Divyang Kankotiya -------- */
            writeCSV(totalControls, entityBean, fileName);
            /*------- End Edited by Divyang Kankotiya -------- */

            pwriter = new PrintWriter(writer);

            //pwriter.println("package " + projectName.replaceAll("[^a-zA-Z0-9]", "_") + "." + parentModuleList.get(0).toString().replaceAll("[^a-zA-Z0-9]", "_") + ";");

            pwriter.println("import com.finlogic.util.csvreader.CsvReader;");
            pwriter.println("import com.thoughtworks.selenium.*;");
            pwriter.println("import com.unitedinternet.portal.selenium.utils.logging.HtmlResultFormatter;");
            pwriter.println("import com.unitedinternet.portal.selenium.utils.logging.LoggingCommandProcessor;");
            pwriter.println("import com.unitedinternet.portal.selenium.utils.logging.LoggingDefaultSelenium;");
            pwriter.println("import com.unitedinternet.portal.selenium.utils.logging.LoggingResultsFormatter;");
            pwriter.println("import com.unitedinternet.portal.selenium.utils.logging.LoggingUtils;");
            pwriter.println("import java.io.BufferedWriter;");
            pwriter.println("import java.io.File;");
            pwriter.println("import java.io.FileNotFoundException;");
            pwriter.println("import java.io.IOException;");
            pwriter.println("import java.util.HashMap;");
            pwriter.println("import java.util.Map;");
            pwriter.println("import org.testng.annotations.*;");

            boolean flag = false;
            for (int counter = 0, eventCounter = 0; counter < totalControls; counter++)
            {
                int count = 0;
                if (entityBean.getHtmlControlID()[counter] == 8)
                {
                    flag = true;
                    break;
                }
                while (count < eventArray[counter])
                {
                    String message = entityBean.getMessage()[eventCounter];
                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1))
                    {
                        flag = true;
                        break;
                    }
                    count++;
                    eventCounter++;
                }
            }

            if (flag)
            {
                pwriter.println("import org.testng.Assert;");
            }

            pwriter.println();
            pwriter.println("public class " + fileName);
            pwriter.println("{");
            pwriter.println();
            pwriter.println("    private BufferedWriter loggingWriter;");
            pwriter.println("    private LoggingDefaultSelenium selenium;");
            pwriter.println("    private LoggingCommandProcessor myProcessor;");
            pwriter.println();
            pwriter.println("    public " + fileName + "()");
            pwriter.println("    {");
            pwriter.println("    }");
            pwriter.println();

            //for reading csv file
            pwriter.println("    @DataProvider(name = \"getData\")");
            pwriter.println("    public Object[][] createData() throws IOException");
            pwriter.println("    {");
            pwriter.println("        CsvReader reader = new CsvReader(\".\" + File.separatorChar + \"src\" + File.separatorChar + \"" + fileName + ".csv\");");
            pwriter.println("        reader.readHeaders();");
            pwriter.println("        int counter = 0;");
            pwriter.println("        while (reader.readRecord())");
            pwriter.println("        {");
            pwriter.println("            counter++;");
            pwriter.println("        }");
            pwriter.println("        reader = new CsvReader(\".\" + File.separatorChar + \"src\" + File.separatorChar + \"" + fileName + ".csv\");");
            pwriter.println("        Object[][] objArr = new Object[counter][];");
            pwriter.println("        HashMap data;");
            pwriter.println("        int i = 0;");
            pwriter.println("        reader.readHeaders();");
            /*------- Edited by Divyang Kankotiya -------- */

            pwriter.println("        while (reader.readRecord())");
            pwriter.println("        {");

            pwriter.println("            data = new HashMap();");

            if (entityBean.getChkloginOnce().equals("0"))
            {
                pwriter.println("            data.put(\"username\", reader.get(\"username\"));");
                pwriter.println("            data.put(\"password\", reader.get(\"password\"));");
            }
            for (int counter = 0; counter < totalControls; counter++)
            {
//                if (entityBean.getCsvHeaderString()[counter] != null)
//                {
//                    finutils.errorhandler.ErrorHandler.PrintInLog("/home/njuser/Desktop/multicombo.txt", "data.put = " + entityBean.getCsvHeaderString()[counter].toString());
//                    pwriter.println("            data.put(\"" + entityBean.getCsvHeaderString()[counter] + "\", reader.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
//                }
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = entityBean.getControlNameID()[counter];
                StringTokenizer multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
                int tokens = 0;
                if (entityBean.getCsvHeaderString()[counter] != null)
                {
                    if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                    {
                        while (tokens < multiValues.countTokens())
                        {
                            pwriter.println("            data.put(\"" + controlName + (tokens + 1) + "\", reader.get(\"" + controlName + (tokens + 1) + "\"));");
                            tokens++;
                        }
                    }
                    else
                    {
                        pwriter.println("            data.put(\"" + entityBean.getCsvHeaderString()[counter] + "\", reader.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
                    }
                }
//                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
//                controlName = entityBean.getControlNameID()[counter];
//                StringTokenizer multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
//                int tokens = 0;
//                if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
//                {
//                    while (tokens < multiValues.countTokens())
//                    {
//                        pwriter.println("            data.put(\"" + checkControlName(controlName + (tokens + 1), control_count) + "\", reader.get(\"" + checkControlName(controlName + (tokens + 1), control_count) + "\"));");
//                        tokens++;
//                    }
//                }
//                else
//                {
//                    pwriter.println("            data.put(\"" + controlName + "\", reader.get(\"" + controlName + "\"));");
//                }
            }
            pwriter.println("            objArr[i] = new Object[]");
            pwriter.println("            {");
            pwriter.println("                data");
            pwriter.println("            };");
            pwriter.println("            i++;");
            pwriter.println("        }");
            pwriter.println("        return objArr;");
            pwriter.println("    }");
            pwriter.println();
            pwriter.println("    @BeforeTest(alwaysRun = true)");
            pwriter.println("    @Parameters(");
            pwriter.println("    {");
            pwriter.println("        \"ip\", \"port\", \"browser\", \"resultPath\", \"resultFile\"");
            pwriter.println("    })");
            pwriter.println("    public void setUp(@Optional String ip, @Optional String port, @Optional String browser, @Optional String resultPath, @Optional String resultFile) throws Exception ");
            pwriter.println("    {");
            pwriter.println("        final String resultHtmlFileName = resultPath + File.separatorChar + resultFile + \".html\";");
            pwriter.println("        final String resultEncoding = \"UTF-8\";");
            pwriter.println();
            pwriter.println("        File f = new File(resultHtmlFileName);");
            pwriter.println("        if (f.exists())");
            pwriter.println("        {");
            pwriter.println("            f.delete();");
            pwriter.println("        }");
            pwriter.println();
            pwriter.println("        loggingWriter = LoggingUtils.createWriter(resultHtmlFileName, resultEncoding);");
            pwriter.println("        LoggingResultsFormatter htmlFormatter = new HtmlResultFormatter(loggingWriter);");
            pwriter.println("        htmlFormatter.setScreenShotBaseUri(new File(resultPath).getAbsolutePath());");
            pwriter.println("        htmlFormatter.setAutomaticScreenshotPath(resultPath);");
            if (entityBean.getCmbServerName().equals("dev.njtechdesk.com") || entityBean.getCmbServerName().equals("test.njtechdesk.com"))
            {
                pwriter.println("        myProcessor = new LoggingCommandProcessor(new HttpCommandProcessor(ip, Integer.parseInt(port), browser, \"http://" + entityBean.getCmbServerName() + "/ACL/index.fin\"), htmlFormatter);");
            }
            else if (entityBean.getCmbServerName().equals("njapps"))
            {
                pwriter.println("        myProcessor = new LoggingCommandProcessor(new HttpCommandProcessor(ip, Integer.parseInt(port), browser, \"http://" + entityBean.getCmbServerName() + ":8081/ACLNEW/index.fin\"), htmlFormatter);");
            }
            pwriter.println("        selenium = new LoggingDefaultSelenium(myProcessor);");
            pwriter.println("        selenium.start();");
            pwriter.println("        selenium.windowMaximize();");
            pwriter.println("        selenium.setSpeed(\"1000\");");
            pwriter.println("    }");
            pwriter.println();

            /*------- Edited by Divyang Kankotiya -------- */
            if (entityBean.getChkloginOnce().equals("1"))
            {
                pwriter.println("    @Test");
                pwriter.println("    public void login() throws InterruptedException, FileNotFoundException, IOException");
                pwriter.println("    {");

                if (entityBean.getCmbServerName().equals("dev.njtechdesk.com") || entityBean.getCmbServerName().equals("test.njtechdesk.com"))
                {
                    pwriter.println("        selenium.open(\"http://" + entityBean.getCmbServerName() + "/ACL/index.fin\");");
                }
                else if (entityBean.getCmbServerName().equals("njapps"))
                {
                    pwriter.println("        selenium.open(\"http://" + entityBean.getCmbServerName() + ":8081/ACLNEW/index.fin\");");
                }
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
                pwriter.println("        selenium.type(\"name=a_user\", \"" + entityBean.getTxtUserName() + "\");");
                pwriter.println("        selenium.type(\"name=a_pwd\", \"" + entityBean.getTxtPassword() + "\");");
                pwriter.println("        selenium.click(\"css=input[name=\\\"login\\\"]\");");
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
                pwriter.println("        selenium.click(\"link=" + projectName + "\");");
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
                pwriter.println("    }");
                pwriter.println();
            }

            pwriter.println("    @Test(dataProvider = \"getData\",alwaysRun = true)");
            pwriter.println("    public void runTest(Map controlValue) throws InterruptedException, FileNotFoundException, IOException");
            pwriter.println("    {");
            if (entityBean.getChkloginOnce().equals("0"))
            {
                if (entityBean.getCmbServerName().equals("dev.njtechdesk.com") || entityBean.getCmbServerName().equals("test.njtechdesk.com"))
                {
                    pwriter.println("        selenium.open(\"http://" + entityBean.getCmbServerName() + "/ACL/index.fin\");");
                }
                else if (entityBean.getCmbServerName().equals("njapps"))
                {
                    pwriter.println("        selenium.open(\"http://" + entityBean.getCmbServerName() + ":8081/ACLNEW/index.fin\");");
                }
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
                pwriter.println("        selenium.type(\"name=a_user\", controlValue.get(\"username\").toString());");
                pwriter.println("        selenium.type(\"name=a_pwd\", controlValue.get(\"password\").toString());");
                pwriter.println("        selenium.click(\"css=input[name=\\\"login\\\"]\");");
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
                pwriter.println("        selenium.click(\"link=" + projectName + "\");");
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
            }

            pwriter.println("        selenium.selectFrame(\"relative=up\");");

            for (int i = parentModuleList.size() - 2; i >= 0; i--)
            {
                pwriter.println("        selenium.click(\"link=" + encode(parentModuleList.get(i).toString()) + "\");");
                pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
            }

            String message;
            for (int counter = 0, eventCounter = 0; counter < totalControls; counter++)
            {
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = encode(entityBean.getControlNameID()[counter]);
                nameid = entityBean.getAccess()[counter];
                xpath = entityBean.getXpath()[counter];

                if (htmlControlName.equalsIgnoreCase("text box") || htmlControlName.equalsIgnoreCase("text area")
                        || htmlControlName.equalsIgnoreCase("date & time picker") || htmlControlName.equalsIgnoreCase("file"))
                {
                    if ("xpath".equals(nameid))
                    {
                        pwriter.println("        selenium.type(\"xpath=" + xpath + "\", controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
                    }
                    else
                    {
                        pwriter.println("        selenium.type(\"" + nameid + "=" + controlName + "\", controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
                    }
                }
                else if (htmlControlName.equalsIgnoreCase("link"))
                {
                    pwriter.println("        selenium.click(\"link=" + controlName.trim() + "\");");
                }
                else if (htmlControlName.equalsIgnoreCase("image"))
                {
                    pwriter.println("        Assert.assertTrue(selenium.isElementPresent(\"xpath=//img[@src='\"+controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString() + \"']\"));");
                }
                else if (htmlControlName.equalsIgnoreCase("check box"))
                {
                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    if ("xpath".equals(nameid) && accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println("        selenium.check(\"" + xpath + "\");");
                    }
                    else if ("xpath".equals(nameid) && !accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println("        selenium.uncheck(\"" + xpath + "\");");
                    }
                    else if (!"xpath".equals(nameid) && accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println("        selenium.check(\"" + nameid + "=\" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
                    }
                    else
                    {
                        pwriter.println("        selenium.uncheck(\"" + nameid + "= \" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString());");
                    }
                }
                else if (htmlControlName.equalsIgnoreCase("radio button"))
                {
                    String accessMethod;
                    accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
                    if (accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println("        selenium.check(\"xpath=//input[@" + nameid + "='" + controlName + "' and @value='\" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString() + \"']\");");
                    }
                    else
                    {
                        pwriter.println("        selenium.uncheck(\"xpath=//input[@" + nameid + "='" + controlName + "' and @value='\" + controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\").toString() + \"']\");");
                    }
                }
                else if (htmlControlName.equalsIgnoreCase("button"))
                {
                    message = entityBean.getMessage()[eventCounter];
                    testCaseNature = dataMan.getTestCaseNature(entityBean.getTestCaseNatureID()[eventCounter]);

                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1) && testCaseNature.equalsIgnoreCase("verify confirmation"))
                    {
                        /*------- Edited by Divyang Kankotiya -------- */
                        if ("ok".equals(message))
                        {
                            pwriter.println("        selenium.chooseOkOnNextConfirmation();");
                        }
                        /*-------End Edited by Divyang Kankotiya -------- */
                        if ("cancel".equals(message))
                        {
                            pwriter.println("        selenium.chooseCancelOnNextConfirmation();");
                        }
                    }
                    if ("xpath".equals(nameid))
                    {
                        pwriter.println("        selenium.click(\"xpath=" + xpath + "\");");
                    }
                    else
                    {
                        pwriter.println("        selenium.click(\"" + nameid + "=" + controlName + "\");");
                    }
                }
                else if (htmlControlName.equalsIgnoreCase("combo box (single select)"))
                {
                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    if ("xpath".equals(nameid))
                    {
                        pwriter.println("        selenium.select(\"" + xpath + "\", ");
                    }
                    else
                    {
                        pwriter.print("        selenium.select(\"" + nameid + "=" + controlName + "\", ");
                    }
                    pwriter.print("\"" + accessMethod.toLowerCase() + "=\"+ controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
                    pwriter.println("");
                }
                else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    pwriter.println("        selenium.removeAllSelections(\"" + controlName + "\");");
                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
                    StringTokenizer multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
                    int tokens = 0;
                    while (tokens < multiValues.countTokens())
                    {
                        if ("xpath".equals(nameid))
                        {
                            pwriter.println("        selenium.addSelection(\"" + xpath + "\",");
                        }
                        else
                        {
                            pwriter.print("        selenium.addSelection(\"" + nameid + "=" + controlName + "\",");
                        }
                        //pwriter.print("\"" + accessMethod.toLowerCase() + "=\"+ controlValue.get(\"" + entityBean.getCsvHeaderString()[counter] + "\"));");
                        pwriter.print("\"" + accessMethod.toLowerCase() + "=\"+ controlValue.get(\"" + controlName + (tokens + 1) + "\"));");
                        //multiValues.nextToken();
                        tokens++;
                        pwriter.println("");
                    }
                }
                /**
                 * Selenium tag for the events and test case nature.
                 */
                int count = 0;
                while (count < eventArray[counter])
                {
                    message = entityBean.getMessage()[eventCounter];
                    testCaseNature = dataMan.getTestCaseNature(entityBean.getTestCaseNatureID()[eventCounter]);

                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1) && (!htmlControlName.equalsIgnoreCase("button")) && testCaseNature.equalsIgnoreCase("verify confirmation"))
                    {
                        /*------- Edited by Divyang Kankotiya -------- */
                        if ("ok".equals(message) && (!htmlControlName.equalsIgnoreCase("button")))
                        {
                            pwriter.println("        selenium.chooseOkOnNextConfirmation();");
                        }
                        /*-------End Edited by Divyang Kankotiya -------- */
                        if ("cancel".equals(message) && (!htmlControlName.equalsIgnoreCase("button")))
                        {
                            pwriter.println("        selenium.chooseCancelOnNextConfirmation();");
                        }
                    }

                    if ((entityBean.getEventID()[eventCounter] != -1) && (!htmlControlName.equalsIgnoreCase("button")))
                    {
                        controlEvent = dataMan.getControlEventName(entityBean.getEventID()[eventCounter]).toLowerCase();
                        if ("".equals(nameid))
                        {
                            pwriter.println("        selenium.fireEvent(\"" + htmlControlName.toLowerCase() + "=" + controlName + "\", \"" + controlEvent.substring(2) + "\");");
                            pwriter.println("        //selenium.waitForPageToLoad(\"3000\");");
                        }
                        else
                        {
                            pwriter.println("        selenium.fireEvent(\"" + nameid + "=" + controlName + "\", \"" + controlEvent.substring(2) + "\");");
                            pwriter.println("        //selenium.waitForPageToLoad(\"3000\");");
                        }
                    }
                    /**
                     * Switch to newly open window 8 == Switch to New Window
                     */
                    if (entityBean.getTestCaseNatureID()[eventCounter] == 8)
                    {
                        pwriter.println("        selenium.selectWindow(\"" + message + "\");");
                        pwriter.println("        selenium.waitForPageToLoad(\"10000\");");
                    }
                    else if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1))
                    {
                        if (testCaseNature.equalsIgnoreCase("verify element present"))
                        {
                            pwriter.println("        Assert.assertTrue(selenium.isElementPresent(\"" + nameid + "=" + message + "\"),\"Expected element '" + message + "' not present on page\");");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify text present"))
                        {
                            pwriter.println("        Assert.assertTrue(selenium.isTextPresent(\"" + message + "\"),\"Expected text '" + message + "' not present on page\");");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify text"))
                        {
                            if (entityBean.getConfirmMsg() != null && confirm_msg_count < entityBean.getConfirmMsg().length)
                            {
                                if (!"".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
                                {
                                    pwriter.println("        Assert.assertEquals(\"" + entityBean.getConfirmMsg()[confirm_msg_count] + "\",selenium.getValue(\"" + message + "\"));");
                                }
                                confirm_msg_count++;
                            }
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify title"))
                        {
                            pwriter.println("        Assert.assertTrue(selenium.getTitle().equals(\"" + message + "\"),\"Title '" + message + "' not macthed\");");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify link"))
                        {
                            pwriter.println("        Assert.assertTrue(selenium.isElementPresent(\"link=" + message + "\"),\"Expected link '" + message + "' not present on page\");");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify alert"))
                        {
                            //pwriter.println("        Assert.assertTrue(selenium.getAlert().contains(\"" + message + "\"),\"Alert message '" + message + "' not matched\");");
                            pwriter.println("        try ");
                            pwriter.println("        {");
                            pwriter.println("                myProcessor.doCommand(\"assertValue\", new String[]");
                            pwriter.println("                {");
                            pwriter.println("                        selenium.getAlert(), \"" + message + "\"");
                            pwriter.println("                });");
                            pwriter.println("        }");
                            pwriter.println("        catch (Exception e)");
                            pwriter.println("        {");
                            pwriter.println("        }");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify confirmation"))
                        {
                            if (entityBean.getConfirmMsg() != null && confirm_msg_count < entityBean.getConfirmMsg().length)
                            {
                                if (!"".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
                                {
                                    //pwriter.println("        Assert.assertTrue(selenium.getConfirmation().contains(\"" + encode(entityBean.getConfirmMsg()[confirm_msg_count]) + "\"),\"Confirmation meassage not matched\");");
                                    pwriter.println("        try");
                                    pwriter.println("        {");
                                    pwriter.println("                myProcessor.doCommand(\"assertValue\", new String[]");
                                    pwriter.println("                {");
                                    pwriter.println("                        selenium.getConfirmation(), \"" + encode(entityBean.getConfirmMsg()[confirm_msg_count]) + "\"");
                                    pwriter.println("                });");
                                    pwriter.println("        }");
                                    pwriter.println("        catch (Exception e)");
                                    pwriter.println("        {");
                                    pwriter.println("        }");
                                }
                                /*------- Edited by Divyang Kankotiya -------- */
                                else
                                {
                                    pwriter.println("        selenium.getConfirmation();");
                                }
                                confirm_msg_count++;
                                /*------- Edited by Divyang Kankotiya -------- */
                            }
                        }
                        else if (testCaseNature.equalsIgnoreCase("wait for page to load"))
                        {
                            pwriter.println("        selenium.waitForPageToLoad(\"" + Integer.parseInt(message) + "\" );");
                        }
                        else if (testCaseNature.equalsIgnoreCase("Select Frame"))
                        {
                            pwriter.println("        selenium.selectFrame(\"relative=up\");");
                            pwriter.println("        selenium.selectFrame(\"" + message + "\");");
                        }
                    }
                    eventCounter++;
                    count++;
                }
                control_count++;
//                confirm_msg_count++;
            }
            pwriter.println("    }");
            pwriter.println();

            pwriter.println("    @AfterTest");
            pwriter.println("    public void tearDownClass() throws Exception");
            pwriter.println("    {");
            pwriter.println("        selenium.stop();");
            pwriter.println("        try");
            pwriter.println("        {");
            pwriter.println("            if (null != loggingWriter)");
            pwriter.println("            {");
            pwriter.println("                loggingWriter.close();");
            pwriter.println("            }");
            pwriter.println("        }");
            pwriter.println("        catch (IOException e)");
            pwriter.println("        {");
            pwriter.println("            // do nothing");
            pwriter.println("        }");
            pwriter.println("    }");
            pwriter.println("}");

            return fileName;
        }
        finally
        {
            pwriter.close();
        }
    }

    private String encode(final String value)
    {
        String temp = value;
//        if (value.indexOf('&') > 0)
//        {
//            /*------- Edited by Divyang Kankotiya -------- */
//            temp = temp.replaceAll("&", "&");
//            //temp = temp.replaceAll("&", "&amp;");
//              /*-------End Edited by Divyang Kankotiya -------- */
//        }
        if (value.indexOf('<') > 0)
        {
            temp = temp.replaceAll("<", "&lt;");
        }
        if (value.indexOf('>') > 0)
        {
            temp = temp.replaceAll(">", "&gt;");
        }
        if (value.indexOf('\"') > 0)
        {
            temp = temp.replaceAll("\"", "&quot;");
        }
        if (value.indexOf('\'') > 0)
        {
            temp = temp.replaceAll("\'", "&apos;");
        }
        return temp;
    }

    private String checkControlName(String controlName, int controlCount)
    {
        StringBuilder cntrlName = new StringBuilder();
        if (!controlName.matches("[a-zA-Z0-9_]+"))
        {
            controlName = controlName.replaceAll("[^a-zA-Z0-9_]+", "_");
            cntrlName.append(controlName);
            cntrlName.append(String.valueOf(controlCount));
        }
        else
        {
            cntrlName.append(controlName);
        }
        return cntrlName.toString();
    }

    private boolean writeCSV(final int controlsCount, final ScriptGenEntityBean entityBean, String fileName) throws SQLException, IOException
    {
        boolean csvStatus = false;
        boolean isFirst = false;
        ScriptGenDataManager dataMan = new ScriptGenDataManager();
        int csv_control_count = 0;
        try
        {
            String htmlControlName;
            String controlName;
            String controlValue;
            int length = controlsCount;
            List name = new ArrayList();
            List value = new ArrayList();
            /*------- Edited by Divyang Kankotiya -------- */
            CsvWriter csv_writer = new CsvWriter(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + "/" + fileName + ".csv");
            /*------- End Edited by Divyang Kankotiya -------- */
            //     CsvWriter csv_writer = new CsvWriter(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + ".csv");

            Set unique_names = new HashSet();
            Set repeat_names = new HashSet();
            for (int counter = 0; counter < length; counter++)
            {
                if (!entityBean.getControlNameID()[counter].equals("-"))
                {
                    if (unique_names.contains(entityBean.getControlNameID()[counter]))
                    {
                        repeat_names.add(entityBean.getControlNameID()[counter]);
                    }
                    else
                    {
                        unique_names.add(entityBean.getControlNameID()[counter]);
                    }
                }
            }

            entityBean.setCsvHeaderString(new String[length]);
            for (int counter = 0; counter < length; counter++)
            {
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = entityBean.getControlNameID()[counter];
                controlValue = entityBean.getControlValue()[counter].trim();
                if (repeat_names.contains(controlName) && htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    StringTokenizer multiValues;
                    try
                    {
                        multiValues = new StringTokenizer(controlValue, ",");
                        int temp = 0;
                        {
                            String control_name = checkControlName(controlName + (temp + 1), csv_control_count);
                            if (isFirst)
                            {
                                control_name = control_name.concat(String.valueOf(csv_control_count));
                            }
                            name.add(control_name);
                            entityBean.getCsvHeaderString()[counter] = control_name;
                            value.add(multiValues.nextToken());
                            temp++;
                        }
                    }
                    catch (NumberFormatException exp)
                    {
                        csvStatus = false;
                        throw exp;
                    }
                    isFirst = true;
                }
                else if (repeat_names.contains(controlName) && !htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    String control_name = checkControlName(controlName, csv_control_count);
                    if (isFirst)
                    {
                        control_name = control_name.concat(String.valueOf(csv_control_count));
                    }
                    name.add(control_name);
                    if (!controlName.equals("-"))
                    {
                        entityBean.getCsvHeaderString()[counter] = control_name;
                    }
                    value.add(controlValue);
                    isFirst = true;
                }
                else
                {
                    if (htmlControlName.equalsIgnoreCase("text box")
                            || htmlControlName.equalsIgnoreCase("text area")
                            || htmlControlName.equalsIgnoreCase("date & time picker")
                            || htmlControlName.equalsIgnoreCase("image")
                            || htmlControlName.equalsIgnoreCase("combo box (single select)")
                            || htmlControlName.equalsIgnoreCase("file"))
                    {
                        name.add(checkControlName(controlName, csv_control_count));
                        entityBean.getCsvHeaderString()[counter] = checkControlName(controlName, csv_control_count);
                        value.add(controlValue);
                    }
                    else if (htmlControlName.equalsIgnoreCase("check box"))
                    {
                        name.add(checkControlName(controlName, csv_control_count));
                        entityBean.getCsvHeaderString()[counter] = checkControlName(controlName, csv_control_count);
                        value.add(controlName);
                    }
                    else if (htmlControlName.equalsIgnoreCase("radio button"))
                    {
                        name.add(checkControlName(controlName, csv_control_count));
                        entityBean.getCsvHeaderString()[counter] = checkControlName(controlName, csv_control_count);
                        if (controlValue.equals(""))
                        {
                            value.add(controlName);
                        }
                        else
                        {
                            value.add(controlValue);
                        }
                    }
                    else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                    {
                        StringTokenizer multiValues;
                        try
                        {
                            multiValues = new StringTokenizer(controlValue, ",");
                            int temp = 0;
                            while (multiValues.hasMoreTokens())
                            {
                                name.add(checkControlName(controlName + (temp + 1), csv_control_count));
                                entityBean.getCsvHeaderString()[counter] = checkControlName(controlName + (temp + 1), csv_control_count);
                                value.add(multiValues.nextToken());
                                temp++;
                            }
                        }
                        catch (NumberFormatException exp)
                        {
                            csvStatus = false;
                            throw exp;
                        }
                    }
                }
                csv_control_count++;
            }

            if (entityBean.getChkloginOnce().equals("0"))
            {
                csv_writer.write("username");
                csv_writer.write("password");
            }
            for (int i = 0; i < name.size(); i++)
            {
                String str = name.get(i).toString();
                csv_writer.write(str);
            }
            csv_writer.endRecord();

            if (entityBean.getChkloginOnce().equals("0"))
            {
                csv_writer.write(entityBean.getTxtUserName());
                csv_writer.write(entityBean.getTxtPassword());
            }
            for (int i = 0; i < value.size(); i++)
            {
                csv_writer.write(value.get(i).toString());
            }
            csv_writer.endRecord();

            csv_writer.close();
            csvStatus = true;
        }
        catch (ArrayIndexOutOfBoundsException exp)
        {
            csvStatus = false;
            throw exp;
        }
        catch (NumberFormatException exp)
        {
            csvStatus = false;
            throw exp;
        }
        return csvStatus;
    }
}