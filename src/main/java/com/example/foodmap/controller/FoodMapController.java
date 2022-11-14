package com.example.foodmap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodmap.constants.FoodMapRtnCode;
import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Store;
import com.example.foodmap.service.ifs.FoodMapService;
import com.example.foodmap.vo.FoodMapReq;
import com.example.foodmap.vo.FoodMapRes;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

	// 1.�g�J���a���
	@PostMapping(value = "/api/setStore")
	public FoodMapRes setStore(@RequestBody FoodMapReq req) {
		//�P�_�����Ω��a��Ʀb�إ߮ɬO�_���Ū���																		//Postman:
		if(!StringUtils.hasText(req.getCity())) {															//"city":"",
			return new FoodMapRes(FoodMapRtnCode.CITY_CANNOT_BE_EMPTY.getMessage()); //�������i����				//"store":""
		} else if(!StringUtils.hasText(req.getStore())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_CANNOT_BE_EMPTY.getMessage());//���a���i����
		}		
		
		// �P�_��ƵL�~�����s�J store
		Store store = foodMapService.setStore(req.getCity(), req.getStore());
		// �s�J��ƮɧP�_���a�O�_�w�s�b
		if (store == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage());	//���a���\�I�w�g�s�b
		}
		
		// ��Ʀs�J���\�æ^�ǰT��: SUCCESSFUL
		FoodMapRes res = new FoodMapRes(store,FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
	
	// 2.�g�J�\�I����
	@PostMapping(value = "/api/setMeals")																
	public FoodMapRes setMeals(@RequestBody FoodMapReq req) {											
																										//Postman:
		 //�P�_�\�I�����O�_�p��1�Τj��5																		//"store":"",
		if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5 ) {										//"meals":"",
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage()); //�s�W�\�I��������		//"meals_price":,
		   }														           							//"meals_assess":
		
		//�P�_�\�I����O�_�p��0
		if(req.getMealsPrice() <= 0 ) {            
			return new FoodMapRes(FoodMapRtnCode.PRICE_CANNOT_BE_LESS_THAN_ZERO.getMessage()); //���椣�i�p��0
		   }
		
		// �P�_��ƿ�J�O�_���T
		FoodMapRes checkres = checkMap(req);
		if (checkres != null) {
			return checkres;
		}
		
		// �P�_��ƵL�~�����s�J food
		Food food = foodMapService.setMeals(req.getStore(), req.getMeals(), req.getMealsPrice(), req.getMealsAssess());
		// �s�J��ƮɧP�_�\�I�O�_�w�s�b
		if (food == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage()); //���a���\�I�w�g�s�b
		} 
		
		// ��Ʀs�J���\�æ^�ǰT��: SUCCESSFUL
		FoodMapRes res = new FoodMapRes(food, FoodMapRtnCode.SUCCESSFUL.getMessage());
			return res;
	}
	
	// �D�n�P�_��H�OPK
	private FoodMapRes checkMap(FoodMapReq req) { 	
		// �P�_�ϥΪ̿�J�����a�W�٬O�_���Ū�
		if (!StringUtils.hasText(req.getStore())) { 					
			return new FoodMapRes(FoodMapRtnCode.STORE_CANNOT_BE_EMPTY.getMessage()); //���a����O�Ū�
		// �P�_�ϥΪ̿�J���\�I�W�٬O�_���Ū�
		} else if (!StringUtils.hasText(req.getMeals())) { 			
			return new FoodMapRes(FoodMapRtnCode.MEALS_CANNOT_BE_EMPTY.getMessage()); //�\�I����O�Ū�
		}
		return null;
	}


	
	// 3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	@PostMapping(value = "/api/findStoreByCity")
	public List<FoodMapRes> findStoreByCity(@RequestBody FoodMapReq req) {
		return foodMapService.findStoreByCity(req.getCity(),req.getNum());
	}

	// 4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	@PostMapping(value = "/api/findByStoreAssessGreaterThanEqual")
	public List<Store> findByStoreAssessGreaterThanEqual(@RequestBody FoodMapReq req) {
		List<Store> store = foodMapService.findStoreAssess(req.getStoreAssess());
		return store;
	}
	
	// 5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	@PostMapping(value = "/api/findStoreAssessAndMealsAssess")
	public FoodMapRes findStoreAssessAndMealsAssess(@RequestBody FoodMapReq req) {
//		if(!StringUtils.hasText(req.getStore())) {
//			return new FoodMapRes(FoodMapRtnCode.FIND_THE_STORE_FAILURE.getMessage());
//		}		
		 FoodMapRes food = foodMapService.findStoreAssessAndMealsAssess(req.getStore(),req.getMealsAssess());
		return food;
	}
	
	
	
	//�ק�ө����
	@PostMapping(value = "/api/changeInfoForStore")
	public FoodMapRes changeInfoForStore(@RequestBody FoodMapReq req) {
		
		//�p�G�r�ꤺ�S���� �h�|�i�J�U�@�� �^�ǥ��ѰT��
		if(!StringUtils.hasText(req.getStore())) {		
			return new FoodMapRes(FoodMapRtnCode.CHANG_STORE_CANNOT_BE_NULL.getMessage());
		}
		
		//�p�G�����P�s���W�����ק�h����������ܨöi�J�U�@��^�ǰT��
		if(!StringUtils.hasText(req.getCity()) && !StringUtils.hasText(req.getNewStore())) {	
			return new FoodMapRes(FoodMapRtnCode.NOTHING_TO_UPDATE.getMessage());
		}
		Store store = foodMapService.changeInfoForStore(req.getCity(), req.getStore(), req.getNewStore());
		if(store == null) {
			return new FoodMapRes(FoodMapRtnCode.FIND_THE_STORE_FAILURE.getMessage());
		}
		
		FoodMapRes res = new FoodMapRes(store,"Change successful!");
		return res ;
	}
	
	
	//�ק��\�I���
	@PostMapping(value = "/api/changeInfoforMeals")
	public FoodMapRes changeInfoforMeals(@RequestBody FoodMapReq req) {
		
		//�p�G�r�ꤺ�S���� �h�|�i�J�U�@�� �^�ǥ��ѰT��
		if(!StringUtils.hasText(req.getMeals()) || !StringUtils.hasText(req.getNewMeals())) {		
			return new FoodMapRes(FoodMapRtnCode.CHANG_MEALS_CANNOT_BE_NULL.getMessage());
		}
		
		//�P�_�\�I�����n���n�ק�(0=���ק�F1~5=�n�ק�B�����u�श��1~5����)
		if(req.getMealsAssess() == 0 ) {
			return null;
		}else 
			if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5) {//�P�_�n��s���\�I�����O�_�p��1�Τj��5
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage());//��s�\�I��������	
		}								           							
				
		//�P�_�\�I����O�_�p��0
		if(req.getMealsPrice() == 0 ) {            
			return null;
		}else 
			if(req.getMealsPrice() < 0 ) {            
			return new FoodMapRes(FoodMapRtnCode.PRICE_CANNOT_BE_LESS_THAN_ZERO.getMessage()); 
		}
		
		Food food = foodMapService.changeInfoforMeals(req.getCity(), req.getMeals(), req.getNewMeals(), req.getMealsPrice(), req.getMealsAssess());
		
		FoodMapRes res = new FoodMapRes(food,"Change successful!");
		return res;		
	}
	
	
	
	
}
