package com.hhcdesk.service;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
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
import java.util.Enumeration;
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

/*import net.sf.jasperreports.compilers.JavaScriptEvaluator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
*/
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.jdbc.Statement;
import com.bonecp.pool.DBUtil_leave;
import com.hhcdesk.EventCapture.MySessionListener;
import com.hhcdesk.db.*;

/**
 * Servlet implementation class LoginServlet
 * Hetero Health Care Limited
 * By Java HHCL Java Tem 
 * Written By Venu
 */
//@WebServlet("/User_Auth")
public class Attendance_assam_hours extends HttpServlet {

	public Attendance_assam_hours() {
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	@SuppressWarnings("static-access")
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
		Map EMAILDATA=new HashMap();
		String ccemail=null;
		PrintWriter out = response.getWriter();
		Map UserMap=new HashMap();
        String insertMessage="";
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
		String Mode=null;
		String EMAIL=null;
		JSONArray values;
		values = new JSONArray();

		JSONArray values_main;
		values_main = new JSONArray();
		
		String Routing=request.getParameter("Routing");
		
		
		String DATA=request.getParameter("DATA");
		
		System.out.println("DATA ::"+DATA);
		
		String Month="currmonth";
		
		String HR_MAIL=" ",EMP_MAIL=" ";
	      try{
	        EMAILDATA=(Map)session.getAttribute("EMAILDATA_MAP");
	        HR_MAIL=EMAILDATA.get("BULOC").toString();
	        EMP_MAIL=EMAILDATA.get("EMPEMAIL").toString();
	      }catch(Exception Err){
	    	  System.out.println("Email fetching Error" +Err);
	      }
//------------------------------------------------------------------------------------------------------------------
		
		//System.out.println("---"+request.getParameter("DATA"));
		
		/*  StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		   // String data = buffer.toString();
		    System.out.println("---"+buffer.toString());
		    
		    
		    
		    String str, wholeStr = "";
			try {
				BufferedReader br = request.getReader();
				while ((str = br.readLine()) != null) {
					wholeStr += str;
				}
				
			} catch (Exception e) {
				//logger.error("", e);
			}
		    */
		    
		
		/*Enumeration enum1 = request.getHeaderNames();
	       while (enum1.hasMoreElements()) {
	       String headerName = (String) enum1.nextElement();
	       String headerValue = request.getHeader(headerName);
	       System.out.println("formfieldss-----"+headerName+":::"+headerValue);
	       //out.print("<b>"+headerName + "</b>: ");
	       //out.println(headerValue + "<br>");
	       }
	       System.out.println("-----------------------hema--------------------------------");*/
		//----------------------------------------------------------------------
		  /*  Enumeration data=request.getParameterNames();
		    
		    while(data.hasMoreElements()){
		    	//System.out.println("Enumeration:::" +data.nextElement());
		    	System.out.println("Enumeration:::" +request.getParameter(""+data.nextElement()+""));
		    }
		
		System.out.println("Routing___Attendance_ ::"+Routing);*/
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
		String Data=null;
		String Data1=null;
		
		if(Routing!=null&&Routing.equalsIgnoreCase("ATTENDANCE_BIO_DATES")){
			
			Routing="ATTENDANCE_BIO";
			
		}
		String date=null,Req_message=null,subject=null,random=null,Atten_Req_Message="Success Fully Submit Your Request";
		
		boolean pHours=false;
		
		if(Routing!=null&&Routing.equalsIgnoreCase("ATT_HOURS")){
			
			date=request.getParameter("ATTDATE");
			Mode=request.getParameter("MODE");
			insertMessage="";
			//Req_message=request.getParameter("message");
			//subject=request.getParameter("Subject");
			//random=request.getParameter("RanDm");
		}
         if(Routing!=null&&Routing.equalsIgnoreCase("ATT_HOURS") || Routing.equalsIgnoreCase("ATT_HOURS_INSERT") ){
			
				Data=request.getParameter("DATA");
				Mode=request.getParameter("MODE");
				EMAIL=request.getParameter("EMAIL");
				ccemail=request.getParameter("ccemail");
				insertMessage="Your request not processed please try again ..!";
			//Req_message=request.getParameter("message");
			//subject=request.getParameter("Subject");
			//random=request.getParameter("RanDm");
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
		
		JSONObject  Doj_main= new JSONObject();
		
		JSONObject  Doj_main_butt= new JSONObject();
		
		Doj_main_butt.put("MAINBUTTON", "false");
		
		
		ResourceBundle bundle_info =(ResourceBundle)(c.getAttribute("bundle"));
		String EnableDays="4";
	try{
		EnableDays=bundle_info.getString("AssamEnableDays");
	}catch(Exception erd){
		System.out.println("EnableDays Assam ::"+EnableDays);
	}
	try {
		//conn =dataSource.getConnection();
		
		//conn=DBUtil.getConnection();
		conn=DBUtil_leave.getInstance().getConnection();
		//conn=(java.sql.Connection)session.getAttribute("ConnectionObj");
	} catch (Exception e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
	}
	   System.out.println("Connection " +conn);	  
		String _FAIL_PAGE="/NewJoinee_Faild.jsp";
		String _SUCCESS_PAGE=null;
		String username=request.getParameter("username");  
		String password=request.getParameter("pwd"); 

		if(Routing!=null && Routing.equalsIgnoreCase("ATT_HOURS") || Routing.equalsIgnoreCase("ATT_HOURS_INSERT")){
			username=(String)session.getAttribute("EMP_ID");
			password=(String)session.getAttribute("EMP_PASSWORD");
		}

  // ************** Insertion Block Start **********************
		 if(Routing.equalsIgnoreCase("ATT_HOURS_INSERT")){
			   
			Random rand = new Random();
			int nRand = rand.nextInt(90000) + 10000;
				
			 java.sql.PreparedStatement pstmt=null;  
			 ps=null;  
			 String SQL = "update `procedure`.tbl_employee_logs_allbu set STATUS=?,MGRSTATUS=?,REMARK=? ,MESSAGE=?,EMAIL='"+EMAIL+"' "
			 		+ "where status=1001 and TRANSACTIONDATE=? and employeeid=? and  time(TRANSACTIONTIME_IN)=? and time(TRANSACTIONTIME_OUT)=?" ;
			 
			 try {
				ps=conn.prepareStatement("INSERT INTO hclhrm_prod_others.tbl_emp_attn_req (EMPLOYEEID,SUBJECT,REQ_DATE,MESSAGE,RANDOMID,FROM_DATE,TO_DATE,TOTA_HOURS,TO_EMAIL,CC_EMAIL,STATUS,HR_CC_MAIL,EMPMAIL,Mail_status) VALUES (?,?,?,?,?,?,?,?,?,?,1004,?,?,'Y')");
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				
		// Create PrepareStatement object TRANSACTIONTIME_IN,TRANSACTIONTIME_OUT
		try {
			 pstmt = conn.prepareStatement(SQL);
			 conn.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     try {
	     			JSONParser parser = new JSONParser();
			        Object obj = parser.parse(Data);
			        JSONObject jsonObject;
				    JSONArray cars = (JSONArray) obj;
				    System.out.println("carsss"+cars.size());
				  for(int i=0;i<cars.size();i++){
				    jsonObject =  (JSONObject) cars.get(i);
				    String REQ_TYPE = (String) jsonObject.get("REQ_TYPE");
				    String KEYID = (String) jsonObject.get("KEYID");
				    String REQ_MESSAGE = (String) jsonObject.get("REQ_MESSAGE");
			        String []KEYID_DATA=KEYID.split("~");
				    Routing="ATT_HOURS";
				   // Mode="FETCH";
				    date=KEYID_DATA[1];
				    pstmt.setString(1,"1002");
				    pstmt.setString(2,"P");
				    pstmt.setString(3,REQ_TYPE);
				    pstmt.setString(4,REQ_MESSAGE);
				    pstmt.setString(5,date);
				    pstmt.setInt(6,Integer.parseInt(username));
				    pstmt.setString(7, KEYID_DATA[2]);
				    pstmt.setString(8, KEYID_DATA[3]);
				    pstmt.addBatch();
				    
				    ps.setString(1,username);
					ps.setString(2, "In Between Hours permission ("+REQ_TYPE+")");
					ps.setString(3,date);
					ps.setString(4,REQ_MESSAGE);
					ps.setInt(5,nRand);
					ps.setString(6,KEYID_DATA[3]);
					ps.setString(7,KEYID_DATA[2]);
					String TIMEDATA="00:00:00";
					try{
					 TIMEDATA=(String) session.getAttribute(username+"$"+KEYID_DATA[2]+"$"+KEYID_DATA[3]);
					}catch(Exception err){}
					ps.setString(8 , TIMEDATA);
					ps.setString(9,EMAIL);
					ps.setString(10,ccemail);
					ps.setString(11,HR_MAIL); //
					ps.setString(12,EMP_MAIL);//
					
					ps.addBatch();
				    
				    
				    
				  System.out.println(i+":"+REQ_TYPE+"~"+KEYID+"~"+REQ_MESSAGE);
				  }
				  System.out.println("pstmt:: "+pstmt.toString());
				  int ExucuteRows[]=pstmt.executeBatch();
				  int count[] = ps.executeBatch();
				  System.out.println("pstmt:: "+ExucuteRows.length);
				  if(ExucuteRows.length>0 && count.length>0){
					  insertMessage="Your request processed successfully ...!";
					  conn.commit();
				  }else{
					  insertMessage="Your request not processed please try again  ...!";
					  conn.rollback();
				  }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception err){
				err.printStackTrace();
				
			}
			   
			   
        
			   
		 }
// ************** Insertion Block End **********************
		
	
		   if(Routing.equalsIgnoreCase("ATT_HOURS")){
			
			
			
			/*Emp_DOJ.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,nettime,status FROM `procedure`.tbl_employee_logs_allbu ");
			Emp_DOJ.append(" where employeeid="+username+" and transactiondate=date_format('"+date+"','%Y-%m-%d') and time(nettime)>time('00:10:00') ");*/
			if(Mode!=null && Mode.equalsIgnoreCase("FETCH")){
				
			Emp_DOJ.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,nettime,status,ifnull(remark,'Personal') as remark ,if(MGRSTATUS='N','No request', if(MGRSTATUS='P','Processed',if(MGRSTATUS='A','Approved','Reject'))) MGRSTATUS,message FROM `procedure`.tbl_employee_logs_allbu ");
			Emp_DOJ.append(" where employeeid="+username+" and transactiondate=date_format(STR_TO_DATE('"+date+"','%d-%m-%Y'),'%Y-%m-%d') and time(nettime)>time('00:10:00')  ");
			
			}else if(Mode!=null && Mode.equalsIgnoreCase("VIEW")){
				
				Emp_DOJ.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,nettime,status,ifnull(remark,'Personal') as remark,if(MGRSTATUS='N','No request', if(MGRSTATUS='P','Processed',if(MGRSTATUS='A','Approved','Reject')))  MGRSTATUS,message FROM `procedure`.tbl_employee_logs_allbu ");
				Emp_DOJ.append(" where employeeid="+username+" and transactiondate=date_format(STR_TO_DATE('"+date+"','%d-%m-%Y'),'%Y-%m-%d') and time(nettime)>time('00:10:00') ");
				
			}else if( Mode!=null && Mode.equalsIgnoreCase("INSERT")){
			  
				Emp_DOJ.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,nettime,status,ifnull(remark,'Personal') as remark,if(MGRSTATUS='N','No request', if(MGRSTATUS='P','Processed',if(MGRSTATUS='A','Approved','Reject'))) MGRSTATUS,message FROM `procedure`.tbl_employee_logs_allbu ");
				Emp_DOJ.append(" where employeeid="+username+" and transactiondate=date_format(STR_TO_DATE('"+date+"','%Y-%m-%d'),'%Y-%m-%d') and time(nettime)>time('00:10:00')  ");
				
				
			}
			
			
			
			
		/*	Emp_DOJ.append(" select employeeid,transactiondate,time(transactiontime_in) as transactiontime_in ,time(transactiontime_out) as transactiontime_out ,nettime,status,ifnull(remark,'Personal') as remark FROM `procedure`.tbl_employee_logs_allbu ");
			Emp_DOJ.append(" where employeeid="+username+" and transactiondate=date_format('"+date+"','%Y-%m-%d') and time(nettime)>time('00:10:00') ");*/
			
			
			
		System.out.println(Emp_DOJ .toString());
		
		
        Res=null;
	    
		try {
			 Res=(ResultSet)DataObj.FetchData(Emp_DOJ.toString(), "EMPLOYEE_ATTENDANCELIST", Res ,conn);
			while(Res.next()){
				
				
				Doj= new JSONObject();
				
				ATT_MONTHS.put(Res.getString(1), Res.getString(2));
				
				Doj.put("employeeid" , Res.getString("employeeid"));
				Doj.put("transactiondate" , Res.getString("transactiondate"));
				Doj.put("transactiontime_in" , Res.getString("transactiontime_in"));
				Doj.put("transactiontime_out" , Res.getString("transactiontime_out"));
				Doj.put("nettime" , Res.getString("nettime"));
				Doj.put("Note" , Res.getString("MGRSTATUS"));
				
				Doj.put("MESSAGE_USER" , Res.getString("message"));
				Doj.put("CheckBox_TY" ,"true");
				
				session.setAttribute(Res.getString("employeeid")+"$"+Res.getString("transactiontime_in")+"$"+Res.getString("transactiontime_out"), Res.getString("nettime"));
				
				
				
				
				
				if(Res.getString("remark").equalsIgnoreCase("Official")){
				Doj.put("Official" , "true");
				Doj.put("Personal" , "false");
				
				}
				
				if(Res.getString("remark").equalsIgnoreCase("Personal")){
					Doj.put("Official" , "false");
					Doj.put("Personal" , "true");
					
					}
				Doj.put("CKID" , Res.getString("employeeid")+"~"+Res.getString("transactiondate")+"~"+Res.getString("transactiontime_in")+"~"+Res.getString("transactiontime_out"));
				
				if(Res.getString("status").equalsIgnoreCase("1001")){
					
					Doj.put("CheckBox" ,"true");
					
					Doj.put("CheckBox_TY" ,"false");
					//Doj.put("Note" ,"No request");
					pHours=true;
					//Doj_main_butt.put("MAINBUTTON", "false");
				}else{
					Doj.put("CheckBox" ,"false");
					Doj.put("CheckBox" ,"Applied");
					Doj.put("CheckBox_TY" ,"true");
				}
				Doj.put("CheckBoxStatus" , Res.getString("status"));
				values.add(Doj);
			}  
			Doj_main.put("HDATA", values);
			//values_main.add(Doj_main);
		}catch(Exception Er){
			System.out.println("Exception At Er::"+Er);
		}
		
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}  // if close;
	
		   
		  
	   if(pHours){
		   Doj_main.put("MAINBUTTON", "false");
		   
		   Doj_main.put("insertMessage", insertMessage);
		  
		   System.out.println(Doj_main.toString());
		   
		out.write(""+Doj_main.toString()+""); 
	   }else{
		   Doj_main.put("MAINBUTTON", "true");
		   Doj_main.put("insertMessage", insertMessage);
		   System.out.println(Doj_main.toString());
		   out.write(""+Doj_main.toString()+"");  
	   }
		//out.print(values);
		
	  }
		
		
 
}
