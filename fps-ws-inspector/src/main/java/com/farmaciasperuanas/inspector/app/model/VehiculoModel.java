package com.farmaciasperuanas.inspector.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vehiculo_asegurado", schema = "inspeccion")
public class VehiculoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vehiculo_asegurado")
	private Long idVehiculoAsegurado;

	@Column(name = "placa")
	private String placa;

	@Column(name = "id_modelo")
	private Integer idModelo;

	@Column(name = "anyo")
	private Integer anyo;

	@Column(name = "color")
	private String color;

	@Column(name = "danyos")
	private String danyos;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "motor")
	private String motor;

	@Column(name = "vin")
	private String vin;

	@Column(name = "tarjeta_propiedad")
	private String tarjetaPropiedad;

	@Column(name = "numero_cilindros")
	private Integer numeroCilindros;

	@Column(name = "numero_puertas")
	private Integer numeroPuertas;

	@Column(name = "kilometraje")
	private Integer kilometraje;

	@Column(name = "usuario_creacion", updatable = false)
	private String usuarioCreacion;

	@Column(name = "usuario_modificacion")
	private String usuarioModificacion;

	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;
}

