package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nxtgio.model.LocalityModel;
import com.nxtgio.model.RegionModel;

public class RegionDao {
	Connection con;
	PreparedStatement pst;
	String query;
	ResultSet rs;

	public RegionDao(Connection con) {
		this.con = con;
	}

	public List<RegionModel> getAllRegion() {
		List<RegionModel> list = new ArrayList<>();   // Create an ArrayList object
		try {
			query = "select * from [region]";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				RegionModel region = new RegionModel();
				region.setRegion_id(rs.getInt("region_id"));
				region.setRegion_name(rs.getString("region_name"));
				list.add(region);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<LocalityModel> getRegionByLocalityId(int localityId) {
		List<LocalityModel> list = new ArrayList<>();
		try {
			query = "select * from [locality] where region_id_fk=? and IsOccupied=0";    /* to lock selected locality   */
			pst = this.con.prepareStatement(query);
			pst.setInt(1, localityId);
			rs = pst.executeQuery();
			while (rs.next()) {
				LocalityModel locality = new LocalityModel();
				locality.setId(rs.getInt("id"));
				locality.setRegion_id_fk(rs.getInt("region_id_fk"));
				locality.setName(rs.getString("name"));
				list.add(locality);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<LocalityModel> getRegisteredRegionByLocalityId(int localityId) {
		List<LocalityModel> list = new ArrayList<>();
		try {
			query = "select * from [locality] where region_id_fk=? and IsOccupied=1";    /* to display only used locality to create order  */
			pst = this.con.prepareStatement(query);
			pst.setInt(1, localityId);
			rs = pst.executeQuery();
			while (rs.next()) {
				LocalityModel locality = new LocalityModel();
				locality.setId(rs.getInt("id"));
				locality.setRegion_id_fk(rs.getInt("region_id_fk"));
				locality.setName(rs.getString("name"));
				list.add(locality);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getAdminId(int localityId) {
		String AdminId = null;
		try {
			query = "select id from admin_register where locality_id_fk=?";    /* to select id from admin register table to use in admin_id_fk for creating order */
			pst = this.con.prepareStatement(query);
			pst.setInt(1, localityId);
			rs = pst.executeQuery();
			while (rs.next()) {
				AdminId = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return AdminId;
	}

}
