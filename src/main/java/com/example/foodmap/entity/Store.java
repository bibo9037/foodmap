package com.example.foodmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store")
public class Store {

	@Column(name = "city")
	private String city; // ���a�Ҧb����

	@Id
	@Column(name = "store")
	private String storeName; // ���a�W��

	@Column(name = "store_assess")
	private double storeAssess; // ���a����

	public Store() {

	}

	public Store(String city, String storeName) {
		this.city = city;
		this.storeName = storeName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public double getStoreAssess() {
		return storeAssess;
	}

	public void setStoreAssess(double storeAssess) {
		this.storeAssess = storeAssess;
	}

}
