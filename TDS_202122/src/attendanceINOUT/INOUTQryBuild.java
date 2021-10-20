package attendanceINOUT;

public class INOUTQryBuild {

	public static void main(String[] args) {
		
		
		String fromDate="2018-12-27";
		String toDate="2019-01-26";
		String pendDate="2018-12-31";
		String cstartDate="2018-01-01";
		
		
		String prvY=fromDate.split("-")[0];
		String prvM=fromDate.split("-")[1];
		String prvSD=fromDate.split("-")[2];
		
		
		
		String prvED=pendDate.split("-")[2];
		
		String curY=toDate.split("-")[0];
		String curM=toDate.split("-")[1];
		String curSD=cstartDate.split("-")[2];
		String curED=toDate.split("-")[2];
		
		StringBuffer prvQ=new StringBuffer();
		StringBuffer curQ=new StringBuffer();
		
		for(int i=Integer.parseInt(prvSD);i<=Integer.parseInt(prvED);i++){
			prvQ.append("CONCAT(B.FINDAY"+i+",' || ',B.LOUTDAY"+i+") AS '"+i+"-"+prvM+"-"+prvY+"'");
			prvQ.append(",");
		}
		
		String oldQry=prvQ.toString().substring(0, prvQ.toString().length() - 1);
		prvQ=new StringBuffer();
		
		for(int i=Integer.parseInt(curSD);i<=Integer.parseInt(curED);i++){
			curQ.append("CONCAT(D.FINDAY"+i+",' || ',D.LOUTDAY"+i+") AS '"+i+"-"+curM+"-"+curY+"'");
			curQ.append(",");
		}
		
		String newQry=curQ.toString().substring(0, curQ.toString().length() - 1);
		
		prvQ.append("SELECT A.EMPLOYEESEQUENCENO EmpCode,A.CALLNAME EmpName,C.NAME Status,");
		prvQ.append(" "+oldQry+","+newQry+"");
		prvQ.append(" FROM");
		prvQ.append(" HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY A ");
		prvQ.append(" LEFT JOIN HCLHRM_PROD.TBL_ATTENDANCE_FIN_LOUT_SUMMARY B ON A.EMPLOYEEID=B.EMPLOYEEID AND B.MONTH='"+prvM+"' AND B.YEAR='"+prvY+"' AND B.FINDAY1 IS NOT NULL ");
		prvQ.append(" LEFT JOIN HCLHRM_PROD.TBL_ATTENDANCE_FIN_LOUT_SUMMARY D ON A.EMPLOYEEID=D.EMPLOYEEID AND D.MONTH='"+curM+"' AND D.YEAR='"+curY+"' AND D.FINDAY1 IS NOT NULL ");
		prvQ.append(" LEFT JOIN HCLADM_PROD.TBL_STATUS_CODES C ON A.STATUS=C.STATUS");
		prvQ.append(" ");
		prvQ.append("");
		prvQ.append("");
		
		System.out.println("prvQ-->"+prvQ.toString());
		
		
		
		
		
	}
	
	

}