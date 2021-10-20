package com.assam.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Assam_health_report
 */
public class Assam_health_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assam_health_report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		PrintWriter out = response.getWriter();
		 HttpSession session=request.getSession();
		String Empid=(String)session.getAttribute("EMP_ID");;
		String Date="NA";
		String ServiceMode="employee";
		String routing=request.getParameter("routing");
		System.out.println( "I am from Servlet Program ....................!" );
		
		//my_date_picker
		if(routing!=null && routing.equalsIgnoreCase("Ajex")){
			Date=request.getParameter("my_date_picker");
		 }
		
		
		String output=null;
				//"[{'Sys_autoID':'1','Sys_attendanceCode':'ZSqSeYdpe1PZ0AZXYNUa8eiYXZpcoEVc','Sys_empType':'1','Sys_employeeID':'10508','Sys_fullName':'Sairam Duggi','Sys_employeeMobile':'9652207972','Sys_dateofBirth':'1994-08-24','Sys_department':'INFORMATION TECHNOLOGY','Sys_bussinessUnit':'HHC - CORPORATE','Sys_shiftDate':'0000-00-00','Sys_Inshift':'Shift-A','Sys_inTime':'06:00:00','Sys_inTemp':'98.3','Sys_loggedByID':'10508','Sys_loggedOn':'2020-04-19 16:46:11','Sys_lastupdated':'2020-04-19 17:56:27','Sys_status':'1001'},{'Sys_autoID':'2','Sys_attendanceCode':'2J4toqGm7oBnIMJuMHHkeqYa1x1jpaiq','Sys_empType':'1','Sys_employeeID':'10484','Sys_fullName':'','Sys_employeeMobile':'9849281442','Sys_dateofBirth':'1989-08-25','Sys_department':'INFORMATION TECHNOLOGY','Sys_bussinessUnit':'HHC - CORPORATE','Sys_shiftDate':'0000-00-00','Sys_Inshift':'Shift-B','Sys_inTime':'10:00:00','Sys_inTemp':'100.0','Sys_loggedByID':'10508','Sys_loggedOn':'2020-04-19 18:31:34','Sys_lastupdated':'2020-04-19 18:31:34','Sys_status':'1002'},{'Sys_autoID':'3','Sys_attendanceCode':'FWVExUEN5V4EmUPQzns48rTXgdbXukj1','Sys_empType':'1','Sys_employeeID':'10508','Sys_fullName':'SAIRAM DUGGI','Sys_employeeMobile':'9652207972','Sys_dateofBirth':'1994-08-24','Sys_department':'INFORMATION TECHNOLOGY','Sys_bussinessUnit':'HHC - CORPORATE','Sys_shiftDate':'0000-00-00','Sys_Inshift':'Shift-B','Sys_inTime':'09:00:00','Sys_inTemp':'98.5','Sys_loggedByID':'10508','Sys_loggedOn':'2020-04-19 18:33:04','Sys_lastupdated':'2020-04-19 18:33:04','Sys_status':'1002'}]";
		NetClientPost obj =new NetClientPost();
		try{
		 output=obj.CallService(Empid, Date, ServiceMode);
		}catch(Exception err){
			err.printStackTrace();
		}
		 request.setAttribute("DOJ_DOB", output);
		 
		 System.out.println(Empid +"~"+Date+"~~" +routing+ "Service Data "+output);
		 if(routing!=null && routing.equalsIgnoreCase("Ajex")){
			 out.write(""+output+""); 
		 }else{
		 request.getRequestDispatcher("health_report.jsp").forward(request, response);
		 }
		//out.write(""+output+"");
	}

}
