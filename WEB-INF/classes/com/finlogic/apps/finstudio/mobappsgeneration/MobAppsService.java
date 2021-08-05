/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.mobappsgeneration;

import com.finlogic.business.finstudio.mobappsgeneration.MobAppsEntityBean;
import com.finlogic.business.finstudio.mobappsgeneration.MobAppsManager;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.mobappsgeneration.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

/**
 *
 * @author njuser
 */
public class MobAppsService
{

    private MobAppsManager mngr = new MobAppsManager();

    public MobAppsEntityBean formBeanToEntityBean(final MobAppsFormBean formBean)
    {
        MobAppsEntityBean entityBean;
        entityBean = new MobAppsEntityBean();
        entityBean.setAppName(formBean.getTxtAppName());
        entityBean.setAppVersion(formBean.getTxtVersion());
        entityBean.setAppPackage(formBean.getTxtPackage());
        entityBean.setWelcomeFile(formBean.getTxtWelcomeFile());
        entityBean.setEmpCode(formBean.getEmpCode());

        StringBuilder target;
        target = new StringBuilder();
        if (formBean.isChkAndroid())
        {
            target.append(MobileFactory.ANDROID).append(",");
        }
        if (formBean.isChkBBerry())
        {
            target.append(MobileFactory.BLACKBERRY).append(",");
        }
        if (formBean.isChkSymbian())
        {
            target.append(MobileFactory.SYMBIAN).append(",");
        }
        target = target.deleteCharAt(target.length() - 1);
        entityBean.setTarget(target.toString());
        return entityBean;
    }

    public String getVersion(final MobAppsFormBean formBean) throws ClassNotFoundException, SQLException
    {
        String version = "";
        version = mngr.getVersion(formBean.getTxtAppName());
        return version;
    }

    public int insertIntoDataBase(final MobAppsFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return mngr.insertIntoDataBase(formBeanToEntityBean(formBean));
    }

    public String upload(final MobAppsFormBean formBean, final int srno) throws IOException
    {
        MultipartFile file;
        file = formBean.getfSource();
        String strDestFileName = "";

        if (file != null && !file.getOriginalFilename().equals(""))
        {
            strDestFileName = MobAppsFormBean.getUPLOAD() + "/" + srno;
            file.transferTo(new File(strDestFileName));
        }
        return strDestFileName;
    }

    private void copyTemplate(final MobileFactory platform, final String srcPath, final String packge, final String appName, final int srno) throws IOException, ParserConfigurationException, SAXException
    {
        String destPath;
        File src, dest;
        DirectoryService dirSrvc;
        dirSrvc = new DirectoryService();

        destPath = MobAppsFormBean.getGENERATED() + "/" + srno + platform + "/" + appName + "/";
        src = new File(srcPath);
        dest = new File(destPath);
        dest.mkdirs();
        dirSrvc.copyTemplate(src, dest, packge);
    }

    private void mergeFolder(final MobileFactory platform, final String webroot, final String appName, final int srno) throws IOException, ParserConfigurationException, SAXException
    {
        String srcPath, destPath;
        File src, dest;
        DirectoryService dirSrvc;
        dirSrvc = new DirectoryService();

        srcPath = MobAppsFormBean.getGENERATED() + "/" + srno + "MOB";
        destPath = MobAppsFormBean.getGENERATED() + "/" + srno + platform + "/" + appName + webroot;
        src = new File(srcPath);
        dest = new File(destPath);
        dest.mkdirs();
        dirSrvc.mergeFolder(src, dest);
    }

    public void extractFolder(final String zipFile, final String srno) throws IOException
    {
        new File(MobAppsFormBean.getGENERATED() + "/" + srno + "MOB").mkdir();
        DirectoryService dirSrvc;
        dirSrvc = new DirectoryService();
        dirSrvc.extractFolder(zipFile, MobAppsFormBean.getGENERATED() + "/" + srno + "MOB");
    }

    public String createApp(final MobileFactory platform, final MobAppsFormBean formBean, final int srno) throws ParserConfigurationException, IOException, SAXException, InterruptedException, Exception
    {
        XmlReader obj;
        obj = new XmlReader();

        XMLProperties xmlProps;
        xmlProps = obj.readXML(platform);
        xmlProps.setCommandValues(formBean, srno, platform.toString());

        //Copy Template from '/template/' to '/generated/'
        String path = xmlProps.getTemplate();
        path = path.replace("#TOMCAT_PATH", MobAppsFormBean.getTOMCAT());
        copyTemplate(platform, path, formBean.getTxtPackage(), formBean.getTxtAppName().replace(" ", ""), srno);

        //Copy extracted folder into template copied in '/generated/'
        path = xmlProps.getWebroot();
        mergeFolder(platform, path, formBean.getTxtAppName().replace(" ", ""), srno);

        //search for file and make changes
        FileService fileSrvc;
        fileSrvc = new FileService();
        String destPath;
        destPath = MobAppsFormBean.getGENERATED() + "/" + srno + platform + "/" + formBean.getTxtAppName().replace(" ", "") + "/";

        //Change Application Name
        List ls;
        ls = xmlProps.getAppName();
        for (int i = 0; i < ls.size(); i++)
        {
            Map<String, String> map;
            map = (Map) ls.get(i);
            String replacement;
            replacement = map.get("pattern");
            replacement = replacement.replace("#APP_NAME", formBean.getTxtAppName());
            fileSrvc.findAndReplace(destPath, map.get("file"), map.get("pattern"), replacement);
        }

        //Change Version
        ls = xmlProps.getVersion();
        for (int i = 0; i < ls.size(); i++)
        {
            Map<String, String> map;
            map = (Map) ls.get(i);
            String replacement;
            replacement = map.get("pattern");
            replacement = replacement.replace("#VERSION", formBean.getTxtVersion());
            fileSrvc.findAndReplace(destPath, map.get("file"), map.get("pattern"), replacement);
        }

        //Change Package Name
        ls = xmlProps.getPackageName();
        for (int i = 0; i < ls.size(); i++)
        {
            Map<String, String> map;
            map = (Map) ls.get(i);
            String replacement;
            replacement = map.get("pattern");
            replacement = replacement.replace("#PACK_NAME", formBean.getTxtPackage());
            fileSrvc.findAndReplace(destPath, map.get("file"), map.get("pattern"), replacement);
        }

        //Change Welcome File
        ls = xmlProps.getWelcomeFile();
        for (int i = 0; i < ls.size(); i++)
        {
            Map<String, String> map;
            map = (Map) ls.get(i);
            String replacement;
            replacement = map.get("pattern");
            replacement = replacement.replace("#WELCOME_FILE", formBean.getTxtWelcomeFile());
            fileSrvc.findAndReplace(destPath, map.get("file"), map.get("pattern"), replacement);
        }

        //Zip Application
        String srcPath;
        srcPath = MobAppsFormBean.getGENERATED() + "/" + srno + platform;
        FolderZipper.zipFolder(srcPath, srcPath + ".zip");

        //Build Application
        BuildApps build;
        build = new BuildApps();
        List comm;
        comm = xmlProps.getCommands();
        if (build.compileApp(comm, srno))
        {
            //return destination of Build Application
            path = xmlProps.getOutput();
            path = path.replace("#APP_PATH", srno + platform.toString() + "/" + formBean.getTxtAppName().replace(" ", ""));
            path = path.replace("#APP_NAME", formBean.getTxtAppName().replace(" ", ""));
            return path;
        }
        else
        {
            return "";
        }
    }
}
