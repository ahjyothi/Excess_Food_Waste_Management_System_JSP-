package com.nxtgio.model;

public class AdminModel {
	private String first_name;
	private String last_name;
	private String email;
	private String username;
	private String password;
	private String address;
	private String contact;
	private String region_id_fk;
	private String locality_id_fk;
	private String description;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRegion_id_fk() {
		return region_id_fk;
	}
	public void setRegion_id_fk(String region_id_fk) {
		this.region_id_fk = region_id_fk;
	}
	public String getLocality_id_fk() {
		return locality_id_fk;
	}
	public void setLocality_id_fk(String locality_id_fk) {
		this.locality_id_fk = locality_id_fk;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
