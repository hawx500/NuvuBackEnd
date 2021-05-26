package com.nuvu.system.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.nuvu.system.entity.TarjetaUsuario;
import com.nuvu.system.entity.TipoFranquicia;
import com.nuvu.system.entity.Usuario;

public interface ITarjetaUsuario extends IServiceGeneric<TarjetaUsuario, Long>{
	
	public List<TarjetaUsuario> findByCriteria(Long id, BigInteger numeroTarjeta, String nombreTarjeta, Date fechaCaducidad, Long numeroCcv, boolean estadoTarjeta, TipoFranquicia tipoFranquicia, Usuario usuario);
	
	public String findByNumeroTarjetaAndNumeroCcv(BigInteger numeroTarjeta, Long numeroCcv);

}
