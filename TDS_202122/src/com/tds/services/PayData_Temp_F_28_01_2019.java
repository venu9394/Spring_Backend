package com.tds.services;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;

@SuppressWarnings({ "serial", "unused" })

public class PayData_Temp_F_28_01_2019 extends HttpServlet {
	//Connection connection=null;
	//Statement statement=null;
	/*public void init() throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Driver loaded...!");
		try {
			connection=DriverManager.getConnection("jdbc:mysql://192.168.30.105:3306/","hcluser","hcluser!23");
			//connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/","hcluser","hcluser!23");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection Established....!");
	}
	*/
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request,response);
	
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		Connection connection=null;
		Statement statement=null;
		final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		
		 long millis=System.currentTimeMillis();  
	       java.sql.Date date=new java.sql.Date(millis);
	       HttpSession session=request.getSession(true);
	       System.out.println(sdf2.format(date)+"~~Date::~~"+date);  
	       
	       
	       
	      String username=(String)session.getAttribute("EMP_ID");
	      
	      String Date_Path=null;
	      System.out.println("username::"+username);
	      if(username==null){
	    	  
	    	  session.invalidate();
	    	  
	      }else{
	    	  //session.setAttribute("SUBPATH", username.concat("_"+sdf2.format(date)+"_"+new Timestamp(date.getTime())+""));
	    	  
	    	  session.setAttribute("SUBPATH", username.concat("_"+sdf2.format(date)));
	    	  
	    	  Date_Path=session.getAttribute("SUBPATH").toString();
	      }
			
	      try {
				//conn =dataSource.getConnection();
				connection=(java.sql.Connection)session.getAttribute("ConnectionObj");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
			}
			
			
		TdsForeCast  ForCast=new TdsForeCast();
		
		Form16B  From16b=new Form16B();	
		
		ArrayList components=new ArrayList();
		ArrayList emppayperiods=new ArrayList();
		ArrayList empcomponents=new ArrayList();
		
		
		ArrayList empids=new ArrayList();
		ArrayList taxempids=new ArrayList();
		
		
		ArrayList empids_main=new ArrayList();
		ArrayList taxempids_main=new ArrayList();
		
		
		ArrayList payperiod=new ArrayList();
		ArrayList BulkList=new ArrayList();
		ArrayList subList=new ArrayList();
		ArrayList subList2=new ArrayList();
		Map empmap=new HashMap();
	
		Map Tax_dec_Map=new HashMap();

		Map ComMod=new HashMap();
		Map Qry2=new HashMap();
		Map Basic=new HashMap();
		Map Gross=new HashMap();
		Map TaxSections=new HashMap();
		Map FinalComponents=new HashMap();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Map TaxException=new HashMap();
		//HttpSession session=request.getSession(true);
		
		StringBuffer Bu_PayID_Buff=new StringBuffer();
		
		String companyid=null;  // Bu unit ID not required any changes
		
		companyid=(String)request.getParameter("BusinessUnit"); // By Bu ID
		
		String Emp_Seq_Number=(String)request.getParameter("EmpId"); // By Emp ID
		//9,10,11
	//String FromPeriod="201609";
	   String FromPeriod="201804";
	   
	  // String ToPeriod="201903";
	   
	   String ToPeriod="201810";
	   
	   int currentPayperiod=201810; 
	   
	   int ClosedPayperiod=201810; 
	   
	   // need to change current payperiod every time every month
	   String TaxStartMonth="2018-04-01";
	   String TaxEndMonth="2018-10-31";
		//String ToPeriod="201608";   //1st Sep onwords Hetero Health Care(transfers)
		
	 	//String TaxStartMonth="2016-09-01";
		
		//String TaxEndMonth="2016-07-30";
		//String TaxEndMonth="2016-08-31";
	   String DwDLocation="ADM";
	  // String TaxStartMonth="2017-04-01";
	  // String TaxEndMonth="2016-08-31";
	   
	  //String TaxEndMonth="2018-03-31";
		
       String FiyYear="2018-2019";
       String ProposedMonths="0";
	   String MaxPayPeriod="0";
		
	  int Rem_Months=0;
      ArrayList list_BU_la=new ArrayList();
      
   
      if(companyid!=null && companyid.equalsIgnoreCase("HYD")){
    	  DwDLocation="HYD";
        list_BU_la.add(10);
        list_BU_la.add(11);
        list_BU_la.add(14);
        list_BU_la.add(15);
        list_BU_la.add(16);
        list_BU_la.add(17);
        list_BU_la.add(18);
        list_BU_la.add(19);
        list_BU_la.add(20);
        list_BU_la.add(21);
        list_BU_la.add(22);
        list_BU_la.add(24);
        
      //  list_BU_la.add(26); 
        
        list_BU_la.add(27);
        list_BU_la.add(28);
        list_BU_la.add(29);
        
      }else if(companyid!=null && companyid.equalsIgnoreCase("MUM")){
    	  DwDLocation="MUM";
      list_BU_la.add(3);
      list_BU_la.add(5);
      list_BU_la.add(6);
      list_BU_la.add(7);
      list_BU_la.add(8);
      list_BU_la.add(12);
      list_BU_la.add(13);
      list_BU_la.add(23);
    	  
	  }else if(companyid!=null && companyid.equalsIgnoreCase("ADM")){
		  DwDLocation="ADM";
		    list_BU_la.add(10);
	        list_BU_la.add(11);
	        list_BU_la.add(14);
	        list_BU_la.add(15);
	        list_BU_la.add(16);
	        list_BU_la.add(17);
	        list_BU_la.add(18);
	        list_BU_la.add(19);
	        list_BU_la.add(20);
	        list_BU_la.add(21);
	        list_BU_la.add(22);
	        list_BU_la.add(24);
	        // Mum
	        list_BU_la.add(3);
	        list_BU_la.add(5);
	        list_BU_la.add(6);
	        list_BU_la.add(7);
	        list_BU_la.add(8);
	        list_BU_la.add(12);
	        list_BU_la.add(13);
	        list_BU_la.add(23);  
	      
	        //list_BU_la.add(26);  no employess
	        
	        list_BU_la.add(26);  
	        list_BU_la.add(27);
	        list_BU_la.add(28);
	        list_BU_la.add(29);
	        
	        
	  }else if(companyid!=null && companyid.equalsIgnoreCase("EMPID")){
		    DwDLocation="ADM";
		    list_BU_la.add("10001");
		  
	  }else{
		    DwDLocation="ADM";
		    list_BU_la.add(companyid);
		  
	  }
        
    /*  list_BU_la.add(3);
      list_BU_la.add(5);
      list_BU_la.add(6);
      list_BU_la.add(7);
      list_BU_la.add(8);
      list_BU_la.add(12);
      list_BU_la.add(13);
      list_BU_la.add(23);*/
      
      /*list_BU_la.add(3); */
      /*list_BU_la.add(5); */
      /*list_BU_la.add(6); */
      /*list_BU_la.add(7); */
      /*list_BU_la.add(8); */
      /*list_BU_la.add(12);*/
      /*list_BU_la.add(13);*/
      /*list_BU_la.add(23);*/
      
     
      
      
     // String GetEMPID="20314";
      boolean empflag=false,allowWhile=false;
      GetDbData DataObj=new GetDbData();
      StringBuffer Employee_ID_Seq_Bulk=new StringBuffer();
      ResultSet rs = null;
      RequestDispatcher rd=null;
      
      if(companyid!=null && companyid.equalsIgnoreCase("EMPID")){
	    	
	    	
	    	Employee_ID_Seq_Bulk.append(" select  count(*) from    ");
			Employee_ID_Seq_Bulk.append(" hclhrm_prod.tbl_employee_payperiod_details b ");
			Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_primary a on ");
			Employee_ID_Seq_Bulk.append(" a.employeeid=b.employeeid   ");
			//Employee_ID_Seq_Bulk.append(" and b.businessunitid=a.companyid ");
			Employee_ID_Seq_Bulk.append(" where  a.employeesequenceno in ("+Emp_Seq_Number+") and  ");
			Employee_ID_Seq_Bulk.append(" b.payperiod between "+FromPeriod+" and "+ToPeriod+" ");
			Employee_ID_Seq_Bulk.append(" group by a.employeeid limit 1");
			
			
		    
			
			rs=null;
				try {
					rs=(ResultSet)DataObj.getBasic(Employee_ID_Seq_Bulk.toString(), "buunit", rs ,connection);
					if(rs.next()){
						
						System.out.println("2");
						
						if(rs.getInt(1)>0){
						  empflag=true;
						  allowWhile=true;
						}
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	
	    }else{
	    	
	    	System.out.println("1");
	    	allowWhile=true;
	    	empflag=true;
	    }
  //2018-19    
      allowWhile=true;
  	  empflag=true;
  	 //2018-19
  	
      System.out.println(companyid +"::allowWhile::" +allowWhile);
      System.out.println("empflag::" +empflag);
      
      java.util.Iterator Bu_List_lat = list_BU_la.iterator();
      
      if(allowWhile && empflag) {
     
    	  // WHILE1
      while(Bu_List_lat.hasNext()){   /// Main Bu Iterator opining For Bu Iteratero
    	  
    	  
    	  
    	 ForCast=new TdsForeCast();
  		 From16b=new Form16B();	
  		 components=new ArrayList();
  		 emppayperiods=new ArrayList();
  		 empcomponents=new ArrayList();
  		 empids=new ArrayList();
  		 taxempids=new ArrayList();
  		 payperiod=new ArrayList();
  		 BulkList=new ArrayList();
  		 subList=new ArrayList();
  		 subList2=new ArrayList();
  		
  		
  		
  		
    	  
    	  companyid=Bu_List_lat.next().toString();
    	  empids=new ArrayList();
    	  
    	  System.out.println("companyid"+companyid);
    	  
		StringBuffer txemployee=new StringBuffer();
	    rs = null;
		String EmpParam=null;
		DataObj=new GetDbData();
		StringBuffer basic=new StringBuffer();
		String Eq_emp_plan=null;
	
	  //*****************************************************************
	  //ADD FOR BULK UPLOAD TO FETCH DATA
		StringBuffer EmployeeIDs_Bulk=new StringBuffer();
		StringBuffer Employee_Seq_Bulk=new StringBuffer();
		Employee_ID_Seq_Bulk=new StringBuffer();
		
	/*if(EmpParam!=null){
		
		Employee_ID_Seq_Bulk.append(" select  a.employeeid,a.employeesequenceno,a.companyid from   hclhrm_prod.tbl_employee_payperiod_details b ");
		Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_primary a on ");
		Employee_ID_Seq_Bulk.append(" a.employeeid=b.employeeid and b.businessunitid=a.companyid ");
		Employee_ID_Seq_Bulk.append(" where a.employeesequenceno in("+GetEMPID+")   and b.payperiod between "+FromPeriod+" and "+ToPeriod+" ");
		Employee_ID_Seq_Bulk.append(" group by a.employeeid ");
		
		Eq_emp_plan="EMPID";
		
	}else{*/
		
		    if(companyid!=null && companyid.equalsIgnoreCase("10001")){
		    	
		    	
		    	/*Employee_ID_Seq_Bulk.append(" select  a.employeeid,a.employeesequenceno,a.companyid from    ");
				Employee_ID_Seq_Bulk.append(" hclhrm_prod.tbl_employee_payperiod_details b ");
				Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_primary a on ");
				Employee_ID_Seq_Bulk.append(" a.employeeid=b.employeeid   ");
				//Employee_ID_Seq_Bulk.append(" and b.businessunitid=a.companyid ");
				Employee_ID_Seq_Bulk.append(" where  a.employeesequenceno in ("+Emp_Seq_Number+") and  ");
				Employee_ID_Seq_Bulk.append(" b.payperiod between "+FromPeriod+" and "+ToPeriod+" ");
				Employee_ID_Seq_Bulk.append(" group by a.employeeid limit 1");*/
		    	
		    	
		    	
		    	/*Employee_ID_Seq_Bulk.append(" select  a.employeeid,a.employeesequenceno,a.companyid, max(d.payperiod) as prdcurrent from    ");
				Employee_ID_Seq_Bulk.append(" hclhrm_prod.tbl_employee_payperiod_details b ");
				Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_primary a on ");
				Employee_ID_Seq_Bulk.append(" a.employeeid=b.employeeid   ");
				
				//Employee_ID_Seq_Bulk.append(" a.employeeid=b.employeeid   ");
				
				Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_pay_data d on d.payperiod=b.payperiod and d.businessunitid=b.businessunitid ");
				Employee_ID_Seq_Bulk.append(" where  a.employeesequenceno in ("+Emp_Seq_Number+") and  ");
				Employee_ID_Seq_Bulk.append(" b.payperiod between "+FromPeriod+" and "+ToPeriod+" ");
				Employee_ID_Seq_Bulk.append(" group by a.employeeid limit 1");*/
				
		    //	Employee_ID_Seq_Bulk.append(" select employeeid,employeesequenceno,companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in("+Emp_Seq_Number+") and status=1001 ");
		    	//** changed for remove employess list Data
		    Employee_ID_Seq_Bulk.append(" select employeeid,employeesequenceno,companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in("+Emp_Seq_Number+") ");
		    	
				rs=null;
					try {
						rs=(ResultSet)DataObj.getBasic(Employee_ID_Seq_Bulk.toString(), "buunit", rs ,connection);
						if(rs.next()){
							EmployeeIDs_Bulk.append(rs.getString("employeeid"));
							Employee_Seq_Bulk.append(rs.getString("employeesequenceno"));
							
							companyid=rs.getString("companyid");
							
							//companyid=rs.getString("companyid"); //enable after one payroll
//							currentPayperiod=Integer.parseInt(rs.getString("prdcurrent")); //enable after one payroll
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
		    	
		    }else{
		    	
		    /*	Employee_ID_Seq_Bulk.append(" select  a.employeeid,a.employeesequenceno   from    ");
		    	Employee_ID_Seq_Bulk.append(" hclhrm_prod.tbl_employee_payperiod_details b ");
		    	Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_primary a on ");
		    	Employee_ID_Seq_Bulk.append(" a.employeeid=b.employeeid   ");
		    	
		    //	Employee_ID_Seq_Bulk.append(" left join hclhrm_prod.tbl_employee_pay_data d on d.payperiod=b.payperiod and d.businessunitid=b.businessunitid ");
		    	
		    //	Employee_ID_Seq_Bulk.append(" and  b.businessunitid=a.companyid ");
		    	Employee_ID_Seq_Bulk.append(" where  a.companyid in("+companyid+") and  ");
		    	Employee_ID_Seq_Bulk.append(" b.payperiod between "+FromPeriod+" and "+ToPeriod+" ");
		    	Employee_ID_Seq_Bulk.append(" group by a.employeeid ");*/
		    	
		    	
		    	Employee_ID_Seq_Bulk.append(" select employeeid,employeesequenceno from hclhrm_prod.tbl_employee_primary where companyid in("+companyid+") and status=1001 ");
		    	
		    	 //## to run outer chart
		    	
		    //	Employee_ID_Seq_Bulk.append(" select employeeid,employeesequenceno from hclhrm_prod.tbl_employee_primary where companyid in("+companyid+")  ");
			       	
		    	
		    	
			rs=null;
				try {
					rs=(ResultSet)DataObj.getBasic(Employee_ID_Seq_Bulk.toString(), "buunit", rs ,connection);
					while(rs.next()){
						EmployeeIDs_Bulk.append(rs.getString("employeeid"));
						EmployeeIDs_Bulk.append(",");
						Employee_Seq_Bulk.append(rs.getString("employeesequenceno"));
						Employee_Seq_Bulk.append(",");
						
						//currentPayperiod=Integer.parseInt(rs.getString("prdcurrent"));
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				EmployeeIDs_Bulk.deleteCharAt(EmployeeIDs_Bulk.length()-1);
				Employee_Seq_Bulk.deleteCharAt(Employee_Seq_Bulk.length()-1);
				System.out.println(Employee_Seq_Bulk+"::EmployeeIDs_Bulk::" +EmployeeIDs_Bulk);
		    }
				//*****************************************************************
		    System.out.println(Employee_Seq_Bulk+"::EmployeeIDs_Bulk::" +EmployeeIDs_Bulk);
		    
		    rs=null;
			try {
				rs=(ResultSet)DataObj.getBasic("SELECT ifnull(MAX(PAYPERIOD),"+currentPayperiod+") ID FROM HCLHRM_PROD.tbl_businessunit_payroll_process WHERE BUSINESSUNITID="+companyid, "buunit", rs ,connection);
				while(rs.next()){
					
					currentPayperiod=Integer.parseInt(rs.getString(1).toString());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
		    
			
			
		    
	    System.out.println(companyid+"!Employee_ID_Seq_Bulk::: ~" +Employee_ID_Seq_Bulk);
		    
		
		basic.append(" select b.employeesequenceno, sum(a.netvalue),a.componentid,count(*) cnt,min(a.Payperiod) minpr,max(a.Payperiod) maxpr,b.callname,sum(a.grossvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
		// ADD-BU11,97-LTA,98-BONUS(Both CTC Components)
		basic.append(" where a.componentid in (22,24,25,26,27,28,30,31,97,98) and ");
		//basic.append(" where a.componentid in (22,24,25,26,27,28,30,31) and ");
		//basic.append(" a.businessunitid=b.companyid and ");
		basic.append(" b.employeeid=a.employeeid ");
		basic.append(" and a.status=1001 ");
		
		
	//	basic.append(" and b.status=1001 and b.companyid="+companyid+" and ");
		
		//basic.append(" and b.status=1001 and b.employeeid in ("+EmployeeIDs_Bulk+")  ");
		
		basic.append("  and b.employeeid in ("+EmployeeIDs_Bulk+")  ");
		
		basic.append(" and a.payperiod between '"+FromPeriod+"' and '"+ToPeriod+"' ");
		basic.append(" group by b.employeesequenceno,a.componentid;"); 
		
		
		System.out.println("1:::"+basic.toString());
		
		StringBuffer employee_profile=new StringBuffer();
		
		/*
		employee_profile.append(" SELECT A.EMPLOYEESEQUENCENO,A.EMPLOYEEID,A.CALLNAME,A.DATEOFBIRTH,ROUND(DATEDIFF(NOW(),A.DATEOFBIRTH)/365) AGE,B.DATEOFJOIN, ");
		employee_profile.append(" IF(B.DATEOFJOIN BETWEEN '"+TaxStartMonth+"' AND '"+TaxEndMonth+"',B.DATEOFJOIN,'"+TaxStartMonth+"') START, ");
		employee_profile.append(" IF(ROUND(DATEDIFF(NOW(),A.DATEOFBIRTH)/365)<60,IF(GENDER='1','G','W'),'S') STATUS,C.PAN, ");
		employee_profile.append(" (SELECT NAME from hcladm_prod.tbl_designation X,hclhrm_prod.tbl_employee_professional_details Y where X.DESIGNATIONID=Y.DESIGNATIONID ");
		employee_profile.append(" AND Y.employeeid=A.EMPLOYEEID) ");
		employee_profile.append(" FROM hclhrm_prod.tbl_employee_primary A ");
		employee_profile.append(" LEFT JOIN hclhrm_prod.tbl_employee_PROFILE B ON A.EMPLOYEEID=B.EMPLOYEEID ");
		employee_profile.append(" LEFT JOIN tbl_employee_personalinfo C ON A.EMPLOYEEID=C.EMPLOYEEID ");
		employee_profile.append(" WHERE A.EMPLOYEEID!=1 AND A.COMPANYID="+companyid+" AND A.STATUS=1001 "); 
        */
		
		StringBuffer industrytitle=new StringBuffer();
		
		industrytitle.append(" SELECT b.employeesequenceno, a.name as BUNAME,c.name,d.name as costcenterid,if(a.code='HHC' OR a.code='GENX' ,'HETERO HEALTHCARE LTD', if(a.code='AZISTA','AZISTA INDUSTRIES Pvt LTD',a.code) ) AS TITLENAME  ");
		industrytitle.append(" from hcladm_prod.tbl_businessunit a ");
		industrytitle.append(" join hclhrm_prod.tbl_employee_primary b ");
		industrytitle.append(" on b.companyid=a.businessunitid ");
		industrytitle.append(" join ");
		industrytitle.append(" hcladm_prod.tbl_status_codes c on c.status=b.status ");
		industrytitle.append(" join hcladm_prod.tbl_costcenter d on b.companyid=d.businessunitid and d.costcenterid=b.costcenterid ");
		industrytitle.append(" where b.employeeid in("+EmployeeIDs_Bulk+") ");
		
		rs=null;
			try {
				rs=(ResultSet)DataObj.getBasic(industrytitle.toString(), "buunit", rs ,connection);
				while(rs.next()){
					Basic.put(rs.getString(1)+"_BUNAME_V" , rs.getString("BUNAME"));
					Basic.put(rs.getString(1)+"_EMPSTATUS_V" , rs.getString(3));
					Basic.put(rs.getString(1)+"_EMPCOSTCENTER_V" , rs.getString(4));
					Basic.put(rs.getString(1)+"_EMPCOMPANYTITLE_V" , rs.getString(5));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
		employee_profile.append(" SELECT A.EMPLOYEESEQUENCENO ID,A.EMPLOYEEID,A.CALLNAME,DATE_FORMAT(A.DATEOFBIRTH,'%d-%m-%Y')DOB,   ");
		employee_profile.append(" ROUND(DATEDIFF(NOW(),A.DATEOFBIRTH)/365) AGE,  IFNULL(DATE_FORMAT(B.DATEOFJOIN,'%d-%m-%Y'),'--') DOJ,  ");
		employee_profile.append(" IF( DATE_FORMAT(IFNULL(B.DATEOFJOIN,CURDATE()),'%Y-%m-%d') >= '"+TaxStartMonth+"' ,DATE_FORMAT(IFNULL(B.DATEOFJOIN,CURDATE()),'%Y-%m-%d'),'"+TaxStartMonth+"')  ");
		employee_profile.append(" START,  IF(ROUND(DATEDIFF(NOW(),A.DATEOFBIRTH)/365)<60,IF(A.GENDER='1','G','W'),  ");
		employee_profile.append(" IF(ROUND(DATEDIFF(NOW(),A.DATEOFBIRTH)/365)>=60 AND ROUND(DATEDIFF(NOW(),A.DATEOFBIRTH)/365)<80, 'S','O')) STATUS,  ");
		employee_profile.append(" IFNULL(C.PAN,'--')PAN,IFNULL(E.NAME,'--') DESIGNATION  FROM HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A  ");
		employee_profile.append(" LEFT JOIN hclhrm_prod.tbl_employee_PROFILE B ON A.EMPLOYEEID=B.EMPLOYEEID  ");
		employee_profile.append(" LEFT JOIN hclhrm_prod.tbl_employee_personalinfo C ON A.EMPLOYEEID=C.EMPLOYEEID  ");
		employee_profile.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON A.EMPLOYEEID=D.EMPLOYEEID  ");
		employee_profile.append(" LEFT JOIN HCLADM_PROD.TBL_DESIGNATION E ON  ");
		
		employee_profile.append(" D.DESIGNATIONID=E.DESIGNATIONID  WHERE A.employeeid IN ("+EmployeeIDs_Bulk+") ");
		//employee_profile.append(" D.DESIGNATIONID=E.DESIGNATIONID  WHERE A.COMPANYID IN ("+companyid+") AND A.STATUS=1001 ");
		
		
		
		System.out.println("2::"+employee_profile.toString());
		StringBuffer EmpTds=new StringBuffer();
		
		/*EmpTds.append(" SELECT A.EMPLOYEESEQUENCENO,SUM(B.TDSVALUE),B.TRANSACTIONID FROM hclhrm_prod.tbl_employee_primary A,  ");
		EmpTds.append(" hclhrm_prod.tbl_employee_tds B WHERE  ");
		//EmpTds.append(" B.BUSINESSUNITID="+companyid+"");
		EmpTds.append(" B.employeeid in ("+EmployeeIDs_Bulk+") ");
		EmpTds.append(" AND  ");
		EmpTds.append(" B.TRANSACTIONID BETWEEN '"+FromPeriod+"' AND '"+ToPeriod+"'  ");
		EmpTds.append(" AND A.EMPLOYEEID=B.EMPLOYEEID AND B.STATUS='C'  ");
		EmpTds.append(" GROUP BY B.EMPLOYEEID,A.EMPLOYEESEQUENCENO ");
		*/
		// ABOVE BLOCKED FOR MISS DATA tds
		
	/*	EmpTds.append("	SELECT A.EMPLOYEESEQUENCENO,SUM(IFNULL(B.NETVALUE,0)),B.PAYPERIOD FROM hclhrm_prod.tbl_employee_primary A, ");
		EmpTds.append("hclhrm_prod.tbl_employee_pay_data B WHERE ");
		EmpTds.append(" B.employeeid in ("+EmployeeIDs_Bulk+") ");
		EmpTds.append(" AND B.componentid=21 AND ");
		EmpTds.append(" B.payperiod BETWEEN '"+FromPeriod+"' AND '"+ToPeriod+"' ");
		EmpTds.append(" AND A.EMPLOYEEID=B.EMPLOYEEID AND B.STATUS=1001 ");
		EmpTds.append(" GROUP BY B.EMPLOYEEID,A.EMPLOYEESEQUENCENO ");*/
		
		
		EmpTds.append(" SELECT A.EMPLOYEESEQUENCENO,SUM(IFNULL(B.NETVALUE,0)),B.PAYPERIOD FROM hclhrm_prod.tbl_employee_primary A,  ");
		EmpTds.append(" hclhrm_prod.tbl_employee_pay_data B,hclhrm_prod.tbl_employee_payperiod_details c WHERE ");
		EmpTds.append(" b.employeeid in ("+EmployeeIDs_Bulk+") ");
		EmpTds.append(" AND B.componentid=21 AND ");
		EmpTds.append(" B.payperiod BETWEEN '"+FromPeriod+"' AND '"+ToPeriod+"' ");
		EmpTds.append(" AND A.EMPLOYEEID=B.EMPLOYEEID AND B.STATUS=1001 and b.payperiod=c.payperiod and b.employeeid=c.employeeid ");
		EmpTds.append(" GROUP BY B.EMPLOYEEID,A.EMPLOYEESEQUENCENO ");
		
		
		
		
		System.out.println("3::"+EmpTds.toString());
		StringBuffer yeardata=new StringBuffer(); // For PayData Info
		//yeardata.append(" select b.employeesequenceno,a.payperiod,a.payabledays,a.grossvalue,a.netvalue,if(a.componentid=22,'Gross',if(a.componentid=56,'LTA',if(a.componentid=87,'Arrear',if(a.componentid=71,'PB', if(a.componentid=80,'INCENTIVE',if(a.componentid=46,'Paid Net(include others)', if(a.componentid=69,'Annual Bonus',if(a.componentid=94,'Bonus','')))  ))))),b.callname ");
		
	//	yeardata.append(" select b.employeesequenceno,a.payperiod,a.payabledays,a.grossvalue,a.netvalue,if(a.componentid=22,'Gross',if(a.componentid=56,'LTA',if(a.componentid=87,'Arrear',if(a.componentid=71,'PB', if(a.componentid=80,'INCENTIVE',if(a.componentid=46,'Paid Net(include others)', if(a.componentid=69,'Annual Bonus',if(a.componentid=94,'Bonus',if(a.componentid=57,'PB',''))))  ))))),b.callname ");
		
		
		//yeardata.append(" select b.employeesequenceno,a.payperiod,a.payabledays,a.grossvalue,a.netvalue,if(a.componentid=22,'Gross',if(a.componentid=56,'LTA',if(a.componentid=87,'Arrear',if(a.componentid=71,'PB', if(a.componentid=80,'INCENTIVE',if(a.componentid=46,'Paid Net(include others)', if(a.componentid=69,'Annual Bonus',if(a.componentid=94,'Bonus',if(a.componentid=57,'PB',if(a.componentid=99,'Ref_Incentive',if(a.componentid=100,'Oth_LTA', ' ' ))))))  ))))),b.callname ");
		
		yeardata.append(" select b.employeesequenceno,a.payperiod,a.payabledays,a.grossvalue,a.netvalue,if(a.componentid=22,'Gross',if(a.componentid=56,'LTA',if(a.componentid=87,'Arrear',if(a.componentid=71,'PB', if(a.componentid=80,'INCENTIVE',if(a.componentid=46,'Paid Net(include others)', if(a.componentid=69,'Annual Bonus',if(a.componentid=94,'Bonus',if(a.componentid=57,'PB',if(a.componentid=99,'Ref_Incentive',if(a.componentid=100,'Oth_LTA', if(a.componentid=88,'ADDITIONS', if(a.componentid=98,'MLTA', if(a.componentid=99,'MBONUS', ' ' ) ) ) ) )))))  ))))),b.callname ");
		
		//if(a.componentid=57,'PB','')
		yeardata.append(" from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
		yeardata.append(" where a.componentid in (22,87,80,56,71,69,94,57,97,98,99,100,88) and "); // ADDITIONS ADDING
		//ABOVE LINE ADD FOR FIELD EMPLOYESS 15 Structure BU11
		//yeardata.append(" where a.componentid in (22,87,80,56,71,69,94,57) and ");
		//yeardata.append(" a.businessunitid=b.companyid and ");
		yeardata.append(" b.employeeid=a.employeeid ");
		yeardata.append(" and a.status=1001 ");
		
		//yeardata.append(" and b.status=1001 and b.companyid="+companyid+" and ");
		yeardata.append(" and b.employeeid in ("+EmployeeIDs_Bulk+")  ");
		
		yeardata.append(" and  a.payperiod between '"+FromPeriod+"' AND '"+ToPeriod+"' ");
		//yeardata.append(" group by b.employeesequenceno,a.componentid ");//46
		
		System.out.println("4::"+yeardata);
		StringBuffer ArrearCom=new StringBuffer(); 
		///Arrear Paid Components which is Paid already.
		
		ArrearCom.append(" select b.employeesequenceno,a.componentid,sum(a.grossvalue),sum(a.netvalue) ");
		ArrearCom.append(" from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
		ArrearCom.append(" where a.componentid in (87,80,56,71,79,90,69,94,57,99,100,88) and ");  // 88 ADD FOR ADDITIONS on 28-12-2017
		//ArrearCom.append(" a.businessunitid=b.companyid and ");
		ArrearCom.append(" b.employeeid=a.employeeid ");
		ArrearCom.append(" and a.status=1001 ");
		ArrearCom.append(" and b.EMPLOYEEID in ("+EmployeeIDs_Bulk+") and ");
		ArrearCom.append(" a.payperiod between '"+FromPeriod+"' AND '"+ToPeriod+"' ");
		ArrearCom.append(" group by b.employeesequenceno,a.componentid ");
		
		System.out.println("5::"+ArrearCom.toString());
		
		StringBuffer EmpSequence=new StringBuffer();
	//	EmpSequence.append(" select employeesequenceno,callname from hclhrm_prod.tbl_employee_primary where employeeid in ("+EmployeeIDs_Bulk+") and companyid="+companyid+" ");
		
		EmpSequence.append(" select employeesequenceno,callname from hclhrm_prod.tbl_employee_primary where employeeid in ("+EmployeeIDs_Bulk+")");
		
		StringBuffer buunit=new StringBuffer();
		
		
		buunit.append(" select a.paystructureid,c.name PayStructure,a.businessunitid,b.name BuName from hcladm_prod.tbl_pay_structure_businessunit a,  ");
		buunit.append(" hcladm_prod.tbl_businessunit b,hcladm_prod.tbl_pay_structure c ");
		buunit.append(" where a.status=1001 and a.businessunitid=b.businessunitid ");
		buunit.append(" and b.businessunitid="+companyid);
		buunit.append(" and c.paystructureid=a.paystructureid ");
		
      //  System.out.println("6::"+buunit.toString());
		StringBuffer Tax_dec=new StringBuffer();
		/*
		Tax_dec.append(" SELECT DISTINCT A.EMPLOYEEID,A.NAME,(SUM(B.TOTAL_PREMIUM)+D.PUBLICPF+D.FDS+D.POSAVINGS+D.NATIONALSAVINGCERTIFICATES+D.UNIT_LIP+D.PRINCIPAL_HOUSINGLOAN ");
        Tax_dec.append(" +D.EQUITYLINKED_SAVINGS+D.TAX_SAVINGBONDS+D.SUKANYA_SAMRIDDI+D.TUITION_FEE_KIDS+D.OTHERS)80C,D.LIFE_IPS 80CCC,D.CPSCG 80CCD,  ");
		Tax_dec.append(" ((C.TOTAL_PREMIUM)+E.PHC)80D,E.S80DD,E.S80DD2,E.S80DDB,E.S80DDB2,E.S80U,E.S80U2,E.S80E,E.S80CCG,E.S80CCG2,E.S80TTA,E.S80TTA1,E.S80G,  ");
		Tax_dec.append(" F.LETOUT,F.OTHERINCOME,E.ToTAL_80D FROM TEST.TBL_IT_EMP_DETAILS A  ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_DETAILS B ON A.REFNO=B.REFNO  ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_DETAILS C ON A.REFNO=C.REFNO  ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_OTHERS D ON A.REFNO=D.REFNO  ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_OTHERS E ON A.REFNO=E.REFNO  ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_RENTHOUSE F ON A.REFNO=F.REFNO  ");
		Tax_dec.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY G ON A.EMPLOYEEID=G.EMPLOYEESEQUENCENO ");
		Tax_dec.append(" where G.COMPANYID IN ("+companyid+") GROUP BY A.EMPLOYEEID ");
		*/
		
		/*Tax_dec.append(" SELECT DISTINCT A.EMPLOYEEID,A.NAME,  ");
		Tax_dec.append(" ((b.final_total)+D.PUBLICPF+D.FDS+D.POSAVINGS+D.NATIONALSAVINGCERTIFICATES+D.UNIT_LIP+D.PRINCIPAL_HOUSINGLOAN+ ");
		Tax_dec.append(" D.EQUITYLINKED_SAVINGS+D.TAX_SAVINGBONDS+D.SUKANYA_SAMRIDDI+D.TUITION_FEE_KIDS+D.OTHERS)80C,D.LIFE_IPS 80CCC,D.CPSCG 80CCD, ");
		Tax_dec.append(" ((C.FINAL_TOTAL)+E.PHC) 80D,E.S80DD,E.S80DD2,E.S80DDB,E.S80DDB2,E.S80U,E.S80U2,E.S80E,E.S80CCG,E.S80CCG2,E.S80TTA,E.S80TTA1,E.S80G, ");
		Tax_dec.append(" F.LETOUT,F.OTHERINCOME,E.TOTAL_80D ");
		Tax_dec.append(" FROM ");
		Tax_dec.append(" TEST.TBL_IT_EMP_DETAILS A ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_DETAILS B ON A.REFNO=B.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_DETAILS C ON A.REFNO=C.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_OTHERS D ON A.REFNO=D.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_OTHERS E ON A.REFNO=E.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_RENTHOUSE F ON A.REFNO=F.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_EMP_IT_TDS G ON A.REFNO=G.REFNO ");
		Tax_dec.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY H ON A.EMPLOYEEID=H.EMPLOYEESEQUENCENO ");
		Tax_dec.append(" WHERE ");
		Tax_dec.append(" A.STATUS in ('S','C') AND G.STATUS in (1001,1005) and H.companyid in("+companyid+") "); // For Active
		//Tax_dec.append(" A.STATUS='C' AND G.STATUS=1002 and H.companyid in("+companyid+") ");
		Tax_dec.append(" GROUP BY A.EMPLOYEEID ORDER BY A.EMPLOYEEID ");*/
		
		
		/*Tax_dec.append(" SELECT DISTINCT A.EMPLOYEEID,A.NAME,  ");
		//Tax_dec.append(" ((b.final_total)+D.PUBLICPF+D.FDS+D.POSAVINGS+D.NATIONALSAVINGCERTIFICATES+D.UNIT_LIP+D.PRINCIPAL_HOUSINGLOAN+ ");
		Tax_dec.append(" IFNULL(D.FINAL_80C,0) 80C, IFNULL(D.LIFE_IPS,0) 80CCC,IFNULL(D.CPSCG,0) 80CCD, ");
		Tax_dec.append(" ( IFNULL(C.FINAL_TOTAL,0)+IFNULL(E.PHC,0)) 80D,IFNULL(E.S80DD,0) S80DD,IFNULL(E.S80DD2,0) S80DD2,IFNULL(E.S80DDB,0) S80DDB,IFNULL(E.S80DDB2,0) S80DDB2,IFNULL(E.S80U,0) S80U,IFNULL(E.S80U2,0) S80U2,IFNULL(E.S80E,0) S80E ,IFNULL(E.S80CCG,0) S80CCG,IFNULL(E.S80CCG2,0) S80CCG2 ,IFNULL(E.S80TTA,0) S80TTA,IFNULL(E.S80TTA1,0) S80TTA1 ,IFNULL(E.S80G,0) S80G, ");
		Tax_dec.append(" IFNULL(F.LETOUT,0) LETOUT,IFNULL(F.OTHERINCOME,0) OTHERINCOME,IFNULL(E.TOTAL_80D,0) TOTAL_80D ");
		Tax_dec.append(" , C.FOR_RELATION,C.CITIZEN  FROM ");
		Tax_dec.append(" TEST.TBL_IT_EMP_DETAILS A ");
		//Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_DETAILS B ON A.REFNO=B.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_DETAILS C ON A.REFNO=C.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_OTHERS D ON A.REFNO=D.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_OTHERS E ON A.REFNO=E.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_RENTHOUSE F ON A.REFNO=F.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_EMP_IT_TDS G ON A.REFNO=G.REFNO ");
		Tax_dec.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY H ON A.EMPLOYEEID=H.EMPLOYEESEQUENCENO ");
		Tax_dec.append(" WHERE ");
		Tax_dec.append("  G.STATUS in (1001,1005) and H.EMPLOYEESEQUENCENO in ("+Employee_Seq_Bulk+") "); // For Active
		//Tax_dec.append(" A.STATUS='C' AND G.STATUS=1002 and H.companyid in("+companyid+") ");
		Tax_dec.append(" GROUP BY A.EMPLOYEEID ORDER BY A.EMPLOYEEID ");*/
		
		//******* modified on 29-01-2018 *******************************
		Tax_dec.append(" SELECT DISTINCT A.EMPLOYEEID,A.NAME,  ");
		//Tax_dec.append(" ((b.final_total)+D.PUBLICPF+D.FDS+D.POSAVINGS+D.NATIONALSAVINGCERTIFICATES+D.UNIT_LIP+D.PRINCIPAL_HOUSINGLOAN+ ");
		Tax_dec.append(" IFNULL(D.FINAL_80C,0) 80C, IFNULL(D.LIFE_IPS,0) 80CCC,IFNULL(D.CPSCG,0) 80CCD, ");
		Tax_dec.append(" ( IFNULL(C.FINAL_TOTAL,0)+IFNULL(E.PHC,0)) 80D,IFNULL(E.S80DD,0) S80DD,IFNULL(E.S80DD2,0) S80DD2,IFNULL(E.S80DDB,0) S80DDB,IFNULL(E.S80DDB2,0) S80DDB2,IFNULL(E.S80U,0) S80U,IFNULL(E.S80U2,0) S80U2,IFNULL(E.S80E,0) S80E ,IFNULL(E.S80CCG,0) S80CCG,IFNULL(E.S80CCG2,0) S80CCG2 ,IFNULL(E.S80TTA,0) S80TTA,IFNULL(E.S80TTA1,0) S80TTA1 ,IFNULL(E.S80G,0) S80G, ");
		Tax_dec.append(" IFNULL(F.LETOUT,0) LETOUT,IFNULL(F.OTHERINCOME,0) OTHERINCOME,IFNULL(E.TOTAL_80D,0) TOTAL_80D ");
		Tax_dec.append(" , C.FOR_RELATION,C.CITIZEN , ifnull(BX.CITIZEN,'NO') as SrCITIZEN FROM ");
		Tax_dec.append(" TEST.TBL_IT_EMP_DETAILS A ");
		//Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_DETAILS B ON A.REFNO=B.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_DETAILS C ON A.REFNO=C.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80C_OTHERS D ON A.REFNO=D.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_80D_OTHERS E ON A.REFNO=E.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_IT_EMP_RENTHOUSE F ON A.REFNO=F.REFNO ");
		Tax_dec.append(" LEFT JOIN TEST.TBL_EMP_IT_TDS G ON A.REFNO=G.REFNO ");
		Tax_dec.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY H ON A.EMPLOYEEID=H.EMPLOYEESEQUENCENO ");
		Tax_dec.append(" left join( ");
		Tax_dec.append(" select ifnull(CITIZEN,'NO') CITIZEN,refno from TEST.TBL_IT_EMP_80D_DETAILS where refno in( ");
		Tax_dec.append(" select max(refno) from TEST.TBL_EMP_IT_TDS where employeeid in("+Employee_Seq_Bulk+") and status in(1001,1005) and fy='"+FiyYear+"' group by employeeid ) ");
		Tax_dec.append(" and for_relation='Mother&Father' ");
		Tax_dec.append(" )BX ON BX.refno=g.refno ");
		Tax_dec.append(" WHERE ");
		Tax_dec.append(" g.refno in(select max(refno) from TEST.TBL_EMP_IT_TDS where employeeid in("+Employee_Seq_Bulk+") and status in(1001,1005) and fy='"+FiyYear+"' group by employeeid) ");
		Tax_dec.append(" and H.EMPLOYEESEQUENCENO in ("+Employee_Seq_Bulk+") "); // For Active
		//Tax_dec.append("  G.STATUS in (1001,1005) and H.EMPLOYEESEQUENCENO in ("+Employee_Seq_Bulk+") "); // For Active
		//Tax_dec.append(" A.STATUS='C' AND G.STATUS=1002 and H.companyid in("+companyid+") ");
		Tax_dec.append(" GROUP BY A.EMPLOYEEID ORDER BY A.EMPLOYEEID ");
		
		//******* modified on 29-01-2018 *******************************
		
		
		// Above Query For Calculating Form 16B only
		
		//System.out.println("Tax_dec:: "+Tax_dec.toString());
		System.out.println("7::"+Tax_dec.toString());
		Basic.put("_TAX_CAL_TO" , TaxEndMonth);
		
		
		
		
		rs=null;
		
	//	System.out.println("employee_profile.toString()::"+employee_profile.toString());
		try {
			
			
			rs=(ResultSet)DataObj.getBasic(employee_profile.toString(), "buunit", rs ,connection);
			while(rs.next()){
				
				Basic.put(rs.getString(1)+"_TAX_CAL_FRM" , rs.getString(7));
				Basic.put(rs.getString(1)+"_EMP_PROF_GENDER" , rs.getString(8));
				Basic.put(rs.getString(1)+"_EMP_PROF_PAN" , rs.getString(9));
				Basic.put(rs.getString(1)+"_EMP_PROF_DESIG" , rs.getString(10));
				//System.out.println("ProposedMonths:::"+ProposedMonths);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 /* 24 basic
		  22 gross
		  25 Hra
		  26 Ca
		  27 Medical
		  28 Education
		  30 Pt
	      //if((Newgross+Oldgross)-(OldPt+NewPt)>250000)
    select b.employeesequenceno,a.componentid,sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b
	where a.componentid in(24,22) and
	a.businessunitid=b.companyid and
	b.employeeid=a.employeeid
	and a.status=1001
	and b.status=1001 and b.companyid=9 and
	a.payperiod between '201604' and '201703'
	group by b.employeesequenceno,a.componentid;

	select b.employeesequenceno,sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b
	where a.componentid=22) and
	a.businessunitid=b.companyid and
	b.employeeid=a.employeeid
	and a.status=1001
	and b.status=1001 and b.companyid=9 and
	a.payperiod between '201604' and '201703'
	group by b.employeesequenceno;

		 */
		/*StringBuffer gross=new StringBuffer();
    gross.append(" select b.employeesequenceno, sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
    gross.append(" where a.componentid=22 and ");
    gross.append(" a.businessunitid=b.companyid and ");
    gross.append(" b.employeeid=a.employeeid ");
    gross.append(" and a.status=1001 ");
    gross.append(" and b.status=1001 and b.companyid="+companyid+" and ");
    gross.append(" a.payperiod between '201604' and '201604' ");
    gross.append(" group by b.employeesequenceno;");

    StringBuffer Hra=new StringBuffer();
    Hra.append(" select b.employeesequenceno, sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
    Hra.append(" where a.componentid=25 and ");
    Hra.append(" a.businessunitid=b.companyid and ");
    Hra.append(" b.employeeid=a.employeeid ");
    Hra.append(" and a.status=1001 ");
    Hra.append(" and b.status=1001 and b.companyid="+companyid+" and ");
    Hra.append(" a.payperiod between '201604' and '201604' ");
    Hra.append(" group by b.employeesequenceno;");

    StringBuffer Ca=new StringBuffer();
    Ca.append(" select b.employeesequenceno, sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
    Ca.append(" where a.componentid=26 and ");
    Ca.append(" a.businessunitid=b.companyid and ");
    Ca.append(" b.employeeid=a.employeeid ");
    Ca.append(" and a.status=1001 ");
    Ca.append(" and b.status=1001 and b.companyid="+companyid+" and ");
    Ca.append(" a.payperiod between '201604' and '201604' ");
    Ca.append(" group by b.employeesequenceno; ");

    StringBuffer Medical=new StringBuffer();
    Medical.append(" select b.employeesequenceno, sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
    Medical.append(" where a.componentid=27 and ");
    Medical.append(" a.businessunitid=b.companyid and ");
    Medical.append(" b.employeeid=a.employeeid ");
    Medical.append(" and a.status=1001 ");
    Medical.append(" and b.status=1001 and b.companyid="+companyid+" and ");
    Medical.append(" a.payperiod between '201604' and '201604' ");
    Medical.append(" group by b.employeesequenceno; ");

    StringBuffer Education=new StringBuffer();
    Education.append(" select b.employeesequenceno, sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
    Education.append(" where a.componentid=28 and ");
    Education.append(" a.businessunitid=b.companyid and ");
    Education.append(" b.employeeid=a.employeeid ");
    Education.append(" and a.status=1001 ");
    Education.append(" and b.status=1001 and b.companyid="+companyid+" and ");
    Education.append(" a.payperiod between '201604' and '201604' ");
    Education.append(" group by b.employeesequenceno; ");

    StringBuffer Pt=new StringBuffer();
    Pt.append(" select b.employeesequenceno, sum(a.netvalue) from hclhrm_prod.tbl_employee_pay_data a,hclhrm_prod.tbl_employee_primary b ");
    Pt.append(" where a.componentid=30 and ");
    Pt.append(" a.businessunitid=b.companyid and ");
    Pt.append(" b.employeeid=a.employeeid ");
    Pt.append(" and a.status=1001 ");
    Pt.append(" and b.status=1001 and b.companyid="+companyid+" and ");
    Pt.append(" a.payperiod between '201604' and '201604' ");
    Pt.append(" group by b.employeesequenceno; ");*/
		
		rs=null;
		rs=(ResultSet)DataObj.fupaid(EmpSequence.toString(), "EmpSequence", rs ,connection);
		try {
			while(rs.next()){
				try{
					//ProposedMonths=rs.getString(5);
					
					empids.add(rs.getString(1));
					
			//System.out.println(rs.getString(1));
			
					Basic.put(rs.getString(1)+".pm", 0);
					Basic.put(rs.getString(1)+".callname", rs.getString(2));
					Basic.put(rs.getString(1)+".preriod", 0);
					
					Basic.put(rs.getString(1)+".gross", 0);
					Basic.put(rs.getString(1)+".basic", 0);

					Basic.put(rs.getString(1)+".Hra", 0);
					Basic.put(rs.getString(1)+"-CA", 0);
					Basic.put(rs.getString(1)+".Medical", 0);
					Basic.put(rs.getString(1)+".Education", 0);
					Basic.put(rs.getString(1)+".Pt", 0);
					Basic.put(rs.getString(1)+".PF", 0);
					
					// ADD FOR Extra 80D on 26-12-2017
					Basic.put(rs.getString(1)+".OUTER_S80D", "0");
					Basic.put(rs.getString(1)+".OUTER_D80D", "0");
					Basic.put(rs.getString(1)+".OUTER_CTC", "0");
				    
				//YEAR CODES
					
					Basic.put(rs.getString(1)+".ANN_basic", 0);

					Basic.put(rs.getString(1)+".ANN_Hra", 0);
					Basic.put(rs.getString(1)+".ANN_CA", 0);
					Basic.put(rs.getString(1)+".ANN_Medical", 0);
					Basic.put(rs.getString(1)+".ANN_Education", 0);
					Basic.put(rs.getString(1)+".ANN_Pt", 0);
					Basic.put(rs.getString(1)+".ANN_PF", 0);
			   //YEAR CODES	
					
					Basic.put(rs.getString(1)+".LTA", 0);
					
					Basic.put(rs.getString(1)+".BuName", 0);
					Basic.put(rs.getString(1)+".PayID", 0);
					
					Basic.put(rs.getString(1)+".ANN_gross", 0);
					
					Basic.put(rs.getString(1)+".ANN_LTA", 0);
					Basic.put(rs.getString(1)+".ANN_BONUS", 0);
					
				// For Annual Caliculation	
					Basic.put(rs.getString(1)+"._80_ANN", 0);
					Basic.put(rs.getString(1)+"._87_ANN", 0);
					Basic.put(rs.getString(1)+"._56_ANN", 0);
					Basic.put(rs.getString(1)+"._71_ANN", 0);
					Basic.put(rs.getString(1)+"._79_ANN", 0);
					Basic.put(rs.getString(1)+"._90_ANN", 0);
					
					
					Basic.put(rs.getString(1)+"._69_ANN", 0);
					
					Basic.put(rs.getString(1)+"._94_ANN", 0);
					
					Basic.put(rs.getString(1)+"._57_ANN", 0);
					
					Basic.put(rs.getString(1)+".ANN_EAR_OTHERS", 0);
					
					Basic.put(rs.getString(1)+".ANN_EAR_OTHERS_ANN", 0); // ADD For TDS Yearly Caliculation
					
					
					Basic.put(rs.getString(1)+".ANN_DED_OTHERS", 0);
					
					Basic.put(rs.getString(1)+"_ANNUAL_BENIFITS", 0);
					Basic.put(rs.getString(1)+".ANN_BONUS", 0);
					Basic.put(rs.getString(1)+".ANN_BONUS_2", 0);
					
					Basic.put(rs.getString(1)+".MEDICAL_ANNUAL", 0); // ADD ON 18-12-2017 for 80D medical caliculation
					
					
					Basic.put(rs.getString(1)+"_INCOME_PRE_COMPANY", "0");
					
					Basic.put(rs.getString(1)+"_INCOME_PRE_PAIDTDS", "0");
					
					Basic.put(rs.getString(1)+".CTC97", "0");
					Basic.put(rs.getString(1)+".CTC98", "0");
					
					Basic.put(rs.getString(1)+".ANNCTC97", "0");
					Basic.put(rs.getString(1)+".ANNCTC98", "0");
					
					
					
					Basic.put(rs.getString(1)+"._99_ANN", "0"); // ADD ON 15-09-2017
					Basic.put(rs.getString(1)+"._100_OTH_LTA", "0"); // ADD ON 15-09-2015
					
					Basic.put(rs.getString(1)+"._88_ADDITIONS", 0); // ADD ON 15-09-2015
					
					Basic.put(rs.getString(1)+"._57_ANN", 0);
					
					Basic.put(rs.getString(1)+"._57_ANN", 0);
					// Tax Declaration MAP Final
					
					Tax_dec_Map.put(rs.getString(1)+"$DE_80C", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_80CCC", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_80CCD", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_80D", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80DD", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80DD2", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80DDB", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80DDB2", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80U", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80U2", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80E", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80CCG", 0);
					
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80CCG2", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80TTA", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80TTA1", 0);
					
					Tax_dec_Map.put(rs.getString(1)+"$DE_S80G", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_LETOUT", 0);
					Tax_dec_Map.put(rs.getString(1)+"$DE_OTHERINCOME", 0);
					
					Tax_dec_Map.put(rs.getString(1)+"$ToTAL_80D", 0);
					
					Tax_dec_Map.put(rs.getString(1)+"$FOR_RELATION", "Mother&Father"); // ADD FOR NEW 80D Modifications
					Tax_dec_Map.put(rs.getString(1)+"$CITIZEN", "NO");      // ADD FOR NEW 80D Modifications
					// Tax Declaration MAP Final
					

				}catch(Exception ex){
					ex.printStackTrace();		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		// VENU-PREV-EMPLOYEAR-DATA FETCH
		
		rs=null;
		try {
			rs=(ResultSet)DataObj.getBasic("SELECT EMPLOYEEID,S80D,D80D,CTC FROM test_mum.tbl_emp_tds_extra_80d_cal where FY='"+FiyYear+"' and status=1001 and EMPLOYEEID in (select employeesequenceno from hclhrm_prod.tbl_employee_primary where employeeid in ("+EmployeeIDs_Bulk+"))", "80OUTER CALICULATIONS", rs ,connection);
			while(rs.next()){
				
			  Basic.put(rs.getString("EMPLOYEEID")+".OUTER_S80D" , rs.getString("S80D"));
			  Basic.put(rs.getString("EMPLOYEEID")+".OUTER_D80D" , rs.getString("D80D"));
			  Basic.put(rs.getString("EMPLOYEEID")+".OUTER_CTC" ,  rs.getString("CTC"));
			
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		    
		
		
		// ADD FOR 80D Caliculation 
			StringBuffer PreVious_employear=new StringBuffer();
				
				PreVious_employear.append(" select EMPLOYEEID EMPID,INCOME_PREV_EMPLOYER INCOME,TDS_PAID PAIDTDS FROM " );
				PreVious_employear.append(" test.tds_prev_employer_income where STATUS=1001 AND FY='"+FiyYear+"' and EMPLOYEEID in (select employeesequenceno from hclhrm_prod.tbl_employee_primary where employeeid in ("+EmployeeIDs_Bulk+")) ");
				System.out.println("PreVious_employear:::" +PreVious_employear);
				/*PreVious_employear.append("select trim(EMPLOYEEID) EMPID,trim(INCOME_PREV_EMPLOYER) INCOME,trim(TDS_PAID) PAIDTDS FROM "
						+ "test.tds_prev_employer_income where STATUS=1001 AND FY='"+FiyYear+"' AND COMPANYID='"+companyid+"'");*/
				rs=null;
					try {
						rs=(ResultSet)DataObj.getBasic(PreVious_employear.toString(), " PreVious_employear ", rs ,connection);
						while(rs.next()){
							
							// Enabled For Check Issues on Data 16-02-2018 final amount by venu
							Basic.put(rs.getString("EMPID")+"_INCOME_PRE_COMPANY" , rs.getString("INCOME"));
							
							
							Basic.put(rs.getString("EMPID")+"_INCOME_PRE_PAIDTDS" , rs.getString("PAIDTDS"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
					
		/*StringBuffer ProposedPeriod=new StringBuffer();

		if(MaxPayPeriod==null || MaxPayPeriod=="0"){
			MaxPayPeriod=FromPeriod;
			ProposedPeriod.append(" Select TIMESTAMPDIFF(MONTH , date((concat("+MaxPayPeriod+" , 25))),date((concat("+ToPeriod+" , 25))))+1  from dual ");
		}else{
			ProposedPeriod.append(" Select TIMESTAMPDIFF(MONTH , date((concat("+MaxPayPeriod+" , 25))),date((concat("+ToPeriod+" , 25))))  from dual ");
		} */
		
		// (round((((select distinct max(payperiod) from hclhrm_prod.tbl_employee_pay_data ")
		//where businessunitid=9)-201511)+1)/30)+1) //( TIMESTAMPDIFF(MONTH, date((concat("+MaxPayPeriod+",30))), date((concat("+ToPeriod+",30))))))
		//,TIMESTAMPDIFF(MONTH, date((concat("+MaxPayPeriod+",30))),date((concat("+ToPeriod+",30)))) proposed 
		rs=null;
		try {
			  
			rs=(ResultSet)DataObj.getBasic(Tax_dec.toString(), "Employee Tax Declaration", rs ,connection);
		 
			while(rs.next()){
				 // 2 Start 80C
				if(rs.getString("80C")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_80C",rs.getString("80C"));
				if(rs.getString("80CCC")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_80CCC", rs.getString("80CCC"));
				if(rs.getString("80CCD")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_80CCD", rs.getString("80CCD"));
				if(rs.getString("80D")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_80D", rs.getString("80D"));
				if(rs.getString("S80DD")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80DD", rs.getString("S80DD"));
				if(rs.getString("S80DD2")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80DD2", rs.getString("S80DD2"));
				if(rs.getString("S80DDB")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80DDB", rs.getString("S80DDB"));
				if(rs.getString("S80DDB2")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80DDB2", rs.getString("S80DDB2"));
				if(rs.getString("S80U")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80U", rs.getString("S80U"));
				if(rs.getString("S80U2")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80U2", rs.getString("S80U2"));
				if(rs.getString("S80E")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80E", rs.getString("S80E"));
				if(rs.getString("S80CCG")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80CCG", rs.getString("S80CCG"));
				if(rs.getString("S80CCG2")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80CCG2", rs.getString("S80CCG2"));
				if(rs.getString("S80TTA")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80TTA", rs.getString("S80TTA"));
				if(rs.getString("S80TTA1")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80TTA1", rs.getString("S80TTA1"));
				if(rs.getString("S80G")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_S80G", rs.getString("S80G"));
				if(rs.getString("LETOUT")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_LETOUT", rs.getString("LETOUT"));
				if(rs.getString("OTHERINCOME")!=null)
				Tax_dec_Map.put(rs.getString(1)+"$DE_OTHERINCOME", rs.getString("OTHERINCOME"));
				if(rs.getString("ToTAL_80D")!=null)
					Tax_dec_Map.put(rs.getString(1)+"$ToTAL_80D", rs.getString("ToTAL_80D"));
				if(rs.getString("SrCITIZEN")!=null)
					Tax_dec_Map.put(rs.getString(1)+"$CITIZEN", rs.getString("SrCITIZEN"));
				// This Below code is added for 80D Caliculation
				/*if(rs.getString("FOR_RELATION")!=null && rs.getString("FOR_RELATION").equalsIgnoreCase("Mother&Father") && rs.getString("CITIZEN")!=null ){
					
					Tax_dec_Map.put(rs.getString(1)+"$FOR_RELATION", rs.getString("FOR_RELATION"));
					Tax_dec_Map.put(rs.getString(1)+"$CITIZEN", rs.getString("CITIZEN"));
					
				}*/
						
			}
		}catch(Exception TxDecl){
			
			System.out.println("TxDecl ~~~~" +TxDecl);
			
		}
   // Above is Add For Tax Declaration 		
		
		
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
				
		try {
			rs=null;
			rs=(ResultSet)DataObj.getBasic(basic.toString(), "Basic",rs,connection);
			while(rs.next()){
				/* 24 basic
			  22 gross
			  25 Hra
			  26 Ca
			  27 Medical
			  28 Education
			  30 Pt*/
				//System.out.println("rs.getString(6)::" +rs.getString(6));

				MaxPayPeriod=rs.getString(6);

				//System.out.println("ResultSet -->1::"+rs.getString(3));
				if(rs.getString(3).equalsIgnoreCase("24")){
					
					Basic.put(rs.getString(1)+".basic", rs.getString(2));
					
					Basic.put(rs.getString(1)+".ANN_basic", rs.getString(2)); // For Actual Gross Annual Caliculation
					
					//Basic.put(rs.getString(1)+".ANN_basic", rs.getString(8)); // For Actual Gross Annual Caliculation
					
					Basic.put(rs.getString(1)+".pm", rs.getString(4));
					Basic.put(rs.getString(1)+".callname", rs.getString(7));
					Basic.put(rs.getString(1)+".preriod", rs.getString(5)+"-"+rs.getString(6));

					txemployee.append(rs.getString(1));
					txemployee.append(",");

					//empids.add(rs.getString(1));
					
				//	System.out.println("ResultSet1::"+rs.getString(1) +"basic" +rs.getString(2));
				}if(rs.getString(3).equalsIgnoreCase("22")){

					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".gross", rs.getString(2));
						Basic.put(rs.getString(1)+".ANN_gross", rs.getString(2)); 
						
						//Basic.put(rs.getString(1)+".ANN_gross", rs.getString(8));
						// For Actual Gross Annual Caliculation
					}else{
						Basic.put(rs.getString(1)+".gross", 0);
					}
				//	System.out.println("ResultSet1::"+rs.getString(1) +"gROSS" +rs.getString(2));
				}if(rs.getString(3).equalsIgnoreCase("25")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".Hra", rs.getString(2));
						Basic.put(rs.getString(1)+".ANN_Hra", rs.getString(2)); // For Actual Gross Annual Caliculation
						
						//Basic.put(rs.getString(1)+".ANN_Hra", rs.getString(8)); 
					}else{
						Basic.put(rs.getString(1)+".Hra",0);
					}
					//System.out.println("ResultSet1::"+rs.getString(1) +"hRA" +rs.getString(2));
				}if(rs.getString(3).equalsIgnoreCase("26")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+"-CA", rs.getString(2));
						Basic.put(rs.getString(1)+".ANN_CA", rs.getString(2)); // For Actual Gross Annual Caliculation
						
						//Basic.put(rs.getString(1)+".ANN_CA", rs.getString(8)); // For Actual Gross Annual Caliculation
					}else{
						Basic.put(rs.getString(1)+"-CA", 0);
					}
					//System.out.println("ResultSet1::"+rs.getString(1) +"Ca" +rs.getString(2));
				}if(rs.getString(3).equalsIgnoreCase("27")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".Medical", rs.getString(2));
						Basic.put(rs.getString(1)+".ANN_Medical", rs.getString(2)); // For Actual Gross Annual Caliculation
						//Basic.put(rs.getString(1)+".ANN_Medical", rs.getString(8)); // For Actual Gross Annual Caliculation
					}else{
						Basic.put(rs.getString(1)+".Medical", 0);
					}
					//System.out.println("ResultSet1::"+rs.getString(1) +"Medical" +rs.getString(2));
				}if(rs.getString(3).equalsIgnoreCase("28")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".Education", rs.getString(2));
						Basic.put(rs.getString(1)+".ANN_Education", rs.getString(2)); // For Actual Gross Annual Caliculation
						
						//Basic.put(rs.getString(1)+".ANN_Education", rs.getString(8)); // For Actual Gross Annual Caliculation
					}else{
						Basic.put(rs.getString(1)+".Education", 0);
					}
					//System.out.println("ResultSet1::"+rs.getString(1) +"eDUCATION" +rs.getString(2));
				}if(rs.getString(3).equalsIgnoreCase("30")){
					if(rs.getString(2)!=null){
						
						Basic.put(rs.getString(1)+".Pt", rs.getString(2));
						Basic.put(rs.getString(1)+".ANN_Pt", rs.getString(2)); // For Actual Gross Annual Caliculation
						//Basic.put(rs.getString(1)+".ANN_Pt", rs.getString(8)); // For Actual Gross Annual Caliculation
						
					}else{
						Basic.put(rs.getString(1)+".Pt", 0);
					}
				}if(rs.getString(3).equalsIgnoreCase("31")){
						if(rs.getString(2)!=null){
							Basic.put(rs.getString(1)+".PF", rs.getString(2));
							Basic.put(rs.getString(1)+".ANN_PF", rs.getString(2)); // For Actual Gross Annual Caliculation
							
							//Basic.put(rs.getString(1)+".ANN_PF", rs.getString(8)); // For Actual Gross Annual Caliculation
							
						}else{
							Basic.put(rs.getString(1)+".PF", 0);
						}
					}
				
				if(rs.getString(3).equalsIgnoreCase("33")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".LTA", rs.getString(2));
					}else{
						Basic.put(rs.getString(1)+".LTA", 0);
					}
				}
				
				// THIS will be Add For Gorss addition 97 and 98 Components
				if(rs.getString(3).equalsIgnoreCase("97")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".CTC97", rs.getString(2));
						Basic.put(rs.getString(1)+".ANNCTC97", rs.getString(2));
					}else{
						Basic.put(rs.getString(1)+".CTC97", 0);
						Basic.put(rs.getString(1)+".ANNCTC97", 0);
					}
				}
				
				if(rs.getString(3).equalsIgnoreCase("98")){
					if(rs.getString(2)!=null){
						Basic.put(rs.getString(1)+".CTC98", rs.getString(2));
						Basic.put(rs.getString(1)+".ANNCTC98", rs.getString(2));
					}else{
						Basic.put(rs.getString(1)+".CTC98", 0);
						Basic.put(rs.getString(1)+".ANNCTC98", 0);
					}
				}
				
				
					//System.out.println("ResultSet1::"+rs.getString(1) +"pF" +rs.getString(2));
				}
				//System.out.println("ResultSet1::"+rs.getString(1) +"basic" +rs.getString(2));
		}catch(Exception ee){
			System.out.println("Error At ee::"+ee);
		}
		/*rs=null;
		rs=(ResultSet)DataObj.getBasic(gross.toString(), "Gross",rs,connection);
		while(rs.next()){
			Basic.put(rs.getString(1)+".gross", rs.getString(2));
			//System.out.println("ResultSet2::"+rs.getString(1) +"gross" +rs.getString(2));
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs=null;
		rs=(ResultSet)DataObj.getBasic(Hra.toString(), "Hra",rs,connection);
		while(rs.next()){
			Basic.put(rs.getString(1)+".Hra", rs.getString(2));
			//System.out.println("ResultSet3::"+rs.getString(1) +"Hra" +rs.getString(2));
		}
		rs=null;
		rs=(ResultSet)DataObj.getBasic(Ca.toString(), "Ca",rs,connection);
		while(rs.next()){
			Basic.put(rs.getString(1)+".Ca", rs.getString(2));
			System.out.println("ResultSet2::"+rs.getString(1) +"#" +rs.getString(2));
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs=null;
		rs=(ResultSet)DataObj.getBasic(Medical.toString(), "Medical",rs,connection);
		while(rs.next()){
			Basic.put(rs.getString(1)+".Medical", rs.getString(2));
			//System.out.println("ResultSet2::"+rs.getString(1) +"#" +rs.getString(2));
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs=null;
		rs=(ResultSet)DataObj.getBasic(Education.toString(), "Education",rs,connection);
		while(rs.next()){
			Basic.put(rs.getString(1)+".Education", rs.getString(2));
			//System.out.println("ResultSet2::"+rs.getString(1) +"#" +rs.getString(2));
		}
		rs=null;
		rs=(ResultSet)DataObj.getBasic(Pt.toString(), "Pt",rs,connection);
		while(rs.next()){
			Basic.put(rs.getString(1)+".Pt", rs.getString(2));
			//System.out.println("ResultSet2::"+rs.getString(1) +"Pt" +rs.getString(2));
		}*/
		rs=null;
		try {
			  
			rs=(ResultSet)DataObj.getBasic(ArrearCom.toString(), "Other Earning Components", rs ,connection);
		 
			while(rs.next()){
				
			if(rs.getString(2).equalsIgnoreCase("80")){ //Insentive
				
				Basic.put(rs.getString(1)+"._80_ANN", rs.getString(4));
				
			}else if(rs.getString(2).equalsIgnoreCase("87")){//Arresr
				
				  Basic.put(rs.getString(1)+"._87_ANN", rs.getString(4));
				
			}else if(rs.getString(2).equalsIgnoreCase("56")){//LTA
				
				   Basic.put(rs.getString(1)+"._56_ANN", rs.getString(4));
				
			}else if(rs.getString(2).equalsIgnoreCase("71")){//PH BONUS
				
				Basic.put(rs.getString(1)+"._71_ANN", rs.getString(4));
				
			}else if(rs.getString(2).equalsIgnoreCase("79")){//Arrear PF Dedu
				
				Basic.put(rs.getString(1)+"._79_ANN", rs.getString(4));
			}else if(rs.getString(2).equalsIgnoreCase("90")){//Arrear PT Dedu
				
				Basic.put(rs.getString(1)+"._90_ANN", rs.getString(4));
			}else if(rs.getString(2).equalsIgnoreCase("94")){
				
				Basic.put(rs.getString(1)+"._94_ANN", rs.getString(4));
			}else if(rs.getString(2).equalsIgnoreCase("69")){
				
				Basic.put(rs.getString(1)+"._69_ANN", rs.getString(4));
			}
             else if(rs.getString(2).equalsIgnoreCase("57")){ // added for PB on 27-FEB-2017
				
				Basic.put(rs.getString(1)+"._57_ANN", Double.parseDouble(rs.getString(4).toString()));
			}
			
             else if(rs.getString(2).equalsIgnoreCase("99")){ // Referal Insentive added for PB on 15-09-2017
 				Basic.put(rs.getString(1)+"._99_ANN", Double.parseDouble(rs.getString(4).toString()));
 			  }
             else if(rs.getString(2).equalsIgnoreCase("100")){ // Referal OTHER LTA added for PB on 15-09-2017
  				Basic.put(rs.getString(1)+"._100_OTH_LTA", Double.parseDouble(rs.getString(4).toString()));
  			  } else if(rs.getString(2).equalsIgnoreCase("88") ){ // Referal OTHER LTA added for PB on 15-09-2017
  				Basic.put(rs.getString(1)+"._88_ADDITIONS", Double.parseDouble(rs.getString(4).toString()));
  			  }
			
			
			/// ADD OTHER EARNING COMPONENTS //DISPLAY in YEARDATA+ ADD LINK IN THIS
			
			// ADDITIONS ADD on 28-12-2017
			Basic.put(rs.getString(1)+".ANN_EAR_OTHERS", Double.parseDouble(Basic.get(rs.getString(1)+"._80_ANN").toString())
					+Double.parseDouble(Basic.get(rs.getString(1)+"._87_ANN").toString())+Double.parseDouble(Basic.get(rs.getString(1)+"._56_ANN").toString())
					+Double.parseDouble(Basic.get(rs.getString(1)+"._71_ANN").toString())+Double.parseDouble(Basic.get(rs.getString(1)+"._94_ANN").toString())+Double.parseDouble(Basic.get(rs.getString(1)+"._69_ANN").toString())+
					 Double.parseDouble(Basic.get(rs.getString(1)+"._99_ANN").toString())+
					 Double.parseDouble(Basic.get(rs.getString(1)+"._100_OTH_LTA").toString()) + Double.parseDouble(Basic.get(rs.getString(1)+"._88_ADDITIONS").toString()) );
			
			// For Annual Additions add Newly so need to remove
			Basic.put(rs.getString(1)+".ANN_EAR_OTHERS_ANN", Double.parseDouble(Basic.get(rs.getString(1)+"._80_ANN").toString())
					+ Double.parseDouble(Basic.get(rs.getString(1)+"._87_ANN").toString())+Double.parseDouble(Basic.get(rs.getString(1)+"._71_ANN").toString())+
					  Double.parseDouble(Basic.get(rs.getString(1)+"._99_ANN").toString()) + Double.parseDouble(Basic.get(rs.getString(1)+"._88_ADDITIONS").toString()) );
			
			
			Basic.put(rs.getString(1)+".ANN_DED_OTHERS", Double.parseDouble(Basic.get(rs.getString(1)+"._79_ANN").toString())+
					Double.parseDouble(Basic.get(rs.getString(1)+"._90_ANN").toString()) +
					Double.parseDouble(Basic.get(rs.getString(1)+"._99_ANN").toString()));
			//System.out.println("ProposedMonths:::"+ProposedMonths);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/// ABOVE IS FOR CALICULATION 80/INCENTIVE
/*	87 /arrear
		56/LTA
		71/PB1011HYD
		80,87,56,71
		79/arrearPF
		90/Arrear PT */

		
		rs=null;
		try {
			  if(MaxPayPeriod==null||MaxPayPeriod=="0"){
				 MaxPayPeriod=FromPeriod;
				 if(Integer.parseInt(MaxPayPeriod)<currentPayperiod ){
					
					 ToPeriod=MaxPayPeriod ;
				 }
			rs=(ResultSet)DataObj.getBasic("Select abs(TIMESTAMPDIFF(MONTH, date((concat("+MaxPayPeriod+",25))),date((concat("+ToPeriod+",25)))))+1  from dual", "ProposedPeriod", rs ,connection);
		  }else{
			  
			  if(Integer.parseInt(MaxPayPeriod)< currentPayperiod){
					
					 ToPeriod=MaxPayPeriod ;
				 }
			  
			  rs=(ResultSet)DataObj.getBasic("Select abs(TIMESTAMPDIFF(MONTH, date((concat("+MaxPayPeriod+",25))),date((concat("+ToPeriod+",25)))))  from dual", "ProposedPeriod", rs ,connection);
		  }
			  
			if(rs.next()){
				
				ProposedMonths=rs.getString(1);
				
				System.out.println("ProposedMonths:::"+ProposedMonths);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rs=null;
		try {
		//	System.out.println("buunit.toString()::"+buunit.toString());
			rs=(ResultSet)DataObj.getBasic(buunit.toString(), "buunit", rs ,connection);
			while(rs.next()){ // while Placed Instead off IF
				
				Bu_PayID_Buff.append(rs.getString(1));
				Bu_PayID_Buff.append(",");
				//Basic.put("BuPayID" , rs.getString(1)); // Comment For BU11 As Both PayStructureID
				Basic.put("BuName" , rs.getString("BuName"));
				//System.out.println("ProposedMonths:::"+ProposedMonths);
			}
			if(Bu_PayID_Buff!=null){
			Bu_PayID_Buff.append(0);
			Basic.put("BuPayID" , Bu_PayID_Buff.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList Components=new ArrayList();
		Map Components_map=new HashMap();
		StringBuffer components_fetch=new StringBuffer();
		
	//	System.out.println("Basic.get(BuPayID):::"+Basic.get("BuPayID"));
		
		StringBuffer components_new=new StringBuffer();
		components_new.append(" select distinct a.paycomponentid,b.name,c.name from hcladm_prod.tbl_pay_structure_details a,");
		components_new.append(" hcladm_prod.tbl_pay_component b, ");
		components_new.append(" hcladm_prod.tbl_pay_component_type c ");
		components_new.append(" where   ");
		components_new.append(" a.paycomponentid=b.paycomponentid AND  ");
		components_new.append(" c.componenttypeid=b.componenttypeid  and ");
		//components_new.append(" c.componenttypeid=b.componenttypeid AND a.paycomponentid not in(34,35,62,93) and ");
		
		components_new.append(" paystructureid in ("+Basic.get("BuPayID")+") and c.indexno ");
		// Changed for BU11 as components 97 and 98
		System.out.println("components_new::"+components_new.toString());
		//components_new.append(" paystructureid="+Basic.get("BuPayID")+" and c.indexno ");
        //a.paycomponentid not in(34,35,62,93)
		rs=null;
		rs=(ResultSet)DataObj.fupaid(components_new.toString(), "fupaid", rs ,connection);
		try {
			while(rs.next()){
				try{
					Components.add(rs.getString(1));
					Components_map.put(rs.getString(1)+"-NAME", rs.getString(2));
					Components_map.put(rs.getString(1)+"-ETYPE", rs.getString(3));
					
					components_fetch.append(rs.getString(1));
					components_fetch.append(",");
					//ProposedMonths=rs.getString(5);
					
					//System.out.println("ResultSet2::"+rs.getString(3) +"~"+rs.getString(4) +"~"+rs.getString(2));
				}catch(Exception ex){
					
			//		System.out.println("components_new ::"+ex);
					
					
					ex.printStackTrace();		
				}
			}
			components_fetch.append("0");
		} catch (SQLException e) {
			
		//	System.out.println("components_new  e::"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	System.out.println("components_fetch.toString().trim()::"+components_fetch.toString().trim());
		StringBuffer fupaid_total=new StringBuffer();
		fupaid_total.append(" select  distinct a.ctctransactionid ,a.componentvalue");
		fupaid_total.append(" ,c.employeesequenceno,a.componentid, a.componentvalue ANNUAL from ");
		fupaid_total.append(" hclhrm_prod.tbl_employee_ctc_details a,hclhrm_prod.tbl_employee_ctc b,hclhrm_prod.tbl_employee_primary c ");
		fupaid_total.append(" where b.effectivedate <=now() ");
		
		//fupaid_total.append(" and c.employeeid=b.employeeid and c.status=1001 and c.companyid="+companyid+" ");
		fupaid_total.append(" and c.employeeid=b.employeeid and ");
		
		fupaid_total.append(" c.employeeid in("+EmployeeIDs_Bulk+") ");
		
		fupaid_total.append(" and a.ctctransactionid=test.CallMaxFunction(c.employeeid) ");
		fupaid_total.append(" and a.ctctransactionid=b.ctctransactionid ");
		fupaid_total.append(" and a.componentid in ("+components_fetch.toString().trim()+") ");
		fupaid_total.append(" group by c.employeesequenceno ,a.componentid ");
		
		System.out.println("fupaid_total::"+fupaid_total.toString());
		
		rs=null;
		rs=(ResultSet)DataObj.fupaid(fupaid_total.toString(), "fupaid", rs ,connection);
		try {
			while(rs.next()){
				try{
					
					Components_map.put(rs.getString(3)+"-CTC-"+rs.getString(4), rs.getString(2));
					//System.out.println("ResultSet2::"+rs.getString(3) +"~"+rs.getString(4) +"~"+rs.getString(2));
				}catch(Exception ex){
					System.out.println("fupaid_total::"+ex);
					ex.printStackTrace();		
				}
			}
			
		} catch (SQLException e) {
			
			System.out.println("fupaid_total E::"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Step 11 :" +ProposedMonths);
		
		StringBuffer fupaid=new StringBuffer();
		
		fupaid.append(" select  distinct max(a.ctctransactionid) ,((max(a.componentvalue))* "+Rem_Months+") ");
		fupaid.append(" ,c.employeesequenceno,a.componentid, max(a.componentvalue) ANNUAL from ");
		fupaid.append(" hclhrm_prod.tbl_employee_ctc_details a,hclhrm_prod.tbl_employee_ctc b,hclhrm_prod.tbl_employee_primary c ");
		fupaid.append(" where b.effectivedate <=now() ");
		
		//fupaid.append(" and c.employeeid=b.employeeid and c.status=1001 and c.companyid="+companyid+" ");
		
		fupaid.append(" and c.employeeid=b.employeeid and ");
		
		fupaid.append("c.employeeid in("+EmployeeIDs_Bulk+")");
		
		fupaid.append(" and a.ctctransactionid=test.CallMaxFunction(c.employeeid) ");
		fupaid.append(" and a.ctctransactionid=b.ctctransactionid ");
		//fupaid.append(" and a.ctctransactionid=test.CallMaxFunction(c.employeeid) ");
		//fupaid.append(" and a.componentid in (22,24,25,26,27,28,30,31,33,36,70,39) ");
		fupaid.append(" and a.componentid in (22,24,25,26,27,28,30,31,33,36,70,39,97,98,93) ");
		fupaid.append(" group by c.employeesequenceno ,a.componentid ") ;
		
		//VENU CAL Changed for 
		
		/*fupaid.append(" select  distinct max(a.ctctransactionid) ,  ");
		fupaid.append(" ((max(a.componentvalue)) * ( (if(max(d.Payperiod) < "+currentPayperiod+"  , max(d.Payperiod), if(c.status=1001,"+ToPeriod+",max(d.Payperiod)  )) - max(d.Payperiod) ))) ");
		fupaid.append(" ,c.employeesequenceno,a.componentid, max(a.componentvalue) ANNUAL , (if(max(d.Payperiod) < "+currentPayperiod+" , max(d.Payperiod), if(c.status=1001,"+ToPeriod+",max(d.Payperiod)  )) - max(d.Payperiod) ) AS actproposed from ");
		fupaid.append(" hclhrm_prod.tbl_employee_ctc_details a,hclhrm_prod.tbl_employee_ctc b,hclhrm_prod.tbl_employee_primary c, ");
		fupaid.append(" hclhrm_prod.tbl_employee_pay_data d ");
		fupaid.append(" where b.effectivedate <=now() ");
		fupaid.append(" and c.employeeid=b.employeeid and ");
		fupaid.append(" c.employeeid in("+EmployeeIDs_Bulk+") ");
		fupaid.append(" and a.ctctransactionid=test.CallMaxFunction(c.employeeid) ");
		fupaid.append(" and a.ctctransactionid=b.ctctransactionid ");
		fupaid.append(" and d.employeeid=c.employeeid " );
		fupaid.append(" and d.payperiod between '"+FromPeriod+"' and '"+ToPeriod+"' ");
		fupaid.append(" and a.componentid in (22,24,25,26,27,28,30,31,33,36,70,39,97,98,93) ");
		fupaid.append(" group by c.employeesequenceno ,a.componentid ");*/
		
		
		
		rs=null;
		System.out.println("fupaid::"+fupaid);
		System.out.println("Step 12");
		
		rs=(ResultSet)DataObj.fupaid(fupaid.toString(), "fupaid", rs ,connection);
		try {
			while(rs.next()){
				try{
					//ProposedMonths=rs.getString(5);
					
					//Basic.put(rs.getString(3) +"_ProposedMonths" , rs.getString("actproposed"));
					
					Basic.put(rs.getString(3) +"_ProposedMonths" , ""+Rem_Months+"");
					
					Basic.put(rs.getString(3) +"-"+rs.getString(4) , rs.getString(2));
					
					 if(rs.getString(4).equalsIgnoreCase("33")){
						 
					   Basic.put(rs.getString(3) +".ANN_LTA", rs.getString(5));
					   
					 }if(rs.getString(4).equalsIgnoreCase("36")){
						 
						   Basic.put(rs.getString(3) +".ANN_BONUS", rs.getString(5));
						 }
					 if(rs.getString(4).equalsIgnoreCase("39")){
						 
						   Basic.put(rs.getString(3) +".ANN_BONUS_2", rs.getString(5));
						 }
					 
					   if(rs.getString(4).equalsIgnoreCase("93")){ // ADD ON 18-12-2017
						 
						   Basic.put(rs.getString(3) +".MEDICAL_ANNUAL", rs.getString(5));
						 }
				
					
			 Basic.put(rs.getString(3)+"_ANNUAL_BENIFITS",Double.parseDouble(Basic.get(rs.getString(3) +".ANN_LTA").toString())+Double.parseDouble(Basic.get(rs.getString(3) +".ANN_BONUS").toString())+Double.parseDouble(Basic.get(rs.getString(3) +".ANN_BONUS_2").toString()));
				
			//*****************************************************************************************		
			 // VENU-CHANGE1
			 
			 //rs.getString("actproposed")
			 
				if(Integer.parseInt(ProposedMonths)==0 || Integer.parseInt(ProposedMonths)==1){
					
				//if(Integer.parseInt(rs.getString("actproposed").toString())==0 || Integer.parseInt(rs.getString("actproposed").toString())==1){	
						
						//if(9==0 || 9==1){	
					
				//	 System.out.println("ANNUAL_BENIFIT_LOAD_LTA ::" +Basic.get(rs.getString(3)+"._56_ANN"));
					
					// For Mumbai we have to disabled this because they are not Paid LTA and Bonus in List
					
					 Basic.put(rs.getString(3)+"_ANNUAL_BENIFITS",0); // for who have paid months and paid gross same with respect store
					
					// Basic.put(rs.getString(3)+"_ANNUAL_BENIFITS",Double.parseDouble(Basic.get(rs.getString(3) +".MEDICAL_ANNUAL").toString()));
					 //Modified on 18-12-2017 for 80D self & Parents Annula deducted as self 80D,monthely deduction as 80DParents
					 
					 
			    }else{
			    	
			    	 /*	Double ANN_LTA= Double.parseDouble(Basic.get(rs.getString(3) +".ANN_LTA").toString());
			    	  	Double ANN_BONUS=Double.parseDouble(Basic.get(rs.getString(3) +".ANN_BONUS").toString());
			    	  	Double ANN_BONUS_2=Double.parseDouble(Basic.get(rs.getString(3) +".ANN_BONUS_2").toString());
			    	
			    	  	Basic.put(rs.getString(1)+"._56_ANN", rs.getString(4)); LTA
			    	      Basic.put(rs.getString(1)+"._90_ANN", rs.getString(4)); // Bonus
			    	      Basic.put(rs.getString(1)+"._69_ANN", rs.getString(4)); // Annual Bonus
			    	  	 		
			    	  	
			    	    Double Paid_ANN_LTA=Double.parseDouble(Basic.get(rs.getString(3) +"._56_ANN").toString());
			    	    Double Paid_ANN_BONUS=Double.parseDouble(Basic.get(rs.getString(3) +"._90_ANN").toString());
			    	    Double Paid_ANN_BONUS_2=Double.parseDouble(Basic.get(rs.getString(3) +"._69_ANN").toString());
			    	    
			    	    if(Paid_ANN_LTA>0){
			    	    	ANN_LTA=Paid_ANN_LTA;
			    	    	
			    	    }
			    	    if(Paid_ANN_BONUS>0){
			    	    	ANN_BONUS=Paid_ANN_BONUS;
			    	    	
			    	    }
			    	    if(Paid_ANN_BONUS_2>0){
			    	    	
			    	    	ANN_BONUS_2=Paid_ANN_BONUS_2;
			    	    	
			    	    }
			    	    
			    	    Basic.put(rs.getString(3)+"_ANNUAL_BENIFITS",ANN_LTA+ANN_BONUS+ANN_BONUS_2);
			    	    */
			    	    
			    	    
			    	   // need to implement data of if old LTA is paid remove from proposed LTA
			    	//  Basic.put(rs.getString(3)+"_ANNUAL_BENIFITS"
			    	
			    	
			    }
	             //********************************************************************************************	

				
				
					// Basic.put(rs.getString(3)+"_ANNUAL_BENIFITS" ,0);
					//System.out.println("ResultSet2::"+rs.getString(3) +"~"+rs.getString(4) +"~"+rs.getString(2));
				}catch(Exception ex){
					ex.printStackTrace();		
				}
			}
			//Basic.put("36.ANN_BONUS",Double.parseDouble(Basic.get("36.ANN_BONUS_1").toString())+Double.parseDouble(Basic.get("36.ANN_BONUS_2").toString()));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		//System.out.println("Step 13");
		/*StringBuffer LTA=new StringBuffer();
		LTA.append(" select  distinct max(a.ctctransactionid) ,((max(a.componentvalue)))  ");
		LTA.append(" ,c.employeesequenceno,a.componentid  from ");
		LTA.append(" hclhrm_prod.tbl_employee_ctc_details a,hclhrm_prod.tbl_employee_ctc b,hclhrm_prod.tbl_employee_primary c ");
		LTA.append(" where b.effectivedate <=now() ");
		LTA.append(" and c.employeeid=b.employeeid and c.status=1001 and c.companyid="+companyid+" ");
		LTA.append(" and a.ctctransactionid=b.ctctransactionid ");
		LTA.append(" and a.componentid in (33) ");
		LTA.append(" group by c.employeesequenceno ,a.componentid ");
		
		rs=null;
		rs=(ResultSet)DataObj.fupaid(LTA.toString(), "fupaid", rs ,connection);
		try {
			while(rs.next()){
				try{
					//ProposedMonths=rs.getString(5);
					Basic.put(rs.getString(3) +"."+LTA , rs.getString(2));
					//System.out.println("ResultSet2::"+rs.getString(3) +"~"+rs.getString(4) +"~"+rs.getString(2));
				}catch(Exception ex){
					ex.printStackTrace();		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			
			  Thread.sleep(10);
			  
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 80C 80D 80 A Caliculation  Part
		/*StringBuffer S80c=new StringBuffer();
	    fupaid.append(" select  distinct max(a.ctctransactionid) ,((max(a.componentvalue))* "+ProposedMonths+") ");
	    fupaid.append(" ,c.employeesequenceno,a.componentid  from ");
	    fupaid.append(" hclhrm_prod.tbl_employee_ctc_details a,hclhrm_prod.tbl_employee_ctc b,hclhrm_prod.tbl_employee_primary c ");
	    fupaid.append(" where b.effectivedate <=now() ");
	    fupaid.append(" and c.employeeid=b.employeeid and c.status=1001 and c.companyid="+companyid+" ");
	    fupaid.append(" and a.ctctransactionid=b.ctctransactionid ");
	    fupaid.append(" and a.componentid in (22,24,25,26,27,28,30) ");
	    fupaid.append(" group by c.employeesequenceno ,a.componentid ");
		rs=null;
		rs=(ResultSet)DataObj.fupaid(fupaid.toString(), "S80c", rs ,connection);
		try {
			while(rs.next()){
				try{
			    	//ProposedMonths=rs.getString(5);
					Basic.put(rs.getString(3) +"-"+rs.getString(4) , rs.getString(2));
					System.out.println("ResultSet2::"+rs.getString(3) +"~"+rs.getString(4) +"~"+rs.getString(2));
				}catch(Exception ex){
					ex.printStackTrace();		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//==================================		
      System.out.println("Test Case  1");
		java.util.Iterator empseq_temp = empids.iterator();
		
	// WHILE2
		while(empseq_temp.hasNext()){
			String empid=empseq_temp.next().toString();
			TaxSections.put(empid+"80C", 0);
			TaxSections.put(empid+"80D", 0);
			
			TaxSections.put(empid+"_OUT_80D_S80DDSUM", 0); // ADD FOR out 80D Caliculation
			
			
			TaxSections.put(empid+"HRENT", 0);
			TaxSections.put(empid+"CHILD", 0);
			TaxSections.put(empid+"AGE", 0);
			TaxSections.put(empid+"INHR", 0);
			TaxSections.put(empid+"MEDICALREM", 0);
			TaxSections.put(empid+"LOCATION", 0);
			TaxSections.put(empid+"Educess", 0);   
			TaxSections.put(empid+"tx_Paid", 0);
			TaxSections.put(empid+"NPS", 0);
			TaxSections.put(empid+"S80D", 0);
			TaxSections.put(empid+"D80D", 0);
			TaxSections.put(empid+"H80D", 0);
			
			TaxSections.put(empid+"SURCHARGE", 0);
			
			TaxSections.put(empid+"ACCT_SURCHARGE", 0);
			TaxSections.put(empid+"RELIF_AMT", 0);
			  /// Add New Data//
			TaxSections.put(empid+"S_80DD", 0);
			TaxSections.put(empid+"S_80DDB", 0);
			TaxSections.put(empid+"S_80U", 0);
			TaxSections.put(empid+"S_80E", 0);
			TaxSections.put(empid+"S_80CCG", 0);
			TaxSections.put(empid+"S_80TTA", 0);
			TaxSections.put(empid+"S_80G", 0);
			
			TaxSections.put(empid+"_LTA", 0);
			TaxSections.put(empid+"_BONUS", 0);
			
			TaxSections.put(empid+"_S80GP", 0);
			TaxSections.put(empid+"_S80GAMT", 0);
			
			TaxSections.put(empid+"_RENT80EE", 0);
			
			TaxSections.put(empid+"_RENTINTEREST", 0);
			
			TaxSections.put(empid+"_OTHERINCOME", 0);
			
			TaxSections.put(empid+"_80C_E", 0);
			TaxSections.put(empid+"_80D_E", 0);
			
			TaxSections.put(empid+"S_80G_E", 0);
			
			TaxSections.put(empid+"_RENT80EE_E", 0);
			TaxSections.put(empid+"_RENTINTEREST_E", 0);
			
			TaxSections.put(empid+"_EmpTax_E", 0);
			TaxSections.put(empid+"_EmpTaxPer_E", 0);
			TaxSections.put(empid+"_EmpTaxAddl_E", 0);
			
			TaxSections.put(empid+"_Emp_Tax_E",0);
			TaxSections.put(empid+"_LTA_P",0);
			
			TaxSections.put(empid+"_SURCHARGE",0);
			TaxSections.put(empid+"_TDS_F",0);
			
			TaxSections.put(empid+"_TDS_F_TEMP",0);
			
			TaxSections.put(empid+"tx_Paid_F",0);
			
			
			TaxSections.put(empid+"_ANN_EmpTax_E",0);
			TaxSections.put(empid+"_ANN_EmpTaxPer_E",0);
			TaxSections.put(empid+"_ANN_EmpTaxAddl_E",0);
			
			TaxSections.put(empid+"_ANN_SURCHARGE",0);
			
			TaxSections.put(empid+"_ANN_EmpTaxAddl_E",0);
			
			TaxSections.put(empid+".MEDICAL_ANNUAL",0);
			
			
			
			
			

			
			
		} //WHILE2E /// Main iterater while

		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		rs=null;
		rs=(ResultSet)DataObj.fupaid(EmpTds.toString(), "EmpTds", rs ,connection);
		try {
			while(rs.next()){
				try{
					TaxSections.put(rs.getString(1)+"_TDS_F_TEMP",rs.getString(2));
								}catch(Exception ex){
					    ex.printStackTrace();		
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Test Case 2");
		rs=null;
		
		Double LetOutProperty=0.00;
		int NofMonths=0;
		Double MTP_LetOut=0.00;
		Double RentIntrest_LetOut=0.00;
		
	StringBuffer TDS_DEC_NEW_MOD=new StringBuffer();
	
	// updated on 22-12-2017 for duplicate elimination with status 1001 and 1005
	TDS_DEC_NEW_MOD.append(" select  ");
	TDS_DEC_NEW_MOD.append(" A.EMPLOYEEID,A.FY,A.HQ,A.AGE,A.KIDCOUNT,A.SEC80C,A.NPS,A.S80D,A.D80D,A.H80D, ");
	TDS_DEC_NEW_MOD.append(" A.S80DDSUM,A.S80GP, ");
	TDS_DEC_NEW_MOD.append(" A.S80GAMT,A.RENT,A.RENTINTEREST, ");
	TDS_DEC_NEW_MOD.append(" A.RENTLETOUT Letout, ");
	TDS_DEC_NEW_MOD.append(" A.RENT80EE,A.OTHERINCOME,A.LTA,A.STATUS, ");
	TDS_DEC_NEW_MOD.append(" A.LUPDATE,A.MTP  ");
	TDS_DEC_NEW_MOD.append(" from test.tbl_emp_it_tds A ");
	TDS_DEC_NEW_MOD.append(" join( ");
	TDS_DEC_NEW_MOD.append(" select max(RefNo) RefNo,employeeid from test.tbl_emp_it_tds where status in(1001,1005) and fy='"+FiyYear+"' ");
	TDS_DEC_NEW_MOD.append(" and EMPLOYEEID IN("+Employee_Seq_Bulk+") group by employeeid ");
	TDS_DEC_NEW_MOD.append(" )B ON B.RefNo=A.RefNo and B.employeeid=A.employeeid  ");
	TDS_DEC_NEW_MOD.append(" where A.fy='"+FiyYear+"' and A.EMPLOYEEID IN("+Employee_Seq_Bulk+") and A.status in(1001,1005) ");
	
	
	
	StringBuffer Age_from_HRMS=new StringBuffer();
	
	Age_from_HRMS.append(" select employeesequenceno,callname,ifnull(round((DATEDIFF(now(), DATEOFBIRTH))/365)-1,0) as age ");
	Age_from_HRMS.append(" from hclhrm_prod.tbl_employee_primary ");
	Age_from_HRMS.append(" where employeesequenceno in("+Employee_Seq_Bulk+") ");
	//Age_from_HRMS.append(" where ifnull(round((DATEDIFF(now(), DATEOFBIRTH))/365)-1,0) ");
	rs=null;
	try {
		rs=(ResultSet)DataObj.getBasic(Age_from_HRMS.toString(), "TdsCaliculation", rs ,connection);
		
		while(rs.next()){
			
			TaxSections.put(rs.getString(1).toString()+"AGE", rs.getString("age"));
			
		}
		
	}catch(Exception err){
		System.out.println("Age ::" +err);
	}
	
	
	
	
		//int totalperiod2=Integer.parseInt(Basic.get(empid+".pm").toString())+Integer.parseInt(ProposedMonths);
		try {
			//System.out.println("MaxPayPeriod:::"+MaxPayPeriod);
		//	rs=(ResultSet)DataObj.getBasic("SELECT  EMPLOYEEID, FY, HQ, AGE, KIDCOUNT, SEC80C,NPS,S80D,D80D,H80D,S80DDSUM,S80GP,S80GAMT,RENT,RENTINTEREST,(((RENTLETOUT*12)-((RENTLETOUT*12)*0.3))-(((RENTLETOUT*12)-((RENTLETOUT*12)*0.3))*0.3)) Letout,RENT80EE,OTHERINCOME,LTA,STATUS, LUPDATE FROM test.tbl_emp_it_tds where fy='"+FiyYear+"' and status=1001 ", "TdsCaliculation", rs ,connection);
			
			// VENU-80C
		//For 80D DUP 22-12-2017	
		//rs=(ResultSet)DataObj.getBasic("SELECT  EMPLOYEEID, FY, HQ, AGE, KIDCOUNT, SEC80C,NPS,S80D,D80D,H80D,S80DDSUM,S80GP,S80GAMT,RENT,RENTINTEREST,RENTLETOUT Letout,RENT80EE,OTHERINCOME,LTA,STATUS, LUPDATE,MTP FROM test.tbl_emp_it_tds where fy='"+FiyYear+"' and EMPLOYEEID IN("+Employee_Seq_Bulk+") and status in(1001,1005) ", "TdsCaliculation", rs ,connection);
			
			rs=(ResultSet)DataObj.getBasic(TDS_DEC_NEW_MOD.toString(), "TdsCaliculation", rs ,connection);
				
			//(((RENTLETOUT*9)-MTP)-((((RENTLETOUT*9)-MTP))*0.3))
			System.out.println("Test Case 3"+rs);
			
			
		//	
			
			//SELECT  EMPLOYEEID 1, FY 2, HQ 3, AGE 4, KIDCOUNT 5, SEC80C 6  ,NPS 7,S80D 8,D80D 9,H80D 10,S80GP 11,S80GAMT 12,RENT 13,RENTINTEREST 14,RENTLETOUT 15,RENT80EE 16,OTHERINCOME 17,LTA 18,STATUS 19, LUPDATE FROM test.tbl_emp_it_tds;
			
	//		rs=(ResultSet)DataObj.getBasic("SELECT  EMPLOYEEID, FY, HQ, AGE, KIDCOUNT, SEC80C, SEC80OTHERS, SEC80D, SEC13AHOUSE, HOUSELOAN, NPS,S80D,D80D,H80D,STATUS, LUPDATE FROM test.tbl_emp_it_tds where fy='"+FiyYear+"'", "TdsCaliculation", rs ,connection);
	
//SELECT  EMPLOYEEID, FY, HQ, AGE, KIDCOUNT, SEC80C,NPS,S80D,D80D,H80D,S80GP,S80GAMT,RENT,RENTINTEREST,RENTLETOUT,RENT80EE,OTHERINCOME,LTA,STATUS, LUPDATE FROM test.tbl_emp_it_tds;			
			
		//	rs=(ResultSet)DataObj.getBasic("SELECT  EMPLOYEEID, FY, HQ, AGE, KIDCOUNT, SEC80C, SEC80OTHERS, SEC80D, SEC13AHOUSE, HOUSELOAN, NPS,STATUS, LUPDATE FROM test.tbl_emp_it_tds where fy='2016-2017'", "ProposedPeriod", rs ,connection);
			//S80D,D80D,H80D
			while(rs.next()){
				
				LetOutProperty=0.00;
				TaxSections.put(rs.getString(1).toString()+"80C", Double.parseDouble(rs.getString("SEC80C").toString()));
				
				TaxSections.put(rs.getString(1).toString()+"80D", Double.parseDouble(rs.getString("S80D"))+Double.parseDouble(rs.getString("D80D"))+Double.parseDouble(rs.getString("H80D"))+Double.parseDouble(rs.getString("S80DDSUM")));
				
				TaxSections.put(rs.getString(1).toString()+"_OUT_80D_S80DDSUM", Double.parseDouble(rs.getString("S80DDSUM")));
				
				TaxSections.put(rs.getString(1).toString()+"HRENT", rs.getString("RENT"));
				TaxSections.put(rs.getString(1).toString()+"CHILD", rs.getString("KIDCOUNT"));
				
				
				//TaxSections.put(rs.getString(1).toString()+"AGE", rs.getString("AGE"));
				
				System.out.println("1::");
				try{
					
			//VENU changed for resignation employess 
				
					NofMonths=Integer.parseInt(Basic.get(rs.getString(1)+".pm").toString())+Integer.parseInt(Basic.get(rs.getString(1) +"_ProposedMonths").toString());
			//	 NofMonths=Integer.parseInt(Basic.get(rs.getString(1)+".pm").toString())+Integer.parseInt(ProposedMonths);
				 
				 System.out.println(rs.getString(1)+"::Employee Working Months::"+NofMonths);
				 
				 System.out.println("2:::");
				}catch(Exception es){
					System.out.println(rs.getString(1)+"Exception At Get Working Months of Employee::"+es);
					NofMonths=0;
				}
				//NofMonths=9;
				//(((RENTLETOUT*9)-MTP)-((((RENTLETOUT*9)-MTP))*0.3))
				 MTP_LetOut=0.00;
				 RentIntrest_LetOut=0.00;
				
				 try{
					 MTP_LetOut=Double.parseDouble(rs.getString("MTP").toString());
				 }catch(Exception er){
					 System.out.println(rs.getString(1)+"Exception At Let Out property Caliculation er ::"+er);
					 MTP_LetOut=0.00;
					 
				 }
				 try{
					 RentIntrest_LetOut=Double.parseDouble(rs.getString("Letout").toString());
					 }catch(Exception er2){
						 System.out.println(rs.getString(1)+"Exception At Let Out property Caliculation er2 ::"+er2);
						 RentIntrest_LetOut=0.00;
						 
					 }
				 
				 
				
				//LetOutProperty=(((RentIntrest_LetOut*NofMonths)-MTP_LetOut)-((((RentIntrest_LetOut*NofMonths)-MTP_LetOut))*0.3));
				 double  LetOutProperty_t=0;
			       if(RentIntrest_LetOut>=MTP_LetOut){
			    	   
			    	   LetOutProperty=(((RentIntrest_LetOut*NofMonths)-MTP_LetOut)-((((RentIntrest_LetOut*NofMonths)-MTP_LetOut))*0.3));
			       }else{
						
			        LetOutProperty_t=(((RentIntrest_LetOut*NofMonths))-((((RentIntrest_LetOut*NofMonths)))*0.3));
			        LetOutProperty=LetOutProperty_t-MTP_LetOut;
			       }
			       
				
				TaxSections.put(rs.getString(1).toString()+"INHR",LetOutProperty);
				
				//Updated for based on employee working months for letout property Ex: 9,10
				//TaxSections.put(rs.getString(1).toString()+"INHR",Double.parseDouble(rs.getString("Letout")));
				
				//Double.parseDouble(rs.getString("RENTINTEREST"))  RENT80EE
				TaxSections.put(rs.getString(1).toString()+"NPS",rs.getString("NPS"));
				TaxSections.put(rs.getString(1).toString()+"_S80GP",rs.getString("S80GP"));
				TaxSections.put(rs.getString(1).toString()+"_S80GAMT",rs.getString("S80GAMT"));
				TaxSections.put(rs.getString(1).toString()+"_RENTINTEREST",Double.parseDouble(rs.getString("RENTINTEREST"))); // 20000 LIMIT updated below
				TaxSections.put(rs.getString(1).toString()+"_RENT80EE",Double.parseDouble(rs.getString("RENT80EE")));// First Loan 50,000Extra
				TaxSections.put(rs.getString(1).toString()+"_OTHERINCOME",Double.parseDouble(rs.getString("OTHERINCOME")));
				//TaxSections.put(rs.getString(1).toString()+"MEDICALREM", 0);
				TaxSections.put(rs.getString(1).toString()+"LOCATION", rs.getString("HQ"));
				
				 // Both enabled for 80D inbult Caliculation on 26-12-2017
				 TaxSections.put(rs.getString(1).toString()+"S80D", rs.getString("S80D"));
				 TaxSections.put(rs.getString(1).toString()+"D80D", rs.getString("D80D"));
				// Both enabled for 80D inbult Caliculation on 26-12-2017
				 
				 TaxSections.put(rs.getString(1).toString()+"H80D", rs.getString("H80D"));
				 
				 double Comman_$Lta=0;
				try{
					
				//Negitive
				double Tem_$Lta_paid =Double.parseDouble(Basic.get(rs.getString(1).toString()+"._56_ANN").toString())+Double.parseDouble(Basic.get(rs.getString(1).toString()+"._100_OTH_LTA").toString());
				double Tem_$Lta_Declared =Double.parseDouble( rs.getString("LTA").toString());
				
				//VENU-LTA CHANGED For Final Caliculation
				
				if(Tem_$Lta_Declared<=Tem_$Lta_paid){
					Comman_$Lta=Tem_$Lta_Declared;
				}else if(Tem_$Lta_Declared>Tem_$Lta_paid){
					Comman_$Lta=Tem_$Lta_paid;
				}
				}catch(Exception Erm){
					System.out.println("Erm-LTA table::"+Erm);
				}
					TaxSections.put(rs.getString(1).toString()+"_LTA_P", ""+Comman_$Lta+"");
					//TaxSections.put(rs.getString(1).toString()+"_LTA_P", rs.getString("LTA"));
				
				//System.out.println(rs.getString(1).toString()+" -LTA -" +rs.getString("LTA"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		System.out.println("Test Case 4");
		
		
		java.util.Iterator empseq = empids.iterator();
		//System.out.println("Befor While");
		while(empseq.hasNext()){    // Caliculation Part Start;
			
			
			
			
			String empid=null;
			double Oldgross=0.0,Newgross=0.0 ,Oldbasic=0.0,Newbasic=0.0,OldHra=0.0,NewHra=0.0,OldCa=0.0;
			double NewCa=0.0,OldMedical=0.0,NewMedical=0.0,OldEducation=0.0,NewEducation=0.0,OldPt=0.0,NewPt=0.0,OldPF=0.0,NewPF=0.0;
			
			double Oldgross_y=0.0,Newgross_y=0.0 ,Oldbasic_y=0.0,Newbasic_y=0.0,OldHra_y=0.0,NewHra_y=0.0,OldCa_y=0.0;
			double NewCa_y=0.0,OldMedical_y=0.0,NewMedical_y=0.0,OldEducation_y=0.0,NewEducation_y=0.0,OldPt_y=0.0,NewPt_y=0.0,OldPF_y=0.0,NewPF_y=0.0;
			
			
			

			try{
					empid=empseq.next().toString();
					
					// Add for different proposed Months by venu
					ProposedMonths=Basic.get(empid+"_ProposedMonths").toString();
				
				if(Basic.get(empid+".gross").toString()!=null){
					
					Oldgross=Double.parseDouble(Basic.get(empid+".gross").toString())+Double.parseDouble(Basic.get(empid+".ANN_EAR_OTHERS").toString())+Double.parseDouble(Basic.get(empid+"._57_ANN").toString())+Double.parseDouble(Basic.get(empid+"_INCOME_PRE_COMPANY").toString());
					
					// ADD FOR 97,98
					Oldgross=Oldgross+Double.parseDouble(Basic.get(empid+".CTC97").toString())+Double.parseDouble(Basic.get(empid+".CTC98").toString())
							+Double.parseDouble(Basic.get(empid+".MEDICAL_ANNUAL").toString());
					
					//Components_map.get(rs.getString(3)+"-CTC-93").toString() ADD FOR 80D Caliculation
					
					//Changed For Annual component 2
				//	System.out.println(Basic.get(empid+".ANN_EAR_OTHERS_ANN").toString()+" Oldgross_y "+empid);
					Oldgross_y=Double.parseDouble(Basic.get(empid+".ANN_gross").toString())+Double.parseDouble(Basic.get(empid+".ANN_EAR_OTHERS_ANN").toString())+Double.parseDouble(Basic.get(empid+"._57_ANN").toString())+Double.parseDouble(Basic.get(empid+"_INCOME_PRE_COMPANY").toString());
				    
					Oldgross_y=Oldgross_y+Double.parseDouble(Basic.get(empid+".ANNCTC97").toString())+Double.parseDouble(Basic.get(empid+".ANNCTC98").toString())
							+Double.parseDouble(Basic.get(empid+".MEDICAL_ANNUAL").toString());
					// ADDING for 80D Caliculation
			//*****************************************************************************************
					// VENU-CHANGE2
				System.out.println("ProposedMonths --->:" + ProposedMonths);
				
					if(Integer.parseInt(ProposedMonths)==0 || Integer.parseInt(ProposedMonths)==1){
					  Oldgross_y=Oldgross; // for who have paid months and paid gross same with respect store
				    }
		  //********************************************************************************************			
				}else{
					Oldgross=0.0;
				}
				if(Basic.get(empid+"-22").toString()!=null){
					Newgross=Double.parseDouble(Basic.get(empid+"-22").toString());
					
					if(Basic.get(empid+"-97")!=null){
					   Newgross=Newgross+Double.parseDouble(Basic.get(empid+"-97").toString());
					 }
					if(Basic.get(empid+"-98")!=null){
						Newgross=Newgross+Double.parseDouble(Basic.get(empid+"-98").toString());
					}
					
				}else{
					Newgross=0.0;
				}

				if(Basic.get(empid+".basic").toString()!=null){
					Oldbasic=Double.parseDouble(Basic.get(empid+".basic").toString());
					
					Oldbasic_y=Double.parseDouble(Basic.get(empid+".ANN_basic").toString());
					
				}else{
					Oldbasic=0.0;
				}
				if(Basic.get(empid+"-24").toString()!=null){
					Newbasic=Double.parseDouble(Basic.get(empid+"-24").toString());
				}else{
					Newbasic=0.0;
				}
				if(Basic.get(empid+".Hra").toString()!=null){
					OldHra=Double.parseDouble(Basic.get(empid+".Hra").toString());
					
					OldHra_y=Double.parseDouble(Basic.get(empid+".ANN_Hra").toString());
					
				}else{
					OldHra=0.0;
				}
				if(Basic.get(empid+"-25").toString()!=null){
					NewHra=Double.parseDouble(Basic.get(empid+"-25").toString());
				}else{
					NewHra=0.0;
				}
				if(Basic.get(empid+"-CA").toString()!=null){
					OldCa=Double.parseDouble(Basic.get(empid+"-CA").toString());
					OldCa_y=Double.parseDouble(Basic.get(empid+".ANN_CA").toString());
					//System.out.println("OldCa::"+OldCa);
				}else{
					OldCa=0.0;
				}

				if(Basic.get(empid+"-26").toString()!=null){
					NewCa=Double.parseDouble(Basic.get(empid+"-26").toString());
				}else{
					NewCa=0.0;
				}
				if(Basic.get(empid+".Medical").toString()!=null){
					OldMedical=Double.parseDouble(Basic.get(empid+".Medical").toString());
					
					OldMedical_y=Double.parseDouble(Basic.get(empid+".ANN_Medical").toString());
					
				}else{
					OldMedical=0.0;
				}
				if(Basic.get(empid+"-27").toString()!=null){
					NewMedical=Double.parseDouble(Basic.get(empid+"-27").toString());
				}else{
					NewMedical=0.0;
				}
				if(Basic.get(empid+".Education").toString()!=null){
					OldEducation=Double.parseDouble(Basic.get(empid+".Education").toString());
					OldEducation_y=Double.parseDouble(Basic.get(empid+".ANN_Education").toString());
				}else{
					OldEducation=0.0;
				}
				if(Basic.get(empid+"-28").toString()!=null){
					NewEducation=Double.parseDouble(Basic.get(empid+"-28").toString());
				}else{
					NewEducation=0.0;
				}
				if(Basic.get(empid+".Pt").toString()!=null){
					OldPt=Double.parseDouble(Basic.get(empid+".Pt").toString())+Double.parseDouble(Basic.get(empid+"._90_ANN").toString());
					OldPt_y=Double.parseDouble(Basic.get(empid+".ANN_Pt").toString())+Double.parseDouble(Basic.get(empid+"._90_ANN").toString());
				}else{
					OldPt=0.0;
				}
				if(Basic.get(empid+"-30").toString()!=null){
					//NewPt=Double.parseDouble(Basic.get(empid+"-30").toString())+100;
					NewPt=Double.parseDouble(Basic.get(empid+"-30").toString());
				}else{
					NewPt=0.0;
				}
				
				if(Basic.get(empid+".PF").toString()!=null){
					OldPF=Double.parseDouble(Basic.get(empid+".PF").toString())+Double.parseDouble(Basic.get(empid+"._79_ANN").toString());;
					OldPF_y=Double.parseDouble(Basic.get(empid+".ANN_PF").toString())+Double.parseDouble(Basic.get(empid+"._79_ANN").toString());;
				}else{
					OldPF=0.0;
				}
				if(Basic.get(empid+"-31").toString()!=null){
					NewPF=Double.parseDouble(Basic.get(empid+"-31").toString());
				}else{
					NewPF=0.0;
				}
			}catch(Exception Ett){

				System.out.println("Etts:" +Ett);
			}
			
		//	System.out.println("Test Case 5");
			//System.out.println("After while..........................!");
			//System.out.println("Oldgross:" +Oldgross);
			/* 24 basic  OldPF=0.0,NewPF=0.0
		  	22 gross
		  	25 Hra
		  	26 Ca
		  	27 Medical
		  	28 Education
		  	30 Pt
	      	//if((Newgross+Oldgross)-(OldPt+NewPt)>250000)*/
			//System.out.println(empid+"!oldGross::"+Oldbasic+"~NewGross::"+Newbasic+"~TotalGross::"+ (Oldbasic+Newbasic));
			
			//Basic.get(empid+".LTA")
			
			//TaxSections.put(rs.getString(1)+"_TDS_F_TEMP",rs.getString(2));


			TaxSections.put(empid+"_TDS_F",Double.parseDouble(TaxSections.get(empid+"_TDS_F_TEMP").toString())+Double.parseDouble(Basic.get(empid+"_INCOME_PRE_PAIDTDS").toString()));
			
			FinalComponents.put(empid+"-LTAF", formatter.format(Double.parseDouble(Basic.get(empid+".LTA").toString())));
			
		//	FinalComponents.put(empid+"-gross", formatter.format((Newgross+Oldgross)+Double.parseDouble(Basic.get(empid+".LTA").toString())));
			
			
			//Changed By 15-12-2016
			//FinalComponents.put(empid+"-gross", formatter.format((Newgross+Oldgross)));
			FinalComponents.put(empid+"-gross", formatter.format((Oldgross)));
			
		//	FinalComponents.put(empid+"-ANN_gross", formatter.format((Newgross+Oldgross_y)+Double.parseDouble(Basic.get(empid+".ANN_LTA").toString()) +Double.parseDouble(Basic.get(empid+".ANN_BONUS").toString()) ));
			
			FinalComponents.put(empid+"-ANN_gross", formatter.format((Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+"_ANNUAL_BENIFITS").toString()))));
			
			
			
			
			
			//Changed By 15-12-2016
			
			
			/*FinalComponents.put(empid+"-basic", formatter.format((Oldbasic+Newbasic)));
			FinalComponents.put(empid+"-Hra", formatter.format((OldHra+NewHra)));
			FinalComponents.put(empid+"-CA", formatter.format((OldCa+NewCa)));
			FinalComponents.put(empid+"-Medical", formatter.format((OldMedical+NewMedical)));
			FinalComponents.put(empid+"-Education", formatter.format((OldEducation+NewEducation)));
			FinalComponents.put(empid+"-Pt", formatter.format((OldPt+NewPt)));*/
			
			FinalComponents.put(empid+"-basic", formatter.format((Oldbasic)));
			FinalComponents.put(empid+"-Hra", formatter.format((OldHra)));
			FinalComponents.put(empid+"-CA", formatter.format((OldCa)));
			FinalComponents.put(empid+"-Medical", formatter.format((OldMedical)));
			FinalComponents.put(empid+"-Education", formatter.format((OldEducation)));
			FinalComponents.put(empid+"-Pt", formatter.format((OldPt)));
			
			
			FinalComponents.put(empid+"-ANN_basic", formatter.format((Oldbasic_y+Newbasic)));
			FinalComponents.put(empid+"-ANN_Hra", formatter.format((OldHra_y+NewHra)));
			FinalComponents.put(empid+"-ANN_CA", formatter.format((OldCa_y+NewCa)));
			FinalComponents.put(empid+"-ANN_Medical", formatter.format((OldMedical_y+NewMedical)));
			FinalComponents.put(empid+"-ANN_Education", formatter.format((OldEducation_y+NewEducation)));
			
			
			//Basic.get(empid+".ANN_DED_OTHERS");
			
			FinalComponents.put(empid+"-ANN_Pt", formatter.format((OldPt_y+NewPt)));
			
			
			
			
		//	FinalComponents.put(empid+"-totalgros", formatter.format((Newgross+Oldgross+Double.parseDouble(Basic.get(empid+".LTA").toString()))-(OldPt+NewPt)));
			
			
			
			//Changed By 15-12-2016
			
			//FinalComponents.put(empid+"-totalgros", formatter.format((Newgross+Oldgross)-(OldPt+NewPt)));
			
			FinalComponents.put(empid+"-totalgros", formatter.format((Oldgross)-(OldPt)));
			
		//	FinalComponents.put(empid+"-ANN_totalgros", formatter.format((Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+".ANN_LTA").toString())+Double.parseDouble(Basic.get(empid+".ANN_BONUS").toString()))-(OldPt_y+NewPt)));
			
			FinalComponents.put(empid+"-ANN_totalgros", formatter.format((Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+"_ANNUAL_BENIFITS").toString()))-(OldPt_y+NewPt)));
			
			//Changed By 15-12-2016
			//FinalComponents.put(empid+"-PF", formatter.format((OldPF+NewPF)));
			
			FinalComponents.put(empid+"-PF", formatter.format((OldPF)));
			
			//add
			
			FinalComponents.put(empid+"_ANN-PF", formatter.format((OldPF+NewPF)));
			
			//Tax Examption Caliculation
			/*TaxSections.put(empid+"80C", 0);
		 	TaxSections.put(empid+"80D", 0);
		 	TaxSections.put(empid+"HRENT", 0);
		 	TaxSections.put(empid+"CHILD", 0);
		 	TaxSections.put(empid+"AGE", 0);
		 	TaxSections.put(empid+"INHR", 0);
		 	TaxSections.put(empid+"MEDICALREM", 0);
		 	TaxSections.put(empid+"LOCATION", 0); 
		 	TaxSections.put(empid+"Educess", 0);*/
			int totalperiod=Integer.parseInt(Basic.get(empid+".pm").toString())+Integer.parseInt(ProposedMonths);
			//double HouseRent=8000.00 * totalperiod;
			
			int totalperiod_pm=Integer.parseInt(Basic.get(empid+".pm").toString());
			
			double HouseRent=Double.parseDouble(TaxSections.get(empid+"HRENT").toString()) * totalperiod;
			
			double HouseRent_pm=Double.parseDouble(TaxSections.get(empid+"HRENT").toString()) * totalperiod_pm;
			
			//************ NPS Caliculation ***************************
			double HouseRentTotal=0.0,HouseRentTotal_y=0.0,HouseRentTotal_pm=0.0;
			double CaTotal=0.0, CaTotal_pm=0.0;
			double MedicalTotal=0.0,MedicalTotal_pm=0.0;
			double ChildrenEdu=0.0,ChildrenEdu_pm=0.0;
			double Basi40=0.0,Basi40_pm=0.00,Basi40_y=0.0;
			
			if((TaxSections.get(empid+"LOCATION").toString()).equalsIgnoreCase("HYD")){
				
				//Changed By 15-12-2016
				//Basi40=Math.round(((Oldbasic+Newbasic)* 0.4));
				Basi40=Math.round(((Oldbasic)* 0.4));
				Basi40_y=Math.round(((Oldbasic_y+Newbasic)* 0.4));
				
			}else if((TaxSections.get(empid+"LOCATION").toString()).equalsIgnoreCase("MUM")){
				
				Basi40=Math.round(((Oldbasic+Newbasic)* 0.5));
				Basi40_y=Math.round(((Oldbasic_y+Newbasic)* 0.5));
				
			}else{
				TaxSections.put(empid+"LOCATION","HYD");
				//Changed By 15-12-2016
				//Basi40=Math.round(((Oldbasic+Newbasic)* 0.4));
				
				Basi40=Math.round(((Oldbasic)* 0.4));
				
				Basi40_y=Math.round(((Oldbasic_y+Newbasic)* 0.4));
			}
			//  double HouseFinal=(Oldbasic+Newbasic)-(Math.round(((Oldbasic+Newbasic)*10)/100));
			
			//Changed By 15-12-2016
			 //double HouseFinal=Math.round((Oldbasic+Newbasic)*0.1);
			
			double HouseFinal=Math.round((Oldbasic)*0.1);
			
			double HouseFinal_y=Math.round((Oldbasic_y+Newbasic)*0.1);
			double ExmpAmount=0.0;
			double FinalIncome=0.0;
			// Final Data Display Mapping
			
			TaxException.put(empid+".houserent" ,HouseRent_pm);
			TaxException.put(empid+".HouseFinal" ,Math.abs(HouseRent_pm-HouseFinal));
			TaxException.put(empid+".HouseFinal_y" ,Math.abs(HouseRent_pm-HouseFinal_y));
			
			
			/*TaxException.put(empid+".houserent" ,HouseRent);
			TaxException.put(empid+".HouseFinal" ,Math.abs(HouseRent-HouseFinal));
			TaxException.put(empid+".HouseFinal_y" ,Math.abs(HouseRent-HouseFinal_y));*/
			
			TaxException.put(empid+".Basi40" ,Basi40);  //TaxSections.put(empid+"CHILD"
			TaxException.put(empid+".Basi40_y" ,Basi40_y);
			
			if((TaxSections.get(empid+"CHILD").toString()).equalsIgnoreCase("2")){
				//TaxException.put(empid+".ChildEdu" , 200 * totalperiod_pm);
				//TaxException.put(empid+".ChildEdu_y" , 200 * totalperiod);
				//Changed By 15-12-2016
				//ChildrenEdu=Math.min((OldEducation+NewEducation), 200 * totalperiod);
				ChildrenEdu_pm=Math.min((OldEducation), 200 * totalperiod_pm);
				ChildrenEdu=Math.min((OldEducation+NewEducation), 200 * totalperiod);
				
				TaxException.put(empid+".ChildEdu" , ChildrenEdu_pm);
				TaxException.put(empid+".ChildEdu_y" , ChildrenEdu);
				
				
			}else if((TaxSections.get(empid+"CHILD").toString()).equalsIgnoreCase("1")){
				//TaxException.put(empid+".ChildEdu" , 100 * totalperiod_pm);
				
				//TaxException.put(empid+".ChildEdu_y" , 100 * totalperiod);
				//Changed By 15-12-2016
				//ChildrenEdu=Math.min((OldEducation+NewEducation), 100 * totalperiod);
				ChildrenEdu_pm=Math.min((OldEducation), 100 * totalperiod_pm);
				ChildrenEdu=Math.min((OldEducation+NewEducation), 100 * totalperiod);
				
				TaxException.put(empid+".ChildEdu" , ChildrenEdu_pm);
				TaxException.put(empid+".ChildEdu_y" , ChildrenEdu);
				
			}else{
				//TaxException.put(empid+".ChildEdu" , 0 * totalperiod_pm);
				
				//TaxException.put(empid+".ChildEdu_y" , 0 * totalperiod);
				
				//Changed By 15-12-2016
				//ChildrenEdu=Math.min((OldEducation+NewEducation), 0 * totalperiod);
				
				ChildrenEdu_pm=Math.min((OldEducation), 0 * totalperiod_pm);
				ChildrenEdu=Math.min((OldEducation+NewEducation), 0 * totalperiod);
				TaxException.put(empid+".ChildEdu" , ChildrenEdu_pm);
				TaxException.put(empid+".ChildEdu_y" , ChildrenEdu);
				
				
			}
			
			
			/*
			// this is used for display purpose only 
			TaxException.put(empid+".CA" , 1600 * totalperiod_pm);
			
			TaxException.put(empid+".CA_y" , 1600 * totalperiod);
			
			
			TaxException.put(empid+".Medical" , 1250 * totalperiod_pm);
			
			TaxException.put(empid+".Medical_y" , 1250 * totalperiod);*/
			
			if((HouseRent_pm-HouseFinal)>0){
				
				//Changed By 15-12-2016
				 //HouseRentTotal=Math.min(Math.min(Basi40, (HouseRent-HouseFinal)),(OldHra+NewHra));
				HouseRentTotal=Math.min(Math.min(Basi40, (HouseRent_pm-HouseFinal)),(OldHra_y+NewHra));
				
				//HouseRentTotal_y=Math.min(Math.min(Basi40, (HouseRent_pm-HouseFinal)),(OldHra));
			//	System.out.println("HouseRentTotal::: "+HouseRentTotal);
				
			}else{
				HouseRentTotal=0;
			}
			
			
			if((HouseRent-HouseFinal_y)>0){
				
				//Changed By 15-12-2016
				//HouseRentTotal=Math.min(Math.min(Basi40, (HouseRent-HouseFinal)),(OldHra+NewHra));
				HouseRentTotal=Math.min(Math.min(Basi40, (HouseRent_pm-HouseFinal)),(OldHra));
				
				HouseRentTotal_y=Math.min(Math.min(Basi40_y, (HouseRent-HouseFinal_y)),(OldHra_y+NewHra));
				
			}else{
				HouseRentTotal=0;
				HouseRentTotal_y=0;
			}
			
			TaxException.put(empid+".Basi40_RE" ,HouseRentTotal);
			
			TaxException.put(empid+".Basi40_RE_y" ,HouseRentTotal_y);
			
			//TaxSections.put(rs.getString(1).toString()+"INHR",Double.parseDouble(rs.getString("RENTLETOUT")));
			
			
			//TaxSections.put(rs.getString(1).toString()+"_RENTINTEREST",Double.parseDouble(rs.getString("RENTINTEREST")));
			//TaxSections.put(rs.getString(1).toString()+"_RENT80EE",Double.parseDouble(rs.getString("RENT80EE")));
			Double  _RENT80EE=0.00;
			Double  _RENTINTEREST=0.00;
			double _RENT80EE_Temp=Double.parseDouble(TaxSections.get(empid+"_RENT80EE").toString());
		//	System.out.println("Test Case 6");
			double _RENTINTEREST_Temp=Double.parseDouble(TaxSections.get(empid+"_RENTINTEREST").toString());
			
			System.out.println("Test Case 6");
			
			if(_RENT80EE_Temp>50000){
				_RENT80EE=(double)50000;
				
			}else{
				_RENT80EE= _RENT80EE_Temp;
			}
			
			Double INHR_LIMIT=Double.parseDouble(TaxSections.get(empid+"INHR").toString());
			
			/*if(INHR_LIMIT==0 &&_RENTINTEREST_Temp>200000){
				_RENTINTEREST=(double) 200000;
				
			}else{
				_RENTINTEREST= _RENTINTEREST_Temp;
				
			}*/
			
			if(_RENTINTEREST_Temp>200000){
				_RENTINTEREST=(double) 200000;
				
			}else{
				_RENTINTEREST= _RENTINTEREST_Temp;
				
			}
			
			//Below Displayed for RentIntrest changed by venu 20-12-2016
		//	TaxSections.put(empid+"_RENTINTEREST_E", _RENTINTEREST);
			
			TaxSections.put(empid+"_RENTINTEREST_E", _RENTINTEREST+_RENT80EE);
			/* if(HouseRent>0){
		  		HouseRentTotal=Math.min(Math.min(Basi40, (HouseRent-HouseFinal)),(OldHra+NewHra));
		  	}else{
			  	HouseRentTotal=0.00;
		  	}*/
			//System.out.println("HouseRentTotal::"+HouseRentTotal);
			
			//Changed By 15-12-2016
			//CaTotal=Math.min((OldCa+NewCa), 1600 * totalperiod);
			
		
			//Changed As per Caliculation 2018-2019
			
			//CaTotal=Math.min( (OldCa), 1600 * totalperiod_pm);
			CaTotal=40000;
			//double CaTotal_y=Math.min((OldCa_y+NewCa), 1600 * totalperiod);
			double CaTotal_y=40000;
			
			//End Changed As per Caliculation 2018-2019
			
            TaxException.put(empid+".CA" , CaTotal);
			
			TaxException.put(empid+".CA_y" , CaTotal_y);
			
			
			
			//System.out.println("CaTotal::"+CaTotal);
			//Changed By 15-12-2016
			//MedicalTotal=Math.min((OldMedical+NewMedical), 1250 * totalperiod);
			
		//Changed As per FY-2018-2019
			//MedicalTotal=Math.min((OldMedical), 1250 * totalperiod_pm);
			MedicalTotal=0;
			
		//	double MedicalTotal_y=Math.min((OldMedical_y+NewMedical), 1250 * totalperiod);
			double MedicalTotal_y=0;
			
			//Changed As per FY-2018-2019
             TaxException.put(empid+".Medical" , MedicalTotal);
			
			 TaxException.put(empid+".Medical_y" , MedicalTotal_y);
			 
			//Changed As per FY-2018-2019 end 
			
			
			
			
			//System.out.println("MedicalTotal::"+MedicalTotal);
			// MedicalTotal=Math.min((OldMedical+NewMedical), 15000);
			
			//Latest Add for Medical Value
			//FinalComponents.put(empid+"-Medical",MedicalTotal);
			
			TaxException.put(empid+"_CA_M",CaTotal);
			
			TaxException.put(empid+"_CA_M_y",CaTotal_y);
			
		  //	ChildrenEdu=Math.min((OldEducation+NewEducation), 200 * totalperiod);
			
			//System.out.println("ChildrenEdu::"+ChildrenEdu);
			
			
			ExmpAmount=HouseRentTotal+CaTotal+MedicalTotal+ChildrenEdu_pm+Double.parseDouble(TaxSections.get(empid+"_LTA_P").toString());
			
		//System.out.println(empid+ "~~ExmpAmount~~ ::" +ExmpAmount);
			double ExmpAmount_y=0.00;
			
			// EDU by VENU
			
	
			ExmpAmount_y=HouseRentTotal_y+CaTotal_y+MedicalTotal_y+ChildrenEdu+Double.parseDouble(TaxSections.get(empid+"_LTA_P").toString());
			
			
		//	System.out.println(empid+ "~"+HouseRentTotal_y+"~"+CaTotal_y+"~"+MedicalTotal_y+"~"+ChildrenEdu+"~"+ChildrenEdu+Double.parseDouble(TaxSections.get(empid+"_LTA_P").toString())+" ::" +ExmpAmount_y);
			
			
			//ExmpAmount=HouseRentTotal+CaTotal+MedicalTotal+ChildrenEdu;  // OLD DATA
			//System.out.println("ExmpAmount::"+ExmpAmount);
            
			
			// For TDS Fore Casting
		//	FinalComponents.put(empid+"-gross_ExAmt", formatter.format((Newgross+Oldgross+Double.parseDouble(Basic.get(empid+".LTA").toString()))-ExmpAmount));
			
			//Changed By 15-12-2016
		  //	FinalComponents.put(empid+"-gross_ExAmt", formatter.format((Newgross+Oldgross)-ExmpAmount));
			FinalComponents.put(empid+"-gross_ExAmt", formatter.format((Oldgross)-ExmpAmount));
			
			FinalComponents.put(empid+"-gross_ExAmt_y", formatter.format((Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+".LTA").toString()))-ExmpAmount_y));
			
		//	FinalComponents.put(empid+"-ANN_gross_ExAmt", formatter.format((Newgross+Oldgross+Double.parseDouble(Basic.get(empid+".ANN_LTA").toString()))-ExmpAmount_y));
			
			FinalComponents.put(empid+"-ANN_gross_ExAmt", formatter.format((Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+"_ANNUAL_BENIFITS").toString()))-ExmpAmount_y));
			
			FinalComponents.put(empid+"-ANN_gross_ExAmt_y", formatter.format((Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+"_ANNUAL_BENIFITS").toString()))-ExmpAmount_y));	
			
		//	FinalIncome=( (Newgross+Oldgross)-(OldPt+NewPt))-ExmpAmount;     // ORGINAL
	//***********************************************************************************Final Income **********************************
		//	FinalIncome=( (Newgross+Oldgross+Double.parseDouble(Basic.get(empid+".LTA").toString()))-(OldPt+NewPt))-ExmpAmount;
			
			//Changed By 15-12-2016
			//FinalIncome=( (Newgross+Oldgross+Double.parseDouble(Basic.get(empid+".LTA").toString()))-(OldPt+NewPt))-ExmpAmount;
			
			//ADD FOR FINAL CALICULATIO 80 D on 18122017
			
			FinalIncome=( (Oldgross+Double.parseDouble(Basic.get(empid+".LTA").toString()))-(OldPt))-ExmpAmount;
			double FinalIncome_y=0.00;
			FinalIncome_y=( (Newgross+Oldgross_y)-(OldPt_y+NewPt))-ExmpAmount_y;
			
			
	//	Double ANN_FinalIncome=( (Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+".ANN_LTA").toString())+Double.parseDouble(Basic.get(empid+".ANN_BONUS").toString()))-(OldPt+NewPt))-ExmpAmount;	
		
		
			Double ANN_FinalIncome=( (Newgross+Oldgross_y+Double.parseDouble(Basic.get(empid+"_ANNUAL_BENIFITS").toString()))-(OldPt_y+NewPt))-ExmpAmount_y;	
			
			TaxException.put(empid+".FinalIncome_B" ,formatter.format(FinalIncome));
			
			TaxException.put(empid+".ANN_FinalIncome_B" ,formatter.format(ANN_FinalIncome));
			
	//**********************************************************************************Final Income End *******************************		
		    //*TaxSections.put(empid+"80C", 0);
			// TaxSections.put(empid+"80D", 0);
			// Double.parseDouble(TaxSections.get(empid+"HRENT").toString()) * totalperiod;
			// FinalIncome=((FinalIncome-(Double.parseDouble(TaxSections.get(empid+"80C").toString())))-(Double.parseDouble(TaxSections.get(empid+"80D").toString())))+(Double.parseDouble(TaxSections.get(empid+"INHR").toString()));
			//  double EduCess=Math.round(FinalIncome * 0.03 ) ;
			// TaxSections.put(empid+"Educess", EduCess);
			// double Txbleamt=FinalIncome-EduCess;
			//***************************** 80C Caliculation *********************************//
			double S80Cal=Double.parseDouble(TaxSections.get(empid+"80C").toString());
			double S80Cal_Temp=0.00,S80Cal_Temp_y=0.00;
			double TotalS80Cal=0.00,TotalS80Cal_y=0.00;
			double NPS=Double.parseDouble(TaxSections.get(empid+"NPS").toString());
			double NPS1=Double.parseDouble(TaxSections.get(empid+"NPS").toString());
			   if(NPS>=50000){
				   NPS= 50000;
			   }
			   
			 //Changed By 15-12-2016
			// S80Cal_Temp=Math.round((S80Cal+OldPF+NewPF));
			 
			 S80Cal_Temp=Math.round((S80Cal+OldPF));
			 
			 S80Cal_Temp_y=Math.round((S80Cal+OldPF+NewPF));
			 
			 
			 TaxSections.put(empid+"_80C_E_y_Temp", S80Cal_Temp_y+NPS1);
			 
			 
			 if(S80Cal_Temp>=150000){
				 S80Cal_Temp=150000;
			 }
			 
			 if(S80Cal_Temp_y>=150000){
				 S80Cal_Temp_y=150000;
			 }
			 
			 
			 TotalS80Cal=Math.round(S80Cal_Temp+NPS);
			 
			 TotalS80Cal_y=Math.round(S80Cal_Temp_y+NPS);
			 
			 TaxSections.put(empid+"_80C_E", TotalS80Cal);
			 
			 TaxSections.put(empid+"_80C_E_y", TotalS80Cal_y);
			 
			// System.out.println("TotalS80Cal----------------->"+empid+":"+TotalS80Cal);
			// S80Cal_Temp=Math.abs(Math.round(S80Cal-NPS));
			//***************************** 80C Caliculation End*********************************//
		    //*********************************80D Caliculation *************************************//
			 //TaxSections.put(rs.getString(1).toString()+"S80D", rs.getString("S80D"));
			 //TaxSections.put(rs.getString(1).toString()+"D80D", rs.getString("D80D"));
			 //TaxSections.put(rs.getString(1).toString()+"H80D", rs.getString("H80D"));
			 
			 int Emp_Age=Integer.parseInt(TaxSections.get(empid+"AGE").toString());
			
			//26-12-2017 below line commented 
			//double S80D=Double.parseDouble(TaxSections.get(empid+"80D").toString());
			 
		//	 System.out.println(empid+"~S80D~"+S80D);
			 // S80D and D80D reversal Changes
			 double S80D=Double.parseDouble(TaxSections.get(empid+"D80D").toString()) + Double.parseDouble(Basic.get(empid+".OUTER_S80D").toString());
			 
			 
			
			 
			 
			 double S80D_Temp=0.00;
			 double S80D_total=0.00;
			 double D80D=Double.parseDouble(TaxSections.get(empid+"S80D").toString()) + Double.parseDouble(Basic.get(empid+".OUTER_D80D").toString());
			 double D80D_Temp=0.00;
			 double D80D_total=0.00;
			 double H80D=Double.parseDouble(TaxSections.get(empid+"H80D").toString());
			 
			 double $_OUT_80D_S80DDSUM=Double.parseDouble(TaxSections.get(empid+"_OUT_80D_S80DDSUM").toString()); //This is add for 80DD out expences
			 
			 // ADD BY VENU for 16B 80D display
			 TaxSections.put(empid+"D80D",S80D);
			 TaxSections.put(empid+"S80D",D80D);
			 
		  double _$80DD_$$= Double.parseDouble(Basic.get(empid+".OUTER_D80D").toString())+Double.parseDouble(Basic.get(empid+".OUTER_S80D").toString())
				  +Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80D").toString());
		  
			
		  Tax_dec_Map.put(empid+"$DE_80D", _$80DD_$$);
		  
		 double Total_80D_16B$=Double.parseDouble(Tax_dec_Map.get(empid+"$ToTAL_80D").toString())+Double.parseDouble(Basic.get(empid+".OUTER_D80D").toString())+Double.parseDouble(Basic.get(empid+".OUTER_S80D").toString());
		  
		  Tax_dec_Map.put(empid+"$ToTAL_80D", Total_80D_16B$);

		  
		  // ADD BY VENU for 16B 80D display end
		  
			 
			 double H80D_Temp=0.00;
			 double H80D_total=0.00;
			 
		 // enabled for 26-12-2017
			/* if(S80D>=25000 && Emp_Age<60){
				 S80D=25000; 
			 }else if(S80D>=30000 && Emp_Age>=60){
				 S80D=30000;  
			 }*/
			 
			 if(Emp_Age<60){
				 S80D=Math.min(S80D, 25000); 
			 }else if(S80D>=30000 && Emp_Age>=60){
				 S80D=Math.min(S80D, 50000);  
			 }
			 
			/* if(D80D>=30000  && Tax_dec_Map.get(empid+"$FOR_RELATION").toString().equalsIgnoreCase("Mother&Father") && Tax_dec_Map.get(empid+"$CITIZEN").toString().equalsIgnoreCase("YES") ){
				 D80D=Math.min(D80D, 30000); 
			 }else if(D80D>=30000  && Tax_dec_Map.get(empid+"$FOR_RELATION").toString().equalsIgnoreCase("Mother&Father") && Tax_dec_Map.get(empid+"$CITIZEN").toString().equalsIgnoreCase("NO") ){
			
				 D80D=Math.min(D80D, 25000); 
				 D80D=25000;
			 }*/
			 System.out.println(Tax_dec_Map.get(empid+"$FOR_RELATION").toString()+"------->");
			 
			 if(Tax_dec_Map.get(empid+"$FOR_RELATION").toString().equalsIgnoreCase("Mother&Father") && Tax_dec_Map.get(empid+"$CITIZEN").toString().equalsIgnoreCase("YES") ){
				 D80D=Math.min(D80D, 50000); 
			 }else if(Tax_dec_Map.get(empid+"$FOR_RELATION").toString().equalsIgnoreCase("Mother&Father") && Tax_dec_Map.get(empid+"$CITIZEN").toString().equalsIgnoreCase("NO") ){
				 D80D=Math.min(D80D, 25000); 
			 }
			  
			 if(H80D>=5000){
				 H80D=5000; 
			 }
			 H80D=0;
			 S80D_total=S80D+D80D+H80D +$_OUT_80D_S80DDSUM ;
	// enabled above and disabled below line  for 80D on 26-12-2017
			// S80D_total=S80D;
			 
			 TaxSections.put(empid+"80D", S80D_total);
			 TaxSections.put(empid+"_80D_E", S80D_total);
			 
		  //  System.out.println(empid + "~S80D_total~" +S80D_total);
	     //*********************************80D Caliculation End *************************************
		    
		 //******************************** Other Sections *******************************************
		    
		 
		 //******************************** Other Sections End *******************************************
		    
		
		    TaxSections.put(empid+"_RENT80EE_E", _RENT80EE);
		    
	//System.out.println(FinalIncome+"~"+(Double.parseDouble(TaxSections.get(empid+"INHR").toString()))+"~"+(Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString()))+"~"+TotalS80Cal+"~"+S80D_total+"~"+_RENT80EE+"~"+_RENTINTEREST+"~"+empid);
		   
		   
		 //   FinalIncome=FinalIncome+(Double.parseDouble(TaxSections.get(empid+"INHR").toString())+(Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString())));
		
		//    ANN_FinalIncome=ANN_FinalIncome+(Double.parseDouble(TaxSections.get(empid+"INHR").toString())+(Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString())));
			//ABOVE BOTH COMMENT  FOR FINANCIAL 2017-2018 
		    
		    
		    
		    
		    
		    
double INHR_1718=Double.parseDouble(TaxSections.get(empid+"INHR").toString()); // Less of House Property Limit Set to 200000
		    
			
			double Mainsum=0.00,subSum=0.00,subSum1=0.00,RENT_80E1718=_RENT80EE+_RENTINTEREST;
			
			subSum=INHR_1718 - RENT_80E1718;
			
			if(subSum<0){
				subSum1=-1*(subSum);
				if(_RENT80EE==0 && subSum1>200000){  // Changed 2laks to 250000 for first loan deduction  -04-12-2017
					
					Mainsum=-200000; // Changed 2laks to 250000 for first loan deduction -04-12-2017
					
				}else if(_RENT80EE>0 && subSum1>200000){ // Changed 2laks to 250000 for first loan deduction -04-12-2017
					
					     Mainsum=-(200000+_RENT80EE); // Changed 2laks to 250000 for first loan deduction -04-12-2017
				 
				}else{
					Mainsum=-1*subSum1;
				}
			}else{
				Mainsum=subSum;
			}
			
			
		    FinalIncome=FinalIncome+(Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString())+Mainsum);
			
		    ANN_FinalIncome=ANN_FinalIncome+(Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString())+Mainsum);
		    
		    
		    // Comment for Fy Year 2017-2018
		   // double CUmm_LEXAMT=(FinalIncome-_RENTINTEREST);  // only for display purpose
		   // double ANN_LEXAMT=(ANN_FinalIncome-_RENTINTEREST); // only for display purpose
		   // TaxException.put(empid+".CUmm_LEXAMT" ,formatter.format(CUmm_LEXAMT));
			//TaxException.put(empid+".ANN_LEXAMT" ,formatter.format(ANN_LEXAMT));
			
			 TaxException.put(empid+".CUmm_LEXAMT" ,formatter.format(FinalIncome));
			 TaxException.put(empid+".ANN_LEXAMT" ,formatter.format(ANN_FinalIncome));
				
			
			
			
			
			TaxException.put(empid+".HP1718LESS_LIMIT_D" ,formatter.format(subSum));
			
			TaxException.put(empid+".HP1718LESS_LIMIT_E" ,formatter.format(Mainsum));
			
			//double Txbleamt=(FinalIncome-(TotalS80Cal+S80D_total+Mainsum));
			//double ANN_Txbleamt=(ANN_FinalIncome-(TotalS80Cal_y+S80D_total+Mainsum));
			
			double Txbleamt=(FinalIncome-(TotalS80Cal+S80D_total));
			double ANN_Txbleamt=(ANN_FinalIncome-(TotalS80Cal_y+S80D_total));
			
				//Comment for 2017-2018
		    //double Txbleamt=(FinalIncome-(TotalS80Cal+S80D_total+(_RENT80EE)+(_RENTINTEREST)));
			//double ANN_Txbleamt=(ANN_FinalIncome-(TotalS80Cal_y+S80D_total+(_RENT80EE)+(_RENTINTEREST)));
			
		//	System.out.println(empid+"~Txbleamt1~" +Txbleamt);
			//Txbleamt
			 int $S80GP=0;
			 double $S80GAMT=0,$S80GAMT_temp=0;
			 $S80GP=Integer.parseInt(TaxSections.get(empid+"_S80GP").toString());
			 $S80GAMT=Double.parseDouble(TaxSections.get(empid+"_S80GAMT").toString());
			 $S80GAMT_temp=Double.parseDouble(TaxSections.get(empid+"_S80GAMT").toString());
			 if($S80GP==50 && Txbleamt>0){
				
				 $S80GAMT=Math.round($S80GAMT/2);
				
			 }
			 if($S80GP==101 && Txbleamt>0 ){
				
				 $S80GAMT=Math.min($S80GAMT, Math.round(Txbleamt*0.1));
			 }
			 if($S80GP==501 && Txbleamt>0){
				
				 $S80GAMT=Math.min(Math.round($S80GAMT/2),Math.round(Txbleamt*0.1));
			 }
			 
			 if(Txbleamt>0){
			 TaxSections.put(empid+"S_80G_E_OL", $S80GAMT);
			 }else{
				 TaxSections.put(empid+"S_80G_E_OL", ""+$S80GAMT+"");
			 }
			// System.out.println($S80GAMT+"$S80GAMT"+empid);
			// System.out.println(Txbleamt+"$Txbleamt"+empid);
			  if(Txbleamt>0){
			     Txbleamt=Txbleamt-$S80GAMT;
			  }
	//////     ADD For Yearly Caliculation
			  if($S80GP==50 && ANN_Txbleamt>0){
					
				  $S80GAMT_temp=Math.round($S80GAMT_temp/2);
					
				 }
				 if($S80GP==101 && ANN_Txbleamt>0 ){
					
					 $S80GAMT_temp=Math.min($S80GAMT_temp, Math.round(ANN_Txbleamt*0.1));
				 }
				 if($S80GP==501 && ANN_Txbleamt>0){
					
					 $S80GAMT_temp=Math.min(Math.round($S80GAMT_temp/2),Math.round(ANN_Txbleamt*0.1));
				 }
				 
				 if(ANN_Txbleamt>0){
				 TaxSections.put(empid+"S_80G_E", $S80GAMT_temp);
				 }else{
					 TaxSections.put(empid+"S_80G_E", ""+$S80GAMT_temp+"");
				 }
				// System.out.println($S80GAMT+"$S80GAMT"+empid);
				// System.out.println(Txbleamt+"$Txbleamt"+empid);
				  if(ANN_Txbleamt>0){
					  ANN_Txbleamt=ANN_Txbleamt-$S80GAMT_temp;
				  }
			 	  /////     ADD For Yearly Caliculation End
			 	  //System.out.println(empid+"~Txbleamt2~" +Txbleamt); 
				  //TaxSections.put(rs.getString(1).toString()+"_S80GP",rs.getString("S80GP"));
				  //TaxSections.put(rs.getString(1).toString()+"_S80GAMT",rs.getString("S80GAMT"));
        	TaxSections.put(empid+"TxamT", formatter.format(Txbleamt));
        	
        	TaxSections.put(empid+"ANN_TxamT", formatter.format(ANN_Txbleamt));
        	
			//System.out.println(formatter.format(4.0));
			TaxException.put(empid+".FinalIncome" ,formatter.format(FinalIncome));
			
			TaxException.put(empid+".ANN_FinalIncome" ,formatter.format(ANN_FinalIncome));
			
			TaxException.put(empid+".ExmpAmount" ,formatter.format(ExmpAmount));
			
			TaxException.put(empid+".ANN_ExmpAmount" ,formatter.format(ExmpAmount_y));
			
			//System.out.println(empid +"+Txbleamt+"+"~"+formatter.format(FinalIncome)+"~"+formatter.format(ExmpAmount)+"~"+(FinalIncome-ExmpAmount));
			//System.out.println(empid +"~Taxble Amt~" +Txbleamt);
			double txLimitCon=250000.00;
			int retval = Double.compare(Txbleamt, txLimitCon);
			
			int ANN_retval = Double.compare(ANN_Txbleamt, txLimitCon);
			
			double txLimitCon60=300000.00;
			int retval60 = Double.compare(Txbleamt, txLimitCon60);
			int ANN_retval60 = Double.compare(ANN_Txbleamt, txLimitCon60);
			double txLimitCon80=500000.00;
			int retval80 = Double.compare(Txbleamt, txLimitCon80);
			int ANN_retval80 = Double.compare(ANN_Txbleamt, txLimitCon80);
			double Tax_Temp_EduCess=0.0;
			double Tax_Temp=0.0,Tax_Temp1=0.0;
			
		//	System.out.println(Tax_Temp1+"Tax_Temp1"+empid);
			
			TaxSections.put(empid+"_Before_Dedu_tax", "0.00");
			 TaxSections.put(empid+"_Before_Dedu_Relif", "0.00");
			
			if( retval>0 && Emp_Age<60){                   // Age Under Below 60
				if(Txbleamt>=250000 && Txbleamt<=500000){
					Tax_Temp1=Txbleamt-250000.00;
				//	System.out.println(Tax_Temp1+"Tax_Temp2"+empid);
					//Tax_Temp=(Tax_Temp1 * 0.1)-5000.00;   // Tax Rebate As Fer Rule 87A/
					
					if(Txbleamt<350001){
					Tax_Temp=(Tax_Temp1 * 0.05)-2500.00; // FinalCial Year 2017-2018
					
					TaxSections.put(empid+"_Before_Dedu_tax", (Tax_Temp1 * 0.05));
					
				/*	TaxSections.put(empid+"_EmpTax_E", "250000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.1");
					TaxSections.put(empid+"_EmpTaxAddl_E", "5000");*/
					
					TaxSections.put(empid+"_EmpTax_E", "250000");  // FinalCial Year 2017-2018
					TaxSections.put(empid+"_EmpTaxPer_E", "0.05");// FinalCial Year 2017-2018
					TaxSections.put(empid+"_EmpTaxAddl_E", "2500");// FinalCial Year 2017-2018
					
					}else{
						Tax_Temp=(Tax_Temp1 * 0.05)-00.00; // FinalCial Year 2017-2018
						TaxSections.put(empid+"_Before_Dedu_tax", (Tax_Temp1 * 0.05));
					/*	TaxSections.put(empid+"_EmpTax_E", "250000");
						TaxSections.put(empid+"_EmpTaxPer_E", "0.1");
						TaxSections.put(empid+"_EmpTaxAddl_E", "5000");*/
						TaxSections.put(empid+"_EmpTax_E", "250000");  // FinalCial Year 2017-2018
						TaxSections.put(empid+"_EmpTaxPer_E", "0.05");// FinalCial Year 2017-2018
						TaxSections.put(empid+"_EmpTaxAddl_E", "0.00");// FinalCial Year 2017-2018
						
					}
					
					
				}else if(Txbleamt>500000 && Txbleamt<=1000000){
					
					
					//Tax_Temp=((Txbleamt-500000)*0.2)+25000;   // FinalCial Year 2017-2018
					
					
					Tax_Temp=((Txbleamt-500000)*0.2)+12500;
					
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					
					TaxSections.put(empid+"_EmpTax_E", "500000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.2");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
					
					
					//System.out.println(empid +"~2-Tax_Temp~" +Tax_Temp);
				}else if(Txbleamt>1000000 && Txbleamt<10000000){
					
					//Tax_Temp=((Txbleamt-1000000)*0.3)+125000;
					
					if(Txbleamt<=5000000){
					Tax_Temp=((Txbleamt-1000000)*0.3)+112500; // FinalCial Year 2017-2018
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					TaxSections.put(empid+"_EmpTax_E", "1000000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
					}else{
						Tax_Temp=((Txbleamt-1000000)*0.3)+112500; // FinalCial Year 2017-2018
						TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
						
						Double surcharge=Tax_Temp*0.1;
						Double Relif=(Txbleamt-5000000);
						Double Relif_dedu=0.00;
						
						 if(Relif<surcharge){
							// Relif_dedu=surcharge-Relif;
							 surcharge=Relif-(Relif*0.3);
						 }
						 
						 //surcharge=surcharge-Relif_dedu;
						 Tax_Temp=Tax_Temp+surcharge;
						 
						
						 
						TaxSections.put(empid+"_EmpTax_E", "1000000");
						TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_EmpTaxAddl_E", "0");
						TaxSections.put(empid+"_SURCHARGE",surcharge);
						
						
					}
					
					
					
					
				}else if(Txbleamt>=10000000){
					
					//Tax_Temp=((Txbleamt-1000000)*0.3)+125000;
					
				
					Tax_Temp=((Txbleamt-1000000)*0.3)+112500; // FinalCial Year 2017-2018
					
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					
					Double surcharge=Tax_Temp*0.15;
					Double Relif=(Txbleamt-10000000);
					Double Relif_dedu=0.00;
					
					 if(Relif<surcharge){
						 
						// Relif_dedu=surcharge-Relif;
						 surcharge=Relif-(Relif*0.3);
					 }
					// surcharge=surcharge-Relif_dedu;
					 Tax_Temp=Tax_Temp+surcharge;
					 
					
					 
					TaxSections.put(empid+"_EmpTax_E", "1000000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
					TaxSections.put(empid+"_SURCHARGE",surcharge);
					
				}
			//	System.out.println(empid +"~F-Tax_Temp~" +Tax_Temp);        // AGE 60
			}else if( retval60>0 && (Emp_Age>=60 && Emp_Age<80)){
				
				//if(Txbleamt>=300000 && Txbleamt<=500000){
				if(Txbleamt>300000 && Txbleamt<=500000){
					
					if(Txbleamt<=350000){
					Tax_Temp=((Txbleamt-300000)*0.05)-2500; // FY YEAR 2017-2018
					TaxSections.put(empid+"_Before_Dedu_tax", ((Txbleamt-300000)*0.05));
					TaxSections.put(empid+"_EmpTax_E", "300000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.05");
					TaxSections.put(empid+"_EmpTaxAddl_E", "2500");
					}else{
						
						Tax_Temp=((Txbleamt-300000)*0.05)-0.00; // FY YEAR 2017-2018
						TaxSections.put(empid+"_Before_Dedu_tax", ((Txbleamt-300000)*0.05));
						TaxSections.put(empid+"_EmpTax_E", "300000");
						TaxSections.put(empid+"_EmpTaxPer_E", "0.00");
						TaxSections.put(empid+"_EmpTaxAddl_E", "0.00");
						
					}
					
					/*Tax_Temp=((Txbleamt-300000)*0.1)-5000;
					TaxSections.put(empid+"_Before_Dedu_tax", ((Txbleamt-300000)*0.1));
					TaxSections.put(empid+"_EmpTax_E", "300000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.1");
					TaxSections.put(empid+"_EmpTaxAddl_E", "5000");*/
					
				}else if(Txbleamt>500000 && Txbleamt<=1000000){
					
					//Tax_Temp=((Txbleamt-500000)*0.2)+25000;
					Tax_Temp=((Txbleamt-500000)*0.2)+10000; // Financial Year 2017-2018
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					TaxSections.put(empid+"_EmpTax_E", "500000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.2");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
				
					
				}else if(Txbleamt>1000000 && Txbleamt<10000000){
					
					
					/*Tax_Temp=((Txbleamt-1000000)*0.3)+110000; // Financial Year 2017-2018
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					TaxSections.put(empid+"_EmpTax_E", "1000000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");*/
					if(Txbleamt<=5000000){
						Tax_Temp=((Txbleamt-1000000)*0.3)+110000; // FinalCial Year 2017-2018
						TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
						TaxSections.put(empid+"_EmpTax_E", "1000000");
						TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_EmpTaxAddl_E", "0");
						}else{
							Tax_Temp=((Txbleamt-1000000)*0.3)+110000; // FinalCial Year 2017-2018
							TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
							Double surcharge=Tax_Temp*0.1;
							Double Relif=(Txbleamt-5000000);
							Double Relif_dedu=0.00;
							 if(Relif<surcharge){
								 
								// Relif_dedu=surcharge-Relif;
								 surcharge=Relif-(Relif*0.3);
							 }
							//surcharge=surcharge-Relif_dedu;
							Tax_Temp=Tax_Temp+surcharge;
							TaxSections.put(empid+"_EmpTax_E", "1000000");
							TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
							TaxSections.put(empid+"_EmpTaxAddl_E", "0");
							TaxSections.put(empid+"_SURCHARGE",surcharge);
						}
				
				
				}else if(Txbleamt>=10000000){
					Tax_Temp=((Txbleamt-1000000)*0.3)+110000;
					Double surcharge=Tax_Temp*0.15;
					Double Relif=(Txbleamt-10000000);
					Double Relif_dedu=0.00;
					 if(Relif<surcharge){
						 
						// Relif_dedu=surcharge-Relif;
						 surcharge=Relif-(Relif*0.3);
						 
					 }
					 //surcharge=surcharge-Relif_dedu;
					 Tax_Temp=Tax_Temp+surcharge;
					
					TaxSections.put(empid+"_EmpTax_E", "1000000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
					TaxSections.put(empid+"_SURCHARGE",surcharge);
					
				}
			}else if( retval80 >0 &&  Emp_Age>=80 ){
				
				if(Txbleamt>500000 && Txbleamt<=1000000){
					//Tax_Temp=((Txbleamt-500000)*0.2)+25000;
					Tax_Temp=((Txbleamt-500000)*0.2)+0.00; //Financial Year 2017-2018
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					
					TaxSections.put(empid+"_EmpTax_E", "500000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.2");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
					
				}else if(Txbleamt>1000000 && Txbleamt<10000000){
					
					
					if(Txbleamt<=5000000){
						Tax_Temp=((Txbleamt-1000000)*0.3)+100000; // FinalCial Year 2017-2018
						TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
						TaxSections.put(empid+"_EmpTax_E", "1000000");
						TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_EmpTaxAddl_E", "0");
						}else{
							Tax_Temp=((Txbleamt-1000000)*0.3)+100000; // FinalCial Year 2017-2018
							TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
							Double surcharge=Tax_Temp*0.1;
							Double Relif=(Txbleamt-5000000);
							Double Relif_dedu=0.00;
							 if(Relif<surcharge){
								 
								// Relif_dedu=surcharge-Relif;
								 surcharge=Relif-(Relif*0.3);
							 }
							//surcharge=surcharge-Relif_dedu;
							Tax_Temp=Tax_Temp+surcharge;
							TaxSections.put(empid+"_EmpTax_E", "1000000");
							TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
							TaxSections.put(empid+"_EmpTaxAddl_E", "0");
							TaxSections.put(empid+"_SURCHARGE",surcharge);
						}
					/*Tax_Temp=((Txbleamt-1000000)*0.3)+125000;
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					TaxSections.put(empid+"_EmpTax_E", "1000000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");*/
					
				}
				else if(Txbleamt>=10000000){
					//Tax_Temp=((Txbleamt-1000000)*0.3)+125000;
					Tax_Temp=((Txbleamt-1000000)*0.3)+100000; // Financial Year 2017-2018
					TaxSections.put(empid+"_Before_Dedu_tax", Tax_Temp);
					
					Double surcharge=Tax_Temp*0.15;
					
					Double Relif=(Txbleamt-10000000);
					Double Relif_dedu=0.00;
					 if(Relif<surcharge){
						 
						// Relif_dedu=surcharge-Relif;
						 surcharge=Relif-(Relif*0.3);
					 }
					 
					 TaxSections.put(empid+"_Before_Dedu_Relif", Relif_dedu);
					 
					// surcharge=surcharge-Relif_dedu;
					 Tax_Temp=Tax_Temp+surcharge;
					
					TaxSections.put(empid+"_EmpTax_E", "1000000");
					TaxSections.put(empid+"_EmpTaxPer_E", "0.3");
					TaxSections.put(empid+"_EmpTaxAddl_E", "0");
					TaxSections.put(empid+"_SURCHARGE",surcharge);
					
				}
				
			}
			    if(Tax_Temp<0){
			    	Tax_Temp=0;
			    }
		//  ADD For Annual Caliculation 
			    
			    TaxSections.put(empid+"_ANN_SURCHARGE",0);
			    
			    TaxSections.put(empid+"_ANN_EmpTaxAddl_E",0);
			    
			    TaxSections.put(empid+"_ANN_Before_Dedu_tax", "0.00");

			    TaxSections.put(empid+"_ANN_Before_Dedu_Relif", "0.00");
			    
			    
			    Double ANN_Tax_Temp=0.00;
			 
////////////////////////////////////////////////////////////////BELOW CHANGES FOR ANNUAL DTAA ////////////////////////////////////////////////////////////////////////
			    
			    if( ANN_retval>0 && Emp_Age<60){
			    	
			    	
					if(ANN_Txbleamt>=250000 && ANN_Txbleamt<=500000){
						Tax_Temp1=ANN_Txbleamt-250000.00;
					//	System.out.println(Tax_Temp1+"Tax_Temp2"+empid);
						
						if(ANN_Txbleamt<350001){
						ANN_Tax_Temp=(Tax_Temp1 * 0.05)-2500.00;
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", (Tax_Temp1 * 0.05));
						TaxSections.put(empid+"_ANN_EmpTax_E", "250000");
						
					//	TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.1");
						
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.05");
						
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "2500");
						}else{
							ANN_Tax_Temp=(Tax_Temp1 * 0.05)-00.00;
							TaxSections.put(empid+"_ANN_Before_Dedu_tax", (Tax_Temp1 * 0.05));
							TaxSections.put(empid+"_ANN_EmpTax_E", "250000");
							TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.05");
							TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "00.00");
						}
						
						
						
					}else if(ANN_Txbleamt>500000 && ANN_Txbleamt<=1000000){
						
						ANN_Tax_Temp=((ANN_Txbleamt-500000)*0.2)+12500;
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
					    TaxSections.put(empid+"_ANN_EmpTax_E", "500000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.2");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						
						//System.out.println(empid +"~2-Tax_Temp~" +Tax_Temp);
					}else if(ANN_Txbleamt>1000000 && ANN_Txbleamt<10000000){
					
					//	ANN_Txbleamt=ANN_Txbleamt+500000; // ADD For Surcharge Caliculation
						
						//ANN_Txbleamt=6100000;
						
						if(ANN_Txbleamt<=5000000){
						ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+112500;
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						
						
						}else{
							
							
							ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+112500;
							TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
							Double surcharge=ANN_Tax_Temp*0.1;
							Double Relif=(ANN_Txbleamt-5000000);
							TaxSections.put(empid+"ACCT_SURCHARGE", surcharge);
							TaxSections.put(empid+"RELIF_AMT", Relif);
							
							Double Relif_dedu=0.00;
							 if(Relif<surcharge){
								 
								 //Relif_dedu=surcharge-Relif;
								 surcharge=Relif-(Relif*0.3);  // Changed For Surcharge Issues
							 }
							 TaxSections.put(empid+"_Before_Dedu_Relif", Relif_dedu);
							
							 //surcharge=surcharge-Relif_dedu; // Changed For Surcharge Issues
							 
							 ANN_Tax_Temp=ANN_Tax_Temp+surcharge; // THIS IS BLOCKED FOR SURCHARGE CAL
							 
							 //ANN_Tax_Temp=ANN_Tax_Temp;
							 
							TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
							TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
							TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
							TaxSections.put(empid+"_ANN_SURCHARGE",surcharge);
							
							
						}
						
					}else if(ANN_Txbleamt>=10000000){
						
						ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+112500;
						
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						
						Double surcharge=ANN_Tax_Temp*0.15;
						Double Relif=(ANN_Txbleamt-10000000);
						
						TaxSections.put(empid+"ACCT_SURCHARGE", surcharge);
						TaxSections.put(empid+"RELIF_AMT", Relif);
						
						Double Relif_dedu=0.00;
						 if(Relif<surcharge){
							 
							 //Relif_dedu=surcharge-Relif;
							 surcharge=Relif-(Relif*0.3);
						 }
						 
						 TaxSections.put(empid+"_Before_Dedu_Relif", Relif_dedu);
						 
						// surcharge=surcharge-Relif_dedu;
						 
						 ANN_Tax_Temp=ANN_Tax_Temp+surcharge;
						
						TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						TaxSections.put(empid+"_ANN_SURCHARGE",surcharge);
						
					}
				//	System.out.println(empid +"~F-Tax_Temp~" +Tax_Temp);
					
				}else if( ANN_retval60>0 && (Emp_Age>=60 && Emp_Age<80)){
					if(Txbleamt>=300000 && ANN_Txbleamt<=500000){
						
						if(ANN_Txbleamt<=350000){
						ANN_Tax_Temp=((ANN_Txbleamt-300000)*0.05)-2500;
						
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ((ANN_Txbleamt-300000)*0.05));
						TaxSections.put(empid+"_ANN_EmpTax_E", "300000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.1");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "2500"); //87A
						}else{
							
							ANN_Tax_Temp=((ANN_Txbleamt-300000)*0.05)-0.00;
							TaxSections.put(empid+"_ANN_Before_Dedu_tax", ((ANN_Txbleamt-300000)*0.1));
							TaxSections.put(empid+"_ANN_EmpTax_E", "300000");
							TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.05");
							TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "00.00"); //87A
							
						}
						
					}else if(ANN_Txbleamt>500000 && ANN_Txbleamt<=1000000){
						ANN_Tax_Temp=((ANN_Txbleamt-500000)*0.2)+10000;
                        
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);

						TaxSections.put(empid+"_ANN_EmpTax_E", "500000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.2");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						
					}else if(ANN_Txbleamt>1000000 && ANN_Txbleamt<10000000){
						
						if(ANN_Txbleamt<=5000000){
						ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+110000;
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						}else{
							
							ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+110000;
							TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
							Double surcharge=ANN_Tax_Temp*0.1;
							Double Relif=(ANN_Txbleamt-5000000);
							
							TaxSections.put(empid+"ACCT_SURCHARGE", surcharge);
							TaxSections.put(empid+"RELIF_AMT", Relif);
							
							
							Double Relif_dedu=0.00;
							 if(Relif<surcharge){
								 
								// Relif_dedu=surcharge-Relif;
								 surcharge=Relif-(Relif*0.3);
							 }
							
							 TaxSections.put(empid+"_ANN_Before_Dedu_Relif", Relif_dedu);
							 
							// surcharge=surcharge-Relif_dedu;
							 
							 ANN_Tax_Temp=ANN_Tax_Temp+surcharge;
							
							TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
							TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
							TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
							TaxSections.put(empid+"_ANN_SURCHARGE",surcharge);
							
						}
						
					}else if(ANN_Txbleamt>=10000000){
						ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+110000;
						
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						
						Double surcharge=ANN_Tax_Temp*0.15;
						Double Relif=(ANN_Txbleamt-10000000);
						
						TaxSections.put(empid+"ACCT_SURCHARGE", surcharge);
						TaxSections.put(empid+"RELIF_AMT", Relif);
						
						
						Double Relif_dedu=0.00;
						 if(Relif<surcharge){
							 
							// Relif_dedu=surcharge-Relif;
							 surcharge=Relif-(Relif*0.3);
						 }
						
						 TaxSections.put(empid+"_ANN_Before_Dedu_Relif", Relif_dedu);
						 
						 //surcharge=surcharge-Relif_dedu;
						 
						 ANN_Tax_Temp=ANN_Tax_Temp+surcharge;
						
						TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						TaxSections.put(empid+"_ANN_SURCHARGE",surcharge);
						
					}
				}else if( ANN_retval80 >0 &&  Emp_Age>=80 ){
					
					
					if(ANN_Txbleamt>500000 && ANN_Txbleamt<=1000000){
						
						
						ANN_Tax_Temp=((ANN_Txbleamt-500000)*0.2)+0.00;
						
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						
						TaxSections.put(empid+"_ANN_EmpTax_E", "500000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.2");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						
					}else if(ANN_Txbleamt>1000000 && ANN_Txbleamt<10000000){
						
						
						if(Txbleamt<=5000000){
						ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+100000;
						
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						
						TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						}else{
							
							ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+100000;
							
							TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
							
							Double surcharge=ANN_Tax_Temp*0.1;
							Double Relif=(ANN_Txbleamt-5000000);
							
							TaxSections.put(empid+"ACCT_SURCHARGE", surcharge);
							TaxSections.put(empid+"RELIF_AMT", Relif);
							
							Double Relif_dedu=0.00;
							 if(Relif<surcharge){
								 
								 //Relif_dedu=surcharge-Relif;
								 surcharge=Relif-(Relif*0.3);
							 }
							 
							 TaxSections.put(empid+"_Before_Dedu_Relif", Relif_dedu);
							 
							// surcharge=surcharge-Relif_dedu;
							 Tax_Temp=Tax_Temp+surcharge;
							
							TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
							TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
							TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
							TaxSections.put(empid+"_ANN_SURCHARGE",surcharge);
							
							
						}
						
					}
					else if(ANN_Txbleamt>=10000000){
						ANN_Tax_Temp=((ANN_Txbleamt-1000000)*0.3)+100000;
						
						TaxSections.put(empid+"_ANN_Before_Dedu_tax", ANN_Tax_Temp);
						
						Double surcharge=ANN_Tax_Temp*0.15;
						Double Relif=(ANN_Txbleamt-10000000);
						
						TaxSections.put(empid+"ACCT_SURCHARGE", surcharge);
						TaxSections.put(empid+"RELIF_AMT", Relif);
						
						Double Relif_dedu=0.00;
						 if(Relif<surcharge){
							// Relif_dedu=surcharge-Relif;
							 
							 surcharge=Relif-(Relif*0.3);
						 }
						 
						 TaxSections.put(empid+"_Before_Dedu_Relif", Relif_dedu);
						 
						// surcharge=surcharge-Relif_dedu;
						 Tax_Temp=Tax_Temp+surcharge;
						
						TaxSections.put(empid+"_ANN_EmpTax_E", "1000000");
						TaxSections.put(empid+"_ANN_EmpTaxPer_E", "0.3");
						TaxSections.put(empid+"_ANN_EmpTaxAddl_E", "0");
						TaxSections.put(empid+"_ANN_SURCHARGE",surcharge);
						
					}
					
				}
				    if(ANN_Tax_Temp<0){
				    	ANN_Tax_Temp=0.00;
				    }
					  
	
	  // ADD For Annual Caliculation End
			    
			    TaxSections.put(empid+"_Emp_Tax_E", formatter.format(Tax_Temp));
			    
			    TaxSections.put(empid+"_ANN_Emp_Tax_E", formatter.format(ANN_Tax_Temp));
			    
			 //   System.out.println(Tax_Temp+ "Tax_Temp"+empid);
			    
			    double EduCess=Math.round(Tax_Temp * 0.04) ;
			    double ANN_EduCess=Math.round(ANN_Tax_Temp * 0.04) ;
				
				
				Tax_Temp_EduCess=Math.round(Tax_Temp+EduCess);
				
				//19-12-2016 Annual Tax Temp EDU Cess
				
				//Double ANN_Tax_Temp_EduCess=(double) Math.round(ANN_Tax_Temp+EduCess);
				Double ANN_Tax_Temp_EduCess=(double) Math.round(ANN_Tax_Temp+ANN_EduCess);
				
				
				
				TaxSections.put(empid+"Educess", EduCess);
				
				TaxSections.put(empid+"ANN_Educess", ANN_EduCess);
				
				
				Double Final_Tax=(double) Math.round(Tax_Temp_EduCess - Double.parseDouble(TaxSections.get(empid+"_TDS_F").toString()));
				
				Double ANN_Final_Tax=(double) Math.round(ANN_Tax_Temp_EduCess - Double.parseDouble(TaxSections.get(empid+"_TDS_F").toString()));
				
				TaxSections.put(empid+"tx_Paid", formatter.format(Tax_Temp_EduCess));
				
				
				TaxSections.put(empid+"ANN_tx_Paid", formatter.format(ANN_Tax_Temp_EduCess));
				
				TaxSections.put(empid+"tx_Paid_F", formatter.format(Final_Tax));
				
				TaxSections.put(empid+"ANN_tx_Paid_F", formatter.format(ANN_Final_Tax));
				
			 int	Total_Months=(Integer.parseInt(Basic.get(empid+".pm").toString()))+(Integer.parseInt(ProposedMonths));
			 
			 Double Paid_For_Month= (double) Math.round((Final_Tax/Integer.parseInt(ProposedMonths)));
			 
			 if(Final_Tax>0){
			    TaxSections.put(empid+"Paid_Month", formatter.format(Paid_For_Month));
			 }else{
				 TaxSections.put(empid+"Paid_Month", 0);
			 }
				taxempids.add(empid.toString());  
				
				Basic.put(empid.toString()+".ProposedMonths", ProposedMonths); // ADD FOR BULK DATA RETRIVING
			  //Total=Math.min(Math.min(a, b), c);
		} // Caliculation Part End;
		PaidDatainfo Paidinfo=new PaidDatainfo();
		
		
		System.getProperties().setProperty("xr.util-logging.loggingEnabled", "true");
		//XRLog.setLoggingEnabled(true);
		
		
		try{
			//ForCast.myCode(ProposedMonths,Basic,TaxException,taxempids,empids,FinalComponents,TaxSections);
		
			 ForCast.myCode(ProposedMonths,Basic,TaxException,taxempids,empids,FinalComponents,TaxSections,Date_Path);
			
		}catch(Exception Fo){
			System.out.println("ForCast Generated Error::"+Fo);
		}
		
		try{
			
			 Paidinfo.myCode(ProposedMonths,Basic,TaxException,taxempids,empids,FinalComponents,TaxSections,Components,Components_map,yeardata.toString(),connection,Date_Path);
		
		//	Paidinfo.myCode(String proposedMonths, Map basic, Map taxException, ArrayList taxempids, ArrayList empids, Map finalComponents, Map TaxSections,List Components,Map Components_map,String yeardata,Connection connection,String Date_Path);
			
			
		}catch(Exception Fo1){
			System.out.println("ForCast Generated Error2::"+Fo1);
		}
	
		// Jrxml Calling  Multiple ParaMeter Calling
		
	try{
			
		   Form16B.generateReport(ProposedMonths,Basic,TaxException,taxempids,empids,FinalComponents,TaxSections,Components,Components_map,yeardata.toString(),Tax_dec_Map,connection,Date_Path);
			//Paidinfo.myCode(ProposedMonths,Basic,TaxException,taxempids,empids,FinalComponents,TaxSections,Components,Components_map,yeardata.toString(),connection);
		}catch(Exception Fo2){
			System.out.println("ForCast Generated Error2::"+Fo2);
		}
		  
		
	    empids_main.addAll(empids);
        taxempids_main.addAll(taxempids);
     
      } //WHILE1E
      
      
      /////////////////////////Main Bu Iterator Closing
      
		// Jrxml Calling  From16b
      
      
		
		request.setAttribute("ProposedMonths",ProposedMonths);
		request.setAttribute("Basic",Basic);
		
		request.setAttribute("TaxException",TaxException);
		
		/*request.setAttribute("taxempids",taxempids);
		
		request.setAttribute("empids",empids);*/
		
            request.setAttribute("taxempids",taxempids_main);
			request.setAttribute("empids",empids_main);
		
		
		request.setAttribute("FinalComponents",FinalComponents);
		request.setAttribute("TaxSections",TaxSections);
		
		session.setAttribute("ProposedMonths",ProposedMonths);
		session.setAttribute("Basic",Basic);
		session.setAttribute("TaxException",TaxException);
		/*session.setAttribute("taxempids",taxempids);
		session.setAttribute("empids",empids);
		*/
		session.setAttribute("taxempids",taxempids_main);
		session.setAttribute("empids",empids_main);
		
		session.setAttribute("FinalComponents",FinalComponents);
		session.setAttribute("TaxSections",TaxSections);
		//System.out.println("Request Dispatcher Fineshed.....");
		 //RequestDispatcher rd = request.getRequestDispatcher("24q.jsp");
		 rd = request.getRequestDispatcher("tds/display3.jsp");
		 rd.forward(request, response);
      }else{
    	  request.setAttribute("message","Invalid employee ID / Payroll not generated for this employee.");
    	  rd = request.getRequestDispatcher("tds/display3error.jsp");
 		  rd.forward(request, response);
      }
	}
}
