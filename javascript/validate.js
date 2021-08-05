// validate.js
// this file contain functions for validating data and printing

//print the document

function self_print()
{
	document.all.a_print.style.visibility="hidden";
	self.print();
}
//////////////////////// display message for deleting entry from foliored //////////
function msgDelete()
{
	alert("Switch/Redemption Done Can not Delete.");
	return false;
}

function validate(formname)
{
	if(formname =="regmst1")
	{
		return regmst1();
	}
	
}

//valadate regmst

function regmst1()
{
	if(document.regmst.regname.value=="")
	{
		alert("Enter Registrar Name.");
		document.regmst.regname.focus();
		return false;
	}
	return true;
}

//calculate TDS Amount

function getTDS()
{
	var amount;
	var netamt=0;
	var tdsamount=0;
	var tottds;
	var tdsrate=0;
	if(document.print_uf_bill.tdsrate.value=="")
	{
		tdsrate =0;
	}
	else
	{
		tdsrate= eval(document.print_uf_bill.tdsrate.value);
	}
	amount= eval(document.print_uf_bill.tamount.value);
	extraamt= eval(document.print_uf_bill.extraamt.value);
//	if(document.print_uf_bill.tottds.value=="")
//	{
//		tottds=0
//	}
//	else
//	{
//		tottds =  eval(document.print_uf_bill.tottds.value);
//	}
	if(tdsrate>0)
	{
		tdsamount=(amount+extraamt)*tdsrate/100;	
	}
	else
		tdsamount=0;
	netamt= amount-tdsamount+extraamt;
	document.print_uf_bill.tdsamt.value=tdsamount;
	document.print_uf_bill.netamt.value=Math.round(netamt);
	getHoldUF();
}	
function getMarginTDS()
{
	var amount;
	var netamt=0;
	var tdsamount=0;
	var tottds;
	var tdsrate=0;
	if(document.advanceprint_uf_bill.tdsrate.value=="")
	{
		tdsrate =0;
	}
	else
	{
		tdsrate= eval(document.advanceprint_uf_bill.tdsrate.value);
	}
	amount= eval(document.advanceprint_uf_bill.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill.extraamt.value);
//	if(document.advanceprint_uf_bill.tottds.value=="")
//	{
//		tottds=0
//	}
//	else
//	{
//		tottds =  eval(document.advanceprint_uf_bill.tottds.value);
//	}
	if(tdsrate>0)
	{
		tdsamount=(amount+extraamt)*tdsrate/100;	
	}
	else
	{
		tdsamount=0;
	}

	netamt= amount-tdsamount+extraamt;
	document.advanceprint_uf_bill.tdsamt.value=tdsamount;
	document.advanceprint_uf_bill.grossamt.value=Math.round(netamt);
	document.advanceprint_uf_bill.netamt.value=Math.round(netamt);

	getMarginHoldUF();
}	
function getMarginTDS_NJP()
{
	var amount;
	var netamt=0;
	var tdsamount=0;
	var tottds;
	var tdsrate=0;
	var extraamt=0;

	if(document.advanceprint_uf_bill_NJP.tdsrate.value=="")
	{
		tdsrate =0;
	}
	else
	{
		tdsrate= eval(document.advanceprint_uf_bill_NJP.tdsrate.value);
	}
	amount= eval(document.advanceprint_uf_bill_NJP.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill_NJP.extraamt.value);
//	if(document.advanceprint_uf_bill_NJP.tottds.value=="")
//	{
//		tottds=0
//	}
//	else
//	{
//		tottds =  eval(document.advanceprint_uf_bill_NJP.tottds.value);
//	}
	if(tdsrate>0)
	{
		tdsamount=(amount+extraamt)*tdsrate/100;	
	}
	else
	{
		tdsamount=0;
	}

	netamt= amount-tdsamount+extraamt;
	document.advanceprint_uf_bill_NJP.tdsamt.value=tdsamount;
	document.advanceprint_uf_bill_NJP.grossamt.value=Math.round(netamt);
	document.advanceprint_uf_bill_NJP.netamt.value=Math.round(netamt);
	
	getMarginHoldUF_NJP();
}	

