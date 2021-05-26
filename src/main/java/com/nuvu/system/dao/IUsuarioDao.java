package com.nuvu.system.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nuvu.system.entity.TipoIdentificacion;
import com.nuvu.system.entity.Usuario;

@Repository
@Transactional
public interface IUsuarioDao extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
	
	public Optional<Usuario> findByidentificacionUsuarioAndTipoIdentificacion(String identificacionUsuario, TipoIdentificacion tipoIdentificacion);

}
