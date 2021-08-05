/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var tran_schema=new Array("ACL",
    "ACL_LOG",
    "ADMIN",
    "ADMINDBA",
    "ADMIN_LOG",
    "BIMS",
    "BIMS_LOG",
    "COMPLIANCE",
    "COMPLIANCE_LOG",
    "DATAUPLOAD",
    "DBACT",
    "DBADMIN",
    "DBADMIN_LOG",
    "DBADMIN_TEST",
    "DDL_PROC",
    "DDL_PROC2",
    "DDL_PROC3",
    "DDL_PROC4",
    "DOCUMENTS",
    "DOCUMENTS_LOG",
    "FAS",
    "FAS_GURUKUL",
    "FAS_GURUKUL_LOG",
    "FAS_LOG",
    "FAS_PARTNER",
    "FAS_PORTFOLIOPLUS",
    "FAS_REALTY",
    "FAS_REALTY_LOG",
    "FAS_TEST",
    "FAS_UPTO_0607",
    "FD",
    "FD_LOG",
    "FINLOGIC",
    "FINUTILITY",
    "FINUTILITY_MFTRAN",
    "FINUTILITY_MFUND",
    "GENERAL_INSURANCE",
    "GENERAL_INSURANCE_LOG",
    "HR",
    "INSURANCE",
    "INSURANCE_LOG",
    "INTEGRATION_SOLUTION",
    "IPO",
    "KMFUND",                
    "KRC_DATAUPLOAD",
    "KRC_FINUTILITY_MFUND",  
    "KRC_MFTRAN",            
    "KRC_MFUND",             
    "MFARCHIVE",
    "MFTRAN",                
    "MFTRAN_LOG"            ,
    "MFUND",                 
    "MFUND_LOG",             
    "MIS",                   
    "NEWFAS",                
    "NJHR",                  
    "NJHR_LOG",              
    "NJINDIA",               
    "NJINDIAINVEST",         
    "NJREALTY",              
    "NJREALTY_LOG",          
    "NJREALTY_OLD",
    "NJ_INVENTORY",          
    "OFFSHORE_MFTRAN",       
    "PMS",                   
    "PMS_LOG",               
    "PORTFOLIOPLUS",         
    "PORTFOLIOPLUS_LOG",     
    "PSS",                   
    "PSS_LOG",               
    "REPORTING",             
    "RND",                   
    "SCOTT",                 
    "TSMSYS",                
    "WFMS",                  
    "WFMS_LOG"       
    );
var brok_schema=new Array(
    "ACL",                  
    "ACL_LOG",              
    "ADMIN",                
    "ADMINDBA",             
    "ADMIN_LOG",            
    "COMPLIANCE",           
    "DATAUPLOAD",           
    "DBADMIN",              
    "DBADMIN_TEST",         
    "DDL_PROC",             
    "DDL_PROC2",            
    "DDL_PROC3",            
    "DDL_PROC4",            
    "DOCUMENTS",            
    "FAS",                  
    "FAS_GURUKUL",          
    "FAS_PARTNER",          
    "FAS_REALTY",           
    "FAS_TEST",             
    "FAS_UPTO_0607",        
    "FD",                   
    "FINLOGIC",             
    "FINUTILITY",           
    "FINUTILITY_MFTRAN",    
    "FINUTILITY_MFUND",     
    "HR",                   
    "INSURANCE",            
    "INTEGRATION_SOLUTION", 
    "IPO",                  
    "KMFUND",               
    "KRC_FINUTILITY_MFUND", 
    "MFARCHIVE",            
    "MFTRAN",               
    "MFTRAN_LOG",           
    "MFUND",                
    "MFUND_LOG",            
    "MIS",                  
    "MIS_LOG",              
    "NEWFAS",               
    "NJHR",                 
    "NJHR_LOG",             
    "NJREALTY",             
    "NJREALTY_LOG",         
    "NJ_INVENTORY",         
    "PMS",                  
    "PSS",                  
    "REPORTING",            
    "RND",                  
    "SCOTT",                
    "TSMSYS",               
    "WFMS"
    );
    
function validateData()
{
    if(document.getElementById("rdoConn").checked)
    {
        if(document.getElementById('server').value == '-1')
        {
            alert("Please select server name");
            document.getElementById('server').focus();
            return false;
        }
        else if(document.getElementById('schema').value == '-1')
        {
            alert("Please select schema name");
            document.getElementById('schema').focus();
            return false;
        }    
        else if(document.getElementById('item').value == '-1')
        {
            alert("Please select item");
            document.getElementById('item').focus();
            return false;
        }
        else if(document.getElementById('itemNmCmb').value == '-1')
        {
            alert("Please select item name");
            document.getElementById('itemNmCmb').focus();
            return false;
        }    
        return true;
    }
    else
    {
        if(document.getElementById('txtaOraQuery').value == "")
        {
            alert("Please enter pl/sql bock");
            document.getElementById('txtaOraQuery').focus();
            return false;
        }
        return true;
    }
}
    
function getSchema()
{
    var selObj = document.getElementById('schema');
    selObj.innerHTML = "<option value='-1'>--Select Schema--</option>";
                        
    if(document.getElementById("server").value == "dev_brok")
    {
        for (var j=0; j<tran_schema.length; j++)
        {
            var options1 = document.createElement("OPTION");
            options1.value = tran_schema[j];
            options1.text = tran_schema[j];
            selObj.options.add(options1);
        }
    }
    else if(document.getElementById("server").value == "dev_tran")
    {
        for (j=0; j<brok_schema.length; j++)
        {
            options1 = document.createElement("OPTION");
            options1.value = brok_schema[j];
            options1.text = brok_schema[j];
            selObj.options.add(options1);
        }
    }
}
function getItemList()
{
    var param = getFormData(document.mainform);
    
    if(document.getElementById("schema").value != "-1" && document.getElementById("item").value != "-1")
    {
        getSynchronousData('oracleToMySql.fin?cmdAction=getItemnList', param, 'itemId');
    }                  
}
function getQuery()
{
    if(validateData())
    {
        document.getElementById("tbformartCode").style.display = "none";
        var param = getFormData(document.mainform);
        if(document.getElementById("rdoConn").checked)
        {                                
            getSynchronousData('oracleToMySql.fin?cmdAction=getNewQuery', param, 'queryUsingConn');                           
        }
        else
        {
            getSynchronousData('oracleToMySql.fin?cmdAction=getNewQuery', param, 'queryUsingBlock');            
        }                
        if(document.getElementById("rdoConn").checked && document.getElementById("query").innerHTML != "")
        {
            document.getElementById("tbformartCode").style.display = "";
        }
    }
}
function showHide()
{
    document.getElementById("tbformartCode").style.display = "none";
    if(document.getElementById("rdoConn").checked)
    {
        document.getElementById("usingBlock").style.display = "none";
        document.getElementById("usingConn").style.display = "";
    }
    else if(document.getElementById("rdoBlock").checked)
    {
        document.getElementById("usingConn").style.display = "none";
        document.getElementById("usingBlock").style.display = "";
    }                        
}                   
