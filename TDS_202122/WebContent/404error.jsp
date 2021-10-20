<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

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
	<script type="text/javascript">	window.history.forward();
		function noBack() { window.history.forward(); }
	
		function callmain(){
			document.getElementById("myForm").submit();
		}
		
  </script>
  
</head>
<body onload="noBack();callmain();" onpageshow="if (event.persisted) noBack();" >
 <form id='myForm' action='login.html' method='GET'>
 <!--  Unable to give The Service Please Try Again..! -->
  Please wait ..!
 </form>

</body>
</html>