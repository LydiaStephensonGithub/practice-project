package com.example.ducks.service;

import java.util.List;

public interface ServiceMethods<T> {

	//create 
	T create(T t);
	
	//read all
	List<T> readAll();
	
	//read by id
	T readById(long id);
	
	//update 
	T update(long id, T t);
	
	//delete
	boolean delete(long id);
	
}
