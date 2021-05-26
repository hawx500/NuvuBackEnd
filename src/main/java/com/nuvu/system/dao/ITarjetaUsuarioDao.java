package com.nuvu.system.dao;

import java.math.BigInteger;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nuvu.system.entity.TarjetaUsuario;

@Repository
@Transactional
public interface ITarjetaUsuarioDao extends JpaRepository<TarjetaUsuario, Long>, JpaSpecificationExecutor<TarjetaUsuario>{
	
	public Optional<TarjetaUsuario> findByNumeroTarjetaAndNumeroCcv(BigInteger numeroTarjeta, Long numeroCcv);
		

}
