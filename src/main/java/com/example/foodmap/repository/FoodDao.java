package com.example.foodmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.FoodId;

@Repository
public interface FoodDao extends JpaRepository<Food, FoodId> {

	public List<Food> findByStoreName(String storeName);
	
	public List<Food> findByStoreNameIn(List<String> storeNameList);

	public List<Food> findByStoreNameInAndMealsAssessGreaterThanEqualOrderByMealsAssessDesc(List<String> storeNameList, int mealsAssess);
	
}