function getMarginHoldUF_NJP()
{
	var amount;
        var amount2;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	var renewamt=0;
        var renewamt_am=0;
	var printshopamt=0;
        var livewireamt=0;
	var extraamt=0;
	var print_pay_req_amt=0
	var guru_pay_req_amt=0
	
	if(document.advanceprint_uf_bill_NJP.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_uf_bill_NJP.holdrate.value);
	}
	amount= eval(document.advanceprint_uf_bill_NJP.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill_NJP.extraamt.value);
	if(holdrate>0)
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
                    
	document.advanceprint_uf_bill_NJP.holdamt.value=holdamount;
	netamt= amount-holdamount+extraamt;
        
	document.advanceprint_uf_bill_NJP.grossamt.value=Math.round(netamt);
	
        amount2= eval(document.advanceprint_uf_bill_NJP.tamount2.value);
        netamt= netamt+amount2 ;
        document.advanceprint_uf_bill_NJP.grossamt2.value=Math.round(netamt);

	renewamt_am=document.advanceprint_uf_bill_NJP.renewamt_am_org.value;
	renewamt=document.advanceprint_uf_bill_NJP.renewamt_org.value;
	
         // alert('renewamt_am'); 
        // alert(renewamt_am);   

	if(netamt<0)
	{
		renewamt=0;
                renewamt_am=0;
	}
	else	
		if(renewamt>netamt)
		{
			renewamt=netamt;
                        renewamt_am = 0;
		}
        else   if(renewamt_am>netamt)
                {
                    renewamt_am=netamt;
                }
	  else 
		netamt=netamt-renewamt-renewamt_am;

	//netamt=netamt-renewamt;

	document.advanceprint_uf_bill_NJP.renewamt.value=renewamt;
        document.advanceprint_uf_bill_NJP.renewamt_am.value=renewamt_am;
	
	if(document.advanceprint_uf_bill_NJP.printshopamt.value=="")
	{ 
		printshopamt =0;
	}
	else
	{
		printshopamt= eval(document.advanceprint_uf_bill_NJP.printshopamt.value);
	} 
	

        if(document.advanceprint_uf_bill_NJP.livewireamt.value=="")
	{ 
		livewireamt =0;
	}
	else
	{
		livewireamt= eval(document.advanceprint_uf_bill_NJP.livewireamt.value);
	}
	if(document.advanceprint_uf_bill_NJP.print_pay_req_amt.value=="")
	{ 
		print_pay_req_amt =0;
	}
	else
	{
		print_pay_req_amt= eval(document.advanceprint_uf_bill_NJP.print_pay_req_amt.value);
	}	
 
	if(document.advanceprint_uf_bill_NJP.guru_pay_req_amt.value=="")
	{ 
		guru_pay_req_amt =0;
	}
	else
	{
		guru_pay_req_amt= eval(document.advanceprint_uf_bill_NJP.guru_pay_req_amt.value);
	}	
	/*netamt=netamt-printshopamt-livewireamt-print_pay_req_amt-guru_pay_req_amt;*/
	if(netamt-printshopamt-livewireamt-print_pay_req_amt<0)
	{
		netamt=netamt-printshopamt-livewireamt;
		//alert('net amt'+netamt);
		print_pay_req_amt=netamt;
		guru_pay_req_amt=0;
		netamt=0;
	}
	else 
	{
		netamt=netamt-printshopamt-livewireamt-print_pay_req_amt;
		if(netamt-guru_pay_req_amt<0)
		{
			guru_pay_req_amt=netamt;
			netamt=0;
		}
	}

//	if(printshopamt<=Math.round(netamt))
//	{
//		document.advanceprint_uf_bill_NJP.netamt.value=Math.round(netamt-printshopamt);
//	}
//	else
//	{ 
//		printshopamt=0;
		document.advanceprint_uf_bill_NJP.printshopamt.value=Math.round(printshopamt);
                document.advanceprint_uf_bill_NJP.livewireamt.value=livewireamt;
		document.advanceprint_uf_bill_NJP.print_pay_req_amt.value=print_pay_req_amt;
		document.advanceprint_uf_bill_NJP.guru_pay_req_amt.value=guru_pay_req_amt;

		document.advanceprint_uf_bill_NJP.netamt.value=Math.round(netamt);
//	}

	if(Math.round(netamt)<0)
		document.advanceprint_uf_bill_NJP.cmdSave.disabled=true;
	else
		document.advanceprint_uf_bill_NJP.cmdSave.disabled=false;

}

