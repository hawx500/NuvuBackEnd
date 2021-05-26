package com.nuvu.system.commons;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaServicioGeneric implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codigo;

	private String mensaje;

	private Object datos;

	public RespuestaServicioGeneric(int codigo, String mensaje) {

		this.codigo = codigo;
		this.mensaje = mensaje;
	}

}
