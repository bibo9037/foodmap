package com.example.foodmap.constants;

public enum FoodMapRtnCode {
	SUCCESSFUL("200", "Successful !!"),
	CITY_CANNOT_BE_EMPTY("400","City cannot be empty!!"),
	STORE_CANNOT_BE_EMPTY("400","Store cannot be empty!!"),
	STORE_OR_MEALS_ALREADY_EXISTED("400","Store or meals already existed!!"),
	CHANG_STORE_CANNOT_BE_NULL("400","Change store cannot be null!!"),
	NOTHING_HAPPENED("400","Nothing happened!"),
	FIND_THE_STORE_FAILURE("400","Find the store fail!!"),
	THE_STORE_DOES_NOT_EXIST("400","The store does not exist!!"),
	ADD_ASSESS_FAILURE("400","Add assess fail!!"),
	PRICE_CANNOT_BE_LESS_THAN_ZERO("400","Price cannot be less than zero!!"),
	STORE_OR_MEALS_NOT_EXISTED("400","Store or meals not existed!!"),
	MEALS_CANNOT_BE_EMPTY("400","Meals cannot be empty!!"),
	CHANG_MEALS_CANNOT_BE_NULL("400","Change meals cannot be null!!"),
	CANNOT_FIND_THE_CITY("400","Cannot find the city!!"),
	SEARCH_ASSESS_ONLY_1_TO_5("400","Search assess only 1 to 5!!"),
	;


	private String code;

	private String message;

	private FoodMapRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
