/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class MasterControllerGenerator
{

    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String QUERY = "Query";
    private static final String WEBSERVICE = "WebService";
    private static final String COMMONCMB = "CommonCmb";
    private static final String DTPKR = "DatePicker";
    private static final String FILEBOX = "FileBox";

    public void generateMasterController(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String moduleName;
        moduleName = Character.toUpperCase(formBean.getTxtModuleName().charAt(0)) + formBean.getTxtModuleName().substring(1).toLowerCase(Locale.getDefault());
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/classes/com/finlogic/apps/"
                + projectName.toLowerCase(Locale.getDefault()) + "/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + moduleName + "Controller.java");

        pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();
        pw.println("import javax.servlet.http.HttpServletRequest;");
        pw.println("import javax.servlet.http.HttpServletResponse;");
        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        }
        pw.println("import org.springframework.web.servlet.ModelAndView;");
        pw.println("import org.springframework.web.servlet.mvc.multiaction.MultiActionController;");

        boolean CmnFile = false;
        if (formBean.isChkAdd())
        {
            String[] mstAddControls;
            mstAddControls = formBean.getHdnAddControl();
            int mstAddLen;
            mstAddLen = mstAddControls.length;
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddControls[i].equals(FILEBOX))
                {
                    CmnFile = true;
                    break;
                }
            }
        }

        boolean importDtFmt = false;
        String[] mstAEDFields;
        mstAEDFields = formBean.getHdnAddField();
        int mstAEDLen;
        mstAEDLen = mstAEDFields.length;
        String[] allMstColumns;
        allMstColumns = formBean.getHdnMstAllColumns();
        String[] allMstDataTypes;
        allMstDataTypes = formBean.getHdnMstAllDataTypes();
        int allColLen;
        allColLen = allMstColumns.length;
        for (int i = 0; i < mstAEDLen; i++)
        {
            for (int j = 0; j < allColLen; j++)
            {
                if (mstAEDFields[i].equals(allMstColumns[j]) && allMstDataTypes[j].equals("Date"))
                {
                    pw.println("import java.text.DateFormat;");
                    pw.println("import java.text.SimpleDateFormat;");
                    pw.println("import java.util.Date;");
                    importDtFmt = true;
                    break;
                }
            }
        }

        if (!importDtFmt && formBean.isChkAdd())
        {
            String[] mstAddControls;
            mstAddControls = formBean.getHdnAddControl();
            int mstAddLen;
            mstAddLen = mstAddControls.length;
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddControls[i].equals(DTPKR))
                {
                    pw.println("import java.text.DateFormat;");
                    pw.println("import java.text.SimpleDateFormat;");
                    pw.println("import java.util.Date;");
                    importDtFmt = true;
                    break;
                }
            }
        }

        if (!importDtFmt && formBean.isChkEdit())
        {
            if (formBean.getHdnEditControl() != null)
            {
                String[] mstEditControls;
                mstEditControls = formBean.getHdnEditControl();
                int mstEditLen;
                mstEditLen = mstEditControls.length;
                for (int i = 0; i < mstEditLen; i++)
                {
                    if (mstEditControls[i].equals(DTPKR))
                    {
                        pw.println("import java.text.DateFormat;");
                        pw.println("import java.text.SimpleDateFormat;");
                        pw.println("import java.util.Date;");
                        importDtFmt = true;
                        break;
                    }
                }
            }
        }

        if (!importDtFmt && formBean.isChkDelete())
        {
            if (formBean.getHdnDeleteControl() != null)
            {
                String[] mstDelControls;
                mstDelControls = formBean.getHdnDeleteControl();
                int mstDelLen;
                mstDelLen = mstDelControls.length;
                for (int i = 0; i < mstDelLen; i++)
                {
                    if (mstDelControls[i].equals(DTPKR))
                    {
                        pw.println("import java.text.DateFormat;");
                        pw.println("import java.text.SimpleDateFormat;");
                        pw.println("import java.util.Date;");
                        importDtFmt = true;
                        break;
                    }
                }
            }
        }

        if (!importDtFmt && formBean.isChkView())
        {
            if (formBean.getHdnViewControl() != null)
            {
                String[] mstViewControls;
                mstViewControls = formBean.getHdnViewControl();
                int mstViewLen;
                mstViewLen = mstViewControls.length;
                for (int i = 0; i < mstViewLen; i++)
                {
                    if (mstViewControls[i].equals(DTPKR))
                    {
                        pw.println("import java.text.DateFormat;");
                        pw.println("import java.text.SimpleDateFormat;");
                        pw.println("import java.util.Date;");
                        break;
                    }
                }
            }
        }

