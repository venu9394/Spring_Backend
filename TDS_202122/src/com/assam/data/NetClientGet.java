package com.assam.data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;

public class NetClientGet {

	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {

		 System.out.println("HAI iAM IN LOGIN PAGE DATA ");
	  try {

		//URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
		
		
		//URL url = new URL("http://localhost:8080/officesisto/iconapp/zmlogin");
		
		//assam_datacapture
		/*@FormParam("Userid") int Userid,
		@FormParam("Password") String Pwd,
		@FormParam("ServiceMode") String ServiceMode*/
		
		/*String data = "";
		
		data += "Userid=20314";
		data += "&Password=NA";
		data +="&ServiceMode=cusfdbk";
		
		Map parameters = new HashMap();
		parameters.put("Userid", "20314");
		parameters.put("Password", "NA");
		parameters.put("ServiceMode", "cusfdbk");*/
		
		
		
		
		/*data += "Userid="+URLEncoder.encode("20314" ,"UTF-8");
		data += "&Password="+URLEncoder.encode("NA" ,"UTF-8");
		data +="&ServiceMode="+URLEncoder.encode("cusfdbk","UTF-8");*/
		
		
		URL url = new URL("http://staging.heterohcl.com/development/azistareports/api/getPOB");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type", "application/json");
		/*conn.setRequestProperty("Userid", "20314");
		conn.setRequestProperty("Password", "NA");
		conn.setRequestProperty("ServiceMode", "cusfdbk");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.addRequestProperty("Userid", "20314");*/
		//setRequestProperty(
				
		//conn.setRequestProperty("Userid","20314");
		//conn.setRequestProperty("Password","NA");
		//conn.setRequestProperty("ServiceMode","cusfdbk");
		conn.setUseCaches(true);
		conn.connect();
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuffer buffer = new StringBuffer();
		while((line = rd.readLine()) != null){
			buffer.append(line).append("\n");
		}
	   System.out.println(buffer.toString());
		rd.close();
		conn.disconnect();
		/*HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		//conn.setRequestProperty("Accept", "application/json");
		//conn.set
*/
		/*if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}*/

		/*BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}*/

		rd.close();
		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

	}

}