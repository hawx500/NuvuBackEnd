package com.nuvu.system.service;

import java.util.List;

import com.nuvu.system.entity.TipoIdentificacion;
import com.nuvu.system.entity.Usuario;

public interface IUsuario extends IServiceGeneric<Usuario, Long>{
	
	public List<Usuario> findByCriteria(String nombreUsuario, String apellidoUsuario, String identificacionUsuario, TipoIdentificacion tipoIdentificacion);
	
	public String findByidentificacionUsuarioAndTipoIdentificacion(String identificacionUsuario, TipoIdentificacion tipoIdentificacion);

}
