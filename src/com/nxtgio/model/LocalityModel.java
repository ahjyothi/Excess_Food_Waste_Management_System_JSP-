package com.nxtgio.model;

public class LocalityModel {
	private int id;
	private int region_id_fk;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegion_id_fk() {
		return region_id_fk;
	}
	public void setRegion_id_fk(int region_id_fk) {
		this.region_id_fk = region_id_fk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
