package com.nuvu.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false)
	private String nombreUsuario;
	
	@Column(name = "APELLIDO", nullable = false)
	private String apellidoUsuario;
	
	@Column(name = "IDENTIFICACION", nullable = false)
	private String identificacionUsuario;
	
	@ManyToOne
    @JoinColumn(name = "FK_TIPO_IDE")
	private TipoIdentificacion tipoIdentificacion;		
	

}
