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
		if (storeDao.existsById(store)) { 											// �ϥ�existsById �P�_��Ʈw���O�_�w�s�b�ۦP��store
			return null;															// �Y�S���h�^��null
		}
		Store sto = new Store(city, store);											// �]�wstore���ܼ�city�Mstore
		return storeDao.save(sto); 													// �ϥ�storeDao��save��k�s�^sto �æ^��
	}

	// 1-1.�ק�ө����
	@Override
	public Store changeInfoForStore(String city, String store, String newStore) {
//		Store store1 = new Store();													// �]�w�ܼ�store1���@�ӷs��Store
//		Optional<Store> storeOp = storeDao.findById(store);							// �]�wStore��ƫ��A��Optional(storeOp)�s��z�LstoreDao��findById��k���o��store
//		store1 = storeOp.get();														// store1�s��z�LstoreOp��get��k���X���F��
		Store store1 = storeDao.findById(store).get(); 								// �e���T�檺�@�Y���G�A�ҥHstore1�̭��񪺬OfindById�����X��store
		storeDao.delete(store1);													// �ϥ�storeDao����delete��k�R��store1�������

		if (store1 == null) {														// �P�_store1�O�_��null
			return null;															// �Y�O�h�^��null
		}

		if (StringUtils.hasText(city)) {											// �P�_�ܼ�city�O�_����
			store1.setCity(city);													// �Y���A�h�N�ȳ]�w�istore1��city
		}

		if (StringUtils.hasText(newStore)) {										// �P�_�ܼ�newStore�O�_����
			store1.setStore(newStore);												// �Y���A�h�N�ȳ]�w�istore1��newStore
			List<Food> foodList = foodDao.findByStore(store); 						// �]�wFood�ꫬ�A��List(foodList)�s��z�LfoodDao��findByStire�����X�ۦP���W��store

			for (Food food1 : foodList) { 											// �]�wFood��ƫ��A���ܼ�food1�A���PfoodList���ۦP���W��store�é�Jfood1��
				foodDao.delete(food1);												// �ϥ�foodDao��delete��k�R����bfood1����store
				food1.setStore(newStore);											// �]�w�s�����W���ܼ�newStore
				foodDao.save(food1);												// �ϥ�foodDao��save��k�s�^food1
			}
		}
		storeDao.save(store1);														// �ϥ�storeDao��save��k�s�^store1
		return store1;																// �^�ǵ��Gstore1
	}

	// 2.�g�J�\�I��T
	@Override
	public Food setMeals(String store, String meals, int mealsPrice, int mealsAssess) {
		Food_Id food_id = new Food_Id(store, meals);								// �]�wFood_Id���ܼ�store�Mmeals

		// �p�G�䤣��store�N�����~�򩹤U��
		if (store.isEmpty()) { 														// �ϥ�isEmpty �P�_store�����S�����a
			return null; 															// �Y�S���h�^��null
		}

		if (foodDao.existsById(food_id)) { 											// �P�_�O�_�w���ۦP��store�άOmeals
			return null; 															// �Y�S���h�^��null
		}

		Food food = new Food(store, meals, mealsPrice, mealsAssess); 				// �s�W�\�I�����ɭ� ���a���������
		foodDao.save(food);															// �z�LfoodDao��save��k�s�^food
		List<Food> foodList = foodDao.findByStore(store); 							// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao��findByStore��k���o��store
		double totalAssess = 0; 													// �]�w�@�Ӭ�0���\�I�`�����ܼ�
		int count = 0; 																// �]�w�@�Ӭ�0���~���ܼ�
		for (Food food1 : foodList) {												// �]�w�ܼ�food1�s��ŦX����store
			totalAssess += food1.getMealsAssess(); 									// �qfood1�����o��MealsAssess��itotalAssess�ð��\�I�������[�`
			count++;																// ��MealsAssess��JtotalAssess�N+1
		}

		double storeAssess = totalAssess / count; 									// �]�w�ܼ�storAssess�s��totalAssess��count�᪺��(�\�I�`����)
		storeAssess = Math.round(totalAssess / count * 10.0) / 10.0; 				// �ܼ�storeAssess�z�LMath��round��k���|�ˤ��J�ܤp���I�� 1 �쪺��

		Optional<Store> storeOp = storeDao.findById(store); 						// �]�wStore��ƫ��A��Optional(storeOp)�s��z�LstoreDao��findById��k���o��store
		storeOp.get().setStoreAssess(storeAssess);									// �ϥ�storeOp��get��k��setStoreAssess��k�]�w�ܼ�storeAssess
		storeDao.save(storeOp.get());												// �ϥ�storeDao��save��k�s�^storeOp��get��k���o���F��
		return food;
	}


	// 2-2.�ק��\�I���
	@Override
	public Food changeInfoforMeals(String store, String meals, String newMeals, int mealsPrice, int mealsAssess) {
		Food_Id food_Id = new Food_Id(store, meals);								// �]�wFood_Id���ܼ�store�Mmeals
		Optional<Food> foodOp = foodDao.findById(food_Id);							// �إ�food�æs���Ʈw�����쪺�\�I��� �]�wFood��ƫ��A��Optional(foodOp)�s��z�LfoodDao��findById��k���o��food_Id

		// ���ۧP�_�n�ק諸�\�I�s���s�b
		if (foodOp.orElse(null) == null) { 											// ��Optional�����e���P�_��null�ɡA�^��null
			return null;
		}
		Food food1 = foodOp.get(); 													// �]�wFood��ƫ��A��food1�s��foodOp��get��k���o���F��
		foodDao.delete(food1); 														// �ϥ�foodDao��delete��k�R��food1���쥻���F��

		if (StringUtils.hasText(newMeals)) { 										// �P�_newMeals�O�_���ȡA��-->��ܻݭn�ק�meals�F�S��-->���ݭק�meals
			food1.setMeals(newMeals); 												// �Y���A�h�N�ȳ]�w�ifood1��newMeals(�s�J�s�ק諸�\�I�W��)
		}

		if (mealsPrice > 0) { 														// �P�_mealsPrice���ȬO�_�j��0�A�j��0-->�s�J�F�Y��0�h��ܤ����ק�
			food1.setMealsPrice(mealsPrice);										// �Y�Ȥj��0�A�h�N�ȳ]�w�ifood1��mealsPrice(�л\�쥻��mealsPrice)
		}

		if (mealsAssess > 0) {														// �P�_mealsAssess���ȬO�_�j��0-->�s�J�F�Y��0�h��ܤ����ק�
			food1.setMealsAssess(mealsAssess);										// �Y�Ȥj��0�A�h�N�ȳ]�w�ifood1��mealsAssess(�л\�쥻��mealsAssess)
			List<Food> foodList = foodDao.findByStore(store);						// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao��findByStore��k���o��store
			double totalAssess = 0;													// �]�w�@�Ӭ�0���\�I�`�����ܼ�
			int count = 0;															// �]�w�@�Ӭ�0���~���ܼ�
			for (Food food : foodList) {											// �]�w�ܼ�food�s��ŦX����store
				totalAssess += food.getMealsAssess();								// �qfood�����o��MealsAssess��itotalAssessu�ð��\�I�������[�`
				count++;															// ��MealsAssess��JtotalAssess�N+1
			}

			double storeAssess = (totalAssess + mealsAssess) / (count + 1);			// �]�w�ܼ�storAssess�s��totalAssess�[�W�s�W��mealsAssess(�Y���ק�����~�[)��count(�Y���ק�����~�[1)�᪺��(�\�I�`����)
			storeAssess = Math.round(storeAssess * 10.0) / 10.0; 					// �ܼ�storeAssess���z�LMath��round��k���|�ˤ��J�ܤp���I�� 1 �쪺��

			Optional<Store> storeOp = storeDao.findById(store);						// �]�wStore��ƫ��A��Optional(storeOp)�s��z�LstoreDao��findById��k���o��store
			Store store1 = storeOp.get();											// �]�wStore��ƫ��A��stroe1�s��storeOp��get��k���o���F��
			store1.setStoreAssess(storeAssess);										// �N�ܼ�storeAssess�]�w�istore1��StoreAssess
			storeDao.save(store1);													// �ϥ�storeDao��save��k�s�^store1
		}

		return foodDao.save(food1);													// �^��food1�����G�æs�^
	}

	// 3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	@Override
	public List<FoodMapRes> findStoreByCity(String city, int num) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();						// �]�w�s��FoodMapRes��ƫ��A��List(resList)
		List<Store> storeList = storeDao.findByCity(city);							// �]�wStore��ƫ��A��List(storeList)�s��z�LstoreDao��findByCity��k���o��city