function getMarginHoldUF_NJP2()
{
	var amount;
        var amount2;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	var renewamt=0;
        var renewamt_am=0;
	var printshopamt=0;
        var livewireamt=0;
	var extraamt=0;
	var print_pay_req_amt=0
	var guru_pay_req_amt=0
	
	if(document.bill_UF_NJP_preview.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.bill_UF_NJP_preview.holdrate.value);
	}
	amount= eval(document.bill_UF_NJP_preview.tamount.value);
	extraamt= eval(document.bill_UF_NJP_preview.extraamt.value);
	if(holdrate>0)
	{
                /* Comment By tushar on : 07/05/08 Told By Sunil to remove extramt from Holdamt  */
		/*holdamount=(amount+extraamt)*holdrate/(100+holdrate);	*/ 

                holdamount=(amount)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
                      
	document.bill_UF_NJP_preview.holdamt.value=holdamount;
	netamt= amount-holdamount+extraamt;
        
	document.bill_UF_NJP_preview.grossamt.value=Math.round(netamt);
	
        amount2= eval(document.bill_UF_NJP_preview.tamount2.value);
        netamt= netamt+amount2 ;
        document.bill_UF_NJP_preview.grossamt2.value=Math.round(netamt);

	renewamt_am=document.bill_UF_NJP_preview.renewamt_am_org.value;
	renewamt=document.bill_UF_NJP_preview.renewamt_org.value;
	
         // alert('renewamt_am'); 
        // alert(renewamt_am);   

	if(netamt<0)
	{
		renewamt=0;
                renewamt_am=0;
	}
	else	
		if(renewamt>netamt)
		{
			renewamt=netamt;
                        renewamt_am = 0;
                        netamt = 0;    
		}
        else   if(renewamt_am>netamt)
                {
                    renewamt_am=netamt;
                    netamt = 0;
                }
	  else 
		netamt=netamt-renewamt-renewamt_am;

	//netamt=netamt-renewamt;

	document.bill_UF_NJP_preview.renewamt.value=renewamt;
        document.bill_UF_NJP_preview.renewamt_am.value=renewamt_am;
	
	if(document.bill_UF_NJP_preview.printshopamt.value=="")
	{ 
		printshopamt =0;
	}
	else
	{
		printshopamt= eval(document.bill_UF_NJP_preview.printshopamt.value);
	} 
	

        if(document.bill_UF_NJP_preview.livewireamt.value=="")
	{ 
		livewireamt =0;
	}
	else
	{
		livewireamt= eval(document.bill_UF_NJP_preview.livewireamt.value);
	}
	if(document.bill_UF_NJP_preview.print_pay_req_amt.value=="")
	{ 
		print_pay_req_amt =0;
	}
	else
	{
		print_pay_req_amt= eval(document.bill_UF_NJP_preview.print_pay_req_amt.value);
	}	
 
	if(document.bill_UF_NJP_preview.guru_pay_req_amt.value=="")
	{ 
		guru_pay_req_amt =0;
	}
	else
	{
		guru_pay_req_amt= eval(document.bill_UF_NJP_preview.guru_pay_req_amt.value);
	}	
	/*netamt=netamt-printshopamt-livewireamt-print_pay_req_amt-guru_pay_req_amt;*/

	if(netamt-printshopamt-livewireamt-print_pay_req_amt<0)
	{
//		netamt=netamt-printshopamt-livewireamt;
		//alert('net amt'+netamt);
		print_pay_req_amt=netamt;
		guru_pay_req_amt=0;
		netamt=0;
	}
	else 
	{
		netamt=netamt-printshopamt-livewireamt-print_pay_req_amt;
		if(netamt-guru_pay_req_amt<0)
		{
			guru_pay_req_amt=netamt;
			netamt=0;
		}
	}

//	if(printshopamt<=Math.round(netamt))
//	{
//		document.bill_UF_NJP_preview.netamt.value=Math.round(netamt-printshopamt);
//	}
//	else
//	{ 
//		printshopamt=0;

		document.bill_UF_NJP_preview.printshopamt.value=Math.round(printshopamt);
                document.bill_UF_NJP_preview.livewireamt.value=livewireamt;
		document.bill_UF_NJP_preview.print_pay_req_amt.value=print_pay_req_amt;
		document.bill_UF_NJP_preview.guru_pay_req_amt.value=guru_pay_req_amt;
                //alert('netamt:'+netamt);
		document.bill_UF_NJP_preview.netamt.value=Math.round(netamt);
//	}

//	if(Math.round(netamt)<0)
//		document.bill_UF_NJP_preview.cmdSave.disabled=true;
//	else
//		document.bill_UF_NJP_preview.cmdSave.disabled=false;

}
	
