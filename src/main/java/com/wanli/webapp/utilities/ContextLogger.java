package com.wanli.webapp.utilities;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;


public class ContextLogger {
    public synchronized static int log(ServletRequest request){
        ServletContext context = request.getServletContext();
        Object ob = context.getAttribute("log");
        if (ob == null) {
            context.setAttribute("log", 1);
            return 1;
        }
        else {
            int incremented = (int)ob + 1;
            context.setAttribute("log", incremented);
            return incremented;
        }

    }
    private ContextLogger(){}
}
