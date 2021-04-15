package com.swathingblock.main;

public class ElementDetails {

	private int type;
	private String description;

	public ElementDetails(int _type, String _description) {
		this.type = _type;
		this.description = _description;
	} 
	
	public ElementDetails(int type) {
		this(type, "DESCRIPTION");
	} 
	
	
	public int getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
}