function getMarginHoldUF_NJP_pro()
{	
	var amount;
        var amount2;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	var renewamt=0;
        var renewamt_am=0;
	var printshopamt=0;
        var livewireamt=0;
	//var extraamt=0;
	var print_pay_req_amt=0
	var guru_pay_req_amt=0
	
	if(document.bill_UF_NJP_preview_pro.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.bill_UF_NJP_preview_pro.holdrate.value);
	}
	amount= eval(document.bill_UF_NJP_preview_pro.tamount.value);
	//extraamt= eval(document.bill_UF_NJP_preview_pro.extraamt.value);
	if(holdrate>0)
	{
                /* Comment By tushar on : 07/05/08 Told By Sunil to remove extramt from Holdamt  */
		/*holdamount=(amount+extraamt)*holdrate/(100+holdrate);	*/ 

                holdamount=(amount)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
                      
	document.bill_UF_NJP_preview_pro.holdamt.value=holdamount;
	netamt= amount-holdamount;
        
	document.bill_UF_NJP_preview_pro.grossamt.value=Math.round(netamt);
	
        amount2= eval(document.bill_UF_NJP_preview_pro.tamount2.value);
        netamt= netamt+amount2 ;
        document.bill_UF_NJP_preview_pro.grossamt2.value=Math.round(netamt);

	renewamt_am=document.bill_UF_NJP_preview_pro.renewamt_am_org.value;
	renewamt=document.bill_UF_NJP_preview_pro.renewamt_org.value;
	
         // alert('renewamt_am'); 
        // alert(renewamt_am);   

	if(netamt<0)
	{
		renewamt=0;
                renewamt_am=0;
	}
	else	
		if(renewamt>netamt)
		{
			renewamt=netamt;
                        renewamt_am = 0;
                        netamt = 0;    
		}
        else   if(renewamt_am>netamt)
                {
                    renewamt_am=netamt;
                    netamt = 0;
                }
	  else 
		netamt=netamt-renewamt-renewamt_am;

	//netamt=netamt-renewamt;

	document.bill_UF_NJP_preview_pro.renewamt.value=renewamt;
        document.bill_UF_NJP_preview_pro.renewamt_am.value=renewamt_am;
	
	if(document.bill_UF_NJP_preview_pro.printshopamt.value=="")
	{ 
		printshopamt =0;
	}
	else
	{
		printshopamt= eval(document.bill_UF_NJP_preview_pro.printshopamt.value);
	} 
	

        if(document.bill_UF_NJP_preview_pro.livewireamt.value=="")
	{ 
		livewireamt =0;
	}
	else
	{
		livewireamt= eval(document.bill_UF_NJP_preview_pro.livewireamt.value);
	}
	if(document.bill_UF_NJP_preview_pro.print_pay_req_amt.value=="")
	{ 
		print_pay_req_amt =0;
	}
	else
	{
		print_pay_req_amt= eval(document.bill_UF_NJP_preview_pro.print_pay_req_amt.value);
	}	
 
	if(document.bill_UF_NJP_preview_pro.guru_pay_req_amt.value=="")
	{ 
		guru_pay_req_amt =0;
	}
	else
	{
		guru_pay_req_amt= eval(document.bill_UF_NJP_preview_pro.guru_pay_req_amt.value);
	}	
	/*netamt=netamt-printshopamt-livewireamt-print_pay_req_amt-guru_pay_req_amt;*/

	if(netamt-printshopamt-livewireamt-print_pay_req_amt<0)
	{
//		netamt=netamt-printshopamt-livewireamt;
		//alert('net amt'+netamt);
		print_pay_req_amt=netamt;
		guru_pay_req_amt=0;
		netamt=0;
	}
	else 
	{
		netamt=netamt-printshopamt-livewireamt-print_pay_req_amt;
		if(netamt-guru_pay_req_amt<0)
		{
			guru_pay_req_amt=netamt;
			netamt=0;
		}
	}

//	if(printshopamt<=Math.round(netamt))
//	{
//		document.bill_UF_NJP_preview_pro.netamt.value=Math.round(netamt-printshopamt);
//	}
//	else
//	{ 
//		printshopamt=0;

		document.bill_UF_NJP_preview_pro.printshopamt.value=Math.round(printshopamt);
                document.bill_UF_NJP_preview_pro.livewireamt.value=livewireamt;
		document.bill_UF_NJP_preview_pro.print_pay_req_amt.value=print_pay_req_amt;
		document.bill_UF_NJP_preview_pro.guru_pay_req_amt.value=guru_pay_req_amt;
                //alert('netamt:'+netamt);
		document.bill_UF_NJP_preview_pro.netamt.value=Math.round(netamt);
//	}

//	if(Math.round(netamt)<0)
//		document.bill_UF_NJP_preview_pro.cmdSave.disabled=true;
//	else
//		document.bill_UF_NJP_preview_pro.cmdSave.disabled=false;

}


function getMarginTDS_AM()
{
	var amount;
	var netamt=0;
	var tdsamount=0;
	var tottds;
	var tdsrate=0;
	
	if(document.advanceprint_uf_bill_AM.tdsrate.value=="")
	{
		tdsrate =0;
	}
	else
	{
		tdsrate= eval(document.advanceprint_uf_bill_AM.tdsrate.value);
	}
	amount= eval(document.advanceprint_uf_bill_AM.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill_AM.extraamt.value);
//	if(document.advanceprint_uf_bill_AM.tottds.value=="")
//	{
//		tottds=0
//	}
//	else
//	{
//		tottds =  eval(document.advanceprint_uf_bill_AM.tottds.value);
//	}
	if(tdsrate>0)
	{
		tdsamount=(amount+extraamt)*tdsrate/100;	
	}
	else
	{
		tdsamount=0;
	}

	netamt= amount-tdsamount+extraamt;
	document.advanceprint_uf_bill_AM.tdsamt.value=tdsamount;
	document.advanceprint_uf_bill_AM.grossamt.value=Math.round(netamt);
	document.advanceprint_uf_bill_AM.netamt.value=Math.round(netamt);

	getMarginHoldUF_AM();
}
function getTDSTR()
{
	var amount;
	var netamt=0;
	var tdsamount=0;
	var tottds=0;
	var tdsrate=0;
	if(document.print_tr_bill.tdsrate.value=="")
	{
		tdsrate=0;
	}
	else
	{
		tdsrate= eval(document.print_tr_bill.tdsrate.value);
	}
	amount= eval(document.print_tr_bill.tamount.value);
	extraamt= eval(document.print_tr_bill.extraamt.value);
//	if(document.print_tr_bill.tottds.value=="")
//	{
//		tottds=0;
//	}
//	else
//	{
//		tottds =  eval(document.print_tr_bill.tottds.value);
//	}
	if(tdsrate>0)	
	{
		tdsamount=(amount+extraamt+tottds)*tdsrate/100;
	}
	netamt= amount-tdsamount+extraamt;
	document.print_tr_bill.tdsamt.value=tdsamount;
	document.print_tr_bill.netamt.value=Math.round(netamt);
	getHoldTR();
}	
function getAppTDS()
{
	var amount;
	var netamt=0;
	var tdsamount=0;
	var tottds=0;
	var tdsrate=0;
	if(document.print_app_bill.tdsrate.value=="")
	{
		tdsrate=0;
	}
	else
	{
		tdsrate= eval(document.print_app_bill.tdsrate.value);
	}
	amount= eval(document.print_app_bill.tamount.value);
	extraamt= eval(document.print_app_bill.extraamt.value);
//	if(document.print_app_bill.tottds.value=="")
//	{
//		tottds=0;
//	}
//	else
//	{
//		tottds =  eval(document.print_app_bill.tottds.value);
//	}
	if(tdsrate>0)	
	{
		tdsamount=(amount+extraamt+tottds)*tdsrate/100;
	}
	netamt= amount-tdsamount+extraamt;
	document.print_app_bill.tdsamt.value=tdsamount;
	document.print_app_bill.netamt.value=Math.round(netamt);
	getHoldAPP();
}	


