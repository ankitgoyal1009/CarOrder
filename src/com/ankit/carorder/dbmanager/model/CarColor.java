package com.ankit.carorder.dbmanager.model;

public class CarColor {
	private Long id;
	private String colorName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return colorName;
	}
}
