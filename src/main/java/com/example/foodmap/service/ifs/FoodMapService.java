package com.example.foodmap.service.ifs;

import java.util.List;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Store;
import com.example.foodmap.vo.FoodMapRes;

public interface FoodMapService {

	// 1.�g�J���a����
	public Store setStore(String city, String storeName);

	// 1-1.�ק�ө���� //Info�i�H�N��DB���h��쪺���
	public Store changeInfoForStore(String city, String storeName, String newStoreName);

	// 2.�g�J�\�I����
	public Food setMeals(String storeName, String mealsName, int mealsPrice, int mealsAssess);

	// 2-2.�ק��\�I���
	public Food changeInfoForMeals(String storeName, String mealsName, String newMealsName, int MealsPrice, int MealsAssess);

	// 3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	public List<FoodMapRes> findStoreByCity(String city, int num);

	// 4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	public List<FoodMapRes> findStoreAssess(double storeAssess);

	// 5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	public List<FoodMapRes> findStoreAssessAndMealsAssess(double storeAssess, int mealsAssess);

}
