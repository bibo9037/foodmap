package com.example.foodmap.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)				//不顯示空的或null
public class FoodMapResponseList {

	private String city;

	private String store;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)			//若數字變數為0則不顯示	
	private double storeAssess;

	private List<FoodMapRes> resList;
	
	private String message;
	
	public FoodMapResponseList() {

	}
	public FoodMapResponseList(String message) {
		this.message = message;
	}

	public FoodMapResponseList(String city, String store, double storeAssess) {
		this.city = city;
		this.store = store;
		this.storeAssess = storeAssess;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public double getStoreAssess() {
		return storeAssess;
	}

	public void setStoreAssess(double storeAssess) {
		this.storeAssess = storeAssess;
	}

	public List<FoodMapRes> getResList() {
		return resList;
	}

	public void setResList(List<FoodMapRes> resList) {
		this.resList = resList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
