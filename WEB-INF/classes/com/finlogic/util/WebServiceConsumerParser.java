/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class WebServiceConsumerParser
{

    private String wsInterface;
    private String wsProject;

    public WebServiceConsumerParser()
    {
    }

    public WebServiceConsumerParser(String wsInterfaceName, String wsProjectName)
    {
        this.wsInterface = wsInterfaceName;
        this.wsProject = wsProjectName;
    }

    public List parseForMethods() throws ClassNotFoundException
    {
        String consumerName;
        consumerName = wsInterface.replace("Service", "Consumer");

        Class classObj;
        classObj = Thread.currentThread().getContextClassLoader().loadClass("com.finlogic.eai.ws.consumer." + wsProject + "." + consumerName);
        //classObj = Thread.currentThread().getContextClassLoader().loadClass("com.finlogic.eai.ws.consumer.hrms.EmployeeConsumer");

        Method[] methods;
        methods = classObj.getDeclaredMethods();
        List<String> methodNames = new ArrayList<String>();

        for (int i = 0; i < methods.length; i++)
        {
            methodNames.add(methods[i].getName());
        }
        return methodNames;
    }

    public Map parseForParams(final String wsMethod) throws ClassNotFoundException
    {
        String consumerName;
        consumerName = wsInterface.replace("Service", "Consumer");

        Class classObj;
        classObj = Thread.currentThread().getContextClassLoader().loadClass("com.finlogic.eai.ws.consumer." + wsProject + "." + consumerName);

        Method[] methods;
        methods = classObj.getDeclaredMethods();
        List<String> params;
        Map<String, List<String>> map = new HashMap<String, List<String>>();

        for (int i = 0; i < methods.length; i++)
        {
            if (methods[i].getName().equals(wsMethod))
            {
                //Return Type
                params = new ArrayList<String>();
                params.add(methods[i].getReturnType().getCanonicalName());
                map.put("returnType", params);

                //Loop for All Parameters
                params = new ArrayList<String>();
                Class[] types = methods[i].getParameterTypes();
                for (Class class1 : types)
                {
                    if (class1.isPrimitive())
                    {
                        params.add(class1.getSimpleName());
                    }
                    else
                    {
                        if (class1.getSimpleName().equals("String"))
                        {
                            params.add(class1.getSimpleName());
                        }
                        else
                        {
                            params.add("null");
                        }
                    }

                }
                map.put("parameters", params);

                //Loop for All Throws Exception
                params = new ArrayList<String>();
                Class[] exps = methods[i].getExceptionTypes();
                for (Class class1 : exps)
                {
                    params.add(class1.getSimpleName());
                }
                map.put("exceptions", params);
            }
        }
        return map;
    }
}
