package com.ankit.carorder.dbmanager.model;

public class Manufacturer {
	private String manufacturerName;
	private Long id;

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return manufacturerName;
	}
}
