<!doctype html>
<html class="fixed">
	<head>
<%@page import="java.util.*" %>
<% 
  
Map EMAILDATA=new HashMap();
      try{
        EMAILDATA=(Map)session.getAttribute("EMAILDATA_MAP");
      }catch(Exception Err){
    	  
      }


  String Emp_BioData=(String)request.getAttribute("DOJ_DOB");
  String TDSFLAG=(String)session.getAttribute("TDSFLAG");
  String EMP_NAME=(String)session.getAttribute("EMP_NAME");
  
  String ATT_MONTHS=(String)request.getAttribute("ATT_MONTHS");
  
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

<%
	Random rand = new Random();
	int nRand = rand.nextInt(90000) + 10000;
	
%>


	<head>
	<script src="MyAng2.js"></script>
	<!-- <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.5/angular.min.js"></script> -->
<script>
var EMAILDATA_MNG="";
try{
 EMAILDATA_MNG= "<%=EMAILDATA.get("MNGEMAIL")%>";
}catch(err){
	
}
var app = angular.module('myApp', []);
app.controller('formCtrl', function($scope,$http) {
	var Data="";
	$scope.Data_2=<%=ATT_MONTHS%>;
	$scope.Data_1="";
	$scope.Data_3="";
	
	$scope.MAINTAB="";
	$scope.Data_H="";
	$scope.Data_H1="";
	$scope.Data_HH="";
	$scope.MAIN_DATE="";
	$scope.Data_21="";
	$scope.Data_31="";
	
	$scope.xy=1;
	$scope.Data_1=<%=Emp_BioData%>;
	//$scope.Data_1= $scope.Data_21.ATT ;
	//$scope.Data_31=$scope.Data_21.TOTAL;
	 
	 
	 
try{
	 $scope.myFunction = function(val) {
		 
		 document.getElementById("errMessage").innerHTML="";
		 
		 document.getElementById("image_scrl").style.display='';
		 document.getElementById("data_load").style.display='none';
		 
		if(val=="My") {
			
			document.getElementById("errMessage").innerHTML="";
				
			document.getElementById("from_1").value="";
			document.getElementById("to_1").value="";
			 
		 var Month_Sel=document.getElementById("Month_Sel").value;
		 var Month_View=document.getElementById("TypeView").value;
		 $scope.Data_1="";
		 //alert(Month_View);
	if(Month_View=="Payperiod"){
		
		//alert(Month_View);
		
		 $http({
		        method : "POST",
		        url : "Attendance_3334_assam?Routing=ATTENDANCE_BIO_DATES&ATT_FLAG=DATES&Month="+Month_Sel+"",		
		    }).then(function mySucces(response) {
		    	
		    	$scope.Data_1=response.data;
		    	//$scope.Data_21="";
		    	//$scope.Data_31="";
		    	//var Data_211= response.data;
		    	
		    	<%-- //$scope.Data_2=<%=Emp_BioData%>; --%>
		    	//$scope.Data_1 = Data_211.ATT ;
		    	//$scope.Data_3 = Data_211.TOTAL ;
		    	
		        //alert(response.data);
		      //  var length1=$scope.Data_1.length;
		    	//alert( $scope.Data_1[length1].TOTAL_DD_HOURS );
		    	
		    	document.getElementById("image_scrl").style.display='none';
				 document.getElementById("data_load").style.display='';
				 
		    }, function myError(response) {
		        $scope.Data_1 = response.statusText;

		    	document.getElementById("image_scrl").style.display='none';
				 document.getElementById("data_load").style.display='';
				 
		    });
	}else{
		
		 document.getElementById("image_scrl").style.display='';
		 document.getElementById("data_load").style.display='none';
		 
		$http({
	        method : "POST",
	        url : "Attendance_3334_assam?Routing=ATTENDANCE_BIO_DATES&ATT_FLAG=MONTHS&Month="+Month_Sel+"",	
	    }).then(function mySucces(response) {
	    	
	    	document.getElementById("image_scrl").style.display='';
			 document.getElementById("data_load").style.display='none';
			 
			 
	    	$scope.Data_1=response.data;
	    	//$scope.Data_31="";
	    	//var Data_211 = response.data;
	       // alert(response.data);
	       
	       <%-- //$scope.Data_2=<%=Emp_BioData%>; --%>
		    	//$scope.Data_1 = Data_211.ATT;
		    	//$scope.Data_3 = Data_211.TOTAL;
		    	
	    	/* alert($scope.Data_1.length);
	    	 var length1=$scope.Data_1.length;
	    	 
	    	 var obj = JSON.parse($scope.Data_1);
	    	   
	    	 alert(obj[length1].TOTAL_DD_HOURS);
	    	 
		    	alert( $scope.Data_1[length1].TOTAL_DD_HOURS ); */
	        document.getElementById("image_scrl").style.display='none';
			 document.getElementById("data_load").style.display='';
			 
	    }, function myError(response) {
	        $scope.Data_1 = response.statusText;
	        document.getElementById("image_scrl").style.display='none';
			 document.getElementById("data_load").style.display='';
	    });
		
	}
		 
}
		
		if(val=="My_Datas"){
			
			document.getElementById("errMessage").innerHTML="";
			
			var Month_FROM="";
			var Month_TO="";
			
			try{
			 Month_FROM=document.getElementById("from_1").value;
			 Month_TO=document.getElementById("to_1").value;
			
			  // alert(Month_FROM.length +"~haa~"+Month_TO.length );
			 
			 
			}catch(err){
				alert(err.message);
			}
			
			
			var Month_Sel="";
			 if(Month_FROM.length<1 || Month_TO.length<1 ){
				
				
				 document.getElementById("errMessage").innerHTML="Please Select From & To Date";
				 
				 //alert("Please Select From & To Date");
				 
				     return false;
			 } 
			 
			 else if(Month_FROM.length>1 && Month_TO.length>1)
				 {
				 
				 
				    var fromdate = Month_FROM;
					var todate = Month_TO;
					var from = new Date(fromdate);
					var to = new Date(todate);
					
					var diffDays = parseInt((to - from) / (1000 * 60 * 60 * 24));
					var Days = diffDays + 1;
					 // alert(Days);
					  if(Days<0 ){
						    
						  document.getElementById("errMessage").innerHTML='From date should be < To Date';
						  //alert("From date should be < To Date");
						   return false;
						   
					  }else if(Days>60){
						  
						  document.getElementById("errMessage").innerHTML="Please select period of 60 or <60 days only";
					 //alert("Please select period of 60 or <60 days only");
					return false;
					}
				 }
			 
			 $scope.Data_1="";
			 
			 $http({
			        method : "POST",
			        url : "Attendance?Routing=ATTENDANCE_BIO_DATES&ATT_FLAG=DATES&Month_FROM="+Month_FROM+"&Month_TO="+Month_TO+" ",
			        
			        		
			    }).then(function mySucces(response) {
			    	$scope.Data_1 = response.data;
			       // alert(response.data);
			      // alert($scope.Data_1.length);
			       
			    }, function myError(response) {
			        $scope.Data_1 = response.statusText;
			    });
		}
 }
   <%-- 	 $scope.Data_1=<%=Emp_BioData%>;
    $scope.Data_2=<%=EmpDOB%>;
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

	$scope.ICT_Req = function (data) {	
	try{
		
		// alert(data.DAFMAIN);
		 $scope.MAINTAB=data.DAFMAIN;
		 $scope.MAIN_DATE=data.DATE;
		 document.getElementById("date").value=data.DATE; 
		 document.getElementById("Requ_Date").innerHTML=data.DATE;
		 document.getElementById("Responce_Message").innerHTML='';
		 document.getElementById("Responce_Message_btn").style.display='';
		 document.getElementById("Requ_Date_Temp").value=data.INNER; 
		 document.getElementById("date1").value=data.DATE; 
		 document.getElementById("Requ_Date1").innerHTML=data.DATE;
		 document.getElementById("Responce_Message1").innerHTML='';
		 document.getElementById("Responce_Message_btn1").style.display='';
		 document.getElementById("Requ_Date_Temp1").value=data.INNER; 
		 
	}catch(err){
		alert(err.message);
	}
		 //url : "Attendance_assam?Routing=ATTENDANCE_BIO_DATES&ATT_FLAG=MONTHS&Month="+Month_Sel+"",
		 try{
              $scope.Data_H="";
			 $http({
			        method : "POST",
			        url: "AttendanceH?Routing=ATT_HOURS&MODE=FETCH&ATTDATE="+data.DATE+"",
			    }).then(function mySucces(response) {
			    	$scope.Data_H = response.data;
			    	var objbutt=$scope.Data_H.MAINBUTTON
			    	//alert(objbutt);
			    	//alert($scope.Data_H.HDATA);
			    	$scope.Data_H=$scope.Data_H.HDATA
			    	//alert($scope.Data_H);
			    if(objbutt=="true"){ 
			    	$('#myModal').modal({
				        show: 'true'
				  });
			    }else{
			    	
			    	$('#myModal_h').modal({
				        show: 'true'
				  });
			    }
			       // alert(response.data);
			    }, function myError(response) {
			        $scope.Data_1 = response.statusText;
			    });
			}catch(err){
				//alert(err.value);
			}
}
	
	
$scope.ICT_Req_view = function (data) {	
	try{
		 document.getElementById("date2").value=data.DATE; 
		 document.getElementById("Requ_Date3").innerHTML=data.DATE; 
	}catch(err){
		alert(err.message);
	}		 
		 try{
              $scope.Data_HH="";
			 $http({
			        method : "POST",
			        url: "AttendanceH?Routing=ATT_HOURS&&MODE=VIEW&ATTDATE="+data.DATE+"",
			    }).then(function mySucces(response) {
			    	$scope.Data_HH = response.data;
			    	var objbutt=$scope.Data_HH.MAINBUTTON
			    	$scope.Data_HH=$scope.Data_HH.HDATA
			    	$('#myModal_view').modal({
				        show: 'true'
				      });
			    }, function myError(response) {
			        $scope.Data_1 = response.statusText;
			    });
			}catch(err){
				//alert(err.value);
			}	
	}
		
$scope.AddParams=function(data){
	
	$("#Responce_Message1").html(" ");
//	document.getElementById("Responce_Message_btn1").style.display='';
	document.getElementById("Responce_Message_btn1").style.display='';
	var mail=document.getElementById("tags2").value;
	//alert(mail);
	var mailformat ="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$";
	
	if(mail.match(mailformat)){
		//alert("true");
	}else{
		//alert("false");
		///return false;
	}
	
	var Field1=document.getElementById(""+data.CKID+"_SUB");
	var Field2=document.getElementById(""+data.CKID+"_message");
	if(Field1.disabled){
		Field1.disabled=false;
	}else{
		Field1.value="Personal";
		Field1.disabled=true;
	}
	
	if(Field2.disabled){
		Field2.disabled=false;
	}else{
		Field2.value="";
		Field2.disabled=true;
	}
}
	
	
$scope.MyData=function(){
	
	
	 document.getElementById("Responce_Message1").innerHTML="";
	
	 document.getElementById("Responce_Message_btn1").style.display='none';
	var myObjmain=[];
  var Obj=document.getElementsByName("HCheckBox");
   var count=0;
   var fd = new FormData();
  try{ 
  for(var i=0;i<Obj.length;i++){
	if(Obj[i].checked){
		var keyID=Obj[i].value;
		var keySUBID=Obj[i].value+"_SUB";
		var keySUBID1=Obj[i].value+"_message";
		var v_Sel_sub=document.getElementById(keySUBID).value;
		var v_message_Sub=document.getElementById(keySUBID1).value;
		var myObj;
		///alert(keyID);
		myObj={
		   KEYID:""+keyID+"",
		   REQ_TYPE:v_Sel_sub,
		   REQ_MESSAGE:v_message_Sub
		  };
		fd.append("KEYID",keyID);
		fd.append("REQ_TYPE",v_Sel_sub);
		fd.append("REQ_MESSAGE",v_message_Sub);
		myObjmain.push(myObj);
		//myObjmain=JSON.stringify(myObjmain);
		count++;
}
  }  // loop for data catching end ;
  console.log(myObjmain +"myObjmain");
  myObjmain=JSON.stringify(myObjmain);
  
  var mail=document.getElementsByName("toemail1")[0].value;
  var ccemail1=document.getElementsByName("ccemail1")[0].value;
  //ccemail1
	//alert(mail);
	var mailformat ="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$";
	if(mail.length==0){
		
		 document.getElementById("Responce_Message1").innerHTML="Please enter  To e-mail ..!";
		 return false;
	}
	if(!mail.match(mailformat)){
		//alert("true");
		 document.getElementById("Responce_Message1").innerHTML="Please enter proper To e-mail ..!"
		 return false;
	}
	/* if(mail.length>0 && !ccemail.match(mailformat)){
		//alert("true");
		 document.getElementById("Responce_Message1").innerHTML="Please enter proper To e-mail ..!"
			return false;
	} */
	
	/* else{
		//alert("false");
		 $("#Responce_Message1").html("Please enter proper To e-mail ..!");
		return false;
	} */
	
  }catch(err){
	  console.log(err.message);
  }
	 
  if(count==0){
	 
	  console.log("i am in false condition ...!"+count)
	  document.getElementById("Responce_Message1").innerHTML="Invalid Request Please select Check box..!";
	  // document.getElementById("Responce_Message_btn1").style.display='';
	   return false;
   }else{
      //alert("myObj::"+myObjmain);
 //** Request ajex as jason object Data
      try{
    	  console.log("i am in true condition ...!"+count)
    	  
    	  fd.append("Routing","ATT_HOURS_INSERT");
    	  fd.append("MODE","INSERT");
    	//  alert("FormData" +fd.toString())
          $scope.Data_H1="";
          $scope.Data_H="";
      //  var formdata = new FormData();
     //  formdata.append("test", "testdata");
         // var Data=encodeURIComponent("Routing=ATT_HOURS_INSERT&MODE=INSERT&DATA="+JSON.stringify(myObjmain)+"");
    	  var requestdata = {
    	            method: 'POST',
    	            url: 'AttendanceH',
    	            data: fd,
    	            headers: {
    	                'Content-Type': "application/x-www-form-urlencoded"
    	            },
    	            encType : "multipart/form-data",
    	            cache : false,
                    processData : false,
                    contentType : false,
    	            params:{
    	            	Routing:"ATT_HOURS_INSERT",
    	            	MODE:"INSERT",
    	            	DATA:myObjmain,
    	            	EMAIL : mail ,
    	            	ccemail : ccemail1,
    	     		    }
    	        };
    	  $http(requestdata).success(function (d) {
    		  $scope.Data_H1 = d;
                 console.log("Inner Success message"+$scope.Data_H1);
        	     var objbutt=$scope.Data_H1.MAINBUTTON;
		    	 var Message=$scope.Data_H1.insertMessage;
		    	 console.log(Message +" :: Message");
		    	 document.getElementById("Responce_Message_btn1").style.display='';
		    	 document.getElementById("Responce_Message1").innerHTML=Message	 
		    	  $scope.Data_H=$scope.Data_H1.HDATA;
		    	  console.log($scope.Data_H +"---------->Data_H");
		    	if(objbutt=="true" && $scope.MAINTAB=="false"){
		    		 document.getElementById($scope.MAIN_DATE+"_BUTT").style.display='none';
		    		 document.getElementById("Responce_Message1").innerHTML=Message ;
		    	}
          }).error(function () {
        	  document.getElementById($scope.MAIN_DATE+"_BUTT").style.display='none';
        	  document.getElementById("Responce_Message1").innerHTML="Request Faild Please Try again/contact admin..!" ;
          });  
    	/* var res = $http.post('AttendanceH', dataObj);
  		console.log("URL" +res);
  		res.success(function(data, status, headers, config) {
  			
  			alert(data)
  			//$scope.message = data;
  		});
  		res.error(function(data, status, headers, config) {
  			alert( "failure message: " + JSON.stringify({data: data}));
  		});		 */
		 
		}catch(err){
			console.log("AAAA:::" + err.message);
		}	
 //** Request ajex as jason object Data        
   }	
}
}); 
</script>

<script>

 function AttendanceRequest(){
	 var date_Temp=document.getElementById("Requ_Date_Temp").value;
	 var Data_Split="";
	 var ReQDate="";
	 var FIN="";
	 var FOUT="";
	 var TIME="";
	 try{
	  	Data_Split=date_Temp.split("#");
	  	ReQDate=Data_Split[0];
	  	FIN=Data_Split[1];
	  	FOUT=Data_Split[2];
	  	TIME=Data_Split[3];
	 }catch(err){
		 alert(err);
	 }
 var  ccemail=document.getElementById("tags3").value;
 var toemail=document.getElementById("tags2").value;
 var message=document.getElementById("message").value;

    if(toemail.length <4){
    	
    	document.getElementById("Responce_Message").innerHTML="Please Enter To mail id.";
    	return false;
    	
    } else if(message.length < 3){
 
    	document.getElementById("Responce_Message").innerHTML="Please Enter Request Message";
    	return false;
    } 
	   var date=document.getElementById("date").value;
	   var Subject=document.getElementById("Subject").value;
	   
		var formData = {toemail:""+toemail+"",ccemail:""+ccemail+"",Routing:"Att_Request",id:""+ReQDate+"",Subject:""+Subject+"",message:""+message+"",FIN:""+FIN+"",FOUT:""+FOUT+"",TIME:""+TIME+"",RanDm:"<%=nRand%>" };
		
	try{
	    $.ajax({
	          type: "post",
	          url: "Attendance_flexi",
	          data: formData,
	          success: function(responseData, textStatus, jqXHR) {
	              //alert(responseData);
	             // var resp=eval(responseData);
	             try{
	              document.getElementById("Responce_Message").innerHTML=responseData;
	              document.getElementById("Responce_Message_btn").style.display='none';
	             // alert("date:"+date);
	              //document.getElementById(""+date+"").innerHTML='Processed';
	              document.getElementById(date+"_BUTT").style.display='none';
	              document.getElementById(date+"_ST").innerHTML='Processed';
	             		//document.getElementById("date").value='';
	       	   			//document.getElementById("Subject").value='';
	       	  			document.getElementById("message").value='';
	       	  	
	             }catch(err){
	            	 //alert(err);
	             }
	          },
	          error: function(jqXHR, textStatus, errorThrown) {
	              console.log(errorThrown);
	              document.getElementById("Responce_Message").innerHTML=errorThrown;
	              document.getElementById("Responce_Message_btn").style.display='';
	             // alert("Error;");
	          }
	      })
	}catch(err){
		
		//alert(err.value);
	}
	  
 }
 
 
 
 function AttendanceRequest_Month(){
	 
	  // alert("2");
		var formData = {Routing:"ATTENDANCE_BIO_DATES", FromDate:"20-20-11",ToDate:"22-20-1986"  };
	try{
	    $.ajax({
	          type: "post",
	          url: "Attendance",
	          data: formData,
	          success: function(responseData, textStatus, jqXHR) {
	            
	               var resp=eval(responseData);
	               
				   //alert("resp::"+resp);
				   
				   $scope.Data_1=eval(responseData);
				  
				   //return  eval(responseData);
                     
				   //myFunction('My');
				   
				   
					   
	             /* try{
	             }catch(err){
	            	 alert(err);
	             } */
	          },
	          error: function(jqXHR, textStatus, errorThrown) {
	              console.log(errorThrown);
	              alert("Error;");
	          }
	      })
	}catch(err){
		 alert(err.value);
	}
	return  eval(responseData);
}
 
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
		<!-- <link rel="stylesheet" href="assets/vendor/bootstrap-datepicker/css/datepicker3.css" /> -->
		

		<!-- Specific Page Vendor CSS -->
		<!-- <link rel="stylesheet" href="assets/vendor/jquery-ui/css/ui-lightness/jquery-ui-1.10.4.custom.css" /> -->
 <link rel="stylesheet" href="jqueryui_date.css">
		

		<!-- Theme CSS -->
		<link rel="stylesheet" href="assets/stylesheets/theme.css" />

		<!-- Skin CSS -->
		<link rel="stylesheet" href="assets/stylesheets/skins/default.css" />

		<!-- Theme Custom CSS -->
		<link rel="stylesheet" href="assets/stylesheets/theme-custom.css">

		<!-- Head Libs -->
		<script src="assets/vendor/modernizr/modernizr.js"></script>
		
			<!-- Specific Page Vendor CSS -->
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
		
   <!-- <script src="jquery.js"></script>
  <script src="jqueryui.js"></script> -->
		   <script>
 /*  $( function() {
    
      from = $( "#from_1" )
        .datepicker({
           changeMonth: true,
           changeYear:true,
          dateFormat: "yy-mm-dd",
          maxDate: 0,
          yearRange: '2016:2020',
		  
        })
        .on( "change", function() {
          to.datepicker( "option", "minDate", getDate( this ) );
        }),
      to = $( "#to_1" ).datepicker({
         changeMonth: true,
         changeYear:true,
		 dateFormat: "yy-mm-dd",
		 maxDate: 0,
		 yearRange: '2016:2020',
		 
		
        
      })
      .on( "change", function() {
        from.datepicker( "option", "maxDate", getDate( this ) );
      });
 
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
  } ); */
  $( function() {
  $("#from_1").datepicker({
	  
	  changeMonth: true,
      changeYear:true,
		maxDate : 0,
		dateFormat : 'yy-mm-dd',
		 yearRange: '2016:2020',
		onSelect : function(date) {
			$("#to_1").datepicker('option', 'minDate', date);
		}
	});

	$("#to_1").datepicker({
		changeMonth: true,
        changeYear:true,
        maxDate: 0,
		dateFormat : 'yy-mm-dd',
			 yearRange: '2016:2020'

	});
  });

  </script>
		<!-- <style>
		tbody{
		display:block;
		height:350px;
		width:auto;
		overflow-y:auto;
		}
		thead,tbody tr{
		display:table;
		width:100%;
		table-layout:fixed;
		}
		thead{
		width: calc(100%-4em);
		}
		</style> -->
<style>

.Selects {
    border: 1px solid #E5E7E9;
	border-radius: 2px;
	height: 30px;
	padding: 2px;
	outline: none;
}

</style>

  <script>
  $( function() {
	  $( "#tags2" ).val(EMAILDATA_MNG);
	 // $( "#tags3" ).val(EMAILDATA_MNG);
	  document.getElementsByName("toemail1")[0].value=EMAILDATA_MNG;
	  var availableTags = 
		  [
"abhisheksingh@heterohealthcare.com",
"ajaykumar.s@heterohealthcare.com",
"aksahoo@heterohealthcare.com",
"aliahmed@heterohealthcare.com",
"alok.p@heterohealthcare.com",
"anuradha@heterohealthcare.com",
"aurokanta.m@heterohealthcare.com",
"b.jena@heterohealthcare.com",
"bhanupratap@heterohealthcare.com",
"bharat.gehalot@heterohealthcare.com",
"bose@heterohealthcare.com",
"brajgaurav@azistaindustries.com",
"brijeshsharma@heterohealthcare.com",
"chiranjeevi.velagala@heterohealthcare.com",
"csreddy@heterohealthcare.com",
"damodararao.m@heterohealthcare.com",
"deepak@heterohealthcare.com",
"deepaksharma@heterohealthcare.com",
"deshmukh.v@heterohealthcare.com",
"diaspaahmedabad.asm@heterohealthcare.com",
"diaspaaurangabad.asm@heterohealthcare.com",
"diaspabangalore.asm@heterohealthcare.com",
"diaspabarasat.fe@heterohealthcare.com",
"diaspabaroda.asm@heterohealthcare.com",
"diaspabhopal.asm@heterohealthcare.com",
"diaspabhubaneswar.asm@heterohealthcare.com",
"diaspacalicut.asm@heterohealthcare.com",
"diaspachandigarh.asm@heterohealthcare.com",
"diaspachennai.asm1@heterohealthcare.com",
"diaspachennai.asm2@heterohealthcare.com",
"diaspacoimbatore.asm@heterohealthcare.com",
"diaspacuttack.asm@heterohealthcare.com",
"diaspadelhi.asm1@heterohealthcare.com",
"diaspadelhi.asm2@heterohealthcare.com",
"diaspadelhi.asm3@heterohealthcare.com",
"diaspaernakulam.asm@heterohealthcare.com",
"diaspaguwahati.asm@heterohealthcare.com",
"diaspahowrah.asm@heterohealthcare.com",
"diaspahubli.asm@heterohealthcare.com",
"diaspahyderabad.asm1@heterohealthcare.com",
"diaspahyderabad.asm2@heterohealthcare.com",
"diaspaindore.asm@heterohealthcare.com",
"diaspajabalpur.asm@heterohealthcare.com",
"diaspajaipur.asm@heterohealthcare.com",
"diaspajodhpur.asm@heterohealthcare.com",
"diaspakanpur.asm@heterohealthcare.com",
"diaspakolhapur.asm@heterohealthcare.com",
"diaspakolkata.asm1@heterohealthcare.com",
"diaspakolkata.asm2@heterohealthcare.com",
"diaspakolkata.fe3@heterohealthcare.com",
"diaspakurnool.asm@heterohealthcare.com",
"diaspalucknow.asm@heterohealthcare.com",
"diaspaludhiana.asm@heterohealthcare.com",
"diaspamadurai.asm@heterohealthcare.com",
"diaspamangalore.asm@heterohealthcare.com",
"diaspameerut.asm@heterohealthcare.com",
"diaspamumbai.asm1@heterohealthcare.com",
"diaspamumbai.asm2@heterohealthcare.com",
"diaspamumbai.asm3@heterohealthcare.com",
"diaspamuzaffarpur.asm@heterohealthcare.com",
"diaspanagpur.asm@heterohealthcare.com",
"diaspapatna.asm@heterohealthcare.com",
"diaspapune.asm1@heterohealthcare.com",
"diaspapune.asm2@heterohealthcare.com",
"diasparajkot.asm@heterohealthcare.com",
"diasparanchi.asm@heterohealthcare.com",
"diaspasilchar.asm@heterohealthcare.com",
"diaspasiliguri.asm@heterohealthcare.com",
"diaspasrinagar.asm@heterohealthcare.com",
"diaspavaranasi.asm@heterohealthcare.com",
"diaspavijayawada.asm@heterohealthcare.com",
"diaspavizag.asm@heterohealthcare.com",
"dilip.t@heterohealthcare.com",
"hiradhar@heterohealthcare.com",
"jagdish.n@heterohealthcare.com",
"jitendrasingh@heterohealthcare.com",
"kalyandas@heterohealthcare.com",
"kotireddy@heterohealthcare.com",
"kvkreddy@heterohealthcare.com",
"manasp@heterohealthcare.com",
"mangesh.p@heterohealthcare.com",
"mani.ps@heterohealthcare.com",
"manoj.saxena@heterohealthcare.com",
"manoj@heterohealthcare.com",
"mitulpatel@heterohealthcare.com",
"nagaraju.v@heterohealthcare.com",
"nilay.chatterjee@heterohealthcare.com",
"nishanth.d@heterohealthcare.com",
"nitinb@heterohealthcare.com",
"obulareddy.pbc@heterohealthcare.com",
"pankajkumar.r@heterohealthcare.com",
"rakesh.singh@heterohealthcare.com",
"ramanuja@heterohealthcare.com",
"ramaswamy.v@heterohealthcare.com",
"ravi.k@heterohealthcare.com",
"ravichandra@heterohealthcare.com",
"s@heterohealthcare.com",
"saikatchakraborty@heterohealthcare.com",
"saikumar.b@heterohealthcare.com",
"sandhya@heterohealthcare.com",
"sangeetha.b@heterohealthcare.com",
"sanjeev.m@heterohealthcare.com",
"santhoshreddy.y@heterohealthcare.com",
"sdprasad@heterohealthcare.com",
"shantaram.p@heterohealthcare.com",
"sp.coiabm@heterohealthcare.com",
"sp.hydabm@heterohealthcare.com",
"sp.punabm@heterohealthcare.com",
"sp.thrabm@heterohealthcare.com",
"sp.triabm@heterohealthcare.com",
"sp.vijabm@heterohealthcare.com",
"sridhars@heterohealthcare.com",
"srihari.b@heterohealthcare.com",
"srinivasa.ch@heterohealthcare.com",
"srinivasarao.m@heterohealthcare.com",
"srreddy@heterohealthcare.com",
"subash@heterohealthcare.com",
"sufiyanahmed@heterohealthcare.com",
"sunil@azistaindustries.com",
"sunilp@heterohealthcare.com",
"sunitgupta@heterohealthcare.com",
"sureshbabu.n@heterohealthcare.com",
"sureshbabu@heterohealthcare.com",
"talk2pranabkr@gmail.com",
"varunt@heterohealthcare.com",
"volga.cheasm@heterohealthcare.com",
"volga.delasm@heterohealthcare.com",
"volga.jaiasm@heterohealthcare.com",
"volga.kolasm@heterohealthcare.com",
"yjojireddy@heterohealthcare.com"

		  ];
	      try{
		     availableTags.push(EMAILDATA_MNG);
		     }catch(err){}
		     
    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
    $( ".addresspicker" ).autocomplete({
        source: availableTags
      });
  // $('#myModal').modal('show');
  
    $( "#tags2" )
      // don't navigate away from the field on tab when selecting an item
      .on( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( "," );
          return false;
        }
      });
  } );
  </script>
  <script>
  $( function() {
	  var availableTags = 
		  [
		  "abhisheksingh@heterohealthcare.com",
"ajaykumar.s@heterohealthcare.com",
"aksahoo@heterohealthcare.com",
"aliahmed@heterohealthcare.com",
"alok.p@heterohealthcare.com",
"anuradha@heterohealthcare.com",
"aurokanta.m@heterohealthcare.com",
"b.jena@heterohealthcare.com",
"bhanupratap@heterohealthcare.com",
"bharat.gehalot@heterohealthcare.com",
"bose@heterohealthcare.com",
"brajgaurav@azistaindustries.com",
"brijeshsharma@heterohealthcare.com",
"chiranjeevi.velagala@heterohealthcare.com",
"csreddy@heterohealthcare.com",
"damodararao.m@heterohealthcare.com",
"deepak@heterohealthcare.com",
"deepaksharma@heterohealthcare.com",
"deshmukh.v@heterohealthcare.com",
"diaspaahmedabad.asm@heterohealthcare.com",
"diaspaaurangabad.asm@heterohealthcare.com",
"diaspabangalore.asm@heterohealthcare.com",
"diaspabarasat.fe@heterohealthcare.com",
"diaspabaroda.asm@heterohealthcare.com",
"diaspabhopal.asm@heterohealthcare.com",
"diaspabhubaneswar.asm@heterohealthcare.com",
"diaspacalicut.asm@heterohealthcare.com",
"diaspachandigarh.asm@heterohealthcare.com",
"diaspachennai.asm1@heterohealthcare.com",
"diaspachennai.asm2@heterohealthcare.com",
"diaspacoimbatore.asm@heterohealthcare.com",
"diaspacuttack.asm@heterohealthcare.com",
"diaspadelhi.asm1@heterohealthcare.com",
"diaspadelhi.asm2@heterohealthcare.com",
"diaspadelhi.asm3@heterohealthcare.com",
"diaspaernakulam.asm@heterohealthcare.com",
"diaspaguwahati.asm@heterohealthcare.com",
"diaspahowrah.asm@heterohealthcare.com",
"diaspahubli.asm@heterohealthcare.com",
"diaspahyderabad.asm1@heterohealthcare.com",
"diaspahyderabad.asm2@heterohealthcare.com",
"diaspaindore.asm@heterohealthcare.com",
"diaspajabalpur.asm@heterohealthcare.com",
"diaspajaipur.asm@heterohealthcare.com",
"diaspajodhpur.asm@heterohealthcare.com",
"diaspakanpur.asm@heterohealthcare.com",
"diaspakolhapur.asm@heterohealthcare.com",
"diaspakolkata.asm1@heterohealthcare.com",
"diaspakolkata.asm2@heterohealthcare.com",
"diaspakolkata.fe3@heterohealthcare.com",
"diaspakurnool.asm@heterohealthcare.com",
"diaspalucknow.asm@heterohealthcare.com",
"diaspaludhiana.asm@heterohealthcare.com",
"diaspamadurai.asm@heterohealthcare.com",
"diaspamangalore.asm@heterohealthcare.com",
"diaspameerut.asm@heterohealthcare.com",
"diaspamumbai.asm1@heterohealthcare.com",
"diaspamumbai.asm2@heterohealthcare.com",
"diaspamumbai.asm3@heterohealthcare.com",
"diaspamuzaffarpur.asm@heterohealthcare.com",
"diaspanagpur.asm@heterohealthcare.com",
"diaspapatna.asm@heterohealthcare.com",
"diaspapune.asm1@heterohealthcare.com",
"diaspapune.asm2@heterohealthcare.com",
"diasparajkot.asm@heterohealthcare.com",
"diasparanchi.asm@heterohealthcare.com",
"diaspasilchar.asm@heterohealthcare.com",
"diaspasiliguri.asm@heterohealthcare.com",
"diaspasrinagar.asm@heterohealthcare.com",
"diaspavaranasi.asm@heterohealthcare.com",
"diaspavijayawada.asm@heterohealthcare.com",
"diaspavizag.asm@heterohealthcare.com",
"dilip.t@heterohealthcare.com",
"hiradhar@heterohealthcare.com",
"jagdish.n@heterohealthcare.com",
"jitendrasingh@heterohealthcare.com",
"kalyandas@heterohealthcare.com",
"kotireddy@heterohealthcare.com",
"kvkreddy@heterohealthcare.com",
"manasp@heterohealthcare.com",
"mangesh.p@heterohealthcare.com",
"mani.ps@heterohealthcare.com",
"manoj.saxena@heterohealthcare.com",
"manoj@heterohealthcare.com",
"mitulpatel@heterohealthcare.com",
"nagaraju.v@heterohealthcare.com",
"nilay.chatterjee@heterohealthcare.com",
"nishanth.d@heterohealthcare.com",
"nitinb@heterohealthcare.com",
"obulareddy.pbc@heterohealthcare.com",
"pankajkumar.r@heterohealthcare.com",
"rakesh.singh@heterohealthcare.com",
"ramanuja@heterohealthcare.com",
"ramaswamy.v@heterohealthcare.com",
"ravi.k@heterohealthcare.com",
"ravichandra@heterohealthcare.com",
"s@heterohealthcare.com",
"saikatchakraborty@heterohealthcare.com",
"saikumar.b@heterohealthcare.com",
"sandhya@heterohealthcare.com",
"sangeetha.b@heterohealthcare.com",
"sanjeev.m@heterohealthcare.com",
"santhoshreddy.y@heterohealthcare.com",
"sdprasad@heterohealthcare.com",
"shantaram.p@heterohealthcare.com",
"sp.coiabm@heterohealthcare.com",
"sp.hydabm@heterohealthcare.com",
"sp.punabm@heterohealthcare.com",
"sp.thrabm@heterohealthcare.com",
"sp.triabm@heterohealthcare.com",
"sp.vijabm@heterohealthcare.com",
"sridhars@heterohealthcare.com",
"srihari.b@heterohealthcare.com",
"srinivasa.ch@heterohealthcare.com",
"srinivasarao.m@heterohealthcare.com",
"srreddy@heterohealthcare.com",
"subash@heterohealthcare.com",
"sufiyanahmed@heterohealthcare.com",
"sunil@azistaindustries.com",
"sunilp@heterohealthcare.com",
"sunitgupta@heterohealthcare.com",
"sureshbabu.n@heterohealthcare.com",
"sureshbabu@heterohealthcare.com",
"talk2pranabkr@gmail.com",
"varunt@heterohealthcare.com",
"volga.cheasm@heterohealthcare.com",
"volga.delasm@heterohealthcare.com",
"volga.jaiasm@heterohealthcare.com",
"volga.kolasm@heterohealthcare.com",
"yjojireddy@heterohealthcare.com"

		  ];
	  
	  try{
		     availableTags.push(EMAILDATA_MNG);
		     }catch(err){}
		     
		     
		     
    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    $( "#tags3" )
      // don't navigate away from the field on tab when selecting an item
      .on( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( "," );
          return false;
        }
      });
  } );
  </script> 
 <style>
