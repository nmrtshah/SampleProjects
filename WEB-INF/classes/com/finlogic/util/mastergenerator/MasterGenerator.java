package com.finlogic.util.mastergenerator;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class MasterGenerator
{
    public String generateMaster(MasterGeneratorFormBean formBean, ArrayList<String> allColumns, ArrayList<String> selectedColumns, ArrayList<String> seletedControls) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        new DataManagerGenerator(formBean, allColumns, selectedColumns);
        new ModelGenerator(formBean);
        new ServiceGenerator(formBean, selectedColumns);
        new ControllerGenerator(formBean);
        new JSPGenerator(formBean, allColumns, selectedColumns,seletedControls);
        new FormBeanGenerator(formBean, selectedColumns);
        new EntityBeanGenerator(formBean, allColumns);
        return tomcatPath + "/webapps/finstudio/generated/" + formBean.getSerialNo();
    }
}