function getHoldUF()
{
	var amount;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	if(document.print_uf_bill.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.print_uf_bill.holdrate.value);
	}
	amount= eval(document.print_uf_bill.tamount.value);
	extraamt= eval(document.print_uf_bill.extraamt.value);
	if(holdrate>0)
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
	netamt= amount-holdamount+extraamt;
	document.print_uf_bill.holdamt.value=holdamount;
	document.print_uf_bill.netamt.value=Math.round(netamt);
}
function getMarginHoldUF()
{
	var amount;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	var renewamt=0;
	if(document.advanceprint_uf_bill.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_uf_bill.holdrate.value);
	}
	amount= eval(document.advanceprint_uf_bill.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill.extraamt.value);
	if(holdrate>0)
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
	renewamt=document.advanceprint_uf_bill.renewamt.value;
	netamt= amount-holdamount+extraamt;
	document.advanceprint_uf_bill.holdamt.value=holdamount;
	document.advanceprint_uf_bill.grossamt.value=Math.round(netamt);
	document.advanceprint_uf_bill.netamt.value=Math.round(netamt-renewamt);
}	

/*function getMarginHoldUF_AM()
{
	var amount;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	var printshopamt=0;
        var tdsamount;
	if(document.advanceprint_uf_bill_AM.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_uf_bill_AM.holdrate.value);
	}
	amount= eval(document.advanceprint_uf_bill_AM.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill_AM.extraamt.value);
        tdsamount= document.advanceprint_uf_bill_AM.tdsamt.value;
	if(holdrate>0)
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
	
	netamt= amount-holdamount+extraamt-tdsamount;
	document.advanceprint_uf_bill_AM.holdamt.value=holdamount;
	document.advanceprint_uf_bill_AM.grossamt.value=Math.round(netamt);
	
	if(document.advanceprint_uf_bill_AM.printshopamt.value=="")
	{
		printshopamt =0;
	}
	else
	{
		printshopamt= eval(document.advanceprint_uf_bill_AM.printshopamt.value);
	}
        document.advanceprint_uf_bill_AM.netamt.value=Math.round(netamt-printshopamt);
} */
function getMarginHoldUF_AM()
{
        
        
	var amount=0;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
        var extraamt=0;
        var renewamt_am = 0;

	if(document.advanceprint_uf_bill_AM.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_uf_bill_AM.holdrate.value);
	}

	amount= eval(document.advanceprint_uf_bill_AM.tamount.value);
	extraamt= eval(document.advanceprint_uf_bill_AM.extraamt.value);
        renewamt_am=eval(document.advanceprint_uf_bill_AM.renewamt_am_org.value);


        if(netamt < 0)
            renewamt_am =0;

	if(holdrate>0) 
        {
            holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
        }
	else
        {
            holdamount=0;
        }    
	        
        netamt= amount-holdamount+extraamt-renewamt_am ;
         
	document.advanceprint_uf_bill_AM.holdamt.value=holdamount;                 
        document.advanceprint_uf_bill_AM.renewamt_am.value=renewamt_am;             
        document.advanceprint_uf_bill_AM.netamt.value=Math.round(netamt); 
        

}
function getMarginHoldUF_AM_pro()
{
        
       
	var amount=0;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
       // var extraamt=0;
        var renewamt_am = 0;

	if(document.advanceprint_uf_bill_AM_pro.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_uf_bill_AM_pro.holdrate.value);
	}

	amount= eval(document.advanceprint_uf_bill_AM_pro.tamount.value);
	//extraamt= eval(document.advanceprint_uf_bill_AM_pro.extraamt.value);
        renewamt_am=eval(document.advanceprint_uf_bill_AM_pro.renewamt_am_org.value);


        if(netamt < 0)
            renewamt_am =0;

	if(holdrate>0) 
        {
            holdamount=(amount)*holdrate/(100+holdrate);	
        }
	else
        {
            holdamount=0;
        }    
	        
        netamt= amount-holdamount-renewamt_am ;
         
	document.advanceprint_uf_bill_AM_pro.holdamt.value=holdamount;                 
        document.advanceprint_uf_bill_AM_pro.renewamt_am.value=renewamt_am;             
        document.advanceprint_uf_bill_AM_pro.netamt.value=Math.round(netamt); 
        

}

