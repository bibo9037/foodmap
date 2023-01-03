package com.example.foodmap.vo;

import java.util.List;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(JsonInclude.Include.NON_EMPTY)				//不顯示空的或null
public class FoodMapRes {
	
	private List<Store> storeList;
	
	private List<Food> mealsList;
	
	private List<FoodMapRes> assessList;
	
	private String city; 
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)		//不顯示為0的內容
	private double storeAssess;
	
	private String message;
	
	private Food food;
	
	private Store store;
	
	
//========================================	
	
	public FoodMapRes() {

	}
	
	public FoodMapRes(int storeAssess) {
		this.storeAssess = storeAssess;
	}
	
	public FoodMapRes(String message) {
		this.message = message;
	}
	
	public FoodMapRes(Food food,String message) {
		this.food = food;
		this.message = message;
	}

	public FoodMapRes(Store store,String message) {
		this.store = store;
		this.message = message;
	}
	
	public List<Store> getStoreName(){
		return storeList;
	}

	public void setStoreName(List<Store> storeList1) {
		this.storeList = storeList1;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public List<Food> getMealsList() {
		return mealsList;
	}

	public void setMealsList(List<Food> mealsList) {
		this.mealsList = mealsList;
	}

	public void setStoreAssess(double storeAssess) {
		this.storeAssess = storeAssess;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getStoreAssess() {
		return storeAssess;
	}

	public void setStoreAssess(int nameAssess) {
		this.storeAssess = nameAssess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<FoodMapRes> getAssessList() {
		return assessList;
	}

	public void setAssessList(List<FoodMapRes> assessList) {
		this.assessList = assessList;
	}


}
