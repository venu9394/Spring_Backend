// 
// Decompiled by Procyon v0.5.36
// 

package com.hhcdesk.service;

import java.util.ListIterator;
import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import com.mysql.jdbc.Statement;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.util.Random;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.ArrayList;
import com.hhcdesk.db.BatchInsertRecords;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.hhcdesk.db.GetDbData;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class ManagerApprovals extends HttpServlet
{
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final ServletContext c = this.getServletContext();
        final HttpSession session = request.getSession(false);
        final Statement statement = null;
        final DataSource dataSource = null;
        Connection conn = null;
        PreparedStatement ps = null;
        final Map User_obj = (Map)session.getAttribute("User_Main_Obj");
        final PrintWriter out = response.getWriter();
        final Map UserMap = new HashMap();
        int call_month = 0;
        final boolean ConnFlag = true;
        int User_Auth = 0;
        final GetDbData DataObj = new GetDbData();
        final JSONObject jason = new JSONObject();
        final JSONObject DOJ_DOB = new JSONObject();
        final JSONObject EM_DOB = new JSONObject();
        final JSONObject Login_bu_jason = new JSONObject();
        final JSONObject PaySlip = new JSONObject();
        final JSONObject ForeCast = new JSONObject();
        final JSONObject ATT_MONTHS = new JSONObject();
        final JSONObject ATT_MONTHS_1 = new JSONObject();
        JSONObject Doj22 = new JSONObject();
        final int dobcnt = 0;
        int dobcnt2 = 0;
        JSONArray values = new JSONArray();
        final JSONArray values2 = new JSONArray();
        final int Month_Size = 0;
        final int Month_Size2 = 0;
        final Map BulkDates_map = new HashMap();
        final Map BulkDates_map2 = new HashMap();
        String Routing = request.getParameter("Routing");
        final String Routing_type = request.getParameter("Routing_type");
        String Month_Sel = request.getParameter("Month_Sel");
        String EMPID = null;
        String RID = null;
        String TB_STATUS = null;
        final String APPMOD = request.getParameter("APP_MOD");
        String HR_ATT_CAL_MODE = request.getParameter("ATT_MOD_F_HR");
        String ATT_MOD_F_HR_BU = request.getParameter("ATT_MOD_F_HR_BU");
        if (HR_ATT_CAL_MODE == null) {
            HR_ATT_CAL_MODE = "MONTH";
        }
        String Month = "currmonth";
        if (Month_Sel == null) {
            Month_Sel = "currmonth";
        }
        String strTime = "0";
        try {
            final SimpleDateFormat sdfTime = new SimpleDateFormat("dd-MM-yyyy");
            final Date now = new Date();
            strTime = "0";
            strTime = sdfTime.format(now);
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        final String Routing_temp = Routing;
        if (Routing.equalsIgnoreCase("ATTENDANCE_BIO_DATES")) {
            Routing = "ATTENDANCE_BIO";
        }
        String date = null;
        String Req_message = null;
        String subject = null;
        String random = null;
        String Atten_Req_Message = "Success Fully Submit Your Request";
        if (Routing.equalsIgnoreCase("Att_Request")) {
            date = request.getParameter("id");
            Req_message = request.getParameter("message");
            subject = request.getParameter("Subject");
            random = request.getParameter("RanDm");
        }
        if (Routing.equalsIgnoreCase("ManagerAppr_req")) {
            EMPID = request.getParameter("EMPID");
            RID = request.getParameter("RID");
            TB_STATUS = request.getParameter("TAB_NAME");
        }
        session.setAttribute("Notice", (Object)"N");
        final BatchInsertRecords BatchInsert = new BatchInsertRecords();
        final ArrayList MasterDataList = new ArrayList();
        final ArrayList<PrepareData> list = new ArrayList<PrepareData>();
        final Map FetchRc = new HashMap();
        final StringBuffer biodata = new StringBuffer();
        final StringBuffer Emp_DOB = new StringBuffer();
        final StringBuffer Emp_DOJ = new StringBuffer();
        final StringBuffer Emp_DOJ2 = new StringBuffer();
        final StringBuffer Months_ATT = new StringBuffer();
        final StringBuffer Months_ATT_1 = new StringBuffer();
        final StringBuffer DepAtt_Leav = new StringBuffer();
        Map DepAtt_Leav_map = new HashMap();
        Map DepAtt_Leav_map_HL = new HashMap();
        Map ATT_APP = new HashMap();
        final ArrayList DOB = new ArrayList();
        final ArrayList DOJ = new ArrayList();
        final ArrayList DOJ_DEPT = new ArrayList();
        final StringBuffer HLDAYLIST = new StringBuffer();
        final Map hlday = new HashMap();
        final Map Att_Req = new HashMap();
        JSONObject Doj23 = new JSONObject();
        final ResourceBundle bundle_info = (ResourceBundle)c.getAttribute("bundle");
        final String Query = bundle_info.getString("HHCL_DESK_USER_LOGIN");
        final String HHCL_EVENT_INFO = bundle_info.getString("HHCL_DESK_EVENT");
        String message = null;
        message = bundle_info.getString("HHCL_DESK_NEWJOINEE");
        try {
            conn = (Connection)session.getAttribute("ConnectionObj");
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        final String _FAIL_PAGE = "/NewJoinee_Faild.jsp";
        String _SUCCESS_PAGE = null;
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        if (Routing != null && (Routing.equalsIgnoreCase("NewJoine") || Routing.equalsIgnoreCase("HOLIDAYS") || Routing.equalsIgnoreCase("BIRTHADYS") || Routing.equalsIgnoreCase("ATTENDANCE_BIO") || Routing.equalsIgnoreCase("Att_Request") || Routing.equalsIgnoreCase("ManagerAppr") || Routing.equalsIgnoreCase("ManagerAppr_att") || Routing.equalsIgnoreCase("ManagerAppr_req") || Routing.equalsIgnoreCase("ManagerAppr_Resi") || Routing.equalsIgnoreCase("Dept_att") || Routing.equalsIgnoreCase("Dept_att_HR"))) {
            username = (String)session.getAttribute("EMP_ID");
            password = (String)session.getAttribute("EMP_PASSWORD");
        }
        final String invalid = null;
        final StringBuffer User_Authen = new StringBuffer();
        User_Authen.append(Query);
        if (Routing.equalsIgnoreCase("NewJoine")) {
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
        if (Routing.equalsIgnoreCase("HOLIDAYS")) {
            Emp_DOJ.append("SELECT concat(trim(DATE_FORMAT(A.HOLIDAYDATE,'%d-%M-%Y')),',',CONCAT(trim(DAYNAME(A.HOLIDAYDATE)))) EVENTDATE,");
            Emp_DOJ.append("A.COMMENTS EVENT,   ");
            Emp_DOJ.append("IF(NOW()<A.HOLIDAYDATE,'UPCOMING','CLOSE') FLAG,IFNULL(B.NAME,'REGULAR') HOLIDAYTYPE   ");
            Emp_DOJ.append("FROM   ");
            Emp_DOJ.append("HCLHRM_PROD.TBL_HOLIDAYS A   ");
            Emp_DOJ.append("LEFT JOIN HCLHRM_PROD.TBL_HOLIDAY_TYPE B ON A.HOLIDAYTYPEID=B.HOLIDAYTYPEID   ");
            Emp_DOJ.append("WHERE A.BUSINESSUNITID IN (   ");
            Emp_DOJ.append("SELECT COMPANYID FROM HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY WHERE EMPLOYEESEQUENCENO IN (" + username + "))   ");
            Emp_DOJ.append(" and A.statusid=1001 ");
            Emp_DOJ.append("order by A.HOLIDAYDATE desc ");
        }
        if (Routing.equalsIgnoreCase("BIRTHADYS")) {
            Emp_DOJ.append(" SELECT A.CALLNAME NAME,E.NAME,C.NAME WISHHIM,D.EMAIL,D.MOBILE FROM    ");
            Emp_DOJ.append(" hclhrm_prod.tbl_employee_primary A   ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID   ");
            Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT C ON B.DEPARTMENTID=C.DEPARTMENTID   ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_CONTACT D ON A.EMPLOYEEID=D.EMPLOYEEID   ");
            Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_businessunit E ON E.BUSINESSUNITID=A.COMPANYID   ");
            Emp_DOJ.append(" WHERE A.STATUS=1001 AND DATE_FORMAT(A.DATEOFBIRTH,'%m-%d')=DATE_FORMAT(now(),'%m-%d')");
        }
        if (Routing.equalsIgnoreCase("DEPINFO_1")) {
            Emp_DOJ.append(" SELECT A.CALLNAME EMPNAME,E.NAME businessunit,C.NAME Department,trim(F.name) designation,trim(D.EMAIL),D.MOBILE FROM    ");
            Emp_DOJ.append(" hclhrm_prod.tbl_employee_primary A   ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID   ");
            Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT C ON B.DEPARTMENTID=C.DEPARTMENTID   ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_CONTACT D ON A.EMPLOYEEID=D.EMPLOYEEID     ");
            Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_businessunit E ON E.BUSINESSUNITID=A.COMPANYID   ");
            Emp_DOJ.append(" LEFT JOIN hcladm_prod.tbl_designation F ON B.designationid=F.designationid   ");
            Emp_DOJ.append(" WHERE A.STATUS=1001 and C.DEPARTMENTID=(select DEPARTMENTID from HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS   ");
            Emp_DOJ.append(" where employeeid in(select employeeid from hclhrm_prod.tbl_employee_primary where employeesequenceno in(" + username + ")))  ");
        }
        ResultSet Res = null;
        final StringBuffer TxnDateOFMgm = new StringBuffer();
        TxnDateOFMgm.append("\tSELECT C.FROMDATE,A.employeeid FROM ");
        TxnDateOFMgm.append("\tHCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
        TxnDateOFMgm.append(" JOIN ");
        TxnDateOFMgm.append(" (SELECT MAX(TRANSACTIONDURATION) PP,BUSINESSUNITID FROM HCLADM_PROD.TBL_TRANSACTION_DATES WHERE TRANSACTIONTYPEID=1 ");
        TxnDateOFMgm.append(" GROUP BY BUSINESSUNITID) B ON A.COMPANYID=B.BUSINESSUNITID ");
        TxnDateOFMgm.append(" JOIN HCLADM_PROD.TBL_TRANSACTION_DATES C ON B.BUSINESSUNITID=C.BUSINESSUNITID AND C.TRANSACTIONTYPEID=1 AND B.PP=C.TRANSACTIONDURATION ");
        TxnDateOFMgm.append(" \tWHERE ");
        TxnDateOFMgm.append(" A.EMPLOYEESEQUENCENO IN (" + username + ") ");
        String string = "2030-01-01";
        String Manager_id = "1001111";
        try {
            Res = DataObj.FetchData(TxnDateOFMgm.toString(), "DepAtt_Leav_Colors", Res, conn);
            while (Res.next()) {
                string = Res.getString(1).toString();
                Manager_id = Res.getString(2).toString();
            }
        }
        catch (Exception Er) {
            System.out.println(" Get Months Exception At 202 Er::" + Er);
        }
        if (Routing.equalsIgnoreCase("ManagerAppr")) {
        	
        	
        	
           /* Emp_DOJ.append(" SELECT A.EMPLOYEESEQUENCENO ID,A.CALLNAME NAME,E.NAME DEPT,B.SUBJECT,  ");
            Emp_DOJ.append(" CONCAT(DATE_FORMAT(B.FROM_DATE,'%d-%m-%Y'),'<--->',DATE_FORMAT(B.TO_DATE,'%d-%m-%Y')) DURATION, ");
            Emp_DOJ.append(" IFNULL(C.TOTA_DAYS,DATEDIFF(B.TO_DATE,B.FROM_DATE)+1) DAYS,C.LEAVE_TYPE, ");
            Emp_DOJ.append(" IF(B.FLAG='A','Approved',IF(B.FLAG='R','Rejected','Pending'))Manager_Status,B.RID,B.FLAG,B.MESSAGE ");
            Emp_DOJ.append("  , if(DATE_FORMAT(B.FROM_DATE,'%Y-%m-%d')<'2019-01-01' ,1,0) BUTTACT  FROM ");
            Emp_DOJ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_ATTN_REQ B ON A.EMPLOYEESEQUENCENO=B.EMPLOYEEID ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_LEAVE_REQ C ON B.RID=C.RID ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON A.EMPLOYEEID=D.EMPLOYEEID ");
            Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT E ON D.DEPARTMENTID=E.DEPARTMENTID ");
            Emp_DOJ.append(" WHERE B.RID>73 AND  B.REQ_TYPE='LR' and  B.EMPLOYEEID!=" + username + " and  B.FLAG in('P','A') AND B.EMPLOYEEID is not null  ");
            Emp_DOJ.append(" AND B.FROM_DATE >= '" + string + "' AND ");
            Emp_DOJ.append(" D.MANAGERID in (" + Manager_id + ") ");
            Emp_DOJ.append(" OR LOWER(B.TO_EMAIL) LIKE '%" + User_obj.get("emp_mailid") + "%' ");
            Emp_DOJ.append(" AND B.FLAG!=NULL ");*/
            
        	Emp_DOJ.append("  SELECT A.EMPLOYEESEQUENCENO ID,A.CALLNAME NAME,E.NAME DEPT,B.SUBJECT,  ");
			Emp_DOJ.append("  CONCAT(DATE_FORMAT(B.FROM_DATE,'%d-%m-%Y'),'<--->',DATE_FORMAT(B.TO_DATE,'%d-%m-%Y')) DURATION, ");
			Emp_DOJ.append("  IFNULL(C.TOTA_DAYS,DATEDIFF(B.TO_DATE,B.FROM_DATE)+1) DAYS,C.LEAVE_TYPE, ");
			Emp_DOJ.append("  IF(B.FLAG='A','Approved',IF(B.FLAG='R','Rejected','Pending')) Manager_Status,B.RID,B.FLAG,B.MESSAGE,txn.TODATE, ");
			 Emp_DOJ.append("  if(DATE_FORMAT(B.FROM_DATE,'%Y-%m-%d')<'2019-01-01' ,1,0) BUTTACT ");
			Emp_DOJ.append("  FROM ");
			Emp_DOJ.append("  HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
			Emp_DOJ.append("  LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_ATTN_REQ B ON A.EMPLOYEESEQUENCENO=B.EMPLOYEEID ");
			Emp_DOJ.append("  LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_LEAVE_REQ C ON B.RID=C.RID ");
			Emp_DOJ.append("  LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON A.EMPLOYEEID=D.EMPLOYEEID ");
			Emp_DOJ.append("  join( ");
			Emp_DOJ.append("   select p.employeesequenceno EmpCode,p.companyid ");
			Emp_DOJ.append("   from hclhrm_prod.tbl_employee_primary p ");
			Emp_DOJ.append("   LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON d.EMPLOYEEID=p.EMPLOYEEID ");
			Emp_DOJ.append("   LEFT JOIN hclhrm_prod.tbl_employee_primary H ON D.managerid=H.EMPLOYEEID ");
			Emp_DOJ.append("   where  h.employeesequenceno="+username+" ");
			Emp_DOJ.append("  )x on x.EmpCode=A.employeesequenceno ");
			Emp_DOJ.append(" join( ");
			Emp_DOJ.append("  SELECT max(PAYPERIOD) payperiod,businessunitid  FROM hclhrm_prod.tbl_businessunit_payroll_process ");
			Emp_DOJ.append(" group by businessunitid ");
			Emp_DOJ.append(" )y on 1=1 and y.businessunitid=x.companyid ");
			Emp_DOJ.append(" join ( ");
			Emp_DOJ.append(" select TODATE,businessunitid ,transactionduration,transactiontypeid from hcladm_prod.tbl_transaction_dates ");
			Emp_DOJ.append(" where transactiontypeid=1 ");
			Emp_DOJ.append(" )txn on txn.businessunitid=y.businessunitid and ");
			Emp_DOJ.append(" txn.transactionduration=y.payperiod and txn.transactiontypeid=1 ");
			Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT E ON D.DEPARTMENTID=E.DEPARTMENTID ");
			Emp_DOJ.append(" WHERE  B.REQ_TYPE='LR' and  B.EMPLOYEEID!="+username+" ");
			Emp_DOJ.append(" and  B.FLAG in('P','A') AND B.EMPLOYEEID is not null ");
			Emp_DOJ.append(" AND B.FROM_DATE >= txn.TODATE order by B.RID ");
			
            
            System.out.println("Emp_DOJ::" + Emp_DOJ.toString());
        }
        if (Routing.equalsIgnoreCase("ManagerAppr_att")) {
        	
           /* Emp_DOJ.append(" SELECT A.EMPLOYEESEQUENCENO ID,A.CALLNAME NAME,E.NAME DEPT,B.SUBJECT, ");
            Emp_DOJ.append(" CONCAT(B.FROM_DATE,'<--->',B.TO_DATE) ");
            Emp_DOJ.append(" DURATION,  B.TOTA_HOURS NET_HOURS,IF(B.FLAG='A','Approved',IF(B.FLAG='R','Rejected','Pending'))Manager_Status,B.RID,B.FLAG, ");
            Emp_DOJ.append(" B.MESSAGE,B.REQ_DATE ");
            Emp_DOJ.append(" FROM ");
            Emp_DOJ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_ATTN_REQ B ON A.EMPLOYEESEQUENCENO=B.EMPLOYEEID ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_LEAVE_REQ C ON B.RID=C.RID ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON A.EMPLOYEEID=D.EMPLOYEEID ");
            Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT E ON D.DEPARTMENTID=E.DEPARTMENTID ");
            Emp_DOJ.append(" WHERE B.RID>73 AND B.REQ_TYPE='AR' AND B.EMPLOYEEID!=" + username + " AND  B.FLAG in('P') ");
            Emp_DOJ.append(" AND B.REQ_DATE >= '" + string + "' ");
            Emp_DOJ.append(" AND D.MANAGERID in (" + Manager_id + ") ");
            Emp_DOJ.append("  ");
            Emp_DOJ.append(" OR LOWER(B.TO_EMAIL) ");
            Emp_DOJ.append(" LIKE '%" + User_obj.get("emp_mailid") + "%' ");
            Emp_DOJ.append(" AND B.FLAG!=NULL AND B.EMPLOYEEID IS NOT NULL ");*/
        	
        	Emp_DOJ.append(" SELECT A.EMPLOYEESEQUENCENO ID,A.CALLNAME NAME,E.NAME DEPT,B.SUBJECT, ");
			Emp_DOJ.append(" CONCAT(B.FROM_DATE,'<--->',B.TO_DATE) ");
			Emp_DOJ.append(" DURATION,  B.TOTA_HOURS NET_HOURS,IF(B.FLAG='A','Approved',IF(B.FLAG='R','Rejected','Pending'))Manager_Status,B.RID,B.FLAG, ");
			Emp_DOJ.append(" B.MESSAGE,B.REQ_DATE ");
			Emp_DOJ.append(" FROM ");
			Emp_DOJ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_ATTN_REQ B ON A.EMPLOYEESEQUENCENO=B.EMPLOYEEID ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_LEAVE_REQ C ON B.RID=C.RID ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON A.EMPLOYEEID=D.EMPLOYEEID ");
			Emp_DOJ.append(" join( ");
			Emp_DOJ.append(" select p.employeesequenceno EmpCode,p.companyid ");
			Emp_DOJ.append(" from hclhrm_prod.tbl_employee_primary p ");
			Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS D ON d.EMPLOYEEID=p.EMPLOYEEID ");
			Emp_DOJ.append(" LEFT JOIN hclhrm_prod.tbl_employee_primary H ON D.managerid=H.EMPLOYEEID ");
			Emp_DOJ.append(" where  h.employeesequenceno="+username+" ");
			Emp_DOJ.append(" )x on x.EmpCode=A.employeesequenceno ");
			Emp_DOJ.append(" join( ");
			Emp_DOJ.append(" SELECT max(PAYPERIOD) payperiod,businessunitid  FROM hclhrm_prod.tbl_businessunit_payroll_process ");
			Emp_DOJ.append(" group by businessunitid ");
			Emp_DOJ.append(" )y on 1=1 and y.businessunitid=x.companyid ");
			Emp_DOJ.append(" join ( ");
			Emp_DOJ.append(" select TODATE,businessunitid ,transactionduration,transactiontypeid from hcladm_prod.tbl_transaction_dates ");
			Emp_DOJ.append(" where transactiontypeid=1 ");
			Emp_DOJ.append(" )txn on txn.businessunitid=y.businessunitid and ");
			Emp_DOJ.append(" txn.transactionduration=y.payperiod and txn.transactiontypeid=1 ");
			Emp_DOJ.append(" LEFT JOIN HCLADM_PROD.TBL_DEPARTMENT E ON D.DEPARTMENTID=E.DEPARTMENTID ");
			Emp_DOJ.append(" WHERE  B.REQ_TYPE='AR' and  B.EMPLOYEEID!="+username+" ");
			Emp_DOJ.append(" and  B.FLAG in('P','A') AND B.EMPLOYEEID is not null ");
			Emp_DOJ.append(" AND B.REQ_DATE >= txn.TODATE order by B.RID ");
        }
        if (Routing.equalsIgnoreCase("ManagerAppr_Resi")) {
            if (APPMOD != null && APPMOD.equalsIgnoreCase("MG")) {
                Emp_DOJ.append(" select c.employeeid,a.callname,c.REASON,c.FEEDBACK,c.DATEMODIFIED,if(C.MANAGER_MSTATUS='A','Approved',if(C.MANAGER_MSTATUS='R','Reject','Pending')) A1,if(C.CC_STATUS='A','Approved',if(C.CC_STATUS='R','Reject','Pending')) R1, C.MANAGER_MSTATUS,C.CC_STATUS,C.MANAGERS_COMMENTS,C.HRS_COMMENTS FROM HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
                Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID ");
                Emp_DOJ.append(" JOIN hclhrm_prod_others.tbl_emp_resignation C ON A.employeesequenceno=c.employeeid  AND C.MANAGER_MSTATUS in ('P','A','R') and C.CC_STATUS in('P','A','R')  and C.STATUS=1001 and C.MGR_OK=0");
                Emp_DOJ.append(" where  B.MANAGERID in(select employeeid from HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY where employeesequenceno=" + username + ") ");
                Emp_DOJ.append(" OR C.TO_MAIL like '%" + User_obj.get("emp_mailid") + "%' ");
            }
            else if (APPMOD != null && APPMOD.equalsIgnoreCase("HR")) {
                Emp_DOJ.append(" select c.employeeid,a.callname,c.REASON,c.FEEDBACK,c.DATEMODIFIED,if(C.MANAGER_MSTATUS='A','Approved',if(C.MANAGER_MSTATUS='R','Reject','Pending')) A1,if(C.CC_STATUS='A','Approved',if(C.CC_STATUS='R','Reject','Pending')) R1,C.MANAGER_MSTATUS,C.CC_STATUS,C.MANAGERS_COMMENTS,C.HRS_COMMENTS FROM HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
                Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID ");
                Emp_DOJ.append(" JOIN hclhrm_prod_others.tbl_emp_resignation C ON A.employeesequenceno=c.employeeid  AND C.MANAGER_MSTATUS in('A','P','R') and CC_STATUS='P' and C.STATUS=1001");
            }
        }
        if (Routing.equalsIgnoreCase("Dept_att")) {
            DepAtt_Leav_map = new HashMap();
            DepAtt_Leav_map_HL = new HashMap();
            ATT_APP = new HashMap();
            DepAtt_Leav.append(" SELECT A.EMPLOYEEID,DATE_FORMAT(SELECTED_DATE,'%e') Day,SELECTED_DATE,A.FLAG,E.LEAVE_TYPE from  ");
            DepAtt_Leav.append(" HCLHRM_PROD_OTHERS.TBL_EMP_ATTN_REQ A  ");
            DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO  ");
            DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS C ON B.EMPLOYEEID=C.EMPLOYEEID  ");
            DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY D ON C.MANAGERID=D.EMPLOYEEID  ");
            DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_LEAVE_REQ E ON A.RID=E.RID,  ");
            DepAtt_Leav.append(" (select adddate('1970-01-01',t4*10000 + t3*1000 + t2*100 + t1*10 + t0) SELECTED_DATE from  ");
            DepAtt_Leav.append(" (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,  ");
            DepAtt_Leav.append(" (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,  ");
            DepAtt_Leav.append(" (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,  ");
            DepAtt_Leav.append(" (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,  ");
            DepAtt_Leav.append(" (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v  ");
            DepAtt_Leav.append(" where SELECTED_DATE between  ");
            DepAtt_Leav.append(" DATE_FORMAT(A.FROM_DATE,'%Y-%m-%d') and DATE_FORMAT(A.TO_DATE,'%Y-%m-%d')  ");
            DepAtt_Leav.append(" AND A.FLAG IN ('A','P','R') AND A.REQ_TYPE='LR'  AND A.STATUS=1001 AND A.RID>73  ");
            DepAtt_Leav.append(" AND D.EMPLOYEESEQUENCENO IN (" + username + ")  ");
            DepAtt_Leav.append(" AND A.FROM_DATE BETWEEN  ");
            if (Month_Sel.equalsIgnoreCase("currmonth")) {
                DepAtt_Leav.append(" (DATE_SUB(CONCAT_WS('-', YEAR(CURDATE()),MONTH(CURDATE()),'01'),INTERVAL DAY(CONCAT_WS('-', YEAR(CURDATE()),MONTH(CURDATE()),'01'))-1 DAY))  ");
                DepAtt_Leav.append(" AND  ");
                DepAtt_Leav.append(" (LAST_DAY(CONCAT_WS('-', YEAR(CURDATE()),MONTH(CURDATE()),'01')))  ");
                DepAtt_Leav.append(" GROUP BY A.RID,SELECTED_DATE ORDER BY A.RID,SELECTED_DATE ");
            }
            else {
                DepAtt_Leav.append(" (DATE_SUB(CONCAT_WS('-', YEAR(CURDATE()),'" + Month_Sel + "','01'),INTERVAL DAY(CONCAT_WS('-', YEAR(CURDATE()),'" + Month_Sel + "','01'))-1 DAY))  ");
                DepAtt_Leav.append(" AND  ");
                DepAtt_Leav.append(" (LAST_DAY(CONCAT_WS('-', YEAR(CURDATE()),'" + Month_Sel + "','01')))  ");
                DepAtt_Leav.append(" GROUP BY A.RID,SELECTED_DATE ORDER BY A.RID,SELECTED_DATE ");
            }
            Months_ATT_1.append(" SELECT DISTINCT IF(MONTH(TRANSACTIONTIME)<10,CONCAT(0,MONTH(TRANSACTIONTIME)),MONTH(TRANSACTIONTIME))MONTHS,MONTHNAME(TRANSACTIONTIME) MONTHNAME ");
            Months_ATT_1.append(" FROM unit_local_db.tbl_reader_log where ");
            Months_ATT_1.append(" MONTH(TRANSACTIONTIME)!=MONTH(CURDATE()) AND ");
            Months_ATT_1.append(" employeeid=" + username + " AND YEAR(TRANSACTIONTIME)=YEAR(CURDATE()) ");
            Months_ATT_1.append(" ORDER BY MONTH(TRANSACTIONTIME) DESC ");
            Res = null;
            try {
                Res = DataObj.FetchData(DepAtt_Leav.toString(), "DepAtt_Leav_Colors", Res, conn);
                while (Res.next()) {
                    DepAtt_Leav_map.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_" + Res.getString("Day"), Res.getString("Day"));
                    DepAtt_Leav_map.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_FLAG_" + Res.getString("Day"), Res.getString("FLAG"));
                    DepAtt_Leav_map.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_TYP_" + Res.getString("Day"), Res.getString("LEAVE_TYPE"));
                }
            }
            catch (Exception Er) {
                System.out.println(" Get Months Exception At 202 Er::" + Er);
            }
            if (Routing_type == null) {
                Res = null;
                try {
                    ATT_MONTHS_1.put((Object)"currmonth", (Object)"Current Month");
                    Res = DataObj.FetchData(Months_ATT_1.toString(), "EMPLOYEE_ATTENDANCELIST", Res, conn);
                    while (Res.next()) {
                        ATT_MONTHS_1.put((Object)Res.getString(1), (Object)Res.getString(2));
                    }
                }
                catch (Exception Er) {
                    System.out.println(" Get Months Exception At 201 Er::" + Er);
                }
            }
            String CallYear = null;
            String CallMonth = null;
            Res = null;
            try {
                if (Month_Sel.equalsIgnoreCase("currmonth")) {
                    Res = DataObj.FetchData(" SELECT trim(EXTRACT(DAY FROM  LAST_DAY(now()) )),EXTRACT(YEAR FROM  now()) ,EXTRACT(MONTH FROM  now()) from dual", "Last Date OF Given Month", Res, conn);
                }
                else {
                    Res = DataObj.FetchData("select date_format(LAST_DAY(date_format(concat(EXTRACT(YEAR FROM LAST_DAY(now())),'-'," + Month_Sel + ",'-',01),'%Y-%m-%d')),'%d'),EXTRACT(YEAR FROM  now()) ,'" + Month_Sel + "' ", "Last Date OF Given Month", Res, conn);
                }
                while (Res.next()) {
                    call_month = Res.getInt(1);
                    CallYear = Res.getString(2);
                    CallMonth = Res.getString(3);
                }
            }
            catch (Exception Er2) {
                System.out.println("Last Day Of Given Month Exception At 99 Er::" + Er2);
            }
            final StringBuffer HL_LV_Q = new StringBuffer();
            HL_LV_Q.append(" select BUSINESSUNITID,HOLIDAYDATE,'HL',DATE_FORMAT(HOLIDAYDATE,'%e'),COMMENTS from HCLHRM_PROD.TBL_HOLIDAYS ");
            HL_LV_Q.append(" where DATE_FORMAT(HOLIDAYDATE,'%m')=" + CallMonth + "  and DATE_FORMAT(HOLIDAYDATE,'%Y')=" + CallYear + " ");
            Res = null;
            try {
                Res = DataObj.FetchData(HL_LV_Q.toString(), "DepAtt_Holidays", Res, conn);
                while (Res.next()) {
                    final int dayaaa = Res.getString(4).toString().length();
                    if (dayaaa == 1) {
                        DepAtt_Leav_map_HL.put("HL$0" + Res.getString(4).toString() + "$" + Res.getString("BUSINESSUNITID").toString(), Res.getString("COMMENTS").toString());
                    }
                    else {
                        DepAtt_Leav_map_HL.put("HL$" + Res.getString(4).toString() + "$" + Res.getString("BUSINESSUNITID").toString(), Res.getString("COMMENTS").toString());
                    }
                }
            }
            catch (Exception Er3) {
                System.out.println(" 345 Get Months Exception At 88 Er::" + Er3);
            }
            final StringBuffer ATT_APP_buf = new StringBuffer();
            ATT_APP_buf.append("select a.EMPLOYEEID, a.REQ_DATE ,DATE_FORMAT(REQ_DATE,'%e'),a.FLAG from hclhrm_prod_others.tbl_emp_attn_req A ");
            ATT_APP_buf.append("LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO ");
            ATT_APP_buf.append("LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS C ON B.EMPLOYEEID=C.EMPLOYEEID ");
            ATT_APP_buf.append("LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY D ON C.MANAGERID=D.EMPLOYEEID ");
            ATT_APP_buf.append("where A.REQ_TYPE='AR' and A.STATUS=1001 AND DATE_FORMAT(REQ_DATE,'%m')=" + CallMonth + " and DATE_FORMAT(REQ_DATE,'%Y')=" + CallYear + " and a.FLAG='A' ");
            Res = null;
            try {
                Res = DataObj.FetchData(ATT_APP_buf.toString(), "DepAtt_Holidays", Res, conn);
                while (Res.next()) {
                    ATT_APP.put(String.valueOf(Res.getString(1).toString()) + "$" + Res.getString(3).toString(), Res.getString("FLAG").toString());
                }
            }
            catch (Exception Er4) {
                System.out.println(" Get Months Exception 77 At Er::" + Er4);
            }
            Emp_DOJ.append(" SELECT A.EMPLOYEESEQUENCENO ID,A.CALLNAME NAME,  ");
            String DummyYear = "0000-00-00";
            for (int i = 1; i <= call_month; ++i) {
                String k = "0";
                if (CallMonth.length() == 1) {
                    CallMonth = "0".concat(CallMonth);
                }
                if (i < 10) {
                    k = "0".concat(new StringBuilder().append(i).toString());
                }
                else {
                    k = new StringBuilder().append(i).toString();
                }
                DummyYear = String.valueOf(CallYear) + "-" + CallMonth + "-" + k;
                if (i != call_month) {
                    Doj22.put((Object)DummyYear, (Object)DummyYear);
                    Emp_DOJ.append(" CONCAT(FINDAY" + i + ",'-',LOUTDAY" + i + ")DAY" + i + ", IF(FINDAY" + i + ">'09.11' OR LOUTDAY" + i + "<'18.00' ,if(date('" + DummyYear + "')<=date(now()),'red',''),' ') DAY" + i + "CL , ");
                    Emp_DOJ.append("if(DAYNAME('" + DummyYear + "')='Sunday','WOFF','WDAY') HLWD_TYPE_" + i + " , concat('HL','$',IF(length('" + i + "')=1,concat('0','" + i + "'), '" + i + "'),'$', A.companyid) HL$" + i + " , ");
                }
                else if (i == call_month) {
                    Doj22.put((Object)DummyYear, (Object)DummyYear);
                    Emp_DOJ.append(" CONCAT(FINDAY" + i + ",'-',LOUTDAY" + i + ") DAY" + i + ",IF(FINDAY" + i + ">'09.11' OR LOUTDAY" + i + "<'18.00' ,if(date('" + DummyYear + "')<=date(now()),'red',''),' ') DAY" + i + "CL ");
                    Emp_DOJ.append(", if(DAYNAME('" + DummyYear + "')='Sunday','WOFF','WDAY') HLWD_TYPE_" + i + " , concat('HL','$',IF(length('" + i + "')=1,concat('0','" + i + "'), '" + i + "'),'$', A.companyid) HL$" + i + " ");
                }
            }
            Emp_DOJ.append(" FROM ");
            Emp_DOJ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID  ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_ATTENDANCE_FIN_LOUT_SUMMARY C ON A.EMPLOYEEID=C.EMPLOYEEID  ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_ATTENDANCE_HOURS_SUMMARY D ON A.EMPLOYEEID=D.EMPLOYEEID  ");
            Emp_DOJ.append(" WHERE ");
            if (Month_Sel.equalsIgnoreCase("currmonth")) {
                Emp_DOJ.append(" A.STATUS=1001   AND C.MONTH=EXTRACT(MONTH FROM LAST_DAY(now())) AND C.YEAR=EXTRACT(YEAR FROM LAST_DAY(now())) AND D.MONTH=EXTRACT(MONTH FROM LAST_DAY(now())) AND D.YEAR=EXTRACT(YEAR FROM LAST_DAY(now())) ");
            }
            else {
                Emp_DOJ.append(" A.STATUS=1001   AND C.MONTH=" + Month_Sel + " AND C.YEAR=EXTRACT(YEAR FROM LAST_DAY(now())) AND D.MONTH=" + Month_Sel + " AND D.YEAR=EXTRACT(YEAR FROM LAST_DAY(now()))");
            }
            Emp_DOJ.append(" AND B.MANAGERID=(SELECT EMPLOYEEID FROM HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY WHERE EMPLOYEESEQUENCENO IN (" + username + ")) ");
            System.out.println("Mahesh Emp_DOJ--->" + Emp_DOJ.toString());
            request.setAttribute("TITLES_TITLES", (Object)Doj22.toString());
        }
        String CompanySelected = null;
        if (Routing.equalsIgnoreCase("Dept_att_HR") && HR_ATT_CAL_MODE != null && HR_ATT_CAL_MODE.equalsIgnoreCase("MONTH")) {
            final StringBuffer Login_bu = new StringBuffer();
            Login_bu.append(" SELECT b.employeesequenceno,a.EMPLOYEEID,b.companyid, a.BUSINESSUNITID,C.NAME  ");
            Login_bu.append(" FROM hclhrm_prod.tbl_employee_businessunit a ");
            Login_bu.append(" left join hclhrm_prod.tbl_employee_primary b on a.employeeid=b.employeeid ");
            Login_bu.append(" left join hcladm_prod.tbl_businessunit c on a.BUSINESSUNITID=c.BUSINESSUNITID ");
            Login_bu.append(" where b.employeesequenceno=" + username + " and c.status=1001 ");
            if (Routing_type == null) {
                Res = null;
                try {
                    Res = DataObj.FetchData(Login_bu.toString(), "Employee Business Unit", Res, conn);
                    while (Res.next()) {
                        Login_bu_jason.put((Object)Res.getString("BUSINESSUNITID"), (Object)Res.getString("NAME"));
                        CompanySelected = Res.getString("companyid");
                    }
                    request.setAttribute("Login_bu_jason", (Object)Login_bu_jason.toString());
                    request.setAttribute("CompanySelected", (Object)CompanySelected);
                }
                catch (Exception ex) {}
                if (ATT_MOD_F_HR_BU == null) {
                    ATT_MOD_F_HR_BU = CompanySelected;
                }
                Doj22 = new JSONObject();
                values = new JSONArray();
                DepAtt_Leav_map = new HashMap();
                DepAtt_Leav_map_HL = new HashMap();
                DepAtt_Leav.append(" SELECT A.EMPLOYEEID,DATE_FORMAT(SELECTED_DATE,'%e') Day,SELECTED_DATE,A.FLAG,E.LEAVE_TYPE from  ");
                DepAtt_Leav.append(" HCLHRM_PROD_OTHERS.TBL_EMP_ATTN_REQ A  ");
                DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO  ");
                DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS C ON B.EMPLOYEEID=C.EMPLOYEEID  ");
                DepAtt_Leav.append(" LEFT JOIN HCLHRM_PROD_OTHERS.TBL_EMP_LEAVE_REQ E ON A.RID=E.RID,  ");
                DepAtt_Leav.append(" (select adddate('1970-01-01',t4*10000 + t3*1000 + t2*100 + t1*10 + t0) SELECTED_DATE from  ");
                DepAtt_Leav.append(" (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,  ");
                DepAtt_Leav.append(" (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,  ");
                DepAtt_Leav.append(" (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,  ");
                DepAtt_Leav.append(" (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,  ");
                DepAtt_Leav.append(" (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v  ");
                DepAtt_Leav.append(" where DATE_FORMAT(SELECTED_DATE,'%Y-%m-%d')>=   ");
                DepAtt_Leav.append(" DATE_FORMAT(A.FROM_DATE,'%Y-%m-%d') and  DATE_FORMAT(SELECTED_DATE,'%Y-%m-%d')<=DATE_FORMAT(A.TO_DATE,'%Y-%m-%d')  ");
                DepAtt_Leav.append(" AND A.FLAG IN ('A','P','R') AND A.REQ_TYPE='LR'  AND A.STATUS=1001 AND A.RID>73  ");
                DepAtt_Leav.append(" AND B.COMPANYID=" + ATT_MOD_F_HR_BU + "  ");
                DepAtt_Leav.append(" AND A.FROM_DATE BETWEEN  ");
                if (Month_Sel.equalsIgnoreCase("currmonth")) {
                    DepAtt_Leav.append(" (DATE_SUB(CONCAT_WS('-', YEAR(CURDATE()),MONTH(CURDATE()),'01'),INTERVAL DAY(CONCAT_WS('-', YEAR(CURDATE()),MONTH(CURDATE()),'01'))-1 DAY))  ");
                    DepAtt_Leav.append(" AND  ");
                    DepAtt_Leav.append(" (LAST_DAY(CONCAT_WS('-', YEAR(CURDATE()),MONTH(CURDATE()),'01')))  ");
                    DepAtt_Leav.append(" GROUP BY A.RID,SELECTED_DATE ORDER BY A.RID,SELECTED_DATE ");
                }
                else {
                    DepAtt_Leav.append(" (DATE_SUB(CONCAT_WS('-', YEAR(CURDATE()),'" + Month_Sel + "','01'),INTERVAL DAY(CONCAT_WS('-', YEAR(CURDATE()),'" + Month_Sel + "','01'))-1 DAY))  ");
                    DepAtt_Leav.append(" AND  ");
                    DepAtt_Leav.append(" (LAST_DAY(CONCAT_WS('-', YEAR(CURDATE()),'" + Month_Sel + "','01')))  ");
                    DepAtt_Leav.append(" GROUP BY A.RID,SELECTED_DATE ORDER BY A.RID,SELECTED_DATE ");
                }
                Months_ATT_1.append(" SELECT DISTINCT IF(MONTH(TRANSACTIONTIME)<10,CONCAT(0,MONTH(TRANSACTIONTIME)),MONTH(TRANSACTIONTIME))MONTHS,MONTHNAME(TRANSACTIONTIME) MONTHNAME ");
                Months_ATT_1.append(" FROM unit_local_db.tbl_reader_log where ");
                Months_ATT_1.append(" MONTH(TRANSACTIONTIME)!=MONTH(CURDATE()) AND ");
                Months_ATT_1.append(" YEAR(TRANSACTIONTIME)=YEAR(CURDATE()) ");
                Months_ATT_1.append(" ORDER BY MONTH(TRANSACTIONTIME) DESC ");
                Res = null;
                try {
                    Res = DataObj.FetchData(DepAtt_Leav.toString(), "DepAtt_Leav_Colors", Res, conn);
                    while (Res.next()) {
                        DepAtt_Leav_map.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_" + Res.getString("Day"), Res.getString("Day"));
                        DepAtt_Leav_map.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_FLAG_" + Res.getString("Day"), Res.getString("FLAG"));
                        DepAtt_Leav_map.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_TYP_" + Res.getString("Day"), Res.getString("LEAVE_TYPE"));
                    }
                }
                catch (Exception ex2) {}
                Res = null;
                try {
                    ATT_MONTHS_1.put((Object)"currmonth", (Object)"Current Month");
                    Res = DataObj.FetchData(Months_ATT_1.toString(), "EMPLOYEE_ATTENDANCELIST", Res, conn);
                    while (Res.next()) {
                        ATT_MONTHS_1.put((Object)Res.getString(1), (Object)Res.getString(2));
                    }
                }
                catch (Exception ex3) {}
            }
            String CallYear2 = null;
            String CallMonth2 = null;
            Res = null;
            try {
                if (Month_Sel.equalsIgnoreCase("currmonth")) {
                    Res = DataObj.FetchData(" SELECT trim(EXTRACT(DAY FROM  LAST_DAY(now()) )),EXTRACT(YEAR FROM  now()) ,EXTRACT(MONTH FROM  now()) from dual", "Last Date OF Given Month", Res, conn);
                }
                else {
                    Res = DataObj.FetchData("select date_format(LAST_DAY(date_format(concat(EXTRACT(YEAR FROM LAST_DAY(now())),'-'," + Month_Sel + ",'-',01),'%Y-%m-%d')),'%d'),EXTRACT(YEAR FROM  now()) ,'" + Month_Sel + "' ", "Last Date OF Given Month", Res, conn);
                }
                while (Res.next()) {
                    call_month = Res.getInt(1);
                    CallYear2 = Res.getString(2);
                    CallMonth2 = Res.getString(3);
                }
            }
            catch (Exception ex4) {}
            final StringBuffer HL_LV_Q2 = new StringBuffer();
            HL_LV_Q2.append(" select BUSINESSUNITID,HOLIDAYDATE,'HL',DATE_FORMAT(HOLIDAYDATE,'%e'),COMMENTS from HCLHRM_PROD.TBL_HOLIDAYS ");
            HL_LV_Q2.append(" where DATE_FORMAT(HOLIDAYDATE,'%m')=" + CallMonth2 + " and DATE_FORMAT(HOLIDAYDATE,'%Y')=" + CallYear2 + " ");
            Res = null;
            try {
                Res = DataObj.FetchData(HL_LV_Q2.toString(), "DepAtt_Holidays", Res, conn);
                while (Res.next()) {
                    final int dayaaa2 = Res.getString(4).toString().length();
                    if (dayaaa2 == 1) {
                        DepAtt_Leav_map_HL.put("HL$0" + Res.getString(4).toString() + "$" + Res.getString("BUSINESSUNITID").toString(), Res.getString("COMMENTS").toString());
                    }
                    else {
                        DepAtt_Leav_map_HL.put("HL$" + Res.getString(4).toString() + "$" + Res.getString("BUSINESSUNITID").toString(), Res.getString("COMMENTS").toString());
                    }
                }
            }
            catch (Exception ex5) {}
            Emp_DOJ.append(" SELECT A.EMPLOYEESEQUENCENO ID,A.CALLNAME NAME,  ");
            String DummyYear2 = "0000-00-00";
            for (int j = 1; j <= call_month; ++j) {
                String l = "0";
                if (CallMonth2.length() == 1) {
                    CallMonth2 = "0".concat(CallMonth2);
                }
                if (j < 10) {
                    l = "0".concat(new StringBuilder().append(j).toString());
                }
                else {
                    l = new StringBuilder().append(j).toString();
                }
                DummyYear2 = String.valueOf(CallYear2) + "-" + CallMonth2 + "-" + l;
                if (j != call_month) {
                    Doj22.put((Object)DummyYear2, (Object)DummyYear2);
                    Emp_DOJ.append(" IF(FINDAY" + j + "='0.00' AND LOUTDAY" + j + "='0.00',if(DATE_FORMAT('" + DummyYear2 + "','%Y-%m-%d')<=DATE_FORMAT(now(),'%Y-%m-%d'),'A','--'),if(DATE_FORMAT('" + DummyYear2 + "','%Y-%m-%d')<=DATE_FORMAT(now(),'%Y-%m-%d'),'P','--')) DAY" + j + ", IF(FINDAY" + j + ">'09.11' OR LOUTDAY" + j + "<'18.00', if(DATE_FORMAT('" + DummyYear2 + "','%Y-%m-%d')<=DATE_FORMAT(now(),'%Y-%m-%d'),'red',' '),' ') DAY" + j + "CL , ");
                    Emp_DOJ.append("if(DAYNAME('" + DummyYear2 + "')='Sunday','WOFF','WDAY') HLWD_TYPE_" + j + " , concat('HL','$',IF(length('" + j + "')=1,concat('0','" + j + "'), '" + j + "'),'$', A.companyid) HL$" + j + " , FINDAY" + j + " IN" + j + ",LOUTDAY" + j + " OUT" + j + ",'" + DummyYear2 + "' ATTDATE" + j + ", ");
                }
                else if (j == call_month) {
                    Doj22.put((Object)DummyYear2, (Object)DummyYear2);
                    Emp_DOJ.append(" IF(FINDAY" + j + "='0.00' AND LOUTDAY" + j + "='0.00',if(DATE_FORMAT('" + DummyYear2 + "','%Y-%m-%d')<=DATE_FORMAT(now(),'%Y-%m-%d'),'A','--'),if(DATE_FORMAT('" + DummyYear2 + "','%Y-%m-%d')<=DATE_FORMAT(now(),'%Y-%m-%d'),'P','--')) DAY" + j + ", IF(FINDAY" + j + ">'09.11' OR LOUTDAY" + j + "<'18.00', if(DATE_FORMAT('" + DummyYear2 + "','%Y-%m-%d')<=DATE_FORMAT(now(),'%Y-%m-%d'),'red',' '),' ') DAY" + j + "CL ");
                    Emp_DOJ.append(", if(DAYNAME('" + DummyYear2 + "')='Sunday','WOFF','WDAY') HLWD_TYPE_" + j + " , concat('HL','$',IF(length('" + j + "')=1,concat('0','" + j + "'), '" + j + "'),'$', A.companyid) HL$" + j + " ,FINDAY" + j + " IN" + j + " , LOUTDAY" + j + " OUT" + j + " ,'" + DummyYear2 + "' ATTDATE" + j + " ");
                }
            }
            Emp_DOJ.append(" FROM ");
            Emp_DOJ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PROFESSIONAL_DETAILS B ON A.EMPLOYEEID=B.EMPLOYEEID  ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_ATTENDANCE_FIN_LOUT_SUMMARY C ON A.EMPLOYEEID=C.EMPLOYEEID  ");
            Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_ATTENDANCE_HOURS_SUMMARY D ON A.EMPLOYEEID=D.EMPLOYEEID  ");
            Emp_DOJ.append(" WHERE ");
            if (Month_Sel.equalsIgnoreCase("currmonth")) {
                Emp_DOJ.append(" A.STATUS=1001   AND C.MONTH=EXTRACT(MONTH FROM LAST_DAY(now())) AND C.YEAR=EXTRACT(YEAR FROM LAST_DAY(now())) AND D.MONTH=EXTRACT(MONTH FROM LAST_DAY(now())) AND D.YEAR=EXTRACT(YEAR FROM LAST_DAY(now())) ");
            }
            else {
                Emp_DOJ.append(" A.STATUS=1001   AND C.MONTH=" + Month_Sel + " AND C.YEAR=EXTRACT(YEAR FROM LAST_DAY(now())) AND D.MONTH=" + Month_Sel + " AND D.YEAR=EXTRACT(YEAR FROM LAST_DAY(now()))");
            }
            Emp_DOJ.append(" AND A.companyid=" + ATT_MOD_F_HR_BU + " ");
            request.setAttribute("TITLES_TITLES", (Object)Doj22.toString());
        }
        Map empatt_Data = new HashMap();
        ArrayList MyHeaderlist = new ArrayList();
        if (Routing.equalsIgnoreCase("Dept_att_HR") && HR_ATT_CAL_MODE != null && HR_ATT_CAL_MODE.equalsIgnoreCase("PAYPERIOD")) {
            Doj22 = new JSONObject();
            values = new JSONArray();
            final StringBuffer TxnDates = new StringBuffer();
            final StringBuffer Txn_Bulk_Dats = new StringBuffer();
            if (Month_Sel.equalsIgnoreCase("currmonth")) {
                TxnDates.append(" SELECT distinct A.FROMDATE,TODATE FROM hcladm_prod.tbl_transaction_dates A  ");
                TxnDates.append(" where  A.BUSINESSUNITID in(" + ATT_MOD_F_HR_BU + ") ");
                TxnDates.append(" and  A.TRANSACTIONTYPEID=1 and A.TRANSACTIONDURATION=date_format(now(),'%Y%m') ");
            }
            else {
                String Da_Month = "0";
                if (Month_Sel.length() == 1 && Integer.parseInt(Month_Sel) < 10) {
                    Da_Month = Da_Month.concat(Month_Sel);
                }
                else {
                    Da_Month = Month_Sel;
                }
                TxnDates.append(" SELECT distinct A.FROMDATE,TODATE FROM hcladm_prod.tbl_transaction_dates A ");
                TxnDates.append(" where  A.BUSINESSUNITID in(" + ATT_MOD_F_HR_BU + ") and A.TRANSACTIONTYPEID=1 and A.TRANSACTIONDURATION=concat(date_format(now(),'%Y'),'" + Da_Month + "') ");
            }
            String Txn_FromDate = "00-00-0000";
            String Txn_Todate = "00-00-0000";
            Res = null;
            try {
                Res = DataObj.FetchData(TxnDates.toString(), "DepAtt_Leav_Colors", Res, conn);
                if (Res.next()) {
                    Txn_FromDate = Res.getString("FROMDATE");
                    Txn_Todate = Res.getString("TODATE");
                }
            }
            catch (Exception Er5) {
                System.out.println(" Get Txn FatesAt Er::" + Er5);
            }
            if (Routing_type == null) {
                Res = null;
                try {
                    ATT_MONTHS_1.put((Object)"currmonth", (Object)"Current Month");
                    Res = DataObj.FetchData(Months_ATT_1.toString(), "EMPLOYEE_ATTENDANCELIST", Res, conn);
                    while (Res.next()) {
                        ATT_MONTHS_1.put((Object)Res.getString(1), (Object)Res.getString(2));
                    }
                }
                catch (Exception ex6) {}
            }
            Txn_Bulk_Dats.append(" select selected_date,date_format(selected_date,'%d'),date_format(selected_date,'%m'),date_format(selected_date,'%Y') from  ");
            Txn_Bulk_Dats.append(" (select adddate('1970-01-01',t4.i*10000 + t3.i*1000 + t2.i*100 + t1.i*10 + t0.i) selected_date from ");
            Txn_Bulk_Dats.append(" (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0, ");
            Txn_Bulk_Dats.append(" (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1, ");
            Txn_Bulk_Dats.append(" (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2, ");
            Txn_Bulk_Dats.append(" (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3, ");
            Txn_Bulk_Dats.append(" (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v ");
            Txn_Bulk_Dats.append(" where selected_date between '" + Txn_FromDate + "' and '" + Txn_Todate + "'  ");
            MyHeaderlist = new ArrayList();
            try {
                Res = DataObj.FetchData(Txn_Bulk_Dats.toString(), "Txn_Bulk_Dats", Res, conn);
                while (Res.next()) {
                    MyHeaderlist.add(Res.getString("selected_date"));
                    Doj22.put((Object)Res.getString("selected_date"), (Object)Res.getString("selected_date"));
                }
            }
            catch (Exception Er5) {
                System.out.println(" Txn_Bulk_Dats Get Txn FatesAt Er::" + Er5);
            }
            Emp_DOJ.append(" SELECT distinct a.EMPLOYEEID,b.callname ");
            Emp_DOJ.append(" FROM hclhrm_prod_others.tbl_employee_iot_dup a, ");
            Emp_DOJ.append(" hclhrm_prod.tbl_employee_primary b where a.employeeid=b.employeesequenceno ");
            Emp_DOJ.append(" and b.companyid=" + ATT_MOD_F_HR_BU + " and a.TRANSACTIONDATE between '" + Txn_FromDate + "' and '" + Txn_Todate + "' ");
            final StringBuffer Emp_DOJ_Att_hr = new StringBuffer();
            Emp_DOJ_Att_hr.append(" SELECT a.EMPLOYEEID,b.callname,a.TRANSACTIONDATE, a.FIN, a.LOUT,a.NET,a.DAYSTATUS,a.STATUSID, ifnull(a.REQ_STATUS,0) rqstatus,  ");
            Emp_DOJ_Att_hr.append(" ifnull(a.STATUS_TYPE,0) STATUSTYPE  FROM hclhrm_prod_others.tbl_employee_iot_dup a, ");
            Emp_DOJ_Att_hr.append(" hclhrm_prod.tbl_employee_primary b where a.employeeid=b.employeesequenceno ");
            Emp_DOJ_Att_hr.append(" and b.companyid=" + ATT_MOD_F_HR_BU + " and a.TRANSACTIONDATE between '" + Txn_FromDate + "' and '" + Txn_Todate + "' ");
            empatt_Data = new HashMap();
            try {
                Res = DataObj.FetchData(Emp_DOJ_Att_hr.toString(), "Emp_DOJ_Att_hr", Res, conn);
                while (Res.next()) {
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_NAME", Res.getString("callname"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_FIN_" + Res.getString("TRANSACTIONDATE"), Res.getString("FIN"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_LOUT_" + Res.getString("TRANSACTIONDATE"), Res.getString("LOUT"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_NET_" + Res.getString("TRANSACTIONDATE"), Res.getString("NET"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_DAYSTATUS_" + Res.getString("TRANSACTIONDATE"), Res.getString("DAYSTATUS"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_STATUSID_" + Res.getString("TRANSACTIONDATE"), Res.getString("STATUSID"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_REQ_STATUS_" + Res.getString("TRANSACTIONDATE"), Res.getString("rqstatus"));
                    empatt_Data.put(String.valueOf(Res.getString("EMPLOYEEID")) + "_REQ_STATUS_TY_" + Res.getString("TRANSACTIONDATE"), Res.getString("STATUSTYPE"));
                }
            }
            catch (Exception Er6) {
                System.out.println(" Get Txn FatesAt Er::" + Er6);
            }
        }
        if (Routing.equalsIgnoreCase("ATTENDANCE_BIO")) {
            final String From = request.getParameter("username");
            final String to = request.getParameter("pwd");
            final String ATT_FLAG = request.getParameter("ATT_FLAG");
            final String Month_FROM = request.getParameter("Month_FROM");
            final String Month_TO = request.getParameter("Month_TO");
            Month = request.getParameter("Month");
            if (Month == null) {
                Month = "currmonth";
            }
            if (Month != null && Month.equalsIgnoreCase("currmonth")) {
                if (ATT_FLAG != null && ATT_FLAG.equalsIgnoreCase("DATES")) {
                    Emp_DOJ.append(" SELECT DATE_FORMAT(TRANSACTIONDATE,'%d-%m-%Y') DAY,FIN,LOUT FOUT,NET PERDAY,IF(DAYNAME(TRANSACTIONDATE)='SUNDAY','WOFF','WDAY')DAYTYPE,");
                    Emp_DOJ.append("IF(NET<'08:50',IF(TRANSACTIONDATE BETWEEN (SELECT MAX(TODATE) FROM hcladm_prod.tbl_transaction_dates ");
                    Emp_DOJ.append(" WHERE BUSINESSUNITID=(select companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in(" + username + "))) AND SUBDATE(CURDATE(),1),1,0),0)STATUS ");
                    Emp_DOJ.append(",TRANSACTIONDATE DAYSP ");
                    Emp_DOJ.append(" FROM ");
                    Emp_DOJ.append(" HCLHRM_PROD_OTHERS.TBL_EMPLOYEE_IOT ");
                    Emp_DOJ.append(" WHERE ");
                    Emp_DOJ.append(" TRANSACTIONDATE BETWEEN '" + Month_FROM + "' AND '" + Month_TO + "' AND EMPLOYEEID=" + username);
                }
                else {
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
                    Emp_DOJ.append("WHERE TRANSACTIONTIME  BETWEEN date(CURDATE()-(day(CURDATE())-1)) AND now() AND A.EMPLOYEEID=" + username + " GROUP BY DAY   ");
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
                    Emp_DOJ.append("and date not in (select DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d') from unit_local_db.tbl_reader_log where employeeid=" + username + " and transactiontime BETWEEN date(CURDATE()-(day(CURDATE())-1)) AND now())   ");
                    Emp_DOJ.append("ORDER BY DAY ");
                }
            }
            else if (ATT_FLAG != null && ATT_FLAG.equalsIgnoreCase("DATES")) {
                Emp_DOJ.append(" SELECT DATE_FORMAT(TRANSACTIONDATE,'%d-%m-%Y') DAY,FIN,LOUT FOUT,NET PERDAY,IF(DAYNAME(TRANSACTIONDATE)='SUNDAY','WOFF','WDAY')DAYTYPE,");
                Emp_DOJ.append("IF(NET<'08:50',IF(TRANSACTIONDATE BETWEEN (SELECT MAX(TODATE) FROM hcladm_prod.tbl_transaction_dates ");
                Emp_DOJ.append(" WHERE BUSINESSUNITID=(select companyid from hclhrm_prod.tbl_employee_primary where employeesequenceno in(" + username + "))) AND SUBDATE(CURDATE(),1),1,0),0)STATUS ");
                Emp_DOJ.append(",TRANSACTIONDATE DAYSP ");
                Emp_DOJ.append(" FROM ");
                Emp_DOJ.append(" HCLHRM_PROD_OTHERS.TBL_EMPLOYEE_IOT ");
                Emp_DOJ.append(" WHERE ");
                Emp_DOJ.append(" TRANSACTIONDATE BETWEEN '" + Month_FROM + "' AND '" + Month_TO + "' AND EMPLOYEEID=" + username);
            }
            else {
                Emp_DOJ.append(" SELECT  ");
                Emp_DOJ.append(" DATE_FORMAT(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1),'%d-%m-%Y')DAY, ");
                Emp_DOJ.append(" SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', -1)FIN, ");
                Emp_DOJ.append(" MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END)FOUT, ");
                Emp_DOJ.append(" SUBSTRING_INDEX(TIMEDIFF(SUBSTRING_INDEX(MAX(CASE WHEN  SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 1), ' ', -1) THEN SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime,' ', 2), ' ', -1)END), ' ', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(transactiontime, ' ', 2), ' ', 2), ' ', -1)),':',2)PERDAY, ");
                Emp_DOJ.append(" IF(DAYNAME(transactiontime)='SUNDAY','WOFF','WDAY')DAYTYPE ");
                Emp_DOJ.append(" FROM ");
                Emp_DOJ.append(" unit_local_db.tbl_reader_log A ");
                Emp_DOJ.append(" LEFT JOIN HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.EMPLOYEEID=B.EMPLOYEESEQUENCENO ");
                Emp_DOJ.append(" WHERE TRANSACTIONTIME  BETWEEN '2017-" + Month + "-01' AND '2017-" + Month + "-31' AND A.EMPLOYEEID=" + username + " GROUP BY DAY ");
                Emp_DOJ.append(" UNION ALL ");
                Emp_DOJ.append(" select  DATE_FORMAT(DATE,'%d-%m-%Y')DAY,'00:00:00' AS FIN,'00:00:00' AS LOUT,'00:00' AS PERDAY,IF(DAYNAME(DATE)='SUNDAY','WOFF','WDAY')DAYTYPE ");
                Emp_DOJ.append(" from ( ");
                Emp_DOJ.append(" select date_add(CONCAT(2017,'-'," + Month + ",'-01'), INTERVAL n5.num*10000+n4.num*1000+n3.num*100+n2.num*10+n1.num DAY ) as date from ");
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
                Emp_DOJ.append(" where date BETWEEN DATE_FORMAT(CONCAT(2017,'-'," + Month + ",'-00'),'%Y-%m-%d') and '2017-" + Month + "-31' ");
                Emp_DOJ.append(" and date not in (select DATE_FORMAT(TRANSACTIONTIME,'%Y-%m-%d') from unit_local_db.tbl_reader_log where employeeid=" + username + " and transactiontime BETWEEN '2017-" + Month + "-01' AND '2017-" + Month + "-31') ");
                Emp_DOJ.append(" ORDER BY DAY ");
            }
            HLDAYLIST.append("SELECT DISTINCT DATE_FORMAT(A.HOLIDAYDATE,'%d-%m-%Y') DAY,");
            HLDAYLIST.append(" TRIM(CONCAT(A.COMMENTS,'  ',IF(A.HOLIDAYTYPEID=0,'','(OPTINAL HLDAY)')))OCCASION");
            HLDAYLIST.append(" FROM");
            HLDAYLIST.append(" hclhrm_prod.tbl_holidays A");
            HLDAYLIST.append(" LEFT JOIN HCLHRM_PROD.TBL_HOLIDAY_TYPE B ON A.HOLIDAYTYPEID=B.HOLIDAYTYPEID");
            HLDAYLIST.append(" WHERE  year(A.HOLIDAYDATE)=year(now()) ");
            Months_ATT.append(" SELECT DISTINCT IF(MONTH(TRANSACTIONTIME)<10,CONCAT(0,MONTH(TRANSACTIONTIME)),MONTH(TRANSACTIONTIME))MONTHS,MONTHNAME(TRANSACTIONTIME) MONTHNAME ");
            Months_ATT.append(" FROM unit_local_db.tbl_reader_log where ");
            Months_ATT.append(" MONTH(TRANSACTIONTIME)!=MONTH(CURDATE()) AND ");
            Months_ATT.append(" employeeid=" + username + " AND YEAR(TRANSACTIONTIME)=YEAR(CURDATE()) ");
            Months_ATT.append(" ORDER BY MONTH(TRANSACTIONTIME) DESC ");
            Res = null;
            try {
                Res = DataObj.FetchData(HLDAYLIST.toString(), "Emp_DOJ", Res, conn);
                while (Res.next()) {
                    hlday.put(Res.getString(1), Res.getString(2));
                }
            }
            catch (Exception Er6) {
                System.out.println("Exception At 55 Er::" + Er6);
            }
            Res = null;
            try {
                Res = DataObj.FetchData("select date_format(REQ_DATE,'%d-%m-%Y'),if(FLAG='P','PROCESSED',if(FLAG='A','APPROVED','REJECT')) statusflg from hclhrm_prod_others.tbl_emp_attn_req where employeeid=" + username + " and REQ_TYPE='AR' ", "EMPLOYEE_ATTENDANCELIST", Res, conn);
                while (Res.next()) {
                    Att_Req.put(Res.getString(1), Res.getString(2));
                }
            }
            catch (Exception Er6) {
                System.out.println("Exception At 44 Er::" + Er6);
            }
            Res = null;
            try {
                ATT_MONTHS.put((Object)"currmonth", (Object)"Current Month");
                Res = DataObj.FetchData(Months_ATT.toString(), "EMPLOYEE_ATTENDANCELIST", Res, conn);
                while (Res.next()) {
                    ATT_MONTHS.put((Object)Res.getString(1), (Object)Res.getString(2));
                }
            }
            catch (Exception Er6) {
                System.out.println("Exception At 333 Er::" + Er6);
            }
        }
        Res = null;
        try {
            ps = conn.prepareStatement(User_Authen.toString());
            ps.setInt(1, Integer.parseInt(username));
            ps.setString(2, password);
            Res = DataObj.FetchDataPrepare_2(ps, "User Authentication", Res, conn);
            if (Res.next()) {
                User_Auth = Res.getInt(1);
            }
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
        Res = null;
        ps = null;
        Res = null;
        ResultSetMetaData rsmd = null;
        if (Routing.equalsIgnoreCase("Att_Request") && User_Auth == 1) {
            ps = null;
            try {
                conn.setAutoCommit(false);
                System.out.println("Connection Established....!");
                final Random rand = new Random();
                final int nRand = rand.nextInt(90000) + 10000;
                ps = conn.prepareStatement("INSERT INTO hclhrm_prod_others.tbl_emp_attn_req (EMPLOYEEID,SUBJECT,REQ_DATE,MESSAGE,RANDOMID,FROM_DATE,TO_DATE,TOTA_HOURS,TO_EMAIL,CC_EMAIL) VALUES (?,?,?,?,?,?,?,?,?,?)");
                date = request.getParameter("id");
                Req_message = request.getParameter("message");
                subject = request.getParameter("Subject");
                random = request.getParameter("RanDm");
                final String FIN = request.getParameter("FIN");
                final String FOUT = request.getParameter("FOUT");
                final String TIME = request.getParameter("TIME");
                String toemail = request.getParameter("toemail");
                String ccemail = request.getParameter("ccemail");
                try {
                    toemail = toemail.replaceAll(";", ",");
                    ccemail = ccemail.replaceAll(";", ",");
                }
                catch (Exception Errr) {
                    System.out.println("Errr--->at Att Req ::" + Errr);
                }
                ps.setString(1, username);
                ps.setString(2, subject);
                ps.setString(3, date);
                ps.setString(4, Req_message);
                ps.setInt(5, nRand);
                ps.setString(6, FIN);
                ps.setString(7, FOUT);
                ps.setString(8, TIME);
                ps.setString(9, toemail);
                ps.setString(10, ccemail);
                ps.addBatch();
                final int[] count = ps.executeBatch();
                System.out.println("add Batch Count::" + count.length);
                if (count.length > 0) {
                    conn.commit();
                }
                else {
                    conn.rollback();
                    Atten_Req_Message = "Request Not Processed Please contact system admin";
                }
            }
            catch (Exception e3) {
                Atten_Req_Message = "Faild To Process Your Request Please Contact System Admin.";
                e3.printStackTrace();
            }
        }
        Res = null;
        ps = null;
        Res = null;
        rsmd = null;
        if (Routing.equalsIgnoreCase("ManagerAppr_req") && User_Auth == 1) {
            ps = null;
            try {
                conn.setAutoCommit(false);
                System.out.println("Connection Established....!");
                ps = conn.prepareStatement("update hclhrm_prod_others.tbl_emp_attn_req set FLAG=? ,MAIL_STATUS='N' where EMPLOYEEID=? and RID=?   ");
                ps.setString(1, TB_STATUS);
                ps.setString(2, subject);
                ps.addBatch();
                final int[] count2 = ps.executeBatch();
                System.out.println("add Batch Count::" + count2.length);
                if (count2.length > 0) {
                    conn.commit();
                }
                else {
                    conn.rollback();
                    Atten_Req_Message = "Request Not Processed Please contact system admin";
                }
            }
            catch (Exception e3) {
                Atten_Req_Message = "Faild To Process Your Request Please Contact System Admin.";
                e3.printStackTrace();
            }
        }
        if (User_Auth == 1 && !Routing.equalsIgnoreCase("Att_Request")) {
            if (Routing.equalsIgnoreCase("NewJoine")) {
                _SUCCESS_PAGE = bundle_info.getString("HHCL_DESK_DIVERT_NEWJOINEE");
            }
            else if (Routing.equalsIgnoreCase("HOLIDAYS")) {
                _SUCCESS_PAGE = bundle_info.getString("HHCL_DESK_DIVERT_HOLIDAYS");
            }
            else if (Routing.equalsIgnoreCase("BIRTHADYS")) {
                _SUCCESS_PAGE = bundle_info.getString("HHCL_DESK_DIVERT_BIRTHDAYS");
            }
            else if (Routing.equalsIgnoreCase("ATTENDANCE_BIO")) {
                _SUCCESS_PAGE = bundle_info.getString("HHCL_DESK_DIVERT_ATTENDANCE");
            }
            Res = null;
            try {
                Res = DataObj.FetchData_Emp_DOB(Emp_DOJ.toString(), "Emp_DOJ", Res, conn);
                while (Res.next()) {
                    Doj23 = new JSONObject();
                    if (Routing.equalsIgnoreCase("NewJoine")) {
                        Doj23.put((Object)"CALLNAME", (Object)Res.getString(1));
                        Doj23.put((Object)"DEPT", (Object)Res.getString(2));
                        Doj23.put((Object)"EMAIL", (Object)Res.getString(3));
                        Doj23.put((Object)"MOBILE", (Object)Res.getString(4));
                        Doj23.put((Object)"BUNAME", (Object)Res.getString(5));
                    }
                    else if (Routing.equalsIgnoreCase("HOLIDAYS")) {
                        Doj23.put((Object)"EVENTDATE", (Object)Res.getString(1));
                        Doj23.put((Object)"EVENT", (Object)Res.getString(2));
                        Doj23.put((Object)"FLAG", (Object)Res.getString(3));
                        Doj23.put((Object)"HOLIDAYTYPE", (Object)Res.getString(4));
                    }
                    else if (Routing.equalsIgnoreCase("BIRTHADYS")) {
                        Doj23.put((Object)"EVENTDATE", (Object)Res.getString(1));
                        Doj23.put((Object)"BUNAME", (Object)Res.getString(2));
                        Doj23.put((Object)"EVENT", (Object)Res.getString(3));
                        Doj23.put((Object)"FLAG", (Object)Res.getString(4));
                        Doj23.put((Object)"HOLIDAYTYPE", (Object)Res.getString(5));
                    }
                    else if (Routing.equalsIgnoreCase("ManagerAppr")) {
                        int BUTTACT = 0;
                        try {
                            BUTTACT = Integer.parseInt(Res.getString("BUTTACT").toString());
                        }
                        catch (Exception err2) {
                            err2.printStackTrace();
                        }
                        Doj23.put((Object)"ID", (Object)Res.getString(1));
                        Doj23.put((Object)"NAME", (Object)Res.getString(2));
                        Doj23.put((Object)"DEPT", (Object)Res.getString(3));
                        Doj23.put((Object)"SUBJECT", (Object)Res.getString(4));
                        Doj23.put((Object)"DURATION", (Object)Res.getString(5));
                        Doj23.put((Object)"DAYS", (Object)Res.getString(6));
                        Doj23.put((Object)"LEAVE_TYPE", (Object)Res.getString(7));
                        Doj23.put((Object)"Manager_Status", (Object)Res.getString(8));
                        Doj23.put((Object)"MESSAGE", (Object)Res.getString("MESSAGE"));
                        Doj23.put((Object)"RID", (Object)Res.getString(9));
                        Doj23.put((Object)"FLAG", (Object)Res.getString(10));
                        if (BUTTACT == 1) {
                            Doj23.put((Object)"BUTTACT", (Object) "true");
                        }
                        else {
                            Doj23.put((Object)"BUTTACT", (Object) "false");
                        }
                        if (Res.getString(10).equalsIgnoreCase("P")) {
                            Doj23.put((Object)"B1", (Object)' ');
                            Doj23.put((Object)"B2", (Object)' ');
                            Doj23.put((Object)"B1T", (Object)"Approve");
                            Doj23.put((Object)"B2T", (Object)"Reject");
                        }
                        else if (Res.getString(10).equalsIgnoreCase("A")) {
                            Doj23.put((Object)"B1", (Object)" ");
                            Doj23.put((Object)"B2", (Object)"none");
                            Doj23.put((Object)"B1T", (Object)"Cancel");
                            Doj23.put((Object)"B2T", (Object)"Reject");
                        }
                        ++dobcnt2;
                    }
                    else if (Routing.equalsIgnoreCase("ManagerAppr_att")) {
                        Doj23.put((Object)"ID", (Object)Res.getString(1));
                        Doj23.put((Object)"NAME", (Object)Res.getString(2));
                        Doj23.put((Object)"DEPT", (Object)Res.getString(3));
                        Doj23.put((Object)"SUBJECT", (Object)Res.getString(4));
                        Doj23.put((Object)"DURATION", (Object)Res.getString(5));
                        Doj23.put((Object)"DAYS", (Object)Res.getString("NET_HOURS"));
                        Doj23.put((Object)"REQ_DATE", (Object)Res.getString("REQ_DATE"));
                        Doj23.put((Object)"LEAVE_TYPE", (Object)Res.getString("SUBJECT"));
                        Doj23.put((Object)"Manager_Status", (Object)Res.getString("Manager_Status"));
                        Doj23.put((Object)"MESSAGE", (Object)Res.getString("MESSAGE"));
                        Doj23.put((Object)"RID", (Object)Res.getString("RID"));
                        Doj23.put((Object)"FLAG", (Object)Res.getString("FLAG"));
                        if (Res.getString("FLAG").equalsIgnoreCase("P")) {
                            Doj23.put((Object)"B1", (Object)' ');
                            Doj23.put((Object)"B2", (Object)' ');
                            Doj23.put((Object)"B1T", (Object)"Approve");
                            Doj23.put((Object)"B2T", (Object)"Reject");
                        }
                        else if (Res.getString("FLAG").equalsIgnoreCase("A")) {
                            Doj23.put((Object)"B1", (Object)" ");
                            Doj23.put((Object)"B2", (Object)"none");
                            Doj23.put((Object)"B1T", (Object)"Cancel");
                            Doj23.put((Object)"B2T", (Object)"Reject");
                            ++dobcnt2;
                        }
                        ++dobcnt2;
                    }
                    else if (Routing.equalsIgnoreCase("BIRTHADYS")) {
                        Doj23.put((Object)"EVENTDATE", (Object)Res.getString(1));
                        Doj23.put((Object)"BUNAME", (Object)Res.getString(2));
                        Doj23.put((Object)"EVENT", (Object)Res.getString(3));
                        Doj23.put((Object)"FLAG", (Object)Res.getString(4));
                        Doj23.put((Object)"HOLIDAYTYPE", (Object)Res.getString(5));
                    }
                    else if (Routing.equalsIgnoreCase("Dept_att_HR") && HR_ATT_CAL_MODE != null && HR_ATT_CAL_MODE.equalsIgnoreCase("MONTH")) {
                        System.out.println("Attendance Formation2::" + Emp_DOJ.toString());
                        final String Emp_id = Res.getString("ID").toString();
                        final String Emp_Name = Res.getString("NAME").toString();
                        String color = " ";
                        System.out.println("Before while loop");
                        String HL_MAP = "WDAY";
                        Doj23.put((Object)"ID", (Object)Emp_id);
                        Doj23.put((Object)"NAME", (Object)Emp_Name);
                        final JSONArray values_dumm = new JSONArray();
                        for (int m = 1; m <= call_month; ++m) {
                            final JSONObject Doj24 = new JSONObject();
                            color = " ";
                            final String WeekDayType = Res.getString("HLWD_TYPE_" + m).toString();
                            final String HL_WEEKDAY = Res.getString("HL$" + m).toString();
                            Doj24.put((Object)"ATTDATE", (Object)Res.getString("ATTDATE" + m));
                            try {
                                HL_MAP = DepAtt_Leav_map_HL.get(new StringBuilder().append(HL_WEEKDAY).toString()).toString();
                                if (HL_MAP == null) {
                                    HL_MAP = "WDAY";
                                }
                            }
                            catch (Exception MAPP) {
                                HL_MAP = "WDAY";
                            }
                            if (WeekDayType != null && WeekDayType.equalsIgnoreCase("WDAY") && HL_MAP != null && HL_MAP.equalsIgnoreCase("WDAY")) {
                                if (DepAtt_Leav_map.get(String.valueOf(Res.getString("ID").toString()) + "_" + m) != null) {
                                    final String Leave_Type = DepAtt_Leav_map.get(String.valueOf(Res.getString("ID").toString()) + "_TYP_" + m).toString();
                                    final String Leave_FLAG = DepAtt_Leav_map.get(String.valueOf(Res.getString("ID").toString()) + "_FLAG_" + m).toString();
                                    if (Leave_FLAG.equalsIgnoreCase("A")) {
                                        color = "green";
                                    }
                                    else if (Leave_FLAG.equalsIgnoreCase("R")) {
                                        color = "red";
                                    }
                                    else {
                                        color = " ";
                                    }
                                    Doj24.put((Object)"DAY", (Object)Leave_Type.concat("#").toString().concat(color));
                                }
                                else {
                                    String MainColor = "NA";
                                    try {
                                        MainColor = ATT_APP.get(String.valueOf(Emp_id) + "$" + m).toString();
                                    }
                                    catch (Exception Edf) {
                                        MainColor = "NA";
                                        System.out.println("Exception at Attendance Color::" + Edf);
                                    }
                                    if (MainColor != null && MainColor != "NA" && Res.getString("DAY" + m + "CL").toString().equals("red")) {
                                        Doj24.put((Object)"DAY", (Object)Res.getString("DAY" + m).toString().concat("#green"));
                                    }
                                    else {
                                        Doj24.put((Object)"DAY", (Object)Res.getString("DAY" + m).toString().concat("#" + Res.getString("DAY" + m + "CL").toString()));
                                        Doj24.put((Object)"DAY_MODE", (Object)(String.valueOf(Res.getString(new StringBuilder("IN").append(m).toString())) + "-" + Res.getString("OUT" + m)));
                                    }
                                }
                            }
                            else {
                                final String color2 = "";
                                if (WeekDayType.equalsIgnoreCase("WOFF")) {
                                    Doj24.put((Object)"DAY", (Object)WeekDayType.concat("#").concat(color2));
                                    Doj24.put((Object)"DAY_MODE", (Object)"Sunday");
                                }
                                else {
                                    Doj24.put((Object)"DAY", (Object)"HL".concat("#").concat(color2));
                                    Doj24.put((Object)"DAY_MODE", (Object)HL_MAP);
                                }
                            }
                            values_dumm.add((Object)Doj24.toString());
                        }
                        Doj23.put((Object)"ATTENDANCE", (Object)values_dumm.toString());
                        System.out.println("HR_ATT_CAL_MODE::" + HR_ATT_CAL_MODE.equalsIgnoreCase("PAYPERIOD"));
                    }
                    else if (Routing.equalsIgnoreCase("Dept_att_HR") && HR_ATT_CAL_MODE != null && HR_ATT_CAL_MODE.equalsIgnoreCase("PAYPERIOD")) {
                        JSONObject Doj25 = new JSONObject();
                        final JSONArray values_dumm2 = new JSONArray();
                        final String Employeeid = Res.getString("employeeid").toString();
                        ListIterator itr = null;
                        try {
                            Doj23.put((Object)"ID", (Object)Employeeid);
                            Doj23.put((Object)"NAME", (Object)empatt_Data.get(String.valueOf(Employeeid) + "_NAME").toString());
                            try {
                                itr = MyHeaderlist.listIterator();
                            }
                            catch (Exception erdd) {
                                System.out.println("Exception at erdd ::" + erdd);
                            }
                            int P_days = 0;
                            int A_days = 0;
                            int W_Off = 0;
                            final int HL_days = 0;
                            int workingDays = 0;
                            int CL = 0;
                            int EL = 0;
                            int OD = 0;
                            int SL = 0;
                            while (itr.hasNext()) {
                                Doj25 = new JSONObject();
                                final String AttDate = itr.next().toString();
                                Doj25.put((Object)"ATTDATE", (Object)AttDate);
                                String DayStatus = "--";
                                try {
                                    DayStatus = empatt_Data.get(String.valueOf(Employeeid) + "_DAYSTATUS_" + AttDate).toString();
                                }
                                catch (Exception erd) {
                                    DayStatus = "--";
                                    System.out.println("Exception erd :: " + erd);
                                }
                                ++workingDays;
                                String Day_in = "--:--:--";
                                String Day_out = "--:--:--";
                                if (DayStatus != "--") {
                                    final String StatusID = empatt_Data.get(String.valueOf(Employeeid) + "_STATUSID_" + AttDate).toString();
                                    final String A_StatusID = empatt_Data.get(String.valueOf(Employeeid) + "_REQ_STATUS_" + AttDate).toString();
                                    final String A_StatusID_ty = empatt_Data.get(String.valueOf(Employeeid) + "_REQ_STATUS_TY_" + AttDate).toString();
                                    if (DayStatus.equalsIgnoreCase("WOFF") && StatusID.equalsIgnoreCase("WOFF")) {
                                        ++W_Off;
                                        Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("COMLATE")) {
                                        ++P_days;
                                        if (A_StatusID.equalsIgnoreCase("0")) {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("AR")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("P")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                            else {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                        }
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("PERFECT")) {
                                        ++P_days;
                                        Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("OK")) {
                                        ++P_days;
                                        Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("INCOMLATE")) {
                                        ++P_days;
                                        if (A_StatusID.equalsIgnoreCase("0")) {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("AR")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("P")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                            else {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                        }
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("EARLYOUT")) {
                                        ++P_days;
                                        if (A_StatusID.equalsIgnoreCase("0")) {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("AR")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("P")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                            else {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                        }
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("LESSHrs.")) {
                                        ++P_days;
                                        if (A_StatusID.equalsIgnoreCase("0")) {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("AR")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("P")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                            else {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                        }
                                    }
                                    else if (DayStatus.equalsIgnoreCase("P") && StatusID.equalsIgnoreCase("ICT")) {
                                        ++P_days;
                                        if (A_StatusID.equalsIgnoreCase("0")) {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("AR")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("P")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                            else {
                                                Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                            }
                                        }
                                    }
                                    else if (DayStatus.equalsIgnoreCase("A") && StatusID.equalsIgnoreCase("A")) {
                                        ++A_days;
                                        System.out.println("A_StatusID:::" + A_StatusID);
                                        if (A_StatusID.equalsIgnoreCase("0")) {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("CL")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                Doj25.put((Object)"DAY", (Object)"CL".concat("#").concat("green"));
                                                ++CL;
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)"CL".concat("#").concat("red"));
                                            }
                                            else {
                                                Doj25.put((Object)"DAY", (Object)"CL".concat("#").concat(" "));
                                                ++CL;
                                            }
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("SL")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                ++SL;
                                                Doj25.put((Object)"DAY", (Object)"SL".concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)"SL".concat("#").concat("red"));
                                            }
                                            else {
                                                ++SL;
                                                Doj25.put((Object)"DAY", (Object)"SL".concat("#").concat(" "));
                                            }
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("EL")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                ++EL;
                                                Doj25.put((Object)"DAY", (Object)"EL".concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)"EL".concat("#").concat("red"));
                                            }
                                            else {
                                                ++EL;
                                                Doj25.put((Object)"DAY", (Object)"EL".concat("#").concat(" "));
                                            }
                                        }
                                        else if (A_StatusID.equalsIgnoreCase("OD")) {
                                            if (A_StatusID_ty.equalsIgnoreCase("A")) {
                                                ++OD;
                                                Doj25.put((Object)"DAY", (Object)"OD".concat("#").concat("green"));
                                            }
                                            else if (A_StatusID_ty.equalsIgnoreCase("R")) {
                                                Doj25.put((Object)"DAY", (Object)"OD".concat("#").concat("red"));
                                            }
                                            else {
                                                ++OD;
                                                Doj25.put((Object)"DAY", (Object)"OD".concat("#").concat(" "));
                                            }
                                        }
                                        else {
                                            Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat("red"));
                                        }
                                    }
                                    Day_in = empatt_Data.get(String.valueOf(Employeeid) + "_FIN_" + AttDate).toString();
                                    Day_out = empatt_Data.get(String.valueOf(Employeeid) + "_FIN_" + AttDate).toString();
                                    Doj25.put((Object)"DAY_MODE", (Object)(String.valueOf(Day_in) + "-" + Day_out));
                                }
                                else {
                                    Doj25.put((Object)"DAY", (Object)DayStatus.concat("#").concat(" "));
                                    Doj25.put((Object)"DAY_MODE", (Object)(String.valueOf(Day_in) + "-" + Day_out));
                                }
                                values_dumm2.add((Object)Doj25.toString());
                            }
                            Doj23.put((Object)"Total_work_Days", (Object)workingDays);
                            Doj23.put((Object)"Working_Days", (Object)P_days);
                            Doj23.put((Object)"Total_Absents", (Object)A_days);
                            Doj23.put((Object)"Total_Weakoff", (Object)W_Off);
                            Doj23.put((Object)"CL", (Object)CL);
                            Doj23.put((Object)"SL", (Object)SL);
                            Doj23.put((Object)"EL", (Object)EL);
                            Doj23.put((Object)"OD", (Object)OD);
                            Doj23.put((Object)"TOT", (Object)(SL + EL + CL));
                            Doj23.put((Object)"ATTENDANCE", (Object)values_dumm2.toString());
                            System.out.println("Doj ::" + Doj23.toString());
                        }
                        catch (Exception er) {
                            System.out.println("er at MgmApprovals: " + er);
                        }
                    }
                    else if (Routing.equalsIgnoreCase("Dept_att")) {
                        final String Emp_id = Res.getString("ID").toString();
                        Doj23.put((Object)"ID", (Object)Emp_id);
                        Doj23.put((Object)"NAME", (Object)Res.getString("NAME").toString());
                        String color3 = " ";
                        System.out.println("Before while loop");
                        String HL_MAP2 = "WDAY";
                        for (int i2 = 1; i2 <= call_month; ++i2) {
                            color3 = " ";
                            final String WeekDayType2 = Res.getString("HLWD_TYPE_" + i2).toString();
                            final String HL_WEEKDAY2 = Res.getString("HL$" + i2).toString();
                            try {
                                HL_MAP2 = DepAtt_Leav_map_HL.get(new StringBuilder().append(HL_WEEKDAY2).toString()).toString();
                                if (HL_MAP2 == null) {
                                    HL_MAP2 = "WDAY";
                                }
                            }
                            catch (Exception MAPP2) {
                                HL_MAP2 = "WDAY";
                            }
                            if (WeekDayType2 != null && WeekDayType2.equalsIgnoreCase("WDAY") && HL_MAP2 != null && HL_MAP2.equalsIgnoreCase("WDAY")) {
                                if (DepAtt_Leav_map.get(String.valueOf(Res.getString("ID").toString()) + "_" + i2) != null) {
                                    final String Leave_Type2 = DepAtt_Leav_map.get(String.valueOf(Res.getString("ID").toString()) + "_TYP_" + i2).toString();
                                    final String Leave_FLAG2 = DepAtt_Leav_map.get(String.valueOf(Res.getString("ID").toString()) + "_FLAG_" + i2).toString();
                                    if (Leave_FLAG2.equalsIgnoreCase("A")) {
                                        color3 = "green";
                                    }
                                    else if (Leave_FLAG2.equalsIgnoreCase("R")) {
                                        color3 = "red";
                                    }
                                    else {
                                        color3 = " ";
                                    }
                                    Doj23.put((Object)("DAY" + i2), (Object)Leave_Type2.concat("#").toString().concat(color3));
                                }
                                else {
                                    String MainColor2 = "NA";
                                    try {
                                        MainColor2 = ATT_APP.get(String.valueOf(Emp_id) + "$" + i2).toString();
                                    }
                                    catch (Exception Edf2) {
                                        MainColor2 = "NA";
                                    }
                                    if (MainColor2 != null && MainColor2 != "NA" && Res.getString("DAY" + i2 + "CL").toString().equals("red")) {
                                        Doj23.put((Object)("DAY" + i2), (Object)Res.getString("DAY" + i2).toString().concat("#green"));
                                    }
                                    else {
                                        Doj23.put((Object)("DAY" + i2), (Object)Res.getString("DAY" + i2).toString().concat("#" + Res.getString("DAY" + i2 + "CL").toString()));
                                    }
                                }
                            }
                            else {
                                final String color4 = "";
                                if (WeekDayType2.equalsIgnoreCase("WOFF")) {
                                    Doj23.put((Object)("DAY" + i2), (Object)WeekDayType2.concat("#").concat(color4));
                                }
                                else {
                                    Doj23.put((Object)("DAY" + i2), (Object)"HL".concat("#").concat(color4));
                                }
                            }
                        }
                        System.out.println("DepAtt_Leav_map_HL::" + DepAtt_Leav_map_HL.toString());
                    }
                    else if (Routing.equalsIgnoreCase("ManagerAppr_Resi")) {
                        Doj23.put((Object)"ID", (Object)Res.getString("employeeid").toString());
                        Doj23.put((Object)"NAME", (Object)Res.getString("callname").toString());
                        Doj23.put((Object)"REASON", (Object)Res.getString("REASON").toString());
                        Doj23.put((Object)"FEEDBACK", (Object)Res.getString("FEEDBACK").toString());
                        Doj23.put((Object)"Applied", (Object)Res.getString("DATEMODIFIED").toString());
                        Doj23.put((Object)"Applied", (Object)Res.getString("DATEMODIFIED").toString());
                        Doj23.put((Object)"Applied", (Object)Res.getString("DATEMODIFIED").toString());
                        Doj23.put((Object)"MANAGERS_COMMENTS", (Object)Res.getString("MANAGERS_COMMENTS").toString());
                        Doj23.put((Object)"HRS_COMMENTS", (Object)Res.getString("HRS_COMMENTS").toString());
                        Doj23.put((Object)"MANAGER_MSTATUS", (Object)Res.getString("A1").toString());
                        Doj23.put((Object)"CC_STATUS", (Object)Res.getString("R1").toString());
                        Doj23.put((Object)"C", (Object)"none");
                        if (APPMOD != null && APPMOD.equalsIgnoreCase("MG") && Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("P") && Res.getString("CC_STATUS").toString().equalsIgnoreCase("P")) {
                            Doj23.put((Object)"A", (Object)" ");
                            Doj23.put((Object)"R", (Object)" ");
                        }
                        else if (APPMOD != null && APPMOD.equalsIgnoreCase("MG") && Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("A") && Res.getString("CC_STATUS").toString().equalsIgnoreCase("P")) {
                            Doj23.put((Object)"A", (Object)"none");
                            Doj23.put((Object)"R", (Object)"none");
                        }
                        else if (APPMOD != null && APPMOD.equalsIgnoreCase("MG") && (Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("A") || Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("R")) && (Res.getString("CC_STATUS").toString().equalsIgnoreCase("R") || Res.getString("CC_STATUS").toString().equalsIgnoreCase("A"))) {
                            Doj23.put((Object)"C", (Object)" ");
                            Doj23.put((Object)"A", (Object)"none");
                            Doj23.put((Object)"R", (Object)"none");
                        }
                        else if (APPMOD != null && APPMOD.equalsIgnoreCase("HR") && Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("P") && Res.getString("CC_STATUS").toString().equalsIgnoreCase("P")) {
                            Doj23.put((Object)"A", (Object)"none");
                            Doj23.put((Object)"R", (Object)"none");
                        }
                        else if (APPMOD != null && APPMOD.equalsIgnoreCase("HR") && (Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("A") || Res.getString("MANAGER_MSTATUS").toString().equalsIgnoreCase("R")) && Res.getString("CC_STATUS").toString().equalsIgnoreCase("P")) {
                            Doj23.put((Object)"A", (Object)" ");
                            Doj23.put((Object)"R", (Object)" ");
                        }
                    }
                    else if (Routing.equalsIgnoreCase("ATTENDANCE_BIO")) {
                        Doj23.put((Object)"DATE", (Object)Res.getString(1));
                        Doj23.put((Object)"FIN", (Object)Res.getString(2));
                        Doj23.put((Object)"FOUT", (Object)Res.getString(3));
                        Doj23.put((Object)"PERDAY", (Object)Res.getString(4));
                        if (Month.equalsIgnoreCase("currmonth")) {
                            Doj23.put((Object)"INNER", (Object)(String.valueOf(Res.getString("DAYSP")) + "#" + Res.getString(2) + "#" + Res.getString(3) + "#" + Res.getString(4)));
                            Doj23.put((Object)"DAYTYPE", (Object)Res.getString(5));
                        }
                        if (hlday.get(Res.getString(1)) != null) {
                            Doj23.put((Object)"DAYTYPE", (Object)hlday.get(Res.getString(1)).toString());
                            Doj23.put((Object)"DAF", (Object)"none");
                        }
                        else {
                            if (Res.getString(5).toString().equalsIgnoreCase("WOFF")) {
                                Doj23.put((Object)"DAF", (Object)"none");
                            }
                            else if (Res.getString(1).toString().equalsIgnoreCase(strTime)) {
                                Doj23.put((Object)"DAF", (Object)"none");
                            }
                            else {
                                Doj23.put((Object)"DAF", (Object)" ");
                            }
                            Doj23.put((Object)"DAYTYPE", (Object)Res.getString(5));
                        }
                        if (Month.equalsIgnoreCase("currmonth")) {
                            if (Res.getString(6).toString().equalsIgnoreCase("0")) {
                                Doj23.put((Object)"DAF", (Object)"none");
                            }
                        }
                        else {
                            Doj23.put((Object)"DAF", (Object)"none");
                        }
                        System.out.println("Res.getString(1)" + Res.getString(1));
                        if (Att_Req.get(Res.getString(1)) != null) {
                            Doj23.put((Object)"DAREQ", (Object)Att_Req.get(Res.getString(1)).toString());
                            Doj23.put((Object)"DAF", (Object)"none");
                        }
                        else {
                            Doj23.put((Object)"DAREQ", (Object)"No Request");
                        }
                    }
                    values.add((Object)Doj23);
                    ++dobcnt2;
                }
            }
            catch (Exception Er4) {
                System.out.println("Exception222 At Er::" + Er4);
            }
        }
        else {
            _SUCCESS_PAGE = "/login.html";
            request.setAttribute("message", (Object)bundle_info.getString("HHCL_DESK_LOGI_FAILD"));
        }
        System.out.println("dobcnt1::" + dobcnt2);
        if (dobcnt2 < 1) {
            System.out.println("dobcnt1-2::" + dobcnt2);
            values = new JSONArray();
            if (Routing.equalsIgnoreCase("NewJoine")) {
                Doj23.put((Object)"CALLNAME", (Object)"No Records");
                Doj23.put((Object)"DEPT", (Object)"No Records");
                Doj23.put((Object)"EMAIL", (Object)"No Records");
                Doj23.put((Object)"MOBILE", (Object)"No Records");
                Doj23.put((Object)"BUNAME", (Object)"No Records");
            }
            else if (Routing.equalsIgnoreCase("HOLIDAYS")) {
                Doj23.put((Object)"EVENTDATE", (Object)"No Records");
                Doj23.put((Object)"EVENT", (Object)"No Records");
                Doj23.put((Object)"FLAG", (Object)"No Records");
                Doj23.put((Object)"HOLIDAYTYPE", (Object)"No Records");
            }
            else if (Routing.equalsIgnoreCase("ManagerAppr_Resi")) {
                Doj23.put((Object)"ID", (Object)"NO_DATA");
                Doj23.put((Object)"NAME", (Object)"NO_DATA");
                Doj23.put((Object)"REASON", (Object)"NO_DATA");
                Doj23.put((Object)"FEEDBACK", (Object)"NO_DATA");
                Doj23.put((Object)"MANAGERS_COMMENTS", (Object)"NO_DATA");
                Doj23.put((Object)"HRS_COMMENTS", (Object)"NO_DATA");
                Doj23.put((Object)"A", (Object)"none");
                Doj23.put((Object)"R", (Object)"none");
                Doj23.put((Object)"C", (Object)"none");
            }
            else if (Routing.equalsIgnoreCase("BIRTHADYS")) {
                Doj23.put((Object)"EVENTDATE", (Object)"No Records");
                Doj23.put((Object)"BUNAME", (Object)"No Records");
                Doj23.put((Object)"EVENT", (Object)"No Records");
                Doj23.put((Object)"FLAG", (Object)"No Records");
                Doj23.put((Object)"HOLIDAYTYPE", (Object)"No Records");
            }
            else if (Routing.equalsIgnoreCase("ATTENDANCE_BIO")) {
                Doj23.put((Object)"DATE", (Object)"NO_DATA");
                Doj23.put((Object)"FIN", (Object)"NO_DATA");
                Doj23.put((Object)"FOUT", (Object)"NO_DATA");
                Doj23.put((Object)"PERDAY", (Object)"NO_DATA");
                Doj23.put((Object)"INNER", (Object)"NO_DATA");
                Doj23.put((Object)"DAYTYPE", (Object)"NO_DATA");
                Doj23.put((Object)"DAF", (Object)"none");
                Doj23.put((Object)"DAREQ", (Object)"NO_DATA");
            }
            else if (Routing.equalsIgnoreCase("ManagerAppr") || Routing.equalsIgnoreCase("ManagerAppr_att") || Routing.equalsIgnoreCase("Dept_att") || Routing.equalsIgnoreCase("Dept_att_HR")) {
                if (Routing.equalsIgnoreCase("Dept_att") || Routing.equalsIgnoreCase("Dept_att_HR")) {
                    Doj23.put((Object)"ID", (Object)"--");
                    Doj23.put((Object)"NAME", (Object)"--");
                    for (int i3 = 1; i3 <= call_month; ++i3) {
                        Doj23.put((Object)("DAY" + i3), (Object)"--".concat("#''"));
                    }
                }
                else {
                    Doj23.put((Object)"ID", (Object)"No_Data");
                    Doj23.put((Object)"NAME", (Object)"No_Data");
                    Doj23.put((Object)"DEPT", (Object)"No_Data");
                    Doj23.put((Object)"SUBJECT", (Object)"No_Data");
                    Doj23.put((Object)"REQ_DATE", (Object)"No_Data");
                    Doj23.put((Object)"DURATION", (Object)"No_Data");
                    Doj23.put((Object)"DAYS", (Object)"No_Data");
                    Doj23.put((Object)"LEAVE_TYPE", (Object)"No_Data");
                    Doj23.put((Object)"Manager_Status", (Object)"No_Data");
                    Doj23.put((Object)"B1", (Object)"none");
                    Doj23.put((Object)"B2", (Object)"none");
                    Doj23.put((Object)"B1T", (Object)"Cancel");
                    Doj23.put((Object)"B2T", (Object)"Reject");
                    Doj23.put((Object)"ID", (Object)"NA");
                    Doj23.put((Object)"NAME", (Object)"NA");
                    Doj23.put((Object)"DAY1", (Object)"NA");
                    Doj23.put((Object)"DAY2", (Object)"NA");
                    Doj23.put((Object)"DAY3", (Object)"NA");
                    Doj23.put((Object)"DAY4", (Object)"NA");
                    Doj23.put((Object)"DAY5", (Object)"NA");
                    Doj23.put((Object)"DAY6", (Object)"NA");
                    Doj23.put((Object)"DAY7", (Object)"NA");
                    Doj23.put((Object)"DAY8", (Object)"NA");
                    Doj23.put((Object)"DAY9", (Object)"NA");
                    Doj23.put((Object)"DAY10", (Object)"NA");
                    Doj23.put((Object)"DAY11", (Object)"NA");
                    Doj23.put((Object)"DAY12", (Object)"NA");
                    Doj23.put((Object)"DAY13", (Object)"NA");
                    Doj23.put((Object)"DAY14", (Object)"NA");
                    Doj23.put((Object)"DAY15", (Object)"NA");
                    Doj23.put((Object)"DAY16", (Object)"NA");
                    Doj23.put((Object)"DAY17", (Object)"NA");
                    Doj23.put((Object)"DAY18", (Object)"NA");
                    Doj23.put((Object)"DAY19", (Object)"NA");
                    Doj23.put((Object)"DAY20", (Object)"NA");
                    Doj23.put((Object)"DAY21", (Object)"NA");
                    Doj23.put((Object)"DAY22", (Object)"NA");
                    Doj23.put((Object)"DAY23", (Object)"NA");
                    Doj23.put((Object)"DAY24", (Object)"NA");
                    Doj23.put((Object)"DAY25", (Object)"NA");
                    Doj23.put((Object)"DAY26", (Object)"NA");
                    Doj23.put((Object)"DAY27", (Object)"NA");
                    Doj23.put((Object)"DAY28", (Object)"NA");
                    Doj23.put((Object)"DAY29", (Object)"NA");
                    Doj23.put((Object)"DAY30", (Object)"NA");
                    Doj23.put((Object)"DAY31", (Object)"NA");
                }
            }
            values.add((Object)Doj23);
            values2.add((Object)Doj22);
            System.out.println("values::" + values.toString());
        }
        request.setAttribute("DOJ_DOB", (Object)values.toString());
        request.setAttribute("ATT_MONTHS", (Object)ATT_MONTHS.toString());
        System.out.println("ATT_MONTHS::" + ATT_MONTHS.toString());
        request.setAttribute("ATT_MONTHS_1", (Object)ATT_MONTHS_1.toString());
        if (Routing == null) {
            session.setAttribute("EMP_ID", (Object)username);
            session.setAttribute("EMP_PASSWORD", (Object)password);
        }
        System.out.println("_SUCCESS_PAGE::" + _SUCCESS_PAGE);
        try {
            if (statement != null) {
                statement.close();
            }
            if (Res != null) {
                Res.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        catch (SQLException e4) {
            e4.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
            if (Res != null) {
                Res.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        catch (SQLException e4) {
            e4.printStackTrace();
        }
        if (Routing.equalsIgnoreCase("Att_Request")) {
            System.out.println("Atten_Req_Message::" + Atten_Req_Message);
            out.write(new StringBuilder().append(Atten_Req_Message).toString());
        }
        else if (Routing_temp.equalsIgnoreCase("ATTENDANCE_BIO_DATES")) {
            System.out.println("Atten_Req_Message::" + Atten_Req_Message);
            System.out.println("values.toString()::" + values.toString());
            out.write(new StringBuilder().append(values.toString()).toString());
        }
        else if (Routing.equalsIgnoreCase("ManagerAppr")) {
            System.out.println("Atten_Req_Message::" + Atten_Req_Message);
            System.out.println("values.toString()::" + values.toString());
            request.getRequestDispatcher("ManagerApprovals.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
        else if (Routing.equalsIgnoreCase("ManagerAppr_att")) {
            System.out.println("Atten_Req_Message::" + Atten_Req_Message);
            System.out.println("values.toString()::" + values.toString());
            request.getRequestDispatcher("ManagerApprovals_att.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
        else if (Routing.equalsIgnoreCase("Dept_att") && Routing_type == null) {
            System.out.println("Atten_Req_Message::" + Atten_Req_Message);
            System.out.println("values.toString()::" + values.toString());
            request.getRequestDispatcher("Dept_attendance.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
        else if (Routing.equalsIgnoreCase("Dept_att_HR") && Routing_type == null) {
            System.out.println("values.toString()::" + values.toString());
            request.getRequestDispatcher("Bu_attendance.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
        else if ((Routing.equalsIgnoreCase("Dept_att") || Routing.equalsIgnoreCase("Dept_att_HR")) && Routing_type != null && Routing_type.equalsIgnoreCase("AJEX")) {
            System.out.println("ATT_PAYPERIOD::" + Emp_DOJ.toString());
            out.write(values.toString() + " $#$ " + Doj22.toString());
        }
        else if (Routing.equalsIgnoreCase("ManagerAppr_Resi")) {
            String Page = "";
            if (APPMOD.equalsIgnoreCase("HR")) {
                Page = "Hr_Manager_reg_approvals.jsp";
            }
            else if (APPMOD.equalsIgnoreCase("MG")) {
                Page = "Manager_reg_approvals.jsp";
            }
            request.getRequestDispatcher(Page).forward((ServletRequest)request, (ServletResponse)response);
        }
        else {
            request.getRequestDispatcher(_SUCCESS_PAGE).forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}
