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
import com.example.foodmap.vo.FoodMapResponseList;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

	// 1.�g�J���a���
	@PostMapping(value = "/api/setStore")
	public FoodMapRes setStore(@RequestBody FoodMapReq req) {
		//�P�_�����Ω��a��Ʀb�إ߮ɬO�_���Ū���																		
		if(!StringUtils.hasText(req.getCity())) {															
			return new FoodMapRes(FoodMapRtnCode.CITY_CANNOT_BE_EMPTY.getMessage()); //�Yreq.getCity���ŭȡA�^�ǰT��:�]�w���������i����				
		} else if(!StringUtils.hasText(req.getStore())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_CANNOT_BE_EMPTY.getMessage());//�Yreq.getStore���ŭȡA�^�ǰT��:�]�w�����a���i����
		}		
		
		// �P�_��ƵL�~�����s�J�ܼ� store
		Store store = foodMapService.setStore(req.getCity(), req.getStore());
		// �s�J��ƮɧP�_���a�O�_�w�s�b
		if (store == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage());	//�Y��Ƥw�s�b�A�^�ǰT��:�]�w�����a���\�I�w�g�s�b
		}
		
		// ��Ʀs�J���\�æ^�ǰT��: SUCCESSFUL
		FoodMapRes res = new FoodMapRes(store,FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
	
	// 1-1.�ק�ө����
		@PostMapping(value = "/api/changeInfoForStore")
		public FoodMapRes changeInfoForStore(@RequestBody FoodMapReq req) {
			
			//�p�G�r�ꤺ�S���� �h�|�i�J�U�@�� �^�ǥ��ѰT��
			if(!StringUtils.hasText(req.getStore())) {		
				return new FoodMapRes(FoodMapRtnCode.CHANG_STORE_CANNOT_BE_NULL.getMessage());
			}
			
			//�p�G�����P�s���W�����ק�h����������ܨöi�J�U�@��^�ǰT��
			if(!StringUtils.hasText(req.getCity()) && !StringUtils.hasText(req.getNewStore())) {	
				return new FoodMapRes(FoodMapRtnCode.NOTHING_HAPPENED.getMessage());
			}
			
			Store store = foodMapService.changeInfoForStore(req.getCity(), req.getStore(), req.getNewStore());
			if(store == null) {
				return new FoodMapRes(FoodMapRtnCode.FIND_THE_STORE_FAILURE.getMessage());
			}
			
			FoodMapRes res = new FoodMapRes(store,"Change successful!");
			return res ;
		}
		
	
	// 2.�g�J�\�I����
	@PostMapping(value = "/api/setMeals")																
	public FoodMapRes setMeals(@RequestBody FoodMapReq req) {											
																										
		//�P�_�g�J�\�I�����a�O�_�s�b
		if(!StringUtils.hasText(req.getCity())) {															
			return new FoodMapRes (FoodMapRtnCode.THE_STORE_DOES_NOT_EXIST.getMessage());
		}
												
		 //�P�_�\�I�����O�_�p��1�Τj��5																		
		if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5 ) {										
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage()); //�s�W�\�I��������		
		   }														           							
		
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
	
	// 2-2.�ק��\�I���
	@PostMapping(value = "/api/changeInfoforMeals")
	public FoodMapRes changeInfoforMeals(@RequestBody FoodMapReq req) {
				
		//�p�G�r�ꤺ�S���� �h�|�i�J�U�@�� �^�ǥ��ѰT��
		if(!StringUtils.hasText(req.getMeals()) || !StringUtils.hasText(req.getStore())) {		
			return new FoodMapRes(FoodMapRtnCode.CHANG_MEALS_CANNOT_BE_NULL.getMessage());
		}
		
		//�P�_�\�I�����n���n�ק�(0=���ק�F1~5=�n�ק�B�����u�श��1~5����)
		if(req.getMealsAssess() < 0 || req.getMealsAssess() > 5) {//�P�_�n��s���\�I�����O�_�p��1�Τj��5
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage());//��s�\�I��������	
		}								           							
				
		//�P�_�\�I����O�_�p��0 
		if(req.getMealsPrice() < 0 ) {            
			return new FoodMapRes(FoodMapRtnCode.PRICE_CANNOT_BE_LESS_THAN_ZERO.getMessage()); 
		}
		
		Food food = foodMapService.changeInfoforMeals(req.getStore(), req.getMeals(), req.getNewMeals(), req.getMealsPrice(), req.getMealsAssess());
		if(food == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage());
		}
		
		FoodMapRes res = new FoodMapRes(food,"Change successful!");
		return res;		
	}
	
	
	// 3.�j�M�S�w������X�Ҧ����a�A�i�H������ܵ���(0��null�����ܥ������a)
	@PostMapping(value = "/api/findStoreByCity")
	public FoodMapResponseList findStoreByCity(@RequestBody FoodMapReq req) {
		
		//���b 1.city���ର�ŭ�
		if(!StringUtils.hasText(req.getCity())) {															
			return new FoodMapResponseList(FoodMapRtnCode.CITY_CANNOT_BE_EMPTY.getMessage());
		}
		FoodMapResponseList classResList = new FoodMapResponseList();
		List<FoodMapRes> resList = foodMapService.findStoreByCity(req.getCity(), req.getNum());
		
		//���b 2.�P�_�����O�_�s�b�A�Y�^null�h�^�ǧ䤣��ӫ������T��
		if(resList == null) {		
			return new FoodMapResponseList(FoodMapRtnCode.CANNOT_FIND_THE_CITY.getMessage());
		}
		
		classResList.setResList(resList);
		classResList.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		
		return classResList;
	}

	// 4.�j�M���a�����X��(�t)�H�W�è̵������C�Ƨ�
	@PostMapping(value = "/api/findByStoreAssessGreaterThanEqual")
	public FoodMapRes findByStoreAssessGreaterThanEqual(@RequestBody FoodMapReq req) {
		
		//�]�m�P�_���a�������]�w�b1~5���������b
		if(req.getStoreAssess() < 1 || req.getStoreAssess() > 5) {									//�P�_�n�d�ߪ����a�����O�_�p��0�Τj��5
			return new FoodMapRes(FoodMapRtnCode.SEARCH_ASSESS_ONLY_1_TO_5.getMessage());			//�]���j�M�����a�������b�d��	
		}
		FoodMapRes res = new FoodMapRes();	
		List<FoodMapRes> assessList = foodMapService.findStoreAssess(req.getStoreAssess());
		res.setAssessList(assessList);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
	
	// 5.�j�M���a�����X��(�t)�H�W�P�\�I�����X��(�t)�H�W �B��ܩ��a�����P�\�I���� �P���C�Ƨ�
	@PostMapping(value = "/api/findStoreAssessAndMealsAssess")
	public FoodMapRes findStoreAssessAndMealsAssess(@RequestBody FoodMapReq req) {
		
		//�]�m�P�_���a�������]�w�b1~5���������b
		if(req.getStoreAssess() < 1 || req.getStoreAssess() > 5) {									//�P�_�n�d�ߪ����a�����O�_�p��0�Τj��5
			return new FoodMapRes(FoodMapRtnCode.SEARCH_ASSESS_ONLY_1_TO_5.getMessage());			//�]���j�M�����a�������b�d��	
		}
		
		//�]�m�P�_�\�I�������]�w�b1~5���������b
		if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5) {									//�P�_�n�d�ߪ��\�I�����O�_�p��0�Τj��5
			return new FoodMapRes(FoodMapRtnCode.SEARCH_ASSESS_ONLY_1_TO_5.getMessage());			//�]���j�M���\�I�������b�d��	
		}
		
		FoodMapRes res = new FoodMapRes();	
		List<FoodMapRes> assess = foodMapService.findStoreAssessAndMealsAssess(req.getStoreAssess(),req.getMealsAssess());
		res.setAssessList(assess);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
}
