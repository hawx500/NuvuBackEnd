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
import com.nuvu.system.entity.Usuario;
import com.nuvu.system.service.IUsuario;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("usuario/operaciones")
@RestController
public class UsuarioController {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private IUsuario usuarioService;

	@Autowired
	private MessageSource mensajes;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody InputRequest<Usuario> request) {
		Usuario tarjetaUsuarioSave = null;

		String validacionUsuarioExistente = usuarioService.findByidentificacionUsuarioAndTipoIdentificacion(
				request.getEntidad().getIdentificacionUsuario(), request.getEntidad().getTipoIdentificacion());

		if (validacionUsuarioExistente != null) {
			return new ResponseEntity<RespuestaServicioGeneric>(
					new RespuestaServicioGeneric(1, validacionUsuarioExistente), HttpStatus.OK);
		}

		try {
			tarjetaUsuarioSave = usuarioService.save(request);
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(1,
					mensajes.getMessage("text.Generico.error.insert.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,
				mensajes.getMessage("text.Generico.insert.success",
						new String[] { tarjetaUsuarioSave.getId().toString() }, LocaleContextHolder.getLocale()),
				tarjetaUsuarioSave), HttpStatus.CREATED);
	}

	@GetMapping("/find")
	public ResponseEntity<?> find(@RequestParam(value = "datosFiltro") String datosFiltro) throws IOException {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		try {
			final Usuario usuarioFiltro = new ObjectMapper().setDateFormat(simpleDateFormat).readValue(datosFiltro,
					Usuario.class);

			listaUsuarios = usuarioService.findByCriteria(usuarioFiltro.getNombreUsuario(),
					usuarioFiltro.getApellidoUsuario(), usuarioFiltro.getIdentificacionUsuario(),
					usuarioFiltro.getTipoIdentificacion());

			if (listaUsuarios.isEmpty()) {
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
				listaUsuarios), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody InputRequest<Usuario> request, @PathVariable Long id) {
		Usuario usuarioActualizado = null;
		Usuario usuarioActual = null;
		try {
			
			usuarioActual = usuarioService.findById(id);
			
			if(request.getEntidad().getNombreUsuario() != null) {
				usuarioActual.setNombreUsuario(request.getEntidad().getNombreUsuario());
			}
			
			if(request.getEntidad().getApellidoUsuario() != null) {
				usuarioActual.setApellidoUsuario(request.getEntidad().getApellidoUsuario());
			}
			
			if(request.getEntidad().getIdentificacionUsuario() != null) {
				usuarioActual.setIdentificacionUsuario(request.getEntidad().getIdentificacionUsuario());
			}
			
			if(request.getEntidad().getTipoIdentificacion() != null) {
				usuarioActual.setTipoIdentificacion(request.getEntidad().getTipoIdentificacion());
			}
			
			
			usuarioActualizado = usuarioService.update(usuarioActual);
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(
					new RespuestaServicioGeneric(1,
							mensajes.getMessage("ext.Generico.error.update.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,
				mensajes.getMessage("text.Generico.update.success",
						new String[] { usuarioActualizado.getId().toString() }, LocaleContextHolder.getLocale()),
				usuarioActualizado), HttpStatus.CREATED);
	}

}
