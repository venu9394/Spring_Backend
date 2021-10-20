<!doctype html>
<html class="fixed">
	<head>
	
<% 
  
  String Emp_BioData1=(String)request.getAttribute("DOJ_DOB");
  String TDSFLAG=(String)session.getAttribute("TDSFLAG");
  String EMP_NAME=(String)session.getAttribute("EMP_NAME");
  String Emp_BioData=null;
  if(Emp_BioData1==null || Emp_BioData1.equalsIgnoreCase("null") || Emp_BioData1==""){
	  
	  Emp_BioData=" [{'Sys_employeeID':'NA','Sys_fullName':'NA', 'Sys_loggedOn':'NA','Sys_loggedOn':'NA','Sys_inTemp':'NA','AllowedtoWork':'NA','ColorCode':''}]" ;
	  System.out.println ("Hi I am in JSP.................!");
  }else{
	  Emp_BioData=Emp_BioData1;
  }
  String  MGRFLAG=(String)session.getAttribute("Manage_Rights");
  String MGRFLAG_S="none";
  if(MGRFLAG!=null && Integer.parseInt(MGRFLAG)>0){
	  
	  MGRFLAG_S=" ";
  }
  
  String  HR_HRMS=(String)session.getAttribute("HR_HRMS");
  if(HR_HRMS!=null && HR_HRMS.equalsIgnoreCase("YES")){
  	HR_HRMS=" ";
  }else{
  	HR_HRMS="none";
  }
%>
	<head>
	<script src="MyAng.js"></script>
<script>
var app = angular.module('myApp', []);
app.controller('formCtrl', function($scope,$http) {
	var Data="";
	$scope.Data_2="";
	$scope.Data_1="";
	$scope.Data_3="";
	$scope.xy=1;
	$scope.Data_1="";
	try{
		
	
		$scope.Data_1=<%=Emp_BioData%>;
    
		//$scope.Data_1="";
		
   <%--  $scope.Data_2=<%=EmpDOB%>;
    $scope.Data_3=<%=HOLIDAYS_PG%>;
  
    $scope.empid = Data.EMPLOYEESEQUENCENO;
    $scope.empname= Data.CALLNAME;
    $scope.emp_doj= Data.DATEOFJOIN;
    $scope.emp_Dep= Data.DEPARTMENT;
    $scope.emp_Des= Data.DESIGNATION;
    $scope.emp_job_type= Data.TYPE;
     --%>
	}catch(err){
		alert("Please Try Again..");
	}
	
	try{
		 $scope.myFunction = function(val) {
			 
			 document.getElementById("ImgScroll").style.display="";
		      //alert(val);
			 var my_date_picker=document.getElementById("my_date_picker").value;
			 $scope.Data_1="";
			 $http({
			        method : "POST",
			        url : "Assam_health_report?routing=Ajex&my_date_picker="+my_date_picker+"",
			        		
			    }).then(function mySucces(response) {
			    	$scope.Data_1 = response.data;
			    	document.getElementById("ImgScroll").style.display="none";
			        //alert(response.data);
			    }, function myError(response) {
			        $scope.Data_1 = response.statusText;
			        document.getElementById("ImgScroll").style.display="none";
			    });
		 }
		 }catch(err1){
			//alert("Please Try Again..");
			 document.getElementById("ImgScroll").style.display="none";
		} 
		
 });
 
</script>

		<!-- Basic -->
		<meta charset="UTF-8">

		<title>Hetero Healthcare LTD</title>
          <link rel="icon" href="LOH.png" />
          
		<meta name="keywords" content="HETERO" />
		<meta name="description" content="Hetero">
		<meta name="author" content="Hetero">

		<!-- Mobile Metas -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		
		<meta http-equiv="X-UA-Compatible" content="IE=8" />

		<!-- Web Fonts  -->
		<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

		<!-- Vendor CSS -->
		<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="assets/vendor/magnific-popup/magnific-popup.css" />
		<link rel="stylesheet" href="assets/vendor/bootstrap-datepicker/css/datepicker3.css" />
		

		<!-- Specific Page Vendor CSS -->
		<!-- <link rel="stylesheet" href="assets/vendor/jquery-ui/css/ui-lightness/jquery-ui-1.10.4.custom.css" /> venu-->

		

		<!-- Theme CSS -->
		<link rel="stylesheet" href="assets/stylesheets/theme.css" />

		<!-- Skin CSS -->
		<link rel="stylesheet" href="assets/stylesheets/skins/default.css" />

		<!-- Theme Custom CSS -->
		<link rel="stylesheet" href="assets/stylesheets/theme-custom.css">

		<!-- Head Libs -->
		<script src="assets/vendor/modernizr/modernizr.js"></script>
		<!-- <link rel="stylesheet" href="jqueryui_date.css">  venu-->

  <script>
  function disableBackButton() {
	   window.history.forward();
	}
	setTimeout("disableBackButton()", 0);


	document.onkeydown = function(){
	       if(event.keyCode == 116) {
	            event.returnValue = false;
	            event.keyCode = 0;
	            return false;
	        }

	}
	</script>

	<script type="text/javascript">
		window.history.forward();
		function noBack() { window.history.forward(); }
		
	
