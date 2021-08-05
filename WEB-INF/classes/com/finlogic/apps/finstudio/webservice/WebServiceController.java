/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservice;

import com.finlogic.util.FolderZipper;
import com.finlogic.util.Logger;
import com.finlogic.util.webservice.CompileFile;
import com.finlogic.util.webservice.FileService;
import com.finlogic.util.webservice.WSCodeGeneration;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Sonam Patel
 */
public class WebServiceController extends MultiActionController
{

    private final String finlib_path = finpack.FinPack.getProperty("finlib_path");
    private final WebSrvcService service = new WebSrvcService();

    public ModelAndView defaultMethod(final HttpServletRequest request, final HttpServletResponse response)
    {
        return setServiceMenu(request, response);
    }

    public ModelAndView getProject(final ModelAndView mav) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = service.getProjectArray();
        List<String> values;
        values = new ArrayList<String>();
        List<String> projects;
        projects = new ArrayList<String>();
        String tmpstr;
        while (srs.next())
        {
            values.add(srs.getString(3));
            projects.add(srs.getString(2));
        }
        int idx;
        for (int j = 0; j < projects.size(); j++)
        {
            idx = j;
            for (int k = j; k < projects.size(); k++)
            {
                if (projects.get(k).compareTo(projects.get(idx)) < 0)
                {
                    idx = k;
                }
            }
            if (idx != j)
            {
                tmpstr = values.get(j);
                values.set(j, values.get(idx));
                values.set(idx, tmpstr);

                tmpstr = projects.get(j);
                projects.set(j, projects.get(idx));
                projects.set(idx, tmpstr);
            }
        }
        mav.addObject("projects", projects);
        mav.addObject("values", values);
        return mav;
    }

    public ModelAndView setServiceMenu(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.setViewName("webservice/Main");
            mav.addObject("finlib_path", finlib_path);
            mav = getProject(mav);
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView onFinish(final HttpServletRequest request, final HttpServletResponse response, final WebServiceFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("finlib_path", finlib_path);
            mav.setViewName("webservice/Links");

            HttpSession session;
            session = request.getSession(false);
            if (session.getAttribute("ACLEmpCode") == null)
            {
                formBean.setEmpCode("");
            }
            else
            {
                formBean.setEmpCode(session.getAttribute("ACLEmpCode").toString());
            }
            int srno;
            srno = service.insertIntoDataBase(formBean);
            if (srno > 0)
            {
                formBean.setTxtSrno(String.valueOf(srno));
                mav.addObject("SRNO", srno);
                String infcPath;
                infcPath = service.uploadInterface(formBean, srno);

                if (!"".equals(infcPath))
                {
                    //process
                    String fileName;
                    fileName = formBean.getIntrfcName().substring(0, formBean.getIntrfcName().length() - 5);

                    //Copy File Content into SRNO.txt File
                    boolean copyTxt = false;
                    copyTxt = FileService.copyFileContent(WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "/" + fileName + ".java", formBean.getTxtSrno());
                    if (copyTxt)
                    {
                        //Check Manually For File is Interface or not
                        String intfc;
                        intfc = service.isInterface(WebServiceFormBean.getWSLOC() + "/" + srno + "tmp/" + srno + ".txt");

                        if ("".equals(intfc))
                        {
                            //Copy Files
                            String src, dest;
                            src = WebServiceFormBean.getWSLOC() + "/" + srno;
                            dest = WebServiceFormBean.getWSLOC() + "/" + srno + "tmp/";
                            File srcFile, destFile;
                            srcFile = new File(src);
                            destFile = new File(dest);
                            FileService.copyFiles(srcFile, destFile);

                            //Compile File
                            CompileFile cmp;
                            cmp = new CompileFile();
                            Map<String, Set> map;
                            map = cmp.compile(dest);

                            if (map.get("success") != null)
                            {
                                Set set;
                                set = map.get("success");
                                if (set.size() > 0)
                                {
                                    List list;
                                    list = new ArrayList<String>();
                                    Iterator iter;
                                    iter = set.iterator();
                                    while (iter.hasNext())
                                    {
                                        // add into List
                                        Object element;
                                        element = iter.next();
                                        list.add(element);
                                    }
                                    mav.addObject("process", "beanupload");
                                    mav.addObject("beans", list);
                                    mav.setViewName("webservice/FileUpload");
                                }
                                else
                                {
                                    //Generate Code
                                    WSCodeGeneration wsGen;
                                    wsGen = new WSCodeGeneration();
                                    String codeFile;
                                    codeFile = wsGen.generate(formBean);

                                    //Zip Folder
                                    FolderZipper.zipFolder(codeFile, WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + formBean.getTxtSrno() + "WS.zip");

                                    mav.addObject("codePath", formBean.getTxtSrno() + "WS.zip");
                                }
                            }
                            else
                            {
                                mav = getProject(mav);
                                mav.setViewName("webservice/Main");
                                String err;
                                err = map.get("failure").toString();
                                err = err.substring(1, err.length() - 1);
                                mav.addObject("errors", err);

                                //Delete Record From Database
                                service.deleteRecord(formBean.getTxtSrno());

                                //Delete SRNO From 'upload/ws', If Exist
                                service.deleteSRNOfromUpload(formBean.getTxtSrno());
                            }
                        }
                        else
                        {
                            mav = getProject(mav);
                            mav.setViewName("webservice/Main");
                            mav.addObject("errors", intfc);

                            //Delete Record From Database
                            service.deleteRecord(formBean.getTxtSrno());

                            //Delete SRNO From 'upload/ws', If Exist
                            service.deleteSRNOfromUpload(formBean.getTxtSrno());
                        }
                    }
                    else
                    {
                        mav = getProject(mav);
                        mav.setViewName("webservice/Main");
                        mav.addObject("errors", "Error While Copying File Content");

                        //Delete Record From Database
                        service.deleteRecord(formBean.getTxtSrno());

                        //Delete SRNO From 'upload/ws', If Exist
                        service.deleteSRNOfromUpload(formBean.getTxtSrno());
                    }
                }
                else
                {
                    //Delete Record From Database
                    service.deleteRecord(formBean.getTxtSrno());
                    //Delete SRNO From 'upload/ws', If Exist
                    service.deleteSRNOfromUpload(formBean.getTxtSrno());
                }
                //Delete Extra Files
                service.deleteExtraFiles(formBean.getTxtSrno());
            }
            else
            {
                mav = getProject(mav);
                mav.setViewName("webservice/Main");
                mav.addObject("errors", "Problem In Data Insertiopn In Database");
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
            try
            {
                //Delete Record From Database
                service.deleteRecord(formBean.getTxtSrno());
            }
            catch (Exception ex)
            {
                Logger.ErrorLogger(ex);
            }
            try
            {
                //Delete SRNO From 'upload/ws', If Exist
                service.deleteSRNOfromUpload(formBean.getTxtSrno());
            }
            catch (Exception ex)
            {
                Logger.ErrorLogger(ex);
            }
            try
            {
                //Delete Extra Files
                service.deleteExtraFiles(formBean.getTxtSrno());
            }
            catch (Exception ex)
            {
                Logger.ErrorLogger(ex);
            }
        }
        return mav;
    }

    public ModelAndView implWS(final HttpServletRequest request, final HttpServletResponse response, WebServiceFormBean formBean)
    {
        if (request.getAttribute("txtSrno") != null)
        {
            formBean.setTxtSrno(request.getAttribute("txtSrno").toString());
        }

        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("finlib_path", finlib_path);
            mav.setViewName("webservice/Links");
            mav.addObject("SRNO", formBean.getTxtSrno());

            //set FormBean by Fetching Record From Database
            formBean = service.setFormBean(formBean.getTxtSrno());

            //get bean list and set in FormBean
            if (request.getAttribute("beans") != null)
            {
                formBean.setBeans(service.getBeanList(request.getAttribute("beans").toString()));
            }

            //Copy File Content into SRNO.txt File
            boolean copyTxt = false;
            copyTxt = FileService.copyFileContent(WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "/" + formBean.getIntrfcName(), formBean.getTxtSrno());
            if (copyTxt)
            {
                //Copy Files
                String src, dest;
                src = WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno();
                dest = WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/";
                File srcFile, destFile;
                srcFile = new File(src);
                destFile = new File(dest);
                FileService.copyFiles(srcFile, destFile);

                //compile Files
                CompileFile cmp;
                cmp = new CompileFile();
                Map<String, Set> map;
                map = cmp.compile(dest);
                if (map.get("success") != null)
                {
                    Set set;
                    set = map.get("success");
                    if (set.size() > 0)
                    {
                        mav = getProject(mav);
                        mav.setViewName("webservice/Main");
                        mav.addObject("errors", "Too Many Dependencies");

                        //Delete Record From Database
                        service.deleteRecord(formBean.getTxtSrno());

                        //Delete SRNO From 'upload/ws', If Exist
                        service.deleteSRNOfromUpload(formBean.getTxtSrno());
                    }
                    else
                    {
                        //Generate Code
                        WSCodeGeneration wsGen;
                        wsGen = new WSCodeGeneration();
                        String codeFile;
                        codeFile = wsGen.generate(formBean);

                        //Zip Folder
                        FolderZipper.zipFolder(codeFile, WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + formBean.getTxtSrno() + "WS.zip");

                        mav.addObject("codePath", formBean.getTxtSrno() + "WS.zip");
                    }
                }
                else
                {
                    mav = getProject(mav);
                    mav.setViewName("webservice/Main");
                    String err;
                    err = map.get("failure").toString();
                    err = err.substring(1, err.length() - 1);
                    mav.addObject("errors", err);

                    //Delete Record From Database
                    service.deleteRecord(formBean.getTxtSrno());

                    //Delete SRNO From 'upload/ws', If Exist
                    service.deleteSRNOfromUpload(formBean.getTxtSrno());
                }
                //Delete Extra Files
                service.deleteExtraFiles(formBean.getTxtSrno());
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
            try
            {
                //Delete Record From Database
                service.deleteRecord(formBean.getTxtSrno());
            }
            catch (Exception ex)
            {
                Logger.ErrorLogger(ex);
            }
            try
            {
                //Delete SRNO From 'upload/ws', If Exist
                service.deleteSRNOfromUpload(formBean.getTxtSrno());
            }
            catch (Exception ex)
            {
                Logger.ErrorLogger(ex);
            }
            try
            {
                //Delete Extra Files
                service.deleteExtraFiles(formBean.getTxtSrno());
            }
            catch (Exception ex)
            {
                Logger.ErrorLogger(ex);
            }
        }
        return mav;
    }
}
