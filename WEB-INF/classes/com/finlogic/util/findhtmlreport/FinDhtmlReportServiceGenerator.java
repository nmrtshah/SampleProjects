/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import com.finlogic.util.WsdlParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportServiceGenerator
{

    public void generateReportService(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String moduleName = Character.toUpperCase(dEntityBean.getModuleName().charAt(0)) + dEntityBean.getModuleName().substring(1).toLowerCase(Locale.getDefault());
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/classes/com/finlogic/apps/"
                + projectName.toLowerCase(Locale.getDefault()) + "/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Service.java");

        pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();
        pw.println("import com.finlogic.util.datastructure.JSONParser;");
        pw.println("import java.util.List;");
        pw.println("import java.util.ArrayList;");
        pw.println("import java.util.Map;");
        pw.println("import java.sql.SQLException;");
        //pw.println("import java.util.ArrayList;");
        //imports for webservice
        Set wsImports = new HashSet();
        WsdlParser wsdlParser = new WsdlParser();
        Set cmbVal = new HashSet();
        if (dEntityBean.isFilter())
        {
            String[] fltrControls = dEntityBean.getFltrControl();
            String[] fltrCmbSource = dEntityBean.getFltrCmbSource();
            int fltrLen = fltrControls.length;
            for (int i = 0; i < fltrLen; i++)
            {
                if (fltrControls[i].equals("ComboBox") || fltrControls[i].equals("TextLikeCombo"))
                {
                    if (fltrCmbSource[i].equals("fltrCmbSrcWS"))
                    {
                        String consumer = dEntityBean.getFltrTxtWsIntrface()[i].replace("Service", "Consumer");
                        wsImports.add("com.finlogic.eai.ws.consumer." + dEntityBean.getFltrTxtWsProject()[i] + "." + consumer);
                        wsdlParser.getWsImports(dEntityBean.getFltrTxtWsExps()[i], wsImports);
                        Set exps = wsdlParser.getWsThrows(dEntityBean.getFltrTxtWsExps()[i]);
                        if (exps.size() > 0)
                        {
                            wsImports.add("javax.xml.ws.WebServiceException");
                        }
                    }
                    else if (fltrCmbSource[i].equals("fltrCmbSrcCommonCmb"))
                    {
                        String str = dEntityBean.getFltrCmbCommonQuery()[i].substring(0, 1).toUpperCase().concat(dEntityBean.getFltrCmbCommonQuery()[i].substring(1));
                        wsImports.add("com.finlogic.service." + str + "Service");
                        cmbVal.add(dEntityBean.getFltrCmbCommonQuery()[i]);
                    }
                }
            }
        }
        Iterator iter = wsImports.iterator();
        while (iter.hasNext())
        {
            Object element = iter.next();
            pw.println("import " + element + ";");
        }

        pw.println();
        pw.println("public class " + moduleName + "Service");
        pw.println("{");
        pw.println("    private final " + moduleName + "Report report = new " + moduleName + "Report();");

        Iterator val = cmbVal.iterator();
        while (val.hasNext())
        {
            Object element = val.next();
            String str = element.toString().substring(0, 1).toUpperCase().concat(element.toString().substring(1));
            pw.println("    private final " + str + "Service " + element + "Service = new " + str + "Service();");
        }

        pw.println();
        pw.println("    public String getReportData(final " + moduleName + "FormBean formBean) throws SQLException,ClassNotFoundException");
        pw.println("    {");
        pw.println("        List<Map> list = new ArrayList();");
        pw.println("        String jsonStr = \"\";");
        pw.println("        JSONParser jsonParser = new JSONParser();");
        boolean isDetailLinkControl = false;
        if (dEntityBean.getCol() != null)
        {
            for (int i = 0; i < dEntityBean.getCol().length; i++)
            {
                if (dEntityBean.getControl()[i].equals("link"))
                {
                    isDetailLinkControl = true;
                    break;
                }
            }
        }
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("        if(formBean.getRdoReportType().equals(\"detailReport\"))");
            pw.println("        {");
            pw.println("            list = report.getDetailReportData(formBean);");
            if (isDetailLinkControl)
            {
                pw.println("        int len = list.size();");
                pw.println("        for (int i = 0; i < len; i++)");
                pw.println("        {");
                pw.println("            Map m = (Map) list.get(i);");
                for (int i = 0; i < dEntityBean.getCol().length; i++)
                {
                    if (dEntityBean.getControl()[i].equals("link"))
                    {
                        pw.println("            if(m.get(\"" + dEntityBean.getCol()[i] + "\") != null)");
                        pw.println("            {");
                        pw.println("                m.put(\"" + dEntityBean.getCol()[i] + "\", m.get(\"" + dEntityBean.getCol()[i] + "\").toString().concat(\"^javascript:void(0)^_self\"));");
                        pw.println("            }");
                    }
                }
                pw.println("        }");
            }
            pw.println("            jsonStr = jsonParser.parse(list, \"" + dEntityBean.getPrimaryKey() + "\", true, false);");
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            list = report.getSummaryReportData(formBean);");
            boolean isSummaryLinkControl = false;
            if (sEntityBean.getGridColumn() != null)
            {
                for (int i = 0; i < sEntityBean.getGridColumn().length; i++)
                {
                    if (sEntityBean.getColumnControl()[i].equals("link"))
                    {
                        isSummaryLinkControl = true;
                        break;
                    }
                }
            }

            if (isSummaryLinkControl)
            {
                pw.println("        int len = list.size();");
                pw.println("        for (int i = 0; i < len; i++)");
                pw.println("        {");
                pw.println("            Map m = (Map) list.get(i);");
                for (int i = 0; i < sEntityBean.getGridColumn().length; i++)
                {
                    if (sEntityBean.getColumnControl()[i].equals("link"))
                    {
                        pw.println("            if(m.get(\"" + sEntityBean.getGridColumn()[i] + "\") != null)");
                        pw.println("            {");
                        pw.println("                m.put(\"" + sEntityBean.getGridColumn()[i] + "\", m.get(\"" + sEntityBean.getGridColumn()[i] + "\").toString().concat(\"^javascript:void(0)^_self\"));");
                        pw.println("            }");
                    }
                }
                pw.println("        }");
            }
            pw.println("            jsonStr = jsonParser.parse(list, \"" + sEntityBean.getPrimaryKey() + "\", true, false);");
            pw.println("        }");
        }
        else
        {
            pw.println("        list = report.getDetailReportData(formBean);");
            if (isDetailLinkControl)
            {
                pw.println("        int len = list.size();");
                pw.println("        for (int i = 0; i < len; i++)");
                pw.println("        {");
                pw.println("            Map m = (Map) list.get(i);");
                for (int i = 0; i < dEntityBean.getCol().length; i++)
                {
                    if (dEntityBean.getControl()[i].equals("link"))
                    {
                        pw.println("            if(m.get(\"" + dEntityBean.getCol()[i] + "\") != null)");
                        pw.println("            {");
                        pw.println("                m.put(\"" + dEntityBean.getCol()[i] + "\", m.get(\"" + dEntityBean.getCol()[i] + "\").toString().concat(\"^javascript:void(0)^_self\"));");
                        pw.println("            }");
                    }
                }
                pw.println("        }");
            }
            pw.println("            jsonStr = jsonParser.parse(list, \"" + dEntityBean.getPrimaryKey() + "\", true, false);");
        }
        //pw.println("        return jsonParser.parse(list, \"" + dEntityBean.getPrimaryKey() + "\", true, false);");
        pw.println("        return jsonStr;");
        pw.println("    }");

        if (dEntityBean.isPdf() || dEntityBean.isExcel() || dEntityBean.isHtml() || dEntityBean.isChart())
        {
            pw.println("    public List<Map> getDetailExportData(" + moduleName + "FormBean formBean) throws SQLException,ClassNotFoundException");
            pw.println("    {");
            pw.println("        return report.getDetailReportData(formBean);");
            pw.println("    }");
        }
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("    public List<Map> getSummaryExportData(" + moduleName + "FormBean formBean) throws SQLException,ClassNotFoundException");
            pw.println("    {");
            pw.println("        return report.getSummaryReportData(formBean);");
            pw.println("    }");

        }
        // Existing code for Combo function
