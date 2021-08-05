/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.mobappsgeneration;

import com.finlogic.util.Logger;
import com.finlogic.util.mobappsgeneration.MobileFactory;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class MobAppsController extends MultiActionController
{

    private String finlib_path = finpack.FinPack.getProperty("finlib_path");
    private MobAppsService service = new MobAppsService();

    public ModelAndView defaultMethod(final HttpServletRequest request, final HttpServletResponse response)
    {
        return setMobApps(request, response);
    }

    public ModelAndView setMobApps(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.setViewName("mobappsgeneration/Main");
            mav.addObject("process", "main");
            mav.addObject("finlib_path", finlib_path);
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getVersion(final HttpServletRequest request, final HttpServletResponse response, final MobAppsFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("process", "getversion");
            mav.setViewName("mobappsgeneration/MobAppsSpecification");
            float version;
            version = Float.parseFloat(service.getVersion(formBean));
            String tag;
            tag = "<input type=\"text\" id=\"hdnVersion\" value=\"" + version + "\" />";
            mav.addObject("version", tag);
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView createApp(final HttpServletRequest request, final HttpServletResponse response, final MobAppsFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("finlib_path", finlib_path);
            mav.setViewName("mobappsgeneration/Links");

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
                mav.addObject("SRNO", srno);
                String SrcName;
                SrcName = service.upload(formBean, srno);
                if (!"".equals(SrcName))
                {
                    service.extractFolder(SrcName, String.valueOf(srno));
                    String appPath, buildPath;
                    try
                    {
                        if (formBean.isChkAndroid())
                        {
                            buildPath = service.createApp(MobileFactory.ANDROID, formBean, srno);
                            appPath = "generated/" + srno + MobileFactory.ANDROID.toString() + ".zip";
                            mav.addObject("androidapp", appPath);
                            mav.addObject("andapp", srno + MobileFactory.ANDROID.toString() + ".zip");
                            if (buildPath != null && !"".equals(buildPath))
                            {
                                mav.addObject("androidbuild", buildPath);
                                mav.addObject("andbuild", buildPath.substring(buildPath.lastIndexOf('/') + 1));
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.ErrorLogger(e);
                    }
                    try
                    {
                        if (formBean.isChkBBerry())
                        {
                            buildPath = service.createApp(MobileFactory.BLACKBERRY, formBean, srno);
                            appPath = "generated/" + srno + MobileFactory.BLACKBERRY.toString() + ".zip";
                            mav.addObject("blackberryapp", appPath);
                            mav.addObject("bbapp", srno + MobileFactory.BLACKBERRY.toString() + ".zip");
                            if (buildPath != null && !"".equals(buildPath))
                            {
                                mav.addObject("blackberrybuild", buildPath);
                                mav.addObject("bbbuild", buildPath.substring(buildPath.lastIndexOf('/') + 1));
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.ErrorLogger(e);
                    }
                    try
                    {
                        if (formBean.isChkSymbian())
                        {
                            buildPath = service.createApp(MobileFactory.SYMBIAN, formBean, srno);
                            appPath = "generated/" + srno + MobileFactory.SYMBIAN.toString() + ".zip";
                            mav.addObject("symbianapp", appPath);
                            mav.addObject("symapp", srno + MobileFactory.SYMBIAN.toString() + ".zip");
                            if (buildPath != null && !"".equals(buildPath))
                            {
                                //Rename app.wgz By #APP_NAME.wgz
                                String oldPath;
                                oldPath = MobAppsFormBean.getTOMCAT() + "/webapps/finstudio/" + buildPath;
                                String newPath;
                                newPath = oldPath.replace("app.wgz", formBean.getTxtAppName().replace(" ", "") + ".wgz");

                                File oldfile = new File(oldPath);
                                File newfile = new File(newPath);
                                if (oldfile.exists())
                                {
                                    if (oldfile.renameTo(newfile))
                                    {
                                        buildPath = buildPath.replace("app.wgz", formBean.getTxtAppName().replace(" ", "") + ".wgz");
                                    }
                                    mav.addObject("symbianbuild", buildPath);
                                    mav.addObject("symbuild", buildPath.substring(buildPath.lastIndexOf('/') + 1));
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.ErrorLogger(e);
                    }
                }
            }
            else
            {
                mav.addObject("error", "Problem In Data Insertiopn In Database");
                mav.setViewName("error");
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }
}