function getMarginHoldTR_AM()
{ 
	var amount=0;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
        var extraamt=0;

	if(document.advanceprint_tr_bill_AM.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_tr_bill_AM.holdrate.value);
	}

	amount= eval(document.advanceprint_tr_bill_AM.tamount.value);
	extraamt= eval(document.advanceprint_tr_bill_AM.extraamt.value);
        
	if(holdrate>0) 
        {
            holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
        }
	else
        {
            holdamount=0;
        }    
	
        netamt= amount-holdamount+extraamt;
	document.advanceprint_tr_bill_AM.holdamt.value=holdamount; 
        document.advanceprint_tr_bill_AM.netamt.value=Math.round(netamt); 
}
function getMarginHoldTR_AM_pro()
{ 
	var amount=0;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
       // var extraamt=0;

	if(document.advanceprint_tr_bill_AM_pro.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.advanceprint_tr_bill_AM_pro.holdrate.value);
	}

	amount= eval(document.advanceprint_tr_bill_AM_pro.tamount.value);
	//extraamt= eval(document.advanceprint_tr_bill_AM_pro.extraamt.value);
        
	if(holdrate>0) 
        {
            holdamount=(amount)*holdrate/(100+holdrate);	
        }
	else
        {
            holdamount=0;
        }    
	
        netamt= amount-holdamount;
	document.advanceprint_tr_bill_AM_pro.holdamt.value=holdamount; 
        document.advanceprint_tr_bill_AM_pro.netamt.value=Math.round(netamt); 
}
function getHoldUU()
{
	var amount;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	if(document.unmark_uf_bill.holdrate.value=="")
	{
		holdrate =0;
	}
	else
	{
		holdrate= eval(document.unmark_uf_bill.holdrate.value);
	}
	amount= eval(document.unmark_uf_bill.tamount.value);
	extraamt= eval(document.unmark_uf_bill.extraamt.value);
	if(holdrate>0)
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	else
		holdamount=0;
	netamt= amount-holdamount+extraamt;
	document.unmark_uf_bill.holdamt.value=holdamount;
	document.unmark_uf_bill.netamt.value=Math.round(netamt);
}	
function getHoldTR()
{
	var amount;
	var print_pay_req_amt;
	var guru_pay_req_amt;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	if(document.print_tr_bill.holdrate.value=="")
	{
		holdrate=0;
	}
	else
	{
		holdrate= eval(document.print_tr_bill.holdrate.value);
	}
	amount= eval(document.print_tr_bill.tamount.value);
	extraamt= eval(document.print_tr_bill.extraamt.value);
	print_pay_req_amt= eval(document.print_tr_bill.print_pay_req_amt.value);
	guru_pay_req_amt= eval(document.print_tr_bill.guru_pay_req_amt.value);
	if(holdrate>0)	
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	netamt= amount-holdamount+extraamt-print_pay_req_amt-guru_pay_req_amt;
	document.print_tr_bill.holdamt.value=holdamount;
	document.print_tr_bill.netamt.value=Math.round(netamt);
}	
function getHoldAPP()
{
	var amount;
	var netamt=0;
	var holdamount=0;
	var holdrate=0;
	if(document.print_app_bill.holdrate.value=="")
	{
		holdrate=0;
	}
	else
	{
		holdrate= eval(document.print_app_bill.holdrate.value);
	}
	amount= eval(document.print_app_bill.tamount.value);
	extraamt= eval(document.print_app_bill.extraamt.value);
	if(holdrate>0)	
	{
		holdamount=(amount+extraamt)*holdrate/(100+holdrate);	
	}
	netamt= amount-holdamount+extraamt;
	document.print_app_bill.holdamt.value=holdamount;
	document.print_app_bill.netamt.value=Math.round(netamt);
}	

