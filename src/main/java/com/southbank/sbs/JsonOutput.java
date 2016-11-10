package com.southbank.sbs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonOutput {
	
	@JsonProperty
	private double x;
	@JsonProperty
	private double sumYT1;
	@JsonProperty
	private double sumYT2;
	
	public JsonOutput(){
		
	}

	public JsonOutput(double x, double sumYT1, double sumYT2) {
		this.x = x;
		this.sumYT1 = sumYT1;
		this.sumYT2 = sumYT2;
	}

	public double getX() {
		return x;
	}

	public double getSumYT1() {
		return sumYT1;
	}

	public double getSumYT2() {
		return sumYT2;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setSumYT1(double sumYT1) {
		this.sumYT1 = sumYT1;
	}

	public void setSumYT2(double sumYT2) {
		this.sumYT2 = sumYT2;
	}

	@Override
	public String toString() {
		return String.format("{"
				+ "\"x\":%s"
				+ ", \"sumYT1\":%s"
				+ ", \"sumYT2\":%s"
				+ "}", 
				x
				, sumYT1
				, sumYT2
				);
	}
	
	
	
}