//        if (formBean.isChkHeader())
//        {
//            pw.println("import java.util.Calendar;");
//        }
        if (formBean.isChkView() && (formBean.isChkPdf() || formBean.isChkXls()))
        {
            pw.println("import java.io.BufferedInputStream;");
            pw.println("import java.io.BufferedOutputStream;");
            if (CmnFile)
            {
                pw.println("import java.io.File;");
            }
            pw.println("import java.io.FileInputStream;");
            pw.println("import javax.servlet.ServletException;");
            pw.println("import javax.servlet.ServletOutputStream;");
            pw.println("import java.util.HashMap;");
            pw.println("import java.util.Locale;");
            pw.println("import java.util.Map;");
            pw.println("import net.sf.jasperreports.engine.JRExporter;");
            pw.println("import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;");
            pw.println("import net.sf.jasperreports.engine.JRExporterParameter;");
            pw.println("import net.sf.jasperreports.engine.JasperFillManager;");
            pw.println("import net.sf.jasperreports.engine.JasperPrint;");
            pw.println("import net.sf.jasperreports.engine.JasperCompileManager;");
            pw.println("import net.sf.jasperreports.engine.JasperReport;");

            if (formBean.isChkPdf())
            {
                pw.println("import net.sf.jasperreports.engine.export.JRPdfExporter;");
            }
            if (formBean.isChkXls())
            {
                pw.println("import net.sf.jasperreports.engine.export.JRXlsExporter;");
                pw.println("import net.sf.jasperreports.engine.export.JRXlsExporterParameter;");
            }
        }
        else if (CmnFile)
        {
            pw.println("import java.io.File;");
        }
        pw.println();

        pw.println("public class " + moduleName + "Controller extends MultiActionController");
        pw.println("{");
        pw.println();
        pw.println("    private final " + moduleName + "Service service = new " + moduleName + "Service();");

        if (CmnFile)
        {
            pw.println("        private final String FILEBOX_PATH = finpack.FinPack.getProperty(\"filebox_path\");");
        }

        pw.println();
        pw.println("    public ModelAndView getMenu(final HttpServletRequest request, final HttpServletResponse response)");
        pw.println("    {");
        pw.println("        ModelAndView mav;");
        pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/main\");");
        pw.println("        String finlib_path;");
        pw.println("        finlib_path = finpack.FinPack.getProperty(\"finlib_path\");");
        pw.println("        mav.addObject(\"finlib_path\", finlib_path);");
