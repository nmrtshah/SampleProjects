function getData(dataSource, divID)
{
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest)
    {
        XMLHttpRequestObject = new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject)
    {
			
        var obj = document.getElementById(divID);
        //obj.innerHTML = '<img src="../../images/load_page.gif"  width="75" height="75"><br><div align=center><font color=darkblue><b>Loading Page... </b></font></div>';
        obj.innerHTML = '<img src="images/loading.gif"  width="75" height="75"><br><div align=center><font color=darkblue><b>Loading Page... </b></font></div>';
        XMLHttpRequestObject.open("POST",dataSource);
        XMLHttpRequestObject.onreadystatechange = function()
        {
            if(XMLHttpRequestObject.readyState == 4  && XMLHttpRequestObject.status == 200)
            {
                obj.innerHTML = XMLHttpRequestObject.responseText;

            }
        }
        XMLHttpRequestObject.send(null);
    }
}
   
function getData(dataSource, divID, param)
{
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest){
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject){
        var obj = document.getElementById(divID);
        obj.innerHTML = '<img src="images/load_page.gif"  width="75" height="75"><br><div align=center><font color=darkblue><b>Loading Page... </b></font></div>';
        XMLHttpRequestObject.open("POST",dataSource);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.onreadystatechange = function(){
            if(XMLHttpRequestObject.readyState == 4  && XMLHttpRequestObject.status == 200){
                obj.innerHTML = XMLHttpRequestObject.responseText;
            }
        }
        //alert(param);
        XMLHttpRequestObject.send(param);
    }
}	

function getRequestBody(oForm) {
    var aParams = new Array();
    var sParam = '';
    for (var i=0 ; i < oForm.elements.length; i++) {
        if(oForm.elements[i].options){	// dropdwn
            for(var j=0; j < oForm.elements[i].options.length; j++)
            {
                if(oForm.elements[i].options[j].selected){
                    sParam = encodeURIComponent(oForm.elements[i].name);
                    sParam += "=";
                    sParam += encodeURIComponent(oForm.elements[i][j].value);
                    aParams.push(sParam);
                }
					
					
            }
        //alert(oForm.elements[i].name + ' = ' +oForm.elements[i].options[0].selected);
        }else{
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
    }
    return aParams.join("&");
		
}
   
//Following function will get the result from server and return it as string formate.
function getDataValid(dataSource, param) 
{
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest)
    {
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject)
    {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject)
    {
        XMLHttpRequestObject.open("POST",dataSource,false);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4 )// && XMLHttpRequestObject.status == 200)
        {
            var t=XMLHttpRequestObject.responseText;
            //alert(t);
            // obj.innerHTML=XMLHttpRequestObject.responseText;            
            return Trim(t);
        }
    }
}
  
function getalldata(oForm) {
    var aParams = new Array();
    var sParam = '';

    for (var i=0 ; i < oForm.elements.length; i++) {
        //alert(oForm.elements[i].value);
        if (oForm.elements[i].tagName == "SELECT")
        {
            /*var sel = oForm.elements[i];
						 sParam = encodeURIComponent(sel.name) + "=" + encodeURIComponent(sel.options[sel.selectedIndex].value) + "&";
						 aParams.push(sParam);*/
            for(var j=0; j < oForm.elements[i].options.length; j++)
            {
                if(oForm.elements[i].options[j].selected)
                {
                    sParam = encodeURIComponent(oForm.elements[i].name);
                    sParam += "=";
                    sParam += encodeURIComponent(oForm.elements[i][j].value);
                    aParams.push(sParam);
                }
									
									
            }
							
        }
	if(oForm.elements[i].type == "textarea")
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        } 
	
        if(oForm.elements[i].type == "checkbox" && oForm.elements[i].checked == true)
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
					
        if(oForm.elements[i].type == "radio" && oForm.elements[i].checked==true )
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
					
        if(oForm.elements[i].tagName == "INPUT" && oForm.elements[i].type=="text" )
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
        if(oForm.elements[i].tagName == "INPUT" && oForm.elements[i].type=="hidden" )
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
    //alert("hello"+sParam);
    }
		
    return aParams.join("&");
		
}

function getData_sync(dataSource, divID, param,flag){
    // alert(dataSource+"   "+divID+"   "+param);
    var XMLHttpRequestObject =false;  
    if(window.XMLHttpRequest){
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject){
        //  alert(document.getElementById(divID));
        var obj = document.getElementById(divID);

        obj.innerHTML = '<center><div><img src="images/load_page.gif"  width="40" height="40"><br><font color=darkblue><b>Loading Page... </b></font></div></center>';
        XMLHttpRequestObject.open("POST",dataSource,flag);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4){
            obj.innerHTML = XMLHttpRequestObject.responseText;
        }

    }
}
   
	
    

 
