package com.nuvu.system.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nuvu.system.commons.RespuestaServicioGeneric;
import com.nuvu.system.entity.TipoFranquicia;
import com.nuvu.system.service.ITipoFranquicia;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("tipoFranquicia/operaciones")
@RestController
public class TipoFranquiciaController {

	@Autowired
	private ITipoFranquicia tipoFranquiciaService;

	@Autowired
	private MessageSource mensajes;

	@GetMapping("/find")
	public ResponseEntity<?> findAllTipoFranquicia() {
		List<TipoFranquicia> listTipoFranquicia = new ArrayList<TipoFranquicia>();
		try {			

			tipoFranquiciaService.findAll().forEach(listTipoFranquicia::add);

			if (listTipoFranquicia.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<RespuestaServicioGeneric>(
					new RespuestaServicioGeneric(1,
							mensajes.getMessage("text.Generico.busqueda.error", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<RespuestaServicioGeneric>(new RespuestaServicioGeneric(0,
				mensajes.getMessage("text.Generico.busqueda.success", null, LocaleContextHolder.getLocale()),
				listTipoFranquicia), HttpStatus.OK);
	}

}
