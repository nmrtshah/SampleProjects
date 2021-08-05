/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.oracletomysql;

import com.finlogic.business.finstudio.oracletomysql.OracleToMysqlEntityBean;
import com.finlogic.business.finstudio.oracletomysql.OracleToMysqlManager;
import com.finlogic.util.Logger;
import com.finlogic.util.finreport.DirectConnection;
import com.finlogic.util.persistence.SQLConnService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.StringUtils;

/**
 *
 * @author BHUMIKA DODIYA
 */
public class OracToMysqlConverter
{

    //private static int count = 0;
    private int count = 0;
    private boolean isToChar = false;
    private OracleToMysqlManager manager = new OracleToMysqlManager();
    private Set forVarName = new HashSet();
    private Set forCurList = new HashSet();
    private Map<String, String> forCurDetail = new HashMap<String, String>();
    private Set uniqueForCurDetail = new HashSet();
    private Set paramCurVars = new HashSet();
    private List inOutVars = new ArrayList();
    private List forCurVars = new ArrayList();

    public String[] mainMethod(String[] sqlLines, String server, String schema, String sourceType, String itemType) throws IOException, ClassNotFoundException, SQLException
    {
        String[] query = sqlLines;
        query = getArray(query);
        query = getOneLine(query);
        query = setOutPutLine(query);
        query = setExitPoint(query);
        query = createSec(query, sourceType, itemType);
        query = replaceReturn(query);
        query = declareSec(query, sourceType);
        query = setSec(query, sourceType);
        query = bgnRmSec(query, sourceType);
        query = typeSec(query, server);
        query = replaceWhile(query);
        query = replaceForLoop(query);
        query = setNewCursor(query, server, schema);
        if (!query[0].contains("Procedure contains cursor with non-select statement"))
        {
            query = setCursor(query, server);
            query = setCursorDecSec(query);
            query = setArray(query, server, schema);
            query = exImdtSec(query);
            query = setObject(query, server, schema);
            query = replaceToDate(query);
            query = replaceDateFormat(query);
            query = replaceToChar(query);
            query = queryConversion(query);
            query = setException(query);
            query = replaceElseIf(query);
            query = setBeginLabel(query);
            query = setDualStatement(query);
            query = pipeToConcat(query);
            query = setVarName(query, itemType);
        }
        return query;
    }

    private String[] pipeToConcat(String[] query)
    {
        for (int i = 0; i < query.length; i++)
        {
            query[i] = query[i].trim();
            if (query[i].startsWith("--"))
            {
                continue;
            }
            if (query[i].startsWith("/*"))
            {
                continue;
            }

            if (query[i].contains("||"))
            {
                String comment = "";
                if (query[i].contains("--"))
                {
                    comment = "\n" + query[i].substring(query[i].indexOf("--"));
                    query[i] = query[i].substring(0, query[i].indexOf("--")).trim();
                }
                while (query[i].contains("/*"))
                {
                    comment += "\n" + query[i].substring(query[i].indexOf("/*"), query[i].indexOf("*/") + 2);
                    query[i] = query[i].substring(0, query[i].indexOf("/*")) + query[i].substring(query[i].indexOf("*/") + 2);
                    query[i] = query[i].trim();
                }

                if (query[i].contains("||"))
                {
                    String pattern1 = "([\\(])";
                    String pattern2 = "([\\)])";
                    String pattern3 = "([,])";
                    String pattern4 = "(\\s+)(['])";
                    String pattern5 = "(['])(\\s+)";
                    String pattern6 = "([=])";
                    String pattern7 = "(\\s+)([\\|\\|])";
                    String pattern8 = "([\\|\\|])(\\s+)";
                    String pattern9 = "(\\s+)([\\(])(\\s+)([\\)])(\\s+)";
                    String pattern10 = "([\\(])(\\s+)([\\)])";
                    query[i] = query[i].replaceAll(pattern1, " $1 ");
                    query[i] = query[i].replaceAll(pattern2, " $1 ");
                    query[i] = query[i].replaceAll(pattern3, " $1 ");
                    query[i] = query[i].replaceAll(pattern4, "$2");
                    query[i] = query[i].replaceAll(pattern5, "$1");
                    query[i] = query[i].replaceAll(pattern6, " $1 ");
                    query[i] = query[i].replaceAll(pattern9, "$2$4");
                    query[i] = query[i].replaceAll(pattern10, "$1$3");
                    while (query[i].contains("  ") || query[i].contains("\t"))
                    {
                        query[i] = query[i].replace("\t", " ");
                        query[i] = query[i].replace("  ", " ");
                    }

                    while (query[i].indexOf('\'') != -1 && StringUtils.countOccurrencesOf(query[i], "'") >= 2)
                    {
                        String temp = query[i].substring(query[i].indexOf('\'')).trim();
                        temp = temp.replaceFirst("'", "A@B");
                        temp = temp.substring(0, temp.indexOf('\'') + 1).trim();
                        temp = temp.replaceFirst("'", "B@A");
                        temp = temp.replace(" ", "@SPACE@");
                        temp = temp.replace("(", "@OPENBR@");
                        temp = temp.replace(")", "@CLOSEBR@");
                        String src = temp.replace("A@B", "'");
                        src = src.replace("B@A", "'");
                        src = src.replace("@SPACE@", " ");
                        src = src.replace("@OPENBR@", "(");
                        src = src.replace("@CLOSEBR@", ")");
                        int repIdx = query[i].indexOf(src);
                        if (repIdx != -1)
                        {
                            query[i] = query[i].substring(0, repIdx) + " " + temp + " " + query[i].substring(repIdx + src.length());
                        }
                    }
                    query[i] = query[i].replaceAll(pattern7, "$2");
                    query[i] = query[i].replaceAll(pattern8, "$1");

                    while (query[i].contains("  "))
                    {
                        query[i] = query[i].replace("  ", " ");
                    }
                    while (query[i].contains("B@A A@B"))
                    {
                        query[i] = query[i].replace("B@A A@B", "B@A@SPACE@A@B");
                    }

                    String[] parts = query[i].split("\\|\\|");
                    for (int j = 0; j < parts.length; j++)
                    {
                        while (parts[j].contains("(") && parts[j].contains(")") && parts[j].indexOf('(') < parts[j].lastIndexOf(')'))
                        {
                            String tmp = getInnerMostString(parts[j]);
                            if (tmp != null)
                            {
                                tmp = tmp.trim();
                                tmp = tmp.replace("(", "@OPENBR@");
                                tmp = tmp.replace(")", "@CLOSEBR@");
                                String org = tmp.substring(8, tmp.length() - 9);
                                org = "(" + org + ")";
                                tmp = tmp.replace(" ", "@SPACE@");
                                parts[j] = parts[j].replace(org, tmp);

                                int idx = parts[j].indexOf("@OPENBR@");
                                if (idx != -1 && idx != 0)
                                {
                                    if (parts[j].charAt(idx - 1) == ' ')
                                    {
                                        parts[j] = parts[j].substring(0, idx - 1) + "@SPACE@" + parts[j].substring(idx);
                                    }
                                }

                                idx = parts[j].indexOf("@CLOSEBR@");
                                if (idx != -1 && idx != parts[j].length() - 9)
                                {
                                    if (parts[j].charAt(idx + 9) == ' ')
                                    {
                                        parts[j] = parts[j].substring(0, idx + 9) + "@SPACE@" + parts[j].substring(idx + 9);
                                    }
                                }
                            }
                        }
                    }
                    query[i] = "";
                    for (int j = 0; j < parts.length; j++)
                    {
                        query[i] += (parts[j] + "||");
                    }
                    query[i] = query[i].substring(0, query[i].length() - 2);

                    while (query[i].indexOf("||") != -1)
                    {
                        String concat = query[i];
                        if (concat.indexOf("||") != -1)
                        {
                            while (concat.indexOf(' ') != -1 && concat.indexOf(' ') < concat.indexOf("||"))
                            {
                                concat = concat.substring(concat.indexOf(' ')).trim();
                            }
                            if (concat.contains("\n"))
                            {
                                concat = concat.substring(0, concat.indexOf('\n'));
                            }
                        }
                        if (concat.indexOf(' ') != -1)
                        {
                            concat = concat.substring(0, concat.indexOf(' ')).trim();
                        }
                        if (concat.endsWith(";"))
                        {
                            concat = concat.substring(0, concat.length() - 1).trim();
                        }
                        if (concat.endsWith("*/"))
                        {
                            concat = concat.substring(0, concat.length() - 2).trim();
                        }
                        String target = "CONCAT(" + concat.replace("||", ",") + ") ";

                        target = target.replace("A@B", "'");
                        target = target.replace("B@A", "'");
                        target = target.replace("@SPACE@", " ");
                        target = target.replace("@OPENBR@", "(");
                        target = target.replace("@CLOSEBR@", ")");
                        while (target.contains("  "))
                        {
                            target = target.replace("  ", " ");
                        }

                        int repIdx = query[i].indexOf(concat);
                        if (repIdx != -1)
                        {
                            query[i] = query[i].substring(0, repIdx) + target + query[i].substring(repIdx + concat.length());
                        }
                    }
                    query[i] = query[i].replace("A@B", "'");
                    query[i] = query[i].replace("B@A", "'");
                    query[i] = query[i].replace("@SPACE@", " ");
                    query[i] = query[i].replace("@OPENBR@", "(");
                    query[i] = query[i].replace("@CLOSEBR@", ")");
                    query[i] = query[i].replace("  ", " ");

                    while (query[i].contains("' '"))
                    {
                        query[i] = query[i].replace("' '", "''");
                    }
                    query[i] = query[i].replace("( ", "(");
                    query[i] = query[i].replace(" )", ")");
                }
                query[i] = query[i].replace(": =", ":=");
                query[i] = query[i] + comment;
            }
        }

        for (int i = query.length - 1; i >= 0; i--)
        {
            query[i] = query[i].trim();
            if (query[i].startsWith("--") || query[i].startsWith("/*"))
            {
                continue;
            }

            if (query[i].contains("CONCAT(") && i > 0)
            {
                int j = i - 1;
                while ((query[j].startsWith("--") || query[j].startsWith("/*") || query[j].trim().equals("\n") || query[j].trim().equals("")) && j > 0)
                {
                    j--;
                }
                query[j] = query[j].trim();
                if (query[j].contains("CONCAT(") && j > 0)
                {
                    String comment = "";
                    if (query[i].contains("--"))
                    {
                        comment = "\n" + query[i].substring(query[i].indexOf("--"));
                        query[i] = query[i].substring(0, query[i].indexOf("--")).trim();
                    }
                    if (query[i].contains("/*"))
                    {
                        comment += "\n" + query[i].substring(query[i].indexOf("/*"), query[i].indexOf("*/") + 2);
                        query[i] = query[i].substring(0, query[i].indexOf("/*")) + query[i].substring(query[i].indexOf("*/") + 2);
                        query[i] = query[i].trim();
                    }

                    if (query[i].startsWith("CONCAT(") && query[j].contains("CONCAT(") && !query[j].endsWith(");"))
                    {
                        if (query[j].endsWith(")"))
                        {
                            query[j] = query[j].substring(0, query[j].length() - 1);
                            query[i] = query[i].substring(7, query[i].length());
                        }
                    }
                    query[i] = query[i] + comment;
                }
            }
        }
        return query;
    }

    private String getInnerMostString(String parseMe)
    {
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("\\([^()]*\\)");
        Matcher regexMatcher = regex.matcher(parseMe);

        while (regexMatcher.find())
        {
            matchList.add(regexMatcher.group());
        }
        if (matchList != null && matchList.size() > 0)
        {
            return matchList.get(0);
        }
        else
        {
            return null;
        }
    }

