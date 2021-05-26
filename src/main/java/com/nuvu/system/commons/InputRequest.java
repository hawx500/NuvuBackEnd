package com.nuvu.system.commons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputRequest<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T entidad;

}
