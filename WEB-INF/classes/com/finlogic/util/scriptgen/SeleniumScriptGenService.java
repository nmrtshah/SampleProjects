package com.finlogic.util.scriptgen;

import com.finlogic.apps.wfms2.scriptgen.ScriptGenService;
import com.finlogic.business.wfms2.scriptgen.ScriptGenDataManager;
import com.finlogic.business.wfms2.scriptgen.ScriptGenEntityBean;
import finpack.FinPack;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Ankur Mistry
 */
public class SeleniumScriptGenService
{

    private static final String TOMCAT_PATH = FinPack.getProperty("tomcat1_path");
    private static final String TAB = "\t";

    /**
     *
     * @param transactionID
     * @param pageID
     * @param eventArray
     * @param entityBean
     * @return boolean
     * @throws SQLException
     */
    public boolean generateScript(final int transactionID, final int pageID, final int[] eventArray, final ScriptGenEntityBean entityBean)
    {
        FileWriter writer;
        PrintWriter pwriter = null;
        boolean xmlStatus = false;
        boolean csvStatus = false;
        final ScriptGenDataManager dataMan = new ScriptGenDataManager();
        final ScriptGenService genService = new ScriptGenService();
        try
        {

            int totalControls;
            totalControls = eventArray.length;
            writer = new FileWriter(TOMCAT_PATH + "/webapps/finstudio/generated/" + pageID + ".xml");
            pwriter = new PrintWriter(writer);

            pwriter.println("<?xml version=\"1.0\"?>");
            pwriter.println("<testcase xmlns=\"jelly:jameleon\" stopTestExecutionOnFailure=\"false\" storeStateOnChange=\"true\" storeStateOnError=\"true\" useCSV=\"true\" countRow=\"true\">");
            pwriter.println("<test-case-summary>Auto Test Selenium Script</test-case-summary>");
            pwriter.println("<test-case-level></test-case-level>");
            pwriter.println("<test-case-id>" + transactionID + "</test-case-id>");
            pwriter.println("<functional-point-tested>Selenuim</functional-point-tested>");
            pwriter.println("<selenium-session baseUrl=\"http://" + entityBean.getCmbServerName() + ":8081/ACL/\" multiWindowMode=\"true\" browser=\"*firefox\" beginSession=\"true\" startSeleniumProxy=\"true\">");
            //pwriter.println("<selenium-session baseUrl=\"http://" + entityBean.getCmbServerName() + ":8081/ACLNEW/\" multiWindowMode=\"true\" browser=\"*" + getBrowserName(entityBean.getBrowserID()) + "\" beginSession=\"true\" startSeleniumProxy=\"true\">");
            pwriter.println(TAB + "<wait functionId=\"wait for 2000 miliseconds\" delayTime=\"2000\" />");
            pwriter.println(TAB + "<selenium-type functionId=\"Enter Values in username\" locator=\"a_user\" keys=\"${username}\"/>");
            pwriter.println(TAB + "<selenium-type functionId=\"Enter Values in password\" locator=\"a_pwd\" keys=\"${password}\"/>");
            pwriter.println(TAB + "<wait functionId=\"wait for 1500 miliseconds\" delayTime=\"1500\" />");
            pwriter.println(TAB + "<selenium-submit functionId=\"submit the current form\" locator=\"xpath=//form[@name='login']\" />");
            pwriter.println(TAB + "<wait functionId=\"wait for 3500 miliseconds\" delayTime=\"3500\" />");
            pwriter.println(TAB + "<selenium-click functionId=\"Click on project link\" locator=\"link=" + encode(dataMan.getProjectName(entityBean.getPrjID())) + "\"/>");
            //loop for parent modules
            SqlRowSet srs = genService.getParentModuleList(entityBean.getModuleID());
            List parentModuleList = new ArrayList();

            while (srs.next())
            {
                parentModuleList.add(srs.getString(1));
            }

            for (int i = parentModuleList.size() - 2; i >= 0; i--)
            {
                pwriter.println(TAB + "<wait functionId=\"wait for 1500 miliseconds\" delayTime=\"1500\" />");
                pwriter.println(TAB + "<selenium-click functionId=\"Page name\" locator=\"link=" + encode(parentModuleList.get(i).toString()) + "\"/>");
            }

            pwriter.println(TAB + "<wait functionId=\"wait for 3500 miliseconds\" delayTime=\"3500\" />");
            pwriter.println(TAB + "<InsertTag functionId=\"Insert test transacation details in DataBase\" transcationid=\"${transactionid}\" pageid=\"${pageid}\" />");
            pwriter.println(TAB + "<wait functionId=\"wait for 2000 miliseconds\" delayTime=\"2000\" />");

            String htmlControlName;
            String controlName;
            String controlEvent;
            String message;
            String testCaseNature;
            String nameid;
            String xpath;
            int confirm_msg_count = 0;
            int control_count = 0;

            for (int counter = 0, eventCounter = 0; counter < totalControls; counter++)
            {
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = encode(entityBean.getControlNameID()[counter]);
                nameid = entityBean.getAccess()[counter];
                xpath = entityBean.getXpath()[counter];
                /**
                 * Selenium tag for Text Box, Text Area or Date Time Picker Text
                 * Box to type values in it.
                 */
                if (htmlControlName.equalsIgnoreCase("text box") || htmlControlName.equalsIgnoreCase("text area") || htmlControlName.equalsIgnoreCase("date & time picker"))
                {
                    if ("xpath".equals(nameid))
                    {
                        pwriter.println(TAB + "<selenium-type functionId=\"enter text into '"
                                + controlName + "' " + htmlControlName + "\" locator=\"" + xpath
                                + "\" keys=\"${" + checkControlName(controlName, control_count).concat(String.valueOf(control_count)) + "}\" />");
                    }
                    else
                    {
                        pwriter.println(TAB + "<selenium-type functionId=\"enter text into '"
                                + controlName + "' " + htmlControlName + "\" locator=\"" + nameid + "=" + controlName
                                + "\" keys=\"${" + checkControlName(controlName, control_count) + "}\" />");
                    }
                }
                else if (htmlControlName.equalsIgnoreCase("file"))
                {
                    if ("xpath".equals(nameid))
                    {
                        pwriter.println(TAB + "<selenium-type functionId=\"enter image path into '"
                                + controlName + "' " + htmlControlName + "\" locator=\"" + xpath
                                + "\" keys=\"" + entityBean.getControlValue()[counter] + "\" />");
                        pwriter.println(TAB + "<wait functionId=\"wait for '2000' miliseconds\" delayTime=\"2000\" />");
                    }
                    else
                    {
                        pwriter.println(TAB + "<selenium-type functionId=\"enter image path into '"
                                + controlName + "' " + htmlControlName + "\" locator=\"" + nameid + "=" + controlName
                                + "\" keys=\"" + entityBean.getControlValue()[counter] + "\" />");
                        pwriter.println(TAB + "<wait functionId=\"wait for '3000' miliseconds\" delayTime=\"3000\" />");
                    }

                }
                /**
                 * Selenium Tag to click on Link.
                 */
                else if (htmlControlName.equalsIgnoreCase("link"))
                {

                    pwriter.println(TAB + "<selenium-click functionId=\"click on '"
                            + controlName + "' " + htmlControlName + "\"  locator=\"link=" + controlName + "\" />");
                }
                /**
                 * Selenium Tag for checking image source given by user is
                 * present or not.
                 */
                else if (htmlControlName.equalsIgnoreCase("image"))
                {
                    pwriter.println(TAB + "<selenium-assert-element-present functionId=\"check whether IMAGE with source '"
                            + entityBean.getControlValue()[counter] + "' present or not \" locator=\"xpath=//img[@src='${"
                            + checkControlName(controlName, control_count) + "}']\" />");
                }
                /**
                 * Selenium tag to check or un-check the check box on page.
                 */
                else if (htmlControlName.equalsIgnoreCase("check box"))
                {
                    String accessMethod;
                    accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
                    if ("xpath".equals(nameid) && accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println(TAB + "<selenium-check functionId=\"check '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"" + xpath + "\" />");
//                        pwriter.println(TAB + "<selenium-check functionId=\"check '"
//                                + controlName + "' " + htmlControlName + "\"  locator=\"" + xpath + "=${" + checkControlName(controlName, control_count).concat(String.valueOf(control_count)) + "}\" />");
//                    }
                    }
                    else if ("xpath".equals(nameid) && !accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println(TAB + "<selenium-uncheck functionId=\"uncheck '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"" + xpath + "}\" />");
                    }
                    else if (!"xpath".equals(nameid) && accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println(TAB + "<selenium-check functionId=\"check '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"" + nameid + "=${" + checkControlName(controlName, control_count) + "}\" />");
                    }
                    else
                    {
                        pwriter.println(TAB + "<selenium-uncheck functionId=\"uncheck '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"" + nameid + "=${" + checkControlName(controlName, control_count) + "}\" />");
                    }
                }
                /**
                 * Selenium tag to check or un-check the radio button on page.
                 */
                else if (htmlControlName.equalsIgnoreCase("radio button"))
                {
                    String accessMethod;
                    accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);
                    if (accessMethod.equalsIgnoreCase("check"))
                    {
                        pwriter.println(TAB + "<selenium-check functionId=\"check '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"xpath=//input[@" + nameid + "='" + controlName + "' and @value='${" + checkControlName(controlName, control_count) + "}']\" />");
                    }
                    else
                    {
                        pwriter.println(TAB + "<selenium-uncheck functionId=\"uncheck '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"xpath=//input[@" + nameid + "='" + controlName + "' and @value='${" + checkControlName(controlName, control_count) + "}']\" />");
                    }
                }
                /**
                 * Selenium tag to click on button.
                 */
                else if (htmlControlName.equalsIgnoreCase("button"))
                {
                    message = entityBean.getMessage()[eventCounter];
                    testCaseNature = dataMan.getTestCaseNature(entityBean.getTestCaseNatureID()[eventCounter]);

                    if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1) && testCaseNature.equalsIgnoreCase("verify confirmation"))
                    {
                        /*------- Edited by Divyang Kankotiya -------- */
                        if ("ok".equals(message))
                        {
                            pwriter.println(TAB + "<selenium-choose-ok-on-next-confirmation functionId=\"Tell Selenium to answer 'ok' on next confirm dialog\" />");
                        }
                        /*-------End Edited by Divyang Kankotiya -------- */
                        if ("cancel".equals(message))
                        {
                            pwriter.println(TAB + "<selenium-choose-cancel-on-next-confirmation functionId=\"Tell Selenium to answer 'cancel' on next confirm dialog\" />");
                        }
                    }
                    if ("xpath".equals(nameid))
                    {
                        pwriter.println(TAB + "<selenium-click functionId=\"click on '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"" + xpath + "\" />");
                    }
                    else
                    {
                        pwriter.println(TAB + "<selenium-click functionId=\"click on '"
                                + controlName + "' " + htmlControlName + "\"  locator=\"" + nameid + "=" + controlName + "\" />");
                    }
                }
                /**
                 * Selenium tag for selecting option from the combo box or list
                 * box<br> depending upon the access method specified.
                 */
                else if (htmlControlName.equalsIgnoreCase("combo box (single select)") || htmlControlName.equalsIgnoreCase("file"))
                {
                    String accessMethod;
                    accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    if ("xpath".equals(nameid))
                    {
                        pwriter.print(TAB + "<selenium-select functionId=\"select option for '"
                                + controlName + "' " + htmlControlName + "\"  selectLocator=\"" + xpath + "\" ");

                        if (accessMethod.equalsIgnoreCase("label"))
                        {
                            pwriter.print("optionLocator=\"label=${" + checkControlName(controlName, control_count).concat(String.valueOf(control_count)) + "}\"");
                        }
                        if (accessMethod.equalsIgnoreCase("value"))
                        {
                            pwriter.print("optionLocator=\"value=${" + checkControlName(controlName, control_count).concat(String.valueOf(control_count)) + "}\"");
                        }
                        if (accessMethod.equalsIgnoreCase("index"))
                        {
                            pwriter.print("optionLocator=\"index=${" + checkControlName(controlName, control_count).concat(String.valueOf(control_count)) + "}\"");
                        }
                    }
                    else
                    {
                        pwriter.print(TAB + "<selenium-select functionId=\"select option for '"
                                + controlName + "' " + htmlControlName + "\"  selectLocator=\"" + nameid + "="
                                + controlName + "\" ");

                        if (accessMethod.equalsIgnoreCase("label"))
                        {
                            pwriter.print("optionLocator=\"label=${" + checkControlName(controlName, control_count) + "}\"");
                        }
                        if (accessMethod.equalsIgnoreCase("value"))
                        {
                            pwriter.print("optionLocator=\"value=${" + checkControlName(controlName, control_count) + "}\"");
                        }
                        if (accessMethod.equalsIgnoreCase("index"))
                        {
                            pwriter.print("optionLocator=\"index=${" + checkControlName(controlName, control_count) + "}\"");
                        }
                    }
                    pwriter.println(" />");
                }
                /**
                 * Selenium tag for selecting multiple items in combo box or
                 * list box.
                 */
                else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    String accessMethod;
                    accessMethod = dataMan.getAccessType(entityBean.getAccessID()[counter]);

                    /**
                     * Selenium cannot recognize multiple values selection for
                     * single control name.<br> So in CSV file the control name
                     * will be appended with the counter to select<br> multiple
                     * values in Combo Box. Control locator name won't be
                     * changed.<br> Suffix value is for unique identification
                     * purpose only.
                     */
                    StringTokenizer multiValues;
                    multiValues = new StringTokenizer(entityBean.getControlValue()[counter], ",");
                    int tokens = 0;
                    while (tokens < multiValues.countTokens())
                    {
                        if ("xpath".equals(nameid))
                        {
                            pwriter.print(TAB + "<selenium-add-selection functionId=\"select option for '"
                                    + controlName + "' " + htmlControlName + "\" selectLocator=\"" + xpath + "\" ");

                            if (accessMethod.equalsIgnoreCase("label"))
                            {
                                pwriter.print("optionLocator=\"label=${" + checkControlName(controlName + (tokens + 1), control_count).concat(String.valueOf(control_count)) + "}\"");
                            }
                            if (accessMethod.equalsIgnoreCase("value"))
                            {
                                pwriter.print("optionLocator=\"value=${" + checkControlName(controlName + (tokens + 1), control_count).concat(String.valueOf(control_count)) + "}\"");
                            }
                            if (accessMethod.equalsIgnoreCase("index"))
                            {
                                pwriter.print("optionLocator=\"index=${" + checkControlName(controlName + (tokens + 1), control_count).concat(String.valueOf(control_count)) + "}\"");
                            }
                        }
                        else
                        {
                            pwriter.print(TAB + "<selenium-add-selection functionId=\"select option for '"
                                    + controlName + "' " + htmlControlName + "\" selectLocator=\"" + nameid + "="
                                    + controlName + "\" ");

                            if (accessMethod.equalsIgnoreCase("label"))
                            {
                                pwriter.print("optionLocator=\"label=${" + checkControlName(controlName + (tokens + 1), control_count) + "}\"");
                            }
                            if (accessMethod.equalsIgnoreCase("value"))
                            {
                                pwriter.print("optionLocator=\"value=${" + checkControlName(controlName + (tokens + 1), control_count) + "}\"");
                            }
                            if (accessMethod.equalsIgnoreCase("index"))
                            {
                                pwriter.print("optionLocator=\"index=${" + checkControlName(controlName + (tokens + 1), control_count) + "}\"");
                            }
                        }

                        pwriter.println(" />");
                        tokens++;
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
                            pwriter.println(TAB + "<selenium-choose-ok-on-next-confirmation functionId=\"Tell Selenium to answer 'ok' on next confirm dialog\" />");
                        }
                        /*-------End Edited by Divyang Kankotiya -------- */
                        if ("cancel".equals(message) && (!htmlControlName.equalsIgnoreCase("button")))
                        {
                            pwriter.println(TAB + "<selenium-choose-cancel-on-next-confirmation functionId=\"Tell Selenium to answer 'cancel' on next confirm dialog\" />");
                        }
                    }

