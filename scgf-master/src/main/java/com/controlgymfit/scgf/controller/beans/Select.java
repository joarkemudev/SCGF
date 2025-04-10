package com.controlgymfit.scgf.controller.beans;

/**
 * Proporciona la estructura para una lista de seleccón.
 * @author JQ
 *
 */
public class Select {

	private String label;
	private String value;
	private String value1;
	private String value2;
	
	public Select(){}
		
	public Select(String label, String value1, String value2) {
		super();
		this.label = label;
		this.value1 = value1;
		this.value2 = value2;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
}
