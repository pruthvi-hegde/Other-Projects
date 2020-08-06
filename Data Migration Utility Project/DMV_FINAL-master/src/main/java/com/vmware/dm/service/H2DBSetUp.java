package com.vmware.dm.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vmware.dm.model.DMConnectionRequest;
import com.vmware.dm.model.H2ConnectResponse;
import com.vmware.dm.model.UpateH2Model;
import com.vmware.dm.model.UpdateH2Records;

@Service
public class H2DBSetUp {
	static Connection con = null;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public  String connectH2(HashSet<String> col, JSONArray jsonArray) throws JSONException {
		H2ConnectResponse conReq = new H2ConnectResponse();
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
		Iterator iterate_value = col.iterator();
		String cols = "";
		while(iterate_value.hasNext())
		{
			cols = cols  + iterate_value.next() + " VARCHAR(50) NOT NULL,";
		}
String	dropSchema = "DROP TABLE IF EXISTS SAMPLE ";	
//String dropTable = "IF OBJECT_ID('SAMPLE', 'U') IS NOT NULL \n" + 
//		"  DROP TABLE SAMPLE;";
st.executeUpdate(dropSchema);
		String 	query = "CREATE TABLE SAMPLE ( "+cols+" );" ;
		st.executeUpdate(query);
		for(int i = 0 ;i<jsonArray.length(); i++)
		{
			JSONObject a = jsonArray.getJSONObject(i);
			System.out.println(a);
			String insertQuery = "INSERT INTO SAMPLE VALUES ('" + a.get("customer_name")  + "','" + a.get("city")+ "','" + a.get("customer_id") + "')";
			System.out.println(insertQuery);
			st.executeUpdate(insertQuery);
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
	public  static String UpdateColumnHeader(String mapJson) throws JSONException {
		H2ConnectResponse conReq = new H2ConnectResponse();
		String h2DBDriver = "org.h2.Driver";
		String h2DBConnectionString = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
		String h2DBUser = "";
		String h2DBPassword = "";
		try {
		Class.forName(h2DBDriver);
		System.out.println("H@2");
		con = DriverManager.getConnection(h2DBConnectionString,h2DBUser,h2DBPassword);
		Statement st = con.createStatement(); 
		String cols = "";
		String customer_name = gson.fromJson(mapJson,UpateH2Model.class).getCustomer_name();
		String customerid = gson.fromJson(mapJson,UpateH2Model.class).getCustomer_id();
		String city = gson.fromJson(mapJson,UpateH2Model.class).getCity();
		if(customer_name==null && customer_name.length()==0)
			customer_name = "customer_name";
		if(customerid==null && customerid.length()==0)
			customerid = "customer_id";
		if(city.isEmpty() && city==null && city.length()==0)
			city = "city";
		
		String updateQuery_name = "ALTER TABLE SAMPLE ALTER COLUMN customer_name RENAME TO "  + customer_name + "; " ;
		st.executeUpdate(updateQuery_name);
		
		String updateQuery_id = "ALTER TABLE SAMPLE ALTER COLUMN customer_id RENAME TO "  + customerid + "; " ;
		st.executeUpdate(updateQuery_id);
		
		String updateQuery_city = "ALTER TABLE SAMPLE ALTER COLUMN city RENAME TO "  + city + "; " ;
		st.executeUpdate(updateQuery_city);
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
	
	public  static String UpdateRecords(String authDetails) throws JSONException {
		H2ConnectResponse conReq = new H2ConnectResponse();
		String h2DBDriver = "org.h2.Driver";
		String h2DBConnectionString = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
		String h2DBUser = "";
		String h2DBPassword = "";
		JSONArray ab = new JSONArray(authDetails);
		try {
		Class.forName(h2DBDriver);
		System.out.println("H@2");
		con = DriverManager.getConnection(h2DBConnectionString,h2DBUser,h2DBPassword);
		Statement st = con.createStatement(); 
		String cols = "";
		for(int i = 0;i<ab.length();i++)
		{
			JSONObject abb = ab.getJSONObject(i);
			
		String columnName = abb.getString("column_name");
		String initial_value = abb.getString("initial_value");
		String final_value = abb.getString("final_value");
		String updateQuery = "UPDATE SAMPLE SET " + columnName + " = '" + final_value  +"' WHERE "+ columnName + "= '" +initial_value + "'"; 
		st.executeUpdate(updateQuery);
		}
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
}
