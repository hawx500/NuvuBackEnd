package com.nuvu.system.impl;

import java.util.ArrayList;
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

import com.nuvu.system.dao.IUsuarioDao;
import com.nuvu.system.entity.TipoIdentificacion;
import com.nuvu.system.entity.Usuario;
import com.nuvu.system.service.IUsuario;

@Service
public class UsuarioImpl extends ServiceGenericImpl<Usuario, Long> implements IUsuario{	
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private MessageSource mensajes;

	@Override
	public JpaRepository<Usuario, Long> getDao() {		
		return usuarioDao;
	}
	
	@Override
	public List<Usuario> findByCriteria(String nombreUsuario, String apellidoUsuario, String identificacionUsuario, TipoIdentificacion tipoIdentificacion) {
		
		return usuarioDao.findAll(new Specification<Usuario>() {
						
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				if(nombreUsuario != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("nombreUsuario"), nombreUsuario)));
				}
				
				if(apellidoUsuario != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("apellidoUsuario"), apellidoUsuario)));
				}
				
				if(identificacionUsuario != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identificacionUsuario"), identificacionUsuario)));
				}
				
				if(tipoIdentificacion != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipoIdentificacion"), tipoIdentificacion)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

	@Override
	public String findByidentificacionUsuarioAndTipoIdentificacion(String identificacionUsuario, TipoIdentificacion tipoIdentificacion) {
		Optional<Usuario> objectUsuario = usuarioDao.findByidentificacionUsuarioAndTipoIdentificacion(identificacionUsuario, tipoIdentificacion);
		if(objectUsuario.isPresent()) {
			return mensajes.getMessage("text.Usuario.existente", new String[] { objectUsuario.get().getIdentificacionUsuario().toString() },
					LocaleContextHolder.getLocale());
		}
		return null;
	}

}