.ui-autocomplete-input {
  border: none; 
  font-size: 14px;
  width:100%;
  height: 34px;
  margin-bottom: 5px;
  padding-top: 2px;
  border: 1px solid #DDD !important;
  padding-top: 0px !important;
  z-index: 1511;
  position: relative;
}
.ui-menu .ui-menu-item a {
  font-size: 12px;
  color:#0088cc;
}
.ui-autocomplete {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1051 !important;
  float: left;
  display: none;
  min-width: 160px;
  _width: 160px;
  padding: 4px 0;
  margin: 2px 0 0 0;
  list-style:none;
  background-color: #ffffff;
  color:#0088cc;
  border-color: #ccc;
  border-color: rgba(0, 0, 0, 0.2);
  border-style: solid;
  border-width: 1px;
  -webkit-border-radius: 2px;
  -moz-border-radius: 2px;
  border-radius: 2px;
  -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  -webkit-background-clip: padding-box;
  -moz-background-clip: padding;
  background-clip: padding-box;
  *border-right-width: 2px;
  *border-bottom-width: 2px;
}
.ui-menu-item > a.ui-corner-all {
    display: block;
    padding: 3px 15px;
    clear: both;
    font-weight: normal;
    line-height: 18px;
    color: #0088cc;
    white-space: nowrap;
    text-decoration: none;
}
/* .ui-state-hover, .ui-state-active {
      color: #ffffff;
      text-decoration: none;
      background-color: #0088cc;
      border-radius: 0px;
      -webkit-border-radius: 0px;
      -moz-border-radius: 0px;
      background-image: none;
} */
.ui-state-hover,
.ui-widget-content .ui-state-hover,
.ui-widget-header .ui-state-hover,
.ui-state-focus,
.ui-widget-content .ui-state-focus,
.ui-widget-header .ui-state-focus {
	border: 1px solid #fbcb09;
	background: #fdf5ce url("images/ui-bg_glass_100_fdf5ce_1x400.png") 50% 50% repeat-x;
	font-weight: bold;
	color: #0088cc;
}
.ui-state-hover a,
.ui-state-hover a:hover,
.ui-state-hover a:link,
.ui-state-hover a:visited,
.ui-state-focus a,
.ui-state-focus a:hover,
.ui-state-focus a:link,
.ui-state-focus a:visited {
	color: #0088cc;
	text-decoration: none;
}
/* #modalIns{
    width: 500px;
} */
.select {
	border: 1px solid #E5E7E9;
	border-radius: 6px;
	height: 25px;
	padding: 2px;
	outline: none;
	color:#0088cc;
	margin-bottom:10px;
	
}

