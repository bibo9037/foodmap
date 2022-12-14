package com.example.foodmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodmap.entity.Store;

public interface StoreDao extends JpaRepository<Store, String> {
	
	public List<Store> findByCity(String city);
	
	public List<Store> findByStoreAssessGreaterThanEqualOrderByStoreAssessDesc(double storeAssess);

}
