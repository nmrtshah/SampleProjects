/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mastergeneratorv2;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2
{
    public String generateMaster(MasterGeneratorV2FormBean formBean) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        new ControllerGenerator(formBean);
        new ServiceGenerator(formBean);
        new ManagerGenerator(formBean);
        new DataManagerGenerator(formBean);
        new FormBeanGeneratorMaster(formBean);
        new FormBeanGeneratorDetail(formBean);
        new EntityBeanGeneratorMaster(formBean);
        new EntityBeanGeneratorDetail(formBean);
        new JSPGenerator(formBean);
        return tomcatPath + "/webapps/finstudio/generated/" + formBean.getSerialNo();
    }
}
