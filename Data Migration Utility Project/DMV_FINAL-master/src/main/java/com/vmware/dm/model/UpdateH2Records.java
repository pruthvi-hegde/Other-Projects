package com.vmware.dm.model;

public class UpdateH2Records {
	
	private String column_name;
	private String initial_value;
	private String final_value;
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getInitial_value() {
		return initial_value;
	}
	public void setInitial_value(String initial_value) {
		this.initial_value = initial_value;
	}
	public String getFinal_value() {
		return final_value;
	}
	public void setFinal_value(String final_value) {
		this.final_value = final_value;
	}
	
	

}
