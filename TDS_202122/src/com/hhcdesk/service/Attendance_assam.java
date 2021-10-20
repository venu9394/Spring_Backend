package com.hhcdesk.service;
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
public class Attendance_assam extends HttpServlet {

	public Attendance_assam() {
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
		String ATT_FLAG=null;
		PrintWriter out = response.getWriter();
		Map UserMap=new HashMap();
        
		boolean ConnFlag=true;
		boolean currMonthflg=false;
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
        
		String EnableDays="4";
	try{
		EnableDays=bundle_info.getString("AssamEnableDays");
		
	}catch(Exception erd){
		System.out.println("EnableDays Assam ::"+EnableDays);
	}
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
		
		 ATT_FLAG=request.getParameter("ATT_FLAG");
		String Month_FROM=request.getParameter("Month_FROM");
		String Month_TO=request.getParameter("Month_TO");
		
		 Month=request.getParameter("Month");
		 
		 System.out.println(ATT_FLAG +"~~"+Month_FROM +"~~"+Month_TO);
		 
		 if(Month==null){
			 Month="currmonth";
			 ATT_FLAG="DATES";
		 }
		
		System.out.println("Month:::"+Month);
		 System.out.println(ATT_FLAG +"~~"+Month_FROM +"~~"+Month_TO);
		
	
		
		StringBuffer PayperiodDayes=new StringBuffer();
		StringBuffer Request_Enable=new StringBuffer();
		String EmpBuid=session.getAttribute("EMPBUID").toString();
	/// Current Month
		UserMap.put("FROMDATE", "0000-00-00");
		UserMap.put("TODATE", "0000-00-00");
		
		if(Month!=null && Month.equalsIgnoreCase("currmonth")  ){
			
			PayperiodDayes.append(" select A.fromdate,A.TODATE,if(month(now())<10,concat('0',month(now())) ,month(now())) AS CurrMonth from hcladm_prod.tbl_transaction_dates A ");
			PayperiodDayes.append("  join( ");
			PayperiodDayes.append("   select max(transactionduration) txnid from hcladm_prod.tbl_transaction_dates where ");
			PayperiodDayes.append("   transactiontypeid=1 and businessunitid="+EmpBuid+" ");
			PayperiodDayes.append("  )B ON A.transactionduration=B.txnid ");
			PayperiodDayes.append("  and A.businessunitid="+EmpBuid+" and A.transactiontypeid=1 ");
			currMonthflg=true;
			
			
		}else{
			
			PayperiodDayes.append(" select A.fromdate,A.TODATE,MONTH(now()) from hcladm_prod.tbl_transaction_dates A ");
			PayperiodDayes.append("  join( ");
			PayperiodDayes.append("   select concat(YEAR(now()),'"+Month+"') txnid ");
			PayperiodDayes.append("  )B ON A.transactionduration=B.txnid ");
			PayperiodDayes.append("  and A.businessunitid="+EmpBuid+" and A.transactiontypeid=1 ");
			
		}
 	    	System.out.println("PayperiodDayes ::"+PayperiodDayes.toString());
			Res=null;
			try {
				Res=(ResultSet)DataObj.FetchData(PayperiodDayes.toString(), "Emp_DOJ", Res ,conn);
				while(Res.next()){
					UserMap.put("FROMDATE", Res.getString("fromdate"));
					UserMap.put("TODATE", Res.getString("TODATE"));
					if(Month!=null && Month.equalsIgnoreCase("CurrMonth") &&currMonthflg ){
						Month=Res.getString("CurrMonth");
					}
				}  
			}catch(Exception Er){
				System.out.println("Exception At Er::"+Er);
			}

boolean requestflag=false;
if(ATT_FLAG!=null && ATT_FLAG.equalsIgnoreCase("DATES")){
	
	
	
	requestflag=true;
	
	if(EmpBuid.equalsIgnoreCase("16") || EmpBuid.equalsIgnoreCase("15")){
		
		
		
		
		Request_Enable.append(" select distinct dateon from test_mum.tbl_attendance_date_limit_shift ");
		Request_Enable.append(" where DATEON  between date_ADD(date_format(current_date()-1,'%Y-%m-%d'),INTERVAL -"+EnableDays+" DAY) and date_format(current_date()-1,'%Y-%m-%d') and STATUS=1002");
		//Request_Enable.append("   ");
		Request_Enable.append(" union ");
		Request_Enable.append(" select distinct transactiondate from hclhrm_prod_others.tbl_attendance_adjustments ");
		Request_Enable.append(" where transactiondate between "+UserMap.get("FROMDATE")+" and "+UserMap.get("TODATE")+" ");
		Request_Enable.append(" and employeeid="+username+"  ");
		
		
		/*Request_Enable.append(" select distinct dateon from test_mum.tbl_attendance_date_limit_shift ");
		Request_Enable.append(" where DATEON between date_ADD(current_date(),INTERVAL -1 DAY) and current_date() and STATUS=1002");
		//Request_Enable.append("   ");
		Request_Enable.append(" union ");
		Request_Enable.append(" select distinct transactiondate from hclhrm_prod_others.tbl_attendance_adjustments ");
		Request_Enable.append(" where transactiondate between "+UserMap.get("FROMDATE")+" and "+UserMap.get("TODATE")+" ");
		Request_Enable.append(" and employeeid="+username+"  ");*/
		
	}else{
		
		Request_Enable.append(" select distinct dateon from test_mum.tbl_attendance_date_limit ");
		Request_Enable.append(" where DATEON between date_ADD(date_format(current_date()-1,'%Y-%m-%d'),INTERVAL -"+EnableDays+" DAY) and date_format(current_date()-1,'%Y-%m-%d') and STATUS=1002");
		Request_Enable.append(" union ");
		Request_Enable.append(" select distinct transactiondate from hclhrm_prod_others.tbl_attendance_adjustments ");
		Request_Enable.append(" where transactiondate between "+UserMap.get("FROMDATE")+" and "+UserMap.get("TODATE")+" ");
		Request_Enable.append(" and employeeid="+username+" ");
		
	}
	
	System.out.println("Request_Enable1" +Request_Enable.toString());
	
	
	
}else{
	
	requestflag=false;
	
    if(EmpBuid.equalsIgnoreCase("16") || EmpBuid.equalsIgnoreCase("15")){
		
		Request_Enable.append(" select distinct dateon from test_mum.tbl_attendance_date_limit_shift ");
		Request_Enable.append(" where DATEON between date_ADD(date_format(current_date()-1,'%Y-%m-%d'),INTERVAL -"+EnableDays+" DAY) and date_format(current_date()-1,'%Y-%m-%d') and STATUS=1002");
		Request_Enable.append(" union ");
		Request_Enable.append(" select distinct transactiondate from hclhrm_prod_others.tbl_attendance_adjustments ");
		Request_Enable.append(" where transactiondate between CONCAT(YEAR(NOW()),'-"+Month+"','-01')  and LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) ");
		Request_Enable.append(" and employeeid="+username+" ");
		
	}else{
		
		Request_Enable.append(" select distinct dateon from test_mum.tbl_attendance_date_limit ");
		Request_Enable.append(" where DATEON between date_ADD(date_format(current_date()-1,'%Y-%m-%d'),INTERVAL -"+EnableDays+" DAY) and date_format(current_date()-1,'%Y-%m-%d') and STATUS=1002");
		Request_Enable.append(" union ");
		Request_Enable.append(" select distinct transactiondate from hclhrm_prod_others.tbl_attendance_adjustments ");
		Request_Enable.append(" where transactiondate between CONCAT(YEAR(NOW()),'-"+Month+"','-01') and LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) ");
		Request_Enable.append(" and employeeid="+username+" ");
		
	}
	
    System.out.println("Request_Enable2" +Request_Enable.toString());
}
		
		Res=null;
		
		if(currMonthflg){
		try {
			Res=(ResultSet)DataObj.FetchData(Request_Enable.toString(), "Enable Days", Res ,conn);
			while(Res.next()){
				UserMap.put(Res.getString("dateon"), Res.getString("dateon"));
			}  
		}catch(Exception Er){
			System.out.println("Exception At Er::"+Er);
		}
		}
			
  // Current month validation
		
		
  //if(Month!=null && Month.equalsIgnoreCase("currmonth")  ){
		
		StringBuffer HoursData=new StringBuffer();
		
		
		
		if(ATT_FLAG!=null && ATT_FLAG.equalsIgnoreCase("MONTHS")){
			
			/*Emp_DOJ.append(" SELECT DATE_FORMAT(TRANSACTIONDATE,'%d-%m-%Y') DAY,FIN,LOUT FOUT,NET PERDAY,IF(DAYNAME(TRANSACTIONDATE)='SUNDAY','WOFF','WDAY')DAYTYPE,");
			Emp_DOJ.append("IF(NET<'08:50',IF(TRANSACTIONDATE BETWEEN (SELECT MAX(TODATE) FROM hcladm_prod.tbl_transaction_dates ");
			Emp_DOJ.append(" WHERE BUSINESSUNITID=(select companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in("+username+"))) AND SUBDATE(CURDATE(),1),1,0),0)STATUS ");
			Emp_DOJ.append(",TRANSACTIONDATE DAYSP ");
			Emp_DOJ.append(" FROM ");
			Emp_DOJ.append(" HCLHRM_PROD_OTHERS.TBL_EMPLOYEE_IOT ");
			Emp_DOJ.append(" WHERE ");
			Emp_DOJ.append(" TRANSACTIONDATE BETWEEN '"+Month_FROM+"' AND '"+Month_TO+"' AND EMPLOYEEID="+username);*/
			
			Emp_DOJ.append(" select distinct A.DATEON AS DAY ,A.ATT_IN AS FIN,A.ATT_OUT AS FOUT,  ");
			Emp_DOJ.append(" A.NET_HOURS AS PERDAY, if(A.DATEON=ifnull(C.LEAVEON,'0000-00-00'),C.LEAVE_TYPE, A.DAYTYPE) AS DAYTYPE, ");
			Emp_DOJ.append(" IF(ifnull(D.FLAG,'No Request')='A','APPROVED',if(ifnull(D.FLAG,'No Request')='R','Reject',if(ifnull(D.FLAG,'No Request')='P','Processed','No Request'))) STATUS, ");
			Emp_DOJ.append(" D.FLAG, ");
			Emp_DOJ.append(" if(A.DATEON=ifnull(C.LEAVEON,'0000-00-00'),concat(C.LEAVE_TYPE,' / ',C.LEAVE_COUNT,' DAY'), A.DAYTYPE) AS DAYTYPE2, ");
			Emp_DOJ.append(" ifnull(A.SHIFT,'General') AS SHIFT, ");
			Emp_DOJ.append(" CASE ");
			Emp_DOJ.append(" WHEN A.SHIFT='Morning Shift' THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Second Shift'  THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Night Shift'   THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
			Emp_DOJ.append(" WHEN A.SHIFT='General'       THEN   TIMEDIFF(A.NET_HOURS,'09:00:00') ");
			Emp_DOJ.append(" ELSE concat('#',TIMEDIFF(A.NET_HOURS,'09:00:00')) ");
			Emp_DOJ.append(" END AS DIFFHOURS, ");
			Emp_DOJ.append(" CASE ");
			Emp_DOJ.append(" WHEN A.SHIFT='Morning Shift' THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Second Shift'  THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Night Shift'   THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" WHEN A.SHIFT='General'       THEN    IF(TIMEDIFF(A.NET_HOURS,'09:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" ELSE  IF(TIMEDIFF(A.NET_HOURS,'09:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" END AS DEDHOURS ,");
			Emp_DOJ.append("  date_format(A.DATEON,'%d-%m-%Y') AS DAYVIEW ,date_format(now(),'%d-%m-%Y') As Newdate from test_mum.tbl_att_leave_in_out_status_report A ");
			Emp_DOJ.append(" join hclhrm_prod.tbl_employee_primary B ");
			Emp_DOJ.append(" ON B.employeesequenceno=A.employeeid and A.PAYPERIOD=0 ");
			Emp_DOJ.append(" left join hclhrm_prod_others.tbl_emp_leave_report C ");
			Emp_DOJ.append(" ON C.employeeid=B.employeesequenceno AND C.LEAVEON=A.DATEON AND C.MANAGER_STATUS IN ('A','P') and C.STATUS=1001 ");
			Emp_DOJ.append(" left join hclhrm_prod_others.tbl_emp_attn_req D ON D.EMPLOYEEID=B.employeesequenceno AND D.REQ_DATE=A.DATEON AND ");
			Emp_DOJ.append(" D.REQ_TYPE='AR' AND D.STATUS=1001 ");
			Emp_DOJ.append(" where B.employeesequenceno in("+username+") ");
		    // Emp_DOJ.append(" and a.DATEON BETWEEN  '"+UserMap.get("FROMDATE")+"' ");
			 //Emp_DOJ.append(" AND '"+UserMap.get("TODATE")+"' ");
			Emp_DOJ.append(" and a.DATEON BETWEEN CONCAT(YEAR(NOW()),'-"+Month+"','-01') ");
			Emp_DOJ.append(" AND LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) ");
			Emp_DOJ.append(" order by a.DATEON ");
			
			
			HoursData.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,");
			HoursData.append(" nettime,status,ifnull(remark,'Personal') as remark FROM `procedure`.tbl_employee_logs_allbu ");
			HoursData.append(" where employeeid="+username+" and ");
			HoursData.append(" transactiondate BETWEEN CONCAT(YEAR(NOW()),'-"+Month+"','-01')  AND LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01'))  and time(nettime)>time('00:10:00') ");
			
			

		}else{
		
			
			Emp_DOJ.append(" select distinct A.DATEON AS DAY ,A.ATT_IN AS FIN,A.ATT_OUT AS FOUT,  ");
			Emp_DOJ.append(" A.NET_HOURS AS PERDAY, if(A.DATEON=ifnull(C.LEAVEON,'0000-00-00'),C.LEAVE_TYPE, A.DAYTYPE) AS DAYTYPE, ");
			Emp_DOJ.append(" IF(ifnull(D.FLAG,'No Request')='A','APPROVED',if(ifnull(D.FLAG,'No Request')='R','Reject',if(ifnull(D.FLAG,'No Request')='P','Processed','No Request'))) STATUS, ");
			Emp_DOJ.append(" D.FLAG, ");
			Emp_DOJ.append(" if(A.DATEON=ifnull(C.LEAVEON,'0000-00-00'),concat(C.LEAVE_TYPE,' / ',C.LEAVE_COUNT,' DAY'), A.DAYTYPE) AS DAYTYPE2, ");
			Emp_DOJ.append(" ifnull(A.SHIFT,'General') AS SHIFT, ");
			Emp_DOJ.append(" CASE ");
			Emp_DOJ.append(" WHEN A.SHIFT='Morning Shift' THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Second Shift'  THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Night Shift'   THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
			Emp_DOJ.append(" WHEN A.SHIFT='General'       THEN   TIMEDIFF(A.NET_HOURS,'09:00:00') ");
			Emp_DOJ.append(" ELSE concat('#',TIMEDIFF(A.NET_HOURS,'09:00:00')) ");
			Emp_DOJ.append(" END AS DIFFHOURS, ");
			Emp_DOJ.append(" CASE ");
			Emp_DOJ.append(" WHEN A.SHIFT='Morning Shift' THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Second Shift'  THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" WHEN A.SHIFT='Night Shift'   THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" WHEN A.SHIFT='General'       THEN    IF(TIMEDIFF(A.NET_HOURS,'09:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" ELSE  IF(TIMEDIFF(A.NET_HOURS,'09:00:00')<'00:00:00','true','false') ");
			Emp_DOJ.append(" END AS DEDHOURS , ");
			Emp_DOJ.append(" date_format(A.DATEON,'%d-%m-%Y') AS DAYVIEW , date_format(now(),'%d-%m-%Y') As Newdate from test_mum.tbl_att_leave_in_out_status_report A ");
			Emp_DOJ.append(" join hclhrm_prod.tbl_employee_primary B ");
			Emp_DOJ.append(" ON B.employeesequenceno=A.employeeid and A.PAYPERIOD=0 ");
			Emp_DOJ.append(" left join hclhrm_prod_others.tbl_emp_leave_report C ");
			Emp_DOJ.append(" ON C.employeeid=B.employeesequenceno AND C.LEAVEON=A.DATEON AND C.MANAGER_STATUS IN ('A','P') and C.STATUS=1001  ");
			Emp_DOJ.append(" left join hclhrm_prod_others.tbl_emp_attn_req D ON D.EMPLOYEEID=B.employeesequenceno AND D.REQ_DATE=A.DATEON AND ");
			Emp_DOJ.append(" D.REQ_TYPE='AR' AND D.STATUS=1001 ");
			Emp_DOJ.append(" where B.employeesequenceno in("+username+") ");
		    Emp_DOJ.append(" and a.DATEON BETWEEN  '"+UserMap.get("FROMDATE")+"' ");
			Emp_DOJ.append(" AND '"+UserMap.get("TODATE")+"' ");
			
			
			//Emp_DOJ.append(" and a.DATEON BETWEEN CONCAT(YEAR(NOW()),'-"+Month+"','-01') ");
			
			//Emp_DOJ.append(" AND LAST_DAY(CONCAT(YEAR(NOW()),'-"+Month+"','-01')) ");
			
			Emp_DOJ.append(" order by a.DATEON ");
			
			
			
			HoursData.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,");
			HoursData.append(" nettime,status,ifnull(remark,'Personal') as remark FROM `procedure`.tbl_employee_logs_allbu ");
			HoursData.append(" where employeeid="+username+" and ");
			HoursData.append(" transactiondate between '"+UserMap.get("FROMDATE")+"' and  '"+UserMap.get("TODATE")+"'   and time(nettime)>time('00:10:00') ");
			
			
		
		}
		System.out.println(HoursData.toString()+"Emp_DOJ:1"+Emp_DOJ);
		
		
		Res=null;
		try {
			Res=(ResultSet)DataObj.FetchData(HoursData.toString(), "Hours Data", Res ,conn);
			while(Res.next()){
				UserMap.put(Res.getString("transactiondate")+"_HOURS", Res.getString("transactiondate"));
				if(Res.getString("status").equalsIgnoreCase("1001")){
					UserMap.put(Res.getString("transactiondate")+"_HOURSF", Res.getString("transactiondate"));
				}
			}  
		}catch(Exception Er){
			System.out.println("Exception At Er::"+Er);
		}
		
		
	/*}else{
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
				
			
		
				
				Emp_DOJ.append(" select distinct A.DATEON AS DAY ,A.ATT_IN AS FIN,A.ATT_OUT AS FOUT,  ");
				Emp_DOJ.append(" A.NET_HOURS AS PERDAY, if(A.DATEON=ifnull(C.LEAVEON,'0000-00-00'),C.LEAVE_TYPE, A.DAYTYPE) AS DAYTYPE, ");
				Emp_DOJ.append(" IF(ifnull(D.FLAG,'No Request')='A','APPROVED',if(ifnull(D.FLAG,'No Request')='R','Reject','No Request')) STATUS, ");
				Emp_DOJ.append(" D.FLAG, ");
				Emp_DOJ.append(" if(A.DATEON=ifnull(C.LEAVEON,'0000-00-00'),concat(C.LEAVE_TYPE,' / ',C.LEAVE_COUNT,' DAY'), A.DAYTYPE) AS DAYTYPE2, ");
				Emp_DOJ.append(" ifnull(A.SHIFT,'General') AS SHIFT, ");
				Emp_DOJ.append(" CASE ");
				Emp_DOJ.append(" WHEN A.SHIFT='Morning Shift' THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
				Emp_DOJ.append(" WHEN A.SHIFT='Second Shift'  THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
				Emp_DOJ.append(" WHEN A.SHIFT='Night Shift'   THEN   TIMEDIFF(A.NET_HOURS,'08:00:00') ");
				Emp_DOJ.append(" WHEN A.SHIFT='General'       THEN   TIMEDIFF(A.NET_HOURS,'09:00:00') ");
				Emp_DOJ.append(" ELSE concat('#',TIMEDIFF(A.NET_HOURS,'09:00:00')) ");
				Emp_DOJ.append(" END AS DIFFHOURS, ");
				Emp_DOJ.append(" CASE ");
				Emp_DOJ.append(" WHEN A.SHIFT='Morning Shift' THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
				Emp_DOJ.append(" WHEN A.SHIFT='Second Shift'  THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
				Emp_DOJ.append(" WHEN A.SHIFT='Night Shift'   THEN    IF(TIMEDIFF(A.NET_HOURS,'08:00:00')<'00:00:00','true','false') ");
				Emp_DOJ.append(" WHEN A.SHIFT='General'       THEN    IF(TIMEDIFF(A.NET_HOURS,'09:00:00')<'00:00:00','true','false') ");
				Emp_DOJ.append(" ELSE  IF(TIMEDIFF(A.NET_HOURS,'09:00:00')<'00:00:00','true','false') ");
				Emp_DOJ.append(" END AS DEDHOURS ");
				Emp_DOJ.append(" from test_mum.tbl_att_leave_in_out_status_report A ");
				Emp_DOJ.append(" join hclhrm_prod.tbl_employee_primary B ");
				Emp_DOJ.append(" ON B.employeesequenceno=A.employeeid ");
				Emp_DOJ.append(" left join hclhrm_prod_others.tbl_emp_leave_report C ");
				Emp_DOJ.append(" ON C.employeeid=B.employeesequenceno AND C.LEAVEON=A.DATEON AND C.MANAGER_STATUS IN ('A','P') and C.STATUS=1001 ");
				Emp_DOJ.append(" left join hclhrm_prod_others.tbl_emp_attn_req D ON D.EMPLOYEEID=B.employeesequenceno AND D.REQ_DATE=A.DATEON AND ");
				Emp_DOJ.append(" D.REQ_TYPE='AR' ");
				Emp_DOJ.append(" where B.employeesequenceno in("+username+") ");
			     Emp_DOJ.append(" and a.DATEON BETWEEN  '"+UserMap.get("FROMDATE")+"' ");
				 Emp_DOJ.append(" AND '"+UserMap.get("TODATE")+"' ");
				
				
		
			}
		System.out.println("Emp_DOJ:::---------->"+Emp_DOJ.toString());	
		
	}*/
		
		
		
		
		
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
			  //ATT_MONTHS.put("currmonth", "Current Month");
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
                          
						
						/*session.setAttribute("EMPBU", Res.getString(2));
						session.setAttribute("EMPBUID", Res.getString(4));
						A.DATEON AS DAY ,A.ATT_IN AS FIN,A.ATT_OUT AS FOUT
						PERDAY,DAYTYPE,STATUS, DAYTYPE2, SHIFT,DIFFHOURS
						DEDHOURS --Flag/true or False*/
						Doj.put("DATE" , Res.getString("DAYVIEW")); 
						Doj.put("FIN" , Res.getString("FIN"));
						Doj.put("FOUT" ,Res.getString("FOUT"));
						Doj.put("PERDAY" , Res.getString("PERDAY"));
						Doj.put("INNER" , Res.getString("DAY")+"#"+Res.getString("FIN")+"#"+Res.getString("FOUT")+"#"+Res.getString("PERDAY"));
						Doj.put("DAYTYPE" , Res.getString("DAYTYPE"));
						Doj.put("DAREQ",Res.getString("STATUS"));
						
						Doj.put("DAFH" , "none");
						
						Doj.put("DAFMAIN" , "false");
						
					try{
						
						Doj.put("LESSHOURS",Res.getString("DIFFHOURS").replace("#", ""));
						
						
					}catch(Exception err){
						Doj.put("LESSHOURS","00:00:00");
						System.out.println("DIFFHOURS ~~~" +err);
					}
					 if(Res.getString("FIN").equalsIgnoreCase("00:00:00") && Res.getString("FOUT").equalsIgnoreCase("00:00:00")){
					
						 Doj.put("LESSHOURS","00:00:00");
					 }
					 
					 Doj.put("SHIFT",Res.getString("SHIFT"));
					 if(Res.getString("SHIFT")!=null && Res.getString("SHIFT").equalsIgnoreCase("Morning Shift")){
					 Doj.put("SHIFT","A");
					 }else if(Res.getString("SHIFT")!=null && Res.getString("SHIFT").equalsIgnoreCase("Second Shift")){
						 Doj.put("SHIFT","B");
					 }else if(Res.getString("SHIFT")!=null && Res.getString("SHIFT").equalsIgnoreCase("Night Shift")){
						 Doj.put("SHIFT","C");
					 }else{
						 Doj.put("SHIFT","G");
					 }
						/*WDAY
						WOFF
						No Request
						00:00:00	00:00:00
						HL*/
					 boolean flagRequest=false;
				       
					try{
                     flagRequest=RequestEnabled(Res.getString("DAY").toString(),UserMap.get("FROMDATE").toString(),UserMap.get("TODATE").toString());
					}catch(Exception err){
						
					}
					if((Res.getString("DAYTYPE").equalsIgnoreCase("WDAY") ||Res.getString("DAYTYPE").equalsIgnoreCase("WOFF")
							||Res.getString("DAYTYPE").equalsIgnoreCase("HL"))	&& (Res.getString("STATUS").equalsIgnoreCase("No Request") 
							&& !Res.getString("FIN").equalsIgnoreCase("00:00:00") && !Res.getString("FOUT").equalsIgnoreCase("00:00:00") 
							&& Res.getString("DEDHOURS").equalsIgnoreCase("true") )){
						
						
						if(currMonthflg && ATT_FLAG.equalsIgnoreCase("DATES") && !Res.getString("DAYVIEW").toString().equalsIgnoreCase(""+Res.getString("Newdate").toString()+"") ){
							
						  Doj.put("DAF" , " ");
						  
						}else{
							
							 Doj.put("DAF" , "none");
						}
						 if(UserMap.get(Res.getString("DAY"))!=null && (UserMap.get(Res.getString("DAY")).toString().equalsIgnoreCase(Res.getString("DAY")))
								  ){
						     Doj.put("DAF" , " ");
						     Doj.put("DAFMAIN" , "true");
						 }else{
							 Doj.put("DAF" , "none");
						 }
				// ****************** Start Hours Data condition *******************************		 
				    if(UserMap.get(Res.getString("DAY")+"_HOURS")!=null && (UserMap.get(Res.getString("DAY")+"_HOURS").toString().equalsIgnoreCase(Res.getString("DAY")))){
				    	Doj.put("DAFH" , "");	
				    }
				// ****************** End Hours Data condition *******************************			 
					}else{
						 Doj.put("DAF" , "none");
					}
					// ****************** Start Hours Data condition *******************************		 
				    if(UserMap.get(Res.getString("DAY")+"_HOURS")!=null && (UserMap.get(Res.getString("DAY")+"_HOURS").toString().equalsIgnoreCase(Res.getString("DAY")))
				     && UserMap.get(Res.getString("DAY"))!=null && (UserMap.get(Res.getString("DAY")).toString().equalsIgnoreCase(Res.getString("DAY"))
				    && Res.getString("STATUS").equalsIgnoreCase("No Request") )
				    		){
				    	Doj.put("DAFH" , "");
				    if(UserMap.get(Res.getString("DAY")+"_HOURSF")!=null && (UserMap.get(Res.getString("DAY")+"_HOURSF").toString().equalsIgnoreCase(Res.getString("DAY")))){	
				    	Doj.put("DAF" , "");
				    }
				    
				    	
				    }
					if(UserMap.get(Res.getString("DAY")+"_HOURS")!=null && (UserMap.get(Res.getString("DAY")+"_HOURS").toString().equalsIgnoreCase(Res.getString("DAY")))){
						
						Doj.put("DAFH" , "");
					}
				// ****************** End Hours Data condition *******************************		
					
/*						if(Month.equalsIgnoreCase("currmonth")){
							Doj.put("INNER" , Res.getString("DAYSP")+"#"+Res.getString(2)+"#"+Res.getString(3)+"#"+Res.getString(4));
							Doj.put("DAYTYPE" , Res.getString(5));
						}
*/						
					
					/*System.out.println("Res.getString(1)"+Res.getString(1));
					if(Att_Req.get(Res.getString(1))!=null){
							
							Doj.put("DAREQ" ,  Att_Req.get(Res.getString(1)).toString());
							
							Doj.put("DAF" , "none");
							if((Res.getString(1).toString()).equalsIgnoreCase(strTime)){
								 Doj.put("DAF" , "none");
								}else{
									 Doj.put("DAF" , " ");
								}
							
						}
						else{
							Doj.put("DAREQ" ,  "No Request");
						}
						
						*/

						//Doj.put("DAYTYPE" , Res.getString(5));
						//Doj.put("MOBILE" , Res.getString(6));

					}
					 // this is adding for new DAF Button Display none
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
				Doj.put("LESSHOURS","00:00:00");
				 Doj.put("SHIFT","  ");
				

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
			request.getRequestDispatcher("HHCL_ATTENDANCE_ASSAM.jsp").forward(request, response);  
		}
	}  
	
  public static boolean RequestEnabled(String attnDate,String fromPayperiod,String toPayperiod){

	  
	  
	  
      Date d1 = null;
      Date d2 = null;
      Date d3=null;
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  boolean isBetween=false;
      try {
          d1 = format.parse(fromPayperiod);
          d2 = format.parse(toPayperiod);
          d3= format.parse(attnDate);
   }catch(Exception e){
          e.printStackTrace();
   }
   
      
    		  if(d3.compareTo(d1) >= 0 && d3.compareTo(d2) <= 0){
    	          isBetween=true;
    	   }
      
   /*if(d3.after(d1) && d3.before(d2)){
          isBetween=true;
   }*/
   
    		  System.out.println(attnDate +"~" +fromPayperiod +"~"+ toPayperiod+"~"+isBetween);
	  
	  return isBetween;
  }
}
