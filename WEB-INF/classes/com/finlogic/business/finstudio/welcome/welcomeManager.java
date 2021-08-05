/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.welcome;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author njuser
 */
public class welcomeManager
{
    public List getStatistics() throws ClassNotFoundException, SQLException
    {
        return  new welcomeDataManager().getStatistics();
    }
}
