package com.example.foodmap.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.FoodId;
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
	public Store setStore(String city, String storeName) {
		if (storeDao.existsById(storeName)) { 													// �ϥ�existsById �P�_��Ʈw���O�_�w�s�b�ۦP��store
			return null; 																	// �Y�S���h�^��null
		}
		Store store = new Store(city, storeName);  												// �]�wstore���ܼ�city�Mstore
		return storeDao.save(store); 															// �ϥ�storeDao��save��k�s�^sto �æ^��
	}

	// 1-1.�ק�ө����
	@Override
	public Store changeInfoForStore(String city, String storeName, String newStoreName) {
		Optional<Store> storeOp = storeDao.findById(storeName);									// �]�wStore��ƫ��A��Optional(storeOp)�s��z�LstoreDao��findById��k���o��store
		if (!storeOp.isPresent()) { 														// �ϥ�isPresent�P�_storeOp�O�_������
			return null;  																	// �Y���ūh�^��null
		}
		Store store = storeOp.get();														// �]�w�ܼ�store1�s��z�LstoreOp��get��k���X���F��
		storeDao.delete(store); 															// �ϥ�storeDao����delete��k�R��store1�������

		if (StringUtils.hasText(city)) {  													// �P�_�ܼ�city�O�_����
			store.setCity(city); 													 		// �Y���A�h�N�ȳ]�w�istore1��city
		}

		if (StringUtils.hasText(newStoreName)) {  												// �P�_�ܼ�newStore�O�_����
			store.setStoreName(newStoreName); 													// �Y���A�h�N�ȳ]�w�istore1��newStore
			List<Food> foodList = foodDao.findByStoreName(storeName);							// �]�wFood�ꫬ�A��List(foodList)�s��z�LfoodDao��findByStore�����X�ۦP���W��store
			foodDao.deleteAll(foodList);													// �Y�n�л\���W�A�h�NfoodList���©��W�R��
			
			for (Food food1 : foodList) {  													// �]�wFood��ƫ��A���ܼ�food1�A���PfoodList���ۦP���W��store�é�Jfood1��
				food1.setStoreName(newStoreName);  												// �]�w�s�����W���ܼ�newStore
			}
			foodDao.saveAll(foodList);														// �N�Ҧ��s�ק諸��Ʀs�^foodList
		}
		storeDao.save(store);  															// �ϥ�storeDao��save��k�s�^store1
		return store;  																	// �^�ǵ��Gstore1
	}

	// 2.�g�J�\�I��T
	@Override
	public Food setMeals(String storeName, String mealsName, int mealsPrice, int mealsAssess) {
		FoodId foodId = new FoodId(storeName, mealsName); 										// �]�wFood_Id���ܼ�store�Mmeals

//		// �p�G�䤣��store�N�����~�򩹤U��
//		if (store.isEmpty()) { 																// �ϥ�isEmpty �P�_store�����S�����a
//			return null;  																	// �Y�S���h�^��null
//		}

		if (foodDao.existsById(foodId)) {  												// �P�_�O�_�w���ۦP��store�άOmeals
			return null;  																	// �Y�S���h�^��null
		}

		Food food = new Food(storeName, mealsName, mealsPrice, mealsAssess);  						// �s�W�\�I�����ɭ� ���a���������
		foodDao.save(food);  																// �z�LfoodDao��save��k�s�^food
		List<Food> foodList = foodDao.findByStoreName(storeName);  								// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao��findByStore��k���o��store

		double totalAssess = 0;  															// �]�w�@�Ӭ�0���\�I�`�����ܼ�
		int count = 0;  																	// �]�w�@�Ӭ�0���~���ܼ�
		for (Food food1 : foodList) { 														// �]�w�ܼ�food1�s��ŦX����store
			totalAssess += food1.getMealsAssess();  										// �qfood1�����o��MealsAssess��itotalAssess�ð��\�I�������[�`
			count++;  																		// ��MealsAssess��JtotalAssess�N+1
		}

		double storeAssess = totalAssess / count; 											// �]�w�ܼ�storAssess�s��totalAssess��count�᪺��(�\�I�`����)
		storeAssess = Math.round(totalAssess / count * 10.0) / 10.0;  						// �ܼ�storeAssess�z�LMath��round��k���|�ˤ��J�ܤp���I�� 1 �쪺��

		Optional<Store> storeOp = storeDao.findById(storeName);  								// �]�wStore��ƫ��A��Optional(storeOp)�s��z�LstoreDao��findById��k���o��store
		storeOp.get().setStoreAssess(storeAssess);  										// �ϥ�storeOp��get��k��setStoreAssess��k�]�w�ܼ�storeAssess
		storeDao.save(storeOp.get());  														// �ϥ�storeDao��save��k�s�^storeOp��get��k���o���F��
		return food;
	}

	// 2-2.�ק��\�I���
	@Override
	public Food changeInfoForMeals(String storeName, String mealsName, String newMealsName, int mealsPrice, int mealsAssess) {
		FoodId food_Id = new FoodId(storeName, mealsName);  										// �]�wFood_Id���ܼ�store�Mmeals
		Optional<Food> foodOp = foodDao.findById(food_Id);  								// �]�wFood��ƫ��A��Optional(foodOp)�s��z�LfoodDao��findById��k���o��food_Id

		// ���ۧP�_�n�ק諸�\�I�s���s�b
		if (!foodOp.isPresent()) {  												// ��Optional�����e���P�_��null�ɡA�^��null
			return null;
		}

		Food food1 = foodOp.get();  														// �]�wFood��ƫ��A��food1�s��foodOp��get��k���o���F��
		foodDao.delete(food1);  															// �ϥ�foodDao��delete��k�R��food1���쥻���F��

		if (StringUtils.hasText(newMealsName)) { 												// �P�_newMeals�O�_���ȡA��-->��ܻݭn�ק�meals�F�S��-->���ݭק�meals
			food1.setMealsName(newMealsName); 													// �Y���A�h�N�ȳ]�w�ifood1��newMeals(�s�J�s�ק諸�\�I�W��)
		}

		if (mealsPrice > 0) {																// �P�_mealsPrice���ȬO�_�j��0�A�j��0-->�s�J�F�Y��0�h��ܤ����ק�
			food1.setMealsPrice(mealsPrice);												// �Y�Ȥj��0�A�h�N�ȳ]�w�ifood1��mealsPrice(�л\�쥻��mealsPrice)
		}

		if (mealsAssess > 0) {																// �P�_mealsAssess���ȬO�_�j��0-->�s�J�F�Y��0�h��ܤ����ק�
			food1.setMealsAssess(mealsAssess); 												// �Y�Ȥj��0�A�h�N�ȳ]�w�ifood1��mealsAssess(�л\�쥻��mealsAssess)
			List<Food> foodList = foodDao.findByStoreName(storeName); 						// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao��findByStore��k���o��store
			double totalAssess = 0;															// �]�w�@�Ӭ�0���\�I�`�����ܼ�
			int count = 0; 																	// �]�w�@�Ӭ�0���~���ܼ�
			for (Food food : foodList) { 													// �]�w�ܼ�food�s��ŦX����store
				totalAssess += food.getMealsAssess();										// �qfood�����o��MealsAssess��itotalAssessu�ð��\�I�������[�`
				count++; 																	// ��MealsAssess��JtotalAssess�N+1
			}

			double storeAssess = (totalAssess + mealsAssess) / (count + 1); 				// �]�w�ܼ�storAssess�s��totalAssess�[�W�s�W��mealsAssess(�Y���ק�����~�[)��count(�Y���ק�����~�[1)�᪺��(�\�I�`����)
			storeAssess = Math.round(storeAssess * 10.0) / 10.0; 							// �ܼ�storeAssess���z�LMath��round��k���|�ˤ��J�ܤp���I�� 1 �쪺��

			Optional<Store> storeOp = storeDao.findById(storeName); 						// �]�wStore��ƫ��A��Optional(storeOp)�s��z�LstoreDao��findById��k���o��store
			Store store1 = storeOp.get(); 													// �]�wStore��ƫ��A��stroe1�s��storeOp��get��k���o���F��
			store1.setStoreAssess(storeAssess); 											// �N�ܼ�storeAssess�]�w�istore1��StoreAssess
			storeDao.save(store1); 															// �ϥ�storeDao��save��k�s�^store1
		}
		return foodDao.save(food1); 														// �^��food1�����G�æs�^
	}

	// 3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	@Override
	public List<FoodMapRes> findStoreByCity(String city, int num) {
		List<FoodMapRes> resList = new ArrayList<>(); 							// �]�w�s��FoodMapRes��ƫ��A��List(resList)
		List<Store> storeList = storeDao.findByCity(city); 									// �]�wStore��ƫ��A��List(storeList)�s��z�LstoreDao��findByCity��k���o��city
//		
		if (storeList.isEmpty()) { 															// �ϥ�isEmpty�P�_storeList���O�_���ӫ����A�p�G�S���h�^��null
			return null;
		}

		if (num > storeList.size() || num == 0) { 											// �]�w�ܼ�num������ܵ��ơA�p�G�n��ܪ����Ƥj��(�ε���0)storeList������ƪ���
			num = storeList.size(); 														// num�h�|�۰��ର��List���פ@��
		}

		storeList = storeList.subList(0, num); 												// �ϥ�storeList��subList��k�]�w�n���List�����ƶq
		
		List<String> storeNameList = putStoreList(storeList);
		List<Food> foodList = foodDao.findByStoreNameIn(storeNameList);
		
		resList = putInfoToResList(storeList,foodList);
		
		return resList;
	}
	
	
	// 4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	@Override
	public List<FoodMapRes> findStoreAssess(double storeAssess) {
		List<FoodMapRes> resList = new ArrayList<>(); 										// �]�wFoodMapRes��ƫ��A��List(resList)
		List<Store> storeList = storeDao.findByStoreAssessGreaterThanEqualOrderByStoreAssessDesc(storeAssess);// �]�wStore��ƫ��A��List�s��z�LstoreDao����k���ostoreAssess
		List<String> storeNameList = putStoreList(storeList);
		List<Food> foodList = foodDao.findByStoreNameIn(storeNameList);

		resList = putInfoToResList(storeList,foodList);
		return resList;
	}

	
	// 5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	@Override
	public List<FoodMapRes> findStoreAssessAndMealsAssess(double storeAssess, int mealsAssess) {
		List<FoodMapRes> resList = new ArrayList<>(); 							// �]�wFoodMapRes��ƫ��A��List(resList)
		List<Store> storeList = storeDao.findByStoreAssessGreaterThanEqualOrderByStoreAssessDesc(storeAssess);// �]�wStore��ƫ��A��List(storeList)�s��z�LstoreDao����k���ostoreAssess
		List<String> storeNameList = putStoreList(storeList);
		List<Food> foodList = foodDao														// �]�wFood��ƫ��A��List(foodList)�s��z�LfoodDao����k���o�ܼ�store��store�MmealsAssess
				.findByStoreNameInAndMealsAssessGreaterThanEqualOrderByMealsAssessDesc(storeNameList, mealsAssess);

		resList = putInfoToResList(storeList,foodList);
		return resList; 																	// �^��resList
	}
	

