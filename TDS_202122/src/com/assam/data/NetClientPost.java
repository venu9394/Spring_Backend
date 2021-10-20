package com.assam.data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientPost {

	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args){
		
		String Empid="NA";
		String Date="NA";
		String ServiceMode="hr";
		
		String Data=NetClientPost.CallService(Empid,Date,ServiceMode);
		
		//Empid, Date, ServiceMode
		
		System.out.println(Data+"DataData");
	}
	public synchronized static String  CallService(String Empid,String Date,String ServiceMode){
		String output = null;
		String outputReturn="";
	  try {

		URL url = new URL("http://services.heterohcl.com/hhcltemplog/api/getEmployeeList");
		
		//http://services.heterohcl.com/assamtemplog/api/getEmployeeList
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		
		conn.setUseCaches (false);
		conn.setDefaultUseCaches (false);
		conn.setRequestProperty("Accept","application/json");

		//conn.setRequestProperty("Content-Type", "application/jason");
	     
		//conn.setRequestProperty("Content-Type", "text");

		//String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
         //DATE=2020-04-19
		
        String data="";
		data +="Date="+Date+"";
		data +="&Empid="+Empid+"";
		data +="&ServiceMode="+ServiceMode;
		
		OutputStream os = conn.getOutputStream();
		os.write(data.getBytes());
		os.flush();
        
		System.out.println("conn.getResponseCode()::" +conn.getResponseCode());
		//HttpURLConnection.HTTP_CREATED
		
		/*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}*/
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			outputReturn +=output;
		}
		conn.disconnect();

	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	 }
	return outputReturn;
	}

}