//        if (formBean.isChkHeader())
//        {
//            pw.println("        Calendar c = Calendar.getInstance();");
//            pw.println("        String mydate = getMonth(c.get(Calendar.MONTH)) + \" \"");
//            pw.println("            + c.get(Calendar.DAY_OF_MONTH) + \",\"");
//            pw.println("            + c.get(Calendar.YEAR) + \" \"");
//            pw.println("            + c.get(Calendar.HOUR_OF_DAY) + \":\"");
//            pw.println("            + c.get(Calendar.MINUTE) + \":\"");
//            pw.println("            + c.get(Calendar.SECOND);");
//            pw.println("        mav.addObject(\"mydate\", mydate);");
//        }        
        pw.println("        return mav;");
        pw.println("    }");
        pw.println();

        if (formBean.isChkHeader())
        {
            pw.println("    public String getMonth(int i)");
            pw.println("    {");
            pw.println("        String[] montharray = new String[]");
            pw.println("        {");
            pw.println("            \"January\", \"February\", \"March\", \"April\", \"May\", \"June\", \"July\", \"August\", \"September\", \"October\", \"November\", \"December\"");
            pw.println("        };");
            pw.println("        return montharray[i];");
            pw.println("    }");
            pw.println();
        }

        pw.println("    public ModelAndView getMasterGrid(final HttpServletRequest req, final HttpServletResponse res, final " + moduleName + "FormBean formBean)");
        pw.println("    {");
        pw.println("        ModelAndView mav;");
        pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/output\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            mav.addObject(\"jsonmaster\", service.getGridData(formBean));");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("            mav.addObject(\"error\", e.getMessage());");
        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
        pw.println("        }");
        pw.println("        return mav;");
        pw.println("    }");
        pw.println();

        if (formBean.isChkAdd())
        {
            pw.println("    public ModelAndView addLoader(final HttpServletRequest req, final HttpServletResponse res)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/add\");");

            String[] mstAddControls;
            mstAddControls = formBean.getHdnAddControl();
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            String[] addOnchange;
            addOnchange = formBean.getHdncmbAddOnchange();
            int addLen;
            addLen = mstAddControls.length;
            boolean flagDatePkr = true;
            boolean flagCombo = false;
            for (int i = 0; i < addLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(QUERY) || addRbtnQuery[i].equals(WEBSERVICE) || addRbtnQuery[i].equals(COMMONCMB)))
                {
                    flagCombo = true;
                }
            }
            if (flagCombo)
            {
                pw.println("        try");
                pw.println("        {");
            }
            pw.println("            mav.addObject(\"masterTask\", \"add\");");
            for (int i = 0; i < addLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(QUERY) || addRbtnQuery[i].equals(WEBSERVICE)))
                {
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        if ("".equals(addOnchange[i]) || "-1".equals(addOnchange[i]))
                        {
                            pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                        }
                    }
                    else
                    {
                        if ("".equals(addOnchange[i]) || "-1".equals(addOnchange[i]))
                        {
                            pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                        }
                    }
                }
                else if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(COMMONCMB)))
                {
                    String str = formBean.getHdncmbAddCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbAddCommonQuery()[i].substring(1));
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                    }
                    else
                    {
                        pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                    }
                }
                else if (mstAddControls[i].equals(DTPKR))
                {
                    if (flagDatePkr)
                    {
                        pw.println("            DateFormat dateFormat;");
                        pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                        pw.println("            Date date;");
                        pw.println("            date = new Date();");
                        pw.println("            String dateString;");
                        pw.println("            dateString = dateFormat.format(date);");
                        flagDatePkr = false;
                    }
                    pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                }
            }
            if (flagCombo)
            {
                pw.println("        }");
                pw.println("        catch (Exception e)");
                pw.println("        {");
                pw.println("            mav.addObject(\"error\", e.getMessage());");
                pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                pw.println("        }");
            }
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit())
        {
            pw.println("    public ModelAndView editLoader(final HttpServletRequest req, final HttpServletResponse res)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/edit\");");

            if (formBean.getHdnEditField() != null)
            {
                String[] allEditField;
                allEditField = formBean.getHdnEditField();
                String[] allEditControl;
                allEditControl = formBean.getHdnEditControl();
                String[] editRbtnQuery;
                editRbtnQuery = formBean.getHdnrdoEditDataSrc();
                String[] editOnchange;
                editOnchange = formBean.getHdncmbEditOnchange();
                int editLen;
                editLen = allEditField.length;
                boolean flagDatePkr = true;
                boolean flagCombo = false;
                for (int i = 0; i < editLen; i++)
                {
                    if ((allEditControl[i].equals(COMBO) || allEditControl[i].equals(TXTLIKECOMBO)) && (editRbtnQuery[i].equals(QUERY) || editRbtnQuery[i].equals(WEBSERVICE) || editRbtnQuery[i].equals(COMMONCMB)))
                    {
                        flagCombo = true;
                    }
                }
                if (flagCombo)
                {
                    pw.println("        try");
                    pw.println("        {");
                }
                for (int i = 0; i < editLen; i++)
                {
                    if ((allEditControl[i].equals(COMBO) || allEditControl[i].equals(TXTLIKECOMBO)) && (editRbtnQuery[i].equals(QUERY) || editRbtnQuery[i].equals(WEBSERVICE)))
                    {
                        if ("None".equals(allEditField[i]))
                        {
                            String id = formBean.getHdntxtEditId()[i];
                            if ("".equals(editOnchange[i]) || "-1".equals(editOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getEdit" + Character.toUpperCase(allEditField[i].charAt(0)) + allEditField[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                        else
                        {
                            if ("".equals(editOnchange[i]) || "-1".equals(editOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "s\", service.getEdit" + Character.toUpperCase(allEditField[i].charAt(0)) + allEditField[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                    }
                    else if ((allEditControl[i].equals(COMBO) || allEditControl[i].equals(TXTLIKECOMBO)) && (editRbtnQuery[i].equals(COMMONCMB)))
                    {
                        String str = formBean.getHdncmbEditCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbEditCommonQuery()[i].substring(1));
                        if ("None".equals(allEditField[i]))
                        {
                            String id = formBean.getHdntxtEditId()[i];
                            pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                        }
                        else
                        {
                            pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                        }
                    }
                    else if (allEditControl[i].equals(DTPKR))
                    {
                        if (flagDatePkr)
                        {
                            pw.println("            DateFormat dateFormat;");
                            pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                            pw.println("            Date date;");
                            pw.println("            date = new Date();");
                            pw.println("            String dateString;");
                            pw.println("            dateString = dateFormat.format(date);");
                            flagDatePkr = false;
                        }
                        pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                    }
                }
                if (flagCombo)
                {
                    pw.println("        }");
                    pw.println("        catch (Exception e)");
                    pw.println("        {");
                    pw.println("            mav.addObject(\"error\", e.getMessage());");
                    pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                    pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                    pw.println("        }");
                }
            }
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("    public ModelAndView deleteLoader(final HttpServletRequest req, final HttpServletResponse res)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/delete\");");

            if (formBean.getHdnDeleteField() != null)
            {
                String[] allDeleteField;
                allDeleteField = formBean.getHdnDeleteField();
                String[] allDeleteControl;
                allDeleteControl = formBean.getHdnDeleteControl();
                String[] delRbtnQuery;
                delRbtnQuery = formBean.getHdnrdoDeleteDataSrc();
                String[] delOnchange;
                delOnchange = formBean.getHdncmbDeleteOnchange();
                int delLen;
                delLen = allDeleteField.length;
                boolean flagDatePkr = true;
                boolean flagCombo = false;
                for (int i = 0; i < delLen; i++)
                {
                    if ((allDeleteControl[i].equals(COMBO) || allDeleteControl[i].equals(TXTLIKECOMBO)) && (delRbtnQuery[i].equals(QUERY) || delRbtnQuery[i].equals(WEBSERVICE) || delRbtnQuery[i].equals(COMMONCMB)))
                    {
                        flagCombo = true;
                    }
                }
                if (flagCombo)
                {
                    pw.println("        try");
                    pw.println("        {");
                }
                for (int i = 0; i < delLen; i++)
                {
                    if ((allDeleteControl[i].equals(COMBO) || allDeleteControl[i].equals(TXTLIKECOMBO)) && (delRbtnQuery[i].equals(QUERY) || delRbtnQuery[i].equals(WEBSERVICE)))
                    {
                        if ("None".equals(allDeleteField[i]))
                        {
                            String id = formBean.getHdntxtDeleteId()[i];
                            if ("".equals(delOnchange[i]) || "-1".equals(delOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getDel" + Character.toUpperCase(allDeleteField[i].charAt(0)) + allDeleteField[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                        else
                        {
                            if ("".equals(delOnchange[i]) || "-1".equals(delOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + "s\", service.getDel" + Character.toUpperCase(allDeleteField[i].charAt(0)) + allDeleteField[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                    }
                    else if ((allDeleteControl[i].equals(COMBO) || allDeleteControl[i].equals(TXTLIKECOMBO)) && (delRbtnQuery[i].equals(COMMONCMB)))
                    {
                        String str = formBean.getHdncmbDeleteCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbDeleteCommonQuery()[i].substring(1));
                        if ("None".equals(allDeleteField[i]))
                        {
                            String id = formBean.getHdntxtDeleteId()[i];
                            pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                        }
                        else
                        {
                            pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                        }
                    }
                    else if (allDeleteControl[i].equals(DTPKR))
                    {
                        if (flagDatePkr)
                        {
                            pw.println("            DateFormat dateFormat;");
                            pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                            pw.println("            Date date;");
                            pw.println("            date = new Date();");
                            pw.println("            String dateString;");
                            pw.println("            dateString = dateFormat.format(date);");
                            flagDatePkr = false;
                        }
                        pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                    }
                }
                if (flagCombo)
                {
                    pw.println("        }");
                    pw.println("        catch (Exception e)");
                    pw.println("        {");
                    pw.println("            mav.addObject(\"error\", e.getMessage());");
                    pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                    pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                    pw.println("        }");
                }
            }
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkView())
        {
            pw.println("    public ModelAndView viewLoader(final HttpServletRequest req, final HttpServletResponse res)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/view\");");

            if (formBean.getHdnViewField() != null)
            {
                String[] allViewField;
                allViewField = formBean.getHdnViewField();
                String[] allViewControl;
                allViewControl = formBean.getHdnViewControl();
                String[] viewRbtnQuery;
                viewRbtnQuery = formBean.getHdnrdoViewDataSrc();
                String[] viewOnchange;
                viewOnchange = formBean.getHdncmbViewOnchange();
                int viewLen;
                viewLen = allViewField.length;
                boolean flagDatePkr = true;
                boolean flagCombo = false;
                for (int i = 0; i < viewLen; i++)
                {
                    if ((allViewControl[i].equals(COMBO) || allViewControl[i].equals(TXTLIKECOMBO)) && (viewRbtnQuery[i].equals(QUERY) || viewRbtnQuery[i].equals(WEBSERVICE) || viewRbtnQuery[i].equals(COMMONCMB)))
                    {
                        flagCombo = true;
                    }
                }
                if (flagCombo)
                {
                    pw.println("        try");
                    pw.println("        {");
                }
                for (int i = 0; i < viewLen; i++)
                {
                    if ((allViewControl[i].equals(COMBO) || allViewControl[i].equals(TXTLIKECOMBO)) && (viewRbtnQuery[i].equals(QUERY) || viewRbtnQuery[i].equals(WEBSERVICE)))
                    {
                        if ("None".equals(allViewField[i]))
                        {
                            String id = formBean.getHdntxtViewId()[i];
                            if ("".equals(viewOnchange[i]) || "-1".equals(viewOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allViewField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getView" + Character.toUpperCase(allViewField[i].charAt(0)) + allViewField[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                        else
                        {
                            if ("".equals(viewOnchange[i]) || "-1".equals(viewOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allViewField[i].toLowerCase(Locale.getDefault()) + "s\", service.getView" + Character.toUpperCase(allViewField[i].charAt(0)) + allViewField[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                    }
                    else if ((allViewControl[i].equals(COMBO) || allViewControl[i].equals(TXTLIKECOMBO)) && (viewRbtnQuery[i].equals(COMMONCMB)))
                    {
                        String str = formBean.getHdncmbViewCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbViewCommonQuery()[i].substring(1));
                        if ("None".equals(allViewField[i]))
                        {
                            String id = formBean.getHdntxtViewId()[i];
                            pw.println("            mav.addObject(\"" + allViewField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                        }
                        else
                        {
                            pw.println("            mav.addObject(\"" + allViewField[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                        }
                    }
                    else if (allViewControl[i].equals(DTPKR))
                    {
                        if (flagDatePkr)
                        {
                            pw.println("            DateFormat dateFormat;");
                            pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                            pw.println("            Date date;");
                            pw.println("            date = new Date();");
                            pw.println("            String dateString;");
                            pw.println("            dateString = dateFormat.format(date);");
                            flagDatePkr = false;
                        }
                        pw.println("            mav.addObject(\"" + allViewField[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                    }
                }
                if (flagCombo)
                {
                    pw.println("        }");
                    pw.println("        catch (Exception e)");
                    pw.println("        {");
                    pw.println("            mav.addObject(\"error\", e.getMessage());");
                    pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                    pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                    pw.println("        }");
                }
            }
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkAdd())
        {
            String[] mstAddControls;
            mstAddControls = formBean.getHdnAddControl();
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            String[] addOnchange;
            addOnchange = formBean.getHdncmbAddOnchange();
            int addLen;
            addLen = mstAddControls.length;
            boolean flag = true;

            for (int i = 0; i < addLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (!"".equals(addOnchange[i]) && !"-1".equals(addOnchange[i])))
                {
                    pw.println("    public ModelAndView fill" + formBean.getHdntxtAddId()[i] + "onchange" + addOnchange[i] + "(final HttpServletRequest req, final HttpServletResponse res)");
                    pw.println("    {");
                    pw.println("        ModelAndView mav;");
                    pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/add\");");
                    pw.println("        try");
                    pw.println("        {");
                    pw.println("            mav.addObject(\"subproc\", \"fill" + formBean.getHdntxtAddId()[i] + "\");");
                    pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + "(req.getParameter(\"" + addOnchange[i] + "\")));");
                    pw.println("        }");
                    pw.println("        catch (Exception e)");
                    pw.println("        {");
                    pw.println("            mav.addObject(\"error\", e.getMessage());");
                    pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                    pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                    pw.println("        }");
                    pw.println("        return mav;");
                    pw.println("    }");
                    pw.println();
                }
            }

            if (formBean.isChkEdit() && formBean.getHdnEditField() != null)
            {
                String[] allEditField;
                allEditField = formBean.getHdnEditField();
                String[] editControls;
                editControls = formBean.getHdnEditControl();
                String[] editOnchange;
                editOnchange = formBean.getHdncmbEditOnchange();
                int editLen;
                editLen = editControls.length;

                for (int i = 0; i < editLen; i++)
                {
                    if ((editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO)) && (!"".equals(editOnchange[i]) && !"-1".equals(editOnchange[i])))
                    {
                        pw.println("    public ModelAndView fill" + formBean.getHdntxtEditId()[i] + "onchange" + editOnchange[i] + "(final HttpServletRequest req, final HttpServletResponse res)");
                        pw.println("    {");
                        pw.println("        ModelAndView mav;");
                        pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/edit\");");
                        pw.println("        try");
                        pw.println("        {");
                        pw.println("            mav.addObject(\"subproc\", \"fill" + formBean.getHdntxtEditId()[i] + "\");");
                        pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "s\", service.getEdit" + Character.toUpperCase(allEditField[i].charAt(0)) + allEditField[i].substring(1).toLowerCase(Locale.getDefault()) + "(req.getParameter(\"" + editOnchange[i] + "\")));");
                        pw.println("        }");
                        pw.println("        catch (Exception e)");
                        pw.println("        {");
                        pw.println("            mav.addObject(\"error\", e.getMessage());");
                        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                        pw.println("        }");
                        pw.println("        return mav;");
                        pw.println("    }");
                        pw.println();
                    }
                }
            }

            if (formBean.isChkDelete() && formBean.getHdnDeleteField() != null)
            {
                String[] allDelField;
                allDelField = formBean.getHdnDeleteField();
                String[] delControls;
                delControls = formBean.getHdnDeleteControl();
                String[] delOnchange;
                delOnchange = formBean.getHdncmbDeleteOnchange();
                int delLen;
                delLen = delControls.length;

                for (int i = 0; i < delLen; i++)
                {
                    if ((delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO)) && (!"".equals(delOnchange[i]) && !"-1".equals(delOnchange[i])))
                    {
                        pw.println("    public ModelAndView fill" + formBean.getHdntxtDeleteId()[i] + "onchange" + delOnchange[i] + "(final HttpServletRequest req, final HttpServletResponse res)");
                        pw.println("    {");
                        pw.println("        ModelAndView mav;");
                        pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/delete\");");
                        pw.println("        try");
                        pw.println("        {");
                        pw.println("            mav.addObject(\"subproc\", \"fill" + formBean.getHdntxtDeleteId()[i] + "\");");
                        pw.println("            mav.addObject(\"" + allDelField[i].toLowerCase(Locale.getDefault()) + "s\", service.getDel" + Character.toUpperCase(allDelField[i].charAt(0)) + allDelField[i].substring(1).toLowerCase(Locale.getDefault()) + "(req.getParameter(\"" + delOnchange[i] + "\")));");
                        pw.println("        }");
                        pw.println("        catch (Exception e)");
                        pw.println("        {");
                        pw.println("            mav.addObject(\"error\", e.getMessage());");
                        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                        pw.println("        }");
                        pw.println("        return mav;");
                        pw.println("    }");
                        pw.println();
                    }
                }
            }

            if (formBean.isChkView() && formBean.getHdnViewField() != null)
            {
                String[] allViewField;
                allViewField = formBean.getHdnViewField();
                String[] viewControls;
                viewControls = formBean.getHdnViewControl();
                String[] viewOnchange;
                viewOnchange = formBean.getHdncmbViewOnchange();
                int editLen;
                editLen = viewControls.length;

                for (int i = 0; i < editLen; i++)
                {
                    if ((viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO)) && (!"".equals(viewOnchange[i]) && !"-1".equals(viewOnchange[i])))
                    {
                        pw.println("    public ModelAndView fill" + formBean.getHdntxtViewId()[i] + "onchange" + viewOnchange[i] + "(final HttpServletRequest req, final HttpServletResponse res)");
                        pw.println("    {");
                        pw.println("        ModelAndView mav;");
                        pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/filter\");");
                        pw.println("        try");
                        pw.println("        {");
                        pw.println("            mav.addObject(\"subproc\", \"fill" + formBean.getHdntxtViewId()[i] + "\");");
                        pw.println("            mav.addObject(\"" + allViewField[i].toLowerCase(Locale.getDefault()) + "s\", service.getView" + Character.toUpperCase(allViewField[i].charAt(0)) + allViewField[i].substring(1).toLowerCase(Locale.getDefault()) + "(req.getParameter(\"" + viewOnchange[i] + "\")));");
                        pw.println("        }");
                        pw.println("        catch (Exception e)");
                        pw.println("        {");
                        pw.println("            mav.addObject(\"error\", e.getMessage());");
                        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
                        pw.println("        }");
                        pw.println("        return mav;");
                        pw.println("    }");
                        pw.println();
                    }
                }
            }

            pw.println("    public ModelAndView insertData(final HttpServletRequest req, final HttpServletResponse res, final " + moduleName + "FormBean formBean)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/add\");");
            pw.println("        try");
            pw.println("        {");
            pw.println("            mav.addObject(\"masterTask\", \"add\");");
            pw.println("            if (service.insertMaster(formBean) > 0)");
            pw.println("            {");
            pw.println("                mav.addObject(\"DBoperation\", \"success\");");
            pw.println();

            for (int i = 0; i < addLen; i++)
            {
                if (mstAddControls[i].equals(FILEBOX))
                {
                    String eleName = formBean.getHdntxtAddEleName()[i];
                    String mthName = eleName.substring(0, 1).toUpperCase() + eleName.substring(1);
                    pw.println("                String " + eleName + "_files[] = formBean.get" + mthName + "();");
                    pw.println("                int " + eleName + "_files_length = " + eleName + "_files.length;");
                    pw.println("                for (int i = 0; i < " + eleName + "_files_length; i++)");
                    pw.println("                {");
                    pw.println("                    File f1 = new File(FILEBOX_PATH + " + eleName + "_files[i]);");
                    pw.println("                    //File f2 = new File(finpack.FinPack.getProperty(\"tomcat1_path\") + \"/webapps/projectname/upload/\" + " + eleName + "_files[i]);");
                    pw.println("                    //f1.renameTo(f2);");
                    pw.println("                }");
                }
            }
            pw.println("            }");
            pw.println("            else");
            pw.println("            {");
            pw.println("                mav.addObject(\"DBoperation\", \"failure\");");
            pw.println("            }");

            for (int i = 0; i < addLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(QUERY) || addRbtnQuery[i].equals(WEBSERVICE)))
                {
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        if ("".equals(addOnchange[i]) || "-1".equals(addOnchange[i]))
                        {
                            pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                        }
                    }
                    else
                    {
                        if ("".equals(addOnchange[i]) || "-1".equals(addOnchange[i]))
                        {
                            pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                        }
                    }
                }
                else if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(COMMONCMB)))
                {
                    String str = formBean.getHdncmbAddCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbAddCommonQuery()[i].substring(1));
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                    }
                    else
                    {
                        pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                    }
                }
                else if (mstAddControls[i].equals(DTPKR))
                {
                    if (flag)
                    {
                        pw.println("            DateFormat dateFormat;");
                        pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                        pw.println("            Date date;");
                        pw.println("            date = new Date();");
                        pw.println("            String dateString;");
                        pw.println("            dateString = dateFormat.format(date);");
                        flag = false;
                    }
                    pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                }
            }
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            mav.addObject(\"error\", e.getMessage());");
            pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
            pw.println("        }");
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("    public ModelAndView getRecByPrimeKey(final HttpServletRequest req, final HttpServletResponse res, final " + moduleName + "FormBean formBean)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/add\");");
            pw.println("        try");
            pw.println("        {");

            String[] mstAEDNames;
            mstAEDNames = formBean.getHdntxtAddName();

            boolean prime = false;
            int j;
            for (j = 0; j < mstAEDLen; j++)
            {
                if (mstAEDFields[j].equals(formBean.getCmbMasterTablePrimKey()))
                {
                    prime = true;
                    String methodName;
                    methodName = mstAEDNames[j].substring(0, 1).toUpperCase() + mstAEDNames[j].substring(1);
                    pw.println("            formBean.set" + methodName + "(req.getParameter(\"primekey\"));");
                    break;
                }
            }
            if (j == mstAEDLen)
            {
                String methodName;
                methodName = formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase() + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault()) + "PrimeKey";
                pw.println("            formBean.set" + methodName + "(req.getParameter(\"primekey\"));");
            }
            pw.println("            SqlRowSet srs;");
            pw.println("            srs = service.getRecByPrimeKey(formBean);");
            pw.println("            mav.addObject(\"masterTask\", formBean.getMasterTask());");
            pw.println("            if (srs.next())");
            pw.println("            {");

            boolean dtPkr = false;

            for (j = 0; j < mstAEDLen; j++)
            {
                for (int i = 0; i < allColLen; i++)
                {
                    if (mstAEDFields[j].equals(allMstColumns[i]))
                    {
                        if (allMstDataTypes[i].equals("Date"))
                        {
                            if (dtPkr)
                            {
                                pw.println("                if (srs.getString(\"" + allMstColumns[i] + "\") != null && !(srs.getString(\"" + allMstColumns[i] + "\").equals(\"\")))");
                                pw.println("                {");
                                pw.println("                    date = srcDateFormat.parse(srs.getString(\"" + allMstColumns[i] + "\"));");
                                pw.println("                    mav.addObject(\"" + allMstColumns[i].toLowerCase(Locale.getDefault()) + "\", destDateFormat.format(date));");
                                pw.println("                }");
                            }
                            else
                            {
                                pw.println("                DateFormat destDateFormat;");
                                pw.println("                destDateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                                pw.println("                DateFormat srcDateFormat;");
                                pw.println("                srcDateFormat = new SimpleDateFormat(\"yyyy-MM-dd\");");
                                pw.println("                Date date;");
                                pw.println("                if (srs.getString(\"" + allMstColumns[i] + "\") != null && !(srs.getString(\"" + allMstColumns[i] + "\").equals(\"\")))");
                                pw.println("                {");
                                pw.println("                    date = srcDateFormat.parse(srs.getString(\"" + allMstColumns[i] + "\"));");
                                pw.println("                    mav.addObject(\"" + allMstColumns[i].toLowerCase(Locale.getDefault()) + "\", destDateFormat.format(date));");
                                pw.println("                }");
                                dtPkr = true;
                            }
                        }
                        else
                        {
                            pw.println("                mav.addObject(\"" + allMstColumns[i].toLowerCase(Locale.getDefault()) + "\", srs.getString(\"" + allMstColumns[i] + "\"));");
                        }
                    }
                }
            }
            if (!prime)
            {
                pw.println("                mav.addObject(\"" + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "\", srs.getString(\"" + formBean.getCmbMasterTablePrimKey() + "\"));");
            }

            pw.println("            }");
            String[] mstAEDControls;
            mstAEDControls = formBean.getHdnAddControl();
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            String[] addOnchange;
            addOnchange = formBean.getHdncmbAddOnchange();
            for (int i = 0; i < mstAEDLen; i++)
            {
                if ((mstAEDControls[i].equals(COMBO) || mstAEDControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(QUERY) || addRbtnQuery[i].equals(WEBSERVICE)))
                {
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        if ("".equals(addOnchange[i]) || "-1".equals(addOnchange[i]))
                        {
                            pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                        }
                    }
                    else
                    {
                        if ("".equals(addOnchange[i]) || "-1".equals(addOnchange[i]))
                        {
                            pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                        }
                    }
                }
                else if ((mstAEDControls[i].equals(COMBO) || mstAEDControls[i].equals(TXTLIKECOMBO)) && (addRbtnQuery[i].equals(COMMONCMB)))
                {
                    String str = formBean.getHdncmbAddCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbAddCommonQuery()[i].substring(1));
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                    }
                    else
                    {
                        pw.println("            mav.addObject(\"" + mstAEDFields[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                    }
                }
            }
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            mav.addObject(\"error\", e.getMessage());");
            pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
            pw.println("        }");
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit())
        {
            pw.println("    public ModelAndView editData(final HttpServletRequest req, final HttpServletResponse res, final " + moduleName + "FormBean formBean)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/edit\");");
            pw.println("        try");
            pw.println("        {");
            pw.println("            if (service.updateMaster(formBean) > 0)");
            pw.println("            {");
            pw.println("                mav.addObject(\"DBoperation\", \"success\");");
            pw.println("            }");
            pw.println("            else");
            pw.println("            {");
            pw.println("                mav.addObject(\"DBoperation\", \"failure\");");
            pw.println("            }");

            if (formBean.getHdnEditField() != null)
            {
                String[] allEditField;
                allEditField = formBean.getHdnEditField();
                String[] allEditControl;
                allEditControl = formBean.getHdnEditControl();
                String[] editRbtnQuery;
                editRbtnQuery = formBean.getHdnrdoEditDataSrc();
                String[] editOnchange;
                editOnchange = formBean.getHdncmbEditOnchange();
                int editLen;
                editLen = allEditField.length;
                boolean flag = true;
                for (int i = 0; i < editLen; i++)
                {
                    if ((allEditControl[i].equals(COMBO) || allEditControl[i].equals(TXTLIKECOMBO)) && (editRbtnQuery[i].equals(QUERY) || editRbtnQuery[i].equals(WEBSERVICE)))
                    {
                        if ("None".equals(allEditField[i]))
                        {
                            String id = formBean.getHdntxtEditId()[i];
                            if ("".equals(editOnchange[i]) || "-1".equals(editOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getEdit" + Character.toUpperCase(allEditField[i].charAt(0)) + allEditField[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                        else
                        {
                            if ("".equals(editOnchange[i]) || "-1".equals(editOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "s\", service.getEdit" + Character.toUpperCase(allEditField[i].charAt(0)) + allEditField[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                    }
                    else if ((allEditControl[i].equals(COMBO) || allEditControl[i].equals(TXTLIKECOMBO)) && (editRbtnQuery[i].equals(COMMONCMB)))
                    {
                        String str = formBean.getHdncmbEditCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbEditCommonQuery()[i].substring(1));
                        if ("None".equals(allEditField[i]))
                        {
                            String id = formBean.getHdntxtEditId()[i];
                            pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                        }
                        else
                        {
                            pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                        }
                    }
                    else if (allEditControl[i].equals(DTPKR))
                    {
                        if (flag)
                        {
                            pw.println("            DateFormat dateFormat;");
                            pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                            pw.println("            Date date;");
                            pw.println("            date = new Date();");
                            pw.println("            String dateString;");
                            pw.println("            dateString = dateFormat.format(date);");
                            flag = false;
                        }
                        pw.println("            mav.addObject(\"" + allEditField[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                    }
                }
            }
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            mav.addObject(\"error\", e.getMessage());");
            pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
            pw.println("        }");
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("    public ModelAndView deleteData(final HttpServletRequest req, final HttpServletResponse res, final " + moduleName + "FormBean formBean)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/delete\");");
            pw.println("        try");
            pw.println("        {");
            pw.println("            if (service.deleteMaster(formBean) > 0)");
            pw.println("            {");
            pw.println("                mav.addObject(\"DBoperation\", \"success\");");
            pw.println("            }");
            pw.println("            else");
            pw.println("            {");
            pw.println("                mav.addObject(\"DBoperation\", \"failure\");");
            pw.println("            }");

            if (formBean.getHdnDeleteField() != null)
            {
                String[] allDeleteField;
                allDeleteField = formBean.getHdnDeleteField();
                String[] allDeleteControl;
                allDeleteControl = formBean.getHdnDeleteControl();
                String[] delRbtnQuery;
                delRbtnQuery = formBean.getHdnrdoDeleteDataSrc();
                String[] delOnchange;
                delOnchange = formBean.getHdncmbDeleteOnchange();
                int delLen;
                delLen = allDeleteField.length;
                boolean flag = true;
                for (int i = 0; i < delLen; i++)
                {
                    if ((allDeleteControl[i].equals(COMBO) || allDeleteControl[i].equals(TXTLIKECOMBO)) && (delRbtnQuery[i].equals(QUERY) || delRbtnQuery[i].equals(WEBSERVICE)))
                    {
                        if ("None".equals(allDeleteField[i]))
                        {
                            String id = formBean.getHdntxtDeleteId()[i];
                            if ("".equals(delOnchange[i]) || "-1".equals(delOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.getDel" + Character.toUpperCase(allDeleteField[i].charAt(0)) + allDeleteField[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                        else
                        {
                            if ("".equals(delOnchange[i]) || "-1".equals(delOnchange[i]))
                            {
                                pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + "s\", service.getDel" + Character.toUpperCase(allDeleteField[i].charAt(0)) + allDeleteField[i].substring(1).toLowerCase(Locale.getDefault()) + "());");
                            }
                        }
                    }
                    else if ((allDeleteControl[i].equals(COMBO) || allDeleteControl[i].equals(TXTLIKECOMBO)) && (delRbtnQuery[i].equals(COMMONCMB)))
                    {
                        String str = formBean.getHdncmbDeleteCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbDeleteCommonQuery()[i].substring(1));
                        if ("None".equals(allDeleteField[i]))
                        {
                            String id = formBean.getHdntxtDeleteId()[i];
                            pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + id + "s\", service.get" + str + "());");
                        }
                        else
                        {
                            pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + "s\", service.get" + str + "());");
                        }
                    }
                    else if (allDeleteControl[i].equals(DTPKR))
                    {
                        if (flag)
                        {
                            pw.println("            DateFormat dateFormat;");
                            pw.println("            dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\");");
                            pw.println("            Date date;");
                            pw.println("            date = new Date();");
                            pw.println("            String dateString;");
                            pw.println("            dateString = dateFormat.format(date);");
                            flag = false;
                        }
                        pw.println("            mav.addObject(\"" + allDeleteField[i].toLowerCase(Locale.getDefault()) + "\", dateString);");
                    }
                }
            }
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            mav.addObject(\"error\", e.getMessage());");
            pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
            pw.println("        }");
            pw.println("        return mav;");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkView() && (formBean.isChkPdf() || formBean.isChkXls()))
        {
            MasterJrxmlGenerator jrxmlGen;
            jrxmlGen = new MasterJrxmlGenerator();
            jrxmlGen.generateMasterJrxml(formBean);
            pw.println("    public ModelAndView getReportFile(final HttpServletRequest request, final HttpServletResponse response, final " + moduleName + "FormBean formBean)");
            pw.println("    {");
            pw.println("        ModelAndView mav;");
            pw.println("        mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/view\");");
            pw.println("        try");
            pw.println("        {");
            pw.println("            String fileName, tomcatPath;");
            pw.println("            tomcatPath = finpack.FinPack.getProperty(\"tomcat1_path\");");
            pw.println("            fileName = tomcatPath + \"/webapps/" + projectName + "/WEB-INF/jrxml/" + moduleName.toLowerCase(Locale.getDefault()) + ".jrxml\";");
            pw.println("            JasperReport jasperReport;");
            pw.println("            jasperReport = JasperCompileManager.compileReport(fileName);");
            pw.println("            StringBuilder outFileName;");
            pw.println("            outFileName = new StringBuilder();");
            if (formBean.isChkPdf())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\"))");
                pw.println("            {");
                pw.println("                outFileName.append(tomcatPath).append(\"/webapps/" + projectName + "/\").append(formBean.getReportName()).append(\".pdf\");");
                pw.println("            }");
            }
            if (formBean.isChkXls())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                pw.println("            {");
                pw.println("                outFileName.append(tomcatPath).append(\"/webapps/" + projectName + "/\").append(formBean.getReportName()).append(\".xls\");");
                pw.println("            }");
            }

            pw.println("            Map parameters;");
            pw.println("            parameters = new HashMap();");
            pw.println("            parameters.put(\"Name\", \"MasterReport\");");
            pw.println("            JRBeanCollectionDataSource dataSource;");
            pw.println("            dataSource = new JRBeanCollectionDataSource(service.getJasperData(formBean));");
            pw.println("            JasperPrint print = null;");
            pw.println("            print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);");
            pw.println("            JRExporter exporter = null;");
            if (formBean.isChkPdf())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\"))");
                pw.println("            {");
                pw.println("                exporter = new JRPdfExporter();");
                pw.println("            }");
            }
            if (formBean.isChkXls())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                pw.println("            {");
                pw.println("                exporter = new JRXlsExporter();");
                pw.println("            }");
            }

            if (formBean.isChkPdf() || formBean.isChkXls())
            {
                pw.println("            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName.toString());");
                pw.println("            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);");
                if (formBean.isChkXls())
                {
                    pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                    pw.println("            {");
                    pw.println("                exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);");
                    pw.println("                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);");
                    pw.println("                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);");
                    pw.println("                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);");
                    pw.println("                exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);");
                    pw.println("            }");
                }
                pw.println("            exporter.exportReport();");
            }

            pw.println("            ServletOutputStream out1;");
            pw.println("            out1 = response.getOutputStream();");
            if (formBean.isChkPdf())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\"))");
                pw.println("            {");
                pw.println("                response.setContentType(\"application/pdf\");");
                pw.println("            }");
            }
            if (formBean.isChkXls())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                pw.println("            {");
                pw.println("                response.setContentType(\"application/vnd.ms-excel\");");
                pw.println("            }");
            }
            pw.println("            response.setHeader(\"Content-disposition\", \"attachment; filename=\\\"\" + formBean.getReportName() + \".\" + formBean.getRdoRptFormate().toLowerCase(Locale.getDefault()) + \"\\\"\");");
            pw.println("            BufferedInputStream bis = null;");
            pw.println("            BufferedOutputStream bos = null;");
            pw.println("            try");
            pw.println("            {");
            pw.println("                bis = new BufferedInputStream(new FileInputStream(outFileName.toString()));");
            pw.println("                bos = new BufferedOutputStream(out1);");
            pw.println("                byte[] buff;");
            pw.println("                buff = new byte[2048];");
            pw.println("                int bytesRead;");
            pw.println("                while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))");
            pw.println("                {");
            pw.println("                    bos.write(buff, 0, bytesRead);");
            pw.println("                }");
            pw.println("            }");
            pw.println("            catch (Exception e)");
            pw.println("            {");
            pw.println("                finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
            pw.println("                throw new ServletException(e);");
            pw.println("            }");
            pw.println("            finally");
            pw.println("            {");
            pw.println("                try");
            pw.println("                {");
            pw.println("                    if (bis != null)");
            pw.println("                    {");
            pw.println("                        bis.close();");
            pw.println("                    }");
            pw.println("                }");
            pw.println("                catch (Exception e)");
            pw.println("                {");
            pw.println("                    finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
            pw.println("                }");
            pw.println("                try");
            pw.println("                {");
            pw.println("                    if (bos != null)");
            pw.println("                    {");
            pw.println("                        bos.close();");
            pw.println("                    }");
            pw.println("                }");
            pw.println("                catch (Exception e)");
            pw.println("                {");
            pw.println("                    finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
            pw.println("                }");
            pw.println("            }");
            pw.println("            out1.close();");
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            mav.addObject(\"error\", e.getMessage());");
            pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
            pw.println("        }");
            pw.println("        return null;");
            pw.println("    }");
            pw.println();
        }

        pw.println("}");
        pw.close();
    }
}
