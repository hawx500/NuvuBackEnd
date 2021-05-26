package com.nuvu.system.service;

import java.io.Serializable;

import com.nuvu.system.commons.InputRequest;

public interface IServiceGeneric<T, ID extends Serializable> {
	
	T save(InputRequest<T> request);
	
	T update(T entity);
	
	T findById(ID id);

}
