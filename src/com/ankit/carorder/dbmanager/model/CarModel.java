package com.ankit.carorder.dbmanager.model;

public class CarModel {
	private String modelName;
	private Long id;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return modelName;
	}
}
