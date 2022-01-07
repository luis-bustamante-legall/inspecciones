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
@Table(name = "asegurado", schema = "core")
public class AseguradoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_asegurado")
	private Long id_asegurado;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@Column(name = "id_ct_tipo_documento")
	private String id_ct_tipo_documento;

	@Column(name = "numero_documento")
	private String numero_documento;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "id_distrito")
	private Integer idDistrito;

	@Column(name = "telefono_movil")
	private String telefono_movil;

	@Column(name = "telefono_fijo")
	private String telefono_fijo;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "usuario_creacion", updatable = false)
	private String usuario_creacion;
	
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fecha_creacion;
}
