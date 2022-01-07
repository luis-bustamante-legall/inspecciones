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
@Table(name = "tramite", schema = "inspeccion")
public class TramiteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tramite")
	private Long id_tramite;
	
	@Column(name = "nro_tramite_compania_seguro")
	private String numeroTramiteSeguro;
	
	@Column(name = "codigo_tramite")
	private String codigo_tramite;

	@Column(name = "codigo_tramite_legall")
	private String codigo_tramite_legall;

	@Column(name = "id_ct_estado_tramite")
	private String id_ct_estado_tramite;

	@Column(name = "id_empresa_cliente")
	private Integer id_empresa_cliente;

	@Column(name = "id_contratante")
	private Integer id_contratante;

	@Column(name = "id_broker")
	private Integer id_broker;
	
	@Column(name = "fecha_asignacion_compania_seguro")
	private String fecha_asignacion_compania_seguro;
	
	@Column(name = "observacion_programacion")
	private String observacion_programacion;

	@Column(name = "usuario_creacion", updatable = false)
	private String usuario_creacion;

	@Column(name = "usuario_modificacion")
	private String usuario_modificacion;
	
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fecha_creacion;

//	@Column(name = "fecha_modificacion")
//	private LocalDateTime fecha_modificacion;
}
