package com.vmware.dm.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vmware.dm.model.CompareDB;
import com.vmware.dm.model.DMConnectionRequest;
import com.vmware.dm.model.DMConnectionResponse;
import com.vmware.dm.model.H2ConnectResponse;

@Service
public class GenericDBSetup {
	@SuppressWarnings("unused")
	@Value("${oracleDriver}")
	private static String OracleDriver;
	@Value("${mysqlDriver}")
	private static String mySqlDriver;
	@SuppressWarnings("unused")
	private static String port;
	static Connection con = null;
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@SuppressWarnings("unused")
	public static String DBSetup(@RequestBody String authDetails) throws JSONException 
    {
		DMConnectionResponse conReq = new DMConnectionResponse();
		String status = "";
		String host = gson.fromJson(authDetails,DMConnectionRequest.class).getHost();
		String portNumber = gson.fromJson(authDetails,DMConnectionRequest.class).getPort();
		String schemaName = gson.fromJson(authDetails,DMConnectionRequest.class).getSchemaName();
		String serviceName = gson.fromJson(authDetails,DMConnectionRequest.class).getServiceName();
		String tableName = gson.fromJson(authDetails,DMConnectionRequest.class).getTableName();
		String type = gson.fromJson(authDetails,DMConnectionRequest.class).getType();
		String userName = gson.fromJson(authDetails,DMConnectionRequest.class).getUserName();
		String password = gson.fromJson(authDetails,DMConnectionRequest.class).getPassword();
		String driver="";
		String column_name="";
	
			
		
		if(type.equals("Oracle"))
		{
			driver = OracleDriver;
			port = "1521";
			column_name="user_tab_cols";
			
		}
		else if(type.equals("MySql"))
		{
			driver = mySqlDriver;
			port =  "3306";
			column_name="INFORMATION_SCHEMA.COLUMNS";
		}			
		JSONArray jsonArray1 = new JSONArray();
		 try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");  
			 con=DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port + ":"+serviceName,userName,password); 
			Statement stmt=con.createStatement();  
//			ResultSet rs = stmt.executeQuery("SELECT column_name\r\n" + 
//					"FROM "+column_name+"\r\n" + 
//					"WHERE table_name = '"+ tableName + "'");
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
			@SuppressWarnings("rawtypes")			
			JSONArray jsonArray = new JSONArray();
			while(rs.next())
			{
				System.out.println(rs);
				int total_rows = rs.getMetaData().getColumnCount();
				JSONObject obj1 = new JSONObject();
				JSONObject obj = new JSONObject();
	            for (int i = 0; i < total_rows; i++) {
	            	
	                obj.put(rs.getMetaData().getColumnLabel(i + 1)
	                        .toLowerCase(), rs.getObject(i + 1));
	                
	            }
	            jsonArray.put(obj);
			}
			
			
			
			ResultSet headers = stmt.executeQuery("SELECT column_name\r\n" + 
					"FROM "+column_name+"\r\n" + 
					"WHERE table_name = '"+ tableName + "'"); 
			System.out.println("SELECT column_name\r\n" + 
					"FROM "+column_name+"\r\n" + 
					"WHERE table_name = '"+ tableName + "';");
			HashSet<String> col = new HashSet<String>();
			while(headers.next())
			{
				col.add(headers.getString(1));
				
			}
			
//			Runnable myTask = new Runnable() {
//				public void run() {
					new H2DBSetUp().connectH2(col,jsonArray);
					
				
//				}
//				};
			conReq.setColumnNames(col);
	            if (con != null) {
	                status = "Success" ; 
	                
	            }
	            conReq.setStatus("Success");

			
			
			conReq.setRecords(jsonArray);
			
	            if (con != null) {
	                status = "Success" ; 
	                
	            }
	            conReq.setStatus("Success");
	           
	        } catch (ClassNotFoundException ex) {
	            System.out.println("Could not find database driver class");
	            ex.printStackTrace();
	        } catch (SQLException ex) {
	            System.out.println("An error occurred. Maybe user/password is invalid");
	            conReq.setStatus("Cannot Connect to DB");
	            ex.printStackTrace();
	        } finally {
	            if (con != null) {
	                try {
	                   // con.close();
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }
		return gson.toJson(conReq);
		
    }
	

	@SuppressWarnings("unused")
	public static String TargetDBSetup(@RequestBody String authDetails) throws JSONException, ClassNotFoundException 
    {
		
		String status = "";
		ArrayList<CompareDB> h2 = new ArrayList<CompareDB>();
		String host = gson.fromJson(authDetails,DMConnectionRequest.class).getHost();
		String portNumber = gson.fromJson(authDetails,DMConnectionRequest.class).getPort();
		String schemaName = gson.fromJson(authDetails,DMConnectionRequest.class).getSchemaName();
		String serviceName = gson.fromJson(authDetails,DMConnectionRequest.class).getServiceName();
		String tableName = gson.fromJson(authDetails,DMConnectionRequest.class).getTableName();
		String type = gson.fromJson(authDetails,DMConnectionRequest.class).getType();
		String userName = gson.fromJson(authDetails,DMConnectionRequest.class).getUserName();
		String password = gson.fromJson(authDetails,DMConnectionRequest.class).getPassword();
		String driver="";
		String column_name="";
		if(type.equals("Oracle"))
		{
			driver = OracleDriver;
			port = "1521";
			column_name="user_tab_cols";
			
		}
		else if(type.equals("MySql"))
		{
			driver = mySqlDriver;
			port =  "3306";
			column_name="INFORMATION_SCHEMA.COLUMNS";
		}	
		
		String h2DBDriver = "org.h2.Driver";
		String h2DBConnectionString = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
		String h2DBUser = "";
		String h2DBPassword = "";
		 try {
			 
				Class.forName(h2DBDriver);
				System.out.println("H@");
				con = DriverManager.getConnection(h2DBConnectionString,h2DBUser,
				h2DBPassword);
				Statement st = con.createStatement(); 
				ResultSet rs_h2 = st.executeQuery("select * from sample;");
				
				
				Class.forName("oracle.jdbc.driver.OracleDriver");  
				con=DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port + ":"+serviceName,userName,password); 
				Statement stmt=con.createStatement();  
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);	
				int i =1;
				while(rs.next() && rs_h2.next())

				{
					
					System.out.println(rs.getObject(i));
					System.out.println(rs_h2.getObject(i));
					
					if(rs.getObject("customer_name").equals(rs_h2.getObject("customer_name")) || rs.getObject("customer_id").equals(rs_h2.getObject("customer_id")) ||rs.getObject("city").equals(rs_h2.getObject("city")))
					{
						CompareDB db_compare = new CompareDB();
							db_compare.setCity(rs.getObject("city").toString());	
							db_compare.setCustomer_id(rs.getObject("customer_id").toString());
							db_compare.setCustomer_name(rs.getObject("customer_name").toString());
							h2.add(db_compare);
					}		
							
				}
				i=i+1;
				
		 }
		 catch (SQLException ex) {
	            System.out.println("An error occurred. Maybe user/password is invalid");
	            
	            ex.printStackTrace();
	        } finally {
	            if (con != null) {
	                try {
	                   // con.close();
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }
			
		
		return gson.toJson(h2);
		
    }	 
		
}