//		
		if (storeList.isEmpty()) { 													// �ϥ�isEmpty�P�_storeList���O�_���ӫ����A�p�G�S���h�^��null
			return null;
		}

		if (num > storeList.size() || num == 0) {									// �]�w�ܼ�num������ܵ��ơA�p�G�n��ܪ����Ƥj��(�ε���0)storeList������ƪ���
			num = storeList.size(); 												// num�h�|�۰��ର��List���פ@��
		}
		storeList.subList(0, num);													// �ϥ�storeList��subList��k�]�w�n���List�����ƶq
		// ��ƫ��A �ܼƦW�� : ����H
		for (Store store : storeList) {
			List<Food> foodList = foodDao.findByStore(store.getStore());			// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao��findByStore��k���o�ܼ�store����store
			FoodMapRes res = new FoodMapRes();										// �]�w�s��FoodMapRes��ƫ��A��res
			res.setMealsList(foodList); 											// �NfoodList����Ƴ]�w�ires��MealsList
			res.setStore(store);
			resList.add(res); 														// �Nres����Ʀs�JresList��
		}
		return resList;
	}

	// 4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	@Override
	public List<FoodMapRes> findStoreAssess(double storeAssess) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();						// �]�wFoodMapRes��ƫ��A��List(resList)
		List<Store> storeList = storeDao.findByStoreAssessGreaterThanEqual(storeAssess);// �]�wStore��ƫ��A��List�s��z�LstoreDao����k���ostoreAssess
		
		// ��foreach��쩱�a�W�١A�A���o�ө��a���Ҧ��\�I
		for (Store store : storeList) {
			List<Food> foodList = foodDao.findByStore(store.getStore());			// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao��findByStore��k���o�ܼ�store��store
			FoodMapRes res = new FoodMapRes();										// �]�w�s��FoodMapRes��ƫ��A��res
			res.setStore(store);													// �Nstore����Ƴ]�w�ires��Store
			res.setMealsList(foodList);												// �NfoodList����Ƴ]�w�ires��MealsList
			resList.add(res);														// �Nres����Ʀs�JresList
		}
		return resList;
	}

	// 5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	@Override
	public List<FoodMapRes> findStoreAssessAndMealsAssess(double storeAssess, int mealsAssess) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();						// �]�wFoodMapRes��ƫ��A��List(resList)

		List<Store> storeList = storeDao.findByStoreAssessGreaterThanEqual(storeAssess);// �]�wStore��ƫ��A��List(storeList)�s��z�LstoreDao����k���ostoreAssess
		for (Store store : storeList) {
			List<Food> foodList = foodDao											// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao����k���o�ܼ�store��store�MmealsAssess
					.findByStoreAndMealsAssessGreaterThanEqualOrderByMealsAssessDesc(store.getStore(), mealsAssess);
			FoodMapRes res = new FoodMapRes();										// �]�w�s��FoodMapRes��ƫ��A��res
			res.setMealsList(foodList);												// �NfoodList����Ƴ]�w�ires��MealsList
			res.setStore(store);													// �Nstore����Ƴ]�w�ires��Store
			resList.add(res);														// �Nres����Ʀs�JresList
		}
		return resList;																// �^��resList
	}

}