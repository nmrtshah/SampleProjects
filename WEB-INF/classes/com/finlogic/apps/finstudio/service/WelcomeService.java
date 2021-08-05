/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.service;

import com.finlogic.business.finstudio.welcome.welcomeManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author njuser
 */
public class WelcomeService
{

    public List getStatistics() throws ClassNotFoundException, SQLException
    {
        return new welcomeManager().getStatistics();
    }
}