function checkper()
{
	var totper;
	totper = eval(document.print_uf_bill.per1.value)+eval(document.print_uf_bill.per2.value)+eval(document.print_uf_bill.per3.value)+eval(document.print_uf_bill.per4.value)+
		eval(document.print_uf_bill.per5.value)+eval(document.print_uf_bill.per6.value)+eval(document.print_uf_bill.per7.value)+eval(document.print_uf_bill.per8.value)+
		eval(document.print_uf_bill.per9.value)+eval(document.print_uf_bill.per10.value);
	if(totper > 100) 
	{
		alert("Total Percentage > 100");
		document.print_uf_bill.cmdAction.value = "SPLIT";
		document.print_uf_bill.paymenttype.value = "S";
		return false;
	}
	else if(totper < 100) 
	{
		alert("Total Percentage < 100");
		document.print_uf_bill.cmdAction.value = "SPLIT";
		document.print_uf_bill.paymenttype.value = "S";
		return false;
	}
	else
	{
		document.print_uf_bill.cmdAction.value = "REPORT";
	}
}
function checktrper()
{
	var totper;
	totper = eval(document.print_tr_bill.per1.value)+eval(document.print_tr_bill.per2.value)+eval(document.print_tr_bill.per3.value)+eval(document.print_tr_bill.per4.value)+
		eval(document.print_tr_bill.per5.value)+eval(document.print_tr_bill.per6.value)+eval(document.print_tr_bill.per7.value)+eval(document.print_tr_bill.per8.value)+
		eval(document.print_tr_bill.per9.value)+eval(document.print_tr_bill.per10.value);
	if(totper > 100) 
	{
		alert("Total Percentage > 100");
		document.print_tr_bill.cmdAction.value = "SPLIT";
		document.print_tr_bill.paymenttype.value = "S";
		return false;
	}
	else if(totper < 100) 
	{
		alert("Total Percentage < 100");
		document.print_tr_bill.cmdAction.value = "SPLIT";
		document.print_tr_bill.paymenttype.value = "S";
		return false;
	}
	else
	{
		document.print_tr_bill.cmdAction.value = "REPORT";
	}
}
function checkapper()
{
	var totper;
	totper = eval(document.print_app_bill.per1.value)+eval(document.print_app_bill.per2.value)+eval(document.print_app_bill.per3.value)+eval(document.print_app_bill.per4.value)+
		eval(document.print_app_bill.per5.value)+eval(document.print_app_bill.per6.value)+eval(document.print_app_bill.per7.value)+eval(document.print_app_bill.per8.value)+
		eval(document.print_app_bill.per9.value)+eval(document.print_app_bill.per10.value);
	if(totper > 100) 
	{
		alert("Total Percentage > 100");
		document.print_app_bill.cmdAction.value = "SPLIT";
		document.print_app_bill.paymenttype.value = "S";
		return false;
	}
	else if(totper < 100) 
	{
		alert("Total Percentage < 100");
		document.print_app_bill.cmdAction.value = "SPLIT";
		document.print_app_bill.paymenttype.value = "S";
		return false;
	}
	else
	{
		document.print_app_bill.cmdAction.value = "REPORT";
	}
}

function checkName()
{
	var Inp = document.retplan.name;
	var regExp = /^[a-zA-Z]{2,30}$/;
	if(!regExp.test(Inp.value))
	{
		alert("Please Enter Proper Name");
		Inp.focus();
		return(false);
	}
	return(true);
}

function checkRetRate()
{
	var Inp = document.retplan.retrate;
	var regExp = /^[0-9]{1,3}$/;
	if(!regExp.test(Inp.value) || (Inp.value==0) )
	{
		alert("Please Enter Valid Rate of Interest at Retirement");
		Inp.focus();
		return(false);
	}
	return(true);
}
function RTrim(VALUE)
{
    var w_space = String.fromCharCode(32);
    var w_enter = String.fromCharCode(10);
    var v_length = VALUE.length;
    var strTemp = "";
    if(v_length < 0)
        return"";
    var iTemp = v_length -1;
    while(iTemp > -1)
    {
        if(VALUE.charAt(iTemp) == w_space || VALUE.charAt(iTemp) == w_enter )
            iTemp = iTemp-1;
        else
            break;
    }
    try
    {
        strTemp = VALUE.substring(0,iTemp +1);
    }
    catch(e)
    {
        strTemp=VALUE;
    }
    return strTemp;
}
function LTrim(VALUE)
{
    var w_space = String.fromCharCode(32);
    var w_enter = String.fromCharCode(10);
    if(v_length < 1)
        return"";
    var v_length = VALUE.length;
    var strTemp = "";
    var iTemp = 0;
    while(iTemp < v_length)
    {
        if(VALUE.charAt(iTemp) == w_space || VALUE.charAt(iTemp) == w_enter )
            iTemp = iTemp + 1;
        else
            break;
    }
    try
    {
        strTemp = VALUE.substring(iTemp,v_length);
    }
    catch(e)
    {
        strTemp=VALUE;
    }
    return strTemp;
}
function Trim(TRIM_VALUE)
{
    if(TRIM_VALUE.length < 1)
        return "";
    TRIM_VALUE = LTrim(TRIM_VALUE);
    TRIM_VALUE = RTrim(TRIM_VALUE);
    if(TRIM_VALUE=="")
        return "";
    else
        return TRIM_VALUE;
}
function RegExValidator(validationType, Ctrl)
{
    var CtrlValue = document.getElementById(Ctrl).value;

    switch(validationType)
    {
        case 'PAN':
            pattern = /^[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}$/;
            break;
    }
    return pattern.test(CtrlValue);
}
function validatePanNo(cntName)
{
    if(document.getElementById(cntName).value != "")
    {
        if(!RegExValidator("PAN", cntName))
        {
            alert("Please enter valid PAN.");
            document.getElementById(cntName).value="";
            document.getElementById(cntName).focus();
            return false;
        }
        else
        {
            return true;
        }
    }
    else
    {
        return true;
    }
}

