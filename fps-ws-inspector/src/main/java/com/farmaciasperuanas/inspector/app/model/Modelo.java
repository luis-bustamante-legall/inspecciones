package com.farmaciasperuanas.inspector.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "modelo", schema = "core")
public class Modelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_modelo")
	private Long idModelo;

	@Column(name = "nombre")
	private String modelo;

	@Column(name = "id_marca")
	private Integer idMarca;

	@JsonbTransient
	@Column(name = "activo")
	private String estado;
}
