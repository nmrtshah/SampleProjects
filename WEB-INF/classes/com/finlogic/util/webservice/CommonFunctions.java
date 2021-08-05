/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 *
 * @author Sonam Patel
 */
public class CommonFunctions
{

    public void getDataType(final Type type, final StringBuilder retType)
    {
        makeTypeString(type, retType);
        if (retType.charAt(retType.length() - 1) == ';')
        {
            retType.deleteCharAt(retType.length() - 1);
            retType.append("\\[\\]");
        }
    }

    private void makeTypeString(final Type mytype, final StringBuilder str)
    {
        if (mytype instanceof ParameterizedType)
        {
            ParameterizedType type;
            type = (ParameterizedType) mytype;
            String[] stmp;
            stmp = type.getRawType().toString().split("\\.");
            str.append(stmp[stmp.length - 1].replace("class ", "")).append("[ ]{0,1}<[ ]{0,1}");
            Type[] typeArguments;
            typeArguments = type.getActualTypeArguments();

            //for (Type typeArgument : typeArguments)
            for (int i = 0; i < typeArguments.length; i++)
            {
                Type typeArgument;
                typeArgument = typeArguments[i];
                if (i != 0)
                {
                    str.append(",[ ]{0,1}");
                }
                makeTypeString(typeArgument, str);
            }
            str.append("[ ]{0,1}>[ ]{0,1}");
        }
        else
        {
            String tmp = mytype.toString();
            String mTyp = tmp;

            if (tmp.contains("[I"))
            {
                mTyp = "int[]";
            }
            if (tmp.contains("[D"))
            {
                mTyp = "double[]";
            }
            if (tmp.contains("[J"))
            {
                mTyp = "long[]";
            }
            if (tmp.contains("[F"))
            {
                mTyp = "float[]";
            }
            if (tmp.contains("[B"))
            {
                mTyp = "byte[]";
            }
            if (tmp.contains("[C"))
            {
                mTyp = "char[]";
            }
            if (tmp.contains("[S"))
            {
                mTyp = "short[]";
            }
            if (tmp.contains("[Z"))
            {
                mTyp = "boolean[]";
            }
            if (tmp.contains("[Ljava.lang.String"))
            {
                mTyp = "String[]";
            }
            if (!mTyp.equals(tmp))
            {
                tmp = tmp.replaceFirst("\\[", "");
                while (tmp.contains("["))
                {
                    mTyp += "[]";
                    tmp = tmp.replaceFirst("\\[", "");
                }
            }
            mTyp = mTyp.replace("[", "@@@\\[");
            mTyp = mTyp.replace("]", "\\]");
            mTyp = mTyp.replace("@@@", "[ ]{0,1}");
            String[] stmp;
            stmp = mTyp.split("\\.");
            str.append(stmp[stmp.length - 1].replace("class ", ""));
        }
    }

    public void makeSet(final Type mytype, final Set set)
    {
        if (mytype instanceof ParameterizedType)
        {
            ParameterizedType type;
            type = (ParameterizedType) mytype;
            addInSet(set, type.getRawType().toString());
            Type[] typeArguments;
            typeArguments = type.getActualTypeArguments();

            for (Type typeArgument : typeArguments)
            {
                makeSet(typeArgument, set);
            }
        }
        else
        {
            addInSet(set, mytype.toString());
        }
    }

    private void addInSet(final Set set, final String element)
    {
        String[] pkg;
        pkg = element.split(" ");
        String elem;
        elem = pkg[pkg.length - 1];

        //For Array The Last Character is ; or []
        if (elem.charAt(elem.length() - 1) == ';')
        {
            elem = elem.substring(0, elem.length() - 1);
            //Remove [L for 1-D , [[L for 2-D , [[[L for 3-D Array and soon
            if (elem.startsWith("["))
            {
                elem = elem.replace("[", "");
                elem = elem.substring(1);
            }
        }
        if (elem.substring(elem.length() - 2).equals("[]"))
        {
            elem = elem.replace("[]", "");
        }
        String retype[];
        retype = elem.split("\\.");

        if (!(retype.length == 3 && retype[0].endsWith("java") && "lang".equals(retype[1])) && retype.length != 1)
        {
            set.add(elem);
        }
    }
}
