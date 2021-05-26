package com.nuvu.system.impl;

import java.io.Serializable;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.stereotype.Service;

import com.nuvu.system.commons.InputRequest;
import com.nuvu.system.service.IServiceGeneric;

@Service
public abstract class ServiceGenericImpl<T, ID extends Serializable> implements IServiceGeneric<T, ID> {

	public abstract JpaRepository<T, ID> getDao();

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public T save(InputRequest<T> request) {
		T requestObj = null;

		try {
			requestObj = getDao().save(request.getEntidad());
		} catch (IllegalArgumentException e) {
			log.info("Error almacenando informacion " + e.getMessage() + " " + e.getCause());
			return requestObj;
		} catch (NonTransientDataAccessException e) {
			log.info("Error almacenando informacion " + e.getMessage() + " " + e.getCause() + " "
					+ e.getClass().getSimpleName());
			return requestObj;
		} catch (RecoverableDataAccessException e) {
			log.info("Error almacenando informacion " + e.getMessage() + " " + e.getCause());
			return requestObj;
		} catch (ScriptException e) {
			log.info("Error almacenando informacion " + e.getMessage() + " " + e.getCause());
			return requestObj;
		} catch (TransientDataAccessException e) {
			log.info("Error almacenando informacion " + e.getMessage() + " " + e.getCause());
			return requestObj;
		} catch (DataAccessException e) {
			log.info("Error almacenando informacion " + e.getMessage() + " " + e.getCause());
			return requestObj;
		}

		return requestObj;
	}

	public T update(T entity) {
		T objUpdate = null;		

		try {
			objUpdate = getDao().saveAndFlush(entity);

		} catch (IllegalArgumentException e) {
			log.info("Error actualizando informacion " + e.getMessage() + " " + e.getCause());
			return objUpdate;

		} catch (NonTransientDataAccessException e) {
			log.info("Error actualizando informacion " + e.getMessage() + " " + e.getCause());
			return objUpdate;
		} catch (RecoverableDataAccessException e) {
			log.info("Error actualizando informacion " + e.getMessage() + " " + e.getCause());
			return objUpdate;
		} catch (ScriptException e) {
			log.info("Error actualizando informacion " + e.getMessage() + " " + e.getCause());
			return objUpdate;
		} catch (TransientDataAccessException e) {
			log.info("Error actualizando informacion " + e.getMessage() + " " + e.getCause());
			return objUpdate;
		} catch (DataAccessException e) {
			log.info("Error actualizando informacion " + e.getMessage() + " " + e.getCause());
			return objUpdate;
		}

		return objUpdate;

	}
	
	public T findById(ID id) {
		Optional<T> obj = getDao().findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

}
