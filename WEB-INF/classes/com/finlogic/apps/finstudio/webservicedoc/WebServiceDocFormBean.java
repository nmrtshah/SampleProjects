/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservicedoc;

/**
 *
 * @author njuser
 */
public class WebServiceDocFormBean
{

    private String rdowebchoice;
    private String producerPro;
    private String consumerPro;
    private String webserviceName;
    private String webserviceURL;
    private String methodName;
    private String beanAllTable;
//    private String beanType;
    private String beanTableData;

//    private String inBeanType;
    private String inParam = "";
    //in parameters value
    private String[] inparamtable_nature;
    private String[] inparamtable_name;
    private String[] inparamtable_dataType;
    private String[] inparamtable_default;
    private String[] inparamtable_format;

//    private String outBeanType;
    private String outParam = "";
    //out parameters value
    private String[] outparamtable_nature;
    private String[] outparamtable_name;
    private String[] outparamtable_dataType;
    private String[] outparamtable_default;
    private String[] outparamtable_format;
    private String[] httpMethod;

    public String getRdowebchoice()
    {
        return rdowebchoice;
    }

    public void setRdowebchoice(String rdowebchoice)
    {
        this.rdowebchoice = rdowebchoice;
    }

    public String getProducerPro()
    {
        return producerPro;
    }

    public void setProducerPro(String producerPro)
    {
        this.producerPro = producerPro;
    }

    public String getConsumerPro()
    {
        return consumerPro;
    }

    public void setConsumerPro(String consumerPro)
    {
        this.consumerPro = consumerPro;
    }

//    public String getBeanType()
//    {
//        return beanType;
//    }
//
//    public void setBeanType(String beanType)
//    {
//        this.beanType = beanType;
//    }
    public String getWebserviceName()
    {
        return webserviceName;
    }

    public void setWebserviceName(String webserviceName)
    {
        this.webserviceName = webserviceName;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

//    public String getInBeanType()
//    {
//        return inBeanType;
//    }
//
//    public void setInBeanType(String inBeanType)
//    {
//        this.inBeanType = inBeanType;
//    }
    public String getInParam()
    {
        return inParam;
    }

    public void setInParam(String inParam)
    {
        this.inParam = inParam;
    }

    public String[] getInparamtable_name()
    {
        return inparamtable_name;
    }

    public void setInparamtable_name(String[] inparamtable_name)
    {
        this.inparamtable_name = inparamtable_name;
    }

    public String[] getInparamtable_dataType()
    {
        return inparamtable_dataType;
    }

    public void setInparamtable_dataType(String[] inparamtable_dataType)
    {
        this.inparamtable_dataType = inparamtable_dataType;
    }

    public String[] getInparamtable_default()
    {
        return inparamtable_default;
    }

    public void setInparamtable_default(String[] inparamtable_default)
    {
        this.inparamtable_default = inparamtable_default;
    }

    public String[] getInparamtable_format()
    {
        return inparamtable_format;
    }

    public void setInparamtable_format(String[] inparamtable_format)
    {
        this.inparamtable_format = inparamtable_format;
    }

//    public String getOutBeanType()
//    {
//        return outBeanType;
//    }
//
//    public void setOutBeanType(String outBeanType)
//    {
//        this.outBeanType = outBeanType;
//    }
    public String getOutParam()
    {
        return outParam;
    }

    public void setOutParam(String outParam)
    {
        this.outParam = outParam;
    }

    public String[] getOutparamtable_name()
    {
        return outparamtable_name;
    }

    public void setOutparamtable_name(String[] outparamtable_name)
    {
        this.outparamtable_name = outparamtable_name;
    }

    public String[] getOutparamtable_dataType()
    {
        return outparamtable_dataType;
    }

    public void setOutparamtable_dataType(String[] outparamtable_dataType)
    {
        this.outparamtable_dataType = outparamtable_dataType;
    }

    public String[] getOutparamtable_default()
    {
        return outparamtable_default;
    }

    public void setOutparamtable_default(String[] outparamtable_default)
    {
        this.outparamtable_default = outparamtable_default;
    }

    public String[] getOutparamtable_format()
    {
        return outparamtable_format;
    }

    public void setOutparamtable_format(String[] outparamtable_format)
    {
        this.outparamtable_format = outparamtable_format;
    }

    public String getBeanAllTable()
    {
        return beanAllTable;
    }

    public void setBeanAllTable(String beanAllTable)
    {
        this.beanAllTable = beanAllTable;
    }

    public String getBeanTableData()
    {
        return beanTableData;
    }

    public void setBeanTableData(String beanTableData)
    {
        this.beanTableData = beanTableData;
    }

    public String[] getInparamtable_nature()
    {
        return inparamtable_nature;
    }

    public void setInparamtable_nature(String[] inparamtable_nature)
    {
        this.inparamtable_nature = inparamtable_nature;
    }

    public String[] getOutparamtable_nature()
    {
        return outparamtable_nature;
    }

    public void setOutparamtable_nature(String[] outparamtable_nature)
    {
        this.outparamtable_nature = outparamtable_nature;
    }

    public String getWebserviceURL()
    {
        return webserviceURL;
    }

    public void setWebserviceURL(String webserviceURL)
    {
        this.webserviceURL = webserviceURL;
    }

    public String[] getHttpMethod()
    {
        return httpMethod;
    }

    public void setHttpMethod(String[] httpMethod)
    {
        this.httpMethod = httpMethod;
    }
}
