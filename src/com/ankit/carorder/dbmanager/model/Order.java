package com.ankit.carorder.dbmanager.model;

public class Order {
	private Long id;
	private String modelName;
	private String color;
	private String status;
	private String time;
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
@Override
public String toString() {
String t = "Order no:"+id+" Model: "+modelName+ "Placed:"+time;
	return t;
}
}
