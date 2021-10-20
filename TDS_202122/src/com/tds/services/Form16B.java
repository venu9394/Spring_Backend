package com.tds.services;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.text.DecimalFormat;
import java.text.NumberFormat;

//import org.apache.commons.io.FileUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

public class Form16B
{
	// Connection connection;

	public static void generateReport(String proposedMonths, Map Basics, Map TaxException, ArrayList taxempids, ArrayList empids, Map FinalComponents, Map TaxSections,List Components,Map Components_map,String yeardata,Map Tax_dec_Map,Connection connection,String Date_Path)
	{
		     String name=null;
		
		     PreparedStatement ps =null;
		     
		     StringBuffer upload=new  StringBuffer();
		     
		     GetDbData DataObj=new GetDbData();
		   //  NumberFormat formatter = new DecimalFormat("#0.00");
		     
		     SimpleDateFormat ft=new SimpleDateFormat ("dd.MM.yyyy");
		     
				upload.append(" insert into tds_forms.tds_fy_2018_2019 ");
				upload.append(" (EMPID, EMP_PAN, EMPPF, ");
				upload.append("  MODE, F_331, F_331_1, ");
				upload.append("  F_332, F_332_1, F_333, ");
				upload.append("  F_334, F_334_1, F_335, ");
				upload.append("  F_336, F_337, F_338, ");
				upload.append("  F_339, F_339_1, F_340, ");
				upload.append("  F_341, F_342, F_343, ");
				upload.append("  F_344, F_346, F_347, ");
				upload.append("  F_348, F_349, F_350, ");
				upload.append("  F_351, F_352, F_354, ");
				upload.append("  F_355, F_355_1,F_356, ");
				upload.append("  F_357,F_358, F_359, ");
				upload.append("  F_360, F_361, F_362, ");
				upload.append("  F_363, F_364, F_365, ");
				upload.append("  F_366, F_367, F_368, ");
				upload.append("  F_369, F_370, F_371, ");
				upload.append("  F_372, F_373, F_374, ");
				upload.append("  F_375, F_376, F_377, ");
				upload.append("  F_377_1, F_345, F_345_1, ");
				upload.append("  F_345_2, F_345_3, F_345_4, ");
				upload.append("  F_345_5, F_345_6, F_345_7, ");
				upload.append("  F_345_8, F_353, F_353_1, ");
				upload.append("  F_353_2, F_353_3, F_353_4, ");
				upload.append("  F_353_5, F_353_6, F_353_7, ");
				upload.append("  F_353_8, F_1, F_2,  ");
				upload.append("  F_3, F_4,F_5,  ");
				upload.append("  F_6, F_7, F_8,  ");
				upload.append("  F_9, F_10,F_11, ");
				upload.append("  F_12 ");
				upload.append("  ) ");
				upload.append(" values( ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
				upload.append(" ?,?,?, ");
			//	upload.append(" ?,?,?, ");
				upload.append(" ? ");
				upload.append(" ) ");
	
			String FilePath="C:/TDS_FORECAST";
			//String Sub_FilePath="C:/TDS_FORECAST/";
			
			String Sub_FilePath="C:/TDS_FORECAST/"+Date_Path+"/16B/";
			
			//Date_Path
			NumberFormat formatter = new DecimalFormat("#0.00");
			
			Map hm=new HashMap();
			//Sub_FilePath="//10.30.0.24/f/java/TDS_ forcast/";
			
		//	Sub_FilePath=Sub_FilePath.concat(Basics.get("BuName").toString());
			
			Sub_FilePath=Sub_FilePath.concat(Basics.get("BuName").toString());
            
			Sub_FilePath=Sub_FilePath.concat("_16B");
			
			File file = new File(FilePath);
			if (!file.exists()) {
				if (file.mkdir()) {
					//System.out.println("Directory is created!");
				} else {
					//System.out.println("Failed to create directory!");
				}
			}

			File files = new File(Sub_FilePath);
			if (!files.exists()) {
				if (files.mkdirs()) {
					//System.out.println("Multiple directories are created!");
				} else {
					//System.out.println("Failed to create multiple directories!");
				}
			}


			//System.out.println("step 1");

			Date dNow = new Date();
			

String sql = upload.toString();
try {
	   ps =  connection.prepareStatement(sql);
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
			try
		  {
				java.util.Iterator empseq = taxempids.iterator();

	
	
	while(empseq.hasNext()){
		      
			    hm=new HashMap();
			    String empid= empseq.next().toString(); 
			  //  System.out.println("JRXML" +empid +"~~"+Tax_dec_Map.get(empid+"$DE_80CCD").toString());
			    hm.put("EMPID", empid);
			    
			    hm.put("EMP_NAME", Basics.get(empid+".callname").toString().trim());
			    
			    String CompanyTitle=Basics.get(empid+"_EMPCOMPANYTITLE_V").toString();
			    
			    hm.put("BU_NAME", ""+CompanyTitle+"");
			    
			   // hm.put("BU_NAME", "HETERO HEALTHCARE LTD");
			    
			   // hm.put("BU_NAME", Basics.get("BuName").toString());
			    //**************************************************************
			    hm.put("_1_a",  Double.parseDouble(FinalComponents.get(empid+"-ANN_gross").toString())); //gross
			    hm.put("_1_b",  0.00); // Medical reembresment
			    hm.put("_1_c",  0.00); // 
			    hm.put("_1_d",  Double.parseDouble(FinalComponents.get(empid+"-ANN_gross").toString())); //total gross
			    
			//    System.out.println("HIAAAAAAAAAAAAAAAAA");
			    //CTC Exemptions
			    hm.put("_2_a",  Double.parseDouble(TaxException.get(empid+".Basi40_RE_y").toString()));
			    hm.put("_2_b",  Double.parseDouble(TaxException.get(empid+"_CA_M_y").toString()));
			    hm.put("_2_c",  Double.parseDouble(TaxException.get(empid+".Medical_y").toString()));
			    hm.put("_2_d",  Double.parseDouble(TaxSections.get(empid+"_LTA_P").toString()));
			    hm.put("_2_e",  Double.parseDouble(TaxException.get(empid+".ChildEdu_y").toString()));  // Child Education
			    hm.put("_2_f",  Double.parseDouble(TaxException.get(empid+".ANN_ExmpAmount").toString()));  // Total Gross of 2a-2d
			    hm.put("_3",    Double.parseDouble(FinalComponents.get(empid+"-ANN_gross_ExAmt").toString()));  //After Deduction of CA,child,etc
			    hm.put("_4_a",  0.00);  // Entertinement Allow
			    hm.put("_4_b",   Double.parseDouble(FinalComponents.get(empid+"-ANN_Pt").toString()));  // PT 
			    hm.put("_5",   Double.parseDouble(FinalComponents.get(empid+"-ANN_Pt").toString()));    //Aggrate AMt
			    hm.put("_6",   Double.parseDouble(TaxException.get(empid+".ANN_FinalIncome_B").toString()));    /// Final income after pt deduct
			    // Other Income Details
			    
			    hm.put("_7_a",   Double.parseDouble(TaxSections.get(empid+"INHR").toString())); // House property
			    
			    hm.put("_7_b",   Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString())); // income from other source
			    
			    hm.put("_7_c.1",   Double.parseDouble(TaxSections.get(empid+"_RENTINTEREST_E").toString())); //Less Deduction Housing Loan Intrest
             // need to add in jasper
 
			    Double TOTAL_let_iNTREST=(Double.parseDouble(TaxSections.get(empid+"INHR").toString())+Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString()))-Double.parseDouble(TaxSections.get(empid+"_RENTINTEREST_E").toString());
			    
			    hm.put("_7_c",   TOTAL_let_iNTREST); //Less Deduction Housing Loan Intrest total of above
			    
			    //7_d ===_7_c 7_d not Used
			  //  hm.put("_7_d",  Double.parseDouble(TaxException.get(empid+".CUmm_LEXAMT").toString())); // income total A+B-c
			    
			    hm.put("_8",   Double.parseDouble(TaxException.get(empid+".ANN_LEXAMT").toString())); //ncome total 6+7
			    
			    hm.put("_9_a_1",  Double.parseDouble(FinalComponents.get(empid+"_ANN-PF").toString()));  //80C section  PF
			    
			    hm.put("_9_a_4",  Double.parseDouble(TaxSections.get(empid+"_80C_E_y").toString()));  //80C section eligible
			    
			    hm.put("_9_1_4.1",  Double.parseDouble(TaxSections.get(empid+"_80C_E_y_Temp").toString()));  //80C section main total
			    
			    hm.put("_9_1_4.2",  Double.parseDouble(TaxSections.get(empid+"_80C_E_y").toString()));  //80C section deductible
			    
			    
			   // hm.put("_9_b_4",  TaxSections.get(empid+"80C").toString());
			    hm.put("_10",  Double.parseDouble(TaxSections.get(empid+"_80D_E").toString()));
			    
			   // Double Total_Deductions_28052019=Double.parseDouble(TaxSections.get(empid+"_80C_E_y").toString())+Double.parseDouble(TaxSections.get(empid+"_80D_E").toString());
			    
			    
			    Double Total_Deductions_28052019  =Double.parseDouble(TaxSections.get(empid+"_80C_E_y").toString())+Double.parseDouble(TaxSections.get(empid+"S_80G_E").toString())+Double.parseDouble(TaxSections.get(empid+"_80D_E").toString());
			    
			    hm.put("_10_t",  Double.parseDouble(Tax_dec_Map.get(empid+"$ToTAL_80D").toString()));  // 80D over all Sum
			    
			    hm.put("_11",  Double.parseDouble(TaxSections.get(empid+"ANN_TxamT").toString()));
			    hm.put("_12",  Double.parseDouble(TaxSections.get(empid+"_ANN_Emp_Tax_E").toString()));
			    hm.put("_13", Double.parseDouble(TaxSections.get(empid+"ANN_Educess").toString()));
			    hm.put("_14", Double.parseDouble(TaxSections.get(empid+"ANN_tx_Paid").toString()));
			    hm.put("_15", Double.parseDouble(TaxSections.get(empid+"_ANN_Before_Dedu_Relif").toString())); 
			    hm.put("_16", Double.parseDouble(TaxSections.get(empid+"ANN_tx_Paid").toString())); 
			    
			    
			    double _$$87A=Double.parseDouble(TaxSections.get(empid+"_ANN_EmpTaxAddl_E").toString()) ;
			    
			    double _ANN_SURCHARGE=Double.parseDouble(TaxSections.get(empid+"_ANN_SURCHARGE").toString()) ;
			    //***************************************************************
			    // ADD on 19-03-2018
			    double _80C$=Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80C").toString()) +Double.parseDouble(FinalComponents.get(empid+"_ANN-PF").toString());
			    
			    hm.put("DE_80C" , _80C$);
			    
			  //  hm.put("DE_80C" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80C").toString()));
			    
			    
			    double Column_356=Double.parseDouble(TaxSections.get(empid+"_80C_E_y_Temp").toString())-(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80CCC").toString())+Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80CCD").toString()));
			    
			    hm.put("DE_80CCC" ,Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80CCC").toString()));
			    hm.put("DE_80CCD" ,Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80CCD").toString()));
			    
			    hm.put("DE_80D" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80D").toString()));
			    hm.put("DE_S80DD" ,Double.parseDouble( Tax_dec_Map.get(empid+"$DE_S80DD").toString()));
			    hm.put("DE_S80DD2" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80DD2").toString()));
			    hm.put("DE_S80DDB" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80DDB").toString()));
			    hm.put("DE_S80DDB2" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80DDB2").toString()));
			    hm.put("DE_S80U" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80U").toString()));
			    hm.put("DE_S80U2" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80U2").toString()));
			    hm.put("DE_S80E" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80E").toString()));
			    hm.put("DE_S80CCG" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80CCG").toString()));
			    hm.put("DE_S80CCG2" ,Double.parseDouble( Tax_dec_Map.get(empid+"$DE_S80CCG2").toString()));
			    hm.put("DE_S80TTA" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA").toString()));
			    hm.put("DE_S80TTA1" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA1").toString()));
			    hm.put("DE_S80G" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80G").toString()));
			    hm.put("DE_LETOUT" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_LETOUT").toString()));
			    hm.put("DE_OTHERINCOME" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_OTHERINCOME").toString()));
			    
			    hm.put("emp_father" , " .............................................  ");
			    hm.put("emp_desc" , Basics.get(empid+"_EMP_PROF_DESIG").toString());
			    
			    if(TaxSections.get(empid+"LOCATION").toString().trim().equalsIgnoreCase("HYD")){
			    	hm.put("emp_loc" , "HYDERABAD");
			    }else if(TaxSections.get(empid+"LOCATION").toString().trim().equalsIgnoreCase("MUM")){
			    	
			    	hm.put("emp_loc" , "MUMBAI");
			    	
			    }else{
			    	
			    	hm.put("emp_loc" , " ");
			    }
			    
			    
			    hm.put("emp_date" , ft.format(dNow));
			
			    
/*
 1	 EMPID	 ps.setString(1 ,empid);	
2	 EMP_PAN	    ps.setString(2 ,empid);	
3	 EMPPF	    ps.setString(3 ,empid);	
4	 MODE	    ps.setString(4 ,empid);	
5	 F_331	    ps.setString(5 ,empid);	
6	 F_331_1	    ps.setString(6 ,empid);	
7	 F_332	    ps.setString(7 ,empid);	
8	 F_332_1	    ps.setString(8 ,empid);	
9	 F_333	    ps.setString(9 ,empid);	
10	 F_334	    ps.setString(10,empid);	
11	 F_334_1	    ps.setString(11,empid);	
12	 F_335	    ps.setString(12,empid);	
13	 F_336	    ps.setString(13,empid);	
14	 F_337	    ps.setString(14,empid);	
15	 F_338	    ps.setString(15,empid);	
16	 F_339	    ps.setString(16,empid);	
17	 F_339_1	    ps.setString(17,empid);	
18	 F_340	    ps.setString(18,empid);	
19	 F_341	    ps.setString(19,empid);	
20	 F_342	    ps.setString(20,empid);	
21	 F_343	    ps.setString(21,empid);	
22	 F_344	    ps.setString(22,empid);	
23	 F_346	    ps.setString(23,empid);	
24	 F_347	    ps.setString(24,empid);	
25	 F_348	    ps.setString(25,empid);	
26	 F_349	    ps.setString(26,empid);	
27	 F_350	    ps.setString(27,empid);	
28	 F_351	    ps.setString(28,empid);	
29	 F_352	    ps.setString(29,empid);	
30	 F_354	    ps.setString(30,empid);	
31	 F_355	    ps.setString(31,empid);	
32	 F_355_1	    ps.setString(32,empid);	
33	 F_356	    ps.setString(33,empid);	
34	 F_357	    ps.setString(34,empid);	
35	 F_358	    ps.setString(35,empid);	
36	 F_359	    ps.setString(36,empid);	
37	 F_360	    ps.setString(37,empid);	
38	 F_361	    ps.setString(38,empid);	
39	 F_362	    ps.setString(39,empid);	
40	 F_363	    ps.setString(40,empid);	
41	 F_364	    ps.setString(41,empid);	
42	 F_365	    ps.setString(42,empid);	
43	 F_366	    ps.setString(43,empid);	
44	 F_367	    ps.setString(44,empid);	
45	 F_368	    ps.setString(45,empid);	
46	 F_369	    ps.setString(46,empid);	
47	 F_370	    ps.setString(47,empid);	
48	 F_371	    ps.setString(48,empid);	
49	 F_372	    ps.setString(49,empid);	
50	 F_373	    ps.setString(50,empid);	
51	 F_374	    ps.setString(51,empid);	
52	 F_375	    ps.setString(52,empid);	
53	 F_376	    ps.setString(53,empid);	
54	 F_377	    ps.setString(54,empid);	
55	 F_377_1	    ps.setString(55,empid);	
56	 F_345	    ps.setString(56,empid);	
57	 F_345_1	    ps.setString(57,empid);	
58	 F_345_2	    ps.setString(58,empid);	
59	 F_345_3	    ps.setString(59,empid);	
60	 F_345_4	    ps.setString(60,empid);	
61	 F_345_5	    ps.setString(61,empid);	
62	 F_345_6	    ps.setString(62,empid);	
63	 F_345_7	    ps.setString(63,empid);	
64	 F_345_8	    ps.setString(64,empid);	
65	 F_353	    ps.setString(65,empid);	
66	 F_353_1	    ps.setString(66,empid);	
67	 F_353_2	    ps.setString(67,empid);	
68	 F_353_3	    ps.setString(68,empid);	
69	 F_353_4	    ps.setString(69,empid);	
70	 F_353_5	    ps.setString(70,empid);	
71	 F_353_6	    ps.setString(71,empid);	
72	 F_353_7	    ps.setString(72,empid);	
73	 F_353_8	    ps.setString(73,empid);	
74	 F_1	    ps.setString(74,empid);	
75	 F_2	    ps.setString(75,empid);	
76	 F_3	    ps.setString(76,empid);	
77	 F_4	    ps.setString(77,empid);	
78	 F_5	    ps.setString(78,empid);	
79	 F_6	    ps.setString(79,empid);	
80	 F_7	    ps.setString(80,empid);	
81	 F_8	    ps.setString(81,empid);	
82	 F_9	    ps.setString(82,empid);	
83	 F_10	    ps.setString(83,empid);	
84	 F_11	    ps.setString(84,empid);	
85	 F_12	    ps.setString(85,empid);	
86	 S_NO	    ps.setString(86,empid);	
          
          
*/
		String LANLARD_PAN="";
		StringBuffer String_Pan=new StringBuffer();
		
		String_Pan.append(" SELECT PAN FROM test.tbl_it_emp_renthouse where refno in(select refno ");
		String_Pan.append(" from test.tbl_emp_it_tds where employeeid="+empid+" and FY='2018-2019' and ");
		String_Pan.append(" status in (1001,1005)) and length(PAN)=10 ");
		
		 ResultSet rs = null;
			    rs=null;
				try {
					rs=(ResultSet)DataObj.getBasic(String_Pan.toString(), "buunit", rs ,connection);
					if(rs.next()){
						
						LANLARD_PAN=rs.getString(1);	
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}  
			    
			    
		//************************************	    
			    ps.setString(1	,   empid);  //EMPID
			    ps.setString(2	," "+Basics.get(empid+"_EMP_PROF_PAN") +" " ); //EMP_PAN
			    ps.setString(3	," 	"); //EMPPF
			    ps.setString(4	," MODE	   " ); //MODE
			    ps.setString(5	," 	");  //F_331
			    ps.setString(6	,"  " ); //F_331_1
			    ps.setString(7	," "+Basics.get(empid+".callname").toString().trim()+"");  //F_332
			    ps.setString(8	," "+Basics.get(empid+"_EMP_PROF_DESIG")+" " ); //F_332_1
			    ps.setString(9	," "+Basics.get(empid+"_EMP_PROF_GENDER")+"	");  //F_333
			    ps.setString(10, ""+Basics.get(empid+"_TAX_CAL_FRM")+"	   " ); //F_334
			    ps.setString(11, ""+Basics.get("_TAX_CAL_TO")+"  " ); //F_334_1
			    ps.setString(12,   ""+Double.parseDouble(FinalComponents.get(empid+"-ANN_gross").toString())+""  ); //F_335
			    ps.setString(13, "0" ); //F_336
			    ps.setString(14, "0" ); //F_337
			    ps.setString(15, ""+Double.parseDouble(FinalComponents.get(empid+"-ANN_gross").toString())+""); //F_338
			    ps.setString(16, "0" ); //F_339
			    ps.setString(17, ""+Double.parseDouble(FinalComponents.get(empid+"-ANN_gross").toString())+""); //F_339_1
			    ps.setString(18, ""+Double.parseDouble(TaxSections.get(empid+"_LTA_P").toString())+"" ); //F_340
			    ps.setString(19, "0" ); //F_341
			    ps.setString(20, "0" ); //F_342
			    //Double.parseDouble(TaxSections.get(empid+"_LTA_P").toString())
			 //   ps.setString(21, ""+Basics.get(empid+"._60_EL").toString().trim()+"" ); // F_343
			    ps.setString(21, "0" ); // F_343
			    ps.setString(22, ""+formatter.format(Double.parseDouble(TaxException.get(empid+".Basi40_RE_y").toString()))+"" ); //F_344 house rent
			    ps.setString(23, "" +formatter.format(Double.parseDouble(TaxException.get(empid+".ChildEdu_y").toString()))+"" ); //F_346->Childrean Education
			    
			    ps.setString(24, ""+formatter.format(Double.parseDouble(TaxException.get(empid+".ANN_ExmpAmount").toString()))+"" ); //F_347
			    
			    ps.setString(25, ""+formatter.format(Double.parseDouble(TaxException.get(empid+"_CA_M_y").toString()))+"" ); //F_348
			    ps.setString(26, ""+formatter.format(Double.parseDouble(FinalComponents.get(empid+"-ANN_Pt").toString()))+"" ); //F_349
			    ps.setString(27, "0" ); //F_350
			    ps.setString(28, ""+ formatter.format(Double.parseDouble(TaxException.get(empid+".ANN_FinalIncome_B").toString()))+"" ); //F_351
			    ps.setString(29, ""+TOTAL_let_iNTREST+"		   " ); //F_352 housing lloan
			    ps.setString(30, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"_OTHERINCOME").toString()))+"	   " ); //F_354 --> Other Income
			    ps.setString(31, "  "+formatter.format(Double.parseDouble(TaxException.get(empid+".ANN_LEXAMT").toString()))+" " ); //F_355  
			    ps.setString(32, " "+formatter.format(Double.parseDouble(TaxException.get(empid+".ANN_LEXAMT").toString()))+" " ); //F_355_1
			  
			  //  ps.setString(33, " "+_80C$+"	   " ); //F_356  --80C 
			    ps.setString(33, " "+formatter.format(Column_356)+"	   " ); //F_356  --80C 
			    
			    
			    ps.setString(34, ""+formatter.format(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80CCC").toString()))+"	   " ); //F_357
			    ps.setString(35, ""+formatter.format(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80CCD").toString()))+"	   " ); //F_358
			    ps.setString(36, "0" ); //F_359
			    ps.setString(37, "0" ); //F_360
			    
			  /*  hm.put("DE_80D" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80D").toString()));
			    hm.put("DE_S80DD" ,Double.parseDouble( Tax_dec_Map.get(empid+"$DE_S80DD").toString()));
			    hm.put("DE_S80DD2" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80DD2").toString()));
			    hm.put("DE_S80DDB" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80DDB").toString()));
			    hm.put("DE_S80DDB2" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80DDB2").toString()));
			    hm.put("DE_S80U" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80U").toString()));
			    hm.put("DE_S80U2" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80U2").toString()));
			    hm.put("DE_S80E" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80E").toString()));
			    hm.put("DE_S80CCG" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80CCG").toString()));
			    hm.put("DE_S80CCG2" ,Double.parseDouble( Tax_dec_Map.get(empid+"$DE_S80CCG2").toString()));
			    hm.put("DE_S80TTA" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA").toString()));
			    hm.put("DE_S80TTA1" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA1").toString()));
			    hm.put("DE_S80G" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80G").toString()));
			    hm.put("DE_LETOUT" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_LETOUT").toString()));
			    hm.put("DE_OTHERINCOME" , Double.parseDouble(Tax_dec_Map.get(empid+"$DE_OTHERINCOME").toString()));*/
			    
			   /* double F_365=Double.parseDouble(Tax_dec_Map.get(empid+"$ToTAL_80D").toString())-(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80D").toString())
			    		+Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80E").toString())+Double.parseDouble(TaxSections.get(empid+"S_80G_E").toString())+
			    		Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA1").toString()));*/
			    
			    double F_365=( Double.parseDouble(TaxSections.get(empid+"S_80G_E").toString())+Double.parseDouble(TaxSections.get(empid+"_80D_E").toString()))-(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80D").toString())+Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80E").toString())+Double.parseDouble(TaxSections.get(empid+"S_80G_E").toString())+Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA1").toString()));
			    
			    ps.setString(38, ""+formatter.format(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_80D").toString()))+"" ); //F_361 -->80D
			    ps.setString(39, ""+formatter.format(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80E").toString()))+"	   " ); //F_362
			    ps.setString(40, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"S_80G_E").toString()))+"	   " ); //F_363
			    ps.setString(41, ""+formatter.format(Double.parseDouble(Tax_dec_Map.get(empid+"$DE_S80TTA1").toString()))+"	   " ); //F_364
			    //F_365
			    //Double.parseDouble(TaxSections.get(empid+"_80D_E").toString())  80DE 
			 //   Double.parseDouble(TaxSections.get(empid+"S_80G_E").toString())
			    
			    ps.setString(42, ""+formatter.format(F_365)+"	   " ); //F_365
			   // ps.setString(42, ""+formatter.format(Double.parseDouble(Tax_dec_Map.get(empid+"$ToTAL_80D").toString()))+"	   " ); //F_365
			    
			    
			    ps.setString(43, ""+formatter.format(Total_Deductions_28052019)+"	 " ); //F_366
			    ps.setString(44, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"ANN_TxamT").toString()))+"	   " ); //F_367
			    
			    //TaxSections.get(empid+"_ANN_SURCHARGE").toString())
                double  F_368=(Double.parseDouble(TaxSections.get(empid+"_ANN_Emp_Tax_E").toString())+_$$87A)-(Double.parseDouble(TaxSections.get(empid+"_ANN_SURCHARGE").toString()));

                ps.setString(45, ""+formatter.format(F_368)+"	   " ); //F_368
			  //  ps.setString(45, ""+formatter.format(((Double.parseDouble(TaxSections.get(empid+"_ANN_Emp_Tax_E").toString())+_$$87A)))+"	   " ); //F_368
			    
			   // double F_370=(Double.parseDouble(TaxSections.get(empid+"_ANN_Emp_Tax_E").toString())+_$$87A)-ANN_tx_Paid;
			 //   TaxSections.get(empid+"_ANN_Emp_Tax_E").toString())

			    ps.setString(46, ""+formatter.format(_$$87A)+"" ); //F_369
			    ps.setString(47, ""+formatter.format(_ANN_SURCHARGE)+"	   " ); //F_370
			    ps.setString(48, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"ANN_Educess").toString()))+"	   " ); //F_371
			    ps.setString(49, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"_ANN_Before_Dedu_Relif").toString()))+"	" ); //F_372
			    
			    ps.setString(50, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"ANN_tx_Paid").toString()))+"	   " ); //F_373
			    
			    ps.setString(51, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"_TDS_F").toString()))+"	   " ); //F_374
			    ps.setString(52, "0	 " ); //F_375
			    ps.setString(53, ""+formatter.format(Double.parseDouble(TaxSections.get(empid+"_TDS_F").toString()))+"	   " ); //F_376
			    
			    double _ShortFall$=Double.parseDouble(TaxSections.get(empid+"ANN_tx_Paid").toString())-Double.parseDouble(TaxSections.get(empid+"_TDS_F").toString());
			    
			    ps.setString(54, ""+formatter.format(_ShortFall$)+"" ); //F_377
			    ps.setString(55, "  " ); //F_377_1
			    
			    ps.setString(56, "" ); //F_345
			    
			    ps.setString(57, " "+LANLARD_PAN+" " ); //F_345_1
			    ps.setString(58, "  " ); //F_345_1
			    ps.setString(59, "  " ); //F_345_3
			    ps.setString(60, "  " ); //F_345_4
			    ps.setString(61, "  " ); //F_345_5
			    ps.setString(62, "  " ); //F_345_6
			    ps.setString(63, "  " ); //F_345_7
			    ps.setString(64, "  " ); //F_345_8
			    ps.setString(65, "	" ); //F_353
			    ps.setString(66, "  " ); //F_353_1
			    ps.setString(67, "  " ); //F_353_2
			    ps.setString(68, "  " ); //F_353_3
			    ps.setString(69, "  " ); //F_353_4
			    ps.setString(70, "  " ); //F_353_5
			    ps.setString(71, "  " ); //F_353_6
			    ps.setString(72, "  " ); //F_353_7
			    ps.setString(73, "  " ); //F_353_8
			    ps.setString(74, "	" );
			    ps.setString(75, "	" );
			    ps.setString(76, "	" );
			    ps.setString(77, "	" );
			    ps.setString(78, "	" );
			    ps.setString(79, "	" );
			    ps.setString(80, " " );
			    ps.setString(81, " " );
			    ps.setString(82, " " );
			    ps.setString(83, " " );
			    ps.setString(84, " " );
			    ps.setString(85, " " );
			  //  ps.setString(86, "S_NO	   " );		    
			    
			    
			  /*  ps.setString(1 ,empid);
			    ps.setString(2 ,"");
			    ps.setString(3 ,"");
			    ps.setString(4 ,"");
			    ps.setString(5 ,"");
			    ps.setString(6 ,"");
			    ps.setString(7 ,Basics.get(empid+".callname").toString().trim()); //F_332
			    ps.setString(8 ,"");  //F_332_1 Designation
			    ps.setString(9 ,"");
			    ps.setString(10,"");
			    ps.setString(11,"");
			    ps.setString(12,"");
			    ps.setString(13,"");
			    ps.setString(14,"");
			    ps.setString(15,"");
			    ps.setString(16,"");
			    ps.setString(17,"");
			    ps.setString(18,"");
			    ps.setString(19,"");
			    ps.setString(20,"");
			    ps.setString(21,"");
			    ps.setString(22,"");
			    ps.setString(23,"");
			    ps.setString(24,"");
			    ps.setString(25,""+Double.parseDouble(TaxException.get(empid+"_CA_M_y").toString())+""); // 348
			    ps.setString(26,"");
			    ps.setString(27,"");
			    ps.setString(28,"");
			    ps.setString(29,"");
			    ps.setString(30,"");
			    ps.setString(31, ""+Double.parseDouble(FinalComponents.get(empid+"-ANN_gross").toString())+"");  //F_355
			    ps.setString(32,empid);
			    ps.setString(33,empid);
			    ps.setString(34,empid);
			    ps.setString(35,empid);
			    ps.setString(36,empid);
			    ps.setString(37,empid);
			    ps.setString(38,empid);
			    ps.setString(39,empid);
			    ps.setString(40,empid);
			    ps.setString(41,empid);
			    ps.setString(42,empid);
			    ps.setString(43,empid);
			    ps.setString(44,empid);
			    ps.setString(45,empid);
			    ps.setString(46,empid);
			    ps.setString(47,empid);
			    ps.setString(48,empid);
			    ps.setString(49,empid);
			    ps.setString(50,empid);
			    ps.setString(51,empid);
			    ps.setString(52,empid);
			    ps.setString(53,empid);
			    ps.setString(54,empid);
			    ps.setString(55,empid);
			    ps.setString(56,empid);
			    ps.setString(57,empid);
			    ps.setString(58,empid);
			    ps.setString(59,empid);
			    ps.setString(60,empid);
			    ps.setString(61,empid);
			    ps.setString(62,empid);
			    ps.setString(63,empid);
			    ps.setString(64,empid);
			    ps.setString(65,empid);
			    ps.setString(66,empid);
			    ps.setString(67,empid);
			    ps.setString(68,empid);
			    ps.setString(69,empid);
			    ps.setString(70,empid);
			    ps.setString(71,empid);
			    ps.setString(72,empid);
			    ps.setString(73,empid);
			    ps.setString(74,empid);
			    ps.setString(75,empid);
			    ps.setString(76,empid);
			    ps.setString(77,empid);
			    ps.setString(78,empid);
			    ps.setString(79,empid);
			    ps.setString(80,empid);
			    ps.setString(81,empid);
			    ps.setString(82,empid);
			    ps.setString(83,empid);
			    ps.setString(84,empid);
			    ps.setString(85,empid);
			    ps.setString(86,empid);*/
			   /* ps.setString(87,empid);
			    ps.setString(88,empid);
			    ps.setString(89,empid);*/
			    ps.addBatch();
			   // ps.setString(90,empid);
			           
			    //*************************************
			   
		try{
			
			  try{
			   JasperFillManager.fillReportToFile("C:\\Form16BJrxml\\NEW_PART_B.jasper",hm,new JREmptyDataSource());
			  }catch(Exception EFD){
				  
				  System.out.println("Jrxml Generation ::"+EFD);
				  
			  }
			   String printFileName="C:\\Form16BJrxml\\NEW_PART_B.jrprint";
		       JasperExportManager.exportReportToPdfFile(printFileName, Sub_FilePath+"\\"+empid+".pdf");
		  }catch(JRException jrxml){
			  	jrxml.printStackTrace();
		  }
		
	}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
			// **************** insert data into File
		    
			try {
				ps.executeBatch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			// ****************
						
			
			
	
	}
	
}