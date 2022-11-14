package com.example.foodmap.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Food_Id;
import com.example.foodmap.entity.Store;
import com.example.foodmap.repository.FoodDao;
import com.example.foodmap.repository.StoreDao;
import com.example.foodmap.service.ifs.FoodMapService;
import com.example.foodmap.vo.FoodMapRes;


@Service
public class FoodMapServiceimpl implements FoodMapService {

	@Autowired
	private FoodDao foodDao;

	@Autowired
	private StoreDao storeDao;

	
	// 1.�g�J�ө���T
	@Override
	public Store setStore(String city, String store) {
		if(storeDao.existsById(store)) {
			return null;
		}
		Store st = new Store(city, store);
		return storeDao.save(st);
	}

	
	// 2.�g�J�\�I��T
	@Override
	public Food setMeals(String store, String meals, int mealsPrice, int mealsAssess) {
		Food_Id food_id = new Food_Id(store, meals);
		if (foodDao.existsById(food_id)) {
			return null;
		}
		Food food = new Food(store, meals, mealsPrice, mealsAssess);	//�s�W�\�I�����ɭ� ���a���������
		foodDao.save(food);
		List<Food> fm = foodDao.findByStore(store);
		double totalAssess = 0 ;
		int count = 0 ;
		for(Food s : fm) {
			totalAssess += s.getMealsAssess();
			count++;
		}
		
		double storeAssess = totalAssess/count;
		storeAssess = Math.round(totalAssess/count * 10.0) / 10.0;		//�|�ˤ��J�ܤp���I�� 1 ��
		
		Optional<Store> st = storeDao.findById(store);
		st.get().setStoreAssess(storeAssess);
		storeDao.save(st.get());
			return food;	
		}

	
	// 3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	@Override
	public List<FoodMapRes> findStoreByCity(String city, int num) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();
		List<Store> storeList = storeDao.findByCity(city); //�s��ۦP�������ө�
		  //��ƫ��A �ܼƦW�� : ����H
		for(Store store : storeList)  {
			List<Food> foodList = foodDao.findByStore(store.getStore());
			FoodMapRes res = new FoodMapRes();
			res.setMealsList(foodList); 								//��foodList�����set�ires��Mealslist��
			res.setCity(store.getCity());
		}
		return null;
	}

	
	// 4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	@Override
	public List<Store> findStoreAssess(double storeAssess) {
		List<Store> store = storeDao.findByStoreAssessGreaterThanEqual(storeAssess);
		return store;
	}

	
	// 5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	@Override
	public FoodMapRes findStoreAssessAndMealsAssess(String store, int mealsAssess) {
		FoodMapRes res = new FoodMapRes();
		List<Store> st = storeDao.findByStore(store);
		List<Food> fo = foodDao.findByMealsAssess(mealsAssess);
		List<Store> pa = new ArrayList<>();

		for (Store assess : st) {
			pa.addAll(storeDao.findByStoreAssess(assess.getStoreAssess()));
		}
		res.setMealsList(fo);
		res.setStoreList(st); 
		return res;
	}

	
	//�ק�ө����
	@Override
	public Store changeInfoForStore(String city, String store, String newStore) {
//		Store store1 = new Store();
//		Optional<Store> storeOp =  storeDao.findById(store);
//		store1 = storeOp.get();
		Store store1 = storeDao.findById(store).get();		//�e���T�檺�@�Y���G
		storeDao.delete(store1);
		if(store1 == null) {
			return null;
		}
		
		if(StringUtils.hasText(city)) {
			store1.setCity(city);	
		}
		
		if(StringUtils.hasText(newStore)) {
			store1.setStore(newStore);
			List<Food> foodList = foodDao.findByStore(store);		//���X�ۦP���W��store
			
			for(Food food1 : foodList) {
				foodDao.delete(food1);
				food1.setStore(newStore);		//�]�w�s�����W�i�h
				foodDao.save(food1);
			}
		}		
		storeDao.save(store1);
		return store1;
	}

	
	//�ק��\�I���
	@Override
	public Food changeInfoforMeals(String store, String meals, String newMeals, int mealsPrice, int mealsAssess) {
		Food_Id foodId = new Food_Id(store, meals);
//		Optional<Food> foodOp = foodDao.findById(foodId);			//�إ�food�æs���Ʈw�����쪺�\�I���
//		Food food1 = foodOp.get();									//���쪺�\�I�|�s��b�o��
		Food food1 = foodDao.findById(foodId).get();
//		foodDao.delete(food1);
		//���ۧP�_�n�ק諸�\�I�s���s�b
//		if(food1 == null) {		
//			return null;
//		}

		
		if (foodDao.existsById(foodId)) {
			return null;
		}
		
		if(StringUtils.hasText(newMeals)) {		//�p�GnewMeals�P�_����J�ȡA��ܻݭn�ק��\�I	
			food1.setMeals(newMeals);
		}
		
//		if(mealsPrice > 0 ) {					//�p�GMealsPrice������0(�ȧP�_���Ū���)�A�N���ק�
//			food1.setMealsPrice(mealsPrice);
//		}
			
			
		if(mealsAssess > 0 ) {
			food1.setMealsAssess(mealsAssess);
			List<Food> fm = foodDao.findByStore(store);
			double totalAssess = 0 ;
			int count = 0 ;
			for(Food s : fm) {
				totalAssess += s.getMealsAssess();
				count++;
			}
		
			double storeAssess = (totalAssess + mealsAssess) / (count + 1);
			storeAssess = Math.round(totalAssess/count * 10.0) / 10.0;		//�|�ˤ��J�ܤp���I�� 1 ��
		
			Optional<Store> st = storeDao.findById(store);
			st.get().setStoreAssess(storeAssess);
			storeDao.save(st.get());
		}
		
		return foodDao.save(food1);
	}
}