//��k�p����
	private List<FoodMapRes> putInfoToResList(List<Store> storeList, List<Food> foodList) {
		List<FoodMapRes> resList = new ArrayList<>(); 
		for (Store store : storeList) { 													// ��foreach��쩱�a�W�١A�A���o�ө��a���Ҧ��\�I
			FoodMapRes res = new FoodMapRes(); 												// �]�w�s��FoodMapRes��ƫ��A��res
			List<Food> foodList1 = new ArrayList<>(); 										// �]�w�s��Food��ƫ��A��List(foodList1)
			for (Food food : foodList) {
				if (store.getStoreName().equalsIgnoreCase(food.getStoreName())) {			// ���store��storeName�Mfood��storeName�O�_�@��
					foodList1.add(food); 													// �Y�@�˫h�Nfood��JfoodList1
				}
			}
			res.setMealsList(foodList1);													// �NfoodList1����Ƴ]�w�ires��MealsList
			res.setStore(store);															// �Nstore����Ƴ]�w�ires��Store
			resList.add(res);																// �Nres����Ʀs�JresList
		}									
		return resList;
	}

	private List<String> putStoreList(List<Store> storeList){
			List<String> storeNameList = new ArrayList<>();
			for (Store store : storeList) {
				storeNameList.add(store.getStoreName());
			}
			return storeNameList;
	}
	
}