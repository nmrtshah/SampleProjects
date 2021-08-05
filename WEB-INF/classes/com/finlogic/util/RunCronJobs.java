/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import java.util.TimerTask;

/**
 *
 * @author njuser
 */
public class RunCronJobs extends TimerTask
{

    private int cronJobType = 0;

    public RunCronJobs(int cronJobTypeP)
    {
        cronJobType = cronJobTypeP;
    }

    public void run()
    {
        if (cronJobType == 3)
        {
            String[] args =
            {
                "Insert"
            };
            ServerAccessAudit.main(args);
        }
        /*else if (cronJobType == 4)
        {
        String[] args =
        {
        "Mail"
        };
        ServerAccessAudit.main(args);
        }*/
        else if (cronJobType == 5)
        {
            String[] args =
            {
                "Status404Mail"
            };
            ServerAccessAudit.main(args);
        }
        else if (cronJobType == 6)
        {
            String[] args =
            {
                "ExecutionTimeWiseMail"
            };
            ServerAccessAudit.main(args);
        }
        else if (cronJobType == 7)
        {
            String[] args =
            {
                "ByteSentWiseMail"
            };
            ServerAccessAudit.main(args);
        }
        else if (cronJobType == 8)
        {
            String[] args = null;
            try
            {
                JarFileAudit.main(args);
            }
            catch(Exception e)
            {
            }
        }
        else if (cronJobType == 9)
        {
            String[] args = null;
            try
            {
                LogCleanUp.main(args);
            }
            catch(Exception e)
            {
            }
        }
        else if (cronJobType == 10)
        {
            String[] args = null;
            try
            {
                ACLClassFileAudit.main(args);
            }
            catch(Exception e)
            {
            }
        }
    }
}
