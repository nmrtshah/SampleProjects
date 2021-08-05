/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import com.finlogic.apps.finstudio.webservice.WebServiceFormBean;
import com.finlogic.util.Logger;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Sonam Patel
 */
public class ImplementorGenerator
{

    public void implement(final WebServiceFormBean formBean) throws ClassNotFoundException, IOException
    {
        String fileName;
        fileName = formBean.getIntrfcName().substring(0, formBean.getIntrfcName().length() - 5);

        //copy class file of interface into /WEB-INF/classes/ folder
//        String src = WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/";
//        String dest = WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/WEB-INF/classes/";
//        FileService.copyClassFiles(new File(src), new File(dest));

        Class classObj;
        //classObj = Thread.currentThread().getContextClassLoader().loadClass(fileName);
//        classObj = Thread.currentThread().getContextClassLoader().loadClass(WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/" + fileName);
        URL dirUrl = new URL("file://" + WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/");
        URLClassLoader cl = new URLClassLoader(new URL[]
                {
                    dirUrl
                }, getClass().getClassLoader());
        classObj = cl.loadClass(fileName);

        String filePath;
        filePath = WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + formBean.getTxtSrno() + "WS/producer/";
        File file;
        file = new File(filePath);
        file.mkdirs();

        PrintWriter pw;
        pw = new PrintWriter(filePath + fileName + "Impl.java");

        CommonFunctions cfun;
        cfun = new CommonFunctions();

        try
        {
            String txtFile;
            txtFile = WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/" + formBean.getTxtSrno() + ".txt";

            String pkg;
            pkg = FileService.getPackage(txtFile);

            Method[] m;
            m = classObj.getDeclaredMethods();
            Set set;
            set = new HashSet();

            for (int i = 0; i < m.length; i++)
            {
                //Return Type
                cfun.makeSet(m[i].getGenericReturnType(), set);

                //Loop for All Parameters
                for (int j = 0; j < m[i].getGenericParameterTypes().length; j++)
                {
                    cfun.makeSet(m[i].getGenericParameterTypes()[j], set);
                }

                //Loop for All Throws Exception
                for (int j = 0; j < m[i].getGenericExceptionTypes().length; j++)
                {
                    cfun.makeSet(m[i].getGenericExceptionTypes()[j], set);
                }
            }

            pw.println("package " + pkg + ";");
            pw.println();

            pw.println("import javax.jws.WebService;");

            Iterator iter;
            iter = set.iterator();
            while (iter.hasNext())
            {
                // Get element
                Object element;
                element = iter.next();
                pw.println("import " + element + ";");
            }
            List<String> beanList = formBean.getBeans();
            for (int i = 0; i < beanList.size(); i++)
            {
                String beanPkg;
                beanPkg = FileService.getBeanPackage(WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "/" + beanList.get(i) + ".java");
                if (!"".equals(beanPkg) && !beanPkg.equals(pkg))
                {
                    pw.println("import " + beanPkg + "." + beanList.get(i) + ";");
                }
            }
            pw.println();

            pw.println("@WebService(endpointInterface=\"" + pkg + "." + fileName + "\")");
            pw.println("public class " + fileName + "Impl implements " + fileName);
            pw.println("{");
            pw.println();
            pw.println("    public " + fileName + "Impl()");
            pw.println("    {");
            pw.println("    }");
            pw.println();

            StringBuilder retType;
            retType = new StringBuilder();
            StringBuilder meth;
            meth = new StringBuilder();
            StringBuilder argType;
            argType = new StringBuilder();
            for (int i = 0; i < m.length; i++)
            {
                retType.delete(0, retType.length());
                meth.delete(0, meth.length());

                cfun.getDataType(m[i].getGenericReturnType(), retType);

                meth.append("(public ){0,1}(abstract ){0,1}(public ){0,1}");

                meth.append(retType).append(" ");
                meth.append(m[i].getName()).append("\\((final ){0,1}");

                for (int j = 0; j < m[i].getGenericParameterTypes().length; j++)
                {
                    argType.delete(0, argType.length());
                    cfun.getDataType(m[i].getGenericParameterTypes()[j], argType);

                    if (j != 0)
                    {
                        meth.append(",[ ]{0,1}(final ){0,1}");
                    }
                    meth.append(argType).append(" [a-zA-Z][a-zA-Z0-9]*");
                }
                meth.append("\\)").append("[ ]{0,1}(throws [a-zA-Z0-9, ]*){0,1}[ ]{0,1}").append(";");

                String method;
                method = FileService.getMethod(txtFile, meth.toString());
                method = method.replaceAll("abstract ", "");
                method = method.trim();
                if ("".equals(method))
                {
                    Logger.DataLogger("Interface " + fileName + ".java: Cannot Match Method Signature for Regular Expression " + meth);
                    continue;
                }
                if (!method.startsWith("public"))
                {
                    method = "public " + method;
                }

                pw.println("    @Override");
                pw.println("    " + method);
                pw.println("    {");
                pw.println("        throw new UnsupportedOperationException(\"Not supported yet.\");");
                pw.println("    }");
                pw.println();
            }
            pw.println("}");
        }
        finally
        {
            pw.close();
        }
    }
}
