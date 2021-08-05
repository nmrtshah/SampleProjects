/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.properties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author njuser
 */
public class HardCodeProperty
{

    private static Properties properties;

    public HardCodeProperty()
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream(finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/META-INF/properties/HardCode.properties");
            properties = new Properties();
            properties.load(is);
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    public String getProperty(String propertyName)
    {
        return properties.getProperty(propertyName);
    }
}