                    if ((entityBean.getEventID()[eventCounter] != -1) && (!htmlControlName.equalsIgnoreCase("button")))
                    {
                        if ("".equals(nameid))
                        {
                            controlEvent = dataMan.getControlEventName(entityBean.getEventID()[eventCounter]).toLowerCase();
                            pwriter.println(TAB + "<selenium-fire-event functionId=\"fire javascript event on '"
                                    + controlName + "'\" eventName=\"" + controlEvent.substring(2)
                                    + "\"  locator=\"" + htmlControlName + "=" + controlName + "\" />");
                            pwriter.println("<!-- " + TAB + "<wait functionId=\"wait for '2000' miliseconds\" delayTime=\"2000\" /> -->");
                        }
                        else
                        {
                            controlEvent = dataMan.getControlEventName(entityBean.getEventID()[eventCounter]).toLowerCase();
                            pwriter.println(TAB + "<selenium-fire-event functionId=\"fire javascript event on '"
                                    + controlName + "'\" eventName=\"" + controlEvent.substring(2)
                                    + "\"  locator=\"" + nameid + "=" + controlName + "\" />");
                            pwriter.println("<!--" + TAB + "<wait functionId=\"wait for '2000' miliseconds\" delayTime=\"2000\" /> -->");
                        }
                    }
                    /**
                     * Switch to newly open window 8 == Switch to New Window
                     */
                    if (entityBean.getTestCaseNatureID()[eventCounter] == 8)
                    {
                        pwriter.println(TAB + "<selenium-get-all-window-names functionId=\"Get all window names.\" contextVariable=\"allNames\" />");
                        pwriter.println(TAB + "<selenium-select-window functionId=\"Select the newly-opened window\" windowName=\"${allNames.get(1)}\" />");
                        pwriter.println(TAB + "<wait functionId=\"wait for '3000' miliseconds\" delayTime=\"3000\" />");
                    }
                    else if ((message != null) && (entityBean.getTestCaseNatureID()[eventCounter] != -1))
                    {

                        if (testCaseNature.equalsIgnoreCase("verify element present"))
                        {
                            pwriter.println(TAB + "<selenium-assert-element-present functionId=\"check whether element '"
                                    + entityBean.getControlValue()[counter] + "' present or not\" locator=\"" + nameid + "=" + message + "\" />");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify text present"))
                        {
                            pwriter.println(TAB + "<selenium-assert-text-present functionId=\"Check for text using a glob pattern\" "
                                    + "pattern=\"" + message + "\" />");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify text"))
                        {
                            pwriter.println(TAB + "<selenium-assert-text-present functionId=\"check whether text '"
                                    + message + "' is present on page or not\" pattern=\"exact:" + message + "\" />");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify title"))
                        {
                            pwriter.println(TAB + "<selenium-assert-title-equals functionId=\"check page title is '"
                                    + message + "' or not\" title=\"" + message + "\" />");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify link"))
                        {
                            pwriter.println(TAB + "<selenium-assert-element-present functionId=\"check whether link '"
                                    + message + "' is present on page or not\" msg=\"expected link not present on page\" locator=\"link=" + message + "\" />");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify alert"))
                        {
                            pwriter.println(TAB + "<selenium-get-alert functionId=\"get the alert message text\" contextVariable=\"alert_text\" />");
                            pwriter.println(TAB + "<ju-session beginSession=\"true\">");
                            pwriter.println(TAB + TAB + "<ju-assert-true functionId=\"check whether expected and actual value match\" "
                                    + "msg=\"text match fail\" test=\"${alert_text.contains('" + message + "')}\" />");
//                            pwriter.println(TAB + TAB + "<ju-assert-equals functionId=\"check whether expected and actual value match\" "
//                                    + "msg=\"text match fail\" expected=\"" + message
//                                    + "\" actual=\"${alert_text}\" />");
                            pwriter.println(TAB + "</ju-session>");
                        }
                        else if (testCaseNature.equalsIgnoreCase("verify confirmation"))
                        {
                            pwriter.println(TAB + "<selenium-get-confirmation functionId=\"Get the confirmation text\" contextVariable=\"confirm_text\"/> ");
                            if (entityBean.getConfirmMsg() != null && confirm_msg_count < entityBean.getConfirmMsg().length)
                            {
                                if (!"".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
                                {
                                    pwriter.println(TAB + "<ju-session beginSession=\"true\">");
                                    pwriter.println(TAB + TAB + "<ju-assert-equals functionId=\"check whether expected and actual value match\" "
                                            + "msg=\"text match fail\"  test=\"${confirm_text.contains('" + encode(entityBean.getConfirmMsg()[confirm_msg_count]) + "')}\" />");
//                                    pwriter.println(TAB + TAB + "<ju-assert-equals functionId=\"check whether expected and actual value match\" "
//                                            + "msg=\"text match fail\" expected=\"" + encode(entityBean.getConfirmMsg()[confirm_msg_count])
//                                            + "\" actual=\"${confirm_text}\" />");
                                    pwriter.println(TAB + "</ju-session>");
                                }
                                confirm_msg_count++;
                            }
                            if (entityBean.getConfirmMsg() == null)
                            {
                                if ("".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
                                {
                                    pwriter.println(TAB + "<ju-session beginSession=\"true\">");
                                    pwriter.println(TAB + TAB + "<ju-assert-equals functionId=\"check whether expected and actual value match\" "
                                            + "msg=\"text match fail\"  test=\"${confirm_text.contains('" + encode(entityBean.getConfirmMsg()[confirm_msg_count]) + "')}\" />");
//                                    pwriter.println(TAB + TAB + "<ju-assert-equals functionId=\"check whether expected and actual value match\" "
//                                            + "msg=\"text match fail\" expected=\"" + encode(entityBean.getConfirmMsg()[confirm_msg_count])
//                                            + "\" actual=\"${confirm_text}\" />");
                                    pwriter.println(TAB + "</ju-session>");
                                }
                                confirm_msg_count++;
                            }
                        }
                        else if (testCaseNature.equalsIgnoreCase("wait for page to load"))
                        {
                            pwriter.println(TAB + "<wait functionId=\"wait for '" + Integer.parseInt(message) + "' miliseconds\" delayTime=\"" + Integer.parseInt(message) + "\" />");
                        }
                    }
                    eventCounter++;
                    count++;
                }
                control_count++;
            }
//          pwriter.println(TAB + "<UpdateTag functionId=\"UpdateTag\" transcationid=\"${transactionid}\" />");
            pwriter.println(TAB + "<wait functionId=\"wait for 3500 miliseconds\" delayTime=\"3500\" />");
            pwriter.println(TAB + "<selenium-close functionId=\"Close the current window\" />");
            pwriter.println("</selenium-session>");
            pwriter.println("</testcase>");

            xmlStatus = true;
            csvStatus = generateCSV(transactionID, pageID, eventArray.length, entityBean);
        }
        catch (NumberFormatException nfe)
        {
            xmlStatus = false;
            throw nfe;
        }
        finally
        {
            pwriter.close();
            return (xmlStatus && csvStatus);
        }
    }

    /**
     *
     * @param transactionID
     * @param pageID
     * @param controlsCount
     * @param entityBean
     * @return boolean
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    private boolean generateCSV(final int transactionID, final int pageID, final int controlsCount, final ScriptGenEntityBean entityBean)
    {

        FileWriter writer;
        PrintWriter pwriter = null;
        boolean csvStatus = false;
        boolean isFirst = false;
        ScriptGenDataManager dataMan;
        dataMan = new ScriptGenDataManager();
        String controlNameLine;
        controlNameLine = "transactionid,pageid,username,password";
        String controlValueLine;
        controlValueLine = transactionID + "," + pageID + "," + entityBean.getTxtUserName() + "," + entityBean.getTxtPassword();
        int csv_control_count = 0;
        try
        {

            String htmlControlName;
            String controlName;
            String controlValue;
            int length;
            length = controlsCount;

            writer = new FileWriter(TOMCAT_PATH + "/webapps/finstudio/generated/" + pageID + ".csv");
            pwriter = new PrintWriter(writer);

            Set unique_names = new HashSet();
            Set repeat_names = new HashSet();
            for (int counter = 0; counter < length; counter++)
            {
                if (entityBean.getControlNameID()[counter].equals("-"))
                {
                }
                else
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

            for (int counter = 0; counter < length; counter++)
            {
                htmlControlName = dataMan.getHTMLControlName(entityBean.getHtmlControlID()[counter]);
                controlName = entityBean.getControlNameID()[counter];
                controlValue = entityBean.getControlValue()[counter].trim();

                if (repeat_names.contains((String) controlName) && htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    StringTokenizer multiValues;
                    try
                    {
                        multiValues = new StringTokenizer(controlValue, ",");
                        int temp = 0;
                        while (multiValues.hasMoreTokens())
                        {
                            controlNameLine = controlNameLine.concat(",").concat(checkControlName(controlName + (temp + 1), csv_control_count));
                            if (isFirst)
                            {
                                controlNameLine = controlNameLine.concat(String.valueOf(csv_control_count));
                            }
                            controlValueLine = controlValueLine.concat(",").concat(multiValues.nextToken());
                            temp++;
                        }
                    }
                    catch (NumberFormatException exp)
                    {
                        csvStatus = false;
                        throw new Exception(exp);
                    }
                    isFirst = true;
                }
                else if (repeat_names.contains((String) controlName) && !htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                {
                    controlNameLine = controlNameLine.concat(",").concat(checkControlName(controlName, csv_control_count));
                    if (isFirst)
                    {
                        controlNameLine = controlNameLine.concat(String.valueOf(csv_control_count));
                    }
                    controlValueLine = controlValueLine.concat(",").concat(controlValue);
                    isFirst = true;
                }
                else
                {
                    if (htmlControlName.equalsIgnoreCase("text box")
                            || htmlControlName.equalsIgnoreCase("text area")
                            || htmlControlName.equalsIgnoreCase("date & time picker")
                            || htmlControlName.equalsIgnoreCase("image")
                            || htmlControlName.equalsIgnoreCase("combo box (single select)"))
                    {
                        controlNameLine = controlNameLine.concat(",").concat(checkControlName(controlName, csv_control_count));
                        controlValueLine = controlValueLine.concat(",").concat(controlValue);
                    }
                    else if (htmlControlName.equalsIgnoreCase("check box"))
                    {
                        controlNameLine = controlNameLine.concat(",").concat(checkControlName(controlName, csv_control_count));
                        controlValueLine = controlValueLine.concat(",").concat(controlName);
                    }
                    else if (htmlControlName.equalsIgnoreCase("radio button"))
                    {
                        controlNameLine = controlNameLine.concat(",").concat(checkControlName(controlName, csv_control_count));
                        controlValueLine = controlValueLine.concat(",").concat(controlValue);
                    }
                    else if (htmlControlName.equalsIgnoreCase("combo box (multi select)"))
                    {
                        /**
                         * For Combo Box (Multi Select), control values are
                         * supposed to be<br> provided in comma separated format
                         * to select multiple values in Combo Box.
                         */
                        StringTokenizer multiValues;
                        try
                        {
                            multiValues = new StringTokenizer(controlValue, ",");
                            int temp = 0;
                            while (multiValues.hasMoreTokens())
                            {
                                controlNameLine = controlNameLine.concat(",").concat(checkControlName(controlName + (temp + 1), csv_control_count));
                                controlValueLine = controlValueLine.concat(",").concat(multiValues.nextToken());
                                temp++;
                            }
                        }
                        catch (NumberFormatException exp)
                        {
                            csvStatus = false;
                            throw new Exception(exp);
                        }
                    }
                }
                csv_control_count++;
            }
            pwriter.println(controlNameLine);
            pwriter.println(controlValueLine);
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
        finally
        {
            pwriter.close();
            return csvStatus;
        }
    }

    /**
     *
     * @param value
     * @return String
     */
    private String encode(final String value)
    {
        String temp = value;
        if (value.indexOf('&') > 0)
        {
            /*------- Edited by Divyang Kankotiya -------- */
            temp = temp.replaceAll("&", "&");
            //temp = temp.replaceAll("&", "&amp;");
             /*-------End Edited by Divyang Kankotiya -------- */
        }
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

    /**
     *
     * @param browserID
     * @return String
     */
//    private String getBrowserName(final int browserID)
//    {
//        String browserName;
//        if (browserID == -1 || browserID == 2 || browserID == 7)
//        {
//            browserName = "firefox";
//        }
//        else if (browserID == 1)
//        {
//            browserName = "iexplore";
//        }
//        else if (browserID == 3)
//        {
//            browserName = "netscape";
//        }
//        else if (browserID == 4)
//        {
//            browserName = "konquerer";
//        }
//        else if (browserID == 5)
//        {
//            browserName = "opera";
//        }
//        else if (browserID == 6)
//        {
//            browserName = "avant";
//        }
//        else
//        {
//            browserName = "firefox";
//        }
//        return browserName;
//    }
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
}
