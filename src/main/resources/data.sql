DROP TABLE IF EXISTS tipo_franquicia;
DROP TABLE IF EXISTS tipo_identificacion;
DROP TABLE IF EXISTS tarjeta_usuario;
DROP TABLE IF EXISTS usuario;

CREATE TABLE tipo_franquicia (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  franquicia VARCHAR(250) NOT NULL
);

insert into tipo_franquicia (franquicia) VALUES('Master Card');
insert into tipo_franquicia (franquicia) VALUES('Visa');
insert into tipo_franquicia (franquicia) VALUES('Diners');
commit;

CREATE TABLE tipo_identificacion (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  tipo_doc VARCHAR(100) NOT NULL
);

insert into tipo_identificacion (tipo_doc) values('CC');
insert into tipo_identificacion (tipo_doc) values('CE');
insert into tipo_identificacion (tipo_doc) values('TI');
insert into tipo_identificacion (tipo_doc) values('NIT');
commit;

CREATE TABLE usuario (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nombre VARCHAR(250) NOT NULL,
  apellido VARCHAR(250) NOT NULL,
  identificacion VARCHAR(10) NOT NULL,
  fk_tipo_ide NUMBER NOT NULL,  
  FOREIGN KEY (fk_tipo_ide) REFERENCES tipo_identificacion(id)  
);

ALTER TABLE usuario add constraint isusuario unique (identificacion);

CREATE TABLE tarjeta_usuario (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  num_tarjeta NUMBER NOT NULL,
  nom_tarjeta VARCHAR(250) NOT NULL,
  fec_cad_tarjeta DATE NOT NULL,
  num_ccv_tarjeta NUMBER NOT NULL,
  estado_tarjeta VARCHAR(5) NOT NULL,
  fk_tip_franquicia NUMBER NOT NULL,
  fk_usuario NUMBER NOT NULL,
  FOREIGN KEY (fk_tip_franquicia) REFERENCES tipo_franquicia(id),
  FOREIGN KEY (fk_usuario) REFERENCES usuario(id));
  
ALTER TABLE tarjeta_usuario add constraint isnum_tarjeta unique (num_tarjeta);