///////////////// Date validation for DatePicker/Textbox type value ///////////////////
//Note: Here passed dt variable contain Date in formate: DD/MM/YYYY only.......
function is_valid_textdate(dt)
{        
    var day=dt.split('/')[0];
    var mn=dt.split('/')[1];
    var yr=dt.split('/')[2];
    if(yr == '' || day == '' || mn == '')
    {
        return false;
    }
    if(yr == 0 || day == 0 | mn == 0)
    {
        return true;
    }
    if(day>31 || day<0 || mn>12 || mn<0 || yr<0)
    {
        return false;
    }
    if((mn=='02') && (day>28))
    {
        if ((((yr % 4 == 0) && (yr % 100 != 0)) || (yr % 400 == 0)) && (day < 30))
            {
                return true;
            }
        else        
            {
                return false;        
            }
    }
    else if((mn=='04' || mn=='06' || mn=='09' || mn=='11') && (day>30))
    {        
        return false;
    }
    else
    {
        return true;            
    }

}
function check_textdate_less_or_equal_current_date(dt)
{
    var cur_date=new Date();
    var cur_year=cur_date.getYear();

    if(cur_year<999)
        cur_year+=1900;

    var cur_month=cur_date.getMonth();
    var cur_day=cur_date.getDate();
    var cdate=new Date(cur_year,cur_month,cur_day);
    var dt_day=dt.split('/')[0];
    var dt_mon=dt.split('/')[1];
    var dt_year=dt.split('/')[2];
    //alert(dt_year+"-"+dt_mon+"-"+dt_day);
    var todt = new Date(dt_year,dt_mon-1,dt_day);
    return(todt<=cdate);
}

function check_textdate_less_current_date(dt)
{
    var cur_date=new Date();
    var cur_year=cur_date.getYear();

    if(cur_year<999)
        cur_year+=1900;

    var cur_month=cur_date.getMonth();
    var cur_day=cur_date.getDate();
    var cdate=new Date(cur_year,cur_month,cur_day);
    var dt_day=dt.split('/')[0];
    var dt_mon=dt.split('/')[1];
    var dt_year=dt.split('/')[2];
    //alert(dt_year+"-"+dt_mon+"-"+dt_day);
    var todt = new Date(dt_year,dt_mon-1,dt_day);
    return(todt<cdate);
}

function check_textdate_greate_or_equal_current_date(dt)
{
    var cur_date=new Date();
    var cur_year=cur_date.getYear();

    if(cur_year<999)
        cur_year+=1900;

    var cur_month=cur_date.getMonth();
    var cur_day=cur_date.getDate();
    var cdate=new Date(cur_year,cur_month,cur_day);
    var dt_day=dt.split('/')[0];
    var dt_mon=dt.split('/')[1];
    var dt_year=dt.split('/')[2];
    //alert(dt_year+"-"+dt_mon+"-"+dt_day);
    var todt = new Date(dt_year,dt_mon-1,dt_day);
    return(todt>=cdate);
}

function check_textdate_greate_current_date(dt)
{
    var cur_date=new Date();
    var cur_year=cur_date.getYear();

    if(cur_year<999)
        cur_year+=1900;

    var cur_month=cur_date.getMonth();
    var cur_day=cur_date.getDate();
    var cdate=new Date(cur_year,cur_month,cur_day);
    var dt_day=dt.split('/')[0];
    var dt_mon=dt.split('/')[1];
    var dt_year=dt.split('/')[2];
    //alert(dt_year+"-"+dt_mon+"-"+dt_day);
    var todt = new Date(dt_year,dt_mon-1,dt_day);
    return(todt>cdate);
}

function compare_two_textdate(from_dt,to_dt)
{    
    var from_dt_day=from_dt.split('/')[0];    
    var from_dt_mon=from_dt.split('/')[1];
    var from_dt_year=from_dt.split('/')[2];
    var from_date=new Date(from_dt_year,from_dt_mon,from_dt_day);
    var to_dt_day=to_dt.split('/')[0];
    var to_dt_mon=to_dt.split('/')[1];
    var to_dt_year=to_dt.split('/')[2];
    var to_date=new Date(to_dt_year,to_dt_mon,to_dt_day);
    return(from_date>to_date);
}
////////////////////////////Date Validation End///////////////////////////
 function check_Length(fieldname,msg,len)
{
    var fname=fieldname;
    var fvalue=fieldname.value;
    if(Trim(fvalue) !='')
    {
        if(fvalue.length > len)
        {
            alert(msg);
            fname.focus();
            return false;
        }
    }
    return true;
}