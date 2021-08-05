Hi 
<%

	String sql1="SELECT R.GRPCODE ,R.EMP_CODE FROM njindiainvest.GRPMST G INNER JOIN integrationsolution.REG_PATN_PROD R ON G.GRPCODE=R.GRPCODE WHERE R.GRPCODE IN ('000005','000020')";
	String sql2="SELECT R.GRPCODE ,R.EMP_CODE FROM njindiainvest.GRPMST G INNER JOIN integrationsolution.REG_PATN_PROD R ON G.GRPCODE=R.GRPCODE WHERE R.GRPCODE IN ('000005','000020')";
 {
	 com.finlogic.util.persistence.SQLUtility sql= new com.finlogic.util.persistence.SQLUtility(); 
	 try
	 {
	 	java.util.List rs1=sql.getList("njindiainvest_offline",sql1);

		out.println("<br><br>Size : " + rs1.size());
	 }
	 catch(Throwable ex)
	 {
	 	out.print("Error : " + ex.getMessage());
	 } 
 }
 {
	 com.finlogic.util.persistence.SQLUtility sql= new com.finlogic.util.persistence.SQLUtility();
	 	//org.springframework.jdbc.support.rowset.SqlRowSet rs =sql.getRowSet("njindiainvest_offline", sql2);
 }
%>
