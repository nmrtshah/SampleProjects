/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finfiletransfer;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLConnService;
import com.finlogic.util.properties.HardCodeProperty;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class FileTransIntermediateDataManager {

    private final SQLConnService connService = new SQLConnService();
    private Connection con = null;
    private final HardCodeProperty property = new HardCodeProperty();    

    private void initalize() throws ClassNotFoundException, SQLException {        
        Class.forName(property.getProperty("test_db2_mysql_driver"));
        Properties props = new Properties();
        props.put("user", property.getProperty("test_db2_mysql_user"));
        props.put("password", property.getProperty("test_db2_mysql_password"));
        String url = property.getProperty("test_db2_mysql_url");//.concat("sslMode=DISABLED;setOldAliasMetadataBehavior=true;characterSetResults=Cp1252;characterEncoding=Cp1252".replaceAll(";", "&#59;"));        
        con = DriverManager.getConnection(url, props);
    }

    public List<Map<String, String>> getData(long id) throws SQLException, ClassNotFoundException {
        String query = null;
        try {
            initalize();
            query = "SELECT * FROM finstudio.FIN_FILE_TRANSFER_TEMP WHERE GEN_ID = :GEN_ID AND (FILE_NAME NOT LIKE 'Deleted:%' AND FILE_NAME NOT LIKE 'Uploaded:%')";
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            Logger.DataLogger(query);
            return connService.getList(con, query, new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }

    }

    public List<Map<String, String>> getDataDlt(long id) throws SQLException, ClassNotFoundException {
        String query = null;
        try {
            initalize();
            query = "SELECT * FROM finstudio.FIN_FILE_TRANSFER_TEMP WHERE GEN_ID = :GEN_ID AND FILE_NAME LIKE 'Deleted:%'";
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            Logger.DataLogger(query);
            return connService.getList(con, query,new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }

    }

    public List<Map<String, String>> getDataUpld(long id) throws SQLException, ClassNotFoundException {
        String query = null;
        try {
            initalize();
            query = "SELECT * FROM finstudio.FIN_FILE_TRANSFER_TEMP WHERE GEN_ID = :GEN_ID AND FILE_NAME LIKE 'Uploaded:%'";
            Logger.DataLogger(query);
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            return connService.getList(con, query, new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }

    }

    public int getDataForOnluFolder(long id) throws SQLException, ClassNotFoundException {
        String query = null;
        try {
            initalize();
            query = "SELECT count(*) FROM finstudio.FIN_FILE_TRANSFER_TEMP WHERE GEN_ID = :GEN_ID AND (FILE_NAME NOT LIKE 'Deleted:%' AND FILE_NAME NOT LIKE 'Uploaded:%') and FILE_TYPE='file'";
            Logger.DataLogger(query);
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            return connService.getInt(con, query, new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }

    }

    public void insertData(long id, String fileNm, String srcExist, String destExist, String fileType) throws SQLException {

        StringBuilder query = new StringBuilder();
        try {
            initalize();
            query.append("INSERT INTO finstudio.FIN_FILE_TRANSFER_TEMP(GEN_ID, FILE_NAME, SRC_EXIST,DEST_EXIST,FILE_TYPE) VALUES(:GEN_ID");
            if (fileNm != null) {
                query.append(",'").append(fileNm).append("'");
            } else {
                query.append(",null");
            }
            if (srcExist != null) {
                query.append(",'").append(srcExist).append("'");
            } else {
                query.append(",null");
            }
            if (destExist != null) {
                query.append(",'").append(destExist).append("'");
            } else {
                query.append(",null");
            }
            if (fileType != null) {
                query.append(",'").append(fileType).append("'");
            } else {
                query.append(",null");
            }
            query.append(")");
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            connService.persist(con, query.toString(), new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }

    }

    public void updateData(long id, String fileNm, String srcExist, String destExist, String fileType) throws SQLException {
        StringBuilder query = new StringBuilder();
        try {
            initalize();
            query.append("UPDATE finstudio.FIN_FILE_TRANSFER_TEMP SET ");
            if (id > 0) {
                query.append("GEN_ID = :GEN_ID ");
            }
            if (fileNm != null) {
                query.append(",FILE_NAME='").append(fileNm).append("'");
            }
            if (srcExist != null) {
                query.append(",SRC_EXIST='").append(srcExist).append("'");
            }
            if (destExist != null) {
                query.append(",DEST_EXIST='").append(destExist).append("'");
            }
            if (fileType != null) {
                query.append(",FILE_TYPE='").append(fileType).append("'");
            }
            query.append(" WHERE GEN_ID = :GEN_ID AND FILE_NAME='").append(fileNm).append("'");
            Logger.DataLogger("Query : " + query.toString());
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            connService.persist(con, query.toString(),new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }
    }

    public void updateStatusAll(long id) throws SQLException {
        StringBuilder query = new StringBuilder();
        try {
            initalize();
            query.append("UPDATE finstudio.FIN_FILE_TRANSFER_TEMP SET STATUS='N' WHERE 1=1 ");
            if (id > 0) {
                query.append(" AND GEN_ID = :GEN_ID");
            }
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            Logger.DataLogger("Query : " + query.toString());
            connService.persist(con, query.toString(), new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }
    }

    public void updateStatus(long id, String fileNm) throws SQLException {
        StringBuilder query = new StringBuilder();
        try {
            initalize();
            query.append("UPDATE finstudio.FIN_FILE_TRANSFER_TEMP SET STATUS='Y' WHERE 1=1 ");
            if (id > 0) {
                query.append(" AND GEN_ID = :GEN_ID");
            }
            if (fileNm != null) {
                query.append(" AND FILE_NAME LIKE '%").append(fileNm).append("'");
            }
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            Logger.DataLogger("Query : " + query.toString());
            connService.persist(con, query.toString(), new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }
    }

    public void deleteData(long id) throws SQLException {
        StringBuilder query = new StringBuilder();
        try {
            initalize();
            query.append("DELETE FROM finstudio.FIN_FILE_TRANSFER_TEMP WHERE GEN_ID = :GEN_ID AND (FILE_NAME NOT LIKE 'Deleted:%' AND FILE_NAME NOT LIKE 'Uploaded:%')");
            Map<String,Object> map = new HashMap();
            map.put("GEN_ID", String.valueOf(id));
            connService.persist(con, query.toString(), new MapSqlParameterSource(map));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            connClose();
        }
    }

    public void connClose() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                Logger.ErrorLogger(e);
            }
        }
    }

    public String getTomcatByProject(String projectName) throws Exception {
        String tomcatPath = "";
        boolean flag = false;
        try {
            File f1 = new File("/opt/");
            if (f1.exists()) {
                File tomcats[] = f1.listFiles();
                for (File tomcat : tomcats) {
                    if (tomcat.getAbsolutePath().contains("tomcat")) {
                        File f2 = new File(tomcat + "/webapps/" + projectName);
                        if (f2.exists() && f2.isDirectory()) {
                            tomcatPath = tomcat.getAbsolutePath();
                            break;
                        }
                    }
                }
                tomcatPath = tomcatPath + "/";
            }
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new Exception(e);
        }

        return tomcatPath;
    }
}
