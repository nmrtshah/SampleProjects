package com.finlogic.apps.finstudio.linkchecker;

/**
 *
 * @author Swapnil
 */
import com.finlogic.util.LinkChecker;
import com.finlogic.util.Logger;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class LinkCheckerController implements Controller
{

    @Override
    public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws IOException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        LinkChecker linkchk;
        linkchk = new LinkChecker();

        String root;
        root = request.getParameter("rooturl");
        String maxlinks;
        maxlinks = request.getParameter("maxlinks");

//        int max;
//        int flag1=0;
//
//        for (int i = 0; i < maxlinks.length(); i++)
//        {
//            max = maxlinks.charAt(i);
//
//            Logger.DataLogger("max1="+max);
//            if (!((max >= 48 && max <= 57)))
//            {
//                flag1=1;
//                Logger.DataLogger("max2="+max);
//                break;
//            }
//
//        }

        int maxlinks1 = 0;
        if (maxlinks != null)// || flag1!=1)
        {
            maxlinks1 = Integer.parseInt(maxlinks);
            Logger.DataLogger("maxlinks1=" + maxlinks1);
        }
//        else
//        {
//            maxlinks1=0;
//            Logger.DataLogger("maxlinks1="+maxlinks1);
//        }


        String external;
        external = request.getParameter("external");

        Logger.DataLogger("external=" + external);

        boolean extrn = false;
        if (external != null && external.equals("yes"))
        {
            extrn = true;
            Logger.DataLogger("extrn1=" + extrn);
        }
        Logger.DataLogger("extrn2=" + extrn);

//        CommonMember.appendLogFile(external);

        if (root != null)
        {
            mav.addObject("list", linkchk.processURL(root, maxlinks1, extrn));
        }

        mav.setViewName("linkchecker");
        return mav;
    }
}
