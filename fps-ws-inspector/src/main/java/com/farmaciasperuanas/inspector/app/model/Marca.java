package com.farmaciasperuanas.inspector.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "marca", schema = "core")
public class Marca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_marca")
	private Long idMarca;

	@Column(name = "nombre")
	private String marca;

	@JsonbTransient
	@Column(name = "activo")
	private String estado;

}
