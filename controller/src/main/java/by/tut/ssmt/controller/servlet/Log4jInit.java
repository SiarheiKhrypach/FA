package by.tut.ssmt.controller.servlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import static by.tut.ssmt.controller.util.ControllerConstants.LOG4;
import static by.tut.ssmt.controller.util.ControllerConstants.LOG_FILE_NAME;


@WebServlet
        (
        name = "Log4jInit",
        urlPatterns = "/Log4jInit",
        initParams = {@WebInitParam(name = "logfile", value = "WEB-INF\\classes\\log4j.properties")},
        loadOnStartup = 1
)
public class Log4jInit extends HttpServlet {
    @Override
    public void init() throws ServletException {
        String logFileName = getInitParameter("logfile");
        String pref = "";
        pref = getServletContext().getRealPath("/");
        PropertyConfigurator.configure(pref + logFileName);
        Logger globalLog = Logger.getRootLogger();
        getServletContext().setAttribute(LOG4, globalLog);
        getServletContext().setAttribute(LOG_FILE_NAME, logFileName);
        globalLog.info("Log4jInit Servlet");

    }
}
