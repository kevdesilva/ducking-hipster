import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;

import oracle.xdb.XMLType;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class TestOracleXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestOracleXML t = new TestOracleXML();
		t.listXML();

	}
	
	   private void initCache()
	   {
		   try {

		       Context ctx = new InitialContext();
		       //DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		       // Load the JDBC driver
		       String driverName = "oracle.jdbc.OracleDriver";
		       Class.forName(driverName);

		       // Create a connection to the database  
		       String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		       //String url = "jdbc:oracle:oci:@localhost:1521:orcl";       
		       
		       String username = "orbeondev";
		       String password = "orbeondev";
		       Connection con = DriverManager.getConnection(url, username, password);
		       //Connection con = DriverManager.getConnection("jdbc:oracle:oci:@localhost_orcl", "orbeondev", "orbeondev");
		       
		       //Connection con = ds.getConnection();
		       con.setAutoCommit(false);
		       PreparedStatement pstmt = (PreparedStatement)con.prepareStatement("select xml, file_content, a.deleted, a.document_id from orbeon_form_data a, orbeon_form_data_attach b where a.document_id = b.document_id and a.app='webraters' and a.form = 'spreadsheets' order by a.last_modified desc");
		       
		       ResultSet rs = (ResultSet)pstmt.executeQuery();
		       //OracleResultSet orset = (OracleResultSet) rs;    	       
		       
		       while (rs.next()) {    	      
	    	      
		    	  //XMLType poxml = (XMLType)ors.getObject(1);
		    	  //rs.getBinaryStream(1);	    	  
		    	  //ors.getBlob(1);
	    	      //String xml = poxml.getStringVal();
		    	  //String xml = "<PO><PONO>200</PONO><PNAME>PO_2</PNAME></PO>";
		    	  XMLType poxml = (XMLType)rs.getObject(1);
		    	  //OPAQUE oq = orset.getOPAQUE(1);
		    	  //XMLType xmlType = XMLType.createXML(oq);
		    	  //Object o=oq.stringValue();
		    	  //System.out.println("[" + o.getClass().getName() + "]");
		    	  String xml=poxml.getStringVal();
		    	  SAXReader reader = new SAXReader();
		    	  Document docin = reader.read(new ByteArrayInputStream(xml.getBytes()));
				  Node node = docin.selectSingleNode("//xls-form");
				  String xlsFormName = node.getText();
				  System.out.println(xlsFormName);
	    	   }
		       rs.close();
		       pstmt.close();
		       con.close();
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }	   
	   }
	   
	   private void listXML()
	   {
		   try {

		       Context ctx = new InitialContext();
		       //DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		       // Load the JDBC driver
		       String driverName = "oracle.jdbc.OracleDriver";
		       Class.forName(driverName);

		       // Create a connection to the database  
		       String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		       //String url = "jdbc:oracle:oci:@localhost:1521:orcl";       
		       
		       String username = "orbeondev";
		       String password = "orbeondev";
		       Connection con = DriverManager.getConnection(url, username, password);
		       //Connection con = DriverManager.getConnection("jdbc:oracle:oci:@localhost_orcl", "orbeondev", "orbeondev");
		       
		       //Connection con = ds.getConnection();
		       con.setAutoCommit(false);
		       PreparedStatement pstmt = (PreparedStatement)con.prepareStatement("select xml, last_modified from orbeon_form_definition order by last_modified");
		       
		       ResultSet rs = (ResultSet)pstmt.executeQuery();
		       //OracleResultSet orset = (OracleResultSet) rs;    	       
		       
		       while (rs.next()) {    	      
	    	      
		    	  //XMLType poxml = (XMLType)ors.getObject(1);
		    	  //rs.getBinaryStream(1);	    	  
		    	  //ors.getBlob(1);
	    	      //String xml = poxml.getStringVal();
		    	  //String xml = "<PO><PONO>200</PONO><PNAME>PO_2</PNAME></PO>";
		    	  XMLType poxml = (XMLType)rs.getObject(1);
		    	  Timestamp dt = rs.getTimestamp(2);
		    	  //OPAQUE oq = orset.getOPAQUE(1);
		    	  //XMLType xmlType = XMLType.createXML(oq);
		    	  //Object o=oq.stringValue();
		    	  //System.out.println("[" + o.getClass().getName() + "]");		    	  
		    	  String xml=poxml.getStringVal();
		    	  System.out.println("***************************start**********************************");
		    	  System.out.println(dt);
		    	  System.out.println(xml);
	    	   }
		       rs.close();
		       pstmt.close();
		       con.close();
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }	   
	   }


}
