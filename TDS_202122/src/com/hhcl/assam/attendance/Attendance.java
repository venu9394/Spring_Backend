package com.hhcl.assam.attendance;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;

import com.mysql.jdbc.Statement;
import com.hhcdesk.EventCapture.MySessionListener;
import com.hhcdesk.db.*;

/**
 * Servlet implementation class LoginServlet
 * Hetero Health Care Limited
 * By Java HHCL Java Tem 
 * Written By Venu
 */
//@WebServlet("/User_Auth")
public class Attendance extends HttpServlet {

	public Attendance() {
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext c = getServletContext();
		
		System.out.println("GetParameter"+ c.getAttribute("Venu"));
		
		HttpSession session = request.getSession(false);
		Statement statement=null;
		ResultSet Res;
		DataSource dataSource=null;
		java.sql.Connection conn=null;
		java.sql.PreparedStatement ps=null;
		
		PrintWriter out = response.getWriter();
		Map UserMap=new HashMap();

		boolean ConnFlag=true;
		int User_Auth=0;
		GetDbData DataObj=new GetDbData();
		JSONObject jason = new JSONObject();
		JSONObject DOJ_DOB = new JSONObject();
		JSONObject EM_DOB = new JSONObject();

		JSONObject PaySlip= new JSONObject();
		JSONObject ForeCast= new JSONObject();

		JSONObject ATT_MONTHS = new JSONObject();
		
		int dobcnt=0;
		int dobcnt1=0;
		JSONArray values;
		values = new JSONArray();

		String Routing=request.getParameter("Routing");
		String Month="currmonth";
		
		System.out.println("Routing___Attendance_ ::"+Routing);
		SimpleDateFormat sdfTime;
		 String strTime="0";
		try{
		sdfTime = new SimpleDateFormat("dd-MM-yyyy");
	    Date now = new Date();
	     strTime="0";
	     strTime = sdfTime.format(now);
		}catch(Exception err){
			err.printStackTrace();
		}
		String Routing_temp=Routing;
		
		if(Routing.equalsIgnoreCase("ATTENDANCE_BIO_DATES")){
			
			Routing="ATTENDANCE_BIO";
			
		}
		
		
		String date=null,Req_message=null,subject=null,random=null,Atten_Req_Message="Success Fully Submit Your Request";
		
		
		if(Routing.equalsIgnoreCase("Att_Request")){
			
			date=request.getParameter("id");
			Req_message=request.getParameter("message");
			subject=request.getParameter("Subject");
			random=request.getParameter("RanDm");
		}

		System.out.println("Routing  :::" +Routing);
		session.setAttribute("Notice" ,"N");
		BatchInsertRecords BatchInsert=new BatchInsertRecords();
		ArrayList MasterDataList = new ArrayList(); 
		ArrayList<PrepareData> list = new ArrayList<PrepareData>();
		Map FetchRc=new HashMap();
		StringBuffer biodata=new StringBuffer();
		StringBuffer Emp_DOB=new StringBuffer();
		StringBuffer Emp_DOJ=new StringBuffer();
		
		StringBuffer Months_ATT=new StringBuffer();
		
		ArrayList DOB=new ArrayList();
		ArrayList DOJ=new ArrayList();
		ArrayList DOJ_DEPT=new ArrayList();


		StringBuffer HLDAYLIST=new StringBuffer();
		Map hlday=new HashMap();
		Map Att_Req=new HashMap();
		JSONObject  Doj= new JSONObject();


		ResourceBundle bundle_info =(ResourceBundle)(c.getAttribute("bundle"));

		String Query=bundle_info.getString("HHCL_DESK_USER_LOGIN");

		String HHCL_EVENT_INFO=bundle_info.getString("HHCL_DESK_EVENT");

		//session.setAttribute("HHCL_EVENT_INFO" ,HHCL_EVENT_INFO);

		String message=null;
		message=bundle_info.getString("HHCL_DESK_NEWJOINEE");
		System.out.println("Query:::"+Query);
		/*try {
			dataSource=(DataSource)(c.getAttribute("dataSource"));

		} catch (Exception e) {
			ConnFlag=false;
			e.printStackTrace();
		}
		try {
			conn =dataSource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		/*DataSource_Cls ConnObj=null;
		try {
			 ConnObj=new DataSource_Cls();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (PropertyVetoException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			//conn =dataSource.getConnection();
			conn=ConnObj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		try {
			//conn =dataSource.getConnection();
			conn=(java.sql.Connection)session.getAttribute("ConnectionObj");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
				e1.printStackTrace();
		}
		System.out.println("Step 1");
         
		
		
	
		String _FAIL_PAGE="/NewJoinee_Faild.jsp";
		String _SUCCESS_PAGE=null;
		String username=request.getParameter("username");  
		String password=request.getParameter("pwd"); 

		if(Routing!=null && (Routing.equalsIgnoreCase("NewJoine") || Routing.equalsIgnoreCase("HOLIDAYS") || Routing.equalsIgnoreCase("BIRTHADYS") || Routing.equalsIgnoreCase("ATTENDANCE_BIO") || Routing.equalsIgnoreCase("Att_Request") )){
			username=(String)session.getAttribute("EMP_ID");
			password=(String)session.getAttribute("EMP_PASSWORD");
		}

		String invalid=null;
		StringBuffer User_Authen=new StringBuffer();
		User_Authen.append(Query);
       
     
		/*Emp_DOJ.append(" SELECT A.CALLNAME NAME,D.NAME DEPT FROM  ");
 		Emp_DOJ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A  ");
 		Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFILE B ON A.EMPLOYEEID=B.EMPLOYEEID  ");
 		Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS C ON C.EMPLOYEEID=A.EMPLOYEEID  ");
 		Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT D ON C.DEPARTMENTID=D.DEPARTMENTID  ");
 		Emp_DOJ.append(" WHERE A.STATUS=1001 AND  ");
 		Emp_DOJ.append(" B.DATEOFJOIN >=DATE_FORMAT(curdate(),'%Y-%m-%d')- INTERVAL DAYOFWEEK(DATE_FORMAT(curdate(),'%Y-%m-%d')) DAY  ");*/
		if(Routing.equalsIgnoreCase("NewJoine")){
			Emp_DOJ.append("SELECT A.CALLNAME NAME,D.NAME DEPT,E.EMAIL,E.MOBILE,F.NAME FROM ");
			Emp_DOJ.append("HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
			Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFILE B ON A.EMPLOYEEID=B.EMPLOYEEID ");
			Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS C ON C.EMPLOYEEID=A.EMPLOYEEID ");
			Emp_DOJ.append("LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT D ON C.DEPARTMENTID=D.DEPARTMENTID ");
			Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.tbl_employee_professional_contact E ON A.EMPLOYEEID=E.EMPLOYEEID ");
			Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_businessunit F ON F.BUSINESSUNITID=A.COMPANYID ");
			Emp_DOJ.append("WHERE A.STATUS=1001 AND ");
			Emp_DOJ.append("B.DATEOFJOIN >=DATE_FORMAT(curdate() ,'%Y-%m-%d')- INTERVAL DAYOFWEEK(DATE_FORMAT(curdate() ,'%Y-%m-%d')) DAY ");

		}
		if(Routing.equalsIgnoreCase("HOLIDAYS")){

			Emp_DOJ.append("SELECT concat(trim(DATE_FORMAT(A.HOLIDAYDATE,'%d-%M-%Y')),',',CONCAT(trim(DAYNAME(A.HOLIDAYDATE)))) EVENTDATE,");
			Emp_DOJ.append("A.COMMENTS EVENT,   ");
			Emp_DOJ.append("IF(NOW()<A.HOLIDAYDATE,'UPCOMING','CLOSE') FLAG,IFNULL(B.NAME,'REGULAR') HOLIDAYTYPE   ");
			Emp_DOJ.append("FROM   ");
			Emp_DOJ.append("HCLHRM_PROD.TBL_HOLIDAYS A   ");
			Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.TBL_HOLIDAY_TYPE B ON A.HOLIDAYTYPEID=B.HOLIDAYTYPEID   ");
			Emp_DOJ.append("WHERE A.BUSINESSUNITID IN (   ");
			Emp_DOJ.append("SELECT COMPANYID FROM HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY WHERE EMPLOYEESEQUENCENO IN ("+username+"))   ");
			Emp_DOJ.append(" and A.statusid=1001 ");
			Emp_DOJ.append("order by A.HOLIDAYDATE desc ");

		}
		if(Routing.equalsIgnoreCase("BIRTHADYS")){
			Emp_DOJ.append(" SELECT A.CALLNAME NAME,E.NAME,C.NAME WISHHIM,D.EMAIL,D.MOBILE FROM    ");
			Emp_DOJ.append(" hclhrm_prod.tbl_employee_primary A   ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID   ");
			Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT C ON B.DEPARTMENTID=C.DEPARTMENTID   ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_CONTACT D ON A.EMPLOYEEID=D.EMPLOYEEID   ");
			Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_businessunit E ON E.BUSINESSUNITID=A.COMPANYID   ");
			Emp_DOJ.append(" WHERE A.STATUS=1001 AND DATE_FORMAT(A.DATEOFBIRTH,'%m-%d')=DATE_FORMAT(now(),'%m-%d')");
		}
		if(Routing.equalsIgnoreCase("DEPINFO_1")){
			Emp_DOJ.append(" SELECT A.CALLNAME EMPNAME,E.NAME businessunit,C.NAME Department,trim(F.name) designation,trim(D.EMAIL),D.MOBILE FROM    ");
			Emp_DOJ.append(" hclhrm_prod.tbl_employee_primary A   ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID   ");
			Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT C ON B.DEPARTMENTID=C.DEPARTMENTID   ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_CONTACT D ON A.EMPLOYEEID=D.EMPLOYEEID     ");
			Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_businessunit E ON E.BUSINESSUNITID=A.COMPANYID   ");
			Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_designation F ON B.designationid=F.designationid   ");
			Emp_DOJ.append(" WHERE A.STATUS=1001 and C.DEPARTMENTID=(select DEPARTMENTID from HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS   ");
			Emp_DOJ.append(" where employeeid in(select employeeid from hclhrm_prod.tbl_employee_primary where employeesequenceno in("+username+")))  ");

		}
		
		
		
		
	if(Routing.equalsIgnoreCase("ATTENDANCE_BIO")){
		
		
		//url : "Attendance?Routing=ATTENDANCE_BIO_DATES&ATT_FLAG='DATES'&Month_FROM="+Month_FROM+" & Month_TO="+Month_TO+" ",
		String From=request.getParameter("username");  
		String to=request.getParameter("pwd"); 
		
		String ATT_FLAG=request.getParameter("ATT_FLAG");
		String Month_FROM=request.getParameter("Month_FROM");
		String Month_TO=request.getParameter("Month_TO");
		
		 Month=request.getParameter("Month");
		 
		 System.out.println(ATT_FLAG +"~~"+Month_FROM +"~~"+Month_TO);
		 
		 if(Month==null){
			 Month="currmonth";
		 }
		
		System.out.println("Month:::"+Month);
		
	if(Month!=null && Month.equalsIgnoreCase("currmonth")  ){
		
		
		if(ATT_FLAG!=null && ATT_FLAG.equalsIgnoreCase("DATES")){
			
			Emp_DOJ.append(" SELECT DATE_FORMAT(TRANSACTIONDATE,'%d-%m-%Y') DAY,FIN,LOUT FOUT,NET PERDAY,IF(DAYNAME(TRANSACTIONDATE)='SUNDAY','WOFF','WDAY')DAYTYPE,");
			Emp_DOJ.append("IF(NET<'08:50',IF(TRANSACTIONDATE BETWEEN (SELECT MAX(TODATE) FROM hcladm_prod.tbl_transaction_dates ");
			Emp_DOJ.append(" WHERE BUSINESSUNITID=(select companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in("+username+"))) AND SUBDATE(CURDATE(),1),1,0),0)STATUS ");
			Emp_DOJ.append(",TRANSACTIONDATE DAYSP ");
			Emp_DOJ.append(" FROM ");
			Emp_DOJ.append(" HCLHRM_PROD_OTHERS.TBL_EMPLOYEE_IOT ");
			Emp_DOJ.append(" WHERE ");
			Emp_DOJ.append(" TRANSACTIONDATE BETWEEN '"+Month_FROM+"' AND '"+Month_TO+"' AND EMPLOYEEID="+username);

		}else{
		
		Emp_DOJ.append("SELECT    ");
		Emp_DOJ.append("DATE_FORMAT(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1),'%d-%m-%Y') DAY,   ");
		Emp_DOJ.append("SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', -1) FIN,   ");
		Emp_DOJ.append("MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1) END) FOUT,   ");
		Emp_DOJ.append("SUBSTRING_INDEX(TIMEDIFF(SUBSTRING_INDEX(MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END), ' ', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', 2), ' ', -1)),':',2)PERDAY,   ");
		Emp_DOJ.append("IF(DAYNAME(transactiontime)='SUNDAY','WOFF','WDAY') DAYTYPE,   ");
		Emp_DOJ.append(" IF(SUBSTRING_INDEX(TIMEDIFF(SUBSTRING_INDEX(MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END), ' ', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', 2), ' ', -1)),':',2)<'08:50',1,0) STATUS ");
		Emp_DOJ.append(" , DATE_FORMAT(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1),'%Y-%m-%d') DAYSP ");
		Emp_DOJ.append("FROM   ");
		Emp_DOJ.append("unit_local_db.tbl_reader_log A   ");
		Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO   ");
		Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.TBL_HOLIDAYS C ON B.COMPANYID=C.BUSINESSUNITID   ");
		Emp_DOJ.append("WHERE TRANSACTIONTIME  BETWEEN date(CURDATE()-(day(CURDATE())-1)) AND now() AND A.EMPLOYEEID="+username+" GROUP BY DAY   ");
		Emp_DOJ.append("UNION ALL   ");
		Emp_DOJ.append("select  DATE_FORMAT(DATE,'%d-%m-%Y')DAY,'00:00:00' AS FIN,'00:00:00' AS LOUT,'00:00' AS PERDAY,IF(DAYNAME(DATE)='SUNDAY','WOFF','WDAY') DAYTYPE ,IF(DAYNAME(DATE)='SUNDAY','','0') STATUS,DATE_FORMAT(DATE,'%Y-%m-%d') DAYSP from (   ");
		Emp_DOJ.append("select date_add(CONCAT(YEAR(NOW()),'-',MONTH(NOW()),'-01'), INTERVAL n5.num*10000+n4.num*1000+n3.num*100+n2.num*10+n1.num DAY ) as date from   ");
		Emp_DOJ.append("(select 0 as num   ");
		Emp_DOJ.append("   union all select 1   ");
		Emp_DOJ.append("   union all select 2   ");
		Emp_DOJ.append("   union all select 3   ");
		Emp_DOJ.append("   union all select 4   ");
		Emp_DOJ.append("   union all select 5   ");
		Emp_DOJ.append("   union all select 6   ");
		Emp_DOJ.append("   union all select 7   ");
		Emp_DOJ.append("   union all select 8   ");
		Emp_DOJ.append("   union all select 9) n1,   ");
		Emp_DOJ.append("(select 0 as num   ");
		Emp_DOJ.append("   union all select 1   ");
		Emp_DOJ.append("   union all select 2   ");
		Emp_DOJ.append("   union all select 3   ");
		Emp_DOJ.append("   union all select 4   ");
		Emp_DOJ.append("   union all select 5   ");
		Emp_DOJ.append("   union all select 6   ");
		Emp_DOJ.append("   union all select 7   ");
		Emp_DOJ.append("   union all select 8   ");
		Emp_DOJ.append("   union all select 9) n2,   ");
		Emp_DOJ.append("(select 0 as num   ");
		Emp_DOJ.append("   union all select 1   ");
		Emp_DOJ.append("   union all select 2   ");
		Emp_DOJ.append("   union all select 3   ");
		Emp_DOJ.append("   union all select 4   ");
		Emp_DOJ.append("   union all select 5   ");
		Emp_DOJ.append("   union all select 6   ");
		Emp_DOJ.append("   union all select 7   ");
		Emp_DOJ.append("   union all select 8   ");
		Emp_DOJ.append("   union all select 9) n3,   ");
		Emp_DOJ.append("(select 0 as num   ");
		Emp_DOJ.append("   union all select 1   ");
		Emp_DOJ.append("   union all select 2   ");
		Emp_DOJ.append("   union all select 3   ");
		Emp_DOJ.append("   union all select 4   ");
		Emp_DOJ.append("   union all select 5   ");
		Emp_DOJ.append("   union all select 6   ");
		Emp_DOJ.append("   union all select 7   ");
		Emp_DOJ.append("   union all select 8   ");
		Emp_DOJ.append("   union all select 9) n4,   ");
		Emp_DOJ.append("(select 0 as num   ");
		Emp_DOJ.append("   union all select 1   ");
		Emp_DOJ.append("   union all select 2   ");
		Emp_DOJ.append("   union all select 3   ");
		Emp_DOJ.append("   union all select 4   ");
		Emp_DOJ.append("   union all select 5   ");
		Emp_DOJ.append("   union all select 6   ");
		Emp_DOJ.append("   union all select 7   ");
		Emp_DOJ.append("   union all select 8   ");
		Emp_DOJ.append("   union all select 9) n5   ");
		Emp_DOJ.append(") a   ");
		Emp_DOJ.append("where date BETWEEN DATE_FORMAT(CONCAT(YEAR(NOW()),'-',MONTH(NOW()),'-00'),'%Y-%m-%d') and NOW()   ");
		Emp_DOJ.append("and date not in (select DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d') from unit_local_db.tbl_reader_log where employeeid="+username+" and transactiontime BETWEEN date(CURDATE()-(day(CURDATE())-1)) AND now())   ");
		Emp_DOJ.append("ORDER BY DAY ");
		
		}
		System.out.println("Emp_DOJ:1"+Emp_DOJ);
		
		
	}else{
		   // Based on Month
		
		if(ATT_FLAG!=null && ATT_FLAG.equalsIgnoreCase("DATES")){
			
			Emp_DOJ.append(" SELECT DATE_FORMAT(TRANSACTIONDATE,'%d-%m-%Y') DAY,FIN,LOUT FOUT,NET PERDAY,IF(DAYNAME(TRANSACTIONDATE)='SUNDAY','WOFF','WDAY')DAYTYPE,");
			Emp_DOJ.append("IF(NET<'08:50',IF(TRANSACTIONDATE BETWEEN (SELECT MAX(TODATE) FROM hcladm_prod.tbl_transaction_dates ");
			Emp_DOJ.append(" WHERE BUSINESSUNITID=(select companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in("+username+"))) AND SUBDATE(CURDATE(),1),1,0),0)STATUS ");
			Emp_DOJ.append(",TRANSACTIONDATE DAYSP ");
			Emp_DOJ.append(" FROM ");
			Emp_DOJ.append(" HCLHRM_PROD_OTHERS.TBL_EMPLOYEE_IOT ");
			Emp_DOJ.append(" WHERE ");
			Emp_DOJ.append(" TRANSACTIONDATE BETWEEN '"+Month_FROM+"' AND '"+Month_TO+"' AND EMPLOYEEID="+username);

			}else{
				
			
		/*Emp_DOJ.append(" SELECT  ");
		Emp_DOJ.append(" DATE_FORMAT(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1),'%d-%m-%Y')DAY, ");
		Emp_DOJ.append(" SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', -1)FIN, ");
		Emp_DOJ.append(" MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END)FOUT, ");
		Emp_DOJ.append(" SUBSTRING_INDEX(TIMEDIFF(SUBSTRING_INDEX(MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END), ' ', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', 2), ' ', -1)),':',2)PERDAY, ");
		Emp_DOJ.append(" IF(DAYNAME(transactiontime)='SUNDAY','WOFF','WDAY')DAYTYPE ");
		Emp_DOJ.append(" FROM ");
		Emp_DOJ.append(" unit_local_db.tbl_reader_log A ");
		Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO ");
		Emp_DOJ.append(" WHERE TRANSACTIONTIME  BETWEEN '2017-"+Month+"-01' AND '2017-"+Month+"-31' AND A.EMPLOYEEID="+username+" GROUP BY DAY ");
		Emp_DOJ.append(" UNION ALL ");
		Emp_DOJ.append(" select  DATE_FORMAT(DATE,'%d-%m-%Y')DAY,'00:00:00' AS FIN,'00:00:00' AS LOUT,'00:00' AS PERDAY,IF(DAYNAME(DATE)='SUNDAY','WOFF','WDAY')DAYTYPE ");
		Emp_DOJ.append(" from ( ");
		Emp_DOJ.append(" select date_add(CONCAT(2017,'-',"+Month+",'-01'), INTERVAL n5.num*10000+n4.num*1000+n3.num*100+n2.num*10+n1.num DAY ) as date from ");
		Emp_DOJ.append(" (select 0 as num ");
		Emp_DOJ.append("    union all select 1 ");
		Emp_DOJ.append("    union all select 2 ");
		Emp_DOJ.append("    union all select 3 ");
		Emp_DOJ.append("    union all select 4 ");
		Emp_DOJ.append("    union all select 5 ");
		Emp_DOJ.append("    union all select 6 ");
		Emp_DOJ.append("    union all select 7 ");
		Emp_DOJ.append("    union all select 8 ");
		Emp_DOJ.append("    union all select 9) n1, ");
		Emp_DOJ.append(" (select 0 as num ");
		Emp_DOJ.append("    union all select 1 ");
		Emp_DOJ.append("    union all select 2 ");
		Emp_DOJ.append("    union all select 3 ");
		Emp_DOJ.append("    union all select 4 ");
		Emp_DOJ.append("    union all select 5 ");
		Emp_DOJ.append("    union all select 6 ");
		Emp_DOJ.append("    union all select 7 ");
		Emp_DOJ.append("    union all select 8 ");
		Emp_DOJ.append("    union all select 9) n2, ");
		Emp_DOJ.append(" (select 0 as num ");
		Emp_DOJ.append("    union all select 1 ");
		Emp_DOJ.append("    union all select 2 ");
		Emp_DOJ.append("    union all select 3 ");
		Emp_DOJ.append("    union all select 4 ");
		Emp_DOJ.append("    union all select 5 ");
		Emp_DOJ.append("    union all select 6 ");
		Emp_DOJ.append("    union all select 7 ");
		Emp_DOJ.append("    union all select 8 ");
		Emp_DOJ.append("    union all select 9) n3, ");
		Emp_DOJ.append(" (select 0 as num ");
		Emp_DOJ.append("    union all select 1 ");
		Emp_DOJ.append("    union all select 2 ");
		Emp_DOJ.append("    union all select 3 ");
		Emp_DOJ.append("    union all select 4 ");
		Emp_DOJ.append("    union all select 5 ");
		Emp_DOJ.append("    union all select 6 ");
		Emp_DOJ.append("    union all select 7 ");
		Emp_DOJ.append("    union all select 8 ");
		Emp_DOJ.append("    union all select 9) n4, ");
		Emp_DOJ.append(" (select 0 as num ");
		Emp_DOJ.append("    union all select 1 ");
		Emp_DOJ.append("    union all select 2 ");
		Emp_DOJ.append("    union all select 3 ");
		Emp_DOJ.append("    union all select 4 ");
		Emp_DOJ.append("    union all select 5 ");
		Emp_DOJ.append("    union all select 6 ");
		Emp_DOJ.append("    union all select 7 ");
		Emp_DOJ.append("    union all select 8 ");
		Emp_DOJ.append("    union all select 9) n5 ");
		Emp_DOJ.append(" ) a ");
		Emp_DOJ.append(" where date BETWEEN DATE_FORMAT(CONCAT(2017,'-',"+Month+",'-00'),'%Y-%m-%d') and '2017-"+Month+"-31' ");
		Emp_DOJ.append(" and date not in (select DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d') from unit_local_db.tbl_reader_log where employeeid="+username+" and transactiontime BETWEEN '2017-"+Month+"-01' AND '2017-"+Month+"-31') ");
		Emp_DOJ.append(" ORDER BY DAY ");*/
				
				
			/*	
				Emp_DOJ.append(" SELECT  ");
				Emp_DOJ.append(" DATE_FORMAT(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1),'%d-%m-%Y')DAY, ");
				Emp_DOJ.append(" SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', -1)FIN, ");
				Emp_DOJ.append(" MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END)FOUT, ");
				Emp_DOJ.append(" SUBSTRING_INDEX(TIMEDIFF(SUBSTRING_INDEX(MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END), ' ', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', 2), ' ', -1)),':',2)PERDAY, ");
				Emp_DOJ.append(" IF(DAYNAME(transactiontime)='SUNDAY','WOFF','WDAY')DAYTYPE, ");
				Emp_DOJ.append(" IF(SUBSTRING_INDEX(TIMEDIFF(SUBSTRING_INDEX(MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END), ' ', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', 2), ' ', -1)),':',2)<'08:50',1,0)STATUS ");
				Emp_DOJ.append(" FROM ");
				Emp_DOJ.append(" unit_local_db.tbl_reader_log A ");
				Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO ");
				Emp_DOJ.append(" WHERE DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d')  >=CONCAT(YEAR(NOW()),'-"+Month+"','-01') and DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d')  <=LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) AND A.EMPLOYEEID="+username+" GROUP BY DAY ");
				Emp_DOJ.append(" UNION ALL ");
				Emp_DOJ.append(" select  DATE_FORMAT(DATE,'%d-%m-%Y')DAY,'00:00:00' AS FIN,'00:00:00' AS LOUT,'00:00' AS PERDAY,IF(DAYNAME(DATE)='SUNDAY','WOFF','WDAY')DAYTYPE,IF(DAYNAME(DATE)='SUNDAY','','AB') STATUS ");
				Emp_DOJ.append(" from ( ");
				Emp_DOJ.append(" select date_add(CONCAT(YEAR(NOW()),'-"+Month+"','-01'), INTERVAL n5.num*10000+n4.num*1000+n3.num*100+n2.num*10+n1.num DAY ) as date from ");
				Emp_DOJ.append(" (select 0 as num ");
				Emp_DOJ.append("    union all select 1 ");
				Emp_DOJ.append("    union all select 2 ");
				Emp_DOJ.append("    union all select 3 ");
				Emp_DOJ.append("    union all select 4 ");
				Emp_DOJ.append("    union all select 5 ");
				Emp_DOJ.append("    union all select 6 ");
				Emp_DOJ.append("    union all select 7 ");
				Emp_DOJ.append("    union all select 8 ");
				Emp_DOJ.append("    union all select 9) n1, ");
				Emp_DOJ.append(" (select 0 as num ");
				Emp_DOJ.append("    union all select 1 ");
				Emp_DOJ.append("    union all select 2 ");
				Emp_DOJ.append("    union all select 3 ");
				Emp_DOJ.append("    union all select 4 ");
				Emp_DOJ.append("    union all select 5 ");
				Emp_DOJ.append("    union all select 6 ");
				Emp_DOJ.append("    union all select 7 ");
				Emp_DOJ.append("    union all select 8 ");
				Emp_DOJ.append("    union all select 9) n2, ");
				Emp_DOJ.append(" (select 0 as num ");
				Emp_DOJ.append("    union all select 1 ");
				Emp_DOJ.append("    union all select 2 ");
				Emp_DOJ.append("    union all select 3 ");
				Emp_DOJ.append("    union all select 4 ");
				Emp_DOJ.append("    union all select 5 ");
				Emp_DOJ.append("    union all select 6 ");
				Emp_DOJ.append("    union all select 7 ");
				Emp_DOJ.append("    union all select 8 ");
				Emp_DOJ.append("    union all select 9) n3, ");
				Emp_DOJ.append(" (select 0 as num ");
				Emp_DOJ.append("    union all select 1 ");
				Emp_DOJ.append("    union all select 2 ");
				Emp_DOJ.append("    union all select 3 ");
				Emp_DOJ.append("    union all select 4 ");
				Emp_DOJ.append("    union all select 5 ");
				Emp_DOJ.append("    union all select 6 ");
				Emp_DOJ.append("    union all select 7 ");
				Emp_DOJ.append("    union all select 8 ");
				Emp_DOJ.append("    union all select 9) n4, ");
				Emp_DOJ.append(" (select 0 as num ");
				Emp_DOJ.append("    union all select 1 ");
				Emp_DOJ.append("    union all select 2 ");
				Emp_DOJ.append("    union all select 3 ");
				Emp_DOJ.append("    union all select 4 ");
				Emp_DOJ.append("    union all select 5 ");
				Emp_DOJ.append("    union all select 6 ");
				Emp_DOJ.append("    union all select 7 ");
				Emp_DOJ.append("    union all select 8 ");
				Emp_DOJ.append("    union all select 9) n5 ");
				Emp_DOJ.append(" ) a ");
				Emp_DOJ.append(" where DATE_FORMAT(DATE,'%Y-%m-%d') >=CONCAT(YEAR(NOW()),'-"+Month+"','-01') and DATE_FORMAT(DATE,'%Y-%m-%d') <=LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) ");
				Emp_DOJ.append(" and date not in (select DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d') from unit_local_db.tbl_reader_log ");
				Emp_DOJ.append(" where employeeid="+username+"  and DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d') >= CONCAT(YEAR(NOW()),'-"+Month+"','-01') AND DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d')<=LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01'))) ");
				Emp_DOJ.append(" ORDER BY DAY ");	*/
				
				Emp_DOJ.append("	select A.DATEON AS DAY ,A.ATT_IN AS FIN,A.ATT_OUT AS FOUT, ");
				Emp_DOJ.append(" A.NET_HOURS AS PERDAY,A.DAYTYPE AS DAYTYPE ,'1' STATUS ");
				Emp_DOJ.append(" 	from test_mum.tbl_att_leave_in_out_status_report A ");
				Emp_DOJ.append("	join hclhrm_prod.tbl_employee_primary B ");
				Emp_DOJ.append(" ON B.employeesequenceno=A.employeeid ");
				Emp_DOJ.append("	where B.employeesequenceno in("+username+") ");
				Emp_DOJ.append(" and a.DATEON BETWEEN CONCAT(YEAR(NOW()),'-"+Month+"','-01') ");
				Emp_DOJ.append("	AND LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) ");
				Emp_DOJ.append(" order by a.DATEON ");
				
				
		
			}
		System.out.println("Emp_DOJ:::---------->"+Emp_DOJ.toString());	
		
	}
		
		
		
		
		
	/*	HLDAYLIST.append("SELECT DISTINCT DATE_FORMAT(A.HOLIDAYDATE,'%d-%m-%Y') DAY,");
		HLDAYLIST.append(" TRIM(CONCAT(A.COMMENTS,'  ',IF(A.HOLIDAYTYPEID=0,'','(OPTINAL HLDAY)')))OCCASION");
		HLDAYLIST.append(" FROM");
		HLDAYLIST.append(" hclhrm_prod.tbl_holidays A");
		HLDAYLIST.append(" LEFT JOIN HCLHRM_PROD.TBL_HOLIDAY_TYPE B ON A.HOLIDAYTYPEID=B.HOLIDAYTYPEID");
		HLDAYLIST.append(" WHERE  year(A.HOLIDAYDATE)=year(now()) ");
		*/
		

		HLDAYLIST.append("SELECT DISTINCT DATE_FORMAT(A.HOLIDAYDATE,'%d-%m-%Y') DAY,");
		HLDAYLIST.append(" TRIM(CONCAT(A.COMMENTS,'  ',IF(A.HOLIDAYTYPEID=0,'','(OPTINAL HLDAY)')))OCCASION");
		HLDAYLIST.append(" FROM");
		HLDAYLIST.append(" hclhrm_prod.tbl_holidays A");
		HLDAYLIST.append(" LEFT JOIN HCLHRM_PROD.TBL_HOLIDAY_TYPE B ON A.HOLIDAYTYPEID=B.HOLIDAYTYPEID");
		//HLDAYLIST.append(" WHERE  year(A.HOLIDAYDATE)=year(now()) ");
		HLDAYLIST.append(" WHERE  A.statusid=1001  ");
		HLDAYLIST.append(" and A.businessunitid in( ");
		HLDAYLIST.append(" select companyid from hclhrm_prod.tbl_employee_primary where ");
		HLDAYLIST.append(" employeesequenceno in("+username+")) ");
		
		//HLDAYLIST.append(" WHERE A.HOLIDAYDATE BETWEEN '2016-01-01' AND '2016-12-31' ");
		//Months_ATT.append(" SELECT DISTINCT IF(MONTH(TRANSACTIONTIME)<10,CONCAT(0,MONTH(TRANSACTIONTIME)),MONTH(TRANSACTIONTIME))MONTHS,MONTHNAME(TRANSACTIONTIME) MONTHNAME  ");
		//Months_ATT.append(" FROM unit_local_db.tbl_reader_log where employeeid=20314 AND YEAR(TRANSACTIONTIME)=YEAR(CURDATE())");
		
		Months_ATT.append(" SELECT DISTINCT IF(MONTH(TRANSACTIONTIME)<10,CONCAT(0,MONTH(TRANSACTIONTIME)),MONTH(TRANSACTIONTIME))MONTHS,MONTHNAME(TRANSACTIONTIME) MONTHNAME ");
		Months_ATT.append(" FROM unit_local_db.tbl_reader_log where ");
		Months_ATT.append(" MONTH(TRANSACTIONTIME)!=MONTH(CURDATE()) AND ");
		Months_ATT.append(" employeeid="+username+" AND YEAR(TRANSACTIONTIME)=YEAR(CURDATE()) ");
		Months_ATT.append(" ORDER BY MONTH(TRANSACTIONTIME) DESC ");
		
		
		
		Res=null;
    
		try {
			Res=(ResultSet)DataObj.FetchData(HLDAYLIST.toString(), "Emp_DOJ", Res ,conn);
			while(Res.next()){
				
				hlday.put(Res.getString(1), Res.getString(2));
				
			}  
		}catch(Exception Er){
			System.out.println("Exception At Er::"+Er);
		}
		
		Res=null;
	    
		try {
			Res=(ResultSet)DataObj.FetchData("select date_format(REQ_DATE,'%d-%m-%Y'),if(FLAG='P','PROCESSED',if(FLAG='A','APPROVED','REJECT')) statusflg from hclhrm_prod_others.tbl_emp_attn_req where employeeid="+username+" and REQ_TYPE='AR' ", "EMPLOYEE_ATTENDANCELIST", Res ,conn);
			while(Res.next()){
				
				Att_Req.put(Res.getString(1), Res.getString(2));
				
			}  
			
		}catch(Exception Er){
			System.out.println("Exception At Er::"+Er);
		}
		
		
		
     Res=null;
	    
		try {
			 ATT_MONTHS.put("currmonth", "Current Month");
			 Res=(ResultSet)DataObj.FetchData(Months_ATT.toString(), "EMPLOYEE_ATTENDANCELIST", Res ,conn);
			while(Res.next()){
				
				ATT_MONTHS.put(Res.getString(1), Res.getString(2));
				
			}  
			
		}catch(Exception Er){
			System.out.println("Exception At Er::"+Er);
		}
		
		
		
	  }
		
		Res=null;
		try {
			ps=conn.prepareStatement(User_Authen.toString());
			ps.setInt(1,Integer.parseInt(username));
			ps.setString(2,password);
			Res=(ResultSet)DataObj.FetchDataPrepare_2(ps, "User Authentication",Res,conn);
			//Res=(ResultSet)DataObj.FetchData("Select * from hclhrm_prod.tbl_employee_primary", "UserAuthentiCation", Res ,conn);
			if(Res.next()){

				User_Auth=Res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Res=null;
		ps=null;
		Res=null;
		ResultSetMetaData rsmd=null;
		
if(Routing.equalsIgnoreCase("Att_Request")  && User_Auth==1){
			ps=null;
			try{	


				conn.setAutoCommit(false);
				System.out.println("Connection Established....!");
				
				Random rand = new Random();
				int nRand = rand.nextInt(90000) + 10000;
				
				//ps=conn.prepareStatement("INSERT INTO hclhrm_prod_others.tbl_emp_attn_req (EMPLOYEEID,SUBJECT,REQ_DATE,MESSAGE,RANDOMID) VALUES (?,?,STR_TO_DATE(?,'%Y-%m-%d'),?,?)");
				//RID, EMPLOYEEID, TO_EMAIL, CC_EMAIL, SUBJECT, REQ_DATE, FROM_DATE, TO_DATE, MESSAGE, URL, MAIL_STATUS, MAIL_ERROR, STATUS, FLAG, COMMENTS, LUPDATE, RANDOMID, REQ_TYPE, TOTA_HOURS
				ps=conn.prepareStatement("INSERT INTO hclhrm_prod_others.tbl_emp_attn_req (EMPLOYEEID,SUBJECT,REQ_DATE,MESSAGE,RANDOMID,FROM_DATE,TO_DATE,TOTA_HOURS,TO_EMAIL,CC_EMAIL) VALUES (?,?,?,?,?,?,?,?,?,?)");
				
				date=request.getParameter("id");
				Req_message=request.getParameter("message");
				subject=request.getParameter("Subject");
				random=request.getParameter("RanDm");
				String FIN=request.getParameter("FIN");
				String FOUT=request.getParameter("FOUT");
				String TIME=request.getParameter("TIME");
				
				String toemail=request.getParameter("toemail");
				String ccemail=request.getParameter("ccemail");
				
				  try{
					  toemail=toemail.replaceAll(";", ",");
					  ccemail=ccemail.replaceAll(";", ",");
					  
				  }catch(Exception Errr){
					  System.out.println("Errr--->at Att Req ::"+Errr);
				  }
				
				ps.setString(1,username);
				ps.setString(2,subject);
				ps.setString(3,date);
				ps.setString(4,Req_message);
				ps.setInt(5,nRand);
				ps.setString(6,FIN);
				ps.setString(7,FOUT);
				ps.setString(8,TIME);
				ps.setString(9,toemail);
				ps.setString(10,ccemail);
				ps.addBatch();
				
				int count[] = ps.executeBatch();

				System.out.println("add Batch Count::"+count.length);

				if(count.length>0){
				conn.commit();
				}else{
					conn.rollback();
					Atten_Req_Message="Request Not Processed Please contact system admin";
				}


				//out.write(""+count.toString()+"");  

			}
			catch (Exception e2)
			{
				Atten_Req_Message="Faild To Process Your Request Please Contact System Admin.";
				e2.printStackTrace();  
			}

} // IF Close For Insertion
		
		
		
		if(User_Auth==1 && !Routing.equalsIgnoreCase("Att_Request") ){

			if(Routing.equalsIgnoreCase("NewJoine")){
				_SUCCESS_PAGE=bundle_info.getString("HHCL_DESK_DIVERT_NEWJOINEE");
			}else if(Routing.equalsIgnoreCase("HOLIDAYS")){

				_SUCCESS_PAGE=bundle_info.getString("HHCL_DESK_DIVERT_HOLIDAYS");
			}else if(Routing.equalsIgnoreCase("BIRTHADYS")){

				_SUCCESS_PAGE=bundle_info.getString("HHCL_DESK_DIVERT_BIRTHDAYS");
			}
			else if(Routing.equalsIgnoreCase("ATTENDANCE_BIO")){

				_SUCCESS_PAGE=bundle_info.getString("HHCL_DESK_DIVERT_ATTENDANCE");
			}



			Res=null;

			try {
				Res=(ResultSet)DataObj.FetchData_Emp_DOB(Emp_DOJ.toString(), "Emp_DOJ", Res ,conn);
				while(Res.next()){

					Doj= new JSONObject();


					if(Routing.equalsIgnoreCase("NewJoine")){	
						Doj.put("CALLNAME" , Res.getString(1)); 
						Doj.put("DEPT" , Res.getString(2));
						Doj.put("EMAIL" , Res.getString(3));
						Doj.put("MOBILE" , Res.getString(4));
						Doj.put("BUNAME" , Res.getString(5));

					}else if(Routing.equalsIgnoreCase("HOLIDAYS")){

						Doj.put("EVENTDATE" , Res.getString(1)); 
						Doj.put("EVENT" , Res.getString(2));
						Doj.put("FLAG" , Res.getString(3));
						Doj.put("HOLIDAYTYPE" , Res.getString(4));


					}
					else if(Routing.equalsIgnoreCase("BIRTHADYS")){
						Doj.put("EVENTDATE" , Res.getString(1)); 
						Doj.put("BUNAME" , Res.getString(2));
						Doj.put("EVENT" , Res.getString(3));
						Doj.put("FLAG" , Res.getString(4));
						Doj.put("HOLIDAYTYPE" , Res.getString(5));

					}
					else if(Routing.equalsIgnoreCase("ATTENDANCE_BIO")){
                          
						
						
						
						
					//	hlday
						
						String Fin=Res.getString(2);
						String Fout=Res.getString(3);
						
						Doj.put("DATE" , Res.getString(1)); 
						/*Doj.put("FIN" , Res.getString(2));
						Doj.put("FOUT" , Res.getString(3));*/
						
						Doj.put("FIN" , Fin);
						Doj.put("FOUT" ,Fout);
						
						Doj.put("PERDAY" , Res.getString(4));
						
						if(Month.equalsIgnoreCase("currmonth")){
							Doj.put("INNER" , Res.getString("DAYSP")+"#"+Res.getString(2)+"#"+Res.getString(3)+"#"+Res.getString(4));
							Doj.put("DAYTYPE" , Res.getString(5));
						}
						
						if(hlday.get(Res.getString(1))!=null){
							
								Doj.put("DAYTYPE" , hlday.get(Res.getString(1)).toString());
								Doj.put("DAF" , "none");
							
						}else{
							
							if(Res.getString(5).toString().equalsIgnoreCase("WOFF")){
									Doj.put("DAF" , "none");
							}else{
								
								// add for curr date
								if((Res.getString(1).toString()).equalsIgnoreCase(strTime)){
									 Doj.put("DAF" , "none");
								}else{
									
									
									 Doj.put("DAF" , " ");
									 
									 
								}
								
								
							}
							Doj.put("DAYTYPE" , Res.getString(5));
							
						}
						
						if(Month.equalsIgnoreCase("currmonth")){
							
						if(Res.getString(6).toString().equalsIgnoreCase("0")){
							
							//Doj.put("DAREQ" ,  Att_Req.get(Res.getString(1)).toString());
							
							// FOr Time Factor Caliculation 
							
							String []INTime;
							String []OUTTime;
							
							int INH=0,INM=0,OUTH=0,OUTM=0;
							  
							try{
								  INTime=Fin.split(":");
								  OUTTime=Fout.split(":");
								
								  INH=Integer.parseInt(INTime[0]);
								  INM=Integer.parseInt(INTime[1]);
								  OUTH=Integer.parseInt(OUTTime[0]);
								  OUTM=Integer.parseInt(OUTTime[1]);
								
								  
								  if(INH==0 && INM==0 && OUTH==0){
									  Doj.put("DAF" , "none");
								  }else if(INH<9 && OUTH>=18){
									
							           Doj.put("DAF" , "none");
									
								}else if((INH>=9 && INH<10 )&& OUTH>=18){
								  
									   if(INM<=10){
										   Doj.put("DAF" , "none");
									   }else{
										   Doj.put("DAF" , " ");
									   }
									
								
								}else if(INH>=10 | OUTH<18){
								  
									 Doj.put("DAF" , " ");
									 
								}else{
									 Doj.put("DAF" , "none");
								}
							    
							    
							    
							    
							}catch(Exception edr){
								 Doj.put("DAF" , "none");
								System.out.println("edr - Exception::"+edr);
							}
						
							 // FOr Time Factor Caliculation 
							    
							
						}
						}else{
							
							/// TIme For Caliculation
							
							
							String []INTime;
							String []OUTTime;
							
							int INH=0,INM=0,OUTH=0,OUTM=0;
							  
							try{
								  INTime=Fin.split(":");
								  OUTTime=Fout.split(":");
								
								  INH=Integer.parseInt(INTime[0]);
								  INM=Integer.parseInt(INTime[1]);
								  OUTH=Integer.parseInt(OUTTime[0]);
								  OUTM=Integer.parseInt(OUTTime[1]);
								
								  
								  if(INH==0 && INM==0 && OUTH==0){
									  Doj.put("DAF" , "none");
								  }else if(INH<9 && OUTH>=18){
									
							           Doj.put("DAF" , "none");
									
								}else if((INH>=9 && INH<10 )&& OUTH>=18){
								  
									   if(INM<=10){
										   Doj.put("DAF" , "none");
									   }else{
										  // Doj.put("DAF" , " ");
										   
										   Doj.put("DAF" , "none");
									   }
									
								
								}else if(INH>=10 | OUTH<18){
								  
									// Doj.put("DAF" , " ");
									 Doj.put("DAF" , "none");
								}else{
									 Doj.put("DAF" , "none");
								}
							
							}catch(Exception err){
							
								Doj.put("DAF" , "none");
							}
							
							
						//TIME For Caliculation
							    //Doj.put("DAF" , "none");
						}
						
							System.out.println("Res.getString(1)"+Res.getString(1));
						
						
						if(Att_Req.get(Res.getString(1))!=null){
							
							Doj.put("DAREQ" ,  Att_Req.get(Res.getString(1)).toString());
							
							Doj.put("DAF" , "none");
							/*if((Res.getString(1).toString()).equalsIgnoreCase(strTime)){
								 Doj.put("DAF" , "none");
								}else{
									 Doj.put("DAF" , " ");
								}*/
							
						}
						else{
							Doj.put("DAREQ" ,  "No Request");
						}
						
						

						//Doj.put("DAYTYPE" , Res.getString(5));
						//Doj.put("MOBILE" , Res.getString(6));

					}
					Doj.put("DAF" , "none"); // this is adding for new DAF Button Display none
					//DOJ_DOB.put("EMPDATA" ,DOJ_DOB.toString());

					values.add(Doj);


					/*DOJ_DOB.put(Res.getString(1) , Res.getString(2)); 
					 * */


					dobcnt1++;
				}  
			}catch(Exception Er){
				System.out.println("Exception At Er::"+Er);
			}
			
		}else{
			_SUCCESS_PAGE="/login.html";
			request.setAttribute("message",bundle_info.getString("HHCL_DESK_LOGI_FAILD")); 
		}

		System.out.println("dobcnt1::" + dobcnt1);
		if(dobcnt1<1){

			System.out.println("dobcnt1-2::" + dobcnt1);
			values = new JSONArray();
			if(Routing.equalsIgnoreCase("NewJoine")){	
				Doj.put("CALLNAME" , "No Records"); 
				Doj.put("DEPT" , "No Records");
				Doj.put("EMAIL" , "No Records");
				Doj.put("MOBILE" , "No Records");
				Doj.put("BUNAME" , "No Records");

			}else if(Routing.equalsIgnoreCase("HOLIDAYS")){

				Doj.put("EVENTDATE" , "No Records"); 
				Doj.put("EVENT" , "No Records");
				Doj.put("FLAG" , "No Records");
				Doj.put("HOLIDAYTYPE" , "No Records");


			}
			else if(Routing.equalsIgnoreCase("BIRTHADYS")){
				Doj.put("EVENTDATE" , "No Records"); 
				Doj.put("BUNAME" , "No Records");
				Doj.put("EVENT" , "No Records");
				Doj.put("FLAG" , "No Records");
				Doj.put("HOLIDAYTYPE" ,"No Records");

			}else if(Routing.equalsIgnoreCase("ATTENDANCE_BIO")){

				/*Doj.put("EMPNAME" , "No Records"); 
				Doj.put("businessunit" , "No Records");
				Doj.put("Department" , "No Records");
				Doj.put("designation" , "No Records");
				Doj.put("EMAIL" , "No Records");
				Doj.put("MOBILE" , "No Records");*/
				
				Doj.put("DATE" , "NO_DATA"); 
				Doj.put("FIN" , "NO_DATA");
				Doj.put("FOUT" , "NO_DATA");
				Doj.put("PERDAY" , "NO_DATA");
				Doj.put("INNER" , "NO_DATA");
				Doj.put("DAYTYPE" , "NO_DATA");
				Doj.put("DAF" , "none");
				Doj.put("DAREQ" ,  "NO_DATA");
				

			}
			values.add(Doj);
			
			System.out.println("values::"+values.toString());
		}
		request.setAttribute("DOJ_DOB",values.toString());
		request.setAttribute("ATT_MONTHS",ATT_MONTHS.toString());
		
		if(Routing==null){
			session.setAttribute("EMP_ID", username);
			session.setAttribute("EMP_PASSWORD", password);

		}
		System.out.println("_SUCCESS_PAGE::"+_SUCCESS_PAGE);
		 try {
			     if(statement!=null){
			       statement.close();
			     }
			   if(Res!=null){
				    Res.close();
	  			}
			 /*if(conn!=null){
				conn.close();
	       }*/
			 if(ps!=null){
				ps.close();
	       }
	 		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Routing.equalsIgnoreCase("Att_Request")){
			
			System.out.println("Atten_Req_Message::"+Atten_Req_Message);
			out.write(""+Atten_Req_Message+"");  
			
		}else if(Routing_temp.equalsIgnoreCase("ATTENDANCE_BIO_DATES")){
			
			System.out.println("Atten_Req_Message::"+Atten_Req_Message);
			
			System.out.println("values.toString()::"+values.toString());
			out.write(""+values.toString()+"");  
			
		}
		
		
		else{
			request.getRequestDispatcher(_SUCCESS_PAGE).forward(request, response);  
		}
	}  
}
