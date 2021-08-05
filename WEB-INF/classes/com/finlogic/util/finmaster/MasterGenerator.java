/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 *
 * @author njuser
 */
public class MasterGenerator
{

    public String generateMasterFiles(final FinMasterFormBean formBean) throws ClassNotFoundException, FileNotFoundException, SQLException, UnsupportedEncodingException
    {
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        MasterJSGenerator jsGen;
        jsGen = new MasterJSGenerator();
        jsGen.generateMasterJS(formBean);
        ErrorJSPGenerator errGen;
        errGen = new ErrorJSPGenerator();
        errGen.generateErrorJSP(formBean);
        MainJSPGenerator mainGen;
        mainGen = new MainJSPGenerator();
        mainGen.generateMainJSP(formBean);
        MasterJSONOutPutGenerator jsonGen;
        jsonGen = new MasterJSONOutPutGenerator();
        jsonGen.generateMasterJSONOutPut(formBean);
        MasterControllerGenerator conGen;
        conGen = new MasterControllerGenerator();
        conGen.generateMasterController(formBean);
        MasterFormBeanGenerator fbGen;
        fbGen = new MasterFormBeanGenerator();
        fbGen.generateMasterFormBean(formBean);
        MasterServiceGenerator srvcGen;
        srvcGen = new MasterServiceGenerator();
        srvcGen.generateMasterService(formBean);
        MasterEntityBeanGenerator entGen;
        entGen = new MasterEntityBeanGenerator();
        entGen.generateMasterEntityBean(formBean);
        MasterManagerGenerator mngrGen;
        mngrGen = new MasterManagerGenerator();
        mngrGen.generateMasterManager(formBean);
        MasterDataManagerGenerator dmGen;
        dmGen = new MasterDataManagerGenerator();
        dmGen.generateMasterDataManager(formBean);
        ReadMeGenerator rmGen;
        rmGen = new ReadMeGenerator();
        rmGen.generateReadMe(formBean);
        return tomcatPath + "/webapps/finstudio/generated/" + formBean.getSrNo() + "MGV2";
    }
}
