/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.business.wfms2.scriptgen.ScriptGenDataManager;
import com.finlogic.business.wfms2.scriptgen.ScriptGenEntityBean;
import finpack.FinPack;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 *
 * @author njuser
 */
public class SeleniumScriptGen
{
    private boolean scriptStatus = false;
    private boolean csvStatus = false;
    private String report = null;
    private final String tomcatPath = FinPack.getProperty("tomcat1_path");

    public String runTest(int scriptID) throws Exception
    {
        try
        {
            /*
            Process ps = Runtime.getRuntime().exec("/bin/sh "+tomcatPath+"/webapps/finstudio/generated/testscript.sh " + scriptID);
            ps.waitFor();   */
        }
        catch (Exception exp)
        {
            throw new Exception(exp);
        }
        finally
        {
            return report;
        }
    }

    public boolean generateScript(int transcationID, int pageID, int[] eventArray, ScriptGenEntityBean entityBean) throws Exception
    {
        FileWriter writer = null;
        PrintWriter pw = null;
        final String TAB = "\t";
        final String TAB2 = TAB + TAB;
        ScriptGenDataManager dataMan = new ScriptGenDataManager();

        try
        {
            int totalControls = eventArray.length;

            writer = new FileWriter(tomcatPath + "/webapps/finstudio/generated/" + pageID + ".xml");
            pw = new PrintWriter(writer);

            pw.println("<?xml version=\"1.0\"?>");
            pw.println(TAB + "<testcase xmlns=\"jelly:jameleon\" useCSV=\"true\" countRow=\"true\">");
            pw.println(TAB + "<test-case-summary>Auto Test Selenium Script</test-case-summary>");
            pw.println(TAB + "<test-case-author>Ankur Mistry</test-case-author>");
            pw.println(TAB + "<test-case-level>UNIT</test-case-level>");
            pw.println(TAB + "<functional-point-tested>Selenuim</functional-point-tested>");
            pw.println(TAB + "<selenium-session baseUrl=\"http://njapps5:8081/ACLNEW/\" browser=\"*" + getBrowserName(entityBean.getBrowserID()) + "\" beginSession=\"true\" startSeleniumProxy=\"true\">");
            pw.println(TAB2 + "<wait functionId=\"wait for 2000 miliseconds\" delayTime=\"2000\" />");
            pw.println(TAB2 + "<selenium-type functionId=\"Enter Values in username\" locator=\"a_user\" keys=\"sandip4\"/>");
            pw.println(TAB2 + "<selenium-type functionId=\"Enter Values in password\" locator=\"a_pwd\" keys=\"njindia\"/>");
            pw.println(TAB2 + "<wait functionId=\"wait for 1500 miliseconds\" delayTime=\"1500\" />");
            pw.println(TAB2 + "<selenium-submit functionId=\"submit the current form\" locator=\"xpath=//form[@name='login']\" />");
            pw.println(TAB2 + "<wait functionId=\"wait for 3500 miliseconds\" delayTime=\"3500\" />");
            pw.println(TAB2 + "<selenium-click functionId=\"Click on project link\" locator=\"link=" + dataMan.getProjectName(entityBean.getPrjID()) + "\"/>");
            pw.println(TAB2 + "<wait functionId=\"wait for 3500 miliseconds\" delayTime=\"3500\" />");
            pw.println(TAB2 + "<selenium-click functionId=\"Page name\" locator=\"link=" + dataMan.getModuleName(entityBean.getModuleID()) + "\"/>");
            pw.println(TAB2 + "<wait functionId=\"wait for 3500 miliseconds\" delayTime=\"3500\" />");
//          pw.println(TAB2 + "<InsertTag functionId=\"Insert test transacation details in daTABase\" transcationid=\"${transcationid}\" pageid=\"${pageid}\" />");
//          pw.println(TAB2 + "<wait functionId=\"wait for 2000 miliseconds\" delayTime=\"2000\" />");

            String htmlControlName = null;
            String controlName = null;
            String controlEvent = null;
            String message = null;
            String testCaseNature = null;

            for ( int counter = 0, eventCounter = 0 ; counter < totalControls ; counter++ )
            {
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = entityBean.getControlNameID()[counter];

                //Selenium tag for TextBox or TextArea to type values in it
                if (htmlControlName.equalsIgnoreCase("text box") || htmlControlName.equalsIgnoreCase("text area"))
                {
                    pw.println(TAB2 + "<selenium-type functionId=\"enter text into '"
                            + controlName + "' "+htmlControlName+"\" locator=\"name=" + controlName
                            + "\" keys=\"${" + controlName + "}\" />");                    
                }                

                //Selenium Tag to click on Link
                else if (htmlControlName.equalsIgnoreCase("link"))
                {
                    pw.println(TAB2 + "<selenium-click functionId=\"click on '"
                            + controlName + "' "+htmlControlName+"\"  locator=\"name=${" + controlName + "}\" />");
                }

                //Selenium Tag for checking image source path is present or not
                else if (htmlControlName.equalsIgnoreCase("image"))
                {
                    pw.println(TAB2 + "<selenium-assert-element-present functionId=\"check whether IMAGE with source '"
                            + entityBean.getControlValue()[counter] + "' present or not \" locator=\"xpath=//img[@src='${"
                            + controlName + "}']\" />");                    
                }

                //Selenium tag for check or uncheck the checkbox or radio button on page
                else if (htmlControlName.equalsIgnoreCase("check box") || htmlControlName.equalsIgnoreCase("radio button"))
                {
                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    if (accessMethod.equalsIgnoreCase("check"))
                    {
                        pw.println(TAB2 + "<selenium-check functionId=\"check '"
                                + controlName + "' "+htmlControlName+"\"  locator=\"id=${" + controlName + "}\" />");
                    }
                    else
                    {
                        pw.println(TAB2 + "<selenium-uncheck functionId=\"uncheck '"
                                + controlName + "' "+htmlControlName+"\"  locator=\"id=${" + controlName + "}\" />");
                    }
                }

                //Selenium tag for clicking on button
                else if (htmlControlName.equalsIgnoreCase("button"))
                {
                    pw.println(TAB2 + "<selenium-click functionId=\"click on '"
                            + controlName + "' "+htmlControlName+"\"  locator=\"name=" + controlName + "\" />");
                }

                //Selenium tag for selecting option from the combobox and list box depeding upon the
                //access method specified
                else if (htmlControlName.equalsIgnoreCase("combo box (single select)"))
                {
                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    pw.print(TAB2 + "<selenium-select functionId=\"select option for '"
                            + controlName + "' "+htmlControlName+"\"  selectLocator=\"name="
                            + controlName + "\" ");

                    if (accessMethod.equalsIgnoreCase("label"))
                    {
                        pw.print("optionLocator=\"label=${" + controlName + "}\"");
                    }
                    if (accessMethod.equalsIgnoreCase("value"))
                    {
                        pw.print("optionLocator=\"value=${" + controlName + "}\"");
                    }
                    if (accessMethod.equalsIgnoreCase("index"))
                    {
                        pw.print("optionLocator=\"index=${" + controlName + "}\"");
                    }
                    pw.println(" />");
                }

                //Selenium tag for selecting multiple items from the combobox or listbox
                else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    String accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    StringTokenizer multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
                    int tokens = 0;
                    while (tokens < multiValues.countTokens())
                    {
                        pw.print(TAB2 + "<selenium-add-selection functionId=\"select option for '"
                                + controlName + "' "+htmlControlName+"\" selectLocator=\"name="
                                + controlName + "\" ");

                        if (accessMethod.equalsIgnoreCase("label"))
                        {
                            pw.print("optionLocator=\"label=${" + controlName + (tokens + 1) + "}\"");
                        }
                        if (accessMethod.equalsIgnoreCase("value"))
                        {
                            pw.print("optionLocator=\"value=${" + controlName + (tokens + 1) + "}\"");
                        }
                        if (accessMethod.equalsIgnoreCase("index"))
                        {
                            pw.print("optionLocator=\"index=${" + controlName + (tokens + 1) + "}\"");
                        }
                        pw.println(" />");
                        tokens++;
                    }                    
                }

                //Selenium tag for entering date & time
                else if (htmlControlName.equalsIgnoreCase("date & time picker"))
                {
                    pw.println(TAB2 + "<selenium-type functionId=\"enter text into '"
                            + controlName + "' DATE AND TIME PICKER\"  locator=\"name="
                            + controlName + "\" keys=\"${" + controlName + "}\" />");                    
                }                

                //Selenium tag for the events and test case nature
                int count = 0;
                while (count < eventArray[counter])
                {
                    message = entityBean.getMessage()[eventCounter];
                    testCaseNature = dataMan.getTestCaseNature(entityBean.getTestCaseNatureID()[eventCounter]);

                    if ((message != null && testCaseNature != null))
                    {
                        if (entityBean.getEventID()[eventCounter] != -1)
                        {
                            controlEvent = dataMan.getControlEventName(entityBean.getEventID()[eventCounter]).toLowerCase();
                            pw.println(TAB2 + "<selenium-fire-event functionId=\"fire javascript event on '"
                                    + controlName + "'\" eventName=\"" + controlEvent
                                    + "\"  locator=\"name=" + controlName + "\" />");
                        }

                        if (testCaseNature.equalsIgnoreCase("verify element present"))
                        {
                            pw.println(TAB2 + "<selenium-assert-element-present functionId=\"check whether element '"
                                    + entityBean.getControlValue()[counter] + "' present or not\" locator=\"id=" + message + "\" />");
                        }

                        else if (testCaseNature.equalsIgnoreCase("verify text present"))
                        {
                            pw.println(TAB2 + "<selenium-assert-text-present functionId=\"Check for text using a glob pattern\" "
                                    + "pattern=\"" + message + "\" />");
                        }

                        else if (testCaseNature.equalsIgnoreCase("verify text"))
                        {
                            pw.println(TAB2 + "<selenium-assert-text-present functionId=\"check whether text '"
                                    + message + "' is present on page or not\" pattern=\"exact:" + message + "\" />");
                        }

                        else if (testCaseNature.equalsIgnoreCase("verify title"))
                        {
                            pw.println(TAB2 + "<selenium-assert-title-equals functionId=\"check page title is '"
                                    + message + "' or not\" title=\"" + message + "\" />");
                        }

                        else if (testCaseNature.equalsIgnoreCase("verify link"))
                        {
                            pw.println(TAB2 + "<selenium-assert-element-present functionId=\"check whether link '"
                                    + message + "' is present on page or not\" msg=\"expected link not present on page\" locator=\"link=" + message + "\" />");
                        }

                        else if (testCaseNature.equalsIgnoreCase("verify alert"))
                        {
                            pw.println(TAB2 + "<selenium-get-alert functionId=\"get the alert message text\" contextVariable=\"alert_text\" />");
                            pw.println(TAB + TAB2 + "<ju-session beginSession=\"true\">");
                            pw.println(TAB + TAB2 + "<ju-assert-equals functionId=\"check whether expected and actual value match\" "
                                    + "msg=\"text match fail\" expected=\"" + message
                                    + "\" actual=\"${alert_text}\" />");
                            pw.println(TAB + TAB2 + "</ju-session>");
                        }

                        else if (testCaseNature.equalsIgnoreCase("wait for page to load"))
                        {
                            pw.println(TAB2 + "<wait functionId=\"wait for '" + Integer.parseInt(message) + "' miliseconds\" delayTime=\"" + Integer.parseInt(message) + "\" />");
                        }
                    }
                    eventCounter++;
                    count++;
                }
            }

//          pw.println(TAB2 + "<UpdateTag functionId=\"UpdateTag\" transcationid=\"${transcationid}\" />");
            pw.println(TAB2 + "<wait functionId=\"wait for 3000 miliseconds\" delayTime=\"3000\" />");
            pw.println(TAB2 + "<selenium-close functionId=\"Close the current window\" />");
            pw.println(TAB + "</selenium-session>");
            pw.println("</testcase>");

            scriptStatus = true;
            csvStatus = generateCSV(transcationID, pageID, entityBean);
        }
        catch (NumberFormatException nfe)
        {
            scriptStatus = false;
            throw new Exception(nfe);
        }
        catch (Exception exp)
        {
            scriptStatus = false;
            throw new Exception(exp);
        }
        finally
        {
            pw.close();
            return (scriptStatus && csvStatus);
        }
    }

    private boolean generateCSV(int transcationID, int pageID, ScriptGenEntityBean entityBean) throws Exception
    {
        FileWriter writer = null;
        PrintWriter pw = null;
        ScriptGenDataManager dataMan = new ScriptGenDataManager();
        String controlNameLine = "transcationid,pageid";
        String controlValueLine = String.valueOf(transcationID) + "," + String.valueOf(pageID);

        try
        {
            String htmlControlName = null;
            String controlName = null;
            String controlValue = null;
            int length = entityBean.getHtmlControlID().length;

            writer = new FileWriter(tomcatPath + "/webapps/finstudio/generated/" + pageID + ".csv");
            pw = new PrintWriter(writer);
            
            for (int counter = 0; counter < length; counter++)
            {
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = entityBean.getControlNameID()[counter];
                controlValue = entityBean.getControlValue()[counter];

                if (htmlControlName.equalsIgnoreCase("text box")
                        || htmlControlName.equalsIgnoreCase("text area")
                        || htmlControlName.equalsIgnoreCase("date & time picker")
                        || htmlControlName.equalsIgnoreCase("link")
                        || htmlControlName.equalsIgnoreCase("button")
                        || htmlControlName.equalsIgnoreCase("image")
                        || htmlControlName.equalsIgnoreCase("combo box (single select)"))
                {
                    controlNameLine += "," + controlName;
                    controlValueLine += "," + controlValue;
                }
                
                else if (htmlControlName.equalsIgnoreCase("check box")
                        || htmlControlName.equalsIgnoreCase("radio button"))
                {
                    controlNameLine += "," + controlName;
                    controlValueLine += "," + controlName;
                }
                
                else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    StringTokenizer multiValues = null;
                    try
                    {
                        multiValues = new StringTokenizer(controlValue, ",");
                        int temp = 0;
                        while (multiValues.hasMoreTokens())
                        {
                            controlNameLine += "," + controlName + String.valueOf(temp + 1);
                            controlValueLine += "," + multiValues.nextToken();
                            temp++;
                        }
                    }
                    catch (Exception exp)
                    {
                        csvStatus = false;
                        throw new Exception(exp);
                    }
                }
            }
            pw.println(controlNameLine);
            pw.println(controlValueLine);
            csvStatus = true;
        }
        catch (Exception exp)
        {
            csvStatus = false;
            throw new Exception(exp);
        }
        finally
        {
            pw.close();
            return csvStatus;
        }
    }

    private String getBrowserName(int browserID)
    {
        String browserName = null;
        switch (browserID)
        {
           case -1:
            case 2:
            case 7: browserName = "firefox";
                    break;
            case 1: browserName = "iexplore";
                    break;
            case 3: browserName = "netscape";
                    break;
            case 4: browserName = "konquerer";
                    break;
            case 5: browserName = "opera";
                    break;
            case 6: browserName = "avant";
                    break;
            default:browserName = "firefox";
                    break;
        }
        return browserName;
    }
}