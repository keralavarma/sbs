package com.southbank.sbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class T2 {
	
	private double _id;
	private double y;
	private double z;
//	private String zz;
	
	public double get_id() {
		return _id;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
//	public String getZz() {
//		return zz;
//	}
	
	public T2(double _id, double y, double z) {
		this._id = _id;
		this.y = y;
		this.z = z;
//		this.zz = zz;
	}
	
	public T2(){
		
	}

}
