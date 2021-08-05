/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sonam Patel
 */
public class OracleAnalyzer
{

    public QueryInfoBean analyzeQuery(String query, final String server)
    {
        query = query.replaceAll("\\s{1,}", " ");
        query = query.trim();
        while (query.endsWith(";"))
        {
            query = query.substring(0, query.length() - 1).trim();
        }

        QueryInfoBean qBean = new QueryInfoBean();
        qBean.setQuery(query);
        String token = query.split(" ")[0].toUpperCase();
        if (token.equals("CREATE"))
        {
            qBean.setClause("CREATE");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            query = query.substring(6).trim();

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            if (query.toUpperCase().startsWith("CLUSTER "))
            {
                dbObjBean.setObjType("CLUSTER");
                query = query.substring(8).trim();
            }
            if (query.toUpperCase().startsWith("DATABASE LINK ") || query.toUpperCase().contains(" DATABASE LINK "))
            {
                if (query.toUpperCase().startsWith("SHARED "))
                {
                    query = query.substring(7).trim();
                    if (query.toUpperCase().startsWith("PUBLIC "))
                    {
                        query = query.substring(7).trim();
                    }
                }
                else if (query.toUpperCase().startsWith("PUBLIC "))
                {
                    query = query.substring(7).trim();
                    if (query.toUpperCase().startsWith("SHARED "))
                    {
                        query = query.substring(7).trim();
                    }
                }
                if (query.toUpperCase().startsWith("DATABASE LINK "))
                {
                    dbObjBean.setObjType("DATABASE LINK");
                    query = query.substring(14).trim();
                }
            }
            else if (query.toUpperCase().startsWith("DATABASE "))
            {
                dbObjBean.setObjType("DATABASE");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("SEQUENCE "))
            {
                dbObjBean.setObjType("SEQUENCE");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("TABLE "))
            {
                dbObjBean.setObjType("TABLE");
                query = query.substring(6).trim();
            }
            else
            {
                if (query.toUpperCase().startsWith("OR REPLACE "))
                {
                    query = query.substring(11).trim();
                }
                if (query.toUpperCase().startsWith("DIRECTORY "))
                {
                    dbObjBean.setObjType("DIRECTORY");
                    query = query.substring(10).trim();
                }
                else if (query.toUpperCase().startsWith("FUNCTION "))
                {
                    dbObjBean.setObjType("FUNCTION");
                    query = query.substring(9).trim();
                }
                else if (query.toUpperCase().startsWith("INDEX ") || (query.toUpperCase().contains(" INDEX ")
                        && (query.toUpperCase().startsWith("UNIQUE ") || query.toUpperCase().startsWith("BITMAP "))))
                {
                    if (query.toUpperCase().startsWith("UNIQUE ") || query.toUpperCase().startsWith("BITMAP "))
                    {
                        query = query.substring(7).trim();
                    }
                    if (query.toUpperCase().startsWith("INDEX "))
                    {
                        dbObjBean.setObjType("INDEX");
                        query = query.substring(6).trim();
                    }
                }
                else if (query.toUpperCase().startsWith("PACKAGE BODY "))
                {
                    dbObjBean.setObjType("PACKAGE BODY");
                    query = query.substring(13).trim();
                }
                else if (query.toUpperCase().startsWith("PACKAGE "))
                {
                    dbObjBean.setObjType("PACKAGE");
                    query = query.substring(8).trim();
                }
                else if (query.toUpperCase().startsWith("PROCEDURE "))
                {
                    dbObjBean.setObjType("PROCEDURE");
                    query = query.substring(10).trim();
                }
                else if (query.toUpperCase().startsWith("ROLE "))
                {
                    dbObjBean.setObjType("ROLE");
                    query = query.substring(5).trim();
                }
                else if (query.toUpperCase().startsWith("SCHEMA AUTHORIZATION "))
                {
                    dbObjBean.setObjType("SCHEMA");
                    query = query.substring(21).trim();
                }
                else if (query.toUpperCase().startsWith("SYNONYM ") || query.toUpperCase().startsWith("PUBLIC SYNONYM "))
                {
                    if (query.toUpperCase().startsWith("PUBLIC "))
                    {
                        query = query.substring(7).trim();
                    }
                    if (query.toUpperCase().startsWith("SYNONYM "))
                    {
                        dbObjBean.setObjType("SYNONYM");
                        query = query.substring(8).trim();
                    }
                }
                else if (query.toUpperCase().startsWith("TRIGGER "))
                {
                    dbObjBean.setObjType("TRIGGER");
                    query = query.substring(8).trim();
                }
                else if (query.toUpperCase().startsWith("TYPE BODY "))
                {
                    dbObjBean.setObjType("TYPE");
                    query = query.substring(10).trim();
                }
                else if (query.toUpperCase().startsWith("TYPE "))
                {
                    dbObjBean.setObjType("TYPE");
                    query = query.substring(5).trim();
                }
                else if (query.toUpperCase().startsWith("MATERIALIZED VIEW "))
                {
                    dbObjBean.setObjType("MATERIALIZED VIEW");
                    query = query.substring(18).trim();
                }
                else if (query.toUpperCase().startsWith("VIEW ") || query.toUpperCase().contains(" VIEW ")
                        && (query.toUpperCase().startsWith("FORCE ") || query.toUpperCase().startsWith("NO FORCE ")))
                {
                    if (query.toUpperCase().startsWith("FORCE "))
                    {
                        query = query.substring(6).trim();
                    }
                    else if (query.toUpperCase().startsWith("NO FORCE "))
                    {
                        query = query.substring(9).trim();
                    }
                    if (query.toUpperCase().startsWith("VIEW "))
                    {
                        dbObjBean.setObjType("VIEW");
                        query = query.substring(5).trim();
                    }
                }
            }
            if (dbObjBean.getObjType() != null && ("DATABASE".equals(dbObjBean.getObjType()) || "DATABASE LINK".equals(dbObjBean.getObjType()) || "SCHEMA".equals(dbObjBean.getObjType())))
            {
                if (query.contains(" "))
                {
                    String tmp = query.substring(0, query.indexOf(' ')).trim();
                    if (tmp.contains("("))
                    {
                        tmp = tmp.substring(0, tmp.indexOf('(')).trim();
                    }
                    dbObjBean.setSchema(tmp);
                    dbObjBean.setObjName(tmp);
                }
                else
                {
                    dbObjBean.setSchema(query.trim());
                    dbObjBean.setObjName(query.trim());
                }
            }
            else
            {
                if (query.contains(" "))
                {
                    String tmp = query.substring(0, query.indexOf(' ')).trim();
                    if (tmp.contains("("))
                    {
                        tmp = tmp.substring(0, tmp.indexOf('(')).trim();
                    }
                    if (tmp.contains("."))
                    {
                        dbObjBean.setSchema(tmp.split("\\.")[0].trim());
                        dbObjBean.setObjName(tmp.split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(tmp);
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.trim());
                }
            }
            if (dbObjBean.getObjType() != null && "DIRECTORY".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("AS "))
                    {
                        qBean.setSubClause("AS");
                        query = query.substring(3).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "INDEX".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("ON "))
                    {
                        qBean.setSubClause("ON");
                        query = query.substring(3).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "PACKAGE".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("IS "))
                    {
                        qBean.setSubClause("IS");
                    }
                    else if (query.toUpperCase().startsWith("AS "))
                    {
                        qBean.setSubClause("AS");
                    }
                    else if (query.toUpperCase().contains(" IS "))
                    {
                        query = query.substring(query.toUpperCase().indexOf(" IS ")).trim();
                        qBean.setSubClause("IS");
                    }
                    else if (query.toUpperCase().contains(" AS "))
                    {
                        query = query.substring(query.toUpperCase().indexOf(" AS ")).trim();
                        qBean.setSubClause("AS");
                    }
                    if (qBean.getSubClause() != null)
                    {
                        query = query.substring(3).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "PACKAGE BODY".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("IS "))
                    {
                        qBean.setSubClause("IS");
                    }
                    else if (query.toUpperCase().startsWith("AS "))
                    {
                        qBean.setSubClause("AS");
                    }
                    if (qBean.getSubClause() != null)
                    {
                        query = query.substring(3).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "ROLE".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("NOT IDENTIFIED"))
                    {
                        qBean.setSubClause("NOT IDENTIFIED");
                    }
                    else if (query.toUpperCase().startsWith("IDENTIFIED "))
                    {
                        qBean.setSubClause("IDENTIFIED");
                        query = query.substring(11).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "SYNONYM".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("FOR "))
                    {
                        qBean.setSubClause("FOR");
                        query = query.substring(4).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("ALTER"))
        {
            qBean.setClause("ALTER");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            query = query.substring(6).trim();

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            if (query.toUpperCase().startsWith("CLUSTER "))
            {
                dbObjBean.setObjType("CLUSTER");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("DATABASE "))
            {
                dbObjBean.setObjType("DATABASE");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("FUNCTION "))
            {
                dbObjBean.setObjType("FUNCTION");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("INDEX "))
            {
                dbObjBean.setObjType("INDEX");
                query = query.substring(6).trim();
            }
            else if (query.toUpperCase().startsWith("PACKAGE "))
            {
                dbObjBean.setObjType("PACKAGE");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("PROCEDURE "))
            {
                dbObjBean.setObjType("PROCEDURE");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("ROLE "))
            {
                dbObjBean.setObjType("ROLE");
                query = query.substring(5).trim();
            }
            else if (query.toUpperCase().startsWith("SEQUENCE "))
            {
                dbObjBean.setObjType("SEQUENCE");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("TABLE "))
            {
                dbObjBean.setObjType("TABLE");
                query = query.substring(6).trim();
                if (query.contains(" "))
                {
                    if (query.substring(0, query.indexOf(' ')).trim().contains("."))
                    {
                        dbObjBean.setSchema(query.substring(0, query.indexOf(' ')).trim().split("\\.")[0].trim());
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim().split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim());
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.trim());
                }
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();

                    Pattern addPattern = Pattern.compile("([ ]{0,1}\\([ ]{0,1})?([a-zA-Z0-9_]+ )([a-zA-Z0-9]{1,}([ ]{0,1}\\([ ]{0,1}[\\d]+([ ]{0,1},[ ]{0,1}[\\d]{1,})?[ ]{0,1}\\))?)");
                    Pattern addConPattern = Pattern.compile("([a-zA-Z0-9_]+)");
                    Pattern dropPattern = Pattern.compile("([a-zA-Z0-9_]+)");
                    Pattern modifyPattern = Pattern.compile("([ ]{0,1}\\([ ]{0,1})?([a-zA-Z0-9_]+ )([a-zA-Z0-9]{1,}([ ]{0,1}\\([ ]{0,1}[\\d]+([ ]{0,1},[ ]{0,1}[\\d]{1,})?[ ]{0,1}\\))?)");
                    Pattern renameColPattern = Pattern.compile("([a-zA-Z0-9_]+) [Tt]{1,1}[Oo]{1,1} ([a-zA-Z0-9_]+)");
                    Pattern renameToPattern = Pattern.compile("([a-zA-Z0-9_]+)");

                    Matcher matcher = null;

                    String subCls = "";
                    String subClsTrg = "";
                    while (query.length() > 0)
                    {
                        if (query.toUpperCase().startsWith("ADD ") || query.toUpperCase().startsWith("ADD("))
                        {
                            if (query.toUpperCase().startsWith("ADD "))
                            {
                                query = query.substring(4).trim();
                            }
                            else if (query.toUpperCase().startsWith("ADD("))
                            {
                                query = query.substring(3).trim();
                            }

                            if (query.toUpperCase().startsWith("CONSTRAINT ") || query.toUpperCase().startsWith("FOREIGN KEY")
                                    || query.toUpperCase().startsWith("PRIMARY KEY") || query.toUpperCase().startsWith("UNIQUE")
                                    || query.toUpperCase().startsWith("CHECK"))
                            {
                                if (query.toUpperCase().startsWith("CONSTRAINT "))
                                {
                                    query = query.substring(11).trim();
                                    matcher = addConPattern.matcher(query);
                                    if (matcher.find())
                                    {
                                        if (matcher.group(0) != null)
                                        {
                                            query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                            query = query.replaceAll("\\s{1,}", " ");
                                        }
                                    }
                                    else
                                    {
                                        subCls = "";
                                        subClsTrg = "";
                                        query = "";
                                    }
                                }

                                if (query.toUpperCase().startsWith("FOREIGN KEY(") || query.toUpperCase().startsWith("FOREIGN KEY ("))
                                {
                                    query = query.substring(11).trim();
                                    query = query.substring(1).trim();
                                    while (query.length() > 0)
                                    {
                                        matcher = addConPattern.matcher(query);
                                        if (matcher.find())
                                        {
                                            if (matcher.group(0) != null && matcher.group(0).toUpperCase().equals("REFERENCES"))
                                            {
                                                query = "";
                                                break;
                                            }
                                            subCls += "ADD,";
                                            subClsTrg += matcher.group(1).trim() + ",";

                                            if (matcher.group(0) != null)
                                            {
                                                query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                query = query.replaceAll("\\s{1,}", " ");
                                            }

                                            if (!query.toUpperCase().matches(".*[A-Z]+.*"))
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                            }
                                        }
                                    }
                                }
                                else if ((query.toUpperCase().startsWith("PRIMARY KEY(") || query.toUpperCase().startsWith("PRIMARY KEY (")
                                        || query.toUpperCase().startsWith("UNIQUE(") || query.toUpperCase().startsWith("UNIQUE (")) && query.endsWith(")"))
                                {
                                    if (query.toUpperCase().startsWith("PRIMARY KEY"))
                                    {
                                        query = query.substring(11).trim();
                                    }
                                    else if (query.toUpperCase().startsWith("UNIQUE"))
                                    {
                                        query = query.substring(6).trim();
                                    }
                                    query = query.substring(1).trim();
                                    while (query.length() > 0)
                                    {
                                        matcher = addConPattern.matcher(query);
                                        if (matcher.find())
                                        {
                                            subCls += "ADD,";
                                            subClsTrg += matcher.group(1).trim() + ",";

                                            if (matcher.group(0) != null)
                                            {
                                                query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                query = query.replaceAll("\\s{1,}", " ");
                                            }

                                            if (!query.toUpperCase().matches(".*[A-Z]+.*"))
                                            {
                                                query = "";
                                            }
                                        }
                                    }
                                }
                                else if (query.toUpperCase().startsWith("CHECK(") || query.toUpperCase().startsWith("CHECK ("))
                                {
                                    query = "";
                                    subCls += "ADD,";
                                    subClsTrg += ",";
                                }
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else
                            {
                                if (query.startsWith("COLUMN "))
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                    continue;
                                }

                                boolean multi = false;
                                while (query.length() > 0)
                                {
                                    matcher = addPattern.matcher(query);
                                    if (matcher.find())
                                    {
                                        if (matcher.group(1) != null && "(".equals(matcher.group(1).trim()))
                                        {
                                            multi = true;
                                        }
                                        if (matcher.group(2) != null && !"COLUMN".equals(matcher.group(2).trim().toUpperCase()))
                                        {
                                            subCls += "ADD,";
                                            subClsTrg += matcher.group(2).trim() + ",";
                                        }
                                        else
                                        {
                                            subCls = "";
                                            subClsTrg = "";
                                            query = "";
                                        }

                                        if (query.contains(matcher.group(0)))
                                        {
                                            query = query.replace(matcher.group(0), "").trim();
                                            query = query.replaceAll("\\s{1,}", " ");
                                            if (!multi && query.contains(","))
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                                continue;
                                            }
                                            if (query.contains(","))
                                            {
                                                query = query.substring(query.indexOf(',') + 1).trim();
                                            }
                                            else
                                            {
                                                query = "";
                                            }
                                            continue;
                                        }
                                        else
                                        {
                                            query = "";
                                        }
                                    }
                                    else
                                    {
                                        query = "";
                                    }
                                }
                            }
                        }
                        else if (query.toUpperCase().startsWith("DROP ") || query.toUpperCase().startsWith("DROP("))
                        {
                            if (query.toUpperCase().startsWith("DROP "))
                            {
                                query = query.substring(5).trim();
                            }
                            else if (query.toUpperCase().startsWith("DROP("))
                            {
                                query = query.substring(4).trim();
                            }

                            if (query.toUpperCase().startsWith("COLUMN "))
                            {
                                query = query.substring(7).trim();
                                matcher = dropPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null)
                                    {
                                        subCls += "DROP,";
                                        subClsTrg += matcher.group(1).trim() + ",";
                                        if (query.contains(matcher.group(0)))
                                        {
                                            query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                            query = query.replaceAll("\\s{1,}", " ");
                                            if (!"".equals(query))
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                            }
                                            break;
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        subCls = "";
                                        subClsTrg = "";
                                        query = "";
                                    }
                                }
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else if (query.startsWith("("))
                            {
                                query = query.substring(1).trim();
                                matcher = dropPattern.matcher(query);
                                while (matcher.find())
                                {
                                    subCls += "DROP,";
                                    subClsTrg += matcher.group(0).trim() + ",";
                                    if (query.contains(matcher.group(0)))
                                    {
                                        query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                        query = query.replaceAll("\\s{1,}", " ");
                                        if (query.contains(","))
                                        {
                                            query = query.substring(query.indexOf(',') + 1).trim();
                                        }
                                        else
                                        {
                                            if (!"".equals(query) && !")".equals(query))
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                            }
                                            query = "";
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        break;
                                    }
                                }
                            }
                            else if (query.toUpperCase().startsWith("CONSTRAINT "))
                            {
                                query = query.substring(11).trim();
                                matcher = dropPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null)
                                    {
                                        subCls += "DROP,";
                                        subClsTrg += ",";

                                        if (query.contains(matcher.group(0)))
                                        {
                                            query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                            query = query.replaceAll("\\s{1,}", " ");
                                            if (!"".equals(query))
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                            }
                                            break;
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        subCls = "";
                                        subClsTrg = "";
                                        query = "";
                                    }
                                }
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else if (query.toUpperCase().startsWith("PRIMARY KEY"))
                            {
                                query = query.substring(11).trim();
                                subCls += "DROP,";
                                subClsTrg += ",";
                                if (!"".equals(query))
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else if (query.toUpperCase().startsWith("UNIQUE"))
                            {
                                query = query.substring(6).trim();
                                if (query.startsWith("("))
                                {
                                    query = query.substring(1).trim();
                                    matcher = dropPattern.matcher(query);
                                    while (matcher.find())
                                    {
                                        subCls += "DROP,";
                                        subClsTrg += matcher.group(0).trim() + ",";
                                        if (query.contains(matcher.group(0)))
                                        {
                                            query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                            query = query.replaceAll("\\s{1,}", " ");
                                            if (query.contains(","))
                                            {
                                                query = query.substring(query.indexOf(',') + 1).trim();
                                            }
                                            else
                                            {
                                                if (!"".equals(query) && !")".equals(query))
                                                {
                                                    subCls = "";
                                                    subClsTrg = "";
                                                    query = "";
                                                }
                                                query = "";
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            break;
                                        }
                                        matcher = dropPattern.matcher(query);
                                    }
                                }
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else
                            {
                                subCls = "";
                                subClsTrg = "";
                                query = "";
                            }
                        }
                        else if (query.toUpperCase().startsWith("MODIFY ") || query.toUpperCase().startsWith("MODIFY("))
                        {
                            if (query.toUpperCase().startsWith("MODIFY "))
                            {
                                query = query.substring(7).trim();
                            }
                            else if (query.toUpperCase().startsWith("MODIFY("))
                            {
                                query = query.substring(6).trim();
                            }

                            if (query.startsWith("COLUMN "))
                            {
                                subCls = "";
                                subClsTrg = "";
                                query = "";
                                continue;
                            }

                            boolean multi = false;
                            while (query.length() > 0)
                            {
                                matcher = modifyPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null && "(".equals(matcher.group(1).trim()))
                                    {
                                        multi = true;
                                    }
                                    if (matcher.group(2) != null && !"COLUMN".equals(matcher.group(2).trim().toUpperCase()))
                                    {
                                        subCls += "MODIFY,";
                                        subClsTrg += matcher.group(2).trim() + ",";
                                    }
                                    else
                                    {
                                        subCls = "";
                                        subClsTrg = "";
                                        query = "";
                                    }

                                    if (query.contains(matcher.group(0)))
                                    {
                                        query = query.replace(matcher.group(0), "").trim();
                                        query = query.replaceAll("\\s{1,}", " ");
                                        if (!multi && query.contains(","))
                                        {
                                            subCls = "";
                                            subClsTrg = "";
                                            query = "";
                                            continue;
                                        }
                                        if (query.contains(","))
                                        {
                                            query = query.substring(query.indexOf(',') + 1).trim();
                                        }
                                        else
                                        {
                                            query = "";
                                        }
                                        continue;
                                    }
                                    else
                                    {
                                        query = "";
                                    }
                                }
                                else
                                {
                                    query = "";
                                }
                            }
                        }
                        else if (query.toUpperCase().startsWith("RENAME "))
                        {
                            query = query.substring(7).trim();
                            if (query.toUpperCase().startsWith("COLUMN "))
                            {
                                query = query.substring(7).trim();
                                matcher = renameColPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null)
                                    {
                                        subCls += "RENAME,";
                                        subClsTrg += matcher.group(1).trim() + ",";
                                    }
                                    if (query.contains(matcher.group(0)))
                                    {
                                        query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                        query = query.replaceAll("\\s{1,}", " ");
                                        if (!"".equals(query))
                                        {
                                            subCls = "";
                                            subClsTrg = "";
                                            query = "";
                                        }
                                        break;
                                    }
                                    else
                                    {
                                        break;
                                    }
                                }
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else if (query.toUpperCase().startsWith("TO "))
                            {
                                subCls += "RENAME,";
                                subClsTrg += ",";
                                query = query.substring(3).trim();
                                matcher = renameToPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (query.contains(matcher.group(0)))
                                    {
                                        query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                        query = query.replaceAll("\\s{1,}", " ");
                                        query = query.trim();
                                        if (!"".equals(query))
                                        {
                                            subCls = "";
                                            subClsTrg = "";
                                            query = "";
                                        }
                                        break;
                                    }
                                    else
                                    {
                                        break;
                                    }
                                }
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else
                            {
                                subCls = "";
                                subClsTrg = "";
                                query = "";
                            }
                        }
                        else
                        {
                            subCls = "";
                            subClsTrg = "";
                            query = "";
                        }
                    }
                    if (subCls.endsWith(","))
                    {
                        subCls = subCls.substring(0, subCls.lastIndexOf(","));
                        qBean.setSubClause(subCls);
                    }
                    if (subClsTrg.endsWith(","))
                    {
                        subClsTrg = subClsTrg.substring(0, subClsTrg.lastIndexOf(","));
                        qBean.setSubClauseTarget(subClsTrg);
                    }
                }
            }
            else if (query.toUpperCase().startsWith("TABLESPACE "))
            {
                dbObjBean.setObjType("TABLESPACE");
                query = query.substring(11).trim();
            }
            else if (query.toUpperCase().startsWith("TRIGGER "))
            {
                dbObjBean.setObjType("TRIGGER");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("TYPE "))
            {
                dbObjBean.setObjType("TYPE");
                query = query.substring(5).trim();
            }
            else if (query.toUpperCase().startsWith("MATERIALIZED VIEW "))
            {
                dbObjBean.setObjType("MATERIALIZED VIEW");
                query = query.substring(18).trim();
            }
            else if (query.toUpperCase().startsWith("VIEW "))
            {
                dbObjBean.setObjType("VIEW");
                query = query.substring(5).trim();
                if (query.contains(" "))
                {
                    if (query.substring(0, query.indexOf(' ')).trim().contains("."))
                    {
                        dbObjBean.setSchema(query.substring(0, query.indexOf(' ')).trim().split("\\.")[0].trim());
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim().split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim());
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.trim());
                }

                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();

                    Pattern addPattern = Pattern.compile("ADD (COLUMN |CONSTRAINT )?");
                    Pattern modifyPattern = Pattern.compile("MODIFY (CONSTRAINT )?");
                    Pattern dropPattern = Pattern.compile("DROP (CONSTRAINT |PRIMARY KEY|UNIQUE )?");
                    Pattern compilePattern = Pattern.compile("COMPILE");
                    String subCls = "";
                    String subClsTrg = "";
                    String tmp = "";
                    while (query.length() > 0)
                    {
                        if (query.toUpperCase().startsWith("ADD "))
                        {
                            Matcher matcher = addPattern.matcher(query.toUpperCase());
                            if (matcher.find())
                            {
                                subCls += matcher.group(0).trim() + ",";
                                if ("ADD COLUMN".equals(matcher.group(0).trim()))
                                {
                                    if (query.contains(","))
                                    {
                                        tmp = query.substring(0, query.indexOf(',')).trim();
                                    }
                                    else
                                    {
                                        tmp = query;
                                    }
                                    tmp = tmp.substring(matcher.group(0).length()).trim();
                                    if (tmp.contains(" "))
                                    {
                                        subClsTrg += tmp.substring(0, tmp.indexOf(' ')).trim() + ",";
                                    }
                                    else
                                    {
                                        subClsTrg += tmp.trim() + ",";
                                    }
                                }
                                else if ("ADD CONSTRAINT".equals(matcher.group(0).trim()))
                                {
                                    if (query.contains(","))
                                    {
                                        tmp = query.substring(0, query.indexOf(',')).trim();
                                    }
                                    else
                                    {
                                        tmp = query;
                                    }
                                    tmp = tmp.substring(matcher.group(0).length()).trim();
                                    int start = 0, end = tmp.length();
                                    if (tmp.toUpperCase().contains("KEY("))
                                    {
                                        start = tmp.toUpperCase().indexOf("KEY(") + 4;
                                        end = tmp.toUpperCase().indexOf(')', start);
                                    }
                                    else if (tmp.toUpperCase().contains("KEY ("))
                                    {
                                        start = tmp.toUpperCase().indexOf("KEY (") + 5;
                                        end = tmp.toUpperCase().indexOf(')', start);
                                    }
                                    subClsTrg += tmp.substring(start, end) + ",";
                                }
                            }
                            if (query.contains(","))
                            {
                                query = query.substring(query.indexOf(',') + 1).trim();
                            }
                            else
                            {
                                query = "";
                            }
                        }
                        else if (query.toUpperCase().startsWith("MODIFY "))
                        {
                            Matcher matcher = modifyPattern.matcher(query.toUpperCase());
                            if (matcher.find())
                            {
                                subCls += matcher.group(0).trim() + ",";
                                if (query.contains(","))
                                {
                                    tmp = query.substring(0, query.indexOf(',')).trim();
                                }
                                else
                                {
                                    tmp = query;
                                }
                                tmp = tmp.substring(matcher.group(0).length()).trim();
                                if (tmp.contains(" "))
                                {
                                    subClsTrg += tmp.substring(0, tmp.indexOf(' ')).trim() + ",";
                                }
                                else
                                {
                                    subClsTrg += tmp.trim() + ",";
                                }
                            }
                            if (query.contains(","))
                            {
                                query = query.substring(query.indexOf(',') + 1).trim();
                            }
                            else
                            {
                                query = "";
                            }
                        }
                        else if (query.toUpperCase().startsWith("DROP "))
                        {
                            Matcher matcher = dropPattern.matcher(query.toUpperCase());
                            if (matcher.find())
                            {
                                subCls += matcher.group(0).trim() + ",";
                                if ("DROP CONSTRAINT".equals(matcher.group(0).trim()))
                                {
                                    if (query.contains(","))
                                    {
                                        tmp = query.substring(0, query.indexOf(',')).trim();
                                    }
                                    else
                                    {
                                        tmp = query;
                                    }
                                    tmp = tmp.substring(matcher.group(0).length()).trim();
                                    if (tmp.contains(" "))
                                    {
                                        subClsTrg += tmp.substring(0, tmp.indexOf(' ')).trim() + ",";
                                    }
                                    else
                                    {
                                        subClsTrg += tmp.trim() + ",";
                                    }
                                }
                                else if ("DROP UNIQUE".equals(matcher.group(0).trim()))
                                {
                                    if (query.contains(","))
                                    {
                                        tmp = query.substring(0, query.indexOf(',')).trim();
                                    }
                                    else
                                    {
                                        tmp = query;
                                    }
                                    tmp = tmp.substring(matcher.group(0).length()).trim();
                                    if (tmp.contains(" "))
                                    {
                                        subClsTrg += tmp.substring(0, tmp.indexOf(' ')).trim() + ",";
                                    }
                                    else
                                    {
                                        subClsTrg += tmp.trim() + ",";
                                    }
                                }
                            }
                            if (query.contains(","))
                            {
                                query = query.substring(query.indexOf(',') + 1).trim();
                            }
                            else
                            {
                                query = "";
                            }
                        }
                        else if (query.toUpperCase().startsWith("COMPILE"))
                        {
                            Matcher matcher = compilePattern.matcher(query.toUpperCase());
                            if (matcher.find())
                            {
                                subCls += matcher.group(0).trim() + ",";
                            }
                            if (query.contains(","))
                            {
                                query = query.substring(query.indexOf(',') + 1).trim();
                            }
                            else
                            {
                                query = "";
                            }
                        }
                        else
                        {
                            if (query.contains(","))
                            {
                                query = query.substring(query.indexOf(',') + 1).trim();
                            }
                            else
                            {
                                query = "";
                            }
                        }
                    }
                    if (subCls.endsWith(","))
                    {
                        subCls = subCls.substring(0, subCls.lastIndexOf(","));
                    }
                    if (subClsTrg.endsWith(","))
                    {
                        subClsTrg = subClsTrg.substring(0, subClsTrg.lastIndexOf(","));
                    }
                    if (!"".equals(subCls))
                    {
                        qBean.setSubClause(subCls);
                    }
                    if (!"".equals(subClsTrg))
                    {
                        qBean.setSubClauseTarget(subClsTrg);
                    }
                }
            }

            if (dbObjBean.getObjType() != null && !"TABLE".equals(dbObjBean.getObjType()) && !"VIEW".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    if (query.substring(0, query.indexOf(' ')).trim().contains("."))
                    {
                        dbObjBean.setSchema(query.substring(0, query.indexOf(' ')).trim().split("\\.")[0].trim());
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim().split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim());
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.trim());
                }
            }

            if (dbObjBean.getObjType() != null && "INDEX".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();

                    if (query.toUpperCase().contains("RENAME TO "))
                    {
                        qBean.setSubClause("RENAME TO");
                        query = query.substring(10).trim();
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "ROLE".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();
                    if (query.toUpperCase().startsWith("NOT IDENTIFIED"))
                    {
                        qBean.setSubClause("NOT IDENTIFIED");
                    }
                    else if (query.toUpperCase().startsWith("IDENTIFIED "))
                    {
                        qBean.setSubClause("IDENTIFIED");
                        query = query.substring(11).trim();
                        qBean.setSubClauseTarget(query.trim());
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "TABLESPACE".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();

                    if (query.toUpperCase().contains("RENAME TO "))
                    {
                        qBean.setSubClause("RENAME TO");
                    }
                }
            }
            else if (dbObjBean.getObjType() != null && "TRIGGER".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    query = query.substring(query.indexOf(' ')).trim();

                    if (query.toUpperCase().startsWith("ENABLE"))
                    {
                        qBean.setSubClause("ENABLE");
                    }
                    else if (query.toUpperCase().startsWith("DISABLE"))
                    {
                        qBean.setSubClause("DISABLE");
                    }
                    else if (query.toUpperCase().startsWith("RENAME TO "))
                    {
                        qBean.setSubClause("RENAME TO");
                    }
                    else if (query.toUpperCase().startsWith("COMPILE"))
                    {
                        qBean.setSubClause("COMPILE");
                    }
                }
            }
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("DROP"))
        {
            qBean.setClause("DROP");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            query = query.substring(4).trim();

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            if (query.toUpperCase().startsWith("CLUSTER "))
            {
                dbObjBean.setObjType("CLUSTER");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("DATABASE LINK ") || query.toUpperCase().startsWith("PUBLIC DATABASE LINK "))
            {
                if (query.toUpperCase().startsWith("PUBLIC "))
                {
                    query = query.substring(7).trim();
                }
                if (query.toUpperCase().startsWith("DATABASE LINK "))
                {
                    dbObjBean.setObjType("DATABASE LINK");
                    query = query.substring(14).trim();
                }
            }
            else if (query.toUpperCase().startsWith("DATABASE"))
            {
                dbObjBean.setObjType("DATABASE");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("DIRECTORY "))
            {
                dbObjBean.setObjType("DIRECTORY");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("FUNCTION "))
            {
                dbObjBean.setObjType("FUNCTION");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("INDEX "))
            {
                dbObjBean.setObjType("INDEX");
                query = query.substring(6).trim();
            }
            else if (query.toUpperCase().startsWith("INDEXTYPE "))
            {
                dbObjBean.setObjType("INDEXTYPE");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("PACKAGE BODY "))
            {
                dbObjBean.setObjType("PACKAGE BODY");
                query = query.substring(13).trim();
            }
            else if (query.toUpperCase().startsWith("PACKAGE "))
            {
                dbObjBean.setObjType("PACKAGE");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("PROCEDURE "))
            {
                dbObjBean.setObjType("PROCEDURE");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("ROLE "))
            {
                dbObjBean.setObjType("ROLE");
                query = query.substring(5).trim();
            }
            else if (query.toUpperCase().startsWith("SEQUENCE "))
            {
                dbObjBean.setObjType("SEQUENCE");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("SYNONYM ") || query.toUpperCase().startsWith("PUBLIC SYNONYM "))
            {
                if (query.toUpperCase().startsWith("PUBLIC "))
                {
                    query = query.substring(7).trim();
                }
                if (query.toUpperCase().startsWith("SYNONYM "))
                {
                    dbObjBean.setObjType("SYNONYM");
                    query = query.substring(8).trim();
                }
            }
            else if (query.toUpperCase().startsWith("TABLE "))
            {
                dbObjBean.setObjType("TABLE");
                query = query.substring(6).trim();
            }
            else if (query.toUpperCase().startsWith("TABLESPACE "))
            {
                dbObjBean.setObjType("TABLESPACE");
                query = query.substring(11).trim();
            }
            else if (query.toUpperCase().startsWith("TRIGGER "))
            {
                dbObjBean.setObjType("TRIGGER");
                query = query.substring(8).trim();
            }
            else if (query.toUpperCase().startsWith("TYPE BODY "))
            {
                dbObjBean.setObjType("TYPE");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("TYPE "))
            {
                dbObjBean.setObjType("TYPE");
                query = query.substring(5).trim();
            }
            else if (query.toUpperCase().startsWith("MATERIALIZED VIEW "))
            {
                dbObjBean.setObjType("MATERIALIZED VIEW");
                query = query.substring(18).trim();
            }
            else if (query.toUpperCase().startsWith("VIEW "))
            {
                dbObjBean.setObjType("VIEW");
                query = query.substring(5).trim();
            }

            if (dbObjBean.getObjType() != null && !"DATABASE".equals(dbObjBean.getObjType()))
            {
                if (query.contains(" "))
                {
                    String tmp = query.substring(0, query.indexOf(' ')).trim();
                    if (tmp.contains("("))
                    {
                        tmp = tmp.substring(0, tmp.indexOf('(')).trim();
                    }
                    if (tmp.contains("."))
                    {
                        dbObjBean.setSchema(tmp.split("\\.")[0].trim());
                        dbObjBean.setObjName(tmp.split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(tmp);
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.trim());
                }
            }
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("INSERT"))
        {
            qBean.setClause("INSERT");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            query = query.substring(6).trim();

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            dbObjBean.setObjType("TABLE");
            if (query.toUpperCase().startsWith("ALL "))
            {
                query = query.substring(4).trim();
            }
            if (query.toUpperCase().startsWith("INTO "))
            {
                query = query.substring(5).trim();
                if (query.contains(" "))
                {
                    String tmp = query.substring(0, query.indexOf(' ')).trim();
                    if (tmp.contains("("))
                    {
                        tmp = tmp.substring(0, tmp.indexOf('(')).trim();
                    }
                    if (tmp.contains("."))
                    {
                        dbObjBean.setSchema(tmp.split("\\.")[0].trim());
                        dbObjBean.setObjName(tmp.split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(tmp);
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query);
                }
            }
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("UPDATE"))
        {
            qBean.setClause("UPDATE");
            String selectQ = query;
            query = query.substring(6).trim();
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            dbObjBean.setObjType("TABLE");
            if (query.contains(" "))
            {
                if (query.substring(0, query.indexOf(' ')).trim().contains("."))
                {
                    dbObjBean.setSchema(query.substring(0, query.indexOf(' ')).trim().split("\\.")[0].trim());
                    dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim().split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim());
                }
            }
            else if (query.contains("."))
            {
                dbObjBean.setSchema(query.split("\\.")[0].trim());
                dbObjBean.setObjName(query.split("\\.")[1].trim());
            }
            else
            {
                dbObjBean.setObjName(query.trim());
            }
            if (query.toUpperCase().contains("SET "))
            {
                qBean.setSubClause("SET");
                query = query.substring(query.toUpperCase().indexOf("SET ") + 4).trim();
                if (query.toUpperCase().contains("WHERE "))
                {
                    selectQ = selectQ.replace(")SET ", ") SET ");
                    selectQ = selectQ.replace(")WHERE ", ") WHERE ");
                    int afterIndex = selectQ.toUpperCase().indexOf(" SET ") + 8;
                    //Get SELECT Query
                    QueryMaker qMaker = new QueryMaker();
                    selectQ = qMaker.makeSelectQuery(selectQ, null, null);
                    Map isValid = qMaker.executeExplain(server, null, "EXPLAIN PLAN FOR " + selectQ);
                    if ("true".equals(isValid.get("valid")))
                    {
                        qBean.setIsWherePresent(true);
                    }
                    else if ("false".equals(isValid.get("valid")))
                    {
                        String error = isValid.get("error").toString();
                        if (error.matches("(?s).* doesn't exist(?s).*") || error.matches("(?s).* does not exist(?s).*"))
                        {
                            qBean.setNeedConfirmation(true);
                        }
                        else
                        {
                            String tmp = selectQ.substring(selectQ.toUpperCase().indexOf("WHERE ", afterIndex));
                            while (tmp.toUpperCase().indexOf("WHERE ") < tmp.toUpperCase().lastIndexOf("WHERE "))
                            {
                                selectQ = qMaker.skipFirstWhere(selectQ, afterIndex);
                                isValid = qMaker.executeExplain(server, null, "EXPLAIN PLAN FOR " + selectQ);
                                if ("true".equals(isValid.get("valid")))
                                {
                                    qBean.setIsWherePresent(true);
                                    break;
                                }
                                else if ("false".equals(isValid.get("valid")))
                                {
                                    error = isValid.get("error").toString();
                                    if (error.matches("(?s).* doesn't exist(?s).*") || error.matches("(?s).* does not exist(?s).*"))
                                    {
                                        qBean.setNeedConfirmation(true);
                                        break;
                                    }
                                }
                                tmp = selectQ.substring(selectQ.toUpperCase().indexOf("WHERE ", 5));
                            }
                        }
                    }
                    //query = query.substring(0, query.toUpperCase().indexOf("WHERE ")).trim();
                }
                if (query.contains("="))
                {
                    if (query.contains("("))
                    {
                        int cnt = 0;
                        int index = query.indexOf('(');
                        int start = 0;
                        int end = 0;
                        while (index < query.length() && query.contains("(") && query.contains(")"))
                        {
                            if (query.charAt(index) == '(')
                            {
                                cnt++;
                                if (start == 0)
                                {
                                    start = index;
                                }
                            }
                            if (query.charAt(index) == ')')
                            {
                                end = index;
                                cnt--;
                                if (cnt == 0)
                                {
                                    String tmp = query.substring(0, start);
                                    tmp += query.substring(end + 1);
                                    query = tmp;
                                    index = start - 1;
                                    start = end = 0;
                                }
                            }
                            index++;
                        }
                    }

                    String subClsTrg = "";
                    String[] cls = query.split(",");
                    for (int i = 0; i < cls.length; i++)
                    {
                        cls[i] = cls[i].trim();
                        if (cls[i].contains("="))
                        {
                            String tmp = cls[i].split("=")[0].trim();
                            if (tmp.contains(" "))
                            {
                                subClsTrg += tmp.split(" ")[0].trim();
                            }
                            else
                            {
                                subClsTrg += tmp.trim();
                            }
                        }
                        else
                        {
                            if (cls[i].contains(" "))
                            {
                                subClsTrg += cls[i].split(" ")[0].trim();
                            }
                            else
                            {
                                subClsTrg += cls[i].trim();
                            }
                        }
                        subClsTrg += ",";
                    }
                    if (subClsTrg.endsWith(","))
                    {
                        subClsTrg = subClsTrg.substring(0, subClsTrg.lastIndexOf(','));
                    }
                    if (!"".equals(subClsTrg))
                    {
                        qBean.setSubClauseTarget(subClsTrg);
                    }
                }
            }
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("DELETE"))
        {
            qBean.setClause("DELETE");
            String selectQ = query;
            query = query.substring(6).trim();
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            dbObjBean.setObjType("TABLE");
            if (query.toUpperCase().startsWith("FROM "))
            {
                query = query.substring(5).trim();
                if (query.contains(" "))
                {
                    if (query.substring(0, query.indexOf(' ')).trim().contains("."))
                    {
                        dbObjBean.setSchema(query.substring(0, query.indexOf(' ')).trim().split("\\.")[0].trim());
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim().split("\\.")[1].trim());
                    }
                    else
                    {
                        dbObjBean.setObjName(query.substring(0, query.indexOf(' ')).trim());
                    }
                }
                else if (query.contains("."))
                {
                    dbObjBean.setSchema(query.split("\\.")[0].trim());
                    dbObjBean.setObjName(query.split("\\.")[1].trim());
                }
                else
                {
                    dbObjBean.setObjName(query.trim());
                }
            }
            if (query.toUpperCase().contains(" WHERE "))
            {
                //Get SELECT Query
                QueryMaker qMaker = new QueryMaker();
                selectQ = qMaker.makeSelectQuery(selectQ, null, null);
                Map isValid = qMaker.executeExplain(server, null, "EXPLAIN PLAN FOR " + selectQ);
                if ("true".equals(isValid.get("valid")))
                {
                    qBean.setIsWherePresent(true);
                }
                else if ("false".equals(isValid.get("valid")))
                {
                    String error = isValid.get("error").toString();
                    if (error.matches("(?s).* doesn't exist(?s).*") || error.matches("(?s).* does not exist(?s).*"))
                    {
                        qBean.setNeedConfirmation(true);
                    }
                    else
                    {
                        while (selectQ.toUpperCase().indexOf("WHERE ") != selectQ.toUpperCase().lastIndexOf("WHERE "))
                        {
                            selectQ = qMaker.skipFirstWhere(selectQ, -1);
                            isValid = qMaker.executeExplain(server, null, "EXPLAIN PLAN FOR " + selectQ);
                            if ("true".equals(isValid.get("valid")))
                            {
                                qBean.setIsWherePresent(true);
                                break;
                            }
                            else if ("false".equals(isValid.get("valid")))
                            {
                                error = isValid.get("error").toString();
                                if (error.matches("(?s).* doesn't exist(?s).*") || error.matches("(?s).* does not exist(?s).*"))
                                {
                                    qBean.setNeedConfirmation(true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            qBean.setDbObjInfo(dbObjBean);
        }
        return qBean;
    }
}