    private String[] setOutPutLine(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].trim().toUpperCase().startsWith("DBMS_"))
                {
                    query[i] = "--" + query[i];
                }
                if (query[i].trim().toUpperCase().startsWith("NULL"))
                {
                    query[i] = "--" + query[i];
                }
                if (query[i].trim().equalsIgnoreCase("DECLARE"))
                {
                    query[i] = "--" + query[i];
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setException(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].trim().startsWith("--") || query[i].trim().startsWith("/*"))
                {
                    continue;
                }
                int beginCounter = 0;
                int exceptionLineCount = 0;
                if (query[i].trim().toUpperCase().startsWith("EXCEPTION"))
                {
                    for (int j = i; j < query.length; j++)
                    {
                        if (query[j].trim().toUpperCase().startsWith("BEGIN"))
                        {
                            beginCounter++;
                        }
                        if (query[j].trim().equalsIgnoreCase("END") || query[j].trim().equalsIgnoreCase("END;")
                                || query[j].trim().equalsIgnoreCase("END ;")
                                || query[j].trim().toUpperCase().contains("END;"))
                        {
                            if (beginCounter == 0)
                            {
                                break;
                            }
                            else
                            {
                                beginCounter--;
                            }
                        }
                        if (query[j].indexOf("*/") > -1)
                        {
                            query[j] = query[j].replaceAll("\\*/", "");
                        }
                        exceptionLineCount++;
                    }
                    //replace with exception part with comments                 
                    query[i] = "/*" + query[i];
                    if (query[i].toUpperCase().contains("END;"))
                    {
                        query[i] = query[i].replace("END;", "*/ END;");
                        query[i] = query[i].replace("end;", "*/ end;");
                    }
                    else
                    {
                        query[i + exceptionLineCount - 1] = query[i + exceptionLineCount - 1] + "*/";
                    }
                    i += exceptionLineCount;
                }
                //else if (query[i].trim().toUpperCase().startsWith("RAISE_APPLICATION_ERROR"))
                else if (query[i].trim().toUpperCase().startsWith("RAISE"))
                {
                    query[i] = "--" + query[i];
                }
                else if (query[i].trim().toUpperCase().endsWith("EXCEPTION;") || query[i].trim().toUpperCase().endsWith("EXCEPTION ;"))
                {
                    query[i] = "--" + query[i];
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String rmSpc(String sqlLine)
    {
        String query = sqlLine;
        try
        {
            query = query.replaceAll("\t", " ");
            while (query.contains("  "))
            {
                query = query.replaceAll("  ", " ");
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query.trim();
    }

    private String[] createSec(String[] query, String sourceType, String itemType)
    {
        try
        {
            if (sourceType.equals("usingConn"))
            {
                //if create stmt does not contains () then need to add             
                for (int i = 0; i < query.length; i++)
                {
                    int point = 0;
                    if (query[i].toUpperCase().contains("CREATE " + itemType))
                    {
                        point = i;
                    }
                    if (query[i].toUpperCase().contains("CREATE " + itemType) && query[i].indexOf('(') > -1)
                    {
                        break;
                    }
                    else
                    {
                        if (query[i].toUpperCase().contains("CREATE") && query[i].toUpperCase().contains(itemType))
                        {
                            if (query[i].toUpperCase().indexOf(") IS") > -1 || query[i].toUpperCase().indexOf(")IS") > -1 || query[i].toUpperCase().indexOf(") AS") > -1 || query[i].toUpperCase().indexOf(")AS") > -1)
                            {
                                break;
                            }
                            else if (query[i].toUpperCase().indexOf(" IS") > -1)
                            {
                                int idx = query[point].toUpperCase().indexOf(" IS");
                                query[point] = query[point].substring(0, idx) + "()" + query[point].substring(idx);
                                break;
                            }
                            else if (query[i].toUpperCase().indexOf(" AS") > -1)
                            {
                                int idx = query[point].toUpperCase().indexOf(" AS");
                                query[point] = query[point].substring(0, idx) + "()" + query[point].substring(idx);
                                break;
                            }
                        }
                        while (query[i].trim().startsWith("/*") || query[i].trim().startsWith("--"))
                        {
                            i++;
                            continue;
                        }
                        if (query[i].indexOf('(') > -1)
                        {
                            break;
                        }
                        if (!query[i].toUpperCase().contains("CREATE")
                                && !query[i].toUpperCase().contains(itemType)
                                && (query[i].toUpperCase().contains("IS") || query[i].toUpperCase().contains("AS")))
                        {
                            query[point] = query[point] + "()";
                            break;
                        }
                    }
                }
                //skip lines till is or as not found
                int loopCount = 0;
                for (int k = 0; k < query.length; k++)
                {
                    //skip all comment lines
                    if (query[k].trim().startsWith("--"))
                    {
                        continue;
                    }
                    else if (query[k].trim().startsWith("/*") && !query[k].trim().startsWith("/* TCIGBF"))
                    {
                        continue;
                    }
                    if (query[k].contains("(") || (query[k].contains(")") && loopCount > 0))
                    {
                        for (int idx = 0; idx < query[k].length(); idx++)
                        {
                            if (query[k].charAt(idx) == '(')
                            {
                                loopCount++;
                            }
                            else if (query[k].charAt(idx) == ')')
                            {
                                loopCount--;
                            }
                        }
                    }
                    if (query[k].contains(")") && loopCount == 0)
                    {
                        count = k;
                        break;
                    }
                }
                for (int i = 0; i <= count; i++)
                {
                    //skip all comment lines
                    if (query[i].trim().startsWith("--"))
                    {
                        continue;
                    }
                    else if (query[i].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF"))
                    {
                        continue;
                    }

                    if (!query[i].toUpperCase().contains("CREATE") && query[i].toUpperCase().contains(itemType))
                    {
                        query[i] = "CREATE " + query[i];
                    }
                    //replace doubleqoute from procedure name
                    if (query[i].toUpperCase().contains(itemType))
                    {
                        if (query[i].contains("\""))
                        {
                            query[i] = query[i].replace("\"", "");
                            query[i] = query[i].replace("()()", "()");
                        }
                    }

                    if (query[i].toUpperCase().contains("CREATE"))
                    {
                        if (query[i].toUpperCase().contains("OR REPLACE"))
                        {
                            query[i] = query[i].replace("CREATE OR REPLACE", "CREATE");
                            query[i] = query[i].replace("create or replace", "CREATE");
                        }
                    }
                    //remove IN from variables if item is function
                    if (itemType.equalsIgnoreCase("FUNCTION") && (query[i].toUpperCase().contains(" IN ") || query[i].toUpperCase().contains(" OUT ")))
                    {
                        query[i] = query[i].replace(" IN ", " ");
                        query[i] = query[i].replace(" OUT ", " ");
                    }

                    if (query[i].contains("(") && query[i].contains(")")
                            && (query[i].toUpperCase().contains("VARCHAR2")
                            || query[i].toUpperCase().contains("VARCHAR")
                            || query[i].toUpperCase().contains("DATE")
                            || query[i].toUpperCase().contains("NUMBER")
                            || query[i].toUpperCase().contains("CHAR")
                            || query[i].toUpperCase().contains("FLOAT")
                            || query[i].toUpperCase().contains("LONG")
                            || query[i].toUpperCase().contains("INTEGER")
                            || query[i].toUpperCase().contains("DOUBLE PRECISION")
                            || query[i].toUpperCase().contains("BOOLEAN")
                            || query[i].toUpperCase().contains("INT")))
                    {
                        String sString = "";
                        if (query[i].indexOf('(') + 1 < query[i].indexOf(')'))
                        {
                            sString = query[i].substring(query[i].indexOf('(') + 1, query[i].indexOf(')'));
                        }
                        String[] allVars = sString.split(",");
                        StringBuilder newVar = new StringBuilder();
                        for (int k = 0; k < allVars.length; k++)
                        {
                            allVars[k] = replaceVar(allVars[k]);
                            allVars[k] = replaceDataType(allVars[k]);
                            newVar.append(allVars[k]);
                            newVar.append(",");
                            inOutVars.add(allVars[k]);
                        }
                        newVar.deleteCharAt(newVar.length() - 1);
                        query[i] = query[i].replace(sString, newVar);
                    }
                    //if (query[i].toUpperCase().contains("IN") && query[i].toUpperCase().contains("OUT"))
                    if ((query[i].toUpperCase().contains("IN OUT") || query[i].toUpperCase().contains("OUT IN")) && !query[i].toUpperCase().contains("OUT INT"))
                    {
                        query[i] = query[i].replaceAll("IN", " ");
                        query[i] = query[i].replaceAll("in", " ");
                        query[i] = query[i].replaceAll("OUT", " ");
                        query[i] = query[i].replaceAll("out", " ");
                        if (query[i].trim().startsWith("/*"))
                        {
                            query[i] = query[i].replace("/*", " ");
                            query[i] = "/*INOUT " + query[i];
                        }
                        else
                        {
                            String varName = "";
                            int tmpCount = count;
                            if (query[i].trim().toUpperCase().contains("CREATE ")
                                    && query[i].trim().toUpperCase().contains(itemType + " ")
                                    && query[i].indexOf('(') > -1)
                            {
                                varName = query[i].substring(query[i].indexOf('(') + 1);
                            }
                            else
                            {
                                varName = query[i];
                            }
                            String newVarName = "";
                            StringBuilder tmpStr = new StringBuilder();
                            if (varName.indexOf(',') != varName.lastIndexOf(','))
                            {
                                String[] allVars = varName.split(",");
                                for (int k = 0; k < allVars.length; k++)
                                {
                                    allVars[k] = replaceVar(allVars[k]) + ",";
                                    allVars[k] = replaceDataType(allVars[k]);
                                    //newVarName += allVars[k];
                                    tmpStr.append(allVars[k]);
                                    inOutVars.add(allVars[k]);
                                }
                                newVarName = tmpStr.toString();
                                int cnt = i;
                                while (query[cnt].trim().startsWith("--") || query[cnt].trim().startsWith("/*"))
                                {
                                    tmpCount--;
                                    cnt++;
                                }
                                if (!query[i + 1].toUpperCase().contains(" IN ") && !query[i + 1].toUpperCase().contains(" OUT ")
                                        && (i == tmpCount || i == tmpCount - 1))
                                {

                                    newVarName = newVarName.substring(0, newVarName.length() - 1);
                                }
                            }
                            else
                            {
                                newVarName = replaceVar(varName);
                                newVarName = replaceDataType(newVarName);
                                inOutVars.add(newVarName);
                            }
                            query[i] = query[i].replace(varName, newVarName);
                        }
                    }
                    if ((query[i].toUpperCase().contains(" IN ") || query[i].toUpperCase().contains(" OUT "))
                            && (query[i].toUpperCase().contains("VARCHAR2")
                            || query[i].toUpperCase().contains("VARCHAR")
                            || query[i].toUpperCase().contains("DATE")
                            || query[i].toUpperCase().contains("NUMBER")
                            || query[i].toUpperCase().contains("CHAR")
                            || query[i].toUpperCase().contains("FLOAT")
                            || query[i].toUpperCase().contains("LONG")
                            || query[i].toUpperCase().contains("INTEGER")
                            || query[i].toUpperCase().contains("DOUBLE PRECISION")
                            || query[i].toUpperCase().contains("BOOLEAN")
                            || query[i].toUpperCase().contains("INT")))
                    {
                        int tmpCount = count;
                        String varName = "";
                        if (query[i].trim().toUpperCase().contains("CREATE ")
                                && query[i].trim().toUpperCase().contains(itemType + " ") && query[i].indexOf('(') > -1)
                        {
                            varName = query[i].substring(query[i].indexOf('(') + 1);
                        }
                        else
                        {
                            varName = query[i];
                        }

                        String newVarName = "";
                        StringBuilder tmpStr = new StringBuilder();
                        if (varName.indexOf(',') != varName.lastIndexOf(','))
                        {
                            String[] allVars = varName.split(",");
                            for (int k = 0; k < allVars.length; k++)
                            {
                                allVars[k] = replaceVar(allVars[k]) + ",";
                                allVars[k] = replaceDataType(allVars[k]);
                                //newVarName += allVars[k];
                                tmpStr.append(allVars[k]);
                                inOutVars.add(allVars[k]);
                            }
                            newVarName = tmpStr.toString();
                            int cnt = i;
                            while (query[cnt].trim().startsWith("--") || query[cnt].trim().startsWith("/*"))
                            {
                                tmpCount--;
                                cnt++;
                            }
                            if (!query[i + 1].toUpperCase().contains(" IN ") && !query[i + 1].toUpperCase().contains(" OUT ")
                                    && (i == tmpCount || i == tmpCount - 1))
                            {

                                newVarName = newVarName.substring(0, newVarName.length() - 1);
                            }
                        }
                        else
                        {
                            newVarName = replaceVar(varName);
                            newVarName = replaceDataType(newVarName);
                            inOutVars.add(newVarName);
                        }
                        query[i] = query[i].replace(varName, newVarName);
                    }
                    //get in/out params for forloop cursor query
                    if (query[i].toUpperCase().contains("VARCHAR2")
                            || query[i].toUpperCase().contains("VARCHAR")
                            || query[i].toUpperCase().contains("DATE")
                            || query[i].toUpperCase().contains("NUMBER")
                            || query[i].toUpperCase().contains("CHAR")
                            || query[i].toUpperCase().contains("FLOAT")
                            || query[i].toUpperCase().contains("LONG")
                            || query[i].toUpperCase().contains("INTEGER")
                            || query[i].toUpperCase().contains("DOUBLE PRECISION")
                            || query[i].toUpperCase().contains("BOOLEAN")
                            || query[i].toUpperCase().contains("INT"))
                    {
                        inOutVars.add(query[i]);
                    }
                    //replace oracle datatype with mysql datatype
                    query[i] = replaceDataType(query[i]);
                    if (query[i].contains(":="))
                    {
                        query[i] = query[i].replaceAll(":=", " DEFAULT ");
                    }
                    if (query[i].toUpperCase().contains("DEFAULT"))
                    {
                        String defaultStr = query[i].substring(query[i].trim().toUpperCase().indexOf("DEFAULT"));
                        if (defaultStr.contains(","))
                        {
                            query[i] = query[i].replace(query[i].subSequence(query[i].trim().toUpperCase().indexOf("DEFAULT"), query[i].trim().length() - 1), " ");
                        }
                        else if (defaultStr.contains(")"))
                        {
                            query[i] = query[i].replace(query[i].subSequence(query[i].trim().toUpperCase().indexOf("DEFAULT"), query[i].trim().length() - 1), " ");
                        }
                        else
                        {
                            query[i] = query[i].replace(query[i].subSequence(query[i].trim().toUpperCase().indexOf("DEFAULT"), query[i].trim().length()), " ");
                        }
                    }
                }
                while (!query[count].trim().toUpperCase().endsWith("AS") && !query[count].trim().toUpperCase().endsWith("IS")
                        && !query[count].trim().toUpperCase().endsWith("IS()") && !query[count].trim().toUpperCase().endsWith("AS()")
                        && count < query.length)
                {
                    count++;
                }
                query[count] = query[count].replace(")AS", ") AS");
                query[count] = query[count].replace(")IS", ") IS");
                //replace as or is                            
                if (query[count].toUpperCase().trim().startsWith("IS"))
                {
                    query[count] = replaceAsIS(query[count], "IS");
                }
                else if (query[count].toUpperCase().contains(" IS"))
                {
                    query[count] = replaceAsIS(query[count], " IS");
                }
                else if (query[count].toUpperCase().trim().startsWith("AS"))
                {
                    query[count] = replaceAsIS(query[count], "AS");
                    query[count] = replaceAsIS(query[count], "As");
                    query[count] = replaceAsIS(query[count], "aS");
                }
                else if (query[count].toUpperCase().contains(" AS"))
                {
                    query[count] = replaceAsIS(query[count], " AS");
                    query[count] = replaceAsIS(query[count], "aS");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceReturn(String[] query)
    {
        for (int i = 0; i <= count; i++)
        {
            if (query[i].toUpperCase().contains("RETURN"))
            {
                query[i] = query[i].replace("RETURN ", "RETURNS ");
                query[i] = query[i].replace("return", "RETURNS ");
                query[i] = replaceDataType(query[i]);
            }
        }
        return query;
    }

    private String replaceVar(String sqlLine)
    {
        String query = sqlLine;
        if ((query.toUpperCase().contains("IN OUT") || query.toUpperCase().contains("OUT IN")) && !query.toUpperCase().contains("OUT INT"))
        {
            query = query.replace("IN", " ");
            query = query.replace("in", " ");
            query = query.replace("OUT", " ");
            query = query.replace("out", " ");
            if (query.trim().startsWith("/*"))
            {
                query = query.replace("/*", " ");
                query = "/*INOUT " + query;
            }
            else
            {
                query = "INOUT " + query;
            }
        }

        if (query.toUpperCase().contains(" IN "))
        {
            query = query.replaceAll(" IN ", " ");
            query = query.replaceAll(" in ", " ");
            if (query.trim().startsWith("/*"))
            {
                query = query.replace("/*", " ");
                query = "/*IN " + query;
            }
            else
            {
                query = "IN " + query;
                query = query.replace("IN (", "( IN ");
            }
        }
        if (query.toUpperCase().contains(" OUT "))
        {
            query = query.replaceAll(" OUT ", " ");
            query = query.replaceAll(" out ", " ");
            if (query.trim().startsWith("/*"))
            {
                query = query.replace("/*", " ");
                query = "/*OUT " + query;
            }
            else
            {
                query = "OUT " + query;
            }
        }
//        if (query.contains(":="))
//        {
//            query = query.replaceAll(":=", " DEFAULT ");
//        }
        return query;
    }

    private String[] declareSec(String[] query, String sourceType)
    {
        try
        {
            if (sourceType.equals("usingConn"))
            {
                for (int i = count; i < query.length; i++)
                {
                    //skip all comment lines
                    if (query[i].trim().startsWith("--"))
                    {
                        continue;
                    }
                    query[i] = query[i].replace("  ", " ");

                    if (query[i].toUpperCase().contains(" VARCHAR2")
                            || query[i].toUpperCase().contains(" VARCHAR")
                            || query[i].toUpperCase().contains(" NUMBER")
                            || query[i].toUpperCase().contains(" INT;")
                            || query[i].toUpperCase().contains(" INT ")
                            || query[i].toUpperCase().contains(" INT:=")
                            || query[i].toUpperCase().contains(" DATE")
                            || query[i].toUpperCase().contains(" FLOAT")
                            || query[i].toUpperCase().contains(" CHAR")
                            || query[i].toUpperCase().contains(" BOOLEAN")
                            || query[i].toUpperCase().contains(" LONG")
                            || query[i].toUpperCase().contains(" INTEGER")
                            || query[i].toUpperCase().contains(" DOUBLE PRECISION"))
                    {
                        if (query[i].trim().startsWith("--") || query[i].trim().startsWith("/*"))
                        {
                            continue;
                        }
                        else
                        {
                            if (query[i].indexOf(';') != query[i].lastIndexOf(';'))
                            {
                                String[] tmpStr = query[i].split(";");
                                StringBuilder str = new StringBuilder();
                                for (int k = 0; k < tmpStr.length; k++)
                                {
                                    tmpStr[k] = "DECLARE " + tmpStr[k];
                                    tmpStr[k] = replaceDataType(tmpStr[k]);
                                    //str += tmpStr[k] + ";\n";
                                    str.append(tmpStr[k]);
                                    str.append(";\n");
                                }
                                query[i] = str.toString();
                            }
                            else
                            {
                                query[i] = "DECLARE " + query[i];
                            }
                        }

                        if (query[i].contains(":="))
                        {
                            query[i] = query[i].replaceAll(":=", " DEFAULT ");
                        }
                        //replace oracle datatype with mysql dataype                    
                        query[i] = replaceDataType(query[i]);
                    }
                    if (i > count && (query[i].toUpperCase().trim().startsWith("BEGIN")))
                    {
                        count = i;
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setSec(String[] query, String sourceType)
    {
        try
        {
            if (sourceType.equals("usingConn"))
            {
                for (int i = count; i < query.length; i++)
                {
                    if (query[i].trim().startsWith("--"))
                    {
                        continue;
                    }
                    if (query[i].contains(":="))
                    {
                        if (query[i].trim().startsWith("/*"))
                        {
                            query[i] = query[i].replace("/*", " ");
                            query[i] = query[i].replace(":=", " = ");
                            query[i] = "/*SET " + query[i];
                        }
                        else
                        {
                            if (query[i].toUpperCase().contains("IF ")
                                    && query[i].toUpperCase().contains("THEN"))
                            {
                                String varToReplace = query[i].substring(query[i].toUpperCase().indexOf("THEN") + 4);
                                query[i] = query[i].replace(varToReplace, " SET " + varToReplace);
                                query[i] = query[i].replace(":=", " = ");
                            }
                            else if (query[i].trim().toUpperCase().startsWith("THEN"))
                            {
                                String varToReplace = query[i].substring(query[i].toUpperCase().indexOf("THEN") + 4);
                                query[i] = query[i].replace(varToReplace, " SET " + varToReplace);
                                query[i] = query[i].replace(":=", " = ");
                            }
                            //else if (query[i].toUpperCase().contains("ELSE"))
                            else if (query[i].trim().toUpperCase().startsWith("ELSE"))
                            {
                                String varToReplace = query[i].substring(query[i].toUpperCase().indexOf("ELSE") + 4);
                                query[i] = query[i].replace(varToReplace, " SET " + varToReplace);
                                query[i] = query[i].replace(":=", " = ");
                            }
                            else if (query[i].toUpperCase().trim().startsWith("FOR ")
                                    && query[i].toUpperCase().contains(" IN")
                                    && !query[i].toUpperCase().contains("..")
                                    && query[i].toUpperCase().contains("LOOP"))
                            {
                                String tmpStr = query[i].substring(0, query[i].lastIndexOf("ABCPQRXYZ") + 10);
                                tmpStr += " SET " + query[i].substring(query[i].lastIndexOf("ABCPQRXYZ") + 10);
                                query[i] = tmpStr;
                                query[i] = query[i].replace(":=", " = ");
                            }
                            else
                            {
                                query[i] = "SET " + query[i];
                                query[i] = query[i].replace(":=", " = ");
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String replaceAsIS(String sqlLine, String replaceStr)
    {
        String query = sqlLine;
        if (query.contains(replaceStr + "()"))
        {
            query = query.replace(replaceStr + "()", "() " + replaceStr);
        }
        if (query.contains(replaceStr.toLowerCase()) && !query.contains("/* TCIGBF"))
        {
            query = "/* TCIGBF Please do not remove this line \n" + new Date() + " */ \n" + query.replace(replaceStr.toLowerCase(), " BEGIN0:BEGIN\n");
        }
        else if (query.contains(replaceStr.toUpperCase()) && !query.contains("/* TCIGBF"))
        {
            query = "/* TCIGBF Please do not remove this line \n" + new Date() + " */ \n" + query.replace(replaceStr.toUpperCase(), " BEGIN0:BEGIN\n");
        }
        else if (query.contains(replaceStr) && !query.contains("/* TCIGBF"))
        {
            query = "/* TCIGBF Please do not remove this line \n" + new Date() + " */ \n" + query.replace(replaceStr, " BEGIN0:BEGIN\n");
        }
        return query;
    }

    private String[] bgnRmSec(String[] query, String sourceType)
    {
        try
        {
            if (sourceType.equals("usingConn"))
            {
                for (int i = count; i < query.length; i++)
                {
                    if (query[i].trim().startsWith("--"))
                    {
                        continue;
                    }

                    if (query[i].toUpperCase().trim().contains("BEGIN"))
                    {
                        query[i] = query[i].replace("BEGIN", "");
                        query[i] = query[i].replace("begin", "");
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] exImdtSec(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                String queryVar = "";
                String intoVar = "";
                String[] varList;
                StringBuilder prStr = new StringBuilder();
                String setVar = "";
                String temp = "";
                StringBuilder tmpStr = new StringBuilder();
                int usingVarLen = 0;

                if (query[i].trim().startsWith("--") || query[i].trim().startsWith("/*"))
                {
                    continue;
                }

                if (query[i].trim().toUpperCase().startsWith("EXECUTE IMMEDIATE"))
                {
                    temp = query[i].trim();
                    temp = temp.replace("INSERT INTO", "INSERT ZXYCBA");
                    temp = temp.replace("insert into", "INSERT ZXYCBA");
                    int a = temp.toUpperCase().indexOf("EXECUTE IMMEDIATE");
                    if (temp.toUpperCase().contains("INTO") && temp.toUpperCase().contains("USING"))
                    {
                        int b = temp.toUpperCase().indexOf("INTO");
                        queryVar = temp.substring(a + 17, b);

                        a = temp.toUpperCase().indexOf("INTO");
                        b = temp.toUpperCase().indexOf("USING");
                        intoVar = temp.substring(a + 4, b);

                        //remove comment from var name
                        if (intoVar.contains("--"))
                        {
                            intoVar = intoVar.substring(0, intoVar.indexOf("--"));
                        }
                        if (intoVar.indexOf(',') > -1)
                        {
                            String queryName = temp.substring(temp.toUpperCase().indexOf("EXECUTE IMMEDIATE") + 17, temp.toUpperCase().indexOf("INTO"));
                            if (queryName.toUpperCase().contains("SELECT") && queryName.toUpperCase().contains("FROM"))
                            {
                                String[] allIntoVar = intoVar.split(",");
                                String newIntoVar = "";
                                tmpStr = new StringBuilder();
                                for (int j = 0; j < allIntoVar.length; j++)
                                {
                                    allIntoVar[j] = "@" + allIntoVar[j] + ",";
                                    //newIntoVar += allIntoVar[j];
                                    tmpStr.append(allIntoVar[j]);
                                }
                                newIntoVar = tmpStr.toString();
                                newIntoVar = newIntoVar.substring(0, newIntoVar.length() - 1);
                                String subStr = queryName.substring(0, queryName.toUpperCase().indexOf(" FROM "));
                                String newSubStr = subStr + " INTO " + newIntoVar + " ";
                                queryVar = queryName.replace(subStr, newSubStr);
                                intoVar = "temp" + i;
                            }
                            else
                            {
                                for (int k = 0; k < query.length; k++)
                                {
                                    if (query[k].trim().startsWith("SET " + queryName.trim() + " =") || (query[k].trim().startsWith("DECLARE " + queryName.trim()) && query[k].toUpperCase().contains("DEFAULT")))
                                    {
                                        String[] allIntoVar = intoVar.split(",");
                                        String newIntoVar = "";
                                        tmpStr = new StringBuilder();
                                        for (int j = 0; j < allIntoVar.length; j++)
                                        {
                                            allIntoVar[j] = "@" + allIntoVar[j].trim() + ",";
                                            //newIntoVar += allIntoVar[j];
                                            tmpStr.append(allIntoVar[j]);
                                        }
                                        newIntoVar = tmpStr.toString();
                                        newIntoVar = newIntoVar.substring(0, newIntoVar.length() - 1);
                                        query[k] = query[k].replace(" FROM ", " INTO " + newIntoVar + " FROM ");
                                        intoVar = "temp" + i;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if (temp.toUpperCase().contains("INTO") && !temp.toUpperCase().contains("USING"))
                    {
                        queryVar = temp.substring(a + 17, temp.toUpperCase().indexOf("INTO"));
                        //intoVar = "temp" + i;
                        String queryIntoVar = temp.substring(temp.toUpperCase().indexOf("INTO") + 4, temp.lastIndexOf(';'));
                        intoVar = queryIntoVar;
                        if (queryIntoVar.indexOf(',') > -1)
                        {
                            String queryName = temp.substring(temp.toUpperCase().indexOf("EXECUTE IMMEDIATE") + 17, temp.toUpperCase().indexOf("INTO"));
                            if (queryName.toUpperCase().contains("SELECT") && queryName.toUpperCase().contains("FROM"))
                            {
                                String[] allIntoVar = intoVar.split(",");
                                String newIntoVar = "";
                                tmpStr = new StringBuilder();
                                for (int j = 0; j < allIntoVar.length; j++)
                                {
                                    allIntoVar[j] = "@" + allIntoVar[j] + ",";
                                    newIntoVar += allIntoVar[j];
                                }
                                newIntoVar = newIntoVar.substring(0, newIntoVar.length() - 1);
                                String subStr = queryName.substring(0, queryName.toUpperCase().indexOf(" FROM "));
                                String newSubStr = subStr + " INTO " + newIntoVar + " ";
                                queryVar = queryName.replace(subStr, newSubStr);
                                intoVar = "temp" + i;
                            }
                            else
                            {
                                for (int k = 0; k < query.length; k++)
                                {
                                    if (query[k].trim().startsWith("SET " + queryName.trim() + " ="))
                                    {
                                        String[] allIntoVar = queryIntoVar.split(",");
                                        String newIntoVar = "";
                                        tmpStr = new StringBuilder();
                                        for (int j = 0; j < allIntoVar.length; j++)
                                        {
                                            allIntoVar[j] = "@" + allIntoVar[j] + ",";
                                            newIntoVar += allIntoVar[j];
                                        }
                                        newIntoVar = newIntoVar.substring(0, newIntoVar.length() - 1);
                                        query[k] = query[k].replace(" FROM ", " INTO " + newIntoVar + " FROM ");
                                        intoVar = "temp" + i;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if (temp.toUpperCase().contains("USING") && !temp.toUpperCase().contains("INTO"))
                    {
                        queryVar = temp.substring(a + 17, temp.toUpperCase().indexOf("USING"));
                        intoVar = "temp" + i;
                    }
                    else if (!temp.toUpperCase().contains("INTO") && !temp.toUpperCase().contains("USING"))
                    {
                        queryVar = temp.substring(a + 17, temp.toUpperCase().indexOf(";"));
                        intoVar = "temp" + i;
                    }
                    queryVar = queryVar.replace("ZXYCBA", "INTO");
                    prStr.append("\n SET @").append(intoVar.trim()).append(" = ").append(queryVar).append(";");
                    prStr.append("\n PREPARE ").append(intoVar).append(" FROM @").append(intoVar.trim()).append(";");

                    if (temp.toUpperCase().contains("USING"))
                    {
                        //temp = temp.trim().substring(temp.toUpperCase().indexOf("USING") + 5, temp.indexOf(";"));
                        temp = temp.trim().substring(temp.toUpperCase().indexOf("USING") + 5, temp.indexOf(';', temp.indexOf("USING")));
                        if (temp.trim().startsWith("--"))
                        {
                            temp = "";
                        }
                    }

                    temp = temp.replace("ZXYCBA", "INTO");
                    varList = temp.split("\\)");
                    temp = "";

                    for (int k = 0; k < varList.length; k++)
                    {
                        if (varList[k].lastIndexOf('(') < varList[k].lastIndexOf(',')
                                && varList[k].lastIndexOf('(') > -1)
                        {
                            //temp = temp + varList[k].substring(0, varList[k].lastIndexOf(",") - 1);
                            temp = temp + varList[k].substring(0, varList[k].lastIndexOf(','));
                            temp = temp + "X101" + varList[k].substring(varList[k].lastIndexOf(',') + 1) + ")";
                        }
                        else if (varList[k].lastIndexOf('(') > -1)
                        {
                            temp = temp + varList[k] + ")";
                            varList[k] = varList[k] + ")";
                        }
                        else
                        {
                            temp = temp + varList[k];
                        }
                    }

                    //if (temp.indexOf(",") > -1) if line not starts with EXECUTE IMMEDIATE
                    if (temp.indexOf(',') > -1 && !temp.trim().startsWith("EXECUTE IMMEDIATE"))
                    {
                        varList = temp.split(",");
                        usingVarLen = varList.length;
                        for (int k = 1; k <= varList.length; k++)
                        {
                            if (!varList[k - 1].trim().startsWith(" ABCPQRXYZ --"))
                            {
                                setVar = setVar + "\nSET @" + k + " = " + varList[k - 1].replace("X101", ",") + ";";
                            }
                        }
                    }
                    else if (varList.length == 1 && !varList[0].toUpperCase().contains("EXECUTE IMMEDIATE"))
                    {
                        usingVarLen = 1;
                        setVar = "\nSET @1 = " + varList[0] + ";";
                    }
                    prStr.append(setVar);
                    prStr.append("\nEXECUTE ").append(intoVar);
                    if (usingVarLen > 0)
                    {
                        prStr.append(" USING ");
                        for (int k = 1; k <= usingVarLen; k++)
                        {
                            prStr.append("@").append(k).append(",");
                        }
                        prStr.deleteCharAt(prStr.length() - 1);
                    }
                    prStr.append(";");
                    prStr.append("\nDEALLOCATE PREPARE ").append(intoVar).append(";");
                    query[i] = prStr.toString();
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] typeSec(String[] query, String server)
    {
        try
        {
            //replace %type with original datatype
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].trim().startsWith("--"))
                {
                    continue;
                }
                query[i] = query[i].replace("%type", "% TYPE");
                query[i] = query[i].replace("%TYPE", "% TYPE");
                if (query[i].toUpperCase().trim().contains("% TYPE") || query[i].toUpperCase().trim().contains("% type"))
                {
                    String[] qrLineAry = query[i].trim().replace(" %", "%").split(" ");
                    String newLine = findType(qrLineAry, server);
                    if (newLine != null)
                    {
                        String newStr = "";
                        if (i <= count)
                        {
                            newStr = qrLineAry[0] + " " + newLine + ",";
                            newStr = replaceVar(newStr);
                        }
                        else if (!qrLineAry[0].toUpperCase().contains("DECLARE"))
                        {
                            newStr = "DECLARE " + qrLineAry[0] + " " + newLine + ";";
                        }
                        else
                        {
                            newStr = qrLineAry[0] + " " + newLine + ";";
                        }
                        query[i] = "";
                        query[i] = newStr;
                        query[i] = replaceDataType(query[i]);
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String findType(String[] qrLineAry, String server) throws SQLException
    {
        String column_type = "";
        Connection conn = null;
        try
        {
            String[] detail = qrLineAry[1].trim().split("\\.");
            String tableNm = "";
            String colNm = "";

            if (detail.length == 2)
            {
                tableNm = detail[0];
                //colNm = detail[0].substring(0, (detail[1].lastIndexOf("%")));
                colNm = detail[1].substring(0, detail[1].indexOf('%'));
            }
            else if (detail.length == 3)
            {
                tableNm = detail[1];
                colNm = detail[2].substring(0, (detail[2].lastIndexOf('%')));
            }
            //get column datatype
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("SELECT DATA_TYPE ");
            queryStr.append("FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = '").append(tableNm).append("' AND COLUMN_NAME='").append(colNm).append("' AND ROWNUM <=1 AND DATA_TYPE <> 'UNDEFINED'");

            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);
            SQLConnService sqlcs = new SQLConnService();
            column_type = sqlcs.getString(conn, queryStr.toString());

            //get datatype length
            if (column_type != null && !column_type.isEmpty() && (column_type.equals("VARCHAR2") || column_type.equals("VARCHAR") || column_type.equals("CHAR")))
            {
                queryStr = new StringBuilder();
                queryStr.append("SELECT DATA_LENGTH ");
                queryStr.append("FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = '").append(tableNm).append("' AND COLUMN_NAME='").append(colNm).append("' AND ROWNUM <= 1 AND DATA_TYPE <> 'UNDEFINED'");

                String column_length = sqlcs.getString(conn, queryStr.toString());
                column_type = column_type + "(" + column_length + ")";
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        finally
        {
            if (conn != null)
            {
                conn.close();
            }
        }
        return column_type;
    }

    private String[] setObject(String[] query, String server, String schema)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].contains("OBJ "))
                {
                    String objName = query[i].substring(query[i].toUpperCase().indexOf("OBJ") + 3, query[i].length());
                    if (objName.indexOf(',') > -1 || objName.indexOf(';') > -1)
                    {
                        objName = objName.substring(objName.length() - 1);
                    }
                    //object defination
                    if (objName.indexOf('.') > -1)
                    {
                        objName = objName.substring(0, objName.indexOf('.'));
                    }
                    SqlRowSet fieldName = manager.getFieldName(objName, server, schema);
                    //SqlRowSet fieldName = getFieldName(objName, "dev_brok");
                    String arrDef = "";
                    StringBuilder tmpStr = new StringBuilder();
                    while (fieldName.next())
                    {
                        //arrDef += fieldName.getString("TEXT");
                        tmpStr.append(fieldName.getString("TEXT"));
                    }
                    arrDef = tmpStr.toString();
                    //get fields from object defination
                    String objfields = "";
                    if (arrDef.toUpperCase().indexOf("AS") > -1 || arrDef.toUpperCase().indexOf("IS") > -1)
                    {
                        objfields = arrDef.substring(arrDef.indexOf('(') + 1, arrDef.lastIndexOf(')')).trim();
                        objfields = replaceDataType(objfields);
                    }
//                    query[i] = "BEGIN \n DROP TEMPORARY TABLE IF EXISTS " + objName
//                            + "; \n CREATE TEMPORARY TABLE " + objName + "(\n" + objfields + "\n);"
//                            + "\n END;";
                    query[i] = "\n DROP TEMPORARY TABLE IF EXISTS " + objName
                            + "; \n CREATE TEMPORARY TABLE " + objName + "(\n" + objfields + "\n);";
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setArray(String[] query, String server, String schema) throws ClassNotFoundException, SQLException
    {
        try
        {
            boolean flag = false;
            for (int n = 0; n < query.length; n++)
            {
                if (query[n].trim().startsWith("--"))
                {
                    continue;
                }
                String arrVarName = "i";
                String defDataType = "CHAR,FLOAT,LONG,NUMBER,NVARCHAR2,VARCHAR,VARCHAR2,BOOLEAN,char,float,long,number,nvarchar2,varchar,varchar2,boolean";
                String arrType = "";
                if (query[n].toUpperCase().contains("ARR ") && (query[n].toUpperCase().contains("IN") || query[n].toUpperCase().contains("OUT")))
                {
                    query[n] = "";
                }
                if (query[n].toUpperCase().contains("ARR ") && !query[n].toUpperCase().contains("IN") && !query[n].toUpperCase().contains("OUT"))
                {
                    StringBuilder typeName = new StringBuilder();
                    typeName.append(query[n].substring(query[n].toUpperCase().indexOf("ARR ") + 3).trim());

                    if (typeName.charAt(typeName.length() - 1) == ';')
                    {
                        typeName = typeName.deleteCharAt(typeName.length() - 1);
                    }
                    //if type name contains .
                    if (typeName.indexOf(".") > -1)
                    {
                        String tmpStr = typeName.substring(0, typeName.indexOf("."));
                        typeName = new StringBuilder();
                        typeName.append(tmpStr);
                    }
                    SqlRowSet fieldName = manager.getFieldName(typeName.toString(), server, schema);
                    String arrDef = "";
                    StringBuilder tmpStr = new StringBuilder();
                    while (fieldName.next())
                    {
                        //arrDef += fieldName.getString("TEXT");
                        tmpStr.append(fieldName.getString("TEXT"));
                    }
                    arrDef = tmpStr.toString();
                    if (arrDef.toUpperCase().indexOf("OF") > -1)
                    {
                        //get array type
                        if (arrDef.substring(arrDef.length() - 1).equals(";"))
                        {
                            arrDef = arrDef.substring(0, arrDef.length() - 1);
                        }
                        arrType = arrDef.substring(arrDef.toUpperCase().indexOf("OF") + 2, arrDef.length()).trim();
                        String tmpArrType = arrType;
                        if (arrType.indexOf('(') > -1)
                        {
                            tmpArrType = arrType.substring(0, arrType.indexOf('('));
                        }
                        //chk if array type is default or any collection type
                        String fields = "";
                        if (defDataType.contains(tmpArrType))
                        {
                            fields = arrType;
                        }
                        else
                        {
                            if (arrType.indexOf('.') > -1)
                            {
                                arrType = arrType.substring(arrType.indexOf('.') + 1);
                            }
                            fieldName = manager.getFieldName(arrType, server, schema);
                            arrDef = "";
                            while (fieldName.next())
                            {
                                arrDef += fieldName.getString("TEXT");
                            }
                            if (arrDef.indexOf('(') > -1 && arrDef.lastIndexOf(')') > -1)
                            {
                                fields = arrDef.substring(arrDef.indexOf('(') + 1, arrDef.lastIndexOf(')'));
                            }
                            //replace oracle datatype with mysql
                            fields = replaceDataType(fields);
                        }

                        query[n] = "\nDROP TEMPORARY TABLE IF EXISTS " + arrType + ";\nCREATE TEMPORARY TABLE " + arrType
                                + "\n(" + fields + "\n);";
                    }
                }
//                //set variable for loop
//                if (query[n].contains(":="))
//                {
//                    query[n] = query[n].replace(":=", "=");
//                    query[n] = "SET " + query[n] + "\n";
//                }
                //change loop         
                if (query[n].toUpperCase().contains(".COUNT() LOOP"))
                {
                    String tmpStr = "SELECT COUNT(*) INTO noOfRecords FROM " + arrType + " \n";
                    arrVarName = query[n].substring(query[n].toUpperCase().indexOf("WHILE") + 5, query[n].indexOf("<="));
                    query[n] = tmpStr + "\n" + " WHILE " + arrVarName + "  <= noOfRecords DO";
                    flag = true;
                }
                //get fields
                if ((query[n].contains("ARR(" + arrVarName + ").") || query[n].contains("arr(" + arrVarName + ").")) && !query[n].contains("ARR."))
                {
                    String fieldName = "";
                    int beginIndex = query[n].toUpperCase().indexOf("ARR(");
                    while (query[n].toUpperCase().toUpperCase().indexOf("ARR(" + arrVarName.toUpperCase() + ").", beginIndex) != -1)
                    {
                        beginIndex = query[n].indexOf('.', beginIndex);
                        if (query[n].indexOf('.') > -1 && query[n].indexOf(',') > -1)
                        {
                            fieldName = query[n].substring(query[n].indexOf('.', beginIndex) + 1, query[n].indexOf(',', beginIndex));
                            query[n] = query[n].replace("ARR(" + arrVarName + ")." + fieldName, "(SELECT " + fieldName + " FROM " + arrType + " WHERE SRNO = " + arrVarName + ")");
                            beginIndex += query[n].indexOf(',', beginIndex);
                        }
                    }
                }
                //end while loop
                if (query[n].toUpperCase().contains("END LOOP") && flag)
                {
                    query[n] = query[n].replace("END LOOP", "END WHILE");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] queryConversion(String[] sqlLine)
    {
        String[] query = sqlLine;
        query = replaceNVL(query);
        query = replaceCHR(query);
        query = replaceCurrentDate(query);
        return query;
    }

    private String[] replaceNVL(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("NVL"))
                {
                    query[i] = query[i].replaceAll("NVL", "IFNULL");
                    query[i] = query[i].replaceAll("nvl", "IFNULL");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceToDate(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("TO_DATE"))
                {
                    query[i] = query[i].replaceAll("TO_DATE", "STR_TO_DATE");
                    query[i] = query[i].replaceAll("to_date", "STR_TO_DATE");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceCHR(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("CHR"))
                {
                    query[i] = query[i].replaceAll("CHR", "CHAR");
                    query[i] = query[i].replaceAll("chr", "CHAR");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceCurrentDate(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("CURRENT_DATE") || query[i].toUpperCase().contains("SYSDATE"))
                {
                    query[i] = query[i].replace("CURRENT_DATE", "NOW()");
                    query[i] = query[i].replace("current_date", "NOW()");
                    query[i] = query[i].replace("SYSDATE()", "NOW()");
                    query[i] = query[i].replace("sysdate()", "NOW()");
                    query[i] = query[i].replace("SYSDATE", "NOW()");
                    query[i] = query[i].replace("sysdate", "NOW()");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceToChar(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("TO_CHAR") && isToChar)
                {
                    query[i] = query[i].replace("TO_CHAR", "DATE_FORMAT");
                    query[i] = query[i].replace("to_char", "DATE_FORMAT");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceDateFormat(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                //replace date part
                if (query[i].toUpperCase().contains("DD-MM-YYYY") || query[i].toUpperCase().contains("DD/MM/YYYY"))
                {
                    query[i] = query[i].replace("DD/MM/YYYY", "%d/%m/%Y");
                    query[i] = query[i].replace("dd/mm/yyyy", "%d/%m/%Y");
                    query[i] = query[i].replace("DD-MM-YYYY", "%d-%m-%Y");
                    query[i] = query[i].replace("dd-mm-yyyy", "%d-%m-%Y");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("DD-MM-YY") || query[i].toUpperCase().contains("DD/MM/YY"))
                {
                    query[i] = query[i].replace("DD/MM/YY", "%d/%m/%y");
                    query[i] = query[i].replace("dd/mm/yy", "%d/%m/%y");
                    query[i] = query[i].replace("DD-MM-YY", "%d-%m-%y");
                    query[i] = query[i].replace("dd-mm-yy", "%d-%m-%y");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("DD-MON-YYYY") || query[i].toUpperCase().contains("DD/MON/YYYY"))
                {
                    query[i] = query[i].replace("DD-MON-YYYY", "%d-%b-%Y");
                    query[i] = query[i].replace("dd-mon-yyyy", "%d-%b-%Y");
                    query[i] = query[i].replace("DD/MON/YYYY", "%d/%b/%Y");
                    query[i] = query[i].replace("dd/mon/yyyy", "d/%b/%Y");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("DD-MON-YY") || query[i].toUpperCase().contains("DD/MON/YY"))
                {
                    query[i] = query[i].replace("DD-MON-YY", "%d-%b-%Y");
                    query[i] = query[i].replace("dd-mon-yy", "%d-%b-%Y");
                    query[i] = query[i].replace("DD/MON/YY", "%d-%b/%Y");
                    query[i] = query[i].replace("dd/mon/yy", "%d-%b/%Y");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("YYYY-MM-DD") || query[i].toUpperCase().contains("YYYY/MM/DD"))
                {
                    query[i] = query[i].replace("YYYY-MM-DD", "%Y-%m-%d");
                    query[i] = query[i].replace("yyyy-mm-dd", "%Y-%m-%d");
                    query[i] = query[i].replace("YYYY/MM/DD", "%Y/%m/%d");
                    query[i] = query[i].replace("yyyy/mm/dd'", "%Y/%m/%d");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("MM-DD-YYYY") || query[i].toUpperCase().contains("MM/DD/YYYY"))
                {
                    query[i] = query[i].replace("MM-DD-YYYY", "%m-%d-%Y");
                    query[i] = query[i].replace("mm-dd-yyyy", "%m-%d-%Y");
                    query[i] = query[i].replace("MM/DD/YYYY", "%m/%d/%Y");
                    query[i] = query[i].replace("mm/dd/yyyy", "%m/%d/%Y");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("YYYY-MM") || query[i].toUpperCase().contains("YYYY/MM"))
                {
                    query[i] = query[i].replace("'YYYY-MM'", "'%Y-%m'");
                    query[i] = query[i].replace("'yyyy-mm'", "'%Y-%m'");
                    query[i] = query[i].replace("'YYYY/MM'", "'%Y/%m'");
                    query[i] = query[i].replace("'yyyy/mm'", "'%Y/%m'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'MM/YYYY'") || query[i].toUpperCase().contains("'MM-YYYY'"))
                {
                    query[i] = query[i].replace("'MM/YYYY'", "'%m/%Y'");
                    query[i] = query[i].replace("'MM/YYYY'", "'%m/%Y'");
                    query[i] = query[i].replace("'MM-YYYY'", "'%m-%Y'");
                    query[i] = query[i].replace("'MM-YYYY'", "'%m-%Y'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'MON-YYYY'") || query[i].toUpperCase().contains("'MON/YYYY'"))
                {
                    query[i] = query[i].replace("'MON-YYYY'", "'%b-%Y'");
                    query[i] = query[i].replace("'mon-yyyy'", "'%b-%Y'");
                    query[i] = query[i].replace("'MON/YYYY'", "'%b/%Y'");
                    query[i] = query[i].replace("'mon/yyyy'", "'%b/%Y'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'MON'"))
                {
                    query[i] = query[i].replace("'MON'", "'%b'");
                    query[i] = query[i].replace("'mon'", "'%b'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'MM'"))
                {
                    query[i] = query[i].replace("'MM'", "'%m'");
                    query[i] = query[i].replace("'mm'", "'%m'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'YYYY'"))
                {
                    query[i] = query[i].replace("'YYYY'", "'%Y'");
                    query[i] = query[i].replace("'yyyy'", "'%Y'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'YY'"))
                {
                    query[i] = query[i].replace("'YY'", "'%y'");
                    query[i] = query[i].replace("'yy'", "'%y'");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("'DD'"))
                {
                    query[i] = query[i].replace("'DD'", "'%d'");
                    query[i] = query[i].replace("'dd'", "'%d'");
                    isToChar = true;
                }
                //replace time part
                if (query[i].toUpperCase().contains("HH24:MI:SS AM") || query[i].toUpperCase().contains("HH:MI:SS AM") || query[i].toUpperCase().contains("HH12:MI:SS AM")
                        || query[i].toUpperCase().contains("HH24:MI:SS PM") || query[i].toUpperCase().contains("HH:MI:SS PM") || query[i].toUpperCase().contains("HH12:MI:SS PM"))
                {
                    query[i] = query[i].replace("HH12:MI:SS AM", "%h:%i:%s %p");
                    query[i] = query[i].replace("HH:MI:SS AM", "%h:%i:%s %p");
                    query[i] = query[i].replace("HH24:MI:SS AM", "%H:%m:%s %p");
                    query[i] = query[i].replace("hh12:mi:ss AM", "%h:%i:%s %p");
                    query[i] = query[i].replace("hh:mi:ss AM", "%h:%i:%s %p");
                    query[i] = query[i].replace("hh24:mi:ss AM", "%H:%m:%s %p");

                    query[i] = query[i].replace("HH12:MI:SS PM", "%h:%i:%s %p");
                    query[i] = query[i].replace("HH:MI:SS PM", "%h:%i:%s %p");
                    query[i] = query[i].replace("HH24:MI:SS PM", "%H:%m:%s %p");
                    query[i] = query[i].replace("hh12:mi:ss PM", "%h:%i:%s %p");
                    query[i] = query[i].replace("hh:mi:ss PM", "%h:%i:%s %p");
                    query[i] = query[i].replace("hh24:mi:ss PM", "%H:%m:%s %p");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("HH24:MI:SS") || query[i].toUpperCase().contains("HH:MI:SS") || query[i].toUpperCase().contains("HH12:MI:SS"))
                {
                    query[i] = query[i].replace("HH12:MI:SS", "%h:%i:%s");
                    query[i] = query[i].replace("HH:MI:SS", "%h:%i:%s");
                    query[i] = query[i].replace("HH24:MI:SS", "%H:%m:%s");
                    query[i] = query[i].replace("hh12:mi:ss", "%h:%i:%s");
                    query[i] = query[i].replace("hh:mi:ss", "%h:%i:%s");
                    query[i] = query[i].replace("hh24:mi:ss", "%H:%m:%s");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("HH12:MI AM") || query[i].toUpperCase().contains("HH:MI AM") || query[i].toUpperCase().contains("HH24:MI AM")
                        || query[i].toUpperCase().contains("HH12:MI PM") || query[i].toUpperCase().contains("HH:MI PM") || query[i].toUpperCase().contains("HH24:MI PM"))
                {
                    query[i] = query[i].replace("HH12:MI AM", "%h:%i %p");
                    query[i] = query[i].replace("HH:MI AM", "%h:%i %p");
                    query[i] = query[i].replace("HH24:MI AM", "%H:%m %p");
                    query[i] = query[i].replace("hh12:mi AM", "%h:%i %p");
                    query[i] = query[i].replace("hh:mi AM", "%h:%i %p");
                    query[i] = query[i].replace("hh24:mi AM", "%H:%m %p");

                    query[i] = query[i].replace("HH12:MI PM", "%h:%i %p");
                    query[i] = query[i].replace("HH:MI PM", "%h:%i %p");
                    query[i] = query[i].replace("HH24:MI PM", "%H:%m %p");
                    query[i] = query[i].replace("hh12:mi PM", "%h:%i %p");
                    query[i] = query[i].replace("hh:mi PM", "%h:%i %p");
                    query[i] = query[i].replace("hh24:mi PM", "%H:%m %p");
                    isToChar = true;
                }
                if (query[i].toUpperCase().contains("HH12:MI") || query[i].toUpperCase().contains("HH:MI") || query[i].toUpperCase().contains("HH24:MI"))
                {
                    query[i] = query[i].replace("HH12:MI", "%h:%i");
                    query[i] = query[i].replace("HH:MI", "%h:%i");
                    query[i] = query[i].replace("HH24:MI", "%H:%m");
                    query[i] = query[i].replace("hh12:mi", "%h:%i");
                    query[i] = query[i].replace("hh:mi", "%h:%i");
                    query[i] = query[i].replace("hh24:mi", "%H:%m");
                    isToChar = true;
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setCursor(String[] query, String server)
    {
        try
        {
            String cursor_name = "";
            String cursor_query = "";
            String ref_cursor_name = "";
            List allCursor = new ArrayList();
            List refCursorList = new ArrayList();
            int cursorCount = 0;
            for (int n = 0; n < query.length; n++)
            {
                while (query[n].contains("ABCPQRXYZ"))
                {
                    query[n] = query[n].replace("ABCPQRXYZ", " ");
                }
                query[n] = query[n].replace("  ", " ");

                if (query[n].trim().startsWith("--"))
                {
                    continue;
                }
                else if (query[n].trim().startsWith("/*") && !query[n].trim().startsWith("/* TCIGBF"))
                {
                    continue;
                }
                //replace cursor declaration   
                if (query[n].toUpperCase().contains("IS REF CURSOR"))
                {
                    ref_cursor_name = query[n].substring(query[n].toUpperCase().indexOf("TYPE") + 5, query[n].toUpperCase().indexOf("IS REF CURSOR"));
                    ref_cursor_name = " " + ref_cursor_name;
                    refCursorList.add(ref_cursor_name);
                    query[n] = "";
                }
                else if ((query[n].trim().toUpperCase().startsWith("CURSOR ") || query[n].trim().toUpperCase().startsWith("DECLARE CURSOR "))
                        && !query[n].toUpperCase().contains("LEAVE"))
                {
                    String tmpCurName = "";
                    if (query[n].toUpperCase().indexOf(" IS") > -1)
                    {
                        cursor_name = query[n].substring(query[n].toUpperCase().indexOf("CURSOR") + 6, query[n].toUpperCase().indexOf(" IS") + 3);
                        String newCursorName = cursor_name;
                        newCursorName = newCursorName.replace(" IS", " FOR");
                        newCursorName = newCursorName.replace(" is", " FOR");
                        query[n] = query[n].replace(cursor_name, newCursorName);
                        cursor_name = cursor_name.substring(0, cursor_name.toUpperCase().indexOf(" IS"));
                    }
                    tmpCurName = cursor_name;

                    if (cursor_name.indexOf('(') > -1 && cursor_name.indexOf(')') > -1)
                    {
                        String paramCursorVarName = cursor_name.substring(cursor_name.indexOf('(') + 1, cursor_name.lastIndexOf(')'));
                        cursor_name = cursor_name.substring(0, cursor_name.indexOf('('));
                        String[] allParams = paramCursorVarName.split(",");
                        for (int m = 0; m < allParams.length; m++)
                        {
                            //if param pass to cursor contains %type
                            allParams[m] = allParams[m].replace("%type", "% TYPE");
                            allParams[m] = allParams[m].replace("%TYPE", "% TYPE");
                            if (allParams[m].toUpperCase().trim().contains("% TYPE") || allParams[m].toUpperCase().trim().contains("% type"))
                            {
                                String[] qrLineAry = allParams[m].trim().replace(" %", "%").split(" ");
                                String newLine = findType(qrLineAry, server);
                                if (newLine != null)
                                {
                                    String newStr = "";
                                    if (!qrLineAry[0].toUpperCase().contains("DECLARE"))
                                    {
                                        newStr = "DECLARE " + qrLineAry[0] + " " + newLine + ";";
                                    }
                                    else
                                    {
                                        newStr = qrLineAry[0] + " " + newLine + ";";
                                    }
                                    allParams[m] = newStr;
                                    allParams[m] = replaceDataType(allParams[m]);
                                }
                            }
                            else
                            {
                                allParams[m] = "DECLARE " + allParams[m] + ";";
                                allParams[m] = replaceDataType(allParams[m]);
                            }
                            paramCurVars.add(allParams[m]);
                        }
                    }

                    allCursor.add(cursor_name);
                    String decNoData = "";

                    query[n] = query[n].replaceAll("  ", " ");
                    query[n] = query[n].replace("CURSOR " + tmpCurName.trim(), "DECLARE " + cursor_name.trim() + " CURSOR ");
                    query[n] = query[n].replace("cursor " + tmpCurName.trim(), "DECLARE " + cursor_name.trim() + " CURSOR ");
                    query[n] = query[n].replace("DECLARE DECLARE ", "DECLARE ");

                    query[n] = decNoData + query[n];
                }
                //get all object of cursor    
                for (int i = 0; i < refCursorList.size(); i++)
                {
                    ref_cursor_name = refCursorList.get(i).toString();
                    if (!ref_cursor_name.equals("") && query[n].contains(" " + ref_cursor_name.trim() + ";"))
                    {
                        String tmp_cursor_name = query[n].trim().substring(0, query[n].trim().lastIndexOf(ref_cursor_name.trim()));
                        allCursor.add(tmp_cursor_name);
                        query[n] = "\nDECLARE " + tmp_cursor_name + " CURSOR FOR SELECT * FROM TMP_TABLE;";
                    }
                }
                //replace open cursor    
                StringBuilder sql_open_cursor = new StringBuilder();
                String comment = "";
                for (int i = 0; i < allCursor.size(); i++)
                {
                    if (query[n].contains("OPEN " + allCursor.get(i).toString().trim()) || query[n].contains("open " + allCursor.get(i).toString().trim()))
                    {
                        if (query[n].indexOf("--") > -1)
                        {
                            comment += query[n].substring(query[n].indexOf("--"));
                            query[n] = query[n].substring(0, query[n].indexOf("--"));
                        }
                        if (query[n].indexOf("/*") > -1)
                        {
                            comment += query[n].substring(query[n].indexOf("/*"), query[n].indexOf("*/") + 2);
                        }
                        if (query[n].toUpperCase().contains("FOR"))
                        {
                            if (query[n].toUpperCase().indexOf("USING") < 0)
                            {
                                if (query[n].contains("FOR"))
                                {
                                    cursor_query = query[n].substring(query[n].toUpperCase().indexOf("FOR") + 3, query[n].length()).trim();
                                }
                            }
                            else
                            {
                                if (query[n].contains("FOR"))
                                {
                                    cursor_query = query[n].substring(query[n].toUpperCase().indexOf("FOR") + 3, query[n].toUpperCase().indexOf("USING")).trim();
                                }
                            }
                        }
                        if (!cursor_query.isEmpty())
                        {
                            sql_open_cursor.append("DROP TEMPORARY TABLE IF EXISTS TMP_TABLE;");
                            sql_open_cursor.append("\n SET @SWV_Stmt = CONCAT('CREATE TEMPORARY TABLE IF NOT EXISTS TMP_TABLE  AS ',COALESCE(");


                            if (cursor_query.charAt(cursor_query.length() - 1) == ';')
                            {
                                cursor_query = cursor_query.substring(0, cursor_query.length() - 1);
                            }
                            if (cursor_query.toUpperCase().contains("SELECT "))
                            {
                                sql_open_cursor.append("'");
                                cursor_query = cursor_query.replace("'", "''");
                            }
                            sql_open_cursor.append(cursor_query);
                            if (cursor_query.toUpperCase().contains("SELECT "))
                            {
                                sql_open_cursor.append("'");
                            }

                            sql_open_cursor.append(",''));");
                            sql_open_cursor.append("\n PREPARE SWT_Stmt FROM @SWV_Stmt;");

                            String query_params = "";
                            int query_param_len = 0;
                            if (query[n].toUpperCase().contains("USING"))
                            {
                                //query_params = query[n].substring(query[n].toUpperCase().indexOf("USING") + 5, query[n].trim().length() - 1).trim();
                                query_params = query[n].substring(query[n].toUpperCase().indexOf("USING") + 5, query[n].indexOf(';', query[n].toUpperCase().indexOf("USING"))).trim();
                                String[] pass_query_params = query_params.split(",");
                                query_param_len = pass_query_params.length;
                                if (!query_params.equals(""))
                                {
                                    for (int k = 0; k < pass_query_params.length; k++)
                                    {
                                        sql_open_cursor.append("\n SET @");
                                        sql_open_cursor.append(k + 1);
                                        sql_open_cursor.append("=");
                                        sql_open_cursor.append(pass_query_params[k]);
                                        sql_open_cursor.append(";");
                                    }
                                }
                            }
                            sql_open_cursor.append("\n EXECUTE SWT_Stmt");
                            if (query_param_len > 0)
                            {
                                sql_open_cursor.append(" USING ");
                                for (int k = 1; k <= query_param_len; k++)
                                {
                                    sql_open_cursor.append("@").append(k).append(",");
                                }
                                sql_open_cursor.deleteCharAt(sql_open_cursor.length() - 1);
                            }
                            sql_open_cursor.append(";");
                            sql_open_cursor.append("\n DEALLOCATE PREPARE SWT_Stmt;");
                        }

                        if (!paramCurVars.isEmpty())
                        {
                            for (Iterator it = paramCurVars.iterator(); it.hasNext();)
                            {
                                String varName = it.next().toString();
                                String tmpVarName = varName;
                                varName = varName.substring(varName.indexOf("DECLARE") + 7).trim();
                                varName = varName.substring(0, varName.trim().indexOf(' '));
                                String[] value = query[n].substring(query[n].indexOf('(') + 1, query[n].lastIndexOf(')')).split(",");
                                for (int m = 0; m < value.length; m++)
                                {
                                    sql_open_cursor.append("\n SET ").append(varName).append(" = ");

                                    if (tmpVarName.contains("DECIMAL") || tmpVarName.contains("INT")
                                            || tmpVarName.contains("DOUBLE") || tmpVarName.contains("BIGINT"))
                                    {
                                        sql_open_cursor.append("'");
                                    }
                                    sql_open_cursor.append(value[m]);
                                    if (tmpVarName.contains("DECIMAL") || tmpVarName.contains("INT")
                                            || tmpVarName.contains("DOUBLE") || tmpVarName.contains("BIGINT"))
                                    {
                                        sql_open_cursor.append("'");
                                    }
                                    sql_open_cursor.append(";");
                                }
                            }
                        }
                        sql_open_cursor.append("\n OPEN ");
                        sql_open_cursor.append(allCursor.get(i));
                        sql_open_cursor.append(";");

                        query[n] = sql_open_cursor.toString();
                    }
                }
                if (!comment.isEmpty())
                {
                    query[n] += "\n" + comment;
                }
                //replace cursor loop with label n loop
                if ((query[n].trim().toUpperCase().startsWith("LOOP")
                        || query[n].trim().toUpperCase().contains(" LOOP"))
                        && !query[n].trim().toUpperCase().contains("LOOP_")
                        && !query[n].trim().toUpperCase().contains("_LOOP")
                        && !query[n].trim().toUpperCase().contains("END LOOP"))
                {
                    cursorCount++;
                    int loopCount = 0;
                    query[n] = query[n].replace("LOOP", "\nCURSOR" + cursorCount + ":LOOP\n");
                    query[n] = query[n].replace("loop", "\nCURSOR" + cursorCount + ":LOOP\n");

                    for (int j = n; j < query.length; j++)
                    {
                        if (query[j].toUpperCase().contains("END LOOP") && loopCount > 0)
                        {
                            loopCount--;
                        }
                        else if (query[j].toUpperCase().contains("END LOOP") && loopCount == 0)
                        {
                            break;
                        }
                        else if (query[j].trim().toUpperCase().startsWith("LOOP") && !query[j].trim().toUpperCase().contains(":LOOP"))
                        {
                            loopCount++;
                        }
                        else if (query[j].toUpperCase().contains("EXIT") && query[j].toUpperCase().contains("WHEN")
                                && query[j].toUpperCase().contains("%NOTFOUND") && loopCount == 0)
                        {
                            //replace exit when %notfound statement                            
                            query[j] = "IF (curNotFound) THEN"
                                    + "\n   LEAVE CURSOR" + cursorCount + ";"
                                    + "\n END IF;";
                        }
                        else if (query[j].toUpperCase().contains("LEAVE") && query[j].toUpperCase().contains("LOOP")
                                && loopCount == 0)
                        {
                            //for replace leave label of setnewcursor
                            query[j] = query[j].replace("LEAVE \nCURSOR" + cursorCount + ":LOOP\n", "LEAVE CURSOR" + cursorCount);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] getArray(String[] query)
    {
        String[] sqlLines = query;
        StringBuilder sqlLinesNew = new StringBuilder();
        try
        {
            for (int i = 0; i < sqlLines.length; i++)
            {
                sqlLines[i] = rmSpc(sqlLines[i]);
                if (!(sqlLines[i].trim().startsWith("--") || sqlLines[i].trim().startsWith("/*") || sqlLines[i].trim().endsWith("*/"))
                        && sqlLines[i].contains("--")
                        && !sqlLines[i].trim().toUpperCase().startsWith("DBMS_OUTPUT.PUT_LINE"))
                {
                    //sqlLinesNew.append(sqlLines[i]).append("\n");                    
                    sqlLinesNew.append(sqlLines[i].substring(sqlLines[i].indexOf("--"))).append("\n");
                    sqlLinesNew.append(sqlLines[i].substring(0, sqlLines[i].indexOf("--"))).append("\n");
                }
                else
                {

                    sqlLinesNew.append(sqlLines[i]).append("\n");
                }
            }
            sqlLines = sqlLinesNew.toString().split("\n");
            sqlLinesNew = new StringBuilder();
            //for execute immidiate & begin
            for (int i = 0; i < sqlLines.length; i++)
            {
                sqlLines[i] = rmSpc(sqlLines[i]);
                if (sqlLines[i].trim().toUpperCase().contains("EXECUTE IMMEDIATE")
                        && !sqlLines[i].toUpperCase().trim().startsWith("EXECUTE IMMEDIATE")
                        && !sqlLines[i].trim().startsWith("--")
                        && !sqlLines[i].trim().startsWith("/*"))
                {
                    sqlLinesNew.append(sqlLines[i].substring(0, sqlLines[i].toUpperCase().indexOf("EXECUTE IMMEDIATE"))).append("\n");
                    sqlLinesNew.append(sqlLines[i].substring(sqlLines[i].toUpperCase().indexOf("EXECUTE IMMEDIATE"))).append("\n");
                }
                else if (sqlLines[i].trim().toUpperCase().contains("BEGIN")
                        && !sqlLines[i].trim().toUpperCase().contains("BEGIN_")
                        && !sqlLines[i].trim().toUpperCase().startsWith("BEGIN")
                        && !sqlLines[i].trim().startsWith("--")
                        && !sqlLines[i].trim().startsWith("/*"))
                {
                    sqlLinesNew.append(sqlLines[i].substring(0, sqlLines[i].toUpperCase().indexOf("BEGIN"))).append("\n");
                    sqlLinesNew.append(sqlLines[i].substring(sqlLines[i].toUpperCase().indexOf("BEGIN"))).append("\n");
                }
                else
                {
                    sqlLinesNew.append(sqlLines[i]).append("\n");
                }
            }
            sqlLines = sqlLinesNew.toString().split("\n");
            sqlLinesNew = new StringBuilder();
            for (int i = 0; i < sqlLines.length; i++)
            {
                sqlLines[i] = rmSpc(sqlLines[i]);
                if (!sqlLines[i].trim().startsWith("--") && sqlLines[i].contains("/*"))
                {
                    sqlLines[i] = sqlLines[i].replace("/*", "\n /*");
                    StringBuilder sqlCommentLine = new StringBuilder();
                    {
                        while (!sqlLines[i].contains("*/"))
                        {
                            sqlCommentLine.append(sqlLines[i]).append("\n");
                            i++;
                        }
                        sqlLines[i] = sqlLines[i].replace("*/", "*/ \n");
                        //sqlCommentLine.append(sqlLines[i]).append("\n ");
                        sqlCommentLine.append(sqlLines[i]).append(" ");
                    }
                    if (sqlCommentLine.indexOf("/*") < sqlCommentLine.indexOf("*/") + 2)
                    {
                        String comment = sqlCommentLine.substring(sqlCommentLine.indexOf("/*"), sqlCommentLine.indexOf("*/") + 2);
                        sqlCommentLine.replace(sqlCommentLine.indexOf("/*"), sqlCommentLine.indexOf("*/") + 2, "");
                        sqlLinesNew.append(comment).append("\n");
                    }
                    sqlLinesNew.append(sqlCommentLine).append("\n");
                }
                else
                {
                    sqlLinesNew.append(sqlLines[i]).append("\n");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return sqlLinesNew.toString().split("\n");
    }

    private String[] replaceElseIf(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("ELSIF"))
                {
                    query[i] = query[i].replace("ELSIF", "ELSEIF");
                    query[i] = query[i].replace("elsif", "ELSEIF");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] getOneLine(String[] sqlLines)
    {
        try
        {
            String[] strCheck =
            {
                "DBMS_OUTPUT", "EXECUTE IMMEDIATE", "INSERT INTO", "DELETE FROM", "CREATE TABLE", "CREATE INDEX", "ALTER TABLE", "OPEN ", "CURSOR ", "SELECT ", "UPDATE ", "UNION ALL", "FETCH ", "DELETE "
            };

            for (int i = 0; i < sqlLines.length; i++)
            {
                if (sqlLines[i].trim().startsWith("--"))
                {
                    sqlLines[i] = sqlLines[i] + "\n";
                }
                if (sqlLines[i].trim().startsWith("/*") && !sqlLines[i].trim().endsWith("*/"))
                {
                    StringBuilder query = new StringBuilder();
                    {
                        while (!(sqlLines[i].trim().endsWith("*/")) && i < sqlLines.length)
                        {
                            query.append(rmSpc(sqlLines[i])).append(" ABCPQRXYZ ");
                            sqlLines[i] = "";
                            i++;
                        }
                        sqlLines[i] = rmSpc(sqlLines[i]);
                        query.append(sqlLines[i]);
                    }
                    sqlLines[i] = query.toString() + "\n";
                    //remove last * from multi line comment                    
                    sqlLines[i] = sqlLines[i].replace("* ABCPQRXYZ */", "**/");
                }
                else if (sqlLines[i].toUpperCase().contains("IF ")
                        || sqlLines[i].toUpperCase().contains("IF("))
                {
                    continue;
                }
                else if (!sqlLines[i].trim().startsWith("/*") && !sqlLines[i].trim().startsWith("--"))
                {
                    for (int j = 0; j < strCheck.length; j++)
                    {
                        if (!(sqlLines[i].toUpperCase().trim().startsWith("FOR ")
                                && sqlLines[i].toUpperCase().contains(" IN")
                                && !sqlLines[i].toUpperCase().contains("..")))
                        {
                            if (sqlLines[i].toUpperCase().contains(strCheck[j]) && !sqlLines[i].trim().endsWith(";"))
                            {
                                if (strCheck[j].equals("OPEN ") || strCheck[j].equals("SELECT ")
                                        || strCheck[j].equals("UPDATE ") || strCheck[j].equals("CURSOR ")
                                        || strCheck[j].equals("FETCH ") || strCheck[j].equals("DELETE "))
                                {
                                    String prvchr = "\n";
                                    String regexp = "[a-zA-Z0-9_]";
                                    if (!sqlLines[i].trim().toUpperCase().startsWith(strCheck[j]))
                                    {
                                        prvchr = sqlLines[i].substring(sqlLines[i].toUpperCase().indexOf(strCheck[j]) - 1, sqlLines[i].toUpperCase().indexOf(strCheck[j]));

                                        if (prvchr.matches(regexp))
                                        {
                                            continue;
                                        }
                                    }
                                }
                                String comment = "";
                                while (sqlLines[i].contains("--"))
                                {
                                    comment = sqlLines[i].substring(sqlLines[i].indexOf("--"));
                                    sqlLines[i] = sqlLines[i].substring(0, sqlLines[i].indexOf(comment));
                                }
                                while (sqlLines[i].contains("/*"))
                                {
                                    //comment += sqlLines[i].substring(sqlLines[i].indexOf("/*"), sqlLines[i].indexOf("*/") + 2);
                                    comment = sqlLines[i].substring(sqlLines[i].indexOf("/*"));
                                    sqlLines[i] = sqlLines[i].substring(0, sqlLines[i].indexOf(comment));
                                }
                                StringBuilder query = new StringBuilder();
                                List<String> comm = new ArrayList<String>();
                                while ((!sqlLines[i].trim().endsWith(";") || sqlLines[i].trim().startsWith("--") || sqlLines[i].trim().startsWith("/*"))
                                        && i < sqlLines.length)
                                {
                                    if (sqlLines[i].trim().startsWith("--"))
                                    {
                                        comm.add(sqlLines[i].trim());
                                        sqlLines[i] = "";
                                        i++;
                                        continue;
                                    }
                                    else if (sqlLines[i].trim().startsWith("/*"))
                                    {
                                        while (!sqlLines[i].trim().endsWith("*/") && i < sqlLines.length)
                                        {
                                            comm.add(sqlLines[i].trim());
                                            sqlLines[i] = "";
                                            i++;
                                            continue;
                                        }
                                        comm.add(sqlLines[i].trim());
                                        sqlLines[i] = "";
                                    }
                                    else if (sqlLines[i].trim().contains("/*") && sqlLines[i].trim().contains("*/"))
                                    {
                                        String tmp_comment = sqlLines[i].substring(sqlLines[i].indexOf("/*"), sqlLines[i].indexOf("*/") + 2);
                                        String line = sqlLines[i].substring(0, sqlLines[i].indexOf("/*"));
                                        line += sqlLines[i].substring(sqlLines[i].indexOf("*/") + 2);
                                        comm.add(tmp_comment);
                                        sqlLines[i] = line;
                                    }
                                    query.append(sqlLines[i]).append(" ABCPQRXYZ ");
                                    sqlLines[i] = "";
                                    i++;
                                }
                                if (!sqlLines[i].trim().startsWith("--") && !sqlLines[i].trim().startsWith("/*"))
                                {
                                    sqlLines[i] = rmSpc(sqlLines[i]);
                                    query.append(" ").append(sqlLines[i]);
                                }
                                else
                                {
                                    comm.add(sqlLines[i]);
                                }
                                sqlLines[i] = query.toString() + "\n";
                                while (sqlLines[i].contains("ABCPQRXYZ  ABCPQRXYZ") || sqlLines[i].contains("  "))
                                {
                                    sqlLines[i] = sqlLines[i].replace("  ", " ");
                                    sqlLines[i] = sqlLines[i].replace("ABCPQRXYZ  ABCPQRXYZ", "ABCPQRXYZ ");
                                    sqlLines[i] = sqlLines[i].replace("ABCPQRXYZ ABCPQRXYZ ABCPQRXYZ", "ABCPQRXYZ ");
                                }
                                for (int k = 0; k < comm.size(); k++)
                                {
                                    sqlLines[i] += comm.get(k) + "\n";
                                }
                                sqlLines[i] += comment + "\n";
                            }
                        }
                        else
                        {
                            while (!sqlLines[i].toUpperCase().contains("LOOP") && i < sqlLines.length)
                            {
                                i++;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < sqlLines.length; i++)
            {
                if (!sqlLines[i].trim().startsWith("/*") && !sqlLines[i].trim().startsWith("--"))
                {
                    if (sqlLines[i].toUpperCase().trim().startsWith("FOR ")
                            && sqlLines[i].toUpperCase().contains(" IN")
                            && !sqlLines[i].toUpperCase().contains("..")
                            && !sqlLines[i].trim().toUpperCase().endsWith("LOOP"))
                    {
                        String comment = "";
                        if (sqlLines[i].contains("--"))
                        {
                            comment = sqlLines[i].substring(sqlLines[i].indexOf("--"));
                            sqlLines[i] = sqlLines[i].substring(0, sqlLines[i].indexOf(comment));
                        }
                        if (sqlLines[i].contains("/*"))
                        {
                            comment = sqlLines[i].substring(sqlLines[i].indexOf("/*"), sqlLines[i].indexOf("*/") + 2);
                            sqlLines[i] = sqlLines[i].substring(0, sqlLines[i].indexOf(comment));
                        }
                        StringBuilder query = new StringBuilder();
                        List<String> comm = new ArrayList<String>();
                        while (!sqlLines[i].trim().toUpperCase().contains("LOOP") || sqlLines[i].trim().startsWith("--") || sqlLines[i].trim().startsWith("/*"))
                        {
                            if (sqlLines[i].trim().startsWith("--"))
                            {
                                comm.add(sqlLines[i].trim());
                                sqlLines[i] = "";
                                i++;
                                continue;
                            }
                            else if (sqlLines[i].trim().startsWith("/*"))
                            {
                                while (!sqlLines[i].trim().endsWith("*/"))
                                {
                                    comm.add(sqlLines[i].trim());
                                    sqlLines[i] = "";
                                    i++;
                                    continue;
                                }
                                comm.add(sqlLines[i].trim());
                                sqlLines[i] = "";
                            }
                            query.append(sqlLines[i]).append(" ABCPQRXYZ ");
                            sqlLines[i] = "";
                            i++;
                        }
                        String tmpstr = sqlLines[i];
                        sqlLines[i] = rmSpc(tmpstr.substring(0, tmpstr.toUpperCase().indexOf("LOOP")));
                        sqlLines[i] += "\n" + " LOOP ";
                        sqlLines[i] += "\n" + tmpstr.substring(tmpstr.toUpperCase().indexOf("LOOP") + 4);
                        query.append(" ").append(sqlLines[i]);

                        sqlLines[i] = query.toString() + "\n";
                        while (sqlLines[i].contains("ABCPQRXYZ  ABCPQRXYZ") || sqlLines[i].contains("  "))
                        {
                            sqlLines[i] = sqlLines[i].replace("  ", " ");
                            sqlLines[i] = sqlLines[i].replace("ABCPQRXYZ  ABCPQRXYZ", "ABCPQRXYZ ");
                            sqlLines[i] = sqlLines[i].replace("ABCPQRXYZ ABCPQRXYZ ABCPQRXYZ", "ABCPQRXYZ ");
                        }
                        for (int k = 0; k < comm.size(); k++)
                        {
                            sqlLines[i] += comm.get(k) + "\n";
                        }
                        sqlLines[i] += comment + "\n";
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return sqlLines;
    }

    private String[] setCursorDecSec(String[] query)
    {
        String tempCur = "";
        List ls = new ArrayList();
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if ((query[i].toUpperCase().contains("CURSOR FOR") || query[i].toUpperCase().contains("CURSOR "))
                        && !query[i].toUpperCase().contains("LEAVE") && !query[i].toUpperCase().contains(":LOOP"))
                {
                    if (query[i].trim().startsWith("--") || (query[i].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF")))
                    {
                        continue;
                    }
                    int counter = i;
                    String comment = "";
                    if (query[counter].contains("--"))
                    {
                        comment = query[counter].substring(query[counter].indexOf("--"));
                        query[counter] = query[counter].substring(0, query[counter].indexOf(comment));
                    }
                    else if (query[counter].contains("/*"))
                    {
                        //comment += sqlLines[i].substring(sqlLines[i].indexOf("/*"), sqlLines[i].indexOf("*/") + 2);
                        comment = query[counter].substring(query[counter].indexOf("/*"));
                        query[counter] = query[counter].substring(0, query[counter].indexOf(comment));
                    }
                    StringBuilder tmpStr = new StringBuilder();
                    while (!query[counter].trim().endsWith(";") && !query[counter].trim().endsWith("*/"))
                    {
                        //tempCur += query[counter] + "\n ";
                        tmpStr.append(query[counter]);
                        tmpStr.append("\n ");
                        query[counter] = "";
                        counter++;
                    }
                    tempCur = tmpStr.toString();
                    tempCur += (query[counter] + comment + "\n ");
                    query[counter] = "";
                }
                //get all declare statement
                ls = getAllDecVars(query);
            }

            for (int i = 0; i < query.length; i++)
            {
                if (ls.size() > 0 && query[i].equals(ls.get(ls.size() - 1)))
                {
                    if (!tempCur.isEmpty() && !forCurList.isEmpty())
                    {
                        query[i] += "\nDECLARE curNotFound BOOLEAN DEFAULT FALSE;\n";
                    }
                    //declare variables use in for loop
                    if (!forVarName.isEmpty())
                    {
                        for (Iterator it = forVarName.iterator(); it.hasNext();)
                        {
                            query[i] += "\nDECLARE " + it.next() + " INT;";
                        }
                    }
                    //declare params pass to cursor
                    if (!paramCurVars.isEmpty())
                    {
                        for (Iterator it = paramCurVars.iterator(); it.hasNext();)
                        {
                            query[i] += "\n" + it.next();
                        }
                    }
                    //declare variables used in for loop cursor
                    if (!uniqueForCurDetail.isEmpty())
                    {
                        for (Iterator it = uniqueForCurDetail.iterator(); it.hasNext();)
                        {
                            String varName = it.next().toString();
                            String varDataType = varName.substring(varName.indexOf('=') + 1);
                            varName = "VAR_" + varName.substring(0, varName.indexOf('='));
                            query[i] += "\nDECLARE " + varName + " " + varDataType + ";";
                        }
//                        for (Map.Entry<String, String> entry : forCurDetail.entrySet())
//                        {
//                            String varName = entry.getKey();
//                            String varDataType = entry.getValue();
//                            varName = "VAR_" + varName.substring(varName.indexOf(".") + 1);
//                            query[i] += "\n DECLARE " + varName + " " + varDataType + ";";
//                        }
                    }
                    //declare for loop cursor
                    if (!forCurList.isEmpty())
                    {
                        Iterator itr = forCurList.iterator();
                        while (itr.hasNext())
                        {
                            query[i] += "\n" + itr.next();
                        }
                    }
                    query[i] += " " + tempCur;
                    if (!tempCur.isEmpty() && !forCurList.isEmpty())
                    {
                        query[i] += "\nDECLARE CONTINUE HANDLER FOR NOT FOUND SET curNotFound = true;";
                    }
                    break;
                }
                else if (ls.isEmpty())
                {
                    if (query[i].toUpperCase().contains("BEGIN"))
                    {
                        if (!tempCur.isEmpty() && !forCurList.isEmpty())
                        {
                            query[i] += "\nDECLARE curNotFound BOOLEAN DEFAULT FALSE;\n";
                        }
                        //declare variables used in for loop 
                        if (!forVarName.isEmpty())
                        {
                            for (Iterator it = forVarName.iterator(); it.hasNext();)
                            {
                                query[i] += "\n DECLARE " + it.next() + " INT;";
                            }
                        }
                        //declare variables passed to cursor
                        if (!paramCurVars.isEmpty())
                        {
                            for (Iterator it = paramCurVars.iterator(); it.hasNext();)
                            {
                                query[i] += "\n" + it.next();
                            }
                        }
                        //declare variables used in for loop cursor
                        if (!uniqueForCurDetail.isEmpty())
                        {
                            for (Iterator it = uniqueForCurDetail.iterator(); it.hasNext();)
                            {
                                String varName = it.next().toString();
                                String varDataType = varName.substring(varName.indexOf('=') + 1);
                                varName = "VAR_" + varName.substring(0, varName.indexOf('='));
                                query[i] += "\n DECLARE " + varName + " " + varDataType + ";";
                            }
                        }
                        //decalre for loop for loop
                        if (!forCurList.isEmpty())
                        {
                            Iterator itr = forCurList.iterator();
                            while (itr.hasNext())
                            {
                                query[i] += "\n" + itr.next();
                            }
                        }
                        query[i] += " " + tempCur;
                        if (!tempCur.isEmpty() && !forCurList.isEmpty())
                        {
                            query[i] += "\nDECLARE CONTINUE HANDLER FOR NOT FOUND SET curNotFound = true;";
                        }
                        break;
                    }
                }

            }
            //replace variables used in for loop with udf vars
            if (!forCurDetail.isEmpty())
            {
                for (int i = 0; i < query.length; i++)
                {
                    Set intoVarNames = forCurDetail.keySet();
                    Iterator it = intoVarNames.iterator();
                    while (it.hasNext())
                    {
                        String varName = it.next().toString().trim();
                        if (query[i].toUpperCase().contains(varName.toUpperCase().trim()))
                        {
                            String tmpStr = varName.substring(varName.indexOf('.') + 1);
                            query[i] = query[i].replace(varName, "VAR_" + tmpStr);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceWhile(String[] query)
    {
        int whileCount = 0;
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                //REMOVE SPACES 
                query[i] = rmSpc(query[i]);
                //skip all comment lines
                if (query[i].trim().startsWith("--") || (query[i].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF")))
                {
                    continue;
                }

                while ((query[i].toUpperCase().contains("WHILE ")
                        || query[i].toUpperCase().contains("WHILE(")
                        || query[i].toUpperCase().contains("WHILE ("))
                        && !query[i].toUpperCase().contains(":WHILE"))
                {
                    whileCount++;
                    query[i] = "WHILE" + whileCount + ":" + query[i].trim();
                    if (query[i].toUpperCase().indexOf("LOOP") > -1)
                    {
                        query[i] = query[i].replace("LOOP", "DO");
                        query[i] = query[i].replace("loop", "DO");
                    }
                    else
                    {
                        query[i + 1] = query[i + 1].replace("LOOP", "DO");
                        query[i + 1] = query[i + 1].replace("loop", "DO");
                    }
                    int loopCount = 0;
                    for (int k = i; k < query.length; k++)
                    {
                        //skip all comment lines
                        if (query[k].trim().startsWith("--"))
                        {
                            continue;
                        }
                        else if (query[k].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF"))
                        {
                            continue;
                        }
                        if (query[k].toUpperCase().contains("END LOOP") && loopCount > 0)
                        {
                            loopCount--;
                        }
                        else if (query[k].toUpperCase().contains("END LOOP") && loopCount == 0)
                        {
                            query[k] = query[k].replace(" ;", ";");
                            query[k] = query[k].replace("END LOOP;", "END WHILE;");
                            query[k] = query[k].replace("end loop;", "END WHILE;");
                            break;
                        }
                        else if (query[k].toUpperCase().contains("LOOP") && !query[k].toUpperCase().contains("LOOP_") && !query[k].toUpperCase().contains("_LOOP"))
                        {
                            loopCount++;
                        }
                        else if (query[k].toUpperCase().contains("EXIT WHEN")
                                && !query[k].toUpperCase().contains("%NOTFOUND")
                                && loopCount == 0)
                        {
                            String expr = query[k].substring(query[k].toUpperCase().indexOf("EXIT WHEN") + 9, query[k].indexOf(';'));
                            query[k] = "IF " + expr + " THEN"
                                    + "\n LEAVE WHILE" + whileCount + ";"
                                    + "\n END IF;";
                        }
                        else if ((query[k].toUpperCase().contains("EXIT ;") || query[k].toUpperCase().contains("EXIT;")) && loopCount == 0)
                        {
                            query[k] = query[k].replace("EXIT ;", "EXIT;");
                            query[k] = query[k].replace("exit ;", "EXIT;");
                            query[k] = query[k].replace("EXIT;", "LEAVE WHILE" + whileCount + ";");
                            //query[k] = "LEAVE WHILE" + whileCount + ";";
                        }
                        else if ((query[k].toUpperCase().contains("RETURN ;") || query[k].toUpperCase().contains("RETURN;")) && loopCount == 0)
                        {
                            query[k] = query[k].replace("RETURN ;", "RETURN;");
                            query[k] = query[k].replace("return ;", "RETURN;");
                            query[k] = query[k].replace("RETURN;", "LEAVE WHILE" + whileCount + ";");
                            //query[k] = "LEAVE WHILE" + whileCount + ";";
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] replaceForLoop(String[] query)
    {
        int forCount = 0;
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                //REMOVE SPACES 
                query[i] = rmSpc(query[i]);
                //skip all comment lines
                if (query[i].trim().startsWith("--"))
                {
                    continue;
                }
                else if (query[i].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF"))
                {
                    continue;
                }
                while (query[i].toUpperCase().contains("FOR ")
                        && query[i].toUpperCase().contains(" IN ")
                        && query[i].toUpperCase().contains("..")
                        && !query[i].toUpperCase().contains(" DECLARE ")
                        && !query[i].toUpperCase().contains(":FOR ")
                        && !query[i].toUpperCase().trim().contains("OPEN "))
                {
                    boolean reverseLoop = false;
                    forCount++;
                    //get variable name                                         
                    String varName = query[i].substring(query[i].toUpperCase().indexOf("FOR") + 3, query[i].toUpperCase().indexOf(" IN "));
                    forVarName.add(varName);
                    String start = query[i].substring(query[i].toUpperCase().indexOf(" IN ") + 4, query[i].indexOf(".."));
                    String end = query[i].substring(query[i].indexOf("..") + 2);

                    if (end.toUpperCase().indexOf("LOOP") > -1)
                    {
                        end = end.substring(0, end.toUpperCase().indexOf("LOOP"));
                    }

                    String condition = "\n SET " + varName + " = " + start
                            + "; \n WHILE " + varName + " <= " + end + " DO";

                    if (query[i].toUpperCase().contains(" REVERSE "))
                    {
                        start = start.substring(start.toUpperCase().indexOf("REVERSE") + 7);
                        condition = "\n SET " + varName + " = " + end
                                + "; \n WHILE " + varName + " >= " + start + " DO";
                        reverseLoop = true;
                    }

                    if (query[i].toUpperCase().indexOf("LOOP") > -1)
                    {
                        query[i] = "FOR" + forCount + ":LOOP" + condition;
                    }
                    else
                    {
                        query[i] = "";
                        if (query[i + 1].trim().indexOf("LOOP") > -1)
                        {
                            query[i + 1] = query[i + 1].replace("LOOP", "FOR" + forCount + ":LOOP") + condition;
                        }
                        else
                        {
                            query[i + 1] = query[i + 1].replace("loop", "FOR" + forCount + ":LOOP") + condition;
                        }
                    }

                    int loopCount = 0;
                    for (int k = i; k < query.length; k++)
                    {
                        //skip all comment lines
                        if (query[k].trim().startsWith("--"))
                        {
                            continue;
                        }
                        else if (query[k].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF"))
                        {
                            continue;
                        }
                        //replace end loop
                        if (query[k].toUpperCase().contains("END LOOP") && loopCount > 0)
                        {
                            loopCount--;
                        }
                        else if (query[k].toUpperCase().contains("END LOOP") && loopCount == 0)
                        {
                            if (reverseLoop)
                            {
                                query[k] = "\nSET " + varName + " = " + varName + "- 1;\nEND WHILE; \nEND LOOP;";
                            }
                            else
                            {
                                query[k] = "\nSET " + varName + " = " + varName + "+ 1;\nEND WHILE; \nEND LOOP;";
                            }
                            break;
                        }
                        else if ((query[k].trim().toUpperCase().startsWith("LOOP")
                                || query[k].toUpperCase().contains(" LOOP"))
                                && !query[k].toUpperCase().contains("_LOOP")
                                && !query[k].toUpperCase().contains("LOOP_")
                                && !query[k].toUpperCase().contains(":LOOP"))
                        {
                            loopCount++;
                        }
                        else if (query[k].toUpperCase().contains("EXIT WHEN")
                                && !query[k].toUpperCase().contains("%NOTFOUND")
                                && loopCount == 0)
                        {
                            String expr = query[k].substring(query[k].toUpperCase().indexOf("EXIT WHEN") + 9, query[k].indexOf(';'));
                            query[k] = "IF " + expr + " THEN"
                                    + "\n LEAVE FOR" + forCount + ";"
                                    + "\n END IF;";
                        }
                        else if ((query[k].toUpperCase().contains("EXIT ;") || query[k].toUpperCase().contains("EXIT;")) && loopCount == 0)
                        {
                            query[k] = query[k].replace("EXIT ;", "EXIT;");
                            query[k] = query[k].replace("exit ;", "EXIT;");
                            query[k] = query[k].replace("EXIT;", "LEAVE FOR" + forCount + ";");
                        }
                        else if ((query[k].toUpperCase().contains("RETURN ;") || query[k].toUpperCase().contains("RETURN;")) && loopCount == 0)
                        {
                            query[k] = query[k].replace("RETURN ;", "RETURN;");
                            query[k] = query[k].replace("return ;", "RETURN;");
                            query[k] = query[k].replace("RETURN;", "LEAVE FOR" + forCount + ";");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setExitPoint(String[] query)
    {
        try
        {
            //exit point name
            for (int i = 0; i < query.length; i++)
            {
                //replace goto exitpoint statement
                if (query[i].toUpperCase().contains("GOTO "))
                {
                    query[i] = "--" + query[i];
                }
                else if (query[i].contains("<<") && query[i].contains(">>"))
                {
                    query[i] = "--" + query[i];
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setBeginLabel(String[] query)
    {
        int beginCount = 0;
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                while (query[i].trim().toUpperCase().startsWith("BEGIN") && !query[i].trim().toUpperCase().contains(":BEGIN"))
                {
                    beginCount++;
                    if (query[i].trim().toUpperCase().startsWith("BEGIN"))
                    {
                        query[i] = "BEGIN" + beginCount + ":" + query[i];
                    }
                    int loopCount = 0;
                    for (int k = i; k < query.length; k++)
                    {
                        //skip all comment lines
                        if (query[k].trim().startsWith("--"))
                        {
                            continue;
                        }
                        else if (query[k].trim().startsWith("/*") && !query[k].trim().startsWith("/* TCIGBF"))
                        {
                            continue;
                        }
                        //replace end
                        if ((query[k].trim().toUpperCase().equals("END;")
                                || query[k].trim().toUpperCase().equals("END ;"))
                                && loopCount > 0)
                        {
                            loopCount--;
                        }
                        else if ((query[k].trim().toUpperCase().equals("END;")
                                || query[k].trim().toUpperCase().equals("END ;"))
                                && loopCount == 0)
                        {
                            break;
                        }
                        else if (query[k].trim().toUpperCase().startsWith("BEGIN") && !query[k].trim().toUpperCase().contains(":BEGIN"))
                        {
                            loopCount++;
                        }
                        else if (query[k].toUpperCase().contains("EXIT WHEN")
                                && !query[k].toUpperCase().contains("%NOTFOUND")
                                && loopCount == 0)
                        {
                            String expr = query[k].substring(query[k].toUpperCase().indexOf("EXIT WHEN") + 9, query[k].indexOf(';'));
                            query[k] = "IF " + expr + " THEN"
                                    + "\n LEAVE BEGIN" + beginCount + ";"
                                    + "\n END IF;";
                        }
                        else if ((query[k].toUpperCase().contains("EXIT ;")
                                || query[k].toUpperCase().contains("EXIT;"))
                                && loopCount == 0)
                        {
                            query[k] = query[k].replace("EXIT ;", "EXIT;");
                            query[k] = query[k].replace("exit ;", "EXIT;");
                            query[k] = query[k].replace("EXIT;", "LEAVE BEGIN" + beginCount + ";");
                            //query[k] = "LEAVE BEGIN" + beginCount + ";";
                        }
                        else if ((query[k].toUpperCase().contains("RETURN ;")
                                || query[k].toUpperCase().contains("RETURN;"))
                                && loopCount == 0)
                        {
                            query[k] = query[k].replace("RETURN ;", "RETURN;");
                            query[k] = query[k].replace("return ;", "RETURN;");
                            query[k] = query[k].replace("RETURN;", "LEAVE BEGIN" + beginCount + ";");
                            //query[k] = "LEAVE BEGIN" + beginCount + ";";
                        }
                    }
                }
            }
            for (int k = 0; k < query.length; k++)
            {
                //skip all comment lines
                if (query[k].trim().startsWith("--"))
                {
                    continue;
                }
                else if (query[k].trim().startsWith("/*") && !query[k].trim().startsWith("/* TCIGBF"))
                {
                    continue;
                }
                if (query[k].toUpperCase().contains("EXIT WHEN")
                        && !query[k].toUpperCase().contains("%NOTFOUND"))
                {
                    String expr = query[k].substring(query[k].toUpperCase().indexOf("EXIT WHEN") + 9, query[k].indexOf(';'));
                    query[k] = "IF " + expr + " THEN"
                            + "\n LEAVE BEGIN0;"
                            + "\n END IF;";
                }
                else if (query[k].toUpperCase().contains("EXIT ;") || query[k].toUpperCase().contains("EXIT;"))
                {
                    query[k] = query[k].replace("EXIT ;", "EXIT;");
                    query[k] = query[k].replace("exit ;", "EXIT;");
                    query[k] = query[k].replace("EXIT;", "LEAVE BEGIN0;");
                    query[k] = "LEAVE BEGIN0;";
                }
                else if (query[k].toUpperCase().contains("RETURN ;") || query[k].toUpperCase().contains("RETURN;"))
                {
                    query[k] = query[k].replace("RETURN ;", "RETURN;");
                    query[k] = query[k].replace("return ;", "RETURN;");
                    query[k] = query[k].replace("return;", "RETURN;");
                    query[k] = query[k].replace("RETURN;", "LEAVE BEGIN0;");
                    //query[k] = "LEAVE BEGIN0;";
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    private String[] setDualStatement(String[] query)
    {
        try
        {
            for (int i = 0; i < query.length; i++)
            {
                if (query[i].toUpperCase().contains("FROM DUAL"))
                {
                    query[i] = query[i].replace("FROM DUAL", "");
                    query[i] = query[i].replace("from dual", "");
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return query;
    }

    public int insert(OracToMysqlFormbean formbean) throws ClassNotFoundException, SQLException
    {
        return manager.insert(formBeanToEntityBean(formbean));
    }

    private OracleToMysqlEntityBean formBeanToEntityBean(OracToMysqlFormbean formbean)
    {
        OracleToMysqlEntityBean entityBean = new OracleToMysqlEntityBean();
        entityBean.setEmp_code(formbean.getEmp_code());
        entityBean.setItem(formbean.getItem());
        entityBean.setItemNmCmb(formbean.getItemNmCmb());
        entityBean.setSchema(formbean.getSchema());
        entityBean.setServer(formbean.getServer());
        return entityBean;
    }

    private String replaceDataType(String query)
    {
        String sqlLine = query;
        if ((sqlLine.toUpperCase().contains(" DATE ") || sqlLine.toUpperCase().contains(" DATE;") || sqlLine.toUpperCase().contains(" DATE)")
                || sqlLine.toUpperCase().contains(" DATE ;") || sqlLine.toUpperCase().contains(" DATE,"))
                && !sqlLine.toUpperCase().contains("DATETIME"))
        {
            sqlLine = sqlLine.replaceAll(" DATE", " DATETIME");
            sqlLine = sqlLine.replaceAll(" date", " DATETIME");
        }
        else if (sqlLine.toUpperCase().contains(" VARCHAR2"))
        {
            if (!sqlLine.toUpperCase().contains(" VARCHAR2(") && !sqlLine.toUpperCase().contains(" VARCHAR2 ("))
            {
                sqlLine = sqlLine.replaceAll(" VARCHAR2", " VARCHAR(4000)");
                sqlLine = sqlLine.replaceAll(" varchar2", " VARCHAR(4000)");
            }
            else
            {
                sqlLine = sqlLine.replaceAll(" VARCHAR2", " VARCHAR");
                sqlLine = sqlLine.replaceAll(" varchar2", " VARCHAR");
            }
        }
        else if (sqlLine.toUpperCase().contains(" VARCHAR"))
        {
            if (!sqlLine.toUpperCase().contains(" VARCHAR("))
            {
                sqlLine = sqlLine.replaceAll(" VARCHAR", " VARCHAR(4000)");
                sqlLine = sqlLine.replaceAll(" varchar", " VARCHAR(4000)");
            }
        }
        else if (sqlLine.toUpperCase().contains(" NUMBER") || sqlLine.toUpperCase().contains(" FLOAT"))
        {
            sqlLine = sqlLine.replaceAll(" NUMBER", " DECIMAL");
            sqlLine = sqlLine.replaceAll(" number", " DECIMAL");
            sqlLine = sqlLine.replaceAll(" FLOAT", " DECIMAL");
            sqlLine = sqlLine.replaceAll(" float", " DECIMAL");
        }
        else if (sqlLine.toUpperCase().contains(" CHAR"))
        {
            sqlLine = sqlLine.replaceAll(" CHAR", " TEXT");
            sqlLine = sqlLine.replaceAll(" char", " TEXT");
        }
        else if (sqlLine.toUpperCase().contains(" LONG"))
        {
            sqlLine = sqlLine.replaceAll(" LONG", " BIGINT");
            sqlLine = sqlLine.replaceAll(" long", " BIGINT");
        }
        else if (sqlLine.toUpperCase().contains(" INTEGER"))
        {
            sqlLine = sqlLine.replaceAll(" INTEGER", " INT");
            sqlLine = sqlLine.replaceAll(" integer", " INT");
        }
        else if (sqlLine.toUpperCase().contains(" DOUBLE PRECISION"))
        {
            sqlLine = sqlLine.replaceAll(" DOUBLE PRECISION", " DOUBLE");
            sqlLine = sqlLine.replaceAll(" double precision", " DOUBLE");
        }
        return sqlLine;
    }

    public List getSchema(String server) throws ClassNotFoundException, SQLException
    {
        return manager.getSchema(server);
    }

    public List getItemList(String item, String owner, String server) throws ClassNotFoundException, SQLException
    {
        return manager.getItemList(item, owner, server);
    }

    public String getNewQuery(String item, String owner, String server, String itemNm) throws ClassNotFoundException, SQLException
    {
        return manager.getNewQuery(item, owner, server, itemNm);
    }

    public String[] setNewCursor(String[] query, String server, String schema) throws ClassNotFoundException, SQLException
    {
        String cursorName = "";
        StringBuilder intoVarName = new StringBuilder();
        int idxEndLoop = 0;
        int cursorCount = 0;
        List prevCursorList = new ArrayList();
        for (int i = 0; i < query.length; i++)
        {
            if (query[i].trim().toUpperCase().startsWith("FOR ") && query[i].trim().toUpperCase().contains(" IN")
                    && !query[i].trim().toUpperCase().contains(".."))//&& query[i].trim().toUpperCase().contains("SELECT "))
            {
                String cursorQuery = query[i].substring(query[i].toUpperCase().indexOf(" IN") + 3, query[i].toUpperCase().indexOf("LOOP"));
                if (cursorQuery.toUpperCase().indexOf("SELECT ") > -1)
                {
                    if (cursorQuery.indexOf('(') > -1)
                    {
                        cursorQuery = cursorQuery.substring(cursorQuery.indexOf('(') + 1);
                    }
                    if (cursorQuery.indexOf(')') > -1)
                    {
                        cursorQuery = cursorQuery.substring(0, cursorQuery.lastIndexOf(')'));
                    }
                }
                cursorQuery = cursorQuery.replace("ABCPQRXYZ", "");
                String tmpCurName = "";
                //String paramToCur = "";
                if (tmpCurName.toUpperCase().indexOf("SELECT ") <= -1)
                {
                    tmpCurName = cursorQuery;
                }

                if (cursorQuery.toUpperCase().indexOf("SELECT ") <= -1)
                {
                    boolean flag = true;
                    for (int k = 0; k < query.length; k++)
                    {
                        if (query[k].trim().startsWith("--") || query[k].trim().startsWith("/*"))
                        {
                            continue;
                        }
                        if (cursorQuery.indexOf('(') > -1)
                        {
                            cursorQuery = cursorQuery.substring(0, cursorQuery.indexOf('('));
                        }
                        //if (query[k].toUpperCase().contains("CURSOR " + cursorQuery.toUpperCase().trim()))
                        if (query[k].toUpperCase().contains("CURSOR ") && query[k].toUpperCase().contains(cursorQuery.toUpperCase().trim())
                                && query[k].toUpperCase().contains("SELECT "))
                        {
                            flag = false;
                            prevCursorList.add(query[k]);
                            String tmpStr = query[k];
                            if (query[k].toUpperCase().indexOf("SELECT") > -1)
                            {
                                //tmpStr = query[k].substring(0, query[k].toUpperCase().indexOf("SELECT "));
                                tmpStr = query[k].substring(0, query[k].toUpperCase().indexOf("IS ") + 3);
                            }
                            if (tmpStr.indexOf('(') > -1 && tmpStr.indexOf(')') > -1)
                            {
                                //String paramCursorVarName = query[k].substring(query[k].indexOf('(') + 1, query[k].lastIndexOf(')'));
                                String paramCursorVarName = tmpStr.substring(tmpStr.indexOf('(') + 1, tmpStr.lastIndexOf(')'));
                                String[] allParams = paramCursorVarName.split(",");
                                for (int m = 0; m < allParams.length; m++)
                                {
                                    //if param pass to cursor contains %type
                                    allParams[m] = allParams[m].replace("%type", "% TYPE");
                                    allParams[m] = allParams[m].replace("%TYPE", "% TYPE");
                                    if (allParams[m].toUpperCase().trim().contains("% TYPE") || allParams[m].toUpperCase().trim().contains("% type"))
                                    {
                                        String[] qrLineAry = allParams[m].trim().replace(" %", "%").split(" ");
                                        String newLine = findType(qrLineAry, server);
                                        if (newLine != null)
                                        {
                                            String newStr = "";
                                            if (!qrLineAry[0].toUpperCase().contains("DECLARE"))
                                            {
                                                newStr = "DECLARE " + qrLineAry[0] + " " + newLine + ";";
                                            }
                                            else
                                            {
                                                newStr = qrLineAry[0] + " " + newLine + ";";
                                            }
                                            allParams[m] = newStr;
                                            allParams[m] = replaceDataType(allParams[m]);
                                        }
                                    }
                                    else
                                    {
                                        allParams[m] = "DECLARE " + allParams[m] + " ;";
                                        allParams[m] = replaceDataType(allParams[m]);
                                    }
                                    paramCurVars.add(allParams[m]);
                                }
                            }

                            cursorQuery = query[k].substring(query[k].toUpperCase().indexOf("SELECT "));
                            if (cursorQuery.indexOf(';') > -1)
                            {
                                String comment = "";
                                while (cursorQuery.contains("--"))
                                {
                                    comment = cursorQuery.substring(cursorQuery.indexOf("--"));
                                    cursorQuery = cursorQuery.substring(0, cursorQuery.indexOf(comment));
                                }
                                while (cursorQuery.contains("/*"))
                                {
                                    comment = cursorQuery.substring(cursorQuery.indexOf("/*"));
                                    cursorQuery = cursorQuery.substring(0, cursorQuery.indexOf(comment));
                                }
                                cursorQuery = cursorQuery.substring(0, cursorQuery.trim().length() - 1);
                                cursorQuery = cursorQuery.replace("ABCPQRXYZ", "");
                                cursorQuery = cursorQuery + comment;
                            }
                            query[k] = "";
                            break;
                        }
                        else if (query[k].toUpperCase().contains("CURSOR ") && query[k].toUpperCase().contains(cursorQuery.toUpperCase().trim())
                                && query[k].toUpperCase().contains("IS "))
                        {
                            cursorQuery = query[k].substring(query[k].indexOf("IS ") + 3);
                            if (cursorQuery.indexOf(";") > -1)
                            {
                                cursorQuery = cursorQuery.substring(0, cursorQuery.length() - 1);
                            }
                        }
                    }
                    for (int h = 0; h < prevCursorList.size(); h++)
                    {
                        String line = prevCursorList.get(h).toString();
                        if (line.toUpperCase().contains("CURSOR ") && line.toUpperCase().contains(cursorQuery.toUpperCase().trim()) && flag)
                        {
                            String tmpStr = line;
                            if (line.toUpperCase().indexOf("SELECT") > -1)
                            {
                                tmpStr = line.substring(0, line.toUpperCase().indexOf("SELECT "));
                            }
                            if (tmpStr.indexOf('(') > -1 && tmpStr.indexOf(')') > -1)
                            {
                                //String paramCursorVarName = query[k].substring(query[k].indexOf('(') + 1, query[k].lastIndexOf(')'));
                                String paramCursorVarName = tmpStr.substring(tmpStr.indexOf('(') + 1, tmpStr.lastIndexOf(')'));
                                String[] allParams = paramCursorVarName.split(",");
                                for (int m = 0; m < allParams.length; m++)
                                {
                                    allParams[m] = allParams[m].replace("%type", "% TYPE");
                                    allParams[m] = allParams[m].replace("%TYPE", "% TYPE");
                                    if (allParams[m].toUpperCase().trim().contains("% TYPE") || allParams[m].toUpperCase().trim().contains("% type"))
                                    {
                                        String[] qrLineAry = allParams[m].trim().replace(" %", "%").split(" ");
                                        String newLine = findType(qrLineAry, server);
                                        if (newLine != null)
                                        {
                                            String newStr = "";
                                            if (!qrLineAry[0].toUpperCase().contains("DECLARE"))
                                            {
                                                newStr = "DECLARE " + qrLineAry[0] + " " + newLine + ";";
                                            }
                                            else
                                            {
                                                newStr = qrLineAry[0] + " " + newLine + ";";
                                            }
                                            allParams[m] = newStr;
                                            allParams[m] = replaceDataType(allParams[m]);
                                        }
                                    }
                                    else
                                    {
                                        allParams[m] = "DECLARE " + allParams[m] + " ;";
                                        allParams[m] = replaceDataType(allParams[m]);
                                    }
                                    paramCurVars.add(allParams[m]);
                                }
                            }
                            cursorQuery = line.substring(line.toUpperCase().indexOf("SELECT "));
                            if (cursorQuery.indexOf(';') > -1)
                            {
                                String comment = "";
                                while (cursorQuery.contains("--"))
                                {
                                    comment = cursorQuery.substring(cursorQuery.indexOf("--"));
                                    cursorQuery = cursorQuery.substring(0, cursorQuery.indexOf(comment));
                                }
                                while (cursorQuery.contains("/*"))
                                {
                                    comment = cursorQuery.substring(cursorQuery.indexOf("/*"));
                                    cursorQuery = cursorQuery.substring(0, cursorQuery.indexOf(comment));
                                }
                                cursorQuery = cursorQuery.substring(0, cursorQuery.trim().length() - 1);
                                cursorQuery = cursorQuery.replace("ABCPQRXYZ", "");
                                cursorQuery = cursorQuery + comment;
                            }
                        }
                    }
                }

                cursorName = query[i].substring(query[i].toUpperCase().indexOf("FOR") + 3, query[i].toUpperCase().indexOf(" IN"));

                if (tmpCurName.toUpperCase().indexOf("SELECT ") > -1)
                {
                    tmpCurName = cursorName;
                }
                if (tmpCurName.toUpperCase().indexOf("SELECT ") <= -1 && tmpCurName.indexOf('(') > -1 && tmpCurName.indexOf(')') > -1)
                {
                    tmpCurName = tmpCurName.substring(0, tmpCurName.indexOf('('));
                }
                String intoVarString = "";
                if (query[i].toUpperCase().contains("LOOP"))
                {
                    int loopCount = 0;
                    for (int k = i + 1; k < query.length; k++)
                    {
                        //skip all comment lines
                        if (query[k].trim().startsWith("--"))
                        {
                            continue;
                        }
                        else if (query[k].trim().startsWith("/*") && !query[i].trim().startsWith("/* TCIGBF"))
                        {
                            continue;
                        }
                        if (query[k].toUpperCase().contains("END LOOP") && loopCount > 0)
                        {
                            loopCount--;
                        }
                        else if (query[k].toUpperCase().contains("END LOOP") && loopCount == 0)
                        {
                            idxEndLoop = k;
                            break;
                        }
                        else if (query[k].toUpperCase().contains("LOOP") && !query[k].toUpperCase().contains("LOOP_") && !query[k].toUpperCase().contains("_LOOP"))
                        {
                            loopCount++;
                        }
                    }
                    StringBuilder tmpStr = new StringBuilder();
                    for (int k = i; k <= idxEndLoop; k++)
                    {
                        tmpStr.append(query[k]);
                        tmpStr.append(" ");
                    }
                    intoVarString = tmpStr.toString();
                    query[idxEndLoop] = "\n END LOOP;\n CLOSE " + tmpCurName.trim() + cursorCount + ";";
                    if (intoVarString.indexOf("LOOP") > -1)
                    {
                        intoVarString = intoVarString.substring(intoVarString.toUpperCase().indexOf("LOOP"));
                    }
                }
                if (!intoVarString.equals(""))
                {
                    //get column names                       
                    intoVarName = getForVarDatatype(cursorQuery, server, cursorName, intoVarString, schema, query);
                }
                if (intoVarName.toString().contains("Procedure contains cursor with non-select statement"))
                {
                    query = new String[1];
                    query[0] = "Procedure contains cursor with non-select statement";
                }
                else
                {
                    if (query[i].toUpperCase().contains("LOOP") && !query[i].toUpperCase().contains("END LOOP"))
                    {
                        if (cursorQuery.toUpperCase().indexOf("SELECT ") > -1)
                        {
                            forCurList.add("\nDECLARE " + tmpCurName.trim() + cursorCount + " CURSOR FOR " + cursorQuery + ";");
                        }

                        StringBuilder line = new StringBuilder();
                        //if param pass to cursor in for loop                    
                        if (!paramCurVars.isEmpty())
                        {
                            for (Iterator it = paramCurVars.iterator(); it.hasNext();)
                            {
                                String varName = it.next().toString();
                                String tmpVarName = varName;
                                varName = varName.substring(varName.indexOf("DECLARE") + 7).trim();
                                varName = varName.substring(0, varName.trim().indexOf(" "));

                                if (query[i].indexOf('(') > -1 && query[i].indexOf(')') > -1)
                                {
                                    String[] value = query[i].substring(query[i].indexOf('(') + 1, query[i].lastIndexOf(')')).split(",");
                                    for (int m = 0; m < value.length; m++)
                                    {
                                        line.append("\n SET ").append(varName).append(" = ");
                                        if (tmpVarName.contains("DECIMAL") || tmpVarName.contains("INT")
                                                || tmpVarName.contains("DOUBLE") || tmpVarName.contains("BIGINT"))
                                        {
                                            line.append("'");
                                        }
                                        line.append(value[m]);
                                        if (tmpVarName.contains("DECIMAL") || tmpVarName.contains("INT")
                                                || tmpVarName.contains("DOUBLE") || tmpVarName.contains("BIGINT"))
                                        {
                                            line.append("'");
                                        }
                                        line.append(";");
                                    }
                                }
                            }
                        }
                        line.append("\n OPEN ").append(tmpCurName.trim()).append(cursorCount).append(";\n LOOP ");
                        line.append("\n FETCH ").append(tmpCurName.trim()).append(cursorCount);
                        if (!intoVarName.toString().isEmpty())
                        {
                            line.append(" INTO ").append(intoVarName.toString());
                        }
                        line.append(";");
                        line.append("\nIF (curNotFound) THEN");
                        line.append("  \nLEAVE LOOP;");
                        line.append("\nEND IF;");

                        query[i] = line.toString();
                    }
                }
                cursorCount++;
            }
        }
        return query;
    }

    public StringBuilder getForVarDatatype(String forCursorQuery, String server, String cursorName, String intoVarString, String schema, String[] sqlLines) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder intoCurVarName = new StringBuilder();
        try
        {
            String forQuery = forCursorQuery.replace("ABCPQRXYZ", "");
            forQuery = forQuery.replace(";", "");
            //get all declare variables n replace with defaults
            List decVars = getAllDecVars(sqlLines);
            forQuery = replaceQueryVarToDef(forQuery, decVars);
            //get all inout vars n replace with defaults                                    
            forQuery = replaceQueryVarToDef(forQuery, inOutVars);
            //get all for cursor vars n replace with defaults                       
            forQuery = replaceQueryVarToDef(forQuery, forCurVars);
            //get all params pass to cursor n replace with defaults
            List forCurParamVars = new ArrayList();
            for (Iterator it = paramCurVars.iterator(); it.hasNext();)
            {
                forCurParamVars.add(it.next().toString());
            }
            forQuery = replaceQueryVarToDef(forQuery, forCurParamVars);
            if (forQuery.indexOf("--") > -1)
            {
                forQuery = forQuery.substring(0, forQuery.indexOf("--"));
            }
            if (forQuery.indexOf("/*") > -1)
            {
                forQuery = forQuery.substring(0, forQuery.indexOf("/*"));
            }

            if (!(forQuery.toUpperCase().contains("INSERT ")
                    || forQuery.toUpperCase().contains("UPDATE ")
                    || forQuery.toUpperCase().contains("DELETE ")
                    || forQuery.toUpperCase().contains("ALTER ")
                    || forQuery.toUpperCase().contains("DROP ")
                    || forQuery.toUpperCase().contains("CREATE ")))
            {
                forQuery = "SELECT * FROM (" + forQuery + " ) WHERE 1 != 1";
                DirectConnection dc = new DirectConnection();
                conn = dc.getConnection(server);
                if (conn != null)
                {
                    ps = conn.prepareStatement(forQuery);
                    rs = ps.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int numberOfColumns = rsmd.getColumnCount();
                    for (int n = 1; n <= numberOfColumns; n++)
                    {
                        String dataType = "";
                        if (rsmd.getColumnTypeName(n).equals("VARCHAR") || rsmd.getColumnTypeName(n).equals("VARCHAR2") || rsmd.getColumnTypeName(n).equals("CHAR"))
                        {
                            dataType = replaceDataType(" " + rsmd.getColumnTypeName(n) + "(" + rsmd.getColumnDisplaySize(n) + ")");
                        }
                        else
                        {
                            dataType = replaceDataType(" " + rsmd.getColumnTypeName(n));
                        }
                        intoCurVarName.append(cursorName.trim()).append(".").append(rsmd.getColumnName(n)).append(",");
                        forCurDetail.put(cursorName.trim() + "." + rsmd.getColumnName(n), dataType);
                        uniqueForCurDetail.add(rsmd.getColumnName(n) + " = " + dataType);
                        forCurVars.add("DECLARE " + cursorName.trim() + "." + rsmd.getColumnName(n) + " = " + dataType);
                    }
                    if (!intoCurVarName.toString().isEmpty())
                    {
                        intoCurVarName.deleteCharAt(intoCurVarName.length() - 1);
                    }
                }
            }
            else
            {
                intoCurVarName = new StringBuilder();
                intoCurVarName.append("Procedure contains cursor with non-select statement");
            }
        }
        catch (SQLException e)
        {
            Logger.ErrorLogger(e);
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
            if (ps != null)
            {
                ps.close();
            }
            if (conn != null)
            {
                conn.close();
            }
        }
        return intoCurVarName;
    }

    //set variable name if it contains procname.varname
    public String[] setVarName(String[] query, String itemType)
    {
        String procName = "";
        for (int i = 0; i < query.length; i++)
        {
            if (query[i].toUpperCase().contains("CREATE " + itemType))
            {
                procName = query[i].substring(query[i].indexOf("CREATE " + itemType) + 17).trim().toUpperCase();
                if (procName.indexOf('.') > -1)
                {
                    procName = procName.substring(procName.indexOf('.') + 1).trim();
                }
                if (procName.indexOf('(') > -1)
                {
                    procName = procName.substring(0, procName.indexOf('(')).trim();
                }
            }
            else if (!procName.isEmpty() && query[i].toUpperCase().contains(procName + "."))
            {
                query[i] = query[i].replace(procName + ".", "");
                while (query[i].toUpperCase().contains(procName + "."))
                {
                    query[i] = (query[i].substring(0, query[i].toUpperCase().indexOf(procName + ".")) + query[i].substring(query[i].toUpperCase().indexOf(procName + ".") + procName.length() + 1));
                }
            }
            else if (!procName.isEmpty() && query[i].toUpperCase().contains("END " + procName + ";"))
            {
                query[i] = query[i].toUpperCase().replace("END " + procName.toUpperCase() + ";", "END;");
            }
        }
        return query;
    }

    public List getAllDecVars(String[] query)
    {
        List ls = new ArrayList();
        for (int i = 0; i < query.length; i++)
        {
            //get all declare statement
            if (query[i].trim().toUpperCase().startsWith("DECLARE") && !query[i].trim().toUpperCase().startsWith("DECLARE CURSOR"))
            {
                if (query[i].trim().startsWith("--") || query[i].trim().startsWith("/*"))
                {
                    continue;
                }

                if (!query[i].trim().endsWith(";") && !query[i].trim().equalsIgnoreCase("DECLARE"))
                {
                    int counter = i;
                    while (!query[counter].trim().endsWith(";"))
                    {
                        ls.add(query[counter]);
                        counter++;
                    }
                    i = counter;
                }
                else if (!query[i].trim().equalsIgnoreCase("DECLARE") && query[i].trim().endsWith(";"))
                {
                    if (query[i].indexOf(';') != query[i].lastIndexOf(';'))
                    {
                        String[] tmpStr = query[i].split(";");
                        for (int j = 0; j < tmpStr.length; j++)
                        {
                            ls.add(tmpStr[j] + ";");
                        }
                    }
                    else
                    {
                        ls.add(query[i]);
                    }
                }
            }
        }
        return ls;
    }

    public String replaceQueryVarToDef(String curQuery, List allVars)
    {
        String forCurQuery = " " + curQuery + " ";
        for (int i = 0; i < allVars.size(); i++)
        {
            String line = allVars.get(i).toString().trim();
            line = line.replace("  ", " ");
            String varname = "";
            if (line.indexOf("DECLARE") > -1)
            {
                varname = line.substring(line.indexOf("DECLARE ") + 8, line.indexOf(" ", line.indexOf("DECLARE ") + 8));
            }
            else if (line.indexOf("IN ") > -1)
            {
                varname = line.substring(line.indexOf("IN ") + 3, line.indexOf(" ", line.indexOf("IN ") + 3));
            }
            else if (line.indexOf("OUT ") > -1)
            {
                varname = line.substring(line.indexOf("OUT ") + 4, line.indexOf(" ", line.indexOf("OUT ") + 4));
            }
            else if (line.indexOf("INOUT ") > -1)
            {
                varname = line.substring(line.indexOf("INOUT ") + 6, line.indexOf(" ", line.indexOf("INOUT ") + 6));
            }
            else
            {
                if (!line.isEmpty() && line.indexOf(" ") > -1)
                {
                    varname = line.substring(0, line.indexOf(" "));
                }
            }
            if (!varname.isEmpty() && forCurQuery.contains(varname))
            {
                String stuffing = "#STUFFING#";
                Pattern regexForDots = Pattern.compile("(\\.\\b" + varname + "\\b)|(\\b" + varname + "\\b\\.)");
                Matcher regexMatcher = regexForDots.matcher(forCurQuery);
                while (regexMatcher.find())
                {
                    forCurQuery = forCurQuery.replace(regexMatcher.group(), regexMatcher.group().substring(0, 2) + stuffing + regexMatcher.group().substring(2));
                }
                if (line.toUpperCase().contains(" VARCHAR")
                        || line.toUpperCase().contains(" TEXT"))
                {
                    forCurQuery = forCurQuery.replaceAll("\\b" + varname + "\\b", "'0'");
                }
                else if (line.toUpperCase().contains(" DATETIME") || line.toUpperCase().contains(" DATE") || line.toUpperCase().contains(" TIMESTAMP"))
                {
                    forCurQuery = forCurQuery.replaceAll("\\b" + varname + "\\b", "SYSDATE");
                }
                else
                {
                    forCurQuery = forCurQuery.replaceAll("\\b" + varname + "\\b", "0");
                }
                forCurQuery = forCurQuery.replaceAll(stuffing, "");
            }
        }
        return forCurQuery;
    }

    public int validateProc(String procName, String server, String procline, String schema, String resourceType, String itemType) throws SQLException, ClassNotFoundException
    {
        String procLines = procline;
        int result = 89;
        String newProcName = procName + System.nanoTime();
        if (newProcName.length() > 30)
        {
            newProcName = "tmp" + System.nanoTime();
        }
        if (procLines.indexOf("\"" + procName + "\"") > -1)
        {
            procLines = procLines.replace("\"" + procName + "\"", newProcName);
        }
        else
        {
            procLines = procLines.replace(procName, newProcName);
        }

        if (resourceType.equals("usingConn"))
        {
            result = manager.validateProc("CREATE " + procLines, server, schema, newProcName, itemType);
        }
        return result;
    }
}