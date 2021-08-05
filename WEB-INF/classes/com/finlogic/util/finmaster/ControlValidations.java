/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class ControlValidations
{

    public void validations(final PrintWriter pw, final String form, final String control, final String validation, final String label, final boolean ismadatory)
    {
        if ("Numeric".equals(validation))
        {
            pw.println("        if (!validate_only_number(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("Alphabet".equals(validation))
        {
            pw.println("        if (!validate_ind(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("Company".equals(validation))
        {
            pw.println("        if (!validate_comp(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("Password".equals(validation))
        {
            pw.println("        if (!validate_password(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("Age".equals(validation))
        {
            pw.println("        if (!validate_age(document." + form + ",\"" + control + "\",true,\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("PANNo".equals(validation))
        {
            pw.println("        if (!validate_panno(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("FolioNo".equals(validation))
        {
            pw.println("        if (!validate_foliono(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("FAXNo".equals(validation) || "MobileNo".equals(validation) || "PhoneNo".equals(validation))
        {
            pw.println("        if (!validate_phonno(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("Pincode".equals(validation))
        {
            pw.println("        if (!validate_pincode(document." + form + ",\"" + control + "\",\"" + label + "\",\"no\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("NAV".equals(validation))
        {
            pw.println("        if (!validate_nav(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("ChequeNo".equals(validation))
        {
            pw.println("        if (!validate_chequeno(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("Email".equals(validation))
        {
            pw.println("        if (!validate_email(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("NJbrcode".equals(validation))
        {
            pw.println("        if (!validate_njbrcode(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("DropDown".equals(validation))
        {
            pw.println("        if (!validate_dropdown(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("SelectLimit".equals(validation))
        {
            pw.println("        if (!validate_select_limit(document." + form + ",\"" + control + "\",\"" + label + "\",20," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("DateValidation".equals(validation))
        {
            pw.println("        if (!validateDate_txt(\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("LessThanFutureDate".equals(validation))
        {
            pw.println("        if (!checkFutureDateNotAllow(document." + form + ",\"" + control + "\",\"" + label + "\",\"sysdate\",\"\"))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("GreaterThanFutureDate".equals(validation))
        {
            pw.println("        if (!checkOnlyFutureDateAllow(document." + form + ",\"" + control + "\",\"" + label + "\",\"sysdate\",\"\"))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("CheckBoxValidation".equals(validation))
        {
            pw.println("        if (!validate_checkbox(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
        else if ("RadioValidation".equals(validation))
        {
            pw.println("        if (!validate_radio(document." + form + ",\"" + control + "\",\"" + label + "\"," + ismadatory + "))");
            pw.println("        {");
            pw.println("            return false;");
            pw.println("        }");
        }
    }
}
