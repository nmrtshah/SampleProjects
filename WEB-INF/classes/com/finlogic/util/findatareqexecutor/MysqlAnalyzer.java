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
public class MysqlAnalyzer
{

    public QueryInfoBean analyzeQuery(String query, final String server, final String database)
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
        if (token.equals("USE"))
        {
            qBean.setClause("USE");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            dbObjBean.setSchema(query.substring(4));
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("CREATE"))
        {
            qBean.setClause("CREATE");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            query = query.substring(6).trim();

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            if (query.toUpperCase().startsWith("DATABASE ") || query.toUpperCase().startsWith("SCHEMA "))
            {
                if (query.toUpperCase().startsWith("DATABASE "))
                {
                    dbObjBean.setObjType("DATABASE");
                    query = query.substring(9).trim();
                }
                else if (query.toUpperCase().startsWith("SCHEMA "))
                {
                    dbObjBean.setObjType("SCHEMA");
                    query = query.substring(7).trim();
                }
                if (query.toUpperCase().startsWith("IF NOT EXISTS "))
                {
                    query = query.substring(14).trim();
                }
            }
            else if (query.toUpperCase().startsWith("FUNCTION ") || (query.toUpperCase().contains(" FUNCTION ") && query.toUpperCase().startsWith("DEFINER")))
            {
                if (query.toUpperCase().startsWith("DEFINER"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("DEFINER = "))
                    {
                        query = query.substring(10).trim();
                        if (query.contains(" "))
                        {
                            query = query.substring(query.indexOf(' ')).trim();
                        }
                    }
                }
                if (query.toUpperCase().startsWith("FUNCTION "))
                {
                    dbObjBean.setObjType("FUNCTION");
                    query = query.substring(9).trim();
                }
            }
            else if (query.toUpperCase().startsWith("INDEX ") || (query.toUpperCase().contains(" INDEX ")
                    && (query.toUpperCase().startsWith("ONLINE ") || query.toUpperCase().startsWith("OFFLINE ")
                    || query.toUpperCase().startsWith("UNIQUE ") || query.toUpperCase().startsWith("FULLTEXT ") || query.toUpperCase().startsWith("SPATIAL "))))
            {
                if (query.toUpperCase().startsWith("ONLINE ") || query.toUpperCase().startsWith("OFFLINE "))
                {
                    if (query.toUpperCase().startsWith("ONLINE "))
                    {
                        query = query.substring(7).trim();
                    }
                    else if (query.toUpperCase().startsWith("OFFLINE "))
                    {
                        query = query.substring(8).trim();
                    }

                    if (query.toUpperCase().startsWith("UNIQUE ") || query.toUpperCase().startsWith("FULLTEXT ") || query.toUpperCase().startsWith("SPATIAL "))
                    {
                        if (query.toUpperCase().startsWith("UNIQUE "))
                        {
                            query = query.substring(7).trim();
                        }
                        else if (query.toUpperCase().startsWith("FULLTEXT "))
                        {
                            query = query.substring(8).trim();
                        }
                        else if (query.toUpperCase().startsWith("SPATIAL "))
                        {
                            query = query.substring(8).trim();
                        }
                    }
                }
                else if (query.toUpperCase().startsWith("UNIQUE ") || query.toUpperCase().startsWith("FULLTEXT ") || query.toUpperCase().startsWith("SPATIAL "))
                {
                    if (query.toUpperCase().startsWith("UNIQUE "))
                    {
                        query = query.substring(7).trim();
                    }
                    else if (query.toUpperCase().startsWith("FULLTEXT "))
                    {
                        query = query.substring(8).trim();
                    }
                    else if (query.toUpperCase().startsWith("SPATIAL "))
                    {
                        query = query.substring(8).trim();
                    }

                    if (query.toUpperCase().startsWith("ONLINE "))
                    {
                        query = query.substring(7).trim();
                    }
                    else if (query.toUpperCase().startsWith("OFFLINE "))
                    {
                        query = query.substring(8).trim();
                    }
                }
                if (query.toUpperCase().startsWith("INDEX "))
                {
                    dbObjBean.setObjType("INDEX");
                    query = query.substring(6).trim();
                }
            }
            else if (query.toUpperCase().startsWith("PROCEDURE ") || (query.toUpperCase().contains(" PROCEDURE ") && query.toUpperCase().startsWith("DEFINER")))
            {
                if (query.toUpperCase().startsWith("DEFINER"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("DEFINER = "))
                    {
                        query = query.substring(10).trim();
                        if (query.contains(" "))
                        {
                            query = query.substring(query.indexOf(' ')).trim();
                        }
                    }
                }
                if (query.toUpperCase().startsWith("PROCEDURE "))
                {
                    dbObjBean.setObjType("PROCEDURE");
                    query = query.substring(10).trim();
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
            else if (query.toUpperCase().startsWith("TRIGGER ") || (query.toUpperCase().contains(" TRIGGER ") && query.toUpperCase().startsWith("DEFINER")))
            {
                if (query.toUpperCase().startsWith("DEFINER"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("DEFINER = "))
                    {
                        query = query.substring(10).trim();
                        if (query.contains(" "))
                        {
                            query = query.substring(query.indexOf(' ')).trim();
                        }
                    }
                }
                if (query.toUpperCase().startsWith("TRIGGER "))
                {
                    dbObjBean.setObjType("TRIGGER");
                    query = query.substring(8).trim();
                }
            }
            else if (query.toUpperCase().startsWith("VIEW ") || query.toUpperCase().contains(" VIEW "))
            {
                if (query.toUpperCase().startsWith("OR REPLACE "))
                {
                    query = query.substring(11).trim();
                }
                if (query.toUpperCase().startsWith("ALGORITHM"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("ALGORITHM = UNDEFINED ") || query.toUpperCase().startsWith("ALGORITHM = TEMPTABLE "))
                    {
                        query = query.substring(22).trim();
                    }
                    else if (query.toUpperCase().startsWith("ALGORITHM = MERGE "))
                    {
                        query = query.substring(18).trim();
                    }
                }
                if (query.toUpperCase().startsWith("DEFINER"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("DEFINER = "))
                    {
                        query = query.substring(10).trim();
                        if (query.contains(" "))
                        {
                            query = query.substring(query.indexOf(' ')).trim();
                        }
                    }
                }
                if (query.toUpperCase().startsWith("SQL SECURITY DEFINER ") || query.toUpperCase().startsWith("SQL SECURITY INVOKER "))
                {
                    query = query.substring(21).trim();
                }
                if (query.toUpperCase().startsWith("VIEW "))
                {
                    dbObjBean.setObjType("VIEW");
                    query = query.substring(5).trim();
                }
            }
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
            if (dbObjBean.getObjType() != null && "INDEX".equals(dbObjBean.getObjType()))
            {
                if (query.toUpperCase().contains(" ON "))
                {
                    qBean.setSubClause("ON");
                    int idx = query.toUpperCase().indexOf(" ON ");
                    query = query.substring(idx + 4).trim();

                    if (query.contains(" "))
                    {
                        String tmp = query.substring(0, query.indexOf(' ')).trim();
                        if (tmp.contains("("))
                        {
                            tmp = tmp.substring(0, tmp.indexOf('(')).trim();
                        }
                        qBean.setSubClauseTarget(tmp);
                    }
                    else
                    {
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
            if (query.toUpperCase().startsWith("DATABASE ") || query.toUpperCase().startsWith("SCHEMA "))
            {
                if (query.toUpperCase().startsWith("DATABASE "))
                {
                    dbObjBean.setObjType("DATABASE");
                    query = query.substring(9).trim();
                }
                else if (query.toUpperCase().startsWith("SCHEMA "))
                {
                    dbObjBean.setObjType("SCHEMA");
                    query = query.substring(7).trim();
                }
            }
            else if (query.toUpperCase().startsWith("FUNCTION "))
            {
                dbObjBean.setObjType("FUNCTION");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("PROCEDURE "))
            {
                dbObjBean.setObjType("PROCEDURE");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("TABLE ") || (query.toUpperCase().contains(" TABLE ")
                    && (query.toUpperCase().startsWith("ONLINE ") || query.toUpperCase().startsWith("OFFLINE ") || query.toUpperCase().startsWith("IGNORE "))))
            {
                if (query.toUpperCase().startsWith("ONLINE ") || query.toUpperCase().startsWith("OFFLINE "))
                {
                    if (query.toUpperCase().startsWith("ONLINE "))
                    {
                        query = query.substring(7).trim();
                    }
                    else if (query.toUpperCase().startsWith("OFFLINE "))
                    {
                        query = query.substring(8).trim();
                    }
                    if (query.toUpperCase().startsWith("IGNORE "))
                    {
                        query = query.substring(7).trim();
                    }
                }
                else if (query.toUpperCase().startsWith("IGNORE "))
                {
                    query = query.substring(7).trim();
                    if (query.toUpperCase().startsWith("ONLINE "))
                    {
                        query = query.substring(7).trim();
                    }
                    else if (query.toUpperCase().startsWith("OFFLINE "))
                    {
                        query = query.substring(8).trim();
                    }
                }
                if (query.toUpperCase().startsWith("TABLE "))
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

                        Pattern addPattern = Pattern.compile("([a-zA-Z0-9_]+ )([a-zA-Z0-9]{1,}([ ]{0,1}\\([ ]{0,1}[\\d]+([ ]{0,1},[ ]{0,1}[\\d]{1,})?[ ]{0,1}\\))?)");
                        Pattern addConPattern = Pattern.compile("([a-zA-Z0-9_]+)");
                        Pattern alterPattern = Pattern.compile("([a-zA-Z0-9_]+)");
                        Pattern changePattern = Pattern.compile("([a-zA-Z0-9_]+) ([a-zA-Z0-9_]+) ([a-zA-Z0-9]{1,}([ ]{0,1}\\([ ]{0,1}[\\d]+([ ]{0,1},[ ]{0,1}[\\d]{1,})?[ ]{0,1}\\))?)");
                        Pattern dropPattern = Pattern.compile("([a-zA-Z0-9_]+)");
                        Pattern modifyPattern = Pattern.compile("([a-zA-Z0-9_]+) ([a-zA-Z0-9]{1,}([ ]{0,1}\\([ ]{0,1}[\\d]+([ ]{0,1},[ ]{0,1}[\\d]{1,})?[ ]{0,1}\\))?)");
                        Pattern renamePattern = Pattern.compile("RENAME (TO |AS )");

                        Matcher matcher = null;

                        String subCls = "";
                        String subClsTrg = "";
                        if (query.endsWith(","))
                        {
                            return qBean;
                        }
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
                                        || query.toUpperCase().startsWith("PRIMARY KEY") || query.toUpperCase().startsWith("UNIQUE"))
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

                                    if ((query.toUpperCase().startsWith("FOREIGN KEY(") || query.toUpperCase().startsWith("FOREIGN KEY (")) && query.contains(")"))
                                    {
                                        query = query.substring(11).trim();
                                        query = query.substring(1).trim();

                                        String tmp = query.substring(0, query.indexOf(')') + 1).trim();
                                        query = query.substring(query.indexOf(')') + 1).trim();
                                        if (query.contains(","))
                                        {
                                            tmp += query.substring(0, query.indexOf(',')).trim();
                                            query = query.substring(query.indexOf(',')).trim();
                                        }
                                        else
                                        {
                                            tmp += query.trim();
                                            query = "";
                                        }

                                        if (tmp.toUpperCase().contains(")REFERENCES ") || tmp.toUpperCase().contains(") REFERENCES "))
                                        {
                                            if (tmp.toUpperCase().contains(")REFERENCES "))
                                            {
                                                tmp = tmp.substring(0, tmp.indexOf(")REFERENCES ")).trim();
                                            }
                                            else if (tmp.toUpperCase().contains(") REFERENCES "))
                                            {
                                                tmp = tmp.substring(0, tmp.indexOf(") REFERENCES ")).trim();
                                            }

                                            while (tmp.length() > 0)
                                            {
                                                matcher = addConPattern.matcher(tmp);
                                                if (matcher.find())
                                                {
                                                    subCls += "ADD,";
                                                    subClsTrg += matcher.group(1).trim() + ",";

                                                    if (matcher.group(0) != null)
                                                    {
                                                        tmp = tmp.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                        tmp = tmp.replaceAll("\\s{1,}", " ");
                                                    }

                                                    if (!tmp.toUpperCase().matches(".*[A-Z]+.*"))
                                                    {
                                                        tmp = "";
                                                    }
                                                }
                                                else
                                                {
                                                    tmp = "";
                                                }
                                            }
                                        }
                                        else
                                        {
                                            subCls = "";
                                            subClsTrg = "";
                                            query = "";
                                            tmp = "";
                                        }
                                    }
                                    else if ((query.toUpperCase().startsWith("PRIMARY KEY(") || query.toUpperCase().startsWith("PRIMARY KEY (")
                                            || query.toUpperCase().startsWith("UNIQUE(") || query.toUpperCase().startsWith("UNIQUE (")
                                            || query.toUpperCase().startsWith("UNIQUE KEY(") || query.toUpperCase().startsWith("UNIQUE KEY (")) && query.contains(")"))
                                    {
                                        if (query.toUpperCase().startsWith("PRIMARY KEY"))
                                        {
                                            query = query.substring(11).trim();
                                        }
                                        else if (query.toUpperCase().startsWith("UNIQUE"))
                                        {
                                            query = query.substring(6).trim();
                                            if (query.toUpperCase().startsWith("KEY"))
                                            {
                                                query = query.substring(3).trim();
                                            }
                                        }
                                        query = query.substring(1).trim();
                                        String tmp = query.substring(0, query.indexOf(')')).trim();
                                        query = query.substring(query.indexOf(')') + 1).trim();

                                        while (tmp.length() > 0)
                                        {
                                            matcher = addConPattern.matcher(tmp);
                                            if (matcher.find())
                                            {
                                                subCls += "ADD,";
                                                subClsTrg += matcher.group(1).trim() + ",";

                                                if (matcher.group(0) != null)
                                                {
                                                    tmp = tmp.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                    tmp = tmp.replaceAll("\\s{1,}", " ");
                                                }

                                                if (!tmp.toUpperCase().matches(".*[A-Z]+.*"))
                                                {
                                                    tmp = "";
                                                }
                                            }
                                            else
                                            {
                                                tmp = "";
                                            }
                                        }
                                    }
                                    else
                                    {
                                        subCls = "";
                                        subClsTrg = "";
                                        query = "";
                                    }
                                }
                                else if (query.toUpperCase().startsWith("INDEX ") || query.toUpperCase().startsWith("KEY "))
                                {
                                    if (query.toUpperCase().startsWith("INDEX "))
                                    {
                                        query = query.substring(6).trim();
                                    }
                                    else if (query.toUpperCase().startsWith("KEY "))
                                    {
                                        query = query.substring(4).trim();
                                    }

                                    if (!(query.toUpperCase().startsWith("USING BTREE ") || query.toUpperCase().startsWith("USING HASH ") || query.startsWith("(")) && query.contains(" "))
                                    {
                                        query = query.substring(query.indexOf(' ') + 1).trim();
                                    }

                                    if (query.toUpperCase().startsWith("USING BTREE ") || query.toUpperCase().startsWith("USING HASH "))
                                    {
                                        if (query.toUpperCase().startsWith("USING BTREE "))
                                        {
                                            query = query.substring(12).trim();
                                        }
                                        else if (query.toUpperCase().startsWith("USING HASH "))
                                        {
                                            query = query.substring(11).trim();
                                        }
                                    }

                                    if (query.startsWith("(") && query.contains(")"))
                                    {
                                        String tmp = query.substring(1, query.indexOf(')') + 1).trim();
                                        query = query.substring(query.indexOf(')') + 1).trim();

                                        while (tmp.length() > 0)
                                        {
                                            matcher = addConPattern.matcher(tmp);
                                            if (matcher.find())
                                            {
                                                subCls += "ADD,";
                                                subClsTrg += matcher.group(1).trim() + ",";

                                                if (matcher.group(0) != null)
                                                {
                                                    tmp = tmp.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                    tmp = tmp.replaceAll("\\s{1,}", " ");
                                                }

                                                if (!tmp.toUpperCase().matches(".*[A-Z]+.*"))
                                                {
                                                    tmp = "";
                                                }
                                            }
                                            else
                                            {
                                                tmp = "";
                                            }
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
                                    if (query.toUpperCase().startsWith("COLUMN "))
                                    {
                                        query = query.substring(7).trim();
                                    }

                                    if (query.startsWith("("))
                                    {
                                        String subQuery = "";
                                        int len = query.length();
                                        int cnt = 0;
                                        for (int i = 1; i < len; i++)
                                        {
                                            if (query.charAt(i) == '(')
                                            {
                                                cnt++;
                                            }
                                            if (query.charAt(i) == (')'))
                                            {
                                                if (cnt == 0)
                                                {
                                                    subQuery = query.substring(1, i).trim();
                                                    query = query.substring(i + 1).trim();
                                                    break;
                                                }
                                                else
                                                {
                                                    cnt--;
                                                }
                                            }
                                        }
                                        while (subQuery.length() > 0 && !subQuery.startsWith(")") && !subQuery.startsWith(","))
                                        {
                                            matcher = addPattern.matcher(subQuery);
                                            if (matcher.find())
                                            {
                                                if (matcher.group(1) != null)
                                                {
                                                    subCls += "ADD,";
                                                    subClsTrg += matcher.group(1).trim() + ",";
                                                }
                                                else
                                                {
                                                    subCls = "";
                                                    subClsTrg = "";
                                                    query = "";
                                                    subQuery = "";
                                                }

                                                if (subQuery.contains(matcher.group(0)))
                                                {
                                                    int index = subQuery.indexOf(matcher.group(0));
                                                    subQuery = subQuery.substring(index + matcher.group(0).length()).trim();
                                                    if (subQuery.contains(","))
                                                    {
                                                        subQuery = subQuery.substring(subQuery.indexOf(',') + 1).trim();
                                                    }
                                                    else
                                                    {
                                                        break;
                                                    }
                                                }
                                                else
                                                {
                                                    subQuery = "";
                                                }
                                            }
                                            else
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                                subQuery = "";
                                            }
                                        }
                                    }
                                    else
                                    {
                                        matcher = addPattern.matcher(query);
                                        if (matcher.find())
                                        {
                                            if (matcher.group(1) != null)
                                            {
                                                subCls += "ADD,";
                                                subClsTrg += matcher.group(1).trim() + ",";
                                            }
                                            else
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                            }

                                            if (query.contains(matcher.group(0)))
                                            {
                                                int index = query.indexOf(matcher.group(0));
                                                query = query.substring(index + matcher.group(0).length()).trim();
                                            }
                                        }
                                        else
                                        {
                                            subCls = "";
                                            subClsTrg = "";
                                            query = "";
                                        }
                                    }
                                }
                            }
                            else if (query.toUpperCase().startsWith("ALTER "))
                            {
                                query = query.substring(6).trim();
                                if (query.toUpperCase().startsWith("COLUMN "))
                                {
                                    query = query.substring(7).trim();
                                }

                                matcher = alterPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null && query.startsWith(matcher.group(1)))
                                    {
                                        subCls += "ALTER,";
                                        subClsTrg += matcher.group(1).trim() + ",";

                                        if (query.contains(matcher.group(0)))
                                        {
                                            query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                            query = query.replaceAll("\\s{1,}", " ");

                                            if (!query.toUpperCase().startsWith("SET DEFAULT ") && !query.toUpperCase().startsWith("DROP DEFAULT"))
                                            {
                                                subCls = "";
                                                subClsTrg = "";
                                                query = "";
                                            }
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
                            else if (query.toUpperCase().startsWith("CHANGE "))
                            {
                                query = query.substring(7).trim();
                                if (query.toUpperCase().startsWith("COLUMN "))
                                {
                                    query = query.substring(7).trim();
                                }

                                matcher = changePattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null && query.startsWith(matcher.group(1)))
                                    {
                                        subCls += "CHANGE,";
                                        subClsTrg += matcher.group(1).trim() + ",";

                                        if (query.contains(matcher.group(0)))
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
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else if (query.toUpperCase().startsWith("DROP "))
                            {
                                query = query.substring(5).trim();
                                if (query.toUpperCase().startsWith("COLUMN ") || query.toUpperCase().startsWith("FOREIGN KEY ")
                                        || query.toUpperCase().startsWith("INDEX ") || query.toUpperCase().startsWith("KEY "))
                                {
                                    String type = "";
                                    if (query.toUpperCase().startsWith("COLUMN "))
                                    {
                                        query = query.substring(7).trim();
                                        type = "COLUMN";
                                    }
                                    else if (query.toUpperCase().startsWith("FOREIGN KEY "))
                                    {
                                        query = query.substring(12).trim();
                                        type = "FOREIGN KEY";
                                    }
                                    else if (query.toUpperCase().startsWith("INDEX "))
                                    {
                                        query = query.substring(6).trim();
                                        type = "INDEX";
                                    }
                                    else if (query.toUpperCase().startsWith("KEY "))
                                    {
                                        query = query.substring(4).trim();
                                        type = "KEY";
                                    }
                                    matcher = dropPattern.matcher(query);
                                    if (matcher.find())
                                    {
                                        if (matcher.group(1) != null && query.startsWith(matcher.group(1)))
                                        {
                                            if ("COLUMN".equals(type))
                                            {
                                                subCls += "DROP,";
                                                subClsTrg += matcher.group(1).trim() + ",";
                                            }
                                            else
                                            {
                                                subCls += "DROP,";
                                                subClsTrg += ",";
                                            }

                                            if (query.contains(matcher.group(0)))
                                            {
                                                query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                query = query.replaceAll("\\s{1,}", " ");
                                                if (!"".equals(query) && !query.startsWith(","))
                                                {
                                                    subCls = "";
                                                    subClsTrg = "";
                                                    query = "";
                                                }
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
                                    if (!"".equals(query) && !query.startsWith(","))
                                    {
                                        subCls = "";
                                        subClsTrg = "";
                                        query = "";
                                    }
                                }
                                else
                                {
                                    matcher = dropPattern.matcher(query);
                                    if (matcher.find())
                                    {
                                        if (matcher.group(1) != null && query.startsWith(matcher.group(1)))
                                        {
                                            subCls += "DROP,";
                                            subClsTrg += matcher.group(1).trim() + ",";
                                            if (query.contains(matcher.group(0)))
                                            {
                                                query = query.replaceFirst(matcher.group(0).replace("(", "\\(").replace(")", "\\)"), "").trim();
                                                query = query.replaceAll("\\s{1,}", " ");
                                                if (!"".equals(query) && !query.startsWith(","))
                                                {
                                                    subCls = "";
                                                    subClsTrg = "";
                                                    query = "";
                                                }
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
                            }
                            else if (query.toUpperCase().startsWith("MODIFY "))
                            {
                                query = query.substring(7).trim();
                                if (query.toUpperCase().startsWith("COLUMN "))
                                {
                                    query = query.substring(7).trim();
                                }

                                matcher = modifyPattern.matcher(query);
                                if (matcher.find())
                                {
                                    if (matcher.group(1) != null && query.startsWith(matcher.group(1)))
                                    {
                                        subCls += "MODIFY,";
                                        subClsTrg += matcher.group(1).trim() + ",";

                                        if (query.contains(matcher.group(0)))
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
                                else
                                {
                                    subCls = "";
                                    subClsTrg = "";
                                    query = "";
                                }
                            }
                            else if (query.toUpperCase().startsWith("RENAME "))
                            {
                                subCls += "RENAME,";
                                subClsTrg += ",";
                                matcher = renamePattern.matcher(query.toUpperCase());
                                if (!matcher.find())
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

                            if (query.contains(","))
                            {
                                query = query.substring(query.indexOf(',') + 1).trim();
                            }
                            else
                            {
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
            }
            else if (query.toUpperCase().startsWith("TABLESPACE "))
            {
                dbObjBean.setObjType("TABLESPACE");
                query = query.substring(11).trim();

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

                if (query.toUpperCase().contains(" ADD DATAFILE ") || query.toUpperCase().contains(" DROP DATAFILE "))
                {
                    String subCls = "";
                    String subClsTrg = "";
                    while (query.length() > 0)
                    {
                        int idx = query.length();
                        String clause = "";
                        String clauseTarget = "";
                        if (query.toUpperCase().contains(" ADD DATAFILE "))
                        {
                            if (idx > query.toUpperCase().indexOf(" ADD DATAFILE "))
                            {
                                idx = query.toUpperCase().indexOf(" ADD DATAFILE ");
                                clause = "ADD DATAFILE";
                                int len = query.length();
                                if (query.indexOf(' ', idx + 14) != -1)
                                {
                                    len = query.indexOf(' ', idx + 14);
                                }
                                clauseTarget = query.substring(idx + 14, len);
                                if (clauseTarget.contains(","))
                                {
                                    clauseTarget = clauseTarget.substring(0, clauseTarget.indexOf(','));
                                }
                            }
                        }
                        if (query.toUpperCase().contains(" DROP DATAFILE "))
                        {
                            if (idx > query.toUpperCase().indexOf(" DROP DATAFILE "))
                            {
                                idx = query.toUpperCase().indexOf(" DROP DATAFILE ");
                                clause = "DROP DATAFILE";
                                int len = query.length();
                                if (query.indexOf(' ', idx + 15) != -1)
                                {
                                    len = query.indexOf(' ', idx + 15);
                                }
                                clauseTarget = query.substring(idx + 15, len);
                                if (clauseTarget.contains(","))
                                {
                                    clauseTarget = clauseTarget.substring(0, clauseTarget.indexOf(','));
                                }
                            }
                        }
                        if (idx != query.length())
                        {
                            subCls += clause + ",";
                            subClsTrg += clauseTarget + ",";
                            query = query.substring(idx + 14).trim();
                        }
                        else
                        {
                            query = "";
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
            else if (query.toUpperCase().startsWith("VIEW ") || query.toUpperCase().contains(" VIEW "))
            {
                if (query.toUpperCase().startsWith("ALGORITHM"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("ALGORITHM = UNDEFINED ") || query.toUpperCase().startsWith("ALGORITHM = TEMPTABLE "))
                    {
                        query = query.substring(22).trim();
                    }
                    else if (query.toUpperCase().startsWith("ALGORITHM = MERGE "))
                    {
                        query = query.substring(18).trim();
                    }
                }
                if (query.toUpperCase().startsWith("DEFINER"))
                {
                    query = query.replaceFirst("=", " = ");
                    query = query.replaceAll("\\s{1,}", " ");
                    if (query.toUpperCase().startsWith("DEFINER = "))
                    {
                        query = query.substring(10).trim();
                        if (query.contains(" "))
                        {
                            query = query.substring(query.indexOf(' ')).trim();
                        }
                    }
                }
                if (query.toUpperCase().startsWith("SQL SECURITY DEFINER ") || query.toUpperCase().startsWith("SQL SECURITY INVOKER "))
                {
                    query = query.substring(21).trim();
                }
                if (query.toUpperCase().startsWith("VIEW "))
                {
                    dbObjBean.setObjType("VIEW");
                    query = query.substring(5).trim();
                }
            }
            if (dbObjBean.getObjType() != null && !"TABLE".equals(dbObjBean.getObjType()) && !"TABLESPACE".equals(dbObjBean.getObjType()))
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
            qBean.setDbObjInfo(dbObjBean);
        }
        else if (token.equals("DROP"))
        {
            qBean.setClause("DROP");
            qBean.setIsWherePresent(false);
            qBean.setNeedConfirmation(false);
            query = query.substring(4).trim();

            DBObjectInfoBean dbObjBean = new DBObjectInfoBean();
            if (query.toUpperCase().startsWith("DATABASE ") || query.toUpperCase().startsWith("SCHEMA "))
            {
                if (query.toUpperCase().startsWith("DATABASE "))
                {
                    dbObjBean.setObjType("DATABASE");
                    query = query.substring(9).trim();
                }
                else if (query.toUpperCase().startsWith("SCHEMA "))
                {
                    dbObjBean.setObjType("SCHEMA");
                    query = query.substring(7).trim();
                }
            }
            else if (query.toUpperCase().startsWith("FUNCTION "))
            {
                dbObjBean.setObjType("FUNCTION");
                query = query.substring(9).trim();
            }
            else if (query.toUpperCase().startsWith("INDEX ") || (query.toUpperCase().contains(" INDEX ")
                    && (query.toUpperCase().startsWith("ONLINE ") || query.toUpperCase().startsWith("OFFLINE "))))
            {
                if (query.toUpperCase().startsWith("ONLINE ") || query.toUpperCase().startsWith("OFFLINE "))
                {
                    if (query.toUpperCase().startsWith("ONLINE "))
                    {
                        query = query.substring(7).trim();
                    }
                    else if (query.toUpperCase().startsWith("OFFLINE "))
                    {
                        query = query.substring(8).trim();
                    }
                }
                if (query.toUpperCase().startsWith("INDEX "))
                {
                    dbObjBean.setObjType("INDEX");
                    query = query.substring(6).trim();
                }
            }
            else if (query.toUpperCase().startsWith("PROCEDURE "))
            {
                dbObjBean.setObjType("PROCEDURE");
                query = query.substring(10).trim();
            }
            else if (query.toUpperCase().startsWith("TABLE ") || (query.toUpperCase().contains(" TABLE ") && query.toUpperCase().startsWith("TEMPORARY ")))
            {
                if (query.toUpperCase().startsWith("TEMPORARY "))
                {
                    query = query.substring(10).trim();
                }
                if (query.toUpperCase().startsWith("TABLE "))
                {
                    dbObjBean.setObjType("TABLE");
                    query = query.substring(6).trim();
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
            else if (query.toUpperCase().startsWith("VIEW "))
            {
                dbObjBean.setObjType("VIEW");
                query = query.substring(5).trim();
            }

            if (dbObjBean.getObjType() != null && !"INDEX".equals(dbObjBean.getObjType()) && !"TABLESPACE".equals(dbObjBean.getObjType()))
            {
                if (query.toUpperCase().startsWith("IF EXISTS "))
                {
                    query = query.substring(10).trim();
                }
            }

            if (dbObjBean.getObjType() != null)
            {
                if ((!"TABLE".equals(dbObjBean.getObjType()) && !"VIEW".equals(dbObjBean.getObjType()))
                        || (("TABLE".equals(dbObjBean.getObjType()) || "VIEW".equals(dbObjBean.getObjType())) && !query.contains(",")))
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
                else if (("TABLE".equals(dbObjBean.getObjType()) || "VIEW".equals(dbObjBean.getObjType())) && query.contains(","))
                {
                    String tmp = query.trim();
                    if (tmp.toUpperCase().contains(" RESTRICT"))
                    {
                        tmp = tmp.substring(0, tmp.toUpperCase().indexOf(" RESTRICT")).trim();
                    }
                    if (tmp.toUpperCase().contains(" CASCADE"))
                    {
                        tmp = tmp.substring(0, tmp.toUpperCase().indexOf(" CASCADE")).trim();
                    }

                    if (tmp.contains("."))
                    {
                        dbObjBean.setSchema(tmp.split("\\.")[0].trim());
                        tmp = tmp.split("\\.")[1].trim();
                    }

                    if (tmp.length() > 0)
                    {
                        if (tmp.contains(","))
                        {
                            String[] objs = tmp.split(",");
                            String objName = "";
                            for (int i = 0; i < objs.length; i++)
                            {
                                objs[i] = objs[i].trim();
                                if (objs[i].length() > 0)
                                {
                                    objName += objs[i] + ",";
                                }
                            }
                            objName = objName.replace(",,", ",");
                            if (objName.endsWith(","))
                            {
                                objName = objName.substring(0, objName.lastIndexOf(','));
                            }
                            if (!"".equals(objName))
                            {
                                dbObjBean.setObjName(objName);
                            }
                        }
                        else
                        {
                            dbObjBean.setObjName(tmp);
                        }
                    }
                }
            }

            if (dbObjBean.getObjType() != null && "INDEX".equals(dbObjBean.getObjType()))
            {
                if (query.toUpperCase().contains(" ON "))
                {
                    qBean.setSubClause("ON");
                    int idx = query.toUpperCase().indexOf(" ON ");
                    query = query.substring(idx + 4).trim();
                    qBean.setSubClauseTarget(query);
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
                    Map isValid = qMaker.executeExplain(server, database, "EXPLAIN " + selectQ);
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
                                isValid = qMaker.executeExplain(server, database, "EXPLAIN " + selectQ);
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
            if (query.toUpperCase().startsWith("FROM ") || query.toUpperCase().contains(" FROM "))
            {
                if (!query.toUpperCase().startsWith("FROM ") && query.toUpperCase().contains(" FROM "))
                {
                    query = query.substring(query.toUpperCase().indexOf(" FROM ")).trim();
                }
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
            }
            if (query.toUpperCase().contains(" WHERE "))
            {
                //Get SELECT Query
                QueryMaker qMaker = new QueryMaker();
                selectQ = qMaker.makeSelectQuery(selectQ, null, null);
                Map isValid = qMaker.executeExplain(server, database, "EXPLAIN " + selectQ);
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
                            isValid = qMaker.executeExplain(server, database, "EXPLAIN " + selectQ);
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
