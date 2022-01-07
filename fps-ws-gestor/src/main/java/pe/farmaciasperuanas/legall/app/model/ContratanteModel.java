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
@Table(name = "contratante", schema = "inspeccion")
public class ContratanteModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contratante")
	private Long id_contratante;
	
	@Column(name = "id_ct_tipo_persona")
	private String id_ct_tipo_persona;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido_paterno")
	private String apellido_paterno;

	@Column(name = "apellido_materno")
	private String apellido_materno;

	@Column(name = "numero_documento")
	private String numero_documento;

	@Column(name = "razon_social")
	private String razon_social;

	@Column(name = "numero_ruc")
	private String numeroRuc;

	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "id_distrito")
	private String id_distrito;

	@Column(name = "telefono_movil")
	private String telefono_movil;

	@Column(name = "telefono_fijo")
	private String telefono_fijo;
	
	@Column(name = "usuario_creacion", updatable = false)
	private String usuario_creacion;
	
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fecha_creacion;

}
