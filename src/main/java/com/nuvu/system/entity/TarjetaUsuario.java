package com.nuvu.system.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TARJETA_USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NUM_TARJETA", nullable = false)
	private BigInteger numeroTarjeta;
	
	@Column(name = "NOM_TARJETA", nullable = false)
	private String nombreTarjeta;
	
	@Column(name = "FEC_CAD_TARJETA", nullable = false)
	private Date fechaCaducidad;
	
	@Column(name = "NUM_CCV_TARJETA", nullable = false)
	private Long numeroCcv;
	
	@Column(name = "ESTADO_TARJETA", nullable = false)
	@Type(type="yes_no")
	private boolean estadoTarjeta;
	
	@ManyToOne
    @JoinColumn(name = "FK_TIP_FRANQUICIA")
	private TipoFranquicia tipoFranquicia;
	
	@ManyToOne
	@JoinColumn(name = "FK_USUARIO")
	private Usuario usuario;

}
