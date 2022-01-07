package pe.farmaciasperuanas.legall.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehiculo_asegurado", schema = "inspeccion")
public class VehiculoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vehiculo_asegurado")
	private Long id_vehiculo_asegurado;

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
	private String tarjeta_propiedad;

	@Column(name = "numero_cilindros")
	private Integer numero_cilindros;
	
	@Column(name = "numero_puertas")
	private Integer numero_puertas;

	@Column(name = "kilometraje")
	private Integer kilometraje;
	
	@Column(name = "usuario_creacion", updatable = false)
	private String usuario_creacion;

	@Column(name = "usuario_modificacion")
	private String usuario_modificacion;
	
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fecha_creacion;
}