//        if (dEntityBean.getFltrCmbSource() != null && dEntityBean.getFltrTxtSrcQuery().length > 0)
//        {
//            String control[] = dEntityBean.getFltrControl();
//            int controlLen = control.length;
//            for (int i = 0; i < controlLen; i++)
//            {
//                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && "fltrCmbSrcQuery".equals(dEntityBean.getFltrCmbSource()[i]))
//                {
//                    pw.println("    public List get" + dEntityBean.getFltrTxtName()[i] + "List() throws SQLException,ClassNotFoundException");
//                    pw.println("    {");
//                    pw.println("        return report.get" + dEntityBean.getFltrTxtName()[i] + "List();");
//                    pw.println("    }");
//                }
//            }
//        }
        // New Code for Combo Function
        if (dEntityBean.getFltrCmbSource() != null && dEntityBean.getFltrTxtSrcQuery().length > 0)
        {
            String control[] = dEntityBean.getFltrControl();
            int controlLen = control.length;
            String depComboArry[] = null;
            for (int i = 0; i < controlLen; i++)
            {
                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && "fltrCmbSrcQuery".equals(dEntityBean.getFltrCmbSource()[i]))
                {
                    if (dEntityBean.getFltrchkForDependentCombo() != null && !dEntityBean.getFltrchkForDependentCombo()[i].equalsIgnoreCase("false") && dEntityBean.getFltrchkForDependentCombo().length > 0)
                    {
                        if (dEntityBean.getFltrCmbDependent() != null)
                        {
                            depComboArry = dEntityBean.getFltrCmbDependent().split(",");
                            if (!depComboArry[i].equalsIgnoreCase(""))
                            {
                                if (!depComboArry[i].equalsIgnoreCase(dEntityBean.getFltrTxtName()[i]))
                                {
                                    pw.println("    public List get" + dEntityBean.getFltrTxtName()[i] + "List(final " + moduleName + "FormBean formBean) throws SQLException,ClassNotFoundException");
                                    pw.println("    {");
                                    pw.println("        return report.get" + dEntityBean.getFltrTxtName()[i] + "List(formBean);");
                                    pw.println("    }");
                                }
                            }
                        }
                    }
                    else
                    {
                        pw.println("    public List get" + dEntityBean.getFltrTxtName()[i] + "List() throws SQLException,ClassNotFoundException");
                        pw.println("    {");
                        pw.println("        return report.get" + dEntityBean.getFltrTxtName()[i] + "List();");
                        pw.println("    }");
                    }
                }
            }
        }

        val = cmbVal.iterator();
        while (val.hasNext())
        {
            Object element = val.next();
            String str = element.toString().substring(0, 1).toUpperCase().concat(element.toString().substring(1));
            pw.println("    public List get" + str + "() throws SQLException,ClassNotFoundException");
            pw.println("    {");
            pw.println("        return " + element + "Service.get" + str + "();");
            pw.println("    }");
        }
        //to fill combo using webservice
        if (dEntityBean.isFilter())
        {
            String[] fltrLabel = dEntityBean.getFltrTxtName();
            String[] fltrControls = dEntityBean.getFltrControl();
            String[] fltrCmbSource = dEntityBean.getFltrCmbSource();
            int addLen = fltrControls.length;
            for (int i = 0; i < addLen; i++)
            {
                if (fltrControls[i].equals("ComboBox") || fltrControls[i].equals("TextLikeCombo"))
                {
                    if (fltrCmbSource[i].equals("fltrCmbSrcWS"))
                    {
                        String consumer = dEntityBean.getFltrTxtWsIntrface()[i].replace("Service", "Consumer");
                        String wsMethod = dEntityBean.getFltrCmbWsMethod()[i];
                        String wsParams = dEntityBean.getFltrTxtWsParams()[i];
                        String wsException = dEntityBean.getFltrTxtWsExps()[i];

                        makeWsMethodCode(pw, fltrLabel[i], consumer, wsMethod, wsParams, wsException);
                    }
                }
            }
        }
        pw.println("}");
        pw.close();
    }

    private void makeWsMethodCode(PrintWriter pw, final String methodName, final String consumer, final String wsMethod, final String wsParams, final String wsException)
    {
        WsdlParser wsdlParser = new WsdlParser();
        Set exceptions = wsdlParser.getWsThrows(wsException);

        pw.println("    public List get" + methodName + "List()");
        pw.println("    {");
        if (exceptions.size() > 0)
        {
            pw.println("        try");
            pw.println("        {");
        }
        pw.println("            " + consumer + " consumer = new " + consumer + "();");

        StringBuilder paramList = new StringBuilder();
        if (wsParams != null)
        {
            String str = wsParams;
            str = str.replace("[", "");
            str = str.replace("]", "");
            if (!"".equals(str))
            {
                String[] params = str.split(",");
                for (int j = 0; j < params.length; j++)
                {
                    params[j] = params[j].trim();
                    if ("String".equals(params[j]))
                    {
                        paramList.append("\"\", ");
                    }
                    else if ("int".equals(params[j]) || "byte".equals(params[j]) || "short".equals(params[j]))
                    {
                        paramList.append("0, ");
                    }
                    else if ("long".equals(params[j]))
                    {
                        paramList.append("0L, ");
                    }
                    else if ("float".equals(params[j]))
                    {
                        paramList.append("0.0f, ");
                    }
                    else if ("double".equals(params[j]))
                    {
                        paramList.append("0.0, ");
                    }
                    else if ("boolean".equals(params[j]))
                    {
                        paramList.append("false, ");
                    }
                    else if ("char".equals(params[j]))
                    {
                        paramList.append("'', ");
                    }
                    else
                    {
                        paramList.append(params[j]).append(", ");
                    }
                }
                if (paramList.length() > 2)
                {
                    paramList.deleteCharAt(paramList.length() - 1);
                    paramList.deleteCharAt(paramList.length() - 1);
                }
            }
        }
        pw.println("            List list = consumer." + wsMethod + "(" + paramList + ");");
        pw.println("            return list;");
        if (exceptions.size() > 0)
        {
            pw.println("        }");
            Iterator expIter = exceptions.iterator();
            while (expIter.hasNext())
            {
                // Get element
                Object element = expIter.next();
                pw.println("        catch (" + element + " e)");
                pw.println("        {");
                pw.println("            throw new WebServiceException(e.getMessage(), e);");
                pw.println("        }");
            }
        }
        pw.println("    }");
    }
}