</script>

<!-- <link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet'> 
<script src= 'https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js' > </script> 
<script src='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js' > </script>  -->

 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.12.4.js"></script>
  <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>

$(document).ready(function() { 
    
   /*  $(function() { 
        $("#my_date_picker").datepicker(); 
    });  */
    
	 $("#my_date_picker").datepicker({
		  
		  changeMonth: true,
	      changeYear:true,
			maxDate : 0,
			dateFormat : 'yy-mm-dd',
			 yearRange: '2020:2020',
			 minDate : '2020-04-20'
			/* onSelect : function(date) {
				$("#to_1").datepicker('option', 'minDate', date);
			} */
		});
    
    
})     
	    

</script>

<style>

.ui-datepicker td span, .ui-datepicker td a {
  color: red; /* Numbers color */
  fill: black; 
}
.ui-datepicker td {
  background: #ededed; /* Numbers background */
}
.ui-datepicker-calendar .ui-state-hover {
  color: red;  /* Numbers color on hover */
}
.ui-datepicker-calendar .ui-state-active {
  color: green; /* Selected date color */
}

</style>
	</head>
	<body onload="noBack();" onpageshow="if (event.persisted) noBack();" ng-app="myApp" ng-controller="formCtrl">
		<section class="body">

			<!-- start: header -->
			<header class="header">
				<div class="logo-container">
					<a href="#" class="logo">
						<img src="assets/images/logo.png" height="35" alt="Hetero" />
					</a>
					<div class="visible-xs toggle-sidebar-left" data-toggle-class="sidebar-left-opened" data-target="html" data-fire-event="sidebar-left-opened">
						<i class="fa fa-bars" aria-label="Toggle sidebar"></i>
					</div>
				</div>
			
				<!-- start: search & user box -->
				<div class="header-right">
			
					<div id="userbox" class="userbox">
						<a href="#" data-toggle="dropdown">
							<div class="clear"></div>
							<div class="profile-info" data-lock-name="sairam" data-lock-email="sairam.d@heterohealthcare.com">
								<span class="name"><%=EMP_NAME %></span>
								
							</div>
			
							<i class="fa custom-caret"></i>
						</a>
			
						<div class="dropdown-menu">
							<ul class="list-unstyled">
								<li class="divider"></li>
								<!-- <li>
									<a role="menuitem" tabindex="-1" href="hhcl_changepassword.html"><i class="fa fa-cogs"></i>Change Password</a>
								</li> -->
								<li>
									<a role="menuitem" tabindex="-1" href="logout"><i class="fa fa-power-off"></i> Logout</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- end: search & user box -->
			</header>
			<!-- end: header -->

			<div class="inner-wrapper">
				<!-- start: sidebar -->
				<aside id="sidebar-left" class="sidebar-left">
					<div class="nano">
						<div class="nano-content">
							<nav id="menu" class="nav-main" role="navigation">
								<ul class="nav nav-main">
									
									<li class="active">
									  <a href="User_Auth?Routing=DashBoard">
										<i class="fa fa-tachometer"></i>
										<span class="font-bold">Dashboard</span>
									  </a>
									</li>
									
									<li>
									  <a href="PayslipDownload?Routing=Accounts" style="display:;"  >
										<i class="fa fa-users"></i>
										<span class="font-bold">Employee Health Report</span>
									  </a>
									 </li> 
									 
							<!--  
									<li>
									  <a href="NewJoinees?Routing=MyProfile"  >
										<i class="fa fa-user"></i>
										<span class="font-bold">Profile</span>
									  </a>
									</li>
									<li>
									  <a href="PayslipDownload">
										<i class="fa fa-download"></i>
										<span class="font-bold">Downloads</span>
									  </a>
									</li>
									<li>
									  <a href="hhcl_careers.jsp">
										<i class="fa fa-briefcase"></i>
										<span class="font-bold">Careers</span>
									  </a>
									</li>
									
									
							
								  <li style="display:<%=MGRFLAG_S%>;">
									  <a  href="ManagerApprovals?Routing=ManagerAppr" target="_parent">
										<i class="fa fa-check" ></i>
										<span class="font-bold">Manager Approvals</span>
									  </a>
									</li> 
									
									<li>
									  <a href="NewJoinees?Routing=DEPINFO">
										<i class="fa fa-users" ></i>
										<span class="font-bold">Department Information</span>
									  </a>
									</li>
									
									
									<li>
									  <a href="PayslipDownload?Routing=UPDATE" style="display:<%=HR_HRMS%>;"  >
										<i class="fa fa-user"></i>
										<span class="font-bold">HR/HRMS</span>
									  </a>
									 </li>  
									 
									 
									<li style="display:<%=TDSFLAG%>;">
									  <a href="http://mydesk.heterohcl.com/IT/" target="_blank">
										<i class="fa fa-money "></i>
										<span class="font-bold">TDS Declaration</span>
									  </a>
									</li>
									
									<li>
									  <a href="http://www.heterohealthcare.com/" target="_blank">
										<i class="fa fa-building-o "></i>
										<span class="font-bold">About Our Organization</span>
									  </a>
									</li>
									
									<li>
										<div id="datepicker" class="calendar"></div>
									</li>
									--->
								</ul>
							</nav>
							
				
							
						</div>
				
					</div>
				
				</aside>
				<!-- end: sidebar -->

				<section role="main" class="content-body">
					<header class="page-header">
						<div class="clear col-md-9" style="color:red;"> <marquee> <%=session.getAttribute("HHCL_EVENT_INFO")%>  </marquee></div>
					<div class="col-md-3 col-sm-3">
					<span><b>Date:</b> <span>
							<script>
									var mydate=new Date()
									var year=mydate.getYear()
									if (year < 1000)
									year+=1900
									var day=mydate.getDay()
									var month=mydate.getMonth()
									var daym=mydate.getDate()
									if (daym<10)
									daym="0"+daym
									var dayarray=new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
									var montharray=new Array("January","February","March","April","May","June","July","August","September","October","November","December")
									document.write("<small><font color='#0088cc' face='Arial'><b>"+dayarray[day]+", "+montharray[month]+" "+daym+", "+year+"</b></font></small>")

								</script>
					</span> </span>
					</div>
					</header>
					<!-- start: page -->
					
						<div class="row" style="padding-left:50px;">
							<div class="col-xs-12 col-sm-6 col-md-11">
								<div class="panel panel-primary" padding='50px 10px 20px 30px' >
								
								<!-- style='background-color:white; color:blacka;' -->
								
							<B>	&nbsp;&nbsp; Employee Helth Report </B> &nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;
								  Select Date : <input type='text'  name='my_date_picker'   id='my_date_picker' > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 
								 <input type='button' value='Get'  ng-click="myFunction('My')" style="background-color:#0088cc; color:white;" >
								 
								 <span id="ImgScroll" style="display:none;">
													<img  src="images/Loading_2.gif" width="24px" height="24px">
													</span>
									<!-- <div class="panel-heading1" height='50px'>
										<span><i class="fa fa-info-circle fa-lg"></i>&nbsp;&nbsp;&nbsp;Employee Data :  
										&nbsp;&nbsp;&nbsp;&nbsp; DATE <input type='date' value='' >
										</span>
										DATE <input type='date' value='' >
										
									</div> -->
									<div class="panel-body">
										<div class="col-md-12">
											<div class="table-responsive">
												<table class="table table-striped table-bordered">
													<thead class="thead-default " style="background-color:#f2f2f2; color:#777777;">
														<tr>
															
															<th class="text-left">EMP ID</th>
															<th class="text-left">Name</th>
															<th class="text-left">Record Date</th>
															<!-- <th class="text-left">In Time</th> -->
															<!-- <th class="text-center">Out Time</th> -->
															<th class="text-left">Temp (F)</th>
															<th class="text-left">Remark</th>
															
														</tr>
													</thead>
											
													<tr ng-repeat="x in Data_1">
														<td>{{ x.Sys_employeeID}}</td>
														<td>{{ x.Sys_fullName}}</td>
														<td>{{ x.Sys_loggedOn }}</td>
														
														<!-- <td>{{ x.Sys_inTime }}</td> -->
														<!-- <td>{{ x.Sys_autoID }}</td> -->
														  <td>{{ x.Sys_inTemp}}</td> 
														  
														  <td><span style='color:{{x.ColorCode}}'>{{ x.AllowedtoWork}}</span></td> 
														  
														  
														  
														  
													</tr>
													
													
												</table>
											</div>
										</div>
									</div>
								</div>	
							</div>
						</div>
					
					
					<!-- end: page -->
					
				</section>
			

			
		</section>
		
		<!-- Vendor -->
		<!-- <script src="assets/vendor/jquery/jquery.js"></script> venu -->
		<script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
		<script src="assets/vendor/bootstrap/js/bootstrap.js"></script>
		
		
		
		<!-- Specific Page Vendor -->
		<!-- <script src="assets/vendor/jquery-ui/js/jquery-ui-1.10.4.custom.js"></script> venu -->
		
		
		<!-- Theme Base, Components and Settings -->
		<script src="assets/javascripts/theme.js"></script>
		
		<!-- Theme Custom -->
		<script src="assets/javascripts/theme.custom.js"></script>
		
		<!-- Theme Initialization Files -->
		<script src="assets/javascripts/theme.init.js"></script>


		<!-- Examples -->
		<script src="assets/javascripts/dashboard/examples.dashboard.js"></script>
		<script src="assets/javascripts/pages/examples.calendar.js"></script>
		
	</body>
</html>