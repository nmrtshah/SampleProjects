/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mobappsgeneration;

import com.finlogic.apps.finstudio.mobappsgeneration.MobAppsFormBean;
import com.finlogic.util.properties.HardCodeProperty;
import java.util.List;

/**
 *
 * @author njuser
 */
public class XMLProperties
{

    private String template;
    private String webroot;
    private List appName;
    private List version;
    private List packageName;
    private List welcomeFile;
    private List commands;
    private String output;

    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(final String template)
    {
        this.template = template;
    }

    public String getWebroot()
    {
        return webroot;
    }

    public void setWebroot(final String webroot)
    {
        this.webroot = webroot;
    }

    public List getAppName()
    {
        return appName;
    }

    public void setAppName(final List appName)
    {
        this.appName = appName;
    }

    public List getVersion()
    {
        return version;
    }

    public void setVersion(final List version)
    {
        this.version = version;
    }

    public List getPackageName()
    {
        return packageName;
    }

    public void setPackageName(final List packageName)
    {
        this.packageName = packageName;
    }

    public List getWelcomeFile()
    {
        return welcomeFile;
    }

    public void setWelcomeFile(final List welcomeFile)
    {
        this.welcomeFile = welcomeFile;
    }

    public List getCommands()
    {
        return commands;
    }

    public void setCommands(final List commands)
    {
        this.commands = commands;
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(final String output)
    {
        this.output = output;
    }

    public void setCommandValues(final MobAppsFormBean formBean, final int srno, final String platform)
    {
        if (commands != null)
        {
            int len;
            len = commands.size();
            for (int i = 0; i < len; i++)
            {
                String comm;
                comm = commands.get(i).toString();
                HardCodeProperty hcp;
                hcp = new HardCodeProperty();
                comm = comm.replace("#ANDROID_PATH", hcp.getProperty("android_path"));
                comm = comm.replace("#ANT_PATH", hcp.getProperty("ant_path"));
                comm = comm.replace("#TOMCAT_PATH", MobAppsFormBean.getTOMCAT());
                comm = comm.replace("#APP_NAME", formBean.getTxtAppName().replace(" ", ""));
                comm = comm.replace("#APP_PATH", srno + platform + "/" + formBean.getTxtAppName().replace(" ", ""));
                comm = comm.replace("#PACK_NAME", formBean.getTxtPackage());
                comm = comm.replace("#VERSION", formBean.getTxtVersion());
                comm = comm.replace("#WELCOME_FILE", formBean.getTxtWelcomeFile());
                commands.set(i, comm);
            }
        }
    }
}
