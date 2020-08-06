package com.vmware.dm.model;

import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONArray;

public class DMConnectionResponse {
	
	private String status;
	
	private HashSet<String> columnNames;
	
	private JSONArray records  ;

	
	public JSONArray getRecords() {
		return records;
	}

	public void setRecords(JSONArray records) {
		this.records = records;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HashSet<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(HashSet<String> columnNames) {
		this.columnNames = columnNames;
	}

	
	

	

}
