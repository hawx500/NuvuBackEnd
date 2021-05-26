package com.nuvu.system.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuvu.system.commons.InputRequest;
import com.nuvu.system.commons.RespuestaServicioGeneric;
import com.nuvu.system.entity.TarjetaUsuario;
import com.nuvu.system.service.ITarjetaUsuario;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("tarjetaUsuario/operaciones")
@RestController
public class UsuarioTarjetaController {
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private ITarjetaUsuario tarjetaUsuarioService;
	
	@Autowired
	private MessageSource mensajes;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody InputRequest<TarjetaUsuario> request) {
		TarjetaUsuario tarjetaUsuario = null;
		
		String validacionTarjetaUsuarioExistente = tarjetaUsuarioService.findByNumeroTarjetaAndNumeroCcv(request.getEntidad().getNumeroTarjeta(), request.getEntidad().getNumeroCcv());
		
		if (validacionTarjetaUsuarioExistente != null) {
			return new ResponseEntity<RespuestaServicioGeneric>(
					new RespuestaServicioGeneric(1, validacionTarjetaUsuarioExistente), HttpStatus.OK);
		}
		
		try {
			tarjetaUsuario = tarjetaUsuarioService.save(request);
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(1,
					mensajes.getMessage("text.Generico.error.insert.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,
				mensajes.getMessage("text.Generico.insert.success",
						new String[] { tarjetaUsuario.getId().toString() }, LocaleContextHolder.getLocale()),
				tarjetaUsuario), HttpStatus.CREATED);		
	}
	
	@GetMapping("/find")
	public ResponseEntity<?> find(@RequestParam(value = "datosFiltro") String datosFiltro) throws IOException {
		List<TarjetaUsuario> listaTarjetaUsuariosActivos = new ArrayList<TarjetaUsuario>(1);
		List<TarjetaUsuario> listaTarjetaUsuariosInactivos = new ArrayList<TarjetaUsuario>(1);
		List<TarjetaUsuario> listaTarjetaUsuariosConsolidado = new ArrayList<TarjetaUsuario>(1);
		try {
			final TarjetaUsuario usuarioTarjetaFiltro = new ObjectMapper().setDateFormat(simpleDateFormat).readValue(datosFiltro,
					TarjetaUsuario.class);
			
			listaTarjetaUsuariosActivos = tarjetaUsuarioService.findByCriteria(usuarioTarjetaFiltro.getId(),
					usuarioTarjetaFiltro.getNumeroTarjeta(),
					usuarioTarjetaFiltro.getNombreTarjeta(),
					usuarioTarjetaFiltro.getFechaCaducidad(),
					usuarioTarjetaFiltro.getNumeroCcv(),
					true,
					usuarioTarjetaFiltro.getTipoFranquicia(),
					usuarioTarjetaFiltro.getUsuario());
			
			listaTarjetaUsuariosInactivos = tarjetaUsuarioService.findByCriteria(usuarioTarjetaFiltro.getId(),
					usuarioTarjetaFiltro.getNumeroTarjeta(),
					usuarioTarjetaFiltro.getNombreTarjeta(),
					usuarioTarjetaFiltro.getFechaCaducidad(),
					usuarioTarjetaFiltro.getNumeroCcv(),
					false,
					usuarioTarjetaFiltro.getTipoFranquicia(),
					usuarioTarjetaFiltro.getUsuario());
			
			if(listaTarjetaUsuariosActivos.size() > 0) {
				for(int i=0;i<listaTarjetaUsuariosActivos.size();i++) {
					listaTarjetaUsuariosConsolidado.add(listaTarjetaUsuariosActivos.get(i));
				}
			}
			
			if(listaTarjetaUsuariosInactivos.size() > 0) {
				for(int j=0;j<listaTarjetaUsuariosInactivos.size();j++) {
					listaTarjetaUsuariosConsolidado.add(listaTarjetaUsuariosInactivos.get(j));
				}
			}
			
			if (listaTarjetaUsuariosActivos.isEmpty()) {
				return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(1, mensajes
						.getMessage("text.Generico.busqueda.sin.registros", null, LocaleContextHolder.getLocale())),
						HttpStatus.ACCEPTED);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(
					new RespuestaServicioGeneric(1,
							mensajes.getMessage("text.Generico.busqueda.error", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,
				mensajes.getMessage("text.Generico.busqueda.success", null, LocaleContextHolder.getLocale()),
				listaTarjetaUsuariosConsolidado), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody InputRequest<TarjetaUsuario> request, @PathVariable Long id) {
		TarjetaUsuario tarjetaUsuarioActualizado = null;
		TarjetaUsuario tarjetaUsuarioActual = null;
		try {
			tarjetaUsuarioActual = tarjetaUsuarioService.findById(id);
			
			if(request.getEntidad().getFechaCaducidad() != null) {
				tarjetaUsuarioActual.setFechaCaducidad(request.getEntidad().getFechaCaducidad());
			}
			
			if(request.getEntidad().getNumeroCcv() != null) {
				tarjetaUsuarioActual.setNumeroCcv(request.getEntidad().getNumeroCcv());
			}
			
			tarjetaUsuarioActualizado = tarjetaUsuarioService.update(tarjetaUsuarioActual);
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(
					new RespuestaServicioGeneric(1,
							mensajes.getMessage("ext.Generico.error.update.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,
				mensajes.getMessage("text.Generico.update.success",
						new String[] { tarjetaUsuarioActualizado.getId().toString() }, LocaleContextHolder.getLocale()),
				tarjetaUsuarioActualizado), HttpStatus.CREATED);		
	}
	
	@SuppressWarnings("unused")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateStatus(@RequestBody InputRequest<TarjetaUsuario> request, @PathVariable Long id) {
		TarjetaUsuario tarjetaUsuarioActualizado = null;
		TarjetaUsuario tarjetaUsuarioActual = null;
		String respuesta = null;
		try {
			tarjetaUsuarioActual = tarjetaUsuarioService.findById(id);
			
			if (request.getEntidad().isEstadoTarjeta()) {
				if(request.getEntidad().isEstadoTarjeta() != tarjetaUsuarioActual.isEstadoTarjeta()) {
					tarjetaUsuarioActual.setEstadoTarjeta(request.getEntidad().isEstadoTarjeta());
					tarjetaUsuarioActualizado = tarjetaUsuarioService.update(tarjetaUsuarioActual);
					respuesta = mensajes.getMessage("text.Generico.registro.activar", null,
							LocaleContextHolder.getLocale());
				}			
			}else {
				if(request.getEntidad().isEstadoTarjeta() != tarjetaUsuarioActual.isEstadoTarjeta()) {
					tarjetaUsuarioActual.setEstadoTarjeta(request.getEntidad().isEstadoTarjeta());
					tarjetaUsuarioActualizado = tarjetaUsuarioService.update(tarjetaUsuarioActual);
					respuesta = mensajes.getMessage("text.Generico.registro.inactivar", null,
							LocaleContextHolder.getLocale());
				}
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(1,
					mensajes.getMessage("text.Generico.error.update.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,respuesta), HttpStatus.CREATED);
	}

}
