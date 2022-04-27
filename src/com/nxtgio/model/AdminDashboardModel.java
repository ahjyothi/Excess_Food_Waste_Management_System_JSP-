package com.nxtgio.model;

public class AdminDashboardModel extends AdminModel {
	private String oid;
	private String user_id_fk;
	private String admin_id_fk;
	private String order_details;
	private String status;
	private String Created_on;
	private String Updated_on;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getUser_id_fk() {
		return user_id_fk;
	}
	public void setUser_id_fk(String user_id_fk) {
		this.user_id_fk = user_id_fk;
	}
	public String getAdmin_id_fk() {
		return admin_id_fk;
	}
	public void setAdmin_id_fk(String admin_id_fk) {
		this.admin_id_fk = admin_id_fk;
	}
	public String getOrder_details() {
		return order_details;
	}
	public void setOrder_details(String order_details) {
		this.order_details = order_details;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreated_on() {
		return Created_on;
	}
	public void setCreated_on(String created_on) {
		Created_on = created_on;
	}
	public String getUpdated_on() {
		return Updated_on;
	}
	public void setUpdated_on(String updated_on) {
		Updated_on = updated_on;
	}
	
}
