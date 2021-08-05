var rule_number             ="0123456789";
var rule_float              ="0123456789.";
var rule_small              ="abcdefghijklmnopqrstuvwxyz";
var rule_capital            ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var rule_alpha              ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
var rule_alpha_number       ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
var rule_alpha_float        ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.";

var rule_global_pin         ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
var rule_global_phno        ="0123456789-()/ ";
var rule_global_comp_name   ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.'&_-/@)( ";
var rule_global_vat_no      ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-/ ";
var rule_globle_reg_no      ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789- ";


var rule_01                 ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
var rule_02                 ="~}|{`_^][@?>=<;:/.-,+*)('&%$#!\"\\";
var rule_03                 ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_~}|{`^][@=;:/.-,+*)('&$#!\\ ";
var rule_04                 ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_~}|{`^][@=;:/.-,+*)('%&$#!\\ ";
var rule_05                 ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 _@/.-'";

function compare_string_global_reg(strdata)
{
    return compare_string(rule_globle_reg_no,strdata);
}
function compare_string_global_vat(strdata)
{
    return compare_string(rule_global_vat_no,strdata);
}
function compare_string_global_pin(strdata)
{
    return compare_string(rule_global_pin,strdata);
}
function compare_string_global_comp_name(strdata)
{
    return compare_string(rule_global_comp_name,strdata);
}
function compare_string_global_phno(strdata)
{
    return compare_string(rule_global_phno,strdata);
}
function compare_string_rule_number(strdata)
{
    return compare_string(rule_number,strdata);
}
function compare_string_rule_float(strdata)
{
    return compare_string(rule_float,strdata);
}
function compare_string_rule_small(strdata)
{
    return compare_string(rule_small,strdata);
}
function compare_string_rule_capital(strdata)
{
    return compare_string(rule_capital,strdata);
}
function compare_string_rule_alpha(strdata)
{
    return compare_string(rule_alpha,strdata);
}
function compare_string_rule_alpha_number(strdata)
{
    return compare_string(rule_alpha_number,strdata);
}
function compare_string_rule_alpha_float(strdata)
{
    return compare_string(rule_alpha_float,strdata);
}
function compare_string_rule_01(strdata)
{
    return compare_string(rule_01,strdata);
}
function compare_string_rule_02(strdata)
{
    return compare_string(rule_02,strdata);
}
function compare_string_rule_03(strdata)
{
    return compare_string(rule_03,strdata);
}
function compare_string_rule_04(strdata)
{
    return compare_string(rule_04,strdata);
}
function compare_string_rule_05(strdata)
{
    return compare_string(rule_05,strdata);
}
function compare_string(strallowed,strdata)
{
	//alert("strallowed :" + strallowed + " Length :" +strallowed.length);
	//alert("strdata :" + strdata + " Length :" +strdata.length);

	var flag=false;
	for(var i=0;i<strdata.length;i++)
	{
		flag=false;

		for(var j=0;j<strallowed.length;j++)
		{
			if(strdata.charAt(i) == strallowed.charAt(j))
			{
				flag=true;
			}
		}
		if(flag==false)
		{
			return false;
		}
	}
	return true;
}
