/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import java.util.Calendar;

/**
 *
 * @author njuser
 */
public abstract class CommonFunction
{

    public static String getCurrentDate()
    {
        Calendar cal;
        cal = Calendar.getInstance();
        String strDate;
        strDate = cal.get(Calendar.DAY_OF_MONTH) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.YEAR);
        return strDate;
    }

    public static String getPreviousDate()
    {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String strDate;
        strDate = cal.get(Calendar.DAY_OF_MONTH) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.YEAR);
        return strDate;
    }
}
