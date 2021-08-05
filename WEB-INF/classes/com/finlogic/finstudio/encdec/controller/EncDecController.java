/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.finstudio.encdec.controller;

import com.finlogic.finstudio.encdec.bean.EncDecDTO;
import com.finlogic.finstudio.encdec.service.EncDecService;
import com.finlogic.util.Logger;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jigna Patel
 */

@Controller
@RequestMapping(value = "encdec.fin")
@Lazy(true)
public class EncDecController
{
    private static final String ACL_EMP_CODE = "ACLEmpCode";
    //private static final String ALLOWED_EMP_CODE = ",J0188,N0155,J0020,";
    
    @Autowired
    @Qualifier("encDecService")
    @Lazy(true)
    private EncDecService encDecService;
    
    @GetMapping
    public String show(HttpServletRequest request, ModelMap model) throws Exception {
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        String empCode = request.getSession().getAttribute(ACL_EMP_CODE).toString();
        model.addAttribute("serverList", getServerList(empCode));
        model.addAttribute("finlib_path", finlibPath);
        
        return "encdec/menu";
    }
    
    @PostMapping(params = "action=encrypt")
    public String encrypt(HttpServletRequest request, ModelMap model, EncDecDTO dto) {
        String empCode = request.getSession().getAttribute(ACL_EMP_CODE).toString();
        model.addAttribute("view", "encOutput");
        try {
            dto.setAllowedServer(getServerList(empCode));
            String encText = encDecService.encrypt(dto);
            if(!encText.equals("-1")) {
                model.addAttribute("result", encText);
            }
            else {
                model.addAttribute("result", "Encryption Error.\nKindly check log file.");
            }
        }
        catch(Exception e) {
            Logger.DataLogger("EncDecController | Exception => "+e);
        }
        return "encdec/menu";
    }
    
    private ArrayList<String> getServerList(String empCode) {
        ArrayList<String> serverList = new ArrayList<>();
        serverList.add("dev");
        serverList.add("test");
        //if(ALLOWED_EMP_CODE.contains(","+empCode+","))
        serverList.add("prod");
        return serverList;
    }
}