.panel-headingattn{
        height:50px;
		background-color:#0088cc;
		line-height:20px;
		color:#fff;
		padding-left:20px;
		padding-top:10px;
		
}
.btn1{
  display: inline-block;
  margin-bottom: 0;
  font-weight: normal;
  text-align: center;
  vertical-align: middle;
  touch-action: manipulation;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  white-space: nowrap;
  padding: 3px 3px;
  font-size: 10px;
  line-height: 1.42857143;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  background-color:#fff;
  color:#0088cc;
}
.form-control1 {
    display: block;
    width: 100%;
    height: 20px;
    padding: 3px 6px;
    font-size: 10px;
    line-height: 1.42857143;
    color: #555555;
    background-color: #ffffff;
    background-image: none;
    border: 1px solid #cccccc;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -webkit-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}




</style>

<script>
  function disableBackButton() {
	   window.history.forward();
	}
	setTimeout("disableBackButton()", 0);

 </script>
 <style>
 .modal {
}
.vertical-alignment-helper {
    display:table;
    height: 100%;
    width: 100%;
}
.vertical-align-center {
    /* To center vertically */
    display: table-cell;
    vertical-align: middle;
}
.modal-content {
    /* Bootstrap sets the size of the modal in the modal-dialog class, we need to inherit it */
    width:inherit;
    height:inherit;
    /* To center horizontally */
    margin: 0 auto;
}
</style>
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

	</head>
	<body  ng-app="myApp" ng-controller="formCtrl"  onload="disableBackButton(); noBack();">
		<section class="body">

			<!-- start: header -->
			<header class="header">
				<div class="logo-container">
					<a href="#l" class="logo">
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
									  <a href="NewJoinees?Routing=MyProfile" >
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
										<!-- <div id="datepicker" class="calendar"></div> -->
									</li>
								</ul>
							</nav>
							
				
							
						</div>
				
					</div>
				
				</aside>
				<!-- end: sidebar -->

				<section role="main" class="content-body">
					<header class="page-header">
						
						<span style="padding-left:850px;"><b>Date:</b>
							<span>
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
							</span>
						</span>
					</header>
					<!-- start: page  ng-selected="item.selected == true"  ng-change='myFunction(this.value);' -->
					 <!--  <select id="Month_Sel" >
					  <option  ng-repeat="(key, value) in Data_2" ng-click="key==9" value="{{key}}"> {{value}} </option>
					  </select> -->
					   &nbsp; &nbsp; &nbsp; 
					   
					    <div id='errMessage' align='center' style='color:red'>    </div>
						<div class="row" style="padding-left:50px;">
						  
						  
							<div class="col-xs-12 col-sm-6 col-md-11">
								<div class="panel panel-primary">
									<div class="panel-headingattn">
										<div class="col-md-3" style="margin-top:5px;">
										 <select class="select" id="Month_Sel" >
										    <option value="currmonth" selected> Current Month </option>
									  		<option  ng-repeat="(key, value) in Data_2"  value="{{key}}"  ng-selected="currmonth"> {{value}} </option>
									  	</select>&nbsp;&nbsp;&nbsp;
									  	<button class="btn1"  ng-click="myFunction('My')">View</button>
									  	</div>
									  	<div class="col-md-6" style="margin-top:5px;" >
									  	
									  	 <span class="col-sm-8">
									  	 Select View Type :
									  	 <select class="select" id='TypeView' >
									  	 <option value='Monthly'>Monthly</option>
									  	 <option value='Payperiod' selected >Payperiod</option>
									  	 </select>
									  	 </span>
									  	
									  	
									  	<div style='display:none'>
									   <span class="col-sm-1">From</span>
									  	<div class="col-sm-3">
									  	<input type="text" id="from_1" name="from"  readOnly class="form-control1">
									  	</div>
									  	<span class="col-sm-1">To</span>
									  	<div class="col-sm-3">
									  	<input type="text" id="to_1" name="to" readOnly class="form-control1">
									  	</div>
									  	<button class="btn1"  ng-click="myFunction('My_Datas')">View</button> 
									  	
									  	</div>
									  </div>
									  <div class="col-md-3">
										<span><i class="fa fa-calendar fa-lg"></i>&nbsp;Attendance Information </span>
										</div>
										
										<!-- <button onclick="AttendanceRequest_Month();"> ClickAjex </button> &nbsp; <button ng-click="myFunction('My')">Click Me!</button> -->	
										
									</div>
									  
									  <span align='center' id='image_scrl' style='display:none;'>
   										<img src="assets/images/spinner-blue1.gif" width='10%' height='10%' />
 										</span>
 										
									<div class="panel-body" id="data_load" style='display:'>
										<div class="col-md-12">
											<div class="table-responsive">
												<table class="table table-striped ">
													<thead class="thead-default " style="background-color:#f2f2f2; color:#777777;">
														<tr>
															
															<th class="text-center">Date</th>
															<th class="text-center">IN</th>
															<th class="text-center">OUT</th>
															<th class="text-center">Net Hours</th>
															<th class="text-center">Day Type</th>
															<th class="text-center">Shift</th>
															<th class="text-center">(+ / -) Hours</th>
															
															<th class="text-center">Late Deduction</th>
															
															<th class="text-center">Adjustments</th>
															<th class="text-center">Request Status</th>
															
															<th class="text-center">Hours Status</th>
															
														</tr>
													</thead>
											
						
						
													<tr ng-repeat="x in Data_1" ID="{{ x.DATE }}" style='background-color:{{ x.ROCOLOR }}'>
														<td class="text-center">{{ x.DATE }}</td>
														<td class="text-center">{{ x.FIN }}</td>
														<td class="text-center">{{ x.FOUT }}</td>
														
														<td class="text-center">{{ x.PERDAY }}</td>
														<td class="text-center">{{ x.DAYTYPE }}</td>
														<td class="text-center">{{ x.SHIFT }}</td>
														<td class="text-center">{{ x.LESSHOURS }}</td>
														
														<td class="text-center">{{ x.DEDHOURS_NET }}</td>
														
														  <td align="center">  
														  
														 <!--  <input type='button' class="btn btn-primary btn-sm"  name="{{ x.INNER }}" id="{{ x.DATE }}" data-toggle="modal" data-target="#myModal" id="myBtn" onclick="ICT_Req(this);" value='Request' style='display:{{ x.DAF }}'  >  
														 -->  
											<input type='button' class="btn btn-primary btn-sm"  name="{{ x.INNER }}" id="{{ x.DATE }}_BUTT"  id="myBtn"  ng-click="ICT_Req(x);" value='Request' style='display:{{ x.DAF }}'  >
														   </td> 
														   <td class="text-center"> <span  id="{{ x.DATE }}_ST" > {{ x.DAREQ }}  </span> </td>
														  
														  <td align="center">  
														  
														
											<input type='button' class="btn btn-primary btn-sm"   id="myBtn" ng-click="ICT_Req_view(x);" value='View' style='display:{{ x.DAFH }}'  >
														   </td> 
														    
														    
													</tr>
													
													<!-- TOTAL_DD_HOURS -->
													<tr >
													 <td> &nbsp; </td> 
													 <td> &nbsp; </td>
													 <td> &nbsp; </td>
													 <td> &nbsp; </td>
													 <td> &nbsp; </td>
													 <td> &nbsp; </td>
													 <td> <B>Net Hours:</B> </td>
													 <td align='center'><b> {{Data_1[Data_1.length-1].TOTAL_DD_HOURS}} hrs. </b> </td> 
													 <td> &nbsp; </td>
													 <td> &nbsp; </td>
													 <td> &nbsp; </td>
													</tr>
												</table>
												
												
											</div>
										</div>
									</div>
									
									  <span><b>Deduction Hours As per Policy Attached Here: 
                                            <a href="HHCL_HR_POLICY_BU33.html" target="_blank"><i class="fa fa-paperclip" style="font-size:28px;color:red" aria-hidden="true"></i></a>
                                            </b></span>
                                            
								</div>	
							</div>
						</div>
					
					
					<!-- end: page -->
					
				</section>
			

			
		</section>
		
		 <!-- 										 btn btn-danger
														 btn btn-success
														 btn btn-primary
														 -->  
		<!-- Model Box -->
		 <!--request Modal-->
					<div class="modal fade" id="myModal"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="vertical-alignment-helper">
					  <div class="modal-dialog vertical-align-center" role="document" >
						<div class="modal-content">
						  <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							  <span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Send Request</h4>
							<!-- Please Submit in between Payroll i.e., 26-previous Month-Current Year ------   27-Current Month-Current Year -->
						  </div>
							<div class="modal-body">
								<form action="ictrequestmail" method="post" >
									
									<!-- <div class="form-group">
									<label class="col-md-4 control-label">To</label>
									  <div class="col-sm-8">
									    <input type="text" class="form-control" name="to" id="to">
									  </div>
									</div>
									<div class="form-group">
									  <label class="col-md-4 control-label">CC</label>
									   <div class="col-sm-8">
									    <input type="text" class="form-control" name="cc" id="cc">
									  </div>
									</div> -->
									
									<div class="form-group">
									  <label class="col-md-4 control-label">Request Date</label>
									   <div class="col-sm-8">
									    <span id="Requ_Date"> </span>
									    
									    <input type="hidden" class="form-control" name="date" id="date"  readOnly>
									    
									     <input type="hidden" class="form-control" name="Requ_Date_Temp" id="Requ_Date_Temp"  >
									     
									    
									    
									    
									  </div>
									</div>
									
									<div class="form-group">
									  <label class="col-md-4 control-label">To-Mail</label>
									   <div class="col-sm-8">
									     <input type="email" class="form-control addresspicker" name="toemail"  id="tags2" value="" placeholder="someone@example.com" readOnly>
									  </div>
									</div>
									
									
									<div class="form-group" style='display:none;'>
									  <label class="col-md-4 control-label">CC-Mail</label>
									   <div class="col-sm-8">
									     <input type="email" class="form-control addresspicker" name="ccemail"  id="tags3" value="" placeholder="someone@example.com" >
									  </div>
									</div>
									
									
									
									<div class="form-group">
									  <label class="col-md-4 control-label">Request Type</label>
									   <!-- <div class="col-sm-8">
									     <input type="text" class="form-control" name="Subject" id="Subject" value="Re: Incomplete Hours" >
									  </div> -->
									  <div class="col-sm-8">
									     <!-- <input type="text" class="form-control" name="Subject" id="Subject" value="Re: Incomplete Hours" > -->
									     
									      <select name="Subject" id="Subject" class="form-control">
									      <option value="Incomplete">Incomplete Hours</option>
									      <option value="Mechineproblem">Mechine Not Working</option>
									      <option value="Swipemiss">Forgot Swipe</option>
									      <option value="ODwork">On Duty</option>
									      <option value="HODPermisssion">HOD Permission</option>
									      
									      </select>
									     
									     
									     
									  </div>
									</div>
									
									<div class="form-group">
										<label class="col-md-4 control-label">Request Message</label>
										<div class="col-md-8">
											<textarea class="form-control" rows="5" id="message" name="message"></textarea>
										</div>
									</div>
									
								

							</div>
							<div class="modal-footer">
							
							<span id="Responce_Message" style="color:red;" class="align-left"> </span>
							
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>&nbsp;
							<input type="button" id="Responce_Message_btn" style="display:" class="btn btn-primary" onclick="AttendanceRequest()" value="Send Request"/>
							
							
							
							</div>
							
						  </div>
						  
						  </div>
						  </div>
						  </div>
<!--  NEW Model Box For HourS Permission Start -->

<div class="modal fade" id="myModal_h" style='width:100%' tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="vertical-alignment-helper">
					  <div class="modal-dialog vertical-align-center" role="document">
						<div class="modal-content">
						  <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							  <span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Attendance Hours Request</h4>
							<!-- Please Submit in between Payroll i.e., 26-previous Month-Current Year ------   27-Current Month-Current Year -->
						  </div>
							<div class="modal-body" style='width:100%'>
								<form action="ictrequestmail_Hours" method="post" >
									
									<!-- <div class="form-group">
									<label class="col-md-4 control-label">To</label>
									  <div class="col-sm-8">
									    <input type="text" class="form-control" name="to" id="to">
									  </div>
									</div>
									<div class="form-group">
									  <label class="col-md-4 control-label">CC</label>
									   <div class="col-sm-8">
									    <input type="text" class="form-control" name="cc" id="cc">
									  </div>
									</div> -->
									
									<div class="form-group">
									  <label class="col-md-4 control-label">Request Date</label>
									   <div class="col-sm-8">
									   <b> <span id="Requ_Date1"> </span></b>
									    
									    <input type="hidden" class="form-control" name="date1" id="date1"  readOnly>
									    
									     <input type="hidden" class="form-control" name="Requ_Date_Temp1" id="Requ_Date_Temp1"  >
									  </div>
									</div>
									<div class="form-group">
									  <label class="col-md-4 control-label">To-Mail</label>
									   <div class="col-sm-8">
									     <input type="email" class="form-control addresspicker" name="toemail1"  id="tags2" value="" placeholder="someone@example.com" readOnly>
									  </div>
									</div>
									
									<div class="form-group" style='display:none;'>
									  <label class="col-md-4 control-label">CC-Mail</label>
									   <div class="col-sm-8">
									     <input type="email" class="form-control addresspicker" name="ccemail1"  id="tags3" value="" placeholder="someone@example.com" >
									  </div>
									</div>
									 <div class="form-group"> 
									  <!-- <label class="col-md-4 control-label">Request Type</label> -->
									   <!-- <div class="col-sm-8">
									     <input type="text" class="form-control" name="Subject" id="Subject" value="Re: Incomplete Hours" >
									  </div> -->
									 
									
							<table width='100%' class="TFtable">
							
							<tbody>
							<tr>
							<th align='center'>##</th>
							 <th align='center' >In Time</th>
							 <th align='center' >Out Time</th>
							 <th align='center' >Request Type</th>
							 <th align='center' >Reason/Request Message</th>
							 <th align='center' >Status</th>
							</tr>
							<tbody>
					<!-- 
				Doj.put("employeeid" , Res.getString("employeeid"));
				Doj.put("transactiondate" , Res.getString("transactiondate"));
				Doj.put("transactiontime_in" , Res.getString("transactiontime_in"));
				Doj.put("transactiontime_out" , Res.getString("transactiontime_out"));
				Doj.put("nettime" , Res.getString("nettime"));
				Doj.put("CKID" , Res.getString("employeeid")+"#"+Res.getString("transactiondate")+"#"+Res.getString("transactiontime_in")+"#"+Res.getString("transactiontime_out"));
				if(Res.getString("status").equalsIgnoreCase("1001")){
					Doj.put("CheckBox" ,"true");
					Doj.put("Note" ,"No request");
				}else{
					Doj.put("CheckBox" ,"false");
					Doj.put("CheckBox" ,"Applied");
				}
				Doj.put("CheckBoxStatus" , Res.getString("status"));
					 -->
							
				
							
							<tr ng-repeat="m in Data_H" >
							
							<td align='center' ><input type='checkbox' value='{{ m.CKID }}' name="HCheckBox" ng-click="AddParams(m);" ng-disabled="{{ m.CheckBox_TY }}"  ></td>
							
							 <td align='center' >{{ m.transactiontime_in  }}</td>
							 <td align='center' >{{ m.transactiontime_out  }}</td>
							 <td align='center' >
							 <select  class="Selects" name="Subject1" id="{{ m.CKID }}_SUB"  disabled>
							   <option value="Personal" ng-selected='{{ m.Personal}}' >Personal</option>
							   <option value="Official" ng-selected='{{ m.Official}}' >Official</option>
							 <select>
							 </td>
							 <td align='center' >
							 
											<textarea class="form-control" rows="1" ID="{{ m.CKID }}_message"  placeholder="{{m.MESSAGE_USER}}" name="message1" disabled></textarea>
							 
							 </td>
							 
							 <td align='center'> {{ m.Note  }} </td>
							 
							</tr>
							
					<!--  
							<tr>
							<td align='center' ><input type='checkbox' value='12345' id='checkH'></td>
							 <td align='center' >12:00</td>
							 <td align='center' >14:00</td>
							 <td align='center' >
							 <select  class="Selects" name="Subject1" id="Subject1">
							   <option value="Personal" selected >Personal</option>
							   <option value="Official">Official</option>
							 <select>
							 </td>
							 <td align='center' >
							 
							
											<textarea class="form-control" rows="1" id="message1" name="message1"></textarea>
										
										
							 
							 </td>
							 
							 <td align='center'> processed </td>
							 
							</tr>
							-->
							
							
							
							
							</table>
							
							<script type='text/javascript'>
							
							function MyData1(){
								
								$("#Responce_Message1").html(" ");
									var myObjmain=[];
								  var Obj=document.getElementsByName("HCheckBox");
								   //alert(Obj.length);
								   var count=0;
								  for(var i=0;i<Obj.length;i++){
									if(Obj[i].checked){
										var keyID=Obj[i].value;
										var keySUBID=Obj[i].value+"_SUB";
										var keySUBID1=Obj[i].value+"_message";
										var v_Sel_sub=document.getElementById(keySUBID).value;
										var v_message_Sub=document.getElementById(keySUBID1).value;
										var myObj;
										myObj={
										  "KEYID":""+keyID+"",
										   "REQ_TYPE":v_Sel_sub,
										   "REQ_MESSAGE":v_message_Sub,
										  };
										myObjmain.push(myObj);
										count++;
								}
								  }  // loop for data catching end ;
								  if(count==0){
									   $("#Responce_Message1").html("Invalid Request Please select Check box..!");
									   return false;
								   }else{
								  
								      ///alert(myObjmain);
								 //** Request ajex as jason object Data
								 
								 
								 
								      
								 //** Request ajex as jason object Data      
								      
								      
								   }
}
							</script>
									 
								<!--  	  <div class="col-sm-8">
									     <!-- <input type="text" class="form-control" name="Subject" id="Subject" value="Re: Incomplete Hours" > -->
									   <!--     <select name="Subject" id="Subject" class="form-control">
									      <option value="Incomplete">Incomplete Hours</option>
									      <option value="Mechineproblem">Mechine Not Working</option>
									      <option value="Swipemiss">Forgot Swipe</option>
									      <option value="ODwork">On Duty</option>
									      <option value="HODPermisssion">HOD Permission</option>
									      </select>
									  </div> -->
									</div>
									<!-- <div class="form-group">
										<label class="col-md-4 control-label">Request Message</label>
										<div class="col-md-8">
											<textarea class="form-control" rows="5" id="message" name="message"></textarea>
										</div>
									</div> -->
							</div>
							
							
							<div class="modal-footer">
							<span id="Responce_Message1" style="color:red;" class="align-left"> </span>
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>&nbsp;
							<!-- <input type="button" id="Responce_Message_btn1" style="display:" class="btn btn-primary" onclick="AttendanceRequest()" value="Send Request"/> -->
							
							<input type="button" id="Responce_Message_btn1" style="display:none" class="btn btn-primary" ng-click="MyData()" tabindex='-1' value="Send Request"/>
							
							</div>
							
						  </div>
						  	  </div>
						  	  
						  	  						     
						
						
						  	  </div>
						  	  </div>
						  	  </div>
		<!--  NEW Model Box For HourS Permission END -->
	
<!--  New Model Box View -->
	
<div class="modal fade" id="myModal_view" style='width:100%' tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="vertical-alignment-helper">
					  <div class="modal-dialog vertical-align-center" role="document">
						<div class="modal-content">
						  <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							  <span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Attendance Hours Request Status</h4>
							<!-- Please Submit in between Payroll i.e., 26-previous Month-Current Year ------   27-Current Month-Current Year -->
						  </div>
							<div class="modal-body" style='width:100%'>
								<form action="ictrequestmail_Hours" method="post" >
									
									<!-- <div class="form-group">
									<label class="col-md-4 control-label">To</label>
									  <div class="col-sm-8">
									    <input type="text" class="form-control" name="to" id="to">
									  </div>
									</div>
									<div class="form-group">
									  <label class="col-md-4 control-label">CC</label>
									   <div class="col-sm-8">
									    <input type="text" class="form-control" name="cc" id="cc">
									  </div>
									</div> -->
									
									<div class="form-group">
									  <label class="col-md-4 control-label"><b>Request Date</b></label>
									  <div class="col-sm-8">
									    <b><span id="Requ_Date3"> </span></b>
									    
									    <input type="hidden" class="form-control" name="date2" id="date2"  readOnly>
									    
									  <!--    <input type="hidden" class="form-control" name="Requ_Date_Temp1" id="Requ_Date_Temp1"  > -->
									  </div> 
									</div>
									<div class="form-group">
									  <!-- <label class="col-md-4 control-label">To-Mail</label>
									   <div class="col-sm-8">
									     <input type="email" class="form-control addresspicker" name="toemail1"  id="tags2" value="" placeholder="someone@example.com" >
									  </div> -->
									</div>
									<div class="form-group">
									<!--   <label class="col-md-4 control-label">CC-Mail</label>
									   <div class="col-sm-8">
									     <input type="email" class="form-control addresspicker" name="ccemail1"  id="tags3" value="" placeholder="someone@example.com" >
									  </div> -->
									</div>
									 <div class="form-group"> 
									  <!-- <label class="col-md-4 control-label">Request Type</label> -->
									   <!-- <div class="col-sm-8">
									     <input type="text" class="form-control" name="Subject" id="Subject" value="Re: Incomplete Hours" >
									  </div> -->
									 
									
							<table width='100%' class="TFtable">
							
							<tbody>
							<tr>
							<th align='center'>##</th>
							 <th align='center' >In Time</th>
							 <th align='center' >Out Time</th>
							 <th align='center' >Request Type</th>
							 <th align='center' >Reason/Request Message</th>
							 <th align='center' >Status</th>
							</tr>
							</tbody>
					<!-- 
				Doj.put("employeeid" , Res.getString("employeeid"));
				Doj.put("transactiondate" , Res.getString("transactiondate"));
				Doj.put("transactiontime_in" , Res.getString("transactiontime_in"));
				Doj.put("transactiontime_out" , Res.getString("transactiontime_out"));
				Doj.put("nettime" , Res.getString("nettime"));
				Doj.put("CKID" , Res.getString("employeeid")+"#"+Res.getString("transactiondate")+"#"+Res.getString("transactiontime_in")+"#"+Res.getString("transactiontime_out"));
				if(Res.getString("status").equalsIgnoreCase("1001")){
					Doj.put("CheckBox" ,"true");
					Doj.put("Note" ,"No request");
				}else{
					Doj.put("CheckBox" ,"false");
					Doj.put("CheckBox" ,"Applied");
				}
				Doj.put("CheckBoxStatus" , Res.getString("status"));
					 -->
							
							
							<tr ng-repeat="x in Data_HH" >
							
							<td align='center' ><input type='checkbox' value='12345' ID="{{ x.CKID }}" disabled ></td>
							
							 <td align='center' >{{ x.transactiontime_in  }}</td>
							 <td align='center' >{{ x.transactiontime_out  }}</td>
							 <td align='center' >
							 <select  class="Selects" name="Subject1" ID="{{ x.CKID }}" disabled >
							     <option value="Personal" ng-selected='{{ x.Personal}}' >Personal</option>
							   <option value="Official" ng-selected='{{ x.Official}}' >Official</option>
							 <select>
							 </td>
							 <td align='center' >
							 
							
											<textarea class="form-control" rows="1" ID="{{ x.CKID }}_message" placeholder="{{x.MESSAGE_USER}}" name="message1" disabled></textarea>
										
										
							 
							 </td>
							 
							 <td align='center'> {{ x.Note  }} </td>
							 
							</tr>
							
				          </table>
							
							<div class="modal-footer">
							<span id="Responce_Message1" style="color:red;" class="align-left"> </span>
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>&nbsp;
							<!-- <input type="button" id="Responce_Message_btn1" style="display:" class="btn btn-primary" onclick="AttendanceRequest()" value="Send Request"/>
						 -->	
						 </div> 
							
						  </div>
						  	  </div>
						  	  
						  	  </div>
						  	  </div>
						  	  </div>
	
<!-- New Model Box view endind -->	
	
		<!-- Model Box Ending -->
		<!-- Vendor -->
		
		<script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
		<script src="assets/vendor/bootstrap/js/bootstrap.js"></script>
		<script src="assets/vendor/nanoscroller/nanoscroller.js"></script>
		<!-- <script src="assets/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
		 -->
		
		<!-- Specific Page Vendor -->
		<script src="assets/vendor/jquery-ui/js/jquery-ui-1.10.4.custom.js"></script>
		<script src="assets/vendor/jquery-ui-touch-punch/jquery.ui.touch-punch.js"></script>
		<script src="assets/vendor/jquery-appear/jquery.appear.js"></script>
		
		
		<!-- Theme Base, Components and Settings -->
		<script src="assets/javascripts/theme.js"></script>
		
		<!-- Theme Custom -->
		<script src="assets/javascripts/theme.custom.js"></script>
		
		<!-- Theme Initialization Files -->
		<script src="assets/javascripts/theme.init.js"></script>


		<!-- Examples -->
		<script src="assets/javascripts/dashboard/examples.dashboard.js"></script>
		
		<!-- <script>
$(document).ready(function(){
    $("#myBtn").click(function(){
        $("#myModal").modal();
    });
});
</script> -->

<style type="text/css">
	.TFtable{
		width:100%; 
		border-collapse:collapse; 
	}
	.TFtable td{ 
		padding:2px; border:#4e95f4 1px solid;
	}
	/* provide some minimal visual accomodation for IE8 and below */
	.TFtable tr{
		background: #b8d1f3;
	}
	/*  Define the background color for all the ODD background rows  */
	.TFtable tr:nth-child(odd){ 
		background: #b8d1f3;
	}
	/*  Define the background color for all the EVEN background rows  */
	.TFtable tr:nth-child(even){
		background: #dae5f4;
	}
</style>

	</body>
</html>