package com.example.foodmap.service.ifs;

import java.util.List;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Store;
import com.example.foodmap.vo.FoodMapRes;

public interface FoodMapService {

	//1.�g�J���a����	
	public Store setStore(String city, String store);
	
	//2.�g�J�\�I����	
	public Food setMeals(String store, String meals, int mealsPrice, int mealsAssess);		
	
	//�ק�ө����   //Info�i�H�N��DB���h��쪺���
	public Store changeInfoForStore(String city, String store, String newStore);	
		
	//�ק��\�I���
	public Food changeInfoforMeals(String store, String meals, String newMeals, int MealsPrice, int MealsAssess);	
		
	//3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	public List<FoodMapRes> findStoreByCity(String city, int num);	
	
	//4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	public List<Store> findStoreAssess(double storeAssess);		
	
	//5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W   �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	public FoodMapRes findStoreAssessAndMealsAssess(String store, int mealsAssess);		
		
}
