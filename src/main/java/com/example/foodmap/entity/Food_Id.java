package com.example.foodmap.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Food_Id implements Serializable {
	
	private String store; // ���a�W��

	private String meals; // �\�I
	
	public  Food_Id() {
		
	}
	
	public Food_Id(String store,String meals) {
	this.store = store;
	this.meals = meals;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getMeals() {
		return meals;
	}

	public void setMeals(String meals) {
		this.meals = meals;
	}
	
	
}
