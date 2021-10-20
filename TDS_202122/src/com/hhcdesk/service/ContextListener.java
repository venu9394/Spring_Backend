package com.hhcdesk.service; 
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import javax.sql.DataSource;

import org.apache.log4j.PropertyConfigurator;
 

//@WebListener("application context listener")
public class ContextListener implements ServletContextListener {
 
    /**
     * Initialize log4j when the application is being started
     */
    public void contextInitialized(ServletContextEvent event) {
        // initialize log4j here
    	
    	System.out.println("Context Listener Invoked");
        ServletContext context = event.getServletContext();
        
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        ArrayList UserList=new ArrayList();
        System.out.println("context.getRealPath"+context.getRealPath(""));
        String fullPath = context.getRealPath("") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(fullPath);
       
        ResourceBundle.Control rbc =ResourceBundle.Control.getControl(Control.FORMAT_DEFAULT);
		ResourceBundle bundle =ResourceBundle.getBundle("sqlqueries", Locale.US, rbc);
		context.setAttribute("bundle", bundle);
    
		DataSource dataSource=null;
		java.sql.Connection conn=null;
		Context initContext = null;
		/*try {
			initContext = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Context envContext = null;
		try {
			envContext = (Context)initContext.lookup("java:/comp/env");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 dataSource = (DataSource)envContext.lookup("jdbc/HHCL_DESK");
			 context.setAttribute("dataSource", dataSource);
		} catch (NamingException es) {
			// TODO Auto-generated catch block
			es.printStackTrace();
		}*/
		
    }
		/*try {
			 dataSource = (DataSource)envContext.lookup("jdbc/HHCL_DESK");
			 context.setAttribute("dataSource", dataSource);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
    public void contextDestroyed(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
    	context.removeAttribute("dataSource");
    	System.out.println("CONTEXT DESTROYED");
    	
        // do nothing
    }  
}