package pe.farmaciasperuanas.legall.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inspeccion", schema = "inspeccion")
public class InspeccionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inspeccion")
	private Long id_inspeccion;

	@Column(name = "id_vehiculo_asegurado")
	private Integer id_vehiculo_asegurado;

	@Column(name = "codigo_inspeccion")
	private String codigo_inspeccion;

	@Column(name = "codigo_inspeccion_legall")
	private String codigo_inspeccion_legall;

	@Column(name = "id_ct_estado_inspeccion")
	private String id_ct_estado_inspeccion;

	@Column(name = "id_tramite")
	private Integer id_tramite;

	@Column(name = "id_distrito")
	private String id_distrito;

	@Column(name = "id_empleado_inspector")
	private Integer id_empleado_inspector;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
//	@Column(name = "fecha_termino")
//	private LocalDateTime fecha_termino;
	
	@Column(name = "direccion_inspeccion")
	private String direccion_inspeccion;
	
	@Column(name = "usuario_creacion", updatable = false)
	private String usuario_creacion;
	
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fecha_creacion;

}
