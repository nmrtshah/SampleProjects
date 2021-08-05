/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.EmailManager;
import com.finlogic.util.Logger;
import finutils.directconn.DBConnManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author sangani
 */
public class LongRequestAuditorCron {

    private static final String td = "<td><center>";
    private static final String td_end = "</center></td>";
    private static final String th = "</th><th>";
    private static final String subject = "Long Running Requests List on - PROD";

    public static void main(String[] args) throws SQLException {

        String[] to = {"devsrtl@njgroup.in"};
        String[] cc = {"kinjal@njgroup.in", "janesh@njgroup.in", "namrata.shah@njgroup.in"};

        Connection con = null;
        PreparedStatement psmnt = null;
        ResultSet rs = null;

        try {

            DBConnManager dbcon = new DBConnManager();
            con = dbcon.getFinConn("finstudio_dbaudit_common");

            StringBuilder query = new StringBuilder();
            query.append("SELECT REQUEST_URI,floor(REQUEST_TAKEN_TIME/3600) as 'SINCE_HOURS',CLIENT_IP,VHOST,ON_TIME FROM finstudio.MANAGER_INFO ");
            query.append("where REQUEST_TAKEN_TIME/3600 > 10 and  REQUEST_URI not like '%/manager/status%' ");
            query.append("and ON_TIME = (SELECT max(ON_TIME) FROM finstudio.MANAGER_INFO) order by ID desc");

            psmnt = con.prepareStatement(query.toString());
            rs = psmnt.executeQuery();

            if (!rs.isBeforeFirst()) {
                Logger.DataLogger("LongRequestAuditorCron: No Long Running Request Exist");
            } else {

                StringBuilder content = new StringBuilder();
                content.append("<html><body><table border=1>");
                content.append("<tr><th> REQUEST_URI").append(th);
                content.append("SINCE_HOURS").append(th);
                content.append("CLIENT_IP").append(th);
                content.append("VHOST").append(th);
                content.append("ON_TIME").append("</th></tr>");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                while (rs.next()) {
                    content.append("<tr>").append(td).append(rs.getString("REQUEST_URI")).append(td_end);
                    content.append(td).append(rs.getString("SINCE_HOURS")).append(td_end);
                    content.append(td).append(rs.getString("CLIENT_IP")).append(td_end);
                    content.append(td).append(rs.getString("VHOST")).append(td_end);
                    content.append(td).append(sdf.format(rs.getTimestamp("ON_TIME"))).append(td_end).append("</tr>");

                }

                content.append("</table></body></html>");
                EmailManager email = new EmailManager();
                email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content.toString(), null);
            }

        } catch (Exception e) {
            Logger.DataLogger("LongRequestAuditorCron Error: " + Logger.ErrorLogger(e));
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    Logger.DataLogger("LongRequestAuditorCron: resultset is not formed :" + Logger.ErrorLogger(e));
                }
            }

            if (con != null) {
                try {
                    con.close();

                } catch (Exception e) {
                    Logger.DataLogger("LongRequestAuditorCron: DB Connection is not formed :" + Logger.ErrorLogger(e));
                }
            }

            if (psmnt != null) {
                try {
                    psmnt.close();
                } catch (Exception e) {
                    Logger.DataLogger("LongRequestAuditorCron: Statement is not formed :" + Logger.ErrorLogger(e));
                }
            }
        }
    }
}
