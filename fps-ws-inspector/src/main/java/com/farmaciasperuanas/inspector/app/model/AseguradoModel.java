package com.farmaciasperuanas.inspector.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "asegurado", schema = "core")
public class AseguradoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_asegurado")
	private Long idAsegurado;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@Column(name = "id_ct_tipo_documento")
	private String idCtTipoDocumento;

	@Column(name = "numero_documento")
	private String numeroDocumento;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "id_distrito")
	private Integer idDistrito;

	@Column(name = "telefono_movil")
	private String telefonoMovil;

	@Column(name = "telefono_fijo")
	private String telefonoFijo;

	@Column(name = "correo")
	private String correo;

	@Column(name = "usuario_creacion", updatable = false)
	private String usuarioCreacion;

	@Column(name = "usuario_modificacion")
	private String usuarioModificacion;

	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;
}
