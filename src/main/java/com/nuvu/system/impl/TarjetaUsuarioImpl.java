package com.nuvu.system.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nuvu.system.dao.ITarjetaUsuarioDao;
import com.nuvu.system.entity.TarjetaUsuario;
import com.nuvu.system.entity.TipoFranquicia;
import com.nuvu.system.entity.Usuario;
import com.nuvu.system.service.ITarjetaUsuario;

@Service
public class TarjetaUsuarioImpl extends ServiceGenericImpl<TarjetaUsuario, Long> implements ITarjetaUsuario{
	
	@Autowired
	private ITarjetaUsuarioDao tarjetaUsuarioDao;
	
	@Autowired
	private MessageSource mensajes;

	@Override
	public JpaRepository<TarjetaUsuario, Long> getDao() {		
		return tarjetaUsuarioDao;
	}

	@Override
	public List<TarjetaUsuario> findByCriteria(Long id, BigInteger numeroTarjeta, String nombreTarjeta, Date fechaCaducidad,
			Long numeroCcv, boolean estadoTarjeta, TipoFranquicia tipoFranquicia, Usuario usuario) {
		
		return tarjetaUsuarioDao.findAll(new Specification<TarjetaUsuario>() {
						
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TarjetaUsuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				if(numeroTarjeta != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("numeroTarjeta"), numeroTarjeta)));
				}
				
				if(nombreTarjeta != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("nombreTarjeta"), nombreTarjeta)));
				}
				
				if(fechaCaducidad != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("fechaCaducidad"), fechaCaducidad)));
				}
				
				if(numeroCcv != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("numeroCcv"), numeroCcv)));
				}
				
				if(tipoFranquicia != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipoFranquicia"), tipoFranquicia)));
				}
				
				if(usuario != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("usuario"), usuario)));
				}
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("estadoTarjeta"), estadoTarjeta)));
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}
	
	public String findByNumeroTarjetaAndNumeroCcv(BigInteger numeroTarjeta, Long numeroCcv){
		Optional<TarjetaUsuario> objectUsuarioTarjeta = tarjetaUsuarioDao.findByNumeroTarjetaAndNumeroCcv(numeroTarjeta, numeroCcv);
		if(objectUsuarioTarjeta.isPresent()) {
			return mensajes.getMessage("text.UsuarioTarjeta.existente", new String[] { objectUsuarioTarjeta.get().getNumeroTarjeta().toString() },
					LocaleContextHolder.getLocale());
		}
		return null;
	}
	
	

}
